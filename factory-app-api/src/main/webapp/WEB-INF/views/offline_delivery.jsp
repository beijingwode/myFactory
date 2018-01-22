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
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/public.css"/>
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/ShopSavvy.css"/>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/offline_delivery.js"></script>
<title>线下发货</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var uid = '${uid}';
</script>
</head>
<body>
<div class="main-cont" id="main-cont" >
	<div class="main-box" style="padding:0 0 60px 0;">
	</div>
	<div class="bottom_btn" style="width:92%;height:auto;padding:15px 4%;position:fixed;bottom:0;left:0;">
		<span id="spanI"></span><p>只查看今日记录</p>
	</div>
	<input id="pageNum" value="" type="hidden">
</div>
</body>
<%@ include file="/commons/alertMessage.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/swiper-3.4.2.jquery.min.js"></script>
</html>
