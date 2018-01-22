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
<title>我的福利-修改密码</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
   
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal_security_password.js"></script>
<body>
<!--top begin-->
<%@ include file="../common/header_03.jsp"%>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
<!--left nav-->
    <%@ include file="menu.jsp"%>
<!--left nav end-->
<!--right content-->
	<div class="Me_content">
    	<div class="on_position">
        	<span class="tl">安全设置</span><em>></em><span>更换密码</span>
        </div>
        <div class="security_cont">
        	<p class="step01"></p>
            <div class="password_finish">
            	<c:if test="${not empty user.phone}">
	            	<div class="yztype yzphone">
	                	<div class="yzlt">
	                    	<h3>通过验证手机</h3>
	                        <p>修改密码的短信验证码将发送到您验证的手机</p>
	                    </div>
	                    <div class="yzbtn"><a href="<%=basePath %>member/securityPassword?type=phone">前往修改</a></div>
	                </div>
                </c:if>
                <c:if test="${not empty user.email}">
	                <div class="yztype">
	                	<div class="yzlt">
	                    	<h3>通过验证邮箱</h3>
	                        <p>修改密码的安全链接将发送至您的验证邮箱上</p>
	                    </div>
	                    <div class="yzbtn" id="sendEmail"><a href="javascript:void(0);">前往修改</a></div>
	                    <div class="loading_sendEmail_password"><img src="../images/loading_small.gif" alt="login_img"></div>
	                </div>
                </c:if>
            </div>
            <div class="Sreturnbtn"><a href="<%=basePath %>member/security">返回</a></div>
        </div>
    </div>
<!--right contont end-->
<div class="clear:after"></div>
</div>

<div class="clear"></div>
<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
<!--help begin-->
<%@ include file="../common/footer.jsp" %>
<!--footer end-->
 <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>
