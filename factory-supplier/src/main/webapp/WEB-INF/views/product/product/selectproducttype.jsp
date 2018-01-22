<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/commons/js.jsp" %>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<input type="hidden" id="categoryid" value="">

<!--content begin-->
<div id="content">
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li <c:if test="${selltype =='createproduct'}">class="curr"</c:if>><a href="${basePath}/product/toSelectProducttype.html">添加新商品</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productOnsell')}">
				<li <c:if test="${selltype =='selling'}">class="curr"</c:if>><a href="${basePath}/product/gotoProductlist.html?selltype=selling">在售商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productWaitSell')}">
				<li <c:if test="${selltype =='waitsell'}">class="curr"</c:if>><a href="${basePath}/product/gotoProductlist.html?selltype=waitsell">待售商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productWaitSell')}">
				<li <c:if test="${selltype =='reject'}">class="curr"</c:if>><a href="${basePath}/product/gotoProductlist.html?selltype=reject">问题商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productOncheck')}">
				<li <c:if test="${selltype =='waitcheck'}">class="curr"</c:if>><a href="${basePath}/product/gotoProductlist.html?selltype=waitcheck">待审批商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li>
			</c:if>
			<%-- <li><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li> --%>
			<li style="background-color: #f5f5f5">&nbsp;&nbsp;</li>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/product/exchageProduct.html">换领商品管理</a></li>
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
            <a href="${basePath}/product/toSelectProducttype.html">商品管理</a><em>></em>
            <a href="javascript:void(0);">
            <c:if test="${productId==null||productId==''}">添加商品-类目</c:if>
			<c:if test="${productId!=null&&productId!=''}">编辑商品-类目</c:if></a>
        </div>
        <div class="sort_wrap">
        	
        	<div class="sort_title">为店铺&nbsp;&nbsp;
	        	<select class="nw_sel wid_160" ismust="1" id="shopId" name="shopId" onchange="refreshCategory();">
					<c:forEach var="item" items="${shopList}">
	            	<option value="${item.id}" <c:if test="${shopId== item.id}">selected</c:if>>${item.shopname}</option>
					</c:forEach>
	            </select>
				&nbsp;&nbsp;添加新商品&nbsp;&nbsp;&nbsp;&nbsp;所属品牌
	        	<select class="nw_sel wid_160" ismust="1" id="brandId" name="brandId" onchange="refreshCategory();">
					<c:forEach var="item" items="${brandList}">
	            	<option value="${item.id}" <c:if test="${brandId== item.id}">selected</c:if>>${item.name}</option>
					</c:forEach>
	            </select>
        	</div>
      		
            <div class="search_sort">
            	<input class="search_input" type="text" id="searchname" value="">
                <div class="search_btn"><a href="javascript:void(0);" onclick="searchtype();">快速找到类目</a></div>
            </div>
            <div class="choose_sort">
            	<h2>选择添加类目</h2>
                <div class="choose_add">
                    <!--第一级类别-->
                    <div class="scr_con">
                        <div id="dv_scroll_1"  style="overflow-y:auto;overflow-x:hidden;height:380px;">
                            <div class="Scroller-Container">
                                <ul class="add_list" id="ul1">
                                    <c:forEach var="item" items="${productCategoryList}">
											<li id="${item.id}" onclick="checkli1(this,${item.id});"><a href ="javascript:void(0);">${item.name}</a></li>
									</c:forEach>
                                </ul>
                            </div>
                        </div>
                  </div>
                    <!--第二级类别-->
                    <div class="scr_con">
                        <div id="dv_scroll_2"   style="overflow-y:auto;overflow-x:hidden;height:380px;">
                            <div class="Scroller-Container">
                                <ul class="add_list" id="ul2">
                                </ul>
                            </div>
                        </div>
                    </div>
                    <!--第三级类别-->
                    <div class="scr_con">
                        <div id="dv_scroll_3"   style="overflow-y:auto;overflow-x:hidden;height:380px;">
                            <div class="Scroller-Container">
                                <ul class="add_last_list" id="ul3">
                                </ul>
                            </div>
                        </div>
                    </div>
                    
                </div>
                <div class="choose_result">
                	<span>您当前选择的是：</span>
                    <span id="span1"></span>
                    <span id="span2"></span>
                    <span id="span3"></span>
                </div>
				<c:if test="${productId==null||productId==''}"></c:if>
				<c:if test="${productId!=null&&productId!=''}">
					<div class="name" style="width: 100%;text-align:left">修改的商品：${productName}</div>
					<div class="name" style="width: 100%;text-align:left">商品原类目：${name1}<em>></em>${name2}<em>></em>${name3}</div>
					<br /><br />
				</c:if>
				<c:if test="${productId==null||productId==''}">
					<div class="next_btn" onclick="toCreateproduct();" style="margin-bottom:40px;"><a href="javascript:void(0);">下一步填写详细信息</a></div>
                </c:if>
                <c:if test="${productId!=null&&productId!=''}">
                	<div class="next_btn" onclick="toUpdateproduct();" style="margin-bottom:40px;"><a href="javascript:void(0);">下一步填写详细信息</a></div>
				</c:if>
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<%@ include file="/commons/footer.jsp" %>

<%@ include file="/commons/box.jsp" %>
  <script type="text/javascript">

    </script>
<script>
var jsBasePath='${basePath}';
var jsShopId="${shopId}";
var jsBrandId="${brandId}";
var jsProductId='${productId}';
</script>

<script type="text/javascript" src="<%=static_resources %>js/product_product_selectproducttype.js"></script>
</body>
</html>