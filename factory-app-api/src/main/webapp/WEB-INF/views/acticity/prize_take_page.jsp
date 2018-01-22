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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/ace/get_the_success.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<title>奖品领取</title>
</head>

<body>
<div class="main-cont" id="main-cont">   
    <div class="main-box">
    	<img src="<%=static_resources %>images/ace/pick_up_the_bag1.png" />
    	<div class="con1 con2">
        	<img src="<%=static_resources %>images/ace/pick_up_the_bag2.png" />  
        	<dl>
        	<input type="hidden" id="prizeId" value="${supplierPrize.id}">
        		<dt><a href="javascript:void();"><img src="<%=static_resources %>${supplierPrize.image}" /></a></dt>
        		<dd><a href="javascript:void();">${supplierPrize.prizeName}</a></dd>
        	</dl>
        </div>
        <div class="con3">
        	<img src="<%=static_resources %>images/ace/pick_up_the_bag3.png" />  
        	<a href="javascript:toAddMsgPage();">立即领取</a> 
        	<p>福袋商品由“我的福利”提供</p>
        </div>   
    </div>
     
</div>
</body>
<script type="text/javascript">
function toAddMsgPage(){
	window.location=jsBasePath+'acticity/toAddMsgPage?supplierPrizeId='+$("#prizeId").val();
}
</script>
</html>
