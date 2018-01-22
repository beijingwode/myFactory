// JavaScript Document
//伪元素兼容低版本浏览器
$(function(){
	$('.left_list li:last-child').css('border-bottom','none');	
	$('.scr_con:last-child').css('border-right','none');
	$('.add_list li:last-child').css('border-bottom','none');
	$('.sort_list:nth-child(even)').css('border-right','none');
})

//商家后台首页-最新公告切换
$(function(){ 
	$('.notice_menu ul li').hover(function(){
		var index=$('.notice_menu ul li').index(this);
       	$('.notice_list').each(function(i){
			if(i==index){
				$('.notice_list').eq(i).css({display:'block'});
				$('.notice_menu ul li').eq(i).addClass('surr');				
			}else{
				$('.notice_list').eq(i).css({display:'none'});
				$('.notice_menu ul li').eq(i).removeClass('surr');
			}
   		})
  	}) 
})

//触发input
/*function borderColor(){
	if(self['oText'].style.borderColor=='red'){
		self['oText'].style.borderColor = 'gray';
	}else{
		self['oText'].style.borderColor = 'red';
	}
	oTime = setTimeout('borderColor()',400);
}*/

//产品规格
/*$(function(){
	$('.role_btn').click(function(){
		$('.pro_rule_wrap').show();			
	})	
})*/

//上传图片
$(function(){
/*$('.uploadbtn').click(function(){
		$('.uploadimg_box').show();	
	})	
	$('.uploadimg_box h2 img').click(function(){
		$('.uploadimg_box').hide();	
	})
	$('#cansel').click(function(){
		$('.uploadimg_box').hide();		
	})*/
})

//其他服务
$(function(){
	$('.mui_tip1').hover(function(){
		$('.tip1').show();	
	},function(){
		$('.tip1').hide();	
	})
	$('.mui_tip2').hover(function(){
		$('.tip2').show();	
	},function(){
		$('.tip2').hide();	
	})	
})

//商家中心-代售商品管理弹出框
/*$(function(){
	$('.elt_icon').click(function(){
		$('.popup_bg').show();
		$('#shop_popup').show();
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#shop_popup').hide();	
		})
		$('#cansel').click(function(){
			$('.popup_bg').hide();
			$('#shop_popup').hide();	
		})
	})
	$('.approve').click(function(){
		$('.popup_bg').show();
		$('#shop_popup_true').show();
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#shop_popup_true').hide();	
		})
		$('#tcansel').click(function(){
			$('.popup_bg').hide();
			$('#shop_popup_true').hide();	
		})
	})	
	$('.pro_approve p').click(function(){
		$('.popup_bg').show();
		$('#shop_popup_fail').show();
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#shop_popup_fail').hide();	
		})
		$('#tcansel').click(function(){
			$('.popup_bg').hide();
			$('#shop_popup_fail').hide();	
		})
	})
	$('.o-delete').click(function(){
		$('.popup_bg').show();
		$('#shop_popup_delete').show();	
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#shop_popup_delete').hide();	
		})
		$('#dcansel').click(function(){
			$('.popup_bg').hide();
			$('#shop_popup_delete').hide();	
		})
	})	
	$('.p_delete').click(function(){
		$('.popup_bg').show();
		$('#shop_popup_alldelete').show();	
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#shop_popup_alldelete').hide();	
		})
		$('#alldcansel').click(function(){
			$('.popup_bg').hide();
			$('#shop_popup_alldelete').hide();	
		})
	})	
	
	$('.adr').click(function(){
		$('.popup_bg').show();
		$('#change_deliver').show();	
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#change_deliver').hide();	
		})
	})	
	$('.cadre').click(function(){
		$('.popup_bg').show();
		$('#change_address').show();	
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#change_address').hide();	
		})
	})	
	
})*/

//商家入驻-上传资质
$(function(){ 
	$('.nw_radio_b input').click(function(){
		var index=$('.nw_radio_b input').index(this);
       	$('.chage').each(function(i){
			if(i==index){
				$('.chage').eq(i).css({display:'block'});
				$('.nw_radio_b input').eq(i).addClass('surr');				
			}else{
				$('.chage').eq(i).css({display:'none'});
				$('.nw_radio_b input').eq(i).removeClass('surr');
			}
   		})
  	}) 
})

//我的店铺-商品归类-全部商品弹出框
$(function(){
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#sort_popup1').hide();
		})
		$('#tcansel').click(function(){
			$('.popup_bg').hide();
			$('#sort_popup1').hide();
		})
})

//我的店铺-基本信息-弹框
$(function(){
	//发起修改弹框
	$('#bootchange').click(function(){
		$('.popup_bg').show();
		$('#change_popup').show();
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#change_popup').hide();		
		})	
		$('#c-cansel').click(function(){
			$('.popup_bg').hide();
			$('#change_popup').hide();	
		})
	})	
	//申请进度弹框
	$('#applyprocess').click(function(){
		$('.popup_bg').show();
		$('#apply_popup').show();
		$('.popup_title label').click(function(){
			$('.popup_bg').hide();
			$('#apply_popup').hide();		
		})
	})		
})

//商家中心-配送管理-发货切换
$(function(){ 
	$('.goods_dh ul li').click(function(){
		var index=$('.goods_dh ul li').index(this);
       	$('.goods_cont').each(function(i){
			if(i==index){
				$('.goods_cont').eq(i).css({display:'block'});
				$('.goods_dh ul li').eq(i).addClass('curr');				
			}else{
				$('.goods_cont').eq(i).css({display:'none'});
				$('.goods_dh ul li').eq(i).removeClass('curr');
			}
   		})
  	}) 
})

//订单管理-已售出的商品-批量发货操作
$(function(){
	$('.change_a a').click(function(){
		var $ul=$(this).closest(".down_con").siblings(".sale_infomation_list");
		$ul.toggle();
		if($ul.is(':hidden')){
			$(this).addClass('a1');	
		}else{
			$(this).removeClass('a1');		
		}	
	})	
})

