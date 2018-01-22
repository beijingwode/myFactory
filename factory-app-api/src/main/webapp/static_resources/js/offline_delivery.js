//var uid=GetUidCookie();
// JavaScript Document
var isload = true;
var flag = true;
$(document).ready(function() {	
	$("#pageNum").val(0);
	flag = false;
	$(".bottom_btn p").css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yixuanze.png) no-repeat left center","background-size":"20px"});
	getOffline(1);
	$(".bottom_btn p").toggle(function(){
		$(this).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yijihuo.png) no-repeat left center","background-size":"20px"});	
		flag = true;
		$("#spanI").html("");
		$(".main-box").html("");
		$("#pageNum").val(0);
		getOffline(0);
	},function(){
		$(this).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/yixuanze.png) no-repeat left center","background-size":"20px"});
		flag = false;
		$("#spanI").html("");
		$(".main-box").html("");
		$("#pageNum").val(0);
		getOffline(1);
	});
	
	$(function(){
		$(window).scroll(function(){
			Load();
		});
	});
	function Load(){
		if(isload){//ajax在后台获取数据时，设值其false，防止页面多次加载
			var loadHeight = 55;//指定滚动条距离底部还有多少距离时进行数据加载
			var documentHeight = parseInt($(document).height(),10);//可视区域当前高度
			var windowHeight = parseInt(window.innerHeight,10);//窗口当前高度
			var scrollHight = parseInt($(window).scrollTop(),10);//窗口滚动条位置
			if(documentHeight - scrollHight - windowHeight < loadHeight){
				//ajax获取数据，以下为模拟
				isload = false;
				if(flag == true){
					getOffline(0);
				}else{
					getOffline(1);	
				}
			}
		}
	}
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
});
function getOffline(type){
	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	$.ajax({
		url : jsBasePath+'managerOrderRecord/getList.user?uid='+uid+'&page='+page+'&pageSize='+5+"&type="+type,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				var result = data.data;
				var list = result.list;
				if(list.length>0){
					var html = "";
					for (var i = 0; i < list.length; i++) {
						html+='<div class="jl_box">';
						html+='<div class="tit_top"><a href="javascript:toOrderDetail(\''+list[i].subOrderId+'\','+list[i].orderType+');">'+list[i].userName+'</a></div>';
						html+='<ul>';
						html+='<li><span>商品</span><p>'+list[i].productName+'</p></li>';
						html+='<li class="li2"><em>'+list[i].itemValues+' X '+list[i].skuNumber+'</em></li>';
						html+='<li><span>操作</span><p>'+(list[i].operationStatus == 0?"取消订单":"确认发货")+'('+(list[i].orderType== 0?"普通订单":"换领订单")+')</p></li>';
						html+='<li><span>时间</span><p>'+getDate(list[i].createDate)+'</p></li>';
						html+='</ul>';
						html+='</div>';
					}
					$(".main-box").append(html);
					$("#pageNum").val(page);
					$("#spanI").html("总计"+result.totalCount+"单");
				}else{
					$("#pageNum").val(-1);
					var html='';
					$(".main-box").append(html);
				}
				isload = true;
			}else{
				showInfoBox(data.msg);
			}
		},
		error : function() {}
	});
}

function toOrderDetail(subOrderId,orderType){
	if(orderType == 5){
		window.location.href=jsBasePath+"exchangeOrder/exchangeOrderDetailPageEx.user?subOrderId="+subOrderId+"&uid="+uid;
	}else{
		window.location.href=jsBasePath+"order/orderDetailPageEx.user?subOrderId="+subOrderId+"&uid="+uid;
	}
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
