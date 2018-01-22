<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>

<!doctype html>
<html>
<head>
    <script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
    <%@ include file="/commons/js.jsp" %>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.min.js"></script>

</head>
<body>
<%@ include file="/commons/header.jsp" %>
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
                <li class="active"><a href="javascript:">分类管理</a></li>

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
            <a href="shopping_sort.html">我的店铺</a><em>></em>
            <a href="#">商品归类</a><em>></em>
            <a href="#">${funcName}</a>
        </div>
        <div class="sale_wrap">
            <form id="sub_form" action="${basePath}/product/findProductlistForCenter.html" method="post">
                <input type="hidden" id="pages" name="pages" value="${pages}"/>
                <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
                <input type="hidden" id="pricesort" name="pricesort" value="${pricesort}"/>
                <input type="hidden" id="createDatesort" name="createDatesort" value="${createDatesort}"/>
                <input type="hidden" id="sortname" name="sortname" value="${sortname}"/>
                <div class="store_search">
                    <span>商品名称</span><input class="pubilc_input f218" type="text" id="name" name="name" value="${name }">
                    <p><span>价格</span><input class="pubilc_input f91" type="text" id="minprice" name="minprice" value="${minprice}" maxLength="8"/><span>到</span>
                        <input class="pubilc_input f91" type="text" id="maxprice" name="maxprice" value="${maxprice}" maxLength="8"/></p>
                    <p><span>状态</span>
                        <select class="select_input f88" id="selltype" name="selltype">
                            <option value="">--请选择--</option>
                            <option value="selling" <c:if test="${selltype == 'selling'}">selected</c:if>>在售</option>
                            <option value="waitsell" <c:if test="${selltype == 'waitsell'}">selected</c:if>>待售</option>
                            <option value="waitcheck" <c:if test="${selltype == 'waitcheck'}">selected</c:if>>审批中</option>
                        </select></p>
                    <div class="store_searchbtn"><a href="javascript:void(0);" onclick="formSubmit('1');">搜索</a></div>
                </div>

            </form>
            <div class="sale_list_wrap">
                <div class="store_allselect">
                    <p><input class="redio" type="checkbox" id="total" onclick="checkTotal();check();"><span>全选</span></p>
                    <div class="store_type"><a href="javascript:void(0);" onclick="updateAllObject(0,null,1)">批量分类</a></div>
                </div>
                <div class="sale_content">
                    <ul class="sale_them">
                        <li class="s1">商品信息</li>
                        <li class="s2">价格<i name="pricesort_i" class="<c:if test='${pricesort==2}'>down</c:if>" onclick="sortOclick('pricesort');"></i></li>
                        <li class="s3">所属分类</li>
                        <li class="s4">创建日期<i name="createDatesort_i" class="<c:if test='${createDatesort==2}'>down</c:if>" onclick="sortOclick('createDatesort');"></i></li>
                        <li class="s5">编辑分类</li>
                        <li class="s6">状态</li>
                    </ul>
                    <ul class="sale_infomation_list">
                        <c:forEach items="${result.msgBody}" var="item" varStatus="status">
                            <li>
                                <div class="sale_tl">
                                    <span><input class="redio" type="checkbox" name="ck" onclick="check();" value="${item.id}">商品条码：${item.barcode}</span>
                                    <span>所属类目：${item.categoryName}</span>
                                </div>
                                <div class="infowrap">
                                    <div class="pro_info">
                                        <div class="s_img"><a href="${basePath}/product/productView.html?productId=${item.id}" target="_blank"><img src="${item.image}" width="78" height="78" alt="Me-order-img"></a></div>
                                        <p><a href="${basePath}/product/productView.html?productId=${item.id}" target="_blank" style="word-break: break-all;">${item.name}</a></p>
                                    </div>
                                    <span>￥<fmt:formatNumber value="${item.minprice}" pattern="#,###.00#"/>-<fmt:formatNumber value="${item.maxprice}" pattern="#,###.00#"/></span>
                                    <div class="belong_type">
                                        <p>${item.storeCategoryName}</p>
                                    </div>
                                    <div class="pro_time">
                                        <p><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></p>
                                        <p><fmt:formatDate value="${item.createDate}" pattern="HH:mm:ss"/></p>
                                    </div>
                                    <div class="add_type">
                                        <p><a href="#">添加分类</a></p>
                                    </div>
                                    <div class="status">
                                        <c:choose><c:when test="${item.isMarketable==1}"><p class="sell">销售中</p></c:when><c:when test="${item.isMarketable==-1||item.isMarketable==-2}"><c:if test="${item.status==1}"><p class="salestop">待审核</p></c:if><c:if test="${item.status==-1}"><p class="salestop">审核不通过</p></c:if><c:if test="${item.status!=1&&item.status!=-1}"><p class="salestop">下架</p></c:if></c:when><c:when test="${item.isMarketable==0}"><c:if test="${item.status==1}"><p class="salestop">
                                            待审核</p></c:if></c:when><c:otherwise><c:choose><c:when test="${item.status==0}"><p class="salestop">未提交审核</p></c:when><c:when test="${item.status==1}"><p class="salestop">待审核</p></c:when><c:when test="${item.status==-1}"><p class="salestop">审核未通过</p></c:when><c:otherwise></c:otherwise></c:choose></c:otherwise></c:choose>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
                <div class="store_allselect">
                    <p><input class="redio" type="checkbox" id="total" onclick="checkTotal();check();"><span>全选</span></p>
                    <div class="store_type"><a href="javascript:void(0);" onclick="updateAllObject(0,null,1)">批量分类</a></div>
                </div>
                <wodepageform:PageFormTag pageSize="${result.size}" totalPage="${result.totalPage}" currentPage="${result.page}" url=""/>
            </div>
        </div>
    </div>
