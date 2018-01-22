<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
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
<title>卡券领取</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/stamps_css/My_stamps_enter.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
function toRecive() {
	sessionStorage.setItem("fromId", "${fromId}");

	var uid=GetUidCookie();
	if(uid!="") {
		window.location = system_domain+"limitTicket/recive${slt.id}.user?uid="+uid;
	} else {
		var registeFlg = '${slt.registeFlg}';
		var state="bindOrLogin";
		if(registeFlg=='1') {
			sessionStorage.setItem("shareId", "${slt.supplierId}");
		}
		sessionStorage.setItem("loginNextUrl", system_domain+"limitTicket/recive${slt.id}.user");
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

<body <c:if test="${onload=='autoRecive'}">onload="javascript:toRecive()"</c:if>>
<c:if test="${onload!='autoRecive'}">
<div class="main-cont" id="main-cont">   
    <div class="main-box">

		<div class="stamps_con">
			<div class="name">
				<img src="<%=static_resources %>images/stamps_images/my_stamps_enter1.png" />
				<p>${slt.companyName}</p>
			</div>
			<div class="stamps_top">
				<img src="<%=static_resources %>images/stamps_images/my_stamps_enter2.png" />
				<div class="top_con">
				<c:if test="${fn:length(sltsList)==0}">
					<p class="p2">${slt.ticketNote}</p>
				</c:if>
				<c:if test="${fn:length(sltsList)==1}">
					<dl>
						<dt><img src="${sltsList[0].image}" /><i></i></dt>
						<dd class="dd1">${sltsList[0].productName}</dd>
						<dd class="dd2">${sltsList[0].itemValues}</dd>
					</dl>
				</c:if>
				<c:if test="${fn:length(sltsList)>=2}">
					<ul>
						<c:forEach items="${sltsList}" var="list" varStatus="s">
							<c:if test="${s.index<=2}">
							<li><img src="${list.image}" /><i></i></li>
							</c:if>
						</c:forEach>
						<c:if test="${fn:length(sltsList)==2}">
					   	 	<li class="li1">2选1</li>
					    </c:if>	
						<li class="li2"><img src="<%=static_resources %>images/stamps_images/sandian.png" /></li>
					</ul>
				</c:if>	
				</div>
			</div>
			<div class="stamps_bottom">
				<img src="<%=static_resources %>images/stamps_images/my_stamps_enter3.png" />
				<div class="span_box">
					<span class="span1">
						<c:choose>
							<c:when test="${slt.ticketType==2}">
	    						 免费
	    					</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${slt.cash>0}">
			    						${slt.cash}
			    					</c:when>
									<c:otherwise>
										${slt.ticket}
			    					</c:otherwise>
								</c:choose> 
	    					</c:otherwise>
						</c:choose> 
					</span><!-- 免费  span1_1 -->
					<span class="span2">
						<c:if test="${slt.ticketType==1}">内购抵扣券</c:if>
    					<c:if test="${slt.ticketType==2}">免费体验券</c:if>
    					<c:if test="${slt.ticketType==3}">通用现金券</c:if>
    					<c:if test="${slt.ticketType==4}">专用现金券</c:if>
					</span>
					<span class="span3"><fmt:formatDate pattern="yyyy.MM.dd" value="${slt.limitStart}" /> - <fmt:formatDate pattern="yyyy.MM.dd" value="${slt.limitEnd}" /></span>
				</div>
				<div class="btn"><a href="javascript:toRecive();">立即领取</a></div>
			</div>
			<div class="num_box">已领取${slt.receiveNum}份/共${slt.ticketNum}份</div>
		</div>
						
	</div>
  		    
</div>
</c:if>
<!-- <a href="javascript:;" onclick="javascript:toRecive();">立即领取</a> -->
</body>
</html>
