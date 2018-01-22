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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/my_hl.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<title>我的换领</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var batchId = "${batchId}"
	var uid=GetUidCookie();
</script>
</head>
<body>

<div class="main-cont" id="main-cont">
<!--content begin-->
	<div class="main_box" style="padding-top:50px;">
		<div class="main_top">
			<ul class="ul1">
				<li class="ul1_li"><a href="javascript:;"  style="width:100%;margin-left:0;text-align:left;">调剂商品</a></li>				
			</ul>			
		</div>	
		<div class="tj_con">
		</div>
		
		<div class="main_top_ypp">
			<ul class="ul1_ypp">
				<li class="ul1_li_ypp"><a href="javascript:;"  style="width:100%;margin-left:0;text-align:left;">原匹配商品</a></li>				
			</ul>			
		</div>	
		<div class="tj_con ypp_con">
		</div>	
		
	</div>
</div>
<script type="text/javascript" src="<%=static_resources %>js/exchange_adjust_details.js"></script>
</body>
</html>
