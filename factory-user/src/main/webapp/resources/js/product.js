var pageSize = 10;//每页显示条数
var result = true;//提交验证前标识
var timeout = 6000;//错误信息展示时间（6秒）
var speId = "";
var pSendAddress;
var isUseEmp =false;//是否是员工专享价
var isNoRealPrice = false;//是否有内购价，用于内购价显示
var questionId = null;//调查问卷id
var isLimit = false;//是否限购
var limitMsg = "";//限购的错误信息
var saleKbn='';
$(document).ready(function() {
	
	initJs();
	commentsCounts();
	$("#goon").click(function(){
		$('.popup_bg').hide();
		$('.shopcar_popup').hide();	
	});
	$("#accounts").click(function(){
		var t = new Date().getTime();
		location.href="/cart/list?t="+t;
	});
	
	//增加数量
	$("#increase").click(function() {
		if (/^\d*[1-9]\d*$/.test($("#buyCount").val())) {
			$("#buyCount").val(parseInt($("#buyCount").val()) + 1);
		} else {
			$("#buyCount").val(1);
		}
		$("#buyCount").change();
	});
	

	$(".pro_referral ul li").each(function(i){
		var html=$(this).html();
		if("商品编号：1183261" == html) {
			$(this).html("商品编号：");
		}
	})
	
	// 减少数量
	$("#decrease").click(function() {
		if (/^\d*[1-9]\d*$/.test($("#buyCount").val())
				&& parseInt($("#buyCount").val()) > 1) {
			$("#buyCount").val(parseInt($("#buyCount").val()) - 1);
		} else {
			$("#buyCount").val(1);
		}
		$("#buyCount").change();
	});
	
	// 编辑数量
	$("#buyCount").bind("input change",function(event) {
		if (!(/^\d*[1-9]\d*$/.test($("#buyCount").val()))||$("#buyCount").val()<1) {
			$("#buyCount").css("border","1px solid #ff6161");
			changeBtn(true);
			return;
		}else{
			$("#buyCount").css("border","1px solid #e5e5e5");
			changeBtn(false);
			/*if(questionId && questionId.length > 0){
				$('.item_btn_02 a').attr("onclick","toQuestionnaire('"+questionId+"');");
				$('.item_btn_01 a').attr("onclick","toQuestionnaire('"+questionId+"');");
			}else{
				$('.item_btn_01 a').attr("onclick","addCart();");
				$('.item_btn_02 a').attr("onclick","addOrder();");
			}
			$(".item_btn_01").css("background-color","#2b8dff");
			$(".item_btn_02").css("background-color","#ff6161");*/
		}
		$.ajax({
			type: "POST",
			url: "/selectStock?partNumber="+$("#specificationId").val()+"&quantity="+$("#buyCount").val(),
			success: function(data){
				var strValue=" ";
				$(".c_item.selected").each(function(i){
					strValue+=$.trim($(this).text())+" ";
				})
				if(data.success){
					$("#selectValue").text(strValue);
					/*if(questionId && questionId.length > 0){
	    				$('.item_btn_02 a').attr("onclick","toQuestionnaire('"+questionId+"');");
	    				$('.item_btn_01 a').attr("onclick","toQuestionnaire('"+questionId+"');");
	    			}else{
	    				$('.item_btn_01 a').attr("onclick","addCart();");
	    				$('.item_btn_02 a').attr("onclick","addOrder();");
	    			}
					$(".item_btn_01").css("background-color","#2b8dff");
					$(".item_btn_02").css("background-color","#ff6161");*/
					changeBtn(false);
					dispCarriage(data.data);
					
				}else{
					$("#selectValue").text(strValue+data.msg);
					changeBtn(true);
			   		/*$('.item_btn_01 a').removeAttr("onclick");
	    			$('.item_btn_02 a').removeAttr("onclick");
	    			$('.item_btn_04 a').removeAttr("onclick");
	    			$(".item_btn_01").css("background-color","#959595");
	    			$(".item_btn_02").css("background-color","#959595");
	    			$(".item_btn_04").css("background-color","#959595");*/
				}
				//处理阶梯价
				dealLadder($("#specificationId").val(), $("#buyCount").val());
			}
		});
	});
	
	//用于登录
	/*var element = $("<div></div>").appendTo(document.body);
	element.load("/../common/loginpopup.jsp")*/
	
	//点击弹出换领规则
	//showruler()
});
function showruler(){
	$('.popup_bg').show();
	$('.hl_help_pop').show();	
}

