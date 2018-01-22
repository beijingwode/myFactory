$(document).ready(function() {
	/**
	 * 个人中心密码修改
	 */
	$(".btnnext").click(function(){
		result = true;
		if(!wode.register.test($("#password").val())){
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
			$("#post_param").html('<input type="hidden" name="userName" value="'+$("#phone").val() +'">'
					+ '<input type="hidden" name="code" value="'+$("#code").val() +'">'
					+ '<input type="hidden" name="password" value="'+$("#password").val() +'">'
					+ '<input type="hidden" name="userCom" value="'+wode.comeFrom +'">'
					+ '<input type="hidden" name="returnUrl" value="'+wode.domain+'/user/writeUserResult?type=security">');
			user_from.action = wode.userDomain+'/updatePasswordByName';
			user_from.submit();
		}
	});
});

function securityResult(data){
	if(data.success){
    	location.href=wode.domain+"/member/modifySuccess";
	}else{
	    $("#rePasswordError").html(data.msg);
	    $("#rePasswordError").fadeIn("slow");
		setTimeout("display()",timeout);
	}
}
function display(){
	$(".alter_error").fadeOut("slow");
}