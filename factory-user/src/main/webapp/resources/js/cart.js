$(document).ready(function() {
	$(".benifit-sales-box").hover(function(){
		$(this).find(".bfit-price").show();
	},function(){
		$(".benifit-sales-box .bfit-price").hide();
	})

	var $quantity = $("input[name='quantity']");
	var $increase = $("a[name='increase']");
	var $decrease = $("a[name='decrease']");
	var $delete = $("a[name='delete']");
	var $giftItems = $("#giftItems");
	var $promotion = $("#promotion");
	var $point = $("#point");
	var $amount = $("#amount");
	var $clear = $("#clear");
	var $submit = $("#submit");
	var timeouts = {};
	var $personalProduct = $(".d_action a.collectProduct");
	//刷新验证购买数量正确与否
	$("quantity").each(function(i){
		if (!(/^\d*[1-9]\d*$/.test($(this).val()))||$(this).val()<0) {
			$(this).css("border","1px solid #ff6161");
			$("#toSubmitOrder").attr("href","javascript:void(0);");
			$(".btn_area").css("background","none repeat scroll 0 0 #959595");
			$prompt = $(this).parent().find("div[class='s-hint']");
			$prompt.html("购买数量不能小于1");
			$prompt.show();
		}
	});
	// 初始数量
	$quantity.each(function() {
		var $this = $(this);
		$this.data("value", $this.val());
		calTotalPrice();
	});
	// 增加数量
	$increase.click(function() {
			var $quantity = $(this).parent().find("input[name='quantity']");
			var quantity = $quantity.val();
			if (/^\d*[1-9]\d*$/.test(quantity)) {
				$quantity.val(parseInt(quantity) + 1);
			} else {
				$quantity.val(1);
			}
			edit($quantity);
	});
	// 减少数量
	$decrease.click(function() {
			var $quantity = $(this).parent().find("input[name='quantity']");
			var quantity = $quantity.val();
			if (/^\d*[1-9]\d*$/.test(quantity)
					&& parseInt(quantity) > 1) {
				$quantity.val(parseInt(quantity) - 1);
			} else {
				$quantity.val(1);
			}
			edit($quantity);
		
	});
	
	// 编辑数量
	$quantity.bind("input propertychange change", function(event) {
		/*if (event.type != "propertychange"
				|| event.originalEvent.propertyName == "value") {
			edit($(this));
		}*/
		if (!(/^\d*[1-9]\d*$/.test($(this).val()))) {
			$(this).css("border","1px solid #ff6161");
			$("#toSubmitOrder").attr("href","javascript:void(0);");
			$(".btn_area").css("background","none repeat scroll 0 0 #959595");
			$prompt = $(this).parent().find("div[class='s-hint']");
			if($(this).val()<1){
				$prompt.html("购买数量不能小于1");
				$prompt.show();
			}else{
				$(this).val("");
			}
		}else{
			$quantity.css("border","1px solid #e5e5e5");
			$("#toSubmitOrder").attr("href","javascript:toSubmitOrder();");
			$(".btn_area").css("background","none repeat scroll 0 0 #ff6161");
			edit($(this));
		}
	});
					
	// 编辑数量
	function edit($quantity) {
		var $oldQuantity = $quantity.data("value");
		var quantity = parseInt($quantity.val()) - parseInt($oldQuantity);
		var currentQuantity = $quantity.val();
	
		if (!isNaN(quantity)) {
			var price = $quantity.parent().find("input[name='realPrice']").val();
			var subtotal = parseInt(currentQuantity)*parseFloat(price);
			var $amount=$quantity.parent().siblings(".d_amount");
			$amount.html(currency(subtotal, true));
			$amount.attr("value",subtotal);
//						if (/^\d*[1-9]\d*$/.test($quantity.val())) {
			var partNumber = $quantity.parent().find("input[name='partNumber']").val();
			clearTimeout(timeouts[partNumber]);
			$quantity.css("border","1px solid #ff6161");
			$("#toSubmitOrder").attr("href","javascript:void(0);");
			$(".btn_area").css("background","none repeat scroll 0 0 #959595");
			$prompt = $quantity.parent().find("div[class='s-hint']");
			timeouts[partNumber] = setTimeout(function() {
				$.ajax({
					url : "add",
					type : "POST",
					data : {
						partNumber : partNumber,
						quantity : quantity,
						totalQuantity : currentQuantity
					},
					dataType : "json",
					cache : false,
					async:false,
					beforeSend : function() {
					},
					success : function(data) {
						if (data.success) {
							$prompt.hide();
							$quantity.css("border","1px solid #e5e5e5");
							$("#toSubmitOrder").attr("href","javascript:toSubmitOrder();");
							$(".btn_area").css("background","none repeat scroll 0 0 #ff6161");
							var obj = data.data;
							if(data.data.realPrice){
									$quantity.parent().find("input[name='realPrice']").val(data.data.realPrice);
									subtotal = parseInt(currentQuantity)*parseFloat(data.data.realPrice);
									$amount=$quantity.parent().siblings(".d_amount");
									$amount.html(currency(subtotal, true));
									$amount.attr("value",subtotal);
									
									if(data.data.isLadder){
										$quantity.parent().siblings(".d_price").html("¥" + toDecimal2(data.data.realPrice) + "+0.01券");
									}else{
										$quantity.parent().siblings(".d_price").html("¥" + toDecimal2(data.data.realPrice) + "+"  + toDecimal2(data.data.maxCompanyTicket) +"券");
									}
							}
							$quantity.data("value", currentQuantity);
							calTotalPrice();
						} else {
							$quantity.css("border","1px solid #ff6161");
							$("#toSubmitOrder").attr("href","javascript:void(0);");
							$(".btn_area").css("background","none repeat scroll 0 0 #959595");
							$prompt.html(data.msg);
							$prompt.show();
						}
						$(".s-hint").each(function(){
							if($(this).attr("style")=="display: none;" ){
								$(".btn_area").css("background","none repeat scroll 0 0 #ff6161");
								$("#toSubmitOrder").attr("href","javascript:toSubmitOrder();");
							}else{
								$(".btn_area").css("background","none repeat scroll 0 0 #959595");
								$("#toSubmitOrder").attr("href","javascript:void(0);");
								return false;
							}
						})
					},
					complete : function() {
					}
				});
			}, 500);
		} else {
			$quantity.val($quantity.data("value"));
		}
	}
					
	/**
	 * 移入收藏夹
	 */
	$personalProduct.click(function(){
		var $str = $(this);
		$.ajax({
			type: "POST",
			url:"/collectProduct?productId="+$str.attr("id"),
			success: function(ret){
				if(ret.success){
					var partNumber = $str.parent().parent().parent().find("input[name='partNumber']").val();
					var $product_list_div = $str.parent().parent().parent().parent();
					var $order_content_div = $str.parent().parent().parent();
					$.ajax({
						url : "delete",
						type : "POST",
						data : {
							partNumber : partNumber
						},
						dataType : "json",
						cache : false,
						success : function(data) {
							if (data.success) {
								if(data.data.totalPrice == 0){
									location.href="/cart/list";
									 return;
								}
								if($product_list_div.children().length > 2){
									$order_content_div.remove();
								}else{
									$product_list_div.remove();
								}
								calTotalPrice();
							} else {
								wode.showBox("错误","已从购物车删除",{hideBtn:true});
							}
						},
						complete : function() {}
					});
				}else{
					wode.showLoginBox();
				}
			}
		});
	});

	//商家选中
	$(".supplierName").click(function(){
		if($(this).is(':checked')){
			$(this).parents(".product_list").find(".order_content :checkbox").each(function(i){
				if(typeof($(this).attr("disabled"))=="undefined"){
					$(this).attr("checked","checked");
				}else{
					$(this).removeAttr("checked");
				}
			});
		}else{
			$(this).parents(".product_list").find(".order_content :checkbox").removeAttr("checked");
		}
		$("input[name='selectPartNumber']:checked").each(function(){
			if($(this).parents(".order_content").find(".s-hint").css("display")=="none"){
				$(".btn_area").css("background","none repeat scroll 0 0 #ff6161");
				$("#toSubmitOrder").attr("href","javascript:toSubmitOrder();");
			}
			if($(this).parents(".order_content").find(".s-hint").css("display")=="block"){
				$(".btn_area").css("background","none repeat scroll 0 0 #959595");
				$("#toSubmitOrder").attr("href","javascript:void(0);");
				return false;
			}
		});
		calTotalPrice();
	})
		
	//sku选中
	$("input[name='selectPartNumber']").click(function(){
		if(!$(this).is(':checked')){
			$(this).parents(".product_list").find(".supplierName").removeAttr("checked");
		}else{
			var flag=true;
			$(this).parents(".product_list").find(".partNumber").each(function(i){
				if(!$(this).is(':checked')){
					flag=false;
					return;
				}
			})
			if(flag){
				$(this).parents(".product_list").find(".supplierName").attr("checked","checked");
			}
		}
		$("input[name='selectPartNumber']:checked").each(function(){
			if($(this).parents(".order_content").find(".s-hint").css("display")=="none"){
				$(".btn_area").css("background","none repeat scroll 0 0 #ff6161");
				$("#toSubmitOrder").attr("href","javascript:toSubmitOrder();");
			}
			if($(this).parents(".order_content").find(".s-hint").css("display")=="block"){
				$(".btn_area").css("background","none repeat scroll 0 0 #959595");
				$("#toSubmitOrder").attr("href","javascript:void(0);");
				return false;
			}
		});
		calTotalPrice();
	});
		
	//商家选中
	$(".supplierName").attr("checked","checked");
	//sku选中
	$("input[name='selectPartNumber']").each(function(){
		if($(this).attr("disabled")=="disabled"){
			$(this).attr("checked",false);
		}
	});
		
});

