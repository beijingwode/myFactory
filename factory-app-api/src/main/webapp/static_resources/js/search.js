/*(function(w){
  $(w).load(function(){
	  ajaxSearch(sort);
  });
})(window)*/

var uid=GetUidCookie();
var isload = true;
var userSupplierId=getSupplierIdCookie();
// JavaScript Document
$(function(){
	if(isWeiXinOpen()) {
    	loginCheck(2);
    }
    
	$("#pageNum").val("0");
	$("#bottom").html("");
	ajaxSearch(sort);
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
				
				ajaxSearch(sort);
			}
		}
	}
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	//关闭筛选
	$(".thickclose").click(function(e){
		$(".thickdiv").hide();
		$(".thickbox").hide();
		$(".main_con ul").removeAttr("style");
	});
	$(".thickdiv").click(function(e){
		$(".thickdiv").hide();
		$(".thickbox").hide();
		$(".main_con ul").removeAttr("style");
	});

	$("#keyword").click(function(e){
		var urla = "";
		try{
			if(jsBasePath) {
				urla = jsBasePath;
			}
		} catch(e){}
		window.location = urla+'search_box.html?'+$("#params").val();
	});

	var params = $("#params").val();
	if (params!="") {
		var p=params.indexOf("key=");
		if(p>-1) {
			var key = params.substring(p+4);
			p=key.indexOf("&");
			if(p>-1) {
				key=key.substring(0,p);
			}
			$("#keyword").val(key);
		}
	}
});
var sort="";//全局变量

