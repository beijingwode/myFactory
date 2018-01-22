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
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/public.css"/>
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/attract_investment_ewm.css"/>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/qr_code.js"></script>

<title>二维码</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var userId = ${userId}
</script>
</head>
<body>
<div class="main-cont" id="main-cont" >
	<div class="main-box" style="margin-top:10px;padding-bottom:20px;background:#fff;">
		<ul class="code_mess">
			<li>${userShare.userNick}</li>
			<li>
				<c:if test="${userShare.wxTempQrUrl!=null}">
					关注公众号二维码	
				</c:if>
				<c:if test="${userShare.wxTempQrUrl==null}">
					员工绑定二维码	
				</c:if>
			</li>
			<li class="li3">
				<c:if test="${userShare.targetActionUrl!=null}">
					跳转活动页	
				</c:if>
			</li>
			<li class="li4">
				<c:if test="${userShare.targetActionUrl!=null}">
					<a href="${userShare.targetActionUrl}">
					${userShare.targetActionUrl}
					</a>
				</c:if>
			</li>
			<li class="li5">发放换领币：${cont}</li>
		</ul> 
		<div class="code_box">
				<c:if test="${userShare.wxTempQrUrl!=null}">
					<img src="<%=basePath %>userShare/getQr?text=${userShare.wxTempQrUrl}" />
					<input type="hidden" id="linkUrl" value="${userShare.wxTempQrUrl}" />
				</c:if>
				<c:if test="${userShare.wxTempQrUrl==null}">
					<img src="<%=basePath %>userShare/getQr?text=${userShare.nextAction}" />
					<input type="hidden" id="linkUrl" value="${userShare.nextAction}" />
				</c:if>
			<%-- <img src="<%=static_resources %>images/ewm_img.png" /> --%>
		</div>
		<input type="hidden" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${userShare.wxTempLimitEnd}" />" id="limitEnd">
		<input type="hidden" value="${userShare.id}" id="shareId">
		<input type="hidden" value="${userShare.userId}" id="supplierId">
		<input type="hidden" value="${userShare.userNick}" id="userNick">
		<input type="hidden" value="${cont}" id="picr">
		<div class="bottom_btn"><a href="javaScript:delqr()" class="btn1">重新生成</a><a href="javaScript:downLoadQrEmp500Ticket()" class="btn2">保存为图片</a></div>
	</div>
</div>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>