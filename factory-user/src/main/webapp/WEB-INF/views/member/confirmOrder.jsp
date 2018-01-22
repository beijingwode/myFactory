<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-订单详情</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
</head>
<body>
<!--top begin-->
<%@ include file="../common/header_03.jsp" %>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Personal_wrap">
	<p class="P_title">商品信息</p>
    <div class="P_list">
    	<p class="P_list_title">
        	<span class="P_sp P_spn">商品</span>
            <span class="P_sl">数量</span>
            <span class="P_fl">内购券</span>
            <span class="P_dj">实付金额</span>
            <span class="P_yf">运费</span>
        </p>
        <c:set var="flag" value="true"></c:set>
        <c:forEach var="subOrderItem" items="${subOrder.subOrderItems }" varStatus="vs">
	        <div class="Me-order-list-c" style="clear:both;">
	        	
	            <div class="M-product P_w30 P_w30n">
			  		<c:if test="${subOrderItem.saleKbn==1 }">
			   		<div class="picon"><img src="../images/picon2.png" /></div>
			   		</c:if>
			  		<c:if test="${subOrderItem.saleKbn==2 }">
			   		<div class="picon"><img src="../images/picon_c2.png" /></div>
			   		</c:if>
			   		<c:if test="${subOrderItem.saleKbn==4 }">
			   		<div class="picon"><img src="../images/picon_z2.png" /></div>
			   		</c:if>
			  		<c:if test="${subOrderItem.saleKbn==5 }">
			   		<div class="picon"><img src="../images/picon_t2.png" /></div>
			   		</c:if>
	                <span class="M-s-img"><a href="/${subOrderItem.productId }.html?pageKey=order"><img src="${subOrderItem.image }" width="78" height="78"></a></span>
	                <div class="M-s-text">
	                    <p class="p1" title="${subOrderItem.productName }">${fn:substring(subOrderItem.productName,0,20)}...</p>
	                    <c:forEach items="${subOrderItem.proValues.keySet()}" var="key" varStatus="keyStatus">
                           <p class="p${keyStatus.index + 2}">${key}：${subOrderItem.proValues.get(key)} </p>
                        </c:forEach>
	                </div>
	            </div>
	            <span class="P_sl">${subOrderItem.number }</span>
	            <span class="P_fl">
	            	<c:if test="${!empty subOrderItem.companyTicket }">${subOrderItem.companyTicket }</c:if>
	            	<c:if test="${empty subOrderItem.companyTicket }">0</c:if>
	            	券
	            </span>
	            <span class="P_dj"><fmt:formatNumber value="${subOrderItem.realPay }" type="currency" pattern="￥0.00"/>
	           <%--  <c:if test="${subOrderItem.benefitAmount > 0}">+<em><fmt:formatNumber value="${subOrderItem.benefitAmount}" type="currency" pattern="#0.00"/>惠</em></c:if> --%>
	            </span>
	            <c:if test="${flag }">
	            	 <span class="P_yf"><fmt:formatNumber value="${subOrder.totalShipping }" type="currency" pattern="￥0.00"/></span>
	            	  <c:set var="flag" value="false"></c:set>
	            </c:if>
	           	
	            <div class="clear"></div>
	        </div>
        </c:forEach>
    </div>
    <div class="tline"></div>
    <p class="P_title">订单信息</p>
    <ul class="Product-Information">
    	<li>订单编号：<span>${subOrder.subOrderId }</span></li>
        <li>卖家昵称：<span>${subOrder.supplierName }</span></li>
        <li>收货信息：<span>${subOrder.address }</span></li>
        <li>成交时间：<span><fmt:formatDate value="${subOrder.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
    </ul>
    <p class="P_title">物流动态</p>
   	<ul class="wl_xx">
    </ul>
</div>
<div class="P_tips">
	<p class="P_t_text"><i></i>请务必在收到货后，再确认收货！否则您可能货没拿到，钱也没了！</p>
    <p class="P_t_btn"><a href="/member/confirmOrder?subOrderId=${subOrder.subOrderId }">确定收货</a></p>
</div>
<script type="text/javascript">
$(document).ready(function(){
	//物流情报显示
	var html="";
	if ('${subOrder.selfDelivery}'!='null' && '${subOrder.selfDelivery}'=='1') {//自提
		 html+= "<li><span>配送方式：</span>自提</li>";
		 $(".wl_xx").html(html);
	}else {
	if('${expressCom}' != '' && '${expressCom}'!='null') {
		if ('${subOrder.expressType}'=='14660000000000000') {//电子卡券
			 html+= "<li><span>配送方式：电子卡券</span></li>";
			 html+= "<li><span>卡券密码:${expressNo}</span></li>";
			 $(".wl_xx").html(html);
		}else if('${subOrder.expressType}'=='14660000000000001'){//厂家直送
			 html+= "<li><span>配送方式：厂家直送</span></li>";
			 html+= "<li><span>货运号:${expressNo}</span></li>";
			 $(".wl_xx").html(html);
		}else{
			var content = '"sname":"express.ExpressSearch","com":"${expressCom}","express_no":"${expressNo}","user":${searchId},"version":"v2"';
	    	$.ajax({
	            url: 'http://kuaidi.wo-de.com/express/busJsonp.do?content={' +content+ '}&token=',
	    		dataType : 'jsonp',
	    	    jsonp:'jsonpcallback',
	    	    success: function(json){
	    	    	if(json.body){
		    	    	var ary = eval(json.body.history)
		    	    	var lis="";
		    	    	for(var i=0; i<ary.length; i++){  
			    	     	lis += "<li><span>"+ary[i].dealDate+"</span>"+ary[i].des+"</li>";
			    	  	}
		    	    	if(lis!="") {
		    	    		$(".wl_xx").html(lis);
		    	    	}
	    	    	}
	    	    }
	    	});
		}
	}
	}
});
</script>
<div class="clear"></div>
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
<%@ include file="../common/footer.jsp" %>
</body>
</html>