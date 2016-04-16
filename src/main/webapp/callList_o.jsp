<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

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

<!--  style="width:5100px;height:2100px"  -->
<table id="dg" title="電話記錄" class="easyui-datagrid"
	url="./json/getCust.do" toolbar="#toolbar" rownumbers="true"
	fitColumns="true" singleSelect="true"
	pagination="true">
	<thead>
		<tr>


			<th field="source" width="100" sortable="true">來源</th>


			<th field="createdt" width="100" sortable="true">日期</th> 
			
			<!-- <th field="sales_id" 
			formatter="(function(value,row, index){ if (row.sales) {return row.sales.id;} else {return value;} })"			
			width="100">SALES ID</th> -->
			
			<th field="sales" 
			formatter="(function(value,row, index){ if (row.sales) {return row.sales.name;} else {return value;} })"			
			 width="100">SALES</th>
		 
			<th field="name" width="100">姓名</th>
			<th field="identity" width="100">ID</th>
			<th field="tel" width="100">TEL</th>
			<th field="sex" width="100">性別</th>
			<th field="occupation" width="100">職業</th>

			<th field="salary" width="100" sortable="true">人工</th>
			<th field="debt" width="100">外債</th>

			<th field="ppty" width="100">物業資料</th>
			<th field="addr" width="100">地址</th>
			
			<th field="status" width="100">近況</th>
		</tr>
	</thead>
</table>

<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
		onclick="newRec()">增加</a>
	<a href="#" class="easyui-linkbutton"
		iconCls="icon-edit" plain="true" onclick="editRec()">更改</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		onclick="destroyRec()">刪除</a>
	<a href="#" class="easyui-linkbutton"  plain="true"
		onclick="newPlan()">開單</a>
	<br/>
	<span>姓名 : </span>
    <input id="name_search" style="line-height:26px;border:1px solid #ccc">
    <span>電話 : </span>
    <input id="tel_search" style="line-height:26px;border:1px solid #ccc">
    <a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">搜索</a>
</div>

<div id="dlg" class="easyui-dialog"
	style="width: 600px; height: 280px; padding: 10px 20px" closed="true"
	buttons="#dlg-buttons">
	<div class="ftitle">客人記錄</div>
	<form id="fm" method="post" novalidate>
	
		<div class="fitem">
				<label>id</label> <input name="id" class="easyui-textbox"
					readonly="true" />
		</div>
		<div class="fitem">
			<label>來源</label> <input name="source" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label>日期</label> <input name="createdt" class="easyui-datebox"
				required="true">
		</div>
		
		<div class="fitem">
			<label>SALES</label> 
			<!-- <input name="sales" class="easyui-textbox"
				required="true">
			 -->
			<input class="easyui-combobox" id=sales name="sales_id"
            data-options="valueField:'id',textField:'name'"
            >
			
		</div>
		<div class="fitem">
			<label>姓名</label> <input name="name" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label>ID</label> <input name="identity" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label>TEL</label> <input name="tel" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label>SEX</label> <select name="sex" class="easyui-combobox"
				required="true">
				<option></option>
				<option>M</option>
				<option>F</option>
				</select>
		</div>
		<div class="fitem">
			<label>職業</label> <input name="occupation" class="easyui-textbox"
				required="true">
		</div>
		<div class="fitem">
			<label>人工</label> <input name="salary" class="easyui-textbox">
		</div>
		<div class="fitem">
			<label>外債</label> <input name="debt" class="easyui-textbox">
		</div>
		<div class="fitem">
			<label>物業資料</label> <input name="ppty" class="easyui-textbox" >
		</div>
		<div class="fitem">
			<label>地址</label> <input name="addr" class="easyui-textbox" data-options="multiline:true" 
				style="width:300px;height:100px">
		</div>
		<div class="fitem">
			<label>近況</label> <input name="status" class="easyui-textbox">
		</div>
	</form>
</div>

<div id="dlg-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton c6"
		iconCls="icon-ok" onclick="saveRec()" style="width: 90px">Save</a>
	
	<a href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')"
		style="width: 90px">Cancel</a>
</div>

<script type="text/javascript">
	var url;
	function newRec() {
		$('#dlg').dialog('open').dialog('center').dialog('setTitle',
				'');
		$('#fm').form('clear');
		url = './json/addCust.do';
		$('#sales').combobox('reload', './json/getSalesLov.do');
	}
	function editRec() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('center').dialog('setTitle',
					'');
			$('#sales').combobox('reload', './json/getSalesLov.do');
			$('#fm').form('load', row);
			
			// console.log(row.sales.name);
			
			if  (typeof(row.sales) != "undefined" ) {
				$('input[name="sales_id"]').val(row.sales.id) ;
			} else {
				$('input[name="sales_id"]').val("");
			}
			
			url = './json/editCust.do' ;
			
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
	function newPlan() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
					
			$.messager.confirm('Confirm',
					'確認開單?', function(r) {
						if (r) {
							$.post('./json/newPlan.do', {
								id : row.id
							}, function(result) {
								if (result.success) {
									$.messager.show({ // show error message
										title : '',
										msg : result.success
									});
									$('#dg').datagrid('reload'); // reload the user data
								} else {
									$.messager.show({ // show error message
										title : '提示',
										msg : result.errorMsg
									});
								}
							}, 'json');
						}
					});
		}
	}
	
	// Search function
    function doSearch(){
        $('#dg').datagrid('load',{
            name_search : $('#name_search').val(),
            tel_search : $('#tel_search').val()
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
   
	
</script>
</body>
</html>