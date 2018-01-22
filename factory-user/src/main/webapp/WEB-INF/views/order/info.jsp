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
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=lGxKvc6MXL3bGoIAYnG9odbk"></script>
<script src="http://libs.baidu.com/jquery/1.9.0/jquery.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/areaFnc.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/order.js?0111"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/order_address.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/express.js"></script>
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
	               		<input class="selradio" type="radio" name="addressRadio" value="1" <c:if test="${vsStatus.first}">checked="checked"</c:if>>自提（自己联系商家取货）
	                </p>
                    <div class="save_btn"><a id="saveAddress" href="javascript:void(0);">保存收货人信息</a></div>
                </div>
                <!--修改收货地址 end-->
                <div class="one_infomation" style="display: none">
                	<h2>支付方式</h2>
                    <p><input type="radio" name="" value="" checked="checked"> 在线支付</p>
                </div>                
                <div class="one_infomation" id="invoiceInfomation">
                	<h2>发票信息<span><a id="showInvoice" href="javascript:void(0);">修改</a></span></h2>
                	<c:if test="${empty invoiceList}">
                		<p id="invoiceInfo" style="display: none;">
                		</p>
                	</c:if>
                	<c:if test="${!empty invoiceList}">
                		<c:set var="defaultInvoice" value="${invoiceList[0] }"></c:set>
                		<p id="invoiceInfo">
                	<input type="hidden" value="${defaultInvoice.id}" name="inId" id="inId">
                			<span>发票抬头：
                				${defaultInvoice.title }
                			</span>
                			<span>发票类型：
                				<c:if test="${defaultInvoice.type == 1 }">
	                        		个人
	                        	</c:if>
	                        	<c:if test="${defaultInvoice.type == 2 }">
	                        		单位
	                        	</c:if>
                			</span>
                		</p>
                	</c:if>
                	<input type="hidden" name="inId" id="inId">
                    <input type="hidden" name="isInvoice" value="1">
                    <input type="hidden" name="invoiceTitle" value="">
                    <input type="hidden" id="invoiceId" name="invoiceId"  value="0"/>
                </div>
                <!--编辑发票信息 begin-->
                <div class="edit_bill">
                	<h2>发票信息</h2>                    
                    <div class="bill">
                    	<h3>类型和抬头</h3>
                    	<c:if test="${!empty invoiceList}">
                    		<c:forEach items="${invoiceList }" var="invoice" varStatus="vStatus">
                    			<p>
                    			<input type="hidden" name="i_invoiceId" value="${invoice.id}">
                    			<input type="hidden" name="i_title" value="${invoice.title}">
                    			<input type="hidden" name="i_type" value="${invoice.type}">
                    			<input type="hidden" name="billType" value="${invoice.billType}">
                    			<input type="hidden" name="taxpayerNumber" value="${invoice.taxpayerNumber}">
                    			<input type="hidden" name="registerAddress" value="${invoice.registerAddress}">
                    			<input type="hidden" name="registerPhone" value="${invoice.registerPhone}">
                    			<input type="hidden" name="openingBan" value="${invoice.openingBan}">
                    			<input type="hidden" name="openingBanNumber" value="${invoice.openingBanNumber}">
                    			
	                        	<input class="selradio" type="radio" name="invoiceRadio" value="${invoice.id}" <c:if test="${vStatus.first}">checked="checked"</c:if>>
	                        	<c:if test="${invoice.type == 1 }">
	                        		个人
	                        	</c:if>
	                        	<c:if test="${invoice.type == 2 }">
	                        		单位
	                        	</c:if>
	                        	<b>${invoice.title }</b>
	                        	<span>
	                        		<a href="javascript:void(0);" name="editInvoice">编辑</a>
	                        	</span>
	                        	<strong>
	                        		<a href="javascript:void(0);" name="delInvoice">删除</a>
	                        	</strong>
	                        </p>
                    		</c:forEach>
                        </c:if>
                        <div class="newbill">
                            <p><input class="selradio" type="radio" name="invoiceRadio" id="useNewInvoice" value="0">新发票</p>
                        </div>
                        <div class="edit_bill_wrap">
                            <div class="bill_info">
                                <span>发票抬头：</span>
                                <input class="bill_input" type="text"  id="invoice_title" maxlength="50">
                                <div class="hinterror" style="color: red;"></div>
                                <br><br>
                               <span> 发票类型：</span>
                               <span><input class="selradio" type="radio" name="invoice_type" value="1">个人</span>
                               <span><input class="selradio" type="radio" name="invoice_type" value="2">单位</span>
                               <br><br>
                            </div>
	                         <div id="oDiv" style="display:none">
					     		<div class="bill_info">
					     			<span>发票类型：</span>
				     				<span><input name="billType" id="billType" value="0" type="radio" checked="checked"/>普通发票</span>
				     				<span><input name="billType" value="1" type="radio" />专用发票</span>
					     			<br><br>
					     			<span>纳税人识别号：</span>
					     			<input type="text" id="taxpayerNumber" class="bill_input" maxlength="50"/>
					     			<div class="hinterror5" style="color: red;"></div>
					     		</div>
					     	</div>
					     	<div id="oDiv1" style="display:none">
					     		<div class="bill_info">
					     			<br><br>
					     			<span>注册地址：</span>
					     			<input type="text" id="registerAddress" class="bill_input" maxlength="100"/>
					     			<div class="hinterror1" style="color: red;"></div>
					     			<br><br>
					     			<span>注册电话：</span>
					     			<input type="text" id="registerPhone" class="bill_input" maxlength="50"/>
					     			<div class="hinterror2" style="color: red;"></div>
					     			<br><br>
					     			<span>开户行：</span>
					     			<input type="text" id="openingBan" class="bill_input" maxlength="100"/>
					     			<div class="hinterror3" style="color: red;"></div>
					     			<br><br>
					     			<span>开户行账号：</span>
					     			<input type="text" id="openingBanNumber" class="bill_input" maxlength="50"/>
					     			<div class="hinterror4" style="color: red;"></div>
					     		</div>
					     	</div>
                        </div>
                    </div>
                    <div class="comment">备注：如商品由第三方卖家销售，发票内容由其卖家决定，发票由卖家开具并寄出</div>
                    <div class="save_btn"><a id="saveInvoice" href="javascript:void(0);">保存发票信息</a></div>
                </div>
                <!--编辑发票信息 begin-->
            </div>
            <div class="shopping_clearing">
            	<div class="shopping_clearing_title">
                	<h2>商品清单<span><a href="/cart/list">回购物车修改商品</a></span></h2>
                </div>
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
								<c:set value="${totalPrice + (cartItem.realPrice)* quantity }" var="totalPrice" />
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
				                       		<a href="/${cartItem.productId }.html"><img src="${cartItem.imagePath }" width="78" height="78"></a>
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
								<label  id="labelShipping${supplierId}" style="float:right;">运费 ￥ ${supplierShipping }</label>
							</div>
		                </div>
                	</c:if>
				</c:forEach>
                <!--one end-->
            </div>
            
            <!-- <div class="shopping_alert_info">请先保存配送信息，再提交订单</div> -->
            <div class="accounting"> 
                <div class="order-list-new">
                    <div class="order-list-left">
                    <c:if test="${ticket>0 }">
                        <div class="order-benifit">
                            <span>需使用<span id="useTicket" style="display: none">
                            <fmt:formatNumber value="${ticket }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/> 
                            </span>内购券<span class="red">￥<span id="getTicketExchange" class="red"><fmt:formatNumber value="${ticketExchange }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></span></span>（账户余额：${totalTicket}）</span>
                        </div>
                    </c:if>
                    
                    <c:if test="${exchangeCanUse>0 }">
                    <div class="order-benifit">
                    	<input type="hidden" id="exchangeCanUse" value="<fmt:formatNumber value="${exchangeCanUse }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>">
                        <input class="check-new" type="checkbox" id="exchangeChecked" checked="checked" disabled="disabled">
                        <span>换领币抵扣<span class="red">￥<span id="getExchangeUse" class="red"><fmt:formatNumber value="${exchangeCash }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></span>元</span>
                        (使用换领币：<fmt:formatNumber value="${exchangeCanUse }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>元)</span>
                        <c:if test="${exchangeCanUse>exchangeCash }">
                          <div class="annotation">
                          		<img  src="../images/wenhao.png" />
                          		<div class="anno_con">
                          			<span>换领优惠提前享</span>
                          			<p>换领币未全部激活，不能完全抵扣。换领币激活后优先以现金券形式返还<em>${exchangeCanUse-exchangeCash }</em>元</p>
                          			<i><img src="../images/wenhaosj.png" /></i>
                          		</div>
                          	</div>
                        </c:if>
                    </div>
                    </c:if>
                    
                    <div class="order-benifit">
                    	<input type="hidden" id="cashBalance" value="<fmt:formatNumber value="${cashExchange }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>">
                        <input class="check-new" type="checkbox" id="cashChecked" <c:if test="${cash<=0 }">disabled</c:if>>
                        <span>使用现金券抵<span class="red">￥<span id="getCashExchange" class="red">0.00</span>元</span>(账户余额：<fmt:formatNumber value="${cashExchange }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>元)</span>
                    </div>
                    
                    </div>
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
                            <li <c:if test="${exchangeCanUse<=0 }">style="display:none;"</c:if> id="liExchangeUse">
                                <span class="ln-01">换领币抵扣：</span>
                                <input type="hidden" id="hidExchangeUse" value="${exchangeCash }">
                                <span class="ln-02">-￥<a id="exchangeUse"><fmt:formatNumber value="${exchangeCash }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></a></span>
                            </li>
                            <li>
                                <span class="ln-01">现金券抵扣：</span>
                                <input type="hidden" id="hidCashExchange" value="0.00">
                                <span class="ln-02">-￥<a id="cashExchange">0.00</a></span>
                            </li>
                            <li>
                                <span class="ln-01">运费：</span>
                                <span class="ln-02" id="spantotalFreight">￥<fmt:formatNumber value="${totalFreight }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></span>
                            	<input type="hidden" value="${totalFreight }" id="totalFreight" name="totalFreight"> 
                            </li>
                        </ul>
                    </div>
                    <div class="price_sum-new">
                        <span class="price_acount">总计价格</span>
                        <!-- <span class="txt">(不含运费)</span> -->
                        <strong class="price">￥<span id="total">
                        <c:choose>
                        <c:when test="${(empty shippingAddressList) && totalFreight>=8888}">
                        <fmt:formatNumber value="${totalPrice-pointExchange+totalFreight-8888 }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>
                        </c:when>
                        <c:otherwise>
                        <fmt:formatNumber value="${totalPrice-pointExchange+totalFreight }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/>
                        </c:otherwise>
                        </c:choose>
                        </span></strong>
                         <br>
                             <span class="ln-02" id="totalCompany">+￥<a id="ticketExchange"><fmt:formatNumber value="${ticketExchange }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></a>内购券</span>
                    </div>
                    <div class="sumbit-info">
                        <!-- <div class="submit-left">
                            <p>李飞糕    13661060147</p>
                            <p>北京 北京市 海淀区 上地科技大厦4号楼106A</p>
                        </div> -->
                        <div class="btn_area-new c-mar"><a id="createOrder" href="javascript:go2CreateOrder();">提交订单</a></div>
                    </div>
                </div> 
            </div>
            <%-- <div class="accounting">            	
                <div class="btn_area c-mar"><a id="createOrder" href="javascript:void(0);">去结算</a></div>
                <div class="price_sum">
                    <span class="price_acount">总计价格</span>
                    <span class="txt">(不含运费)</span>
                    <strong class="price">￥<fmt:formatNumber value="${totalPrice }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></strong>
                </div>
            </div> --%>
            
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