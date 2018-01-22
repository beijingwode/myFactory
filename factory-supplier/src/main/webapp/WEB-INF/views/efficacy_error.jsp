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
        
        <div class="register_cont">
        	<div class="successwrap">
            	<div class="verify_email" style="margin-left:105px;">
                    <h2>您的链接已经失效！</h2>
                    <div class="goemail">
                        
                        <span style="margin-left:0;">3秒后自动，进入<a href="index.html">商家首页</a></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->
<script type="text/javascript">

$(document).ready(function(){
	setTimeout("gotomain()",3000);
});

function gotomain(){
	window.location.href = "${basePath}/user/login.html";
}

</script>

<%@ include file="/commons/footer.jsp" %>
</body>
</html>
