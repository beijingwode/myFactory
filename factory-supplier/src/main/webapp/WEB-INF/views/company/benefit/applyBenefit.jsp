<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>商家中心--员工福利</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/welfare.css">

<%@ include file="/commons/js.jsp" %>

</head>

<body>
	<!-- top start -->
	<%@ include file="/commons/header.jsp" %>
	<!-- top end -->

	<div id="content" class="clear">
		<!-- left menu start -->
		<%@ include file="/commons/leftmenu.jsp"%>
		<!-- left menu end -->
		  <!--right begin-->
    <div class="right">
    	<div class="position">
        	<span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/company/enterprise/getEnterpriseInfo.html">员工福利</a><em>></em>
            <a href="javascript:void(0);">福利额度申请</a>
        </div>
        <div class="r-content">
        	<div class="p-info">
            	<span>
            	<c:out value="${curYear }"/>年度第
	        	<c:choose>  
		        <c:when test="${curSeason=='1'}">一</c:when>  
		        <c:when test="${curSeason=='2'}">二</c:when>  
		        <c:when test="${curSeason=='3'}">三</c:when>  
		        <c:otherwise>四</c:otherwise>  
	   			</c:choose> 
        		季度福利额度：
            	</span>
            	<c:choose>  
		        <c:when test="${info ==null}"><div class="info">尚未申请福利，点击<a href="javascript:void(0);" id="apply" onclick="apply()">立即申请</a>获得额度</div></c:when>  
		    	<c:when test="${info.status == 1}"><div class="info" >申请已提交，等待审批中…… </div></c:when> 
		    	<c:when test="${info.status == 2}"><div class="info" ><span><fmt:formatNumber type="number" value="${info.applyLimit }"  maxFractionDigits="0" />元 </span></div></c:when> 
		        <c:otherwise><div class="info" >申请被拒绝</div></c:otherwise>  
	   			</c:choose> 
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--弹出框 begin-->
<div class="popup_bg"></div>
<div class="public_popup" id="applay_pop">
	<div class="close-btn" id="applay-close"><i class="close-icon"></i></div>
	<div class="pop-cont">
    	<p>您已提出申请，请等待审核通过</p>
        <div class="ture-btn"><a href="#">确认</a></div>
    </div>
</div>
<!--弹出框 end-->
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	
<script>
var jsBasePath = '${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/company_benefit_applyBenefit.js"></script>
</body>
</html>
