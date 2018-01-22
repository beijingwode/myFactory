
var uid=GetUidCookie();
$(document).ready(function() {

    if(isWeiXinOpen()) {
    	loginCheck(1);
    }
    
	init();
	var groupBuy =sessionStorage.getItem("groupBuy");
	if(typeof(groupBuy)!=undefined && groupBuy!=null){//清除购物团信息
		sessionStorage.removeItem("groupBuy");
	};
	var btnName =sessionStorage.getItem("btnName");
	if(typeof(btnName)!=undefined && btnName!=null){//清除匹配信息
		sessionStorage.removeItem("btnName");
	}
	//点击编辑
	$(".top_box span").toggle(
	  function(){			
		$(this).html("保存");
		$(".order_box ul li dl .dd1 a").hide();
		$(".order_box ul li .price a").show();
		$(".quantity-wrapper").show();
	  },
	   function(){			  
		$(this).html("编辑");
		$(".order_box ul li dl .dd1 a").show();
		$(".order_box ul li .price a").hide();
		$(".quantity-wrapper").hide();
	  }
	);
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
});

function selAll() {

	if($("#sel_all").val()==1) {
		//取消勾选
		$("#sel_all").val("0");
	} else {
		//选中
		$("#sel_all").val("1");
	}
	
	if($("#sel_all").val()==1) {
		//选中
		$(".all_btn em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
		$(".order_box input[name='sel_top']").val("1");
		$(".order_box input[name='sel_item']").val("1");
		$(".order_box em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
	} else {
		//取消
		$(".all_btn em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
		$(".order_box input[name='sel_top']").val("0");
		$(".order_box input[name='sel_item']").val("0");
		$(".order_box em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
	}
	
	refreshCash();
}
function toggleSel(obj,type) {

	if('item' == type) {
		var sel=$(obj).children("input[name='sel_item']");
		if($(sel).val() == 1) {
			$(sel).val("0");
			$(obj).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
			
			var selS = $(obj).parent().parent().find("em input[name='sel_item']");
			var hassel = false;
			for (var i = 0; i < selS.length; i++) {
				if($(selS[i]).val() == 1) {
					hassel=true;
					break;
				}
			}
			if(hassel==false){
				var topDiv =$(obj).parent().parent().prev("div");
				var topSel=$(topDiv).children("input[name='sel_top']");
				var em=$(topDiv).children("em");
				$(topSel).val("0");
				$(em).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
			}
			
		} else {
			$(sel).val("1");
			$(obj).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
			
			var topDiv =$(obj).parent().parent().prev("div");
			var topSel=$(topDiv).children("input[name='sel_top']");
			var em=$(topDiv).children("em");
			$(topSel).val("1");
			$(em).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
		}
	} else {
		var em=$(obj).children("em");
		var sel=$(em).children("input[name='sel_top']");
		if($(sel).val() == 1) {
			$(sel).val("0");
			$(em).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
			
			var ul =$(obj).next("ul");
			var itemEm = ul.find("li em");
			var itemSel=$(itemEm).children("input[name='sel_item']");
			$(itemSel).val("0");
			$(itemEm).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
		} else {
			$(sel).val("1");
			$(em).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});

			var ul =$(obj).next("ul");
			var itemEm = ul.find("li em");
			var itemSel=$(itemEm).children("input[name='sel_item']");
			$(itemSel).val("1");
			$(itemEm).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
		}
	}
	refreshCash();
}
var ladderMap =null;
function init(){
	$.ajax({
		url : jsBasePath +'cart/list.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			var html='';

			if (data.success) {
				var result = data.data;
				var freeMap = result.freeMap;
				ladderMap = result.ladderMap;
				var cart = result.cart;
				var userFactory = result.userFactory;
				var specificationsId ='';
				for (var i = 0; i < cart.length; i++) {
					html +='<div class="order_box">';
					html +='<div class="order_top" onclick="toggleSel(this,\'top\');">';
					if (userFactory.supplierId == cart[i].supplierId) {
						html +='<em><input type="hidden" name="sel_top"></em><p onclick="go2ShopByShopId(\''+cart[i].shopId+'\');event.stopPropagation()||(event.cancelBubble = true);">自家</p>';
					}else{
						html +='<em><input type="hidden" name="sel_top"></em><p onclick="go2ShopByShopId(\''+cart[i].shopId+'\');event.stopPropagation()||(event.cancelBubble = true);">'+cart[i].supplierName+'</p>';
					}
					html +='<label style="float:right;color:#c22936;padding-right: 5px; line-height: 22px;" onclick="go2ShopBySupplierId(\''+cart[i].shopId+'\');event.stopPropagation()||(event.cancelBubble = true);">';
				    if(freeMap!=null && freeMap!='' && typeof(freeMap)!="undefined"){
						html+=freeMap[cart[i].supplierId];
				    }
				    html +='</label>';
				    html +='</div>';
					html +='<ul>';
					var cartItems = cart[i].cartItemList;
					for (var j = 0; j < cartItems.length; j++) {
						var flag = false;
						if(cartItems[j].partNumber && typeof(cartItems[j].partNumber)!="undefined"){
							specificationsId = cartItems[j].partNumber;
						}
						if(ladderMap!=null && ladderMap!='' && typeof(ladderMap)!="undefined"){
							if(ladderMap[cartItems[j].partNumber]!=""){
								flag = true;
								html +='<li style="height: 96px;">';
							}else{
								html +='<li>';
							}
					    }else{
					    	html +='<li>';
					    }
						html +='<em onclick="toggleSel(this,\'item\');"><input type="hidden" name="sel_item"><input type="hidden" name="sku_id" value="'+cartItems[j].partNumber+'"><input type="hidden" name="product_Id" value="'+cartItems[j].productId+'"></em>';
						if(flag){
								html +='<dl style="height: 96px;">';
					    }else{
					    	html +='<dl>';
					    }
						html +='<dt><a href="javascript:go2p(\''+cartItems[j].productId+'&specificationsId='+specificationsId+'\');"><img src="'+cartItems[j].imagePath+'" /></a>'
						if(cartItems[j].saleKbn==1) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon2.png" /></div>';
						} else if(cartItems[j].saleKbn==2) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_c2.png" /></div>';
						} else if(cartItems[j].saleKbn==4) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_z2.png" /></div>';
						}else if(cartItems[j].saleKbn==5) {
							html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_t2.png" /></div>';
						}
						html +='</dt>';
						html +='<dd class="dd1">';
						html +='<a href="javascript:go2p(\''+cartItems[j].productId+'&specificationsId='+specificationsId+'\');">'+cartItems[j].productName+'</a>';
						html +='<div class="quantity-wrapper">';
						html +='<input id="quantityMinus" class="quantity-decrease" name="" type="button" value="-" onclick="reduce(\''+cartItems[j].partNumber+'\',this);"/>';
						html +='<input type="text" readonly class="quantity" size="4" value="'+cartItems[j].quantity+'" id="number" >';
						html +='<input id="quantityPlus"  class="quantity-increase" name="" type="button" value="+" onclick="add(\''+cartItems[j].partNumber+'\',this);"/> ';
						html +='</div>';
						html +='</dd>';
						var specificationList = cartItems[j].specificationList;
					    var kingaku="";
					    for(var k=0;k<specificationList.length;k++) {
					    	kingaku += specificationList[k]+";&nbsp;";
					    }
					    html +='<dd class="dd2">'+kingaku+'</dd>';
					    if(cartItems[j].maxCompanyTicket>0){
					    	html +='<dd class="dd3">￥<span>'+(cartItems[j].realPrice).toFixed(2)+'</span>+'+(cartItems[j].maxCompanyTicket).toFixed(2)+'券</dd>';
					    }else{
					    	html +='<dd class="dd3">￥<span>'+(cartItems[j].realPrice).toFixed(2)+'</span></dd>';
					    }
						
						html +='</dl>';
						html +='<div class="price"><a href="javascript:go2Del(\''+cartItems[j].partNumber+'\');" style="display:none;"></a><i>x'+cartItems[j].quantity+'</i></div>';
						//html +='<div class="price"><a href="javascript:go2Del(\''+cartItems[j].partNumber+'\');" style="display:none;"></a></div>';
						html +='<input type="hidden" name="price_item" value="'+(cartItems[j].realPrice)+'">';
						html +='<input type="hidden" name="num_item" value="'+cartItems[j].quantity+'">';
						var companyTicket = cartItems[j].maxCompanyTicket;
						if(companyTicket==null || companyTicket==''){
							companyTicket =0;
						}
						html +='<input type="hidden" name="companyTicket_item" value="'+companyTicket+'">';
						if(flag){
							html +='<div class="qc_title"><p><img src="'+jsBasePath+'static_resources/images/promotion_qc.png">'+ladderMap[cartItems[j].partNumber]+'</p></div>';
							html +='<div class="chakan" onclick="go2CKPromotion(\''+cartItems[j].partNumber+'\')"><span>查看</span></div>';
					    }
						html +='</li>';
					}
					html +='</ul>';
					html +='<div class="buy_btn"><a onclick ="go2ShoppingGroup(\''+cart[i].shopId+'\',this);">一起购</a></div>';
					html +='</div>';
				}
				
		    	$(".main-box").html(html);
		    	$(".main-box").show();
			} else {
				$(".main-box").hide();
				//showInfoBox("购物车为空!");
			}
		},
		error : function() {}
	});
}
function go2p(pid){
	window.location=jsBasePath +'productm?productId='+pid;
}

