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
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>我的一起购</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/swiper-3.4.2.min.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/confirm_an_order.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/I_buy_it_together.js?0109"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/handleTime.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var userId = GetUidCookie();
</script>
</head> 

<body>
<div class="main-cont" id="main-cont" >
	<input id="pageNum" value="" type="hidden">
</div>
<script type="text/javascript" src="<%=static_resources %>js/swiper-3.4.2.jquery.min.js"></script>
</body>
</html>
