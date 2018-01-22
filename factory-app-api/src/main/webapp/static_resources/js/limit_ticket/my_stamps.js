//JavaScript Document
$(document).ready(function() {
	init();
});
function init(){
	if(uid=="") return;
	$.ajax({
		url:jsBasePath+"limitTicket/getTicketList.user?uid="+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		var list = data.data;
	    		var html = '';
	    		if(list.length!=0){
	    			for (var i = 0; i < list.length; i++) {
		    			if(list[i].status==2){
		    				if(list[i].ticketType==3||list[i].ticketType==4){
		    					html+='<li class="yiyongwan xianjin xianjin_yyw"><!-- 已失效状态添加yishixiao 已用完状态添加yiyongwan -->';
		    				}else{
		    					html+='<li class="yiyongwan"><!-- 已失效状态添加yishixiao 已用完状态添加yiyongwan -->';
		    				}
		    			}else if(list[i].status==3){
		    				if(list[i].ticketType==3||list[i].ticketType==4){
		    					html+='<li class="yishixiao xianjin xianjin_ysx"><!-- 已失效状态添加yishixiao 已用完状态添加yiyongwan -->';
		    				}else{
		    					html+='<li class="yishixiao"><!-- 已失效状态添加yishixiao 已用完状态添加yiyongwan -->';
		    				}
		    			}else{
		    				if(list[i].ticketType==3||list[i].ticketType==4){
		    					html+='<li class="xianjin"><!-- 已失效状态添加yishixiao 已用完状态添加yiyongwan -->';
		    				}else{
		    					html+='<li class=""><!-- 已失效状态添加yishixiao 已用完状态添加yiyongwan -->';
		    				}
		    			}
		    			html+='<a href="'+list[i].nextAction+'" data-ticket="'+list[i].id+'">';
		    			html+='	<div class="xin_icon"></div>';
		    			if(list[i].ticketType==3||list[i].ticketType==4){
		    				if(list[i].status==2){
		    					html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_top_bg2.png" /></p>';
		    				}else if(list[i].status==3){
		    					html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_top_bg3.png" /></p>';
		    				}else{
		    					html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_top_bg1.png" /></p>';
		    				}
		    			}else{
		    				html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_top_bg.png" /></p>';
		    			}
		    			html+='<div class="stamps_box">';
		    			html+='	<div class="stamps_con">';
		    			html+='<div class="stamps_lt">';
		    			if(list[i].ticketType==2){
		    				html+='<span class="span1">免费</span>';
		    			}else{
		    				if(list[i].cashBalance>0){
		    					html+='<span class="span1">'+list[i].cashBalance+'</span>';
		    				}else{
		    					html+='<span class="span1">'+list[i].ticketBalance+'</span>';
		    				}
		    			}
		    			var ticketName="";
		    			if(list[i].ticketType==1){
		    				ticketName = '内购抵扣券';
		    			}else if(list[i].ticketType==2){
		    				ticketName = '免费体验券';
		    			}else if(list[i].ticketType==3){
		    				ticketName = '通用现金券';
		    			}else{
		    				ticketName = '专用现金券';
		    			}
		    			html+='<span class="span2">'+ticketName+'</span>';
		    			html+='</div>';
		    			html+='<div class="stamps_rt">';
		    			html+='<div class="rt_con">';
		    			if(list[i].limitType==1){
	    					html+='<p class="p2">全平台无限制通用</p>';
	    				} else if(list[i].limitType==2) {
			    			if(list[i].supplierLimitTicketSkuList && list[i].supplierLimitTicketSkuList.length>0){
		    					if(list[i].supplierLimitTicketSkuList.length==1){
				    				html+='<dl>';
				    				html+='<dt><img src="'+list[i].supplierLimitTicketSkuList[0].image+'" /><i></i></dt>';
				    				html+='<dd class="dd1">'+list[i].supplierLimitTicketSkuList[0].productName+'</dd>';
				    				html+='<dd class="dd2">'+list[i].supplierLimitTicketSkuList[0].itemValues.replace("{","").replace("}","").replace(/\"/g,"")+'</dd>';
				    				html+='</dl>';
				    			} else {
			    					html+='<ul>';
			    					for (var j = 0; j < list[i].supplierLimitTicketSkuList.length; j++) {
			    						html+='<li><img src="'+list[i].supplierLimitTicketSkuList[j].image+'" /><i></i></li>';
									}
			    					if(list[i].supplierLimitTicketSkuList.length==2){
			    						html+='<li class="li1">2选1</li>';
			    					} else {
			    						html+='<li class="li2"><img src="'+jsBasePath+'static_resources/images/stamps_images/sandian.png" /></li>';
			    					}
				    				html+='</ul>';
				    			}
			    			}
	    				} else {
	    					html+='<p class="p2">'+list[i].ticketNote+'</p>';
	    				}	
		    			html+='<div class="rt_bottom"><p class="p3">使用期限：'+getDate(list[i].limitEnd)+'</p>';
		    			if(list[i].limitType==2) {
    				    	if(list[i].ticketType==4 || list[i].ticketType==3){
		    					html+='<em class="dakai2">';
		    					html+='<img src="'+jsBasePath+'static_resources/images/stamps_images/dakai_icon1.png" />';
		    					html+='</em>';
		    				}else{
		    					html+='<em class="dakai1">';
		    					html+='<img src="'+jsBasePath+'static_resources/images/stamps_images/dakai_icon.png" />';
		    					html+='</em>';
		    				}
    				    }
		    			html+='</div>';
		    			html+='</div>';
		    			html+='</div>';
		    			html+='</div>';
		    			html+='<div class="zhang yishixao_icon"><img src="'+jsBasePath+'static_resources/images/stamps_images/yishixiao_icon.png" /></div>';
		    			html+='<div class="zhang yiyongwan_icon"><img src="'+jsBasePath+'static_resources/images/stamps_images/yiyongwan_icon.png" /></div>';
		    			html+='<div class="gz">';
		    			html+='<span>限商品：</span>';
		    			html+='<ul>';
		    			html+='<li>'+list[i].ticketNote+'</li>';
		    			html+='</ul>';
		    			html+='</div>';
		    			html+='</div>';
		    			if(list[i].ticketType==3||list[i].ticketType==4){
		    				if(list[i].status==2){
		    					html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_bottom_bg2.png" /></p>';
		    				}else if(list[i].status==3){
		    					html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_bottom_bg3.png" /></p>';
		    				}else{
		    					html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_bottom_bg1.png" /></p>';
		    				}
		    			}else{
		    				html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_bottom_bg.png" /></p>';
		    			}
		    			html+='</a>';
		    			html+='</li>';
					}
		    		$("#ticket").html(html);
	    		}else{
	    			var html='';
	    			html +='<div class="empty_stamps">暂无优惠券</div>';
	    			$(".main-box").append(html);
	    		}
			}
			
	    	userCheck();
	    },
	    error : function() {}
	})
}
function userCheck(){
	$.ajax({
		url : jsBasePath+'wx/checkSubscribe.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				checkUser(data.data);
			} 
		},
		error : function() {
		}
	});
}
function checkUser(openId){
	$.ajax({
		url : jsBasePath+'wx/checkUser.user?uid='+uid+"&openId="+openId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				if("1"==data.data){
					$(".my_stamps_ewm").hide();
				}else{
					$(".my_stamps_ewm").show();
				}
			}
		},
		error : function() {
		}
	});
}