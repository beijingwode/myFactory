// JavaScript Document
$(function(){
	var isload = true;
	
	$(function(){
		$(window).scroll(function(){
			Load();
		});
	});
	function Load(){
		if(isload){//ajax在后台获取数据时，设值其false，防止页面多次加载
			var loadHeight = 215;//指定滚动条距离底部还有多少距离时进行数据加载
			var documentHeight = parseInt($(document).height(),10);//可视区域当前高度
			var windowHeight = parseInt($(window).height(),10);//窗口当前高度
			var scrollHight = parseInt($(window).scrollTop(),10);//窗口滚动条位置
			
			var needLoad = false;
			if($.trim($('#pro_if1').html()) == '') {
				$("#pageNum").val("1");
				needLoad = true;
			}
			if(documentHeight - scrollHight - windowHeight < loadHeight){
				needLoad = true;
			}
			
			if(needLoad){
				//ajax获取数据，以下为模拟
				isload = false;
				
				var pageNumber = parseInt($("#pageNum").val(),10);
				if(pageNumber<10){
					var arys = new Array("clr_bor_blue","clr_bor_green","clr_bor_yellow","clr_bor_pink","clr_bor_gray","clr_bor_purple","clr_bor_orange","clr_bor_olive","clr_bor_LightBlue","clr_bor_peru","clr_bor_coral","clr_bor_burlyWood");
					var arysbtm = new Array("blue_btm","green_btm","yellow_btm","pink_btm","gray_btm","purple_btm","orange_btm","olive_btm","LightBlue_btm","peru_btm","coral_btm","burlyWood_btm");
					
					$('#pro_if'+pageNumber).html("<img src='../images/refresh.gif' style='margin-left:540px;' />").load('/index_'+pageNumber+'f.html?v='+version,function(){	
	
						var host = window.location.href
						if(host.indexOf("ace.wd-w.com") >= 0 ){
							 $("#pro_if "+pageNumber+" a").each(function(i){
						    	var href = $(this).attr("href");
						    	if(href &&href.indexOf('www.wd-w.com')!=-1) {
							    	href = href.replace("www.wd-w.com","ace.wd-w.com");
							    	$(this).attr("href",href);
						        } 			
							 })
						}
						
						var ul = $("#pro_if"+pageNumber+" .if_r_list");
						$($(ul).children("li")[0]).addClass(arys[pageNumber-1]);
						
					 	//楼层切换 
					 	$("."+arysbtm[pageNumber-1]+" .if_r_list li").each(function(index){
							$(this).hover(function(){//先把所有的隐藏掉
					 			$("."+arysbtm[pageNumber-1]+" .pro_tab_box").addClass("dis");
					 			if(index<$("."+arysbtm[pageNumber-1]+" .pro_tab_box").size()){
					 				$("."+arysbtm[pageNumber-1]+" .pro_tab_box:eq("+index+")").removeClass("dis");//在把对应的索引的显示
					 			} else {
					 				$("."+arysbtm[pageNumber-1]+" .pro_tab_box:eq(0)").removeClass("dis");//在把对应的索引的显示
					 			}
								
					 			$("."+arysbtm[pageNumber-1]+" .if_r_list li").removeClass(arys[pageNumber-1]);
					 			$(this).addClass(arys[pageNumber-1]);
					 		});	
					 	});
					 	
					 	FloorBy();
					 	
					}); 
					$("#pageNum").val(pageNumber + 1);
				} 
				isload = true;
			}
		}
	}
	Load();
});