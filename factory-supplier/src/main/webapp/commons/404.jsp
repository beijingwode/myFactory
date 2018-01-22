<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ include file="/commons/taglibs.jsp" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragram" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0"> 
<title>商户资质审核中</title>
<%@ include file="/commons/js.jsp" %>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
    <div class="error_cont">
    	<div id="da-error-wrapper">                	
            <div id="da-error-pin"></div>
            <div id="da-error-code">
                404<span>请看提示</span>                    
            </div>        
            <h1 class="da-error-heading">无法显示网页，可能是网络问题，请稍后再试！</h1>
        </div>
    </div>
</div>
<!--content end-->
<%@ include file="/commons/footer.jsp" %>
</body>
</html>