
function ajaxGetLimitTicket(uid,sku){
	$.ajax({
		url : jsBasePath +'limitTicket/getProductTicket.user?uid='+uid+'&skuId='+sku,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				var list = data.data;

				try{
					if(showLimitTickets && typeof(showLimitTickets) == "function"){
						showLimitTickets(uid,sku,list);
					} else {
						$.getScript(jsBasePath+"static_resources/js/limit_ticket/limit_ticket_show.js", function() {
							showLimitTickets(uid,sku,list);
						});
					}
				}catch(e){
					$.getScript(jsBasePath+"static_resources/js/limit_ticket/limit_ticket_show.js", function() {
						showLimitTickets(uid,sku,list);
					});
				}
			}
		},
		error : function() {}
	});
}