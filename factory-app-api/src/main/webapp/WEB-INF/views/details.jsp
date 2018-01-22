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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>我的福利-个人中心</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/recharge.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/details.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/recharge.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
</head> 

<body>
<div class="header">
	<div class="header_con">
		<img src="<%=static_resources %>images/activity_mine_relative_title_image.png" />
    	<div class="sz_btn"><a href="javascript:void(0);"><img src="<%=static_resources %>images/mine_set_icon.png" /></a><span>我的</span></div>
        <dl>
        	<a href="javascript:void(0);">
	        	<dt><img src="<%=static_resources %>images/activity_mine_imageview_headphoto_image_c.png" onerror="<%=static_resources %>images/activity_mine_imageview_headphoto_image_c.png" alt="<%=static_resources %>images/activity_mine_imageview_headphoto_image_c.png" /></dt>
	            <dd class="dd1">昵称</dd>
	            <dd class="dd2">北京我的网科技有限公司</dd>
            </a>
        </dl>
     </div>
     <div class="header_bottom">
         <ul>
         	<li class="xianjin"><a href="javascript:void(0);"><em></em><span>内购券</span></a></li>
             <li class="fuli"><a href="javascript:void(0);"><em></em><span>优惠券</span></a></li>
             <li class="huanling" style="background:none"><a href="javascript:void(0);"><em>0.00</em><span>换领币</span></a></li>
         </ul>
     </div>
    
</div>

<div class="all_orders">
	<div class="ord_top"><span>我的订单</span><a href="javascript:go2AllOrders();">查看全部订单</a></div>
    <div class="orders_con" id="orderstatus">
    	<dl>
        	<a href="javascript:void(0);" class="dfk">
            
        	<dt><span></span><img src="<%=static_resources %>images/activity_mine_relative_to_be_paid_image.png" /></dt>
            <dd>待付款</dd>
            </a>
        </dl>
        <dl>
        	<a href="javascript:void(0);" class="dfh">
            
        	<dt><span></span><img src="<%=static_resources %>images/activity_mine_relative_to_be_shipped_image.png" /></dt>
            <dd>待发货</dd>
            </a>
        </dl>
        <dl>
        	<a href="javascript:void(0);" class="dsh">
            
        	<dt><span></span><img src="<%=static_resources %>images/activity_mine_relative_shipped_image.png" /></dt>
            <dd>待收货</dd>
            </a>
        </dl>
        <dl>
        	<a href="javascript:void(0);" class="dpl">
            
        	<dt><span></span><img src="<%=static_resources %>images/activity_mine_relative_to_be_evaluated_image.png" /></dt>
            <dd>待评论</dd>
            </a>
        </dl>
        <dl>
        	<a href="javascript:void(0);" class="sh">
            
        	<dt><span></span><img src="<%=static_resources %>images/activity_mine_relative_refund_image.png" /></dt>
            <dd>退款/售后</dd>
            </a>
        </dl>
    </div>
</div>

<div class="invite_friends"><span>邀请好友</span><a href="javascript:inviteFriends()">立省30元</a></div>
<div class="more">	
	<div class="more_con">
		<dl>
        	<a href="javascript:go2Address();">
        	<dt><img src="<%=static_resources %>images/address_icon.png" /></dt>
            <dd>地址管理</dd>
            </a>
        </dl>
        <dl>
        	<a href="javascript:goIBuyPage();">
        	<dt><img src="<%=static_resources %>images/buy_icon.png" /></dt>
            <dd>我的一起购</dd>
            </a>
        </dl>
        <dl>
        	<a href="javascript:go2hl();">
        	<dt><img src="<%=static_resources %>images/huanling_icon.png" /></dt>
            <dd>我的换领</dd>
            </a>
        </dl>
        <dl>
        	<a href="http://www.wd-w.com/app.htm?d=1">
        	<dt><img src="<%=static_resources %>images/TogetherToBuy/app_icon.png" /></dt>
            <dd>APP下载</dd>
            </a>
        </dl>
        <dl class="product">
        	<a href="javascript:void(0);">
        	<dt><img src="<%=static_resources %>images/spsc_icon.png" /></dt>
            <dd>商品收藏</dd>
            </a>
        </dl>
        <dl class="shop">
        	<a href="javascript:void(0);">
        	<dt><img src="<%=static_resources %>images/dpsc_icon.png" /></dt>
            <dd>店铺收藏</dd>
            </a>
        </dl>
	</div>
</div>

<div class="recharge_money"  style="display: none">
     <div class="shareColleague"><span>同事邀请二维码</span></div>
	<p style="vertical-align: middle;text-align:center;"></p>
	<a href="javascript:go2close()" id="closeBox" class="thickclose" >×</a>
</div>
<div class="add_money-mask" style="display: none"></div>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
