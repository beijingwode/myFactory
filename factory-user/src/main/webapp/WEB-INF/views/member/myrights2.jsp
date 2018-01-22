<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<!doctype html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的福利--我的维权</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
   
</head>
<body>

<!--top begin-->
<%@ include file="../common/header_03.jsp" %>
<!--top end-->

<div class="Me_wrap">
  <!--left nav-->
  <%@ include file="menu.jsp" %>
  <!--left nav end-->

  <div class="Me_content">
    <p class="P_title">我的维权</p>
    <div class="appraise_nav">
      <ul>
        <li><a href="/member/myrights">查看退货记录</a></li>
        <li class="current"><a>申请退货</a></li>
      </ul>
    </div>
    <div class="appraise_list_wrap" style="display:block;">
      <ul class="appraise_theme">
        <li class="d1">订单编号</li>
        <li class="d2">订单商品</li>
        <li class="d3">下单时间</li>
        <li class="d4">操作</li>
      </ul>
      <ul class="Rights_cont">
        <c:forEach var="suborder" items="${page.list }">
        <li>
          <p class="back_num">${suborder.subOrderId }</p>
          <div class="R-product-wrap">
            <c:forEach var="subOrderItem" items="${suborder.subOrderItems }" varStatus="vs">
            <div class="R-product">
              <span class="R-s-img">
          		<c:if test="${subOrderItem.saleKbn==1 }">
           		<div class="picon"><img src="/images/picon2.png" /></div>
           		</c:if>
          		<c:if test="${subOrderItem.saleKbn==2 }">
           		<div class="picon"><img src="/images/picon_c2.png" /></div>
           		</c:if>
           		<c:if test="${subOrderItem.saleKbn==4 }">
           		<div class="picon"><img src="/images/picon_z2.png" /></div>
           		</c:if>
          		<c:if test="${subOrderItem.saleKbn==5 }">
           		<div class="picon"><img src="/images/picon_t2.png" /></div>
           		</c:if>
              	<a href="/${subOrderItem.productId }.html?pageKey=order" target="_blank"><img src="${subOrderItem.image }" width="78" height="78" alt="Me-order-img"></a>
              </span>
              <p class="p1"><c:choose>
                <c:when test="${fn:length(subOrderItem.productName) > 30}">
                  <c:out value="${fn:substring(subOrderItem.productName, 0, 30)}..." />
                </c:when>
                <c:otherwise>
                  <c:out value="${subOrderItem.productName}" />
                </c:otherwise>
              </c:choose>
              </p>
            </div>
            </c:forEach>
          </div>
          <span class="r_date"><fmt:formatDate pattern="yyyy-MM-dd" value="${suborder.createTime}" /><br><fmt:formatDate pattern="HH:mm:ss" value="${suborder.createTime}" /></span>
          <p class="r_back"><a href="/member/toReturnOrder?subOrderId=${suborder.subOrderId}">退货</a></p>
        </li>
        </c:forEach>
      </ul>
    </div>
    <jsp:include page="../common/page.jsp" flush="true">
      <jsp:param name="page" value="${page}"/>
    </jsp:include>
  </div>
  <!--right contont end-->
  <div class="clear:after"></div>
</div>

<div class="clear"></div>
</div>

<%@ include file="../common/footer.jsp" %>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
 <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/member.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
</body>
</html>
