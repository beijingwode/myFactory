<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../common/include.jsp" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${ss.shopname}_我的福利</title>
    <link rel="stylesheet" href="${basePath}css/Brand.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}css/loginpopup.css" type="text/css" />
    <script type="text/javascript" src="${basePath}resources/js/jquery1.8.0.js"></script>
    <script type="text/javascript" src="${basePath}resources/js/jquery.luara.0.0.1.min.js"></script>
</head>
<body>
<%@ include file="head.jsp" %>
<!--top end-->
<!-----------------------------广告位-------------------------------->
<div class="ad_con">
    <c:choose>
<c:when test="${empty ss.topImage}">
	<img src="https://img2.wd-w.com/upload/regular/wdc/default_banner0.jpg">
</c:when>
<c:otherwise>
 	 <img src="${ss.topImage}">
</c:otherwise>
</c:choose>
</div>
<!------------------------导航---------------------------------->
<ul class="B_nav">
    <li><a href="javascript:" class="a1">首页</a></li>
    <li><a href="${basePath}shop/${ss.id}/intro">品牌介绍</a></li>
</ul> 
<!------------------------banner---------------------------------->

<div class="B_banner">
	<ul>
     	<c:forEach items="${banner}" var="item">
           <li><img src="${item}" alt=""/></li>
     	</c:forEach>
       </ul>
       <ol>
     	<c:forEach items="${banner}" var="item">
           <li></li>
     	</c:forEach>
       </ol>
</div>
<script>
        $(function(){
            <!--调用Luara示例-->
            $(".B_banner").luara({width:"1200",height:"300",interval:4500,selected:"seleted",deriction:"left"});

        });
</script>
<!---------------------------------------内容------------------------------------------------->
<div class="Brand_con">
    <div class="B_c_left">
        <div class="product_sort">
            <ul>
	            <li style="background:#F8F8F8;">
		        	<a href="${basePath}shop/${ss.id}">全部商品</a>
		        </li>
                <c:forEach var="category" items="${categories}" varStatus="categoryStatus">
                    <c:choose >
                        <c:when test="${parent != null}">
                            <li class="lt ${category.id eq parent ? 'after' : ''}"><a href="javascript:">${category.name}</a>
                                <ul class="downlist" ${category.id eq parent || category.isExpanding ? 'style="display: block;"' : ''}>
                        </c:when>
                        <c:otherwise>
                            <li class="lt ${category.isExpanding ? 'after' : ''}"><a href="javascript:">${category.name}</a>
                                <ul class="downlist" ${category.isExpanding ? 'style="display: block;"' : ''} >
                        </c:otherwise>
                    </c:choose>
                        <c:forEach var="child" items="${category.children}">
                            <li><a href="${basePath}shop/${ss.id}?cat=${child.id}" ${child.id == cat ? 'style="color: red;"' : ''}>${child.name}</a></li>
                        </c:forEach>
                        </ul>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="B_c_right">
        <p class="B_c_title">本店商品</p>
        <ul class="B_cr_list">
		  <c:forEach items="${result.hits }" var="hit">
          	<li>
      		  <c:choose>
				<c:when test="${hit.saleKbn == 1}">
				  <div class="picon"><img src="../images/picon1.png" /></div>
			    </c:when>
				<c:when test="${hit.saleKbn == 2}">
				  <div class="picon"><img src="../images/picon_c1.png" /></div>
			    </c:when>
			    <c:when test="${hit.saleKbn == 4}">
				  <div class="picon"><img src="../images/picon_z1.png" /></div>
			    </c:when>
				<c:when test="${hit.saleKbn == 5}">
				  <div class="picon"><img src="../images/picon_t1.png" /></div>
			    </c:when>
		  	  </c:choose>
				<c:set var="fucoin" value="${hit.maxFucoin>maxBenefit?maxBenefit:hit.maxFucoin}"></c:set>
              	<p class="B_c_img"><a href="${basePath}<fmt:formatNumber value="${hit.productId }" pattern="#0"/>.html?skuId=<fmt:formatNumber value="${hit.minSkuId }" pattern="#0"/>&pageKey=shop" target="_blank"><img src="${hit.image}" width="168px" height="130px"></a></p>
                <h2>${hit.name}</h2>
	            <p class="p1"><span>内购价：¥<fmt:formatNumber value="${hit.salePrice }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="0"/><i>+<fmt:formatNumber value="${fucoin}" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="0"/>券</i></span><em><fmt:formatNumber value="${hit.discount}" type="number" groupingUsed="false" maxFractionDigits="1" minFractionDigits="0"/>折</em></p>
	            <p  class="p2">电商价：￥<fmt:formatNumber value="${hit.price}" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="0"/></p>
            </li>
          </c:forEach>
            <div class="clear"></div>
        </ul>

        <div class="page">
            <c:if test="${result.p > 1 }">
                <a href="${result.getQueryUrl(url,'page',(result.p-1)) }">前一页</a>
            </c:if>
            <c:forEach var="i" begin="${(result.p - (5 - 1)/2) < 1 ? 1 : (result.p - (5 - 1)/2) }" end="${(result.p + (5-1)/2) > result.totalPage ? result.totalPage : (result.p + (5-1)/2)}" varStatus="status">
                <c:if test="${status.first && i <= 3}">
                    <c:forEach var="j" begin="1" end="${i - 1 }">
                        <a href="${result.getQueryUrl(url,'page',j) }">${j }</a>
                    </c:forEach>
                </c:if>
                <c:if test="${status.first && i > 3}">
                    <a href="${result.getQueryUrl(url,'page',1) }">1</a>
                    <a href="${result.getQueryUrl(url,'page',2) }">2</a>
                    <span>...</span>
                </c:if>
                <c:if test="${i != result.p }">
                    <a href="${result.getQueryUrl(url,'page',i) }">${i }</a>
                </c:if>
                <c:if test="${i == result.p }">
                    <a href="javascript:" class="page_curr">${i }</a>
                </c:if>
                <c:if test="${status.last && i < (result.totalPage - 1)}">
                    <span>...</span>
                </c:if>
            </c:forEach>
            <c:if test="${result.p < result.totalPage }">
                <a href="${result.getQueryUrl(url,'page',(result.p+1)) }">后一页</a>
            </c:if>
            <span>共${result.totalPage }页</span>
            <input type="text" id="page" class="input_text">
            <span>页</span>
            <input type="button" id="page_button" value="确定" class="input_btn">
        </div>
    </div>
    <div class="clear"></div>
