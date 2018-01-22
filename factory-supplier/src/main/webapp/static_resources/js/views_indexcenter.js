$(function(){
	
	var error = jsError;
	if(error == 'error'){
		$("#error2").html("账号或密码错误，请从新输入！");
		$("#error2").fadeIn();
		setTimeout("gotomain()",3000);
	}
	
	
    $("#userName").blur(function(){//用户名文本框失去焦点触发验证事件
	   	var email = $("#userName").val();
	    if(!checkEmail(email) && email!='')//只处验证不能为空并且只能为英文或者数字或者下划线组成的２－１５个字符
	    {
	       	$("#error1").fadeIn();
	       	setTimeout("gotomain()",3000);
	    } else {
	       	$("#error1").fadeOut();
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

//校验邮箱
function checkEmail(email){
	email=$.trim(email);
	if(email.length==0) return false;
	if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
	return pattern.test(email);
}

function gotomain(){
	$("#error1").fadeOut();
	$("#error2").fadeOut();
}

function _login(){
	var userName = $("#userName").val();
	var password = $("#password").val(); 
// 	var pa = /^[a-z0-9_-]{6,20}$/;
	if($.trim(userName).length==0){
		$("#userName").focus();
		$("#error1").html("邮箱不能为空！");
		$("#error1").fadeIn();
		setTimeout("gotomain()",3000);
		return false;
	}
	if(!checkEmail(userName)){
		$("#userName").focus();
		$("#error1").html("邮箱格式不正确！");
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
	$('#sub_form_resetpwd').submit();
}