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
<%@ include file="/commons/js.jsp" %>
<style type="text/css">
.st-btn-disabled { float:left; margin:30px 0 0 58px; border:1px solid #999; background:#b5b5b5; text-align:center; width:160px; height:44px; }
.st-btn-disabled a:link,.st-btn-disabled a:visited { display:block; font:18px/44px "Microsoft YaHei"; color:#fff; }
.st-btn-disabled a:hover {color:#fff;}
</style>
</head>
<script type="text/javascript">
var jsResult = "${result}";

</script>
<script type="text/javascript" src="<%=static_resources %>js/company_cooperate_stamps.js"></script>
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
            <a href="javascript:void(0);">划拨内购券额度</a>
        </div>
        <div class="r-content" style="width:958px;">
	        	<div class="st-extra">
	            	<div class="st-my">
	                	<span>可划拨额度：</span>
	                	<input type="hidden" id="can" value="<fmt:formatNumber type="number" value="${canTransfer }" pattern="0" />">
	                    <span class="red"><fmt:formatNumber type="number" value="${canTransfer }" maxFractionDigits="0" />元</span>
	                </div>
	                <div class="st-btn"><a href="#"  >划拨福利</a></div>
	            </div>
            <div class="st-cont" style="width:958px;display:none">
                <c:if test="${!empty ent_info }">
            	<ul class="st-list-title">
                	<li>
                    	<span>当前可划拨总额度：</span>
                        <span class="red"><fmt:formatNumber type="number" value="${canTransfer }" pattern="0" />元</span>
                    </li>
                    <li>
                    	<span>当前划拨额度：</span>
                        <span class="red" id="transfe">0元</span>
                    </li>
                    <li>
                    	<span>剩余额度：</span>
                        <span class="red" id="residue"><fmt:formatNumber type="number" value="${canTransfer }" pattern="0" />元</span>
                    </li>
                </ul>
			<form action="${basePath}/company/transfer/transferBenefit.json" method="post" id="transferForm">
	                <ul class="company-list">
	                <c:forEach items="${ent_info}" var="i">
	                	<li>
	                    	<span>${i.enterpriseName}</span>
	                    	 <div class="com-r">
	                    	<input type="hidden" value="${i.enterpriseId}" name="ent_id">
	                        <input class="welfare_input" maxlength="10" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)" type="text" name="abs" placeholder="请输入划拨额度">
	                    	 <div class="error"></div>
                        </div>
	                    </li>
	                </c:forEach>
	                </ul>
	                <div class="st-sort-btn">
	                	<a href="#" class="cansel-btn" id="stamps_cancel">取消</a>
	                    <a href="#" class="cansel-btn" style="margin-left:36px" id="stamps_sub_but">确认</a>
	                </div>
                </c:if>
            </div>            
        </div>
    </div>
    <!--right end-->
	</div>
	<!-- 余额不足 begin-->
	<div class="public_popup" id="insufficient_balance" style="height:140px;" >
    <div class="pop-title-2">
        <div class="suceess-close-btn" id="insufficient_balance-close"><i class="suceess-close-icon"></i></div>
    </div>
    <div class="pop-cont" style="padding-top:40px;">
    	<p>余额不足！</p>
    </div>
	</div>
	<!-- 余额不足 end-->
	<!-- 背景弹出框 begin -->
		<div class="popup_bg"></div>
	<!-- 背景弹出框 end -->
	
	<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	
</body>
</html>
