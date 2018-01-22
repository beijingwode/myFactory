<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的网-通过绑定邮箱找回密码</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/member.css">
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
   
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/find_password_send_email.js"></script>
<body>
<!--top begin-->
<%@ include file="common/header_05.jsp"%>
<!--top end-->

<!--content begin-->
<div class="L_content">
	<div class="register_cont_wrap">
    	<div class="process_password st2">
        	<div class="password_box">
        		<input type="hidden" id="email" value="${email}">
                <div class="alter_cont">
                    <p class="Stishi">邮件已发送，请前往<font class="h-blue">${email}</font>查阅我们的邮件</p>
                    <div class="S_btnwrap">
                        <div class="btngoemail"><a href="#">前往邮箱</a></div>
                        <div class="btnprev"><a href="<%=com.wode.factory.user.util.Constant.COMM_USER_URL+"forgetPassword?from=myFactory&domain="+com.wode.factory.user.util.Constant.SYSTEM_DOMAIN%>">上一步</a></div>
                    </div>
                    <p class="Semail">未收到邮件，<a href="javascript:void(0);">重新发送？</a>
                   		<span class="sendEmailResult"></span>
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="common/footer.jsp" %>
<!--footer end-->
 <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script> <script>
    $(function(){
    	wode.publiced=1;
    });
</script>
</body>
</html>