function check(key){
	if(key){
		$("#"+key).siblings().removeClass('selected');
		$("#"+key).addClass('selected');
	}
	var strKey="";
	
		var strValue=" ";
		var strlist=new Array();
		$(".c_item.selected").each(function(i){
			strlist[i]=$(this).attr("id");
			strValue+=$.trim($(this).text())+" ";
		})
		strKey = sort(strlist).valueOf();
		$("#selectValue").text(strValue);
		var str = strKey.join("");
	
	if(str!=speId){
		speId = str;
		$.ajax({
		    dataType: 'json',
		    url:"/detail?itemsIds="+strKey + "&quantity="+$("#buyCount").val(),
		    cache:false,
		    success: function(data){
		    	if(data.success){
		    		if(data.data.price!=null) {
		    			$("#price").html(data.data.price.toFixed(2));
		    			$("#specificationId").val(data.data.id);
		    		}
		    		saleKbn = data.data.saleKbn;
		    		$(".picon_box").remove();
		    		//折扣标识
		    		if(data.data.maxFucoin!=null && data.data.maxFucoin>0 && data.data.isMarketable==1){
		    			$("#flj").remove();
		    			
		    			var str = "<li id='flj'><div style='width:510px;height:30px;'>" +
		    					"<span class='sm_metatit'>内购价：</span>" +
		    					"<label id='benifit-price-id' class='benifit-price'>￥"+(data.data.internalPurchasePrice).toFixed(2)+"</label>" +
		    					"<i id='benifit-add_q-id' class='add_q'>+内购券" +data.data.cost+ "</i>" +
		    					"<em id='benifit-em-id' style='font-style:normal;margin-left:20px;line-height:35px;float:left;'>" + ((data.data.internalPurchasePrice)*10/data.data.price).toFixed(1) + "折</em>" +
		    					"</div></li>";

	    				str +="<li><div style='width:300px;height:30px;'><span class='sm_metatit'>电商价：</span>"+
	    					"<strong>￥<span id='price'>"+data.data.price.toFixed(2)+"</span></strong>"+
	    					"</div></li>";
	    			
		    			$(".summay li:eq(0)").html(str);
		    			isNoRealPrice = false;
		    		} else if(data.data.cost!=null || data.data.price!=null) {
		    			$("#flj").remove();
			    		if(data.data.cost!=null && data.data.cost>0 && data.data.isMarketable==1) {
			    			var maxFuntion = data.data.price-data.data.internalPurchasePrice;
			    			if(maxFuntion > data.data.benefitSubsidy){
			    				maxFuntion = data.data.benefitSubsidy;
			    			}
			    			var str = "<li id='flj'><div style='width:510px;height:30px;'>" +
			    					"<span class='sm_metatit'>员工专享价：</span>" +
			    					"<label id='benifit-price-id' class='benifit-price'>￥"+(data.data.cost).toFixed(2)+"</label>" +
			    					//"<em id='benifit-em-id' style='font-style:normal;margin-left:20px;line-height:35px;float:left;'>" + ((data.data.internalPurchasePrice)*10/data.data.price).toFixed(1) + "折</em>" +
			    					"</div>" +"<div style='width:300px;height:30px;'><span class='sm_metatit'>电商价：</span>"+
			    					"<strong>￥<span id='price'>"+data.data.price.toFixed(2)+"</span></strong>"+
			    					"</div>"
			    					"</li>";
		    			
			    			$(".summay li:eq(0)").html(str);
			    			isUseEmp = true;
			    			isNoRealPrice = false;
			    		} else {
			    			isNoRealPrice = true;
		    				var str ="<li><div style='width:300px;height:30px;'><span class='sm_metatit'>电商价：</span>"+
		    					"<strong>￥<span id='price'>"+data.data.price.toFixed(2)+"</span></strong>"+
		    					"</div><p class='benifit-mark'>员工内购价格正在制定中...</p></li>";
		    				
			    			$(".summay li:eq(0)").html(str);
			    		}
		    		}
		    		

			    	
			    	if(saleKbn==1) {
			    		$(".zoompic").before('<div class="picon_box"><div class="picon"><img src="images/picon.png" /></div><p>原因：'+data.data.saleNote+'</p></div>')
			    	} else if(saleKbn==2) {
			    		$(".zoompic").before('<div class="picon_box"><div class="picon"><img src="images/picon_c.png" /></div><p>原因：'+data.data.saleNote+'</p></div>')
			    		
			    		var html = '<div class="buybtn item_btn_02 item_btn_02_2"><a href="javascript:void(0);" onclick="addOrder();">直接购买</a></div><div class="buybtn item_btn_04"><a href="javascript:void(0);" onclick="addMatch();">我想领</a></div><div class="hl_help"><a href="javascript:void(0);" onclick="showruler();" title="点击查看换领规则"><img src="images/hl_help_icon1.png" /></a></div>';
				    	$(".item_btn").html(html);
				    	
				    	var str = "<li id='flj'><div style='width:510px;height:30px;'>" +
	    					"<span class='sm_metatit'>换领价：</span>" +
	    					"<label id='benifit-price-id' class='benifit-price' ><span class='hlj'>"+(data.data.internalPurchasePrice).toFixed(2)+"</span><i class='hlb'><img src='images/huanlingbi_icon.png' /></i></label>" +
	    					"</div></li>";
				    	str +="<li><div style='width:300px;height:30px;'><span class='sm_metatit'>匹配中：</span>"+
							"<strong><span id='price' class='ppz'>"+data.data.orderCount  +"</span></strong>"+
							"</div></li>";
				    	$(".summay li:eq(0)").html(str);

		    			isUseEmp = true;	// 换领商品不使用阶梯价
				    	//设置左边框换领商品
				    	setSortLeft(data.data.productId);
			    	}else if(saleKbn==4) {
			    		$(".zoompic").before('<div class="picon_box"><div class="picon"><img src="images/picon_z.png" /></div><p>'+data.data.saleNote+'</p></div>')
			    	} else if(saleKbn==5) {
			    		$(".zoompic").before('<div class="picon_box"><div class="picon"><img src="images/picon_t.png" /></div><p>'+data.data.saleNote+'</p></div>')
			    	}
			    	if(saleKbn!=2){
			    		$(".item_btn_03").show();
			    	}
			    	
		    		if(data.data.stock<1){
		    			strValue+=" 库存不足";
		    			changeBtn(true);
		    		}else{
		    			changeBtn(false);
		    		}
		    		$('.c_num').text(data.data.stock);
		    		pSendAddress = $(".sm_price").html();
		    		if(data.data.carriage != null) {
		    			if (data.data.carriage==8888) {
		    				$(".sm_price").html("销售区域不包含默认地址:" + data.data.carriageDes);
						}else if(data.data.carriage==9999){
							$(".sm_price").html("超过限购数量，不能再买啦");
			    			changeBtn(true);
						}else{
							dispCarriage(data.data.carriage + "####" + data.data.carriageDes  + "####" +  data.data.freeDes );
						}
		    		}
		    		
		    		if(data.data.salesPromotion){
		    			dealSalesPromotionHtml(data.data.salesPromotion);
		    		}else{
		    			$("#productSalesPromotion").html("");
		    		}
		    		dealMinLimitNum(data.data.minLimitNum);
		    		isLimit = data.data.limit;
		    		limitMsg = data.data.limitMsg;//限购的错误信息
		    	}else {
		    		$("#selectValue").text(data.msg);
		    		
	    			var dprice=$("#price").html();

	    			$("#flj").remove();

    				var str ="<li><div style='width:300px;height:30px;'><span class='sm_metatit'>电商价：</span>"+
    					"<strong>￥<span id='price'>"+dprice+"</span></strong>"+
    					"</div><p class='benifit-mark'>员工内购价格正在制定中...</p></li>";
    				isNoRealPrice = true;
	    			$(".summay li:eq(0)").html(str);
		    	}
		    	
		    	if($("#specificationId").val() != ""){
			    	$("#thumbnail li").removeClass('current');
			    	$("#thumbnail li[class!='"+$("#specificationId").val()+"']").hide();
			    	$("#thumbnail li[class="+$("#specificationId").val()+"]").show();
			    	$("."+$("#specificationId").val()).each(function(i){
			    		if(i==0){
			    			$(this).addClass(" current");
			    		}
			    	})
			    	if(typeof($("."+$("#specificationId").val()+".current img").attr("src")).length=="number"){
			    		$(".zoompic img").attr("src",$("."+$("#specificationId").val()+".current img").attr("src"));
			    		$(".zoompic img").attr("alt",$("."+$("#specificationId").val()+".current img").attr("src"));
			    	}
		    	}
		    	//处理阶梯价
				dealLadder($("#specificationId").val(), $("#buyCount").val());
		    },
		 	error:function(){
		 		var p=window.location.href.indexOf('?');
		 		if(p>-1) {
			 		location.href=window.location.href.substring(0,p);
		 		}
			}
		});
		
	}
}
function GetQueryString(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return decodeURI(r[2]); return null;
}




