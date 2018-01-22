<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "static_resources/";
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, telephone=no">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/transaction_management.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/prompt.css" />
<title>订单详情</title>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/wxGetUid.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/order_detail.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/nochange_font.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var subOrderId = '${subOrderId}';
	var status = '${status}';
</script>
</head>
<body>
<div class="main_box" style="padding-bottom:50px;">
    <div class="address">
        <p class="p1"><span style="float:left;" id="receiveName"></span><span style="float:right;" id="receiveTel"></span></p>
        <p class="p2"><span style="float:left;">地址：</span></p>
    </div>
    <div class="logistics">
        <dl>
            <dt><img src="<%=static_resources %>images/orderdetails_sendout.png" /></dt>
            <dd class="dd1"><span></span><em></em></dd>
        </dl>
        <dl id="dd2">
            <dt><img src="<%=static_resources %>images/orderdetails_address.png" /></dt>
            <dd id="xinxi"><a href="javascript:void(0);">
                <p></p>
                <i></i>
            </a></dd>
        </dl>
    </div>
    <div class="my_address">
    	<p><span>我的发货信息：</span><em id="sendAddress"></em></p>
        <p><span>我的退货信息：</span><em id="returnedAddress"></em></p>
    </div>
    <div id="product">
    	<div class="con_details" style="background:#fff;margin-top:10px;width:96%;padding-left:4%;">
       	 <dl>
            <dt><a href="javascript:void(0);"><img src="<%=static_resources %>images/shop_dt.png" /></a>
                <div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
            </dt>
            <dd class="dd1"><a href="javascript:void(0);"></a></dd>
            <dd class="dd2"></dd>
         </dl>
         <div class="ds_rt"><span></span><em></em></div>
    	</div>
   </div>
    <div class="price_con">
        <p id="pz"><span>商品总额</span><em></em></p>
        <!-- <p id="flj"><span>内购券</span><em></em></p> -->
        <p id="hlj"><span>换领币</span><em></em></p>
        <p id="yf"><span>运费</span><em></em></p>
        <p id="dueAmount"><span>应收金额</span><em></em></p>
        <p id="receiveAmount"><span>实收金额</span><em></em></p>
    </div>
    <c:if test="${status==3||status==5||status==11||status==12}">
    <div class="tk">退款申请详情</div>
    <div class="tk_con">
    	<p id="buyName"><span></span></p>
        <p id="refundOrder"><span></span></p>
        <p id="refundPay"><span>退款金额：</span><i></i></p>
        <p id="refundReason"><span></span></p>
        <p id="refundStatus"><span></span></p>
        <p id="autoRrefundTime"><span>逾期自动退款时间：</span><i></i></p>
        <p id="refundNote"><span>退款说明：测试</span></p>
        <div class="img_con"></div>
    </div>
    </c:if>
    <div class="order_xinxi">
        <p id="orderId"></p>
        <p id="closeOrder"></p>
        <p id="createTime"></p>
        <p id="payTime"></p>
        <p id="sendTime"></p>
    </div>
    <!-- <div class="bottom_btn"><a href="javascript:;" class="a2">拒绝退款</a><a href="javascript:;" class="a1">同意退款</a></div> -->
</div>
<input type="hidden" id="returnId">
<input type="hidden" id="refurnId">
<input type="hidden" id="userId">
<div class="recharge_money"  style="display: none">
     <div class="theme-tit">确定同意退款</div>
     <div class="theme-input"><input type="text" id="refund" value="将退回货款给买家，确定吗？" disabled="disabled"/></div>
     <div class="theme-popbod">
        <a href="javascript:go2Close();">取消</a>  
        <a href="javascript:go2Sure();"  style="border:none;">确定</a>
     </div>
</div>
<div class="add_money-mask" style="display: none"></div>
<div class="thickdiv" ></div>
<div class="orderbox" >
    <div class="thickcon">
		<input type="hidden" id="suborderID">
    	
				<div class="region">选择关闭订单原因</div>
				<div class="close_list_con">
					<ul>
						<li><span>未及时付款</span><em></em></li>
						<li><span>买家不想买</span><em></em></li>
						<li><span>买家重新拍</span><em></em></li>
						<li><span>恶意买家/同行捣乱</span><em></em></li>
						<li><span>缺货</span><em></em></li>
						<li><span>其它原因</span><em></em></li>
					</ul>
				</div>
				<div class="fh_btn" style="position: absolute; left: 4%; bottom: 0;">
					<a href="javascript:ajaxUpdateSubOrder();">确认关闭订单</a>
				</div>
    </div>
    <a href="javascript:void(0)" id="closeBox" class="thickclose" >×</a>
</div>
<%@ include file="/commons/newAlertMessage.jsp" %>
<script>
//微信浏览图片放大
    $(document).on('click', '.img_con img',function(event) {
        var imgArray = [];
        var curImageSrc = $(this).attr('src');
        var oParent = $(this).parent();
        if (curImageSrc && !oParent.attr('href')) {
            $('.img_con img').each(function(index, el) {
                var itemSrc = $(this).attr('src');
                imgArray.push(itemSrc);
            });
            WeixinJSBridge.invoke('imagePreview', { 
                'current': curImageSrc,
                'urls': imgArray
            });
            /* wx.previewImage({
                current: curImageSrc,
                urls: imgArray
            }); */
        }
    });
</script>
</body>
</html>