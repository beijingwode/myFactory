// JavaScript Document	
$(function(){	
	// 获取用户余额,并根据余额获取显示其他内容
	getLogoAndBalance();
});
//banner中的圆点居中显示
$(function(){
	var uWidth = $(".d2 ul").width()/2;	
	$(".d2 ul").css({"margin-left":"-"+uWidth+"px",});
})

function getLogoAndBalance() {
	//自家专享
	$.ajax({
		url:'user/getLogoAndBalance.json',
		dataType : 'json',
		success: function(result){
			if(result.success){
				var cash = 0;
				var ticket = 0;
				var exchange = 0;
				var comId = "";
				var logo = "";
				
				if(result.data.balance) {
					cash=result.data.balance;
				}
				if(result.data.companyTicket) {
					ticket=result.data.companyTicket;
				}
				if(result.data.userExchange) {
					exchange=result.data.userExchange;
				}
				if(result.data.comId) {
					comId=result.data.comId;
				}
				if(result.data.logo) {
					logo=result.data.logo;
				}
				
				if(logo!="") {
					$(".logo img").attr("src",logo);
				}
				
				var html="";
				html += '<li class="li1">现金券：'+cash+'</li>';
				if(exchange>0) {
					html += '<li class="li1">换领币：'+exchange+'</li>';
				}
				html += '<li class="li1">内购券：'+ticket+'</li>';
				html += '<input type="hidden" id="userCash" value="'+cash+'">';
				html += '<input type="hidden" id="userTicket" value="'+ticket+'">';
				html += '<input type="hidden" id="userExchange" value="'+exchange+'">';
				$(".my_stamps ul").html(html);
				
				
				
				if(comId!=""){
					getCompanyPros(comId,5,null);
				}
				// 现金券专区
				if(cash>0) {
					getCashPros(cash);
				}
				
				// 试用商品
				getTrailPros(cash);

				// 换领
				if(exchange>0) {
					getExchangePros(cash,exchange);
				}
				
				// 特省
				getOnSellPros(cash);
				
				// 一般内购
				getNormalPros(cash);
			} else {
				goLogin();
			}
		 },
		 error:function(e){
			 goLogin();
		 }
	});//自家专享结束
}

function getCompanyPros(comId,type,selfs) {
	$(".exclusive .tit1 a").attr("href","product/search?supplierId="+comId);
	//自家专享
	$.ajax({
		url:'product/getDifferentgoods.json?type='+type+'&comId='+comId,
		dataType : 'json',
		success: function(result){
			var ps=new Array();
			var idx=0;
			
			if(selfs) {
				for (var i = 0; i < selfs.length; i++) {
					ps[idx++]=selfs[i];
				}
				
				for (var i = 0; i < result.data.length; i++) {
					var has=false;
					for(var j=0;j<selfs.length;j++) {
						if(selfs[j].productId == result.data[i].productId) {
							has=true;
							break;
						}
					}
					
					if(!has) {
						ps[idx++]=result.data[i];
					}
				}
			} else {
				for (var i = 0; i < result.data.length; i++) {
					ps[idx++]=result.data[i];
				}
			}
			 
			if(ps.length>=3){
				var html="";
			
				for (var i = 0; i < ps.length; i++) {
					html += '<li>';
					html += '<img src="'+ps[i].image+'" width="312" height="312" />';
					html += '<span></span>';
					html += '<div class="exc_pro_details">';
					html += '<p class="p1">'+ps[i].brand+'</p>';
					var name=ps[i].name;
					if(name.indexOf(ps[i].brand)==0) {
						name=name.substring(ps[i].brand.length);
					}
					html += '<p class="p2">'+name+'</p>';
					html += '<p class="p3">￥'+parseFloat(ps[i].salePrice).toFixed(2)+'</p>';
					html += '<p class="p4">内购券：￥'+ps[i].maxFucoin+'</p>';
					html += '<p class="p4 p6">电商价：￥'+ps[i].price+'</p>';
					html += '<p class="p5"><a href="'+ps[i].productId+'.html?skuId='+ps[i].minSkuId+'&pageKey=index"  target="_blank">商品详情</a></p>';
					html += '</div>';
					html += '</li>';
				}

				if(html!="") {
					$(".exclusive ul").html(html);
					zijiazhuanxiang();
					$(".exclusive").show();
				}				
			} else {
				if(type==5) {
					getCompanyPros(comId,7,result.data);
				}
			}
		 },
		 error:function(e){
			
		 }
	});//自家专享结束
}