function selected(){
	var skuId=GetQueryString("skuId");
	var strValue="";
	var strKey="";
	
	var strlist=new Array();
	$(".c_item.selected").each(function(i){
		strlist[i]=$(this).attr("id");
	})
	strKey = sort(strlist).valueOf();
	if(skuId && skuId!=undefined &&skuId!=null){		
		$.ajax({
		    dataType: 'json',
		    url:"/detail?skuId="+skuId + "&quantity="+$("#buyCount").val() +"&itemsIds="+strKey,
		    success: function(data){
		    	if(data.success){
		    		if(data.data.itemids){
		    			var words = data.data.itemids.split(',');
		    			if(words){
		    				for(var i = 0;i < words.length;i++){
		    					$("#"+words[i]).siblings().removeClass('selected');
		    					$("#"+words[i]).addClass('selected');
		    				}
		    			}
		    		}
		    		if(data.data.price!=null) {
		    			$("#price").html(data.data.price.toFixed(2));
		    			$("#specificationId").val(data.data.id);
		    		}
		    		saleKbn = data.data.saleKbn;
		    		$(".picon_box").remove();
		    		//折扣标识
		    		if(data.data.maxFucoin!=null && data.data.maxFucoin>0 && data.data.isMarketable==1){
		    			$("#flj").remove();
		    			
		    			var str = "<li id='flj'><div style='width:510px;height:30px;'>" +
		    					"<span class='sm_metatit'>内购价：</span>" +
		    					"<label id='benifit-price-id' class='benifit-price'>￥"+(data.data.internalPurchasePrice).toFixed(2)+"</label>" +
		    					"<i id='benifit-add_q-id' class='add_q'>+内购券" +data.data.cost+ "</i>" +
		    					"<em id='benifit-em-id' style='font-style:normal;margin-left:20px;line-height:35px;float:left;'>" + ((data.data.price-data.data.maxFucoin)*10/data.data.price).toFixed(1) + "折</em>" +
		    					"</div></li>";

	    				str +="<li><div style='width:300px;height:30px;'><span class='sm_metatit'>电商价：</span>"+
	    					"<strong>￥<span id='price'>"+data.data.price.toFixed(2)+"</span></strong>"+
	    					"</div></li>";
	    			
		    			$(".summay li:eq(0)").html(str);
		    			isNoRealPrice = false;
		    		} else if(data.data.cost!=null || data.data.price!=null) {
		    			$("#flj").remove();
			    		if(data.data.cost!=null && data.data.cost>0 && data.data.isMarketable==1) {
			    			var maxFuntion = data.data.price-data.data.internalPurchasePrice;
			    			if(maxFuntion > data.data.benefitSubsidy){
			    				maxFuntion = data.data.benefitSubsidy;
			    			}
			    			var str = "<li id='flj'><div style='width:510px;height:30px;'>" +
	    							 "<span class='sm_metatit'>员工专享价：</span>" +
	    							 "<label id='benifit-price-id' class='benifit-price'>￥"+(data.data.cost).toFixed(2)+"</label>" +
	    							 //"<em id='benifit-em-id' style='font-style:normal;margin-left:20px;line-height:35px;float:left;'>" + ((data.data.internalPurchasePrice)*10/data.data.price).toFixed(1) + "折</em>" +
	    							 "</div>" +"<div style='width:300px;height:30px;'><span class='sm_metatit'>电商价：</span>"+
	    							 "<strong>￥<span id='price'>"+data.data.price.toFixed(2)+"</span></strong>"+
	    							 "</div>"
	    							 "</li>";
		    			
			    			$(".summay li:eq(0)").html(str);
			    			isUseEmp = true;
			    			isNoRealPrice = false;
			    		} else {

		    				var str ="<li><div style='width:300px;height:30px;'><span class='sm_metatit'>电商价：</span>"+
		    					"<strong>￥<span id='price'>"+data.data.price.toFixed(2)+"</span></strong>"+
		    					"</div><p class='benifit-mark'>员工内购价格正在制定中...</p></li>";
		    				isNoRealPrice = true;
			    			$(".summay li:eq(0)").html(str);
			    		}
		    		}
		    		$('.c_num').text(data.data.stock);
		    		pSendAddress = $(".sm_price").html();
		    		if(data.data.carriage != null) {
		    			if (data.data.carriage==8888) {
		    				$(".sm_price").html("销售区域不包含默认地址:" + data.data.carriageDes);
						}else if(data.data.carriage==9999){
							$(".sm_price").html("超过限购数量，不能再买啦");
							/*$('.item_btn_01 a').removeAttr("onclick");
			    			$('.item_btn_02 a').removeAttr("onclick");
			    			$(".item_btn_01").css("background-color","#959595");
			    			$(".item_btn_02").css("background-color","#959595");*/
							changBtn(true);
						}else{
							dispCarriage(data.data.carriage + "####" + data.data.carriageDes  + "####" +  data.data.freeDes );
						}
		    		}
		    		if(data.data.salesPromotion){
		    			dealSalesPromotionHtml(data.data.salesPromotion);
		    		}else{
		    			$("#productSalesPromotion").html("");
		    		}
		    		//处理起购数量
		    		dealMinLimitNum(data.data.minLimitNum);
		    		isLimit = data.data.limit;
		    		limitMsg = data.data.limitMsg;//限购的错误信息

			    	
			    	if(saleKbn==1) {
			    		$(".zoompic").before('<div class="picon_box"><div class="picon"><img src="images/picon.png" /></div><p>原因：'+data.data.saleNote+'</p></div>')
			    		$(".item_btn_04").hide();
			    	} else if(saleKbn==2) {
			    		$(".zoompic").before('<div class="picon_box"><div class="picon"><img src="images/picon_c.png" /></div><p>原因：'+data.data.saleNote+'</p></div>')
			    		
			    		/*$(".item_btn_01").hide();
			    		$(".item_btn_03").hide();*/
			    		var html = '<div class="buybtn item_btn_02 item_btn_02_2"><a href="javascript:void(0);" onclick="addOrder();">直接购买</a></div><div class="buybtn item_btn_04"><a href="javascript:void(0);" onclick="addMatch();">我想领</a></div><div class="hl_help"><a href="javascript:void(0);" onclick="showruler();" title="点击查看换领规则"><img src="images/hl_help_icon1.png" /></a></div>'
			    		$(".item_btn").html(html);
			    		
			    		var str = "<li id='flj'><div style='width:510px;height:30px;'>" +
							"<span class='sm_metatit'>换领价：</span>" +
							"<label id='benifit-price-id' class='benifit-price' ><span class='hlj'>"+(data.data.internalPurchasePrice).toFixed(2)+"</span><i class='hlb'><img src='images/huanlingbi_icon.png' /></i></label>" +
							"</div></li>";

			    		str +="<li><div style='width:300px;height:30px;'><span class='sm_metatit'>匹配中：</span>"+
							"<strong><span id='price' class='ppz'>"+data.data.orderCount  +"</span></strong>"+
							"</div></li>";
			    		$(".summay li:eq(0)").html(str);
			    		//设置左边框换领商品
			    		setSortLeft(data.data.productId);
			    		
		    			isUseEmp = true;	// 换领商品不使用阶梯价
			    	}else if(saleKbn==4) {
			    		$(".zoompic").before('<div class="picon_box"><div class="picon"><img src="images/picon_z.png" /></div><p>'+data.data.saleNote+'</p></div>')
			    	} else if(saleKbn==5) {
			    		$(".zoompic").before('<div class="picon_box"><div class="picon"><img src="images/picon_t.png" /></div><p>'+data.data.saleNote+'</p></div>')
			    	}
			    	if(saleKbn!=2){
			    		$(".item_btn_03").show();
			    	}

		    		if(data.data.stock<1){
		    			strValue+=" 库存不足";
		    			$("#selectValue").text(strValue+" "+$("#buyCount").val());
		    			changeBtn(true);
		    		}else{
		    			changeBtn(false);
		    		}
		    	} else {
		    		$("#selectValue").text(data.msg);
		    		
	    			var dprice=$("#price").html();

	    			$("#flj").remove();

    				var str ="<li><div style='width:300px;height:30px;'><span class='sm_metatit'>电商价：</span>"+
    					"<strong>￥<span id='price'>"+dprice+"</span></strong>"+
    					"</div><p class='benifit-mark'>员工内购价格正在制定中...</p></li>";
    				
	    			$(".summay li:eq(0)").html(str);
	    			isNoRealPrice = true;
		    	}
		    	
		    	if($("#specificationId").val() != ""){
			    	$("#thumbnail li").removeClass('current');
			    	$("#thumbnail li[class!='"+$("#specificationId").val()+"']").hide();
			    	$("#thumbnail li[class="+$("#specificationId").val()+"]").show();
			    	$("."+$("#specificationId").val()).each(function(i){
			    		if(i==0){
			    			$(this).addClass(" current");
			    		}
			    	})
			    	if(typeof($("."+$("#specificationId").val()+".current img").attr("src")).length=="number"){
			    		$(".zoompic img").attr("src",$("."+$("#specificationId").val()+".current img").attr("src"));
			    		$(".zoompic img").attr("alt",$("."+$("#specificationId").val()+".current img").attr("src"));
			    	}
		    	
		    	}
		    },
		 	error:function(){
		 		var p=window.location.href.indexOf('?');
		 		if(p>-1) {
			 		location.href=window.location.href.substring(0,p);
		 		}	   
			}
		});
		
	}else{
		check();
	}
}

