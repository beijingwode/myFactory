
var timeout = 3000;//错误信息展示时间（3秒）
$(document).ready(function() {
	setTimeout("display()",timeout);
	$("#loading").hide();
	/**
	 * 填写帐号
	 */
	$("#checkUserName").click(function(){
		var result = true;
		if(!wode.phone.test($("#userName").val())){
			if(!wode.checkEmail($("#userName").val())){
				$(".error").html("请填写正确的手机号/邮箱");
		 		$(".error").fadeIn("slow");
		 		setTimeout("display()",timeout);
		 		result = false;
			}
		}
		
		if(result){
			$("#loading").show();
			$.ajax({
			    dataType: 'json',
			    url:"/user/findPasswordByUserName?userName="+$("#userName").val(),
			    success: function(data){
			    	if(data.success){
			    		location.href=data.data;
			    	}else{
			    		$("#loading").hide();
				    	$(".error").html(data.msg);
						$(".error").fadeIn("slow");
						setTimeout("display()",timeout);
			    	}
			    },
			 	error:function(){
				    alert("未知错误");
				}
			});
		}
	});
	
	/**
	 * 发送验证码
	 */
	$(".getyzm").click(function(){
		sendSmsCode();
	});
	
	/**
	 * 验证手机验证码
	 */
	$("#next").click(function(){
		var result = true;
		var code = /^\d{6}$/;//验证码校验
		if(!code.test($("#code").val())){
			$(".error").html("请填写正确的验证码");
			$(".error").fadeIn("slow");
			setTimeout("display()",timeout);
			result = false;
		}
		if(result){
			$("#loading").show();
			$.ajax({
			    url:wode.userDomain+"/checkPhoneCode?code="+$("#code").val()+"&phone="+$("#phone").val(),
				dataType : 'jsonp',
			    jsonp:'jsonpcallback',
			    success:function(data){
					if(data.success){
			    		location.href="/user/modifyPasswordByPhone?phone="+$("#phone").val()+"&code="+data.data;
			    	}else{
			    		$("#loading").hide();
				    	$(".error").html(data.msg);
						$(".error").fadeIn("slow");
						setTimeout("display()",timeout);
			    	}
			    },
			 	error:function(){
					alert("未知错误");
				}
			});
		}
	});
	
	/**
	 * 修改密码
	 */
	$("#modifyNext").click(function(){
		var result = true;
		var register = /^[0-9A-Za-z]{4,20}$/;//密码验证
		if(!register.test($("#password").val())){
			$("#passwordError").html("密码由4-20位字母、数字组合");
	 		$("#passwordError").fadeIn("slow");
	 		setTimeout("display()",timeout);
	 		result = false;
		}
		
		if($("#password").val()!=$("#rePassword").val()){
			$("#rePasswordError").html("两次密码不一致");
	 		$("#rePasswordError").fadeIn("slow");
	 		setTimeout("display()",timeout);
	 		result = false;
		}
		if(result){
			$("#loading").show();
			var user_from = document.getElementById("user_from");
			$("#post_param").html('<input type="hidden" name="userName" value="'+$("#userName").val() +'">'
					+ '<input type="hidden" name="code" value="'+$("#code").val() +'">'
					+ '<input type="hidden" name="password" value="'+$("#password").val() +'">'
					+ '<input type="hidden" name="userCom" value="'+wode.comeFrom +'">'
					+ '<input type="hidden" name="returnUrl" value="'+wode.domain+'/user/writeUserResult?type=forget">');
			user_from.action = wode.userDomain+'/updatePasswordByName';
			user_from.submit();
			
		}
	});
	
});

function changeVCode(){
	$(".login_yzm img").attr("src",wode.userDomain+"/captcha?num="+Math.round(Math.random()*10000));
}
function forgetResult(data){
	if(data.success){
		location.href=wode.domain+'/user/modifyPasswordSuccess';
	}else{
		$("#loading").hide();
    	$(".error").html(data.msg);
		$(".error").fadeIn("slow");
		setTimeout("display()",timeout);
	}
}

function sendSmsCode() {
	$("#loading").show();
	$.ajax({
	    url:wode.userDomain+"/sms/sendForPassword?userId="+$("#userId").val()+"&userFrom="+wode.comeFrom+"&vcode="+$("#vcode").val(),
		dataType : 'jsonp',
	    jsonp:'jsonpcallback',
	    async: false,
	    cache:false,
	    success:function(data){
	    	if("验证码错误，请刷新后重试"==data.msg) {
	    		$(".yzm_error").show();
	    		clearTimeout(timeID);
	    		time($(".getyzm"),0);
	    	} else {
	    		$(".yzm_error").hide();
				$("#loading").hide();
				$(".error").html(data.msg);
				$(".error").fadeIn("slow");
				setTimeout("display()",timeout);
	    	}
	    },
	 	error:function(){
			alert("未知错误");
		}
	});
	
	time($(".getyzm"),60);
}

function display(){
	$(".error").fadeOut("slow");
	$(".sendSmsBefore").fadeOut("slow");
	$(".sendSmsResult").fadeOut("slow");
	$(".alter_error").fadeOut("slow");
}
var timeID;
function time(btn,wait){
    if (wait == 0) {
        btn.removeAttr("disabled");
        btn.html("获取验证码");
    } else {
        btn.attr("disabled",true);
        btn.html(wait + "秒后点击");
        wait--;
        timeID=setTimeout(function () {
            time(btn,wait);
        },
        1000)
    }
}