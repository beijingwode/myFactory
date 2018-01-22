$(document).ready(function() {
	$("#sendEmail").click(function(){
		$(".loading_sendEmail_password").show();
		$("#sendEmail").hide();
		$.ajax({
		    dataType: 'json',
		    url:wode.domain+"/personal/sendSecurityEmail",
		    success: function(data){
		    	$("#sendEmail").show();
		    	if(data.success){
		    		location.href=wode.domain+"/member/sendEmailSuccessForPassword";
		    	}else{
		    		$(".popup_box_title span").html("发送失败");
					$(".box_message li").html(data.msg);
					$("#boxCancel").hide();
					$("#boxCheck").css("margin-right","-180px");
					$(".box").show();
					$("#boxCheck").click(function(){
						$(".box").hide();
					});
		    	}
		    },
		 	error:function(){
		 		$(".popup_box_title span").html("发送失败");
				$(".box_message li").html("未知错误，邮件发送失败！");
				$("#boxCancel").hide();
				$("#boxCheck").css("margin-right","-180px");
				$(".box").show();
				$("#boxCheck").click(function(){
					$(".box").hide();
				});
			}
		});
	});
});