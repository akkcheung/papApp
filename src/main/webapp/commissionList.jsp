<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Call List</title>

<link rel="stylesheet" type="text/css"
	href="./js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="./js/themes/icon.css">
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.easyui.min.js"></script>

<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 100px;
}

.fitem input {
	width: 160px;
}
</style>

<!--  style="width:5100px;height:2100px"  -->
<table id="dg" title="Call List" class="easyui-datagrid"
	url="./json/getCust.do" toolbar="#toolbar" rownumbers="true"
	fitColumns="true" singleSelect="true">
	<thead>
		<tr>
			<th field="createdt" width="100">日期</th>
			<th field="code" width="100">CODE</th>
			
			<!-- todo : Ask for detail -->
			<th field="#" width="100">Contact Person</th>
			<th field="source" width="100">來源</th>
			
			<!-- lov , sales.name -->
			<th field="sales" width="100">主任</th>
			
			<!-- cust.name -->
			<th field="name" width="100">客人全名</th>
			
			<!-- cust.sex -->
			<th field="sex" width="100">男/女</th>
			
			<!-- cust.tel -->
			<th field="tel" width="100">TEL</th>
			
			<!-- 已簽, 取消 -->
			<!-- event.status -->
			<th field="status" width="100">近況</th>

			<!-- event.descr -->
			<th field="descr" width="100">進度</th>

			<!-- event.remark -->
			<th field="remark" width="100">備註</th>			

			<th field="loanAmt" width="100">貸款金額</th>
			<th field="loanUsage" width="100">貸款用途</th>
			
			<th field="status" width="100">近況</th>
			
			<!-- todo ? 20% -->
			<th field="" width="100">入金</th>
			<!-- todo ? -->
			<th field="" width="100">收錢日</th>
			
		</tr>
	</thead>
</table>

<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
		onclick="newRec()">增加</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-edit" plain="true" onclick="editRec()">更改</a> <a
		href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		onclick="destroyRec()">刪除</a>
</div>

<div id="dlg" class="easyui-dialog"
	style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
	buttons="#dlg-buttons">
	<div class="ftitle">進度 Information</div>
	<form id="fm" method="post" novalidate>
		
		<div class="fitem">
			<label>日期</label> <input name="createdt" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label>CODE</label> <input name="code" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label>來源</label> 
		</div>
		<div class="fitem">
			<label>主任</label> 
		</div>
		<div class="fitem">
			<label>客人全名</label> 
		</div>
		<div class="fitem">
			<label>男/女</label> 
		</div>
		<div class="fitem">
			<label>近況</label> 
		</div>
		<div class="fitem">
			<label>貸款金額</label> <input name="loanAmt" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label>貸款用途</label> <input name="loanUsage" class="easyui-textbox">
		</div>
		<div class="fitem">
			<label>收錢日</label> <input name="loanRcvDt" class="easyui-textbox">
		</div>
		<div class="fitem">
			<label>status</label> <input name="status" class="easyui-textbox">
		</div>
		
	</form>
</div>

<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton c6"
		iconCls="icon-ok" onclick="save()" style="width: 90px">Save</a> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
		style="width: 90px">Cancel</a>
</div>

<script type="text/javascript">
	var url;
	function newRec() {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle',
				'New Call Information');
		$('#fm').form('clear');
		url = 'save_user.php';
	}
	function editRec() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('center').dialog('setTitle',
					'Edit Call Information');
			$('#fm').form('load', row);
			url = 'update_user.php?id=' + row.id;
		}
	}
	function saveRec() {
		$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.errorMsg) {
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				} else {
					$('#dlg').dialog('close'); // close the dialog
					$('#dg').datagrid('reload'); // reload the user data
				}
			}
		});
	}
	function destroyRec() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm',
					'Are you sure you want to destroy this user?', function(r) {
						if (r) {
							$.post('destroy_user.php', {
								id : row.id
							}, function(result) {
								if (result.success) {
									$('#dg').datagrid('reload'); // reload the user data
								} else {
									$.messager.show({ // show error message
										title : 'Error',
										msg : result.errorMsg
									});
								}
							}, 'json');
						}
					});
		}
	}
</script>