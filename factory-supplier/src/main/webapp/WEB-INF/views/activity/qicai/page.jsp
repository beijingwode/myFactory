<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
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
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta content="always" name="referrer">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>商家中心--活动管理</title>
<%@ include file="/commons/js.jsp"%>

<script type="text/javascript">
	var jsBasePath = '${basePath}';
	var jsStaticResources = '<%=static_resources %>';
	var jsWelfareLevel="${welfareLevel}";
	var jsEntId="${ent.id}";
</script>
</head>
<body>
	<!-- top start -->
	<%@ include file="/commons/header.jsp"%>
	<!-- top end -->

	<div id="content" class="clear">
		<!-- left menu start -->
		<%@ include file="/commons/menu_activity.jsp"%>
		<!-- left menu end -->
		<!--right begin-->
		<div class="right">
			<div class="position">
				<span>您所在的位置：</span> 
				<a href="javascript:;">商家中心</a><em>></em> 
				<a href="javascript:;">活动管理</a><em>></em>
				<a href="javascript:;">阶梯价格（企采/集采）</a>
			</div>
			<div class="sale_info">
			  <form action="${basePath}/activity/qicai/page.html" method="post" id="sub_form">
				<input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}" />
				<input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}" />
				<div class="sale_wrap">
					<div class="sale_one">
						<div class="sale_option">
							<div class="title">商品名称：</div>
							<input class="pubilc_input f218" type="text" id="productName" name="productName" value="${query.productName}" maxLength="20">
						</div>
						<div class="sale_option">
							<div class="title">分类：</div>
							<input type="hidden" name="categoryidtemp" id="categoryidtemp" value="${query.categoryId}" /> 
							<select class="pubilc_input f226" id="categoryid" name="categoryId">
								<option value="">--请选择--</option>
							</select>
						</div>
						<div class="sale_option">
							<div class="title">内购价：</div>
							<input class="pubilc_input f91" onkeyup="this.value=this.value.replace(/\D/g,'')" onblur="this.value=this.value.replace(/\D/g,'')" type="text" id="minPrice" name="minPrice" value="${query.minPrice}"/><span>到</span>
							<input class="pubilc_input f91" onkeyup="this.value=this.value.replace(/\D/g,'')" onblur="this.value=this.value.replace(/\D/g,'')" type="text" id="maxPrice" name="maxPrice" value="${query.maxPrice}" />
						</div>
					</div>
					<div class="clear"></div>

					<div class="btnwrap">
						<div class="checkbtn"><a href="javascript:void(0);" onclick="formSubmit('1');">查询</a></div>
						<div class="resetbtn"><a href="javascript:void(0);" onclick="formReset();">重置</a></div>
					</div>
				</div>
			  </form>
			</div>
			
        	<div class="sale_wrap">
        		<div class="sale_list_wrap">
        			<div class="sale_content">
					  <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                        <thead>
                        <th style="min-width:120px;">分类</th>
                        <th style="width:245px;">商品标题</th>
                        <th style="width:140px;">规格</th>
                        <th style="width:75px;">内购价</th>
                        <th style="width:50px;">库存</th>
                        <th style="width:170px;">阶梯价格</th>
                        <th style="min-width:150px;">操作</th>
                        </thead>
                        <tbody>
                          <c:forEach items="${page.list}" var="item" varStatus="status">
                            <tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>">
                                <td>${item.categoryName}</td>
                                <td>${item.productName}</td>
                                <td>${item.itemValues}</td>
                                <td><fmt:formatNumber value="${item.internalPurchasePrice}" type="currency" pattern="0.00"/></td>
                                <td>${item.quantity}</td>
                                <c:if test="${item.type==0}">
                                <td>${item.num}件以上,￥<fmt:formatNumber value="${item.price}" type="currency" pattern="0.00"/></td>
                                </c:if>
                                <c:if test="${item.type==1}">
                                <td>${item.num}件以上,${item.price}折</td>
                                </c:if>
								<td>
                                    <div class="operate">
                                    	<a target="_blank" href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>${item.pId}.html?skuId=${item.skuId}">预览</a>&nbsp;&nbsp;
                                    	<a target="_blank"  href="${basePath}/product/toCreateProduct.html?productId=${item.pId}&selltype=selling">编辑商品(或删除)</a>
                                    </div>
                                </td>
                            </tr>
                          </c:forEach>
                        </tbody>
                      </table>
        			</div>
        		</div>
        	</div>
        	
        	<wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>
		</div>
		<!--right end-->
	</div>

	<!-- 页脚 begin -->
	<%@ include file="/commons/footer.jsp"%>
	<!-- 页脚 end -->
</body>
	<script type="text/javascript" src="<%=static_resources %>js/page.js"></script>
	<script type="text/javascript" src="<%=static_resources %>js/activity/qicai_page.js"></script>
</html>