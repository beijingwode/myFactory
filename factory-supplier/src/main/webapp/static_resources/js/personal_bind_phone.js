var wode={"hasLogin":0};//只保留一个全局变量，避免变量冲突.
wode.phone = /^(1[3|4|5|7|8][0-9])\d{8}$/;//手机号码验证
wode.userDomain = "http://passport.wd-w.com";//邮箱验证
wode.comeFrom = "myFactory";//邮箱验证

var timeout = 3000;
$(document).ready(function() {
	$("#phone").removeAttr("readonly");
	$("#loading").hide();
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
				url:wode.userDomain+"/sms/sendForBindPhone?userId="+$("#uid").val() + "&phone="+$("#phone").val()+"&userFrom="+wode.comeFrom,
	    		dataType : 'jsonp',
	    	    jsonp:'jsonpcallback',
				success: function(data){
					$("#loading").hide();
					if(data.success)
						$("#phone").attr("readonly","readonly");
					var msg = data.msg;
					if("验证码错误，请刷新后重试" == msg) {
						msg="校验码错误，请重新输入";
					}
					$("#error").html(msg);
					$("#error").fadeIn("slow");
					setTimeout("display()",timeout);
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
						location.href=jsBasePath+"/user/bindPhoneSuccess";
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
	changeVCode();
});

function changeVCode(){
	$(".login_yzm img").attr("src",wode.userDomain+"/captcha?num="+Math.round(Math.random()*10000));
}
function display(){
	$("#error").fadeOut("slow");
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