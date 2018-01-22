// JavaScript Document
var wode={"hasLogin":0};//只保留一个全局变量，避免变量冲突.
wode.domain="http://supplier.wd-w.com"; //"http://www.wd-w.com";
wode.userDomain = "https://passport.wd-w.com";//邮箱验证
wode.comeFrom = "myFactory";//邮箱验证
//商品平台道页-图片轮播效果
var g_oTimer=null;
var g_iCurOpacity=0;
var g_iCurActiveIndex=0;
var g_oTimerAutoPlay=null;

window.onload=function ()
{
	var url=window.location.href;
	if(url.indexOf(wode.domain) != 0) {
		var indexP = url.indexOf(":");
		var start= url.substring(0,indexP+3);
		url = url.substring(indexP+3);
		
		indexP = url.indexOf(":");
		if(indexP != 0) {
			var domainS = url.substring(0,indexP);
			url = url.substring(indexP);
			indexP = url.indexOf("/");
			domainS=domainS+url.substring(0,indexP);
			url = url.substring(indexP+1);
			indexP = url.indexOf("/");
			var r = url.substring(0,indexP);
			if(r=="supplier") {
				wode.domain=start+domainS + "/supplier";
			} else {
				wode.domain=start+domainS;
			}
		}
	}
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
			g_oTimerAutoPlay=setInterval(next, 6000);
		}
	}
	
	g_oTimerAutoPlay=setInterval(next, 6000);
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
	g_oTimer=setInterval("showImgInner("+iIndex+")", 100);
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