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
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/public.css"/>
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/attract_investment_ewm.css"/>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/merchan_qr_code.js?1227"></script>
<title>商家二维码</title>
</head>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var userId = ${userId}
</script>
<body>
<div class="main-cont" id="main-cont" >
	<div class="searchbox">					
        <div class="search_inp">
        	<input type="search" id="search" placeholder="搜索" />
        </div>       
        <div class="search_btn">
        	<a id="search_btn" href="javascript:querySupplier();">搜索</a>
        </div>       
	</div> 

	<div class="main-box" style="padding-top:60px;">
		<ul class="company_name">
		</ul>
	</div>
	<div class="add_bus_btn"><a href="javascript:saveSupplier();">添加商家</a></div>
</div>
</body>

</html>
