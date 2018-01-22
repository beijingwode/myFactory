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
    <title>换领记录</title>
    <%@ include file="/commons/js.jsp" %>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.min.js"></script>
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
                <div class="sale_one sale_one1">
                    <div class="sale_option">
                        <div class="title">内购价：</div>
                        <span class="col_red">${sep.productPrice}</span>
                    </div>
                	<div class="sale_option">
                        <div class="title">商品总数：</div>
                        <span  class="col_red">${sep.productCnt}</span>
                    </div>
                	
                    <div class="sale_option" >
                        <div class="title">预订数：</div>
                        <span class="col_red" >${sep.reservedNum}</span>
                    </div>
                    <div class="sale_option" style="margin:0;">
                        <div class="title">已换领：</div>
                        <span class="col_red">${sep.exchangeSuNum}</span>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
            <form id="sub_form" action="${basePath}/exchangeProduct/exchangeProductHis.html" method="post">
   			  <input type="hidden" name="type" value="${type}"/>
              <input type="hidden" name="selfDelivery" value="${selfDelivery}"/>
              <input type="hidden" id="pages" name="pages" value="${pages}"/>
              <input type="hidden" id="ticketId" name="ticketId" value="${ticketId}"/>
              <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
         	</form>
            <div class="sale_list_wrap" style="margin-top:10px;">
	            <ul class="Sold_change">
	                <li><a href="javascript:void(0);" onclick="tagChange(this);" typename="1" <c:if test="${type==1}">class="a1"</c:if>>预定明细</a></li>
	                <li><a href="javascript:void(0);" onclick="tagChange(this);" typename="2" <c:if test="${type==2}">class="a1"</c:if>>换领明细</a></li>
	            </ul>
            </div>
            <div class="sale_list_wrap" style="margin-top:10px;">
                <div class="sale_content" style="margin-top:10px;">
                    <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                        <thead>
                        <th style="width:240px;">公司</th>
                        <th style="width:210px;">电话</th>
                        <th style="width:220px;">数量</th>
                        <th style="width:220px;">规格</th>
                        <th style="width:120px;">日期</th>
                        </thead>
                        <tbody>
                        <c:choose>
	                    	<c:when test="${result.msgBody !=null}">
	                          <c:forEach items="${result.msgBody}" var="item" varStatus="status">
	                        	<tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>">
	                                <td>${item.nickname}</td>
	                                <td>***${item.phone}</td>
	                                <td>${item.number}</td>
	                                <td>${item.itemValues}</td>
	                                <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd" /></td>
	                            </tr>
	                        </c:forEach>
	                         </c:when>
                    	<c:otherwise><p class="h-result">未查询到符合条件的订单</p></c:otherwise> 
                    	  </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- 分页位置 start-->
            <wodepageform:PageFormTag pageSize="${result.size}" totalPage="${result.totalPage}" currentPage="${result.page}" url=""/>
            <!-- 分页位置 end-->
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

</script>
<script type="text/javascript" src="<%=static_resources %>js/exProduct_suborder_his.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>