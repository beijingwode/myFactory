<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>购物车_我的.com</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/public.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/shoppingcart.css">
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
   
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/cart.js?1234"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/cart_empty.js"></script>
</head>
<body>
<!--top begin-->
<%@ include file="common/header_01.jsp" %> 
<!--top end-->
<c:if test="${!empty checkRet }">
	<div style="width: 100%;text-align: center;">${checkRet}</div>
</c:if>

<!--content begin-->
<div id="middle">
	<div class="middle_content">
    	<div class="cart_hd">
        	<h2>我的购物车</h2>
        </div>
        <c:if test="${!empty cart }">
        <div class="cart_info">
        	<div class="cart_thead">
            	<div class="column t_checkbox"></div>
                <div class="column t_goods">商品</div>
                <div class="column t_price">单价</div>
                <div class="column t_quantity">数量</div>
                <div class="column t_amount">金额小计</div>
                <div class="column t_inventory">库存</div>
                <div class="column t_action">操作</div>
            </div>
            <div class="product_list_wrap">
            	<!--one begin-->
            	<c:set value="0" var="totalPrice" />
                <c:forEach var="map" items="${cart}">
					       <c:set value="${map.key}" var="supplierId" />
					       <c:set value="" var="supplierName" />
					       <c:set value="${map.value}" var="cartItems" /> 
					       <c:forEach items="${map.value}" var="cartItem" varStatus="vs"> 
					       			<c:if test="${vs.first}">
					       				<c:set value="${cartItem.supplierName}" var="supplierName" />
					       			    <c:set value="${cartItem.shopId}" var="shopId" />
					       			</c:if>
					       </c:forEach>
							<div class="product_list">
			                	<div class="product_itemhead">
			                    	<input type="checkbox" class="supplierName" checked="checked">
			                        <label>商家：<a href="${basePath}/shop/${shopId}"><c:if test="${user.supplierId != supplierId}">${supplierName }</c:if><c:if test="${user.supplierId == supplierId}">自家</c:if></a></label>
			                        
			                        <label style="float: right;color: red;">
			                        <c:if test="${not empty freeMap }">
			                            <c:forEach var="mapa" items="${freeMap}">
			                               <c:if test="${map.key == mapa.key}">
			                               <a href="${basePath}/shop/${shopId}" style="color:red;">${mapa.value}</a>
			                               </c:if>
			                            </c:forEach>
			                        </c:if>
			                        </label>
			                    </div>
			                    <c:forEach items="${cartItems}" var="cartItem">  
						       		<%-- <c:set value="${cartItem.product}" var="product" /> --%>
						       		<%--<c:set value="${cartItem.quantity}" var="quantity" />--%>
						       		<c:set value="${totalPrice + cartItem.price * cartItem.quantity }" var="totalPrice" />
						       		<div class="order_content">
				                    	<div class="comn d_checkbox">
				                    		<c:if test="${cartItem.stock>0}">
					                        <input type="checkbox" class="partNumber" price="${cartItem.price*cartItem.quantity}" name="selectPartNumber" value="${cartItem.supplierId }_${cartItem.partNumber }" checked="checked">
					                        </c:if>
					                        <c:if test="${cartItem.stock<1}">
					                        <input type="checkbox" class="partNumber" price="0" name="selectPartNumber" value="${cartItem.supplierId }_${cartItem.partNumber }" disabled="disabled">
					                        </c:if>
				                    	</div>
				                        <div class="comn d_goods">
				                        	<div class="cart_product">
			                        			<c:if test="${cartItem.saleKbn==1 }">
				                        		<div class="picon"><img src="../images/picon2.png" /></div>
				                        		</c:if>
			                        			<c:if test="${cartItem.saleKbn==2 }">
				                        		<div class="picon"><img src="../images/picon_c2.png" /></div>
				                        		</c:if>
				                        		<c:if test="${cartItem.saleKbn==4 }">
				                        		<div class="picon"><img src="../images/picon_z2.png" /></div>
				                        		</c:if>
			                        			<c:if test="${cartItem.saleKbn==5 }">
				                        		<div class="picon"><img src="../images/picon_t2.png" /></div>
				                        		</c:if>
				                        		<a href="/${cartItem.productId }.html"><img src="${cartItem.imagePath }" width="78" height="78"></a>
				                        	</div>
				                            <p class="s-name"><a href="/${cartItem.productId }.html" title="${cartItem.productName }">${cartItem.productName }</a></p>
				                        	<c:forEach items="${cartItem.specificationList}" var="specification">
				                        		<p  title="${specification}" style="height:20px;overflow:hidden;">${specification}</p>			                        		
				                        	</c:forEach>
				                        	 <c:if test="${not empty ladderMap }">
			                            <c:forEach var="ladderMapa" items="${ladderMap}">
			                               <c:if test="${ladderMapa.key == cartItem.partNumber && not empty ladderMapa.value}">
			                              <p class="qc"  style="height:22px;margin-top:3px;" title="${ladderMapa.value}"><em>企采</em>${ladderMapa.value}</p>
			                               </c:if>
			                            </c:forEach>
			                        </c:if>
				                        	
				                        </div>
				                        <div class="comn d_price">
				                        ¥<fmt:formatNumber value="${cartItem.realPrice }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>
				                        	<c:if test="${cartItem.maxCompanyTicket>0 &&  (empty cartItem.isLadder ||  cartItem.isLadder  ==false)}">
				                        		+<fmt:formatNumber value="${cartItem.maxCompanyTicket }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>券
				                        	</c:if>
				                        	<c:if test="${ not empty cartItem.isLadder &&  cartItem.isLadder  ==true}">
				                        		+0.01券
				                        	</c:if>
				                        </div>
				                        <div class="comn d_quantity">
				                        	<input type="hidden" name="price" value="${cartItem.price }">
				                        	<input type="hidden" name="realPrice" value="${cartItem.realPrice }">
				                        	<input type="hidden" name="partNumber" value="${cartItem.partNumber }">
				                        	<a <c:if test="${cartItem.stock>0}"> name ="decrease" </c:if> href="javascript:void(0);">-</a>
				                            <input class="text_amount" onpaste="return false;" type="text" <c:if test="${cartItem.stock>0}">name="quantity" </c:if> value="${cartItem.quantity }">
				                            <a <c:if test="${cartItem.stock>0}"> name="increase" </c:if>  href="javascript:void(0);" >+</a>
				                            <c:if test="${cartItem.stock>0}">
					                        <div class="s-hint" style="display: none;"></div>
					                        </c:if>
				                        </div>                       
				                        <div class="comn d_amount" value="${cartItem.realPrice * cartItem.quantity }">¥<fmt:formatNumber value="${cartItem.realPrice * cartItem.quantity }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/> </div>
				                        <c:if test="${cartItem.stock>0}">
				                        <div class="comn d_inventory">有货</div>
				                        </c:if>
				                        <c:if test="${cartItem.stock<1}">
				                        <div class="comn d_inventory" style="color: red;">无货</div>
				                        </c:if>
				                        <div class="comn d_action">
				                        	<p><a class="collectProduct" id="${cartItem.productId }" href="javascript:void(0);">移入关注</a></p>
				                            <strong><a name="delete" href="javascript:void(0);" onclick="delCartItem(this);">删除</a></strong>
				                        </div>
				                    </div>
					       		</c:forEach>
			                </div>
                </c:forEach>
                <!--one end-->
            </div>     
                   
            <div class="accounting">            	
                <div class="btn_area"><a id="toSubmitOrder" href="javascript:toSubmitOrder();">去结算</a></div>
                <div class="price_sum">
                    <span class="price_acount">总计价格</span>
                    <span class="txt">(不含内购券及运费)</span>
                    <strong class="price"> ¥ <fmt:formatNumber value="${totalPrice }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></strong>
                    <input type="hidden" id="totalPrice" value = "${totalPrice }" />
                </div>
            </div>
        </div>
        </c:if>
        <c:if test="${empty cart }">
				<div id="middle">
					<div class="middle_content">
						<div class="cart-empty">
							<div class="message">
								<ul>
									<li>购物车内暂时没有商品&nbsp;&nbsp;<a href="/index.html"
										class="ftx-05">去购物&gt;</a></li>
								</ul>
							</div>
						</div>
						<div class="my-attention">
							<h2>热销商品</h2>
							<div class="cart_show">
								<div class="cart_btn">
									<span class="prev">上一页</span> <span class="next">下一页</span>
								</div>
								<div class="cart_content">
									<div class="cart_content_list">
										<ul>
											<c:forEach items="${productList }" var="product">
												<li><a href="/${product.id}.html" target="_blank"><img
														src="${product.image }" width="170" height="170"></a> <span>${product.showPrice}</span>
													<h4>
														<a href="/${product.id}.html">${product.fullName}</a>
													</h4></li>
											</c:forEach>
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</c:if>
    </div>
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="common/footer.jsp" %> 
<!--footer end-->
<!--登录弹出框 begin-->
<%@ include file="common/loginpopup.jsp" %>
<!--登录弹出框 end-->
<!--提示弹出框 begin-->
<%@ include file="common/box.jsp" %>
<!--提示弹出框 end-->
 <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>