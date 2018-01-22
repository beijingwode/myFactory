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
<title>确认订单</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/swiper-3.4.2.min.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/confirm_an_order.css" />


<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var specificationsId = '${specificationsId}';
	var quantity = '${quantity}';
	var orderType = '${orderType}';
	var partNumbers = '${partNumbers}';
	var backNum = '${backNum}';
	var groupId = '${groupId}';
</script>
</head> 

<body>
<div class="main-cont" id="main-cont" >
	<div class="main_top main_top1" id="addShopGroup">
		<p>还没有建立购物团哦！</p>
		<div class="btns"><a href="javascript:;">新建购物团</a></div>
	</div>
	<div class="main_top main_top2" style="display: none" id="chooseShopGroup">
		<div class="btns"><a href="javascript:;">选择购物团</a></div>
	</div>
	<div class="main_top main_top3" style="display: none" id="shopGroupInfo">
		<div class="link"><a href="javascript:go2ChooseShopGroup();">购物团信息</a></div>
		
		<div class="tuan_name" id="group_name"><p></p></div>
		<div class="user">
			<div class="p_con">
				<p class="p1"></p>
				<p class="p2"></p>
			</div>			
		</div>
		<div class="tuan_kegou">
			<div class="tuan_name">可购商品</div>
			<div class="main_three_con_v2 swiper-container" >
	     		<div class="swiper-wrapper" >
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
		
		<!--<div class="t_con">
			<p>团长说：</p>
			<p></p>
			 <input class="text_input" id="note" type="text" value="" placeholder="给团员留言">  
		</div>	--> 	
	</div>
	<div class="main_con">
		<div class="link" id="shopName" data-shopId="">员工福利旗舰店</div>
		<dl>
			<a href="javascript:void(0);">
				<dt><img src="" /></dt>
				<dd class="dd1"></dd>
				<dd class="dd2"></dd>
				<dd class="dd3"><p>￥<span></span></p><em><i></i></em></dd>
			</a>
		</dl>
		<div class="yunfei">
			<!-- <p>最高运费：<span>￥20.00</span></p> -->
			<input class="text_input" type="text" value="" placeholder="给卖家留言">
		</div>
	</div>
	<div class="yunfeiheji">运费合计：<span id="totalShipping">￥0.00</span></div>
	<div class="dikouquan">
		<p>抵扣</p>
		<ul>
			<li id="fuli" class="none_col">
			<span></span>
			</li>
			<input type="hidden" id="fuli_user">
			<input type="hidden" id="fuli_sel" value="0">
			<li id="xianjin" onclick="toggleSel(this.id);">
			<span></span>
			</li>
			<input type="hidden" id="xianjin_user">
			<input type="hidden" id="xianjin_sel" value="0">
		</ul>
	</div>
	<input type="hidden" id="totalPrice" value="0">
    <input type="hidden" id="totalFreight" value="0">
    <input type="hidden" id="totalfuli" value="0">
    <input type="hidden" id="totalhuanling" value="0">
    <input type="hidden" id="totalCash" value="0">
    <input type="hidden" id="total" value="0">
	<div class="TogetherToBuy_help"><a href="<%=basePath%>TogetherToBuy/TogetherToBuy_help.html">如何一起购？</a></div>
	<div class="bottom_box">
		<p>合计：<span></span></p>
		<a href="javascript:orderSubmit();">提交订单</a>
	</div>
</div>
<input type="hidden" id="groupId" value="">
<div class="theme-popover2 popover3"  style="display:none">
     <div class="theme_popover_con2">
     	<p class="p1">内购券不足<em>￥39.00</em></p>
     	<p>您剩余内购券：￥1.00</p>
     	<p>是否前往APP找好友凑券</p>
     </div>
     <div class="theme-popbod_know" >
        <a class="qx_btn" href="javascript:void(0);" onclick="closePo2()">取消</a>
        <a class="qcq_btn" href="javascript:void(0);" onclick="go2App()">去凑券</a>
     </div>
</div>
<div class="theme-popover-mask2" style="display: none"></div>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/grouporder_confirm.js?0109"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/order_sessionStorage.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/handleTime.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/swiper-3.4.2.jquery.min.js"></script>
<%@ include file="/commons/alertMessage.jsp" %>
<script>
function closePo2(){
	$(".theme-popover-mask2").hide();
	$(".theme-popover2").hide();
	back();
}

</script>
</body>
</html>
