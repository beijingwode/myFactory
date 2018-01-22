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
<title>我的福利-个人资料</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/personal.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var avatar = '${avatar}';
	var nickName = '${nickName}';
	var gender = '${gender}';
	var birthday = '${birthday}';
	var phone = '${phone}';
	var sectionName = '${sectionName}';
</script>
</head>
<body>
<div class="main-cont" id="main-cont">
	
    
    <div class="main-box" style="position: absolute;top: 0px;">
        <div class="ads_con personal_box">
        	<ul>
            	<li class="tx_photo">
            		<a href="javascript:selectImg();"><span>头像：</span><em><img src="<%=static_resources %>images/activity_mine_imageview_headphoto_image_c.png" /></em></a>
            	</li>
                <li class="nicheng"><a href="javascript:void(0);"><span>昵称：</span><em></em></a></li>
                <li class="sex"><a href="javascript:void(0);"><span>性别：</span><em></em></a></li>
                <li class="birthday"><a href="javascript:void(0);"><span>生日：</span><em></em></a></li>
                <li class="mobile"><a href="javascript:void(0);"><span>手机：</span><em></em></a></li>
                <li class="sectionName" style="display: none"><a href="javascript:void(0);"><span>部门：</span><em></em></a></li>
                <li class="acount"><a href="javascript:void(0);" style="background:url()"><span>账号：</span><em></em></a></li>
                <!-- <li><a href="#"><span>修改登录密码：</span><em></em></a></li> -->
           </ul>
        </div>
    	
		<input type="file" id="fileInput" style="display: none" accept="image/*" onchange="updateAvatar(this);">
    </div>
    
<%@ include file="/commons/alertMessage.jsp" %>
</div>
</body>
</html>
