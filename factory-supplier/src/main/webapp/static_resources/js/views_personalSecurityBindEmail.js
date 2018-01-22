
	$(document).ready(function(){
		selectedHeaderLi("sy_header");
		
		/**
		 * 发送绑定确认邮件
		 */
		$("#sendEmailForBind").click(function(){
			var result = true;
			if(!checkEmail($("#email").val())){
				$(".sendEmailResult").html("请填写正确的邮箱");
		 		$(".sendEmailResult").fadeIn("slow");
		 		setTimeout("display()",timeout);
		 		result = false;
			}
			if(result){
				$("#loading").show();
				$.ajax({
				    dataType: 'json',
				    url:jsBasePath+"/user/toBindEmail?type=bindMail&userId="+$("#userId").val()+"&toEmail="+$("#email").val(),
				    success: function(data){
						$("#loading").hide();
					    $(".sendEmailResult").html(data.data.message);
					    $(".sendEmailResult").fadeIn("slow");
						setTimeout("display()",3000);
				    },
				 	error:function(){
					    alert("未知错误，请联系客服");
					}
				});
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

	/**
	 * 重新发送绑定确认邮件
	 */
	$("#sendEmailAgainForBind").click(function(){
		$("#sendEmailForBind").click();
	});
	
	function display(){
		$(".sendEmailResult").fadeOut("slow");
	}
