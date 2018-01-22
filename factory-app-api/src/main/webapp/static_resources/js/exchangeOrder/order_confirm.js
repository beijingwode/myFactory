
var uid=GetUidCookie();
$(document).ready(function() {
	type = 5;
	if(!sessionCheckOrder("order_confirm",type)) {
		return;
	}
	var isPageHide = false;
	window.addEventListener('pageshow', function(){
		// ios以外无效
		if(isPageHide){window.location.reload();}
	});
	window.addEventListener('pagehide', function(){isPageHide = true;});
	
	init();
	
	$(".address").attr("onclick","javascript:go2Addr();")
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	
	$(".thickdiv").click(function(e){
		$(".thickdiv").hide();
		$(".zt_message").hide();
	});
});

function toggleSel(id) {

	if($("#"+id+"_sel").val()==1) {
		//取消勾选
		$("#"+id+"_sel").val("0");
	} else {
		//选中
		$("#"+id+"_sel").val("1");
	}
	
	//自提勾选
	if(id=="selfDelivery"){
		if($("#"+id+"_sel").val()==1) {//选中
			$("#selfDelivery .selfDelivery_btm p em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"16px 16px"});
			$(".address").hide();
			$(".bottom_box i").show();
			$(".bottom_box span").css({"font-size":"1.0em"});
		} else {
			$("#selfDelivery .selfDelivery_btm p em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"16px 16px"});
			$(".bottom_box i").hide();
			$(".bottom_box span").css({"font-size":"1.6em"});
			if($("#shippingAddressId").val() == "") {
				$(".address a").show();
				$(".address").show();
			}else{
				$(".address").show();
			}
		}
		calculateTotalFreight();
	}else if(id=="freeSwap"){//调剂
		if($("#"+id+"_sel").val()==1) {
			$("#freeSwap .freeSwap_btm p em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/new_collection_check.png) no-repeat","background-size":"22px 22px"});
		}else{
			$("#freeSwap .freeSwap_btm p em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/jihuo.png) no-repeat","background-size":"22px 22px"});
		}
	}else{
		refreshCash();
	}
}
function init(){
	// 购物车结算
	exchangeOrderInit();
}

