$(function(){{
	querydata()
}})

	function querydata(){
		$.ajax({
			url :jsBasePath+'group/tuan_order.user?uid='+userId+'&groupId='+groupId,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    cache:false,
			success : function(data){
				var list = data.data;
				var html = "";
				var totalPrices =0;
				var totalTickets =0;
				html+="<div class='main-cont' id='main-cont' >";
				html+="<div class='main_con shop_con' style='background:none;'>";
				for (var i = 0; i < list.length; i++) {
						var totalPrice =0;
						var totalTicket =0;
						html+="<div class='shop_con_box'>";
						html+="<div class='shop_top1' style='background:none;'>";
						html+="<div class='tz_con'>";
						html+="<div class='shop_top_img'>"
						if(list[i].isLadder == 1){
							html+="<em></em>"
						}
						html+=	"<img src='"+list[i].userAvatar+"' />";
						html+="</div>";
						var phonenum = list[i].phoneNum;
						html+="<p>"+phonenum.substring(0,3)+"***"+phonenum.substring(10,11)+"</p>";
						html+="</div>";
						html+="<div class='pri_con'>";
						for (var j = 0; j < list[i].groupSuborderitemList.length; j++) {
							totalPrice+=list[i].groupSuborderitemList[j].internalPurchasePrice;
							totalTicket+=list[i].groupSuborderitemList[j].companyTicket;
						}
						totalPrices+=totalPrice;
						totalTicket+=totalTicket;
						html+="<p class='p1'>￥<em>"  + totalPrice + "</em>+内购券"  + totalTicket + "</p>";
						html+="<p class='p2'>已省"+list[i].saveProductAmonut+"</p>";
						html+="</div>";
						html+="<span></span>";
						html+="</div>";
						html+="<div class='shop_con_dl'>";
					for (var j = 0; j < list[i].groupSuborderitemList.length; j++) {
						html+="<dl>";
						html+="<a href='javascript:;'>";
						html+="<dt><img src='"+list[i].groupSuborderitemList[j].image+"' /></dt>";
						html+="<dd class='dd1'>"+list[i].groupSuborderitemList[j].productName+"</dd>";
						html+="<dd class='dd2'>"+list[i].groupSuborderitemList[j].itemValues+"</dd>";
						html+="<dd class='dd3'><p>￥<span>"+list[i].groupSuborderitemList[j].internalPurchasePrice+"</span>+内购券 "+list[i].groupSuborderitemList[j].companyTicket+"</p><em>X<i>"+list[i].groupSuborderitemList[j].number+"</i></em></dd>";
						html+="</a>";
						html+="</dl>";
					}
					html+="</div>";
					html+="</div>";
				}
				html+="</div>";
				html+="</div>";
				html+="<div class='tuan_order_bottom'>";
				html+="<dl>";
				html+="<dt>合计：</dt>";
				html+="<dd class='dd1'>￥<span>"+totalPrices+"</span>+内购券"+totalTickets+"</dd>";
				html+="<dd class='dd2'>（已省￥"+list[0].groupSaveAmonut+"）</dd>";
				html+="</dl>";
				if(data.msg != ""){
					var numMatchExpress = data.msg.split(",");
					html += "<p>";
					var flag = "";
					for (var n = 0; n < numMatchExpress.length; n++) {
						var numMatch = numMatchExpress[n].split("=");
						if(numMatch[0] == "priceNumMatchExpress"){
							html+="还差￥"+numMatch[1]+"包邮";
							flag = numMatch[1];
						}
						if(numMatch[0] == "buyNumMatchExpress"){
							if(flag == ""){
								html+="还差"+numMatch[1]+"件包邮";
							}else{
								html+="再凑"+numMatch[1]+"件,且￥"+flag+"以上包邮";
							}
						}
					}
					html += "</p>";
				}
				html+="</div>";
				$("#main-cont").html(html);
			},
			error : function(){
			}
		});
	}
$(".shop_top1 span").live('click', function() {
	if($(this).parent().next(".shop_con_dl").css("display") == "none"){
		$(this).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/tuan_order_zk.png) no-repeat center 5px","background-size":"20px 10px"});
		$(this).parent().next(".shop_con_dl").show();
	}else{
		$(this).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/tuan_order_sq.png) no-repeat center 5px","background-size":"20px 10px"});
		$(this).parent().next(".shop_con_dl").hide();
	}
}); 
//$(".shop_top1 span").toggle(function(){
//	$(this).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/tuan_order_zk.png) no-repeat center 5px","background-size":"20px 10px"});
//	$(this).parent().next(".shop_con_dl").show();
//},function(){
//	$(this).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/tuan_order_sq.png) no-repeat center 5px","background-size":"20px 10px"});
//	$(this).parent().next(".shop_con_dl").hide();
//}
//);