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
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的网-找回密码手机验证</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/member.css">
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/find_password.js"></script>
<body onload="changeVCode();">
<!--top begin-->
<%@ include file="common/header_05.jsp"%>
<!--top end-->

<!--content begin-->
<div class="L_content">
	<div class="register_cont_wrap">
    	<div class="process_password st2">
        	<div class="password_box">
                <div class="alter_cont">
                	 <div class="alter_num">
                        <span class="name">输入校验码：</span>
                        <input class="user_input" type="text" id="vcode" maxlength="4" style="width:98px;float:left;margin-right:5px;">
		                <div class="login_yzm" id="" ><img src="/images/img_yzm.png" width="85px" height="40px" /></div>
		                <div class="login_yzm_chg" id="" ><a href="javascript:changeVCode();" style="color:#2b8dff;">看不清？<br />换一张！</a></div>		              
                    	<span class="yzm_error">校验码错误！</span>
                    </div>
                    <input type="hidden" id="phone" value="${allPhone}">
                    <input type="hidden" id="userId" value="${uid}">
                    <div class="alter_num">
                        <span class="name">当前绑定的手机号：</span>
                        <strong class="red">${phone}</strong>
                        <button class="getyzm">获取验证码</button>
                    	<span class="sendSmsBefore">验证码已发送到绑定手机，若未收到，请稍候重新点击获取验证码</span>
                    </div>
                    <div class="alter_num">
                        <span class="name">短信验证码：</span>
                        <input class="yzminput" type="text" id="code" maxlength="6">
                    	<span class="error"></span>
                    </div>
                    <div class="btnnext"><a id="next" href="javascript:void(0);">下一步</a></div>
                    <div class="btnprev"><a href="/user/forgetPassword">上一步</a></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="common/footer.jsp" %>
<!--footer end-->
  <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script> <script>
    $(function(){
    	wode.publiced=1;
    });
</script>
</body>
</html>