function initJs(){
	selected();
//	check();
	
	//判断是否已关注该店铺
	$.ajax({
		type: "POST",
		url: "/selectCollectionShop?shopId="+$("#shopid").val(),
		success: function(data){
			if(data){
				$(".store_btn02 a").html("已收藏店铺");
			}else{
				$(".store_btn02").attr("onclick","collectionShop()");
			}
		}
	});
	
	//判断是否已关注该商品
	$.ajax({
		type: "POST",
		url: "/selectCollectionProduct?productId="+$("#productId").val(),
		success: function(data){
			if(data){
				$(".p_ad strong").html("已关注");
			}else{
				$(".p_ad strong").attr("onclick","collectionProduct()");
			}
		}
	});

	
	$(".item_company").hide();
	//店铺的评分
	$.ajax({
		type: "POST",
		url: "/shopDetail?shopId="+$("#supplierId").attr("data"),
		success: function(data){
			if(data.success){
				//console.log(data.data.shopDescription)
				$("#shopDescription").html(new Number(data.data.shopDescription).toFixed(2));
				$("#shopService").html(new Number(data.data.shopService).toFixed(2));
				$("#deliverySpeed").html(new Number(data.data.deliverySpeed).toFixed(2));

				if(data.data.shopName !=null && data.data.shopName!='') {
					$(".p_item_ext h2").html(data.data.shopName);
				}
				//客服电话
				var html="<p>客服电话：";
				if(data.data.tel2 == null) {
					html+=data.data.tel1 +"</p>";
				} else {
					html+=data.data.tel2 +"</p>";
				}
				
				html="";  //暂不显示客服电话
				if(data.data.qq != null) {
					html+="<p>客服ＱＱ：";
					html+='<img  style="CURSOR: pointer" onclick="javascript:window.open(\'http://wpa.qq.com/msgrd?v=3&uin='+data.data.qq+'&site=qq&menu=yes\', \'_blank\', \'height=502, width=644,toolbar=no,scrollbars=no,menubar=no,status=no\');"  border="0" SRC=http://wpa.qq.com/pa?p=1:'+data.data.qq+':10 alt="联系客服">';
					html+="</p>";
				}
				$(".item_company").html(html);
				$(".item_company").show();
				
				var free = data.data.free;
				if(free){
					free=$.trim(free);
					if("商家全场："==free) {
						free="";
					}
				}
				if(free && free!="") {
					var $ad = $(".p_ad span");
					html = $ad.html();
					if(html.indexOf(free)==-1) {
						if(html=="") {
							$ad.html(free);							
						} else {
							$ad.html(html + "<br />" + free);
						}
					}
				}
			}
		}
	});
	
}

/**
 * 关注该商品
 */
function collectionProduct(){
	//关注商品
	$.ajax({
		type: "POST",
		url:"/collectProduct?productId="+$("#productId").val(),
		success: function(ret){
			if(ret.success){
			   	$(".p_ad strong").html("已关注");
			}else{
				wode.showLoginBox(collectionProduct);
			}
		}
	});
}

/**
 * 加载店铺相关信息
 */
function collectionShop(){
	//收藏店铺
	$.ajax({
		type: "POST",
		url: "/collectionShop?shopId="+$("#shopid").val(),
		success: function(ret){
			if(ret.success){
				$(".store_btn02 a").html("已收藏店铺");
			}else{
				wode.showLoginBox(collectionShop);
			}
		}
	});
}



