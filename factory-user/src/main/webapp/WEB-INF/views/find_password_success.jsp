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
<title>我的网-密码找回成功</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/member.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/bottom.css">
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/find_password_success.js"></script>
<body>
<!--top begin-->
<%@ include file="common/header_05.jsp"%>
<!--top end-->

<!--content begin-->
<div class="L_content">
	<div class="register_cont_wrap">
    	<div class="process_password st4">
        	<div class="password_box">
                <p class="seccess">修改密码成功！</p>
                <p class="return_info">3秒后跳转到<a href="../login.html?from=/index.html">&nbsp;&nbsp;登录</a></p>
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
