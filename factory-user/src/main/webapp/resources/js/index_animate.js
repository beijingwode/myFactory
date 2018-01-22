$(function() {

	//搜索框吸顶

	var a = $('.search_fixed'), b = a.offset();

	$(document).on('scroll', function() {
		var c = $(document).scrollTop();

		if (b.top <= c) {
			a.addClass("fixed");
		} else {
			a.removeClass("fixed");
		}
	})

	//搜索框右侧交互效果
	$(".search_fixed_con .ul2 li .fff_show").each(function(index) {
		$(this).hover(function() {//先把所有的隐藏掉
			//$(".box").addClass("dis");
			//$(".box:eq("+index+")").removeClass("dis");//在把对应的索引的显示				
			$(".search_fixed_con .ul2 li .fff_show").removeClass("a2");
			$(".search_fixed_con .ul2 li .fff_show").addClass("a1");
			$(this).addClass("a2");
		})
	});
	$('.search_fixed_con .ul2 li ').hover(function() {
		var timer = $(this).data("timer");
		var lit = $(this);
		clearTimeout(timer);
		timer = setTimeout(function() {
			lit.find('.box').fadeIn(500);
		}, 300);
		$(this).data("timer", timer);
	}, function() {
		var timer = $(this).data("timer");
		clearTimeout(timer);
		$(this).find('.box').fadeOut("fast");
		$(".search_fixed_con .ul2 li .fff_show").addClass("a2");
	});

	//进度条	
	function animate() {
		$(".bar_con").each(function(i, item) {
			var a = parseInt($(item).attr("width"));
			$(item).animate({
				width : a + "%"
			}, 1000);
		});
	}
	;
	animate();

	//自家专享
	$(".exc_box").slide({
		titCell : "",
		mainCell : ".exc_con ul",
		autoPage : true,
		effect : "leftLoop",
		autoPlay : false,
		vis : 3,
		scroll : 3,
	//delayTime:4000
	});

	$(".exc_box .exc_con ul li").mouseover(function() {
		$(this).find("span").show();
		$(this).find(".exc_pro_details").show();
	});
	$(".exc_box .exc_con ul li").mouseout(function() {
		$(this).find("span").hide();
		$(this).find(".exc_pro_details").hide();
	});

	//厂商联盟
	$(".alliance").slide({
		titCell : "",
		mainCell : ".alliance_con ul",
		autoPage : true,
		effect : "leftLoop",
		autoPlay : false,
		vis : 6,
		scroll : 6,
	//delayTime:4000
	});

	$(".btns1 .btn_left").mouseover(function() {
		$(this).css({
			"background" : "url(images/btn_icon_left2.png) no-repeat",
		})
	});
	$(".btns1 .btn_left").mouseout(function() {
		$(this).css({
			"background" : "url(images/btn_icon_left1.png) no-repeat",
		})
	});
	$(".btns1 .btn_right").mouseover(function() {
		$(this).css({
			"background" : "url(images/btn_icon_right2.png) no-repeat",
		})
	});
	$(".btns1 .btn_right").mouseout(function() {
		$(this).css({
			"background" : "url(images/btn_icon_right1.png) no-repeat",
		})
	});

	//现金券专区交互
	$(".main_con ul li").mouseover(function() {
		$(this).find("dt").find("span").show();
		$(this).find("dt").find("p").show();
		$(this).addClass("hover");
	});
	$(".main_con ul li").mouseout(function() {
		$(this).find("dt").find("span").hide();
		$(this).find("dt").find("p").hide();
		$(this).removeClass("hover");
	});

	//我的券显示效果（页面滑动到现金券专区时，我的券显示）
	$(window).scroll(function() {
		var h = $(this).scrollTop();//获得滚动条距top的高度
		//alert(h); 
		if (h > 1200) {
			$(".my_stamps").fadeIn();
		} else {
			$(".my_stamps").fadeOut();
		}
	});

	//显示到相应区域对应的我的券变红

	var if1_height = $("#pro_if1").offset().top - 210;
	var if2_height = $("#pro_if2").offset().top - 210;
	var if3_height = $("#pro_if3").offset().top - 210;
	var if4_height = $("#pro_if4").offset().top - 210;
	var if5_height = $("#pro_if5").offset().top - 210;
	$(window).scroll(function() {
		var this_scrollTop = $(this).scrollTop();

		if (this_scrollTop > if5_height) {
			$(".my_stamps ul li").removeClass("col");
			$(".my_stamps ul li.li1").addClass("col");
			$(".my_stamps ul li.li2").addClass("col");
		} else if (this_scrollTop > if4_height) {
			$(".my_stamps ul li").removeClass("col");
			$(".my_stamps ul li.li1").addClass("col");
			$(".my_stamps ul li.li2").addClass("col");
		} else if (this_scrollTop > if3_height) {
			$(".my_stamps ul li").removeClass("col");
			$(".my_stamps ul li.li3").addClass("col");
		} else if (this_scrollTop > if2_height) {
			$(".my_stamps ul li").removeClass("col");
			$(".my_stamps ul li.li1").addClass("col");
			$(".my_stamps ul li.li2").addClass("col");
		} else if (this_scrollTop > if1_height) {
			$(".my_stamps ul li").removeClass("col");
			$(".my_stamps ul li.li1").addClass("col");
			$(".my_stamps ul li.li2").addClass("col");
		}

	});

    shopping();
	banner();
});

