// JavaScript Document
var num ='1';
$(document).ready(function() {
	$(".all_product_list ul").html("");
//	getTsMore(1);
//	$("#more").show();
	/*$(".u1 li").each(function(){
		  var y = $(this).children().last();
		  alert(y.text());
	});*/
	getSyMore();
	$('.u1 li a').click(function(){
	    $('.u1 li').removeClass('surr');
	    $(this).parent().addClass('surr');
	    getSyMore();
	  })
	$('.u2 li a').click(function(){
	    $('.u2 li').removeClass('surr');
	    $(this).parent().addClass('surr');
	    if($(this)[0].id=="price"){
	    	if(num=='1'){
	    		$(this).parent().removeClass("down");
	    		$(this).parent().addClass("up");
	    		num='2';
	    	}else{
	    		$(this).parent().removeClass("up");
	    		$(this).parent().addClass("down");
	    		num='1';
	    	}
	    }
	    getSyMore();
	 })
	
});

function getSyMore() {
	var tagFlg='';
	var sort='';
	var condition1=$(".u1").children("li.surr").children("a")[0].id;
	if(condition1=="gr"){//个人级限购
		tagFlg="0X";
	}else{
		tagFlg="1X";
	}
	var condition2=$(".u2").children("li.surr").children("a")[0].id;
	if(condition2=="discount"){//折扣
		sort="discount_0"
	}else if(condition2=="price"){
		if(num =='1'){
			sort="price_1"
		}else{
			sort="price_0"
		}
	}
	$.ajax({
		type: "POST",
		url: "/product/jsonSearch?saleKbn=5&tagFlg="+tagFlg+"&sort="+sort,
		success: function(data){
			if(data.success){
				var hits = data.data;
				var html = '';
				for(var i=0;i<hits.length;i++) {
					var name = hits[i].name;
					//name=name.substring(0,15);
					html +='<li class="at">';
					html +='<div class="all_product_pho">';
					html +='<a href="/'+hits[i].productId+'.html?pageKey=shiyong"  target="_blank">'
					html +='<img src="'+hits[i].image+'"/>';
					html +='</a>';
					html +='</div>';
					html +='<h2>'
					html +='<a href="/'+hits[i].productId+'.html?skuId='+hits[i].minSkuId+'?pageKey=shiyong" target="_blank">'+name+'</a>';
					html +='</h2>'
					html +='<p class="p1">';
					html +='<span>内购价：¥'+hits[i].salePrice+'<i>+'+hits[i].maxFucoin+'券</i></span>';
					html +='<em>'+(hits[i].salePrice/hits[i].price*10).toFixed()+'折</em>';
					html +='</p>';
					html +='<p class="p2">电商价：￥ '+hits[i].price+'</p>';
					html +='<em><a href="/shop/'+hits[i].shopId+'target="_blank">'+hits[i].shopName+'</a></em>';
					html +='</li>';
				}
				$(".all_product_list ul").html(html);
			}
		}
	});
}