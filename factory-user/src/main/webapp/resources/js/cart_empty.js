// JavaScript Document
//购物车没有商品页-我的关注
$(function(){
	var page=1;
	var i=5;  //一页放5个	
	//向后按钮
	$("span.next").click(function(){
		var $parent=$(this).parents(".cart_show");	//根据当前点击元素获取到父级元素
		var $v_show=$parent.find(".cart_content_list");  //寻找“内容展示区域”
		var $v_content=$parent.find(".cart_content");  //寻找“内容展示区域”的外围DIV元素
		var v_width=$v_content.width();
		var len=$v_show.find("li").length;
		//只要不是整数就往大的方向取最小的整数
		var page_count=Math.ceil(len/i);
		if(!$v_show.is(":animated")){  //判断“内容展示区域”是否处于正在动画状态
			if(page==page_count){   //判断已经到最后一页了，必须跳转到第一页
				$v_show.animate({left:"0px"},"slow");   //通过改变left值，跳转到第一页
				page=1;	
			}else{
				$v_show.animate({left:"-="+v_width},"slow");   //通过改变left值，达到每次换一页	
				page++;
			}
		}
		
	})
	//向前按钮
	$("span.prev").click(function(){
		var $parent=$(this).parents(".cart_show");	//根据当前点击元素获取到父级元素
		var $v_show=$parent.find(".cart_content_list");  //寻找“内容展示区域”
		var $v_content=$parent.find(".cart_content");  //寻找“内容展示区域”的外围DIV元素
		var v_width=$v_content.width();
		var len=$v_show.find("li").length;
		var page_count=Math.ceil(len/i);
		if(!$v_show.is(":animated")){
			if(page==1){   //判断已经到第一页了，必须跳转到最后一页
				$v_show.animate({left:"-="+v_width*(page_count-1)},"slow");	
				page=page_count;
			}else{
				$v_show.animate({left:"+="+v_width},"slow");	
				page--;
			}
		}
		
	})	
})