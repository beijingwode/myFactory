<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
	String path = request.getContextPath();
	if(request.getServerPort() != 80 && request.getServerPort() != 443) {
		path=":"+request.getServerPort()+path;
	}
	String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-个人中心</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>css/global.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>css/my_hl.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/member.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript">
	var basePath = '<%=basePath %>';
</script>
</head>
<body>

<!--top begin-->
<%@ include file="../common/header_03.jsp" %>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
<!--left nav-->
<%@ include file="menu.jsp" %>
<!--left nav end-->
<!--right content-->
	<div class="Me_content">
    	<%@ include file="my_hlb.jsp" %>
        <!-- <div class="hl_tit_li">
        	<ul>
        		<li class="crr bg"><a href="javascript:;">欲领清单</a></li>
        		<li><a href="hl_wish_list.html">调剂清单</a></li>
        	</ul>
        </div> -->
        	<c:set value="0" var="exTotalAdjustment"/>
        	<c:set value="0" var="exTotalShipping"/>
        	<c:set value="0" var="exRealPrice"/>
        	
        	<c:set value="0" var="totalAdjustment"/>
        	<c:set value="0" var="totalShipping"/>
        	<c:set value="0" var="realPrice"/>
        	<c:forEach items="${exSubOrders}" var="exsub">
        		<c:set value="${exsub.totalAdjustment+exTotalAdjustment}" var="exTotalAdjustment" />
				<c:set value="${exsub.totalShipping+exTotalShipping}" var="exTotalShipping" />
				<c:set value="${exsub.realPrice+exRealPrice}" var="exRealPrice" />
        	</c:forEach>
        <div class="table_top">
        	<ul>
        		<li class="w408"><a href="/member/myhlOrder" class="back_btn">返回</a>调剂商品</li>
        		<li class="w100">单价</li>
        		<li class="w100">数量</li>
        		<li class="w100">总价</li>
        		<li class="w120">使用换领币</li>
        		<li class="w90">现金支付</li>
        		<li class="w100">操作</li>
        	</ul>
        </div>
        <c:forEach items="${subOrders}" var="ord">
        <div class="pro_box">
        	<c:set value="${ord.realPrice+realPrice}" var="realPrice" />  
        	<c:set value="${ord.totalShipping+totalShipping}" var="totalShipping" /> 
        	<c:set value="${ord.totalAdjustment+totalAdjustment}" var="totalAdjustment" />
        	<div class="pro_box_top"><p class="p1">匹配单号：${ord.subOrderId}</p><p class="p2">${ord.supplierName}</p><p class="p3">状态更新日期：<fmt:formatDate value="${ord.updateTime}" pattern="yyyy-MM-dd"/></p></div>
        	<c:forEach items="${ord.subOrderItems}" var="sub">
        	<c:set value="${sub.internalPurchasePrice}" var="rprice" />  
        	<%-- <c:set value="${totalAdjustment+rprice}" var="totalAdjustment" /> --%>
        	<div class="pro_con green_bg">
        		<table border="0" cellspacing="0" cellpadding="0">
        			<tr>
	        			<td class="w408">
	        				<dl>
	        					<dt><a href="<%=basePath %>${sub.productId}.html?skuId=${sub.skuId}&pageKey=huanling"><img src="${sub.image}" /></a></dt>
	        					<dd class="dd1"><a href="<%=basePath %>${sub.productId}.html?skuId=${sub.skuId}&pageKey=huanling">${sub.productName}</a></dd>
	        					<c:forEach items="${sub.proValues.keySet()}" var="key" varStatus="keyStatus">
		                              <dd> ${key}：${sub.proValues.get(key)} </dd>
		                       	</c:forEach>
		                               
	        					<%-- <dd>${sub.itemValues}</dd> --%>
	        				</dl>
	        			</td>
		        		<td class="w100">￥${sub.internalPurchasePrice}</td>
		        		<td class="w100">${sub.number}</td>
		        		<td class="w100">￥${rprice}</td>
		        		<td class="w120">${totalAdjustment}</td>
		        		<td class="w90">
		        			<c:if test="${ord.thirdPay!=null}">￥${ord.thirdPay}</c:if>
		        		</td>
		        		<td class="w100 bor">
		        			<a href="<%=basePath %>${sub.productId}.html?skuId=${sub.skuId}&pageKey=huanling">查看商品详情</a>
		        		</td>
	        		</tr>
        		</table>
        	</div>
        	</c:forEach>
        </div>
        </c:forEach>
  		<div class="zj_details">
  			<ul>
  				<li class="li1">运费：<em>￥${totalShipping}</em></li>
  				<li class="li2">总计：<span>¥${realPrice}+${totalAdjustment}<i><img src="<%=basePath %>images/huanlingbi_icon.png" /></i></span></li>
  				<li class="li1">结余： <em>¥${exRealPrice-realPrice}+${exTotalAdjustment-totalAdjustment}<i><img src="<%=basePath %>images/huanlingbi_icon_small.png" width="20px" height="20px" /></i></em></li>
  				<li class="li4">结余的换领币已退回您的账户，现金已退款</li>
  			</ul>
  		</div>
  		
  		<div class="ypp_tit"><a href="javascript:;">查看原匹配商品</a></div>
  		<div class="ypp_con">
  			<div class="table_top">
	        	<ul>
	        		<li class="w408">原匹配商品</li>
	        		<li class="w100">单价</li>
	        		<li class="w100">数量</li>
	        		<li class="w100">总价</li>
	        		<li class="w120">使用换领币</li>
	        		<li class="w90">现金支付</li>
	        		<li class="w100"></li>
	        	</ul>
	        </div>
	        <c:forEach items="${exSubOrders}" var="exsub">
		        <div class="pro_box">
		        	<div class="pro_box_top"><p class="p1">匹配单号：${exsub.subOrderId}</p><p class="p2">${exsub.supplierName}</p><p class="p3">状态更新日期：<fmt:formatDate value="${exsub.updateTime}" pattern="yyyy-MM-dd"/></p></div>
		        	<c:forEach items="${exsub.subOrderItems}" var="esub">
		        	<div class="pro_con yellow_bg">
		        		<table border="0" cellspacing="0" cellpadding="0">
		        			<tr>
			        			<td class="w408">
			        				<dl>
			        					<dt><a href="<%=basePath %>${esub.productId}.html?skuId=${esub.skuId}&pageKey=huanling"><img src="${esub.image}" /></a></dt>
			        					<dd class="dd1"><a href="<%=basePath %>${esub.productId}.html?skuId=${esub.skuId}&pageKey=huanling">${esub.productName}</a></dd>
			        					<dd> ${esub.itemValues} </dd>
			        				</dl>
			        			</td>
				        		<td class="w100">￥${esub.internalPurchasePrice}</td>
				        		<td class="w100">${esub.number}</td>
				        		<td class="w100">￥${esub.number*esub.internalPurchasePrice}</td>
				        		<td class="w120">${exTotalAdjustment}</td>
				        		<td class="w90">
				        			<c:if test="${exsub.thirdPay!=null}">￥${exsub.thirdPay}</c:if>
				        		</td>
				        		<td class="w100"></td>
			        		</tr>
		        		</table>
		        	</div>
		        	</c:forEach>
		        </div>
	        </c:forEach>
       		<div class="zj_details">
	  			<ul>
	  				<li class="li1">运费：<em>￥${exTotalShipping}</em></li>
	  				<li class="li2">总计：<span>¥${exRealPrice}+${exTotalAdjustment}<i><img src="<%=basePath %>images/huanlingbi_icon.png" /></i></span></li>
	  			</ul>
	  		</div>
  		</div>
  	
</div>
<!--right contont end-->
<div class="clear:after"></div>
</div>

<div class="clear"></div>
<%@ include file="../common/footer.jsp" %>
<script>
$(function(){
	$(".ypp_tit a").toggle(function(){
		$(this).css({"background":"url("+basePath+"images/ypp_tit_bg1.png) no-repeat right center"});
		$(".ypp_con").show();
	},function(){
		$(this).css({"background":"url("+basePath+"images/ypp_tit_bg.png) no-repeat right center"});
		$(".ypp_con").hide();
	})
})
</script>
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>