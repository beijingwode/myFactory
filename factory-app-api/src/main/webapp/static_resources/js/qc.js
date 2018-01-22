// JavaScript Document
$(document).ready(function() {
	$("#more ul").html("");
	getTsMore(1);
	$("#more").show();
});
var specificationsId='';
function getTsMore(page) {
	$.ajax({
		type: "get",
		dataType: "json",  //返回json格式的数据  
		async: false,
		cache: false,
		url: "pSearch?tagFlg=1&page="+page,
		success: function(data){
			if(data.success){
				var cost = data.msg;
				var result = data.data;
				var hits = result.hits;
				var html = '';
				var arr = new Array();
				var rprice = 0;
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
					/*var product='/'+productId+'.html';
					html +='<li><dl>';
					html +='<dt><a href="javascript:go2Search(\''+product+'\')"><img src="'+hits[i].image+'" width="292px" height="292" /></a></dt>';
					html +='<dd style="margin-top:10px;"><a href="javascript:go2Search(\''+product+'\')" style="overflow: hidden;white-space:nowrap;" >'+name+'</a></dd>';
					html +='<dd><span>内购价：￥'+(hits[i].price - hits[i].maxFucoin).toFixed(2)+'</span></dd>';
					html +='<dd><em>电商价：￥'+(hits[i].price).toFixed(2)+'</em></dd>';
					html +='</dl></li>';*/
					html +='<li>';
					html +='<dl>';
					html +='<dt><a href="javascript:go2Search(\''+productId+'&specificationsId='+specificationsId+'&pageKey=qc'+'\')"><img src="'+hits[i].image+'" /></a>';
						/*if(hits[i].saleKbn==1) {
							html +='<div class="picon"><img src="static_resources/images/picon2.png" /></div>';
						} else if(hits[i].saleKbn==2) {
							html +='<div class="picon"><img src="static_resources/images/picon_c2.png" /></div>';
						} else if(hits[i].saleKbn==5) {
							html +='<div class="picon"><img src="static_resources/images/picon_t2.png" /></div>';
						}*/
					html +='</dt>';
					html +='<dd class="dd1"><a href="javascript:go2Search(\''+productId+'&specificationsId='+specificationsId+'&pageKey=qc'+'\')">'+name+'</a></dd>';
					html +='<dd><span>￥'+(rprice.toFixed(2))+'+'+parseFloat(hits[i].maxFucoin).toFixed(2)+'券</span></dd>';
					html +='</dl>';
					html +='</li>';
				}
				$("#more ul").append(html);
				if(hits.length == 20) {
					setTimeout("getTsMore("+(++page)+");", 3000);
				}
			}else{
				$("#more").hide();
			}
		}
	});
}