function getCashPros(cash) {

	//现金券专区
	$.ajax({
		url:'product/getDifferentgoods.json?type=6&userMoney='+cash,
		dataType : 'json',
		success: function(result){
			var html="";

			if(result.data.length>=4){
				var div_hid_products = $("#pro_if1 .hid_product_div");
				for (var i = 0; i < result.data.length; i++) {
					if((i >=4 && result.data.length<8) || i >7){
						break;
					}
					var product = result.data[i];
					var link=product.productId+'.html?skuId='+product.minSkuId+'&pageKey=index';
					for(var j=0;j<div_hid_products.length;j++) {
						var index=$(div_hid_products[j]).find("input[name='replaceIndex']").val();
						if(index==(i+1)) {
							link = $(div_hid_products[j]).find("input[name='link']").val();
							product.image = $(div_hid_products[j]).find("input[name='image']").val();
							product.brand = $(div_hid_products[j]).find("input[name='titel1']").val();
							product.name = $(div_hid_products[j]).find("input[name='titel2']").val();
							product.maxFucoin = parseFloat($(div_hid_products[j]).find("input[name='maxFucoin']").val());
							product.price = parseFloat($(div_hid_products[j]).find("input[name='proPrice']").val());
							product.salePrice =parseFloat($(div_hid_products[j]).find("input[name='proSalePrice']").val());
							product.stock = parseFloat($(div_hid_products[j]).find("input[name='quantity']").val());
							break;
						}
					}
					
					var showPrice = product.salePrice;
					var salePrice = product.salePrice.toFixed(2);
					showPrice=showPrice-cash;
					var strPrice = "0";
					if(showPrice>0) {
						strPrice=showPrice.toFixed(2);
						salePrice = cash.toFixed(2);
					}
					
					html += '<li>';
					html += '<a href="'+link+'" target="_blank">';
					html += '<dl>';
					html += '<dt>';
					html += '<img src="'+product.image+'" width="260" height="260" />';
					html += '<span></span>';
					html += '<p>+内购券:￥'+product.maxFucoin.toFixed(2)+' <br />+现金券:￥'+parseFloat(salePrice).toFixed(2)+' <br /><i>电商价:￥'+product.price.toFixed(2)+'</i></p>';
					html += '<em></em>';
					html += '</dt>';
					html += '<dd class="dd1">'+product.brand+'</dd>';
					var name=product.name;
					if(name.indexOf(product.brand)==0) {
						name=name.substring(product.brand.length);
					}
					html += '<dd class="dd2">'+name+'</dd>';
					html += '<dd class="dd3"><em>￥</em><span>'+parseFloat(strPrice).toFixed(2)+'</span></dd>';
					html += '<dd class="dd4">  ';
					html += '<div class="pro_bar">';
					var allStock = 10;
					var stock = 3;
					if(product.allStock) {
						allStock=product.allStock;
					}
					if(product.stock) {
						stock=product.stock;
					}
					if(allStock<=0){
						allStock=10;
						stock=0;
					}
					if(allStock<stock || allStock>500) {
						allStock = 80;
						stock=35;
					}
					allStock=randomStock(stock,allStock);
					html += '<div class="bar_con" width="'+stock/allStock*100+'%" style="width:0px;"></div>';
					html += '</div>';
					html += '<p>仅剩'+stock+'件</p>';
					html += '</dd>';
					html += '</dl>';
					html += '</a>';
					html += '</li>';         
				}
			}

			if(html!="") {
				$("#pro_if1 ul").html(html);
				mouser("#pro_if1");
				$("#pro_if1").show();
				changeLink("#pro_if1");
			}
			
		 },
		 error:function(e){
			 
		}
	});//现金券专区结束
}

