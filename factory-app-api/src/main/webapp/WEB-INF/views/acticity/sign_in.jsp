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
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/alert.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/sign_in/sign_in.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<title>签到</title>
<style type="text/css">
.theme-popbod1 a{height:40px;width:50%;font-size:2.0em;color:#007aff;display:block;text-align:center;line-height:40px;float:left;}
.theme-popbod1 .btn_no{width:49%;border-right:1px solid #e0dfdf}
.theme-popbod1 .btn_yes{width:49%;border-right:1px solid #e0dfdf}
</style>
</head>
<script type="text/javascript">
var jsBasePath = '<%=basePath%>';
var enterpriseId = '${enterpriseId}';
var activityId = '${activityId}';
var userId = '${userId}';
var openId = '${openId}';
</script>
<body>
<%@ include file="/commons/alertMessage.jsp" %> 
<div class="main-cont" id="main-cont">   
    <div class="sign_in">
    	<div class="sign_in_banner"><img src="${wxBanner}" /></div>
    	<ul>
    		<li><input type="text" value="${name}" id="name" placeholder="请输入姓名" /></li>
    		<li><input type="text" value="${phone}" id="phone" placeholder="请输入手机号"  name="phone" maxlength=11 /></li>
    	</ul>
    	<div class="sign_in_btn"><a href="javascript:check();"><img src="<%=static_resources %>images/sign_in/sign_in_btn.png" /></a></div>
    	<p>由我的福利提供技术支持</p>
    </div>    
</div>
<div class="mask"></div>
<div class="hint_show">
	<p class="p1">稍后将根据您填写的信息抽奖</p>
	<p class="p2">请确保真实哦~</p>
	<div class="btns"><a href="javascript:hide();" class="btn1">改一下</a><a href="javascript:sign();" class="btn2">填好了</a></div>
</div>
<div class="theme-popover" id="msgShowDiv" style="display: none">
	 <div class="theme-poptit"></div>
     <div class="theme-popbod1" >
       <a href="javascript:close();" class="btn_no">否</a><a href="javascript:toLoginYes();" class="btn_yes" >是</a>  
     </div>
</div>
<script>
$(function(){
	/*  $(".sign_in_btn").click(function(){
		$(".mask").show();
		$(".hint_show").show();
	})   */
	/* $(".btns .btn1,.btns .btn2").click(function(){
		$(".mask").hide();
		$(".hint_show").hide();
	}) */
})

function hide(){
	$(".mask").hide();
	$(".hint_show").hide();
}

function check(){
	var name = $("#name").val();
	var phone = $("#phone").val();
	if(name == null || name == '') { 
	   showInfoBox("请输入姓名");
       return false; 
	} 
	if(phone.length==0) { 
	   showInfoBox("请输入手机号码");
       return false; 
    }     
    if(phone.length!=11) 
    { 
       showInfoBox("请输入有效的手机号码");
       return false; 
    }
    
	var myreg=/^1([3456789]\d|21)\d{8}$/i;
    if(!myreg.test(phone)) 
    { 
       showInfoBox('请输入有效的手机号码'); 
       return false; 
    } 
    $(".mask").show();
	$(".hint_show").show();
}

function sign(){
	var name = $("#name").val();
	var phone = $("#phone").val();
 	//验证一切数据
	 $.ajax({
		url : jsBasePath+'acticity/addSignMsg',
		type : "GET",
		data: {"name":name,"phone":phone,"enterpriseId":enterpriseId,"activityId":activityId,"userId":userId,"openId":openId},
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
	    	if(data.success) {
				window.location=jsBasePath+'acticity/toSignSuccessPage?recordId='+data.data.recordId+"&ticket="+data.data.ticket;
	    	} else {
	    		showInfoBox(data.msg);
	    		if(data.msg == "该手机号已通过其他方式注册，请先登录后再签到"){  
	    			$(".theme-poptit").html('该手机已绑定过，是否使用验证码登录？');
		    		$(".theme-popbod1").show();
		    		$(".theme-popover").show();
	    			$(".hint_show").hide();
	    	 	 }
	    	}
		},error : function() {}
	}) 
}

//点击"是"时跳转到手机验证码登录页
function toLoginYes() {
	sessionStorage.setItem("phone", $.trim($("#phone").val()));
	window.location=jsBasePath+"user/toPhoneLogin?exp1="+openId+"&toUrl=&type=W&msg=";
}

function close(){
	$(".theme-popbod1").hide();
	$(".theme-popover").hide();
	$(".mask").hide();
	$(".hint_show").hide();
}
</script>
</body>

</html>
