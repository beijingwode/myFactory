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
<title>绑定手机</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript">
function jump() {
	sessionStorage.setItem("type", "${type}");
	if ("${fuid}"!='') {
		sessionStorage.setItem("fuid", "${fuid}");
	}
	if ("${shareId}"!='') {
		sessionStorage.setItem("shareId", "${shareId}");
	}
	if ("${loginNextUrl}"!='') {
		sessionStorage.setItem("loginNextUrl", jsBasePath + "${loginNextUrl}");
	}
	
	var uid=GetUidCookie();
	if(uid!="") {
		if('${type}'=='') {
			window.location = jsBasePath+'userShare/userFriendBind${shareId}?fuid=${fuid}&type=${type}';
		} else {
			if(sessionStorage.loginNextUrl) {
				var loginNextUrl=sessionStorage.loginNextUrl;
				if(loginNextUrl.indexOf("uid=")==-1) {
					if(loginNextUrl.indexOf("?")==-1) {
						loginNextUrl=loginNextUrl+"?uid="+uid;
					} else {
						loginNextUrl=loginNextUrl+"&uid="+uid;
					}
				}
				window.location = loginNextUrl;
			} else {
				window.location = jsBasePath+"index_m.htm";
			}
		}
		return;
	} else {
		if(isWeiXin()) {
			var state="friendBind";
			var rtn = encodeURI(jsBasePath+"wx/hasBind");
			window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_open_appId+"&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
		} else {
			alert("请使用微信扫一扫，重新扫描二维码");
		}
	}
}
</script>
</head>

<body onload="jump()">
</body>
</html>
