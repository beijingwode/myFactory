
$(window).on('load',function(){
	$('.flexslider').flexslider({
    animation: "slide",
    directionNav: false,
    start:function(slider){
      $('body').removeClass('loading');
    }
  });
});

var uid=GetUidCookie();
var userSupplierId=getSupplierIdCookie();
var shopId;
$(document).ready(function(){
	if(pageStock && pageStock!=null && typeof(pageStock)!=undefined){
		//将页面对应的库存保存到sessionStorage;
		sessionStorage.setItem("pageStock"+productId,productId+"_"+pageStock);
	}
	 var isPageHide = false;           
		window.addEventListener('pageshow', function(){
		if(isPageHide){window.location.reload();}
		});           
	window.addEventListener('pagehide', function(){isPageHide = true;}); 
	var fromWay=sessionStorage.getItem("fromWay");
	if(typeof(fromWay)!=undefined &&fromWay!=''){
		sessionStorage.removeItem("fromWay");
		var number = $("#number").val();
		var partNumbers="0_"+$("#specificationsId").val();
		if(fromWay=='buyBtn'){
			sessionBefore2Order();
			window.location=jsBasePath +'order/confirmCartPage.user?uid='+uid+'&partNumbers='+partNumbers+'&productIds='+productId+",";
		}else if(fromWay=='cart'){
			ajaxCartAdd();
		}else if(fromWay=='togetherTobuy'){
			sessionBefore2Order();
			window.location = jsBasePath+'groupOrder/confirmCartPage.user?uid='+uid+'&partNumbers='+partNumbers
		}
	}
	var groupBuy =sessionStorage.getItem("groupBuy");
	if(typeof(groupBuy)!=undefined && groupBuy!=null){//清除购物团信息
		sessionStorage.removeItem("groupBuy");
	}
	
	var btnName =sessionStorage.getItem("btnName");
	if(typeof(btnName)!=undefined && btnName!=null){//清除匹配信息
		sessionStorage.removeItem("btnName");
	}
	ajaxGetDetail();
 	
	//获得文本框对象
 	var t = $("#number");
 	
 	//初始化数量为1,并失效减
 	if (parseInt(t.val())<=1 || parseInt(t.val())<=minLimitNum) {//初始化数量小于等于起售数量
 		$('#quantityMinus').attr('disabled',true);
 	}
 	
    //数量增加操作
    $("#quantityPlus").click(function(){
    	if (parseInt(t.val()+1)>1 &&  parseInt(t.val()+1)>minLimitNum){
            $('#quantityMinus').attr('disabled',false);
        } 
    	if (limitCnt>0&&limitCnt<=t.val()) {
			showInfoBox("购买数量超过限购数量")
		}else{
		if(parseInt(productStock)<=parseInt(t.val())){//库存小于等于当前数量
			$('#quantityPlus').attr('disabled',true); return
		}else{
			 t.val(parseInt(t.val())+1)
	 	        ajaxSelectStock(selSku());
		}
		}
    });
 	    
    //数量减少操作
    $("#quantityMinus").click(function(){
    	
    	if (parseInt(t.val())<=1 || parseInt(t.val())<=minLimitNum){
    		$('#quantityMinus').attr('disabled',true); return
    	}
    	 t.val(parseInt(t.val())-1);
    	 if(parseInt(productStock)==parseInt(t.val())){
    		$('#quantityPlus').attr('disabled',true);
    		checkStock(number);
    	 }else if(parseInt(productStock)>parseInt(t.val())){
    		$('#quantityPlus').attr('disabled',false);
    		checkStock(number);
    	 }else{
    		 return;
    	 }
    	 ajaxSelectStock(selSku());
      
    });
   
   var bottomBtn = $("#go2Cart").siblings();
 	for (var i = 0; i < bottomBtn.length; i++) {
 		if($(bottomBtn[i]).attr("id")!='no_product'){
			$(bottomBtn[i]).bind("click",function(){
				  chooseSku(this);
				});
		}
	}
 	
 	$(".ttb_link a").on("tap",function(){
 		 event.stopPropagation(); // 阻止事件冒泡
		 chooseSku(this);
	});
 	
 	if(isWeiXinOpen() && uid!="") {
    	$(".shop-part").hide();
    	showComment();
    	showCollection();
    } else {
        if(!isWeiXin()){
    		var html = '';
    		
    		html +='<div class="push_con" style="padding-top:20px;border-top:1px solid #ccc">';
    		html +='<p>复制链接，在微信中查看，可以直接关注公众号</p>';
    		html +='<img src="static_resources/images/push_pic4.png" />';
    		html +='<p>或在 微信公众号中搜索”我的网”</p>';
    		html +='<img src="static_resources/images/push_pic5.png" />';
    		html +='</div>';
    		html +='<div id="dlApp" class="part2" style="height:50px;line-height:50px;text-align:center;margin-top:10px;border-top:1px solid #ccc;border-bottom:1px solid #ccc;padding:0;">';
    		html +='可以复制链接，在“<span style="color:#ff4040;">我的福利</span>”APP中购买';
    		html +='</div>';
            
        	$(".shop-part").html(html);
        	
        	$(".bottom_box").hide();
        	$(".product_details").hide();//详情页隐藏
        	
        } else {
        	var href = window.location.href;
        	var fuid = "";
        	if (href.indexOf("fuid=")!=-1) {
        		fuid=href.substring(href.indexOf("fuid=")+5);
            	if (fuid.indexOf("&")!=-1) {
            		fuid=fuid.substring(0,fuid.indexOf("&"));
            	}
        	}
        	if(fuid!="") {
        		sessionStorage.setItem("fuid", fuid);
        	}
        	if(sessionStorage.shareId) {
        	} else {
            	var shareId = "";
            	if (href.indexOf("shareId=")!=-1) {
            		shareId=href.substring(href.indexOf("shareId=")+8);
                	if (shareId.indexOf("&")!=-1) {
                		shareId=shareId.substring(0,shareId.indexOf("&"));
                	}
            	}
        		if(shareId!="") {
        			sessionStorage.setItem("shareId", shareId);
        		}
        	}

        	if(sessionStorage.shareId || fuid!="") {
        		showComment();
        		showCollection();
        		$(".shop-part").hide();
            	
        		$(".shop_link a").unbind();
        		$(".bottom_con a").unbind();
        		$(".shop_link a").attr("href","javascript:goBind()");
        		$(".bottom_con a").attr("href","javascript:goBind()");
        		
        	} else {
        		var html = '';
        		html +='<div class="push_con" style="padding-top:20px;border-top:1px solid #ccc">';
        		html +='<img src="'+jsBasePath+'static_resources/images/push_pic6.png" />';
        		html +='</div>';
        		html +='<div id="dlApp" class="part2" style="height:50px;line-height:50px;text-align:center;margin-top:10px;border-top:1px solid #ccc;border-bottom:1px solid #ccc;padding:0;">';
        		html +='还可以复制链接，在“<span style="color:#ff4040;">我的福利</span>”APP中购买';
        		html +='</div>';
        		html +='<div id="buy_open_app" class="part3" style="margin-top:30px;width:100%;height:50px;background:#d9d9d9;text-align:center;line-height:50px;">';
        		html +='<a href="http://www.wd-w.com/app.htm?d=1" class="buy_open_app" style="color:#535353;" >点击，打开或下载我的福利app</a>';
        		html +='</div>';
            	$(".shop-part").html(html);
            	
            	$(".bottom_box").hide();
            	$(".product_details").hide();//详情页隐藏
        	}
        }
    } 
});
/**
*弹出层修改规格
**/
var productName;//商品名称
var PMP;//商品主图
var skuMap;
var psendAddress;
var stockMap;
var limitCnt;//商品限购数量
var trialProduct = false;//判断是否为评价后购买商品并且非回答
var isCanBuy = true;
var isCanBuyMsg = '';
var questionId = '';//问卷模板id
var minLimitNum =1;
var productStock = 0;//商品库存