function orderSubmit() {
	if ($("#shippingAddressId").val()==''&&$("#selfDelivery_sel").val()=='0') {
		showInfoBox("收货地址不能为空");
		return;
	}
	if($("#selfDelivery_sel").val()=='1'){//选中自提
		if( checkUserInfo()){
			$(".thickdiv").show();
			$(".zt_message").show();
			return;
		}
	}
	$(".bottom_box a").attr("href","javascript:void(0);")
	var total = parseFloat($("#total").val());
	if(total > 0.0) {
		// 购物车结算
		cartCreate();
	} else {
		// 购物车结算
		showConfirm("您确认使用换领币支付此订单吗？","cartCreate()","setSubmitA()");	
	}
}
function go2SaveUser(){//保存用户信息
	var userName= $(".username").val();
	var sectionName= $(".sectionName").val();
	var allTrue = true;
	if(userName=='' && $.trim(userName)==''){
		$(".ts_message").html("请填写真实姓名");
		allTrue = false;
	}
	if(sectionName=='' && $.trim(sectionName)==''){
		$(".ts_message").html("请填写部门信息");
		allTrue =false;
	}
	if(allTrue){
		$.ajax({
			url : jsBasePath +'user/updateEmp.user?uid='+uid+"&realName="+$.trim(userName)+"&sectionName="+$.trim(sectionName),
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    cache:false,
			success : function(data) {
				if (data.success) {
					$(".thickdiv").hide();
					$(".zt_message").hide();
					cartCreate();
				}
			},
			error : function() {}
		});
	}
}
function checkUserInfo(){
	var userInfo = $(".bottom_box").data("userInfo");
	var  isShowZT = false;
	if(userInfo && userInfo.employeeType!='' && userInfo.employeeType==1){//员工
		var realName = userInfo.realName;
		var sectionName = userInfo.sectionName;
		if(realName==null || realName=="" ||typeof(realName)=="undefined"){
			isShowZT =true;
		}
		if(sectionName==null || sectionName=="" ||typeof(sectionName)=="undefined" ){
			isShowZT =true;
		}
	}
	return isShowZT;
}
function setSubmitA() {
	$(".bottom_box a").attr("href","javascript:orderSubmit();")
}
function go2Coupons(){
	$(".theme-popover-mask2").show();
	$(".popover3").show();
}
function exchangeOrderInit(){
	if(uid=='') return;
	$.ajax({
		url : jsBasePath +'exchangeOrder/confirmCart.user?partNumbers='+partNumbers+'&uid='+uid,
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
				$(".bottom_box").data("userInfo",userFactory);
				$(".username").val(userFactory.realName);
				$(".phone").val(userFactory.phone);
				$(".sectionName").val(userFactory.sectionName);
				//收货地址
				var shippingAddressList = result.shippingAddressList;
				var isSelfDelivery =true;//默认自提
				if(shippingAddressList && shippingAddressList.length > 0) { 
					var defaultAddress = shippingAddressList[0];
					var addressInfo = sessionStorage.getItem("checkAddress");
					if(typeof(addressInfo)!=undefined && addressInfo!=null &&addressInfo!=''){
						isSelfDelivery= false;
						addressInfo = JSON.parse(addressInfo);
						defaultAddress =addressInfo;
						$("#shippingAddressId").val(defaultAddress.id);
					}
					var shippingAddressId = $("#shippingAddressId").val();
					for(var i=0;i<shippingAddressList.length;i++) {
						if(shippingAddressList[i].id == shippingAddressId) {
							defaultAddress = shippingAddressList[i];
							break;
						}
					}
					if (defaultAddress.areaName==undefined ||defaultAddress.areaName==null ) {
						defaultAddress.areaName='';
					}
					var addressInfo = defaultAddress.provinceName+' '+defaultAddress.cityName+' '+defaultAddress.areaName+' '+defaultAddress.address;
			    	$(".address .p1").html('<span>收货人：'+defaultAddress.name+'</span><em>'+defaultAddress.phone+'</em>');
			    	$(".address .p2").html('收货地址：'+addressInfo);
			    	$("#shippingAddressId").val(defaultAddress.id);
			    	$("#name").val(defaultAddress.name);
			    	$("#mobile").val(defaultAddress.phone);
			    	$("#address").val(addressInfo);
				} else {
					$(".address p").hide();
					$(".address a").show();
					$("#shippingAddressId").val("");
			    	$("#name").val("");
			    	$("#mobile").val("");
			    	$("#address").val("");
				}
			    //商品
			    var cartItems=result.cart;
			    var totalPrice = 0;
			    var totalfuli = 0;//所有商品应共用内购券数
			    var fuli = 0;
				var cart = result.cart;
				var totalRealPrice = 0;//所有商品内购价
				for (var k = 0; k < cart.length; k++) {
					 html +='<div class="main_con">';
					 html +='<div class="link" id="shopName" onclick="go2ShopByShopId('+cart[k].shopId+')">';
					    if (userFactory.supplierId==cart[k].supplierId) {
					    	html +="自家";
						}else{
							html +=cart[k].supplierName;
						}
					    html +='<label id="supplierFreeString'+cart[k].supplierId+'" style="float:right;color:#c22936;">';
					    if(freeMap!=null && freeMap!='' && typeof(freeMap)!="undefined"){
					    	html+=freeMap[cart[k].supplierId];
					    }
					    html +='</label>';
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
					    html +='<dd class="dd3"><p>￥<span>'+((cartItems[i].realPrice)).toFixed(2)+'<img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" width="22px" height="22px"/></span></p><em>X<i>'+cartItems[i].quantity+'</i><em></dd>';
					    html +='<input type="hidden" name="skuId" value="'+cartItems[i].partNumber+'">';
					    html +='<input type="hidden" name="skuNum" value="'+cartItems[i].quantity+'">';
					    html +='<input type="hidden" name="skuFreights" value="0">';
					    var skuCompanyTicket = cartItems[i].maxCompanyTicket*cartItems[i].quantity;
					    var prvProductRealPrice = cartItems[i].realPrice*cartItems[i].quantity;//单个商品真实价格
					    totalfuli += skuCompanyTicket;
					    totalRealPrice+=prvProductRealPrice;
					    html +='<input type="hidden" name="skuCompanyTicket" value="'+skuCompanyTicket+'">';
					    html +='<input type="hidden" name="maxCompanyTicket" value="'+cartItems[i].maxCompanyTicket+'">';
					    
					    html +='<input type="hidden" name="skuExchangeTicket" value="'+cartItems[i].benefitTicket+'">';
					    html +='<input type="hidden" name="skuExchangeCash" value="'+cartItems[i].benefitAmount+'">';
					    html +='<input type="hidden" name="skuExchangeSelf" value="'+cartItems[i].benefitSelf+'">';
					    
					    html +='</a>'
					    html +='</dl>';
					    totalPrice +=(cartItems[i].realPrice+cartItems[i].maxCompanyTicket)*cartItems[i].quantity;
					    
				    }
				    html +='<input type="hidden" name="freightAdd" id="freightAdd" value="'+cart[k].supplierId+'">'
				    html +='<div class="yunfei">';
				    html +='<p id="freight_'+cart[k].supplierId+'">运费：<span>￥0.00</span></p>';
				    html +='<input class="text_input" type="text" id="note" data="'+cart[k].supplierId+'" value="" placeholder="给卖家留言">'
		            html +='</div>';
				    html +='</div>';
				}
			    $("#orderPrdouct").html(html);
			    $("#huanling").show();
			    if (result.userExchage>result.exchangeCanUse) {//用户换领币大于商品所需 默认选中
			    	$("#huanling").html('换领币：￥'+result.exchangeCanUse.toFixed(2)+'&nbsp;<i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" width="22px" height="22px"/></i><span></span>');
			    	$("#totalhuanling").val(result.exchangeCanUse);
				}else{
					$("#huanling").html('换领币：￥'+result.userExchage.toFixed(2)+'&nbsp;<i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" width="22px" height="22px"/></i><span></span>');
			    	$("#totalhuanling").val(result.userExchage);
				}
			   	$("#huanling_sel").val(1);
			   	$("#huanling span").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/new_collection_check.png) no-repeat","background-size":"22px 22px"});
			   	//调剂
			   //$("#freeSwap").show();
			   	//内购券隐藏
			   	$("#fuli").hide();
			    $("#totalfuli").val(totalfuli);
				//现金券隐藏
			    $("#xianjin").hide();
		    	$("#xianjin_user").val(0);
		    	
		    	$("#totalPrice").val(totalPrice);
				//计算运费(TODO 换领单 已选自提则直接计算运费，否则先选自提 临时)
		    	if(isSelfDelivery){
					toggleSel("selfDelivery");
				}
		    	calculateTotalFreight();
		    	/*if($("#selfDelivery_sel").val()==1) {
		    	} else {
		    		toggleSel("selfDelivery");
		    	}*/
				//调剂商品
				//adjustProduct(totalRealPrice);
			} else {
				showInfoBox(data.msg);
				setTimeout(back,1500);
			}
		},
		error : function() {}
	});
}
function back() {
	history.back();
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
	var url=jsBasePath +'exchangeOrder/create.user?fromType=weixin&uid='+uid;
	var data={"userId":uid,"name":$("#name").val(),"mobile":$("#mobile").val(),"address":$("#address").val(),"message":message,
				"useCompanyTicket":useCompanyTicket,"useExchangeTicket":useExchangeTicket,"useExchangeCash":useExchangeCash,"useExchangeSelf":useExchangeSelf,
				"useCash":useCash,"shippingAddressId":$("#shippingAddressId").val(),"sku_nums":sku_nums,"sku_freights":sku_freights,
				"selfDelivery":$("#selfDelivery_sel").val(),"freeSwap":$("#freeSwap_sel").val()};

	if(url=='' || uid=='') return;
	$.ajax({
		url : url,
		type : "POST",
		dataType: "json",  //返回json格式的数据  
		data:data,  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html='';

			if (data.success) {
				//删除选中地址
				var checkAddress = sessionStorage.getItem("checkAddress");
	    		if(checkAddress && checkAddress!=null && checkAddress!=''){
	    			sessionStorage.removeItem("checkAddress");
	    		}
	    		var pageNum = sessionStorage.getItem("pageNum");
	    		if(pageNum && pageNum!=null && pageNum!=''){
	    			sessionStorage.removeItem("pageNum");
	    		}
				var result = data.data;
				if(productIds!="" && productIds && productIds!=null){
					var productIdArr =productIds.split(",");
					for (var i = 0; i < productIdArr.length; i++) {
						var pageStock =sessionStorage.getItem("pageStock"+productIdArr[i]);
						if(pageStock && pageStock!=null){
							sessionStorage.removeItem("pageStock"+productIdArr[i]);//移除
						}
					}
				}
				if(result.status == 1) {
					sessionSetOrderStep("payZero");
					sessionStorage.setItem("order", "orderConfirm");
					window.location = jsBasePath+'pay/pay_success.user?uid='+uid+'&orderId='+result.orderId+'&suborderId='+'&fromWay='+orderType+'&type=5';
				} else {
					sessionSetOrderStep("created");
					window.location=jsBasePath +'pay/page.user?uid='+uid+'&orderId='+result.orderId+'&totalFee='+(result.realPrice-useCash)+'&orderType='+orderType+'&pruductId='+"&backNum="+backNum+'&type=5';
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
	var $listCompanyTicket  = $('input[name="skuCompanyTicket"]');
	var $listFreights  = $('input[name="skuFreights"]');
	var selfDelivery=$("#selfDelivery_sel").val();
	//var useCompanyTicket="";
	var sku_nums="";
	var nums=[];
	var fuli_sel = $("#fuli_sel").val();
	for(var i=0;i<$listSku.length;i++) {
		sku_nums += $($listSku[i]).val() + "_" + $($listNum[i]).val()+",";
	}
	if(sku_nums.length>0) {
		sku_nums= sku_nums.substring(0,sku_nums.length-1)
	}
	if(selfDelivery !="1") {
		var pageNum = sessionStorage.getItem("pageNum");
		if (pageNum == null || typeof(pageNum)==undefined ||pageNum=='') {
			if($("#shippingAddressId").val() == "") {
				showInfoBox("请先添加收货地址");
				sessionStorage.setItem("pageNum", 1);
				setTimeout(go2Addr, 1500);
				return;
			}
		}
	}
	$.ajax({
		url : jsBasePath +'order/newCalculateFreight.user?shippingAddressId='+$("#shippingAddressId").val()+'&uid='+uid+'&sku_nums='+sku_nums+'&selfDelivery='+selfDelivery,
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
			    		//showInfoBox("配送不到当前收货地址,请选择其他收货地址");
			    		blnExp=true;
			    		oldData[i]=0;
					}else if(9999==oldData[i]){
						//showInfoBox("已超过限购数量,请减少购买数量");
						blnExp=true;
						oldData[i]=0;
					}
					$($listFreights[i]).val(oldData[i]);
					sum+=oldData[i];
			    }
			    
			    $("#totalFreight").val(sum);
			    if (blnExp) {
			    	$(".yunfeiheji span").html('存在超限购买商品');
			    	$(".bottom_box a").attr("class","disable");
			    	$(".bottom_box a").attr("disabled","true");
			    	$(".bottom_box a").attr("href","javascript:void(0);")
				}else{
					$(".yunfeiheji span").html('￥'+sum.toFixed(2));
					//if(isMaxCompanyTicket){
						$(".bottom_box a").removeClass("disable");
				    	$(".bottom_box a").removeAttr("disabled");
				    	$(".bottom_box a").attr("href","javascript:orderSubmit();")
					//}
				}
		    	
		    	//计算商家显示信息
				$(".main_con").each(
						function(){
							var freightSupplier = parseFloat("0", 10);
							var $listFreight1 = $(this).find('input[name="skuFreights"]');
							for(var i=0;i<$listFreight1.length;i++) {
								freightSupplier+=parseFloat($($listFreight1[i]).val(),10);
							}
							var supplierId = $(this).find('input[id="freightAdd"]').val();
							
							$("#freight_"+supplierId).html("运费：<span>￥"+parseFloat(freightSupplier,10).toFixed(2)+"</span>");
						  }		
				);
				//刷新商家包邮提示
				dealFreeString(freeMap);
				//计算价格
		    	refreshCash();
			}else{
				//计算价格
		    	refreshCash();
				showInfoBox(data.msg);
				var pageNum = sessionStorage.getItem("pageNum");
				if (pageNum == 1) {
					$(".yunfeiheji span").html('请先指定收货地址');
					//$("#freight .freight_top").html('运费<span>请先指定收货地址</span>');
				}
			}
		},
		error : function() {}
	});
}

