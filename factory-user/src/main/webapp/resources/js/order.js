$().ready(function() {
	wode.checkLogin();
	dealError();

	var $saveInvoice = $("#saveInvoice");
	var $createOrder = $("#createOrder");
	var $directCreate = $("#directCreate");
	var $useNewInvoice = $("#useNewInvoice");
	var $newInvoiceDiv = $(".edit_bill_wrap");  
	var $showInvoice= $("#showInvoice");
	var $editInvoiceDiv= $(".edit_bill");
	
	var $invoiceTitle = $("input[name='invoiceTitle']");
	var $delInvoice =$("a[name='delInvoice']");
	var $editInvoice = $("a[name='editInvoice']");
	var editInvoiceFlag = false;//发票编辑标识
	var $editInvoiceRadio=$('input[name="invoiceRadio"]');
	
	//初始化现金余额
	var cashBalance = Number($("#cashBalance").val());
	if(cashBalance>0) {
		$("#cashChecked").change(function(){
			refreshCash();
		});
	}

	$editInvoice.click(editInvoice);
	//选择发票地址 radio
	$editInvoiceRadio.change(editInvoiceRadio);
	
	//显示使用新发票DIV  重新绑定事件
	  $useNewInvoice.unbind("change").change(useNewInvoice);
	
	//显示修改地址DIV
	$showInvoice.click(function() {
		var display = $editInvoiceDiv.css("display");
		if(display == "none"){
			$editInvoiceDiv.show(500);
			$("#invoiceInfomation").hide();
		}else if(dispaly = "block"){
			$editInvoiceDiv.hide(500);
		}
	});
	//计算运费
	calculateTotalFreight();
	
	$('#invoice_title').focus(
			function (){
				$(this).parent().find(".hinterror").text("");
			}
	); 
	$('#taxpayerNumber').focus(
			function (){
				$(this).parent().find(".hinterror5").text("");
			}
	);
	$('#registerAddress').focus(
			function (){
				$(this).parent().find(".hinterror1").text("");
			}
	);
	$('#registerPhone').focus(
			function (){
				$(this).parent().find(".hinterror2").text("");
			}
	);
	$('#openingBan').focus(
			function (){
				$(this).parent().find(".hinterror3").text("");
			}
	);
	$('#openingBanNumber').focus(
			function (){
				$(this).parent().find(".hinterror4").text("");
			}
	);
	//
	$saveInvoice.click(function() {
		var $invoiceRadio  = $('input[name="invoiceRadio"]:checked');
		if(typeof($invoiceRadio.val()) =='undefined'){
			wode.showBox("错误","请完善发票信息",{hideBtn:true});
			return;
		}
		var falg = editInvoiceFlag;
		if($invoiceRadio.val() == 0 || falg == true){
			var invoiceId=$("#invoiceId").val();
			var title=$.trim($("#invoice_title").val());
			var type = $("input[name='invoice_type']:checked").val();
			var billType;
			var taxpayerNumber;
			var registerAddress;
			var registerPhone;
			var openingBan;
			var openingBanNumber;
			if(type == 2){
				billType = $("input[name='billType']:checked").val();
				taxpayerNumber = $.trim($("#taxpayerNumber").val());
				if(billType == 1){
					registerAddress = $.trim($("#registerAddress").val());
					registerPhone = $.trim($("#registerPhone").val());
					openingBan = $.trim($("#openingBan").val());
					openingBanNumber = $.trim($("#openingBanNumber").val());
				}
			}
			if(title == "" && type == 2){
				$("#invoice_title").parent().find(".hinterror").text("请输入发票抬头");
				return;
			}
			if(title == "" && type == 1){
				var $t_name = $("input[name='name']");
				title = $t_name.val();//个人发票，不填，默认是 收货人
			}
			if(typeof(type) =='undefined'){
				$("#invoice_title").parent().find(".hinterror").text("请选择发票类型");
				return;
			}
			if(type == 2){
				if(taxpayerNumber == "" || taxpayerNumber == null){
					$("#taxpayerNumber").parent().find(".hinterror5").text("请输入纳税人识别号");
					return;
				}
				if(billType == 1){
					if(registerAddress == "" || registerAddress == null){
						$("#registerAddress").parent().find(".hinterror1").text("请输入注册地址");
						return;
					}
					if(registerPhone == "" || registerPhone == null){
						$("#registerPhone").parent().find(".hinterror2").text("请输入注册电话");
						return;
					}
					if(openingBan == "" || openingBan == null){
						$("#openingBan").parent().find(".hinterror3").text("请输入开户行");
						return;
					}
					if(openingBanNumber == "" || openingBanNumber == null){
						$("#openingBanNumber").parent().find(".hinterror4").text("请输入开户行账号");
						return;
					}
				}
			}
			$.ajax({
				url : "saveInvoice",
				type : "POST",
				data : {
					"id":invoiceId,"title" : title,"type":type,
					"billType":billType,"taxpayerNumber":taxpayerNumber,
					"registerAddress":registerAddress,"registerPhone":registerPhone,
					"openingBan":openingBan,"openingBanNumber":openingBanNumber
				},
				dataType : "json",
				cache : false,
				beforeSend : function() {
					// $submit.prop("disabled", true);
				},
				success : function(data) {
					if(data.success){
						editInvoiceFlag = false;
						var value = "";
						if(type == 1){
							value = "个人";
						}else{
							value = "单位";
						}
						var str = "<span>发票抬头："+title+"</span><span>发票类型："+value+"</span>";
						$("#invoiceInfo").html(str);
						$invoiceTitle.val(title);
						$("#invoiceId").val(data.data.id);
						$("#inId").val(data.data.id);
						var invoiceStr ="<input type=\"hidden\" value=\""+data.data.id+"\" name=\"i_invoiceId\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.title+"\" name=\"i_title\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.type+"\" name=\"i_type\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.billType+"\" name=\"billType\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.taxpayerNumber+"\" name=\"taxpayerNumber\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.registerAddress+"\" name=\"registerAddress\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.registerPhone+"\" name=\"registerPhone\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.openingBan+"\" name=\"openingBan\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.openingBanNumber+"\" name=\"openingBanNumber\">"+"\n"
						+"<input type=\"radio\" value=\""+data.data.id+"\" name=\"invoiceRadio\" class=\"selradio\" checked=\"checked\">"+"\n"
						+""+value+""+"\n"
						+"<b>"+data.data.title+"</b>"+"\n"
						+"<span><a name=\"editInvoice\" href=\"javascript:void(0);\">编辑</a></span>"+"\n"
						+"<strong><a name=\"delInvoice\" href=\"javascript:void(0);\">删除</a></strong>"+"\n";
						if(falg== true){//替换
							$("input[name='invoiceRadio'][value='"+invoiceId+"']").parent().html(invoiceStr);
						}else{//追加
							$(".newbill").prev().remove();
							$(".newbill").before("<p>"+"\n"+invoiceStr+"</p>"+"\n");
						}
						var display = $newInvoiceDiv.css("display");
						if(dispaly = "block"){
							$newInvoiceDiv.hide(500);
						}
						$editInvoiceDiv.hide(500);
						$("#invoiceInfo").css("display","block");
						$("#invoiceInfomation").show();
						//重新绑定事件
						$("a[name='delInvoice']").unbind('click').click(delInvoice);//发票编辑 删除事件
						$("a[name='editInvoice']").unbind('click').click(editInvoice);//发票编辑 编辑事件
						$('input[name="invoiceRadio"]').unbind("change").change(editInvoiceRadio);//发票选择 单选按钮事件
						$("#useNewInvoice").unbind("change").change(useNewInvoice);//使用新发票 单选按钮事件
					}else{
						editInvoiceFlag = false;
						wode.showBox("错误",data.msg,{hideBtn:true});
					}
				},
				complete : function() {
					// $submit.prop("disabled", false);
				}
			});
		}else{
			var invoiceId = $invoiceRadio.parent().find("input[name='i_invoiceId']").val();
			var title = $invoiceRadio.parent().find("input[name='i_title']").val();
			var type = $invoiceRadio.parent().find("input[name='i_type']").val();
			$.ajax({
				url : "saveInvoice",
				type : "POST",
				data : {
					"invoiceId":invoiceId,"title" : title,"type":type
				},
				dataType : "json",
				cache : false,
				beforeSend : function() {
					// $submit.prop("disabled", true);
				},
				success : function(data) {
					if(data.success){
						var value = "";
						if(type == 1){
							value = "个人";
						}else{
							value = "单位";
						}
						var str = "<span>发票抬头："+title+"</span><span>发票类型："+value+"</span>";
						$("#invoiceId").val(invoiceId);
						$("#invoiceInfo").html(str);
						$invoiceTitle.val(title);
						$editInvoiceDiv.hide(500);
						$("#invoiceInfomation").show();
					}else{
						wode.showBox("错误",data.msg,{hideBtn:true});
					}
				},
				complete : function() {
					// $submit.prop("disabled", false);
				}
			});
		}
		

	});
	
	$delInvoice.click(delInvoice);
	
	$("#orderForm").submit(function(){
		
		if($("#total").html()=="0.00"){
			wode.showBox("支付确认","使用"+$("#getCashExchange").html()+"现金券支付");
			$("#boxCheck").attr("onclick","ajaxCreate()");
		}else{
			ajaxCreate();
		}
	});

	function delInvoice() {
		var $invoice=$(this).parent().parent();
		var invoiceId = $invoice.find("input[name='i_invoiceId']").val();
		
		$.ajax({
			url : "deleteInvoice",
			type : "POST",
			data : {
				"invoiceId":invoiceId
			},
			dataType : "json",
			cache : false,
			success : function(data) {
				if(data.success){
					if($("#invoiceId").val() == invoiceId){//若删除的发票信息，是当前选中，清空发票信息
						$("#invoiceId").val("");
						$("#invoiceInfo").html("");
					}
					$invoice.remove();
				}else{
					wode.showBox("错误",data.msg,{hideBtn:true});
				}
			}
		});
	}
	
	var type = 1;
	var billType = 0; 
	
	function editInvoice() {
		var $invoice=$(this).parent().parent();
		$invoice.find("input[name='invoiceRadio']").attr("checked","checked");//让编辑的发票信息 选中
		var invoiceId = $invoice.find("input[name='i_invoiceId']").val();
		$("input[name='invoiceRadio'][value='"+invoiceId+"']");
		var title = $invoice.find("input[name='i_title']").val();
		type = $invoice.find("input[name='i_type']").val();
		billType = $invoice.find("input[name='billType']").val();
		var taxpayerNumber = $invoice.find("input[name='taxpayerNumber']").val();
		var registerAddress = $invoice.find("input[name='registerAddress']").val();
		var registerPhone = $invoice.find("input[name='registerPhone']").val();
		var openingBan = $invoice.find("input[name='openingBan']").val();
		var openingBanNumber = $invoice.find("input[name='openingBanNumber']").val();
		$("#invoiceId").val(invoiceId);
		$("#inId").val(invoiceId);
		$("#invoice_title").val(title);
		$("input[name='invoice_type'][value='"+type+"']").attr("checked","checked");
		if(type == 2){
			document.getElementById("oDiv").style.display="";
			$("input[name='billType'][value='"+billType+"']").attr("checked","checked");
			$("#taxpayerNumber").val(taxpayerNumber);
			if(billType == 1){
				document.getElementById("oDiv1").style.display="";
				$("#registerAddress").val(registerAddress);
				$("#registerPhone").val(registerPhone);
				$("#openingBan").val(openingBan);
				$("#openingBanNumber").val(openingBanNumber);
			}else{
				$('#oDiv1 input[type="text"]').val("");
				$('input[type="radio"][name="billType"][value="0"]').attr("checked",true);
			}
		}else{
			$('#oDiv input[type="text"]').val("");
		}
		var display = $newInvoiceDiv.css("display");
		if(display == "none"){
			$newInvoiceDiv.show(500);
		}
		editInvoiceFlag = true;
	};
	$('input[name="billType"]').click(function () { 
		   if($(this).val() == 1){
			   document.getElementById("oDiv1").style.display="";
		   }else{
			   document.getElementById("oDiv1").style.display="none";
			   if(billType == 0){
				   $('#oDiv1 input[type="text"]').val("");
			   }
		   }
		});
	
	$('input[name="invoice_type"]').click(function(){
		   if($(this).val() == 2){
			   document.getElementById("oDiv").style.display="";
			   $('input[name="billType"]').click(function () { 
				   if($(this).val() == 1){
					   document.getElementById("oDiv1").style.display="";
				   }else{
					   document.getElementById("oDiv1").style.display="none";
					   if(billType == 0){
						   $('#oDiv1 input[type="text"]').val("");
					   }
				   }
				});
			   if(billType == 1){
				   document.getElementById("oDiv1").style.display="";
			   }else{
				   $('input[type="radio"][name="billType"][value="0"]').attr("checked",true);
			   }
		   }else{
			   if(type == 1){
				   $('#oDiv1 input[type="text"]').val("");
				   $('#oDiv input[type="text"]').val("");
				   $('input[type="radio"][name="billType"][value="0"]').attr("checked",true);
			   }
			   document.getElementById("oDiv").style.display="none";
			   document.getElementById("oDiv1").style.display="none";
		   }
	  });

	function useNewInvoice() {
		editInvoiceFlag=false;
		$("#invoiceId").val("0");
		$("#invoice_title").val("");
		$('#oDiv1 input[type="text"]').val("");
		$('#oDiv input[type="text"]').val("");
		$("input[name='invoice_type'][value='1']").attr("checked","checked");
		document.getElementById("oDiv").style.display="none";
		document.getElementById("oDiv1").style.display="none";
		$('input[type="radio"][name="billType"][value="0"]').attr("checked",true);
		var display = $newInvoiceDiv.css("display");
		if(display == "none"){
			$newInvoiceDiv.show(500);
		}
	};

	function editInvoiceRadio(){
		  editInvoiceFlag = false;
		  var display = $newInvoiceDiv.css("display");
			if(dispaly = "block"){
				$newInvoiceDiv.hide(500);
			}
	};
	
	
});