function ajaxGetDetail(){
	var number = $("#number").val();
	if(number=='' || number<=0) {
		number=1;
		$("#number").val(number);
	}
	$.ajax({
		url : jsBasePath +'product/detail.json?productId='+productId+'&userId='+uid+'&quantity='+number+'&specificationsId='+$("#specificationsId").val(),
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			var html='';

			if (data.success) {
				var result = data.data;
				stockMap = result.stockMap;
				isCanBuy =result.isCanBuy;
				if(!isCanBuy){
					isCanBuyMsg =result.isCanBuyMsg;
				}
			    //特省
			    var product=result.product;
			    sessionStorage.setItem("saleKbn",product.saleKbn);
			    if(product.saleKbn == 1) {//特省
			    	$("#product_flag a").html('<img src="'+jsBasePath+'static_resources/images/TogetherToBuy/tesheng_icon.png" />');
			    	$("#go2Cart").after('<a href="javascript:void(0);" class="add_cart" id="add_cart">加入购物车</a><a href="javascript:void(0);" class="togetherTobuy_btn" id="togetherTobuy_btn">一起购</a><a href="javascript:void(0);" class="buy_btn" id="buy_btn">立即购买</a>');
			    	$(".TogetherToBuy_link").html('特省原因：'+product.saleNote);
			    	$(".TogetherToBuy_link").show();
			    	$("#product_flag").show();
			    }else if(product.saleKbn == 2) {//换领
			    	$("#product_flag a").html('<img src="'+jsBasePath+'static_resources/images/TogetherToBuy/huanling_icon.png" />');
				    $("#go2Cart").after('<a href="javascript:void(0);" class="buy_btn2" id="buy_btn">直接购买</a> <a href="javascript:void(0);" class="match_btn" id="match_btn">我想领</a>');
				    $(".TogetherToBuy_link").html('换领原因：'+product.saleNote);
				    $(".TogetherToBuy_link").show();
				    $("#product_flag").show();
			    }else if(product.saleKbn == 5) {//试用
			    	if(product.saleNote && product.saleNote!=''){
			    		$(".TogetherToBuy_link").html('试用：'+product.saleNote);
					    $(".TogetherToBuy_link").show();
			    	}
			    	$("#product_flag a").html('<img src="'+jsBasePath+'static_resources/images/TogetherToBuy/shiyong_icon.png" />');
				    $("#go2Cart").after('<a href="javascript:void(0);" class="add_cart_sy" id="add_cart">加入购物车</a><a href="javascript:void(0);" class="buy_btn_sy" id="buy_btn">申请试用</a>');
				    $("#product_flag").show();
			    }else if(product.saleKbn == 4) {//专享
			    	if(product.saleNote && product.saleNote!=''){
			    		$(".TogetherToBuy_link").html('专享原因：'+product.saleNote);
					    $(".TogetherToBuy_link").show();
			    	}
			    	$("#product_flag a").html('<img src="'+jsBasePath+'static_resources/images/TogetherToBuy/zhuanxiang_icon1.png" />');
				    
				    $("#go2Cart").after('<a href="javascript:void(0);" class="add_cart" id="add_cart">加入购物车</a><a href="javascript:void(0);" class="togetherTobuy_btn" id="togetherTobuy_btn">一起购</a><a href="javascript:void(0);" class="buy_btn" id="buy_btn">立即购买</a>');
				    $("#product_flag").show();
			    } else {
			    	$("#product_flag").hide();
			    	$("#go2Cart").after('<a href="javascript:void(0);" class="add_cart" id="add_cart">加入购物车</a><a href="javascript:void(0);" class="togetherTobuy_btn" id="togetherTobuy_btn">一起购</a><a href="javascript:void(0);" class="buy_btn" id="buy_btn">立即购买</a>');
			    }
			    if(product.saleKbn == 5 && product.empPrice == 0 && product.saleNote.length == 10 ){
			    	if(!product.isQuestioned){//判断是否回答过问卷
			    		trialProduct = true;
			    	}
			    	questionId = product.questionnaireId;
			    }
			    productName=product.fullName;//商品名称
		    	$(".main_two .con_tit p").html(product.fullName);
				//主图
				var defaultImage=result.defaultImage;
			    $(".con_box .slides img").each(function (index) {
			    	if(index<defaultImage.length) {
			          $(this).attr("src",defaultImage[index].source); 
			    	} else {
				       $(this).attr("src",defaultImage[0].source); 
			    	}
			    });
			    PMP=defaultImage[0].source;//商品首张主图
			    $(".thickcon dl dt img").attr("src",defaultImage[0].source);
		    	//价格
			    var purchasePrice = result.purchasePrice;
			    if(typeof(purchasePrice)!=undefined && purchasePrice !=null){
		    		if(purchasePrice=='0'){
		    			$("#buy_btn").text("立即领取");
		    		}
		    	}
		    	//邮费
		    	var carriage=result.carriage
		    	var sendAddress = product.sendAddress;
		    	sendAddress = sendAddress.replace(" 市辖区","");
		    	psendAddress = sendAddress;
		    	if(carriage=='' || carriage<=0) {
		    		sendAddress = '<span class="span2"> 包邮 </span><span>' + sendAddress+'</span>';
		    	} else if (carriage==8888) {
		    		sendAddress = '<span>销售区域不包含默认地址:</span>';
		    	} else if (carriage==9999) {
			    	sendAddress = '<span>超过限购数量，不能再买啦</span>';
		    	} else {
		    		sendAddress = '<span class="span2"> ￥'+carriage+" </span><span> "+ sendAddress+'</span>';
		    	}
		    	if (carriage==8888) {
		    		$(".postage:eq(0)").html(sendAddress+"<span>" + result.carriageDes +"</span>");
				}else if(carriage==9999){
					$(".postage:eq(0)").html(sendAddress);
				}else{
					sendAddress = sendAddress + " 发往 <span> " + result.carriageDes +"</span>";
					$(".postage:eq(0)").html("邮费" + sendAddress);
				}
		    	//服务
		    	var supplierId  = product.supplierId;
		    	var service =result.serviceDatas;
		    	if(service && service!=null && service!=''){
		    		var html='';
		    		var page='';
		    		for (var i = 0; i < service.length; i++) {
						html+='<li>'+service[i].title+'</li>';
						page+='<dl><dt>'+service[i].title+'</dt>';
						page+='<dd>'+service[i].content.replace(/\n/g,"<br />")+'</dd>';
						page+='</dl>';
					}
		    		$(".serve a ul").html(html);
		    		$(".thickbox2 .thickcon p").after(page);
		    	}
		    	
		    	//sku
		    	var smap=product.smap;
		    	var index = 0;
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
						as += '<a title="2" class="a-item J_ping" id="'+ vl[j].id +'" href="javascript:void(0);" onclick="sel(this)">'+vl[j].specificationValue+'</a>';	
					}
					$(div).children("p").html(as);
		    	} 
		    	
		    	if(index<2) {
		    		$(".thickcon .pro-color").eq(1).hide();
		    	}
		    	var defaultSelectSKU=result.defaultSelectSKU;
	    		var divs = $(".thickcon .pro-color");
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
		    	togetStock();
		    	number = result.minLimitNum;
		    	minLimitNum = result.minLimitNum;//起售数量
		    	$("#number").val(number);
		    	ajaxGetImage(result.specificationsId);
		    	//ajaxGetLimitTicket(uid,result.specificationsId);
		    	$("#specDetailInfo").html(selected + '&nbsp;&nbsp;' + '<span id="amount">'+number+'件</span>');
		    	$(".thickcon .dd1:eq(1)").html(selected);
		    	$(".thickcon .dd1:eq(0)").html("库存"+result.stock+"件");
		    	$(".thickcon .dd1:eq(2)").html(minLimitNum+'件起售');
		    	$("#specificationsId").val(result.specificationsId);
		    	productStock = result.stock;
		    	skuMap = result.skuMap;
		    	
		    	//店铺
		    	var supplierShopVo=product.supplierShopVo;
		    	if (userSupplierId!=supplierId) {
		    		$(".shop_link a").html("商家："+supplierShopVo.shopname);
		    	}else{
		    		$(".shop_link a").html("商家：自家");
		    	}
		    	shopId=product.shopId;
		    	$(".shop_link a").click(function(e){
					window.location = jsBasePath +'shop/page?shopId='+shopId;
		    	});
		    	if(product.saleKbn!=2 && product.saleKbn!=4 && product.saleKbn !=5){
		    		ajaxGetGroupBuyUser(shopId);
		    	}
		    	//底部按钮
		    	//详情
		    	if(product.introduction==''){
		    		$(".product_details").hide();
		    	}else{
		    		var introduction =product.introduction.replace(/<\/p>/g,"").replace(/<\p>/g,"");
		    		$(".con_box_img").html(introduction);
		    	}
		    	getCartNum();
		     	if(isWeiXinOpen() && uid!="") {
		     		// 准备分享
		     		var userInfo = result.userFactory;
		     		if(userInfo && userInfo!=null && userInfo!=''){
		     			var employeeType = userInfo.employeeType
				    	 var userName = userInfo.nickName;
				    	 if (employeeType!='' && employeeType!=0) {//员工账户
				 	 	   	go2Share(userName);
				    	 }
		     		}
	    	    }
			}
		},
		error : function() {}
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
	ajaxGetImage(selSku());
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
	$("#specDetailInfo").html(selected + '&nbsp;&nbsp;' + '<span id="amount">'+number+'件</span>');

	$(".thickcon .dd1:eq(1)").html(selected);
	productStock = stockMap[selt];
	
	if(skuMap[selt]=="0"){
		$(".thickcon .dd1:eq(0)").html("库存"+stockMap[selt]+"件");
		checkStock(stockMap[selt]);
	}
	
	return skuMap[selt];
}

