$(document).ready(function() {
	$("#receiver").val("");
	$("#address").val("");
	$("#phone").val("");
	$("#default").removeAttr("checked");
	$("#s_submit").attr("onclick","c_save(null)");
});

/**
 * 收货地址-鼠标滑过效果
 */
$(function(){
	$('.receiver_list ul .ads').hover(function(){
		var deletetxt="<div class='r_delete'><img src='../images/close.gif' width='14' height='14' alt='close' onclick='deleteAddress("+$(this).attr('id')+");'></div>";	
		var changetxt="<div class='change_txt'><a href='javascript:void(0);' onclick='modifyAddress("+$(this).attr('id')+","+$(this).val()+");'>修改</a></div>";
		$(this).prepend(deletetxt);
		$(this).prepend(changetxt);
	},function(){
		$(this).find('.r_delete').remove();
		$(this).find('.change_txt').remove();	
	})	
})

/**
 * 收货地址-修改地址弹框
 */
	function modifyAddress(str,aid){
		hidden();
		refresh();
		var cityNo;
		if(China[aid]==undefined){
			cityNo=null;
			$("#cityNo").val(cityNo);
			$("#provinceNo").val(null);
			$("#districtNo").val(null);
			
		}else{
			cityNo = China[aid].father;
			$("#cityNo").val(cityNo);
			$("#provinceNo").val(China[cityNo].father);
			$("#districtNo").val(aid);
		}
			getArea(1,null);
			getArea(2,$("#provinceNo").val());
			getArea(3,$("#cityNo").val());
			$("#province").attr("onchange","getArea(2,$('#province option:selected').val())");
			$("#city").attr("onchange","getArea(3,$('#city option:selected').val())");
			$('.popup_bg').show();
			$('#address_popup').show();
			$('.popup_title span').html("修改地址");
			$('.popup_title label').click(function(){
				$('.popup_bg').hide();
				$('#address_popup').hide();	
			})
			$('#cansel').click(function(){
				$('.popup_bg').hide();
				$('#address_popup').hide();	
			})
			$("#s_submit").removeAttr("onclick");
			$("#s_submit").attr("onclick","c_save("+str+")");
		
			$.ajax({
				dataType: 'json',
				url: '/user/selectAddress',
				data : {"id":str},
				success: function(data){
				    if(data.success){
				    	$("#receiver").val(data.data.name);
						$("#address").val(data.data.address);
						$("#phone").val(data.data.phone);
						if(data.data.send==1)
							$("#default").attr("checked","checked");
						$("#s_submit").attr("onclick","c_save("+str+")");
				    }else{
				    	wode.showBox("错误","获取地址信息失败，请重试",{hideBtn:true});
				    }
				   },
				 error:function(){
					 wode.showBox("错误","未知错误，请联系客服",{hideBtn:true});
				}
			});
		
	}

/**
 * 收货地址-删除
 */
	function deleteAddress(str){
		$.ajax({
		    dataType: 'json',
		    url: '/user/removeAddress',
		    data : {"id":str},
		    success: function(data){
		    	if(data.success){
		    		$("#"+str).remove();
		    		location.reload();
		    	}else{
		    		wode.showBox("错误","删除地址失败，请刷新重试",{hideBtn:true});
		    	}
		    },
		 	error:function(){
		 		wode.showBox("错误","未知错误，请联系客服",{hideBtn:true});
			}
		});
	}

/**
 * 收货地址-增加新地址弹框
 */
$(function(){
	$('.receiver_list ul .bgcolor').click(function(){
		hidden();
		refresh();
		$("#city option[value!=0]").remove();
		$("#district option[value!=0]").remove();
		getArea(1,null);
		$("#province").attr("onchange","getArea(2,$('#province option:selected').val())");
		$("#city").attr("onchange","getArea(3,$('#city option:selected').val())");
		$('.popup_bg').show();
		$('#address_popup').show();
		$("#s_submit").removeAttr("onclick");
		$("#s_submit").attr("onclick","c_save('')");
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#address_popup').hide();	
		})
		$('#cansel').click(function(){
			$('.popup_bg').hide();
			$('#address_popup').hide();	
		})
	})		
})

/**
 * 根据code获取区域
 */
