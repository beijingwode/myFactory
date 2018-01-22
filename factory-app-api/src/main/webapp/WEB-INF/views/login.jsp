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
<title>登录</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/cou_style.css" />

<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

</head>

<body>
<div class="main-cont" id="main-cont">
	<div class="top top1">
  		<c:if test="${type == 'W'}">
        <h1 style="width:100%;text-align:center;">我的福利 账号绑定</h1>
      	</c:if>
  		<c:if test="${type != 'W'}">
        <h1 style="width:100%;text-align:center;">登录</h1>
      	</c:if>
        <input type="hidden" id="msg" value="${msg}">
        <input type="hidden" id="forId" value="${forId}">
        <input type="hidden" id="exp1" value="${exp1}">
        <input type="hidden" id="toUrl" value="${toUrl}">
        <input type="hidden" id="type" value="${type}">
    </div>
    <div class="main-box">
    	<div class="main_con">
            <div class="int_box">
                <input id="userName" type="text" placeholder="首次登录账号为注册时手机号"  />
            </div>            
            <div class="int_box int_box3">
                <input id="password" type="password" placeholder="首次登录密码为注册时手机号" />
            </div>
        </div>
        <p style="font-size:1.2em;text-align: left;color: #a5a5a7;padding:10px 0 0 10px"><em style="color:#ff4040;">*</em>商家账号可用邮箱登录</p>
        <div class="btn_box" onclick="c_login()">确定</div>
        <div class="btn_btm">
        	<ul style="width:280px;font-size: 1.4em">
            	<li><a href="javascript:toPhoneLogin()">验证码登录</a></li>
  				<c:if test="${type != 'W'}">
                <li>|</li>
                <li><a href="<%=basePath%>userShare/toBind${forId}?type=${type}"">成为好友</a></li>
      			</c:if>
      			<c:if test="${type == 'W'}">
                <li>|</li>
                <li id="qr"><a href="javascript:void(0)">亲友二维码</a></li>
      			</c:if>     			
                <li>|</li>
                <li id="zc"><a href="javascript:void(0)">快速注册</a></li>
      			
            </ul>
        </div>
    </div>
</div>
<div style="display: none">
<iframe id='login_iframe' name="login_iframe"></iframe>
<form method="POST" id="user_from" target="login_iframe">
	<div id="post_param"></div>
</form>
</div>

<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript">

function c_login(){
	var result = true;
	
	if ($("#userName").val().length < 1) {
		showInfoBox("账号不能为空");
		result = false;
	}
	if ($("#password").val().length < 1) {
		showInfoBox("密码不能为空");
		result = false;
	}
	
	if(result){
		var user_from = document.getElementById("user_from");
		$("#post_param").html('<input type="hidden" name="userName" value="'+$("#userName").val() +'">'
				+ '<input type="hidden" name="password" value="'+$("#password").val() +'">'
				+ '<input type="hidden" name="code" value="'+$("#vcode").val() +'">'
				+ '<input type="hidden" name="userCom" value="myFactory">'
				+ '<input type="hidden" name="loginType" value="factory_web_login">'
				+ '<input type="hidden" name="returnUrl" value="<%=basePath %>user/writeUserResult?type=login">');
		user_from.action = comm_user_domain+'login';
		user_from.submit();
	}
}

function showLoginResult(json,showVcode){
	if(json.success){
    	sessionStorage.setItem("login", "success");
		var type = '${type}';		
		if(type == 'W') {
			var toUrl="${toUrl}";
			var loginNextUrl =sessionStorage.getItem("loginNextUrl");
			if(typeof(loginNextUrl)!="undefined" &&loginNextUrl!=null){
				sessionStorage.removeItem("loginNextUrl");
				toUrl=encodeURI(loginNextUrl.replace(/&/g,"____").replace(/=/g,"****"));
			}
			window.location = '<%=basePath %>wx/bindAccount.user?ticket='+json.data+'&exp1=${exp1}&toUrl='+toUrl+'&type=W';			
		} else {
			window.location = "<%=basePath %>blackEnvelope/openPage"+$("#forId").val() +'.user?ticket='+json.data;
		}
		
	}else{
		if(json.msg.indexOf("验证码错误")==0) {
			showInfoBox("失败次数过多,请使用验证码登录","toPhoneLogin()");
			
		} else {
			showInfoBox(json.msg);
		}
	}
}

function toPhoneLogin() {
	window.location = '<%=basePath %>user/toPhoneLogin?forId='+$("#forId").val() +'&msg='+$("#msg").val() +'&exp1='+$("#exp1").val() +'&toUrl='+$("#toUrl").val() +'&type='+$("#type").val();
}

function scanQr() {
	wx.scanQRCode({
	    needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
	    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
	    success: function (res) {
	    	var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果	
		}
	
	});
}

$(document).ready(function() {
	var type = '${type}';
	if(type != 'W') {
		var uid = GetUidCookie();
		if(uid!="") {
			window.location = "<%=basePath %>blackEnvelope/openPage"+$("#forId").val()+".user?uid="+uid;
			return;
		}
	}
	
	$("#zc").click(function(){
		if(sessionStorage.openId) {
			window.location = "/user/register?openId="+sessionStorage.openId;
		} else {
			window.location ="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_open_appId+"&redirect_uri="+system_domain+"wx/getOpenId&response_type=code&scope=snsapi_base&state=register#wechat_redirect";			
		}
	});
	
	$.ajax({
		url : '<%=basePath %>wx/wxConfig?url='+encodeURI(window.location.href.replace(/&/g,"____").replace(/=/g,"****")),
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: data.data.appId, // 必填，公众号的唯一标识
				    timestamp: data.data.timestamp, // 必填，生成签名的时间戳
				    nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
				    signature: data.data.signature,// 必填，签名，见附录1
				    jsApiList: ["scanQRCode"] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				
				wx.ready(function(){					
					$("#qr a").attr("href","javascript:scanQr()");
				});
				
				wx.error(function(res){
					//alert(JSON.stringify(res));
				});
			} else {
			}
		},
		error : function() {
			alert("err");
		}
	});
	
	if("mailRegisterSuccess" == $("#msg").val()) {
		$("#msg").val("");
		showInfoBox("请查看激活邮件。激活后再次登录","");
	}
	
	var login =sessionStorage.getItem("login");
	if(typeof(login)!=undefined &&login!=null){
		if(login == "success"){
			sessionStorage.removeItem("login");
			if(sessionStorage.loginPreUrl){
				window.location = sessionStorage.loginPreUrl;	
			} else {
				document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
				    // 通过下面这个API隐藏右上角按钮
				    WeixinJSBridge.call('closeWindow');
				});
			}
		}
	}
});

</script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