/**
*弹出层修改规格
**/
function ajaxGetImage(sku){
	var number = $("#number").val();
	if(number=='' || number<=0) {
		number=1;
		$("#number").val(number);
	}
	$("#specificationsId").val(sku);
	if(sku!="0"){
	$.ajax({
		url : jsBasePath +'product/image.json?specificationsId='+sku+'&userId='+uid+'&quantity='+number,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html='';

			if (data.success) {
				var result = data.data;
				if(result.isLadder){
					result.cost = result.maxFucoin;
				}
				//主图
				var defaultImage=result.image;
			    $(".con_box .slides img").each(function (index) {
			    	if(index<defaultImage.length) {
			          $(this).attr("src",defaultImage[index].source); 
			    	} else {
				          $(this).attr("src",defaultImage[0].source); 
			    	}
			       });
			    $(".thickbox1 .thickcon dl dt img").attr("src",defaultImage[0].source);
			    //价格
			    var salesPromotion = result.salesPromotion;
			    var arry  = new Array();
			    for ( var i in salesPromotion) {
			    	arry.push(i)
				}
			    sessionStorage.setItem("salesPromotion",JSON.stringify(arry));//设置阶梯价格
			    
			    if(salesPromotion && salesPromotion!=null && salesPromotion!='' && !isEmptyObject(salesPromotion)){
			    	dealSalesPromotion(salesPromotion);
			    }else{
			    	$(".jtj_box").hide();
			    }
			    var rprice = 0;
			    var purchasePrice = result.purchasePrice;
		    	if(typeof(purchasePrice)!=undefined && purchasePrice !=null){
		    		rprice = result.purchasePrice
		    		if(purchasePrice=='0'){
		    			$("#buy_btn").text("立即领取");
		    		}
		    	}else{
		    		rprice =result.price-result.maxFunction;
		    	}
		    	var is_exclusive =false;//是否为专享
		    	if (result.maxFucoin!=null && result.maxFucoin>=0) {
			    	
			    	$(".thickbox1 .thickcon .dd2").html('￥<em>'+parseFloat(rprice).toFixed(2)+'</em>+内购券'+result.cost);
				}else{
					if (result.cost!=null && result.cost>0) {
						is_exclusive =true;
						
				    	$(".thickbox1 .thickcon .dd2").html('￥<em>'+parseFloat(result.cost).toFixed(2)+'</em>');
					}
				}
			    var saleKbn = sessionStorage.getItem("saleKbn");
				if(saleKbn && saleKbn!=null){
					var pricePage ='';
					if(saleKbn=='2'){//换领
					    pricePage +='<div class="price">';
					    pricePage +='<div class="price_lt"><i id="price_icon"><img src="'+jsBasePath+'static_resources/images/TogetherToBuy/hl_price.png" /></i><p><em></em><span>'+parseFloat(rprice).toFixed(2)+'</span><img src="'+jsBasePath+'static_resources/images/TogetherToBuy/hl_currency.png" style="margin-left:5px;height:21px;width:18px; margin-top: -4px;"/></p></div>';
					    pricePage +='<div class="price_rt">匹配中的数量：<span>'+result.orderCount+'</span></div>';
					    pricePage +='</div>';
					    pricePage +='<div class="price">';
					    pricePage +='<div class="price_lt huanling"><p>抢购价：￥'+parseFloat(rprice).toFixed(2)+'+'+result.cost+'内购券</p></div>';
					    pricePage +='<div class="price_rt">库存：<span>'+result.stock+'</span></div>';
					    pricePage +='</div>';
					    $(".thickbox1 .thickcon .dd2").html('￥<em>'+parseFloat(rprice).toFixed(2)+'</em>');
					}else{
						pricePage +='<div class="price">';
						pricePage +='<div class="price_lt">';
						if(is_exclusive){//专享
							pricePage +='<i id="price_icon"><img src="'+jsBasePath+'static_resources/images/TogetherToBuy/zhuanxiang_icon.png" /></i><p><em>￥</em><span>'+parseFloat(result.cost).toFixed(2)+'</span></p>';
						}else{//非专享
							pricePage +='<i id="price_icon"><img src="'+jsBasePath+'static_resources/images/TogetherToBuy/neigou_icon.png"/></i><p><em>￥</em><span>'+parseFloat(rprice).toFixed(2)+'</span>+内购券'+parseFloat(result.cost).toFixed(2)+'</p>';
						}
						pricePage+='</div>'
					    pricePage +='<div class="price_rt">电商价：<span>￥'+parseFloat(result.showPrice).toFixed(2)+'</span></div>';
					    pricePage +='</div>';
					}
					$(".price").remove();
					$(".con_tit").after(pricePage);
				}
		    	//邮费
		    	var carriage=result.carriage;
		    	var sendAddress = psendAddress;
		    	if(carriage=='' || carriage<=0) {
		    		sendAddress = '<span class="span2">包邮 </span><span>' + sendAddress+'</span>';
		    	} else if (carriage==8888) {
		    		sendAddress = '<span>销售区域不包含默认地址:</span>';
		    	} else if (carriage==9999) {
			    	sendAddress = '<span>超过限购数量，不能再买啦</span>';
		    	} else {
		    		sendAddress = '<span class="span2">' + carriage + "</span><span>" + sendAddress+'</span>';
		    	}
		    	if (carriage==8888) {
		    		$(".postage:eq(0)").html(sendAddress+"<span>" + result.carriageDes +"</span>");
				}else if(carriage==9999){
					$(".postage:eq(0)").html(sendAddress);
				}else{
					sendAddress = sendAddress + "发往<span>" + result.carriageDes +"</span>";
					$(".postage:eq(0)").html("邮费" + sendAddress);
				}
		    	minLimitNum = result.minLimitNum;
		    	if($("#number").val()<minLimitNum){//当前选择数量小于起售数量，则将数量变为最小起售数量
		    		$("#number").val(minLimitNum);
		    	}else{
		    		if($("#number").val()>minLimitNum){
		    			$("#quantityMinus").attr("disabled",false);
		    		}
		    	}
		    	$(".thickbox1 .thickcon .dd1:eq(0)").html("库存"+result.stock+"件");
		    	$(".thickbox1 .thickcon .dd1:eq(2)").html(result.minLimitNum+"件起售");
		    	$("#amount").html($("#number").val()+"件");
		    	if($("#number").val()>result.stock){
		    		$("#quantityMinus").attr("disabled",false);
		    		checkStock(0);
		    	}else{
		    		checkStock(result.stock);
		    	}
		    	autoChooseSalePromotion(number);
		  		if(typeof(saleKbn)!=undefined && saleKbn!=null && saleKbn==2){//换领
		  			$(".price_rt").css("text-decoration","none");
		  		}
		  		ajaxGetLimitTicket(uid,sku);
			}
		},
		error : function() {}
	});
	}
}
function isEmptyObject(e) {  
    var t;  
    for (t in e)  
        return !1;  
    return !0  
}
/**
*弹出层修改规格
**/
function ajaxSelectStock(sku){
	var number = $("#number").val();
	if(number=='' || number<=0) {
		number=1;
		$("#number").val(number);
	}
	$.ajax({
		url : jsBasePath +'product/selectStock.json?specificationsId='+sku+'&userId='+uid+'&quantity='+number,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
		success : function(data) {
			var html='';
			if (data.success) {
				var result = data.data;
				var salesPromotion = sessionStorage.getItem("salesPromotion");
				var index='';
				if(result.isLadder){//阶梯价（换领无）
					autoChooseSalePromotion(number);
					if(result.maxFucoin>=0){
						$(".price_lt p").html('<em>￥</em><span>'+parseFloat(result.realPrice).toFixed(2)+'</span>+'+result.maxFucoin+'内购券');
						$(".thickbox1 .thickcon .dd2").html('￥'+parseFloat(result.realPrice).toFixed(2)+'+'+result.maxFucoin);
					}else{
						$(".price_lt p").html('<em>￥</em><span>'+parseFloat(result.realPrice).toFixed(2)+'</span>');
						$(".thickbox1 .thickcon .dd2").html('￥'+parseFloat(result.realPrice).toFixed(2));
					}
				}else{//无阶梯价 
					var saleKbn = sessionStorage.getItem("saleKbn");
					if(saleKbn && saleKbn=='2'){//换领
					}else{
						if(result.maxFucoin>=0){
							$(".price_lt p").html('<em>￥</em><span>'+parseFloat(result.realPrice).toFixed(2)+'</span>+'+result.maxFucoin+'内购券');
							$(".thickbox1 .thickcon .dd2").html('￥'+parseFloat(result.realPrice).toFixed(2)+'+'+result.maxFucoin);
						}else{
							$(".price_lt p").html('<em>￥</em><span>'+parseFloat(result.cost).toFixed(2)+'</span>');
							$(".thickbox1 .thickcon .dd2").html('￥'+parseFloat(result.cost).toFixed(2));
						}
					}
				}
		    	//邮费
		    	var carriage=result.carriage;
		    	var sendAddress = psendAddress;
		    	if(carriage=='' || carriage<=0) {
		    		sendAddress = '<span class="span2">包邮 </span><span>' + sendAddress+'</span>';
		    	} else if (carriage==8888) {
		    		sendAddress = '<span>销售区域不包含默认地址:</span>';
		    	} else if (carriage==9999) {
			    	sendAddress = '<span>超过限购数量，不能再买啦</span>';
		    	} else {
		    		sendAddress = '<span class="span2">' + carriage + "</span><span>" + sendAddress+'</span>';
		    	}
		    	if (carriage==8888) {
		    		$(".postage:eq(0)").html(sendAddress+"<span>" + result.carriageDes +"</span>");
				}else if(carriage==9999){
					$(".postage:eq(0)").html(sendAddress);
				}else{
					sendAddress = sendAddress + "发往<span>" + result.carriageDes +"</span>";
					$(".postage:eq(0)").html("邮费" + sendAddress);
				}
		    	
			}else{
				showInfoBox(data.msg);
			}
		},
		error : function() {}
	});
}