function refreshCash(){
	var totalPrice = parseFloat($("#totalPrice").val());
	var totalFreight = parseFloat($("#totalFreight").val());
	var ticketExchange = 0;
	var hidExchangeUse = parseFloat($("#hidExchangeUse").val());

	var total=totalPrice+totalFreight-ticketExchange-hidExchangeUse;
	total =parseFloat(total.toFixed(2));
	
	if($("#cashChecked").attr("checked")=="checked"){
		var cashExchange = parseFloat($("#hidCashExchange").val());
		var cashBalance = parseFloat($("#cashBalance").val());
		if(total>cashBalance) {
			$("#getCashExchange").html(cashBalance.toFixed(2));
			$("#hidCashExchange").val(cashBalance.toFixed(2));
			$("#cashExchange").html(cashBalance.toFixed(2));
		} else {
			$("#getCashExchange").html(total.toFixed(2));
			$("#hidCashExchange").val(total.toFixed(2));
			$("#cashExchange").html(total.toFixed(2));
		}
	} else {
		$("#getCashExchange").html("0.00");
		$("#hidCashExchange").val("0.00");
		$("#cashExchange").html("0.00");
		
	}
	total=total-parseFloat($("#hidCashExchange").val());
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
		url : "calculateTotalFreight",
		type : "POST",
		data : {
			"selfDelivery":selfDelivery,"shippingAddressId":value,"useCompanyTicket":useCompanyTicket,"skus": skus,"nums":nums
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
					$(".btn_area-new").addClass("btn_area-new_gray");
					$("#directCreate").attr("href","javascript:void(0);");
					$("#createOrder").attr("href","javascript:void(0);");
				}else{
					$("#spantotalFreight").html("￥"+freight.sub(0,2));
					$(".btn_area-new").addClass("btn_area-new_red");
					$("#directCreate").attr("href","javascript:go2Submit();");
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
				
				//dealFreeString
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
	var $invoiceTitle = $("input[name='invoiceTitle']");
	var $invoiceId = $("input[name='inId']");
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
	var invoiceTitle = $invoiceTitle.val();
	var invoiceId = $invoiceId.val();
	var message = "";
	$("input[name='note']").each(function(i){
		if($(this)){
			
				var str=$(this).val();
				  str=str.replace(/[\_|\,]/g," "); 
				message+=$(this).attr("data")+"_"+ str+",";
		}
	})
	var useCompanyTicket = 0;
		useCompanyTicket = $("#useTicket").html();//内购券使用总额

	var $addressRadio  = $('input[name="addressRadio"]:checked');

	var $listSku  = $('input[id="partNumber"]');
	var $listNum  = $('input[id="quantity"]');
	var $listFreight  = $('input[id="freight"]');
	var $listExchangeTicket  = $('input[name="skuExchangeTicket"]');
	var $listExchangeCash  = $('input[name="skuExchangeCash"]');
	var $listExchangeSelf  = $('input[name="skuExchangeSelf"]');
	
	var skus=[];
	var freights=[];
	var nums=[];
	var useExchangeTicket,useExchangeCash,useExchangeSelf;
	useExchangeTicket="";
	useExchangeCash="";
	useExchangeSelf="";
	for(var i=0;i<$listSku.length;i++) {
		skus[i]=$($listSku[i]).val();
		nums[i]=$($listNum[i]).val();
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
	$.ajax({
		url : "/order/create",
		type : "POST",
		data : {
			"name" : name,
			"mobile" : mobile,
			"selfDelivery" : selfDelivery,
			"address" : address + "(电话:" + mobile + ")",
			"invoiceTitle" : invoiceTitle,
			"invoiceId":invoiceId,
			"message" : message,
			"useCompanyTicket" : useCompanyTicket,
			"useExchangeTicket" : useExchangeTicket,// 商品的价格
			"useExchangeCash" : useExchangeCash,// 商品的价格
			"useExchangeSelf" : useExchangeSelf,// 商品的价格
			"useCash" : $("#hidCashExchange").val(),// 使用的总内购券
			"addressRadio" : $addressRadio.val(),
			"skus" : skus,
			"nums" : nums,
			"freights" : freights,
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
function go2Submit(){

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
		$("#directOrderForm").submit();
	}else{
		$("#directOrderForm").submit();
	}
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