/**
 * 评论总数
 */
function commentsCounts(){
	$.ajax({
		type: "POST",
	    dataType: 'json',
	    url:"/commentsMsg?productId="+$("#productId").val(),
	    success: function(data){
	    	if(data.success){
	    		var commentsCountVo = data.data;
	    		if(commentsCountVo!=null){
		    		var comentsCount = parseInt(commentsCountVo.badCount)+parseInt(commentsCountVo.nomalCount)+parseInt(commentsCountVo.praiseCount);
		    		$("#commentsCount2").html(comentsCount);//总评数
		    		$("#commentsCount3").html(comentsCount);//总评数
					$("#badCount").html(commentsCountVo.badCount);//差评数
					$("#nomalCount").html(commentsCountVo.nomalCount);//中评数
					$("#praiseCount").html(commentsCountVo.praiseCount);//好评数
					$("#goodsRatings").html(commentsCountVo.goodsRatings.toFixed(1));//商品评分
					$(".stage_rating").css("width",commentsCountVo.goodsRatings.toFixed(1)*15+"px");
					$(".w_1").css("width",commentsCountVo.goodsRatings.toFixed(1)*15+"px");//星标显示程度
					$("#serviceRatings").html(commentsCountVo.serviceRatings.toFixed(1));//服务评分
					$(".w_2").css("width",commentsCountVo.serviceRatings.toFixed(1)*15+"px");//星标显示程度
					$("#logisticsRatings").html(commentsCountVo.logisticsRatings.toFixed(1));//物流评分
					$(".w_3").css("width",commentsCountVo.logisticsRatings.toFixed(1)*15+"px");//星标显示程度
					if(comentsCount>0){
						$("#praiseDegree1").html((commentsCountVo.praiseCount/comentsCount*100).toFixed(0)+"%");//好评度
						$("#praiseDegree2").html((commentsCountVo.praiseCount/comentsCount*100).toFixed(0));//好评度
						$(".p_1").css("width",(commentsCountVo.praiseCount/comentsCount*100).toFixed(0)+"%");//效果显示程度
						$("#nomalDegree").html((commentsCountVo.nomalCount/comentsCount*100).toFixed(0));//中平度
						$(".p_2").css("width",(commentsCountVo.nomalCount/comentsCount*100).toFixed(0)+"%");//效果显示程度
						$("#badDegree").html((commentsCountVo.badCount/comentsCount*100).toFixed(0));//差评度
						$(".p_3").css("width",(commentsCountVo.badCount/comentsCount*100)+"%");//效果显示程度
					}else{
						$("#praiseDegree1").html("100%");//好评度
						$("#praiseDegree2").html(100);//好评度
						$(".p_1").css("width","100%");//效果显示程度
						$("#nomalDegree").html(100);//中平度
						$(".p_2").css("width","100%");//效果显示程度
						$("#badDegree").html(100);//差评度
						$(".p_3").css("width","100%");//效果显示程度
					}
					$(".g_icon_2").attr("class","g_icon_1");//商品评分
					comment(1,0,"");
	    		}else{
		    		$("#commemtCount").html(0);
					$("#badCount").html(0);
					$("#nomalCount").html(0);
					$("#praiseCount").html(0);
					$(".stage_rating").css("width",5*15+"px");
					$(".w_1").css("width",5*15+"px");//星标显示程度
					$(".w_2").css("width",5*15+"px");//星标显示程度
					$(".w_3").css("width",5*15+"px");//星标显示程度
					$(".p_1").css("width",$("#praiseDegree2").text()+"%");//效果显示程度
					$(".p_2").css("width",$("#nomalDegree").text()+"%");//效果显示程度
					$(".p_3").css("width",$("#badDegree").text()+"%");//效果显示程度
	    		}
	    	}
	    },
	 	error:function(){
		    
		}
	});
}
/**
 * 评论List
 */
function comment(nowPage,index,check){
	$("#commentsList").remove();
	$('.p_inner ul li').each(function(i){
		if(i==index){
			$('.p_inner ul li').eq(i).addClass('curr');				
		}else{
			$('.p_inner ul li').eq(i).removeClass('curr');
		}
	})
	$.ajax({
	    dataType: 'json',
	    url:"/comments/"+nowPage+"/"+pageSize+".json?productId="+$("#productId").val()+"&index="+index,
	    success: function(data){
	    	if(data.success){
				var list = "<div class='appraise_cont' id='commentsList'><div class='appraise_list'><ul>";
				var count = data.data.list.length;
				for (var i = 0; i < count; i++) {
					var item = data.data.list[i];
					var str = "";
					var jsonObj = eval('(' + item.attributeJson + ')');
					for(var p in jsonObj){//遍历json对象的每个key/value对,p为key
						str+="<p>"+p+"："+jsonObj[p]+"</p>"
			    	}
					var showUserName= item.userNickName;
					var shopLink = "#";
					var head = "images/m_head.jpg";
					var images = item.images;
					if(item.shopLink && item.shopLink!="" &&item.shopLink!="null") {
						shopLink= "/shop/" + item.shopLink;
					}
					if(item.avatar && item.avatar!="") {
						head= item.avatar;
					}
					list += "<li>" +
						    	"<div class='m_info'>" +
						    		"<div class='m_rm'>" +
						    			"<div class='m_rm_img'>" +
							    			"<a href='"+shopLink+"'><img src='"+head+"' width='28' height='28' alt='m_head'></a>" +
							    			"<i class='vip_"+item.userLevel+"'></i>" +
							    		"</div>" +
							    		"<span>"+showUserName+"</span>" +
							    	"</div>" +
							    	"<div class='m_co'>"+str+"</div>"+
							    	"<div class='m_tm'>"+new Date(item.creatTime).format("yyyy-MM-dd hh:mm:ss")+"</div>" +
							    "</div>"
							    list +="<div class='m_cont'>"+item.text+"</div>"
							    if(images!=null){
									for (var a = 0; a < images.length; a++) {
										if(images[a]!=null&&images[a]!=""){
											list+="<img src='"+images[a]+"' width='50' height='50'>";
										}
									}
								}
					list +="</li>"
							    
				}
				list+="</ul></div>";
				var page="<div class='page'>";
				var pageNum = nowPage;//当前页
				if(index==0){
				}else if(index==1){
					var commentsCount = parseInt($("#praiseCount").html());//好评总数
				}else if(index==2){
					var commentsCount = parseInt($("#nomalCount").html());//中评总数
				}else if(index==3){
					var commentsCount = parseInt($("#badCount").html());//差评总数
				}
				var pageCount = Math.ceil(commentsCount/pageSize);//总页数
				var classStyle = "class='page_curr'";
				if(commentsCount<pageSize || commentsCount==pageSize){
					page+="<a href='javascript:;' onclick='comment(1,"+index+",'')' class='page_curr'>1</a></div></div></div>"
				}
				if(commentsCount>pageSize){
					if(pageNum!=1){
						page+="<a href='javascript:;' onclick='comment("+(1)+","+index+",check)'>首页</a>";
						page+="<a href='javascript:;' onclick='comment("+(pageNum-1)+","+index+",check)'>上一页</a>";
					}
					for(var i=1;i<pageCount+1;i++){
						page+="<a href='javascript:;' onclick='comment("+i+","+index+",check)' ";
						if(pageNum == i){
							page+=classStyle;
						}
						page+=">"+i+"</a>";
					}
					if(pageNum!=pageCount){
						page+="<a href='javascript:void(0);' onclick='comment("+(pageNum+1)+","+index+",check)'>下一页</a>";
						page+="<a href='javascript:void(0);' onclick='comment("+(pageCount)+","+index+",check)'>末页</a></div></div></div>";
					}
				}
				list+=page;
				$("#productComents").append(list);
				/*if(check!=null)
					$("html,body").animate({scrollTop: $('.pro_tab_trigger').offset().top}, 500);*/
	    	}
	    },
	 	error:function(){
		    
		}
	});
}

