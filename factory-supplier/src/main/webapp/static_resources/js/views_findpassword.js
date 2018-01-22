//验证邮箱并且发送邮件
function cheksend(){
	var toEmail = $("#toEmail").val();
	if(!chek()){
		return false;
	}
	$(".sendcode").removeAttr("onclick");
	$.ajax({
	    dataType: 'json',
		url: jsBasePath+"/user/userSendMail.json?toEmail="+toEmail+"&type=findPwdByEmail",
		cache:false,
		success: function (data) {
// 			$("#findEmailbtn").removeAttr("disabled");
// 			$("#findEmailbtn").addClass("btn-primary1");
			if(data.data.errorCode != "0") {
// 				$("#" + data.key).nextAll('p').hide();
// 				$("#" + data.key).after($("<p>").addClass("help-inline font-red").html("<small>" + data.message + "</small>"));
				if(data.data.errorCode=="3507"){
					$("#error3").fadeIn();
					setTimeout("display()",3000);
				}else if(data.data.errorCode=="3505"){
					$("#error4").fadeIn();
					setTimeout("display()",3000);
				}
				$(".sendcode").attr("onclick","cheksend()");
			} else {
				$("#error2").fadeIn();
				setTimeout("display()",6000);
				return false;
			}
		},
		error: function (msg) {
			$(".sendcode").attr("onclick","cheksend()");
			return false;
		}
	});
}	

function display(){
	$("#error2").fadeOut();
	$("#error3").fadeOut();
	$("#error4").fadeOut();
}

function chek(){
	var toEmail = $("#toEmail").val();
	if(!checkEmail(toEmail)){
		$("#error1").fadeIn();
		return false;
	}
	
	$("#error1").fadeOut();
	return true;
}
//校验邮箱
function checkEmail(email){
	email=$.trim(email);
	if(email.length==0) return false;
	if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
	return pattern.test(email);
}
//下一步
function next(){
	var toEmail = $("#toEmail").val();
	if(!chek()){
		return false;
	}
	var code = $("#code").val();
	if($.trim(code).length==0||$.trim(code).length<5){
		$("#code").focus();
		return false;
	}
	$.ajax({
		type: "post",
		url: jsBasePath+"/user/toResetPwdByMailFind.json",
		data: "toEmail="+toEmail+"&type=findPwdByEmail&code="+code,
		async:false,
		cache:false,
		dataType: "json",
		success: function (data) {
			if(data.result.errorCode == "0") {
				$("#tmpCode").val(data.code);
				$("#phone").val(data.phone);
				document.getElementById("sub_form").submit();
			} else if(data.result.errorCode == "3501"){
				$("#error_code").html("（验证码已过期）").fadeIn().fadeOut(4000);
				return false;
			}else if(data.result.errorCode == "3502"){
				$("#error_code").html("（验证码错误）").fadeIn().fadeOut(4000);
				return false;
			}else{
				return false;
			}
		},
		error: function (msg) {
			return false;
		}
	});
}