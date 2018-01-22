// JavaScript Document
//顶部鼠标滑过掌上生活二维码显示
$(document).ready(function() {
	$('.hand_life_li').hover(function(){ 
		$(this).addClass("hand_life_li_hover");
		$(this).find('.ewm_inner').fadeIn(500);
		$(this).children('em').css("background","url(../images/top_li_bg_03.png) no-repeat right center"); 
	},function(){ 
		$(this).removeClass("hand_life_li_hover");
		$(this).find('.ewm_inner').fadeOut("fast");
		$(this).children('em').css("background","url(../images/top_li_bg_02.png) no-repeat right center");
	}); 
}); 
