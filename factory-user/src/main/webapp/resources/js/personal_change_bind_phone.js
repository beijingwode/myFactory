
var timeout = 4000;//错误信息展示时间（4秒）
$(document).ready(function() {
	var pageSize = 2;//每页显示条数
	var result = true;//提交验证前标识
	/**
	 * 个人中心修改手机绑定：提交旧手机验证码
	 */
	$("#loading").hide();
	setTimeout("display()",timeout);
	$("#submitResult").click(function(){
		var code = /^\d{6}$/;//验证码校验
		result = true;
		if(!code.test($("#code").val())){
			$("#sendResult").html("请填写正确的验证码");
			$("#sendResult").fadeIn("slow");
			setTimeout("display()",timeout);
			result = false;
		}
		
		if(result){
			$("#loading").show();
			$.ajax({
			    url:wode.userDomain+"/checkPhoneCode?code="+$("#code").val()+"&phone="+$("#phone").val(),
				dataType : 'jsonp',
			    jsonp:'jsonpcallback',
				success: function(data){
					$("#loading").hide();
					if(data.success){
						location.href="/member/bindPhone";
					}else{
						$("#sendResult").html(data.msg);
						$("#sendResult").fadeIn("slow");
						setTimeout("display()",timeout);
					}
				 },
				 error:function(){
					$("#loading").hide();
				 	$("#sendResult").html("未知错误，请联系客服");
				}
			});
		}
	});
	
	/**
	 * 再次发送验证短信
	 */
	$("#changePhone").click(function(){
		sendVCode();
	});
	changeVCode();
});

function sendVCode() {

	$("#loading").show();
	$.ajax({
	    url:wode.userDomain+"/sms/changeBindPhone?userId="+$("#uid").val()+"&userFrom="+wode.comeFrom+"&vcode="+$("#vcode").val(),
		dataType : 'jsonp',
	    jsonp:'jsonpcallback',
	    success: function(data){
	    	$("#loading").hide();
	    	if(data.success) {
	    		$("#before").html("短消息已发送到绑定手机，若未收到，请稍候点击获取验证码再次发送");
	    		$("#sendResult").html("验证码发送成功");
	    	} else if("验证码错误，请刷新后重试"==data.msg) {
		    		$(".yzm_error").show();
		    } else {
		    		$("#sendResult").html(data.msg);
		    }
	    	$("#sendResult").fadeIn("slow");
	    	setTimeout("display()",timeout);
	    },
	 	error:function(){
	 		$("#loading").hide();
		    alert("未知错误");
		}
	});
	time($("#changePhone"),60);
}

function changeVCode(){
	$(".login_yzm img").attr("src",wode.userDomain+"/captcha?num="+Math.round(Math.random()*10000));
}

function display(){
	$("#sendResult").fadeOut("slow");
	$("#before").fadeOut("slow");
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