function getTrailPros(cash) {
	//答题领好物
	$.ajax({
		url:'product/getDifferentgoods.json?type=4',
		dataType : 'json',
		success: function(result){
			var html="";

			if(result.data.length>=4){
				var div_hid_products = $("#pro_if2 .hid_product_div");
				for (var i = 0; i < result.data.length; i++) {
					if((i >=4 && result.data.length<8) || i >7){
						break;
					}
					
					var product = result.data[i];
					var link=product.productId+'.html?skuId='+product.minSkuId+'&pageKey=index';
					for(var j=0;j<div_hid_products.length;j++) {
						var index=$(div_hid_products[j]).find("input[name='replaceIndex']").val();
						if(index==(i+1)) {
							link = $(div_hid_products[j]).find("input[name='link']").val();
							product.image = $(div_hid_products[j]).find("input[name='image']").val();
							product.brand = $(div_hid_products[j]).find("input[name='titel1']").val();
							product.name = $(div_hid_products[j]).find("input[name='titel2']").val();
							product.maxFucoin = parseFloat($(div_hid_products[j]).find("input[name='maxFucoin']").val());
							product.price = parseFloat($(div_hid_products[j]).find("input[name='proPrice']").val());
							product.salePrice =parseFloat($(div_hid_products[j]).find("input[name='proSalePrice']").val());
							product.stock = parseFloat($(div_hid_products[j]).find("input[name='quantity']").val());
							break;
						}
					}
					
					var showPrice = product.salePrice;
					var salePrice = product.salePrice.toFixed(2);
					showPrice=showPrice-cash;
					var strPrice = "0";
					if(showPrice>0) {
						strPrice=showPrice.toFixed(2);
						salePrice = cash.toFixed(2);
					}
					
					html += '<li>';
					html += '<a href="'+link+'" target="_blank">';
					html += '<dl>';
					html += '<dt>';
					html += '<img src="'+product.image+'" width="260" height="260" />';
					html += '<span></span>';
					html += '<p>+内购券:￥'+product.maxFucoin.toFixed(2)+' <br />+现金券:￥'+parseFloat(salePrice).toFixed(2)+' <br /><i>电商价:￥'+product.price.toFixed(2)+'</i></p>';
					html += '<em><img src="images/new_index_icon1.png" /></em>';
					html += '</dt>';
					html += '<dd class="dd1">'+product.brand+'</dd>';
					var name=product.name;
					if(name.indexOf(product.brand)==0) {
						name=name.substring(product.brand.length);
					}
					html += '<dd class="dd2">'+name+'</dd>';
					html += '<dd class="dd3"><em>￥</em><span>'+parseFloat(strPrice).toFixed(2)+'</span></dd>';
					html += '<dd class="dd4">  ';                         	
					html += '<div class="pro_bar">';
					var allStock = 10;
					var stock = 3;
					if(product.allStock) {
						allStock=product.allStock;
					}
					if(product.stock) {
						stock=product.stock;
					}
					if(allStock<=0){
						allStock=10;
						stock=0;
					}
					if(allStock<stock || allStock>500) {
						allStock = 80;
						stock=35;
					}
					allStock=randomStock(stock,allStock);
					html += '<div class="bar_con" width="'+stock/allStock*100+'%" style="width:0px;"></div>';
					html += '</div>';
					html += '<p>仅剩'+stock+'件</p>';
					html += '</dd>';
					html += '</dl>';
					html += '</a>';
					html += '</li>';        
				}
			}
			if(html!="") {
				$("#pro_if2 ul").html(html);
				mouser("#pro_if2");
				$("#pro_if2").show();
				changeLink("#pro_if2");
			}
			
		 },
		 error:function(e){
			 
		}
	});//答题领好物结束
}

