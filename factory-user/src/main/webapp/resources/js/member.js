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
  	});
  	
	$("#orderStatus").change(function() {
		if(this.value !=""){
			var status = this.value;
			var page = $("input[name='page']").val();
			if (typeof(page) == "undefined"){
				page = 1;
			}
			window.location = "/member/myorders?page="+page+"&status="+status;
		}else{
			var page = $("input[name='page']").val();
			if (typeof(page) == "undefined"){
				page = 1;
			}
			window.location = "/member/myorders?page="+page;
		}
	});
	$("#orderExchangeStatus").change(function() {
		if(this.value !=""){
			var status = this.value;
			var page = $("input[name='page']").val();
			if (typeof(page) == "undefined"){
				page = 1;
			}
			window.location = "/member/myhlOrder?page="+page+"&exchangeStatus="+status;
		}else{
			var page = $("input[name='page']").val();
			if (typeof(page) == "undefined"){
				page = 1;
			}
			window.location = "/member/myhlOrder?page="+page;
		}
	});
  	
})
