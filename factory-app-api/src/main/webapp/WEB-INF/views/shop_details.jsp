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
<title>${shopName }</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/shop_details.css" />
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var shopId = '${shopId}';
	var shopName = '${shopName}';
</script>
</head> 

<body>

<body>
<div class="main-cont" id="main-cont" >
	<div class="top_box">
        <div class="top" >
           <a href="javascript:(0);" class="aleft"></a>  	
            <h1>${shopName}</h1>           
        </div>
    </div>
    <div class="main-box" style="top:46px;">    	
        <div class="shop_banner"><img src="<%=static_resources %>images/shop_home_top_img_default.png" /></div>
       
        <div class="shops_con">
        	<div class="shop_xq">
                <dl>
                    <dt><img src="<%=static_resources %>images/shop_home_default_logo.png" /></dt>
                    <dd class="dd1">${shopName}</dd>                    
                </dl>
                <div class="sc_btn">
                <a href="javascript:increaseShop();" id="addShop"><i><img src="<%=static_resources %>images/iv_goods_good_collection_image.png" width="18" height="16" /></i>收藏店铺</a>
                <a href="javascript:removeShop();" id="delShop" style="display: none"><i><img src="<%=static_resources %>images/iv_goods_good_collection_image_select.png" width="18" height="16" /></i>取消收藏</a>
                </div>
            </div>
            <ul id="shops_li">
            	<li><span id="shopDescription">描述相符:</span><span id="shopService">服务态度:</span><span id="deliverySpeed">发货速度:</span></li>
                <li>
                	<span>在线客服：</span><p>${shopName}</p>
                </li>
                <li>
                	<span>商家电话：</span><a href="tel:" id="shopTel"></a>
                </li>
                <li>
                	<span>公司名称：</span><p id="companyName"></p>
                </li>
                <li>
                	<span>公司地址：</span><p id="companyAddress">北京市市辖区技术厉害高科技啊上过课好看就</p>
                </li>
            </ul>
        </div>
    </div>


</div>


<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/shop_details.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main-box").css("top","0");
	}	
});
</script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
