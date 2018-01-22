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
<title>${vo.share.shareTitle}</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/WeChat_share.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/beehive_640.js"></script>

</head>

<body>
<div class="main-cont" id="main-cont">
	<div class="fd_box">
        <dl>
            <dt><img src="${vo.share.userAvatar}" /></dt>
            <dd>${vo.share.userNick}</dd>
            <dd>${vo.share.shareMsg1}</dd>
        </dl>
    </div>
	<div class="con_box">        
        <h3>${vo.share.shareMsg2}</h3>
        <h5>${vo.share.shareMsg3}</h5>
         <div class="main_three_con">
             <ul>
				<c:forEach items="${vo.items}" var="item" varStatus="status">
                 	<c:if test="${status.index%2 == 0 }">
                 	<li>
                    <dl>
	                    <c:if test="${item.saleKbn == 1}">
	                 	<div class="picon"><img src="<%=static_resources %>images/picon1.png" /></div>
	         			</c:if>
	                    <c:if test="${item.saleKbn == 2}">
	                 	<div class="picon"><img src="<%=static_resources %>images/picon_c1.png" /></div>
	         			</c:if>
	         			<c:if test="${item.saleKbn == 4}">
	                 	<div class="picon"><img src="<%=static_resources %>images/picon_z1.png" /></div>
	         			</c:if>
	                    <c:if test="${item.saleKbn == 5}">
	                 	<div class="picon"><img src="<%=static_resources %>images/picon_t1.png" /></div>
	         			</c:if>
                        <dt><a href="javascript:void(0);"><img src="${item.image}" /></a></dt>
                        <dd class="dd1"><a href="javascript:void(0);">${item.productName}</a></dd>
                        <dd class="dd2">￥${item.realPrice}+${item.welFare}券</dd>
                    </dl>
                    </c:if>
                 	<c:if test="${status.index%2 == 1 }">
                     <dl>
	                     <c:if test="${item.saleKbn == 1}">
	                 	<div class="picon"><img src="<%=static_resources %>images/picon1.png" /></div>
	         			</c:if>
	                     <c:if test="${item.saleKbn == 2}">
	                 	<div class="picon"><img src="<%=static_resources %>images/picon_c1.png" /></div>
	         			</c:if>
	                     <c:if test="${item.saleKbn == 5}">
	                 	<div class="picon"><img src="<%=static_resources %>images/picon_t1.png" /></div>
	         			</c:if>
                         <dt><a href="javascript:void(0);"><img src="${item.image}" /></a></dt>
                         <dd class="dd1"><a href="javascript:void(0);">${item.productName}</a></dd>
                         <dd class="dd2">￥${item.realPrice}+${item.welFare}券</dd>
                     </dl>
                 	</li>
                    </c:if>
     			</c:forEach> 
             </ul>
         </div>
        <div class="ewm_img"><img src="<%=basePath %>userShare/getQr?text=${vo.share.nextAction}" ><p>${vo.share.shareFooter1}</p><p>${vo.share.shareFooter2}</p><p>${vo.share.shareFooter3}</p></div>
    </div>
</div>
</body>

<script type="text/javascript">

$(document).ready(function(){
	$("a").attr("href","${vo.share.nextAction}");
});
</script>
</html>
