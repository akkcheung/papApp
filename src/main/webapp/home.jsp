<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>

<link rel="stylesheet" type="text/css"
	href="./js/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="./js/themes/icon.css">
<script type="text/javascript" src="./js/jquery.min.js"></script>
<script type="text/javascript" src="./js/jquery.easyui.min.js"></script>
</head>

<body>
<jsp:include page="/menu.jsp"></jsp:include>

<table id="dg" title="到期提示" class="easyui-datagrid"
	url="./json/getOsEvents.do" 
	fitColumns="true" singleSelect="true"
	>
	<thead>
		<tr>

			<th field="alarm" width="100" sortable="true">到期日</th>

			<th field="descr" width="100" sortable="true">內容</th> 
			
			<th field="plan" 
			formatter="(function(value,row, index){ if (row.plan) {return row.plan.code;} else {return value;} })"			
			 width="100">單號</th>
		 
			<th field="name" 
			formatter="(function(value,row, index){ if (row.plan.cust) {return row.plan.cust.name;} else {return value;} })"
			width="100">客人姓名</th>
			
			<th field="tel" 
			formatter="(function(value,row, index){ if (row.plan.cust) {return row.plan.cust.tel;} else {return value;} })"			
			width="100">電話</th>
			
			<!-- 
			<th field="sex"
			formatter="(function(value,row, index){ if (row.plan.cust) {return row.plan.cust.tel;} else {return value;} })"						 
			width="100">性別</th>
			 -->
			
			</tr>
	</thead>
</table>
</body>
</html>