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
<title>凑券包详情</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/cou_style.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.flexslider-min.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	sessionStorage.setItem("fuid", "${fuid}");
</script>
</head>

<body>
<div class="main-cont" id="main-cont">
	<div class="top_con_box">
        <div class="top_box">
            <div class="top">
                <h1>凑券包详情</h1>
            </div>
        </div>
        <dl>
        	<dt><img src="${vo.fromUserAvatar}" /></dt>
            <dd class="dd1"><span>${vo.fromUserNike}</span>的凑券包<img src="<%=static_resources %>images/cou_icon.png" /></dd>
            <dd class="dd2">${vo.message}</dd>
        </dl>
    </div>
    <div class="com_det" style="margin-top:10px;">
    	<dl>
        	<dt>
        	<c:if test="${vo.reasonType==1}"><a href="<%=basePath%>productm?productId=${vo.expKey1}&specificationsId=${vo.expKey2}&quantity=1"><img src="${vo.expImg1}" /></a></c:if>
        	<c:if test="${vo.reasonType !=1}"><img src="${vo.expImg1}" /></c:if>
        	</dt>
            <dd>${vo.expMsg1}</dd>
            <dd><span></span></dd>
        </dl>
    </div>
    <div class="main_middle">
    	<em>${note}</em>
	  <c:forEach items="${vo.items}" var="item" varStatus="status">
        <div class="mid_con">
        	<dl>
            	<dt><img src="${item.userAvatar}" /></dt>
                <dd><span style="float:left;text-align:left;">${item.userNike}</span><span style="float:right;text-align:right">${item.price}元</span></dd>
                <dd><em><fmt:formatDate value="${item.updateTime}" pattern="yyyy-M-d HH:mm"/></em>
                <c:if test="${item.orders==1}"><i><img src="<%=static_resources %>/images/i_bg.png" />手气最佳</i></c:if>
                </dd>
            </dl>
        </div>
      </c:forEach>
      <c:if test="${vo.status==4 || vo.status==6}">
        <p>凑到的券，可用于发红包或享受员工福利折扣1</p>
        
        <p> 还可以关注公众号，体验更多员工福利
        <img src="<%=static_resources %>images/push_pic6.png" />
        </p>
	  </c:if>
    </div>
        
    <c:if test="${vo.status!=4 && vo.status!=6}">
    <button><a href="javascript:goNext('帮${vo.userNike}凑券','${vo.id}')">帮他凑券</a></button>
    </c:if>
</div>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/blackEnvelope.js?1213"></script>
</html>
