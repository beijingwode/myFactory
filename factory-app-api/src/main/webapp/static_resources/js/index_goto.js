if(typeof(system_domain) == "undefined"){
	var system_domain ="http://api.wd-w.com/";
	var weixin_open_appId ="wxb62e121cbeffdddf";
	var comm_user_domain = "https://passport.wd-w.com/";
}
function go2Search(url) {
	if(url=="") return;
	url=url.replace(/ /g,"");
	if(url.indexOf('activity')==0) {
		//活动页
		url = url.substring(9);
		var truePath = system_domain;
		if(typeof(jsBasePath)!="undefined") {
			truePath=jsBasePath;
		}
		
		if(truePath + "wx_dm_a.html"==url) {
			window.location = truePath + "wx_dm.html";
		} else if(url.indexOf('_app.html')>-1) {
			window.location = url.replace('_app.html','_wx.html');
		} else {
			window.location = url;
		}
	} else if(url.indexOf('_app.html')>-1) {
		//活动页
		window.location = url.replace('_app.html','_wx.html');
	} else {
		var params = url;
		var p = url.indexOf('?');
		var isSearch=false;
		if(p==-1) {
			p = url.indexOf('=');
			if(p==-1) {
				p = url.indexOf('channel');
				if(p==-1) {
					p = url.indexOf('shop');
					if(p==-1) {
						url=url.replace(".html","").replace("/","");
						//无参数 直接跳转到商品详情
						window.location = 'productm?productId=' + url+'&from=a';
					} else {
						//shop
						url=url.substring(p+5);
						url=url.replace(".html","").replace("/","");
						window.location = 'shop/page?shopId=' + url;
					}
					isSearch=false;
				} else {
					//无参数 直接跳转到商品详情
					var chn = url.substring(p+8);
					if(chn=="neigouhui"){
						params='';
						isSearch=true;
					}else{
						window.location = chn+'.html';
						isSearch=false;
					}
				}
			} else {
				p=url.indexOf('specificationsId');
				if(p==-1){
					params=params.replace("category","cat").replace("search_key","key");
					isSearch=true;
				}else{
					url=url.replace(".html","").replace("/","");
					//无参数 直接跳转到商品详情
					window.location = 'productm?productId=' + url+'&from=a';
				}
			}
		} else {
			if(url.indexOf('/search')!=-1){
				params =  url.substring(p+1);
				params=params.replace("category","cat").replace("search_key","key");
				isSearch=true;
			}else{
				//url.indexOf("http://www.wd-w.com/");
				url=url.replace(".html","").replace("http://www.wd-w.com/","").replace("http://wd-w.com/","").replace("?","&").replace("skuId","specificationsId");
				//无参数 直接跳转到商品详情
				window.location = 'productm?productId=' + url+'&from=a';
			}
		}
		
		if(isSearch) {
			//无参数 直接跳转到商品详情
			params = params.replace(/&/g,"____");
			window.location = 'pSearch/page?fromPageKey=index&params=' + encodeURI(params);
			
		}
	}
}
function check(url){
	
}
