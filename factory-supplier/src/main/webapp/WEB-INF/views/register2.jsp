<%@ page contentType="text/html;charset=UTF-8" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>我的网商家后台</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/member.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/bottom.css">
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
var jsKey="${result.key }";
var jsBasePath='${basePath}';
var jsMessage="${result.message }";
</script>
<script type="text/javascript" src="<%=static_resources %>js/views_register2.js"></script>
</head>

<body>
<!--top begin-->
<div id="top">	
    <div class="logo">
        <span><a href="${basePath}/user/tosuppliermain.html"><img src="<%=static_resources %>images/logo.png" width="167" height="71" alt="logo"></a></span>
        <strong>商家注册</strong>
    </div>
</div>
<!--top end-->

<!--content begin-->
<div class="L_content">
	<div class="register_center">
    	<ul class="progress_cont progress-2">
        	<li class="Li01 current">填写账号信息</li>
            <li class="Li02 current">验证账号信息</li>
            <li class="Li03-1">注册成功</li>
        </ul>
        
        <div class="register_cont">
        	<div class="register_left">
            	<div class="verify_email">
                    <p>您的注册邮箱：<c:out value="${result.key }"></c:out> </p>
                    <strong>我的网已经向您的注册邮箱发放了一封激活邮件，请24小时内登录该邮箱完成账号注册。</strong>
                    <div class="goemail">
                        <div class="goemailbtn"><a id="goemailbtn" href="javascript:void(0);" target="_blank">去邮箱验证</a></div>
                        <span onclick="reSendEmail();" ><a href="javascript:void(0);">没收到？再次发送</a></span>
                    </div>
                    <div class="goproblem">
                    	<h3>还没收到验证邮件？</h3>
                        <p>请确认邮件是否被您提供的邮箱系统自动拦截，或被误认为垃圾邮箱放到垃圾箱了。</p>
                        <p>如果您的邮箱不可用，请您更换邮箱重新注册。</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->

<%@ include file="/commons/footer.jsp" %>
</body>
</html>
