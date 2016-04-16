<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<body>

<p>

<h3 align="center"> 客户管理系統 </h3>

<center> 
 
<s:form action="loginUser.do">
 
<s:textfield name="userName" label="帳戶" /><br>

<s:password name="password" label="密碼" /><br>


<s:submit value="Click" align="center" />
 
</s:form>

</center>
</body>
</html>