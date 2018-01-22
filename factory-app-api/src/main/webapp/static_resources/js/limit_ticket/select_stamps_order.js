var uid=GetUidCookie();
$(document).ready(function() {
	var isPageHide = false;
	window.addEventListener('pageshow', function(){
		// ios以外无效
		if(isPageHide){window.location.reload();}
	});
	window.addEventListener('pagehide', function(){isPageHide = true;});

	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});

	getUsableTickits();
	$(".no_btn").click(function(e){
		sessionStorage.setItem("limitTicketIds","0");
		history.back();
	});
	$(".yes_btn").click(function(e){
		var limitTicketIds="";
		$("#main-cont input").each(function(i){
			if($(this)){
				if($(this).val() == "1") {
					limitTicketIds += $(this).attr("id").substring(3) + ",";
				}
			}
		});

		if(limitTicketIds.length>0) {
			limitTicketIds= limitTicketIds.substring(0,limitTicketIds.length-1);
		} else {
			limitTicketIds="0";
		}
		sessionStorage.setItem("limitTicketIds",limitTicketIds);
		history.back();
	});
});


function getUsableTickits(){
	$.ajax({
		url : jsBasePath +'limitTicket/getUsableTickits.user?uid='+uid+'&skuIds='+skuIds,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(res) {
			var html='';
			if (res.success) {
				var list = res.data;
				var checks = ","+limitTicketIds+",";
				for(var i=0;i<list.length;i++){

					if(list[i].ticketType==3||list[i].ticketType==4){
						html+='<li class="xianjin">';
					}else if(list[i].ticketType==1||list[i].ticketType==2){
						html+='<li>';
					}

					html+='<a href="javascript:checkLimitTicket('+list[i].id+');">';
					var chk=checks.indexOf(","+list[i].id+",")>-1;

					html+='<div class="select_icon">';
					if(chk) {
						html+='<input type="hidden" id="flg'+list[i].id+'" value="1"/>';
						html+='<img src="'+jsBasePath+'static_resources/images/stamps_images/xuanzhong_icon.png" />';
					} else {
						html+='<input type="hidden" id="flg'+list[i].id+'" value="0"/>';		
						html+='<img src="'+jsBasePath+'static_resources/images/stamps_images/weixuanzhong_icon.png" />';	
					}
					html+='</div>';
					
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
					html+='</div>';
					if(list[i].ticketType==3||list[i].ticketType==4){
						html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_bottom_bg1.png" /></p>';
					}else{
						html+='<p class="p1"><img src="'+jsBasePath+'static_resources/images/stamps_images/beijing_bottom_bg.png" /></p>';
					}
					html+='</a>';
					html+='</li>';
				}
				
		    	$(".dikouquan ul").prepend(html);
		    	//优惠券使用规则
				$(".dakai1").toggle(function(){
					alert(1);
					$(this).html('<img src="'+jsBasePath+'static_resources/images/stamps_images/shouqi_icon.png" /></em>');
					$(this).parents(".stamps_box").find(".gz").show();
				},function(){
					$(this).html('<img src="'+jsBasePath+'static_resources/images/stamps_images/dakai_icon.png" /></em>');
					$(this).parents(".stamps_box").find(".gz").hide();
				});
				
				$(".dakai2").toggle(function(){
					$(this).html('<img src="'+jsBasePath+'static_resources/images/stamps_images/shouqi_icon1.png" /></em>');
					$(this).parents(".stamps_box").find(".gz").show();
				},function(){
					$(this).html('<img src="'+jsBasePath+'static_resources/images/stamps_images/dakai_icon1.png" /></em>');
					$(this).parents(".stamps_box").find(".gz").hide();
				});
			}

	    	$("#main-cont ul").html(html);
		},
		error : function() {}
	});
}

function checkLimitTicket(id) {

	var hid = $("#flg"+id);
	var chk=hid.val() == "1";
	chk=!chk;
	
	if(chk) {
		hid.val("1");
		hid.next().attr("src",jsBasePath+'static_resources/images/stamps_images/xuanzhong_icon.png');
	} else {
		hid.val("0");
		hid.next().attr("src",jsBasePath+'static_resources/images/stamps_images/weixuanzhong_icon.png');
	}	
}