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
<title>我的福利-修改昵称</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/personal_nc.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var avatar = '${avatar}';
	var nickName = '${nickName}';
	var gender = '${gender}';
	var birthday = '${birthday}';
</script>
</head>
<body>
<div class="main-cont" id="main-cont">
	
    
    <div class="main-box personal_nc_box"  style="position: absolute;top: 0px;">
        <div class="personal_nc"><input id="nickName" type="text" value="${nickName}" /></div>
    	<p>此昵称并非你的登录名称</p>
    	<div id="save"><span>保存</span></div> 
    </div>
    
</div>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
