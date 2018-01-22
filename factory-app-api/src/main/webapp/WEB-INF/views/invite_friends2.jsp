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
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<title>邀请好友</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
</head>
<body>
<div class="main-cont" id="main-cont" >
	<div class="main-box" >
		<div class="bg_img">
			<img src="<%=static_resources %>images/invite_friends2_bg.png" />
			<p>${vo.share.shareMsg1}</p> 
			<a href="javascript:getNow();">立即领取</a>
		</div>
		<div class="bottom_img">
			<div class="img1"><img src="<%=static_resources %>images/invite_friends_bottom_02.png" /></div>
			<div class="img2"><img src="<%=static_resources %>images/invite_friends_bottom_03.png" /></div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript">
$(function(){
	var qrUrl = '${vo.share.wxTempQrUrl}';
	
	if(qrUrl && qrUrl!=null && qrUrl!=""){
		
		var getQrUrl=encodeURI(jsBasePath+'userShare/getQr?text='+qrUrl);
		if(getQrUrl && getQrUrl!=null && getQrUrl!=''){
			$(".img2 img").attr("src",getQrUrl);
		}
	}
}); 
function getNow(){
	window.location='${vo.share.nextAction}';
}
</script>
</html>