function shopping(){
	$.ajax({
		   type: "POST",
		   url: "/user/getCartNum",
		   success: function(ret){
		   		if(ret.success){
		   			var obj = ret.data;
		   			$(".shopping_amount").text(obj.num);
		   			if(obj.num == 0){
		   				$(".buycar_con").html("<div class='tit'>购物车中还没有商品，赶紧选购吧！</div>");
		   				return;
		   			}
		   			var cartItems = obj.cartItems;
		   			var cartInfo = "";
		   			var totalPrice = 0;
		   			for(var i = 0; i < cartItems.length; i++){
		   				var cartItem = cartItems[i];
                        
		   				cartInfo += ' <dl>';
		   				cartInfo += '     <dt><a href="/'+ cartItem.productId +'.html"><img src="'+cartItem.imagePath+'" width="50" height="50" /></a></dt>';
		   				cartInfo += '     <dd class="dd1"><a href="/'+ cartItem.productId +'.html">'+cartItem.productName+'</a></dd>';
		   				cartInfo += '     <dd class="dd2">';
		   				for(var j = 0; j<cartItem.specificationList.length; j++){
		   					//cartInfo += "<dd>"+cartItem.specificationList[j]+"</dd>"
	                		cartInfo += cartItem.specificationList[j]+'&nbsp;&nbsp;&nbsp;';
	                	}
		   				cartInfo += ' </dd>';
		   				cartInfo += '     <dd class="dd3">￥'+cartItem.price+'<i>*'+cartItem.quantity+'</i><a href="#" name="delete">删除</a></dd>';
		   				cartInfo += '<input type="hidden" name="partNumber" value="'+cartItem.partNumber+'">';
		   				cartInfo += ' </dl>   ';
		                totalPrice += cartItem.price *cartItem.quantity;
		   			}
		   			
		   			if(obj.num>0){
		   				$(".shopping_amount").html(obj.num);
		   				$(".span1").html(obj.num);
		   				$(".span2").html(currency(totalPrice, true));
		   			} 
		   			$(".list_con").html(cartInfo);
		   			
		   			
					// 删除
		   			$("a[name='delete']").click(function() {
							var partNumber = $(this).parent().parent().find("input[name='partNumber']").val();
							var $shoppingcar_list_cont = $(this).parent().parent();
							$.ajax({
								url : "/cart/delete",
								type : "POST",
								data : {
									partNumber : partNumber
								},
								dataType : "json",
								cache : false,
								beforeSend : function() {
//									$submit.prop("disabled", true);
								},
								success : function(data) {
									if (data.success) {
										$shoppingcar_list_cont.remove();
										var obj = data.data;
										$(".shopping_amount").text(obj.totalNumber);
										if(obj.totalNumber>0&&totalPrice>0){
											$(".buycar_bottom_con .span1").html(obj.totalNumber);
											$(".buycar_bottom_con .span2").html(currency(obj.totalPrice, true));
										}else{
											//$(".go_account_btn").remove();
											$(".buycar_bottom").remove();
											//$('.shoppingcar_cont').hide();
										}
									} else {
										alert(data.msg);
									}
								},
								complete : function() {
//									$submit.prop("disabled", false);
								}
							});
						return false;
				});
		   		}
		   }
	}); 
}

var setting = {
		priceScale: "2",
		priceRoundType: "roundHalfUp",
		currencySign: "￥",
		currencyUnit: "元",
		uploadImageExtension: "",
		uploadFlashExtension: "",
		uploadMediaExtension: "",
		uploadFileExtension: ""
	};


//货币格式化
function currency(value, showSign, showUnit) {
	if (value != null) {
		var price="";
		var f;
		if (setting.priceRoundType == "roundHalfUp") {
			f = (Math.round(value * Math.pow(10, setting.priceScale)) / Math.pow(10, setting.priceScale));
		} else if (setting.priceRoundType == "roundUp") {
			f = (Math.ceil(value * Math.pow(10, setting.priceScale)) / Math.pow(10, setting.priceScale));
		} else {
			f = (Math.floor(value * Math.pow(10, setting.priceScale)) / Math.pow(10, setting.priceScale));
		}
		price=f.toFixed(setting.priceScale);
		if (showSign) {
			price = setting.currencySign + price;
		}
		if (showUnit) {
			price += setting.currencyUnit;
		}
		return price;
	}
}