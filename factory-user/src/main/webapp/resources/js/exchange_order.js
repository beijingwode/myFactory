$().ready(function() {
	wode.checkLogin();
	dealError();
	var $createOrder = $("#createOrder");
	
	//计算运费
	calculateTotalFreight();
	
	$("#orderForm").submit(function(){
		
		if($("#total").html()=="0.00"){
			wode.showBox("支付确认","使用￥"+$("#hidExchangeUse").val()+"换领币支付");
			$("#boxCheck").attr("onclick","ajaxCreate()");
		}else{
			ajaxCreate();
		}
	})
});

function refreshCash(){
	var totalPrice = parseFloat($("#totalPrice").val());
	var totalFreight = parseFloat($("#totalFreight").val());
	var hidExchangeUse = parseFloat($("#hidExchangeUse").val());

	var total=totalPrice+totalFreight-hidExchangeUse;
	total =parseFloat(total.toFixed(2));
	
	$("#total").html(total.toFixed(2));	
}

function calculateTotalFreight() {
	var $shippingAddressId= $("#shippingAddressId");
	var selfDelivery= $("#selfDelivery").val();
	var $addressRadio  = $('input[name="addressRadio"]:checked');
	if(typeof($addressRadio.val()) =='undefined' && $shippingAddressId.val()== -1){
		wode.showBox("错误","请选择收货地址",{hideBtn:true});
		return;
	}

	var value = $addressRadio.val();
	var $listSku  = $('input[id="partNumber"]');
	var $listNum  = $('input[id="quantity"]');
	
	var skus=[];
	var nums=[];
	for(var i=0;i<$listSku.length;i++) {skus[i]=$($listSku[i]).val();}
	for(var i=0;i<$listNum.length;i++) {nums[i]=$($listNum[i]).val();}

	var useCompanyTicket = $("#useTicket").html();//使用的内购券
	$.ajax({
		url : "/order/calculateTotalFreight",
		type : "POST",
		data : {
			"selfDelivery":selfDelivery,"shippingAddressId":value,"skus": skus,"nums":nums
		},
		dataType : "json",
		cache : false,
		beforeSend : function() {
			// $submit.prop("disabled", true);
		},
		success : function(data) {
			if(data.success){
				
				var $listFreight  = $('input[id="freight"]');
				
				var freight = 0;
				var blnExp = false;
				for(var i=0;i<data.data.oldData.length;i++) {
					var c = data.data.oldData[i];
					if(c>=8888) {
						blnExp = true;
						c=0;
					}
					$($listFreight[i]).val(c);
					freight+=c;
				}
				
				var totalFreight  = Number($("#totalFreight").val());
				if (blnExp) {
					$("#spantotalFreight").html("存在超限购买商品");
					$(".btn_area-new").css("background","#666");
					$("#createOrder").attr("href","javascript:void(0);");
				}else{
					$("#spantotalFreight").html("￥"+freight.sub(0,2));
					$(".btn_area-new").css("background","#ff6161");
					$("#createOrder").attr("href","javascript:go2CreateOrder();");
				}
				if(freight == "0" || freight == 0){
					$("#labelShipping").html("运费 ￥ 0.00");
				}
				$("#totalFreight").val(freight);

				//计算商家显示信息
				$(".shopping_list").each(
					function(){
						var freightSupplier = parseFloat("0", 10);
						var $listFreight1 = $(this).find('input[id="freight"]');
						for(var i=0;i<$listFreight1.length;i++) {
							freightSupplier+=parseFloat($($listFreight1[i]).val(), 10);
						}
						var supplierId = $(this).find('input[id="freightAdd"]').val();
						
						$("#labelShipping"+supplierId).html("运费 ￥"+parseFloat(freightSupplier,10).toFixed(2));
					  }
				);
				
				dealFreeString(data.data.freeMap);
				refreshCash();
				dealError();
			}else{
				wode.showBox("错误",data.msg,{hideBtn:true});
				if (data.msg =="请先指定收货地址") {
					$("#spantotalFreight").html("请先指定收货地址");
				}
			}
		},
		complete : function() {
			// $submit.prop("disabled", false);
		}
	});
}