function getArea(str,code) {
    var data;
	if(code!=""){
		if(str==1)
	        data=getArea90(1,null);	
		if(str==2)
	        data=getArea90(2,code);	
		if(str==3)
	        data=getArea90(3,code);	
		
		var option = "";
		if(str==1)
			option += "<option value='0'>省</option>";
		if(str==2)
			option += "<option value='0'>市</option>";
		if(str==3)
			option += "<option value='0'>区/县</option>";
		for (var i = 0; i < data.length; i++) {
		    var item = data[i];
		    if(str==1){
		    	if(item["code"]==$("#provinceNo").val())
		    		option += "<option value=\""+item["code"]+"\" selected='selected'>"+item["name"]+"</option>\n";
		    	else
		    		option += "<option value=\""+item["code"]+"\" title='"+item["name"]+"'>"+item["name"]+"</option>\n";
			}else if(str==2){
				if(item["code"]==$("#cityNo").val())
		    		option += "<option value=\""+item["code"]+"\" selected='selected'>"+item["name"]+"</option>\n";
		    	else
		    		option += "<option value=\""+item["code"]+"\"  title='"+item["name"]+"'>"+item["name"]+"</option>\n";
			}else if(str==3){
				if(item["code"]==$("#districtNo").val())
		    		option += "<option value=\""+item["code"]+"\" selected='selected'>"+item["name"]+"</option>\n";
		    	else
		    		option += "<option value=\""+item["code"]+"\" title='"+item["name"]+"'>"+item["name"]+"</option>\n";
			}
		}
		if(str==1){
			$("#province").html(option);
		}else if(str==2){
			$("#city").html(option);
		}else if(str==3){
			$("#district").html(option);
		}
	}
}

/**
 * 新增地址保存
 */
function c_save(str){
	var send = 0;
    var result = true;
	if($.trim($("#receiver").val()).length<1){
		$(".r_error").html("请输入收货人姓名");
		$(".r_error").fadeIn("slow");
		setTimeout("display('.r_error')",timeout);
		result = false;
	}
	
	if($('#province option:selected').val()==0 ||
			$('#city option:selected').val()==0||
			$('#district option:selected').val()==0){
		$(".s_error").html("请完整选择省、市、区");
		$(".s_error").fadeIn("slow");
		setTimeout("display('.s_error')",timeout);
		result = false;
	}
	
	if($.trim($("#address").val()).length<1){
		$(".a_error").html("请输入详细地址");
		$(".a_error").fadeIn("slow");
		setTimeout("display('.a_error')",timeout);
		result = false;
	}
	
	if(!wode.phone.test($("#phone").val())){
		$(".p_error").html("请填写正确的手机号码");
 		$(".p_error").fadeIn("slow");
 		setTimeout("display('.p_error')",timeout);
 		result = false;
	}
	
	if($("#default").attr("checked")=="checked"){
		send = 1;
	}
	if(result){
		$.ajax({
		    dataType: 'json',
		    url: '/user/addAddress',
		    type : "POST",
		    data : {
		    	"name":$.trim($("#receiver").val()),
		    	"provinceName":$('#province option:selected').html(),
		    	"cityName":$('#city option:selected').html(),
		    	"districtName":$('#district option:selected').html(),
		    	"address":$.trim($("#address").val()),
		    	"phone":$("#phone").val(),
				"send":send,
				"id":str,
				"aid":$('#district option:selected').val()
			},
		    success: function(data){
		    	if(data.success){
		    		location.reload();
		    	}else{
		    		wode.showBox("错误","新增地址失败，请刷新重试",{hideBtn:true});
		    	}
		    },
		 	error:function(){
		 		wode.showBox("错误","未知错误，请联系客服",{hideBtn:true});
			}
		});
	}
}

function display(str){
	$(str).fadeOut("slow");
}
function hidden(){
	$(".r_error").html("");
	$(".s_error").html("");
	$(".a_error").html("");
	$(".p_error").html("");
}
function refresh(){
	$("#provinceNo").val("");
	$("#cityNo").val("");
	$("#districtNo").val("");
	$("#receiver").val("");
	$("#address").val("");
	$("#phone").val("");
	$("#default").removeAttr("checked");
}