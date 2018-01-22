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
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" >
<meta content="telephone=no" name="format-detection" />
<title>手机号验证码登录</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/cou_style.css" />
<script type="text/javascript">
var jsBasePath = "<%=basePath %>";
var type = "${type}";
var userNick = "${userNick}";
</script>
</head>

<body>
<div class="main-cont" id="main-cont">
	<div class="top top1">
	<c:if test="${type == 'W'}">
        <h1 style="width:100%;text-align:center;">我的福利 账号绑定</h1>
      	</c:if>
  		<c:if test="${type != 'W'}">
        <h1 style="width:100%;text-align:center;">验证码登录</h1>
    </c:if>
    <input type="hidden" id="msg" value="${msg}">
    <input type="hidden" id="forId" value="${forId}">
    <input type="hidden" id="exp1" value="${exp1}">
    <input type="hidden" id="toUrl" value="${toUrl}">
    <input type="hidden" id="type" value="${type}">
    <input type="hidden" id="shareId" value="${shareId}" />
    </div>
    <div class="main-box">
    	<div class="main_con">
            <div class="int_box">
                <input type="text" id="phone" placeholder="请输入手机号" maxlength="11" />
            </div>
            <div class="int_box">
                <input type="text" id="code" placeholder="请输入短信验证码" maxlength="6" /><a class="yzm_btn" id="yzm_btn" onclick="sendCode(this)" />获取验证码</a>
            </div>
        </div>
        <div class="btn_box"><input type="submit" value="登录" /></div>
        
        <p style="font-size:1.4em;text-align: center;color: #73b4f9;margin-top: 15px;">${msg}</p>
    </div>
     
</div>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/phoneLogin.js"></script>

<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
