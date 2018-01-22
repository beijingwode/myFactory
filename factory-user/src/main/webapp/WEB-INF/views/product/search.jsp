<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="${basePath }css/common.css">
<link rel="stylesheet" type="text/css" href="${basePath }css/style.css">
</head>
<script type="text/javascript" src="${basePath }resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="${basePath }resources/js/jquery.Query.js"></script>
<script type="text/javascript"	src="${basePath }resources/js/application.js"></script>
<script type="text/javascript" 	src="${basePath }resources/js/shopping.js"></script>
<body>
	<!--top begin-->
	<%@ include file="/common/header.jsp"%>
	<!--top end-->

	<!--content begin-->
	<div id="content">
		<div class="position">
			<a>全部搜索结果</a><em>></em><a>"${keyword }"</a>
		</div>

		<div class="detail_wrap">
			<!--left begin-->
			<div class="left">
				<div class="product_sort">
					<ul>
					  <c:forEach items="${result.categories.keySet() }" var="parent_cat">
						<li class="lt"><a href="${result.getQueryUrl(url,'cat',parent_cat) }">${catService.findById(parent_cat).name}</a>
							<ul class="downlist" style="display: block;">
								<c:forEach items="${result.categories.get(parent_cat) }" var="cat">
								<li><a href="${result.getQueryUrl(url,'cat',cat) }">${catService.findById(cat).name }</a></li>
								</c:forEach>
							</ul>
						</li>
					  </c:forEach>
					</ul>
				</div>

				<div class="sort">
					<h2>推广商品</h2>
					<div class="historyproduct">
						<ul>
						  <c:forEach items="${recommend}" var="rep">
							<li>
								<div class="p-img">
									<a href="/${rep.id }.html" target="_blank" title="${rep.fullName}">
										<img src="${rep.image }" width="174" height="77" alt="${rep.fullName }">
									</a>
								</div>
								<div class="p-price">
									<strong>¥${rep.showPrice }</strong>
								</div>
								<div class="p-name">
									<p>
										<a href="/${rep.id }.html" target="_blank" title="${rep.fullName}">${rep.fullName }</a>
									</p>
								</div>
							</li>
						  </c:forEach>
						</ul>
					</div>
				</div>

			</div>
			<!--left end-->

			<!--right begin-->
			<div class="right">
				<div class="hot_recommend">
					<h2>今日特供福利</h2>
					<div class="recommend_list">
						<ul>
						  <c:forEach items="${hotProducts}" var="pro" begin="0" end="3">
							<li>
								<div class="l_img">
									<a href="/${pro.id}.html?pageKey=search" target="_blank" title="${pro.fullName}">
										<img src="${pro.image}" width="104" height="71" alt="${pro.fullName}">
									</a>
								</div>
								<div class="l_r">
									<p class="p3" style="height: 40px; overflow: hidden;">
										<a href="/${pro.id}.html?pageKey=search" target="_blank" title="${pro.fullName}">${pro.fullName}</a>
									</p>
									${pro.showPrice}
									<div class="purchase_btn">
										<a href="/${pro.id}.html" target="_blank">立即抢购</a>
									</div>
								</div>
							  </li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="hot_recommend">
					<a name="filters"></a>
					<ul class="choose_term">
						<li>${keyword }<em>></em></li>
						<li>商品筛选</li>
						<c:if test="${result.brand != null }">
						  <li><em>> </em></li>
						  <li><a href="${result.getQueryUrl(url,'brand','') }#filters">品牌：${result.brand }<b>×</b></a></li>
						</c:if>
						<c:if test="${result.selectedFilters != null }">
						  <c:forEach items="${result.selectedFilters.keySet()}" var="filters">
							<c:set var="redis_agg" scope="page"	value="${result.selectedFilters.get(filters)}" />
							<c:set var="fl" scope="page" value="${filters }_${result.selectedFilters.get(filters) }" />
							<li><em>> </em></li>
							<li><a href="${result.getQueryUrl(url,'fl',fl) }#filters">${filters }：${redis_agg }<b>×</b></a></li>
						  </c:forEach>
						</c:if>
					</ul>
					<c:if test="${brands != null }">
						<div class="band_one btm">
							<div class="h_name">品牌：</div>
							<div class="h_values">
								<div class="scr_con">
									<div id="dv_scroll">
										<div class="Scroller-Container">
											<ul class="brand_list">
												<c:forEach items="${brands }" var="brand" varStatus="brand_status">
												  <c:set var="brand_size" value="${brand_status.count }" scope="page" />
												  <li><a href="${result.getQueryUrl(url,'brand',wode:encode(brand)) }#filters">${brand }</a></li>
												</c:forEach>
											</ul>
										</div>
									</div>
									
								</div>
							</div>
						</div>
					</c:if>
					<c:forEach items="${aggregations.keySet()}" var="agg_name" begin="0" end="3">
					  <div class="band_one">
						<div class="h_name">${agg_name }：</div>
						<div class="h_values">
							<ul class="brand_list">
								<c:forEach items="${aggregations.get(agg_name)}" var="agg_lable">
								  <c:set var="fl_agg" scope="page" value="${agg_name }_${agg_lable }" />
								  <li><a href="${result.getQueryUrl(url,'fl',wode:encode(fl_agg)) }#filters">${wode:decode(agg_lable) }</a></li>
								</c:forEach>
							</ul>
						</div>
					  </div>
					</c:forEach>
					<c:if test="${aggregations.size() >4 }">
						<div id="band_one" style="display: none;">
						  <c:forEach items="${aggregations.keySet()}" var="agg_name" begin="4">
							<div class="band_one">
								<div class="h_name">${agg_name }：</div>
								<div class="h_values">
									<ul class="brand_list">
									  <c:forEach items="${aggregations.get(agg_name)}" var="agg_lable">
										<c:set var="fl_agg" scope="page" value="${agg_name }_${agg_lable }" />
										  <li><a href="${result.getQueryUrl(url,'fl',wode:encode(fl_agg)) }#filters">${agg_lable }</a></li>
										</c:forEach>
									</ul>
								</div>
							</div>
						  </c:forEach>
						</div>
					</c:if>
					<div class="clear"></div>
					<c:if test="${aggregations.size() >4 }">
						<div class="extra_btn"></div>
					</c:if>
				</div>
				<div class="all_product">
					<div class="sort_title">
						<a name="orders"></a>
						<ul class="sort_lt">
						  <c:choose>
							<c:when test="${result.sort == null}">
							  <li class="surr"><a href="javascript:void(0);">默认</a></li>
							</c:when>
							<c:otherwise>
							  <li><a href="${result.getQueryUrl(url,'sort','') }#orders">默认</a></li>
							</c:otherwise>
						  </c:choose>
						  <c:choose>
							<c:when test="${fn:indexOf(result.sort,'discount') > -1}">
							  <li class="surr"><a href="javascript:void(0);">折扣</a></li>
							</c:when>
							<c:otherwise>
							  <li><a href="${result.getQueryUrl(url,'sort','discount_0') }#orders">折扣</a></li>
							</c:otherwise>
						  </c:choose>

						<!--<c:choose>
 							<c:when test="${fn:indexOf(result.sort, 'revNum') > -1 }">
                        	  <li class="surr"><a href="javascript:void(0);">评论数</a></li>
							</c:when>
							<c:otherwise>
                        	  <li><a href="${result.getQueryUrl(url,'sort','revNum_1') }#orders">评论数</a></li>
							</c:otherwise>
						  </c:choose> -->

						  <c:choose>
							<c:when test="${fn:indexOf(result.sort, 'price') > -1 }">
							  <c:choose>
								<c:when test="${fn:indexOf(result.sort, '_0') > -1}">
								  <li class="surr up"><a href="${result.getQueryUrl(url,'sort','price_1') }#orders">价格<i></i></a></li>
								</c:when>
								<c:otherwise>
								  <li class="surr down"><a href="${result.getQueryUrl(url,'sort','price_0') }#orders">价格<i></i></a></li>
								</c:otherwise>
							  </c:choose>
							</c:when>
							<c:otherwise>
								<li><a href="${result.getQueryUrl(url,'sort','price_1') }#orders">价格</a></li>
							</c:otherwise>
						   </c:choose>
						</ul>
						<div class="sort_rt">
							<span>${result.p }/${result.totalPage }</span>
							<a class="arrow_left" href="${(result.p-1)>=1 ? result.getQueryUrl(url,'page',(result.p-1)) : 'javascript:void(0);' }#orders"></a>
							<a class="arrow_right" href="${(result.p+1)<=result.totalPage ? result.getQueryUrl(url,'page',(result.p+1)) : 'javascript:void(0);' }#orders"></a>
						</div>
					</div>
					<div class="all_product_list">
						<ul>
						  <c:forEach items="${result.hits }" var="hit">
							<li class="at">
							  <c:choose>
								<c:when test="${hit.saleKbn == 1}">
								  <div class="picon"><img src="../images/picon1.png" /></div>
							    </c:when>
								<c:when test="${hit.saleKbn == 2}">
								  <div class="picon"><img src="../images/picon_c1.png" /></div>
							    </c:when>
							    <c:when test="${hit.saleKbn == 4}">
								  <div class="picon"><img src="../images/picon_z1.png" /></div>
							    </c:when>
								<c:when test="${hit.saleKbn == 5}">
								  <div class="picon"><img src="../images/picon_t1.png" /></div>
							    </c:when>
						  	  </c:choose>
							  <c:set var="salePrice" value="${hit.salePrice}"></c:set>
							  <c:set var="salePoint" value="${hit.discount}"></c:set>
