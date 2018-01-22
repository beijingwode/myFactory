//我的评价-切换 || 我的维权-切换
/*$(function(){
	$('.appraise_nav ul li').click(function(){
		var index=$('.appraise_nav ul li').index(this);
       	$('.appraise_list_wrap').each(function(i){
			if(i==index){
				$('.appraise_list_wrap').eq(i).css({display:'block'});
				$('.appraise_nav ul li').eq(i).addClass('current');				
			}else{
				$('.appraise_list_wrap').eq(i).css({display:'none'});
				$('.appraise_nav ul li').eq(i).removeClass('current');
			}
   		})
  	}) 
})*/

//我的评价-评价弹框
$(function(){
	$('.A_cont li .A_btn').click(function(){
		if($(this).find("a").attr("data-type") == 1) {			
		} else {
			$('.appraise_box').hide();
			$(this).next('.appraise_box').show();
			
			$('.click_btn').click(function(){
	            $(this).closest('.appraise_box').hide();
			})
		}
	})	
})

//我的评价-评价弹框-星级评价
$(function(){
    $('.star_ul').each(function(index){
        $(this).find('a').click(function(){
            $(this).addClass('active-star');
            $($('.s_result')[index]).html($(this).attr('title'));
            $(this).parent().siblings().find("a").removeClass("active-star");
            $(this).parent().parent().parent().find('input').val($(this).attr("score"));
        });
    })
})
