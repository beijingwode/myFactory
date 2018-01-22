<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 

pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="renderer" content="webkit">
    <meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
    <title>现金储值</title>
</head>
<body>
    <form action="${requestUrl}" method="post" id="pay_form">
        <c:forEach items="${payment}" var="item">
            <input type="hidden" value="${item.value}" name="${item.key}">
        </c:forEach>
    </form>
    准备跳转至银联支付......
<script>
    $("#pay_form").submit();
</script>
</body>
</html>