function getExchangePros(cash,exchange) {

	//福利随心换
	$.ajax({
		url:'product/getDifferentgoods.json?type=3',
		dataType : 'json',
		success: function(result){
			var html="";
			
			if(result.data.length>=4){
				var div_hid_products = $("#pro_if3 .hid_product_div");
				for (var i = 0; i < result.data.length; i++) {
					if((i >=4 && result.data.length<8) || i >7){
						break;
					}
					var product = result.data[i];
					var link=product.productId+'.html?skuId='+product.minSkuId+'&pageKey=index';
					for(var j=0;j<div_hid_products.length;j++) {
						var index=$(div_hid_products[j]).find("input[name='replaceIndex']").val();
						if(index==(i+1)) {
							link = $(div_hid_products[j]).find("input[name='link']").val();
							product.image = $(div_hid_products[j]).find("input[name='image']").val();
							product.brand = $(div_hid_products[j]).find("input[name='titel1']").val();
							product.name = $(div_hid_products[j]).find("input[name='titel2']").val();
							product.maxFucoin = parseFloat($(div_hid_products[j]).find("input[name='maxFucoin']").val());
							product.price = parseFloat($(div_hid_products[j]).find("input[name='proPrice']").val());
							product.salePrice =parseFloat($(div_hid_products[j]).find("input[name='proSalePrice']").val());
							product.stock = parseFloat($(div_hid_products[j]).find("input[name='quantity']").val());
							break;
						}
					}
					
					var ex=0;
					var ca=0;
					var needPay = product.salePrice;
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
					
					html += '<li>';
					html += '<a href="'+link+'"  target="_blank">';
					html += '<dl>';
					html += '<dt>';
					html += '<img src="'+product.image+'" width="260" height="260" />';
					html += '<span></span>';
					html += '<p>+换领币:￥'+ex.toFixed(2)+' <br />+现金券:￥'+ca.toFixed(2)+' <br /><i>换领价:￥'+product.salePrice.toFixed(2)+'</i></p>';
					html += '<em><img src="images/new_index_icon.png" /></em>';
					html += '</dt>';
					html += '<dd class="dd1">'+product.brand+'</dd>';
					var name=product.name;
					if(name.indexOf(product.brand)==0) {
						name=name.substring(product.brand.length);
					}
					html += '<dd class="dd2">'+name+'</dd>';
					html += '<dd class="dd3"><em>￥</em><span>'+needPay.toFixed(2)+'</span></dd>';
					html += '<dd class="dd4">  ';                         	
					html += '<div class="pro_bar">';
					var allStock = 10;
					var stock = 3;
					if(product.allStock) {
						allStock=product.allStock;
					}
					if(product.stock) {
						stock=product.stock;
					}
					if(allStock<=0){
						allStock=10;
						stock=0;
					}
					if(allStock<stock || allStock>500) {
						allStock = 80;
						stock=35;
					}
					allStock=randomStock(stock,allStock)							
					html += '<div class="bar_con" width="'+stock/allStock*100+'%" style="width:0px;"></div>';
					html += '</div>';
					html += '<p>仅剩'+stock+'件</p>';
					html += '</dd>';
					html += '</dl>';
					html += '</a>';
					html += '</li>'; 
				}
			}
			if(html!="") {
				$("#pro_if3 ul").html(html);
				mouser("#pro_if3");
				$("#pro_if3").show();
				changeLink("#pro_if3");
			}
		 },
		 error:function(e){
			 
		}
	});//福利随心换结束
}

