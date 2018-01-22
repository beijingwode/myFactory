
var uid=GetUidCookie();
var shopId='';
var groupBuy = null;
$(document).ready(function() {
	if(!sessionCheckOrder("order_confirm",4)) {
		return;
	}
	
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
		if(isPageHide){window.location.reload();}
	});
	window.addEventListener('pagehide', function(){isPageHide = true;});
	//选择购物团
	if(groupId && groupId!==null && groupId!=""){
		$("#groupId").val(groupId);
	}
	cartInit();
	/*if(groupId && groupId!==null && groupId!=""){
		checkShopGroup();
	}*/
	groupBuy = sessionStorage.getItem('groupBuy');
	if(typeof(groupBuy)!=undefined && groupBuy!=null){
		groupBuy = JSON.parse(groupBuy);
		setShopGroupInfo(groupBuy)
		calculateTotalFreight();
	}
	
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
		
	//添加新购物团
	$("#addShopGroup").click(function(e) {
		window.location = jsBasePath +'group/page.user?uid=' + uid+'&shopId='+shopId;
	});
	//选择购物团
	$("#chooseShopGroup").click(function(e) {
		go2ChooseShopGroup();
	});
	var chooseimages = sessionStorage.getItem("chooseimages");
	if(typeof(chooseimages)!=undefined && chooseimages!=null &&chooseimages!=''){
		var html = ''; 
		var slice=chooseimages.substring(0,chooseimages.length-1);
		var split=slice.split(",");
		for (var i = 0; i < split.length; i++) {
			//html+='<div class="swiper-slide"><a href="javascript:;"><img src="'+split[i]+'" /></a></div>';
			html+='<div class="swiper-slide"><a href="javascript:;"><img src="'+split[i]+'" /></a></div>';
		}
		$(".swiper-wrapper").html(html);
		sessionStorage.removeItem("chooseimages");
	}
	//左右滑动
	var mySwiper = new Swiper('.swiper-container',{
	slidesPerView : 'auto',//'auto'
	//slidesPerView : 3.7,
	observer:true,//修改swiper自己或子元素时，自动初始化swiper
	observeParents:true,//修改swiper的父元素时，自动初始化swiper
	})
});

function toggleSel(id) {

	if($("#"+id+"_sel").val()==1) {
		//取消勾选
		$("#"+id+"_sel").val("0");
	} else {
		//选中
		$("#"+id+"_sel").val("1");
	}
	refreshCash();
}

function orderSubmit() {
	$(".bottom_box a").attr("href","javascript:void(0);")
	var total = parseFloat($("#total").val());
	if(total > 0.0) {
		cartCreate();
	} else {
		showConfirm("您确认使用现金券支付此订单吗？","cartCreate()","setSubmitA()");
	}
}

function setSubmitA() {
	$(".bottom_box a").attr("href","javascript:orderSubmit();")
}
var isMaxCompanyTicket = true;

