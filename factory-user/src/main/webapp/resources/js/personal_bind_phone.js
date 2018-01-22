$(document).ready(function() {
	$("#phone").removeAttr("readonly");
	$("#loading").hide();
	changeVCode();
	/**
	 * 个人中心手机绑定：获取验证码
	 */
	$(".getyzm").click(function(){
		result = true;
		if(!wode.phone.test($("#phone").val())){
			$("#error").html("请填写正确的手机号码");
			$("#error").fadeIn("slow");
			setTimeout("display()",timeout);
			result = false;
		}
		
		if(result){
			$("#loading").show();
			
			$.ajax({
				url:wode.userDomain+"/sms/sendForBindPhone?userId="+$("#uid").val() + "&phone="+$("#phone").val()+"&userFrom="+wode.comeFrom+"&vcode="+$("#vcode").val(),
	    		dataType : 'jsonp',
	    	    jsonp:'jsonpcallback',
				success: function(data){
					$("#loading").hide();
					if(data.success)
						$("#phone").attr("readonly","readonly");
					

			    	if("验证码错误，请刷新后重试"==data.msg) {
			    		$(".yzm_error").show();
			    	} else {
			    		$(".yzm_error").hide();
						$("#error").html(data.msg);
						$("#error").fadeIn("slow");
						setTimeout("display()",timeout);
					}
				 },
				 error:function(){
					 $("#loading").hide();
				 	$("#error").html("短信发送失败，请联系客服");
				}
			});
			time($(".getyzm"),60);
		}
	});
	
	/**
	 * 个人中心手机绑定：提交绑定
	 */
	$("#submitResult").click(function(){
		var code = /^\d{6}$/;//验证码校验
		result = true;
		if(!wode.phone.test($("#phone").val())){
			$("#error").html("请填写正确的手机号码");
			$("#error").fadeIn("slow");
			setTimeout("display()",timeout);
			result = false;
			return;
		}
		
		if(!code.test($("#code").val())){
			$("#error").html("请填写正确的验证码");
			$("#error").fadeIn("slow");
			setTimeout("display()",timeout);
			result = false;
		}
		
		if(result){
			$.ajax({
				url:wode.userDomain+"/bindPhone?userId="+$("#uid").val() + "&phone="+$("#phone").val()+"&code="+$("#code").val(),
	    		dataType : 'jsonp',
	    	    jsonp:'jsonpcallback',
				success: function(data){
					if(data.success){
						location.href=wode.domain+"/member/bindPhoneSuccess";
					}else{
						$("#error").html(data.msg);
						$("#error").fadeIn("slow");
						setTimeout("display()",timeout);
					}
				 },
				 error:function(){
				 	$("#error").html("未知错误，请联系客服");
				}
			});
		}
	});
});

function display(){
	$("#error").fadeOut("slow");
}

function changeVCode(){
	$(".login_yzm img").attr("src",wode.userDomain+"/captcha?num="+Math.round(Math.random()*10000));
}
function time(btn,wait){
    if (wait == 0) {
        btn.removeAttr("disabled");
        btn.html("获取验证码");
    } else {
        btn.attr("disabled",true);
        btn.html(wait + "秒后点击");
        wait--;
        setTimeout(function () {
            time(btn,wait);
        },
        1000)
    }
}