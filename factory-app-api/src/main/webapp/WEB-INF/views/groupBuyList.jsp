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
<title>选择购物团</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>/css/swiper-3.4.2.min.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/confirm_an_order.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var shopId = '${shopId}';
	var sku_nums = '${sku_nums}';
	var userType = '${userType}';
	var productIds = '${productIds}';
</script>
</head> 

<body>
<div class="main-cont" id="main-cont" >
	
	<%-- <div class="main_top main_top3">
		<input type="hidden" id="endTime">
		<div class="time_end"><p>2天10小时3分钟46秒</p><span>6/15人</span></div>
		<div class="user">
			<div class="tuanzhang">
				<dl>
					<dt><img src="<%=static_resources %>images/TogetherToBuy/comment_box_img.png" /><span><img src="<%=static_resources %>images/TogetherToBuy/tuanzhang.png" /></span></dt>
					<dd>138****7895</dd>
				</dl>
				<a href="javascript:;">团长信息</a>
			</div>
			<div class="p_con tuan_p_con">
				<p class="p1">赵子达  15326545871 北京市朝阳区</p>
				<p class="p2">来广营哈哈日啦的更好好就行空腹喝刚回卡号公交卡但是过后</p>
			</div>			
		</div>
		<div class="t_con">
			<p>团长说：</p>
			<p>不拼太重的！</p>
			<div class="btns"><a href="javascript:;">立即参团！</a></div>
			<div class="t_con_bottom">至少可省￥3.60</div>
		</div>		
	</div> --%>
	
	<%-- <div class="TogetherToBuy_help"><a href="<%=basePath%>TogetherToBuy/TogetherToBuy_help.html">如何一起购？</a></div> --%>
	<div class="TogetherToBuy_btn" style="position:fixed"><a href="javascript:go2CreateGroup();">建新团</a></div>
	
</div>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/groupShopList.js?0109"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/handleTime.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/swiper-3.4.2.jquery.min.js"></script>
<script>
$(function(){
	/* $(".tuanzhang a").click(function(){
		$(".tuanzhang").hide();
		$(".tuan_p_con").show();
	}) */
})
</script>
</body>
</html>
