<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html> 
<head><title>test</title> 
<style type="text/css">@import url(<%=basePath%>/plupload/js/jquery.plupload.queue/css/jquery.plupload.queue.css);</style>
<script type="text/javascript" src="<%=basePath%>/js/jquery-1.6.2.min.js" ></script> 
<script type="text/javascript" src="<%=basePath%>/plupload/js/plupload.full.js"></script>
<script type="text/javascript" src="<%=basePath%>/plupload/js/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<script type="text/javascript" src="<%=basePath%>/plupload/js/i18n/cn.js"></script>
<script type="text/javascript">
	// Convert divs to queue widgets when the DOM is ready
	$(function() {
		$("#uploader").pluploadQueue({
			runtimes : 'gears,flash,silverlight,browserplus,html5,html4',// General settings
			url : 'FileUpload.action',
			max_file_size : '100mb',
			unique_names : true,
			chunk_size: '1mb',
			resize: { width: 320, height: 240, quality: 100},
			filters : [// Specify what files to browse for
				{title : "Image files", extensions : "jpg,gif,png"},
				{title : "Zip files", extensions : "zip"},
				{title : "xls, xlsx文档", extensions : "xls,xlsx"}
			],
			flash_swf_url : '<%=basePath%>/plupload/js/plupload.flash.swf',
			silverlight_xap_url : '<%=basePath%>/plupload/js/plupload.silverlight.xap'
		});
		
		
		$('form').submit(function(e) {
	        var uploader = $('#uploader').pluploadQueue();
	        if (uploader.files.length > 0) {
	            uploader.bind('StateChanged', function() { // When all files are uploaded submit form
	                if (uploader.files.length === (uploader.total.uploaded + uploader.total.failed)) {
	                    $('form')[0].submit();
	                }
	            });
	            uploader.start();
	        } else {
				alert('请先上传数据文件.');
			}
	        return false;
    	});
  
	});
</script>
</head>
<body>
	<div>
		<div style="width: 750px; margin: 0px auto">
			<form action="Submit.action" method="post">
				<div id="uploader">
					<p>您的浏览器未安装 Flash, Silverlight, Gears, BrowserPlus 或者支持 HTML5 .</p>
				</div>
				<input type="submit" value="完成"/>
			</form>
		</div>
	</div>
</body>
</html>