<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head><title>result</title>
</head>
<body>
	<div style="width: 750px; margin: 0px auto">
		<div>导入结果</div>
		<div>
			<p>${resultString}</p>
		</div>
	</div>
</body>
</html>
