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
var wode={"hasLogin":0};//只保留一个全局变量，避免变量冲突.
wode.domain="http://supplier.wd-w.com"; //"http://www.wd-w.com";
wode.userDomain = "<%=com.wode.factory.supplier.util.Constant.COMM_USER_URL %>";//邮箱验证
wode.comeFrom = "myFactory";//邮箱验证

var jsBasePath='${basePath}';	
</script>
<script type="text/javascript" src="<%=static_resources %>js/views_findpassword2.js"></script>
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
    	<ul class="progress_cont progress-2">
        	<li class="F01 current">身份验证</li>
            <li class="F02 current">重设密码</li>
            <li class="F03-1">完成</li>
        </ul>
        
        <div class="register_cont">
        	<div class="register_left">
               	<input type="hidden" id="code" value="${code}">
               	<input type="hidden" id="userName" value="${phone}">
            	<div class="register_one">
                	<div class="designation">新密码：</div>
                    <input class="register_input" type="password" id="password" name="password" value="">
                    <div class="register_error" id="error2" style="display: none">（6-20位字符，由字母、数字和符号两种以上数字组合）</div>
                    <div class="register_error" id="error5" style="display: none"></div>
                </div>
                <div class="register_one">
                	<div class="designation">确认密码：</div>
                    <input class="register_input" type="password" id="confirmPassword" name="confirmPassword" value="">   
                    <div class="register_error" id="error3" style="display: none">（两次密码输入不一致）</div>                 
                </div>
                <div class="register_sub">
                	<input type="hidden" name="type" value="findPwdByEmail"/>    
<!--                 	<input type="hidden" name="type" value="findPwdByEmail"/>                 -->
                    <div class="register_btn register_after" onclick="return submit();"><a href="javascript:void(0);">提交</a></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->
<div style="display: none">
<iframe id='login_iframe' name="login_iframe"></iframe>
<form method="POST" id="user_from" target="login_iframe">
	<div id="post_param"></div>
</form>
</div>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>
