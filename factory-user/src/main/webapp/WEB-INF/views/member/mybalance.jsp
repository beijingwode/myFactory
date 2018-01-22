<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-个人中心</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
</head>
<body>

<!--top begin-->
<%@ include file="../common/header_03.jsp" %>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
<!--left nav-->
<%@ include file="menu.jsp" %>
<!--left nav end-->
<!--right content-->
<div class="Me_content">
	<div class="valible_sum">可用余额：<span>
	<c:if test="${!empty account }">
		<fmt:formatNumber value="${account.balance }" type="currency" pattern="￥.00"/>
	</c:if>
	<c:if test="${empty account }">
		￥0.00
	</c:if>
	</span><strong>您可用使用账户余额在我的网购买商品时，进行支付操作</strong></div>
</div>
<!--right contont end-->
<div class="clear:after"></div>
</div>

<div class="clear"></div>
<%@ include file="../common/footer.jsp" %>
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>