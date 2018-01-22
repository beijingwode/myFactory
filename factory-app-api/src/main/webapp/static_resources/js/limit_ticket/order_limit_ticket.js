
function calculateLimitTicket(limitTicketIds){
	// 用过即删除
	sessionStorage.removeItem("limitTicketIds");

	
	var $listSku  = $('input[name="skuId"]');
	var $listCompanyTicket  = $('input[name="skuCompanyTicket"]');
	var $listCash  = $('input[name="skuCash"]');
	var sku_cashs_tickets="";
	var nums=[];
	var fuli_sel = $("#fuli_sel").val();
	for(var i=0;i<$listSku.length;i++) {
		sku_cashs_tickets += $($listSku[i]).val() + "_" + $($listCash[i]).val() + "_" + $($listCompanyTicket[i]).val()+",";
	}
	if(sku_cashs_tickets.length>0) {
		sku_cashs_tickets= sku_cashs_tickets.substring(0,sku_cashs_tickets.length-1)
	}
	$.ajax({
		url : jsBasePath +'limitTicket/calculateLimitTicket.user?uid='+uid+'&sku_cashs_tickets='+sku_cashs_tickets+'&limitTicketIds='+limitTicketIds,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(res) {
	    	$("#coupon").remove();
			var html='';
			if (res.success) {
				var result = res.data;
		    	$("#coupon_ticket").val(result.ticket);
		    	$("#coupon_cash").val(result.cash);
		    	$("#coupon_all").val(result.amount);
		    	$("#coupon_ids").val(result.limitTicketIds);
		    	if(result.limitTicketIds == "0") {
		    		html+='<li id="coupon" class="youhuiquan youhuiquan2" onclick="javascript:toSelectTicket();">';
		    		html+='<a href="javascript:;"><div class="p1">优惠券：<i>' + result.names +'</i></div><div class="p2">未使用</div></a>';
		    		html+='</li>'
				    $("#coupon_key").val("");
		    	} else {
		    		html+='<li id="coupon" class="youhuiquan" onclick="javascript:toSelectTicket();">';
		    		html+='<a href="javascript:;"><div class="p1">优惠券：' + result.names +'</div><div class="p2">减'+result.amount.toFixed(2)+'</div></a>';
		    		html+='</li>'
				    $("#coupon_key").val(result.keyInfo);
		    	}

		    	$(".dikouquan ul").prepend(html);
				//计算价格
		    	refreshCash();
			}else{
		    	$("#coupon_ticket").val(0);
		    	$("#coupon_cash").val(0);
		    	$("#coupon_all").val(0);
		    	$("#coupon_ids").val("");
		    	$("#coupon_key").val("");
		    	
				//计算价格
		    	refreshCash();
			}
		},
		error : function() {}
	});
}
function toSelectTicket() {
	var $listSku  = $('input[name="skuId"]');
	var skuIds="";
	for(var i=0;i<$listSku.length;i++) {
		skuIds += $($listSku[i]).val() + ",";
	}
	if(skuIds.length>0) {
		skuIds= skuIds.substring(0,skuIds.length-1)
	}
	window.location = jsBasePath +'limitTicket/getOneselfTicketPage.user?uid='+uid+'&limitTicketIds='+$("#coupon_ids").val()+'&skuIds='+skuIds;
}
function getWithOutTicketBuySkuId(){
	var $listSku  = $('input[name="skuId"]');
	var skuIds="";
	for(var i=0;i<$listSku.length;i++) {
		skuIds += $($listSku[i]).val() + ",";
	}
	if(skuIds.length>0) {
		skuIds= skuIds.substring(0,skuIds.length-1)
	}
	$.ajax({
		url : jsBasePath +'limitTicket/getWithOutTicketBuySkuId.user?uid='+uid+'&skuIds='+skuIds,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(res) {
			if (res.success) {
				var list = res.data;
				if(list.length>0){
					try{
						if(showLimitTickets && typeof(showLimitTickets) == "function"){
							showLimitTickets(list);
						} else {
							$.getScript(jsBasePath+"static_resources/js/limit_ticket/order_limit_ticket_without_show.js", function() {
								showLimitTickets(list);
							});
						}
					}catch(e){
						$.getScript(jsBasePath+"static_resources/js/limit_ticket/order_limit_ticket_without_show.js", function() {
							showLimitTickets(list);
						});
					}
				}
			}
		},
		error : function() {}
	});
}