</div>



<!--footer begin-->
<%@ include file="../common/footer.jsp" %>
<!--footer end-->


<script type="text/javascript" src="${basePath}resources/js/shopping.js"></script>
<script type="text/javascript" src="${basePath}resources/js/application.js"></script>
<script type="text/javascript">
    /**
     * 加载店铺相关信息
     */
    function collectShop(){
        //收藏店铺
        $.ajax({
            type: "POST",
            url: "/collectionShop?shopId=${ss.id}",
            success: function(ret){
                if(ret.success){
                    $(".B_btn .a1").html("已收藏店铺");
                }else{
                    wode.showLoginBox();
                }
            }
        });
    }
    $.ajax({
        type: "POST",
        url: "/selectCollectionShop?shopId=${ss.id}",
        success: function(data){
            if(data){
                $(".B_btn .a1").html("已收藏店铺");
            } else {
                $(".B_btn .a1").click(collectShop);
            }
        }
    });
</script>
<!--登录弹出框 begin-->
<div class="popup_bg" style="display: none;"></div>
<div class="login_popup" style="display: none;">
    <div class="L_popupbox">
        <div class="L_title">
            <p>我的网</p>
            <div class="close_btn"><img src="../images/close.gif" width="14" height="14" alt="close"></div>
        </div>
        <div class="L_cont">
            <div id="pError"><span id="error"></span></div>
            <input class="L_public_input L_txt" type="text" id="userName" placeholder="用户名/邮箱/手机号">
            <input class="L_public_input P_txt" type="password" id="password" placeholder="登录密码">
            <div class="L_lrgst">
                <span class="ylt"><a href="/user/forgetPassword" target="_blank">忘记密码？</a></span>
                <span class="yrt"><a href="/register.html" target="_blank">免费注册</a></span>
            </div>
            <input class="L_popbtn" type="button" name="" value="立即登录" onclick="wode.doLogin();"/>
        </div>
    </div>
</div>
<!--登录弹出框 end-->
<script type="text/javascript" src="${basePath}resources/js/top_ewm.js"></script>
</body>
</html>
