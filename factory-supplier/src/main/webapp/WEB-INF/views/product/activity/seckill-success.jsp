<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>我的网商家中心-活动列表</title>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%@ include file="/commons/js.jsp" %>
</head>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">        
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
			<c:if test="${userSession.type != 3 || userSession.hasAuth('promotionList')}">
				<li class="curr"><a href="${basePath}/promotion/list.html">活动申请中心</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('myPromotionList')}">
				<li><a href="${basePath}/promotion/mylist.html">申请中的活动</a></li>
			</c:if>
        </ul>
    </div>
    <!--left end-->
    
    <!--right begin-->
    <div class="right">    	
        <div class="merchant_info">
        	<div class="process step_4"></div>
            <div class="s-min-height">
                <div class="s-success">
                    <div class="s-success-cont">恭喜您，秒杀商品设置成功！</div>
                </div>
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->


<script type="text/javascript" src="<%=static_resources %>js/product_activity_seckillSuccess.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>