$(document).ready(function() {
	$(".getyzm").removeAttr("disabled");
	$(".getyzm").click(function(){

		$.ajax({
		    url:wode.userDomain+"/sms/sendForPassword?userId="+$("#userId").val()+"&userFrom="+wode.comeFrom+"&vcode="+$("#vcode").val(),
    		dataType : 'jsonp',
    	    jsonp:'jsonpcallback',
		    success:function(data){
		    	if("验证码错误，请刷新后重试" == data.msg) {
			    	$(".yzm_error").fadeIn("slow");
		    	} else {
		    		$(".yzm_error").hide();
			    	$(".error").html(data.msg);
			    	$(".error").fadeIn("slow");
					setTimeout("display()",timeout);
		    	}
		    },
		 	error:function(){
			}
		});
		time($(".getyzm"),60);
	});
	
	$(".btnnext").click(function(){

		$.ajax({
		    url:wode.userDomain+"/checkPhoneCode?code="+$("#code").val()+"&phone="+$("#phone").val(),
			dataType : 'jsonp',
		    jsonp:'jsonpcallback',
		    success:function(data){
				if(data.success){
		    		location.href=wode.domain+"/member/modifyByPhone?code="+data.data;
		    	}else{
			    	$(".error").html(data.msg);
			    	$(".error").fadeIn("slow");
					setTimeout("display()",timeout);
		    	}
		    },
		 	error:function(){
				alert("未知错误");
			}
		});
	});
});

function changeVCode(){
	$(".login_yzm img").attr("src",wode.userDomain+"/captcha?num="+Math.round(Math.random()*10000));
}
function display(){
	$(".error").fadeOut("slow");
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