//评论
function comments(){
	$('.pro_panel').css({display:'none'});
	$('.pro_tab_trigger ul li').removeClass('current');
	$('.pro_panel').eq(3).css({display:'block'});
	$('.pro_tab_trigger ul li').eq(3).addClass('current');
	$("html,body").animate({scrollTop: $('.pro_tab_trigger').offset().top}, 500);
}

//时间格式转换为yyyy-MM-dd hh:mm:ss
Date.prototype.format = function(format)
{
var o = {
            "M+" : this.getMonth()+1, //month
            "d+" : this.getDate(), //day
            "h+" : this.getHours(), //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3), //quarter
            "S" : this.getMilliseconds() //millisecond
        }
    if(/(y+)/.test(format))
    	format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4 - RegExp.$1.length));
    for(var k in o)
    	if(new RegExp("("+ k +")").test(format))
    		format = format.replace(RegExp.$1,RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length));
    return format;
}
/**
 * 获取链接参数
 * @param name
 * @returns
 */
function GetQueryString(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return decodeURI(r[2]); return null;
}

/**
 * 添加购物车
 */
function addCart(){
	/*$('.item_btn_01 a').removeAttr("onclick");
	$('.item_btn_02 a').removeAttr("onclick");
	$(".item_btn_01").css("background-color","#959595");
	$(".item_btn_02").css("background-color","#959595");*/

	var pageKey = GetQueryString("pageKey");
	if(pageKey==null || typeof(pageKey)=="undefined" ){
		pageKey="";
	}
	$.ajax({
		type: 'post',
	    dataType: 'json',
	    url:"/cart/add?partNumber="+$("#specificationId").val()+"&quantity="+$("#buyCount").val()+"&pageKey="+pageKey+"&fromType=pc",
	    success: function(data){
	    	if(data.success){
	    		var obj = data.data;
	    		var productList = new Array(); 
	    		productList = obj.perhapsNeedProduct;
	    		var liStr = "";
	    		for(var i=0;i<productList.length;i++){
	    			liStr+="<li>" +
	    				"<div class='shop_pho'>" +
	    					"<a href=/"+productList[i].id+".html>" +
	    						"<img src='"+productList[i].image+"' width='80' height='80' alt='"+productList[i].image+"'>" +
	    					"</a>" +
	    				"</div>" +
	    				"<div class='nm'>" +
    						"<a title='"+productList[i].name+"' href='/"+productList[i].id+".html'>"+productList[i].name.substr(0,16)+"</a>" +
    					"</div>" +
	    				"<div class='price'>¥&nbsp;"+productList[i].minprice+"</div>" +
	    				"</li>";
	    		}
	    		$('.shoplist ul li').remove();
	    		$('.shoplist ul').append(liStr);
	    		$('.popup_bg').show();
	    		$('.shopcar_popup').show();	
	    		$('.close_btn').click(function(){
		    		$('.popup_bg').hide();
		    		$('.shopcar_popup').hide();	
	    		})
	    		/*$('.item_btn_01 a').attr("onclick","addCart();");
		    	$('.item_btn_02 a').attr("onclick","addOrder();");
		    	$(".item_btn_01").css("background-color","#2b8dff");
		    	$(".item_btn_02").css("background-color","#ff6161");*/
	    		changeBtn(false);
	    		wode.getCartNum();//重新获取购物车信息
	    	}else{
	    		var strValue=" ";
				$(".c_item.selected").each(function(i){
					strValue+=$.trim($(this).text())+" ";
				})
				$("#selectValue").text(strValue+data.msg);
				/*$('.item_btn_01 a').removeAttr("onclick");
		    	$('.item_btn_02 a').removeAttr("onclick");
		    	$(".item_btn_01").css("background-color","#959595");
		    	$(".item_btn_02").css("background-color","#959595");*/
		    	changeBtn(true);
	    	}
	    },
	 	error:function(){
	 		$("#selectValue").text("未知错误，请联系卖家");
	 		//wode.showBox("错误","未知错误，请联系卖家",{hideBtn:true});
		}
	});
}

/**
 * 直接购买
 */
function addOrder(){

	var pageKey = GetQueryString("pageKey");
	if(pageKey==null || typeof(pageKey)=="undefined" ){
		pageKey="";
	}
	if(wode.hasLogin==1){
		$.ajax({
			type: "POST",
			url: "/cart/newAdd?partNumber="+$("#specificationId").val()+"&quantity="+$("#buyCount").val()+"&pageKey="+pageKey+"&fromType=pc",
			success: function(data){
				if(data.success){
					var partNumbers =$("#supplierId").val()+"_"+$("#specificationId").val()+","
					location.href="/order/info?partNumbers="+partNumbers;
				}else{
					var strValue=" ";
					$(".c_item.selected").each(function(i){
						strValue+=$.trim($(this).text())+" ";
					})
					$("#selectValue").text(strValue+data.msg);
					/*$('.item_btn_01 a').removeAttr("onclick");
			    	$('.item_btn_02 a').removeAttr("onclick");
			    	$(".item_btn_01").css("background-color","#959595");
			    	$(".item_btn_02").css("background-color","#959595");*/
			    	changeBtn(true);
				}
			}
		});
	}else{
		wode.showLoginBox(addOrder);
	}
}
/**
 * 我想领
 * @returns
 */
