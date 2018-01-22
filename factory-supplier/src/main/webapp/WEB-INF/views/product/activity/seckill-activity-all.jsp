<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>我的网商家中心-活动列表</title>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
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
            <c:if test="${userSession.type != 3 || userSession.hasAuth('promotionList')}">
                <li><a href="${basePath}/promotion/list.html">活动申请中心</a></li>
            </c:if>
            <li class="curr"><a href="javascript:;">申请中的活动</a></li>
        </ul>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
        <div class="merchant_info">
            <ul class="seckill-cont-all btn-pad">
                <li><a href="javascript:;" class="sec" onclick="toQuery('');">全部活动</a></li>
                <li><a href="javascript:;" class="sec" onclick="toQuery('2');">申请成功</a></li>
                <li><a href="javascript:;" class="sec" onclick="toQuery('-1');">申请失败</a></li>
                <li><a href="javascript:;" class="sec" onclick="toQuery('0');">等待审核</a></li>
                <!-- <li><a href="javascript:;" class="sec" onclick="toQuery('1');">审核中</a></li> -->
            </ul>
            <div class="seckill-title">
                <ul class="seckill-act-all">
                    <li>活动名称</li>
                    <li>商品名称</li>
                    <li>活动开启时间</li>
                    <li>成功报名</li>
                    <li>状态</li>
                    <li>操作</li>
                </ul>
                <c:forEach var="item" items="${promotionList}">
                    <ul class="seckill-cont-all">
                        <li>
                            <a href="${basePath}/promotion/toPromotionInfo.html?id=${item.promotionId}">${item.promotion.name}</a>
                        </li>
                        <li style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"
                            title="${item.fullName}">${item.fullName}</li>
                        <li><fmt:formatDate value="${item.joinStart}" pattern="yyyy-MM-dd"/></li>
                        <li>${item.promotion.joinTotal}</li>
                        <!--
                    <c:if test="${item.status == -2}">
                    	<li>已退出</li>
                    	<li></li>
                    </c:if>
                    <c:if test="${item.status == -1}">
                    	<c:if test="${!empty item.reviewedDate}">
	                    	<c:if test="${ now.time - item.reviewedDate.time < 0}">
	                    		<li>审核中</li>
	                    		<li><a href="javascript:;" class="sec" onclick="closeAlert(${item.id});">退出报名</a></li>
	                    	</c:if>
	                    	<c:if test="${ now.time - item.reviewedDate.time >= 0}">
	                    		<li>失败</li>
		                    	<li><a href="javascript:;" class="sec" onfocus="WdatePicker({
		                    	<c:if test="${!empty promotionProductList}">
			                    disabledDates:[
			                    <c:forEach var="item2" varStatus="status" items="${promotionProductList}">
			                    <c:if test="${status.first}">'<fmt:formatDate value="${item2.joinStart}" pattern="yyyy-MM-dd"/>'</c:if> 
			                    <c:if test="${!status.first}">,'<fmt:formatDate value="${item2.joinStart}" pattern="yyyy-MM-dd"/>'</c:if> 
			                    </c:forEach>
			                    ],
			                    </c:if>
		                    	isShowToday:false,autoPickDate:false,isShowClear:false,minDate:'${begin}',maxDate:'${end}',el:'sj',onpicked:function(){toNext();}})">报名</a></li>
	                    	</c:if>
                    	</c:if>
                    	<c:if test="${empty item.reviewedDate}">
                    		<li>审核中</li>
	                    	<li><a href="javascript:;" class="sec" onclick="closeAlert(${item.id});">退出报名</a></li>
                    	</c:if>
                    </c:if>
                    <c:if test="${item.status == 0 || item.status == 1 }">
                    	<li>等待审核</li>
                    	<li class="secbtn"><a href="${basePath}/promotion/productSet.html?id=${item.skuId }&promotionProductId=${item.id}" class="sec">修改报名</a><a href="javascript:;" class="sec" onclick="closeAlert(${item.id});">退出报名</a></li>
                    </c:if>
                    <c:if test="${item.status == 2 }">
                    	<c:if test="${!empty item.reviewedDate}">
	                    	<c:if test="${ now.time - item.reviewedDate.time < 0}">
	                    		<li>审核中</li>
	                    		<li><a href="javascript:;" class="sec" onclick="closeAlert(${item.id});">退出报名</a></li>
	                    	</c:if>
	                    	<c:if test="${ now.time - item.reviewedDate.time >= 0}">
	                    		<li>成功</li>
	                    		<li><a href="javascript:;" class="sec" onclick="closeAlert2(${item.id});">详细</a></li>
	                    	</c:if>
                    	</c:if>
                    	<c:if test="${empty item.reviewedDate}">
                    		<li>审核中</li>
	                    	<li><a href="javascript:;" class="sec" onclick="closeAlert(${item.id});">退出报名</a></li>
                    	</c:if>
                    </c:if>-->


                        <c:if test="${item.status == -2}">
                            <li>已退出</li>
                            <li></li>
                        </c:if>
                        <c:if test="${item.status == -1}">
                            <li>审核失败</li>
                            <li><a href="javascript:;" class="sec" onclick="WdatePicker({
                            <c:if test="${!empty promotionProductList}">
                                    disabledDates:[
                            <c:forEach var="item2" varStatus="status" items="${promotionProductList}">
                            <c:if test="${status.first}">'<fmt:formatDate value="${item2.joinStart}"
                                                                          pattern="yyyy-MM-dd"/>'
                            </c:if>
                            <c:if test="${!status.first}">,'<fmt:formatDate value="${item2.joinStart}"
                                                                            pattern="yyyy-MM-dd"/>'
                            </c:if>
                            </c:forEach>
                                    ],
                            </c:if>
                                    isShowToday:false,autoPickDate:false,isShowClear:false,minDate:'${begin}',maxDate:'${end}',el:'sj',onpicked:function(){toNext();}})">报名</a>
                            </li>
                        </c:if>
                        <c:if test="${item.status == 0}">
                            <li>待审核</li>
                            <li class="secbtn"><a
                                    href="${basePath}/promotion/productSet.html?id=${item.skuId }&promotionProductId=${item.id}"
                                    class="sec">修改报名</a><a href="javascript:;" class="sec"
                                                           onclick="closeAlert(${item.id});">退出报名</a></li>
                        </c:if>
                        <c:if test="${item.status == 2 }">
                            <li>成功</li>
                            <li><a href="javascript:;" class="sec" onclick="closeAlert2(${item.id});">详细</a></li>
                        </c:if>
                    </ul>
                    <!--秒杀活动详细 start-->
                    <div class="sort_popup" id="sec-detail${item.id}">
                        <div class="popup_title">
                            <span>活动申请详情</span>
                            <label onclick="cancelButton2(${item.id});"><img src="<%=static_resources %>images/close.gif"
                                                                             width="14" height="14" alt="close"></label>
                        </div>
                        <div class="sort_popup_cont">
                            <div class="change_ln hmartop sec-mar">
                                <span>申请活动：</span>
                                <span>${item.promotion.name}</span>
                            </div>
                            <div class="change_ln sec-mar">
                                <span>活动状态：</span>
                                <span>成功</span>
                            </div>
                            <div class="change_ln sec-mar">
                                <span>申请日期：</span>
                                <span><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="change_ln sec-mar">
                                <span>开始日期：</span>
                                <span><fmt:formatDate value="${item.joinStart}" pattern="yyyy-MM-dd"/></span>
                            </div>
                            <div class="change_ln sec-mar">
                                <span>活动分配时间：</span>
                                <span><fmt:formatDate value="${item.joinStart}" pattern="HH:mm"/></span>
                            </div>
                            <div class="clear"></div>
                            <div class="popup_btn chmarbtn">
                                <a href="javascript:;" onclick="cancelButton2(${item.id});">确认</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <form id="sub_form2" action="${basePath}/promotion/toConfirm.html" method="post">
                <c:if test="${!empty promotionList}">
                    <input type="hidden" name="promotionId" value="${promotionList.get(0).promotionId}">
                </c:if>
                <input type="hidden" id="sj" name="bmTime">
            </form>
            <wodepageform:PageFormTag pageSize="${result.size}" totalPage="${result.totalPage}"
                                      currentPage="${result.page}" url=""/>
            <form id="sub_form" action="${basePath}/promotion/mylist.html" method="post">
                <input type="hidden" id="pages" name="pages" value="${pages}"/>
                <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
                <input type="hidden" id="status" name="status" value="${status}"/>
            </form>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->


<!--退出报名弹出-->
<div class="sort_popup" id="sec-delete">
    <input type="hidden" name="id" value=""/>
    <div class="popup_title">
        <span>退出报名</span>
        <label onclick="cancelButton();"><img src="<%=static_resources %>images/close.gif" width="14" height="14"
                                              alt="close"></label>
    </div>
    <div class="sort_popup_cont">
        <div class="change_ln hmartop">
            <span class="sec-center">是否确认退出报名？</span>
        </div>
        <div class="clear"></div>
        <div class="popup_btn chmarbtn">
            <a href="javascript:;" onclick="closeAlertImpl();">确认</a>
            <a href="javascript:;" onclick="cancelButton();">取消</a>
        </div>

    </div>
</div>

<script type="text/javascript">
var jsBasePath = '${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_activity_seckillActivityAll.js"></script>

<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>