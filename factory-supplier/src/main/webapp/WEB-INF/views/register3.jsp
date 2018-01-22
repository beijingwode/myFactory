<%@ page contentType="text/html;charset=UTF-8" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
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
<link rel="stylesheet" type="text/css" href="${basePath}/css/member.css">
<link rel="stylesheet" type="text/css" href="${basePath}/css/bottom.css">
<script type="text/javascript" src="${basePath}/js/jquery1.8.0.js"></script>
</head>

<body>
<!--top begin-->
<div id="top">	
    <div class="logo">
        <span><a href="${basePath}/user/tosuppliermain.html"><img src="${basePath}/images/logo.png" width="167" height="71" alt="logo"></a></span>
        <strong>商家注册</strong>
    </div>
</div>
<!--top end-->

<!--content begin-->
<div class="L_content">
	<div class="register_center">
    	<ul class="progress_cont progress-3">
        	<li class="Li01 current">填写账号信息</li>
            <li class="Li02 current">验证账号信息</li>
            <li class="Li03-1 current">注册成功</li>
        </ul>
        
        <div class="register_cont">
        	<div class="successwrap">
            	<div class="verify_email">
                    <h2>恭喜您，注册成功！</h2>
                    <strong>您的我的网账号：${result.key }</strong>
                    <div class="goemail">
                        <div class="goemailbtn"><a href="${basePath}/supplier/enterMain.html">马上开店</a></div>
                        <span>进入 <a href="${basePath}/user/tosuppliermain.html">商家中心</a> 或返回我的网 <a href="${basePath}/user/tosuppliermain.html">商家首页</a></span>
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
