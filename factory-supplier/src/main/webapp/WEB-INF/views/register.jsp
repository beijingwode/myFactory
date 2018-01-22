<%@ page contentType="text/html;charset=UTF-8" %>


<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>我的网商家后台</title>
<body onload="toRegister()">
<script>
function toRegister() {
	window.location ="<%=com.wode.factory.supplier.util.Constant.COMM_USER_URL+"register?from=myFactory&domain="+com.wode.factory.supplier.util.Constant.SYSTEM_DOMAIN %>";
}
</script>
</html>
