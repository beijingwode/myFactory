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
        <li class="current"><a>查看退货记录</a></li>
        <li><a href="/member/myrights/list">申请退货</a></li>
      </ul>
    </div>
    <div class="appraise_list_wrap" style="display:block;">
      <ul class="appraise_theme">
        <li class="r1">退货编号</li>
        <li class="r2">订单编号/订单商品</li>
        <li class="r3">申请时间</li>
        <li class="r4">订单金额/退款金额</li>
        <li class="r5">
          <div class="r_selected">
            <select id="status_box">
              <option value="none">全部</option>
              <option value="1" ${status == 1 ? 'selected' : ''}>完成</option>
              <option value="0" ${status == 0 ? 'selected' : ''}>处理中</option>
            </select>
          </div>
        </li>
        <li class="r6">操作</li>
      </ul>
      <ul class="Rights_cont">
        <c:forEach items="${page.list}" var="ro" >
        <li>
          <p class="return_num">${ro.returnOrderId}</p>
          <div class="order_num">
            <span>${ro.subOrderId}</span>
            <p>${ro.productName}</p>
          </div>
          <span class="r_time"><fmt:formatDate value="${ro.createTime}" pattern="yyyy-MM-dd"/> <br><fmt:formatDate value="${ro.createTime}" pattern="HH:mm:ss"/></span>
          <p class="r_clock"><fmt:formatNumber value="${ro.realPrice}" pattern="#0.00"/> <br><fmt:formatNumber value="${ro.returnPrice}" pattern="#0.00"/></p>
          <p class="r_finish">
              <c:if test="${ro.status == 1}">
                完成
              </c:if>
              <c:if test="${ro.status == 0}">
                  <span class="red">处理中</span>
              </c:if>
              <c:if test="${ro.status == -1}">
                  <span class="red">失败</span>
              </c:if>
          </p>
          <p class="r_look"><a href="/member/toReturnOrder?subOrderId=${ro.subOrderId}">查看</a></p>
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
<script type="text/javascript">
    $("#status_box").change(function(){
        if($(this).val()!=='none'){
            window.location.href='${basePath}member/myrights?status='+$(this).val();
        } else {
            window.location.href='${basePath}member/myrights';
        }
    });
</script>
</body>
</html>
