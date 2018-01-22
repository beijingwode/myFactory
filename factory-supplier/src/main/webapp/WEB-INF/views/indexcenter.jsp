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
<title>我的网-商家登录</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/member.css">

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/bottom.css">
<script type="text/javascript" src="<%=com.wode.factory.supplier.util.Constant.COMM_USER_URL %>cookie.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>resources/js/util.js"></script>
<script type="text/javascript">

var jsError='${param.error}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/views_indexcenter.js"></script>
</head>

<body>
<!--top begin-->
<div id="top">	
    <div class="logo">
        <span><a href="${basePath}/user/login.html"><img src="<%=static_resources %>images/logo.png" width="167" height="71" alt="logo"></a></span>
        <strong>商家登录</strong>
    </div>
</div>
<!--top end-->

<!--content begin-->
<div class="L_content">
	<div class="login_img"><img src="<%=static_resources %>images/login_img.jpg" width="490" height="400" alt="login_img"></div>
    <div class="login_cont">
    	<div class="login_box">
    	<form  id="sub_form_resetpwd" action="${basePath}/user/hasLogin.html" method="post">
            <div class="login_one">
                <div class="name">账号：</div>
                <input class="user_input" id="userName" name="userName" type="text"  tabindex="1" value="">
                <div class="freebtn"><span id="error1" style="display: none">邮箱不能为空！</span><a href="${basePath}/user/toregister.html">免费注册</a></div>
            </div>
            <div class="login_one" style="margin-top:32px; *margin-top:0;">
                <div class="name">密码：</div>
                <input class="user_input"  id="password" name="password" type="password" maxlength="20"  tabindex="2" value="">
                <div class="freebtn"><span id="error2" style="display: none">密码不能为空且最少6位！</span><a href="${basePath}/user/findpassword.html">忘记密码？</a></div>
            </div>
            <div class="login_btn" onclick="_login()"><a href="javascript:void(0);" >登录</a></div>
            </form>
        </div>
    </div>
</div>
<!--content end-->

<%@ include file="/commons/footer.jsp" %>
</body>
</html>