$(document).ready(function() {
	var timeout = 3000;
	
	/**
	 * 根据邮箱判定前往邮箱地址
	 */
	$(".btngoemail").each(function(){
		$(".goemailbtn").css("background","#919191");
		$(".goemailbtn a").removeAttr("target");
		$(".btngoemail").css("background","#919191");
		$(".btngoemail a").removeAttr("target");
		if($("#email").val().indexOf("@qq.com")>0){
			$(".btngoemail a").attr("href","http://mail.qq.com");
			$(".btngoemail a").attr("target","_Blank");
			$(".goemailbtn").css("background","#ff6161");
			return;
		}
		if($("#email").val().indexOf("@163.com")>0){
			$(".btngoemail a").attr("href","http://mail.163.com");
			$(".btngoemail a").attr("target","_Blank");
			$(".goemailbtn").css("background","#ff6161");
			return;
		}
		if($("#email").val().indexOf("@126.com")>0){
			$(".btngoemail a").attr("href","http://mail.126.com");
			$(".btngoemail a").attr("target","_Blank");
			$(".goemailbtn").css("background","#ff6161");
			return;
		}
		$(".goemailbtn a").attr("href","#");
		$(".btngoemail a").attr("href","#");
	})
	
	$("#email").focusout(function(){
		if(wode.checkEmail($("#email").val())){
			$(".goemailbtn a").attr("href","#");
			$(".goemailbtn a").removeAttr("target");
			$(".goemailbtn").css("background","#919191");
			var str = $("#email").val().substr($("#email").val().indexOf("@")+1);
			if($("#email").val().indexOf("@qq.com")>0){
				$(".goemailbtn a").attr("href","http://mail.qq.com");
				$(".goemailbtn a").attr("target","_Blank");
				$(".goemailbtn").css("background","#ff6161");
			}
			if($("#email").val().indexOf("@163.com")>0){
				$(".goemailbtn a").attr("href","http://mail.163.com");
				$(".goemailbtn a").attr("target","_Blank");
				$(".goemailbtn").css("background","#ff6161");
			}
			if($("#email").val().indexOf("@126.com")>0){
				$(".goemailbtn a").attr("href","http://mail.126.com");
				$(".goemailbtn a").attr("target","_Blank");
				$(".goemailbtn").css("background","#ff6161");
			}
		}else{
			$(".goemailbtn a").removeAttr("target");
			$(".goemailbtn a").attr("href","#");
			$(".goemailbtn").css("background","#919191");
			$(".sendEmailResult").html("请填写正确的邮箱");
	 		$(".sendEmailResult").fadeIn("slow");
	 		setTimeout("display()",timeout);
		}
	});
	/**
	 * 发送修改密码确认邮件
	 */
	$("#sendEmailAgainForModify").click(function(){
		$.ajax({
		    dataType: 'json',
		    url:wode.domain+"/personal/sendSecurityEmail?userId="+$("#userId").val(),
		    success: function(data){
		    	$(".sendEmailResult").html(data.msg);
		    	$(".sendEmailResult").fadeIn("slow");
				setTimeout("display()",timeout);
		    },
		 	error:function(){
			    alert("未知错误，请联系客服");
			}
		});
	});
	
	/**
	 * 发送注册激活邮件
	 */
	$("#sendEmailAgainForRegister").click(function(){
		$.ajax({
		    dataType: 'json',
		    url:wode.domain+"/personal/sendRegisterEmail?type=register&userId="+$("#userId").val(),
		    success: function(data){
		    	$(".sendEmailResult").html(data.msg);
		    	$(".sendEmailResult").fadeIn("slow");
				setTimeout("display()",timeout);
		    },
		 	error:function(){
			    alert("未知错误，请联系客服");
			}
		});
	});
	
	/**
	 * 发送绑定确认邮件
	 */
	$("#sendEmailForBind").click(function(){
		var result = true;
		if(!wode.checkEmail($("#email").val())){
			$(".sendEmailResult").html("请填写正确的邮箱");
	 		$(".sendEmailResult").fadeIn("slow");
	 		setTimeout("display()",timeout);
	 		result = false;
		}
		if(result){
			$("#loading").show();
			$.ajax({
			    dataType: 'json',
			    url:wode.domain+"/personal/sendRegisterEmail?type=bind&userId="+$("#userId").val()+"&email="+$("#email").val(),
			    success: function(data){
					$("#loading").hide();
				    $(".sendEmailResult").html(data.msg);
				    $(".sendEmailResult").fadeIn("slow");
					setTimeout("display()",timeout);
			    },
			 	error:function(){
				    alert("未知错误，请联系客服");
				}
			});
		}
	});
	
	/**
	 * 重新发送绑定确认邮件
	 */
	$("#sendEmailAgainForBind").click(function(){
		$("#sendEmailForBind").click();
	});
});

function display(){
	$(".sendEmailResult").fadeOut("slow");
}