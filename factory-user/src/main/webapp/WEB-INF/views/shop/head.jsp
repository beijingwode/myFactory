<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<div id="top">
   <%@ include file="/common/_top.jsp" %>
</div>

<div class="head">
    <div class="search">
        <div class="searchbox">
            <form action="${basePath }product/search" method="get" id="search_form">
                <input class="searchinput" type="text" name="key" value="${keyword}" onBlur="if(value==''){value=defaultValue;}" onFocus="if(value==defaultValue){value='';}">
                <input  class="search_btn" id="search_btn" type="submit" value="搜索" />
                <button class="search_btn btn_ll" onclick="searchShop();">搜本店</button>
            </form>
        </div>
        <div class="search_list">
            <ul>
                 <li><a href="http://wd-w.com/product/search?key=%E5%AE%B6%E7%94%A8%E7%94%B5%E5%99%A8&page=1#orders">电器</a></li>
                 <li><a href="http://wd-w.com/product/search?key=%E6%A2%B5%E7%94%B0">家居</a></li>
                 <li class="curr"><a href="http://wd-w.com/product/search?key=%E6%80%9D%E5%87%AF%E4%B9%90">思凯乐</a></li>
                 <li><a href="http://wd-w.com/product/search?key=%E9%9F%A9%E5%A6%99">韩妙</a></li>
            </ul>
        </div>
    </div>
    <div class="logo"><a href="/"><img src="/images/logo.png" alt="logo"></a></div>
    <div class="Brand_logo">
        <span class="B_img"><a href="${basePath}shop/${ss.id}"><img src="${ss.logo}"  /></a></span>
        <span class="B_text">
        <c:if test="${user.supplierId ==ss.supplierId}">自家</c:if>
        <c:if test="${user.supplierId !=ss.supplierId}">${shopName}</c:if>
         <br /> <c:if test="${not empty ss.qq}"><img  style="CURSOR: pointer" onclick="javascript:window.open('http://wpa.qq.com/msgrd?v=3&uin=${ss.qq}&site=qq&menu=yes', '_blank', 'height=502, width=644,toolbar=no,scrollbars=no,menubar=no,status=no');"  border="0" SRC=http://wpa.qq.com/pa?p=1:${ss.qq}:10 alt="联系客服"></c:if>
         
	        <div class="Brand_js">
	            <p class="B_sj"></p>
	            <div class="B_con">
	                <p class="Bc_title">商家：<c:if test="${user.supplierId ==ss.supplierId}">自家</c:if>
	        <c:if test="${user.supplierId !=ss.supplierId}">${shopName}</c:if>  </p>
	                <!--<div class="pf">
	                    <ul class="ul_left">
	                        <li class="li1">评分</li>
	                        <li>商品评分：<em>4.50</em>分</li>
	                        <li>服务态度：<em>4.27</em>分</li>
	                        <li>物流速度：<em>4.66</em>分</li>
	                    </ul>
	                    <ul class="ul_left w100">
	                        <li class="li1">同行业对比</li>
	                        <li><i></i>16.33%</li>
	                        <li><i class="xj"></i>7.45%</li>
	                        <li><i></i>15.64%</li>
	                    </ul>
	                    <div class="clear"></div>
	                </div>-->
	                <ul class="b_add">
	                    <li>公司：<c:if test="${user.supplierId ==ss.supplierId}">自家</c:if><c:if test="${user.supplierId !=ss.supplierId}">${supplier.comName}</c:if></li>
	                    <li>地址：${supplier.comState} ${supplier.comCity} ${supplier.comAddress}</li>
	                    <li>电话：${ss.shopPhone}</li>
	                </ul>
	                <div class="B_btn">
	                    <a href="javascript:" class="a1">收藏店铺</a>
	                </div>
	            </div>
	
	        </div>
        </span>
    </div>

</div>
<script type="text/javascript">
    function searchShop() {
        $("#search_form").attr("action","${basePath }shop/search/${ss.id}");
        $("#search_form").submit();
        rememberKey();
    }
   $('.Brand_logo .Bc_title').find('br').remove();
</script>