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
<title>我的网-激活邮件发送成功</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
    
<script type="text/javascript" src="<%=basePath %>resources/js/register_email_success.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal.js"></script>
<body>
<!--top begin-->
<%@ include file="common/header_03.jsp"%>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
	<div class="Me_content">
    	<div class="on_position">
        	<span class="tl">注册</span><em>></em><span>帐号激活</span>
        </div>
        <div class="security_cont">
        	<p class="step02"></p>
            <div class="password_finish">
            	<div class="alter_cont">
            		<input type="hidden" id="userId" value="${userId}">
                    <p class="Stishi">帐号激活邮件已发送，请前往您的注册邮箱查阅我们的邮件</p>
                    <p class="Semail">未收到邮件，
                    	<a href="javascript:void(0);" id="sendEmailAgainForRegister">重新发送</a>？
                    	<span class="sendEmailResult" style="display: none;margin-left:10px;"></span>
                    </p>
                    	<div>
							<img class="loading_sendEmail_s" style="display: none;" id="loading" src="http://www.wd-w.com:80/images/loading_small.gif"/>
						</div>
                </div>
            </div>
            <div class="Sreturnbtn"><a href="<%=basePath %>member/securityPassword">返回</a></div>
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