/**
*弹出层修改规格
**/
function ajaxCartAdd(){
	if(uid=="") return;
	var number = $("#number").val();
	if(!isCanBuy){
		showInfoBox(isCanBuyMsg);
		return;
	}
	if(trialProduct){
		window.location = jsBasePath +"order/comments/questionnaire"+questionId+"?uid="+uid+'&fromWay=cart';
	}else{
	if(number=='' || number<=0) {
		number=1;
		$("#number").val(number);
	}
		
	$.ajax({
		url : jsBasePath +'cart/add?uid='+uid+'&specificationId='+$("#specificationsId").val()+'&quantity='+number+'&pageKey='+pageKey+'&fromType=weixin',
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache: false,
		success : function(data) {
			var html='';
			if (data.success) {
				getCartNum();
				showInfoBox("添加购物车成功");
				setTimeout(closePoP(),1500);
			} else {
				showInfoBox(data.msg);
			}
		},
		error : function() {}
	});
	}
}
function getCartNum(){
	if(uid==null||uid==""){
		$("#cartNum").html("0");
		return;
	} 
	$.ajax({
		url :'user/getCartNum.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
		cache: false,
		success : function(data) {
			if(data.success){
				$("#cartNum").html(data.data);
			}else{
				
			}
		}
	})
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
/**
 * 判断该规格下的库存
 * @param stock
 */
function checkStock(stock){
	var open =$(".bottom_con").data("open");
	if (stock==0) {
		$("#no_product").siblings().hide();
		if(open!='all' && open){
			$("#no_product").attr("class","no_product_sku");
		}else{
			$("#no_product").attr("class","no_product");
			$("#go2Cart").show();
		}
		$("#no_product").show();
		$('#quantityPlus').attr('disabled',true);
	}else{
		if(open=='all'){//全部
			$(".bottom_con").children().show();
			dealBottomBtn();
		}else{//单个按钮
			$("#"+open).show();
		}
		$("#no_product").hide();
		$('#quantityPlus').attr('disabled',false);
	}
	
}

function goBind() {
	sessionStorage.setItem("loginNextUrl", window.location.href);
	sessionStorage.setItem("loginPreUrl", window.location.href);
	var shareId =sessionStorage.getItem("shareId");
	var fromId="";

	var state="bindOrLogin";
	if(sessionStorage.shareId) {
		state="bindOrLogin";
	} else {
		state="friendBind";
	}
	var openId=sessionStorage.getItem("openId");
	if(typeof(openId)=="undefined" || openId==null){
		var rtn = encodeURI(system_domain+"wx/hasBind");
		window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_open_appId+"&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
	} else {
		if(sessionStorage.shareId) {
			window.location="/userShare/companyBindPage"+shareId+"?fromId="+fromId+"&openId="+encodeURI(openId);
		} else {
			window.location = '/userShare/userFriendBind?fuid='+sessionStorage.fuid+'&type=4';
		}
	}
}

function ajaxGetGroupBuyUser(shopId){
	$.ajax({
		url : jsBasePath +'group/getApplyByShop.user?uid='+uid+'&shopId='+shopId+'&productIds='+productId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success && data.data>0) {
				$(".ttb_link").show();
				$(".ttb_link a").on("click",function(){
					  chooseSku(this);
				});
			}
		},
		error : function() {}
	});
}

