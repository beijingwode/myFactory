<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../common/include.jsp" %>

<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>我的福利</title>
    <link rel="stylesheet" href="${basePath}css/common.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}css/style.css" type="text/css" />
    <script type="text/javascript" src="${basePath}resources/js/jquery1.8.0.js"></script>
    
    <script type="text/javascript" src="${basePath }resources/js/application.js"></script>
    <script type="text/javascript" src="${basePath}resources/js/jquery.kinMaxShow-1.0.src.js"></script>
    <script>
        $(function(){
            banner();
        });
    </script>
</head>
<body>
<%@ include file="/common/header.jsp" %>
<!-- banner begin -->
<div class="bannerwrapper">
    <div class="banner" id="banner" >
<c:forEach items="${banners}" var="banner">
        <a href="${basePath}${banner.link}.html" class="d1" style="background:url(${basePath}images/banner.jpg) center no-repeat"></a>
</c:forEach>
        <div class="d2" id="banner_id">
            <ul>
<c:forEach items="${banners}" var="banner">
                <li></li>
</c:forEach>
            </ul>
        </div>
    </div>
</div>
<!-- banner end -->

<!--content begin-->
<div id="content">
    <!--热门活动 begin-->
    <div class="shopping_if-new">
        <div class="shopping_if_title-new border-bt-purple">
            <h2>热门活动</h2>
        </div>
        <div class="shopping_wrap-new">
            <div class="shopping_activity-new">
                <img src="/images/activity-new.jpg" width="231" height="480" alt="activity-new">
            </div>
            <div class="shopping_list_wrap-new">
                <div class="shopping_list">
                    <ul>
<c:forEach items="${result.hits}" var="hit" end="7">
                        <li>
                            <a href="${basePath}${hit.productId}.html?pageKey=search"><img src="${hit.image}" width="198" height="104" alt="${hit.name}"></a>
                            <p><a href="${basePath}${hit.productId}.html?pageKey=search">${hit.name}</a></p>
                            <strong>¥ ${hit.price - hit.maxFucoin}</strong>
                            <em>¥ ${hit.price}</em>
                        </li>
</c:forEach>
                    </ul>
                </div>
            </div>

        </div>
    </div>
    <!--热门活动 end-->

    <!--品牌特卖 begin-->
    <div class="shopping_if-new">
        <div class="shopping_if_title-new border-bt-red">
            <h2>特卖</h2>
            <%--<ul class="shoppingif_r_list">
                <li><a href="#">联想</a><em>|</em></li>
                <li><a href="#">华硕</a><em>|</em></li>
                <li><a href="#">ThinkPad</a><em>|</em></li>
                <li><a href="#">戴尔</a><em>|</em></li>
                <li><a href="#">三星</a></li>
                <p class="more"><a href="#">MORE</a></p>
            </ul>--%>
        </div>
        <div class="shopping_wrap-new">
            <div class="shopping_info-new">
                <h2>分类选择</h2>
                <ul class="brand-list-new">
<c:forEach items="${root}" var="category" end="13">
                    <li><a href="${basePath}product/search?allCat=${category.id}">${category.name}<%--<img src="images/brand-new.jpg" alt="brand-new">--%></a></li>
</c:forEach>
                </ul>
            </div>
            <div class="shopping_list_wrap-new">
                <div class="shopping_list">
                    <ul>
<c:forEach items="${result.hits}" var="hit" begin="8">
                        <li>
                            <a href="${basePath}${hit.productId}.html"><img src="${hit.image}" width="198" height="104" alt="${hit.name}"></a>
                            <p><a href="${basePath}${hit.productId}.html">${hit.name}</a></p>
                            <strong>¥ ${hit.price - hit.maxFucoin}</strong>
                            <em>¥ ${hit.price}</em>
                        </li>
</c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!--品牌特卖 end-->

</div>
<!--content end-->

<!--footer begin-->
<%@ include file="/common/footer.jsp" %>
<!--footer end-->
<script type="text/javascript" src="${basePath }resources/js/top_ewm.js"></script>
</body>
