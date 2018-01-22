<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="/css/loginpopup.css">
<script type="text/javascript" src="/resources/js/placeholderfriend.js"></script>
<!--登录弹出框 begin-->
<div class="popup_bg"></div>
<div class="login_popup" >
	<div class="L_popupbox">    	
    	<div class="L_title">
        	<p>我的福利</p>
            <div class="close_btn"><img src="/images/close.gif" width="14" height="14" alt="close"></div>
        </div>
        <div class="L_cont">
        	<div id="pError"><span id="error"></span></div>
        	<input class="L_public_input L_txt" type="text" id="userName"  placeholder="用户名/邮箱/手机号">
            <input class="L_public_input P_txt" type="password" id="password" placeholder="登录密码" maxlength="20">
            <div class="L_lrgst">
            	<span class="ylt"><a href="/user/forgetPassword" target="_blank">忘记密码？</a></span>
                <span class="yrt"><a href="/register.html" target="_blank">免费注册</a></span>
            </div>
            <input class="L_popbtn" type="button" onclick="wode.doLogin();" value="立即登录">
        </div>
    </div>
</div>
<!--登录弹出框 end-->

<script>
/**
 * 监听输入框的回车操作
 */  
$(".login_popup").keydown(function(event){ 
if(event.keyCode == 13){
	wode.doLogin();
} 
});
</script>