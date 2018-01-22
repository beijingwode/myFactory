<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
if(request.getServerPort() != 80 && request.getServerPort() != 443) {
	path=":"+request.getServerPort()+path;
}
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<title>推送短信</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
</head>

<body style="background:#fff;">
<div class="main-cont" id="main-cont">   
    <div class="main-box">
    	<a href="javascript:toAddMsgPage();"><img src="<%=static_resources %>images/push_message/push_message_pic.jpg" /></a>
    </div>    
</div>
</body>
<script type="text/javascript">
	function toAddMsgPage(){
		window.location=jsBasePath+'acticity/toPushMsgPage';
	}
</script>
</html>
