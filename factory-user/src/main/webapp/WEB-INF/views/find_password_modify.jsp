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
<title>我的网-找回密码，修改新密码</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/member.css">
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/find_password.js"></script>
<body>
<!--top begin-->
<%@ include file="common/header_05.jsp"%>
<!--top end-->

<!--content begin-->
<div class="L_content">
	<div class="register_cont_wrap">
    	<div class="process_password st3">
        	<div class="password_box">
                <div class="alter_cont">
                	<input type="hidden" id="code" value="${code}">
                	<input type="hidden" id="userName" value="${phone}">
                    <div class="alter_password">
                    	<span class="name">新密码：</span>
                        <input class="passwordtxt" type="password" id="password" placeholder="由4-20位字母、数字组合">
                    	<p class="alter_error" id="passwordError"></p>
                    </div>
                    <div class="alter_password">
                    	<span class="name">确认密码：</span>
                        <input class="passwordtxt" type="password" id="rePassword">
                        <p class="alter_error" id="rePasswordError"></p>
                    </div>
                    <div class="btnnext"><a id="modifyNext" href="javascript:void(0);">下一步</a></div>
                    <!-- <div class="btnprev"><a href="findpassword_02.html">上一步</a></div> -->
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->
<div style="display: none">
<iframe id='forget_iframe' name="forget_iframe"></iframe>
<form method="POST" id="user_from" target="forget_iframe">
	<div id="post_param"></div>
</form>
</div>
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
