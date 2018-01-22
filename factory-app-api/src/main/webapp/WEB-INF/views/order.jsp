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
<title><c:if test="${status==null}">全部订单</c:if><c:if test="${status==0}">待支付订单</c:if><c:if test="${status==1}">待发货订单</c:if>
            <c:if test="${status==2}">待收货订单</c:if><c:if test="${status==4}">待评论订单</c:if><c:if test="${status==311}">售后订单</c:if></title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css?321" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/order.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/order_sessionStorage.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var status = '${status}';
</script>
</head> 

<body>
<div class="main-cont" id="main-cont">
	
    
    <div class="main-box" style="position: absolute;top: 0px;">
    	<input type="hidden" id="pageNum" value="0">
    </div>
    
</div>
<div class="thickdiv" ></div>
<div class="orderbox" >
    <div class="thickcon">
    <input type="hidden" id="suborderID">
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
   <a href="javascript:void(0)" id="closeBox" class="thickclose" >×</a>
</div>
<script>
$(function(){
	$(".close_list_con ul li em").each(function(index){
		$(this).click(function(){//先把所有的隐藏掉
			
			$(".close_list_con ul li em").removeClass("em1");
			$(this).addClass("em1");
		});
	});	
})
</script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
