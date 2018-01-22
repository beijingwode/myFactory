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
    <title>我的网---分类管理</title>
    <%@ include file="/commons/js.jsp" %>
    <script type="text/javascript">
        var categories = [], supplier = ${supplierId}, updates = [], updateFlag = 0, page = '${page.pageNum}', saveStatus = 0;
    </script>
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
                        <ul class="twomenu">
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('allProducts')}">
                                <li><a href="${basePath}/shopSetting/products.html?mark=all">全部商品</a></li>
                            </c:if>
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('unCategoryProducts')}">
                                <li><a href="${basePath}/shopSetting/products.html?mark=TMP">未分类商品</a></li>
                            </c:if>
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('categoryProducts')}">
                                <li><a href="javascript:"><i class="icon02"></i>已分类商品</a></li>
                                <ul class="threemenu">
                                    <c:forEach items="${allCats}" var="cat">
                                        <c:choose>
                                            <c:when test="${not empty cat.children}">
                                                <li><a href="javascript:"><i class="icon03"></i>${cat.name}</a>
                                                    <ul class="fourmenu">
                                                        <c:forEach items="${cat.children}" var="child">
                                                            <li><a href="${basePath}/shopSetting/products.html?mark=${child.id}">${child.name}</a></li>
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
            <a href="javascript:">分类管理</a>
        </div>
        <div class="sale_wrap">
            <div class="manage_btn">
                <div class="m_button"><a href="javascript:" onclick="addCategory();">新增类目</a></div>
                <div class="m_button"><a href="javascript:" onclick="expandAll();">全部展开</a></div>
                <div class="m_button"><a href="javascript:" onclick="collapseAll();">全部收起</a></div>
                <div class="saving_button"><a href="javascript:" onclick="saveCats();">保存</a></div>
            </div>
            <div class="store_allselect">
                <p><input class="redio" type="checkbox" onclick="selectAll(this);"><span>全选</span></p>
                <div class="store_type"><a href="javascript:" onclick="delAll();">批量删除</a></div>
            </div>
            <div class="sale_content">
                <ul class="sale_them">
                    <li class="m1">分类名称</li>
                    <li class="m2">添加分类</li>
                    <li class="m3">移动</li>
                    <li class="m4">操作</li>
                    <li class="m5">默认展开</li>
                </ul>
                <ul class="manage_list">
                    <c:forEach items="${page.list}" var="cat" varStatus="catStatus">
                        <c:set var="order" scope="page" value="${page.pageNum}${catStatus.index}"/>
                        <li>
                            <div class="mana01">
                                <span><input class="redio" type="checkbox" value="${cat.id}" onclick="listenParent(this);">
                                    <c:if test="${not empty cat.children}">
                                        <i class="adicon" state="off" onclick="switcher(this, '${cat.id}')"></i>
                                    </c:if>
                                    <c:if test="${empty cat.children}">
                                        <i></i>
                                    </c:if>
                                </span>
                                <input class="pubilc_input f148" type="text" value="${cat.name}">
                            </div>
                            <div class="mana02"><a href="javascript:" onclick="addChild(this,'${cat.id}');">添加子分类</a></div>
                            <div class="mana03">
                                <span class="upbtn" onclick="move(this, 'top');"></span>
                                <span class="topbtn" onclick="move(this,'up');"></span>
                                <span class="downbtn" onclick="move(this,'down');"></span>
                                <span class="bottombtn" onclick="move(this, 'bottom');"></span>
                            </div>
                            <div class="mana04"><a href="javascript:" onclick="delCat(this,'${cat.id}');">删除</a></div>
                            <div class="mana05" state="${cat.isExpanding}" onclick="switchState(this);"><img src="<%=static_resources %>${cat.isExpanding ? 'images/on.png' : 'images/off.png'}" width="37" height="22"></div>
                        </li>
                        <c:forEach items="${cat.children}" var="child" varStatus="childStatus">
                            <li nodeFor="${cat.id}" style="display:none;">
                                <div class="mana01">
                                    <span><input class="redio" type="checkbox" value="${child.id}" onclick="listenChild(this);"></span>
                                    <i class="micon"></i><input class="pubilc_input f148" type="text" value="${child.name}">
                                </div>
                                <div class="mana02"></div>
                                <div class="mana03">
                                    <span class="upbtn" onclick="childMove(this,'top');"></span>
                                    <span class="topbtn" onclick="childMove(this,'up');"></span>
                                    <span class="downbtn" onclick="childMove(this,'down');"></span>
                                    <span class="bottombtn" onclick="childMove(this,'bottom');"></span>
                                </div>
                                <div class="mana04"><a href="javascript:" onclick="delCat(this,'${child.id}');">删除</a><a href="${basePath}/shopSetting/products.html?mark=${child.id}">查看商品</a></div>
                                <div class="mana05"></div>
                            </li>
                        </c:forEach>
                    </c:forEach>

                </ul>
            </div>
            <div class="store_allselect">
                <p><input class="redio" type="checkbox" onclick="selectAll(this);"><span>全选</span></p>
                <div class="store_type"><a href="javascript:" onclick="delAll();">批量删除</a></div>
            </div>
            <jsp:include page="/commons/page.jsp" flush="true">
                <jsp:param name="page" value="${page}"/>
                <jsp:param name="pageSize" value="1"/>
            </jsp:include>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="/commons/footer.jsp" %>
<!--footer end-->
<%@ include file="/commons/box.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/index.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/menu_min.js"></script>

<script type="text/javascript">
var staticResources = '<%=static_resources %>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_shopsetting_category.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        selectedHeaderLi("wddp_header");
    });
</script>
</body>
</html>