<%-- 							  ${(salePrice*10)/hit.price} --%>
							  <c:set var="fucoin" value="${hit.maxFucoin>maxBenefit?maxBenefit:hit.maxFucoin}"></c:set>
							 	<div class="all_product_pho">
									<a href="${basePath}<fmt:formatNumber value="${hit.productId }" pattern="#0"/>.html?skuId=<fmt:formatNumber value="${hit.minSkuId }" pattern="#0"/>&pageKey=search"  target="_blank">
										<img src="${hit.image}" alt=">${hit.name }">
									</a>
								</div>
								<h2>
									<a href="${basePath}<fmt:formatNumber value="${hit.productId }" pattern="#0"/>.html?skuId=<fmt:formatNumber value="${hit.minSkuId }" pattern="#0"/>&pageKey=search"  target="_blank">${hit.name}</a>
								</h2>
								<p class="p1">
									<span>内购价：¥<fmt:formatNumber value="${hit.salePrice }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="0" /><i>+<fmt:formatNumber value="${fucoin}" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="0"/>券</i></span>
									<em><fmt:formatNumber value="${salePoint}" type="number" groupingUsed="false" maxFractionDigits="1" minFractionDigits="0"/>折</em>
								</p>
								<p class="p2">
									电商价：￥ <fmt:formatNumber value="${hit.price}" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="0" />
								</p> 
								<em><a href="/shop/<fmt:formatNumber value="${hit.shopId }" pattern="#0"/>" target="_blank">
								<c:if test="${user.supplierId ==hit.supplierId}">自家</c:if>
								<c:if test="${user.supplierId !=hit.supplierId}">${hit.shopName }</c:if>
								</a></em> 
								<%--<p>已有<b><fmt:formatNumber value="${hit.revNum }" pattern="##0"/></b>个评价</p>
                            	<div class="pro_btn">
                                <div class="p_btn_01"><a href="/product_detail.html">加入购物车</a></div>
                                <div class="p_btn_02"><a href="#">关注</a></div>
                            </div>--%>
                            </li>
						  </c:forEach>
						</ul>
					</div>
					<div class="page">
					  <c:if test="${result.p > 1 }">
						<a href="${result.getQueryUrl(url,'page',(result.p-1)) }#orders">前一页</a>
					  </c:if>
					  <c:forEach var="i" begin="${(result.p - (5 - 1)/2) < 1 ? 1 : (result.p - (5 - 1)/2) }" end="${(result.p + (5-1)/2) > result.totalPage ? result.totalPage : (result.p + (5-1)/2)}" varStatus="status">
						<c:if test="${status.first && i <= 3}">
						  <c:forEach var="j" begin="1" end="${i - 1 }">
							<a href="${result.getQueryUrl(url,'page',j) }#orders">${j }</a>
						  </c:forEach>
						 </c:if>
						<c:if test="${status.first && i > 3}">
							<a href="${result.getQueryUrl(url,'page',1) }#orders">1</a>
							<a href="${result.getQueryUrl(url,'page',2) }#orders">2</a>
							<span>...</span>
						</c:if>
						<c:if test="${i != result.p }">
							<a href="${result.getQueryUrl(url,'page',i) }#orders">${i }</a>
						</c:if>
						<c:if test="${i == result.p }">
							<a href="javascript:" class="page_curr">${i }</a>
						</c:if>
						<c:if test="${status.last && i < (result.totalPage - 1)}">
							<span>...</span>
						</c:if>
					  </c:forEach>
					  <c:if test="${result.p < result.totalPage }">
						<a href="${result.getQueryUrl(url,'page',(result.p+1)) }#orders">后一页</a>
					  </c:if>
					   <span>共${result.totalPage }页</span> 
					   <input type="text" id="page"	class="input_text"> <span>页</span> 
					   <input type="button" id="page_button" value="确定" class="input_btn">
					</div>
				</div>
			</div>
			<!--right end-->
		</div>

	</div>
	<!--content end-->


<script type="text/javascript">
    $("#page_button").click(function(){
        var page = $.trim($("#page").val()), total = ${result.totalPage};
        if(!isNaN(page) && page > 0 && page <= total) {
            window.location.href=$.query.set("page", page);
        }
    })
 </script>

<!--footer begin-->
<%@ include file="/common/footer.jsp"%>
<!--footer end-->
<script type="text/javascript" src="${basePath }resources/js/top_ewm.js"></script>
</body>
</html>