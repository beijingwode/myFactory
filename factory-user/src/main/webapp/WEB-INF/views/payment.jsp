<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <title>订单支付</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/public.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath %>css/shoppingcart.css">
    <script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
    <script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".zhifu").click(function () {
                $(".zhifu").removeAttr("checked");
                $(this).attr("checked", "checked");
            });
            $("#link").click(function () {
                if (typeof($(".zhifu:checked").val()) == "undefined") {
                    wode.showBox("错误", "请选择付款方式", {"hideBtn": true});
                } else {
                	var subOrderId = '${subOrder.subOrderId }';
                	var orderId = '${order.orderId }';
                	$.ajax({
                        type: "GET",
                        url: "/order/orderStatus",
                        data:{"subOrderId":subOrderId,"orderId":orderId,"payType":"${payType}"},
                        success: function (data) {
                        	var status = data.data;
                        	if(status==1){//1 已支付
	                            wode.showBox("订单信息","您的订单已付款");
	                            $("#boxCheck").click(function(){
	                            	$(".box").hide();
	                            	location.href="/member/myorders";
	                            });
	                            return ;
                        	}else if(status!=0){
                        		wode.showBox("订单信息","您的订单状态有误,不能进行支付");
                                $("#boxCheck").click(function(){
                                	$(".box").hide();
                                });
                                return ;
                        	}
                        	if ($(".zhifu:checked").val()=="pingtaiyue") {
        						wode.showBox("支付确认","使用"+$(".red").html()+"现金券支付");
        						$("#boxCheck").click(function(){
        						location.href = "/payment/toPay?type=${payType}&orderId=${order.orderId }&subOrderId=${subOrder.subOrderId }&zhifu=" + $(".zhifu:checked").val();
                            	});
                        	}else{ 
                        		location.href = "/payment/toPay?type=${payType}&orderId=${order.orderId }&subOrderId=${subOrder.subOrderId }&zhifu=" + $(".zhifu:checked").val();
                        	}
                        }
                    });
       				
         			
                }
            });
        });
    </script>
