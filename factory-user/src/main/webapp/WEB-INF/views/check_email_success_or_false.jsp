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
<title>我的网-邮箱验证结果</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
   
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/register_email_success.js"></script>

<script>
    $(function(){
    	wode.publiced=1;
    })
</script>
<body>
<!--top begin-->
<%@ include file="common/header_03.jsp"%>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
<!--right content-->
	<div class="Me_content">
    	<div class="on_position">
        	<span class="tl">注册认证</span><em>></em><span>邮箱激活</span>
        </div>
        <input type="hidden" id="result" value="${result}">
        <input type="hidden" id="userId" value="${userId}">
        <c:if test="${result=='1'}">
	        <div class="security_cont">
	        	<p class="step03"></p>
	            <div class="password_finish">
	            	<p class="false">邮箱认证通过，账户激活成功！</p>
	            	<p class="return_info">3秒后跳转到<a href="../index.html">&nbsp;&nbsp;首页</a></p>
	            </div>
	        </div>
        </c:if>
        <c:if test="${result=='0'}">
	        <div class="security_cont">
	            <div class="password_finish" id="sendEmailAgainForRegister">
	            	<p class="false">链接已失效，账户激活失败，请<a href="javascript:;">&nbsp;点击&nbsp;</a>重新发送激活邮件！</p>
	            </div>
	            <span class="sendEmailResult" style="display: none;"></span>
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
