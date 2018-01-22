var uid=GetUidCookie();
var flag = false;
$(function(){
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
	queryGroupProdyct();

})
/**
 * 获取可购商品
 */
function queryGroupProdyct(){
	$.ajax({
		url : jsBasePath +'group/getShopProducts.user?uid='+uid+'&shopId='+shopId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				var result = data.data.hits;
				var productIds = sessionStorage.getItem("chooseProductIds");//选中的商品id
				var html = ''; 
				html+='<div class="order_box">';
				html+='<div class="order_top"><p>'+result[0].shopName+'</p></div>';
				for (var i = 0; i < result.length; i++) {
					html+=' <ul>';
					html+='<li>';
					if(typeof(productIds)!=undefined && productIds!=null && productIds!='' && productIds.indexOf(result[i].productId)!=-1){
						html+='<em id="checkEm'+i+'" onclick="toggleSel(this,\'item\');" style="background:url('+jsBasePath+'static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat;background-size:15px 15px"> <input type="hidden" name="sel_item" value="1"/> <input type="hidden" name="productId" value="'+result[i].productId+'"/></em>';
					}else{
						html+='<em id="checkEm'+i+'" onclick="toggleSel(this,\'item\');"> <input type="hidden" name="sel_item"/> <input type="hidden" name="productId" value="'+result[i].productId+'"/></em>';
					}
					html+='<dl>';
					html+='<dt>';
					if(typeof(result[i].priceEx)!='undefined'){
						html+='<span class="jietijia">阶梯价</span>';
					}
					html+='<a href="javascript:toProductDetil('+result[i].productId+','+result[i].minSkuId+')"><img src="'+result[i].image+'" /></a><input type="hidden" name="chooseimages" value="'+result[i].image+'">';
					html+='</dt>';
					html+='<dd class="dd1">';
					html+='<a href="javascript:void(0);">'+result[i].name+'</a>';                       	
					html+='</dd>';
					html+='<dd class="dd3">电商价:￥'+result[i].price+'</dd>';
					html+='<dd class="dd4">内购价:￥<span>'+parseFloat(result[i].salePrice).toFixed(2)+'</span><i>+内购券'+parseFloat(result[i].maxFucoin).toFixed(2)+'</i></dd>';
					html+='</dl>';
					html+='</li>';
					html+='</ul>'; 
				}
				html+='</div> ';
				$(".main-box").html(html);
				if(typeof(productIds)==undefined || productIds==null || productIds==''){
					$(".order_box em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
					$(".all_btn em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
					$(".order_box input[name='sel_item']").val("1");
				}
				checkAllSel();
				refreshCash();
			}
			
		},
		error : function() {}
	});
}
/**
 * 进入可选商品详情
 * @param productId
 * @param skuId
 * @returns
 */
function toProductDetil (productId,skuId){
	location.href=jsBasePath+"group/detail.user?uid="+uid+"&productId="+productId+"&skuId="+skuId;
}
function toggleSel(obj,type) {
	if('item' == type) {
		var sel=$(obj).children("input[name='sel_item']");
		if($(sel).val() == 1) {
			$(sel).val("0");
			$(obj).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
			checkAllSel();
		} else {
			$(sel).val("1");
			$(obj).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
			checkAllSel();
			
		}
	}
	refreshCash();
}
function checkAllSel(){
	var $listSelitem  = $('input[name="sel_item"]');
	var allSel = true;
	for(var i=0;i<$listSelitem.length;i++) {
		if($($listSelitem[i]).val()==0){
			allSel = false;
			break;
		}
	}
	if(allSel){
		$("#sel_all").val("0");
		selAll();
	}else{
		$("#sel_all").val("0");
		$(".all_btn em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
	}
	
}
function selAll() {
	if($("#sel_all").val()==1) {
		//取消勾选
		$("#sel_all").val("0");
	} else {
		//选中
		$("#sel_all").val("1");
	}
	
	if($("#sel_all").val()==1) {
		//选中
		$(".all_btn em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
		//$(".order_box input[name='sel_top']").val("1");
		$(".order_box input[name='sel_item']").val("1");
		$(".order_box em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan1_icon.png) no-repeat","background-size":"15px 15px"});
	} else {
		//取消
		$(".all_btn em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
		//$(".order_box input[name='sel_top']").val("0");
		$(".order_box input[name='sel_item']").val("0");
		$(".order_box em").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yuan_icon.png) no-repeat","background-size":"15px 15px"});
	}
	
	refreshCash();
}
function refreshCash(){
	var $listSel  = $('input[name="sel_item"]');
	var $productIds  = $('input[name="productId"]');
	var partNumbers = "";
	var num = 0;
	for(var i=0;i<$listSel.length;i++) {
		if($($listSel[i]).val()=="1") {
			partNumbers += $($productIds[i]).val()+",";
			num++;
		}
	}
	sessionStorage.setItem("chooseProductIds", partNumbers);//将选中商品id放入session
	$(".rt_con").html("已选"+num+"件商品");
}
/**
 * 提交
 */
function confirmOrder(){

	var $listSel  = $('input[name="sel_item"]');
	var $productIds  = $('input[name="productId"]');
	var $chooseimages  = $('input[name="chooseimages"]');
	var ckchooseimages = "";
	var partNumbers = "";
	for(var i=0;i<$listSel.length;i++) {//获取选中的商品id和图片路径，用,拼接
		if($($listSel[i]).val()=="1") {
			partNumbers += $($productIds[i]).val()+",";
			ckchooseimages += $($chooseimages[i]).val()+",";
		}
	}
	if(ckchooseimages=="" || partNumbers=="") return showInfoBox("请至少选择一个商品");
	sessionStorage.setItem("chooseimages", ckchooseimages);//将选中商品图片路径放入session
	sessionStorage.setItem("chooseProductIds", partNumbers);//将选中商品id放入session
	history.back();
	//window.location=jsBasePath +'group/page.user?uid='+uid+'&shopId='+shopId+'&productIds='+partNumbers;
}