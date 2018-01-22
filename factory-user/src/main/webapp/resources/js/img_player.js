// JavaScript Document
//商品主屏道页-图片轮播效果
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
	/**
	 * 加载热销商品（分类页）
	 */
	$(".shopping_pro_list").each(function(){
		if($("#categoryId").val()!=null){
			//从缓存中取该分类下的热销商品
			$.ajax({
				type: "POST",
				url: "/pageChannelHotCategory/selectHotProductByCategory?categoryId="+$("#categoryId").val(),
				success: function(data){
					var count = data.data.length;
					if(count>0){
						var hotTypeStr = "";
						var hotProductStr = "";
						for (var i = 0; i < count; i++) {
							var item = data.data[i];
							hotTypeStr+="<li";
							if(i==0)
								hotTypeStr+=" class='surr'";
							for(var p in item){
								hotTypeStr+="><a href=''>"+p+"</a></li>";
								hotProductStr+="<ul class='shopping_hotpro'";
								if(i==0)
									hotProductStr+=" style='display:block;'";
								hotProductStr+=">";
								for(var j = 0; j<item[p].length; j++){
									hotProductStr+="<li>" +
													"<div class='num nred'>"+(j+1)+"</div>" +
													"<div class='shopping_hotinfo'>" +
														"<a href='/"+item[p][j].id+".html'><img src='"+item[p][j].image+"' width='49' height='58' alt='"+item[p][j].name+"'>" +
														"<p title='"+item[p][j].name+"'>" +item[p][j].name.substr(0,30)+"</p></a>" +
														"<strong>¥ "+item[p][j].showPrice+"</strong>" +
													"</div>" +
													"</li>";
								}
								hotProductStr+="</ul>"
							}
						}
						$('.shopping_pro_list ul li').remove();
						$('.shopping_pro_list ul').append(hotTypeStr);
						$('.shopping_hotpro').remove();
						$('.shopping_product_right').append(hotProductStr);
					}
				},
				error:function(){
					alert("未知错误");
				}
			});
		}
	});
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