
var uid=GetUidCookie();
$(document).ready(function() {

	$(".ul1_ypp .ul1_li_ypp a").toggle(function(){
		$(this).css({"background":"url("+jsBasePath+"static_resources/images/ypp_icon1.png) no-repeat right center","background-size":"20px 11px"});
		$(".ypp_con").show();
	},function(){
		$(this).css({"background":"url("+jsBasePath+"static_resources/images/ypp_icon.png) no-repeat right center","background-size":"20px 11px"});
		$(".ypp_con").hide();
	})
    
	init();	
	
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
});

function init(){
	$.ajax({
		url : jsBasePath +'exchangeOrder/getBatchInfo.user?uid='+uid+"&batchId="+batchId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
		success : function(data) {
			var html='';

			if (data.success) {
				var result = data.data;
				var exSubOrders = data.data.exSubOrders;
				var subOrders = data.data.subOrders;
				var exTotalAdjustment=0;
				var exTotalShipping=0;
				var exRealPrice=0;
				
				var totalAdjustment=0;
				var totalShipping=0;
				var realPrice=0;
				
				var exHtml = '';
				var html = '';

				// 循环订单
				for (var i = 0; i < subOrders.length; i++) {
					totalAdjustment += subOrders[i].totalAdjustment;
					totalShipping += subOrders[i].totalShipping;
					realPrice += subOrders[i].realPrice;
					
					html += '<div class="main_con main_con_adjust">';
					html += ' <div class="pp_top"><span>'+subOrders[i].supplierName+'</span><a href="'+jsBasePath+'orderM?subOrderId='+subOrders[i].subOrderId+'">查看订单</a></div>';
					html += ' <div class="pp_pro">';
					var subOrderItems = subOrders[i].subOrderItems;
					for(var j=0;j<subOrderItems.length;j++) {
						var rprice = subOrderItems[j].internalPurchasePrice;
						html += '  <dl>';
						html += '   <dt><a href="javascript:;"><img src="'+subOrderItems[j].image+'" /></a></dt>';
						html += '   <dd class="dd1"><a href="javascript:;">'+subOrderItems[j].productName+'</a></dd>';
						html += '   <dd class="dd2">'+subOrderItems[j].itemValues.replace("{","").replace("}","").replace(/\"/g,"")+'</dd>';
						html += '   <dd class="dd3"><span>'+((rprice).toFixed(2))+'</span><i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i><em>x '+subOrderItems[j].number+'</em></dd>';
						html += '  </dl>\n';
					}
					html += ' </div>';
					html += '</div>\n';
				}
				html += '<div class="hlb_price hlb_price_adjust"><span>总计：¥'+(realPrice).toFixed(2)+'+'+(totalAdjustment).toFixed(2)+'</span><i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i><em>（含运费￥'+(totalShipping).toFixed(2)+'）</em></div>';

				// 循环换领单
				for (var i = 0; i < exSubOrders.length; i++) {
					exTotalAdjustment += exSubOrders[i].totalAdjustment;
					exTotalShipping += exSubOrders[i].totalShipping;
					exRealPrice += exSubOrders[i].realPrice;

					exHtml += '<div class="main_con main_con_adjust">';
					exHtml += ' <div class="pp_top"><span>'+exSubOrders[i].supplierName+'</span></div>';
					exHtml += ' <div class="pp_pro">';
					var subOrderItems = exSubOrders[i].subOrderItems;
					for(var j=0;j<subOrderItems.length;j++) {
						var rprice = subOrderItems[j].internalPurchasePrice;
						exHtml += '  <dl>';
						exHtml += '   <dt><a href="javascript:;"><img src="'+subOrderItems[j].image+'" /></a></dt>';
						exHtml += '   <dd class="dd1"><a href="javascript:;">'+subOrderItems[j].productName+'</a></dd>';
						exHtml += '   <dd class="dd2">'+subOrderItems[j].itemValues.replace("{","").replace("}","").replace(/\"/g,"")+'</dd>';
						exHtml += '   <dd class="dd3"><span>'+((rprice).toFixed(2))+'</span><i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i><em>x '+subOrderItems[j].number+'</em></dd>';
						exHtml += '  </dl>\n';
					}
					exHtml += ' </div>';
					exHtml += '</div>\n';
				}
				exHtml += '<div class="hlb_price hlb_price_adjust"><span>总计：¥'+(exRealPrice).toFixed(2)+'+'+(exTotalAdjustment).toFixed(2)+'</span><i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i><em>（含运费￥'+(exTotalShipping).toFixed(2)+'）</em></div>';
		    	
				html += '<div class="jieyu">';
				html += ' <div class="hlb_price  hlb_price_adjust hlb_price_jy"><span>结余：¥'+(exRealPrice-realPrice).toFixed(2)+'+'+(exTotalAdjustment-totalAdjustment).toFixed(2)+'<i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i></span></div>';
				html += ' <p>结余的换领币已退回您的账户，现金已退款</p>';
				html += '</div>';
				$(".tj_con:eq(0)").html(html);
		    	$(".tj_con:eq(1)").html(exHtml);
			}
		},
		error : function() {}
	});
}

function back() {
	history.back();
}