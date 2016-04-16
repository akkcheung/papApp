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

<script type="text/javascript" src="./js/datagrid-detailview.js"></script>

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

<!--  style="width:5100px;height:2100px"  -->
<table id="dg" title="交單" class="easyui-datagrid"
	url="./json/getPlan.do" toolbar="#toolbar" rownumbers="true"
	fitColumns="true" singleSelect="true"
	pagination="true">
	<thead>
		<tr>
			<th field="createdDt" width="100"
				>日期</th>
			<th field="code" width="100" sortable=true>CODE</th>

			<th field="P.cust.source"
				formatter="(function(value,row, index){ if (row.cust) {return row.cust.source;} else {return value;} })"
				width="100" sortable=true>來源</th>

			<!-- lov , sales.name -->
			<th field="P.cust.sales.name"
				formatter="(function(value,row, index){ if (row.cust.sales) {return row.cust.sales.name;} else {return value;} })"
				width="100" sortable=true>主任</th>

			<!-- cust.name -->
			<th field="name"
				formatter="(function(value,row, index){ if (row.cust) {return row.cust.name;} else {return value;} })"
				width="100">客人全名</th>

			<!-- cust.sex -->
			<th field="sex" 
			formatter="(function(value,row, index){ if (row.cust) {return row.cust.sex;} else {return value;} })"
			width="100">男/女</th>

			<!-- cust.tel -->
			<th field="tel"
				formatter="(function(value,row, index){ if (row.cust) {return row.cust.tel;} else {return value;} })"
				width="100">電話</th>

			<!-- 已簽, 取消 -->
			<!-- event.status -->
			<th field="cust_status"
				formatter="(function(value,row, index){ if (row.cust) {return row.cust.status;} else {return value;} })"
				width="100">客人近況</th>

			<th field="remark" width="100">備註</th>

			<th field="loanAmt" width="100">貸款金額</th>
			<th field="loanUsage" width="100">貸款用途</th>

			<th field="status" width="100">狀態</th>
			<!-- 
			todo ? 20%
			<th field="" width="100">入金</th>
			todo ?
			<th field="" width="100">收錢日</th>
			 -->
		</tr>
	</thead>
</table>



<div id="toolbar">
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
		onclick="newRec()">增加</a>  -->
	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
		onclick="editRec()">更改</a> <a href="#" class="easyui-linkbutton"
		iconCls="icon-remove" plain="true" onclick="destroyRec()">刪除</a>日 <a
		href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
		onclick="newEventRec()">增加進度</a>
</div>

<div id="dlg" class="easyui-dialog"
	style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
	buttons="#dlg-buttons">
	<div class="ftitle">交單</div>
	<form id="fm" method="post" novalidate>

		<div class="fitem">
			<label>id</label> <input name="id" class="easyui-textbox"
				readonly="true" />
		</div>

		<div class="fitem">
			<label>日期</label> 
			<input name="createdDt" class="easyui-datebox"
				required="true">
			<!-- 	<input name="createdDt" class="easyui-textbox"
				required="true"> -->
				
		</div>
		<div class="fitem">
			<label>CODE</label> <input name="code" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label id=source>來源</label>
		</div>
		<div class="fitem">
			<label id=sales_name>主任</label>
		</div>
		<div class="fitem">
			<label id="cust_name">客人全名</label>
			<!-- <input name="cust_name" id="cust_name" class="easyui-textbox"/> -->
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
			<label>收錢日</label> <input name="loanRcvDt" class="easyui-datebox">
		</div>
		<div class="fitem">
			<label>狀態</label> <select id="status" class="easyui-combobox"
				name="status" style="width: 200px;">
				<option>已簽約</option>
				<option>未簽約</option>
				<option>已批</option>			
				<option>催收中</option>				
				<option>取消</option>
				<option>已約</option>
			</select>
		</div>

	</form>
</div>

<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-ok" onclick="saveRec()" style="width: 90px">Save</a> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
		style="width: 90px">Cancel</a>
</div>

<!-- 
<div id="event_toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
		onclick="newEvent()">增加</a> 
	<a href="#" class="easyui-linkbutton"  iconCls="icon-edit" plain="true" 
		onclick="editEvent(this)">更改</a>
    <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		onclick="destroyEvent()">刪除</a>
</div>
 -->

<div id="event_dlg" class="easyui-dialog"
	style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
	buttons="#event-dlg-buttons">
	<div class="ftitle">進度</div>
	<form id="event_fm" method="post" novalidate>

		<div class="fitem">
			<label>id</label> <input name="id" class="easyui-textbox"
				readonly="true" />
		</div>

		<div class="fitem">
			<label>建立日期</label> <input name="created" class="easyui-datebox"
				required="true">
		</div>
		<div class="fitem">
			<label>內容</label> <input name="descr" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label>備註</label> <input name="remark" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label>狀態</label> <input name="status" class="easyui-textbox">
		</div>
		<div class="fitem">
			<label>下次提示日期</label> <input name="alarm" class="easyui-datebox">
		</div>
	</form>
</div>

<div id="event-dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-ok" onclick="saveEventRec()" style="width: 90px">Save</a> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-cancel" onclick="javascript:$('#event_dlg').dialog('close')"
		style="width: 90px">Cancel</a>
</div>

