<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE HTML>
<html>
<title>商家中心</title>
<head>
<meta charset="utf-8">
</head>
<body onload="toLogin()">
<script>
function toLogin() {
	window.location="<%=Constant.COMM_USER_URL+"?from=myFactory&domain="+Constant.SYSTEM_DOMAIN %>";	
}
</script>
</body>
</html>
