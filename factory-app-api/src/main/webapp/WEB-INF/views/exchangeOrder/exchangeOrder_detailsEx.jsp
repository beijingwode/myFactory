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
<meta name = "format-detection" content = "telephone=no">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>我的福利-订单详情</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/exchangeOrder/exchangeOrder_detailsEx.js?2"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/special_orderDetail.css" />
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var subOrderId = '${subOrderId}';
	var uid = '${uid}';
</script>
</head> 

<body>
<div class="main-cont" id="main-cont">
	
    <div class="main-box" style="position: absolute;top: 6px;">
    	<div class="address">
        	<p class="p1"><span id="name" style="float:left;"></span><span id="mobile" style="float:right;"></span></p>
            <p class="p2"></p>
        </div>
        <div class="logistics" >
        	<dl>
            	<dt><img src="<%=static_resources %>images/wuliutubiao.png" /></dt>
                <dd class="dd1"><span></span><em></em></dd>
            </dl>
            <dl id="dd2">
            	<dt><img src="<%=static_resources %>images/wuliuxinxi.png" /></dt>
                <dd id="xinxi"><a href="javascript:void(0);">
                	<p></p>
                    <i></i>
                </a></dd>
            </dl>
        </div>
        <div class="e_card">
        	<dl id="e_card_info">
            	<dt><img src="<%=static_resources %>images/E_card_coupons.png"/></dt>
                <dd><a href="javascript:void(0);">查看卡券详情</a></dd>
            </dl>
        </div>
        <div class="order_box" style="margin:10px 0 0 0;">
        	<div class="order_top"><p style="border:none;"></p></div>
            <ul>
            </ul>
            <div class="price_con">
            	<p id="pz"><span>商品总额</span><em></em></p>
                <p id='yf'><span>运费</span><em></em></p>
                <!-- <p id="flj"><span>内购券</span><em class="flj"></em></p> -->
                <p id='hlj'><span>换领币</span><img width="16" height="16" style="display: none"><em class="hlj"></em></p>
                <p id="Payable"><span>订单总价</span><i></i></p> 
                <p id='realPay'><span>实付金额</span><i class="realPay"></i></p>
                <p id='cashPay'><span>已付金额</span><i></i></p>
                <p id='returnPay'><span>退款金额</span><i></i></p>
            </div>
            
        </div>
        
        <div class="order_xinxi">
        	<p style="padding-bottom:5px;" id="distanceAutomaticConfirm"><span>自动确认收货时间：</span><em></em></p>
        	<p id='subOrderId'></p>
            <p id='createTime'></p>
            <p id='payTime'></p>
            <p id='sendTime'></p>
            <p id='serviceTime'></p>
        </div>
        
    </div>
    
</div>
<!-- 弹出二维码 -->
<div class="t_ewm"><img src="<%=static_resources %>images/ewm_img.png" /></div>

<!-- 取消订单/确认发货 -->
<div class="t_btn_box">
	<p class="t_p1">是否确定发货？</p>
	<p class="t_p2">该操作不可逆</p>
	<div class="t_btns">
		<a href="javascript:void(0);" class="no_btn">不</a>
		<a href="javascript:void(0);" class="yes_btn">确定发货</a>
	</div>
</div>

<div class="theme-popover2 popover2"  style="display:none">
     <div class="theme_popover_con2">
     	<span>换领优惠提前享</span>
     	<p>换领币未全部激活，不能完全抵扣。换领币激活后优先以现金券形式返还<em>xxx</em>元</p>
     </div>
     <div class="theme-popbod_know" >
        <a href="javascript:void(0);" onclick="closePo2()">我知道了</a>
     </div>
</div>
<div class="theme-popover2 popover3"  style="display:none">
     <div class="theme_popover_con2">
     	<span>不能申请售后</span>
     	<p>此订单含有试用商品,您需要先评价后申请售后,评价后获取试用返现,不影响申请售后.</p>
     </div>
     <div class="theme-popbod_know" >
        <a class="qx_btn" href="javascript:void(0);" onclick="closePo2()">取消</a>
        <a href="javascript:void(0);" onclick="go2PJ('${subOrderId}')">去评价</a>
     </div>
</div>
<div class="theme-popover-mask2" style="display: none"></div>
<div class="thickdiv" onclick="hide()"></div>
<div class="orderbox" >
    <div class="thickcon">
    <input type="hidden" id="orderStatus">
		<div class="region">选择关闭订单原因</div>
			<div class="close_list_con">
				<ul>
					<li><span>我不想买了</span><em></em></li>
					<li><span>信息错了，我重拍</span><em></em></li>
					<li><span>卖家缺货</span><em></em></li>
					<li><span>付款遇到问题（余额不足，不会付款）</span><em></em></li>
					<li><span>拍错了</span><em></em></li>
					<li><span>其它原因</span><em></em></li>
				</ul>
			</div>
		<div class="fh_btn" style="position:absolute;left:4%;bottom:0;">
		<a href="javascript:toCancel();">确认取消订单</a>
		</div>
   </div>
   <a href="javascript:closeReason()" id="closeBox" class="thickclose" >×</a>
</div>
<div class="smallPopup">
<div class="share odb">
<a href="javascript:go2Service(\''+result.subOrderId+'\',\''+result.realPrice+'\');">申请售后</a>
</div>
<div class="share odb">
<a href="javascript:go2Service(\''+result.subOrderId+'\',\''+result.realPrice+'\');">开票申请</a>
</div>
</div>
<%@ include file="/commons/alertMessage.jsp" %>
<script>
function closePo2(){
	$(".theme-popover-mask2").hide();
	$(".theme-popover2").hide();
}
$(function(){
	$(".close_list_con ul li em").each(function(index){
		$(this).click(function(){//先把所有的隐藏掉
			
			$(".close_list_con ul li em").removeClass("em1");
			$(this).addClass("em1");
		});
	});	
})
function hide(){
	$(".t_ewm").hide();
	$(".thickdiv").hide();
	$(".t_btn_box").hide();
}
function closeReason(){
	$(".orderbox").hide();
}
</script>
</body>
</html>
