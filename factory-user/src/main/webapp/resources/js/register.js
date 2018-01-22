var register = /^[0-9A-Za-z]{4,20}$/;//密码验证
var phone = /^(1[3|4|5|7|8][0-9])\d{8}$/;//手机号码验证
var result = true;//提交前验证标识
var timeout = 6000;//错误信息展示时间（6秒）
var domain = "http://www.wd-w.com";

//校验邮箱
function checkEmail(email){
	email=$.trim(email);
	if(email.length==0) return true;
	if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
	return pattern.test(email);
}

$(document).ready(function() {
	changeVCode();
	
	var url=window.location.href;
	if(url.indexOf(domain) != 0) {
		var indexP = url.indexOf(":");
		var start= url.substring(0,indexP+3);
		url = url.substring(indexP+3);

		indexP = url.indexOf(":");
		if(indexP != 0) {
			var domainS = url.substring(0,indexP);
			url = url.substring(indexP);
			indexP = url.indexOf("/");
			domainS=domainS+url.substring(0,indexP);

			domain=start+domainS;
		}
	}
	initEmailPostfix();
	$('.getcode a').attr("onclick","sendsms()");
	$('#phoneRegister a').attr("onclick","phoneRegister()");
	$('#emailRegister a').attr("onclick","emailRegister()");
	$("#phoneChecked").click(function(){
		if($("#phoneChecked").attr("checked")=="checked"){
			$('#phoneRegister a').attr("onclick","phoneRegister()");
			$("#phoneRegister").removeClass();
			$("#phoneRegister").addClass("register_btn");
		}else{
			$('#phoneRegister a').removeAttr("onclick");
			$("#phoneRegister").removeClass();
			$("#phoneRegister").addClass("register_btn_s");
		}
	});
	
	$("#emailChecked").click(function(){
		if($("#emailChecked").attr("checked")=="checked"){
			$('#emailRegister a').attr("onclick","emailRegister()");
			$("#emailRegister").removeClass();
			$("#emailRegister").addClass("register_btn");
		}else{
			$('#emailRegister a').removeAttr("onclick");
			$("#emailRegister").removeClass();
			$("#emailRegister").addClass("register_btn_s");
		}
	})
	
	/*$("input").blur(function(){
		if(!phone.test($("#phone").val())){
			$("#phoneError").html("请填写正确的手机号码");
	 		$("#phoneError").fadeIn("slow");
	 		setTimeout("display()",timeout);
		}
		
		if($("#code").val().length!=6){
			$("#codeError").html("请填写正确的验证码");
	 		$("#codeError").fadeIn("slow");
	 		setTimeout("display()",timeout);
		}
		
		if(!register.test($("#password").val())){
			$("#passwordError").html("密码由6-20位字母、数字和符号两种以上数字组合");
	 		$("#passwordError").fadeIn("slow");
	 		setTimeout("display()",timeout);
		}
		
		if($("#password").val()!=$("#rePassword").val()){
			$("#rePasswordError").html("两次密码不一致");
	 		$("#rePasswordError").fadeIn("slow");
	 		setTimeout("display()",timeout);
		}
	})*/
	/**
	 * 监听输入框的回车操作
	 */  
	$("#registerByPhone").keydown(function(event){ 
		if(event.keyCode == 13){
			phoneRegister();
		}
	});
	
	/**
	 * 监听输入框的回车操作
	 */  
	$("#registerByEmail").keydown(function(event){ 
		if(event.keyCode == 13){
			emailRegister();
		}
	});
	
});


