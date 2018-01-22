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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/binding_phone_new.css" />
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
	
    <div class="main-box" style="top:0;">
    <input type="hidden" id="shareId" value="${shareId}"/>
    <input type="hidden" id="fromId" value="${fromId}" />
    <input type="hidden" id="companyType" value="${companyType}" />
    <input type="hidden" id="openId" value="${openId}" />
    <input type="hidden" id="afterDo" value="${afterDo}" />
    	<div class="main_con">
    		<div class="main_top_con">
    			<img src="<%=static_resources %>images/bangding_top_img.png" />
    			<p class="p1"><span>${comName}</span>员工</p>
            </div>
            <div class="input_box">
	            <div class="int_box">
	            	<span class="span1"></span>
	                <input type="text" placeholder="请输入姓名（使用真实姓名,享更多权益）" id="name" />
	            </div>
	            <div class="int_box">
	            	<span class="span2"></span>
	                <input type="text" placeholder="请输入您的手机号/企业邮箱" id="phoneEmail"   />
	            </div> 
            </div>        
        </div>
        
        <div class="btn_box" >绑  定</div>
        <p class="p3"><a href="javascript:toLogin()">已有账号，直接登录</a></p>
         
    </div>
     
</div>
<div class="protocol"><span></span><em>我已认真阅读并同意</em>&nbsp;<a href="<%=basePath%>/protocol.html">《我的网服务协议》</a></div>
<div class="theme-popover">
     <div class="theme-poptit">
     </div>
     <div class="theme-popbod">
        <a href="javascript:void(0);">确定</a>  
     </div>
     <div class="theme-popbod1">
        <a href="javascript:void(0);" class="btn_no">否</a><a href="javascript:toLoginYes();" class="btn_yes" >是</a>  
     </div>
	 <input type="hidden" id="rntId">
</div>
<div class="theme-popover-mask"></div>
<div class="recharge_money"  style="display: none">
	<p class="p1">恭喜您绑定成功</p>
	<p class="p2"><em>500元</em>内购券&<i style="font-style:normal;color:#c22936">免品券</i>已发至您的账号</p>
	<div class="new_theme-popbod">
        <a href="javascript:go2HDPage();">确定</a>  
     </div> 
</div>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/companyBind.js?1213"></script>
<script type="text/javascript">
function inputNumber(obj) {
	obj.value=obj.value.replace(/\D/g,'');
}

$(function(){
	var h=window.innerHeight-45;
	$("#main-cont").attr("style","height:"+h+"px");
})


$(function(){
	//手机号码激活
	$("#phoneEmail").focus(function(){
		$(this).prev().css({"background":"url(../static_resources/images/bindphone_icon12.png) no-repeat 2px","background-size":"20px 19px"});
	});
	$("#phoneEmail").blur(function() { 
		var str = $(this).val(); 
		str = $.trim(str); 
		if(str == ''){ 
			$(this).prev().css({"background":"url(../static_resources/images/bindphone_icon1.png) no-repeat 2px","background-size":"20px 19px"}); 
		};
	}); 
	
	//姓名激活
	$("#name").focus(function(){
		$(this).prev().css({"background":"url(../static_resources/images/bindphone_icon32.png) no-repeat 2px","background-size":"20px 19px"});
	});
	$("#name").blur(function() { 
		var str = $(this).val(); 
		str = $.trim(str); 
		if(str == ''){ 
			$(this).prev().css({"background":"url(../static_resources/images/bindphone_icon3.png) no-repeat 2px","background-size":"20px 19px"}); 
		};
	}); 
	
	
})
</script>
</body>
</html>
