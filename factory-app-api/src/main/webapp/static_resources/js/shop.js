(function(w){
  $(w).load(function(){
	  init();
  });
})(window)

var uid=GetUidCookie();
var isload = true;
// JavaScript Document
$(function(){
	$("#pageNum").val("0");
	$(function(){
		$(window).scroll(function(){
			Load();
		});
	});
	function Load(){
		if(isload){//ajax在后台获取数据时，设值其false，防止页面多次加载
			var loadHeight = 115;//指定滚动条距离底部还有多少距离时进行数据加载
			var documentHeight = parseInt($(document).height(),10);//可视区域当前高度
			var windowHeight = parseInt(window.innerHeight,10);//窗口当前高度
			var scrollHight = parseInt($(window).scrollTop(),10);//窗口滚动条位置
			if(documentHeight - scrollHight - windowHeight < loadHeight){
				//ajax获取数据，以下为模拟
				isload = false;
				
				init();
			}
		}
	}
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    // 通过下面这个API隐藏右上角按钮
   	 WeixinJSBridge.call('hideOptionMenu');
	});
	//ajaxGetGroupBuyUser(shopId);
	$(".shop_xq dl dt").click(function(){//点击店铺logo
		window.location = jsBasePath+'shop_details.html?shopId='+shopId;
	})
	$(".search_inp").click(function(e){
		var urla = "";
		try{
			if(jsBasePath) {
				urla = jsBasePath;
			}
		} catch(e){}
		window.location = urla+'search_box.html?shop='+shopId;
	});
});
function back() {
	history.back();
}
var userSupplierId=getSupplierIdCookie();
function init(){
	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	
	$.ajax({
		url : jsBasePath + 'shop/shopIndex?shopId=' + shopId+ '&page=' + page,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				var result = data.data;
				var result2 = result.result;
				var cost = data.msg;
				var html='';
				var shop = result.shopDetail;
				var supplier = result.supplier;
				if (userSupplierId !='' && userSupplierId==supplier.id) {
					$(".shop_xq .dd1").html("自家	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				}else{
				var shopName;
				if (shop.shopname.length>12) {
					shopName=shop.shopname.substring(0,11)+"...";
				}else{
					shopName=shop.shopname;
				}
				$(".shop_xq .dd1").html(shopName);
				}
				//粉丝数
				var collectionShopCount=data.data.collectionShopCount;
				if (shop.banner!=undefined) {//品牌背景图
					$(".shop_banner img").attr("src",shop.banner);
				}
				if (shop.logo!=""||typeof(shop.logo)!=undefined) {//logo
					$(".shop_xq dt img").attr("src",shop.logo);
				}
				$(".shop_xq .dd2 span").html(collectionShopCount);//粉丝数
				$(".shoptop_tab .li2 span").html(result2.totalNum)//全部商品数量
				//top
				/*html +='<li>';
				if (shop.banner!=undefined) {
					html +='<div class="shop_banner"><img src="'+shop.banner+'" class="alpha"/>';
				}else{
					html +='<div class="shop_banner"><img class="alpha" src="'+jsBasePath+'static_resources/images/shop_home_top_img_default.png" />';
				}
				//html +='<div class="shop_banner"><img src="'+shop.banner+'" /></div>';
				html +='<div class="shop_xq">';
				html +='<dl>';
				if (shop.logo==''||typeof(shop.logo)==undefined) {
					html +='<dt><img src="'+jsBasePath+'static_resources/images/shop_home_default_logo.png" /></dt>';
				}else{
					html +='<dt><img src="'+shop.logo+'" /></dt>';
				}
				html +='<dd class="dd1">'+shop.shopname+'</dd>';
				html +='<dd class="dd2"><img src="'+jsBasePath+'static_resources/images/shop_home_fensi.png"><span>'+collectionShopCount+'</span></dd>';
				html +='</dl>';
				html +='<div class="sc_btn" ><a href="javascript:toggleCollectShop();">收藏</a></div>';
				html +='</div>';
				html +='</div>';
				$(".shops_top").html(html);*/

				//商品
				drwaProduct(result2.hits,page,cost);
				//判断收藏
				ajaxGetCollectShop(shopId);
			} else {
				showInfoBox(data.msg);
			}
		},
		error : function() {}
	});
}

