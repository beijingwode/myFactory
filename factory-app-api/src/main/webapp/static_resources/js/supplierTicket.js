
var uid = GetUidCookie();
//JavaScript Document
$(function() {
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	ajaxData();
});
function ajaxData(){
	if(uid=="") return;
	$.ajax({
		url:jsBasePath+'supplierTicket/list.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	var result = data.data;
	    	if (result.length>0) {
				var html='';
				for (var i = 0; i < result.length; i++) {
					var cnt = result[i].saleCnt-result[i].empBuyCnt;
					var per = parseInt(result[i].empLevel*100 / cnt);
					var kbn = 0;
					if(result[i].stopFlg >0){
						//已领取
						html +='<div class="stamps_con stamps_con3">';
						html +='<img src="'+jsBasePath+'static_resources/images/stamps_bg2.png" />';
						html +='<div class="tuzhang"></div>';
						
						kbn=2;
					} else if(cnt > result[i].empLevel){
						html +='<div class="stamps_con stamps_con1">';
						html +='<img src="'+jsBasePath+'static_resources/images/stamps_bg1.png" />';
						kbn=0;
					} else {
						html +='<div class="stamps_con stamps_con2">';
						html +='<img src="'+jsBasePath+'static_resources/images/stamps_bg1.png" />';
						kbn=1;
					}
					html +='<div class="sp_con_lt">';
					html +='<ul>';
					html +='<li class="li1">员工专享现金券</li>';
					html +='<li class="li2">企业福利，可购买任意商品</li>';
					html +='<li class="li3">数量:'+cnt+"&nbsp;商品:"+result[i].productName+'热卖，员工专享福利</li>';
					html +='</ul>';
					html +='<div class="price"><em>￥</em><span>'+result[i].divPrice+'</span></div>';
					html +='</div>';
					
					html +='<div class="sp_con_rt">';
					html +='<a href="javascript:goNext(\''+result[i].id+'\','+kbn+')">';
					html +='<div class="bor_jd">';
					html +='<span class="span1">已抢'+per+'%</span>';
					html +='<div class="barbox">';
					html +='<div class="barline">';
					html +='<div width="'+per+'" style="width:0px;" class="charts"></div>';
					html +='</div>';
					html +='</div>';
					if(result[i].stopFlg >0){
						html +='<span class="span2">去使用</span>';
					} else if(cnt > result[i].empLevel){
						html +='<span class="span2">立即抢券</span>';
					} else {
						html +='<span class="span2">已抢完</span>';
					}
					html +='</div>';
					html +='</a>';
					html +='</div>';
					html +='</div>';
				}
				$(".stamps_box").html(html);
				
				animate();
			}
	    }
	});
}

function animate(){
	$(".charts").each(function(i,item){
		var a=parseInt($(item).attr("width"));
		$(item).animate({
			width: a+"%"
		},1000);
	});
}
function goNext(id,kbn){
	if(kbn==2 || kbn==1) {
		go2Next(id);
	} else {
		$.ajax({
			url:jsBasePath+'supplierTicket/receiveSpecialSaleCash.user?uid='+uid+'&id='+id,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    success : function(data) {
		    	if (data.success) {
					
					showInfoBox("抢券成功，获得现金。看看别人手气","go2Next('"+id+"')");
				}else{
					showInfoBox(data.msg,"go2Next('"+id+"')");
				}
		    }
		});
		
	}
}
function go2Next(id){//刷新页面
	window.location.href=jsBasePath+'supplierTicket/hisPage?id='+id;
}