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
<title>我的福利-修改绑定手机</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal_change_bind_phone.js"></script>
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
        	<span class="tl">安全设置</span><em>></em><span>验证手机号码</span>
        </div>
        <div class="security_cont">
        	<p class="progress-2"></p>
            <div class="password_finish">
            	<span class="sendSmsBefore" id="before" style="padding-left: 30px;"></span>                 
            	<div class="alter_cont">
                    <div class="alter_num">
                        <span class="name">输入验证码：</span>
                        <input class="user_input" type="text" id="vcode" maxlength="4" style="width:98px;float:left;margin-right:5px;">
		                <div class="login_yzm" id="" ><img src="/images/img_yzm.png" width="85px" height="40px" /></div>
		                <div class="login_yzm_chg" id="" ><a href="javascript:changeVCode();" style="color:#2b8dff;">看不清？<br />换一张！</a></div>		              
                    	<span class="yzm_error">验证码错误！</span>
                    </div>
                    <div class="alter_num">
                    	<input type="hidden" id="uid" value="${uid}">
                    	<input type="hidden" id="phone" value="${phone}">
                    	<span class="name">短信验证码：</span>
                        <input class="getyzminput" type="text" id="code" maxlength="6">
                        <button class="getyzm" id="changePhone">获取验证码</button>
	                    <span class="sendSmsResult" id="sendResult" style="display: none;"></span>
	                    <img id="loading" src="<%=basePath %>images/loading_small.gif"/>
                    </div>
                    <div class="btnprev"><a href="<%=basePath %>member/security">上一步</a></div>
                    <div class="btnnext"><a id="submitResult" href="javascript:void(0);">下一步</a></div>
                    
                </div>
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
