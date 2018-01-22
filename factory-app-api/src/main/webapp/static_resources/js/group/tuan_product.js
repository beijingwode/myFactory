
$(window).on('load',function(){
	$('.flexslider').flexslider({
    animation: "slide",
    directionNav: false,
    start:function(slider){
      $('body').removeClass('loading');
    }
  });});
var uid=GetUidCookie();
var userSupplierId=getSupplierIdCookie();
$(document).ready(function(){
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	//收藏按钮点击效果
	$(".slides_top").on("tap",function(e){
		  toggleCollectProduct();
	});
	ajaxGetDetail();
	ajaxGetComments();
 	 
});

function checkProduct(){
	
	var productIds = sessionStorage.getItem("chooseProductIds");//选中的商品id
	if(typeof(productIds)!=undefined && productIds!=null && productIds!=''){
		if(productIds.indexOf(productId)==-1){
			productIds+=productId+",";
			sessionStorage.setItem("chooseProductIds",productIds);
		}
	}else{
		sessionStorage.setItem("chooseProductIds",productId+",");
	}
	history.back();
}
/**
*弹出层修改规格
**/
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
			    //特省
			    var product=result.product;
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
		    	//价格
			    var salesPromotion = result.salesPromotion;
			    var arry  = new Array();
			    for ( var i in salesPromotion) {
			    	arry.push(i)
				}
			    sessionStorage.setItem("salesPromotion",JSON.stringify(arry));//设置阶梯价格
			    if(salesPromotion && salesPromotion!=null && salesPromotion!=''){
			    	dealSalesPromotion(salesPromotion);
			    }else{
			    	$(".jtj_box").hide();
			    }
			    
			    var rprice = 0;
			    var purchasePrice = result.purchasePrice;
		    	if(typeof(purchasePrice)!=undefined && purchasePrice !=null){
		    		rprice = result.purchasePrice
		    	}else{
		    		rprice =result.price-result.maxFunction;
		    	}
				var pricePage ='';
					pricePage +='<div class="price">';
					pricePage +='<div class="price_lt">';
					pricePage +='<i id="price_icon"><img src="'+jsBasePath+'static_resources/images/TogetherToBuy/neigou_icon.png"/></i><p><em>￥</em><span>'+(rprice).toFixed(2)+'</span>+内购券'+parseFloat(result.cost).toFixed(2)+'</p>';
					pricePage+='</div>'
					pricePage +='<div class="price_rt">电商价：<span>￥'+parseFloat(product.showPrice).toFixed(2)+'</span></div>';
					pricePage +='</div>';
				$(".con_tit").after(pricePage);
		    	//邮费
		    	var carriage=result.carriage
		    	var sendAddress = product.sendAddress;
		    	sendAddress = sendAddress.replace(" 市辖区","");
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
		    		for (var i = 0; i < service.length; i++) {
						html+='<li>'+service[i].title+'</li>';
					}
		    		$(".serve a ul").html(html);
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
		    	//var itemSkuId= new Array();
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
		    	number = result.minLimitNum;
		    	$("#number").val(number);
		    	autoChooseSalePromotion(number);
		    	$("#specDetailInfo").html(selected + '&nbsp;&nbsp;' + '<span id="amount">'+number+'件</span>');
		    	$("#specificationsId").val(result.specificationsId);
		    	//店铺
		    	var supplierShopVo=product.supplierShopVo;
		    	if (userSupplierId!=supplierId) {
		    		$(".shop_link a").html("商家："+supplierShopVo.shopname);
		    	}else{
		    		$(".shop_link a").html("商家：自家");
		    	}
		    	ajaxGetCollectProduct();
		    	//详情
		    	if(product.introduction==''){
		    		$(".product_details").hide();
		    	}else{
		    		$(".con_box_img").html(product.introduction);
		    	}
			}
		},
		error : function() {}
	});
}
/**
*弹出层修改规格
**/
function ajaxGetComments(){
	$.ajax({
		url : jsBasePath +'product/comments.json?productId='+productId+'&page=1&pageSize=1',
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html='';
			if (data.success) {
				var result = data.data;
				var list = result.comments.list;
				var ccv = result.commentRatings;
				var html="";
				if(list.length>0){
					//var good = ccv.praiseCount;
					var all = ccv.praiseCount+ccv.nomalCount+ccv.badCount;
					var p=parseFloat(100.00);
			    	$(".comment_top p").html('用户评价（'+all+'）');
			    	var username='';
					for(var i=0;i<list.length ;i++) {
						var shopLink = "javascript:void(0)";
						var head = "static_resources/images/ease_default_avatar.png";
						
						if(list[i].avatar && list[i].avatar!="") {
							head= list[i].avatar;
						}
						html += '<li>';
						html += '<div class="li_top">';
						html += '<dl>';
						html += '<dt>';
						html += '<img src='+head+'>';
                		html += '</dt>';
                		username = list[i].userNickName;
                		if(username && username!=''){
                			var userhead = username.substring(0,1);
                			var userend = username.substring(username.length-1,username.length);
                			username = userhead+"*****"+userend;
                		}
                		html += '<dd>'+username+'</dd>';
                		html += '</dl>';
                		html += '<div class="li_top_rt">'+list[i].creatTimeString+'</div>';
                		html += '</div>';
						if(list[i].star1<2){
							html += '<div class="li_top_lt star1"></div>';							
						} else if(list[i].star1<3) {
							html += '<div class="li_top_lt star2"></div>';					
						} else if(list[i].star1<4) {
							html += '<div class="li_top_lt star3"></div>';					
						} else if(list[i].star1<5) {
							html += '<div class="li_top_lt star4"></div>';					
						} else {
							html += '<div class="li_top_lt star5"></div>';
						}
						html += '<p>'+list[i].text +'</p>';
						var imageList = list[i].images;
						if(imageList && imageList!=null){
						html +='<div class="comment_img_box">';
						for (var j = 0; j < imageList.length; j++) {
							if(imageList[j]!=''){
								html+='<image src="'+imageList[j]+'">';
							}
						}
                        html +='</div>';
						}
						html += '</li>';
					}
					
			    	$(".comment_box ul").html(html);
			    	
				}else{
					$(".comment_box").hide();
				}			
			}
		},
		error : function() {}
	});
}
function toggleCollectProduct(){
	if(uid=="") return;
	if($("#collection").val()==1){
		$.ajax({
			url : jsBasePath +'collectProduct/delete.user?uid='+uid+'&productIdList='+productId,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				if (data.success) {
					$("#collection").val(0);
					$("#isCollection").hide();
					$("#noCollection").show();
				}
			},
			error : function() {}
		});
	} else {
		$.ajax({
			url : jsBasePath +'collectProduct/add.user?uid='+uid+'&productId='+productId,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				if (data.success) {
					  $("#collection").val(1);
					  $("#isCollection").show();
					  $("#noCollection").hide();
				}
			},
			error : function() {}
		});
	}
}
function ajaxGetCollectProduct(){
	if(uid=="") return;
	
	$.ajax({
		url : jsBasePath +'collectProduct/check.user?uid='+uid+'&productId='+productId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success && data.data) {
				//$(".slides_top .collect_btn img").attr("src",$(".slides_top .collect_btn img").attr("src").replace("collect_btn_img.png"));
				$("#isCollection").show();
				$("#noCollection").hide();
				$("#collection").val(1);
			}
		},
		error : function() {}
	});
}

function dealSalesPromotion(resultMap){
	if(resultMap){
		var html ='';
		for(var key in resultMap)  {
			html+='<li>';
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
   // ajaxSelectStock(selSku());
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
		sessionStorage.removeItem("salesPromotion");
	}
}
