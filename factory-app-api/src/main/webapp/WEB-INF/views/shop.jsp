<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
if(request.getServerPort() != 80 && request.getServerPort() != 443) {
	path=":"+request.getServerPort()+path;
}
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>我的福利-店铺详情</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/shop_search.css" />
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var shopId = '${shopId}';
</script>
</head> 

<body>

<body>
<div class="main-cont" id="main-cont" >
	<input type="hidden" id="pageNum" value="0">
	<div class="top_box" style="z-index: 9;border-bottom:none">
		<div class="search_box">		
        <div class="search_inp">
        	<a href="javascript:void(0);">搜索店铺商品</a>
        </div>
        </div>
     </div>
    <div class="main-box" style="top:56px">
    	<div class="shops_top">
			<div class="shop_banner">
				<img class="alpha" src="<%=static_resources %>images/shop_home_top_img_default.png" onerror="javascript:this.src='<%=static_resources %>images/shop_home_top_img_default.png'"/>
			</div>
			<div class="shop_xq">
				<dl>
					<dt><img src="<%=static_resources %>/images/shop_home_default_logo.png" /></dt>
					<dd class="dd1"></dd>
					<dd class="dd2">粉丝 |<span></span></dd>
				</dl>
				<div class="sc_btn" >
					<a href="javascript:void(0);">
						<img src="<%=static_resources %>/images/shop_home_collect.png" id="addShop" onclick="increaseShop()"/>
						<img src="<%=static_resources %>/images/shop_home_already_collect.png" style="display: none" id="delShop" onclick="removeShop()"/>
					</a>
				</div>
			</div>
    	</div>
    	<div class="shoptop_tab">
				<ul>
					<li class="li1"><a href="javascript:void(0);" class="shopIndex"><i><img src="<%=static_resources %>/images/activity_shop_home_center_text_image.png"></i>店铺首页</a></li>
					<li class="li2"><a href="javascript:ajaxSearchShop();" class="allProduct"><i><img src="<%=static_resources %>/images/shop_home_all_goods.png"></i>全部商品<span></span></a></li>
				</ul>
		</div>
        <div class="shops_con">
            <ul id="shops_li">
            </ul>
        </div>
        <!-- <p class="shop_p">正在加载更多数据</p> -->
    </div>
</div>


<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/shop.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