</head>
<body>
<!--top begin-->
<%@ include file="common/header_04.jsp" %>
<!--top end-->
<!--content begin-->
<div id="middle">
    <c:if test="${!empty order }">
    <div class="middle_content">
        <div class="cart_hd">
            <h2>订单商品</h2>
        </div>
        <div class="cart_info">
            <div class="mergeorder">
                <div class="mergeorder_title">
                    <span>合并</span><em>|</em>
                    <span>${fn:length(subOrders)}笔订单</span>
                    <label id="amount"><fmt:formatNumber value="${realPrice }" type="currency" pattern="0.00"/>元</label>
                </div>
                <div class="mergeorderwrap">
                    <div class="orderdetailbox">
                        <p></p>
                        <ul class="orderthem">
                            <li><span>订单名称</span></li>
                            <li><span>收款方</span></li>
                            <li><span>金额</span></li>
                        </ul>
                        <ul class="order_list">
                            <c:forEach var="subOrder" items="${subOrders }">
                                <li>
                                    <b>
                                        <c:set value="true" var="falg"></c:set>
                                        <c:forEach items="${subOrder.subOrderItems }" var="subOrderItem">
                                            <c:if test="${falg }">
                                                <c:choose>
                                                    <c:when test="${fn:length(subOrderItem.productName) > 20}">
                                                        <c:out value="${fn:substring(subOrderItem.productName, 0, 20)}..."/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${subOrderItem.productName}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:set value="false" var="falg"></c:set>
                                            </c:if>
                                        </c:forEach>
                                    </b>
                                    <span>${subOrder.supplierName }</span>
                                    <strong class="out"><fmt:formatNumber value="${subOrder.realPrice-subOrder.cashPay }" type="currency" pattern="￥0.00"/></strong>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="cart_hd">
            <h2>支付订单</h2>
        </div>
        <div class="cart_info">
            <div class="mergeorder">
                <h3>其他支付方式</h3>
                <c:if test="${!empty userBalance }">
                <div class="valible_sum">账户可用余额：
                    <fmt:formatNumber value="${userBalance.balance }" type="currency" pattern="￥0.00"/>
                    </c:if>
                    <div class="payway">
                        <ul class="tooltab">
                            <li class="fronttab"><a href="javascript:">平台支付</a></li>
                            <!-- <li><a href="javascript:;">储蓄卡</a></li>
                            <li><a href="javascript:;">信用卡</a></li> -->
                        </ul>
                        <div class="paycont" style="display:block;">
                            <ul>
                                <li>
                                    <span><input class="radio zhifu" type="radio" value="zhifubao" checked="checked"></span>
                                    <div class="pho"><img src="<%=basePath %>images/zhifubao.jpg" width="148" height="48" alt="zhifubao"></div>
                                </li>

                                <li>
                                    <span><input name="payType" class="radio zhifu" type="radio" value="wxpay"></span>
                                    <div class="pho"><img src="<%=basePath %>images/WePayLogo.png" width="148" height="48" alt="微信支付"></div>
                                </li>

                                <li>
                                    <span><input name="payType" class="radio zhifu" type="radio" value="unionpay"></span>
                                    <div class="pho"><img src="<%=basePath %>images/yinlian.png" width="148" height="48" alt="银联支付"></div>
                                </li>

                                <c:if test="${(userBalance.balance) >= (realPrice) }">
                                    <li>
                                        <span style="margin-top:20px;"><input class="radio zhifu marginTop0" type="radio" value="pingtaiyue"></span>
                                        <div class="pho"><img src="<%=basePath %>images/yue.jpg" width="148" height="48" alt="yue"></div>
                                    </li>
                                </c:if>
                            </ul>
                        </div>
                        <div class="total">需再支付：<span class="red"><fmt:formatNumber value="${realPrice }" type="currency" pattern="0.00"/>元</span></div>
                        <div class="nextstep"><a id="link" href="javascript:void(0);">下一步</a></div>
                    </div>
                </div>
            </div>
        </div>
        </c:if>
        <c:if test="${!empty subOrder }">
        <div class="middle_content">
            <div class="cart_hd">
                <h2>订单商品</h2>
            </div>
            <div class="cart_info">
                <div class="mergeorder">
                    <div class="mergeorder_title">
                        <label><fmt:formatNumber value="${subOrder.realPrice-subOrder.cashPay }" type="currency" pattern="0.00"/>元</label>
                    </div>
                    <div class="mergeorderwrap">
                        <div class="orderdetailbox">
                            <p></p>
                            <ul class="orderthem">
                                <li><span>订单名称</span></li>
                                <li><span>收款方</span></li>
                                <li><span>金额</span></li>
                            </ul>
                            <ul class="order_list">
                                <li>
                                    <b>
                                        <c:set value="true" var="falg"></c:set>
                                        <c:forEach items="${subOrder.subOrderItems }" var="subOrderItem">
                                            <c:if test="${falg }">
                                                <c:choose>
                                                    <c:when test="${fn:length(subOrderItem.productName) > 20}">
                                                        <c:out value="${fn:substring(subOrderItem.productName, 0, 20)}..."/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${subOrderItem.productName}"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:set value="false" var="falg"></c:set>
                                            </c:if>
                                        </c:forEach>
                                    </b>
                                    <span>${subOrder.supplierName }</span>
                                    <strong class="out"><fmt:formatNumber value="${subOrder.realPrice-subOrder.cashPay }" type="currency" pattern="￥0.00"/></strong>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="cart_hd">
                <h2>支付订单</h2>
            </div>
            <div class="cart_info">
                <div class="mergeorder">
                    <h3>其他支付方式</h3>
                    <c:if test="${!empty userBalance }">
                    <div class="valible_sum">账户可用余额：
                        <fmt:formatNumber value="${userBalance.balance }" type="currency" pattern="￥0.00"/>
                        </c:if>
                        <div class="payway">
                            <ul class="tooltab">
                                <li class="fronttab"><a href="javascript:">平台支付</a></li>
                                <!-- <li><a href="javascript:;">储蓄卡</a></li>
                                <li><a href="javascript:;">信用卡</a></li> -->
                            </ul>
                            <div class="paycont" style="display:block;">
                                <ul>
                                    <li>
                                        <span><input name="payType" class="radio zhifu" type="radio" value="zhifubao" checked="checked"></span>
                                        <div class="pho"><img src="<%=basePath %>images/zhifubao.jpg" width="148" height="48" alt="zhifubao"></div>
                                    </li>
                                    <li>
                                        <span><input name="payType" class="radio zhifu" type="radio" value="wxpay"></span>
                                        <div class="pho"><img src="<%=basePath %>images/WePayLogo.png" width="148" height="48" alt="微信支付"></div>
                                    </li>

                                    <li>
                                        <span><input name="payType" class="radio zhifu" type="radio" value="unionpay"></span>
                                        <div class="pho"><img src="<%=basePath %>images/yinlian.png" width="148" height="48" alt="银联支付"></div>
                                    </li>
                                    <c:if test="${(userBalance.balance) >= (subOrder.realPrice-subOrder.cashPay) }">
                                        <li>
                                            <span><input name="payType" class="radio zhifu" type="radio" value="pingtaiyue"></span>
                                            <div class="pho"><img src="<%=basePath %>images/yue.jpg" width="148" height="48" alt="yue"></div>
                                        </li>
                                    </c:if>
                                </ul>
                            </div>
                            <div class="total">需再支付：<span class="red"><fmt:formatNumber value="${subOrder.realPrice-subOrder.cashPay }" type="currency" pattern="0.00"/>元</span></div>
                            <div class="nextstep"><a id="link" href="javascript:void(0);">下一步</a></div>
                        </div>
                    </div>
                </div>
            </div>
            </c:if>
        </div>
        <!--content end-->
        <%@ include file="common/box.jsp" %>
        <!--footer begin-->
        <%@ include file="common/footer.jsp" %>
        <!--footer end-->
        <script type="text/javascript" src="/resources/js/top_ewm.js"></script>
</body>
</html>