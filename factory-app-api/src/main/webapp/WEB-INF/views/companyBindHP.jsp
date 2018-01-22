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
<title>绑定手机 享员工福利</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/binding_phone.css" />
<script type="text/javascript">
var jsBasePath = "<%=basePath %>";
</script>
<style type="text/css">
p {
  font-size: 1.4em;
}
</style>
</head>

<body>
<div class="main-cont" id="main-cont">
	<div class="top">   	
        <h1 style="width:100%;text-align:center;">绑定手机 确认员工身份</h1>
    </div>
    <div class="main-box">
    <input type="hidden" id="shareId" value="${shareId}"/>
    <input type="hidden" id="fromId" value="${fromId}" />
    <input type="hidden" id="companyType" value="${companyType}" />
    <input type="hidden" id="openId" value="${openId}" />
    	<div class="main_con">
    		<p>亲爱滴小主：<br />
    		请您绑定为
	        <span>${comName}</span>
			的员工，成为企业专享用户，800+大牌福利随便挑。</p>
            <div class="int_box">
                <input type="text" placeholder="请输入8位惠普员工号（必须）" id="empNumber" />
            </div>
            <div class="int_box">
                <input type="number" placeholder="请输入手机号（必须）" id="phone" onkeyup="inputNumber(this)" oninput="if(value.length>11)value=value.slice(0,11)" />
            </div>
            <div class="int_box">
                <input type="number" placeholder="请输入短信验证码（必须）" id="code" onkeyup="inputNumber(this)"/><a class="yzm_btn" id="yzm_btn" onclick="sendCode(this)" >获取验证码</a>
            </div>
            <div class="int_box">
                <input type="text" placeholder="请输入姓名（必须）" id="name" />
            </div>
        </div>
        <p><input type="checkbox" checked="checked" disabled="disabled" /> <em>我已认真阅读并同意我的网</em>&nbsp;<a href="<%=basePath%>/protocol.html">《服务协议》</a></p> 
        <div class="btn_box" style="font-size: 1.4em">立即绑定</div>
        <p style="font-size:1.4em;text-align: center;color: #73b4f9;margin-top: 15px;"><a href="javascript:toLogin()">已有账号，登录</a></p>
    </div>
     
</div>
<div class="theme-popover">
     <div class="theme-poptit">
          填写手机验证码错误
     </div>
     <div class="theme-popbod">
        <a href="javascript:void(0);">确定</a>  
     </div>
	 <input type="hidden" id="rntId">
</div>
<div class="theme-popover-mask"></div>
<div class="recharge_money"  style="display: none">
	<p style="vertical-align: middle;text-align:center;"><img alt="<%=static_resources %>images/bind_success.png" src="<%=static_resources %>images/bind_success.png" width="auto" height="auto" style="vertical-align: middle;" ></p>
	<div class="new_theme-popbod">
        <a href="javascript:go2HDPage();">确定</a>  
     </div> 
</div>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/companyBindHP.js?1213"></script>
<script type="text/javascript">
function inputNumber(obj) {
	obj.value=obj.value.replace(/\D/g,'');
}
</script>
</body>
</html>
