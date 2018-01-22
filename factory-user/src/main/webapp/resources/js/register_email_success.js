$(document).ready(function() {
	var time = 3000;
	if($("#result").val()=='1' || $("#type").val()=='nullUser'){
		setTimeout("redirect('/index.html')",time);
	}
	if($("#type").val()=='abate'){
		setTimeout("redirect('/index.html')",time);
	}
	/**
	 * 发送注册激活邮件
	 */
	$("#sendEmailAgainForRegister").click(function(){
		$(".loading_sendEmail_s").show();
		$.ajax({
		    dataType: 'json',
		    url:"/personal/sendRegisterEmail?type=register&userId="+$("#userId").val(),
		    success: function(data){
		    	$(".sendEmailResult").html(data.msg);
		    	$(".sendEmailResult").fadeIn("slow");
				setTimeout("display()",time);
				$(".loading_sendEmail_s").hide();
		    },
		});
	});
});

function redirect(str){
	location.href=str;
}

function display(){
	$(".sendEmailResult").fadeOut("slow");
}