<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Uploadify Test</title>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="uploadify/uploadify.css">
<script type="text/javascript" src="uploadify/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="uploadify/jquery.uploadify.min.js"></script>
<style type="text/css"> body {font: 13px Arial, Helvetica, Sans-serif;}</style>
<script type="text/javascript">
	$(function() {
		$("#uploadify").uploadify({
			'uploader' : 'servlet/fileUpload',
			'uploadLimit' : 5,
			'swf' : '/uploadify/uploadify/uploadify.swf',
			'buttonText' : '选择图片',
			//'fileObjName' : 'fileName',
			//'formData' : {'someKey' : 'someValue', 'someOtherKey' : 1},
			'width' : 84,
			'height' : 22,
			'multi' : true,
			'auto' : true,
			'removeCompleted' : false,
			'queueID' : 'queue_id',
			'fileTypeDesc' : '图片文件',
			'fileTypeExts' : '*.gif;*.jpg;*.jpeg;*.png', //允许上传的文件类型，使用分号分割
			'fileSizeLimit' : '6000KB'
		});
	});
</script>
</head>
<body>
	<form>
		<p><input type="file" id="uploadify" name="uploadify" /></p>	
		<p id="queue_id" style="width: 350px;"></p>
	</form>
</body>
</html>