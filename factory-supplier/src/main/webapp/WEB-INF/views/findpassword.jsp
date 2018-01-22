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

var jsBasePath='${basePath}';	

</script>
<script type="text/javascript" src="<%=static_resources %>js/views_findpassword.js"></script>
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
    	<ul class="progress_cont progress-1">
        	<li class="F01 current">身份验证</li>
            <li class="F02">重设密码</li>
            <li class="F03">完成</li>
        </ul>
        
        <div class="register_cont">
        	<div class="register_left">
            	<div class="register_one">
                	<div class="designation"><span>*</span>公司注册邮箱：</div>
                    <input class="register_input" type="text" id="toEmail" name="toEmail" value="${toEmail}" onblur="chek();">
                    <div class="register_error" id="error1" style="display: none">（请填写正确的邮箱）</div>
                </div>
                <div class="register_one">
                	<div class="designation"><span>*</span>邮箱验证码：</div>
                    <input class="register_input" type="text" id="code" name="code" value="">  
                    <div class="register_error" style="margin-left:0px;display: none" id="error_code"></div>   
                </div>
                <div class="sendcode" onclick="cheksend();"><a href="javascript:void(0);">发送验证码</a></div><span style="display: none" id="error2" class="f-error">已发送，请注意查收</span>
                <span style="display: none" id="error3" class="f-error">该邮箱未注册</span>
                <span style="display: none" id="error4" class="f-error">请使用绑定邮箱</span>
                <div class="register_sub">                   
                    <div class="register_btn register_after" onclick="next();"><a href="javascript:void(0);">下一步</a></div>
                </div>
            </div>
            <form action="${basePath}/user/findpassword2.html" id="sub_form" method="post">
               	<input type="hidden" id="tmpCode" name="code">
               	<input type="hidden" id="phone" name="phone">
            </form>
        </div>
    </div>
</div>
<!--content end-->

<%@ include file="/commons/footer.jsp" %>
</body>
</html>
