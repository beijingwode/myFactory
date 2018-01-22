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
<script type="text/javascript">
function jump() {
	sessionStorage.setItem("openId", "${openId}");
	var shareId= "${shareId}";
	if(shareId!="" && shareId!="-1") {
		sessionStorage.setItem("shareId", shareId);
	}
	sessionStorage.setItem("type", "${type}");
	sessionStorage.setItem("loginNextUrl", "${nextUrl}");
	var state='${state}';

	var uid = getLoginId();
	
	if(state=="friendBind") {
		if(uid == "") {
			if(sessionStorage.shareId){
				window.location='<%=basePath%>userShare/userFriendBind${shareId}?type='+sessionStorage.type;	
			} else {
				window.location="<%=basePath%>user/toLogin?exp1=${openId}&toUrl=&type=W&msg=";
			}
		} else {
			if(sessionStorage.loginNextUrl){
				var loginNextUrl=sessionStorage.getItem("loginNextUrl");
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
	} else if(state=="companyBind" || state=="autoTicket" || state=="reciveTicket"){
		if(uid == "") {
			if(sessionStorage.shareId){
				window.location='<%=basePath%>userShare/companyBindPage${shareId}?openId=${openId}&afterDo='+state;	
			} else {
				window.location="<%=basePath%>user/toLogin?exp1=${openId}&toUrl=&type=W&msg=";
			}
		} else {
			if(sessionStorage.loginNextUrl){
				var loginNextUrl=sessionStorage.getItem("loginNextUrl");
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
		
	} else {
		if(sessionStorage.loginNextUrl){
			var loginNextUrl=sessionStorage.getItem("loginNextUrl");
			if(loginNextUrl.indexOf("uid=")==-1) {
				if(loginNextUrl.indexOf("?")==-1) {
					loginNextUrl=loginNextUrl+"?uid="+uid;
				} else {
					loginNextUrl=loginNextUrl+"&uid="+uid;
				}
			}
			window.location = loginNextUrl;
		} else {
	        window.location = jsBasePath+"shiyongjihe.html";
		}
	}
}

function getLoginId() {
	//获取cookie字符串 
	var strCookie = document.cookie;
	//将多cookie切割为多个名/值对 
	var arrCookie = strCookie.split("; ");
	//遍历cookie数组，处理每个cookie对 
	
	var uid = "";
	for (var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		 if (arr[0] == "uid") {
			//uid
			uid = arr[1];
		}
	}

	return uid;
}
</script>
</head>

<body onload="jump()">
</body>
</html>
