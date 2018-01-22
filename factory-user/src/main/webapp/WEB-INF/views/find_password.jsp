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
<title>我的网-密码找回</title>
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
    	<div class="process_password st1">
        	<div class="password_box">
                <div class="alter_cont">
                    <div class="alter_num">
                        <span class="name">手机/邮箱：</span>
                        <input class="yzminput" type="text" id="userName" name="" value="请填写手机/邮箱" onFocus="if(value==defaultValue){value='';}" onBlur="if(value==''){value=defaultValue;}">
                    	<img id="loading" alt="" src="<%=basePath %>images/loading_small.gif">
                    	<span class="error"></span>
                    </div>
                    <div class="btnnext"><a id="checkUserName" href="javascript:void(0);">下一步</a></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="common/footer.jsp" %>
<!--footer end-->
 <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
 <script>
    $(function(){
    	wode.publiced=1;
    });
</script>
</body>
</html>
