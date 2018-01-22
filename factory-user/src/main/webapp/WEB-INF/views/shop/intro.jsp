<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../common/include.jsp" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>我的福利</title>
    <link rel="stylesheet" href="${basePath}css/Brand.css" type="text/css" />
    <link rel="stylesheet" href="${basePath}css/loginpopup.css" type="text/css" />
    <script type="text/javascript" src="${basePath}resources/js/jquery1.8.0.js"></script>
    <script type="text/javascript" src="${basePath}resources/js/jquery.luara.0.0.1.min.js"></script>
</head>
<body>

<%@ include file="head.jsp"%>
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
    <li><a href="${basePath}shop/${ss.id}">首页</a></li>
    <li><a href="javascript:" class="a1">品牌介绍</a></li>
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

    <div class="">
        <p class="B_c_title">品牌介绍</p>
        <div class="B_c_con">
            ${ss.brandIntroduce}

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
                <span class="ylt"><a href="/register.html" target="_blank">忘记密码？</a></span>
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
