var isload = true;
// JavaScript Document
$(function(){
    //登录检查
	if(typeof(isPreview)=="undefined" || !isPreview) {
	    if(isWeiXin()) {
	    	loginCheck(0);
	    } else {
			var url = window.location.href;
			if(url.indexOf("app=1") > -1) {
				$("a").attr("href","#");
			} else {
		    	alert("很抱歉，手机版目前仅支持微信中访问，你可以尝试使用电脑版。");
		    	window.location = "http://www.wd-w.com";
			}
	    }
	}

	$(".search_box").click(function(e){//点击设置
		go2Search("search_key=");
	});
	
    //bannner
    $('.flexslider').flexslider({
	      animation: "slide",
	      directionNav: false,
	      start:function(slider){
	        $('body').removeClass('loading');
	      }
	    });
    
    //ajaxpageDataIndex();
	  
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
				
				ajaxpageDataIndex();
			}
		}
	}
	if (typeof WeixinJSBridge == "object" && typeof WeixinJSBridge.invoke == "function") {
		handleFontSize();
		} else {
			if (document.addEventListener) {
			document.addEventListener("WeixinJSBridgeReady", handleFontSize, false);
			} else if (document.attachEvent) {
			document.attachEvent("WeixinJSBridgeReady", handleFontSize);
			document.attachEvent("onWeixinJSBridgeReady", handleFontSize);
			}
		}
	 function handleFontSize() {
		// 设置网页字体为默认大小
		WeixinJSBridge.invoke('setFontSizeCallback', {'fontSize': 0});
		// 重写设置网页字体大小的事件
		WeixinJSBridge.on('menu:setfont', function () {
			WeixinJSBridge.invoke('setFontSizeCallback', {'fontSize': 0});
		});
	 }
});

function changeBrandScope() {
	var brandScopeIndex = parseInt($("#brandScopeIndex").val(),10);
	var html="";
	//先画前16个
	for(var i=0;i<16;i++) {
		html +='<li><a href="javascript:go2Search(\''+brandJson[brandScopeIndex].link+'\')"><img src="'+brandJson[brandScopeIndex].img+'" /></a></li>';

		brandScopeIndex++;
		if(brandScopeIndex==brandJson.length) {
			brandScopeIndex=0;
		}
	}
	//再画4个
	for(var i=0;i<4;i++) {

		html +='<li class="border_none"><a href="javascript:go2Search(\''+brandJson[brandScopeIndex].link+'\')"><img src="'+brandJson[brandScopeIndex].img+'" /></a></li>';
		brandScopeIndex++;
		if(brandScopeIndex==brandJson.length) {
			brandScopeIndex=0;
		}
	}
	
	$("#brand_scope .main_one_con ul").html(html);
	$("#brandScopeIndex").val(brandScopeIndex);
}
function ajaxpageDataIndex(){
	var page = parseInt($("#pageNum").val()) + 1;
	var specificationsId='';
	if(page<1) return;
	$.ajax({
		url : 'recommendProduct?type=1&pageSize=20&page=' + page,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var result = data.data;
			var cost = data.msg;
			if(result==null || typeof(result) == "undefined") {
				$("#pageNum").val(-1);
				$(".youlike_box p").hide();
			} else {
				$("#pageNum").val(page);
				var html='';
				var rprice = 0;
				for(var i=0;i<result.length;i++) {
					if(result[i].salePrice && result[i].salePrice!=null){
						rprice = result[i].salePrice;
					}else{
						rprice = result[i].price-result[i].companyTicket;
					}
					if (result[i].companyTicket>cost) {
						result[i].companyTicket=cost;
					}
					if(result[i].skuId && typeof(result[i].skuId)!="undefined"){
						specificationsId = result[i].skuId;
					}
					html +='<li>';
					html +='<dl>';
					html +='<dt><a href="javascript:go2Search(\''+result[i].productId+'&specificationsId='+specificationsId+'&pageKey=index'+'\')"><img src="'+result[i].image+'" /></a>';
					if(result[i].saleKbn==1) {
						html +='<div class="picon"><img src="static_resources/images/picon2.png" /></div>';
					} else if(result[i].saleKbn==2) {
						html +='<div class="picon"><img src="static_resources/images/picon_c2.png" /></div>';
					} else if(result[i].saleKbn==4) {
						html +='<div class="picon"><img src="static_resources/images/picon_z2.png" /></div>';
					}else if(result[i].saleKbn==5) {
						html +='<div class="picon"><img src="static_resources/images/picon_t2.png" /></div>';
					}
					html +='</dt>';
					html +='<dd class="dd1"><a href="javascript:go2Search(\''+result[i].productId+'&specificationsId='+specificationsId+'&pageKey=index'+'\')">'+result[i].name+'</a></dd>';
					if(result[i].companyTicket>0){
						html +='<dd><span>￥'+(rprice.toFixed(2))+'+'+parseFloat(result[i].companyTicket)+'券</span></dd>';
					}else{
						html +='<dd><span>￥'+rprice.toFixed(2)+'</span></dd>';
					}
					html +='<dd class="dd3"><span>电商价 :￥'+result[i].price.toFixed(2)+'</span></dd>';
					html +='</dl>';
					html +='</li>';
				}

				$("#like_li").append(html);
				var url = window.location.href;
				if(url.indexOf("app=1") > -1) {
					$("a").attr("href","#");
				}
			}
			
			isload = true;
		},
		error : function() {}
	});
}
