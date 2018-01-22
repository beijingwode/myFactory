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
<title>我的福利-购物车</title>
<%-- <link rel="stylesheet" type="text/css" href="<%=static_resources %>css/shopping_cart.css" /> --%>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/shopping_cart_v2.css" />
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
</script>
</head> 

<body>

<div class="main-cont" id="main-cont" >
	<div class="top_box">
        <div class="top" >
            <!--<a href="javascript:(0);" class="aleft"></a>-->   	
            <h1>购物车</h1>
            <span>编辑</span>
        </div>
    </div>
    
    <div class="main-box" style="display: none">
    </div>
    <div class="bottom_box">
    	<div class="all_btn" onclick="selAll();"><em></em>全选</div>
        <div class="bottom_box_rt">
        	<div class="rt_con">
            	<p class="p1">总计：<span>￥0.00</span></p>
                <p class="p2">内购券：<span>￥0.00</span></p>
            </div>
            <input type="hidden" id="sel_all" value="0">
            <a href="javascript:void(0);">结算</a>
        </div>
    </div>
</div>
<div class="thickdiv" ></div>
<div class="promotion_bottom">
	<div class ="title">促销</div>
	<div class ="content">
		<ul>
			<li id="productSalesPromotionQC">
				<p><i>企采</i></p>
			</li>
		</ul>
	</div>
	<a href="javascript:void(0)" id="closeBox" class="thickclose" >X</a>
</div>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/cart.js?5"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/order_sessionStorage.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript">
	$(".thickdiv").click(function(){
		$(".thickdiv").hide();
		$(".promotion_bottom").hide();
	})
	$(".thickclose").click(function(){
		$(".thickdiv").hide();
		$(".promotion_bottom").hide();
	})
</script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
