$(function(){
	//加载页面，控制左边的菜单
	$("#stamps").parent().parent().prev().addClass("active");
	$("#stamps").parent().parent().attr("style","display: block;");
	$("#stamps").parent().addClass("active");
	$("#stamps").addClass("active");
});
	$(function(){
			//加载页面，控制左边的菜单
			$("#stamps").addClass("curr");
			if(jsResult=="1"){
				$(".pop-cont p").html("划拨成功！");
				$("#insufficient_balance").show();
				$(".popup_bg").show();
			}else if(jsResult=="0"){
				$(".pop-cont p").html("划拨失败！请重试");
				$("#insufficient_balance").show();
				$(".popup_bg").show();
			}
	}); 

	//输入后检查
	function inutNumber(obj) {
		obj.value=obj.value.replace(/\D/g,'');
		calculator();
	}
	
	function gotomain(param){
		$(".error").fadeOut();
		$("#"+param).next().text("");
		$("#"+param).attr("id","");
	}
	function gotomain1(param){
		$(".error").fadeOut();
		$("#"+param).next().text("");
		$("#"+param).attr("id","");
	}
	//计算
	function calculator() {
		//隐藏域中的企业id
		var ent_id = document.getElementsByName("ent_id");
		var ary = document.getElementsByName("abs");
		//打算划拨多少额度
		var sum=0;
		//当前划拨总额度
		var can=document.getElementById("can").value;
		for(var i=0;i<ary.length;i++){
			if(!isNaN(ary[i].value) && "" != ary[i].value) {
				sum +=parseInt(ary[i].value);
			}
			//截取营业额第一个数字
			var ary_frist = ary[i].value.substring(0,1);
			if(ary_frist==0&&ary_frist.length>0){
				//以企业的id+frist作为id值
				var id_frist = ent_id[i].value+"frist";
				//为标签设置id
				ary[i].id=id_frist;
				$("#"+id_frist).next().text("开头不能为0");
				$('.error').show();
				setTimeout(function(){
					gotomain1(id_frist);
					},2000);
			}
			//剩余额度小于0，清空输入框
			if((can-sum)<0&&ary[i].value!=""){
				//以企业的id作为id值
				var id_frist = ent_id[i].value+"";
				//为标签设置id
				ary[i].id=id_frist;
				$("#"+ent_id[i].value).next().text("划拨的福利有误！！");
				$('.error').show();
				var v = ent_id[i].value;
				setTimeout(function(){
					gotomain(id_frist);
					},2000);
			}
		}
		//只有划拨福利错误的时候才会触发
		/* if(flag){
			$("#transfer_welfare_error").next().text("划拨的福利有误！！");
			$('.error').show();
			setTimeout("gotomain()",1000);
		} */
		residue = (can-sum);
		//当前划拨额度
		$("#transfe").text(sum + "元");
		//剩余额度
		$("#residue").text(residue + "元");
		
		//没有划拨，或者余额不足，按钮不能点
		if(sum == 0 || residue<0) {
			document.getElementById("stamps_sub_but").className = 'cansel-btn';
		} else {
			document.getElementById("stamps_sub_but").className = 'true-btn';
		}
	}
	

	$(document).ready(function(){
		//给企业划拨福利的输入框获取光标
		$(".welfare_input").focus(function(){
			$(this).attr("class","p-red");
		});
		//给企业划拨福利的输入框失去光标
		$(".welfare_input").blur(function(){
			$(this).removeClass("p-red");
			$(this).attr("class","welfare_input");
		});
		
		
		//取消，将清空输入框
		$("#stamps_cancel").click(function(){
			$(".welfare_input").val("");
			 calculator();
		});
		var btn = document.getElementById("stamps_sub_but");		
		//余额判断
		if($("#can").val() == 0 || btn==null) {
			var o = $(".st-btn");
			o.removeClass();
			o.addClass('st-btn-disabled');
		} else {
			//显示划拨
			$(".st-btn").click(function(){
				$(".st-cont").fadeIn();
			});
		}
		
		//确定，计算划拨额度
		$("#stamps_sub_but").click(function(){
			if('true-btn' == document.getElementById("stamps_sub_but").className){
				//剩余的
				$("#transferForm").submit();
			}
			
		});
		//关闭余额不足弹窗
		$("#insufficient_balance-close").click(function(){
			$("#insufficient_balance").hide();
			$('.popup_bg').hide();
		});
	});
	