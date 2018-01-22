<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<title>下单_我的福利</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/public.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/shoppingcart.css">
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/areaFnc.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/order_address.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/exchange_order.js"></script>
</head>
<script type="text/javascript">
$(function(){
	$(".alter_address .h-receiveaddress").hover(function(){
		$(this).find("strong").css("visibility","visible");		
	},function(){
		$(this).find("strong").css("visibility","");		
	});
	$(".alter_address .h-receiveaddress").click(function(){
		$(this).addClass("cursor").siblings().removeClass("cursor");
	});
	$(".newaddress").click(function(){
		$(".alter_address .h-receiveaddress").removeClass("cursor");
	})
})
</script>
<body>
<!--top begin-->
<%@ include file="../common/header_02.jsp" %>
<!--top end-->

<!--content begin-->
<div id="middle">
	<div class="middle_content">
		<input type="hidden" value="${btnName}" id="match">
		<form id="orderForm" action="javaScript:void(0);" method="post" autocomplete="off">
    	<div class="cart_hd">
        	<h2>填写订单信息</h2>
        </div>
        <div class="cart_info">
        	<div class="order_info">
            	<div class="one_infomation" id="addressInfomation" style="<c:if test="${empty shippingAddressList}">display: none</c:if>">
                	<h2>选择收货地址<span><a id="showAddress" href="javascript:void(0);">修改</a></span></h2>
	                <input type="hidden" name="selfDelivery" id="selfDelivery" value="0">
                	<c:if test="${empty shippingAddressList}">
                		<p id="addressP" style="display: none"><input class="selradio" type="radio" name="commonAddress" value=""><span id="addressSpan"></span></p>
	                    <input type="hidden" id="shippingAddressId" value="">
	                    <input type="hidden" name="aid" value="">
	                    <input type="hidden" name="name" value="">
	                    <input type="hidden" name="address" value="">
	                    <input type="hidden" name="mobile" value="">
	                    <input type="hidden" name="phone" value="">
	                    <input type="hidden" name="provinceName" value="">
	                    <input type="hidden" name="cityName" value="">
	                    <input type="hidden" name="areaName" value="">
                	</c:if> 
                	<c:if test="${!empty shippingAddressList}">
                		<c:set var="defaultAddress" value="${shippingAddressList[0] }"></c:set>
                		<p id="addressP"><input class="selradio" type="radio" name="commonAddress" value="" checked="checked"><span id="addressSpan">${defaultAddress.provinceName }  ${defaultAddress.cityName }  ${defaultAddress.areaName }  ${defaultAddress.address }  （${defaultAddress.name }收）  ${defaultAddress.phone }</span></p>
	                    <input type="hidden" id="shippingAddressId" value="${defaultAddress.id}">
	                    <input type="hidden" id="aid" value="${defaultAddress.aid}">
	                    <input type="hidden" name="name" value="${defaultAddress.name }">
	                    <input type="hidden" name="address" value="${defaultAddress.provinceName } ${defaultAddress.cityName } ${defaultAddress.areaName } ${defaultAddress.address }">
	                    <input type="hidden" name="mobile" value="${defaultAddress.phone }">
	                    <input type="hidden" name="phone" value="">
	                    <input type="hidden" name="provinceName" value="${defaultAddress.provinceName }">
	                    <input type="hidden" name="cityName" value="${defaultAddress.cityName }">
	                    <input type="hidden" name="areaName" value="${defaultAddress.areaName }">
                	</c:if>
                	
                	<!-- 代收驿站 begin -->
                    <div class="stationTab" id="collecting_address_div" style="display: none;">
                    </div>
                    <!-- 代收驿站 end -->
                </div>
                <!--修改收货地址 begin-->
                <div class="alter_address" <c:if test="${empty shippingAddressList}">style="display: block"</c:if>>
                	<h2>选择收货地址</h2>
                	<c:if test="${!empty shippingAddressList}">
	                	<c:forEach items="${shippingAddressList}" var="shippingAddress" varStatus="vsStatus">  
	                		 <p class="h-receiveaddress">
	                		 	<input type="hidden" name="s_shippingAddressId" value="${shippingAddress.id }">
	                		 	<input type="hidden" name="s_aid" value="${shippingAddress.aid }">
	                		 	<input type="hidden" name="s_name" value="${shippingAddress.name }">
	                		 	<input type="hidden" name="s_phone" value="${shippingAddress.phone }">
	                		 	<input type="hidden" name="s_address" value="${shippingAddress.address }">
	                		 	<input type="hidden" name="s_provinceName" value="${shippingAddress.provinceName }">
	                		 	<input type="hidden" name="s_cityName" value="${shippingAddress.cityName }">
	                		 	<input type="hidden" name="s_areaName" value="${shippingAddress.areaName }">
	                		 	<input class="selradio" type="radio" name="addressRadio" value="${shippingAddress.id }" <c:if test="${vsStatus.first}">checked="checked"</c:if>>
	                		 	${shippingAddress.provinceName }  ${shippingAddress.cityName }  ${shippingAddress.areaName }  ${shippingAddress.address }  （${shippingAddress.name }收）  ${shippingAddress.phone }
	                		 	<%-- <c:if test="${vsStatus.first}">
	                		 		<span>默认地址</span>
	                		 	</c:if> --%>
	                		 	<strong class="s_ch"><a href="javascript:void(0);" name="editAddress">修改</a></strong>
	                		 	<strong class="s_de"><a href="javascript:void(0);" name="delAddress">删除</a></strong>
	                		 </p>
	                	</c:forEach>
                	</c:if>
                    <div class="newaddress">
                    	<p><input class="selradio" type="radio" name="addressRadio" value="0" id="useNewAddress" <c:if test="${empty shippingAddressList}">checked="checked"</c:if>><a  href="javascript:void(0);">使用新收货地址</a></p>
                    </div>
                    <div class="address_info_wrap" <c:if test="${empty shippingAddressList}">style="display: block;"</c:if>>
                    	<div class="address_info">
                        	<span><b>*</b>收 货 人  ：</span>
                            <input class="txt_input" type="text" id="t_name" value="" maxlength="10">
                            <div class="hinterror" style="color: red;float:right;margin-right:300px;"></div>
                        </div>
                        <div class="address_info">
                        	<span><b>*</b>手机号码：</span>
                            <input class="txt_input" type="text" id="t_mobile" value="" maxlength="11">
                            <div class="hinterror" style="color: red;float:right;margin-right:300px;"></div>
                        </div>
                        <div class="address_info">
                        	<span><b>*</b>所在地址：</span>
                            <div class="DivSelect">
                                <select id = "province">                                	
                                </select>
                            </div>
                            <div class="DivSelect">
                                <select id = "city">
                                </select>
                            </div>
                            <div class="DivSelect">
                                <select id = "area">
                                </select>
                            </div>
                            <div class="hinterror" style="color: red;float:right;margin-right:300px;"></div>
                        </div>
                        <div class="address_info">
                        	<span><b>*</b>详细地址：</span>
                            <input class="f_input" type="text" id="t_address" value="" maxlength="48">
                            <div class="hinterror" style="color: red;float:right;margin-right:300px;"></div>
                        </div>
                    </div>
                    <p class="h-receiveaddress">
	               		<input class="selradio" type="radio" name="addressRadio" value="1" <c:if test="${vsStatus.first}">checked="checked"</c:if>>公司统一收货（免运费）
	                </p>
                    <div class="save_btn"><a id="saveAddress" href="javascript:void(0);">保存收货人信息</a></div>
                </div>
                <!--修改收货地址 end-->
                <div class="one_infomation">
                	<h2>支付方式</h2>
                    <p><input type="radio" name="" value="" checked="checked"> 在线支付</p>
                </div> 
            </div>
            <div class="shopping_clearing">
                <div class="shopping_clearing_thead">
                	<div class="com s_goods">商品</div>
                    <div class="com s_price">价格</div>
                    <div class="com s_quantity">数量</div>
                    <div class="com s_cmount">金额小计</div>
                    <!-- <div class="com s_binifit-new">内购券</div> -->
                </div>
                <!--one begin-->
                <c:set value="0" var="totalPrice" />
                	<c:forEach items="${cart}" var="map">  
						<c:set value="${map.key}" var="supplierId" />
						<c:set value="" var="supplierName" />
					    <c:set value="${map.value}" var="cartItems" />
					    <c:forEach items="${cartItems}" var="cartItem" varStatus="vs"> 
					       	<c:if test="${vs.first}">
					       		<c:set value="${cartItem.supplierName}" var="supplierName" />
					       		<c:set value="${cartItem.shopId}" var="shopId" />
					       	</c:if>
					    </c:forEach>
					    
					    <c:set value="false" var="buyFlag" />
					     <c:forEach items="${cartItems}" var="cartItem" varStatus="vs"> 
					       	<c:if test="${cartItem.buyFlag && buyFlag == false}">
					       		<c:set value="true" var="buyFlag" />
					       	</c:if>
					    </c:forEach>
					  <c:if test="${buyFlag }">  
						<div class="shopping_list">
		                	<div class="shopping_list_tl">
		                    	<h2>商家：<a href="${basePath}/shop/${shopId}">
		                    	<c:if test="${supplierId!=user.supplierId }">${supplierName }</c:if>
		                    	<c:if test="${supplierId ==user.supplierId }">自家</c:if>
		                    	</a>
		                    	<label style="float:right;color:red;">
		                    	<a id="supplierFreeString${supplierId}" href="${basePath}/shop/${shopId}" style="color:red;">
		                    	<c:if test="${not empty freeMap }">
			                            <c:forEach var="mapa" items="${freeMap}">
			                               <c:if test="${supplierId == mapa.key}">
			                                ${mapa.value}
			                               </c:if>
			                            </c:forEach>
			                        </c:if>
			                        </a>
			                        </label>
		                    	</h2>
		                    </div>
		                     <input type="hidden" id="freightAdd" value="${supplierId}">
		                    <c:set value="0.0" var="supplierShipping" />
		                    <c:forEach items="${cartItems}" var="cartItem">  
		                    <c:set var="supplierShipping" value="${supplierShipping+cartItem.freight}" />
		                    	<c:if test="${cartItem.buyFlag}">
								<c:set value="${cartItem.quantity}" var="quantity" />
								<input type="hidden" id="quantity" value="${cartItem.quantity}">
								<input type="hidden" id="partNumber" value="${cartItem.partNumber}">
								<input type="hidden" id="freight" value="0">
								<input type="hidden" id="realAmount" value="${cartItem.realAmount}">
								<input type="hidden" name="skuExchangeTicket" value="${cartItem.benefitTicket}">
								<input type="hidden" name="skuExchangeCash" value="${cartItem.benefitAmount}">
								<input type="hidden" name="skuExchangeSelf" value="${cartItem.benefitSelf}">
								<c:set value="${totalPrice + cartItem.realAmount }" var="totalPrice" />
								<div class="shopping_list_cont">
				                    <div class="pub c_goods">
				                       	<div class="shopping_product">
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
				                       		<a href="/${cartItem.productId }.html?pageKey=huanling"><img src="${cartItem.imagePath }" width="78" height="78"></a>
				                       	</div>
				                        <p>${cartItem.productName }</p>
				                         <c:forEach items="${cartItem.specificationList}" var="specification">  
				                             	<strong>
				                             	${fn:substring(specification,0,fn:indexOf(specification, ":"))}：${fn:substring(specification,fn:indexOf(specification, ":")+1,fn:length(specification))}
				                             	</strong>
				                             </c:forEach>
				                        </div>
				                        <div class="pub c_price">￥<fmt:formatNumber value="${cartItem.realPrice }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>
				                        		+<fmt:formatNumber value="${cartItem.maxCompanyTicket }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>券
				                        
				                        </div>
				                        <div class="pub c_quantity">${quantity }</div>
				                        <div class="pub c_cmount">￥<fmt:formatNumber value="${cartItem.realPrice * quantity }"  type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>
				                        </div>
				                    	<%-- <div class="pub c_binifit-new"><input class="check-new" type="checkbox" name="" value=""><span class="red">${cartItem.maxCompanyTicket }</span>内购券</div> --%>
				                    </div>
			                    
			                    </c:if>
							</c:forEach>
		                    <div class="new-message">
								<span>给卖家留言</span><input class="textarea-new" type="text" id="note" name="note" placeholder="选填：对本次交易的说明（建议填写已经和卖家达成一致的说明）字数不能超过100" data="${supplierId}" maxlength="100"/>
							</div>
		                </div>
                	</c:if>
				</c:forEach>
                <!--one end-->
            </div>
            <!-- 调剂商品 -->
            <c:if test="${!empty searchs}">
			<div class="tiaoji" style="display: block">
				<span>调剂商品</span>
				<p>下单后将依据调剂商品为您尽快达成换领；您可进入调剂清单编辑。</p>
				<ul>
					<c:forEach items="${searchs}" var="searchReuslt" end="2"> 
					<li><img src="${searchReuslt.image }" /></li>
					</c:forEach>
					<c:if test="${fn:length(searchs)>3 }">
					<li class="last_li"><img src="<%=basePath %>images/sangedian.png" /></li>
					</c:if>
				</ul>
			</div>
			</c:if>
            <!-- <div class="shopping_alert_info">请先保存配送信息，再提交订单</div> -->
            <div class="accounting"> 
                <div class="order-list-new">
                    <div class="order-list-right">
                        <ul class="order-list-info">
                            <li>
                                <span class="ln-01">商品数量：</span>
                                <span class="ln-02">${totalNum }</span>
                            </li>
                            <li>
                                <span class="ln-01">金额合计：</span>
                                <span class="ln-02">￥<fmt:formatNumber value="${totalPrice }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></span>
                                <input type="hidden" id="totalPrice" value="${totalPrice }">
                            </li>
                            <li>
                                <span class="ln-01">运费：</span>
                                <span class="ln-02" id="spantotalFreight">￥<fmt:formatNumber value="0" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></span>
                            	<input type="hidden" value="0" id="totalFreight" name="totalFreight"> 
                            </li>
                            <li <c:if test="${exchangeCanUse<=0 }">style="display:none;"</c:if> id="liExchangeUse">
                                <span class="ln-01">换领币抵扣：</span>
                                <input type="hidden" id="hidExchangeUse" value="${exchangeCanUse }">
                                <span class="ln-02">-￥<a id="exchangeUse"><fmt:formatNumber value="${exchangeCanUse }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></a></span>
                            </li>
                        </ul>
                    </div>
                    <div class="price_sum-new">
                        <span class="price_acount">总计价格</span>
                        <!-- <span class="txt">(不含运费)</span> -->
                        <strong class="price">￥<span id="total">
                            <fmt:formatNumber value="${totalPrice-exchangeCanUse}" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>
                        </span></strong>
                    </div>
                    <div class="price_sum-new">
                    	<input type="hidden" id="freeSwap" value="1" checked="checked"><!-- <label for="freeSwap">当换领活动结束时，若匹配不成功，同意调剂换领商品。</label> -->
                    </div>
                    <div class="sumbit-info">
                        <div class="btn_area-new c-mar"><a id="createOrder" href="javascript:go2CreateOrder();">提交订单</a></div>
                    </div>
                </div> 
            </div>            
        </div>
        </form>
    </div>
</div>
<input type="hidden" id="infoError" value="${errormsg }" />
<!--content end-->
<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
<!--footer begin-->
<%@ include file="../common/footer.jsp" %>
<!--footer end-->
<script>
	$(function(){
		$(".annotation img").mouseover(function(){			
			$('.anno_con').fadeIn();
		});
		$(".annotation img").mouseout(function(){			
			$('.anno_con').fadeOut();
		})
		
	})
</script>
</body>
</html>