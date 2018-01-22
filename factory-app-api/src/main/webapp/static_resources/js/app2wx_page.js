if(typeof(system_domain) == "undefined"){
		//jQuery.getScript("static_resources/js/common/system_config.js");//动态加载js
	var system_domain ="http://api.wd-w.com/";
	var weixin_open_appId ="wxb62e121cbeffdddf";
	var comm_user_domain = "https://passport.wd-w.com/";
}
$(document).ready(function(){
	try{
		if($(".slides li").length>1){
		$('.flexslider').flexslider({
		      animation: "slide",
		      directionNav: false,
		      start:function(slider){
		        $('body').removeClass('loading');
		      }
		});
		}
	} catch (e) {
		// TODO: handle exception
	}
	var pageKey = $("#pageKey").val();
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main_box").css("top","0");
		$(".footer a").show();
	    $("a").each(function(i){
	    	var href = $(this).attr("href");
	    	if(href &&!href.indexOf('javascript')==0) {
		    	if(href.length>21 && href.length<150) {
		    		if(href.indexOf('activity')==0){//活动页做处理
		    			href = href.substring(9);
		    		}else if(href.indexOf('http://wd-w.com/shop/')!=-1 || href.indexOf('http://www.wd-w.com/shop/')!=-1){//shop
		    			href = href.replace("?","&");
			    		href = href.replace("http://wd-w.com/shop/",system_domain+"shop/page?shopId=");
			    		href = href.replace("http://www.wd-w.com/shop/",system_domain+"shop/page?shopId=");
			    		href = href.replace(".html","");
			    		href=href+"&fromPageKey="+pageKey;
		    		}else if(href.indexOf('http://wd-w.com/product/search?key=')!=-1 || href.indexOf('http://www.wd-w.com/product/search?key=')!=-1){//搜索关键字/或加brand
			    		href = href.replace("http://wd-w.com/product/search?",system_domain+"pSearch/page?params=");
			    		href = href.replace("http://www.wd-w.com/product/search?",system_domain+"pSearch/page?params=");
			    		href = href.replace(".html","");
			    		href=href+"&fromPageKey="+pageKey;
		    		}else if(href.indexOf('http://www.wd-w.com/product/list?cat=')!=-1 || href.indexOf('http://wd-w.com/product/list?cat=')!=-1){//搜索分类/或brand
			    		href = href.replace("http://wd-w.com/product/list?",system_domain+"pSearch/page?params=");
			    		href = href.replace("http://www.wd-w.com/product/list?",system_domain+"pSearch/page?params=");
			    		href = href.replace(".html","");
			    		href=href+"&fromPageKey="+pageKey;
		    		}
		    		else{
			    		href = href.replace("?","&");
			    		href = href.replace("http://wd-w.com/",system_domain+"productm?productId=");
			    		href = href.replace("http://www.wd-w.com/",system_domain+"productm?productId=");
			    		href = href.replace(".html","");
			    		href = href.replace("specificationsId=","skuId=");
		    		}
	            }
	   			$(this).attr("href","javascript:goNext('"+href+"')");
	    	}
	    });
	    
	    if(pageKey) {
		    $.getScript("cart/activityLog?app=wx&url="+pageKey);
	    }
	    
	}else{

	    $("a").each(function(i){
	    	var href = $(this).attr("href");
	    	if(href && !href.indexOf('javascript')==0) {
		    	if(href.length>21 && href.length<100) {
		    		if(href.indexOf('activity')==0){//活动页做处理
		    			//href = href.substring(9);
		    		}else{
	//	    		href = href.replace("?","&");
	//	    		href = href.replace("http://wd-w.com/",system_domain+"productm?productId=");
	//	    		href = href.replace("http://www.wd-w.com/",system_domain+"productm?productId=");
	//	    		href = href.replace(".html","");
		    		href = href.replace("skuId=","specificationsId=");
		    		}	   			
		   			$(this).attr("href",href);		   			
	            }
	    	}
	    });
	    
	    if(pageKey) {
			$.getScript("cart/activityLog?app=app&url="+pageKey);
	    };
	    
	    var url=window.location.href;
	    if(url.indexOf("iframe=1")>-1){
	    	$(".top a").hide();
	    }else if(url.indexOf("app=1")>-1){
	    	$(".top a").show();
	    }else{
	    	if(history.length > 0){
	    	}else{
	    		$(".top a").hide();
	    	}
	    }
	}
});

function goNext(url) {
	//获取cookie字符串 
	var strCookie = document.cookie;
	//将多cookie切割为多个名/值对 
	var arrCookie = strCookie.split("; ");
	//遍历cookie数组，处理每个cookie对 
	
	var uid = "";
	for (var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		 if (arr[0] == "uid") {
			//uid
			uid = arr[1];
		}
	}
	if(uid!="") {
		window.location=url;
	} else {
		sessionStorage.setItem("loginNextUrl", url);
		sessionStorage.setItem("loginPreUrl", window.location.href);
		var hasCom= false;
		var shareId =sessionStorage.getItem("shareId");
		var supplierId=sessionStorage.getItem("supplierId");
		if(typeof(shareId)=="undefined" || shareId==null){
			if(typeof(supplierId)!="undefined" && supplierId!=null){
				hasCom=true;
				shareId=supplierId;
				sessionStorage.setItem("shareId", shareId);
			} else {
				if($("#supplierId").length>0){
					supplierId=$("#supplierId").val();
					if(supplierId!="") {
						hasCom=true;
						shareId=supplierId;
						sessionStorage.setItem("shareId", shareId);
					}
				}
			}
		} else {
			hasCom=true;
		}
		var fromId="";
		if(sessionStorage.fromId){
			fromId=sessionStorage.fromId;
		}
		var openId=sessionStorage.getItem("openId");
		if(typeof(openId)=="undefined" || openId==null){
			var state="bindOrLogin";
			if(isWeiXinH5()) {
				var rtn = encodeURI(system_domain+"wx/hasBind");
				window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_open_appId+"&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
			} else {
				alert("请使用微信扫一扫，重新扫描二维码");
			}
		} else {
			if(hasCom) {
				window.location="/userShare/companyBindPage"+shareId+"?fromId="+fromId+"&openId="+encodeURI(openId);			
			} else {
				window.location="/user/toLogin?exp1="+encodeURI(openId)+"&toUrl=&type=W&msg=";
			}
		}
	}
}