function back() {
	history.back();
}
function cartInit(){
	$.ajax({
		url : jsBasePath +'groupOrder/confirmCart.user?partNumbers='+partNumbers+'&uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			var html='';
			if (data.success) {
				var result = data.data;
				var freeMap = result.freeMap;
				var userFactory = result.userFactory;
			    //商品
			    var cartItems=result.cart;
			    var hasExChange = false;
			    var totalPrice = 0;
			    var totalfuli = 0;
			   // var fuli = 0;
			    var totalRealPrice = 0;//所有商品内购价
			    var useCompanyTicket = result.companyTicket;
			    var useExchangeCash = result.exchangeCash?result.exchangeCash:0;
			    var useExchangeTicket = result.exchangeTicket?result.exchangeTicket:0;
			    var hasGroup = result.groupBuyNum;
				var cart = result.cart;
				var cart = result.cart;
				var totalCompanyTicket = 0;//所有商品应共用内购券数
				for (var k = 0; k < cart.length; k++) {
					shopId =cart[k].shopId;
					html +='<div class="link" id="shopName" data-shopId="'+cart[k].shopId+'">';
				    if (userFactory.supplierId==cart[k].supplierId) {
				    	html +="自家";
					}else{
						html +="商铺："+cart[k].supplierName;
					}
				    html +="</div>";
					var cartItems = cart[k].cartItemList;
					for(var i=0;i<cartItems.length;i++) {
					    html +='<dl>';
					    html +='<a href="javascript:void(0);">';
					    html +='<dt><img src="'+cartItems[i].imagePath+'" /></dt>'
					    html +='<dd class="dd1">'+cartItems[i].productName+'</dd>';
					    var specificationList = cartItems[i].specificationList;
					    var kingaku="";
					    for(var j=0;j<specificationList.length;j++) {
					    	kingaku += specificationList[j]+";&nbsp;";
					    }
					    html +='<dd class="dd2">'+kingaku+'</dd>';
					    html +='<dd class="dd3" id="sku_'+cartItems[i].partNumber+'"><p>￥<span>'+((cartItems[i].realPrice)).toFixed(2)+'</span>+'+(cartItems[i].maxCompanyTicket).toFixed(2)+'内购券</p><em>X<i>'+cartItems[i].quantity+'</i><em></dd>';
					    html +='<input type="hidden" name="skuId" value="'+cartItems[i].partNumber+'">';
					    html +='<input type="hidden" name="productId" value="'+cartItems[i].productId+'">';
					    html +='<input type="hidden" name="skuNum" value="'+cartItems[i].quantity+'">';
					    html +='<input type="hidden" name="skuFreights" value="0">';
					    html +='<input type="hidden" name="realPrice" id="skuRealPrice_'+cartItems[i].partNumber+'" value="'+cartItems[i].realPrice+'">';
					    
					    var skuCompanyTicket = cartItems[i].maxCompanyTicket*cartItems[i].quantity;
					    var skuExchange = cartItems[i].price*cartItems[i].quantity-skuCompanyTicket;
					    var skuExchangeTicket=skuExchange;
					    var prvProductTicket = cartItems[i].maxCompanyTicket*cartItems[i].quantity;//单个商品需用内购券
					    var prvProductRealPrice = cartItems[i].realPrice*cartItems[i].quantity;//单个商品真实价格
					    
					    totalCompanyTicket+=prvProductTicket;
					    totalRealPrice+=prvProductRealPrice;
					    if(cartItems[i].saleKbn==2) {
					    	hasExChange=true;
					    } else {
					    	skuExchange=0;
					    }
					   /*if(skuCompanyTicket>useCompanyTicket) {
					    	skuCompanyTicket=useCompanyTicket;
					    	useCompanyTicket=0;
					    } else {
					    	useCompanyTicket=useCompanyTicket-skuCompanyTicket;
					    }*/
					    totalfuli += skuCompanyTicket;
					    
					    html +='<input type="hidden" name="skuCompanyTicket" id="skuTotalCompanyTicket_'+cartItems[i].partNumber+'" value="'+skuCompanyTicket+'">';
					    html +='<input type="hidden" name="productPrice" id="skuPrice_'+cartItems[i].partNumber+'" value="'+(cartItems[i].price)+'">';
					    html +='<input type="hidden" name="maxCompanyTicket" id="skuCompanyTicket_'+cartItems[i].partNumber+'" value="'+cartItems[i].maxCompanyTicket+'">';
					    html +='<input type="hidden" name="skuExchangeTicket" value="'+cartItems[i].benefitTicket+'">';
					    html +='<input type="hidden" name="skuExchangeCash" value="'+cartItems[i].benefitAmount+'">';
					    html +='<input type="hidden" name="skuExchangeSelf" value="'+cartItems[i].benefitSelf+'">';
					    totalPrice +=(cartItems[i].realPrice+cartItems[i].maxCompanyTicket)*cartItems[i].quantity;
					    html +='</a>';
					    html +='</dl>';
				    }
				}
				html +='<input type="hidden" name="freightAdd" id="freightAdd" value="'+cartItems[0].supplierId+'">'
				html +='<div class="yunfei">';
				html +='<input class="text_input" id="note" data="'+cartItems[0].supplierId+'" type="text" value="" placeholder="给卖家留言">';
				html +='</div>';
				$(".main_con").html(html);
				//内购券
			    if (result.companyTicket>totalCompanyTicket) {
			    	isMaxCompanyTicket =true;
			    }else{
			    	isMaxCompanyTicket= false;
			    }
			    $("#fuli").html("内购券：￥"+totalfuli.toFixed(2)+"<span></span>");
			    $("#fuli_user").val(result.companyTicket);
			    if (result.companyTicket>=totalCompanyTicket) {//用户内购券大于商品所需 默认选中
				    $("#fuli_sel").val(1);
				    $("#fuli span").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/new_collection_check.png) no-repeat","background-size":"22px 22px"});
				}else{//用户内购券小于商品所需 未激活
				    $("#fuli span").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/new_collection_noact.png) no-repeat","background-size":"22px 22px"});
				}
			    $("#totalfuli").val(totalfuli);
			    if(totalfuli<=0){
				    $("#fuli").hide();
				}
				//现金券
			    if(result.balance > 0) {
			    	$("#xianjin").html("现金券：￥"+0.00+"<span></span>");
			    	$("#xianjin_user").val(result.balance);
			    	if(result.balance<=totalRealPrice){//现金券不够
			    		$("#xianjin_sel").val(1);
			    	}
			    } else {
			    	$("#xianjin").hide();
			    	$("#xianjin_user").val(0);
			    }
			    
			    if(hasGroup && hasGroup>0){
			    	//if(hasGroup==1){
			    		//checkShopGroup()
			    	//}else{
			    		$("#addShopGroup").hide();
				    	$("#shopGroupInfo").hide();
						$("#chooseShopGroup").show();
			    	//}
			    }
			    
			    //计算运费
		    	$("#totalPrice").val(totalPrice);
		    	refreshCash();
				calculateTotalFreight();
				if (result.companyTicket<totalCompanyTicket) {
					$(".bottom_box a").html("去凑券");
				    $(".bottom_box a").attr("href","javascript:go2Coupons();");
					$(".popover3 .theme_popover_con2").html('<p class="p1">内购券不足<em>￥'+totalfuli.toFixed(2)+'</em></p><p>您剩余内购券：￥'+result.companyTicket.toFixed(2)+'</p><p>是否前往APP找好友凑券</p>');
				}
			} else {
				showInfoBox(data.msg);
				setTimeout(back,1500);
			}
		},
		error : function() {}
	});
}
function go2Coupons(){
	$(".theme-popover-mask2").show();
	$(".popover3").show();
}
function go2App(){
	window.location="http://www.wd-w.com/app.htm?d=1";
}
function cartCreate(){
	var $listSku  = $('input[name="skuId"]');
	var $listNum  = $('input[name="skuNum"]');
	var $listCompanyTicket  = $('input[name="skuCompanyTicket"]');
	var $listFreights  = $('input[name="skuFreights"]');
	
	var $skuExchangeTicket  = $('input[name="skuExchangeTicket"]');
	var $skuExchangeCash  = $('input[name="skuExchangeCash"]');
	var $skuExchangeSelf  = $('input[name="skuExchangeSelf"]');
	
	var useCompanyTicket="";
	var useExchangeTicket="";
	var useExchangeCash="";
	var useExchangeSelf="";
	
	var sku_nums="";
	var sku_freights = "";
	var nums=[];
	var fuli_sel = $("#fuli_sel").val();
	var huanling_sel = $("#huanling_sel").val();
	for(var i=0;i<$listSku.length;i++) {
		if(fuli_sel==1) {
			useCompanyTicket += $($listSku[i]).val() + "_" + parseFloat($($listCompanyTicket[i]).val()).toFixed(2)+",";
		} else {
			useCompanyTicket += $($listSku[i]).val() + "_0,";
		}
		if(huanling_sel==1) {
			useExchangeCash += $($listSku[i]).val() + "_" + $($skuExchangeCash[i]).val()+",";
			useExchangeTicket += $($listSku[i]).val() + "_" + $($skuExchangeTicket[i]).val()+",";
			useExchangeSelf += $($listSku[i]).val() + "_" + $($skuExchangeSelf[i]).val()+",";
		} else {
			useExchangeCash += $($listSku[i]).val() + "_0,";
			useExchangeTicket += $($listSku[i]).val() + "_0,";
			useExchangeSelf += $($listSku[i]).val() + "_0,";
		}
		sku_nums += $($listSku[i]).val() + "_" + $($listNum[i]).val()+",";
		sku_freights += $($listSku[i]).val() + "_" + $($listFreights[i]).val()+",";
	}
	if(useCompanyTicket.length>0) {
		useCompanyTicket= useCompanyTicket.substring(0,useCompanyTicket.length-1);
	}
	if(useExchangeTicket.length>0) {
		useExchangeTicket= useExchangeTicket.substring(0,useExchangeTicket.length-1);
		useExchangeCash= useExchangeCash.substring(0,useExchangeCash.length-1);
		useExchangeSelf= useExchangeSelf.substring(0,useExchangeSelf.length-1);
	}
	if(sku_nums.length>0) {
		sku_nums= sku_nums.substring(0,sku_nums.length-1)
	}
	if(sku_freights.length>0) {
		sku_freights= sku_freights.substring(0,sku_freights.length-1)
	}
	var useCash=0;
	if($("#xianjin_sel").val()==1) {
		useCash =$("#totalCash").val();
	}
	var message = "";
	$("input[id='note']").each(function(i){
		if($(this)){
				var str=$(this).val();
				  str=str.replace(/[\_|\,]/g," "); 
				message+=$(this).attr("data")+"_"+ str+",";
		}
	})
	
	$.ajax({
		url : jsBasePath +'groupOrder/create.user?fromType=weixin&uid='+uid,
		type : "POST",
		dataType: "json",  //返回json格式的数据  
		data: {"userId":uid,"name":$("#name").val(),"mobile":$("#mobile").val(),"address":$("#address").val(),"message":message,
			"useCompanyTicket":useCompanyTicket,"useExchangeTicket":useExchangeTicket,"useExchangeCash":useExchangeCash,"useExchangeSelf":useExchangeSelf,
			"useCash":useCash,"shippingAddressId":$("#shippingAddressId").val(),"sku_nums":sku_nums,"sku_freights":sku_freights,"groupId":$("#groupId").val(),"note":$("#note").val()},  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html='';
			
			if (data.success) {
				var result = data.data;
				if(result.status == 1 || result.realPrice==useCash) {
					sessionSetOrderStep("payZero");
					sessionStorage.setItem("order", "orderConfirm");
					window.location = jsBasePath+'pay/pay_success.user?uid='+uid+'&orderId='+result.orderId+'&suborderId='+'&fromWay='+orderType+'&type=1';
				} else {
					sessionSetOrderStep("created");
					window.location=jsBasePath +'pay/page.user?uid='+uid+'&orderId='+result.orderId+'&totalFee='+(result.realPrice-useCash)+'&orderType='+orderType+"&backNum="+backNum+'&type=1';
				}
			} else {
				
				var msg = data.msg;				
				if(msg.indexOf("需要先回答商家问卷才能下单")>-1) {
					// 试用问券
					showInfoBox(data.msg,"toQuestionnaire('"+data.data+"')");
					
				} else {
					showInfoBox(data.msg);
				}
				$(".bottom_box a").attr("href","javascript:orderSubmit();")
			}
		},
		error : function() {}
	});
}
//计算运费
function calculateTotalFreight(){

	var $listSku  = $('input[name="skuId"]');
	var $listNum  = $('input[name="skuNum"]');
	//var $listCompanyTicket  = $('input[name="skuCompanyTicket"]');
	var $listFreights  = $('input[name="skuFreights"]');
	var $listShowShipping = $('#maxShippingFee');
	//var selfDelivery=$("#selfDelivery_sel").val();
	var useCompanyTicket="";
	var sku_nums="";
	var nums=[];
	var fuli_sel = $("#fuli_sel").val();
	for(var i=0;i<$listSku.length;i++) {
		sku_nums += $($listSku[i]).val() + "_" + $($listNum[i]).val()+",";
	}
	if(sku_nums.length>0) {
		sku_nums= sku_nums.substring(0,sku_nums.length-1)
	}
	if($("#groupId").val()=="") return;
	$.ajax({
		url : jsBasePath +'groupOrder/newCalculateFreight.user?groupId='+$("#groupId").val()+'&uid='+uid+'&sku_nums='+sku_nums+'&selfDelivery=0',
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			var html='';
			var blnExp = false;
			if (data.success) {
				var result = data.data;
				var freeMap = result.freeMap;
				var oldData = result.oldData;
				var sum = 0;
			    for(var i=0;i<oldData.length;i++) {
			    	if (8888==oldData[i]) {
			    		blnExp=true;
			    		oldData[i]=0;
					}else if(9999==oldData[i]){
						blnExp=true;
						oldData[i]=0;
					}
					$($listFreights[i]).val(oldData[i]);
					$($listShowShipping[i]).html("￥" + oldData[i]);
					sum+=oldData[i];
			    }
			   //$("#maxShippingFee").html(sum);
			    $("#totalShipping").html("￥" + sum);
			    $("#totalFreight").val(sum);
			    if (blnExp) {
			    	$("#totalShipping").html('存在超限购买商品');
			    	$(".bottom_box a").attr("class","disable");
			    	$(".bottom_box a").attr("disabled","true");
			    	$(".bottom_box a").attr("href","javascript:void(0);")
				}
			    //重新设定价格
			    var newProductPrice =  result.productPrice;
			    if(newProductPrice && newProductPrice!=null && newProductPrice!=''){
			    	//sku_2647923186108289
			    	for ( i in newProductPrice) {
						$("#sku_"+i+" p").html('￥<span>'+((newProductPrice[i].internalPurchasePrice)).toFixed(2)+'</span>+'+(newProductPrice[i].maxFucoin).toFixed(2)+'内购券');
						$("#skuRealPrice_"+i).val(newProductPrice[i].internalPurchasePrice);
						$("#skuCompanyTicket_"+i).val(newProductPrice[i].maxFucoin);
						$("#skuPrice_"+i).val(newProductPrice[i].price);
					}
			    	//重新修改价格
			    	var totalPrice = 0;
			    	var totalfuli = 0;
			    	for(var i=0;i<$listSku.length;i++) {
			    		//sku_nums += $($listSku[i]).val() + "_" + $($listNum[i]).val()+",";
			    		var pvSkuPrice = parseFloat($("#skuRealPrice_"+$($listSku[i]).val()).val())*parseFloat($($listNum[i]).val());
			    		$("#skuRealPrice_"+$($listSku[i]).val()).val(pvSkuPrice);
			    		totalPrice+=pvSkuPrice;
			    		var pvSKuCompanyTicket = parseFloat($("#skuCompanyTicket_"+$($listSku[i]).val()).val())*parseFloat($($listNum[i]).val());
			    		$("#skuTotalCompanyTicket_"+$($listSku[i]).val()).val(pvSKuCompanyTicket);
			    		totalfuli+=pvSKuCompanyTicket;
			    	}
			    	$("#fuli").html("内购券：￥"+totalfuli.toFixed(2)+"<span></span>");
			    	$("#totalPrice").val(totalPrice+totalfuli);
			    	$("#totalfuli").val(totalfuli);
			    	
			    	if ($("#fuli_user").val()>=totalfuli) {//用户内购券大于商品所需 默认选中
				    	$("#fuli_sel").val(1);
				    	$("#fuli span").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/new_collection_check.png) no-repeat","background-size":"22px 22px"});
				    }else{//用户内购券小于商品所需 未激活
				    	$("#fuli span").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/new_collection_noact.png) no-repeat","background-size":"22px 22px"});
				    }
			    	//现金券
				    if(xianjin_user<=totalPrice){//现金券不够
				    	$("#xianjin_sel").val(1);
				    }
				   
			    }
			    
				//计算价格
		    	refreshCash();
			}else{
				//计算价格
		    	refreshCash();
				showInfoBox(data.msg);
			}
		},
		error : function() {}
	});
}


