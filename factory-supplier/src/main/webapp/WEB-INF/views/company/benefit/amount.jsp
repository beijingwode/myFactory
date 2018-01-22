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
<script type="text/javascript">
$(document).ready(function(){
	//加载页面，控制左边的菜单
	$("#ent_amount").addClass("curr");

});
</script>
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
            <a href="javascript:void(0);">福利额度总览</a>
        </div>
        <div class="r-content">
        	<div class="a-title">
        	<c:out value="${info.curYear }"/>年度第
        	<c:choose>  
	        <c:when test="${info.curSeason=='1'}">一</c:when>  
	        <c:when test="${info.curSeason=='2'}">二</c:when>  
	        <c:when test="${info.curSeason=='3'}">三</c:when>  
	        <c:otherwise>四</c:otherwise>  
   			</c:choose> 
        	季度福利额度总览
        	</div>
            <table class="a-table" cellpadding="0" cellspacing="0">
                <thead>
                    <tr>
                        <th>项目</th>
                        <th>数额</th>
                        <th>单位</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>本季度全部额度</td>
                        <td class="money-right"><fmt:formatNumber type="number" maxFractionDigits="2"  value="${info.allTicketSum }" /> 
                        </td>
                        <td>元</td>
                    </tr>
                    <tr>
                        <td>上季度剩余额度</td>
                        <td class="money-right"><fmt:formatNumber type="number" maxFractionDigits="2"  value="${info.lastTicketSum }" /></td>
                        <td>元</td>
                    </tr>
                    <tr>
                        <td>本季度划入福利额度</td>
                        <td class="money-right"><fmt:formatNumber type="number" maxFractionDigits="2"  value="${info.currentTransferSum }" /></td>
                        <td>元</td>
                    </tr>
                    <tr>
                        <td>本季度新增额度</td>
                        <td class="money-right"><fmt:formatNumber type="number" maxFractionDigits="2"  value="${info.currentTicketSum }" /></td>
                        <td>元</td>
                    </tr>
                    <tr>
                        <td>已发放额度</td>
                        <td class="money-right"><fmt:formatNumber type="number" maxFractionDigits="2"  value="${info.giveTicketSum }" /></td>
                        <td>元</td>
                    </tr>
                    <tr>
                        <td>可发放额度</td>
                        <td class="money-right"><fmt:formatNumber type="number" maxFractionDigits="2"  value="${info.allTicketSum-info.giveTicketSum - info.transfeTicketSum }" /></td>
                        <td>元</td>
                    </tr>
                    <tr>
                        <td>已划拨额度</td>
                        <td class="money-right"><fmt:formatNumber type="number" maxFractionDigits="2"  value="${info.transfeTicketSum }" /></td>
                        <td>元</td>
                    </tr>
                    <tr>
                        <td>可划拨额度</td>
                        <td class="money-right"><fmt:formatNumber type="number"  value="${canTransfer }"  maxFractionDigits="2" /></td>
                        <td>元</td>
                    </tr>
                </tbody>
            </table>          
            <div class="a-title">现金额度总览<span>（累积至今的数据）</span></div>
            <table class="a-nummer" cellpadding="0" cellspacing="0">
                <tbody>
                    <tr>
                        <td>现金总额  (至今)</td>
                        <td class="money-right"><fmt:formatNumber type="number" value="${info.allCashSum }"  maxFractionDigits="2" /></td>
                        <td>元</td>
                    </tr>
                    <tr>
                        <td>可发放现金（至今）</td>
                        <td class="money-right"><fmt:formatNumber type="number" value="${info.allCashSum - info.giveCashSum }"  maxFractionDigits="2" /></td>
                        <td>元</td>
                    </tr>
                    <tr>
                        <td>已发放现金（至今）</td>
                        <td class="money-right"><fmt:formatNumber type="number" value="${info.giveCashSum }"  maxFractionDigits="2" /></td>
                        <td>元</td>
                    </tr>
                </tbody>
            </table>  
            <div class="r-btn-box">
            	<div class="r-btn-s"><a href="${basePath}/company/benefit/getApplyBenefitRecord.html">申请额度</a></div>
                <div class="r-btn-s"><a href="${basePath}/company/benefit/toSaveCash.html">现金储值</a></div>
                <div class="r-btn-s"><a href="${basePath}/company/benefit/page.html">发放福利</a></div>
                <div class="r-btn-s"><a href="${basePath}/company/benefit/entBenefitFlow.html">查看流水</a></div>
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	
</body>
</html>
