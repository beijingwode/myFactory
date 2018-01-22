
var uid=GetUidCookie();
//JavaScript Document
$(function(){
	init();
	$(".tit_con ul input").click(function(){
		$(".tit_con span").removeClass("tit_span");
		$(this).prev().attr("class","tit_span");
		
    });
	
	$(window).resize(function() {
    	$(".tit_con").css("height",$(".thickbox").height()-87);
    });
	
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
})

function init(){
	if(uid == "") return;
	$.ajax({
		url:jsBasePath+'app/product/ajaxGetProductForUpdate.user?uid='+uid+'&id='+productId,
		type : "GET",
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				var data2 = data.data;
				$("#isMarketable").val(data2.isMarketable);
				$("#status").val(data2.status);
				var html='';
				$("#mainPic img").attr("src",data2.image);//商品主图
				$(".dd1").html('商品名称：'+data2.fullName);//商品名称
				$(".dd2").html('品牌：'+data2.brandName);//商品品牌
				if (data2.saleKbn==5) {//试用
					$("#trialPrice").val(data2.empPrice);//返现金额
				}
				if(data2.saleKbn==4){//专享
					$("#empPrice").val(data2.empPrice);//员工专享价
				}
				var psl = data2.productSpecificationslist;//商品规格对象
				if (psl && psl.length>0) {
					for (var i = 0; i < psl.length; i++) {
						var flj=psl[i].price-psl[i].maxFucoin
						html+='<div class="standard_con">'
						html+='<ul>'
						html+='<li class="li1">规格:'+psl[i].itemnames+'</li>';
						html+='<li class="li2"><span>电商价:'+parseFloat(psl[i].price).toFixed(2)+'</span></li>';
						html+='<li class="li3"><span>内购价:'+parseFloat(flj).toFixed(2)+'</span></li>';
						html+='<li class="li4"><span class="span1">库存:'+psl[i].stock+'</span><span class="span2">预警值:'+psl[i].warnnum+'</span></li>';
						html+='</ul>'
						
						html+='<em onclick="changPSL(\''+psl[i].id+'\',\''+psl[i].itemnames+'\',\''+psl[i].price+'\',\''+flj+'\',\''+psl[i].stock+'\',\''+psl[i].warnnum+'\')"></em>';
						html+='<input type="hidden" id="sku_'+psl[i].id+'" name="specifications" value="'+psl[i].id+'_'+psl[i].price+'_'+psl[i].stock+'_'+psl[i].warnnum+'_'+psl[i].maxFucoin+'"/>';
						html+='</div>'
					}
					$("#pslList").html(html);
				}
				if (data2.saleKbn==2) {//换领
					$(".standard_con em").hide();
				}
			}
	    },
	    error : function() {}
	})
}
function changPSL(psid,itemnames,price,flj,stock,warnnum){
	$("#psId").val(psid);//规格id
	$("#ps").html('规格：'+itemnames);
	$("#price").val(parseFloat(price).toFixed(2));
	$("#flj").val(parseFloat(flj).toFixed(2));
	$("#allnum").val(stock);
	$("#warnnum").val(warnnum);
	$(".thickdiv").show();
	$(".thickbox").show();
}
function checkNum(obj){
	obj.value = obj.value.replace(/[^\d.]/g,""); 
    obj.value = obj.value.replace(/\.{2,}/g,".");    
    obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');   
    if(obj.value.indexOf(".")< 0 && obj.value !=""){
    	obj.value= parseFloat(obj.value);
   }
}
//取消
function go2Close(){
	$(".thickdiv").hide();
	$(".thickbox").hide();
}
//确定保存
function go2Save(){
	var price=$("#price").val();
	var flj=$("#flj").val();
	var trialPrice=$("#trialPrice").val();//返现金额
	var empPrice=$("#empPrice").val();//员工专享价
	if (parseFloat(flj)>parseFloat(price)) {
		showInfoBox("内购价不能大于电商价")
	}else if(parseFloat(trialPrice)>parseFloat(flj)){
		showInfoBox("内购价不能低于返现金额")
	}else if(parseFloat(empPrice)>parseFloat(flj)){
		showInfoBox("内购价不能低于专享价")
	}else{
		var isMarketable=$("#isMarketable").val();
		var status=$("#status").val();
		if (isMarketable==1&&status==2) {
			$(".add_money-mask").show();
			$(".recharge_money").show();
		}else{
			go2Sure();
		}
		
	}
	
}
function close(){
	$(".recharge_money").hide();
	$(".add_money-mask").hide();
}
function go2Sure(){
	var skuId=$("#psId").val();
	var price=$("#price").val();
	var flj=$("#flj").val();
	var macF = parseFloat(price)-parseFloat(flj);
	var stock=$("#allnum").val();
	var warnNum=$("#warnnum").val();
	$("#sku_"+skuId).val(skuId + "_" + price + "_" + stock + "_" + warnNum + "_"+ macF+ "_"+flj)
	//var specifications_result= skuId + "_" + price + "_" + stock + "_" + warnNum + "_"+ macF;
	updateSKU();
}
function updateSKU(){
	if(uid == "") return;
	var $specifications=$("input[name='specifications']");
	var html='';
	html +='<input type="hidden" name="productid" value="'+productId+'">';
	for (var i = 0; i < $specifications.length; i++) {
		html+='<input type="hidden" name="specifications_result" value="'+$specifications[i].value+'"/>';
	}
	$("#post_param").html(html);
	
	$.ajax({
		//url:jsBasePath+'app/product/ajaxSpecificationsChange.user?uid='+uid+'&productid='+productId+'&specifications_result='+specifications_result,
		url:jsBasePath+'app/product/ajaxSpecificationsChange.user?uid='+uid,
		type : "POST",
	    async: true,
	    data:$("#updateSku_from").serialize(),
	    success : function(data) {
	    	if (data.success) {
	    		close();
	    		go2Close();
	    		showInfoBox("修改成功");
				setTimeout(refresh, 1500)
			}
	    },
	    error : function() {}
	})
}
function refresh(){
	window.location.reload();
}