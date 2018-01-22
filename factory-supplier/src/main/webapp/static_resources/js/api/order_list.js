
var uid=GetUidCookie();
var isload = true;
// JavaScript Document
$(function(){
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
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
	if(status==5){
		$(".top_tab").show();
	}
	if (status!=5) {
		$(".main_box").removeAttr("style");
	}
	$(".swiper-wrapper li").each(function(index){
		$(this).click(function(){//先把所有的隐藏掉
			$(".top_tab ul li").removeClass("this");
			$(this).addClass("this");
			if (index==0) {//仅退款
				status=5;
			}else if(index==1){//退货退款
				status=3;
			}else if(index==2){//仅退款完毕
				status=12;
			}else{
				status=11;
			}
			$("#pageNum").val("0");
			$(".main_two").html('')//清空页面数据
			$(".top_tab").show();
			init()
		});
	});	
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
})
function init(){
	if(uid == "") return;
	var pages = parseInt($("#pageNum").val()) + 1;
	if(pages<1) return;
	$.ajax({
		url:jsBasePath+'app/suborder/selllist.user?uid='+uid+'&status='+status+'&pages='+pages,
		type : "GET",
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				var result = data.data;
				if (result) {
					var result2 = result.result;
					var msgBody = result2.msgBody;
					var totalPage = result2.totalPage;
					if (parseInt(pages)<=parseInt(totalPage)) {//页数
					if (result2.errorCode==0 &&msgBody.length>0) {//订单列表
						var html=''; 
						$("#pageNum").val(pages);
						var purchasePrice=0;
						for (var i = 0; i < msgBody.length; i++) {
								html+='<div class="main_two_con">';
								html+='<div class="con_top"><span>订单编号：'+msgBody[i].subOrderId+'</span>';
								if (status==0) {
									html+='<em>未支付</em></div>';
								}else if(status==1){
									if (msgBody[i].stockUp==1) {
										html+='<em>备货中</em></div>';
									}else{
										html+='<em>待发货</em></div>';
									}
								}else if(status==2){
									html+='<em>已发货</em></div>';
								}else if(status==4){
									html+='<em>已完成</em></div>';
								}else if(status==5){
									html+='<em>仅退款申请中</em></div>';
								}else if(status==3){
									html+='<em>退款退货申请中</em></div>';
								}else if(status==12){
									html+='<em>仅退款完成</em></div>';
								}else if(status==11){
									html+='<em>退货退款完成</em></div>';
								}else{
									html+='<em>已关闭</em></div>';
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
										html+='<div class="ds_rt"><span>￥'+purchasePrice.toFixed(2)+'</span><em>x'+suborderitemList[j].number+'</em><em>'+suborderitemList[j].companyTicket+'券</em></div>';
										html+='</div>';
									}
								}
								html+='<div class="clearing">实付款：￥'+msgBody[i].realPrice.toFixed(2)+'(含运费：'+msgBody[i].totalShipping.toFixed(2)+'元)</div>';
								if (status==1) {//待发货页面显示
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
								}else if(status==0){//待付款
									html+='<div class="btns"><div class="btns_rt"><span class="btn_ren" onclick="go2CloseSubOrder(\''+msgBody[i].subOrderId+'\')">关闭订单</span><span style="margin:0;"  onclick="go2UpdateShipping(\''+msgBody[i].subOrderId+'\',\''+msgBody[i].totalShipping+'\')">修改运费</span></div></div>';
								}else if(status==2){//已发货
									html+='<div class="btns"><div class="btns_rt"><span class="btn_ren" style="margin:0;" onclick="go2CloseSubOrder(\''+msgBody[i].subOrderId+'\')">延长收货时间</span></div></div>';
								}else if(status==5||status==3){//仅退款申请中或者退货退款申请中
									html+='<div class="btns"><div class="btns_rt"><span style="margin:0;" onclick="go2ReturnDetail(\''+msgBody[i].subOrderId+'\')">查看退款申请详情</span></div></div>'
								}
								html+='</div>';
						}
						$(".main_two").append(html);
					}else{//数据为空
						$("#pageNum").val(-1);
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

function go2OrderDetail(subOrderId){//订单详情
	if(uid == "") return;
	window.location=jsBasePath+'app/suborder/orderDetail?uid='+uid+'&subOrderId='+subOrderId+'&flag=1';
}
function go2UpdateShipping(subOrderId,totalShipping){//修改运费
	$("#suborderID").val(subOrderId);
	if (totalShipping!=null&&totalShipping!='') {
		var shipping=parseFloat(totalShipping).toFixed(2);
		$("#shipping").val(shipping);//运费显示
	}
	$(".recharge_money").show();
	$(".add_money-mask").show();
	
}
function checkNum(obj){//校验金额
	obj.value = obj.value.replace(/[^\d.]/g,""); 
    obj.value = obj.value.replace(/\.{2,}/g,".");    
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');   
    if(obj.value.indexOf(".")< 0 && obj.value !=""){
    	obj.value= parseFloat(obj.value);
   }
}
function go2Close(){//取消修改运费
	$(".recharge_money").hide();
	$(".add_money-mask").hide();
}
function go2Sure(){//确定修改
	if(uid == "") return;
	var suborderID=$("#suborderID").val();
	var shipping=$("#shipping").val();
	if (shipping=="") {
		go2Close();
		showInfoBox("请输入运费");
	}else if(shipping>=8888) {
		go2Close();
		showInfoBox("运费不能超过8888");
	}else{
	$.ajax({
		url:jsBasePath+'app/suborder/updateFreight.user?uid='+uid+'&suborder='+suborderID+'&freight='+shipping,
		type : "POST",
	    async: true,
	    success : function(data) {
	    	go2Close();
	    	if (data.success) {
	    		showInfoBox("修改成功")
	    		setTimeout(reload, 1500)
			}else{
				showInfoBox(data.msg);
			}
	    }
	})
	}
}
function reload(){
	closeMsg();
	go2Close();
	$(".thickdiv").hide();
	$(".orderbox").hide();
	$(".main_box").removeAttr("style");
	$(".main_two").html('');
	$("#pageNum").val("0");
	init();
	//window.location.reload();//页面重新加载
}
function go2CloseSubOrder(subOrderId){//进入关闭订单页面
	$(".thickdiv").show();
	$(".orderbox").show();
	$("#suborderID").val(subOrderId);
	$(".main_box").attr("style","position:fixed");
}
function ajaxUpdateSubOrder(){//确认关闭
	if(uid == "") return;
	var subOrderId= $("#suborderID").val();
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
	}
	toClose(url);
	
}
function toClose(url){
	$.ajax({
		url:url,
		type : "GET",
   	 	async: false,
	    cache:false,
   		success : function(data) {
    		if (data.success) {
    			if(status==0){//关闭订单
    				showInfoBox("关闭订单成功");
    				setTimeout(back, 1500);
    			}else if(status==2){//延长收货时间
    				showInfoBox("延长收货时间成功");
    				//setTimeout(window.location.reload(), 1500)
    				setTimeout(reload, 1500)
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
function go2Send(subOrderId){//发货
	if(uid == "") return;
	window.location=jsBasePath+'app/suborder/orderDetail?uid='+uid+'&subOrderId='+subOrderId+'&flag=2';
}
function go2UpdateStockUp(subOrderId,stockUp){//修改备货状态
	if(uid == "") return;
	$.ajax({
		url:jsBasePath+'app/suborder/ajaxUpdateStockUp.user?uid='+uid+'&subOrderId='+subOrderId+'&stockUp='+stockUp,
		type : "GET",
	    async: true,
	    success : function(data) {
	    	if (data.success) {
				reload();//页面重新加载
			}else{
				showInfoBox(data.msg);
			}
	    }
	})
}

function go2ReturnDetail(subOrderId){//查看退款单详情
	if(uid == "") return;
	window.location=jsBasePath+'app/suborder/orderDetail?uid='+uid+'&subOrderId='+subOrderId+'&status='+status+'&flag=1';
}