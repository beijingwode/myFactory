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
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/stamps_css/My_stamps_enter.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/limitTicket2wx_page.js?0112"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<title>
	<c:choose>
		<c:when test="${errMsg==''}">
	    	领取成功
	    </c:when>
		<c:otherwise>
			领取失败
	    </c:otherwise>
	</c:choose> 
</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
</head>

<body>
<div class="main-cont" id="main-cont">   
    <div class="main-box">
			<c:choose>
				<c:when test="${slt.ticketType==3||slt.ticketType==4}">
	    			<div class="stamps_con xj">
	    		</c:when>
				<c:otherwise>
					<div class="stamps_con">
	    		</c:otherwise>
			</c:choose> 
			<div class="name">
				<c:if test="${type==3}">
					<img src="<%=static_resources %>images/stamps_images/my_stamps_enter7.png" />
				</c:if>
				<c:if test="${type==0||type==2||type==1||type==4}">
					<img src="<%=static_resources %>images/stamps_images/my_stamps_enter6.png" />
					<p class="p1">
					<c:if test="${errMsg==''}">
						恭喜您
						<br/>
						<span>领取成功</span>
					</c:if>
					<c:if test="${type==2||type==1||type==4}">
						<span>领取失败</span>
						<br/>
						${errMsg}
					</c:if>
				</p>
				</c:if>
			</div>
			<div class="stamps_top">
			<c:if test="${type==3}">
				<img src="<%=static_resources %>images/stamps_images/my_stamps_enter8.png" />
			</c:if>
			<c:if test="${type==0||type==2||type==1||type==4}">
			<c:choose>
				<c:when test="${slt.ticketType==3||slt.ticketType==4}">
	    			<img src="<%=static_resources %>images/stamps_images/my_stamps_enter22.png" />
	    		</c:when>
				<c:otherwise>
					<img src="<%=static_resources %>images/stamps_images/my_stamps_enter2.png" />
	    		</c:otherwise>
			</c:choose> 
			</c:if>
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
				<c:if test="${type==3}">
					<img src="<%=static_resources %>images/stamps_images/my_stamps_enter9.png" />
				</c:if>
				<c:if test="${type==0||type==2||type==1||type==4}">
					<c:choose>
						<c:when test="${slt.ticketType==3||slt.ticketType==4}">
			    			<img src="<%=static_resources %>images/stamps_images/my_stamps_enter55.png" />
			    		</c:when>
						<c:otherwise>
							<img src="<%=static_resources %>images/stamps_images/my_stamps_enter5.png" />
			    		</c:otherwise>
					</c:choose> 
				</c:if>
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
					<span class="span3">${limits}</span>
				</div>
				
			</div>
			<div class="btn_suc">
				<c:if test="${type==3||type==4||type==1}">
					<a href="javascript:gotoIndex();">去逛逛</a>
				</c:if>
				<c:if test="${type==0||type==2}">
					<a href="${slt.nextAction}" data-ticket="${ult.id}">立即使用</a>
				</c:if>
			</div>
			<div class="see_box"><a href="javascript:gotomoreLink();">查看卡券包</a>
				<input type="hidden" id="moreLink" value="${moreLink}">
			</div> 
		</div>
						
	</div>
  		    
</div>
<script type="text/javascript">
	function gotoIndex(){
		window.location = jsBasePath+"/index_m.htm";
	}
	function gotomoreLink(){
		var moreLink = $("#moreLink").val();
		window.location = moreLink;
	}
</script>
</body>
</html>
