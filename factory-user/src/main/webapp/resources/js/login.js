var phone = /^(1[3|4|5|7|8][0-9])\d{8}$/;//手机号码验证
//var register = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/;//密码验证
var timeout = 6000;//错误信息展示时间（6秒）
var wode={"hasLogin":0};//只保留一个全局变量，避免变量冲突.
wode.domain="http://www.wd-w.com";
wode.userDomain = "https://passportd.wd-w.com";//邮箱验证
wode.comeFrom = "myFactory";//邮箱验证
//校验邮箱
function checkEmail(email){
	email=$.trim(email);
	if(email.length==0) return false;
	if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
	return pattern.test(email);
}

$(document).ready(function() {
	var url=window.location.href;
	if(url.indexOf(wode.domain) != 0) {
		var indexP = url.indexOf(":");
		var start= url.substring(0,indexP+3);
		url = url.substring(indexP+3);

		indexP = url.indexOf(":");
		if(indexP != 0) {
			var domainS = url.substring(0,indexP);
			url = url.substring(indexP);
			indexP = url.indexOf("/");
			domainS=domainS+url.substring(0,indexP);

			wode.domain=start+domainS;
		}
	}
	
	var cookie = document.cookie;
    var array = cookie.split(';');
    if(array.length>0){
		for (var item in array){
			if (array[item].indexOf('Factory_userName')>-1){
				var arrayItem = array[item].split('=');
				$("#userName").val(arrayItem[1]);
		    }
		}
    }
    
	//切换登录方式
 	$(".login_mode ul li").each(function(index){
		$(this).click(function(){//先把所有的隐藏掉
 			$(".login_box").addClass("dis_none");
 			$(".login_box:eq("+index+")").removeClass("dis_none");//在把对应的索引的显示
			if(index==1) {changeQr();}
			else {loopStop=true;}
 			$(".login_mode ul li").removeClass("login_none");
 			$(this).addClass("login_none");
 		});
 	});
	
 	//鼠标滑过二维码向左移动帮助图片显示
 	$(".qrcode-img").mouseover(function(){		
 		$(".qrcode-img").stop().animate({left:"12px"},600,function(){$(".qrcode-help").show();});		
 	});
 	 	
 	$(".qrcode-main").mouseout(function(){
 		$(".qrcode-img").stop().animate({left:"80px"},600);
 		$(".qrcode-help").hide();
 	});
});
/**
 * 验证登录
 */
function check(){
	var member=0;
	if($("#member").prop("checked")){
		member=1;
	}
	if(typeof(user_ticket) != "undefined" && user_ticket!=null ){
		$.postJSON("user/hasLogin",{"ticket":user_ticket,"remenber":member},function(data){
			if(data.success){
				var from=getQueryString("from");
				if(from==null){
					from=document.referrer;
				}
				
				if($("#member").attr("checked")=="checked"){
					 document.cookie="Factory_userName="+$("#userName").val()+";";
				}
				
				if(from==null || from.indexOf("/register")>0){
					window.location="/index.html";
				}else{
					var url=decode64(from);
					if(url==null||url==""){
						url="/index.html"
					}
					window.location=url;
				}
				$(".login_btn").css("background","#ff6161");
	    		$(".login_btn a").attr("onclick","c_login();");
			}else{
				$("#passwordError").html(data.msg);
				$("#passwordError").fadeIn("slow");
				setTimeout("display()",timeout);
			}
		});
	}
}



	/**
	 * 监听输入框的回车操作
	 */  
$(document).keydown(function(event){ 
	if(event.keyCode == 13){
		c_login();
	} 
});
/**
 * 登录
 */
function c_login(){
	var result = true;
//	if(!phone.test($("#userName").val())){
//		if(!checkEmail($("#userName").val())){
//			if($("#userName").val().length<1)
//				$("#userNameError").html("邮箱/手机号码不能为空");
//			else
//				$("#userNameError").html("邮箱/手机号码填写有误");
//			$("#userNameError").fadeIn("slow");
//			setTimeout("display()",timeout);
//			result = false;
//		}
//	}
	
	if ($("#password").val().length < 1) {
		$("#passwordError").html("密码不能为空");
		$("#passwordError").fadeIn("slow");
		setTimeout("display()", timeout);
		result = false;
	}
	
	if(result){
		$(".login_btn").css("background","#959595");
		$(".login_btn a").removeAttr("onclick");
		$(".loading_img").show();
		var user_from = document.getElementById("user_from");
		$("#post_param").html('<input type="hidden" name="userName" value="'+$("#userName").val() +'">'
				+ '<input type="hidden" name="password" value="'+$("#password").val() +'">'
				+ '<input type="hidden" name="code" value="'+$("#vcode").val() +'">'
				+ '<input type="hidden" name="userCom" value="'+wode.comeFrom +'">'
				+ '<input type="hidden" name="loginType" value="factory_web_login">'
				+ '<input type="hidden" name="returnUrl" value="'+wode.domain+'/user/writeUserResult?type=login">');
		user_from.action = wode.userDomain+'/login';
		user_from.submit();
	}
}
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
function display(){
	$(".login_error").fadeOut("slow");
}
function modifyPassword(){
	$(".login_error").fadeOut("slow");
	location.href = "user/forgetPassword";
}


