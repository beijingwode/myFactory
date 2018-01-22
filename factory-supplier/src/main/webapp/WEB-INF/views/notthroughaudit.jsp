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
                信 息 <span>平台正在审核您的商家资质</span> 
             <span>请耐心等待……</span>                    
            </div>        
            
        </div>
    </div>
</div>
<!--content end-->
<%@ include file="/commons/footer.jsp" %>
</body>
</html>