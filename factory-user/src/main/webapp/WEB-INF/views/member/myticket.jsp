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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-我的内购券</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
   
<script type="text/javascript" src="<%=basePath %>resources/js/member.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
</head>
<body>
<!--top begin-->
<%@ include file="../common/header_03.jsp" %>
<!--top end-->
<!---------------------------------------åå®¹------------------------------------------------->
<div class="Me_wrap">
<!--left nav-->
<%@ include file="menu.jsp" %>
<!--left nav end-->
<!--right content-->
		<div class="Me_content">
            <c:if test="${!empty page.list }">
		<div class="benifit-cont-new">
            <h2 class="benifit-title-new">内购券明细</h2> 
            <ul class="P-benifit-new">
            <c:forEach var="ticket" items="${page.list }">
                <li>
                    <div class="P-benifit-01">
                    	<c:if test="${ticket.opCode=='216' || ticket.opCode=='204' || ticket.opCode=='205' || ticket.opCode=='290' || ticket.opCode=='292' || ticket.opCode=='296' || ticket.opCode=='294' || ticket.opCode=='222' || ticket.opCode=='220'}">
                        <span class="P-fa P-green">${ticket.iconUrl}</span>
                    	</c:if>
                    	<c:if test="${ticket.opCode=='217' || ticket.opCode=='203' || ticket.opCode=='207' || ticket.opCode=='221' || ticket.opCode=='298'}">
                        <span class="P-fa P-red">${ticket.iconUrl}</span>
                    	</c:if>
                    	<c:if test="${ticket.opCode=='202' || ticket.opCode=='206'}">
                        <span class="P-fa P-light-g">${ticket.iconUrl}</span>
                    	</c:if>
                        <span class="P-cont-new">${ticket.note}</span>
                    </div>
                    <span class="P-benifit-02">${ticket.value} <fmt:formatNumber value="${ticket.amount }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></span>
                    <span class="P-benifit-02"><fmt:formatDate value="${ticket.opDate}" pattern="yyyy-M-d"/></span>
                </li>
                 <!-- <div class="P-benifit-01">
                        <span class="P-fa P-light-g">赠</span>
                        <span class="date-new">2015-8-12</span>
                        <span class="P-cont-new">企业向您发放内购券</span>
                    </div>
                    <span class="P-benifit-02">+ 3000</span>
                    <a class="P-benifit-03" href="#">查看详情</a> -->
             </c:forEach>
            </ul>             
        </div>
        <div style="text-align: right;">
	        <jsp:include page="../common/page.jsp" flush="true">
            	<jsp:param name="page" value="${page}"/>
      		</jsp:include>
      	</div>
      	</c:if>
    </div>
<!--right contont end-->
<div class="clear:after"></div>
</div>

<div class="clear"></div>
<%@ include file="../common/footer.jsp" %>
 <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>