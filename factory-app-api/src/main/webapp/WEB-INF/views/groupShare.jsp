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
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/Group_purchase_invitation.css"/>
<title>${vo.share.shareTitle}</title>
</head>
<body>
<div class="main-cont" id="main-cont" >
	<div class="main-box">
		<p class="p1"><img src="<%=static_resources %>images/TogetherToBuy/top_tit_icon1.png" /></p>
		<p class="p2"><span>${vo.share.userNick}</span>向您推荐了以下商品</p>
		<dl>
			<c:forEach items="${vo.items}" var="item">
			<c:if test="${item.buyProductNum gt 0}">
			<span>团内已购：${item.buyProductNum}件</span>
			</c:if>
			<dt><img src="${item.banners[0]}"/></dt>
			<div class="dd_box">
			<dd class="dd1">${item.productName}</dd>
			<dd class="dd2">电商价：￥${item.price}</dd>
			<dd class="dd3">￥<em>${item.realPrice}</em>+内购券${item.welFare}</dd>
			</div>
			</c:forEach>
		</dl>
		<p class="p3"><img src="<%=static_resources %>images/TogetherToBuy/invitation_tit.png" /></p>
		<%-- <div class="ewm_box"><img src="<%=static_resources %>images/qrcode_for_gh.jpg" /></div> --%>
		<div class="ewm_box"><img src="<%=basePath %>userShare/getQr?text=${vo.share.nextAction}" /></div>
		<%-- <p class="p4">${vo.share.shareFooter1}</p>
		<p class="p4">${vo.share.shareFooter2}</p> --%>
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

