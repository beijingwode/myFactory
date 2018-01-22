var uid=GetUidCookie();
$(function(){
	if(type==0){
		if(userAvatar){
			$(".top_dl dt img").attr("src",userAvatar);
		}
		$(".top_dl").show();
	}
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
		if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){
		isPageHide = true;
	});  
	queryGroupBuyProduct();
	$(".thickclose").click(function(){
		$(".thickdiv").hide();
		$(".thickbox1").hide();
		$(".thickbox2").hide();
	});
	
	$(".thickdiv").click(function(){
		$(".thickdiv").hide();
		$(".thickbox1").hide();
		$(".thickbox2").hide();
	});
   //点击购买数量
   //获得文本框对象
   var t = $("#number");
   //初始化数量为1,并失效减
	if (parseInt(t.val())<=1 ) {//初始化数量小于等于起售数量
		$('#quantityMinus').attr('disabled',true);
	}
    //数量增加操作
    $("#quantityPlus").on("click",function(){  
    	if (parseInt(t.val()+1)>1){
	        $('#quantityMinus').attr('disabled',false);
	    } 
    	if(parseInt(productSkuStock)<=parseInt(t.val())){//库存小于等于当前数量
			$('#quantityPlus').attr('disabled',true); return
		}else{
			 t.val(parseInt(t.val())+1)
	 	        ajaxGetImage(selSku(),"update");
		}
        if (parseInt(t.val())!=1){
            $('#quantityMinus').attr('disabled',false);
        }
    }) 
    //数量减少操作
    $("#quantityMinus").on("click",function(){
    	if (parseInt(t.val())<=1){
	    		$('#quantityMinus').attr('disabled',true); return
	    	}
	    	t.val(parseInt(t.val())-1);
	    if(parseInt(productSkuStock)==parseInt(t.val())){
	    	$('#quantityPlus').attr('disabled',true);
	    		checkStock(number);
	    }else if(parseInt(productSkuStock)>parseInt(t.val())){
	    	$('#quantityPlus').attr('disabled',false);
	    	checkStock(number);
	    }else{
	    	 return;
	    }
	    ajaxGetImage(selSku(),"update");
    })
    
})

