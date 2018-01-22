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
<title>团信息设置</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/swiper-3.4.2.min.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/confirm_an_order.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/swiper-3.4.2.jquery.min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/tuan_mass_set.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.mobile.custom.min.js"></script>

</head> 
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var shopId = '${shopId}';
	var fromWay = '${fromWay}';
	var productIds = '${productIds}';
</script>
<body>
<input type="hidden" id="product">
<div class="main-cont" id="main-cont" >
	<div class="tuan_tit" style="margin-top:10px;"><span>团名称</span><input type="text" id="groupName" value="${nickName}的购物团" maxlength="15" placeholder="xxx的购物团"></div>
	<div class="tuan_kegou" style="margin-top:10px;">
		<div class="tuan_name"><a href="javascript:goTuanProduct()">团员可购商品<em>团员只可选购团长指定商品</em></a></div>
		<div class="main_three_con_v2 swiper-container" >
     		<div class="swiper-wrapper" id="productImage">
		        <div class="swiper-slide"><a href="javascript:;"><img src="../static_resources/images/parker_05.png" /></a></div>
		        <div class="swiper-slide"><a href="javascript:;"><img src="../static_resources/images/parker_05.png" /></a></div>
		        <div class="swiper-slide"><a href="javascript:;"><img src="../static_resources/images/parker_05.png" /></a></div>      
		        <div class="swiper-slide"><a href="javascript:;"><img src="../static_resources/images/parker_05.png" /></a></div>
		        <div class="swiper-slide"><a href="javascript:;"><img src="../static_resources/images/parker_05.png" /></a></div>
		        <div class="swiper-slide"><a href="javascript:;"><img src="../static_resources/images/parker_05.png" /></a></div>
				<div class="swiper-slide"><a href="javascript:;"><img src="../static_resources/images/parker_05.png" /></a></div>
		        <div class="swiper-slide"><a href="javascript:;"><img src="../static_resources/images/parker_05.png" /></a></div>
	     	</div>
	     </div>
	</div>
	<div class="main_top main_top3">			
		<div class="tuan_name">团长收货信息</div>
		<div class="user">			
			<div class="p_con tuan_p_con" style="display:block;">
				<p class="p1">收货人：赵子达  <em>15326545871</em></p>
				<p class="p2">收货地址：北京市朝阳区来广营哈哈日啦的更好好就行空腹喝刚回卡号公交卡但是过后</p>
			</div>			
		</div>			
	</div>
	<input type="hidden" id="ifchecked" value="0">
	<div class="time_end_set">
		<div class="set_top"><p class="p1"><span>截止时间</span><em>根据时限自动拼团完成</em></p><p class="p2"><span></span><em></em></p></div>
		<div class="set_bottom">
			<div class="quantity-wrapper">
                <input id="quantityMinus_d" class="quantity-decrease" name="" type="button" value="-" />                
                <input type="text" readonly class="quantity" size="4" value="2" id="number_d" >
                <input id="quantityPlus_d"  class="quantity-increase" name="" type="button" value="+" /> 
            </div>
            <em>最多14天</em>
		</div>
	</div>
	<div class="TogetherToBuy_help"><a href="<%=basePath%>TogetherToBuy/TogetherToBuy_help.html">如何一起购？</a></div>
	<div class="TogetherToBuy_btn"><a href="javascript:addGroupBuy();">确认设置</a></div>
	
</div>
<%@ include file="/commons/alertMessage.jsp" %>
<script>
</script>
</body>
</html>
