

$(function(){
	
	var error = jsError;
	if(error == 'error'){
		$("#error2").html("账号或密码错误，请从新输入！");
		$("#error2").fadeIn();
		setTimeout("gotomain()",3000);
	}else if(error=='otherSupplierLogin'){
		$("#error1").html("不是商家账号");
		$("#error1").fadeIn();
		setTimeout("gotomain()",3000);
	}
	
	
    $("#userName").blur(function(){//用户名文本框失去焦点触发验证事件
   	var email = $("#userName").val();
   	/* var phonePattern = /^0?1[3|4|5|8][0-9]\d{8}$/;
   	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; */
   	   var phonePattern = /^[0-9a-zA-Z\@\_\-\.]+$/;
   	   var pattern = /^[0-9a-zA-Z\@\_\-\.]+$/;
       if(pattern.test(email)||phonePattern.test(email))//只处验证不能为空并且只能为英文或者数字或者下划线组成的２－１５个字符
       {
       	$("#error1").fadeOut();
       }
       else
       {
       	$("#error1").fadeIn();
       	setTimeout("gotomain()",3000);
       }

    });

     $("#password").blur(function(){//用户名文本框失去焦点触发验证事件
   	var password = $("#password").val();
       if((password.length<6||password.length>20) && password.length>0)//只处验证和上面一样
       {
    	$("#error2").html("密码不能为空且最少6位！");
       	$("#error2").fadeIn();
       	setTimeout("gotomain()",3000);
       }
       else
       {
       	$("#error2").fadeOut();
       }

    });
     
    $("body").bind('keyup',function(event) {  
    	var act = document.activeElement.id;
    	if(act == "password" || act == "userName"){
    		if(event.keyCode==13){
    			_login();
    	    } 
    	}
   	}); 

});

function _login(){
	var userName = $("#userName").val();
	var password = $("#password").val();
	/* var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
	var phonePattern = /^0?1[3|4|5|8][0-9]\d{8}$/; */
	var pattern = /^[0-9a-zA-Z\@\_\-\.]+$/;
	var phonePattern = /^[0-9a-zA-Z\@\_\-\.]+$/;
// 	var pa = /^[a-z0-9_-]{6,20}$/;
	if($.trim(userName).length==0){
		$("#userName").focus();
		$("#error1").html("账号不能为空！");
		$("#error1").fadeIn();
		setTimeout("gotomain()",3000);
		return false;
	}
	if($.trim(password).length<6||$.trim(password).length>20){
		$("#password").focus();
		$("#error2").html("密码不能为空且最少6位！");
		$("#error2").fadeIn();
		setTimeout("gotomain()",3000);
		return false;
	}
	if(pattern.test(userName)||phonePattern.test(userName)){

		var user_from = document.getElementById("user_from");
		$("#post_param").html('<input type="hidden" name="userName" value="'+$("#userName").val() +'">'
				+ '<input type="hidden" name="password" value="'+$("#password").val() +'">'
				+ '<input type="hidden" name="code" value="'+$("#vcode").val() +'">'
				+ '<input type="hidden" name="userCom" value="'+wode.comeFrom +'">'
				+ '<input type="hidden" name="loginType" value="seller_web_login">'
				+ '<input type="hidden" name="returnUrl" value="'+wode.domain+'/user/writeUserResult?type=login">');
		user_from.action = wode.userDomain+'/login';
		user_from.submit();
		
	}else{
		$("#userName").focus();
		$("#error1").html("邮箱格式不正确！");
		$("#error1").fadeIn();
		setTimeout("gotomain()",3000);
		return false;
	}
}

function showLoginResult(json,showVcode){
	if(json.success){
		window.location=jsBasePath+"/user/hasLogin?ticket="+json.data +"&flag=" + $("#flag").val();		
	}else{
		var user = json.data;
		if(user!=undefined){
			//账号未激活
			if(user.enabled==0) {
				window.location=jsBasePath+"/user/toregister2.html?email=" + $("#userName").val() + "&key=" + user.userId;
			} 
			
			//账户已禁用，
			if(user.usable==0) {
				$("#error2").html("该账户已禁用，请联系管理员解禁");
				$("#error2").fadeIn();
				setTimeout("gotomain()",3000);
			}
			
		}else{

			if('1'==showVcode) {
				changeVCode();
				$("#divVcode").show();
				moveBtn();
			}
			
			$("#error2").html(json.msg);
			$("#error2").fadeIn();
			setTimeout("gotomain()",3000);
		}
	}
}

function changeVCode(){
	$(".login_yzm img").attr("src",wode.userDomain+"/captcha?num="+Math.round(Math.random()*10000));
}

function gotomain(){
	$("#error1").fadeOut();
	$("#error2").fadeOut();
}





moveBtn();
function moveBtn(){ 
	var displayBlock = $("#divVcode").attr("style");
	if(displayBlock.indexOf("none") != -1){
		
		$(".login_btn").css({"margin-top":"30px"});
	}else{
		
		$(".login_btn").css({"margin-top":"5px"});
	
	} 
} 
	
//切换登录方式
$(function(){
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
})


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
	$(".qrcode-img img").attr("src",jsBasePath+"/user/getQrForLogin?num="+Math.round(Math.random()*10000));
	
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
	    jQuery.ajax({
	        type: "POST",
	        dataType :"json",
	        url: jsBasePath+"/user/qrLogin",
	        data: {"ticket":t,"member":1},
	        success: function(data){
				if(data.success){
					window.location=jsBasePath+data.msg;
				} else {
					alert(data.msg)
				}
	        }
	    });
	}
}

function getQrTicket(){
	if(loopStop) return;
	
	jQuery.getScript(
			jsBasePath+"/user/getQrTicket?num="+Math.round(Math.random()*10000),
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
