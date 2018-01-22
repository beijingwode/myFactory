<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/transaction_management.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/wxGetUid.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/user_info.js"></script>
<title>个人信息</title>
<script>
	var jsBasePath = '<%=basePath%>';
	var shopId = '${shopId}';
</script>
</head>
<body style="background:#fafafa;">
<div class="main_box">
	<div class="personal_box">
        <ul>
            <li class="tx_photo"><span>头像：</span><em onclick="selectImg()"><img src="<%=static_resources %>images/shop_dt.png" /></em></li>
            <li><span>账号：</span><em id="userName"></em></li>
            <li><span>个性签名：</span><em id="introduce"></em></li>
            <li style="border:none;"><span>店铺名称：</span><em id="shopName"></em></li>           
       </ul>
   </div>
   <input type="file" id="fileInput" style="display: none" accept="image/*" onchange="updateAvatar(this);">    
</div>
<%@ include file="/commons/newAlertMessage.jsp" %>
</html>