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
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" >
<meta content="telephone=no" name="format-detection" />
<title>签到</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript">
function jump() {
	var uid=GetUidCookie();
	if(uid!="") {
		window.location = system_domain+"acticity/signPage${activityId}?userId="+uid;
	} else {
		var state="activitySighIn";
		sessionStorage.setItem("activityId", "${activityId}");
		if(isWeiXin()) {
			var rtn = encodeURI(system_domain+"wx/hasBind");
			window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_open_appId+"&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
		} else {
			alert("请使用微信扫一扫，重新扫描二维码");
		}
	}
}
</script>
</head>

<body onload="javascript:jump()">
</body>
</html>
