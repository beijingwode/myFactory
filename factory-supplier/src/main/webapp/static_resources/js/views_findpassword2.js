
window.onload=function () {
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
			url = url.substring(indexP+1);
			indexP = url.indexOf("/");
			var r = url.substring(0,indexP);
			if(r=="supplier") {
				wode.domain=start+domainS + "/supplier";
			} else {
				wode.domain=start+domainS;
			}
		}
	}
}

$(function(){
	 $("#password").blur(function(){//用户名文本框失去焦点触发验证事件
			$("#error5").hide();
	    	var password = $("#password").val();
	    	var re=/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/;
	        if(password.length<6||password.length>20||!re.test(password))//只处验证和上面一样
	        {
	        	$("#error2").fadeIn();
	        }
	        else
	        {
	        	$("#error2").fadeOut();
	        }

	     });
	 $("#confirmPassword").blur(function(){//用户名文本框失去焦点触发验证事件
			$("#error5").hide();
	    	var password = $("#password").val();
	    	var password2 = $("#confirmPassword").val();
	        if(password!=password2 )//只处验证和上面一样
	        {
	        	$("#error3").fadeIn();
	        }
	        else
	        {
	        	$("#error3").fadeOut();
	        }

	     });
});

function submit(){
	var password = $("#password").val();
	var password2 = $("#confirmPassword").val();
	var re=/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/;
	if(password.length<6||password.length>20||!re.test(password)){
		$("#error2").fadeIn();
		return false;
	}
	else
	{
		$("#error2").fadeOut();
	}
	if(password2.length<6||password2.length>20){
		$("#error3").fadeIn();
		return false;
	}
	else
	{
		$("#error3").fadeOut();
	}
    if(password!=password2)//只处验证和上面一样
    {
    	$("#error3").fadeIn();
    	return false;
    }
	else
	{
		$("#error3").fadeOut();
	}
	var user_from = document.getElementById("user_from");
	$("#post_param").html('<input type="hidden" name="userName" value="'+$("#userName").val() +'">'
			+ '<input type="hidden" name="code" value="'+$("#code").val() +'">'
			+ '<input type="hidden" name="password" value="'+$("#password").val() +'">'
			+ '<input type="hidden" name="userCom" value="'+wode.comeFrom +'">'
			+ '<input type="hidden" name="returnUrl" value="'+wode.domain+'/user/writeUserResult?type=forget">');
	user_from.action = wode.userDomain+'/updatePasswordByName';
	user_from.submit();
	
	return false;
}


function forgetResult(data){
	if(data.success){
		window.location=jsBasePath+"/user/findpassword3.html";
	}else{
		$("#error5").html(data.msg);
		$("#error5").fadeIn();
	}
}