function ajaxNewCartAdd(btn){
	if(uid=="") return;
	var number = $("#number").val();
	if(!isCanBuy){
		showInfoBox(isCanBuyMsg);
		return;
	}
	if(number=='' || number<=0) {
		number=1;
		$("#number").val(number);
	}
	if(btn=='matchBtn'){//匹配
		var matchRules = localStorage.getItem("isKnowRules");
		if(matchRules==null){//
			$(".thickdiv").addClass("thickdiv1");
			$(".thickdiv").show();
			$(".thickbox1").hide();
			$(".thickbox2").hide();
			dealBottomBtn();
			$(".huanling_hit").show();
			return;
		}
	}
	$.ajax({
		url : jsBasePath +'cart/newAdd?uid='+uid+'&specificationId='+$("#specificationsId").val()+'&quantity='+number+"&pageKey="+pageKey+"&fromType=weixin",
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html='';
			if (data.success) {
				if(trialProduct){
					window.location = jsBasePath +"order/comments/questionnaire"+questionId+"?uid="+uid+'&fromWay='+btn;
				}else{
					var partNumbers = "0_"+ $("#specificationsId").val() + ",";
					if (btn == 'buyBtn') {
						sessionStorage.setItem("btnName","other");
						sessionBefore2Order();
						window.location = jsBasePath+ 'order/confirmCartPage.user?uid=' + uid+ '&partNumbers=' + partNumbers+"&productIds="+productId;
					} else if (btn == 'togetherTobuy') {
						sessionBefore2Order();
						window.location = jsBasePath+ 'groupOrder/confirmCartPage.user?uid=' + uid+ '&partNumbers=' + partNumbers;
					}else if(btn=='matchBtn'){//匹配
						sessionStorage.setItem("btnName","match");
						sessionBefore2Order();
						window.location = jsBasePath+ 'exchangeOrder/confirmCartPage.user?uid=' + uid+ '&partNumbers=' + partNumbers;
					}
				}
			} else {
				showInfoBox(data.msg);
			}
		},
		error : function() {}
	});
}
function chooseSku(that){
	$(".thickdiv").show();
	$(".thickbox1").show();
	var openWay = '';
	if("add_cart" == that.className){//购物车
		$(that).attr("class","add_cart_sku");
		$(that).siblings().hide();
		openWay = "add_cart";
	}else if("togetherTobuy_btn"==that.className){
		$("#togetherTobuy_btn").attr("class","togetherTobuy_btn_sku");
		$("#togetherTobuy_btn").siblings().hide();
		openWay = "togetherTobuy_btn";
	}else if("match_btn" == that.className){
		$(that).attr("class","match_btn_sku");
		$(that).siblings().hide();
		openWay = "match_btn";
	}else if("buy_btn" == that.className){
		$(that).attr("class","buy_btn_sku");
		$(that).siblings().hide();
		openWay = "buy_btn";
	}else if("buy_btn2" == that.className){
		$(that).attr("class","buy_btn2_sku");
		$(that).siblings().hide();
		openWay = "buy_btn";
	}else if("add_cart_sy" == that.className){
		$(that).attr("class","add_cart_sku");
		$(that).siblings().hide();
		openWay = "add_cart";
	}else if("buy_btn_sy" == that.className){
		$(that).attr("class","buy_btn_sku");
		$(that).siblings().hide();
		openWay = "buy_btn";
	}else{
		openWay = "all";
		$("#go2Cart").show();
	}
	var otherBtn = $("#go2Cart").siblings();
	for (var i = 0; i < otherBtn.length; i++) {
		$(otherBtn[i]).unbind();
	}
	$("#add_cart").attr("onclick","ajaxCartAdd()");//购物车
	$("#buy_btn").attr("onclick","ajaxNewCartAdd(\'buyBtn\')");//直接购买
	$("#togetherTobuy_btn").attr("onclick","ajaxNewCartAdd(\'togetherTobuy\')");//一起购
	$("#match_btn").attr("onclick","ajaxNewCartAdd(\'matchBtn\')");//换领
	$(".bottom_con").data('open',openWay);
}

