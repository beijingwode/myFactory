<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/stamps_css/My_stamps.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/limit_ticket/select_stamps_order.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/handleTime.js"></script>

<title>选择优惠券</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var limitTicketIds = '${limitTicketIds}';
	var skuIds = '${skuIds}';
</script>
</head>

<body>
<div class="main-cont" id="main-cont">   
    <div class="main-box">
    	<ul>
    	</ul>
    </div>
     <div class="use_btn"><a href="javascript:;" class="no_btn">不使用</a><a href="javascript:;" class="yes_btn">去使用</a></div>
</div>
</body>
</html>

