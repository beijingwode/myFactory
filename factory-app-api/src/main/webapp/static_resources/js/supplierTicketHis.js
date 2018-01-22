
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
		url:jsBasePath+'supplierTicket/detail.user?uid='+uid+"&id="+specialSaleId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	var result = data.data.detail;
	    	var list = data.data.list;
	    	var cnt = 0;
	    	var num = 0
	    	if (result.length>0) {
				var html='';
				for (var i = 0; i < result.length; i++) {
					cnt = result[i].saleCnt-result[i].empBuyCnt;
					num = cnt-result[i].empLevel;
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
	    	
	    	
	    	if (list.length>0) {
				var html='';
				
				if( num==0 ){
					html +='<p>共'+ cnt +'个员工专享现金券，已抢完</p>';
				}else{
					html +='<p>共'+ cnt +'个员工专享现金券，剩余'+num+'个</p>';
				}
					
				for (var i = 0; i < list.length; i++) {					
					html +='<dl>';
					html +='<dt><img src="'+list[i].empAvatar+'" /></dt>';
					html +='<dd class="dd1">'+list[i].empName+'</dd>';
					html +='<dd class="dd2">'+getDate(list[i].empUseDate)+'</dd>';
					html +='</dl>';
				}				    			    
				    
				$(".details_con").html(html);
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


function getDate(createTime){
	var tt=new Date(parseInt(createTime))
	Y = tt.getFullYear() + '-';
	M = (tt.getMonth()+1 < 10 ? '0'+(tt.getMonth()+1) : tt.getMonth()+1) + '-';
	D = tt.getDate() + ' ';
	h = tt.getHours() + ':';
	m = (tt.getMinutes()<10 ? '0'+(tt.getMinutes()) :tt.getMinutes())+ ':';
	s = (tt.getSeconds()<10 ? '0'+(tt.getSeconds()) :tt.getSeconds());
    return   Y+M+D+h+m+s;     
}
function goNext(id,kbn){
	if(kbn==0 || kbn==1) {
		window.history.back();
	} else {
		window.location.href=jsBasePath+'index_m.htm';
	}
}