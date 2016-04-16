<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户管理系統</title>

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

</head>

<body>

<jsp:include page="/menu.jsp"></jsp:include>

	<table id="dg" title="" class="easyui-datagrid"
		url="./json/getSales.do" toolbar="#toolbar" rownumbers="true"
		fitColumns="true" singleSelect="true" pagination="true">
		<thead>
			<tr>
				<th field="name" width="100" sortable="true">姓名</th>
				<th field="team" width="100" sortable="true">Team</th>
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
		style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">銷售員記錄</div>
		<form id="fm" method="post" novalidate>

			<div class="fitem">
				<label>id</label> <input name="id" class="easyui-textbox"
					readonly="true" />
			</div>
			<div class="fitem">
				<label>姓名</label> <input name="name" class="easyui-textbox"
					required="true">
			</div>
			<div class="fitem">
				<label>Team</label> <input name="team" class="easyui-textbox"
					required="true">
			</div>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="saveRec()" style="width: 90px">Save</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
			style="width: 90px">Cancel</a>
	</div>
	
	<script type="text/javascript">
	var url;
	function newRec() {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle',
				'');
		$('#fm').form('clear');
		url = './json/addSales.do';
	}
	function editRec() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('center').dialog('setTitle',
					'');
			$('#fm').form('load', row);
			url = './json/editSales.do' ;			
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

</body>