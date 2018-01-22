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
<title>团员可购商品</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/confirm_an_order.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/swiper-3.4.2.jquery.min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/tuan_buy.js"></script>
</head>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var shopId = '${shopId}';
	//var fromWay = '${fromWay}';
</script>
<body>
<div class="main-cont" id="main-cont" >
	
    <div class="main-box">
    </div>    	
    <div class="bottom_box">
    	<div class="all_btn"><em onclick="selAll()"></em>全选</div>
        <div class="bottom_box_rt">
        	<div class="rt_con"></div>
            <a href="javascript:confirmOrder();">确定</a>
            <input type="hidden" id="sel_all" value="1">
        </div>
    </div>
</div>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
<script>
</script>
</html>
