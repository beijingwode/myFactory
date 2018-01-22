// JavaScript Document
$(document).ready(function(e) {
	/***不需要自动滚动，去掉即可***/
	//time = window.setInterval(function(){
		//$('.og_next').click();	
	//},5000);
	
	var ulw =251
	var aw = 1004;//ul宽度
	var cnt =4;
	
	if(window.innerWidth) {
		var width = window.innerWidth;
		if(width>1700) {
			ulw=281;
			aw=1405;
			cnt =5;
		}
	}
	
	drawSsm(cnt,ulw,aw);
});

function drawSsm(cnt,ulw,aw) {

	var linum = $('.mainlist li').length;//图片数量
	var w = linum * ulw;//ul宽度
	
	$('.piclist').css('width', w + 'px');//ul宽度
	$('.swaplist').html($('.mainlist').html());//复制内容
	
	$('.og_next').click(function(){
		
		if($('.swaplist,.mainlist').is(':animated')){
			$('.swaplist,.mainlist').stop(true,true);
		}
		if(linum>cnt){//多于4张图片
			ml = parseInt($('.mainlist').css('left'));//默认图片ul位置
			sl = parseInt($('.swaplist').css('left'));//交换图片ul位置

			var hcnt=ml/ulw*-1;
			var left=linum-hcnt-cnt; 
			if(left>cnt) {
				left=cnt;
			}
			if(left>0) {
				$('.mainlist').animate({left: ml - left*ulw + 'px'},'slow');//默认图片滚动	
			} else {
				$('.mainlist').animate({left: '0px'},'slow');//默认图片滚动	
			}
		}
	});
	
	$('.og_prev').click(function(){
		
		if($('.swaplist,.mainlist').is(':animated')){
			$('.swaplist,.mainlist').stop(true,true);
		}
		
		if(linum>cnt){
			ml = parseInt($('.mainlist').css('left'));
			sl = parseInt($('.swaplist').css('left'));
			var hcnt=ml/ulw*-1;
			if(hcnt>cnt) {
				hcnt=cnt;
			}
			
			if(hcnt>0) {
				$('.mainlist').animate({left: ml + hcnt*ulw + 'px'},'slow');//默认图片滚动	
			} else {
				$('.mainlist').animate({left: (linum-cnt)*ulw*-1+ 'px'},'slow');//默认图片滚动	
			}
		}
	})    
}

$(document).ready(function(){
	$('.brand_list').hover(function(){
			$('.og_prev,.og_next').show(300);
			
		},function(){
			$('.og_prev,.og_next').hide(300);
			
	});
	$('.og_prev,.og_next').hover(function(){
			
			$(this).fadeTo('fast',1);
		},function(){
			
			$(this).fadeTo('fast',0.7);
	})

})