</div>
<!--right end-->
</div>
<!--content end-->

<!--商品快捷修改-弹出框 begin-->
<div class="popup_bg"></div>
<div class="shop_popup" id="shop_popup">
    <div class="popup_title">
        <span>商品快捷修改</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="$('#shop_popup').hide();"></label>
    </div>
    <div class="popup_cont" id="alertPriceDiv">
    </div>
</div>
<!--商品快捷修改-弹出框 end-->

<!--确认上架 begin-->
<div class="shop_popup" id="shop_popup_true">
    <div class="popup_title">
        <span><c:if test="${selltype=='selling'}">确认下架</c:if><c:if test="${selltype=='waitsell'}">确认提交审批</c:if><c:if test="${selltype=='waitcheck'}"></c:if></span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
    <div class="popup_cont">
        <div class="popup_txt">确认商品 "<span id="alertproductname"></span>" <c:if test="${selltype=='selling'}">下架吗？</c:if><c:if test="${selltype=='waitsell'}">提交审批审核吗？</c:if><c:if test="${selltype=='waitcheck'}"></c:if></div>
        <div class="popup_btn">
            <div class="popupbtn"><input type="hidden" id="alertproductid" value=""/><a href="javascript:void(0);" onclick="subforCheck(this);">确认</a></div>
            <div class="popupbtn"><a href="javascript:void(0);" onclick="$('#shop_popup_true').hide();">取消</a></div>
        </div>
    </div>
</div>
<!--确认上架 end-->

<!--添加分类弹出框 begin-->
<div class="popup_bg"></div>
<div class="sort_popup" id="sort_popup">
    <div class="popup_title">
        <span>添加分类</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
    <div class="sort_popup_cont">
        <!--scroll begin-->
        <div class="sort_scr_con">
            <div id="sort_dv_scroll">
                <div class="sort_Scroller-Container">
                    <c:forEach items="${cates}" var="cate">
                        <ul class="sort_list">
                            <li><p>${cate.name}</p></li>
                            <c:forEach items="${cate.children}" var="child">
                                <li><span><input class="redio" type="checkbox" name="mycate" value="${child.id}">${child.name}</span></li>
                            </c:forEach>
                        </ul>
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
            <a href="#">确认</a>
            <a href="#" class="tcansel">取消</a>
        </div>
    </div>
</div>
<!--添加分类弹出框 end-->



<script type="text/javascript">
var jsBasePath='${basePath}';
var jsSelltype=${selltype};
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_shopsetting_productlistforcenter.js"></script>
<%@ include file="/commons/footer.jsp" %>
</body>
<script type="text/javascript" src="<%=static_resources %>js/menu_min.js"></script>
</html>