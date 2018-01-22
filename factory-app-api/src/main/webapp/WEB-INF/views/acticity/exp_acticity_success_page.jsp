<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
if(request.getServerPort() != 80 && request.getServerPort() != 443) {
	path=":"+request.getServerPort()+path;
}
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/ace/get_quan.css" />
<title>奖品领取</title>
</head>

<body>
<div class="main-cont" id="main-cont">   
    <div class="main-box">
    	<img src="<%=static_resources %>images/ace/get_quan7.png" />
    	<div class="con1 con2 con6">
        	<img src="<%=static_resources %>images/ace/get_quan8.png" />
        	<div class="quan_con">
				
			</div>  
        	
        </div>
        <div class="con3">
        	<img src="<%=static_resources %>images/ace/get_the_success_pic3.png" />  
        	<p>奖品由“我的福利”提供</p>
        </div>   
    </div>
     
</div>
</body>

</html>
