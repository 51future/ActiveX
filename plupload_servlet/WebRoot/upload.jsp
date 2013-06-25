<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head><title>test</title>
<link  type="text/css" rel="stylesheet" href="<%=basePath%>/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css" type="text/css" media="screen" />
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/plupload/js/plupload.full.js"></script>
<script type="text/javascript" src="<%=basePath%>/plupload/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<script type="text/javascript" src="<%=basePath%>/plupload/js/i18n/cn.js"></script>
<script type="text/javascript">
	$(function() {
		$("#uploader").pluploadQueue({
			runtimes : 'gears,flash,silverlight,browserplus,html5,html4',
			url : 'servlet/fileUpload',
			max_file_size : '10mb',
			unique_names : true,
			chunk_size : '2mb',
			resize: { width: 320, height: 240, quality: 100},
			filters : [// Specify what files to browse for
				{title : "Image files", extensions : "jpg,gif,png"},
				{title : "Zip files", extensions : "zip"},
				{title : "xls, xlsx文档", extensions : "xls,xlsx"}
			],
			flash_swf_url : '<%=basePath%>/plupload/js/plupload.flash.swf',
			silverlight_xap_url : '<%=basePath%>/plupload/js/plupload.silverlight.xap',
			multipart_params : { 'user' : 'wbin', 'time' : '2013-06-25'}
		});
	});
</script>
</head>
<body>
	<div>
		<div style="width: 750px; margin: 0px auto">
			<form id="formId" action="Submit.action" method="post">
				<div id="uploader">
					<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
				</div>
			</form>
		</div>
	</div>
</body>

</html>