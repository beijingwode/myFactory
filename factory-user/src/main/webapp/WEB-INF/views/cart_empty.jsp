<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
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
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的网-我的购物车</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/public.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/shoppingcart.css">
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
   
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/cart_empty.js"></script>
<body>
<!--top begin-->
<%@ include file="common/header_01.jsp" %> 
<!--top end-->

<!--content begin-->
<div id="middle">
	<div class="middle_content">
    	<div class="cart-empty">
            <div class="message">
                <ul>
                    <li>购物车内暂时没有商品&nbsp;&nbsp;<a href="/index.html" class="ftx-05">去购物&gt;</a></li>
                    <li class="login">
                    	<a href="/login.html" class="btn-1">登录</a>
                    </li>
                </ul>
            </div>	
        </div>
        <div class="my-attention">
        	<h2>热销商品</h2>
            <div class="cart_show">                        
                <div class="cart_btn">
                    <span class="prev">上一页</span>
                    <span class="next">下一页</span>
                </div>
                <div class="cart_content">
                    <div class="cart_content_list">
                        <ul>
                        	<c:forEach items="${productList }" var="product">
	                            <li>
	                            	<a href="/${product.id}.html" target="_blank"><img src="${product.image }" width="170" height="170"></a>
	                    			<p class="p3"><a href="/${product.id}.html">${product.fullName}</a></p>                            		
			                		${product.showPrice}
			                		<p  class="p2">电商价：￥${product.minprice}</p>
	                                
	                            </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="common/footer.jsp" %>
<!--footer end-->
 <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>