function getOnSellPros(cash) {

	//不止特省
	$.ajax({
		url:'product/getDifferentgoods.json?type=2',
		dataType : 'json',
		success: function(result){
			var html="";

			if(result.data.length>=4){
				var div_hid_products = $("#pro_if4 .hid_product_div");
				for (var i = 0; i < result.data.length; i++) {
					if((i >=4 && result.data.length<8) || i >7){
						break;
					}
					var product = result.data[i];
					
					var link=product.productId+'.html?skuId='+product.minSkuId+'&pageKey=index';
					for(var j=0;j<div_hid_products.length;j++) {
						var index=$(div_hid_products[j]).find("input[name='replaceIndex']").val();
						if(index==(i+1)) {
							link = $(div_hid_products[j]).find("input[name='link']").val();
							product.image = $(div_hid_products[j]).find("input[name='image']").val();
							product.brand = $(div_hid_products[j]).find("input[name='titel1']").val();
							product.name = $(div_hid_products[j]).find("input[name='titel2']").val();
							product.maxFucoin = parseFloat($(div_hid_products[j]).find("input[name='maxFucoin']").val());
							product.price = parseFloat($(div_hid_products[j]).find("input[name='proPrice']").val());
							product.salePrice =parseFloat($(div_hid_products[j]).find("input[name='proSalePrice']").val());
							product.stock = parseFloat($(div_hid_products[j]).find("input[name='quantity']").val());
							break;
						}
					}
					
					var showPrice = product.salePrice;
					var salePrice = product.salePrice.toFixed(2);
					showPrice=showPrice-cash;
					var strPrice = "0";
					if(showPrice>0) {
						strPrice=showPrice.toFixed(2);
						salePrice = cash.toFixed(2);
					}
					
					html += '<li>';
					html += '<a href="'+link+'" target="_blank">';
					html += '<dl>';
					html += '<dt>';
					html += '<img src="'+product.image+'" width="260" height="260" />';
					html += '<span></span>';
					html += '<p>+内购券:￥'+product.maxFucoin.toFixed(2)+' <br />+现金券:￥'+salePrice+' <br /><i>电商价:￥'+product.price.toFixed(2)+'</i></p>';
					html += '<em><img src="images/new_index_icon2.png" /></em>';
					html += '</dt>';
					html += '<dd class="dd1">'+product.brand+'</dd>';
					var name=product.name;
					if(name.indexOf(product.brand)==0) {
						name=name.substring(product.brand.length);
					}
					html += '<dd class="dd2">'+name+'</dd>';
					html += '<dd class="dd3"><em>￥</em><span>'+parseFloat(strPrice).toFixed(2)+'</span></dd>';
					html += '<dd class="dd4">  ';                         	
					html += '<div class="pro_bar">';
					var allStock = 10;
					var stock = 3;
					if(product.allStock) {
						allStock=product.allStock;
					}
					if(product.stock) {
						stock=product.stock;
					}
					if(allStock<=0){
						allStock=10;
						stock=0;
					}
					if(allStock<stock || allStock>500) {
						allStock = 80;
						stock=35;
					}
					allStock=randomStock(stock,allStock);
					html += '<div class="bar_con" width="'+stock/allStock*100+'%" style="width:0px;"></div>';
					html += '</div>';
					html += '<p>仅剩'+stock+'件</p>';
					html += '</dd>';
					html += '</dl>';
					html += '</a>';
					html += '</li>'; 
				}
			}
			if(html!="") {
				$("#pro_if4 ul").html(html);
				mouser("#pro_if4");
				$("#pro_if4").show();
				changeLink("#pro_if4");
			}
			
		 },
		 error:function(e){
		}
	});//不止特省结束
}

