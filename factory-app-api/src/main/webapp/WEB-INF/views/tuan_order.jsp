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
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/confirm_an_order.css"/>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/tuan_order.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>

<title>团员订单</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var groupId = "${groupId}"
	var userId = uid=GetUidCookie();
</script>
</head>

<body>
<div class="main-cont" id="main-cont" >
	<div class="main_con shop_con">
		<div class="shop_top1">
			<a href="javascript:;">
				<div class="shop_top_img"><img src="../static_resources/images/TogetherToBuy/comment_box_img.png" /></div>
		    	<p>138****9562</p>
	    	</a>
	    </div>
	    <dl>
			<a href="javascript:;">
				<dt><img src="../static_resources/images/TogetherToBuy/comment_box_img.png" /></dt>
				<dd class="dd1">华工科技是更好看机会给发货给及恐慌个</dd>
				<dd class="dd2">规格：S(160/80)</dd>
				<dd class="dd3"><p>￥<span>39.00</span>+内购券 26.00</p><em>X<i>2</i></em></dd>
			</a>
		</dl>
		<dl>
			<a href="javascript:;">
				<dt><img src="../static_resources/images/TogetherToBuy/comment_box_img.png" /></dt>
				<dd class="dd1">华工科技是更好看机会给发货给及恐慌个</dd>
				<dd class="dd2">规格：S(160/80)</dd>
				<dd class="dd3"><p>￥<span>39.00</span>+内购券 26.00</p><em>X<i>2</i></em></dd>
			</a>
		</dl>
    
   
    	<div class="heji" style="margin-top:0;border-top:1px solid #e3e3e8;">合计：<p>￥<em>10.00</em> + 内购券  26.00</p></div>
	</div>
	<div class="TogetherToBuy_help"><a href="javascript:;">如何一起购？</a></div>
</div>
</body>
</html>
