<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>我的网---商品归类</title>
    <%@ include file="/commons/js.jsp" %>
    <link rel="stylesheet" type="text/css" href="<%=static_resources %>css/style.css">
</head>
<body>
<!--top begin-->
<%@ include file="/commons/header.jsp" %>
<!--header end-->
<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <div class="left_store_list">
            <ul>
                <c:forEach items="${shopList}" var="shop">
                 <li><a href="javascript:"><i class="icon01"></i>${shop.shopname}</a>
                 	<ul class="twomenu">
		                <li><a href="${basePath}/shopSetting.html?id=${shop.id}">基本信息</a></li>
		                <c:if test="${userSession.type != 3 || userSession.hasAuth('pageSet')}">
		                    <li><a href="${basePath}/shopSetting/pageset.html?id=${shop.id}">店铺页面设置</a></li>
		                </c:if>
		                <c:if test="${userSession.type != 3 }">
		                    <li><a href="${basePath}/shopSetting/categoryBrand.html?id=${shop.id}">品牌及分类</a></li>
		                </c:if>
                    </ul>
               	</c:forEach>
                <c:if test="${userSession.type != 3 || userSession.hasAuth('category')}">
                    <li ><a href="${basePath}/category/list.html?">分类管理</a></li>
                </c:if>
                <c:if test="${userSession.type != 3 || userSession.hasAuth('productCategory')}">
                    <li><a href="javascript:"><i class="icon01"></i>商品归类</a>
                        <ul class="twomenu" ${not empty mark ? 'style="display:block;"' : ''}}>
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('allProducts')}">
                                <li  ${mark == 'all' ? 'class="active"' : ''}><a href="${basePath}/shopSetting/products.html?mark=all">全部商品</a></li>
                            </c:if>
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('unCategoryProducts')}">
                                <li ${mark == 'TMP' ? 'class="active"' : ''}><a href="${basePath}/shopSetting/products.html?mark=TMP">未分类商品</a></li>
                            </c:if>
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('categoryProducts')}">
                                <li><a href="javascript:" style="background:none;color:#434343;"><i class="icon02"></i>已分类商品</a>
                                    <ul class="threemenu" ${not empty parent ? 'style="display:block;"' : ''}>
                                        <c:forEach items="${allCats}" var="cat">
                                            <c:choose>
                                                <c:when test="${not empty cat.children}">
                                                    <li><a href="javascript:"><i class="icon03"></i>${cat.name}</a>
                                                        <ul class="fourmenu" ${parent.id == cat.id ? 'style="display:block;"' : ''}>
                                                            <c:forEach items="${cat.children}" var="child">
                                                                <li ${mark != 'all' && mark != 'TMP' && mark == child.id ? 'class="active"' : ''}><a href="${basePath}/shopSetting/products.html?mark=${child.id}">${child.name}</a></li>
                                                            </c:forEach>
                                                        </ul>
                                                    </li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li><a href="javascript:">${cat.name}</a></li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:if>
                        </ul>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/shopSetting.html">我的店铺</a><em>></em>
            <a href="javascript:">商品归类</a><em>></em>
            <c:choose>
                <c:when test="${mark == 'all'}">
                    <a href="javascript:">全部商品</a>
                </c:when>
                <c:when test="${mark == 'TMP'}">
                    <a href="javascript:">未分类商品</a>
                </c:when>
                <c:otherwise>
                    <a href="javascript:">已分类商品</a><em>></em>
                    <a href="javascript:">${parent.name}</a><em>></em>
                    <a href="javascript:">${sc.name}</a>
                </c:otherwise>
            </c:choose>

        </div>
        <div class="sale_wrap">
            <div class="store_search">
                <form id="queryForm" action="products.html?mark=${mark}" method="post">
                    <span>商品名称</span><input class="pubilc_input f218" type="text" name="name" value="${not empty query.name ? query.name : ''}">
                    <p><span>价格</span><input class="pubilc_input f98" type="text" name="minprice" value="${query.minprice}"><span>到</span>
                        <input class="pubilc_input f98" type="text" name="maxprice" value="${query.maxprice}"></p>
                    <p><span>状态</span>
                        <select class="select_input f88" name="isMarketable">
                            <option value="">请选择</option>
                            <option value="1" ${query.isMarketable == true ? 'selected="selected"' : ''}>上架</option>
                            <option value="0" ${query.isMarketable == false ? 'selected="selected"' : ''}>下架</option>
                        </select></p>
                    <div class="store_searchbtn"><a href="javascript:" onclick="submitQuery();">搜索</a></div>
                </form>
            </div>
            <div class="store_allselect">
                <p><input class="redio" name="selectAll" type="checkbox" onclick="selectAll(this);" value=""><span>全选</span></p>
                <div class="store_type"><a href="javascript:" onclick="addCategories();">批量分类</a></div>
            </div>
            <div class="sale_content">
                <ul class="sale_them">
                    <li class="s1">商品信息</li>
                    <li class="s2">价格</li>
                    <li class="s3">所属分类</li>
                    <li class="s4">创建日期</li>
                    <li class="s5">编辑分类</li>
                    <li class="s6">状态</li>
                </ul>
                <ul class="sale_infomation_list">
                    <c:forEach items="${page.list}" var="product" varStatus="proStatus">
                        <li>
                            <div class="infowrap">
                                <div class="shp_info">
                                    <div class="leftradio"><input class="redio pros" type="checkbox" value="${product.id}"></div>
                                    <div class="s_img">
                                    	<c:if test="${product.saleKbn==1 }">
								   		<div class="picon"><img src="<%=static_resources %>images/picon2.png" /></div>
								   		</c:if>
								  		<c:if test="${product.saleKbn==2 }">
								   		<div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
								   		</c:if>
								   		<c:if test="${product.saleKbn==4 }">
								   		<div class="picon"><img src="<%=static_resources %>images/picon_z2.png" /></div>
								   		</c:if>
								  		<c:if test="${product.saleKbn==5 }">
								   		<div class="picon"><img src="<%=static_resources %>images/picon_t2.png" /></div>
								   		</c:if>						   		
								   		
                                    	<a href="#"><img src="${product.image}" width="78" height="78" alt="Me-order-img"></a>
                                    </div>
                                    <p><a href="#" style="word-break: break-all;">${product.fullName}</a></p>
                                </div>
                                <span>￥<fmt:formatNumber value="${product.minprice }" pattern="#0.00"/>-<fmt:formatNumber value="${product.maxprice }" pattern="#0.00"/></span>
                                <div class="belong_type">
                                    <p>${product.storeCategoryName}</p>
                                </div>
                                <div class="pro_time">
                                    <p><fmt:formatDate value="${product.createDate}" pattern="yyyy-MM-dd"/></p>
                                    <p><fmt:formatDate value="${product.createDate}" pattern="HH:mm:ss"/></p>
                                </div>
                                <div class="add_type">
                                    <p><a href="javascript:" id-data="${product.storeCategoryId}" onclick="addCategory(this, '${product.id}');">添加分类</a></p>
                                </div>
                                <div class="status">
                                    <c:choose><c:when test="${product.isMarketable==1}"><p class="sell">销售中</p></c:when><c:when test="${product.isMarketable==-1||product.isMarketable==-2}"><c:if test="${product.status==1}"><p class="salestop">待审核</p></c:if><c:if test="${product.status==-1}"><p class="salestop">审核不通过</p></c:if><c:if test="${product.status!=1&&product.status!=-1}"><p class="salestop">下架</p></c:if></c:when><c:when test="${product.isMarketable==0}"><c:if test="${product.status==1}">
                                        <p class="salestop">待审核</p></c:if></c:when><c:otherwise><c:choose><c:when test="${product.status==0}"><p class="salestop">未提交审核</p></c:when><c:when test="${product.status==1}"><p class="salestop">待审核</p></c:when><c:when test="${product.status==-1}"><p class="salestop">审核未通过</p></c:when><c:otherwise></c:otherwise></c:choose></c:otherwise></c:choose>
                                </div>
                            </div>
                        </li>
                    </c:forEach>

                </ul>
            </div>
            <div class="store_allselect">
                <p><input class="redio" name="selectAll" type="checkbox" onclick="selectAll(this);" value=""><span>全选</span></p>
                <div class="store_type"><a href="javascript:" onclick="addCategories();">批量分类</a></div>
            </div>
            <form id="pageForm" action="products.html?mark=${mark}" method="post">
                <input type="hidden" name="name" value="${query.name}"/>
                <input type="hidden" name="minprice" value="${query.minprice}"/>
                <input type="hidden" name="maxprice" value="${query.maxprice}"/>
                <input type="hidden" name="isMarketable" value="${query.isMarketable}"/>
                <input type="hidden" id="pageNum" name="pageNumber" value="${page.pageNum}"/>
                <div class="page">
                    <c:if test="${!page.isFirstPage}">
                        <a href="javascript:" onclick="goto('${page.prePage}');">前一页</a>
                    </c:if>
                    <c:forEach var="i" begin="${(page.pageNum - (5 - 1)/2) < 1 ? 1 : (page.pageNum - (5 - 1)/2) }" end="${(page.pageNum + (5-1)/2) > page.lastPage ? page.lastPage : (page.pageNum + (5-1)/2)}" varStatus="status">
                        <c:if test="${status.first && i <= 3}">
                            <c:forEach var="j" begin="1" end="${i - 1 }">
                                <a href="javascript:" onclick="goto('${j}');">${j }</a>
                            </c:forEach>
                        </c:if>
                        <c:if test="${status.first && i > 3}">
                            <a href="javascript:" onclick="goto('1');">1</a>
                            <a href="javascript:" onclick="goto('2');">2</a>
                            <span>...</span>
                        </c:if>
                        <c:if test="${i != page.pageNum }">
                            <a href="javascript:" onclick="goto('${i}');">${i }</a>
                        </c:if>
                        <c:if test="${i == page.pageNum }">
                            <a href="javascript:" onclick="goto('${i}');">${i }</a>
                        </c:if>
                        <c:if test="${status.last && i < (page.lastPage - 1)}">
                            <span>...</span>
                        </c:if>
                    </c:forEach>
                    <c:if test="${page.pageNum < page.lastPage }">
                        <a href="javascript:" onclick="goto('${page.nextPage}');">后一页</a>
                    </c:if>
                    <span>共${page.lastPage }页</span>
                    <input type="text" id="page" class="input_text" value="${page.pageNum}">
                    <span>页</span>
                    <input type="button" id="page_button" value="确定" onclick="jumpto()" class="input_btn">
                </div>
            </form>
            <script type="text/javascript">
                function goto(page) {
                    $("#pageNum").val(page);
                    $("#pageForm").submit();
                }

                function jumpto() {
                    var pageNum = $.trim($("#page").val());
                    if (!isNaN(pageNum) && pageNum > 0 && pageNum <= ${page.lastPage}) {
                        goto(pageNum);
                    } else {
                        $("#page").val($("#pageNum").val());
                    }
                }
            </script>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="/commons/footer.jsp" %>
