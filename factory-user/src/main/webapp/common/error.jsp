<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String error = request.getParameter("error");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/common.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/style.css">
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<body>
<!--top begin-->
<%@ include file="header.jsp" %> 
<!--top end-->

<!--content begin-->
<div id="content">
	<div class="errorwrap">
    	<div class="errortext">
        	<p class="errortitle">非常抱歉，您的请求未成功！</p>
            <p><span class="errred">可能原因：</span><%=error%></p>
        </div>
    </div>
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="footer.jsp" %>
<!--footer end-->
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>