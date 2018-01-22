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
<title>我的福利_支付成功</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/shoppingcart.css">
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
    
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/cart.js"></script>
<style>
.interesting-img ul li p {height:18px;line-height:18px;overflow:hidden; font:12px "Microsoft YaHei"; color:#434343; margin:0px 0 0 16px; text-align:left;}
.interesting-img ul li  .p1 span{
	color:#ff6161;

}
.interesting-img ul li  .p1 em{
	color:#474646;
    font-style:normal;
    margin-left:10px;
}


.interesting-img ul li  .p2 {
	font: 12px "Microsoft YaHei";
	color: #7d7d7d;
	
}
.interesting-img ul li  .p3 a {
	height:18px;line-height:18px;display:block;overflow:hidden;
	font: 12px "Microsoft YaHei";
	color: #7d7d7d;
	text-align:left;
}
</style>
</head>
<body>
<!--top begin-->
<%@ include file="../common/header_03.jsp" %> 
<!--top end-->
<!--content begin-->
<div id="middle">
	<div class="middle_content">
    	<div class="pay-seccess">
        	<div class="pay-title"><i class="icon-pay"></i><span>您已经付款成功</span></div>
        	<%-- <div class="pay-price">付款金额<span>
        	<c:if test="${!empty order }">
        		<fmt:formatNumber value="${order.realPrice }" type="currency" pattern="￥0.00"/>
        	</c:if>
            <c:if test="${!empty suborder }">
            	<fmt:formatNumber value="${suborder.realPrice }" type="currency" pattern="￥0.00"/>
            </c:if>
        	</span></div> --%>
        	<div class="pay-price">${notice }</div>
            <div class="pay-btn">
                <a href="/member/myorders">前往我的订单</a>
            </div>
        </div>
        <c:if test="${!empty productList }">
        	 <div class="my-attention">
	        	<h3>您可能感兴趣</h3>
	            <div class="cart_show pay-wrap">
	            	<div class="interesting-img">                        
	                    <ul>
	                    	<c:forEach items="${productList }" var="product">
	                            <li>
	                            	<a href="/${product.id}.html" target="_blank"><img src="${product.image }" height="129" width="129"></a>
	                            	<p class="p3"><a href="/${product.id}.html">${product.fullName}</a></p>                            		
			                		${product.showPrice}
			                		<p  class="p2">电商价：￥${product.minprice}</p>
	                            </li>
           					</c:forEach>
	                    </ul>    
	                </div>            
	            </div>
        </div>
        </c:if>
    </div>
</div>
<!--content end-->
<!--footer begin-->
<%@ include file="../common/footer.jsp" %> 
<!--footer end-->
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>