function drwaProduct(list,page,cost) {
	if(list.length>0) {
		$("#pageNum").val(page);
		var specificationsId='';
		var html='';
		for(var i=0;i<list.length;i++) {
			if(list[i].minSkuId && typeof(list[i].minSkuId)!="undefined"){
				specificationsId = list[i].minSkuId;
			}
			html +='<li>';
			html +='<dl>';
			html +='<dt><a href="'+jsBasePath+'productm?productId='+list[i].id+'&specificationsId='+specificationsId+'&from=a&pageKey=shop"><img src="'+list[i].image+'" /></a>'
			if(list[i].saleKbn==1) {
				html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon2.png" /></div>';
			} else if(list[i].saleKbn==2) {
				html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_c2.png" /></div>';
			}else if(list[i].saleKbn==4) {
				html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_z2.png" /></div>';
			} else if(list[i].saleKbn==5) {
				html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_t2.png" /></div>';
			}
			html +='</dt>';
			html +='<dd class="dd1"><a href="'+jsBasePath+'productm?productId='+list[i].productId+'&specificationsId='+specificationsId+'&from=a">'+list[i].name+'</a></dd>';
			if (list[i].maxFucoin>cost) {
				list[i].maxFucoin=cost;
			}
			html +='<dd><span>￥'+parseFloat(list[i].salePrice).toFixed(2)+'+'+parseFloat(list[i].maxFucoin).toFixed(2)+'券</span></dd>';
			html +='<dd class="dd3" style="margin-top:0px;"><span>电商价：￥'+parseFloat(list[i].price).toFixed(2)+'券</span></dd>';
			html +='</dl>';
			html +='</li>';
		}
		$("#shops_li").append(html);
		
	} else {
		$("#pageNum").val(-1);
	}
}
function ajaxSearch(){

	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	
	var url = jsBasePath+'shop/shopProduct?shopId=' + shopId;

	$.ajax({
		url : url + '&page=' + page,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			drwaProduct(data.data.page.list,page);
			
			isload = true;
		},
		error : function() {}
	});
}

function removeShop(){
	if(uid=="") return;
	$.ajax({
		url : jsBasePath +'collectShop/delete.user?uid='+uid+'&shopIdList='+shopId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				window.location.href=window.location.href;
			}
		},
		error : function() {}
	});
}
function increaseShop(){
	if(uid=="") return;
	$.ajax({
		url : jsBasePath +'collectShop/add.user?uid='+uid+'&shopId='+shopId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				window.location.href=window.location.href;
			}
		},
		error : function() {}
	});
}
function ajaxGetCollectShop(shopId){
	if(uid=="") return;
	
	$.ajax({
		url : jsBasePath +'collectShop/check.user?uid='+uid+'&shopId='+shopId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success && data.data) {
				$("#delShop").show();//已收藏
				$("#addShop").hide();//未收藏默认
			}
		},
		error : function() {}
	});
}
function ajaxSearchShop(){
	window.location = jsBasePath +'pSearch/page?fromPageKey=shop&params=' + encodeURI("shop="+shopId);
}
function ajaxGetGroupBuyUser(shopId){
	$.ajax({
		url : jsBasePath +'group/getApplyByShop.user?uid='+uid+'&shopId='+shopId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success && data.data>0) {
				$(".TogetherToBuy_link i").after("您在该店铺有个"+data.data+"一起购邀请,购买任意商品参团哦！");
				$(".TogetherToBuy_link").show();
			}
		},
		error : function() {}
	});
}