function thickclose(){
		$(".thickdiv").hide();
		$(".thickbox1").hide();
		$(".thickbox2").hide();
}
var productSkuStock = 0;//商品规格对应库存
function queryGroupBuyProduct(){
	$.ajax({
	    url:jsBasePath + "group/getGroupBuyProducts.user?uid="+uid+"&groupId="+groupId+"&showSkuConut=1",
	    type : "POST",
		dataType: "json",  //返回json格式的数据
		cache:false,
	    async: false,
	    success:function(data){
	    	if(data.success) {
	    		var html = '';
	    		var result = data.data;
	    		$("#shopName").html(result[0].exp4);//店铺名称
	    		for (var i = 0; i < result.length; i++) {
	    			html+='<li>';
	    			html+='<p class="number" >已选<span id="selectNum_'+result[i].productId+'">0</span>件</p>';               	
	    			html+='<dl>';
	    			html+='<dt>';
	    			if(result[i].minLimitNum > 1){
	    				if(result[i].purchasedNum > result[i].minLimitNum){
		    				if(result[i].bestPrice != 0){
			    				html+='<span class="jietijia">阶梯价</span>';
			    			}
		    			}else{
		    				html+='<span class="jietijia">'+result[i].minLimitNum+'件开团</span>';
		    			}
	    			}else{
	    				if(result[i].bestPrice != 0){
		    				html+='<span class="jietijia">阶梯价</span>';
		    			}
	    			}
	    			html+='<a href="javascript:void(0);"><img src="'+result[i].image+'" /></a></dt>';
	    			html+='<dd class="dd0">团内已购'+result[i].purchasedNum+'件</dd>';
	    			html+='<dd class="dd1">';
	    			html+='<a href="javascript:void(0);">'+result[i].productName+'</a>';                       	
	    			html+='</dd>';
	    			html+='<dd class="dd3">电商价:￥'+parseFloat(result[i].marketPrice).toFixed(2)+'</dd>';
	    			html+='<dd class="dd4">￥<span>'+parseFloat(result[i].internalPurchasePrice).toFixed(2)+'</span><i>+'+parseFloat(result[i].maxFucoin).toFixed(2)+'券</i></dd>';
	    			html+='</dl>';
	    			html+='<em onclick="selectSku(this)"></em>';
	    			html +='<input type="hidden" name="productId" value="'+result[i].productId+'">';
	    			html +='<input type="hidden" name="skuId" value="'+result[i].skuId+'">';
	    			html +='<input type="hidden" name="itemValues" value="'+result[i].itemValues.replace("{","").replace("}","").replace(/\"/g,"")+'">';
	    			html +='<input type="hidden" name="productName" value="'+result[i].productName+'">';
	    			html+='</li>';
				}
	    		$("#product").html(html);
	    		setSelectNum();
	    		
	    	}
	    },
	 	error:function(){
		}
	});
}
function selectSku(that){
	var productId =($(that).nextAll('input[name="productId"]').val());
	var skuId =($(that).nextAll('input[name="skuId"]').val());
	
	var goods = localStorage.getItem("cartItem"+skuId);//取回cartItemStr
    goods = JSON.parse(goods);//把字符串转换成JSON对象
    if(goods!=null && goods!="undefined"){
    	$("#number").val(goods.num);
    }else{
    	$("#number").val(1);
    }
	var productName =($(that).nextAll('input[name="productName"]').val());
	var itemValues =($(that).nextAll('input[name="itemValues"]').val());
	if(productId ==null && typeof(productId)=="undefined" || productId=='') return;
	if(skuId ==null && typeof(skuId)=="undefined" || skuId=='') return;
	$.ajax({
	    url:jsBasePath + "group/getProductSkuNumberAndPrice.user?uid="+uid+"&groupId="+groupId+"&productId="+productId+"&skuId="+skuId+"&quantity="+$("#number").val(),
	    type : "POST",
		dataType: "json",  //返回json格式的数据
		cache:false,
	    async: false,
	    success:function(data){
	    	if(data.success) {
	    		var result = data.data;
	    		$(".thickbox1").data("productId",productId);
	    		$(".thickbox1").data("productName",productName);
	    		$(".thickbox1").data("skuPrice",result.internalPurchasePrice);
	    		$(".thickbox1").data("maxFucoin",result.maxFucoin);
	    		$(".thickbox1").data("stock",result.stock);
	    		$(".thickbox1 .skuImage img").attr("src",result.imageSource);
	    		$(".thickbox1 .dd0").html('团内已购'+result.suborderItemSum+'件');
	    		if(result.minLimitNum > 1){
    				if(result.suborderItemSum <result.minLimitNum){
    					$(".thickbox1 .jietijia").html(result.minLimitNum+"件开团");
	    			}else{
	    				if(result.ladderPrice > 0){
	    					$(".thickbox1 .jietijia").html("阶梯价");
		    			}else{
		    				$(".thickbox1 .jietijia").hide();
		    			}
	    			}
    			}else{
    				if(result.ladderPrice > 0){
    					$(".thickbox1 .jietijia").html("阶梯价");
	    			}else{
	    				$(".thickbox1 .jietijia").hide();
	    			}
    			}
	    		$(".thickbox1 .dd1 a").html(productName);
	    		$(".thickbox1 .dd3").html('电商价:￥'+parseFloat(result.marketPrice).toFixed(2));
	    		$(".thickbox1 .dd4").html('￥<span id="price">'+parseFloat(result.internalPurchasePrice).toFixed(2)+'</span><i id="maxFucoin">+'+parseFloat(result.maxFucoin).toFixed(2)+'券</i>');
	    		if(result.badNumber > 0){
	    			$(".thickbox1 .jtj_box p").html('团内当前单价：¥'+parseFloat(result.internalPurchasePrice).toFixed(2)+'，还差'+result.badNumber+'件达到：<i>¥'+parseFloat(result.ladderPrice).toFixed(2)+'</i>');
	    		}else{
	    			$(".thickbox1 .jtj_box p").html('团内当前单价：¥'+parseFloat(result.internalPurchasePrice).toFixed(2)+'，<i>已达到最低阶梯价价格</i>');
	    		}
	    		if(result.ladderPrice > 0){
	    			$(".thickbox1 .jtj_box").show();
	    		}else{
	    			$(".thickbox1 .jtj_box").hide();
	    		}
	    		productSkuStock = result.stock;
	    		$(".thickbox1 .kucun").html('库存：'+result.stock+'件');
	    		checkStock(result.stock);
	    		getSkuMsg(productId,skuId);
	    		$("#specificationsId").val(skuId);
	    		$("#itemValues").val(itemValues);
				$(".thickdiv").show();
				$(".thickbox1").show();
				$(".bottom_box").removeClass('bottom_box1');
	    	}
	    },
	 	error:function(){
		}
	});
}
var stockMap =null;
var skuMap =null ;
function getSkuMsg(productId,skuId){
	$.ajax({
	    url:jsBasePath + "group/getGroupBuyProductNumber.user?uid="+uid+"&groupId="+groupId+"&productId="+productId+"&skuId="+skuId,
	    type : "POST",
		dataType: "json",  //返回json格式的数据
		cache:false,
	    async: false,
	    success:function(data){
	    	var result = data.data;
	    	if(result != null) {
	    		var smap = result.smap;
		    	var defaultSelectSKU = result.strMap;
		    	skuMap = result.skuMap;
		    	stockMap = result.stockMap;
		    	var index = 0;
		    	$(".thickbox1 .thickcon .pro-color p").eq(0).html('');
		    	$(".thickbox1 .thickcon .pro-color p").eq(1).html('');
		    	for(i in smap){
		    		index++;
		    		var div;
		    		if(index==1) {
		    			div=$(".thickbox1 .thickcon .pro-color").eq(0);
		    		} else if(index==2)  {
		    			div=$(".thickbox1 .thickcon .pro-color").eq(1);
		    		} else {
		    			break;
		    		}
		    		
		    		$(div).children(".part-note-msg").html(i);
		    		
					var vl = smap[i];
					var as = "";
					for(var j=0;j<vl.length;j++) {
						as += '<a title="1" class="a-item J_ping" id="'+ vl[j].id +'" href="javascript:void(0);" onclick="sel(this)">'+vl[j].specificationValue+'</a>';	
					}
					$(div).children("p").html(as);
		    	} 
		    	
		    	if(index<2) {
		    		$(".thickbox1 .thickcon .pro-color").eq(1).hide();
		    	}else{
		    		$(".thickbox1 .thickcon .pro-color").eq(1).show();
		    	}
	    		var divs = $(".thickbox1 .thickcon .pro-color");
		    	var selected="";
		    	for(i in defaultSelectSKU){
		    		for(var k=0;k<divs.length;k++) {
		    			if($(divs[k]).children(".part-note-msg").html()==i) {
							var as = $(divs[k]).find("p a");
							for(var j=0;j<as.length;j++) {
								if($(as[j]).attr("id")==defaultSelectSKU[i]) {
									selected += " " + $(as[j]).html();
									$(as[j]).addClass("selected");
									break;
								}
							}
		    			}
		    		}
		    	}
	    	}
	    },
	 	error:function(){
		}
	});
}
//规格选择
function sel(obj) {
	$($(obj).parent().children()).removeClass("selected")
	var notSel=$(".thickcon1 .pro-color .disable");
	for (var i = 0; i < notSel.length; i++) {
		var notSelId=$(notSel[i]).attr("id");
		$("#"+notSelId+"").removeClass("disable");
	}
	$(obj).addClass("selected");
	togetStock();
	ajaxGetImage(selSku(),"select");
}

function togetStock(){
	var selects = $(".thickbox1 .thickcon .pro-color .selected");
	var selects2 = $(".thickbox1 .thickcon .pro-color .selected").siblings();//得到所有兄弟级元素节点
	var notSel=$(".thickbox1 .thickcon .pro-color .disable");
	for (var i = 0; i < notSel.length; i++) {
		var notSelId=$(notSel[i]).attr("id");
		$("#"+notSelId+"").removeClass("disable");
		$("#"+notSelId+"").attr("onclick","sel(this)");
	}
	var sel1,sel2,selt,sel3,sel5;
	sel1=$(selects[0]).attr("id");
	if(selects.length==2) {
		sel2=$(selects[1]).attr("id");
		if(sel1>sel2) {
			selt=sel2+","+sel1;
		} else {
			selt=sel1+","+sel2;			
		}
		
		var pid1=$(selects[0]).parent().attr("id");
		for (var i = 0; i < selects2.length; i++) {
			var bPid=$(selects2[i]).parent().attr("id");//兄弟元素的父级id
			if (pid1==bPid) {//相同同一父级下规格
				sel3=$(selects2[i]).attr("id");//
				if (sel2>sel3) {
					sel5 = sel3+","+sel2;
				}else{
					sel5 = sel2+","+sel3;
				}
			}else{//不是同一父级下的规格
				sel3=$(selects2[i]).attr("id");
				if (sel1>sel3) {
					sel5 = sel3+","+sel1;
				}else{
					sel5 = sel1+","+sel3;
				}
			}
			if (stockMap[sel5]==0) {
				$("#"+sel3+"").addClass("disable");
			}
		}
		
	} else if(selects.length==1) {//只有一个规格
		selt=sel1;
		for (var i = 0; i < selects2.length; i++) {
			var itemSkuId=$(selects2[i]).attr("id");
			if(stockMap[itemSkuId]==0){//库存为零
				$("#"+itemSkuId+"").addClass("disable");
			}
		}
	}else if(selects.length==0){//所有规格库存都为0
		for (i in stockMap) {
			var any = i.indexOf(",");
			if (any == -1) {// 表示单个规格
				$("#"+i+"").addClass("disable");
			} else {// 表示多个规格
				var split = i.split(",");
				$("#"+split[0]+"").addClass("disable");
				$("#"+split[1]+"").addClass("disable");
			}
		}
	}
}
function selSku() {
	var selects = $(".thickbox1 .thickcon .pro-color .selected");
	var selects2 = $(".thickbox1 .thickcon .pro-color .selected").siblings();//得到所有兄弟级元素节点
	var sel1,sel2,selt,sel3,sel5;
	sel1=$(selects[0]).attr("id");
	var selected=" " + $(selects[0]).html();
	if(selects.length==2) {
		sel2=$(selects[1]).attr("id");
		selected +=" " + $(selects[1]).html();
		if(sel1>sel2) {
			selt=sel2+","+sel1;
		} else {
			selt=sel1+","+sel2;			
		}
		
		var pid1=$(selects[0]).parent().attr("id");
		for (var i = 0; i < selects2.length; i++) {
			var bPid=$(selects2[i]).parent().attr("id");//兄弟元素的父级id
			if (pid1==bPid) {//相同同一父级下规格
				sel3=$(selects2[i]).attr("id");//
				if (sel2>sel3) {
					sel5 = sel3+","+sel2;
				}else{
					sel5 = sel2+","+sel3;
				}
			}else{//不是同一父级下的规格
				sel3=$(selects2[i]).attr("id");
				if (sel1>sel3) {
					sel5 = sel3+","+sel1;
				}else{
					sel5 = sel1+","+sel3;
				}
			}
		}
	} else if(selects.length==1) {//只有一个规格
		selt=sel1;
		for (var i = 0; i < selects2.length; i++) {
			var itemSkuId=$(selects2[i]).attr("id");
			if(stockMap[itemSkuId]==0){//库存为零
			}
		}
	}
	var number = $("#number").val();
	if(number=='' || number<=0) {
		number=1;
		$("#number").val(number);
	}
	
	
	if(skuMap[selt]=="0"){
		$(".thickcon .kucun").html("库存"+stockMap[selt]+"件");
		checkStock(stockMap[selt]);
	}
	
	return skuMap[selt];
}
function ajaxGetImage(sku,flag){
	if(flag != "update"){
		var goods = localStorage.getItem("cartItem"+sku);//取回cartItemStr
	    goods = JSON.parse(goods);//把字符串转换成JSON对象
	    if(goods!=null && goods!="undefined"){
	    	$("#number").val(goods.num);
	    }else{
	    	$(".thickbox1 .dd2 i").html('');
	    	$("#number").val(1);
	    }
	}
	$("#specificationsId").val(sku);
	var productId =  $(".thickbox1").data("productId");
	if(sku!="0" && productId!=null && productId!='' && typeof(productId)!="undefined"){
		$.ajax({
		    url:jsBasePath + "group/getProductSkuNumberAndPrice.user?uid="+uid+"&groupId="+groupId+"&productId="+productId+"&skuId="+sku+"&quantity="+$("#number").val(),
		    type : "POST",
			dataType: "json",  //返回json格式的数据
			cache:false,
		    async: false,
		    success:function(data){
		    	if(data.success) {
		    		var result = data.data;
		    		$(".thickbox1").data("skuPrice",result.internalPurchasePrice);
		    		$(".thickbox1").data("maxFucoin",result.maxFucoin);
		    		$(".thickbox1").data("stock",result.stock);
		    		$(".thickbox1 .skuImage img").attr("src",result.imageSource);
		    		if(result.minLimitNum > 1){
	    				if(result.suborderItemSum <result.minLimitNum){
	    					$(".thickbox1 .jietijia").html(result.minLimitNum+"件开团");
		    			}else{
		    				if(result.ladderPrice > 0){
		    					$(".thickbox1 .jietijia").html("阶梯价");
			    			}else{
			    				$(".thickbox1 .jietijia").hide();
			    			}
		    			}
	    			}else{
	    				if(result.ladderPrice > 0){
	    					$(".thickbox1 .jietijia").html("阶梯价");
		    			}else{
		    				$(".thickbox1 .jietijia").hide();
		    			}
	    			}
		    		$("#itemValues").val(result.itemValues.replace("{","").replace("}","").replace(/\"/g,""));
		    		$(".thickbox1 .dd0").html('团内已购'+result.suborderItemSum+'件');
		    		$(".thickbox1 .dd3").html('电商价:￥'+parseFloat(result.marketPrice).toFixed(2));
		    		$(".thickbox1 .dd4").html('￥<span id="price">'+parseFloat(result.internalPurchasePrice).toFixed(2)+'</span><i id="maxFucoin">+'+parseFloat(result.maxFucoin).toFixed(2)+'券</i>');
		    		if(result.badNumber > 0){
		    			$(".thickbox1 .jtj_box p").html('团内当前单价：¥'+parseFloat(result.internalPurchasePrice).toFixed(2)+'，还差'+result.badNumber+'件达到：<i>¥'+parseFloat(result.ladderPrice).toFixed(2)+'</i>');
		    		}else{
		    			$(".thickbox1 .jtj_box p").html('团内当前单价：¥'+parseFloat(result.internalPurchasePrice).toFixed(2)+'，<i>已达到最低阶梯价价格</i>');
		    		}
		    		if(result.ladderPrice > 0){
		    			$(".thickbox1 .jtj_box").show();
		    		}else{
		    			$(".thickbox1 .jtj_box").hide();
		    		}
		    		$(".thickbox1 .kucun").html('库存：'+result.stock+'件');
		    		productSkuStock = result.stock;
		    		checkStock(result.stock);
		    	}
		    },
		 	error:function(){
			}
		});
	}
}
/**
 * 判断该规格下的库存
 * @param stock
 */
function checkStock(stock){
	if(stock==0){
		$(".thickbox1 .add_buycar a").attr("class","no_product");
		$(".thickbox1 .add_buycar a").attr("href","javascript:void(0);");
		$(".thickbox1 .add_buycar a").removeAttr("onclick");
	}else{
		$(".thickbox1 .add_buycar a").removeClass("no_product");
		$(".thickbox1 .add_buycar a").attr("onclick","go2AddCart(this);");
	}
}
/**
 * 加入购物车
 * @param obj
 * @returns
 */
function go2AddCart(obj){
	var productId =$(obj).parent().parent().data("productId");//商品id
	var productName =$(obj).parent().parent().data("productName");//商品名称
	var skuPrice =$(obj).parent().parent().data("skuPrice");//商品价格
	var maxFucoin =$(obj).parent().parent().data("maxFucoin");//商品内购券
	var stock =$(obj).parent().parent().data("stock");//商品库存
	var spId =$("#specificationsId").val();//规格id
	var itemValues =$("#itemValues").val();//规格值
	var nums =parseInt($("#number").val());//数量
	var keyName = "cartItem" + spId;
	var productKey = "pro"+productId;
	var cartItem = { "pid": productId, "pName": productName, "spId": spId ,"itemValues":itemValues,"num":nums,"skuPrice":skuPrice,"maxFucoin":maxFucoin,"stock":stock};
    var cartItemStr = JSON.stringify(cartItem);
    var goods = localStorage.getItem(keyName);//取回cartItemStr
    goods = JSON.parse(goods);//把字符串转换成JSON对象
    if(goods!=null && goods!="undefined"){//如果不为空，则判断购物车中包含了当前购买的商品
    	//覆盖
   	 	var productNum = localStorage.getItem(productKey);
   	 	if(productNum!=null && productNum!="undefined"){//购物车商品对应数量
   	 		productNum=parseInt(productNum)-parseInt(goods.num)+parseInt(nums);
   	 		localStorage.setItem(productKey,productNum);
   	 	}
   	 	localStorage.setItem(keyName,cartItemStr);
    }else{//新增
   	 	localStorage.setItem(keyName,cartItemStr);
   	 	var productNum = localStorage.getItem(productKey);
   	 	if(productNum!=null && productNum!="undefined"){//购物车商品对应数量
   	 		productNum=parseInt(productNum)+parseInt(nums);
   	 		localStorage.setItem(productKey,productNum);
   	 	}else{
   	 		localStorage.setItem(productKey,nums);
   	 	}
    }
     setSelectNum();
     thickclose();
}

function setSelectNum(){
	 var totalSelectNum=0;
     for (var i = 0; i < localStorage.length; i++) {
         if (localStorage.key(i).indexOf("pro")>-1) {
        	 var productId = localStorage.key(i).split("pro")[1];
        	 totalSelectNum+= parseInt(localStorage.getItem(localStorage.key(i)));
        	 $("#selectNum_"+productId).html(localStorage.getItem("pro"+productId));
         }
     }
     $("#totalSelectNum").html(totalSelectNum);
     if(totalSelectNum >0){
    	 $("#totalSelectCart").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/can_buy_icon4.png) no-repeat","background-size":"50px"});
    	 $("#totalSelectCart").attr("href","javascript:open();");
     }else{
    	 $("#totalSelectCart").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/can_buy_icon3.png) no-repeat","background-size":"50px"});
    	 $("#totalSelectCart").attr("href","javascript:void(0);");
     }
    	
     refreshCash();
}
/**
 * 刷新金额
 * @returns
 */
function refreshCash(){
	var totalPrice=0;
	var totalCompanyTicket=0;
	 for (var i = 0; i < localStorage.length; i++) {
		 if (localStorage.key(i).indexOf("cartItem")>-1) {
			 var cartItem =  localStorage.getItem(localStorage.key(i));
			 cartItem = JSON.parse(cartItem);
			 if(cartItem != null){
				 totalPrice += parseFloat(cartItem.skuPrice)*parseFloat(cartItem.num);
				 totalCompanyTicket += parseFloat(cartItem.maxFucoin)*parseFloat(cartItem.num);
			 }
		 }
	 }
	 $("#prices").html(parseFloat(totalPrice).toFixed(2));
	 $("#maxFucoins").html(parseFloat(totalCompanyTicket).toFixed(2));
}
function open(){
	if(localStorage.length>0){
		var html = '';
		 for (var i = 0; i < localStorage.length; i++) {
	         if (localStorage.key(i).indexOf("cartItem")>-1) {
	        	 var cartItem =  localStorage.getItem(localStorage.key(i));
	        	 cartItem = JSON.parse(cartItem);
	        	html+='<li>';
	     		html+='<dl>	';	           			          
	     		html+='<dd class="dd1">';
	     		html+='<a href="javascript:void(0);">'+cartItem.pName+'</a>   ';                   
	     		html+='</dd>';
	     		html+='<dd class="dd2">'+cartItem.itemValues+'</dd>	';
	     		html+='<dd class="dd4">￥<span>'+parseFloat(cartItem.skuPrice).toFixed(2)+'</span><i>+'+parseFloat(cartItem.maxFucoin).toFixed(2)+'券</i></dd>';
	     		html+='</dl>';
	     		html+='<div class="quantity-wrapper" data-spid="'+cartItem.spId+'" data-pid="'+cartItem.pid+'" data-stock="'+cartItem.stock+'">';
	     		html+='<input class="quantity-decrease" name="" type="button" value="-" onclick="reduce(this)"/>';                        
	     		html+='<input type="text" readonly class="quantity" size="4" value="'+cartItem.num+'" id="numbers" >';
	     		html+='<input class="quantity-increase" name="" type="button" value="+" onclick="add(this)"/>';                     
	     		html+='</div>';
	     		html+='</li>';
	         }
	     }
		$(".thickbox2 ul").html(html);
		$(".thickdiv").show();
		$(".thickbox2").show();
		$(".bottom_box").addClass('bottom_box1');
	}else{
		thickclose();
	}
	
}
/**
 * 减少
 */
function reduce(obj){
	var number = $(obj).next().val();
	var spid = $(obj).parent().data("spid");
	var pid = $(obj).parent().data("pid");
	number = parseInt(number)-1;//减少
	var pnum =  localStorage.getItem("pro"+pid);
	if(pnum!=null){
		localStorage.setItem("pro"+pid,parseInt(pnum)-1);
		setSelectNum();
	}else{
		return;
	}
	$(obj).next().val(number);
	if(number>0){
		if(spid!=null && pid!=null && pid!='' && typeof(pid)!="undefined"){
			$.ajax({
			    url:jsBasePath + "group/getProductSkuNumberAndPrice.user?uid="+uid+"&groupId="+groupId+"&productId="+pid+"&skuId="+spid+"&quantity="+number,
			    type : "POST",
				dataType: "json",  //返回json格式的数据
				cache:false,
			    async: false,
			    success:function(data){
			    	if(data.success) {
			    		var result = data.data;
			    		$(obj).parent().prev().find('.dd4').html('￥<span>'+parseFloat(result.internalPurchasePrice).toFixed(2)+'</span><i>+'+parseFloat(result.maxFucoin).toFixed(2)+'券</i></dd>');
			    		var cartItem =localStorage.getItem("cartItem"+spid);
			    		cartItem = JSON.parse(cartItem);
			    		cartItem.num=number;//修改数量
			    		cartItem.skuPrice=result.internalPurchasePrice;//修改价格
			    		cartItem.maxFucoin=result.maxFucoin;//修改价格
			    		localStorage.setItem("cartItem"+spid,JSON.stringify(cartItem));//重新设定
			    		refreshCash();
			    	}
			    },
			 	error:function(){
				}
			});
		}
	}else{
		localStorage.removeItem("cartItem"+spid);
		var pnum =  localStorage.getItem("pro"+pid);
		if(pnum!=null && pnum==0){
			localStorage.removeItem("pro"+pid);
			setSelectNum();
		}
		open();
		refreshCash();
	}
}
/**
 * 增加操作
 */
function add(obj){
	var number = $(obj).prev().val();
	var spid = $(obj).parent().data("spid");
	var pid = $(obj).parent().data("pid");
	var stock = $(obj).parent().data("stock");
	number = parseInt(number)+1;//增加
	if(stock!=null){
		if(stock<number) return showInfoBox("库存不足");
	}else{
		return;
	}
	var pnum =  localStorage.getItem("pro"+pid);
	if(pnum!=null){
		localStorage.setItem("pro"+pid,parseInt(pnum)+1);
		setSelectNum();
	}else{
		return;
	}
	$(obj).prev().val(number);
	if (spid != null && pid != null && pid != '' && typeof (pid) != "undefined") {
		$.ajax({
			url : jsBasePath + "group/getProductSkuNumberAndPrice.user?uid="
					+ uid + "&groupId=" + groupId + "&productId=" + pid
					+ "&skuId=" + spid + "&quantity=" + number,
			type : "POST",
			dataType : "json", // 返回json格式的数据
			cache : false,
			async : false,
			success : function(data) {
				if (data.success) {
					var result = data.data;
					$(obj).parent().prev().find('.dd4').html(
							'￥<span>'
									+ parseFloat(result.internalPurchasePrice)
											.toFixed(2) + '</span><i>+'
									+ parseFloat(result.maxFucoin).toFixed(2)
									+ '券</i></dd>');
					var cartItem = localStorage.getItem("cartItem" + spid);
					cartItem = JSON.parse(cartItem);
					cartItem.num = number;// 修改数量
					cartItem.skuPrice = result.internalPurchasePrice;// 修改价格
					cartItem.maxFucoin = result.maxFucoin;// 修改价格
					localStorage.setItem("cartItem" + spid, JSON
							.stringify(cartItem));// 重新设定
					refreshCash();
				}
			},
			error : function() {
			}
		});
	}
}
function submitOrder(){
	if(localStorage.length>0){
		var specificationIdAndQuantity="";
		var partNumbers="";
		for (var i = 0; i < localStorage.length; i++) {
	         if (localStorage.key(i).indexOf("cartItem")>-1) {
	        	 var cartItem =  localStorage.getItem(localStorage.key(i));
	        	 if(cartItem!=null){
	        		 cartItem = JSON.parse(cartItem);
		        	 specificationIdAndQuantity+=cartItem.spId+"_"+cartItem.num+","; 
		        	 partNumbers += "0_"+cartItem.spId+",";
	        	 }
	         }
		}
		if(specificationIdAndQuantity!="" && partNumbers!=""){
			$.ajax({
				url : jsBasePath + "cart/adds?uid="
						+ uid + "&specificationIdAndQuantity=" + specificationIdAndQuantity+"&pageKey=groupBuy&fromType=weixin",
				type : "POST",
				dataType : "json", // 返回json格式的数据
				cache : false,
				async : false,
				success : function(data) {
					if (data.success) {
						localStorage.clear();//全部清除数据
						sessionBefore2Order();
						window.location = jsBasePath+ 'groupOrder/confirmCartPage.user?uid=' + uid+ '&partNumbers=' + partNumbers+"&groupId="+groupId;
					}
				},
				error : function() {
				}
			});
		}
	}else{
		return showInfoBox("购物车是空的，先添加商品吧")
	}
}