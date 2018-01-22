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
<title>我的福利-订单支付</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var orderId = '${orderId}';
	var subOrderId = '${subOrderId}';
	var orderType = '${orderType}';
	var pruductId = '${pruductId}';
	var backNum = '${backNum}';
	var type = '${type}';
</script>
</head> 

<body>

<div class="main-cont" id="main-cont" >
	<input type="hidden" id="totalFee" value="${totalFee }">
  	<div class="pay_top">支付金额<span><span><fmt:formatNumber value="${totalFee }" type="currency" pattern="0.00"/>元</span></span></div>
      <div class="pay_box">
      	<div class="box_top"><p><em>微信</em>支付</p><span><fmt:formatNumber value="${totalFee }" type="currency" pattern="0.00"/>元</span></div>
          <ul>
          	<li class="pay_weixin" onclick="toggleSel('weixin')">
              	<dl>
                  	<dt><img src="<%=static_resources %>images/weixinpay.png" /></dt>
                      <dd class="dd1">微信支付</dd>
                      <dd class="dd2"></dd>
                      <input type="hidden" id="weixin_sel">
                  </dl>
                  <em></em>
              </li>
              <li class="pay_yue" style="border:none" onclick="toggleSel('yue')">
              	<dl>
                  	<dt><img src="<%=static_resources %>images/activity_mine_relative_refund_image1.png" /></dt>
                      <dd class="dd1">现金券余额余额支付</dd>
                      <dd class="dd2">余额：￥0.00 不足以支付此订单</dd>
                      <input type="hidden" id="cashBanlance">
                      <input type="hidden" id="yue_sel">
                  </dl>
                  <em></em>
              </li>
          </ul>
      </div>
      <div class="pay_btn" style="display: none"><a href="javascript:paySubmit();">确认支付</a></div>   
</div>

<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/pay.js?0109"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/order_sessionStorage.js"></script>

<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
