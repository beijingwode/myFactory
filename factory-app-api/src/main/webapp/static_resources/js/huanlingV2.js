// JavaScript Document
$(document).ready(function() {
	$("#more ul").html("");
	getTsMore(1);
});
var specificationsId='';
function getTsMore(page) {
	var searchKey="";
	var moreKey= $("#moreKey").val();
	var moreVal= $("#moreVal").val();
	if(moreKey!="" && moreVal!="" ) {
		searchKey = moreKey +"="+moreVal + "&";
	}
	var cate = $(".swiper-wrapper .thisone").attr("data-index");
	var flag = true;
	if(cate && cate!=null && cate!=""){
		searchKey = searchKey+ "cat="+cate;
	}else{
		flag =false;
	}
	if(flag){
		$.ajax({
			type: "get",
			dataType: "json",  //返回json格式的数据  
			async: false,
			cache: false,
			url: "pSearch?"+searchKey+"&page="+page,
			success: function(data){
				if(data.success){
					var cost = data.msg;
					var result = data.data;
					var hits = result.hits;
					var html = '';
					var rprice = 0;
					if(hits.length>0){
						for(var i=0;i<hits.length;i++) {
							var name = hits[i].name;
							if(hits[i].salePrice && hits[i].salePrice!=null){
								rprice = hits[i].salePrice;
							}else{
								rprice = hits[i].price-hits[i].maxFucoin;
							}
							if (hits[i].maxFucoin>cost) {
								hits[i].maxFucoin=cost;
							}
							var productId = hits[i].productId;
							if(hits[i].minSkuId && typeof(hits[i].minSkuId)!="undefined"){
								specificationsId = hits[i].minSkuId;
							}
							html +='<li>';
							html +='<dl>';
							html +='<a href="javascript:go2Search(\''+productId+'&specificationsId='+specificationsId+'&pageKey=huanling'+'\')">';
							html +='<dt><img src="'+hits[i].image+'" /></dt>';
							html +='<dd class="dd1">'+name+'</dd>';
							html +='<dd class="dd2"><span>'+(rprice.toFixed(2))+'</span><i><img src="static_resources/images/huanlingbi_icon.png" /></i></dd>';
							html +='<dd class="dd3">还剩'+hits[i].stock+'份</dd>';
							html +='</a>';
							html +='</dl>';
							html +='</li>';
						}
						$("#more ul").append(html);
						$("#more").show();
						if(hits.length == 20) {
							setTimeout("getTsMore("+(++page)+");", 100);
						}
					}else{
						$("#more").hide();
					}
				}else{
					$("#more").hide();
				}
			}
		});
	}else{
		$("#more").hide();
	}
	
}