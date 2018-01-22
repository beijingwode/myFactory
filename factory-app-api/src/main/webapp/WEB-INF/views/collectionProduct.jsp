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
<title>我的福利-商品收藏</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/collectionProduct.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
</head> 

<body>
<div class="main-cont" id="main-cont">
	
    
    <div class="main-box" style="position: absolute;top: 5px;">
    <input type="hidden" id="pageNum" value="0">
        <div class="like_con">
	 		<ul id="like_li">
            </ul>	 		
	 	</div>
        <!-- <div class="bottom_hint">已经浏览到最后了</div> -->
    </div>
    <div class="shop_bottom" style="display:block">
    	<div class="all_btn" onclick="selall();"><em><input type="hidden" id="sel_all" value="0"></em>全选</div>
        <div class="del_btn" onclick="delProduct();"><img src="<%=static_resources %>/images/del_btn.png" /></div>
    </div>
</div>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
