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
<script language="javascript" type="text/javascript" src="<%=static_resources %>/resources/My97DatePicker/WdatePicker.js"></script>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">        
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
        	<li class="curr"><a href="javascript:;">活动申请中心</a></li>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('myPromotionList')}">
            <li><a href="${basePath}/promotion/mylist.html">申请中的活动</a></li>
            </c:if>
        </ul>
    </div>
    <!--left end-->
    
    <!--right begin-->
    <div class="right">    	
        <div class="merchant_info">
        	<div class="seckill-title">
            	<ul class="seckill-act">
                	<li>活动名称</li>
                    <li>报名截止</li>
                    <li>成功报名</li>
                    <li>分类</li>
                </ul>
                <c:forEach var="item" items="${promotionList}">
                <ul class="seckill-cont">
                	<li><a href="${basePath}/promotion/toPromotionInfo.html?id=${item.id}">${item.name}</a></li>
                    <li>当天截止</li>
                    <li>${item.joinTotal}</li>
                    <li><a class="sec" href="javascript:;" onclick="WdatePicker({
                    <c:if test="${!empty promotionProductList}">
                    disabledDates:[
                    <c:forEach var="item2" varStatus="status" items="${promotionProductList}">
                    <c:if test="${status.first}">'<fmt:formatDate value="${item2.joinStart}" pattern="yyyy-MM-dd"/>'</c:if> 
                    <c:if test="${!status.first}">,'<fmt:formatDate value="${item2.joinStart}" pattern="yyyy-MM-dd"/>'</c:if> 
                    </c:forEach>
                    ],
                    </c:if>
                    isShowToday:false,autoPickDate:false,isShowClear:false,minDate:'${begin}',maxDate:'${end}',el:'sj',onpicked:function(){toNext();}})">报名</a>
                    </li>
                </ul>
                </c:forEach>
                <form id="sub_form2" action="${basePath}/promotion/toConfirm.html" method="post">
                	<c:if test="${!empty promotionList}">
		            	<input type="hidden" name="promotionId" value="${promotionList.get(0).id}">
		            </c:if>
               		<input type="hidden" id="sj" name="bmTime">
                </form>
            </div>
			<wodepageform:PageFormTag pageSize="${result.size}"  totalPage="${result.totalPage}" currentPage="${result.page}" url=""/>
            <form id="sub_form" action="${basePath}/promotion/list.html" method="post">
		    	 <input type="hidden" id="pages" name="pages" value="${pages}"/>
		     	 <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
		    </form>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<script type="text/javascript">

$(document).ready(function(){
	selectedHeaderLi("hdgl_header");
});
/*** 表单提交*/
function formSubmit(page){
	if(page!=undefined){
		$("#pages").val(page);
	}else{
		$("#pages").val(1);
	}
	$("#sub_form").submit();
}

/*** 快速跳转*/
function gotoPage(){
	var pagenum = $("#pagenum").val();
	formSubmit(pagenum);
}

function toNext(){
	$("#sub_form2").submit();
};
</script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>