function ajaxCreate() {
	var $t_name = $("input[name='name']");
	var $address = $("input[name='address']");
	var $mobile = $("input[name='mobile']");
	var name = "";
	var mobile = "";
	var address = "";
	var selfDelivery= $("#selfDelivery").val();
	if(selfDelivery == "1") {
	} else {
		if($("input[name=collectingPointCheck]").attr("checked")==undefined){
			name = $t_name.val();
			mobile = $mobile.val();
			address = $address.val();
		}else{
			name = $("#shipping_name").val();
			mobile = $("#shipping_phone").val();
			address = $("#shipping_address").val();
		}
	}
	var invoiceTitle = "";
	var message = "";
	$("input[name='note']").each(function(i) {
		if ($(this)) {

			var str = $(this).val();
			str = str.replace(/[\_|\,]/g, " ");
			message += $(this).attr("data") + "_" + str + ",";
		}
	})
	var usePoint = 0;
	var useCompanyTicket = 0;
	var $addressRadio  = $('input[name="addressRadio"]:checked');
	var $listSku  = $('input[id="partNumber"]');
	var $listFreight  = $('input[id="freight"]');
	var $listSku  = $('input[id="partNumber"]');
	var $listExchangeTicket  = $('input[name="skuExchangeTicket"]');
	var $listExchangeCash  = $('input[name="skuExchangeCash"]');
	var $listExchangeSelf  = $('input[name="skuExchangeSelf"]');
	
	var skus=[];
	var freights=[];
	var useExchangeTicket,useExchangeCash,useExchangeSelf;
	useExchangeTicket="";
	useExchangeCash="";
	useExchangeSelf="";
	for(var i=0;i<$listSku.length;i++) {
		skus[i]=$($listSku[i]).val();
		useExchangeTicket += skus[i]+"_" + $($listExchangeTicket[i]).val()+",";
		useExchangeCash += skus[i]+"_" + $($listExchangeCash[i]).val()+",";
		useExchangeSelf += skus[i]+"_" + $($listExchangeSelf[i]).val()+",";
	}

	if(useExchangeTicket.length > 0) {
		useExchangeTicket = useExchangeTicket.substring(0,useExchangeTicket.length-1);
		useExchangeCash = useExchangeCash.substring(0,useExchangeCash.length-1);
		useExchangeSelf = useExchangeSelf.substring(0,useExchangeSelf.length-1);
	}
	for(var i=0;i<$listFreight.length;i++) {
		freights[i]=$($listFreight[i]).val();
	}
	var freeSwap = "1";
	if(!$('#freeSwap').prop("checked")) {
		freeSwap="0";
	}
	$.ajax({
		url : "/exchangeOrder/create",
		type : "POST",
		data : {
			"name" : name,
			"mobile" : mobile,
			"selfDelivery" : selfDelivery,
			"address" : address + "(电话:" + mobile + ")",
			"invoiceTitle" : invoiceTitle,
			"message" : message,
			"usePoint" : 0,
			"useCompanyTicket" : 0,
			"useExchangeTicket" : useExchangeTicket,// 商品的价格
			"useExchangeCash" : useExchangeCash,// 商品的价格
			"useExchangeSelf" : useExchangeSelf,// 商品的价格
			"useCash" : 0,						// 使用的总现金券
			"addressRadio" : $addressRadio.val(),
			"skus" : skus,
			"freights" : freights,
			"freeSwap" : freeSwap
		},
		dataType : "json",
		cache : false,
		beforeSend : function() {
			// $submit.prop("disabled", true);
		},
		success : function(data) {
			if (data.success) {
				window.location = data.data;
			} else {
				var msg = data.msg;
				
				if(msg.indexOf("需要先回答商家问卷才能下单")>-1) {
					// 试用问卷
					wode.showBox("回答问卷",msg);
					$("#boxCheck").attr("onclick","toQuestionnaire('"+data.data+"')");
					
				} else {
					wode.showBox("错误", msg, {
						hideBtn : true
					});	
				}
			}
		},
		complete : function() {
			// $submit.prop("disabled", false);
		}
	});
}

function go2CreateOrder(){
	var selfDelivery= $("#selfDelivery").val();
	//如果代收点复选框是未选中状态,订单地址用的是常用地址
	if($("input[name=collectingPointCheck]").attr("checked")==undefined){
		if(selfDelivery == "1") {			
		} else {
			if($("input[name='name']").val() == "" || $("input[name='address']").val() == "" || $("input[name='mobile']").val() == ""){
				wode.showBox("错误","请保存收货地址",{hideBtn:true});
				return;
			}
		}
		$("#orderForm").submit();
	}else{
		$("#orderForm").submit();
	}
}

function toQuestionnaire(qId) {
	window.location = "/member/questionnaire"+qId;
}
//处理商家包邮信息
function dealFreeString(array){
	if(array){
		for(var key in array)  {
			$("#supplierFreeString" + key).html(array[key]);
		}
	}
}

function dealError(){
	var errormsg= $("#infoError").val();
	if(errormsg && errormsg!="" && errormsg!=null){
		wode.showBox("错误",errormsg,{hideBtn:true});
		$(".btn_area-new").css("background","#666");
		$("#directCreate").attr("href","javascript:void(0);");
		$("#createOrder").attr("href","javascript:void(0);");
	}
}
