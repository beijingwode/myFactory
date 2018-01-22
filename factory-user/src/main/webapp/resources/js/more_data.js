$(function(){
		ajaxpageDataIndex();
	});

function ajaxpageDataIndex(){
	var searchKey="";
	var moreKey= $("#moreKey").val();
	var moreVal= $("#moreVal").val();
	if(moreKey!="" && moreVal!="" ) {
		searchKey = moreKey +"="+moreVal + "&";
	}
	
	var key="";
	var url=window.location.href;
	var p = url.indexOf("key=");
	if(p>-1) {
		url = url.substring(p+4);
		p=url.indexOf("&");
		if(p>-1) {
			key= url.substring(0,p);
		} else {
			key=url;
		}
	}
	$(".searchinput").val(decodeURI(key));
	var specificationsId='';
	$.ajax({
		url: "/product/jsonSearch?"+searchKey+"key="+encodeURI(key),
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			var result = data.data;
			var cost = data.msg;
			if(result==null || typeof(result) == "undefined") {
				$(".ts_tit").hide();
			} else {
				var html='';
				for(var i=0;i<result.length;i++) {
					
					if(result[i].minSkuId && typeof(result[i].minSkuId)!="undefined"){
						specificationsId = result[i].minSkuId;
					}
					var name = result[i].name;
					var start=name.indexOf("<em>");
					var end =name.indexOf("</em>");
					if(start!=-1 && end!=-1){
						name=name.replace("<em>","").replace("</em>","");
					}
					html +='<li>';
					html +='<dl>';
					html +='<dt><a href="/'+result[i].productId+'.html?specificationsId='+specificationsId+'&pageKey='+$("#pageKey").val()+'" target="_blank"><img src="'+result[i].image+'" /></dt>';
					html +='<dd><div style="width:auto;overflow:hidden;white-space:nowrap;text-overflow:ellipsis;-o-text-overflow:ellipsis;"><a href="/'+result[i].productId+'.html?specificationsId='+specificationsId+'&pageKey='+$("#pageKey").val()+'" target="_blank" class="QCproductName">'+name+'</a></div></dd>';
					html +='<dd><span>内购价:￥<i>'+result[i].salePrice.toFixed(2)+'</i></span></dd>';
					html +='<dd><em>电商价：￥'+result[i].price.toFixed(2)+'</em></dd>';
					html +='</dl>';
					html +='</a>';
					html +='</li>';
				}

				$("#like_li").html(html);
			}
			
		},
		error : function() {}
	});
}
