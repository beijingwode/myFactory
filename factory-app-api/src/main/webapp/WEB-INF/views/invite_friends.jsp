<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
if(request.getServerPort() != 80 && request.getServerPort() != 443) {
	path=":"+request.getServerPort()+path;
}
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/public.css"/>
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/ShopSavvy.css"/>
<title>邀请好友</title>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript">
var userNick = '${vo.share.userNick}';
var desc = '${vo.share.shareMsg1}';
desc =desc.replace("<span>","").replace("</span>","").replace(/&nbsp;/g,"");
var jsBasePath = '<%=basePath%>';
</script>
</head>
<body>
<div class="main-cont" id="main-cont" >
	<div class="main-box" style="padding:0 0 15px 0;background:#fff;">
		<img src="<%=static_resources %>images/invite_friends_bg.png" /> 
	</div>
	<div class="top_icon"><img src="<%=static_resources %>images/top_share_icon.png" /></div>
	<div class="bottom_btn"><a href="javaScript:void(0)">点击右上角，发送给朋友</a></div>
</div>
<script type="text/javascript">
var uid=GetUidCookie();
$(document).ready(function(){
	if(uid!=''){
		go2Share();
	}
});
function go2Share(){
	var link = window.location.href;
	var imgUrl = '<%=static_resources %>images/wo_de_logo.png';
	$.ajax({
		url : jsBasePath+'wx/wxConfig?url='+encodeURI(window.location.href.replace(/&/g,"____").replace(/=/g,"****")),
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				
				wx.config({
				    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
				    appId: data.data.appId, // 必填，公众号的唯一标识
				    timestamp: data.data.timestamp, // 必填，生成签名的时间戳
				    nonceStr: data.data.nonceStr, // 必填，生成签名的随机串
				    signature: data.data.signature,// 必填，签名，见附录1
				    jsApiList: ['onMenuShareTimeline',
				                'onMenuShareAppMessage',
				                'onMenuShareQQ',
				                'onMenuShareWeibo',
				                'onMenuShareQZone',
				                'showOptionMenu',
				                'hideOptionMenu'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
				});
				
				wx.ready(function(){
					var shareData={
							title: '我的福利 朋友一生一起走 ，一起省钱一起嗨！', // 分享标题
				            link: link, // 分享链接,将当前登录用户转为fuid,以便于发展下线
				            imgUrl: imgUrl, // 分享图标
				            desc:desc.replace("<span>","").replace("</span>","").replace("&nbsp",""),
				            success:function (res) {  // 用户确认分享后执行的回调函数
					            shareSuccess();
				            },
					};
					//分享给朋友
					wx.onMenuShareAppMessage(shareData);
					//分享到朋友圈
					wx.onMenuShareTimeline(shareData);
					// 分享到QQ
					wx.onMenuShareQQ(shareData);
					//分享微博
					wx.onMenuShareWeibo(shareData);
					//分享qq空间
					wx.onMenuShareQZone(shareData);
				});
				wx.error(function(res){
					//alert(JSON.stringify(res));
				});
			} else {
			}
		},
		error : function() {
			//alert("err");
		}
	});
}
/**
 * 分享成功调用
 */
function shareSuccess(){
	$.ajax({
		url : jsBasePath+'userShare/shareSuccess.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				console.log("success");
			}
		}
	})
}
</script>
</body>
</html>
