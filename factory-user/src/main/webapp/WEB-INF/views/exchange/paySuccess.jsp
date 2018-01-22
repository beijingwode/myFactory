<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利_支付完成</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/style.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/loginpopup.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/hl_pay.css">
    
	<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
	<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
	<script type="text/javascript" src="<%=basePath %>resources/js/cart.js"></script>
</head>
<body>
<!--top begin-->
<%@ include file="../common/header_03.jsp" %> 
<!--top end-->
<!--content begin-->
<div class="mainbody">
   <div class="pay_con">
   		<div class="pay_con_lt">
   			<div class="p1">换领匹配中</div>
   			<div class="p2">请您留意查看调剂清单<br/>若匹配失败，将依照调剂清单为您调剂商品。</div>
   			<dl>
   				<dt><a href="javascript:;"><img src="${item.image }" /></a></dt>
   				<dd class="dd1"><a href="javascript:;">${item.productName }</a></dd>
   				<dd><span>${item.itemValues }</span><em>X ${item.number }</em></dd>
   			</dl>
   		</div>
   		<div class="see_more"><a href="/huanling.html" target="_blank">浏览更多商品</a><a href="/member/myrenewal" class="see_list" target="_blank">查看调剂清单<c:if test="${cnt>0}"><span>${cnt}</span></c:if></a></div>
   </div>
   <!-- 其他换领 -->
   <c:if test="${!empty productList }">
   <div class="hl_box">
   		<div class="tit">其他换领</div>
   		<div class="hl_con">
   			<ul>
				<c:forEach items="${productList }" var="product">
   				<li>
   					<dl>
   						<dt><a href="/${product.productId}.html?skuId=${product.minSkuId}&pageKey=huanling" target="_blank"><img src="${product.image }" /></a></dt>
   						<dd class="dd1"><a href="/${product.productId}.html?skuId=${product.minSkuId}&pageKey=huanling">${product.name}</a></dd>
   						<dd class="dd2">换领价：${product.salePrice}</dd>
   						<dd class="dd3"><a href="/shop/${product.shopId }" target="_blank">${product.shopName}</a></dd>
   					</dl>
   				</li>
				</c:forEach>
   			</ul>
   		</div>
   </div>
   </c:if>
</div>
<!--content end-->
<!--footer begin-->
<%@ include file="../common/footer.jsp" %> 
<!--footer end-->
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>