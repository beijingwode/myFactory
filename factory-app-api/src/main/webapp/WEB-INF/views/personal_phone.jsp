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
<title><c:if test="${phone!=''&&phone!=null}">我的福利-修改手机</c:if><c:if test="${phone==null||phone==''}">我的福利-绑定手机</c:if></title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var avatar = '${avatar}';
	var nickName = '${nickName}';
	var gender = '${gender}';
	var birthday = '${birthday}';
	var phone = '${phone}';
</script>
</head>
<body>
<div class="main-cont" id="main-cont">
	
    	<div class="main_con">
            <div class="int_box">
                <input type="tel" id="phone" placeholder="请输入手机号" maxlength="11" value="${phone}" />
            </div>
            <div class="int_box">
                <input type="text" id="code" placeholder="请输入短信验证码" maxlength="6" /><a class="yzm_btn" id="yzm_btn" onclick="sendCode(this)" />获取验证码</a>
            </div>
        </div>
        <div id="save" ><span>保存</span></div> 
</div>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/personal_phone.js"></script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
