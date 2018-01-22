<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
if(request.getServerPort() != 80 && request.getServerPort() != 443) {
	path=":"+request.getServerPort()+path;
}
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" >
<meta content="telephone=no" name="format-detection" />
<title>企业员工 邮箱注册</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/binding_phone.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
</head>

<body>
<div class="main-cont" id="main-cont">
	<div class="top">   	
        <h1 style="width:100%;text-align:center;">企业员工 邮箱注册</h1>
    </div>
    <div class="main-box">
    	<input type="hidden" id="openId" value="${openId}" />
    	<div class="main_con">
            <div class="int_box" >            	
                <input type="text"  placeholder="请输入企业邮箱"  class="email_text"  maxlength="50" style="width:55%" />  
                <div id="divselect">
                      <select id="emailPostFix" >
						 <option value="">请选择邮箱后缀</option>
						 <option value="@wo-de.com">@wo-de.com</option>
					  </select>
                </div>               
            </div>
            
            <div class="int_box">            	
                <input type="password" placeholder="请输入密码(由4-20位字母、数字组成)"  value="" class="pass_text1"  maxlength="20" style="width:95%"  />
            </div>
            <div class="int_box">           	
                <input type="password" placeholder="请确认密码" value="" class="pass_text2" maxlength="20" style="width:95%" />
            </div>
            
        </div>
        <p><input type="checkbox" checked="checked" disabled="disabled" /> <em>我已认真阅读并同意我的网</em>&nbsp;<a href="/protocol.html">《服务协议》</a></p> 
        <div class="btn_box" >立即注册</div>
        <div class="href_login">已有账号，<a href="javascript:;">登录</a></div>
        
    </div>
</div>
<div style="display: none">
<iframe id='login_iframe' name="login_iframe" src=""></iframe>
<form method="POST" id="user_from" target="login_iframe">
	<div id="post_param"></div>
</form>
</div>
<script type="text/javascript">

function doRegister(email,passWd){
	var result = true;
	
	if(result){
		$(".btn_box").unbind();
		
		var user_from = document.getElementById("user_from");
		$("#post_param").html('<input type="hidden" name="userName" value="'+email +'">'
				+ '<input type="hidden" name="userEmail" value="'+email +'">'
				+ '<input type="hidden" name="nickName" value="'+email +'">'
				+ '<input type="hidden" name="password" value="'+passWd +'">'
				+ '<input type="hidden" name="enabled" value="0">'
				+ '<input type="hidden" name="usable" value="1">'
				+ '<input type="hidden" name="userType" value="1">'
				+ '<input type="hidden" name="userCom" value="myFactory">'
				+ '<input type="hidden" name="returnUrl" value="<%=basePath %>user/writeUserResult?type=regist2">');
		user_from.action = comm_user_domain+'regist';
		user_from.submit();
	}
}

function showRegisterResult2(data){
	if(data.success){
		$("#login_iframe").attr("src","http://www.wd-w.com/personal/sendRegisterEmail?type=register&userId="+data.data.userId);
    	showInfoBox("注册成功！","tologin('mailRegisterSuccess');");
	}else{
    	showInfoBox(data.msg);
    	$(".btn_box").click(function(){
    		checkAndRegist();		
    	});	
	}
}

function tologin(msg) {
	window.location = '<%=basePath %>user/toLogin?msg='+msg+'&exp1='+$("#openId").val() +'&toUrl='+encodeURI("http://mp.weixin.qq.com/s/AZu8nPhL703JJJJLBe8BTw")+'&type=W';
}

$(document).ready(function() {
	initEmailPostfix();
	
	$(".btn_box").click(function(){
		checkAndRegist();		
	});	
	
	$(".href_login").click(function(){
		tologin('');
	});

	var openId=$("#openId").val();
	if(openId!="" && openId!="null" ) {
		sessionStorage.setItem("openId", openId);
	}
});

function checkAndRegist() {
	var re_email=/^(\w|\-|\.){1,}$/i;	
	
	var re_password=/^[a-z0-9A-Z]{4,16}$/i;
	
	var email_text=$.trim($(".email_text").val());
	var select_val=$("#emailPostFix").val();
	
	var pass_text1=$.trim($(".pass_text1").val());
	var pass_text2=$.trim($(".pass_text2").val());
	
	if(!re_email.test(email_text)||email_text==''){
		showInfoBox('请输入合法的邮箱地址');
	  return false;
    }
	if(select_val==''){
		showInfoBox('请填选择邮箱后缀');
		  return false;
	}
	
	if(!re_password.test(pass_text1)||pass_text1==''){
		showInfoBox('请输入正确的密码');
	  	return false;
    }
	if(pass_text2==''){
    	showInfoBox('请确认密码');
	  	return false;
    }else if(pass_text2!=pass_text1 && pass_text2!=""){
		showInfoBox('两次密码不一致');
	  	return false;
    } 
    
    doRegister(email_text+$("#emailPostFix").val(),pass_text1);
}

function initEmailPostfix(){
	$.ajax({
		dataType: 'json',
		url:"<%=basePath %>user/emailPostfixs",
		success: function(result){
		    if(result.success){
		    	var ls = result.data;
		    	if(ls.length>0) {
		    		var html='<option value="">请选择邮箱后缀</option>';
		    		for(var i=0;i<ls.length;i++) {
		    			html +='<option value="@'+ ls[i] +'">@'+ ls[i] +'</option>';
		    		}
		    		$("#emailPostFix").html(html);
		    	}
		    
		    }
		},
		error:function(){
		}
	});
}
</script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
