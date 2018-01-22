// JavaScript Document

//伪元素兼容低版本浏览器
$(function(){
	$('.shopping_label_list ul li:last-child').css('border-bottom','none');	
	$('.product_sort ul li:last-child').css('border-bottom','none');
	$('.menu_one_list:last-child').css('border-bottom','none');
})

//全部商品分类下拉菜单
$(function(){
	
	
	
})

//热销排行榜切换
$(function(){
	$('.shopping_pro_list ul li').live("hover",function(){
		var index=$('.shopping_pro_list ul li').index(this);
       	$('.shopping_hotpro').each(function(i){
			if(i==index){
				$('.shopping_hotpro').eq(i).css({display:'block'});
				$('.shopping_pro_list ul li').eq(i).addClass('surr');				
			}else{
				$('.shopping_hotpro').eq(i).css({display:'none'});
				$('.shopping_pro_list ul li').eq(i).removeClass('surr');
			}
   		})
  	}) 
})

//banner 二级菜单
$(function(){ 
	$('.shopping_list_menu .lt').hover(function(){ 
		$(this).find('.hcolor').addClass("hover");
		$(this).find('.hcolor').css('color','#ff6161');
		$(this).find('.shopping_menu_layer').show();
	},function(){ 
		$(this).find('.hcolor').removeClass("hover");
		$(this).find('.hcolor').css('color','#fff');
		$(this).find('.shopping_menu_layer').hide();
	}); 
}); 


//商品详情-加入购物车弹出框
$(function(){
	$('.item_btn_01').click(function(){
		$('.popup_bg').show();
		$('.shopcar_popup').show();	
		$('.close_btn').click(function(){
			$('.popup_bg').hide();
			$('.shopcar_popup').hide();	
		})
	})	
})
//商品详情-登录弹出框
$(function(){
	$('.item_btn_02').click(function(){
		$('.popup_bg').show();
		$('.login_popup').show();	
		$('.close_btn').click(function(){
			$('.popup_bg').hide();
			$('.login_popup').hide();	
		})
	})	
})

//商品详情-商品介绍切换
$(function(){ 
	$('.pro_tab_trigger ul li').click(function(){
		var index=$('.pro_tab_trigger ul li').index(this);
       	$('.pro_panel').each(function(i){
			if(i==index){
				$('.pro_panel').eq(i).css({display:'block'});
				$('.pro_tab_trigger ul li').eq(i).addClass('current');				
			}else{
				$('.pro_panel').eq(i).css({display:'none'});
				$('.pro_tab_trigger ul li').eq(i).removeClass('current');
			}
   		})
  	}) 
})

//商品详情页-图片切换带缩略图片滚动切换
$(function(){
	
	//点击小图切换大图
	$("#thumbnail li a").hover(function(){
		$(".zoompic img").hide().attr({ "src": $(this).attr("href"), "title": $("> img", this).attr("title") });
		$("#thumbnail li.current").removeClass("current");
		$(this).parents("li").addClass("current");
		return false;
	});
	$(".zoompic>img").load(function(){
		$(".zoompic>img:hidden").show();
	});
	
	//小图片左右滚动
	var $slider = $('.slider ul');
	var $slider_child_l = $('.slider ul li').length;
	var $slider_width = $('.slider ul li').width();
	$slider.width($slider_child_l * $slider_width);
	
	var slider_count = 0;
	
	if ($slider_child_l < 5) {
		$('#btn-right').css({cursor: 'auto'});
		$('#btn-right').removeClass("dasabled");
	}
	
	$('#btn-right').click(function() {
		if ($slider_child_l < 5 || slider_count >= $slider_child_l - 5) {
			return false;
		}
		
		slider_count++;
		$slider.animate({left: '-=' + $slider_width + 'px'}, 'fast');
		slider_pic();
	});
	
	$('#btn-left').click(function() {
		if (slider_count <= 0) {
			return false;
		}
		slider_count--;
		$slider.animate({left: '+=' + $slider_width + 'px'}, 'fast');
		slider_pic();
	});
	
	function slider_pic() {
		if (slider_count >= $slider_child_l - 5) {
			$('#btn-right').css({cursor: 'auto'});
			$('#btn-right').addClass("dasabled");
		}
		else if (slider_count > 0 && slider_count <= $slider_child_l - 5) {
			$('#btn-left').css({cursor: 'pointer'});
			$('#btn-left').removeClass("dasabled");
			$('#btn-right').css({cursor: 'pointer'});
			$('#btn-right').removeClass("dasabled");
		}
		else if (slider_count <= 0) {
			$('#btn-left').css({cursor: 'auto'});
			$('#btn-left').addClass("dasabled");
		}
	}
	
});

//商品详情页评价切换
/*$(function(){ 
	$('.p_inner ul li').click(function(){
		var index=$('.p_inner ul li').index(this);
       	$('.appraise_list').each(function(i){
			if(i==index){
				$('.appraise_list').eq(i).css({display:'block'});
				$('.p_inner ul li').eq(i).addClass('curr');				
			}else{
				$('.appraise_list').eq(i).css({display:'none'});
				$('.p_inner ul li').eq(i).removeClass('curr');
			}
   		})
  	}) 
})*/

//首页顶部-我是商家
$(function(){
	$('.roof_right_list .business').click(function(e){
		$('.business_list').toggle();
		e.stopPropagation();
		$('.roof_right_list .business a').css('background','#fff url(images/factory_img.png) no-repeat -126px -26px');
		$('body').click(function(){
			$('.business_list').hide();
			$('.roof_right_list .business a').css('background','#fff url(images/factory_img.png) no-repeat -126px 0px');
		})		 
	})
   
	$('.business_list li').click(function(){
		$('.business a').text(($(this).text()));  
	})   
})