<script type="text/javascript">
	var url;
	function newRec() {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle', '');
		$('#fm').form('clear');
		url = 'save_user.php';
	}
	function editRec() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('center').dialog('setTitle', '');
			$('#fm').form('load', row);
			url = './json/editPlan.do';
		}

		if (typeof (row.cust) != "undefined") {
			// $('input:text[id=cust_name]').val(row.cust.name) ;
			$('#source').text("來源 : " + row.cust.source);
			$('#sales_name').text("主任 : " + row.cust.sales.name);
			$('#cust_name').text("客人全名 : " + row.cust.name);
			console.log(row.cust.name);

		} else {
			// $('input[name="cust_name"]').val("");
		}
	}
	function saveRec() {

		console.log(url);

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

	// sub-grid 
	$('#dg')
			.datagrid(
					{
						view : detailview,
						detailFormatter : function(index, row) {
							return '<div style="padding:2px"><table class="ddv"></table></div>';
						},
						onExpandRow : function(index, row) {
							var ddv = $(this).datagrid('getRowDetail', index)
									.find('table.ddv');
							ddv
									.datagrid({
										//url:'datagrid22_getdetail.php?itemid='+row.id,
										url : './json/getEvents.do?plan_id='
												+ row.id,
										fitColumns : true,
										singleSelect : true,
										rownumbers : true,
										loadMsg : '',
										// height:'auto',
										height : '200px',
										toolbar : '#event_toolbar',
										columns : [ [
												{
													field : 'action',
													title : 'Action',
													formatter : function(value,
															row, index) {
														var s = '<button onclick="editEventRecord(this)">更改</button> ';
														// var s = '<a href="#" class="easyui-linkbutton"  iconCls="icon-edit" plain="true" onclick="editRecord(this)">更改</a> ';
														return s;
													}
												}, {
													field : 'id',
													title : 'Event ID',
													width : 10
												}, {
													field : 'created',
													title : '建立日期',
													width : 50
												}, {
													field : 'descr',
													title : '內容',
													width : 100
												}, {
													field : 'remark',
													title : '備註',
													width : 100
												}, {
													field : 'status',
													title : '狀態',
													width : 100
												}, {
													field : 'alarm',
													title : '下次提示日期',
													width : 50
												} ] ],
										onResize : function() {
											$('#dg')
													.datagrid(
															'fixDetailRowHeight',
															index);
										},
										onLoadSuccess : function() {
											setTimeout(function() {
												$('#dg').datagrid(
														'fixDetailRowHeight',
														index);
											}, 0);
										}
									});
							$('#dg').datagrid('fixDetailRowHeight', index);
						}
					});

	// Event Operations
	function newEventRec() {

		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#event_dlg').dialog('open').dialog('center').dialog('setTitle',
					'');
			$('#event_fm').form('clear');
			url = './json/newEvent.do?plan_id=' + row.id;
			console.log(url);
		}
	}
	function editEventRecord(btn) {
		var tr = $(btn).closest('tr.datagrid-row');
		var index = parseInt(tr.attr('datagrid-row-index'));
		var dg = tr.closest('div.datagrid-view').children('table');
		var row = dg.datagrid('getRows')[index];

		// console.log(row)

		if (row) {
			$('#event_dlg').dialog('open').dialog('center').dialog('setTitle',
					'');
			$('#event_fm').form('load', row);
			// url = 'update_user.php?id=' + row.id;
			url='./json/editEvent.do';
		}
	}
	function saveEventRec() {

		console.log(url);

		$('#event_fm').form('submit', {
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
					$('#event_dlg').dialog('close'); // close the dialog
					// $('#dg').datagrid('reload'); // reload the user data
					
					var selectedrow = $("#dg").datagrid("getSelected");
					console.log("selectedrow" + selectedrow);
					
					var index = $("#dg").datagrid("getRowIndex", selectedrow);
					console.log("index" + index);

					var ddv = $("#dg").datagrid('getRowDetail', index)
									.find('table.ddv');
					ddv.datagrid('reload');
				}
			}
		});
	}
	
	// format easyui-datebox
	$('.easyui-datebox').datebox({
        formatter : function(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return (d<10?('0'+d):d)+'-'+(m<10?('0'+m):m)+'-'+y;
            // return y +'-'+(m<10?('0'+m):m)+'-'+ (d<10?('0'+d):d);
        },
        parser : function(s){

            if (!s) return new Date();
            var ss = s.split('-');
            var y = parseInt(ss[2],10);
            var m = parseInt(ss[1],10);
            var d = parseInt(ss[0],10);
            if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
                return new Date(y,m-1,d)
            } else {
                return new Date();
            }
        }

    }); 
	
	$('#dg').datagrid({
	        rowStyler:function(index,row){
	            if (row.status == '已簽約'){
	                // return 'background-color:pink;color:blue;font-weight:bold;';
	                return 'color:green;';
	            }
	            if (row.status == '未簽約'){
	                // return 'background-color:pink;color:blue;font-weight:bold;';
	                return 'color:yellow;font-weight:bold';
	            }
	            if (row.status == '已批'){
	                // return 'background-color:pink;color:blue;font-weight:bold;';
	                return 'color:blue;';
	            }
	            if (row.status == '取消'){
	                // return 'background-color:pink;color:blue;font-weight:bold;';
	                return 'color:red;';
	            }
	        }
	});
</script>

</body>
</html>