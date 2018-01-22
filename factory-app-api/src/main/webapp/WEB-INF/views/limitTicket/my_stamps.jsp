<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/stamps_css/My_stamps.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/limit_ticket/my_stamps.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/handleTime.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/limitTicket2wx_page.js?0112"></script>
<title>我的券</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var uid=GetUidCookie();//用户id
</script>
</head>

<body>
<div class="main-cont" id="main-cont">   
    <div class="main-box">
    	<ul id="ticket">
    	
    	</ul>
    	<div class="my_stamps_ewm"><img src="<%=static_resources %>images/stamps_images/my_stamps_ewm.png" /></div>
    </div>
     
</div>
<script>
$(function(){
	$(".dakai1").toggle(function(){
		$(this).html('<img src="'+jsBasePath+'static_resources/images/stamps_images/shouqi_icon.png" /></em>');
		$(this).parent().parent().parent().parent().siblings(".gz").show();
	},function(){
		$(this).html('<img src="'+jsBasePath+'static_resources/images/stamps_images/dakai_icon.png" /></em>');
		$(this).parent().parent().parent().parent().siblings(".gz").hide();
	})
	$(".dakai2").toggle(function(){
		$(this).html('<img src="'+jsBasePath+'static_resources/images/stamps_images/shouqi_icon1.png" /></em>');
		$(this).parent().parent().parent().parent().siblings(".gz").show();
	},function(){
		$(this).html('<img src="'+jsBasePath+'static_resources/images/stamps_images/dakai_icon1.png" /></em>');
		$(this).parent().parent().parent().parent().siblings(".gz").hide();
	})
})
</script>
</body>

</html>
