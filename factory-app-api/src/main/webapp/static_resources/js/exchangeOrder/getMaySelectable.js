function adjustProduct(){
	if(uid=='') return;
	var flag= false;
	if(subOrderId && subOrderId!='' && subOrderId!=null){
		flag =true;
	}
	if(orderId && orderId!='' && orderId!=null){
		flag =true;
	}
	if(flag){
		$.ajax({
			url : jsBasePath +'exchangeOrder/queryProduct.user?uid='+uid+'&subOrderId='+subOrderId+'&orderId='+orderId,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
			async: true,
			success : function(data) {
				if (data.success) {
					var result =data.data;
					var html='';
					if(result.length>0){
						for (var i = 0; i < result.length; i++) {
							html+='<li>';
							html+='<dl>';
							html+='<dt><img src="'+result[i].imagePath+'" /></dt>';
				    		html+='<dd class="dd1">'+result[i].productName+'</dd>';
				    		html+='<dd class="dd2"></dd>';
				    		html+='<dd class="dd3"><span>'+result[i].salePrice.toFixed(2)+'</span><i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i></dd>';
				    		html+='</dl>';
				    		html+='<em onclick="toggleSel(this,\'item\');"> <input type="hidden" name="sel_item"/> <input type="hidden" name="productId" value="'+result[i].productId+'"/></em>';
				    		html+='</li>';
						}
						$(".tiaoji_con ul").html(html);
						$(".tiaoji_box").show();
						$(".thickdiv").show();
					}
				}
			},
			error : function() {}
		});
	}
}
function toggleSel(obj,type) {
	if('item' == type) {
		var sel=$(obj).children("input[name='sel_item']");
		if($(sel).val() == 1) {
			$(sel).val("0");
			$(obj).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
		} else {
			$(sel).val("1");
			$(obj).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
		}
	}
}
function disNone(){
	$(".thickdiv").hide();
	$(".tiaoji_box").hide();
}
function chooseGood(){
	var $listSel  = $('input[name="sel_item"]');
	var $productIds  = $('input[name="productId"]');
	var partNumbers = "";
	for(var i=0;i<$listSel.length;i++) {
		if($($listSel[i]).val()=="1") {
			partNumbers += $($productIds[i]).val()+",";
		}
	}
	if(partNumbers=="" ||partNumbers.replace(/,/g,'')==""){
		return showInfoBox("请选择调剂商品");
	}else{
		$.ajax({
			url : jsBasePath +'exchangeOrder/addFavorites.user?uid='+uid+'&Products='+partNumbers,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
			async: true,
			success : function(data) {
				if (data.success) {
					disNone();
				}
			},
			error : function() {}
		});
	}
}