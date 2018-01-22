<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的网-邮件结果提示页</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  
<script type="text/javascript" src="<%=basePath %>resources/js/register_email_success.js"></script>
<body>
<!--top begin-->
<%@ include file="common/header_03.jsp"%>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
<!--right content-->
	<div class="Me_content">
        <input type="hidden" id="type" value="${type}">
        <c:if test="${type=='nullUser'}">
	        <div class="security_cont">
	            <div class="password_finish">
	            	<p class="seccess">用户未登录！</p>
	            	<p class="return_info">3秒后跳转到<a href="/login.html?from='/index.html'">&nbsp;&nbsp;登录</a></p>
	            </div>
	        </div>
        </c:if>
        <c:if test="${type=='abate'}">
	        <div class="security_cont">
	            <div class="password_finish">
	            	<p class="seccess">该链接已失效！</p>
	            	<p class="return_info">3秒后跳转到<a href="/index.html">&nbsp;&nbsp;首页</a></p>
	            </div>
	        </div>
        </c:if>
    </div>
<!--right contont end-->
<div class="clear:after"></div>
</div>

<div class="clear"></div>
<!--help begin-->
<%@ include file="common/footer.jsp" %>
<!--footer end-->
  <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>
