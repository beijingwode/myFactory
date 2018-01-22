// JavaScript Document

//伪元素兼容低版本浏览器
$(function(){
	$('.menu_one_list:last-child').css('border-bottom','none');
})


//首页banner 轮播
$(function() {

	$(window).resize(function() {
			var width = window.innerWidth;
			if(width>1700) {
				var links = $("link");
				for(var i=0;i<links.length;i++) {

					if($(links[i]).attr("href")=="css/index.css") {
						$($('head')[0]).append('<link rel="stylesheet" type="text/css" href="css/index_1620.css">');
						
						$(links[i]).remove();
						drawBrands(44);
						var ulw =281;
						var aw = 1405;//ul宽度
						var cnt =5;

						$('.mainlist').animate({left: '0px'},'fast');//默认图片滚动	
						drawSsm(cnt,ulw,aw);
						break;
					}
				}
			} else {
				var links = $("link");
				for(var i=0;i<links.length;i++) {

					if($(links[i]).attr("href")=="css/index_1620.css") {
						$($('head')[0]).append('<link rel="stylesheet" type="text/css" href="css/index.css">');
						$(links[i]).remove();
						
						drawBrands(29);
						

						var ulw =251;
						var aw = 1004;//ul宽度
						var cnt =4;

						$('.mainlist').animate({left: '0px'},'fast');//默认图片滚动	
						drawSsm(cnt,ulw,aw);
						break;
					}
				}
			}
			
		});
	
	FloorBy();
});

//首页二级菜单
$(function(){ 
	if($.trim($('.menu_list').text()).length>0){
		$('.menu_list').load('/category.html',function(){
			$('.menu_list .mt').hover(function(){ 
				$(this).find('h2').addClass("active");
				$(this).find('.menu_player').show();
			},function(){ 
				$(this).find('h2').removeClass("active");
				$(this).find('.menu_player').hide();
			}); 
		});
	};	
}); 

//楼层轮播

function FloorBy(){
	$(".pro_focus").each(function(findex){
		var sWidth = $(this).width(); 
		var len = $(this).find("ul li").length; 
		var index = 0;
		var picTimer;
		$(this).find("ul").css("width",sWidth * (len));
		
		$(this).find(".btn").remove();
		var btn = "<div class='btn'>";
		for(var i=0; i < len; i++) {
			btn += "<span></span>";
		}
		btn += "</div>";
		$(this).append(btn);
		
		
		$(this).find(".btn span").mouseenter(function() {
			var index = $(this).parent().find("span").index(this);
			showPics(index,$(".pro_focus").get(findex));
		})
		
		$(this).find(".btn span").eq(0).trigger("mouseenter");
		
		
		$(this).hover(function() {
			clearInterval(picTimer);
		},function() {
			picTimer = setInterval(function() {
				showPics(index,$(".pro_focus").get(findex));
				index++;
				if(index == len) {index = 0;}
			},4000); 
		}).trigger("mouseleave");
		
	});	
}

function showPics(index,focus_dom) { 
	var sWidth = $(focus_dom).width(); 
	var nowLeft = -index*sWidth; 
	$(focus_dom).find(" ul").stop(true,false).animate({"left":nowLeft},300); 
	$(focus_dom).find(".btn span").removeClass("on").eq(index).addClass("on"); 
	$(focus_dom).find(".btn span").stop(true,false).animate({},300).eq(index).stop(true,false).animate({},300); 
}