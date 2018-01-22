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
<title>我的网-绑定邮箱</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
 
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal_security_sendemail_success.js"></script>
<body>
<!--top begin-->
<%@ include file="common/header_03.jsp"%>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
<!--left nav-->
	<%@ include file="member/menu.jsp"%>
<!--left nav end-->
<!--right content-->
	<div class="Me_content">
    	<div class="on_position">
        	<span class="tl">安全设置</span><em>></em><span>绑定/更换邮箱</span>
        </div>
        <div class="security_cont">
        	<p class="progress-email-2"></p>
            <div class="password_finish">
            	<div class="alter_cont">
                    <div class="alter_num">
                    	<span class="emailname">新邮箱：</span>
                        <input class="yzminput" type="text" id="email">
                        <input type="hidden" id="type" value="${type}">
                        <input type="hidden" id="userId" value="${user.id}">
                        <input type="hidden" id="email" value="${user.email}">
                        <em>
                        	<a id="sendEmailForBind" href="javaScript:void(0);">发送验证邮件</a>
                        </em>
                        <img class="loading_sendEmail" style="display: none;" id="loading" src="<%=basePath %>images/loading_small.gif"/>
                    	<p class="sendEmailResult" style="display: none;"></p>
                    </div>
                    <div class="btnprev" style="margin-left:120px;"><a href="<%=basePath %>member/security">上一步</a></div>
                    <div class="goemailbtn"><a href="#">前往邮箱</a></div>
                    
                    <p class="resendemail">未收到，<a id="sendEmailAgainForBind" href="javascript:void(0);">重发</a>？</p>
                </div>
            </div>
            <div class="Sreturnbtn"><a href="<%=basePath %>member/security">返回</a></div>
        </div>
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
