var isload = true;
// JavaScript Document
var uid=GetUidCookie();
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

	$(".search_inp").click(function(e){//点击设置
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
				
				ajaxMoreIndexData();
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
	 ajaxpageDataIndex();
	 
	//顶部我的券点击显示
	 $('.my_quan span').toggle(function(){
	 	  $(".quan_con").show();
	 },function(){
	 	 $(".quan_con").hide();
	 }); 
	 mySwiper();
});
var cash =0;
var exchange = 0;
var maxcost=800;//福利系数
function ajaxpageDataIndex(){//首页
	if(uid=='') return;
	$.ajax({
		url : 'pageData/newIndex?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				var result = data.data;
				var Arrays ='';
				maxcost = data.msg;
				if(result && result.length>0){
					var CashArea ='';//现金专区
					var ExchangeArea='';//换领专区
					var AnswerArea='';//答题试用专区
					var SaveMoreArea='';//特省专区
					var isCashArea=false;//现金专区是否存在
					var isExchangeArea=false;//换领专区是否存在
					var isAnswerArea=false;//试用专区是否存在
					var isSaveMoreArea=false;//特省专区是否存在
					for (var i = 0; i < result.length; i++) {
						if(result[i].name=='user_ticket_have'){
							var userInfo = result[i].pageDataList[0];
							if(userInfo){
								if(userInfo.ex1Value && userInfo.ex1Value!=''){
									cash = userInfo.ex1Value;
								}
								if(userInfo.ex2Value && userInfo.ex2Value!=''){
									exchange = userInfo.ex2Value;
								}
								$(".quan_con ul").html('<li>现金券：'+userInfo.ex1Value+'</li><li>换领币：'+userInfo.ex2Value+'</li><li>内购券：'+userInfo.ex3Value+'</li>');
							}
							if(userInfo.ex4Value && userInfo.ex4Value!=''){
								$(".top_shopName").html('【'+userInfo.ex4Value+'】员工福利店')
							}else{
								$(".top_shopName").hide();
							}
							if(userInfo.ex5Value && userInfo.ex5Value!=''){
								$("#shop").val(userInfo.ex5Value);
							}
						}else if("Ownexclusive"==result[i].name){
							var Ownexclusive = result[i].pageDataList;//自家专享
							var pageHtml='';
							if(Ownexclusive && Ownexclusive.length>0){
								for (var j = 0; j < Ownexclusive.length; j++) {
									pageHtml+='<div class="swiper-slide"><a href="javascript:go2Search(\''+Ownexclusive[j].link+'&specificationsId='+Ownexclusive[j].minSkuId+'\');"><img src="'+Ownexclusive[j].imagePath+'" /></a></div>';
								}
								pageHtml+='<div class="swiper-slide last_div"><a href="javascript:go2Shop();"><img src="static_resources/images/parker_08.png" /></a></div>'
								$("#Ownexclusive .swiper-wrapper").html(pageHtml);
								 mySwiper();
								$("#Ownexclusive").show();
							}else{
								$("#Ownexclusive").hide();
							}
						}else if("CashArea"==result[i].name){//现金专区
							CashArea = result[i].pageDataList;//现金专区
							var u1='';
							var u2='';
							var ad=''
							if(CashArea && (CashArea.length==11 ||CashArea.length==7 || CashArea.length==9)){
								isCashArea=true;
								Arrays = dealData(CashArea,u1,u2,ad,"CashArea","left");
								u1=Arrays[0];
								u2=Arrays[1];
								ad=Arrays[2];
								$("#CashArea .ul1").html(u1);
								$("#CashArea .ul2").html(u2);
								$("#CashArea .ad").html(ad);
								$("#CashArea").show();
							}else{
								$("#CashArea").hide();
							}
						}else if("AnswerArea"==result[i].name){
							AnswerArea = result[i].pageDataList;//现金专区
							var u1='';
							var u2='';
							var ad=''
							if(AnswerArea && (AnswerArea.length==11||AnswerArea.length==7 || AnswerArea.length==9)){
								isAnswerArea =true;
								if(isCashArea){
									Arrays = dealData(AnswerArea,u1,u2,ad,"AnswerArea","right");
								}else{
									Arrays = dealData(AnswerArea,u1,u2,ad,"AnswerArea","left");
								}
								u1=Arrays[0];
								u2=Arrays[1];
								ad=Arrays[2];
								$("#AnswerArea .ul1").html(u1);
								$("#AnswerArea .ul2").html(u2);
								$("#AnswerArea .ad").html(ad);
							}else{
								$("#AnswerArea").hide();
							}
						}else if("ExchangeArea"==result[i].name){
							ExchangeArea = result[i].pageDataList;//现金专区
							var u1='';
							var u2='';
							var ad=''
							if(ExchangeArea && (ExchangeArea.length==11||ExchangeArea.length==7 || ExchangeArea.length==9)){
								isExchangeArea=true;
								if(isCashArea){
									if(isAnswerArea){
										Arrays = dealData(ExchangeArea,u1,u2,ad,"exchange","left");
									}else{
										Arrays = dealData(ExchangeArea,u1,u2,ad,"exchange","right");
									}
								}else{
									if(isAnswerArea){
										Arrays = dealData(ExchangeArea,u1,u2,ad,"exchange","right");
									}else{
										Arrays = dealData(ExchangeArea,u1,u2,ad,"exchange","left");
									}
								}
								u1=Arrays[0];
								u2=Arrays[1];
								ad=Arrays[2];
								$("#ExchangeArea .ul1").html(u1);
								$("#ExchangeArea .ul2").html(u2);
								$("#ExchangeArea .ad").html(ad);
								$("#ExchangeArea").show();
							}else{
								$("#ExchangeArea").hide();
							}
						}else if("SaveMoreArea"==result[i].name){
							SaveMoreArea = result[i].pageDataList;//现金专区
							var u1='';
							var u2='';
							var ad=''
							if(SaveMoreArea && (SaveMoreArea.length==11||SaveMoreArea.length==7 || SaveMoreArea.length==9)){
								isSaveMoreArea =true;
								if(isCashArea){
									if(isAnswerArea){
										if(isExchangeArea){
											Arrays = dealData(SaveMoreArea,u1,u2,ad,"SaveMoreArea","right");
										}else{
											Arrays = dealData(SaveMoreArea,u1,u2,ad,"SaveMoreArea","left");
										}
									}else{
										Arrays = dealData(SaveMoreArea,u1,u2,ad,"SaveMoreArea","right");
									}
								}else{
									Arrays = dealData(SaveMoreArea,u1,u2,ad,"SaveMoreArea","left");
								}
								u1=Arrays[0];
								u2=Arrays[1];
								ad=Arrays[2];
								$("#SaveMoreArea .ul1").html(u1);
								$("#SaveMoreArea .ul2").html(u2);
								$("#SaveMoreArea .ad").html(ad);
								$("#SaveMoreArea").show();
							}else{
								$("#SaveMoreArea").hide();
							}
						}
					}
					$('.ngj_con ul li dl .div_pop1').on("toggle",test());
					
					animate();
				}
			}else{
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
		},
		error : function() {}
	});
}
function dealData(data,u1,u2,ad,dataName,positionFlag){
	var myArr=new Array();
	var footer_more='';//底部搜索图片
	var header_title='';//头部图片
	var allStock = 10;
	var stock = 3;
	for (var i = 0; i < data.length; i++) {
		if (data[i].title && data[i].title != '') {
			if (data[i].title == 'header_title') {
				header_title=data[i];
			} else if (data[i].title == 'footer_more') {
				footer_more = data[i];
			} else if ('big_image' == data[i].title) {
				if(data[i].link && data[i].link!=''){
					ad += '<a href="javascript:go2Search(\''+ data[i].link+'\');"><img src="'
					+ data[i].imagePath + '" /></a>';
				}else{
					ad += '<a href="javascript:void(0);"><img src="'
						+ data[i].imagePath + '" /></a>';
				}
			}
		}
		if (data[i].ex6Value && data[i].ex6Value != '') {
			if (parseFloat(data[i].maxFucoin) > parseFloat(maxcost)) {
				data[i].maxFucoin=parseFloat(maxcost).toFixed(2);
			}
			if (data[i].ex6Value == '0' || data[i].ex6Value == '2' || data[i].ex6Value == '4' || data[i].ex6Value == '6') {
				u1 += '<li>';
				u1 += '<dl>';
				u1 += '<dt><a href="javascript:go2Search(\'' + data[i].link
						+ '&specificationsId=' + data[i].minSkuId+'&pageKey=index'
						+ '\');"><img src="' + data[i].imagePath
						+ '" /></dt></a>';
				u1 += '<div class="div_pop1">';
				u1 += '<dd class="dd1">' + data[i].proName + '</dd>';
				u1 += '<dd class="dd2">电商价:￥'
						+ parseFloat(data[i].marketPrice).toFixed(2)
						+ '</dd>';
				var showPrice = parseFloat(data[i].proPrice);
				var salePrice = parseFloat(data[i].proPrice).toFixed(2);
				var ex=0;
				var ca=0;
				var needPay = parseFloat(data[i].proPrice);
				if(dataName=='exchange'){
					if(needPay >= exchange) {
						ex = exchange;
					} else {
						ex = needPay;
					}
					needPay=needPay-ex;
						
					if(needPay >= cash) {
						ca = cash;
					} else {
						ca = needPay;
					}
					needPay=needPay-ca;
				}
				showPrice=showPrice-parseFloat(cash);
				var strPrice = "0";
				if(showPrice>0) {
					strPrice=showPrice.toFixed(2);
					salePrice = parseFloat(cash).toFixed(2);
				}
				if(dataName=='exchange'){
					u1 += '<dd class="dd3"><span>内购价:￥'
						+ parseFloat(needPay).toFixed(2)
						+ '+</span>';
					u1+='<i  class="huanlingbi"><img src="static_resources/images/huanlingbi_icon.png"  width="15" height="15" /></i>'
				}else{
					u1 += '<dd class="dd3"><span>内购价:￥'
						+ parseFloat(strPrice).toFixed(2)
						+ '+</span>';
					u1+='<i><img src="static_resources/images/quanios@2x.png" /></i>';
				}
				u1 +='</dd>';
				u1 += '<em><img src="static_resources/images/right_bottom_icon.png" /></em>';
				if(dataName=='exchange'){
					u1 += '<dd class="dd4">+换领币:'
						+ parseFloat(ex).toFixed(2)
						+ '</dd>';
					u1 += '<dd class="dd4">+现金券:'
						+ parseFloat(ca).toFixed(2) + '</dd>';
				}else{
					u1 += '<dd class="dd4">+内购券:'
						+ parseFloat(data[i].maxFucoin).toFixed(2)
						+ '</dd>';
					u1 += '<dd class="dd4">+现金券:'
						+ parseFloat(salePrice).toFixed(2) + '</dd>';
				}
					
				u1 += '<dd class="dd6">';
				u1 += '<div class="pro_bar">';
					
				allStock=data[i].allStock;
				stock=data[i].stock;
					
				if(allStock<=0){
					allStock=10;
					stock=0;
				}
				if(allStock<stock || allStock>500) {
					allStock = 80;
					stock=35;
				}
				allStock=randomStock(stock,allStock);
				u1 += '<div class="bar_con" width="'+(stock/allStock*100).toFixed(2)+'" style="width:0px;"></div>';
				u1 += '</div>';
				u1 += '<p>仅剩' + stock + '件</p>';
				u1 += '</dd>';
				u1 += '</div>'
				u1 += '</dl>';
				u1 += '</li>';
			} else {
				u2 += '<li>';
				u2 += '<dl>';
				u2 += '<dt><a href="javascript:go2Search(\'' + data[i].link
						+ '&specificationsId=' + data[i].minSkuId+'&pageKey=index'
						+ '\');"><img src="' + data[i].imagePath
						+ '" /></dt></a>';
				u2 += '<div class="div_pop1">';
				u2 += '<dd class="dd1">' + data[i].proName + '</dd>';
				u2 += '<dd class="dd2">电商价:￥'
						+ parseFloat(data[i].marketPrice).toFixed(2)
						+ '</dd>';
				var showPrice = parseFloat(data[i].proPrice);
				var salePrice = parseFloat(data[i].proPrice).toFixed(2);
				var strPrice = "0";
				var ex=0;
				var ca=0;
				var needPay = parseFloat(data[i].proPrice);
				if(dataName=='exchange'){
					if(needPay >= exchange) {
						ex = exchange;
					} else {
						ex = needPay;
					}
					needPay=needPay-ex;
						
					if(needPay >= cash) {
						ca = cash;
					} else {
						ca = needPay;
					}
					needPay=needPay-ca;
				}
				showPrice=showPrice-parseFloat(cash);
				if(showPrice>0) {
					strPrice=showPrice.toFixed(2);
					salePrice = parseFloat(cash).toFixed(2);
				}
				if(dataName == 'exchange'){
					u2 += '<dd class="dd3"><span>内购价:￥'
						+ parseFloat(needPay).toFixed(2)
						+ '+</span>';
					u2+='<i  class="huanlingbi"><img src="static_resources/images/huanlingbi_icon.png"  width="15" height="15" /></i>'
				}else{
					u2 += '<dd class="dd3"><span>内购价:￥'
						+ parseFloat(strPrice).toFixed(2)
						+ '+</span>';
					u2+='<i><img src="static_resources/images/quanios@2x.png" /></i>';
				}
				u2 +='</dd>';
				u2 += '<em><img src="static_resources/images/right_bottom_icon.png" /></em>';
				if(dataName == 'exchange'){
					u2 += '<dd class="dd4">+换领币:'
						+ parseFloat(ex).toFixed(2)
						+ '</dd>';
						u2 += '<dd class="dd4">+现金券:'
						+ parseFloat(ca).toFixed(2) + '</dd>';
				}else{
					u2 += '<dd class="dd4">+内购券:'
						+ parseFloat(data[i].maxFucoin).toFixed(2)
						+ '</dd>';
					u2 += '<dd class="dd4">+现金券:'
						+ parseFloat(salePrice).toFixed(2) + '</dd>';
				}
					
				u2 += '<dd class="dd6">';
				u2 += '<div class="pro_bar">';
					
				allStock=data[i].allStock;
				stock=data[i].stock;
					
				if(allStock<=0){
					allStock=10;
					stock=0;
				}
				if(allStock<stock || allStock>500) {
					allStock = 80;
					stock=35;
				}
				allStock=randomStock(stock,allStock);
				u2 += '<div class="bar_con" width="'+(stock/allStock*100).toFixed(2)+'" style="width:0px;"></div>';
				u2 += '</div>';
				u2 += '<p>仅剩' + stock + '件</p>';
				u2 += '</dd>';
				u2 += '</div>'
				u2 += '</dl>';
				u2 += '</li>';
			}
		}
	}
	if(u1!='' && u2!=''){
		if(positionFlag =="left"){
			if(header_title){
				var head ='<li><img src="' + header_title.imagePath + '" /></li>';
				u1 = head.concat(u1);
			}
			if(footer_more.link && footer_more.link!=''){
				u2+='<li class="con_more_btn"><a href="javascript:go2Search(\''+footer_more.link+'\');"><img src="'
				+ footer_more.imagePath + '" /></a></li>';
			}else{
				u2 += '<li class="con_more_btn"><a href="javascript:void();"><img src="'
					+ footer_more.imagePath + '" /></a></li>';
			}
		}else if(positionFlag =="right"){
			if(header_title){
				var head ='<li><img src="' + header_title.imagePath + '" /></li>';
				u2 = head.concat(u2);
			}
			if(footer_more.link && footer_more.link!=''){
				u1+='<li class="con_more_btn"><a href="javascript:go2Search(\''+footer_more.link+'\');"><img src="'
				+ footer_more.imagePath + '" /></a></li>';
			}else{
				u1 += '<li class="con_more_btn"><a href="javascript:void();"><img src="'
					+ footer_more.imagePath + '" /></a></li>';
			}
		}
	}
	
	myArr[0]=u1;
	myArr[1]=u2;
	myArr[2]=ad;
	return myArr;
}
//满库存时的随机处理
function randomStock(stock,allStock) {
	if(stock<allStock || stock<=3) return allStock;
	
	var rand =10+ getRandomNum(3,10);
	
	return parseInt(stock*rand/10);
}
function getRandomNum(Min,Max)
{   
	var Range = Max - Min;   
	var Rand = Math.random();   
	return(Min + Math.round(Rand * Range));   
} 
function ajaxMoreIndexData(){
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
			var allStock = 10;
			var stock = 3;
			if(result==null || typeof(result) == "undefined") {
				$("#pageNum").val(-1);
				$("#more").hide();
			} else {
				$("#pageNum").val(page);
				var u1='';
				var rprice = 0;
				for(var i=0;i<result.length;i++) {
					if (result[i].companyTicket>cost) {
						result[i].companyTicket=cost;
					}
					if(result[i].skuId && typeof(result[i].skuId)!="undefined"){
						specificationsId = result[i].skuId;
					}
					var showPrice = parseFloat(result[i].salePrice);
					var salePrice = parseFloat(result[i].salePrice).toFixed(2);
					var strPrice = "0";
					showPrice=showPrice-parseFloat(cash);
					if(showPrice>0) {
						strPrice=showPrice.toFixed(2);
						salePrice = parseFloat(cash).toFixed(2);
					}
					allStock=result[i].allStock;
					stock=result[i].stock;
						
					if(allStock<=0){
						allStock=10;
						stock=0;
					}
					if(allStock<stock || allStock>500) {
						allStock = 80;
						stock=35;
					}
					allStock=randomStock(stock,allStock);
					u1 += '<li>';
					u1 += '<dl>';
					u1 += '<dt><a href="javascript:go2Search(\''+result[i].productId+'&specificationsId='+specificationsId+'&pageKey=index'+'\');"><img src="' + result[i].image+ '" /></dt></a>';
					u1 += '<div class="div_pop1">';
					u1 += '<dd class="dd1">' + result[i].name + '</dd>';
					u1 += '<dd class="dd2">电商价:￥'
							+ parseFloat(result[i].price).toFixed(2)
							+ '</dd>';
					
					u1 += '<dd class="dd3"><span>内购价:￥'
							+ parseFloat(strPrice).toFixed(2)
							+ '+</span>';
					u1+='<i><img src="static_resources/images/quanios@2x.png" /></i>';
					u1 +='</dd>';
					u1 += '<em><img src="static_resources/images/right_bottom_icon.png" /></em>';
					u1 += '<dd class="dd4">+内购券:'
							+ parseFloat(result[i].companyTicket).toFixed(2)
							+ '</dd>';
					u1 += '<dd class="dd4">+现金券:'
							+ parseFloat(salePrice).toFixed(2) + '</dd>';
					u1 += '<dd class="dd6">';
					u1 += '<div class="pro_bar">';
					u1 += '<div class="bar_con" width="'+(stock/allStock*100).toFixed(2)+'" style="width:'+(stock/allStock*100).toFixed(2)+'px;"></div>';
					u1 += '</div>';
					u1 += '<p>仅剩' + stock + '件</p>';
					u1 += '</dd>';
					u1 += '</div>'
					u1 += '</dl>';
					u1 += '</li>';
				}
				$("#more ul").append(u1);
				$('.ngj_con ul li dl .div_pop1').on("toggle",test());
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

function animate(){
	$(".bar_con").each(function(i,item){
		var a=parseInt($(item).attr("width"));
		$(item).animate({
			width: a+"%"
		},1000);
	});
}
function mySwiper(){
	 //左右滑动
	 var mySwiper = new Swiper('.swiper-container',{
	 slidesPerView : 'auto',//'auto'
	 //slidesPerView : 3.7,
	 observer:true,//修改swiper自己或子元素时，自动初始化swiper
	 observeParents:true,//修改swiper的父元素时，自动初始化swiper
	 })
}
function go2Shop(){
	if($("#shop").val()!=''){
		window.location = 'shop/page?shopId='+$("#shop").val();
	}
}