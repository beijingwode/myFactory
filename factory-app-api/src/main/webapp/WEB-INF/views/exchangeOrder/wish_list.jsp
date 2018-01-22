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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>我的换领</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/my_hl.css" />
<script>
	var jsBasePath = '<%=basePath %>';
</script>
</head> 
<body>

<div class="main-cont" id="main-cont">
<!--content begin-->
	<input type="hidden" id="pageNum" value="0">
	<div class="main_box main_box2">
		<div class="main_top">
			<ul class="ul1">
				<li><a href="javascript:myhlorder()">欲领清单</a></li>
				<li><a href="javascript:;" class="this_one">调剂清单</a></li>
			</ul>
			<ul class="ul3">
				<li id="selectedPro" class="crr"><a href="javascript:void(0)">已选商品</a></li>
				<li id="weiPro"><a href="javascript:void(0);">备选商品</a></li>
			</ul>
		</div>	
		<div class="tab_box">
			
		</div>
		
		<!-- <div class="tab_box dis">
			
		</div> -->
	</div>
</div>
<%@ include file="/commons/alertMessage.jsp" %>
</body>

<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/zepto.min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/exchangeOrder/wish_list.js"></script>
</html>