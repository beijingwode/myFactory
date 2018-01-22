<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>微信支付</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/wechat_pay.css"/>
    <script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
    <script type="text/javascript" src="<%=basePath %>resources/js/qr.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
</head>

<body>
<%@ include file="common/header_04.jsp" %>
<div class="main">
    <div class="w">
        <div class="order">
            <div class="o-left">
                <h3 class="o-title">请您及时付款，以便订单尽快处理！订单号：${orderNo}</h3>
                <p class="o-tips">请您在提交订单后<span class="font-red">24小时</span>内完成支付，否则订单会自动取消。</p>
            </div>
            <div class="o-right">
                <div class="o-price">
                    <em>应付金额</em><strong>${payment.totalFee}</strong><em>元</em>
                </div>
            </div>
            <div class="clr"></div>

        </div>
        <div class="payment">
            <div class="pay-weixin">
                <div class="p-w-hd">微信支付</div>
                <div class="p-w-bd" style="position:relative">
                     <div class="p-w-box">
                        <div class="pw-box-hd" id="code">

                        </div>
                        <div class="pw-box-ft">
                            <p>请使用微信扫一扫</p>
                            <p>扫描二维码支付</p>
                        </div>
                    </div>
                    <div class="p-w-sidebar"></div>
                </div>
            </div>
            <!-- payment-change 变更支付方式 -->
            <div class="payment-change">
                <a class="pc-wrap" id="reChooseUrl" href="<%=basePath %>payment/pay?${keyType}=${orderNo}">
                    <i class="pc-w-arrow-left">&lt;</i>
                    <strong>选择其他支付方式</strong>
                </a>
            </div>
        </div>
    </div>
</div>
<%@ include file="common/footer.jsp" %>
<script type="text/javascript">
    $('#code').qrcode("${payQR}");
    function checkOrder() {
        $.post("/order/orderStatus",{"${keyType}":'${orderNo}',"payType":"${payment.payType}"},function (data) {
                    if(data.data == 1){
                        location.href = "/member/paySuccess?${keyType}=${orderNo}&payType=${payment.payType}";
                    }
        });
        setTimeout('checkOrder()', 5000);
    }
    checkOrder();
</script>
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>


