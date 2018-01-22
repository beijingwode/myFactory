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
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/public.css"/>
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/hlb_get_results.css"/>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<title><c:if test="${errMsg=='' || errMsg=='again'}">领取成功</c:if><c:if test="${errMsg!='' && errMsg!='again'}">领取失败</c:if></title>
</head>

<body>
<div class="main-cont" id="main-cont" >
  <c:if test="${errMsg==''}">
	<img src="<%=static_resources %>images/hlb_get_the_success_bg.png" class="img_bg" />
  </c:if>
  <c:if test="${errMsg=='again'}">
	<img src="<%=static_resources %>images/hlb_get_the_again_bg.png" class="img_bg" /> 
  </c:if>
  <c:if test="${errMsg!='' && errMsg!='again'}">
	<img src="<%=static_resources %>images/hlb_get_the_failure_bg.png" class="img_bg" />
  </c:if>
	<div class="main-box">
		<div class="failure">
	      <c:if test="${errMsg!='' && errMsg!='again'}">
			${errMsg}
	  	  </c:if>
	  	</div>
		<div class="com_name"><span>${companyName}</span><em>员工专享福利</em></div>
		<div class="hlb">${amount}</div>
		<div class="end_date">${limits}</div>
		<div class="go_btn"><a href="${moreLink}"><img src="<%=static_resources %>images/go_btn.png" /></a></div>
	</div>
</div>
<script type="text/javascript">
</script>  
</body>
</html>