function showLoginResult(json,showVcode){
	var member=0;
	if($("#member").attr("checked")=="checked"){
		member=1;
	}
	
	if(json.success){
		user_ticket = json.data;
		if($("#password").val()==$("#userName").val()){
			$(".alert_box").show();
			$(".alert_bg").show();
			$(".alert_box .sure_btn").click(function(){
				modifyPassword();
			});
			$(".alert_box .no_btn").click(function(){
				$(".alert_box").hide();
				$(".alert_bg").hide();
				$("#login_iframe").attr("src",wode.userDomain + "/writeTicket/"+user_ticket+"?callbackUrl="+wode.domain+"/login_callback.html");
			});
			
		}else{
			$("#login_iframe").attr("src",wode.userDomain + "/writeTicket/"+user_ticket+"?callbackUrl="+wode.domain+"/login_callback.html");
		}
	}else{
		var user = json.data;
		if(user!=undefined && user.enabled==0){
			/**
			 * 发送注册激活邮件
			 */
			$.ajax({
				dataType: 'json',
				url:"/personal/sendRegisterEmail?type=enabled&userId="+user.userId+"&email="+user.userEmail,
				success: function(data){
					if(data.success){
						$("#passwordError").html("帐号未激活，已发送激活邮件至注册邮箱，请先进行激活");
						
					}else{
						$("#passwordError").html("帐号未激活，发送邮件失败，请检查注册邮箱是否可用");
					}
					$(".loading_img").hide();
					$(".login_btn").css("background","#ff6161");
		    		$(".login_btn a").attr("onclick","c_login();");
					$("#passwordError").fadeIn("slow");
					setTimeout("display()",timeout);
				},
			});
		}else{
			
			if('1'==showVcode) {
				changeVCode();
				$("#divVcode").show();
			}
			
			$(".loading_img").hide();
			$(".login_btn").css("background","#ff6161");
    		$(".login_btn a").attr("onclick","c_login();");
    		$("#passwordError").html(json.msg);
	    	$("#passwordError").fadeIn("slow");
			setTimeout("display()",timeout);
		}
	}
}

function changeVCode(){
	$(".login_yzm img").attr("src",wode.userDomain+"/captcha?num="+Math.round(Math.random()*10000));
}

function MsgErr(){
	if(loopStop) return;
	
	loopStop=true;
	qrMsgShow =false;
	$(".qrcode-mod").show();
	$(".qrcode-msg").hide();
	$(".msg-err").show();
}

function changeQr(){
	$(".msg-err").hide();
	$(".qrcode-img img").attr("src",wode.domain+"/user/getQrForLogin?num="+Math.round(Math.random()*10000));
	
	loopStop=false;
	qrMsgShow =false;
	 //二维码显示两分钟后提示扫码失败 
	 setTimeout("MsgErr()",15000);
	 getQrTicket();
}
var loopStop =false;
var qrMsgShow =false;
function showQrMsg(t) {
	if(t=='1') {
		if(!qrMsgShow) {
			$(".qrcode-mod").hide();
			$(".qrcode-msg").show();			
		}
	} else if (t=='null') {
	} else {
		loopStop=true;
		$.postJSON("user/hasLogin",{"ticket":t,"remenber":1},function(json){
			if(json.success){
				user_ticket = t;
				$("#login_iframe").attr("src",wode.userDomain + "/writeTicket/"+user_ticket+"?callbackUrl="+wode.domain+"/login_callback.html");
			}
		});		
	}
}

function getQrTicket(){
	if(loopStop) return;
	
	jQuery.getScript(
			wode.domain+"/user/getQrTicket?num="+Math.round(Math.random()*10000),
			function(data, status, jqxhr) {
				setTimeout("getQrTicket()",1500);
			});
	
//	$.ajax({
//		dataType: 'json',
//		url:wode.domain+"/user/getQrTicket?num="+Math.round(Math.random()*10000),
//		success: function(data){
//			if(data.success){
//				if('1' == data.data) {
//					$(".qrcode-mod").hide();
//					$(".qrcode-msg").show();
//				} else {
//				}
//			}
//			//每秒请求，判断二维码是否被扫描
//			setTimeout("getQrTicket()",1500);
//		},
//	});
	
}
