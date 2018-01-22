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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/new_confirm_order.css" />
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var specificationsId = '${specificationsId}';
	var quantity = '${quantity}';
	var orderType = '${orderType}';
	var productIds = '${productIds}';
	var partNumbers = '${partNumbers}';
	var backNum = '${backNum}';
</script>
</head> 

<body>
<div class="main-cont" id="main-cont" >
	<input type="hidden" id="page" value="0">
	<div class="address">
       <p class="p1"></p>
       <p class="p2"></p>
       <a href="javascript:;" style="display: none">点击此处添加收货地址</a>
       <input type="hidden" id="shippingAddressId" value="${shippingAddressId}">
       <input type="hidden" id="name">
       <input type="hidden" id="mobile">
       <input type="hidden" id="address">  
    </div>
    <div class="selfDelivery" id="selfDelivery" onclick="toggleSel(this.id);">
         <input type="hidden" id="selfDelivery_user">
         <div class="selfDelivery_btm"><p><span>自提（免邮、仅限现场直接领取）</span><em></em></p></div>
         <input type="hidden" id="selfDelivery_sel" value="0">
    </div>
    <div id="orderPrdouct">  
	</div>
	<div class="yunfeiheji">运费合计：<span></span></div>
	
	<div class="dikouquan">
		<p>抵扣</p>
		<ul>
			<input type="hidden" id="coupon_ticket" value="0">
			<input type="hidden" id="coupon_cash" value="0">
			<input type="hidden" id="coupon_all" value="0">
			<input type="hidden" id="coupon_ids" value="">
			<input type="hidden" id="coupon_key" value="">
			
			<li id="fuli" class="none_col">
			<span></span>
			</li>
			<input type="hidden" id="fuli_user">
			<input type="hidden" id="fuli_need" value="0">
			<input type="hidden" id="fuli_sel" value="0">
			
			<li id="xianjin" onclick="toggleSel(this.id);">
			<span></span>
			</li>
			<input type="hidden" id="xianjin_user">
			<input type="hidden" id="xianjin_sel" value="0">
			
			<li id="huanling" style="display:none" >
			<span><i></i></span>
			</li>
			<input type="hidden" id="huanling_user">
			<input type="hidden" id="huanling_sel" value="0">			
		</ul>
	</div>
	
	
	<div class="freeSwap" id="freeSwap" onclick="toggleSel(this.id);" style="display: none">
         <input type="hidden" id="freeSwap_user">
         <div class="freeSwap_btm"><p><span>当换领活动结束时，若匹配不成功，依照调剂清单调剂换领商品</span><em></em></p></div>
         <input type="hidden" id="freeSwap_sel" value="1">
    </div>
	<input type="hidden" id="totalPrice" value="0">
    <input type="hidden" id="totalFreight" value="0">
    <input type="hidden" id="totalhuanling" value="0">
    <input type="hidden" id="totalCash" value="0">
    <input type="hidden" id="total" value="0">
	<div class="TogetherToBuy_help" style="display: none"><a href="<%=basePath %>/hl_help.html">如何换领？</a></div>
	<div class="bottom_box">
		<p><i style="display:none;font-style: normal;font-size: 0.8em;">（配送：自提）</i>合计：<span></span></p>
		<a href="javascript:orderSubmit();">提交订单</a>
	</div>
</div>
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

<div class="thickdiv" ></div>
<!-- 自提信息补充弹层 -->
<div class="zt_message">
	<div class="zt_top">
		<p>请确认以下信息真实<br />以确保正常收货</p>
	</div>
	<ul>
		<li><span>姓名</span><input type="text" class="username" placeholder="请输入您的姓名" required="required"/></li>
		<li><span>手机</span><input type="text" class="phone" placeholder="请至个人中心设置中绑定" readonly="readonly"/></li>
		<li style="margin-bottom:10px;"><span>部门</span><input type="text" placeholder="请输入您的部门" class="sectionName" required="required"/></li>
		<li><span></span>（或楼层等工作位置信息）</li>
	</ul>
	<div class="ts_message"></div><!-- 验证提示信息 -->
	<div class="zt_message_btn"><a href="javascript:go2SaveUser();">确定</a></div>
</div>

<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/order/order_confirm.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/limit_ticket/order_limit_ticket.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/order_sessionStorage.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/handleTime.js"></script>
<%@ include file="/commons/alertMessage.jsp" %>
<script>
function closePo2(){
	$(".theme-popover-mask2").hide();
	$(".theme-popover2").hide();
	back();
}

$(".quan_box").click(function(){
	$(".thickdiv").show();
	$(".lq_thickbox").show();
})
$(".close_btn").click(function(){
	$(".thickdiv").hide();
	$(".lq_thickbox").hide();
})

</script>
</body>
</html>

