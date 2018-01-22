<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../common/include.jsp" %>
<!doctype html>
<html>
<head>
  <meta http-equiv="content-type" content="text/html; charset=utf-8">
  <meta name="description" content="">
  <meta name="keywords" content="">
  <title>我的福利</title>
  <link rel="stylesheet" type="text/css" href="${basePath }css/common.css">
  <link rel="stylesheet" type="text/css" href="${basePath }css/kong_shop.css?124">
 <script type="text/javascript" src="${basePath }resources/js/jquery1.8.0.js"></script>
   
  <script type="text/javascript" src="${basePath }resources/js/scrollbar.js"></script>
  <script type="text/javascript" src="${basePath }resources/js/application.js"></script>
  <script type="text/javascript" src="${basePath }resources/js/shopping.js"></script>
</head>

<body>
<!--top begin-->
<%@ include file="/common/header.jsp" %>
<!--top end-->

<div class="no_con">
  <span class="n_c_img"><img src="${basePath }images/kong_q.png"></span>
  <div class="n_c_text">
    <p>抱歉哦！没有找到与“<span>${keyword}</span>”相关的商品。或许你可以去看看：</p>
    <p><a href="${basePath}">返回首页</a> <!-- | <a href="javascript:history.back();">返回上一页</a></p> -->
  </div>
  <div class="clear"></div>
</div>
<!--content end-->
<!------------热销推荐---------------->
<div class="rx_list">
  <p class="list_title">热销商品</p>
  <div class="list_ccon">
    <ul>
      <c:forEach items="${productList }" var="product">
        <li class="at">
          <div class="all_product_pho"><a href="/${product.id}.html?pageKey=search" target="_blank" title="${product.fullName}"><img src="${product.image }" height="186" alt="${product.fullName}"></a></div>          
          <h2><a href="/${product.id}.html?pageKey=search" title="${product.fullName}">${product.fullName}</a></h2>
      	  ${product.showPrice}
      	  <p  class="p2">电商价：￥${product.minprice}</p>
        </li>
      </c:forEach>
      <div class="clear"></div>
    </ul>

  </div>
</div>

<!--footer begin-->
<%@ include file="/common/footer.jsp" %>
<!--footer end-->
 <script type="text/javascript" src="${basePath }resources/js/top_ewm.js"></script>
</body>
</html>