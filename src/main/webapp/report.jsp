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

	<form id="ff" method="post">
		<div class="fitem">
			<label for="pYr">年份:</label> <input class="easyui-validatebox"
				type="text" name="pYr" data-options="required:true" />
		</div>
		<div class="fitem">
			<label for="pMon">月份:</label> <input class="easyui-validatebox"
				type="text" name="pMon" data-options="required:true" />
		</div>

		<div>
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				 onclick="submit_0()" >Sales Daily Worksheet Report</a>
		</div>
		
		<div>
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				 onclick="submit_1()" >Performance Report - MIL</a>
		</div>
		
		<div>
			<a href="javascript:void(0)" class="easyui-linkbutton c6"
				 onclick="submit_2()" >Performance Report - Team </a>
		</div>
	</form>

	<script type="text/javascript">
		function submit_0() {
			$('#ff').form('submit', {
				url : './jasper/genSalesDailyWsRpt.do',
				onSubmit : function() {
					// do some check
					// return false to prevent submit;
					return $(this).form('validate');
				},
				success : function(data) {
					alert(data)
				}
			});
		}
		
		function submit_1() {
			$('#ff').form('submit', {
				url : './jasper/genPerformanceRpt.do',
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(data) {
					alert(data)
				}
			});
		}
		
		function submit_2() {
			$('#ff').form('submit', {
				url : './jasper/genPerformanceTeamRpt.do',
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(data) {
					alert(data)
				}
			});
		}
	</script>

</body>
</html>