function back() {
	history.back();
}
function confirmOrder(){

	var $listSel  = $('input[name="sel_item"]');
	var $listSku  = $('input[name="sku_id"]');
	var $listProductId  = $('input[name="product_Id"]');
	
	var partNumbers = "";
	var productIds ="";
	for(var i=0;i<$listSel.length;i++) {
		if($($listSel[i]).val()=="1") {
			partNumbers += "0_"+$($listSku[i]).val()+",";
			if(productIds.indexOf($($listProductId[i]).val())<0){
				productIds += $($listProductId[i]).val()+",";
			}
		}
	}

	sessionBefore2Order();
	window.location=jsBasePath +'order/confirmCartPage.user?uid='+uid+'&partNumbers='+partNumbers+"&productIds="+productIds;
}
function go2ShoppingGroup(shopId,that){
	var $thatListSel = $(that).parent().prev().find('input[name="sel_item"]');
	var $thatListSku = $(that).parent().prev().find('input[name="sku_id"]');
	var partNumbers = "";
	for(var i=0;i<$thatListSel.length;i++) {
		if($($thatListSel[i]).val()=="1") {
			partNumbers += "0_"+$($thatListSku[i]).val()+",";
		}
	}
	if(partNumbers=="") return showInfoBox("请选择一起购的商品，只能选择同一个商家");

	sessionBefore2Order();
	window.location = jsBasePath+'groupOrder/confirmCartPage.user?uid='+uid+'&partNumbers='+partNumbers
}
function refreshCash(){

	var $listSel  = $('input[name="sel_item"]');
	var $listNum  = $('input[name="num_item"]');
	var $listPrice  = $('input[name="price_item"]');
	var $listCompanyTicket  = $('input[name="companyTicket_item"]');
	
	var total = parseFloat(0.0);
	var totalCompanyTicket =parseFloat(0.0);
	var flag = false;
	for(var i=0;i<$listSel.length;i++) {
		if($($listSel[i]).val()=="1") {
			total += parseFloat($($listPrice[i]).val())*parseFloat($($listNum[i]).val());
			totalCompanyTicket += parseFloat($($listCompanyTicket[i]).val())*parseFloat($($listNum[i]).val());
			if(parseFloat($($listNum[i]).val())==0.0){
				flag = true;
			}
		}
	}
	
	if(total >= 0.0) {
		if(flag){
			$(".bottom_box_rt a").attr("href","javascript:void(0);");
			$(".bottom_box_rt a").attr("class","disable");
		}else{
			$(".bottom_box_rt a").attr("href","javascript:confirmOrder();");
			$(".bottom_box_rt a").removeAttr("class","disable");
		}
	} else {
		$(".bottom_box_rt a").attr("href","javascript:void(0);");
	}
	$(".bottom_box_rt .p1 span").html('￥<i>'+total.toFixed(2)+'</i>');
	$(".bottom_box_rt .p2 span").html('￥'+totalCompanyTicket.toFixed(2));
}
function add(specificationId,add){//增加数量
	var number=$(add).prev().val();
	var totalQuantity=parseInt(number)+1;//变化后数量
	$.ajax({
		url : jsBasePath +'cart/add?uid='+uid+'&specificationId='+specificationId+'&quantity=1&totalQuantity='+totalQuantity,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache: false,
		success : function(data) {
			if (data.success) {
				var number=$(add).prev().val();
				$(add).prev().val(parseInt(number)+1);
				var num_item=$(add).parents("dl").siblings('input[name="num_item"]');
				var i=$(add).parents("dl").siblings('.price').find("i");
				//var i=$(add).parents("dl").next().find("i");
				i.html('x'+(parseInt(number)+1));
				num_item.val(parseInt(number)+1);
				var k=$(add).parents("dl").find(".dd3");
				var realPrice=data.data.realPrice;
				var maxCompanyTicket=(data.data.maxCompanyTicket);
				if(maxCompanyTicket>0){
					k.html('￥<span>'+(realPrice).toFixed(2)+'</span>+'+(maxCompanyTicket).toFixed(2)+'券');
				}else{
					k.html('￥<span>'+(realPrice).toFixed(2)+'</span>');
				}
				var price_item=$(add).parents("dl").siblings('input[name="price_item"]');
				var companyTicket_item=$(add).parents("dl").siblings('input[name="companyTicket_item"]');
				price_item.val(realPrice.toFixed(2));
				if(maxCompanyTicket==null || maxCompanyTicket==''){
					maxCompanyTicket = 0;
				}
				companyTicket_item.val(maxCompanyTicket.toFixed(2));
				refreshCash()
			}else{
				showInfoBox(data.msg);
			}
		}
	});
}
function reduce(specificationId,reduce){//减少数量
	var number=$(reduce).next().val();
	var totalQuantity = parseInt(number)-1;//变化后数量
	if (number>1) {
		$.ajax({
			url : jsBasePath +'cart/add?uid='+uid+'&specificationId='+specificationId+'&quantity=-1&totalQuantity='+totalQuantity,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    cache: false,
			success : function(data) {
				if (data.success) {
					var number=$(reduce).next().val();
					$(reduce).next().val(parseInt(number)-1);
					var num_item=$(reduce).parents("dl").siblings('input[name="num_item"]');
					num_item.val(parseInt(number)-1);
					var i=$(reduce).parents("dl").next('.price').find("i");
					//var i=$(add).parents("dl").next().find("i");
					i.html('x'+(parseInt(number)-1));
					var k=$(reduce).parents("dl").find(".dd3");
					
					var realPrice=data.data.realPrice;
					var maxCompanyTicket=(data.data.maxCompanyTicket);
					if(maxCompanyTicket>0){
						k.html('￥<span>'+(realPrice).toFixed(2)+'</span>+'+(maxCompanyTicket).toFixed(2)+'券');
						//k.html('￥'+realPrice.toFixed(2)+'+'+maxCompanyTicket.toFixed(2)+'券');
					}else{
						k.html('￥<span>'+(realPrice).toFixed(2)+'</span>');
					}
					var price_item=$(reduce).parents("dl").siblings('input[name="price_item"]');
					var companyTicket_item=$(reduce).parents("dl").siblings('input[name="companyTicket_item"]');
					price_item.val(realPrice.toFixed(2));
					if(maxCompanyTicket==null || maxCompanyTicket==''){
						maxCompanyTicket = 0;
					}
					companyTicket_item.val(maxCompanyTicket.toFixed(2));
					refreshCash()
				}
			}
		});
	}
	
}
function go2Del(specificationId){
	$.ajax({
		url : jsBasePath +'cart/delete?uid='+uid+'&specificationId='+specificationId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				//window.location= jsBasePath+'cart/page';
				//window.location= history.back();
				//location.reload();
				refresh()
			}else{
				showInfoBox(data.msg)
			}
		}
	});
}
function refresh(){
	$(".order_box").remove();//清除内容
	$("#sel_all").val(1);
	selAll();
	init();
	$(".top_box span").html("保存");
	$(".order_box ul li dl .dd1 a").hide();
	$(".order_box ul li .price a").show();
	$(".quantity-wrapper").show();
	refreshCash();
}
function go2ShopByShopId(shopId){//跳转到店铺首页
	if(shopId==''||shopId==null||shopId==undefined ||shopId=="undefined"){
		//showInfoBox("店铺信息有误,请联系客服！");
	}else{
		window.location = jsBasePath +'shop/page?shopId=' + shopId;
	}
}
function go2CKPromotion(skuId){
	if(ladderMap[skuId]!=""){
		//$("#productSalesPromotionQC")
		$("#productSalesPromotionQC").html('<p><i>企采</i>'+ladderMap[skuId]+'</p>');
	}
	$(".thickdiv").show();
	$(".promotion_bottom").show();
}