function getNormalPros(cash) {

	//优享内购价
	$.ajax({
		url:'product/getDifferentgoods.json?type=1',
		dataType : 'json',
		success: function(result){
			var html="";
			
			if(result.data.length>4){
				var div_hid_products = $("#pro_if5 .hid_product_div");
				for (var i = 0; i < result.data.length; i++) {
					if((i >=4 && result.data.length<8) || i >7){
						break;
					}
					var product = result.data[i];
					
					var link=product.productId+'.html?skuId='+product.minSkuId+'&pageKey=index';
					for(var j=0;j<div_hid_products.length;j++) {
						var index=$(div_hid_products[j]).find("input[name='replaceIndex']").val();
						if(index==(i+1)) {
							link = $(div_hid_products[j]).find("input[name='link']").val();
							product.image = $(div_hid_products[j]).find("input[name='image']").val();
							product.brand = $(div_hid_products[j]).find("input[name='titel1']").val();
							product.name = $(div_hid_products[j]).find("input[name='titel2']").val();
							product.maxFucoin = parseFloat($(div_hid_products[j]).find("input[name='maxFucoin']").val());
							product.price = parseFloat($(div_hid_products[j]).find("input[name='proPrice']").val());
							product.salePrice =parseFloat($(div_hid_products[j]).find("input[name='proSalePrice']").val());
							product.stock = parseFloat($(div_hid_products[j]).find("input[name='quantity']").val());
							break;
						}
					}
					
					var showPrice = product.salePrice;
					var salePrice = product.salePrice.toFixed(2);
					showPrice=showPrice-cash;
					var strPrice = "0";
					if(showPrice>0) {
						strPrice=showPrice.toFixed(2);
						salePrice = cash.toFixed(2);
					}
					
					html += '<li>';
					html += '<a href="'+link+'" target="_blank">';
					html += '<dl>';
					html += '<dt>';
					html += '<img src="'+product.image+'" width="260" height="260" />';
					html += '<span></span>';
					html += '<p>+内购券:￥'+product.maxFucoin.toFixed(2)+' <br />+现金券:￥'+salePrice+' <br /><i>电商价:￥'+product.price.toFixed(2)+'</i></p>';
					html += '<em></em>';
					html += '</dt>';
					html += '<dd class="dd1">'+product.brand+'</dd>';
					var name=product.name;
					if(name.indexOf(product.brand)==0) {
						name=name.substring(product.brand.length);
					}
					html += '<dd class="dd2">'+name+'</dd>';
					html += '<dd class="dd3"><em>￥</em><span>'+parseFloat(strPrice).toFixed(2)+'</span></dd>';
					html += '<dd class="dd4">';                         	
					html += '<div class="pro_bar">';
					var allStock = 10;
					var stock = 3;
					if(product.allStock) {
						allStock=product.allStock;
					}
					if(product.stock) {
						stock=product.stock;
					}
					if(allStock<=0){
						allStock=10;
						stock=0;
					}
					if(allStock<stock || allStock>500) {
						allStock = 80;
						stock=35;
					}
					allStock=randomStock(stock,allStock);					
					html += '<div class="bar_con" width="'+stock/allStock*100+'%" style="width:0px;"></div>';
					html += '</div>';
					html += '<p>仅剩'+stock+'件</p>';
					html += '</dd>';
					html += '</dl>';
					html += '</a>';
					html += '</li>'; 
				}
			}
			if(html!="") {
				$("#pro_if5 ul").html(html);
				mouser("#pro_if5");
				$("#pro_if5").show();
				changeLink("#pro_if5");
			}
		 },
		 error:function(e){
			 
		}
	});//优享内购价结束
}

function zijiazhuanxiang(){
	//自家专享
	$(".exc_box").slide({
		titCell:"",
		mainCell:".exc_con ul",
		autoPage:true,
		effect:"leftLoop",
		autoPlay:false,
		vis:3,
		scroll:3,
		//delayTime:4000
	});	

	$(".exc_box .exc_con ul li").mouseover(function(){
		$(this).find("span").show();
		$(this).find(".exc_pro_details").show();	
	});
	$(".exc_box .exc_con ul li").mouseout(function(){
		$(this).find("span").hide();
		$(this).find(".exc_pro_details").hide();	
	});
}

function mouser(selecter){
	//现金券专区交互
	$(selecter+" ul li").mouseover(function(){
		$(this).find("dt").find("span").show();
		$(this).find("dt").find("p").show();
		$(this).addClass("hover");	
	});
	$(selecter+" ul li").mouseout(function(){
		$(this).find("dt").find("span").hide();
		$(this).find("dt").find("p").hide();
		$(this).removeClass("hover");		
	});

	animate(selecter);
}
//进度条	
function animate(selecter){
	$(selecter+" .bar_con").each(function(i,item){
		var a=parseInt($(item).attr("width"));
		$(item).animate({
			width: a+"%"
		},1000);
	});
};

// 满库存时的随机处理
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
function changeLink(area) {
	var host = window.location.href
	if(host.indexOf("ace.wd-w.com") >= 0 ){
		 $(area + " a").each(function(i){
		    	var href = $(this).attr("href");
		    	if(href &&href.indexOf('www.wd-w.com')!=-1) {
			    	href = href.replace("www.wd-w.com","ace.wd-w.com");
			    	$(this).attr("href",href);
		        } 			
		 })
	}
}