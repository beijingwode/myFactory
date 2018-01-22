<%@ page contentType="text/html;charset=UTF-8" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>我的网商家中心</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/member.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/bottom.css">
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">

function submit(){
	$('#sub_form_resetpwd').submit();
}
</script>
</head>

<body>
<!--top begin-->
<div id="top">	
    <div class="logo">
        <span><a href="${basePath}/user/tosuppliermain.html"><img src="<%=static_resources %>images/logo.png" width="167" height="71" alt="logo"></a></span>
        <strong>找回密码</strong>
    </div>
</div>
<!--top end-->

<!--content begin-->
<div class="L_content">
	<div class="register_center">
    	<ul class="progress_cont progress-3">
        	<li class="F01 current">身份验证</li>
            <li class="F02 current">重设密码</li>
            <li class="F03-1">完成</li>
        </ul>
        
        <div class="register_cont">
        	<div class="successwrap">
            	<div class="verify_email">
                    <h2>恭喜您，找回密码成功！</h2>
                    <div class="goemail marleft">
                        <span>登录 <a href="${basePath}/user/tosuppliermain.html">商家中心</a> 或返回我的网 <a href="${basePath}/user/tosuppliermain.html">商家首页</a></span>
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