function phoneRegister(){
	result = true;
	if(!phone.test($("#phone").val())){
		$("#phoneError").html("请填写正确的手机号码");
 		$("#phoneError").fadeIn("slow");
 		setTimeout("display()",timeout);
 		result = false;
	}
	
	if($("#code").val().length!=6){
		$("#codeError").html("请填写正确的验证码");
 		$("#codeError").fadeIn("slow");
 		setTimeout("display()",timeout);
 		result = false;
	}
	
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
	
	alert("本系统仅限企业员工使用，为保证员工权益，请通过商家端进行员工注册。");
	return false;
	if(result){
		$('#phoneRegister a').removeAttr("onclick");
		$("#phoneRegister").removeClass();
		$("#phoneRegister").addClass("register_btn_s");
		$("#loadingPhoneRegister").show();
		
		var user_from = document.getElementById("user_from");
		$("#post_param").html('<input type="hidden" name="userName" value="'+$("#phone").val() +'">'
				+ '<input type="hidden" name="userPhone" value="'+$("#phone").val() +'">'
				+ '<input type="hidden" name="nickName" value="'+$("#phone").val() +'">'
				+ '<input type="hidden" name="password" value="'+$("#password").val() +'">'
				+ '<input type="hidden" name="enabled" value="1">'
				+ '<input type="hidden" name="usable" value="1">'
				+ '<input type="hidden" name="userType" value="1">'
				+ '<input type="hidden" name="userCom" value="'+wode.comeFrom +'">'
				+ '<input type="hidden" name="code" value="'+$("#code").val() +'">'
				+ '<input type="hidden" name="returnUrl" value="'+domain+'/user/writeUserResult?type=regist">');
		user_from.action = wode.userDomain+'/regist';
		user_from.submit();
	}
}

function showRegisterResult(data){
	$("#loadingPhoneRegister").hide();
	$('#phoneRegister a').attr("onclick","phoneRegister()");
	$("#phoneRegister").removeClass();
	$("#phoneRegister").addClass("register_btn");
	if(data.success){
		$("#rePasswordError").html(data.msg);
			$("#rePasswordError").fadeIn("slow");
			setTimeout("display()",timeout);
		window.location="/index.html"
	}else{
		if(data.msg=="验证码错误"){
			$("#codeError").html("请填写正确的验证码");
			$("#codeError").fadeIn("slow");
		}else{
			$("#rePasswordError").html(data.msg);
			$("#rePasswordError").fadeIn("slow");
		}
		setTimeout("display()",timeout);
	}
}

function showRegisterResult2(data){
	if(data.success){
		$.ajax({
			dataType: 'json',
			url:"personal/sendRegisterEmail?type=register&userId="+data.data.userId,
			success: function(result){
			    if(result.success){
			    	location.href=domain+result.data;
			    }else{
			    	$("#loading").hide();
		    		$('#emailRegister a').attr("onclick","emailRegister()");
					$("#emailRegister").removeClass();
					$("#emailRegister").addClass("register_btn");
			    	$("#rePasswordEmailError").html(result.msg);
			     	$("#rePasswordEmailError").fadeIn("slow");
			     	setTimeout("display()",timeout);
			    }
			},
			error:function(){
				$("#loading").hide();
				$('#emailRegister a').attr("onclick","emailRegister()");
				$("#emailRegister").removeClass();
				$("#emailRegister").addClass("register_btn");
			 	$("#rePasswordEmailError").html("注册失败,请联系客服");
		     	$("#rePasswordEmailError").fadeIn("slow");
		     	setTimeout("display()",timeout);
			}
		});
	}else{
		$("#loading").hide();
		$('#emailRegister a').attr("onclick","emailRegister()");
		$("#emailRegister").removeClass();
		$("#emailRegister").addClass("register_btn");
		$("#rePasswordEmailError").html(data.msg);
 		$("#rePasswordEmailError").fadeIn("slow");
 		setTimeout("display()",timeout);
	}
}

