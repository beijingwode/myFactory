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
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/ShopSavvy.css" />
<title>${vo.share.shareTitle}</title>
</head>
<body>
<div class="main-cont" id="main-cont" >
	<div class="main-box">
		<div class="top_bg">
			<img src="<%=static_resources %>images/ShopSavvy_bg.png" />		
			<c:forEach items="${vo.items}" var="item">
				<dl>
					<span>这个宝贝  刚刚为我节省了¥${(item.price-item.realPrice)*vo.number}</span>
					<dt><img src="${item.banners[0]}" /></dt>				
					<dd class="dd1">${item.productName}</dd>
					<dd class="dd2">电商价：￥${item.price}</dd>
					<dd class="dd3">￥<em>${item.realPrice}</em>+内购券${item.welFare}</dd>				
				</dl>
			</c:forEach>
		</div>
		<p class="p3">扫码绑定亲友！一起省钱真朋友！</p>
		<div class="ewm_box"><img src="<%=basePath %>userShare/getQr?text=${vo.share.nextAction}" /></div>
		 
	</div>
</div>
</body>
<script type="text/javascript">
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
    // 通过下面这个API隐藏右上角按钮
   	 WeixinJSBridge.call('hideOptionMenu');
	});
</script>
</html>