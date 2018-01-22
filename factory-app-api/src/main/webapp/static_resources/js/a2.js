var isload = true;
// JavaScript Document
$(function(){
    //登录检查
    if(isWeiXin()) {
    	$(".push_con p").hide();
    	$(".push_con img:eq(0)").hide();
    	$(".push_con img:eq(1)").hide();
    	
    	loginCheck(0);
    } else {
    	$(".push_con img:eq(2)").hide();
    	
    	alert("很抱歉，手机版目前仅支持微信中访问，你可以尝试使用电脑版。");
    	window.location = "http://www.wd-w.com";
    }

	$(".search_box").click(function(e){//点击设置
		go2Search("discount=0-5");
	});
	
    //bannner
    $('.flexslider').flexslider({
	      animation: "slide",
	      directionNav: false,
	      start:function(slider){
	        $('body').removeClass('loading');
	      }
	    });
    
    //ajaxpageDataIndex();
});

function changeBrandScope() {
	var brandScopeIndex = parseInt($("#brandScopeIndex").val(),10);
	var html="";
	//先画前16个
	for(var i=0;i<16;i++) {
		html +='<li><a href="javascript:go2Search(\''+brandJson[brandScopeIndex].link+'\')"><img src="'+brandJson[brandScopeIndex].img+'" /></a></li>';

		brandScopeIndex++;
		if(brandScopeIndex==brandJson.length) {
			brandScopeIndex=0;
		}
	}
	//再画4个
	for(var i=0;i<4;i++) {

		html +='<li class="border_none"><a href="javascript:go2Search(\''+brandJson[brandScopeIndex].link+'\')"><img src="'+brandJson[brandScopeIndex].img+'" /></a></li>';
		brandScopeIndex++;
		if(brandScopeIndex==brandJson.length) {
			brandScopeIndex=0;
		}
	}
	
	$("#brand_scope .main_one_con ul").html(html);
	$("#brandScopeIndex").val(brandScopeIndex);
}