function go2Addr() {
	if($("#shippingAddressId").val() == "") {
		//添加地址
		window.location=jsBasePath+"address/newAddress.user?uid="+uid+"&backNum="+backNum+"&pageId=0&orderType="+orderType+"&partNumbers=" + partNumbers + "&productId=" + "&specificationsId="+specificationsId+"&quantity="+quantity;
	} else {
		//选择地址
		window.location=jsBasePath+"address/page?backNum="+backNum+"&orderType="+orderType+"&partNumbers=" + partNumbers + "&productId=" + "&specificationsId="+specificationsId+"&quantity="+quantity;
	}
}

function refreshCash(){
	var totalPrice = parseFloat($("#totalPrice").val());
	var totalFreight = parseFloat($("#totalFreight").val());
	var totalfuli = parseFloat($("#totalfuli").val());
	var totalhuanling = parseFloat($("#totalhuanling").val());
	var couponTicket = parseFloat($("#coupon_ticket").val());
	var couponCash = parseFloat($("#coupon_cash").val());
	var totalCash = parseFloat(0.0);

	var $listExchange  = $('input[name="skuExchange"]');
	for(var i=0;i<$listExchange.length;i++) {
		totalhuanling += parseFloat($($listExchange[i]).val());
	}
	
	//总额=商品总价
	var total=totalPrice;
	
	//总额=商品总价-内购券-内购优惠券-现金优惠券
	//if($("#fuli_sel").val()==1) {
		total = total-totalfuli-couponTicket-couponCash;
		total = parseFloat(total.toFixed(2));
	//}
	//总额=商品总价-内购券-换领券
	total = total-totalhuanling;
	total = parseFloat(total.toFixed(2));
	//总额=商品总价-内购券-换领券+运费
	total = total+ totalFreight;
	total = parseFloat(total.toFixed(2));
	//总额=商品总价-内购券-换领券+运费-现金券
	var xianjin_user=parseFloat($("#xianjin_user").val());
	if(total>xianjin_user) {
		totalCash = xianjin_user;
	} else {
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
function go2App(){
	window.location="http://www.wd-w.com/app.htm?d=1";
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
function dealFreeString(array){
	if(array){
		for(var key in array)  {
			$("#supplierFreeString" + key).html(array[key]);
		}
	}
}