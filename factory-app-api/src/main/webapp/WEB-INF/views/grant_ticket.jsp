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
<title><c:choose><c:when test="${errCode == 'success' }">领取成功</c:when><c:otherwise>领取失败</c:otherwise></c:choose></title>
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/public.css"/>
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/lingquan.css"/>
</head>
<body <c:if test="${errCode != 'success'}"> style="background:#fff"</c:if>>
<div class="main-cont" id="main-cont" >
  <c:choose>
    <c:when test="${errCode == 'success' }">
	  <div class="main-box main-box1">
		<img src="<%=static_resources %>images/lingquan_suc.png" /> 
	  </div>
	  <div class="bottom_btn"><a href="<%=basePath %>index_m.htm">去逛逛</a></div>
    </c:when>
    <c:when test="${errCode == 'need login' }">
	  <div class="main-box main-box2">
		<img src="<%=static_resources %>images/lingquan_lose.png" /> 
		<p>领取失败，请先登录再扫码</p>
		<a href="<%=basePath%>user/toLogin?exp1=${openId}&toUrl=&type=W&msg=">去登录</a>
		<script type="text/javascript">
			sessionStorage.setItem("loginNextUrl", "<%=basePath %>index_m.htm");
		</script>
	  </div>
    </c:when>
    <c:otherwise>
	  <div class="main-box main-box2">
		<img src="<%=static_resources %>images/lingquan_lose.png" /> 
		<p>领取失败，二维码已失效</p>
		<a href="javascript:selfClose();">确定</a>
		<script type="text/javascript">
			function selfClose() {
			    try{
				    WeixinJSBridge.call('closeWindow');
				} catch(e) {
				    alert(e);
					window.close();
				}
			}
		</script>
	  </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
