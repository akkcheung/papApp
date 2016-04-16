<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  	
  	<!-- add the jQuery script -->
    <script type="text/javascript" src="/scripts/jquery-1.11.0.min.js"></script>	
    <!-- add the jQWidgets framework -->
    <script type="text/javascript" src="/jqwidgets/jqxcore.js"></script>
    <!-- add one or more widgets -->
    <script type="text/javascript" src="/jqwidgets/jqxbuttons.js"></script>
    <!-- add one of the jQWidgets styles -->
    <link rel="stylesheet" href="/jqwidgets/styles/jqx.darkblue.css" type="text/css" />
</head>
<body>
<script type="text/javascript">
        $(document).ready(function () {
            $("#myButton").jqxButton({ width: '120px', height: '35px', theme: 'darkblue'});
        });
    </script>
	
    <input type="button" value="Click Me" id='myButton'/>
</body>
</html>