//首页-购物车下拉框
$(function(){
	$('.shoppingcar').hover(function(){
		$('.shoppingcar_cont').show();	
		$('.shoppingcar_cont').hover(function(){
			$('.shoppingcar_cont').show();		
		},function(){
			$('.shoppingcar_cont').hide();		
		})
	},function(){
		$('.shoppingcar_cont').hide();		
	})	
})


//商品基础分类页-左侧下拉
$(function(){
	/*$(".lt a").click(function(){				
		if($(this).next(".downlist").is(":hidden")){
			$(this).next(".downlist").show();
			$(this).parent("li").addClass("after").siblings("li").removeClass("after");			
			if($(this).parent("li").siblings("li").children(".downlist").is(":visible")){
				$(this).parent("li").siblings("li").find(".downlist").hide();
			}			
			return false;
			}else{				
				$(this).next(".downlist").hide();	
				$(this).parent("li").removeClass("after");			
				$(this).next(".downlist").children("li").find(".downlist").hide();				
				//return false;			
		}
	});	*/
	$(".lt a").click(function(){
		if($(this).next(".downlist").is(":hidden")){
			$(this).next(".downlist").show();
			$(this).parent("li").addClass("after");	
		}else{
			$(this).next(".downlist").hide();	
			$(this).parent("li").removeClass("after");
		}
	})
})

//商品基础分类页-商品筛选
$(function(){
	$('.extra_btn').click(function(){
		$('#band_one').toggle();
		$(this).addClass('offed');	
		if($('#band_one').is(':hidden')){
			$(this).removeClass('offed');		
		}
	})	
})

//商品基础分类页-列表鼠标滑过效果
$(function(){
	$('.at').hover(function(){
		$(this).addClass('hover').siblings().removeClass('hover');	
	},function(){	
		$(this).removeClass('hover');	
	})	
})

//商品基础分类页-加入购物车
$(function(){ 
	$('.all_product_list ul li .p_btn_01').click(function(){
		var index=$('.all_product_list ul li .p_btn_01').index(this);
       	$('.add_shopcar').each(function(i){
			if(i==index){
				$('.add_shopcar').eq(i).css({display:'block'});	
				$('.close_btn').click(function(){
					$('.add_shopcar').eq(i).css({display:'none'});		
				})		
			}else{
				$('.add_shopcar').eq(i).css({display:'none'});
			}
   		})
  	})
})

//购物车-订单支付
$(function(){ 
	$('.tooltab li').click(function(){
		var index=$('.tooltab li').index(this);
       	$('.paycont').each(function(i){
			if(i==index){
				$('.paycont').eq(i).css({display:'block'});
				$('.tooltab li').eq(i).addClass('fronttab');			
			}else{
				$('.paycont').eq(i).css({display:'none'});
				$('.tooltab li').eq(i).removeClass('fronttab');
			}
   		})
  	}) 
})

$(function(){
	$('#pay_detail').click(function(){
		$('.mergeorderwrap').toggle();	
	})	
})


//个人中心-买家申请退款
$(function(){
	$('.flow_btn').click(function(){
		if($('.wl_xx').hasClass('D_height')){
			$('.wl_xx').removeClass('D_height');
			$(this).addClass('offed');	
		}else{
			$('.wl_xx').addClass('D_height');
			$(this).removeClass('offed');	
		}
	})	
})

//我的评价-切换 || 我的维权-切换
$(function(){ 
	$('.appraise_nav ul li').click(function(){
		var index=$('.appraise_nav ul li').index(this);
       	$('.appraise_list_wrap').each(function(i){
			if(i==index){
				$('.appraise_list_wrap').eq(i).css({display:'block'});
				$('.appraise_nav ul li').eq(i).addClass('current');				
			}else{
				$('.appraise_list_wrap').eq(i).css({display:'none'});
				$('.appraise_nav ul li').eq(i).removeClass('current');
			}
   		})
  	}) 
})

//我的评价-评价弹框
$(function(){
	$('.A_cont li .A_btn').click(function(){
		$('.appraise_box').show();	
		$('.click_btn').click(function(){
			$('.appraise_box').hide();	
		})
	})	
})

//我的评价-评价弹框-星级评价
$(function(){
    $('.star_ul').each(function(index){
        $(this).find('a').click(function(){
            $(this).addClass('active-star');
            $($('.s_result')[index]).html($(this).attr('title'));
            $(this).parent().siblings().find("a").removeClass("active-star");
            $(this).parent().parent().parent().find('input').val($(this).attr("score"));
        });
    })
})

//个人中心-账户管理-安全设置-收货地址-鼠标滑过效果
$(function(){
	$('.receiver_list ul .ads').hover(function(){
		var deletetxt="<div class='r_delete'><img src='images/close.gif' width='14' height='14' alt='close'></div>";	
		var changetxt="<div class='change_txt'><a href='#'>修改</a></div>";
		$(this).prepend(deletetxt);
		$(this).prepend(changetxt);
	},function(){
		$(this).find('.r_delete').remove();
		$(this).find('.change_txt').remove();	
	})	
})
//个人中心-账户管理-安全设置-收货地址-增加新地址弹框
$(function(){
	$('.receiver_list ul .bgcolor').click(function(){
		$('.popup_bg').show();
		$('#address_popup').show();	
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

//店铺-鼠标滑过效果
$(function(){
	$(".B_text").hover(function(){
		$(".Brand_js").show();
		$(".Brand_js").hover(function(){
			$(this).show();	
		},function(){
			$(this).hide();	
		})	
	},function(){
		$(".Brand_js").hide();		
	})	
})
