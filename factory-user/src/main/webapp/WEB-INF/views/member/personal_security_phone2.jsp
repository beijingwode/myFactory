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
<script type="text/javascript" src="<%=basePath %>resources/js/personal_security_phone2.js"></script>
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
        	<p class="step02"></p>
            <div class="password_finish">
            	<div class="alter_cont">
                    <div class="alter_password">
                    	<input type="hidden" id="code" value="${code}"/>
                    	<input type="hidden" id="phone" value="${userName}"/>
                    	<span class="name">新密码：</span>
                        <input class="passwordtxt" type="password" id="password" maxlength="20" placeholder="4-20位字母、数字组合">
                    	<p id="passwordError" class="alter_error" style="display: none;">密码由4-20位字母、数字组合</p>
                    </div>
                    <div class="alter_password">
                    	<span class="name">确认密码：</span>
                        <input class="passwordtxt" type="password" id="rePassword">
                        <p id="rePasswordError" class="alter_error" style="display: none;">密码不一致！</p>
                    </div>
                    
                    <div class="btnprev"><a href="<%=basePath %>member/securityPassword">上一步</a></div>
                    <div class="btnnext"><a href="javascript:void(0);">下一步</a></div>
                </div>
            </div>
            <div class="Sreturnbtn"><a href="<%=basePath %>member/securityPassword?type=phone">返回</a></div>
        </div>
    </div>
<!--right contont end-->
<div class="clear:after"></div>
</div>
<div style="display: none">
<iframe id='security_iframe' name="security_iframe"></iframe>
<form method="POST" id="user_from" target="security_iframe">
	<div id="post_param"></div>
</form>
</div>
<div class="clear"></div>
<!--help begin-->
<%@ include file="../common/footer.jsp" %>
<!--footer end-->
  <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>