$("#search_btn").click(function(){//点击搜索触发事件
	$(".main_con ul").html("");
	$("#pageNum").val(0);
	$("#bottom").html("");
	$("#zh").addClass("thisone")
	$("#discount").removeClass("thisone");
	$("#price").removeClass("thisone");
	$("#price").attr("class","search_jg search_jg1");
	$("#filter").attr("class","search_sx search_sx1");
	go2reset();
	sort="";
	//$("#params").val("");//清空参数
	$("#searchBrand").val("");//清空选中品牌
	$("#priceRange").val("");//清空价格区间
	ajaxSearch(sort);
})
$("#zh").click(function(){//点击综合触发事件
	$(".main_con ul").html("");
	$("#pageNum").val(0);
	$("#bottom").html("")
	$("#discount").removeClass("thisone");
	$(this).addClass("thisone");
	$("#price").removeClass("thisone");
	$("#price").attr("class","search_jg search_jg1");
	sort="";
	ajaxSearch(sort);
})
$("#discount").click(function(){//点击折扣
	$(".main_con ul").html("");
	$("#pageNum").val(0);
	$("#bottom").html("")
	$(this).addClass("thisone");
	$("#zh").removeClass("thisone");
	$("#price").removeClass("thisone");
	$("#price").attr("class","search_jg search_jg1");
	sort="discount_0";
	ajaxSearch(sort);
})
$("#price").toggle(//点击价格
	function(){
		$(this).attr("class","search_jg search_jg2");
		$(".main_con ul").html("");
		$("#pageNum").val(0);
		$("#bottom").html("")
		$(this).addClass("thisone")
		$("#zh").removeClass("thisone");
		$("#discount").removeClass("thisone");
		sort="price_0";
		ajaxSearch(sort);
	},
	function(){
		$(this).attr("class","search_jg search_jg3");
		$(".main_con ul").html("");
		$("#pageNum").val(0);
		$("#bottom").html("");
		$(this).addClass("thisone")
		$("#zh").removeClass("thisone");
		$("#discount").removeClass("thisone");
		sort="price_1";
		ajaxSearch(sort);
	}
)
var filterBrand;//筛选品牌
var skuId="";
function ajaxSearch(sort){
	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	var key = $("#keyword").val();
	var url = jsBasePath+'pSearch';
	var cat=false;
	var params = $("#params").val();
	if(params.indexOf('cat')>-1) {
		url=url+'/category?'+params.replace('cat','id');
		cat = true;
	} else {
		if(params=="") {
			url=url+'?';
		} else {
			url=url+'?'+params.replace('cat','id');
		}
	}
	//var searchBrand =getSelBrand();
	//var priceRange=getSalePrice();
	var searchBrand=$("#searchBrand").val();
	var priceRange=$("#priceRange").val();
	if (key!=null&&key!="") {
		if (params!="") {
			url=url+'&key='+key+'&page='+page+'&sort='+sort+'&brand='+searchBrand+'&salePrice='+priceRange;
		}else{
			url=url+'key='+key+'&page='+page+'&sort='+sort+'&brand='+searchBrand+'&salePrice='+priceRange;
		}
	}else{
		if (params!="") {
			url=url+'&page='+page+'&sort='+sort+'&brand='+searchBrand+'&salePrice='+priceRange;
		}else{
			url=url+'page='+page+'&sort='+sort+'&brand='+searchBrand+'&salePrice='+priceRange;
		}
	}
	$.ajax({
		url : url,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			var hits;
			var cost = data.msg;
			if(cat) {
				hits = data.result.hits;
			} else {
				hits = data.data.hits;
			}
			
			if(hits && hits.length>0) {
				$("#pageNum").val(page);
				
				var html='';
				var rprice =0;
				for(var i=0;i<hits.length;i++) {
					if(hits[i].salePrice && hits[i].salePrice!=null){
						rprice = hits[i].salePrice;
					}else{
						rprice = hits[i].price-hits[i].maxFucoin;
					}
					if (hits[i].maxFucoin>cost) {
						hits[i].maxFucoin=cost;
					}
					if(hits[i].minSkuId && typeof(hits[i].minSkuId)!="undefined"){
						skuId = hits[i].minSkuId;
					}
					html +='<li>';
					html +='<dl>';
					html +='<dt><a href="'+jsBasePath+'productm?productId='+hits[i].productId+'&specificationsId='+skuId+'&from=a&pageKey='+pageKey+'"><img src="'+hits[i].image+'" /></a>'
					if(hits[i].saleKbn==1) {
						html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon2.png" /></div>';
					} else if(hits[i].saleKbn==2) {
						html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_c2.png" /></div>';
					} else if(hits[i].saleKbn==4) {
						html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_z2.png" /></div>';
					}else if(hits[i].saleKbn==5) {
						html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_t2.png" /></div>';
					}
					html +='</dt>';
					html +='<dd class="dd1"><a href="'+jsBasePath+'productm?productId='+hits[i].productId+'&specificationsId='+skuId+'&from=a&pageKey=search">'+hits[i].name+'</a></dd>';
					if (userSupplierId !='' && userSupplierId == hits[i].supplierId) {
						html +='<dd class="dd2"><span class="span2">自家</span></dd>';
					}else{
						html +='<dd class="dd2"><span class="span2">'+hits[i].shopName+'</span></dd>';
					}
					html +='<dd class="dd3">￥'+(rprice.toFixed(2))+'+'+hits[i].maxFucoin+'券</dd>';
					html +='</dl>';
					html +='</li>';
				}
            
				$(".main_con ul").append(html);
			} else {
				if ($("#pageNum").val()==0) {
					$("#pageNum").val(-1);
					var html='';
					html +='<img src="'+jsBasePath+'static_resources/images/empty.png" />';
					html +='<p>我的福利已经知道知道您的需求啦</p>';
					html +='<p>您可以下载APP</p>';
					html +='<a href="http://wd-w.com/app.htm?d=1">更多商品尽在APP</a>';
					$("#bottom").html(html);
				}else{
					$("#pageNum").val(-1);
					var html='';
					html +='<a href="http://wd-w.com/app.htm?d=1">更多商品尽在APP</a>';
					$("#bottom").html(html);
				}
				
			}
			
			isload = true;
		},
		error : function() {}
	});
}
function go2filter(){//点击筛选
	$(".main_con ul").attr("style","position:fixed");
	ajaxSearchBrand();
	$(".thickdiv").show();
	$(".thickbox").show();
}
//品牌选择
function sel(obj){
	$($(obj).parent().children()).removeClass("selected");
	$(obj).addClass("selected");
}
function ajaxSearchBrand(){
	var key = $("#keyword").val();
	var url = jsBasePath+'pSearch';
	var cat=false;
	var params = $("#params").val();
	if(params.indexOf('cat')>-1) {
		url=url+'/category?'+params.replace('cat','id');
		cat = true;
	} else {
		if(params=="") {
			url=url+'?';
		} else {
			url=url+'?'+params.replace('cat','id');
		}
	}
	//var searchBrand=$("#searchBrand").val();
	//var priceRange=$("#priceRange").val();
	var searchBrand =getSelBrand();
	var priceRange=getSalePrice();
	if (key!=null&&key!="") {
		if (params!="") {
			url=url+'&key='+key+'&sort='+sort;
		}else{
			url=url+'key='+key+'&sort='+sort;
		}
	}else{
		if (params!="") {
			url=url+'&sort='+sort;
		}else{
			url=url+'sort='+sort;
		}
	}
	var selBrand=$("#searchBrand").val();
	$.ajax({
		url : url,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			var aggregations = data.data.aggregations;
			if (aggregations!=''&&aggregations!=undefined) {
				 filterBrand = data.data.aggregations.brand;
			}
			if (filterBrand!=''&&filterBrand!=undefined) {
				var pinPai='';
				for (var i = 0; i < filterBrand.length; i++) {
					if (selBrand==filterBrand[i]) {
						pinPai +='<a title="1" class="a-item J_ping selected" href="javascript:void(0)" onclick="sel(this)";>'+filterBrand[i]+'</a>';
					}else{
						pinPai +='<a title="1" class="a-item J_ping" href="javascript:void(0)" onclick="sel(this)";>'+filterBrand[i]+'</a>'
					}
				}
				$("#color").html(pinPai);
			}
		}
	})
}
function getSelBrand(){
	var selBrand=$(".thickcon .pro-color .selected").html();
	$("#searchBrand").val(selBrand);
	if (selBrand!=undefined) {
		return selBrand;
	}else{
		selBrand='';
		return selBrand;
	}
}
//点击重置
function go2reset(){
	$(".price_inp .price_lt").val('');//最低价清空
	$(".price_inp .price_rt").val('');//最高价清空
	$("#priceRange").val("");
	$("#searchBrand").val("");
	$("#color").children().removeClass("selected");
}
//点击确定
function go2submit(){
	var brand =getSelBrand();
	var salePrice=getSalePrice();
	 $("#pageNum").val("0");
	 $("#bottom").html("");
	 $(".thickdiv").hide();
	 $(".thickbox").hide();
	 $(".main_con ul").empty();
	 if (brand!=''||salePrice!='') {
		 $("#filter").attr("class","search_sx search_sx2 thisone");
	}else{
		$("#filter").attr("class","search_sx search_sx1");
	}
	 $(".main-cont ul").removeAttr("style");
	 ajaxSearch(sort);
}
function getSalePrice(){
	var low;//最低价
	var high;//最高价
	low = parseFloat($(".price_inp .price_lt").val());
	if (isNaN(low)) {
		low=0;
	}
	high =parseFloat($(".price_inp .price_rt").val());
	if (isNaN(high)) {
		high=0;
	}
	var salePrice;
	if (low > high && high > 0) {//高低互换
		$(".price_inp .price_lt").val(high);
		$(".price_inp .price_rt").val(low);
		salePrice = high + "-" + low;
	}else if (low == 0 && high > 0) {
		salePrice = "0" + "-" + high;
	} else if (low > 0 && high > low) {
		salePrice = low + "-" + high;
	} else if (low > 0 && high == 0) {
		salePrice = low + "-";
	} else if (low==high && (low > 0&&high>0)){
		salePrice = low;
	} else {
		salePrice = "";
	}
	$("#priceRange").val(salePrice);
	return salePrice;
}
