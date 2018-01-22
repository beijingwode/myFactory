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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/bindphone.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/bindphone.js?1213"></script>
<title>绑定手机 成为亲友</title>
</head>
<script type="text/javascript">
var jsBasePath = "<%=basePath %>";
var type = "${type}";
var userNick = "${userNick}";
var openId = sessionStorage.openId;
var empSupplierId = "${empSupplierId}";
</script>
<body style="background:#fff;">
<div class="main-cont" id="main-cont">   
	<input type="hidden" id="shareId" value="${shareId}" />
    <input type="hidden" id="fuid" value="${fuid}" />
    <div class="main-box">
    	<dl>
    		<dt><img src="${userAvatar}" /></dt>
    		<dd class="dd1">${shareMsg1}</dd>
    		<dd class="dd2"><span>${userNick}</span>邀您一起享受海量员工内购价~</dd>
    		<dd class="dd3">绑定手机号，福利升级即刻开始！</dd>
    	</dl>
    	<div class="main_con">
           
            <div class="int_box int_box1">
            	<span class="span1"></span>
                <input type="text" id="phone" placeholder="请输入手机号" maxlength="11" />
            </div>
            <div class="int_box">
            	<span class="span2"></span>
                <input type="text" id="code" placeholder="请输入验证码" maxlength="6" /><a class="yzm_btn" id="yzm_btn" onclick="sendCode(this)" />获取验证码</a>
            </div>
            <div class="int_box int_box3">
            	<span class="span3"></span>
                <input type="text" id="memo" placeholder="请输入姓名(用真实姓名,享更多权益)" maxlength="6" value="<c:if test="${type==9}">临时账号  ${userName}</c:if>" />
            </div>
            <input type="hidden" id="userName" value="${userName}"/>
        </div>
        <c:if test="${empSupplierId != '' }">
			<input type="hidden" id="chooseWorkmate" value="0"/>
	        <p><span id="choose"></span><em>是否为同事？</em></p>
        </c:if>
        <div class="btn_box">立即绑定</div>
        <input type="hidden" id="chooseWork" value="0"/>
        <p><span id="choose2"></span><em>我已认真阅读并同意</em>&nbsp;<a href="<%=basePath%>/protocol.html">《我的网服务协议》</a></p>           
    </div>
     
</div>
 <div class="theme-popover">
     <div class="theme-poptit">          
     </div>
     <div class="theme-popbod">
        <a href="javascript:void(0);">确定</a>  
     </div>
</div>
<div class="theme-popover-mask"></div>
</body>
<script>
</script>
</html>
