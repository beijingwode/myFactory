
var uid=GetUidCookie();
var isload = true;
// JavaScript Document
$(function(){
	if(isWeiXinOpen()) {
		loginCheck('shop7');
	}
	$("#pageNum").val("0");
	init();
	$(function(){
		$(window).scroll(function(){
			Load();
		});
	});
	function Load(){
		if(isload){//ajax在后台获取数据时，设值其false，防止页面多次加载
			var loadHeight = 115;//指定滚动条距离底部还有多少距离时进行数据加载
			var documentHeight = parseInt($(document).height(),10);//可视区域当前高度
			var windowHeight = parseInt(window.innerHeight,10);//窗口当前高度
			var scrollHight = parseInt($(window).scrollTop(),10);//窗口滚动条位置
			if(documentHeight - scrollHight - windowHeight < loadHeight){
				//ajax获取数据，以下为模拟
				isload = false;
				init();
			}
		}
	}
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
})
function init(){
	if(uid == "") return;
	var pages = parseInt($("#pageNum").val()) + 1;
	if(pages<1) return;
	$.ajax({
		url:jsBasePath+'app/suborder/selllist.user?uid='+uid+'&status=1'+'&pages='+pages+'&sizes=10',
		type : "GET",
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				var result = data.data;
				if (result) {
					$("#nonPaymentCount em").html(result.nonPaymentCount);//待支付数量
					$("#unfilledCount em").html(result.unfilledCount);//待发货
					$("#refundCount em").html(result.refundCount);//维权
					$("#notReceivingCount em").html(result.notReceivingCount);//已发货
					$("#completeCount em").html(result.completeCount);//已完成
					$("#closeCount em").html(result.closeCount);//已关闭
					var result2 = result.result;
					var msgBody = result2.msgBody;
					var totalPage = result2.totalPage;
					if (parseInt(pages)<=parseInt(totalPage)) {//页数
					if (result2.errorCode==0 && msgBody.length>0) {//有急需处理的订单
						var html=''; 
						$("#pageNum").val(pages);
						var purchasePrice = 0;
						for (var i = 0; i < msgBody.length; i++) {
							if (msgBody[i].status==1) {//待发货的订单
								html+='<div class="main_two_con">';
								html+='<div class="con_top"><span>订单编号：'+msgBody[i].subOrderId+'</span>';
								if (msgBody[i].stockUp==1) {
									html+='<em>备货中</em></div>';
								}else{
									html+='<em>待发货</em></div>';
								}
								html+='<div class="con_time">创建时间：'+msgBody[i].createTimeString+'</div>';
								var suborderitemList = msgBody[i].suborderitemlist;
								if (suborderitemList&&suborderitemList.length>0) {
									for (var j = 0; j < suborderitemList.length; j++) {
										html +='<div class="con_details" onclick="go2OrderDetail(\''+msgBody[i].subOrderId+'\')">';
										html +='<dl>';
										html +='<dt><a href="javascript:void(0);"><img src="'+suborderitemList[j].image+'"/></a>';
										if(suborderitemList[j].saleKbn==1) {
											html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon2.png" /></div>';
										} else if(suborderitemList[j].saleKbn==2) {
											html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_c2.png" /></div>';
										} else if(suborderitemList[j].saleKbn==4) {
											html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_z2.png" /></div>';
										} else if(suborderitemList[j].saleKbn==5) {
											html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_t2.png" /></div>';
										}
										html+='</dt>';
										html+='<dd class="dd1"><a href="javascript:void(0);">'+suborderitemList[j].productName+'</a></dd>';
										html+='<dd class="dd2">'+suborderitemList[j].itemValues+'</dd>'
										html+='</dl>';
										if(suborderitemList[j].internalPurchasePrice && suborderitemList[j].internalPurchasePrice !=null){
											purchasePrice =suborderitemList[j].internalPurchasePrice
										}else{
											purchasePrice = (suborderitemList[j].price - suborderitemList[j].companyTicket/suborderitemList[j].number)
										}
										html+='<div class="ds_rt"><span>￥'+purchasePrice.toFixed(2)+'</span><em>x'+suborderitemList[j].number+'</em></div>';
										html+='</div>';
									}
								}
								html+='<div class="clearing">实付款：￥'+msgBody[i].realPrice.toFixed(2)+'(含运费：'+msgBody[i].totalShipping.toFixed(2)+'元)</div>';
								if (msgBody[i].urgeNumber>0) {//催单次数大于0
									html+=' <div class="btns"><div class="p_txt p_txt2">被催单次数(<em>'+msgBody[i].urgeNumber+'</em>)</div>';
								}else{
									html+=' <div class="btns"><div class="p_txt">被催单次数(<em>'+msgBody[i].urgeNumber+'</em>)</div>';
								}
								if (msgBody[i].stockUp==1) {//备货中
									html+='<div class="btns_rt"><span  onclick="go2Send(\''+msgBody[i].subOrderId+'\')">发货</span><span  style="margin:0;" onclick="go2UpdateStockUp(\''+msgBody[i].subOrderId+'\',0)">取消备货</span></div></div>';
								}else{
									html+='<div class="btns_rt"><span  onclick="go2Send(\''+msgBody[i].subOrderId+'\')">发货</span><span  style="margin:0;" onclick="go2UpdateStockUp(\''+msgBody[i].subOrderId+'\',1)">备货</span></div></div>';
								}
								html+='</div>';
							}
						}
						$(".main_two").append(html);
					}else{//数据为空
						$("#pageNum").val(-1);
						$(".bottom_con").show();
					}
				}
				}
				isload = true;
			}else{
				showInfoBox(msg);
			}
	    },
	    error : function() {}
	});
}
function go2OrderList(status){//订单列表
	if(uid == "") return;
	window.location=jsBasePath+'app/suborder/page?uid='+uid+'&status='+status;
}
function go2OrderDetail(subOrderId){//订单详情
	if(uid == "") return;
	window.location=jsBasePath+'app/suborder/orderDetail?uid='+uid+'&subOrderId='+subOrderId+'&flag=1';
}
function go2Send(subOrderId){//发货
	if(uid == "") return;
	window.location=jsBasePath+'app/suborder/orderDetail?uid='+uid+'&subOrderId='+subOrderId+'&flag=2';
}
function go2UpdateStockUp(subOrderId,stockUp){//修改备货状态
	if(uid == "") return;
	$.ajax({
		url:jsBasePath+'app/suborder/ajaxUpdateStockUp.user?uid='+uid+'&subOrderId='+subOrderId+'&stockUp='+stockUp,
		type : "GET",
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		$("#pageNum").val("0");
	    		$(".main_two").html('');
	    		init();
	    		//window.location.href=window.location.href+"?time="+10000*Math.random();;//页面重新加载
			}else{
				showInfoBox(data.msg);
			}
	    }
	})
}
