<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="../common/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <title>我的福利-我的订单</title>
    <link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css"/>
    <script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
    
    <script type="text/javascript" src="<%=basePath %>resources/js/member.js"></script>
    <script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
    <script type="text/javascript" src="<%=basePath %>resources/js/myorders.js"></script>
</head>
<body>

<!--top begin-->
<%@ include file="../common/header_03.jsp" %>
<!--top end--><!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
    <!--left nav-->
    <%@ include file="menu.jsp" %>
    <!--left nav end--><!--right content-->
    <div class="Me_content">
        <div class="Me-order-title">
            <span class="O-qx"></span> 
            <span class="O-sp">商品</span> 
            <span class="O-dj">单价</span> 
            <span class="O-sl">数量</span>
            <span class="O-sl">使用内购券</span>
            <span class="O-dd">订单金额</span> 
            <span class="O-sh">收货人 </span>
            <span class="O-xl">
	            <select id="orderStatus">
                    <option value="">交易状态</option>
                    <option value="0" <c:if test="${status == 0 }">selected="selected"</c:if>>待付款</option>
                    <option value="1" <c:if test="${status == 1 }">selected="selected"</c:if>>待发货</option>
                    <option value="2" <c:if test="${status == 2 }">selected="selected"</c:if>>待收货</option>
                    <option value="4" <c:if test="${status == 4 }">selected="selected"</c:if>>交易成功</option>
                    <option value="-1" <c:if test="${status == -1 }">selected="selected"</c:if>>交易关闭</option>
                    <option value="3" <c:if test="${status == 3 }">selected="selected"</c:if>>退款中</option>
                    <option value="11" <c:if test="${status == 11 }">selected="selected"</c:if>>已退款</option>
                </select>
            </span> <span class="O-cz">操作</span>
        </div>
        <c:if test="${!empty page.list }">
            <ul class="Me-order-con">
                <c:forEach var="suborder" items="${page.list }">
                    <li>
                        <p class="Me-order-top">
                            <span class="T-xz">
                			  <c:choose>
                			  	<c:when test="${suborder.orderType == 5}">【换领】</c:when>
                			  	<c:when test="${suborder.orderType == 1 || suborder.orderType == 4 }">【一起购】</c:when>
                			  	<c:otherwise>&nbsp;</c:otherwise>
                			  </c:choose>
                            </span>
                            <span class="T-time"><fmt:formatDate pattern="yyyy-MM-dd" value="${suborder.createTime}"/>  </span> <span class="T-dd">订单号：${suborder.subOrderId }</span>
                            <span class="T-dp">
                            <c:if test="${user.supplierId !=suborder.supplierId}">${suborder.supplierName }</c:if>
                            <c:if test="${user.supplierId ==suborder.supplierId}">自家</c:if> 
                            </span>
                        </p>
                        <c:set var="commentable" value="false" scope="page"/> 
                        <div class="orderLeft">
	                        <c:forEach var="subOrderItem" items="${suborder.subOrderItems }" varStatus="vs"> 
		                        <c:if test="${empty subOrderItem.commentFlag || subOrderItem.commentFlag == 0}"> 
		                        	<c:set var="commentable" value="true" scope="page"/> 
		                        </c:if>
		                        <div class="Me-order-list">
		                            <div class="M-product">
	                        			<c:if test="${subOrderItem.saleKbn==1 }">
		                        		<div class="picon"><img src="../images/picon2.png" /></div>
		                        		</c:if>
	                        			<c:if test="${subOrderItem.saleKbn==2 }">
		                        		<div class="picon"><img src="../images/picon_c2.png" /></div>
		                        		</c:if>
		                        		<c:if test="${subOrderItem.saleKbn==4 }">
		                        		<div class="picon"><img src="../images/picon_z2.png" /></div>
		                        		</c:if>
	                        			<c:if test="${subOrderItem.saleKbn==5 }">
		                        		<div class="picon"><img src="../images/picon_t2.png" /></div>
		                        		</c:if>
		                                <span class="M-s-img"><a href="/${subOrderItem.productId }.html?skuId=${subOrderItem.partNumber }&pageKey=order" target="_blank"><img src="${subOrderItem.image }" height="78px" width="78px"></a></span>
		
		                                <div class="M-s-text">
		                                    <p class="p1">
		                                        <a href="/${subOrderItem.productId }.html?pageKey=order" target="_blank"> <c:choose> <c:when test="${fn:length(subOrderItem.productName) > 16}"> <c:out value="${fn:substring(subOrderItem.productName, 0, 16)}..."/> </c:when> <c:otherwise> <c:out value="${subOrderItem.productName}"/> </c:otherwise> </c:choose> </a>
		                                    </p>
		                                    <c:forEach items="${subOrderItem.proValues.keySet()}" var="key" varStatus="keyStatus">
		                                        <p class="p${keyStatus.index + 2}">${key}：${subOrderItem.proValues.get(key)} </p>
		                                    </c:forEach>
		                                </div>
		
		                            </div>
		                            <span class="O-dj"><fmt:formatNumber value="${subOrderItem.internalPurchasePrice }" type="currency" pattern="￥0.00"/></span> 
		                            <span class="O-sl">${subOrderItem.number }</span>
		                            <span class="O-sl"><fmt:formatNumber value="${subOrderItem.companyTicket==null?0:subOrderItem.companyTicket }" type="currency" pattern="￥0.00"/>券</span>
		                        </div>
	                    	</c:forEach>
	                    </div>
                    	<div class="orderRight">
				                <span class="s1"><fmt:formatNumber value="${suborder.realPrice }" type="currency" pattern="￥0.00"/>
					            <br/>(含邮费 <em><fmt:formatNumber value="${suborder.totalShipping }" type="currency" pattern="￥0.00"/></em>)
				                <c:if test="${suborder.status == 0 }"> 
				                <c:if test="${!empty suborder.cashPay}"> 
				            		<br/>(已支付 <em><fmt:formatNumber value="${suborder.cashPay}" type="currency" pattern="￥0.00"/></em>)				                
				                </c:if>			                
				                </c:if>
				                
					            </span> 
					            <span class="s2" title="${suborder.name }">${suborder.name } </span>
					            <span class="s3">
					              	<c:if test="${suborder.status == -1 }"> 交易关闭 </c:if>
					                <c:if test="${suborder.status == 0 }"> 待支付 </c:if>
					                <c:if test="${suborder.status == 1 && (empty suborder.stockUp || suborder.stockUp==0)}"> 待发货 </c:if>
					                <c:if test="${suborder.status == 1 && suborder.stockUp==1}"> 备货中 </c:if>
					                <c:if test="${suborder.status == 2 }"> 待收货 </c:if>
					                <c:if test="${suborder.status == 3 }"> 退单申请中 </c:if>
	                                <c:if test="${suborder.status == 5 }"> 退款申请中 </c:if>
	                                <c:if test="${suborder.status == 12 }"> 已退款 </c:if>
					                <c:if test="${suborder.status == 4 &&  (empty suborder.commentStatus || suborder.commentStatus == 0)}"> 交易成功 </c:if>
					                <c:if test="${suborder.status == 4 &&  suborder.commentStatus == 1}"> 已评价 </c:if>
					                <c:if test="${suborder.status == 11 }"> 已退货 </c:if>
					                <c:if test="${suborder.status == -11 }"> 退货失败 </c:if>
					                <c:if test="${suborder.status == -12 }"> 退款失败 </c:if>
					                <c:if test="${suborder.status == 13 }"> 同意退货 </c:if>
					                <c:if test="${suborder.status == 14 }"> 拒绝退货 </c:if>
					                <c:if test="${suborder.status == 15 }"> 同意退款 </c:if>
					                <c:if test="${suborder.status == 16 }"> 拒绝退款 </c:if>
					                <c:if test="${suborder.invoiceStatus == 2}"><br>发票已开</c:if>
					            </span>
	                            <div class="s4">
		                            <p class="" id="${suborder.subOrderId }"></p>
		                            <c:if test="${suborder.status == -1 }">
		                                <p><a href="javascript:void(0);" class="deleteOrder" soid="${suborder.subOrderId }">删除</a></p>
		                            </c:if>
		                            <c:if test="${suborder.status == 0 }">
			                            <p><a class="payment" href="javascript:void(0);" soid="${suborder.subOrderId }">立即付款</a></p>
			                            <p><a href="javascript:void(0);" class="cancelOrder" soid="${suborder.subOrderId }">取消订单</a></p>
		                            </c:if>
		                            <c:if test="${suborder.status == 1 }">
		                            	<c:if test="${suborder.stockUp == 0 || empty suborder.stockUp }">
			                            <p><a href="javascript:void(0);" class="cancelOrder" soid="${suborder.subOrderId }">取消订单</a></p>
			                            </c:if>
		                                <c:if test="${suborder.canUrgedDelivery }">
		                               	<p><a href="javascript:void(0);" class="canUrgedDelivery" sorderid="${suborder.subOrderId }">催促发货</a></p>
	                                	</c:if>
		                                <c:if test="${suborder.selfDelivery==1 }">
		                                <p><a href="/member/toConfirmOrder?subOrderId=${suborder.subOrderId }">确认收货</a></p>
	                                	</c:if>
	                            	</c:if>
		                            <c:if test="${suborder.status == 2 }">
		                                <p><a href="/member/toConfirmOrder?subOrderId=${suborder.subOrderId }">确认收货</a></p>
											<!-- <p><a href="#">查看物流</a></p> -->
	                                </c:if>
		                            <c:if test="${suborder.status == 4 && commentable}">
		                                <p class="p1"><a href="/member/ordereviews?order=${suborder.subOrderId}">评价</a></p>
	                                		<!-- <p><a href="#">查看物流</a></p> -->
	                                	<p><a href="/member/toReturnOrder?subOrderId=${suborder.subOrderId }">申请售后</a></p>
	                            	</c:if>
	                                <c:if test="${suborder.status == 4 && !commentable }">
	                                	<!-- <p class="p1">已评价</p> -->
	                                		<!-- <p><a href="#">查看物流</a></p> -->
	                                	<p><a href="/member/toReturnOrder?subOrderId=${suborder.subOrderId }">申请售后</a></p>
	                            	</c:if>
	                            	
	                            	<c:if test="${suborder.status == 1 || suborder.status == 2 || suborder.status == 4  }">
	                            	  <p><a target="_blank" href="/invoice/getInvoice?suborderid=${suborder.subOrderId}">
	                            	  <c:if test="${suborder.invoiceStatus != 2}">申请开票</c:if>
	                            	  </a></p>
	                            	 </c:if>
	                            	   <c:if test="${suborder.invoiceStatus == 2}">
	                            	  <p><a target="_blank" href="/invoice/getInvoice?suborderid=${suborder.subOrderId}">
	                            	查看发票
	                            	  </a></p>
	                            	  </c:if>
	                                <%-- <c:choose >
	                                	<c:when test="${suborder.status == 3 || suborder.status == 5 || suborder.status == 11 || suborder.status == 12 || suborder.status == 13 || suborder.status == 14 || suborder.status == 15 || suborder.status == 16}">
	                            	 
	                                <c:choose >
	                                	<c:when test="${suborder.status == 3 || suborder.status == 5 || suborder.status == 11 || suborder.status == 12 || suborder.status == -11 || suborder.status == -12}">
	                                    	<p><a href="/member/toReturnOrder?subOrderId=${suborder.subOrderId }">订单详情</a></p>
	                                	</c:when>
	                                	<c:otherwise>
	                                    	<p><a href="/member/orderDetail?subOrderId=${suborder.subOrderId }">订单详情</a></p>
	                                	</c:otherwise>
	                                </c:choose> --%>
	                                <p><a href="/member/orderDetail?subOrderId=${suborder.subOrderId }">订单详情</a></p>
	                                <c:if test="${suborder.status == 3 || suborder.status == 5 || suborder.status == 13 || suborder.status == 14 || suborder.status == 15 || suborder.status == 16}">
	                                	<p><a href="/member/toReturnOrder?subOrderId=${suborder.subOrderId }">申请售后</a></p>
	                                </c:if>
	                            </div>
                        	</div>
                    	<div class="clear"></div>
                    </li>
                </c:forEach>
            </ul>
            <div style="text-align: right;">
                <jsp:include page="../common/page.jsp" flush="true">
                    <jsp:param name="page" value="${page}"/>
                </jsp:include>
            </div>
        </c:if>
    </div>
    <select class="cancel-select" style="display:none;">
        <option title="我不想买了" value="1">我不想买了</option>
        <option title="信息错了，我重新拍" value="2">信息错了，我重新拍</option>
        <option title="卖家缺货" value="3">卖家缺货</option>
        <option title="付款遇到问题（余额不足，不会付款）" value="4">付款遇到问题（余额不足，不会付款）</option>
        <option title="拍错了" value="5">拍错了</option>
        <option title="其他原因" value="6">其他原因</option>
    </select>
    <!--right contont end-->
    <div class="clear:after"></div>
</div>
<div class="clear"></div>
<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
<%@ include file="../common/footer.jsp" %>
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>