var uid=GetUidCookie();
$(function(){
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
		if(isPageHide){window.location.reload();}
	});
	window.addEventListener('pagehide', function(){isPageHide = true;});
	var fromWay = sessionStorage.getItem("fromWay");
	if(fromWay && fromWay!='' &&fromWay == 'groupList'){
		sessionStorage.removeItem("fromWay");
		history.back();
	}
	init();
	if(userType && userType!=1){//非员工账户 创建新团隐藏
		$(".TogetherToBuy_btn").hide();
	}
	//左右滑动
	var mySwiper = new Swiper('.swiper-container',{
	slidesPerView : 'auto',//'auto'
	//slidesPerView : 3.7,
	observer:true,//修改swiper自己或子元素时，自动初始化swiper
	observeParents:true,//修改swiper的父元素时，自动初始化swiper
	})
});

function init(){
	if(uid=="") return;
	$.ajax({
		url:jsBasePath+'group/groupShopList.user?uid='+uid+'&shopId='+shopId+'&sku_nums='+sku_nums+'&productIds='+productIds,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if(data.success){
	    		var result = data.data;
	    		if(result!=null){
	    			var html = '';
    				for (var i = 0; i < result.length; i++) {
    					html+="<div class='main_top main_top3' style='padding-bottom:20px;'>";
    					html+="<div class='tuan_name'>";
						if(result[i].limitedTime == 1){
							html+="<p class='p1'>团名称:"+result[i].groupName+"</p>";
							html+="<p class='p2'>"+result[i].countDown+"</p>";
						}else{
							html+="团名称:"+result[i].groupName;
						}
						////////
						html+="</div>";
						html+="<div class='user'>";
						html+="<div class='p_con'>";
						html+="<p class='p1'>团长:"+result[i].commanderName+"<em>"+result[i].phoneNum+"</em></p>";
						html+="<p class='p2'>"+result[i].address+"</p>";
						html+="</div>";			
						html+="</div>";
						
						var groupBuyProductList = result[i].groupBuyProductList;
						html+="<div class='tit'>可够商品<span class='tit_span'>至少可省￥"+result[i].saveShippingFee+"</span></div>";
						html+="<div class=' swiper-container' >";
						html+="<div class='swiper-wrapper' >";
						if(groupBuyProductList!=null){
							for(var j=0;j<groupBuyProductList.length ;j++) {
								html+="<div class='swiper-slide'><a href='javascript:;'><img src='"+groupBuyProductList[j].image+"'/></a></div>";
							}
						}
						
						html+="</div>";
						html+="</div>";
						html+="<div class='btns' style='width:92%;margin:0 auto;'><a onclick='go2CJ(this)' id='cj_"+i+"'>选择该团</a></div>";
						html+="</div>";
					}
    				html+="<div class='TogetherToBuy_help'><a href='"+jsBasePath+"TogetherToBuy/TogetherToBuy_help.html'>如何一起购？</a></div>"
    				$(".TogetherToBuy_btn").before(html);
	    			for (var i = 0; i < result.length; i++) {
	    				//alert(JSON.stringify(result[i]));
						$("#cj_"+i+"").data('selData',JSON.stringify(result[i]));//绑定数据
					}
	    		}
	    	}
	    }
	});
}
function sel(that){
	 $(that).hide();
	 $(that).next().show();
}
function go2CJ(that){
	var data =$(that).data('selData');
	//data = JSON.parse(data);
	sessionStorage.setItem("groupBuy",data);
	//alert(data);
	back();
}
function back(){
	history.back();
}
function go2CreateGroup(){
	window.location = jsBasePath +'group/page.user?uid=' + uid+'&shopId='+shopId+'&fromWay=groupList';
}