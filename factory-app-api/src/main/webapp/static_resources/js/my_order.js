$(function(){{
	querydata()
}})
	function querydata(){
		$.ajax({
			url :jsBasePath+'group/my_order.user?uid='+userId+'&groupId='+groupId,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    cache:false,
			success : function(data){
				var list = data.data;
				var html = "";
				for (var i = 0; i < list.length; i++) {
					var totalPrice =0;
					var totalTicket =0;
					var totalshipping =0;
					var totalShipping=list[i].totalShipping;
					var nowShipping= list[i].nowShipping;
					var newShipping = totalShipping-nowShipping;
					html+="<div class='main-cont' id='main-cont' >";
					html+="<div class='main_con shop_con'>";
					html+="<div class='shop_top'>";
					html+="<p>"+list[i].groupBuy.shopName+"</p> ";
					html+="<a href='javascript:;'>进去店铺</a>";
					html+="</div>";
					for (var j = 0; j < list[i].groupSuborderitemList.length; j++) {
						html+="<dl>";
						html+="<a href='javascript:;'>";
						html+="<dt><img src='"+list[i].groupSuborderitemList[j].image+"' /></dt>";
						html+="<dd class='dd1'>"+list[i].groupSuborderitemList[j].productName+"</dd>";
						html+="<dd class='dd2'>"+list[i].groupSuborderitemList[j].itemValues+"</dd>";
						html+="<dd class='dd3'><p>￥<span>"+list[i].groupSuborderitemList[j].internalPurchasePrice+"</span>+内购券"+list[i].groupSuborderitemList[j].companyTicket+"</p><em>X<i>"+list[i].groupSuborderitemList[j].number+"</i></em></dd>";
						html+="</a>";
						html+="</dl>";
						totalPrice+=list[i].groupSuborderitemList[j].internalPurchasePrice;
						totalTicket+=list[i].groupSuborderitemList[j].companyTicket;
						totalshipping+=list[i].groupSuborderitemList[j].shipping;
					}
					html+="</div>";
					html+="<div class='yunfeiheji my_order'>运费合计：<span>￥"+totalshipping+"</span></div>";
					html+="<div class='yunfeiheji my_order'>团内人数："+list[i].joinNum+"<span>已省 ￥"+newShipping+"</span></div>";
					html+="</div>";
					html+="<div class='heji'>合计：<p>￥<em>"+totalPrice+"</em> + 内购券  "+totalTicket+"</p></div>";
				}
					html+="<div class='TogetherToBuy_help'><a href='"+jsBasePath+"TogetherToBuy/TogetherToBuy_help.html'>如何一起购？</a></div>";
				$("#main-cont").html(html);
				
			},
			error : function(){
				alert("err");
			}
		});
	}