
var uid=GetUidCookie();
// JavaScript Document
$(function(){
	init();
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
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
	//切换弹窗页面
	$(".close_list_con ul li em").each(function(index){
		$(this).click(function(){//先把所有的隐藏掉
			$(".close_list_con ul li em").removeClass("em1");
			$(this).addClass("em1");
			var delivertime = $(".close_list_con li .em1").prev().html();
			$(".fh_btn span").html('('+delivertime+')');
		});
	});
});
var status;//订单状态
function init(){
	if(uid == "") return;
	$.ajax({
		url:jsBasePath+'app/suborder/toOrderDetail.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		var result = data.data;
	    		var compInfo = result.compInfo;//物流公司信息
	    		var shippingAddressSend = result.shippingAddressSend;//我的发货地址
	    		var shippingAddressReturned = result.shippingAddressReturned;//我的退货地址
	    		var suborder = result.suborder;//订单
	    		var expressNo = suborder.expressNo;//快递单号
	    		var expressType = suborder.expressType;//物流id
	    		status = suborder.status;//订单状态
	    		var orders = suborder.orders;//收件人信息
	    		var suborderitemlist = suborder.suborderitemlist;
	    		var returnorder = result.returnorder;
	    		var refundorder = result.refundorder;
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
				}
	    		if (shippingAddressReturned &&shippingAddressReturned!=null &&typeof(shippingAddressReturned)!=undefined) {
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
				}
	    		if (orders) {
	    			$("#userId").val(orders.userId);
	    			$("#receiveName").html(orders.name);//收件人
					$("#receiveTel").html(orders.mobile);//收件手机
					$(".p2").html('<span style="float:left;">地址：</span>'+orders.address);
					//当订单为未支付，待发货，已关闭时物流隐藏
					if (status=='-1'||status=='0'||status=='1'||orders.selfDelivery=='1') {
						$(".logistics").hide();
					}else{
						if (compInfo!=undefined ) {
							if (compInfo.name!= undefined &&compInfo.name!='') {
								$(".dd1 span").html(compInfo.name);
							}
							if (expressNo!='') {
								$(".dd1 em").html(expressNo);
							}
							if (expressType=='14660000000000000'||expressType=='14660000000000001') {
								$("#dd2").hide();
								$(".logistics").css("height","auto");
							}
							if (compInfo.name!=''&&expressNo!='') {
								ajaxlistlogInfo(compInfo.pinYin,expressNo)
							}
						}
						$("#xinxi a").attr("href","javascript:go2CKShipping("+suborder.subOrderId+","+suborder.expressType+","+suborder.expressNo+");");
					}
					//商品信息
					if (suborderitemlist&&suborderitemlist.length>0) {
						var html ='';
						var totalProduct = 0;//订单中商品总价
						var totalCompanyTicket =0;//订单中内购券总额
						var purchasePrice =0;//内购价
						for (var i = 0; i < suborderitemlist.length; i++) {
							html+='<div class="con_details" style="background:#fff;margin-top:10px;width:96%;padding-left:4%;">';
							html+='<dl>';
							html+='<dt>';
							html+='<a href="javascript:void(0);"><img src="'+suborderitemlist[i].image+'"/></a>';
							if(suborderitemlist[i].saleKbn==1) {
								html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon2.png"/></div>';
							} else if(suborderitemlist[i].saleKbn==2) {
								html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_c2.png"/></div>';
							}else if(suborderitemlist[i].saleKbn==4) {
								html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_z2.png"/></div>';
							} else if(suborderitemlist[i].saleKbn==5) {
								html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_t2.png"/></div>';
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
							html+='<div class="ds_rt"><span>￥'+purchasePrice.toFixed(2)+'</span><em>x'+suborderitemlist[i].number+'</em><em>'+suborderitemlist[i].companyTicket+'券</em></div>';
							html+='</div>';
							totalProduct += purchasePrice*suborderitemlist[i].number;
							//totalCompanyTicket +=suborderitemlist[i].companyTicket;
							//<em>'+(suborderitemlist[i].price-purchasePrice).toFixed(2)+'券</em>
						}
						$("#product").html(html);
					}
					//商品价格单
					$("#pz em").html('￥'+(totalProduct).toFixed(2));
					//$("#pz em").html('￥'+(suborder.totalProduct.toFixed(2)-suborder.totalShipping.toFixed(2)).toFixed(2));
					/*$("#flj em").html('￥'+((suborder.totalProduct-suborder.totalShipping)-(suborder.realPrice-suborder.totalShipping)).toFixed(2));*/
					//$("#flj em").html('￥'+(totalCompanyTicket.toFixed(2)));
					if (suborder.benefitType &&suborder.benefitType==3) {//换领券不为空
						$("#hlj em").html('-'+'￥'+suborder.benefitAmount.toFixed(2));
					}else{
						$("#hlj").hide();
					}
					$("#yf em").html('￥'+suborder.totalShipping.toFixed(2));
					$("#dueAmount em").html('￥'+(totalProduct+suborder.totalShipping-suborder.benefitAmount).toFixed(2));
					if (status==0) {//待支付
						var realPrice = suborder.realPrice;
						realPrice=0;
						$("#receiveAmount em").html('￥'+realPrice.toFixed(2));
					}else{
						$("#receiveAmount em").html('￥'+suborder.realPrice.toFixed(2));
					}
					//退款申请详情
					if (refundorder!=null) {
						$("#refurnId").val(refundorder.refundOrderId);
					}
					if (returnorder!=null) {
						$("#returnId").val(returnorder.returnOrderId)
					}
					if (status==3||status==11) {//退货退款中 和退货退款完毕
						$("#buyName span").html("买家："+orders.name);
						$("#refundStatus span").html(setTuiKuan(suborder.takeTime));
						$("#refundOrder span").html("退款编号："+refundorder.refundOrderId);
						$("#refundPay i").html(returnorder.returnPrice.toFixed(2));//退款金额
						$("#refundReason span").html("退款原因："+returnorder.reason);
						$("#refundNote span").html("退款说明："+returnorder.note);
						var attachmentList = returnorder.returnorderAttachmentList;
						if (attachmentList!=null||attachmentList.length>0) {//退款凭证
							var image='';
							for (var j = 0; j < attachmentList.length; j++) {
								image+='<img src="'+attachmentList[j].image+'">';
							}
							$(".img_con").html(image);
						}
						if(status==3){
							$("#autoRrefundTime i").html(getDate(returnorder.lastTime));//逾期自动退款时间
						}else{
							$("#autoRrefundTime").hide();
						}
					}else if(status==5||status==12){//仅退款 和仅退款完毕
						$("#buyName span").html("买家："+orders.name);
						$("#refundStatus span").html(setTuiKuan(suborder.takeTime));
						$("#refundOrder span").html("退款编号："+refundorder.refundOrderId);
						$("#refundPay i").html(refundorder.refundPrice.toFixed(2));
						$("#refundReason span").html("退款原因："+refundorder.reason);
						$("#refundNote span").html("退款说明："+refundorder.note);
						var attachmentList = refundorder.attachmentList;
						if (attachmentList!=null||attachmentList.length>0) {//退款凭证
							var image='';
							for (var j = 0; j < attachmentList.length; j++) {
								image+='<img src="'+attachmentList.image+'">'
							}
							$("#attchment").html(image);
						}
						if(status==5){
							$("#autoRrefundTime i").html(getDate(refundorder.lastTime));//逾期自动退款时间
						}else{
							$("#autoRrefundTime").hide();
						}
					}else{
						$(".tk").hide();
						$(".tk_con").hide();
					}
					//订单信息
					$("#orderId").html('订单编号：'+suborder.subOrderId);
					if (status==-1) {//关闭订单
						if (suborder.closeReason!=null||suborder.closeReason!='') {
							$("#closeOrder").html('关闭原因：'+suborder.closeReason);
						}else{
							$("#closeOrder").html('关闭原因：默认');
						}
						$("#payTime").hide();
						$("#sendTime").hide();
					}else{
						$("#closeOrder").hide();
					}
					$("#createTime").html('创建时间：'+suborder.createTimeString);
					if (status==0) {//未支付
						$("#payTime").hide();
						$("#sendTime").hide();
					}else if(status==1){//待发货
						$("#sendTime").hide();
					}
					if (suborder.payTime!=null&&suborder.payTime!='') {
							$("#payTime").html('付款时间：'+getDate(suborder.payTime));
					}
					if (suborder.sendTime!=null&&suborder.sendTime!='') {
							$("#sendTime").html('发货时间：'+getDate(suborder.sendTime));
					}
					if (status==2) {//已发货（延长收货）
						$(".region").html("选择延长收货时间");
						var html='';
						html+='<li><span>3天</span><em></em></li>';
						html+='<li><span>5天</span><em></em></li>';
						html+='<li><span>7天</span><em></em></li>';
						html+='<li><span>10天</span><em></em></li>';
						$(".close_list_con ul").html(html);
						$(".fh_btn a").html("确认延长收货时间<span></span>");
					}else if(status==3||status==5){
						$(".region").html("选择退款原因");
						var html='';
						html+='<li><span>包装不完整</span><em></em></li>';
						html+='<li><span>影响二次销售</span><em></em></li>';
						html+='<li><span>未收到退货</span><em></em></li>';
						$(".close_list_con ul").html(html);
						$(".fh_btn a").html("确认拒绝退款");
					}
					//底部显示
					var bottom='';
					if (status==0) {//未支付
						bottom+='<div class="bottom_btn"><a href="javascript:go2CloseSubOrder(\''+subOrderId+'\');" class="a1">关闭订单</a><a href="javascript:go2UpdateShipping(\''+subOrderId+'\',\''+suborder.totalShipping+'\');" class="a2">修改运费</a></div>';
					}else if(status==1){//待发货
						if (suborder.stockUp==1) {
							bottom+='<div class="bottom_btn"><a href="javascript:go2Send(\''+subOrderId+'\');" class="a2">发货</a><a href="javascript:go2UpdateStockUp(\''+subOrderId+'\',0);" class="a2">取消备货</a></div>';
						}else{
							bottom+='<div class="bottom_btn"><a href="javascript:go2Send(\''+subOrderId+'\');" class="a2">发货</a><a href="javascript:go2UpdateStockUp(\''+subOrderId+'\',1);" class="a2">备货</a></div>';
						}
					}else if(status==2){//已发货
						bottom+='<div class="bottom_btn"><a href="javascript:go2CloseSubOrder(\''+subOrderId+'\');" class="a1">延长收货时间</a></div>';
					}else if(status==3||status==5){//维权
						bottom+='<div class="bottom_btn"><a href="javascript:go2CloseSubOrder(\''+suborder.subOrderId+'\');" class="a2">拒绝退款</a><a href="javascript:toAgree(\''+suborder.subOrderId+'\');" class="a1">同意退款</a></div>';
					}
					$(".main_box").append(bottom);
					var page='';
					if (status==3||status==5) {
						page+='<div class="theme-tit">确定同意退款</div>';
						page+='<div class="theme-input"><input type="text" id="refund" value="将退回货款给买家，确定吗？" disabled="disabled"/></div>';
						page+='<div class="theme-popbod">';
						page+='<a href="javascript:go2Close();">取消</a>';
						page+='<a href="javascript:go2Sure();" style="border:none;">确定</a>';
						page+='</div>';
					}else if(status==0){
						page+='<div class="theme-tit">修改运费</div>';
						page+='<div class="theme-input"><input type="text" id="shipping" placeholder="请输入运费" autocomplete="off" min="0.01" step="0.01" onkeyup="checkNum(this)" maxlength="8"/></div>';
						page+='<div class="theme-popbod">';
						page+='<a href="javascript:go2Close();" >取消</a>  ';
						page+='<a href="javascript:toConfirm();" style="border:none;">确定</a>';
						page+='</div>';
					}
					$(".recharge_money").html(page);
				}
			}
	    },
	    error : function() {}
	})
}
//获取物流信息
function ajaxlistlogInfo (expressName,expressNo){
	var content = '"sname":"express.ExpressSearch","com":"'+expressName+'","express_no":"'+expressNo+'","user":'+uid+',"version":"v2"';
	$.ajax({
        url: 'http://kuaidi.wo-de.com/express/busJsonp.do?content={' +content+ '}&token=',
		dataType : 'jsonp',
	    jsonp:'jsonpcallback',
	    success: function(json){
	    	var ary = eval(json.body.history);
	    	if(ary.length > 0) {
	    		var des = ary[0].des;
	    		if (des.length>21) {
					 var subStr=des.substring(0,18);
					 des=subStr+"...";
				}
	    		$("#xinxi p").html(des);
	    		$("#xinxi i").html(ary[0].dealDate);
	    	}
	    }
	});
}
function setTuiKuan(takeTime){
	var goodstatus;
	if (takeTime!=null ||taskTime!='') {
		goodstatus="货物状态：已收到货";
	}else{
		goodstatus="货物状态：未收到货";
	}
	return goodstatus;
}
//时间转换
function getDate(createTime){
	var tt=new Date(parseInt(createTime))
	Y = tt.getFullYear() + '-';
	M = (tt.getMonth()+1 < 10 ? '0'+(tt.getMonth()+1) : tt.getMonth()+1) + '-';
	D = (tt.getDate()< 10 ? '0'+(tt.getDate()) : tt.getDate()) + ' ';
	h = (tt.getHours()<10 ? '0'+(tt.getHours()) :tt.getHours()) + ':';
	m = (tt.getMinutes()<10 ? '0'+(tt.getMinutes()) :tt.getMinutes())+ ':';
	s = (tt.getSeconds()<10 ? '0'+(tt.getSeconds()) :tt.getSeconds());
    return   Y+M+D+h+m+s;     
}
//修改运费
function go2UpdateShipping(subOrderId,totalShipping){
	if (totalShipping!=null&&totalShipping!='') {
		var shipping=parseFloat(totalShipping).toFixed(2);
		$("#shipping").val(shipping);//运费显示
	}
	$(".recharge_money").show();
	$(".add_money-mask").show();
}
//校验运费
function checkNum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,""); 
    obj.value = obj.value.replace(/\.{2,}/g,".");    
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');   
    if(obj.value.indexOf(".")< 0 && obj.value !=""){
    	obj.value= parseFloat(obj.value);
   }
}
//取消修改运费
function go2Close(){
	$(".recharge_money").hide();
	$(".updateShipping").hide();
	$(".add_money-mask").hide();
}
//确定修改运费
function toConfirm(){
	var shipping=$("#shipping").val();
	if (shipping=="") {
		go2Close();
		showInfoBox("请输入运费");
	}else if(shipping>=8888) {
		go2Close();
		showInfoBox("运费不能超过8888");
	}else{
	$.ajax({
		url:jsBasePath+'app/suborder/updateFreight.user?uid='+uid+'&suborder='+subOrderId+'&freight='+shipping,
		type : "POST",
		async: false,
   	 	cache: false,
	    success : function(data) {
	    	go2Close();
	    	if (data.success) {
	    		showInfoBox("修改成功");
	    		setTimeout(reload, 1500)
				//window.location.reload();//页面重新加载
			}else{
				showInfoBox(data.msg);
			}
	    }
	})
	}
}
//进入关闭订单页面
function go2CloseSubOrder(subOrderId){
	$(".thickdiv").show();
	$(".orderbox").show();
	$(".main_box").attr("style","position:fixed");
}
function ajaxUpdateSubOrder(){//确认关闭
	if(uid == "") return;
	var obj = $(".close_list_con li .em1").prev().html();
	var url='';
	if (status==0) {//未支付
		if (obj=='') {
		showInfoBox("请选择原因");
		}else{
			url=jsBasePath+'app/suborder/ajaxUpdateSuborder.user?uid='+uid+'&subOrderId='+subOrderId+'&status=-1'+'&closeReason='+obj;
		}
	}else if(status==2){//已发货
		if (obj=='') {
		showInfoBox("请选择延长时间");
		}else{
			var delivertime=parseInt(obj);
			url=jsBasePath+'app/suborder/ajaxUpdateSuborder.user?uid='+uid+'&subOrderId='+subOrderId+'&delivertime='+delivertime;
		}
	}else if(status==3||status==5){
		if (obj=='') {
		showInfoBox("请选择原因");
		}else{
			var refurnId=$("#refurnId").val();
			url=jsBasePath+'app/suborder/ajaxRefuse.user?uid='+uid+'&subOrderId='+subOrderId+'&refundOrderId='+refurnId+'&refuseNote='+obj;
		}
	}
	toClose(url,status);
	
}
function toClose(url,status){
	$.ajax({
		url:url,
		type : "GET",
   	 	async: false,
   	 	cache: false,
   		success : function(data) {
    		if (data.success) {
    			if(status==0){//关闭订单
    				showInfoBox("关闭订单成功");
    				setTimeout(back, 1500);
    			}else if(status==2){//延长收货时间
    				showInfoBox("延长收货时间成功");
    				setTimeout(reload, 1500)
    			}else{
    				showInfoBox("拒绝退款成功");
    				setTimeout(back, 1500);
    			}
			}else{
				showInfoBox(data.msg);
			}
   		 },
    	error : function() {}
		})
}
function reload(){
	//window.location.href=window.location.href;//页面重新加载
	//window.location.reload(true);
	window.location.href=window.location.href+"&time="+10000*Math.random();
}
//发货页面
function go2Send(subOrderId){
	if(uid == "") return;
	window.location=jsBasePath+'app/suborder/orderDetail?uid='+uid+'&subOrderId='+subOrderId+'&flag=2';
}
//修改备货状态
function go2UpdateStockUp(subOrderId,stockUp){
	if(uid == "") return;
	$.ajax({
		url:jsBasePath+'app/suborder/ajaxUpdateStockUp.user?uid='+uid+'&subOrderId='+subOrderId+'&stockUp='+stockUp,
		type : "GET",
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		init()
			}else{
				showInfoBox(data.msg);
			}
	    }
	})
}
//同意退款
function toAgree(subOrderId){
	$(".recharge_money").show();
	$(".add_money-mask").show();
}
function go2Sure(){
	if(uid == "") return;
	var returnId=$("#returnId").val();
	var refurnId=$("#refurnId").val();
	var userId=$("#userId").val();
	$.ajax({
		url:jsBasePath+'app/suborder/returncheck.user?uid='+uid+'&subOrderId='+subOrderId+'&returnOrderId='+returnId+'&refundOrderId='+refurnId+'&userId='+userId,
		type : "GET",
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		go2Close();
	    		showInfoBox("退款成功")
	    		setTimeout(back, 1500);
			}else{
				showInfoBox(data.msg);
			}
	    }
	})
}
function back(){
	history.back();
}
//查看物流信息页面
function go2CKShipping(subOrderId,expressType,expressNo){
	if(uid == "") return;
	window.location='http://api.wd-w.com/logistics?userId='+uid+'&subOrderId='+subOrderId+'&expressType='+expressType+'&expressNo='+expressNo;
}