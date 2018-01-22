
var uid=GetUidCookie();//用户id
//JavaScript Document
$(document).ready(function() {
	init();
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
});
function init(){
	if(uid=="") return;
	$.ajax({
		url:jsBasePath+"userShare/list.user?uid="+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		var result;
				result = data.data;
				
				if (result&&result.length>0) {
					var html='';
					for (var i = 0; i < result.length; i++) {
						html +='<li>';
						html +='<div class="sl-content sl-content-my-share">';
						html +='<p>福利分享 让朋友省钱才是真朋友</p>';
						html +='<dl>';
						html +='<dt><a href="javascript:go2userShare(\''+result[i].id+'\');">';
						html +='<img src="'+result[i].imgUrl+'" />';
						html +='</a></dt>';
						html +='<dd class="dd1"><a href="javascript:go2userShare(\''+result[i].id+'\');">'+result[i].shareMsg3+'</a></dd>';
						html +='<dd class="dd2">'+getDate(result[i].createTime)+'</dd>';
						html +='</dl>';
						html +='</div>';
						html +='<div class="sl-opts my-share-btn">';
						html +='<span onclick="go2Del(\''+result[i].id+'\')">删除</span>'
						html +='</div>'
						html +='</li>';
					}
					var phoneHeight=screen.height;//当前设备屏幕高度
					html+='<li>';
					if (result.length>=(parseInt(phoneHeight/100))) {
						html+='<div class="bottom">';
						html+='<div><a href="http://www.wd-w.com/app.htm?d=1"><img src="'+jsBasePath+"static_resources/images/app_share_open.png"+'"/></a></div>';
						html+='</div>';
						html+='</li>';
					}else{
						$(".bottom").show();
					}
					$(".my_share").html(html);
					jQuery.getScript(jsBasePath+"static_resources/js/slideleft.js");//动态加载js
				}else{
					var page ='';
					page +=' <div class="no_share"><img src="'+jsBasePath+"static_resources/images/no_share_img.png"+'" /></div>';
					page +='<div class="no_share_con">';
					page +='<ul>';
					page +='<li><p>快去分享给小伙伴们</p></li>'
					page +='<li><p>让他们</p></li>';
					page +='<li><p>享受更多的福利吧！</p></li>';
					page +='<li><a href="javascript:go2shareProduct();">去找商品分享</a></li>';
					page +='<li><a href="http://www.wd-w.com/app.htm?d=1">马上下载APP</a></li>';
					$(".main-box").html(page);
				}
			}
	    },
	    error : function() {}
	})
}
function go2shareProduct(){
	window.location=jsBasePath+'pSearch/page';
}

function getDate(createTime){
	var tt=new Date(parseInt(createTime))
	Y = tt.getFullYear() + '-';
	M = (tt.getMonth()+1 < 10 ? '0'+(tt.getMonth()+1) : tt.getMonth()+1) + '-';
	D = tt.getDate() + ' ';
	h = tt.getHours() + ':';
	m = (tt.getMinutes()<10 ? '0'+(tt.getMinutes()) :tt.getMinutes())+ ':';
	s = (tt.getSeconds()<10 ? '0'+(tt.getSeconds()) :tt.getSeconds());
    return   Y+M+D+h+m+s;     
}
//详情页面
function go2userShare(id){
	window.location = jsBasePath+'userShare/page'+id;
}
//删除
function go2Del(id){
	if(uid=="") return;
	$.ajax({
		url:jsBasePath+'userShare/delete.user?shareId='+id+'&uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				refresh();
			}else{
				showInfoBox(data.msg)
			}
	    },
	    error : function() {}
	});
}
function refresh(){
	location.reload();
}
function go2Close(){
	$(".bottom").hide();
}