function addMatch(){
	$('.popup_bg').show();
	$('.TogetherToBuy_btn_ewm').show();	
	$('.TogetherToBuy_btn_ewm .p1').html("扫码下载app");
	$('.TogetherToBuy_btn_ewm .p2').html("100+大牌福利等你换");
	/*var pageKey = GetQueryString("pageKey");
	if(pageKey==null || typeof(pageKey)!="undefined" ){
		pageKey="";
	}
	if(wode.hasLogin==1){
		$.ajax({
			type: "POST",
			url: "/cart/newAdd?partNumber="+$("#specificationId").val()+"&quantity="+$("#buyCount").val()+"&pageKey="+pageKey+"&fromType=pc",
			success: function(data){
				if(data.success){
					var partNumbers =$("#supplierId").val()+"_"+$("#specificationId").val()+","
					location.href="/exchangeOrder/info?partNumbers="+partNumbers;
				}else{
					var strValue=" ";
					$(".c_item.selected").each(function(i){
						strValue+=$.trim($(this).text())+" ";
					})
					$("#selectValue").text(strValue+data.msg);
					$('.item_btn_04 a').removeAttr("onclick");
			    	$('.item_btn_02 a').removeAttr("onclick");
			    	$(".item_btn_04").css("background-color","#959595");
			    	$(".item_btn_02").css("background-color","#959595");
					changeBtn(true);
				}
			}
		});
	}else{
		wode.showLoginBox(addOrder);
	}*/
}

function toQuestionnaire(qId) {
	if(isLimit == true || isLimit == "true"){
		var strValue=" ";
		$(".c_item.selected").each(function(i){
			strValue+=$.trim($(this).text())+" ";
		})
		$("#selectValue").text(strValue+limitMsg);
		/*$('.item_btn_01 a').removeAttr("onclick");
    	$('.item_btn_02 a').removeAttr("onclick");
    	$(".item_btn_01").css("background-color","#959595");
    	$(".item_btn_02").css("background-color","#959595");*/
		changeBtn(true);
	}else{
		var strKey="";
		var strlist=new Array();
		$(".c_item.selected").each(function(i){
			strlist[i]=$(this).attr("id");
		})
		strKey = sort(strlist).valueOf();
		var pageKey = GetQueryString("pageKey");
		if(pageKey==null || typeof(pageKey)=="undefined" ){
			pageKey="";
		}
		$.ajax({
			type: "POST",
			url: "/cart/newAdd?partNumber="+$("#specificationId").val()+"&quantity="+$("#buyCount").val()+"&pageKey="+pageKey+"&fromType=pc",
			success: function(data){
				window.location = "/member/questionnaire"+qId +"?quantity=" +$("#buyCount").val() +"&itemIds=" + strKey;
			}
		});
	}
}

/**
 * 排序
 */
function sort(arr){
    return quickSort(arr,0,arr.length-1);
    function quickSort(arr,l,r){            
        if(l<r){        
            var mid=arr[parseInt((l+r)/2)],i=l-1,j=r+1;         
            while(true){
                while(arr[++i]<mid);
                while(arr[--j]>mid);             
                if(i>=j)break;
                var temp=arr[i];
                arr[i]=arr[j];
                arr[j]=temp;
            }       
            quickSort(arr,l,i-1);
            quickSort(arr,j+1,r);
        }
        return arr;
    }
}

//显示运费
function dispCarriage(str) {
	var ary=str.split('####');
	
	var disp = $(".sm_price").html();
	var start = disp.indexOf("(");
	if(start>-1) {
		var end = disp.indexOf("发货");
		if(end>-1) {
			disp=disp.substring(start,end+2);
		}
	}else{
		var start = pSendAddress.indexOf("(");
		var end = pSendAddress.indexOf("发货");
		if(end>-1) {
			pSendAddress=pSendAddress.substring(start,end+2);
		}
		disp =pSendAddress.substring(start,end+2)
	}
	if(ary.length>1) {
		
		if(ary[0]==8888){//
			disp="销售区域不包含默认地址:"+ary[1]
		$(".sm_price").html(disp);
		}else if(ary[0]==9999){
			disp="超过限购数量，不能再买啦";
		$(".sm_price").html(disp);
		}else{
			disp +="至"+ary[1];
			disp +=")";
			$(".sm_price").html("￥&nbsp;"+ary[0] +disp);
		}
	}
}
/**
*详情页页面效果
*/

//全部商品分类下拉菜单
$(function(){
		
	$('.menu_list').mouseover(function(){
		$(this).show();	
		$('.allproduct_menu p').css('background','url(images/factory_img.png) no-repeat 180px -74px');
	})
	$('.menu_list').mouseout(function(){
		$(this).hide();	
		$('.allproduct_menu p').css('background','url(images/factory_img.png) no-repeat 180px -38px');
	})
	
	
})

//商品详情-商品介绍切换
$(function(){
	$('.pro_tab_trigger ul li').click(function(){
		var index=$('.pro_tab_trigger ul li').index(this);
       	$('.pro_panel').each(function(i){
			if(i==index){
				$('.pro_panel').eq(i).css({display:'block'});
				$('.pro_tab_trigger ul li').eq(i).addClass('current');				
			}else{
				$('.pro_panel').eq(i).css({display:'none'});
				$('.pro_tab_trigger ul li').eq(i).removeClass('current');
			}
   		})
  	})
  	
//商品详情页-图片切换带缩略图片滚动切换

	//点击小图切换大图
	$("#thumbnail li img").mouseover(function(){
		if(!$(this).parents("li").hasClass("current")){
			$(".zoompic img").attr({ "src": $(this).attr("src") });
			$("#thumbnail li.current").removeClass("current");
			$(this).parents("li").addClass("current");
		}
		return false;
	});
		
	//小图片左右滚动
	var $slider = $('.slider ul');
	var $slider_child_l = $('.slider ul li[dispaly=list-item]').length;
	var $slider_width = $('.slider ul li[dispaly=list-item]').width();
	$slider.width($slider_child_l * $slider_width);
	
	var slider_count = 0;
	
	if ($slider_child_l < 5) {
		$('#btn-right').css({cursor: 'auto'});
		$('#btn-right').removeClass("dasabled");
	}
	
	$('#btn-right').click(function() {
		if ($slider_child_l < 5 || slider_count >= $slider_child_l - 5) {
			return false;
		}
		
		slider_count++;
		$slider.animate({left: '-=' + $slider_width + 'px'}, 'fast');
		slider_pic();
	});
	
	$('#btn-left').click(function() {
		if (slider_count <= 0) {
			return false;
		}
		slider_count--;
		$slider.animate({left: '+=' + $slider_width + 'px'}, 'fast');
		slider_pic();
	});
	
	function slider_pic() {
		if (slider_count >= $slider_child_l - 5) {
			$('#btn-right').css({cursor: 'auto'});
			$('#btn-right').addClass("dasabled");
		}
		else if (slider_count > 0 && slider_count <= $slider_child_l - 5) {
			$('#btn-left').css({cursor: 'pointer'});
			$('#btn-left').removeClass("dasabled");
			$('#btn-right').css({cursor: 'pointer'});
			$('#btn-right').removeClass("dasabled");
		}
		else if (slider_count <= 0) {
			$('#btn-left').css({cursor: 'auto'});
			$('#btn-left').addClass("dasabled");
		}
	}
});