//提交订单
function toSubmitOrder(){
	var partNumbers = "";
	$("input[name='selectPartNumber']").each(function(i){
		if($(this).is(':checked')){
			partNumbers+=$(this).val()+",";
		}
	})
	if(partNumbers.length < 2){
		wode.showBox("错误","请勾选购买商品",{hideBtn:true});
		return;
	}
	$.ajax({
		   type: "POST",
		   url: "/user/checkLogin",
		   success: function(ret){
		   		if(ret.success){
		   			window.location="/order/info?partNumbers="+partNumbers;
		   		}else{
		   			wode.showLoginBox();
		   		}
		   		
		   }
	});
}

//提交订单
function delCartItem(obj){
	var partNumber = $(obj).parent().parent().parent().find("input[name='partNumber']").val();
	var $product_list_div = $(obj).parent().parent().parent().parent();
	var $order_content_div = $(obj).parent().parent().parent();
	
	wode.showBox("确认","您要删除该商品？");
	
	$("#boxCheck").unbind("click");
	$("#boxCheck").click(function(){
		$("a[name='delete']").removeAttr("onclick");
		$.ajax({
			url : "delete",
			type : "POST",
			data : {
				partNumber : partNumber
			},
			dataType : "json",
			cache : false,
			beforeSend : function() {
			},
			success : function(data) {
				
				if (data.success) {
					$("a[name='delete']").attr("onclick","delCartItem(this)");
					var obj = data.data;
					if(obj.totalPrice == 0){
						 var t = new Date().getTime();
						 location.href="/cart/list?t="+t;
						 return;
					}
					if($product_list_div.children().length > 2){
						$order_content_div.remove();
					}else{
						$product_list_div.remove();
					}
					$(".box").hide();
					calTotalPrice();
				} else {
					wode.showBox("错误",data.msg,{hideBtn:true});
					$("a[name='delete']").attr("onclick","delCartItem(this)");
				}
				
			},
			complete : function() {
			}
		});
	});
	return false;
}

function calTotalPrice(){
	var $totalPrice = $("#totalPrice");
	var amount=0;
	$("input[name='selectPartNumber']").each(function(i){
		if($(this).is(':checked')){
			amount+=parseFloat($(this).parents(".order_content").find(".d_amount").attr("value"));
		}
	})
	$totalPrice.parent().find(".price").text(currency(amount, true));
}
//保留俩位小数没有补零
function toDecimal2(x) {    
    var f = parseFloat(x);    
    if (isNaN(f)) {    
        return false;    
    }    
    var f = Math.round(x*100)/100;    
    var s = f.toString();    
    var rs = s.indexOf('.');    
    if (rs < 0) {    
        rs = s.length;    
        s += '.';    
    }    
    while (s.length <= rs + 2) {    
        s += '0';    
    }    
    return s;    
}