function refreshCash(){
	var totalPrice = parseFloat($("#totalPrice").val());
	var totalFreight = parseFloat($("#totalFreight").val());
	var totalfuli = parseFloat($("#totalfuli").val());
	var totalhuanling = parseFloat($("#totalhuanling").val());
	var totalCash = parseFloat(0.0);

	var $listExchange  = $('input[name="skuExchange"]');
	for(var i=0;i<$listExchange.length;i++) {
		totalhuanling += parseFloat($($listExchange[i]).val());
	}
	
	//总额=商品总价
	var total=totalPrice;
	
	//总额=商品总价-内购券
	total = total-totalfuli;
	total = parseFloat(total.toFixed(2));
	//总额=商品总价-内购券-换领币
	
	//总额=商品总价-内购券-换领币+运费
	total = total+ totalFreight;
	total = parseFloat(total.toFixed(2));
	//总额=商品总价-内购券-换领币+运费-现金券
	var xianjin_user=parseFloat($("#xianjin_user").val());
	if(total>=xianjin_user) {//需要的现金券大于等于自己拥有的现金券
		totalCash = xianjin_user;
	} else {//需要的现金券小于自己拥有的现金券
		totalCash = total;
	}
	$("#totalCash").val(totalCash);
	if(totalCash<=0){
		$("#xianjin").hide();
	}else{
		$("#xianjin").html("现金券：￥"+totalCash.toFixed(2)+"<span></span>");
	}
	if($("#xianjin_sel").val()==1) {
		total = total-totalCash;
		total = parseFloat(total.toFixed(2));
		$("#xianjin span").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/new_collection_check.png) no-repeat","background-size":"22px 22px"});
	} else {
		$("#xianjin span").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/jihuo.png) no-repeat","background-size":"22px 22px"});
	}
	
	$("#total").val(total);
	$(".bottom_box p span").html('￥'+total.toFixed(2));
}

