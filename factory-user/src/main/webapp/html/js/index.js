// JavaScript Document
//首页banner 轮播
$(function() {
	var sWidth = $(".pro_focus").width(); 
	var len = $(".pro_focus ul li").length; 
	var index = 0;
	var picTimer;
	
	var btn = "<div class='btn'>";
	for(var i=0; i < len; i++) {
		btn += "<span></span>";
	}
	btn += "</div>";
	$(".pro_focus").append(btn);
	//$(".pro_focus .btnBg").css("opacity",0.5);

	$(".pro_focus .btn span").mouseenter(function() {
		index = $(".pro_focus .btn span").index(this);
		showPics(index);
	}).eq(0).trigger("mouseenter");
		
	$(".pro_focus ul").css("width",sWidth * (len));
	
	$(".pro_focus").hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			showPics(index);
			index++;
			if(index == len) {index = 0;}
		},4000); 
	}).trigger("mouseleave");
	
	function showPics(index) { 
		var nowLeft = -index*sWidth; 
		$(".pro_focus ul").stop(true,false).animate({"left":nowLeft},300); 
		$(".pro_focus .btn span").removeClass("on").eq(index).addClass("on"); 
		$(".pro_focus .btn span").stop(true,false).animate({},300).eq(index).stop(true,false).animate({},300); 
	}
});

//首页二级菜单
$(function(){ 
	$('.menu_list .mt').hover(function(){ 
		$(this).addClass("active");
		$(this).find('.menu_player').show();
		//$(this).find('.menu_player').fadeIn(500); 
	},function(){ 
		$(this).removeClass("active");
		$(this).find('.menu_player').hide();
		//$(this).find('.menu_player').stop(true,false).fadeOut("fast"); 
	}); 
}); 