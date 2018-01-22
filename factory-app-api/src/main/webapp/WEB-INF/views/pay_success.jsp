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
<meta name="screen-orientation" content="portrait">
<meta name="x5-orientation" content="portrait">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<c:if test="${type ne 5 || empty type}">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/pay_success.css" />
</c:if>
<c:if test="${type==5}">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/successful_atches.css" />
</c:if>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<title><c:if test="${type==5}">支付完成</c:if><c:if test="${type ne 5 || empty type}">支付成功</c:if></title>
</head>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var orderId = '${orderId}';
	var subOrderId = '${suborderId}';
	var fromWay = '${fromWay}';
	var type = '${type}';
</script>
<body>
<div class="main-cont" id="main-cont" >
	<div class="top">
        <h1><a href="javascript:close();" class="aleft"></a><c:if test="${type==5}">支付完成</c:if><c:if test="${type ne 5 || empty type}">支付成功</c:if></h1>
    </div>
    <c:if test="${type ne 5 || empty type}">
	<div class="main_box" style="position:absolute;top:45px;left:0;">
		<div class="pay_success_hint">
			<ul>
				<li class="li1">支付成功</li>
				<li class="li2">订单正在处理中，我们将尽快为您发货</li>
			</ul>
			<div class="duigou"><img src="<%=static_resources %>images/complete_icon.png" /></div>
		</div>
		
		<div class="pay_details">
			<div class="consignee">
				<div class="cons_name"></div>
				<div class="address"><span>收货地址：</span><p></p></div>
			</div>
			<div class="pay_amount">
				<ul>
					<li class="li1"><span>抵扣券总额：</span><em></em></li>
					<li class="li2"><span>总价：</span><em></em></li>
				</ul>
				<div class="btns"><a href="javascript:go2Index();">返回首页</a><a href="javascript:go2Order();">查看详情</a></div>
			</div>
		</div>
		<div class="TogetherToBuy_help"><a href="http://www.wd-w.com/app.htm?d=1">下载APP，邀请好友参团</a></div>
		<div class="ewm" style="display: none">
			<p>关注“我的网”公众号，实时掌握订单进度</p>
			<div class="img_box"><img src="<%=static_resources %>images/qrcode_for_gh.jpg" /></div>
			<p>长按识别上方二维码<br />
				还有更多福利等您一起来享</p>
		</div>
	   
	</div>
	</c:if>
	<c:if test="${type==5}">
	<div class="main_con">
		<div class="main_top" >
			<img src="<%=static_resources%>images/successful_atches.png" />
			<p class="p1">换领匹配中</p>
			<p class="p2">请您留意查看调剂清单</p>
			<p class="p3">若匹配失败后，将依照调剂清单为您调剂商品</p>
		</div>
		<div class="pp_pro">
		<dl>
		</dl>
		<div class="hlb_price"  id="realPay"><span></span><i><img src="<%=static_resources %>images/huanlingbi_icon.png" /></i><em>（含运费￥0.00）</em></div>
	</div>
	
	<div class="pp_pro pp_pro1">
		<div class="hlb_price" id="balance"><span></span><i><img src="<%=static_resources %>images/huanlingbi_icon.png" /></i></div>		
	</div>
	<div class="btn_box"><a href="javascript:go2more();">浏览更多商品</a><a href="javascript:go2Wish();" class="wish_list_btn">查看调剂清单<span></span></a></div>
	</div>
	<div class="ewm" style="display: none">
			<p>关注“我的网”公众号，实时掌握订单进度</p>
			<div class="img_box"><img src="<%=static_resources %>images/qrcode_for_gh.jpg" /></div>
			<p>长按识别上方二维码<br />
				还有更多福利等您一起来享</p>
		</div>
	</c:if>
</div>
<div class="thickdiv" ></div>
<div class="tiaoji_box"  style="display:none">
	<p class="p1">再挑几个心仪的吧！</p>
	<p class="p2">匹配不成功时将依您所选为您调剂</p>
	<div class="tiaoji_con">
		<ul></ul>
	</div>
	<div class="tiaoji_btns"><a href="javascript:disNone();" class="btn_no">我不要</a><a href="javascript:chooseGood();" class="btn_yes">选好了</a></div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main_box").css("top","0");
	}
});
</script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/pay_success.js?0109"></script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
