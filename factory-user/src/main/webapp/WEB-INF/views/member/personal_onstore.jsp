<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-我的收藏店铺</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/store.js"></script>
<body>
	<!--top begin-->
	<%@ include file="../common/header_03.jsp"%>
	<!--top end-->
	<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
	<%@ include file="menu.jsp"%>
	<!--right content-->
	<div class="Me_content">
		<div class="on_title">
			<span class="onlt">我关注的店铺</span>
			<div class="onrt">
				<span>共 ${page.total} 条</span> 
				<span>${page.pageNum}/${page.pages}</span>
				<c:if test="${page.pages>1 && page.pageNum>1}">
					<span><a href="<%=basePath %>member/personalStore">首页</a></span>
					<span><a href="<%=basePath %>member/personalStore?pages=${page.pageNum-1}">前一页</a></span>
				</c:if>
				<c:if test="${page.pageNum!=page.pages && page.pages>1}">
					<span><a href="<%=basePath %>member/personalStore?pages=${page.pageNum+1}">后一页</a></span>
					<span><a href="<%=basePath %>member/personalStore?pages=${page.pages}">末页</a></span>
				</c:if>
			</div>
		</div>

		<div class="onstore_wrap">
			<!--第一个 begin-->
			<input id="pageSize" value="${page.pageSize}" style="display:none;"/>
			<c:forEach var="shop" items="${page.list}" varStatus="status">
			<div class="onstore_one">
				<div class="onstore_info">
					<div class="onsotre_logo">
						<a href="#">
						<c:if test="${not empty shop.logo}">
							<img src="${shop.logo}" width="60"height="60" alt="onsotre_logo">
						</c:if>
						<c:if test="${empty shop.logo}">
							<img src="<%=basePath %>images/onsotre_logo.jpg" width="60"height="60" alt="onsotre_logo">
						</c:if>
						</a>
					</div>
					<p><c:if test="${user.supplierId ==shop.supplierId}">自家</c:if><c:if test="${user.supplierId !=shop.supplierId}">${shop.shopname}</c:if></p>
					<!-- <div class="jp_percent_star"><span class="current_rating a_1"></span></div> -->
					<span><a href="/shop/${shop.id}">进入店铺</a></span> 
					<span><a id="${shop.id}"  href="javaScript:void(0);">取消关注</a></span>
				</div>
				<!--左右按钮图片滚动 begin-->
				<div class="store_showpro">
					<h2>热销商品</h2>
					<div class="feature">                         
                        <div class=block>
                            <div class="botton-scroll" id="s${status.index}">
								<ul class="featureul">
									<c:forEach var="product" items="${productMap[shop.id]}">
									<li>
			                      		<c:if test="${product.saleKbn==1 }">
			                       		<div class="picon"><img src="../images/picon2.png" /></div>
			                       		</c:if>
			                      		<c:if test="${product.saleKbn==2 }">
			                       		<div class="picon"><img src="../images/picon_c2.png" /></div>
			                       		</c:if>
			                       		<c:if test="${product.saleKbn==4 }">
			                       		<div class="picon"><img src="../images/picon_z2.png" /></div>
			                       		</c:if>
			                      		<c:if test="${product.saleKbn==5 }">
			                       		<div class="picon"><img src="../images/picon_t2.png" /></div>
			                       		</c:if>
										<a href="<%=basePath %>${product.id}.html" target="_blank">
											<img src="${product.image}" width="170" height="170">
										</a>
									</li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<a class="prev ${status.index}p" href="javascript:void(0);"></a>
                        <a class="next ${status.index}n" href="javascript:void(0);"></a> 
					</div>
				</div>
				<!--左右按钮图片滚动 end-->
			</div>
			<div class="onstore_line"></div>
			</c:forEach>
		</div>
		<div class="on_title">
			<span class="onbt">我关注的店铺（${page.total}）</span>
			<div class="onrt">
				<span>共 ${page.total} 条</span> 
				<span>${page.pageNum}/${page.pages}</span>
				<c:if test="${page.pages>1 && page.pageNum>1}">
					<span><a href="<%=basePath %>member/personalStore">首页</a></span>
					<span><a href="<%=basePath %>member/personalStore?pages=${page.pageNum-1}">前一页</a></span>
				</c:if>
				<c:if test="${page.pageNum!=page.pages && page.pages>1}">
					<span><a href="<%=basePath %>member/personalStore?pages=${page.pageNum+1}">后一页</a></span>
					<span><a href="<%=basePath %>member/personalStore?pages=${page.pages}">末页</a></span>
				</c:if>
			</div>
		</div>
	</div>
	<!--right contont end-->
	<div class="clear:after"></div>
</div>
<div class="clear"></div>
<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
<!--help begin-->
<%@ include file="../common/footer.jsp" %>
<!--footer end-->
  <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>
