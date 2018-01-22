
var uid=GetUidCookie();
// JavaScript Document
$(function(){
	init();
	//关闭选择关闭原因
	$(".thickclose").click(function(e){
		$(".thickdiv").hide();
		$(".orderbox").hide();
		$(".main_box").removeAttr("style");
	});
	$(".thickdiv").click(function(e){
		$(".thickdiv").hide();
		$(".orderbox").hide();
		$(".main_box").removeAttr("style");
	});
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
})
var selfDelivery;
var commonExpressList = null;//常用快递
var allCompInfoList = null;//全部快递公司
function init(){
	if(uid == "") return;
	$.ajax({
		url:jsBasePath+'/app/suborder/toSendOut.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				allCompInfoList = data.data.allCompInfoList;
				var shippingAddressSend = data.data.shippingAddressSend;
				var shippingAddressReturned = data.data.shippingAddressReturned;
				commonExpressList = data.data.commonExpressList;//常用快递公司信息
				if (shippingAddressSend && shippingAddressSend !=null && typeof(shippingAddressSend)!=undefined ) {
	    			var SprovinceName = shippingAddressSend.provinceName;//发货省份
		    		var ScityName = shippingAddressSend.cityName;//发货区域
		    		var SareaName = shippingAddressSend.areaName;//发货市
		    		var Saddress = shippingAddressSend.address;//发货详情
		    		if (SprovinceName==null) {
		    			SprovinceName='';
					}
		    		if(ScityName==null){
		    			SprovinceName='';
					}
		    		if(SareaName==null){
		    			SareaName='';
					}
		    		if(Saddress==null){
		    			Saddress='';
					}
		    		//我的发货信息
					$("#sendAddress").html(SprovinceName+','+ScityName+','+SareaName+','+Saddress);
				}else{
					$("#sendAddress").parent().hide();
					$("#sendAddressNull").show();
					$(".fh_btn a").attr("href","javascript:void(0)");
					$(".fh_btn").addClass("disable");
				}
	    		if (shippingAddressReturned &&shippingAddressReturned!=null && typeof(shippingAddressReturned)!=undefined ) {
	    			var RprovinceName = shippingAddressReturned.provinceName;//退货省份
		    		var RcityName = shippingAddressReturned.cityName;//退货区域
		    		var RareaName = shippingAddressReturned.areaName;//退货市
		    		var Raddress = shippingAddressReturned.address;//退货详情
		    		if (RprovinceName==null) {
		    			RprovinceName='';
					}
		    		if(RcityName==null){
		    			RcityName='';
					}
		    		if(RareaName==null){
		    			RareaName='';
					}
		    		if(Raddress==null){
		    			Raddress='';
					}
					//我的退货信息
					$("#returnedAddress").html(RprovinceName+','+RcityName+','+RareaName+','+Raddress);
				}else{
					$("#returnedAddress").parent().hide();
					$("#returnedAddressNull").show();
					$(".fh_btn a").attr("href","javascript:void(0)");
					$(".fh_btn").addClass("disable");
				}
				var suborder = data.data.suborder;
				var order = suborder.orders;
				var suborderitemlist = suborder.suborderitemlist;
				selfDelivery =order.selfDelivery;
				if (selfDelivery!=undefined&&selfDelivery==1) {//自提订单
					$("#chooseShip").hide();
					$("#shipInfo").hide();
				}
				// 确认收货信息及收货详情
				if (order) {
					$("#subOrderNum span").html('订单编号：'+subOrderId);
					$("#buyName span").html('买家：'+order.name);
					$("#buyInfo i").html(order.address);
					$("#buyTel i").html(order.mobile);
				}
				//商品信息
				if (suborderitemlist&&suborderitemlist.length>0) {
					var html ='';
					var purchasePrice = 0;
					for (var i = 0; i < suborderitemlist.length; i++) {
						html+='<div class="con_details" style="background:#fff;margin-top:10px;width:96%;padding-left:4%;">';
						html+='<dl>';
						html+='<dt>';
						html+='<a href="javascript:void(0);"><img src="'+suborderitemlist[i].image+'"/></a>';
						if(suborderitemlist[i].saleKbn==1) {
							html +='<div class="picon"><img src="'+jsBasePath+'/static_resources/images/picon2.png"/></div>';
						} else if(suborderitemlist[i].saleKbn==2) {
							html +='<div class="picon"><img src="'+jsBasePath+'/static_resources/images/picon_c2.png"/></div>';
						} else if(suborderitemlist[i].saleKbn==4) {
							html +='<div class="picon"><img src="'+jsBasePath+'/static_resources/images/picon_z2.png"/></div>';
						} else if(suborderitemlist[i].saleKbn==5) {
							html +='<div class="picon"><img src="'+jsBasePath+'/static_resources/images/picon_t2.png"/></div>';
						}else{
							html +='<div class="picon"></div>';
						}
						html+='</dt>';
						html+='<dd class="dd1"><a href="javascript:void(0);">'+suborderitemlist[i].productName+'</a></dd>';
						html+='<dd class="dd2">'+suborderitemlist[i].itemValues+'</dd>';
						html+='</dl>';
						if(suborderitemlist[i].internalPurchasePrice && suborderitemlist[i].internalPurchasePrice !=null){
							purchasePrice =suborderitemlist[i].internalPurchasePrice
						}else{
							purchasePrice = (suborderitemlist[i].price - suborderitemlist[i].companyTicket/suborderitemlist[i].number)
						}
						html+='<div class="ds_rt"><span>￥'+purchasePrice.toFixed(2)+'</span><em>x'+suborderitemlist[i].number+'</em></div>';
						html+='</div>';
						}
						$("#product").html(html);
					}
				//确认发货/退货信息
				if (commonExpressList==null) {
					$("#commExpress").attr("href","javascript:go2SetCommExpress()");
				}
				
	    	}
	    },
	    error : function() {}
	})
}
//选择物流公司
function go2ChooseExpress(id){
	if ("1"==id) {
		chooseAllCompInfo()
	}else if("2"==id){
		chooseCommonExpress()
	}
}
//选择完物流公司
function chooseShipping(expressType,expressName){
	$("#expressName").html(expressName);//页面快递名称
	$("#expressType").val(expressType);//快递id
	if (expressType=="14660000000000000") {//电子卡券
		$("#qr").children("span").html("卡券密码：");
	}else{
		if (expressType=="14660000000000001") {//厂家直送
			$("#qr").children("span").html("货运号：");
		}else{
			$("#qr").children("span").html("运单号码：");
		}
	}
	if (expressType=="14660000000000001") {//厂家直送
		$("#expressNo").attr("placeholder","此项可不填");
	}else{
		$("#expressNo").attr("placeholder","请填写或点击右图扫描");
	}
	$("#expressCompany").html("快递："+expressName)//提示框快递名称
	$(".thickdiv").hide();
	$(".orderbox").hide();
	$(".main_box").removeAttr("style");
}
function go2ConfirmSend(){
	if (selfDelivery==1) {
		go2Sure();
	}else{
	var expressType=$("#expressType").val();
	var expressNo=$("#expressNo").val();
	if (expressType==''||expressType==undefined) {
		showInfoBox("请选择快递");
	}else if(expressNo==undefined||expressNo==''){
		if (expressType=="14660000000000000") {
			showInfoBox("请输入卡券密码");
		}else if(expressType == "14660000000000001"){//厂家直送
			go2Sure();
		}else{
			showInfoBox("请输入快递单号");
		}
	}else{
		$("#expressNum").html("单号："+expressNo);//提示框单号
		$(".recharge_money").show();
		$(".add_money-mask").show();
	}
	}
}
function go2Close(){//取消
	$(".recharge_money").hide();
	$(".add_money-mask").hide();
}
function go2Sure(){//确定
	if(uid == "") return;
	var expressType=$("#expressType").val();
	var expressNo=$("#expressNo").val();
	var sendAddress=$("#sendAddress").html();
	var returnedAddress=$("#returnedAddress").html();
	$.ajax({
		url:jsBasePath+'/app/suborder/sendOut.user?uid='+uid+'&subOrderId='+subOrderId+'&expressType='+expressType+'&expressNo='+expressNo+'&sendAddress='+sendAddress+'&returnedAddress='+returnedAddress,
		type : "POST",
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		var result = data.data.result;
	    		if (0==result.errorCode) {
	    			go2Close();
	    			showInfoBox("发货成功");
	    			setTimeout(back, 1500);
				}
			}else{
				showInfoBox(data.msg);
			}
	    },
	    error : function() {}
	})
}
function back(){//回退
	history.back();
}
function chooseAllCompInfo(){//全部快递公司
	//快递公司信息
	if (allCompInfoList!=null&&allCompInfoList.length>0) {
		var html="";
		var arr = new Array();
		for (var i = 0; i < allCompInfoList.length; i=i+3) {
			arr[0] = allCompInfoList[i];
			if (i+1>=allCompInfoList.length) {
				arr[1]='';
			}else{
				arr[1] = allCompInfoList[i+1];
			}
			if (i+2>=allCompInfoList.length) {
				arr[2]='';
			}else{
				arr[2] = allCompInfoList[i+2];
			}
			html +='<tr>';
			html +='<td onclick="chooseShipping(\''+arr[0]["id"]+'\',\''+arr[0]["name"]+'\')">'+arr[0]["name"]+'</td>';
			if (arr[1]=='') {
				html +='<td>  </td>';
			}else{
			html +='<td onclick="chooseShipping(\''+arr[1]["id"]+'\',\''+arr[1]["name"]+'\')">'+arr[1]["name"]+'</td>';
			}
			if (arr[2]=='') {
				html +='<td>  </td>';
			}else{
				html +='<td onclick="chooseShipping(\''+arr[2]["id"]+'\',\''+arr[2]["name"]+'\')">'+arr[2]["name"]+'</td>';
			}
			html+='</tr>';
		}
		$("table").html(html);
		$(".thickdiv").show();
		$(".orderbox").show();
		$(".main_box").attr("style","position:fixed");
	}
}
function chooseCommonExpress(){//常用快递公司选择
	//快递公司信息
	if (commonExpressList!=null &&commonExpressList.length>0) {
		var html="";
		var arr = new Array();
		for (var i = 0; i < commonExpressList.length; i=i+3) {
			arr[0] = commonExpressList[i];
			if (i+1>=commonExpressList.length) {
				arr[1]='';
			}else{
				arr[1] = commonExpressList[i+1];
			}
			if (i+2>=commonExpressList.length) {
				arr[2]='';
			}else{
				arr[2] = commonExpressList[i+2];
			}
			html +='<tr>';
			html +='<td onclick="chooseShipping(\''+arr[0]["id"]+'\',\''+arr[0]["name"]+'\')">'+arr[0]["name"]+'</td>';
			if (arr[1]=='') {
				html +='<td>  </td>';
			}else{
			html +='<td onclick="chooseShipping(\''+arr[1]["id"]+'\',\''+arr[1]["name"]+'\')">'+arr[1]["name"]+'</td>';
			}
			if (arr[2]=='') {
				html +='<td>  </td>';
			}else{
				html +='<td onclick="chooseShipping(\''+arr[2]["id"]+'\',\''+arr[2]["name"]+'\')">'+arr[2]["name"]+'</td>';
			}
			html+='</tr>';
		}
		$("table").html(html);
		$(".thickdiv").show();
		$(".orderbox").show();
		$(".main_box").attr("style","position:fixed");
	}
}
function go2SetCommExpress(){//设置常用快递
	window.location = jsBasePath+'/supplierExpress.html';
}