// JavaScript Document
//顶部鼠标滑过掌上生活二维码显示
$(document).ready(function() {
	$("#more ul").html("");
//	getTsMore(1);
//	$("#more").show();
});

function getTsMore(page) {
	$.ajax({
		type: "POST",
		url: "/product/jsonSearch?saleKbn=1&page="+page,
		success: function(data){
			if(data.success){
				var hits = data.data;
				var html = '';
				for(var i=0;i<hits.length;i++) {
					var name = hits[i].name;
					name=name.substring(0,15);
					html +='<li><dl>';
					html +='<dt><a href="/'+hits[i].productId+'.html?pageKey=tesheng"  target="_blank"><img src="'+hits[i].image+'" width="292px" height="292" /></a></dt>';
					html +='<dd style="margin-top:10px;"><a href="/'+hits[i].productId+'.html?pageKey=tesheng"  target="_blank">'+name+'</a></dd>';
					html +='<dd><span>内购价：￥'+(hits[i].price - hits[i].maxFucoin).toFixed(2)+'</span></dd>';
					html +='<dd><em>电商价：￥'+(hits[i].price).toFixed(2)+'</em></dd>';
					html +='</dl></li>';
				}
				$("#more ul").append(html);
				if(hits.length == 20) {
					setTimeout("getTsMore("+(++page)+");", 3000);
					
				}
			}
		}
	});
}