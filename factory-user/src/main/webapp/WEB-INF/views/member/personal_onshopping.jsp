<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-我收藏的商品</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal_onshopping.js"></script>
<body>
<!--top begin-->
<%@ include file="../common/header_03.jsp"%>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
<!--left nav-->
<%@ include file="menu.jsp"%>
<!--left nav end-->
<!--right content-->
	<div class="Me_content">
    	<div class="on_title">
        	<span class="onlt">我关注的商品</span>
            <div class="onrt">
            	<span>共 ${page.total} 条</span> 
				<span>${page.pageNum}/${page.pages}</span>
				<c:if test="${page.pageNum>1}">
					<span><a href="<%=basePath %>member/personalProduct">首页</a></span>
					<span><a href="<%=basePath %>member/personalProduct?pages=${page.pageNum-1}">前一页</a></span>
				</c:if>
				<c:if test="${page.pageNum!=page.pages && page.pages>1}">
					<span><a href="<%=basePath %>member/personalProduct?pages=${page.pageNum+1}">后一页</a></span>
					<span><a href="<%=basePath %>member/personalProduct?pages=${page.pages}">末页</a></span>
				</c:if>
            </div>
        </div>
        <div class="onshopping_list">
        	<ul>
        		<c:forEach var="product" items="${page.list}">
            	<li>
                	<div class="on_goods">
                		<span><input type="checkbox" value="${product.id}"></input></span>
                        <div class="on_pho">
                       	  <c:choose>
							<c:when test="${product.saleKbn == 1}">
							  <div class="picon"><img src="../images/picon1.png" /></div>
						    </c:when>
							<c:when test="${product.saleKbn == 2}">
							  <div class="picon"><img src="../images/picon_c1.png" /></div>
						    </c:when>
						    <c:when test="${product.saleKbn == 4}">
							  <div class="picon"><img src="../images/picon_z1.png" /></div>
						    </c:when>
							<c:when test="${product.saleKbn == 5}">
							  <div class="picon"><img src="../images/picon_t1.png" /></div>
						    </c:when>
					  	   </c:choose>
                        	<a href="<%=basePath %>${product.id}.html?pageKey=collection"><img src="${product.image}" width="78" height="78" alt="${product.image}"></a>
                        </div>
                        <p><a href="<%=basePath %>${product.id}.html?pageKey=collection">${product.fullName}</a></p>
                    </div>
				    <c:set var="fucoin" value="${product.maxprice>maxBenefit?maxBenefit:product.maxprice}"></c:set>
                    <div class="on_price">¥&nbsp;${product.showPrice}+${fucoin}券</div>
                    <%-- <div class="on_h">
                    	<c:if test="${product.allnum>0}">有货</c:if>
                    	<c:if test="${product.allnum<1}"><span class="red">无货</span></c:if>
                    </div> --%>
                    <div class="on_operate">
                    	<!-- <p class="on_btn01"><a href="#">加入购物车</a></p> -->
                        <p class="on_btn02"><a href="javascript:void(0)" onclick="canelCollectionProduct(${product.id});">删除</a></p>
                    </div>
                </li>
                </c:forEach>
            </ul>
        </div>
        <div class="on_title">
        	<span class="onselected"><input class="on_checkbox" type="checkbox">全选</span>
            <div class="on_cansel"><a href="javascript:void(0)" onclick="canelMore();">删除</a></div>
            <!-- <div class="on_car"><a href="#">加入购物车</a></div> -->
            <div></div>
            <div class="onrt">
            	<span>共 ${page.total} 条</span> 
				<span>${page.pageNum}/${page.pages}</span>
				<c:if test="${page.pageNum>1}">
					<span><a href="<%=basePath %>member/personalProduct">首页</a></span>
					<span><a href="<%=basePath %>member/personalProduct?pages=${page.pageNum-1}">前一页</a></span>
				</c:if>
				<c:if test="${page.pageNum!=page.pages && page.pages>1}">
					<span><a href="<%=basePath %>member/personalProduct?pages=${page.pageNum+1}">后一页</a></span>
					<span><a href="<%=basePath %>member/personalProduct?pages=${page.pages}">末页</a></span>
				</c:if>
            </div>
        </div>
    </div>
<!--right contont end-->
<div class="clear:after"></div>
</div>

<div class="clear"></div>
<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
<!--help begin-->
<%@ include file="../common/footer.jsp" %>
<!--footer end-->
  <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>