function dealSalesPromotion(resultMap){
	if(resultMap){
		var html ='';
		for(var key in resultMap)  {
			html+='<li onclick="chooseSalePromotion(this)">';
			html+='<em>≥'+key+'件</em>';
			html+='<i>￥'+resultMap[key].toFixed(2)+'/件</i>';
			html+='</li>';
		}
		$(".jtj_con ul").html(html);
	}
}
function chooseSalePromotion(that){
	var s = $(that).children('em')[0].innerHTML;
	var i = $(that).children('i')[0].innerHTML;
    var num= s.replace(/[^0-9]/ig,"");
    $("#number").val(num);
    ajaxSelectStock(selSku());
    var reprice= i.replace(/[^0-9]/ig,"");
    $(".jtj_con ul li").removeClass("crr")
	$(that).addClass("crr")
}
function autoChooseSalePromotion(num){
	var salesPromotion = sessionStorage.getItem("salesPromotion");
	var index='';
	if(salesPromotion && salesPromotion!=null && salesPromotion!=''){
		salesPromotion = JSON.parse(salesPromotion);
		for ( var i in salesPromotion) {
			if(parseInt(num)>=parseInt(salesPromotion[i])){//购买数量大于优惠数量
				index=i;
			}else{
				continue;
			}
		}
		$(".jtj_con ul li").removeClass("crr");
		if(index!='' && parseInt(index)>=0){
			$(".jtj_con ul li:eq("+index+")").addClass("crr");
		}
	}
}
/**
 * 处理底部按钮
 * @returns
 */