//处理阶梯价
function dealLadder(id,num){
	if(isUseEmp==false || isUseEmp =="false"){
		if(id=="") return;
		$.ajax({
			type: "POST",
			url: "/getLadder?partNumber="+id+"&quantity="+num,
			success: function(data){
				if(data){
					if(isNoRealPrice==false || isNoRealPrice =="false"){
						$("#benifit-price-id").html("￥"+data.price);
						$("#benifit-add_q-id").html("+内购券 "+data.maxFucoin);
						$("#benifit-em-id").html((data.price*10/data.oldPrice).toFixed(1) + "折");
					}else{
						$("#flj").remove();
						if(data.isLadder && data.isLadder != false &&  data.isLadder != "false"){
							var str = "<li id='flj'><div style='width:350px;height:30px;'>" +
	    					"<span class='sm_metatit'>内购价：</span>" +
	    					"<label id='benifit-price-id' class='benifit-price'>￥"+data.price+"</label>" +
	    					"<i id='benifit-add_q-id' class='add_q'>+内购券" +data.maxFucoin+ "</i>" +
	    					"<em id='benifit-em-id' style='font-style:normal;margin-left:20px;line-height:35px;float:left;'>" + (data.price*10/data.oldPrice).toFixed(1) + "折</em>" +
	    					"</div></li>";
							str +="<li><div style='width:300px;height:30px;'><span class='sm_metatit'>电商价：</span>"+
	    					"<strong>￥<span id='price'>"+data.oldPrice+"</span></strong>"+
	    					"</div></li>";
	    			        $(".summay li:eq(0)").html(str);
						}else{
							var str ="<li><div style='width:300px;height:30px;'><span class='sm_metatit'>电商价：</span>"+
	    					"<strong>￥<span id='price'>"+data.price+"</span></strong>"+
	    					"</div><p class='benifit-mark'>员工内购价格正在制定中...</p></li>";
	    				
		    			    $(".summay li:eq(0)").html(str);
						}
					}
				}
				
			}
		});
	}
}


// 处理企采展示信息
function dealSalesPromotionHtml(salesPromotion){
	
		var salesPromotionHtml = "";
		salesPromotionHtml+='<div class="div1"> ';
		salesPromotionHtml+='<span class="sm_metatit">促销：</span> ';
		salesPromotionHtml+='  <div class="cx_activity"> ';
		salesPromotionHtml+='   <p> ';
		salesPromotionHtml+='       <em>企采</em> ';
		salesPromotionHtml+='      <span>' + salesPromotion + '</span> ';
		salesPromotionHtml+='   </p> ';
		salesPromotionHtml+=' </div> ';
		salesPromotionHtml+='</div>       ';
		salesPromotionHtml+='<div class="div2" style="display: none;"> ';
		salesPromotionHtml+=' <span class="sm_metatit">促销：</span> ';
		salesPromotionHtml+='<div class="cx_activity"> ';
		salesPromotionHtml+='   <p> ';
		salesPromotionHtml+='      <em>企采</em> ';
		salesPromotionHtml+='      <span>' + salesPromotion + '</span> ';
		salesPromotionHtml+='  </p>	                      ';   	
		salesPromotionHtml+='</div> ';
		salesPromotionHtml+='</div>    ';          
		$("#productSalesPromotion").html(salesPromotionHtml);
		
		$(".div1 .cx_activity p span").hover(function(){
			$(".div2").fadeIn(300);
		});
		$(".div2").mouseout(function(){
			$(".div2").fadeOut(300);
		});
}
//处理起购数量
function dealMinLimitNum(minLimitNum){
	if(minLimitNum){
		if(minLimitNum > $("#buyCount").val()){
			$("#buyCount").val(minLimitNum);
			$("#buyCount").change();
		}
	}
}


function changeBtn(flag){
	if(flag){
		$('.item_btn_01 a').removeAttr("onclick");
		$('.item_btn_02 a').removeAttr("onclick");
		$('.item_btn_04 a').removeAttr("onclick");
		
		$(".item_btn_01").removeClass("item_btn_blue");
		$(".item_btn_02").removeClass("item_btn_red");
		$(".item_btn_02_2").removeClass("item_btn_white");
		$(".item_btn_04").removeClass("item_btn_darkBlue");
		
		$(".item_btn_01").addClass("item_btn_gray1");
		$(".item_btn_02").addClass("item_btn_gray2");
		//$(".item_btn_02").css("background","#959595");
		$(".item_btn_04").addClass("item_btn_gray3");
		$('.item_btn_02').removeClass("item_btn_02_2");
	}else{
		if(questionId && questionId.length > 0){
			$('.item_btn_02 a').attr("onclick","toQuestionnaire('"+questionId+"');");
			$('.item_btn_01 a').attr("onclick","toQuestionnaire('"+questionId+"');");
		}else{
			$('.item_btn_01 a').attr("onclick","addCart();");
			$('.item_btn_02 a').attr("onclick","addOrder();");
			$('.item_btn_04 a').attr("onclick","addMatch();");
		}
		if(saleKbn!='' && saleKbn==2){
			$(".item_btn_02").addClass("item_btn_02_2");
		}
		
		$(".item_btn_01").removeClass("item_btn_gray1");
		$(".item_btn_02").removeClass("item_btn_gray2");
		//$(".item_btn_02").css("background","#959595");
		$(".item_btn_04").removeClass("item_btn_gray3");
		
		$(".item_btn_01").addClass("item_btn_blue");
		$(".item_btn_02").addClass("item_btn_red");
		$(".item_btn_02_2").addClass("item_btn_white");
		$(".item_btn_04").addClass("item_btn_darkBlue");
	}
}