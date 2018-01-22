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
    <title>试用问卷结果</title>
    <%@ include file="/commons/js.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%=static_resources %>css/style.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details_vote.css" />
    <style>
       .sale_info{width:890px;height:auto;padding:10px 0 30px 60px;overflow:hidden;margin-bottom:40px;border-bottom:1px solid #eee;} 
       .sale_info dl{width:890px;height:auto;}
       .sale_info dl dt{width:100px;height:100px;float:left;margin-right:30px;position:relative;border:1px solid #e5e5e5;}
        .sale_info dl dt .picon{position:absolute;top:3px;right:3px;}
       .sale_info dl dt a{width:100px;height:100px;display:block;}
       .sale_info dl dt a img{width:100px;height:100px;}
       .sale_info dl dd{width:250px;height:25px;overflow:hidden;line-height:25px;float:left;margin-right:30px;}
       .sale_info dl dd a{font-size:14px;}
       .sale_info dl dd span{color:#6a6a6a}
       .sale_info dl dd .col_red{color:#ff4040;}
       .sale_info dl dd span em{font-style:normal;}
    </style>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
			<c:if test="${leftMenu != 'order'}">
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/product/toSelectProducttype.html">添加新商品</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productOnsell')}">
				<li><a href="${basePath}/product/gotoProductlist.html?selltype=selling">在售商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productWaitSell')}">
				<li><a href="${basePath}/product/gotoProductlist.html?selltype=waitsell">待售商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productWaitSell')}">
				<li><a href="${basePath}/product/gotoProductlist.html?selltype=reject">问题商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productOncheck')}">
				<li><a href="${basePath}/product/gotoProductlist.html?selltype=waitcheck">待审批商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li>
			</c:if>
			<%-- <li><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li> --%>
			<li style="background-color: #f5f5f5">&nbsp;&nbsp;</li>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/product/exchageProduct.html">换领商品管理</a></li>
			</c:if>
		  </c:if>
    	  <c:if test="${leftMenu == 'order'}">
             <c:if test="${userSession.type != 3 || userSession.hasAuth('sellList')}">
                 <li><a href="${basePath}/suborder/gotoSelllist.html">已售出的商品</a></li>
             </c:if>
             <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
                 <li><a href="${basePath}/comments/toevaluation.html?commentDegree=all">评价管理</a></li>
             </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
                <li><a href="${basePath}/questionnaire/templates.html">问卷模板</a></li>
            </c:if>
		  </c:if>
			
		  <c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
			<li class="curr"><a href="${basePath}/questionnaire/trialProduct.html?leftMenu=${leftMenu}">试用商品问卷</a></li>
		  </c:if>
        </ul>
    </div>
    <!--left end-->
    <!--right begin-->

    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="javascript:void(0);">商品管理</a><em>></em>
            <a href="javascript:void(0);">试用商品问卷</a>
        </div>
        <div class="sale_wrap">
          <c:if test="${type==1}">
            <div class="sale_info">           
               <dl>
               		<dt>
               			<div class="picon"><img src="<%=static_resources %>images/picon_t2.png" /></div>
						<a href="${basePath}/product/productView.html?productId=${q.productId}"  target="_blank"><img src="${q.productImg}"  alt="Me-order-img"></a>  
               		</dt>
               		<dd style="width:700px;"><a href="">${q.productName}</a></dd>
               		<dd>状态：<span >
                       	  <c:choose>
			      		  	<c:when test="${q.status==2}">
			            		<em>进行中</em>
			      		  	</c:when>
			      		  	<c:when test="${q.status==3}">
			            		<em>已结束</em>
			      		  	</c:when>
			      		  	<c:when test="${q.status==-1}">
			            		<em>已删除</em>
			      		  	</c:when>
			      		  	<c:otherwise>
			            		<em>待审核</em>
			      		  	</c:otherwise>
			      		  </c:choose> 	
      		  
                        </span>
                    </dd>
                    <dd>结束日期：<span><fmt:formatDate value="${q.endDate}" pattern="yyyy-MM-dd" /></span></dd>
                    <dd>试用商品总数：<span  class="col_red">${q.productCnt}</span></dd>
                    <dd>价格(含内购券)：<span class="col_red" >￥<fmt:formatNumber value="${q.minprice}" pattern="#,##0.00#"/>--<fmt:formatNumber value="${q.maxprice}" pattern="#,##0.00#"/></span></dd>
               		<dd>回答人数：<span class="col_red">${q.answerCnt}人</span></dd>
                    <dd>返现金额：<span class="col_red">￥<fmt:formatNumber value="${q.empPrice}" pattern="#,##0.00#"/></span></dd>
             		
               </dl>             

                <div class="clear"></div>
            </div>
          </c:if>
			<div class="main_bd main_bd_con">				
				<div class="vote_list js_vote_list">

                  <c:forEach var="qq" items="${q.listQuestion}" varStatus="qs">
				    <div class="vote_item">
				    	<div class="vote_item_hd">
				        	<h4 class="vote_title">${qs.index+1}. ${qq.questionTitle}</h4>
				        </div>
				    	<div class="vote_item_bd">
				        	<ul class="vote_option_list">
                	  		  <c:forEach var="o" items="${qq.listOption}" varStatus="statusJ">
                	  		 	<li class="vote_option">
				                	<div class="vote_option_msg group" >
				                	  <c:if test="${not empty o.image}">
				                    	<span class="img_panel">
											<span class="preview bg_img" style="background-image:url('${o.image}');"></span>
										</span>
									  </c:if>
				                    	<strong class="vote_option_title" title="${o.optionTitle}">${o.optionTitle}</strong>
				                    </div>
				                    
          							<c:if test="${type==1}">
									  <c:set var="p" value="${q.answerCnt>0?(o.cnt*100/q.answerCnt):0}"></c:set>
									</c:if>
				                    <c:if test="${type!=1}">
									  <c:set var="p" value="0"></c:set>
									</c:if>
				                    <div class="vote_result_meta">
				                        <div class="vote_graph">
				                        <em w="${p}" style="background-color:#7DADE1" class="vote_progress"></em>
				                        </div>
				                        <span class="vote_result_tips vote_number">${o.cnt}票</span>
				                        <span class="vote_result_tips vote_percent"><fmt:formatNumber value="${p}" pattern="#,##0.0#"/>%</span>
				                    </div>
				                </li>
				              </c:forEach>
				          	</ul>
				       	</div>
					</div>
				  </c:forEach>					
				</div>	
			</div>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<script type="text/javascript">
var jsBasePath='${basePath}';

$(document).ready(function () {
	selectedHeaderLi("spgl_header");
	
	animate();
});

function animate(){
	$(".vote_progress").each(function(i,item){
		var a=parseInt($(item).attr("w"));
		$(item).animate({
			width: a+"%"
		},1000);
	});
}
</script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/alertMessage.jsp" %>

</body>
</html>