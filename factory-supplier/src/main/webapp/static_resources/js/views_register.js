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
	var flag = true;
	function _submit(){
		if(checkout()){
			if($("#password").val() != $("#password2").val()){
				//两次密码不一致
				//alert("两次密码不一致");
				$("#error3").fadeIn();
				return false;
			}else{
				if(flag){
					return false;
				}
				
				var user_from = document.getElementById("user_from");
				$("#post_param").html('<input type="hidden" name="userName" value="'+$("#email").val() +'">'
						+ '<input type="hidden" name="userEmail" value="'+$("#email").val() +'">'
						+ '<input type="hidden" name="nickName" value="'+$("#email").val() +'">'
						+ '<input type="hidden" name="password" value="'+$("#password").val() +'">'
						+ '<input type="hidden" name="enabled" value="0">'
						+ '<input type="hidden" name="usable" value="1">'
						+ '<input type="hidden" name="userType" value="2">'
						+ '<input type="hidden" name="userCom" value="'+wode.comeFrom +'">'
						+ '<input type="hidden" name="returnUrl" value="'+wode.domain+'/user/writeUserResult?type=regist2">');
				user_from.action = wode.userDomain+'/regist';
				user_from.submit();
			} 
		}else{
			//alert('验证失败');
			return false;
		}
	}
	
	$(function(){
	     $("#email").blur(function(){//用户名文本框失去焦点触发验证事件
			$("#error5").hide();
	    	var email = $("#email").val();
	        if(!checkEmail(email))//只处验证不能为空并且只能为英文或者数字或者下划线组成的２－１５个字符
	        {
	        	$("#error1").fadeIn();
	        }
	        else
	        {
	        	$("#error1").fadeOut();
	        	var basePath = jsBasePath;
				$.ajax({
					url : basePath +'/user/getEmail.json?email='+email,
					type : "GET",
					dataType: "json",  //返回json格式的数据  
				    async: true,
					success : function(data) {
						
						if(data.result.errorCode==0){
							flag = false;
							$("#error4").fadeOut();
						}else{
							flag = true;
							$("#error4").fadeIn();
						}
						
					}, error : function() {    
				    }  
				});
	        }

	     });

	      $("#password").blur(function(){//用户名文本框失去焦点触发验证事件
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
	     $("#password2").blur(function(){//用户名文本框失去焦点触发验证事件
	    	var password = $("#password").val();
	    	var password2 = $("#password2").val();
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
	
	function checkout(){
		$("#error5").hide();
		
		var email = $("#email").val();
		var password = $("#password").val();
		var password2 = $("#password2").val();
		var re=/[A-Za-z].*[0-9]|[0-9].*[A-Za-z]/;
		if(!checkEmail(email)){
			$("#error1").fadeIn();
			$("#sub").removeClass().addClass("register_btn register_before");
			return false;
		}
		else
		{
			$("#error1").fadeOut();
		}	
		if(password.length<6||password.length>20||!re.test(password)){
			$("#error2").fadeIn();
			$("#sub").removeClass().addClass("register_btn register_before");
			return false;
		}
		else
		{
			$("#error2").fadeOut();
		}
		if(password2.length<6||password2.length>20){
			$("#error3").fadeIn();
			$("#sub").removeClass().addClass("register_btn register_before");
			return false;
		}
		else
		{
			if($("#password").val() != $("#password2").val()){
				$("#error3").fadeIn();
				$("#sub").removeClass().addClass("register_btn register_before");
				return false;
			}else{
				$("#error3").fadeOut();	
			}
		}
		if(!$('#checkbox').is(':checked')) {
			$("#sub").removeClass().addClass("register_btn register_before");
			return false;
		}
		$("#sub").removeClass().addClass("register_btn register_after");
		return true;
	}
	

	function showRegisterResult2(data){
		if(data.success){
			window.location=jsBasePath+"/user/emailRegistration.html?userId="+data.data.userId;
		}else{
			$("#error5").html(data.msg);
			$("#error5").fadeIn();
		}
	}
	
	//校验邮箱
	function checkEmail(email){
		email=$.trim(email);
		if(email.length==0) return false;
		if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
		var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
		return pattern.test(email);
	}

