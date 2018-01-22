$(document).ready(function() {
	/**
	 * 根据邮箱判定前往邮箱地址
	 */
	$(".Stishi").each(function(){
		if($("#email").val().indexOf("@qq.com")>0){
			$(".btngoemail a").attr("href","http://mail.qq.com");
			$(".btngoemail a").attr("target","_Blank");
		}
		if($("#email").val().indexOf("@163.com")>0){
			$(".btngoemail a").attr("href","http://mail.163.com");
			$(".btngoemail a").attr("target","_Blank");
		}
		if($("#email").val().indexOf("@126.com")>0){
			$(".btngoemail a").attr("href","http://mail.126.com");
			$(".btngoemail a").attr("target","_Blank");
		}
	});
	
	/**
	 * 发送找回密码确认邮件
	 */
	$(".Semail a").click(function(){
		sendMail();
	});
})

function sendMail(){
	window.location.reload();
}

function display(){
	$(".sendEmailResult").fadeOut("slow");
	$(".Semail a").fadeIn("slow");
}