function initEmailPostfix(){
	$.ajax({
		dataType: 'json',
		url:"/personal/emailPostfixs",
		success: function(result){
		    if(result.success){
		    	var ls = result.data;
		    	if(ls.length>0) {
		    		var html='<option value="">请选择邮箱后缀</option>';
		    		for(var i=0;i<ls.length;i++) {
		    			html +='<option value="@'+ ls[i] +'">@'+ ls[i] +'</option>';
		    		}
		    		
		    		$("#emailPostFix").html(html);
		    	}
		    }
		},
		error:function(){
		}
	});
}
function emailRegister(){
	var result = true;
	
	var email = "";
	if($("#emailPostFix").val() == "") {
		$("#emailError").html("请填选择邮箱后缀");
 		$("#emailError").fadeIn("slow");
 		setTimeout("display()",timeout);
 		result = false;
	} else {
		email = $("#email").val() + $("#emailPostFix").val();
		
		if(!checkEmail(email)){
			$("#emailError").html("请填写合法的邮箱");
	 		$("#emailError").fadeIn("slow");
	 		setTimeout("display()",timeout);
	 		result = false;
		}
	}
	
	
	if(!register.test($("#passwordEmail").val())){
		$("#passwordEmailError").html("（密码4-20位字符，由字母、数字组合）");
 		$("#passwordEmailError").fadeIn("slow");
 		setTimeout("display()",timeout);
 		result = false;
	}
	
	if($("#passwordEmail").val()!=$("#rePasswordEmail").val()){
		$("#rePasswordEmailError").html("两次密码不一致");
 		$("#rePasswordEmailError").fadeIn("slow");
 		setTimeout("display()",timeout);
 		result = false;
	}
	
	
		
	if(result){
		$('#emailRegister a').removeAttr("onclick");
		$("#emailRegister").removeClass();
		$("#emailRegister").addClass("register_btn_s");
		$("#loading").show();
		
		var user_from = document.getElementById("user_from");
		$("#post_param").html('<input type="hidden" name="userName" value="'+email +'">'
				+ '<input type="hidden" name="userEmail" value="'+email +'">'
				+ '<input type="hidden" name="nickName" value="'+email +'">'
				+ '<input type="hidden" name="password" value="'+$("#passwordEmail").val() +'">'
				+ '<input type="hidden" name="enabled" value="0">'
				+ '<input type="hidden" name="usable" value="1">'
				+ '<input type="hidden" name="userType" value="1">'
				+ '<input type="hidden" name="userCom" value="'+wode.comeFrom +'">'
				+ '<input type="hidden" name="returnUrl" value="'+wode.domain+'/user/writeUserResult?type=regist2">');
		user_from.action = wode.userDomain+'/regist';
		user_from.submit();
	}
}

function sendsms(){
	result = true;
	if(!phone.test($("#phone").val())){
		$("#phoneError").html("请填写正确的手机号码");
 		$("#phoneError").fadeIn("slow");
 		setTimeout("display()",timeout);
 		result = false;
	}

	if($("#vcode").val()==""){
		$("#login_yzm_error").show();
 		result = false;
	}
	
	if(result){
		$("#loadingPhone").show();
		$.ajax({
		    url:wode.userDomain+"/sms/sendSms?phone="+$("#phone").val() + "&vcode="+$("#vcode").val()+ "&userFrom="+wode.comeFrom,
    		dataType : 'jsonp',
    	    jsonp:'jsonpcallback',
		    success:function(data){
		    	$("#loadingPhone").hide();
		    	if(data.success){
		    		$("#codeError").html("验证码已发送，请稍候");
		     		$("#codeError").fadeIn("slow");
		     		setTimeout("display()",timeout);
		    	}else{
		    		if(data.msg=="验证码错误，请刷新后重试") {
		    			$("#login_yzm_error").show();
		    		} else {
		    			$("#codeError").hide();
			    		$("#phoneError").html(data.msg);
			     		$("#phoneError").fadeIn("slow");
			     		setTimeout("display()",timeout);
		    		}
		    	}
		    },
		 	error:function(){
		 		$("#loadingPhone").hide();
		 		$("#phoneError").html("验证码发送失败，请联系客服");
	     		$("#phoneError").fadeIn("slow");
	     		setTimeout("display()",timeout);
			}
		});
		time($(".getcode a"),60);
	}
}

function display(){
	$(".register_error").fadeOut("slow");
}

function changeVCode(){
	$(".login_yzm img").attr("src",wode.userDomain+"/captcha?num="+Math.round(Math.random()*10000));
}

function time(btn,wait){
    if (wait == 0) {
        btn.attr("onclick","sendsms()");
        btn.html("获取验证码");
    } else {
        btn.removeAttr("onclick");
        btn.html(wait + "秒后点击");
        wait--;
        setTimeout(function () {
            time(btn,wait);
        },
        1000)
    }
}
