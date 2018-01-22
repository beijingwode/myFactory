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
<meta name = "format-detection" content = "telephone=no">
<title>我的福利</title>

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/address.js?3"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>

<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var orderType='${orderType}';
	var specificationsId='${specificationsId}';
	var productId='${productId}';
	var quantity='${quantity}';
	var partNumbers='${partNumbers}';
	var backNum='${backNum}';
	
</script>
</head> 

<body>
<div class="main-cont" id="main-cont">
	
    
    <div class="main-box" style="position: absolute;top: 0px;">
        <div class="address_box"><a href="javascript:go2Add();">添加收货地址</a></div>
    </div>
    <%@ include file="/commons/alertMessage.jsp" %>
</div>

</body>
</html>
