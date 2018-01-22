
function showLimitTickets(uid,sku,list){
	makeLimitTicketDiv();
	
	var html='';
	var html2 = '';
	if(list.length!=0){
		html+='<ul >';
		for (var i = 0; i < list.length; i++) {
			if(list[i].ticketType==3||list[i].ticketType==4){
				html+='<li class="xianjin"><!-- 已失效状态添加yishixiao 已用完状态添加yiyongwan -->';
			}else if(list[i].ticketType==1||list[i].ticketType==2){
				html+='<li class=""><!-- 已失效状态添加yishixiao 已用完状态添加yiyongwan -->';
			}
			if(list[i].flag==1){
				html+='<a href="javascript:hideLq();">';
			} else {
				html+='<a href="javascript:getLimitTicket('+uid+','+list[i].id+');" id="'+list[i].id+'" data-ticket="'+sku+'">';
			}
			html+='	<div class="xin_icon"></div>';
			if(list[i].ticketType==3||list[i].ticketType==4){
				html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_top_bg1.png" /></p>';
			}else{
				html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_top_bg.png" /></p>';
			}
			html+='<div class="stamps_box">';
			html+='	<div class="stamps_con">';
			html+='<div class="stamps_lt">';
			if(list[i].ticketType==2){
				html+='<span class="span1">免费</span>';
			}else{
				if(list[i].cash>0){
					html+='<span class="span1">'+list[i].cash+'</span>';
				}else{
					html+='<span class="span1">'+list[i].ticket+'</span>';
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
			/*html+='<div class="zhang yishixao_icon"><img src="'+jsBasePath+'static_resources/images/stamps_images/yishixiao_icon.png" /></div>';*/
			if(list[i].flag==1){
				sessionStorage.setItem("pageKey","ticket_"+list[i].userLimitTicketId);
				html+='<div class="zhang yiyongwan_icon"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_ylq_icon.png" /></div>';
			}
		    if(list[i].limitType==2) {
    			html+='<div class="gz">';
    			html+='<span>限商品：</span>';
    			html+='<ul>';
    			html+='<li>'+list[i].ticketNote+'</li>';
    			html+='</ul>';
    			html+='</div>';
		    }
			html+='</div>';
			if(list[i].ticketType==3||list[i].ticketType==4){
				html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_bottom_bg1.png" /></p>';
			}else{
				html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_bottom_bg.png" /></p>';
			}
			html+='</a>';
			html+='</li>';
			if(i<3){
				if(list[i].ticketType==1){
    				if(list[i].flag==1){
    					html2+='<li id="ticketType1"><i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_lt_icon1.png" /></i><span><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_xz_icon.png" />内购抵扣券</span><i class="quan_rt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_rt_icon1.png" /></i></li>';
    				}else{
    					html2+='<li id="ticketType1"><i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_lt_icon1.png" /></i><span>内购抵扣券</span><i class="quan_rt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_rt_icon1.png" /></i></li>';
    				}
    			}else if(list[i].ticketType==2){
    				if(list[i].flag==1){
    					html2+='<li id="ticketType1"><i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_lt_icon1.png" /></i><span><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_xz_icon.png" />免费体验券</span><i class="quan_rt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_rt_icon1.png" /></i></li>';
    				}else{
    					html2+='<li id="ticketType2"><i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_lt_icon1.png" /></i><span>免费体验券</span><i class="quan_rt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_rt_icon1.png" /></i></li>';
    				}
    			}else if(list[i].ticketType==3){
    				if(list[i].flag==1){
    					html2+='<li class="xj" id="ticketType4"><i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_lt_icon2.png" /></i><span><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_xz_icon2.png" />通用现金券</span><i class="quan_rt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_rt_icon2.png" /></i></li>';
    				}else{
    					html2+='<li class="xj" id="ticketType4"><i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_lt_icon2.png" /></i><span>通用现金券</span><i class="quan_rt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_rt_icon2.png" /></i></li>';
    				}
    			}else if(list[i].ticketType==4){
    				if(list[i].flag==1){
    					html2+='<li class="xj" id="ticketType4"><i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_lt_icon2.png" /></i><span><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_xz_icon2.png" />专用现金券</span><i class="quan_rt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_rt_icon2.png" /></i></li>';
    				}else{
    					html2+='<li class="xj" id="ticketType4"><i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_lt_icon2.png" /></i><span>专用现金券</span><i class="quan_rt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_rt_icon2.png" /></i></li>';
    				}
    			}
			}
		}
		html+='</ul>';
		$("#ticketType").html(html2);
		$(".lq_thickbox_con").html(html);
		//优惠券使用规则
		$(".dakai1").toggle(function(){
			$(this).html('<img src="static_resources/images/stamps_images/shouqi_icon.png" /></em>');
			$(this).parents(".stamps_box").find(".gz").show();
		},function(){
			$(this).html('<img src="static_resources/images/stamps_images/dakai_icon.png" /></em>');
			$(this).parents(".stamps_box").find(".gz").hide();
		})
		
		$(".dakai2").toggle(function(){
			$(this).html('<img src="static_resources/images/stamps_images/shouqi_icon1.png" /></em>');
			$(this).parents(".stamps_box").find(".gz").show();
		},function(){
			$(this).html('<img src="static_resources/images/stamps_images/dakai_icon1.png" /></em>');
			$(this).parents(".stamps_box").find(".gz").hide();
		})
		$(".quan_box").show();
	} else {
		$(".quan_box").hide();
	}
}

function getLimitTicket(uid,id){
	if(id=='') return
	var skuId = $("#"+id+"").attr("data-ticket");
	$.ajax({
		url : jsBasePath +'limitTicket/receiveimitTicket.user?uid='+uid+'&limitTicketId='+id,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				$(".thickdiv").hide();
				$(".lq_thickbox").hide();

				ajaxGetLimitTicket(uid,skuId);
			}else{
				showInfoBox(data.msg);
			}
		},
		error : function() {}
	});
}

function makeLimitTicketDiv() {
	if($(".quan_box").length == 0) {
		var html='';
		// 领券
		html += '<div class="quan_box">';
		html += '<a href="javascript:;"><span>领券：</span><ul id="ticketType"></ul></a>';
		html += '</div>';
		
		$(".postage").before(html);
		
		html='';
		//<!-- 领券弹层 -->
		html += '<div class="lq_thickbox">';
		html += '<div class="lq_tit">优惠券</div><div class="close_btn">X</div>';
		html += '<div class="lq_thickbox_con"></div>';
		html += '</div>';

		$(".huanling_hit").after(html);
		
		//弹出优惠券
		$(".quan_box").click(function(e){
			$(".thickdiv").show();
			$(".lq_thickbox").show();
		});
		$(".close_btn").click(function(e){
			hideLq();
		});
	}
}

function hideLq() {
	$(".thickdiv").hide();
	$(".lq_thickbox").hide();
}