function toQuestionnaire(qId) {
	window.location = jsBasePath +"order/comments/questionnaire"+qId+"?uid="+uid;
}
function go2ShopByShopId(shopId){//跳转到店铺首页
	if(shopId==''||shopId==null||shopId==undefined ||shopId=="undefined"){
		//showInfoBox("店铺信息有误,请联系客服！");
	}else{
		window.location = jsBasePath +'shop/page?shopId=' + shopId;
	}
}
function go2ChooseShopGroup(){
	if(shopId=='') return;
	var $listSku  = $('input[name="skuId"]');
	var $listProductId  = $('input[name="productId"]');
	var $listNum  = $('input[name="skuNum"]');
	var sku_nums='';
	var productIds='';
	for(var i=0;i<$listSku.length;i++) {
		sku_nums += $($listSku[i]).val() + "_" + $($listNum[i]).val()+",";
		productIds+=$($listProductId[i]).val()+","
	}
	if(sku_nums.length>0) {
		sku_nums= sku_nums.substring(0,sku_nums.length-1)
	}
	if(sku_nums=='' || productIds=='') return;
	window.location = jsBasePath +'group/chooseGroupBuy.user?uid=' + uid+'&shopId='+shopId+'&sku_nums='+sku_nums+"&productIds="+productIds;
	
}
function checkShopGroup(){
	if(shopId=='') return;
	var $listProductId  = $('input[name="productId"]');
	var productIds='';
	for(var i=0;i<$listProductId.length;i++) {
		productIds+=$($listProductId[i]).val()+","
	}
	if(productIds=='') return;
	$.ajax({
		url : jsBasePath +'group/groupShopList.user?uid='+uid+'&shopId='+shopId+"&productIds="+productIds,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				var result = data.data;
				var groupBuy=null;
				if(groupId!=null && groupId != "" && groupId){
					for (var i = 0; i < result.length; i++) {
						if(groupId == result[i].id){
							groupBuy = result[i];
							break;
						}
					}
				}else{
					groupBuy = result[0];
				}
				if(groupBuy && groupBuy!=null ){
					setShopGroupInfo(groupBuy)
				}
			}
			
		},
		error : function() {}
	});
}
function setShopGroupInfo(groupBuy){
	$("#groupId").val(groupBuy.id);
	var phoneNum = groupBuy.phoneNum;
	if(phoneNum && phoneNum!=''){
		var phonehead = phoneNum.substring(0,3);
		var phoneend = phoneNum.substring(phoneNum.length-4,phoneNum.length);
		phoneNum = phonehead+"*****"+phoneend;
	}
	$("#group_name").html('团名称：'+groupBuy.groupName);
	$(".p_con .p1").html('团长：'+groupBuy.userName+' '+phoneNum+' ');
	$(".p_con .p2").html(groupBuy.address);
	
	if(groupBuy.userAvatar && groupBuy.userAvatar!=''){
		$(".user dl dt img").attr("src",groupBuy.userAvatar);
	}
	var groupBuyProductList =  groupBuy.groupBuyProductList;
	if(typeof(groupBuyProductList)!=undefined && groupBuyProductList!=null &&groupBuyProductList!=''){
		var html = ''; 
		for (var i = 0; i < groupBuyProductList.length; i++) {
			html+='<div class="swiper-slide"><a href="javascript:;"><img src="'+groupBuyProductList[i].image+'" /></a></div>';
		}
		$(".swiper-wrapper").html(html);
	}
	//queryGroupProdyct(groupBuy.id)
	$("#addShopGroup").hide();
	$("#chooseShopGroup").hide();
	$("#shopGroupInfo").show();
}