function dealBottomBtn(){
	$("#go2Cart").show();
	var saleKbn = sessionStorage.getItem("saleKbn");
	if(productStock>0){
		if(saleKbn==2){//换领
			$("#match_btn").show();
			$("#match_btn").attr("class","match_btn");
			$("#buy_btn").attr("class","buy_btn2");
		}else if(saleKbn==5){//试用
			$("#add_cart").show();
			$("#add_cart").attr("class","add_cart_sy");
			$("#buy_btn").attr("class","buy_btn_sy");
		}else{//特省及普通
			$("#add_cart").show();
			$("#togetherTobuy_btn").show();
			$("#add_cart").attr("class","add_cart");
			$("#buy_btn").attr("class","buy_btn");
			$("#togetherTobuy_btn").attr("class","togetherTobuy_btn");
		}
		$("#buy_btn").show();
	}else{
		$("#no_product").show();
		$("#no_product").attr("class","no_product");
	}
}

function showComment() {
	$.getScript(jsBasePath+"static_resources/js/product_m_comment.js", function() {
		ajaxGetComments();
	});
}

function showCollection() {
	$.getScript(jsBasePath+"static_resources/js/product_m_collection.js", function() {
		ajaxGetCollectProduct();
	});
}

function go2Share(uName){
	var link = window.location.href;
	$.ajax({
		url : jsBasePath+'wx/wxConfig?url='+encodeURI(window.location.href.replace(/&/g,"____").replace(/=/g,"****")),
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: data.data.appId, // 必填，公众号的唯一标识
				    timestamp: data.data.timestamp, // 必填，生成签名的时间戳
				    nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
				    signature: data.data.signature,// 必填，签名，见附录1
				    jsApiList: ['onMenuShareTimeline',
				                'onMenuShareAppMessage',
				                'onMenuShareQQ',
				                'onMenuShareWeibo',
				                'onMenuShareQZone',
				                'showOptionMenu',
				                'hideOptionMenu'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				
				wx.ready(function(){
					var shareData={
							title: '我的福利分享', // 分享标题
				            link: link+'&userName='+uName+'&fuid='+uid+'&type=4&pageKey=share', // 分享链接,将当前登录用户转为fuid,以便于发展下线
				            imgUrl: PMP, // 分享图标
				            desc:'我在看【'+productName+'】分享给你,快来看!',
					};
					//分享给朋友
					wx.onMenuShareAppMessage(shareData);
					//分享到朋友圈
					wx.onMenuShareTimeline(shareData);
					// 分享到QQ
					wx.onMenuShareQQ(shareData);
					//分享微博
					wx.onMenuShareWeibo(shareData);
					//分享qq空间
					wx.onMenuShareQZone(shareData);
					wx.showOptionMenu();
				});
				wx.error(function(res){
					//alert(JSON.stringify(res));
				});
			} else {
			}
		},
		error : function() {
			//alert("err");
		}
	});
}
function closePoP(){
	closeMsg();
	$(".thickdiv").hide();
	$(".thickbox1").hide();
	$(".thickbox2").hide();
	dealBottomBtn();
}
function matchRules(){
	localStorage.setItem("isKnowRules","yes");
	$(".thickdiv").removeClass("thickdiv1");
	$(".huanling_hit").hide();
	ajaxNewCartAdd('matchBtn');
}