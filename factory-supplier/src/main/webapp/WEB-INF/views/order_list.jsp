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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/swiper.min.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/transaction_management.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/prompt.css" />
<title><c:if test="${status==0}">待买家付款</c:if><c:if test="${status==1}">待您发货</c:if>
<c:if test="${status==2}">已发货</c:if><c:if test="${status==4}">已完成</c:if><c:if test="${status==5}">维权单</c:if><c:if test="${status==-1}">已关闭</c:if></title>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/wxGetUid.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/order_list.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/nochange_font.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var status = '${status}';
</script>
</head>
<body>
<div class="main_box"  style="padding-top:42px;">
	<div class="top_tab"  id="header" style="display: none">
    	<ul class="swiper-wrapper">
        	<li class="swiper-slide this" id="onlyReturn"><a href="javascript:void(0);">仅退款申请中</a></li>
            <li id="onlyRefund"  class="swiper-slide"><a href="javascript:void(0);">退款退货申请中</a></li>
            <li id="allReturn"  class="swiper-slide"><a href="javascript:void(0);">仅退款完毕</a></li>
            <li id="allRefund"  class="swiper-slide"><a href="javascript:void(0);">退款退货完毕</a></li>
        </ul>
    </div>
	<input type="hidden" id="pageNum" value="0">
    <div class="main_two">
    </div>
    <div class="bottom_con">没有更多内容了</div>
</div>
<div class="recharge_money"  style="display: none">
     <div class="theme-tit">修改运费</div>
     <input type="hidden" id="suborderID">
     <div class="theme-input"><input type="text" id="shipping" placeholder="请输入运费" autocomplete="off" min="0.01" step="0.01" onkeyup="checkNum(this)" maxlength="8"/></div>
     <div class="theme-popbod">
        <a href="javascript:go2Close();" >取消</a>  
        <a href="javascript:go2Sure();" style="border:none;">确定</a>
     </div>
</div>
<div class="add_money-mask" style="display: none"></div>
<div class="thickdiv" ></div>
<div class="orderbox" >
    <div class="thickcon">
		<input type="hidden" id="suborderID">
    	<c:if test="${status==0}">
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
		</c:if>
    	<c:if test="${status==2}">
    			<div class="region">选择延长收货时间</div>
				<div class="close_list_con">
					<ul>
						<li><span>3天</span><em></em></li>
						<li><span>5天</span><em></em></li>
						<li><span>7天</span><em></em></li>
						<li><span>10天</span><em></em></li>
					</ul>
				</div>
				<div class="fh_btn" style="position: absolute; left: 4%; bottom: 0;">
					<a href="javascript:ajaxUpdateSubOrder();">确认延长收货时间<span></span></a>
				</div>
		</c:if>
    </div>
    <a href="javascript:void(0)" id="closeBox" class="thickclose" >×</a>
</div>
<%@ include file="/commons/newAlertMessage.jsp" %>
</body>
<script type="text/javascript" src="<%=static_resources %>js/swiper.min.js"></script>
<script type="text/javascript">
window.onload = function() {
  var mySwiper1 = new Swiper('#header',{
	  freeMode : true,
	  slidesPerView : 'auto',
  });
  
}
</script>

</html>