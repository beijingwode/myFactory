
function showLimitTickets(list){
	makeWithOurLimitTicketDiv();
	$("#withOurLimitTicketJson").val(JSON.stringify(list));
	refreshQuanBox();
}

function showXQ() {
	var str=$("#withOurLimitTicketJson").val();
	var list=JSON.parse(str);
	var int0=0;
	var html='';
	for(var i=0;i<list.length;i++){
		if(list[i].flag==1) continue;
		int0++;
		if(list[i].ticketType==3||list[i].ticketType==4){
			html+='<li class="xianjin">';
		}else if(list[i].ticketType==1||list[i].ticketType==2){
			html+='<li>';
		}
		html+='<a href="javascript:getLimitTicket('+uid+','+list[i].id+');" id="'+list[i].id+'">';
		// <div class="xin_icon"><img src="<%=static_resources %>images/stamps_images/xin_icon.png" /></div>
		if(list[i].ticketType==3||list[i].ticketType==4){
			html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_top_bg1.png" /></p>';
		}else{
			html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_top_bg.png" /></p>';
		}
		html+='<div class="stamps_box">';
		html+='<div class="stamps_con">';
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
		html+='<div class="rt_bottom"><p class="p3">'+getDate(list[i].limitStart)+' - '+getDate(list[i].limitEnd)+'</p></div>';
		html+='</div>';
		html+='</div>';
		html+='</div>';
		html+='</div>';
		if(list[i].ticketType==3||list[i].ticketType==4){
			html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_bottom_bg1.png" /></p>';
		}else{
			html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_bottom_bg.png" /></p>';
		}
		html+='</a>';
		html+='</li>';
		
		if(int0==3) break;
	}

	$(".lq_thickbox ul").html(html);
	if(int0==0) {
		hideLq();
	}
}

function refreshQuanBox() {
	var str=$("#withOurLimitTicketJson").val();
	var list=JSON.parse(str);
	var int0=0;
	var html='';
	for(var i=0;i<list.length;i++){
		if(list[i].flag==1) continue;
		int0++;
		if(list[i].ticketType==3||list[i].ticketType==4){
			html+='<li class="xj">';
		}else if(list[i].ticketType==1||list[i].ticketType==2){
			html+='<li>';
		}
		if(list[i].ticketType==3||list[i].ticketType==4){
			html+='<i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_lt_icon2.png" /></i>';
			html+='<span>';
			if(list[i].ticketType==3){
				html+='通用现金券';
			} else {
				html+='专用现金券';
			}
			html+='</span>';
			html+='<i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_rt_icon2.png" /></i>';
		}else{
			html+='<i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_lt_icon1.png" /></i>';
			html+='<span>';
			if(list[i].ticketType==1){
				html+='内购抵扣券';
			} else {
				html+='免费体验券';
			}
			html+='</span>';
			html+='<i class="quan_lt1"><img src="'+jsBasePath+'static_resources/images/stamps_images/quan_rt_icon1.png" /></i>';
		}
		
		html+='</li>';
		
		if(int0==3) break;
	}

	$(".quan_box ul").html(html);
	if(int0==0) {
		$(".quan_box").hide();
	} else {
		$(".quan_box").show();
	}
}

function makeWithOurLimitTicketDiv() {
	if($(".quan_box").length == 0) {
		var html='';
		// 领券
		html += '<div class="quan_box">';
		html += '<input type="hidden" id="withOurLimitTicketJson" value="">';
		html += '<a href="javascript:;"><span>领券：</span><ul></ul></a>';
		html += '</div>';
		
		$(".dikouquan").before(html);
		$(".quan_box").hide();
		

		html='';
		html += '<div class="lq_thickbox">';
		html += '<div class="lq_tit">优惠券(点券领取)</div>';
		html += '<div class="close_btn">X</div>';
		html += '<div class="lq_thickbox_con"><ul></ul></div>';
		html += '</div>';
		
		$(".thickdiv").before(html);
		$(".lq_thickbox").hide();
		
		//弹出优惠券
		$(".quan_box").click(function(e){
			$(".thickdiv").show();
			$(".lq_thickbox").show();
			showXQ();
		});
		$(".close_btn").click(function(e){
			hideLq();
		});
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

				var str=$("#withOurLimitTicketJson").val();
				var list=JSON.parse(str);

				for(var i=0;i<list.length;i++){
					if(list[i].id==id) {
						list[i].flag=1;
						calculateLimitTicket("");
						break;
					}
				}
				$("#withOurLimitTicketJson").val(JSON.stringify(list));
				refreshQuanBox();
				showXQ();
			}else{
				showInfoBox(data.msg);
			}
		},
		error : function() {}
	});
}

function hideLq() {
	$(".thickdiv").hide();
	$(".lq_thickbox").hide();
}