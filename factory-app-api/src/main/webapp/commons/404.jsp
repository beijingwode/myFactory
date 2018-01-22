<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" >
<meta content="telephone=no" name="format-detection" />
<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/404.css" />
</head>

<body>
<!--content begin-->
<div id="content">
	<div class="errorwrap">
    	<div class="errortext">
        	<p class="errortitle">非常抱歉，您的请求未成功！</p>
            <p><span class="errred">可能原因：</span>您查看的商品不存在或已下架，请选择其它商品</p>
            <p>请尝试以下办法：</p>
            <p>1、请检查您的操作是否正确;</p>
            <p>2、直接联系在线客服 或者拨打010－57746483进行咨询；</p>
            <p>3、返回<a href="http://api.wd-w.com/index_m.htm">我的网首页</a>。</p>
        </div>
    </div>
</div>
<!--content end-->


</body>
</html>
