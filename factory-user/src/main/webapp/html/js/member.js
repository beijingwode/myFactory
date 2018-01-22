// JavaScript Document
//注册切换
$(function(){ 
	$('.register_list ul li').click(function(){
		var index=$('.register_list ul li').index(this);
       	$('.register_cont').each(function(i){
			if(i==index){
				$('.register_cont').eq(i).css({display:'block'});
				$('.register_list ul li').eq(i).addClass('current');				
			}else{
				$('.register_cont').eq(i).css({display:'none'});
				$('.register_list ul li').eq(i).removeClass('current');
			}
   		})
  	}) 
})
