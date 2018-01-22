// JavaScript Document
var g_oTimer=null;
var g_iCurOpacity=0;
var g_iCurActiveIndex=0;
var g_oTimerAutoPlay=null;

window.onload=function ()
{
	var oDiv=document.getElementById('shopping_packet');
	var oOl=oDiv.getElementsByTagName('ol')[0];
	var oUl=oDiv.getElementsByTagName('ul')[0];
	var aLiBtn=oOl.getElementsByTagName('li');
	var aLiAds=oUl.getElementsByTagName('li');
	
	var i=0;
	
	for(i=0;i<aLiBtn.length;i++)
	{
		aLiBtn[i].index=i;
		aLiBtn[i].onmouseover=liBtnActived;
		
		aLiAds[i].getElementsByTagName('img')[0].onmouseover=function ()
		{
			clearInterval(g_oTimerAutoPlay);
		}
		
		aLiAds[i].getElementsByTagName('img')[0].onmouseout=function ()
		{
			g_oTimerAutoPlay=setInterval(next, 3000);
		}
	}
	
	g_oTimerAutoPlay=setInterval(next, 3000);
};

function liBtnActived()
{
	gotoImg(this.index);
}

function gotoImg(iIndex)
{
	var oDiv=document.getElementById('shopping_packet');
	var oOl=oDiv.getElementsByTagName('ol')[0];
	var oUl=oDiv.getElementsByTagName('ul')[0];
	var aLiBtn=oOl.getElementsByTagName('li');
	var aLiAds=oUl.getElementsByTagName('li');
	
	var i=0;
	
	if(g_iCurActiveIndex==iIndex)
	{
		return;
	}
	
	g_iCurActiveIndex=iIndex;
	
	for(i=0;i<aLiBtn.length;i++)
	{
		aLiBtn[i].className='';
		aLiAds[i].style.display='none';
	}
	
	aLiBtn[iIndex].className='active';
	g_iCurOpacity=20;
	
	if(g_oTimer)
	{
		clearInterval(g_oTimer);
	}
	
	aLiAds[iIndex].style.filter='alpha(opacity='+g_iCurOpacity+')';
	aLiAds[iIndex].style.opacity=g_iCurOpacity/100;
	aLiAds[iIndex].style.display='block';
	g_oTimer=setInterval("showImgInner("+iIndex+")", 35);
}

function next()
{
	var oDiv=document.getElementById('shopping_packet');
	var oUl=oDiv.getElementsByTagName('ul')[0];
	var aLiAds=oUl.getElementsByTagName('li');
	
	gotoImg((g_iCurActiveIndex+1)%aLiAds.length);
}

function showImgInner(iIndex)
{
	var oDiv=document.getElementById('shopping_packet');
	var oUl=oDiv.getElementsByTagName('ul')[0];
	var oLiAds=oUl.getElementsByTagName('li')[iIndex];
	
	g_iCurOpacity+=10;
	if(g_iCurOpacity>=100)
	{
		oLiAds.style.filter='';
		oLiAds.style.opacity='';
		
		clearInterval(g_oTimer);
		g_oTimer=null;
	}
	else
	{
		oLiAds.style.filter='alpha(opacity='+g_iCurOpacity+')';
		oLiAds.style.opacity=g_iCurOpacity/100;
	}
}

//伪元素兼容低版本浏览器
$(function(){
	$('.shopping_label_list ul li:last-child').css('border-bottom','none');	
})

//全部商品分类下拉菜单
$(function(){
	$('.allproduct_menu').mouseover(function(){
		$('.menu_list').show();	
		$('.allproduct_menu p').css('background','url(images/factory_img.png) no-repeat 180px -74px');
	})
	$('.allproduct_menu').mouseout(function(){
		$('.menu_list').hide();
		$('.allproduct_menu p').css('background','url(images/factory_img.png) no-repeat 180px -38px');	
	})	
	$('.menu_list').mouseover(function(){
		$(this).show();	
		$('.allproduct_menu p').css('background','url(images/factory_img.png) no-repeat 180px -74px');
	})
	$('.menu_list').mouseout(function(){
		$(this).hide();	
		$('.allproduct_menu p').css('background','url(images/factory_img.png) no-repeat 180px -38px');
	})
	
	//二级菜单	
	$('.menu_list .mt').hover(function(){ 
		$(this).addClass("active");
		$(this).find('.menu_player').show();
	},function(){ 
		$(this).removeClass("active");
		$(this).find('.menu_player').hide(); 
	});
})

//热销排行榜切换
$(function(){ 
	$('.shopping_pro_list ul li').hover(function(){
		var index=$('.shopping_pro_list ul li').index(this);
       	$('.shopping_hotpro').each(function(i){
			if(i==index){
				$('.shopping_hotpro').eq(i).css({display:'block'});
				$('.shopping_pro_list ul li').eq(i).addClass('surr');				
			}else{
				$('.shopping_hotpro').eq(i).css({display:'none'});
				$('.shopping_pro_list ul li').eq(i).removeClass('surr');
			}
   		})
  	}) 
})

//banner 二级菜单
$(function(){ 
	$('.shopping_list_menu .lt').hover(function(){ 
		$(this).addClass("hover");
		$(this).find('.hcolor').css('color','#ff6161');
		$(this).find('.shopping_menu_layer').show();
	},function(){ 
		$(this).removeClass("hover");
		$(this).find('.hcolor').css('color','#fff');
		$(this).find('.shopping_menu_layer').hide();
	}); 
}); 

//商品详情-商品介绍切换
$(function(){ 
	$('.pro_tab_trigger ul li').click(function(){
		var index=$('.pro_tab_trigger ul li').index(this);
       	$('.pro_panel').each(function(i){
			if(i==index){
				$('.pro_panel').eq(i).css({display:'block'});
				$('.pro_tab_trigger ul li').eq(i).addClass('current');				
			}else{
				$('.pro_panel').eq(i).css({display:'none'});
				$('.pro_tab_trigger ul li').eq(i).removeClass('current');
			}
   		})
  	}) 
})