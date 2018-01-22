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
<title>我的分享</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
</head>

<body>
<div class="main-cont" id="main-cont">	
    <div class="main-box" style="top:3px;">
        <ul class="my_share">
        </ul>
    </div>
    <div class="bottom" style="text-align:right;position: fixed;bottom: 5px; display: none">
    	<a href="http://www.wd-w.com/app.htm?d=1"><img src="<%=static_resources %>/images/app_share_open.png" /></a>
    </div>
</div>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/zepto.min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/my_share_list.js"></script>

</body>
</html>