<!--footer end-->
<%@ include file="/commons/box.jsp" %>
<!--添加分类弹出框 begin-->
<div class="popup_bg"></div>
<div class="sort_popup" id="sort_popup1">
    <div class="popup_title">
        <span>添加分类</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
    <div class="sort_popup_cont">
        <!--scroll begin-->
        <div class="sort_scr_con">
            <div id="sort_dv_scroll">
                <div class="sort_Scroller-Container">
                    <c:forEach items="${allCats}" var="cat">

                        <div class="sort_list_box">
                            <ul class="sort_list">
                                <li><p>${cat.name}</p></li>
                                <c:forEach items="${cat.children}" var="child">
                                    <li><span><input class="redio" type="checkbox" for="categories" id="${child.id}" value="${child.id}">${child.name}</span></li>
                                </c:forEach>
                            </ul>
                        </div>

                    </c:forEach>
                </div>
            </div>

            <div id="sort_dv_scroll_bar">
                <div class="sort_Scrollbar-Track">
                    <div class="sort_Scrollbar-Handle"></div>
                </div>
            </div>
        </div>
        <!--scroll end-->
        <div class="clear"></div>
        <div class="popup_btn sortmar10">
            <div class="popupbtn">
                <a href="javascript:" id="catSaveButton" for="" onclick="saveCate(this);">确认</a>
                <a href="javascript:" id="tcansel">取消</a>
            </div>
        </div>
    </div>
</div>
<!--添加分类弹出框 end-->
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/index.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/menu_min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/scrollbar.js"></script>

<script type="text/javascript">
var jsBasePath='${basePath}';

</script>
<script type="text/javascript" src="<%=static_resources %>js/product_shopsetting_products.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        selectedHeaderLi("wddp_header");
    });
</script>
</body>
</html>
