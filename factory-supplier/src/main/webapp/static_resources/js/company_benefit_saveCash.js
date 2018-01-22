
$(document).ready(function(){
	//加载页面，控制左边的菜单
	$("#save_cash").addClass("curr");
	//进入页面,储值金额输入框获取焦点
	$("#money").focus();
	
	//输入框获取光标
	$(".c-input").live("focus",function(){
		$(this).addClass("p-red");
	});
	//输入框失去光标
	$(".c-input").live("blur",function(){
		$(this).removeClass("p-red");
	});
	
	$("#money").keyup(function(){
		var money =$("#money").val();
		inutNumber_double(this);
		if($("#money").val().length==0){
			$("#p_money").hide();
		}else{
			$("#show_money").html($("#money").val()+"元");
			$("#p_money").show();
		}
		
	});

	$("#next").click(function(){
		var mon = $("#money").val();
		if($.trim(mon).length==0){
			$("#saveCash_error").text("不能为空");
			$("#saveCash_error").show();
			setTimeout("gotomain()",2000);
			return ;
		}else{
			//根据小数点分割输入的金额，并取其小数点的个数。
			var mon_length = mon.split(".").length-1;
			if(mon_length>1){
				$("#saveCash_error").text("请输入整数或者小数数值");
				$("#saveCash_error").show();
				setTimeout("gotomain()",2000);
				return ;
			}else if(parseFloat(mon)<1){
				$("#saveCash_error").text("输入的数值必须大于1");
				$("#saveCash_error").show();
				setTimeout("gotomain()",2000);
				return ;
			}else if(mon.indexOf(".")!=-1){
				if(mon.substr(mon.indexOf(".")+1,mon.length).length>2){
					$("#saveCash_error").text("小数点后面不能超过2位");
					$("#saveCash_error").show();
					setTimeout("gotomain()",2000);
					return ;
				}
			}
			/* var mon_frist = mon.substring(0,1);
			if(mon_frist==0){
				$("#saveCash_error").text("参数开头不能为0");
				$("#saveCash_error").show();
				setTimeout("gotomain()",2000);
				return ;
			} */
		}
		
		/* if(inputVerify()){ */
			location.href=jsBasePath + "/company/payment/toPay?money="+mon+"&zhifu="+$('input[name="zhifu"]:checked').val();
		/* } */
	});
	
	$('.allsend-suceess-close').click(function(){
		$('.popup_bg').hide();
		$('#applay').hide();
	});
	if(jsSuccess =="1"){
		$(".pop-cont p").html("充值成功！");
		$("#applay").show();
		$('.popup_bg').show();
	}else if( jsSuccess =="0"){
		$(".pop-cont p").html( jsError );
		$("#applay").show();
		$('.popup_bg').show();
	}

});

function gotomain(){
	$("#saveCash_error").fadeOut();
}

//输入验证
function inputVerify(){
	var money=$("#money").val();
	if(money == 0.01){
		return true ;
	}
	if(!(/^(\+|-)?\d+$/.test( money ))){
	    $("#money").addClass("p-red");
	    return false;  
    }else{
    	$("#money").removeClass("p-red");	
    }
	
	return true ;
}
