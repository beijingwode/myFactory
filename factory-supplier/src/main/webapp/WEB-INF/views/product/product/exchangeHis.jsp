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
    <title>换领币领用</title>
    <%@ include file="/commons/js.jsp" %>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
    <style>
        table tbody tr td {
            text-align: center;
            height: 30px;
        }

        table thead th {
            border-right: 1px solid #e6e8ea;
            background: #e9e7e7;
            height: 30px;
        }

        .td_bor td {
            border: 1px solid #e6e8ea;
            border-left: none;
        }

        .td_bor_g td {
            background: #f4f3f3;
            border-right: 1px solid #e6e8ea;
        }

        /*
        css 注释：
        只对table td设置左与上边框；
        对table设置右与下边框；
        为了便于截图，我们将css 注释说明换行排版
        */
        .sale_info{border-bottom:1px solid #eee;padding-bottom:20px;}
        .sale_option{width:300px;margin-right:15px;}
        .sale_option em{font-style:normal;color:#333}
        .sale_option .title{width:120px;font-size:14px;color:#000;float:left;}
        .sale_option span{width:175px;text-align:left;color:#6a6a6a;font-size:14px;float:left;display:block;}
        
        .sale_option span.col_red{color:#ff4040;}
        .sale_option i{font-style:normal;color:#ff4040;padding:0 3px;}
        
        .sale_one1 .sale_option{width:220px;}
        .sale_one1 .sale_option span{width:95px;}
        
        .sale_content .table-c th img{float:right;margin-top:4px;margin-right:6px;cursor:pointer}
        .sale_content .table-c .th_yjh{position:relative;}
        .sale_content .table-c .th_yjh p{position:absolute;top:-80px;left:90px;height:auto;width:150px;background:#fff;border:1px solid #ccc;border-radius:3px;padding:10px;font-weight:500;color:#6a6a6a;text-align:left;display:none;}
        
        .sale_content .table-c .th_sps{position:relative;}
        .sale_content .table-c .th_sps p{position:absolute;top:-80px;left:-80px;height:auto;width:150px;background:#fff;border:1px solid #ccc;border-radius:3px;padding:10px;font-weight:500;color:#6a6a6a;text-align:left;display:none;}
        .table_tit{height:26px;overflow:hidden}
    	.table_tit span{font-size:14px;width:80px;height:24px;line-height:24px;text-align:center;border:1px solid #2b8dff;border-radius: 3px;color:#2b8dff;float:left;}
        .table_tit p{float:right;line-height:30px;color:#bbb}
        .table_tit p i{font-style:normal;color:#ff4040;font-size:16px;padding:0 3px 0 0}
    </style>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
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
				<li class="curr"><a href="${basePath}/product/exchageProduct.html">换领商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/questionnaire/trialProduct.html">试用商品问卷</a></li>
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
            <a href="javascript:void(0);">换领商品管理</a>
        </div>
        <div class="sale_wrap">
            <div class="sale_info">
                <div class="sale_one">
                    <div class="sale_option"  style="width:330px;">
                        <div class="title">商品名称：</div>
                        <span style="width:205px;float:right;">${sep.productName}</span>
                        
                    </div>
                    <div class="sale_option" style="width:240px;">
                        <div class="title">换领期限：</div>                            
                        <span style="width:115px;">至<fmt:formatDate value="${sep.limitEnd}" pattern="yyyy-MM-dd" /></span>
                    </div>
                    <div class="sale_option"  style="width:330px;">
                        <div class="title">状态：</div>                            
                        <span class="zt_span" style="width:205px;">
                       <c:choose>
      		  	<c:when test="${sep.status==2}">
            		<em>换领中</em>
      		  	</c:when>
      		  	<c:when test="${sep.status==3}">
            		<em>已结束（领完）</em>
      		  	</c:when>
      		  	<c:when test="${sep.status==4}">
            		<em>提前终止</em>
      		  	</c:when>
      		  	<c:when test="${sep.status==5}">
            		<em>已结束（终止）</em>
      		  	</c:when>
      		  	<c:when test="${sep.status==7}">
            		<em>已结束（到期）</em>
      		  	</c:when>
      		  	<c:when test="${sep.status==9}">
            		<em>线下已发放</em>
      		  	</c:when>
      		  	<c:otherwise>
            		<em>待审核</em>
      		  	</c:otherwise>
      		  </c:choose>
      		  
                        </span>
                    </div>
                </div>
                <div class="sale_one">
                    <div class="sale_option"   style="width:500px;">
                     <div class="title">分配状况：</div>
                        <span style="width:375px;float:right;">平均每人获得 <i>${sep.empAvgAmount}</i>元换领币,合<i>
                        <c:if test="${sep.productPrice.unscaledValue() != 0.00}">
                        	<fmt:formatNumber type="number" value="${sep.empAvgAmount/sep.productPrice}" pattern="0.0" maxFractionDigits="1"/> </i>个商品</span>
                        </c:if>
                       	<c:if test="${sep.productPrice.unscaledValue() == 0.00}">
                        	<fmt:formatNumber type="number" value="0.0" pattern="0.0" maxFractionDigits="1"/> </i>个商品</span>
                        </c:if>
                    </div>

                </div>
                <div class="clear"></div>
            </div>
            
            <div class="sale_list_wrap" style="margin-top:10px;">
            	<div class="table_tit">
            		<span>领用记录</span>
            		<p><c:if test="${sep.clearStatus == 0 }"><i>*</i>尚未完成清算，暂不能线下发放</c:if></p>
            	</div>
                <div class="sale_content" style="margin-top:10px;">
                
                    <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                        <thead>
                        <th style="min-width:95px;">姓名</th>
                        <th style="width:110px;">职务</th>
                        <th style="width:120px;">电话</th>
                        <th style="width:120px;">部门</th>
                        <th style="width:120px;">换领币</th>
                        <th style="width:120px;">已消费</th>
                        <th style="width:235px;">消费内容</th>
                        <th style="width:110px;">领取时间</th>
                        </thead>
                        <tbody>
                          <c:forEach items="${uets}" var="item" varStatus="status">
                        	<tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>">
                                <td>${item.nickname}</td>
                                <td>${item.duty}</td>
                                <td>${item.phone}</td>
                                <td>${item.sectionName}</td>
                                <td><fmt:formatNumber value="${item.empAvgAmount}" type="currency" pattern="0.00"/></td>
                                <td><fmt:formatNumber value="${item.usedAmount}" type="currency" pattern="0.00"/></td>
                                <td>${item.usedNote}</td>
                                <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd" /></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                
	             <div class="r-btn-box">
	            	<div class="r-btn-s"><a href="${basePath}/exchangeProduct/exportExchangeHis?ticketId=${sep.id}&type=0" target="_blank">导出全部</a></div>
	                <div class="r-btn-s" style="width:110px;"><a href="${basePath}/exchangeProduct/exportExchangeHis?ticketId=${sep.id}&type=2" target="_blank">导出已消费名单</a></div>
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
});

$(".sale_content .table-c .th_yjh img").mouseover(function(){
	$(".sale_content .table-c .th_yjh p").fadeIn();
});
$(".sale_content .table-c .th_yjh img").mouseout(function(){
	$(".sale_content .table-c .th_yjh p").fadeOut();
});

$(".sale_content .table-c .th_sps img").mouseover(function(){
	$(".sale_content .table-c .th_sps p").fadeIn();
});
$(".sale_content .table-c .th_sps img").mouseout(function(){
	$(".sale_content .table-c .th_sps p").fadeOut();
});
</script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>