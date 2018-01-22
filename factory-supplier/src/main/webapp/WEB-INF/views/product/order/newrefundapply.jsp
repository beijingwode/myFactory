<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform" %>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<title>我的网商家中心</title>
<%@ include file="/commons/header.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/newReturnOrder.css">
<%@ include file="/commons/js.jsp" %>
<!--header end-->
<!--content begin-->
<div id="content">
    <div class="order_position">
        <span>您的位置：</span>
        <a href="javascript:void(0);">商家中心</a><em>></em>
        <c:if test="${userSession.type != 3 || userSession.hasAuth('sellList')}">
            <a href="${basePath}/suborder/gotoSelllist.html">订单管理</a><em>></em>
        </c:if>
        <c:if test="${userSession.type != 3 || userSession.hasAuth('sellList')}">
            <a href="${basePath}/suborder/gotoSelllist.html">已售出的商品</a>
        </c:if>
    </div>
    <div class="refundwrap">
        <c:if test="${!isReturnOrder && refundorder.status eq 1 }"><p class="r-process-step r-p-2"></p></c:if>
        <c:if test="${!isReturnOrder && refundorder.status eq 4}"><p class="r-process-step r-p-13"></p></c:if>
        <c:if test="${!isReturnOrder && refundorder.status eq 3}"><p class="r-process-step r-p-11"></p></c:if>
        <c:if test="${!isReturnOrder && refundorder.status eq 10}"><p class="r-process-step r-p-3"></p></c:if>
        <c:if test="${isReturnOrder && returnorder.status eq 0}"><p class="r-newReturnProcess-step r-newReturnp0"></p></c:if>
        <c:if test="${isReturnOrder && returnorder.status eq 2}"><p class="r-newReturnProcess-step r-newReturnp2"></p></c:if>
        <c:if test="${isReturnOrder && returnorder.status eq 3}"><p class="r-newReturnProcess-step r-newReturnp3"></p></c:if>
        <c:if test="${isReturnOrder && returnorder.status eq 4}"><p class="r-newReturnProcess-step r-newReturnp4"></p></c:if>
        <c:if test="${isReturnOrder && returnorder.status eq 1}"><p class="r-newReturnProcess-step r-newReturnp5"></p></c:if>
        <c:if test="${isReturnOrder && returnorder.status eq 6}"><p class="r-newReturnProcess-step r-newReturnp6"></p></c:if>
        <c:if test="${isReturnOrder && returnorder.status eq -1}"><p class="r-newReturnProcess-step r-newReturnp6"></p></c:if>
    </div>

    <!--left begin-->
    <div class="order_left">
        <div class="order_applay">
            <c:if test="${!isReturnOrder}">
                <h3>退款申请</h3>
            </c:if>
            <c:if test="${isReturnOrder}">
                <h3>退货退款申请</h3>
            </c:if>
            <div class="order_shopinfo">
                <div class="order_shopimg">
                	<c:if test="${!empty suborder.suborderitemlist && suborder.suborderitemlist[0].saleKbn==1}">
                	<div class="picon"><img src="<%=static_resources %>images/picon2.png" /></div>
                	</c:if>
                	<c:if test="${!empty suborder.suborderitemlist && suborder.suborderitemlist[0].saleKbn==2}">
                	<div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
                	</c:if>
                	<c:if test="${!empty suborder.suborderitemlist && suborder.suborderitemlist[0].saleKbn==4}">
                	<div class="picon"><img src="<%=static_resources %>images/picon_z2.png" /></div>
                	</c:if>
                	<c:if test="${!empty suborder.suborderitemlist && suborder.suborderitemlist[0].saleKbn==5}">
                	<div class="picon"><img src="<%=static_resources %>images/picon_t2.png" /></div>
                	</c:if>
                	<img
                        <c:if test="${empty suborder.suborderitemlist}">src=""</c:if>
                        <c:if test="${!empty suborder.suborderitemlist}">src="${suborder.suborderitemlist[0].image}"</c:if>
                        width="78" height="78" alt="Me-order-img"></div>
                <p style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;"><c:if
                        test="${!empty suborder.suborderitemlist}">${suborder.suborderitemlist[0].productName}</c:if></p>
                        <p style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">
                        <c:if test="${!empty suborder.suborderitemlist}">${suborder.suborderitemlist[0].itemValues}</c:if>
                        </p>
                        <p style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">数量：
                        <c:if test="${!empty suborder.suborderitemlist}">${suborder.suborderitemlist[0].number}</c:if>
                        </p>
            </div>
            <div class="shopinfo">
                <div class="order_lst">
                    <span class="or_name">订单编号：</span>
                    <span class="or_cont">${suborder.subOrderId}</span>
                </div>
                <div class="order_lst">
                    <span class="or_name">订单总价：</span>
                    <span class="or_cont red">￥${suborder.totalProduct-suborder.companyTicket}</span>
                </div>
                <div class="order_lst">
                    <span class="or_name">快递费用：</span>
                    <span class="or_cont">￥${suborder.totalShipping}元</span>
                </div>
                <div class="order_lst">
                    <span class="or_name">收货地址：</span>
                    <span class="or_cont">${suborder.orders.address}</span>
                </div>
                <div class="order_lst">
                    <span class="or_name">物流状态：</span>
                    <span class="or_cont"><c:if test="${(refundorder.goodsStatus == null ||refundorder.goodsStatus ==0)}">未收到货</c:if><c:if test="${refundorder.goodsStatus ==1}">已收到货</c:if></span>
                </div>
            </div>
            <div class="shopinfo">
                <div class="order_lst">
                    <span class="or_name">申请日期：</span>
                    <span class="or_cont">
                    <c:if test="${!isReturnOrder}">
                    <fmt:formatDate value="${refundorder.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
                    </c:if>
                    <c:if test="${isReturnOrder}">
                    <fmt:formatDate value="${returnorder.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>
                    </c:if>
                    </span>
                </div>
                <div class="order_lst">
                    <span class="or_name">退款金额：</span>
                    <span class="or_cont red">￥
                   	<c:if test="${!isReturnOrder}">${refundorder.refundPrice}</c:if><c:if test="${isReturnOrder}">${returnorder.returnPrice}</c:if>元
                    </span>
                </div>
                <div class="order_lst">
                    <span class="or_name">退款原因：</span>
                    <span class="or_cont"><c:if test="${!isReturnOrder}">${refundorder.reason}</c:if><c:if test="${isReturnOrder}">${returnorder.reason}</c:if></span>
                </div>
            </div>
        </div>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="order_right">
    	<div class="order_right_info">
              <c:choose>
              	<c:when test="${isReturnOrder}">
              	<c:if test="${returnorder.status eq 0}">
               		<h3>请处理售后申请</h3>
               		<div class="order_if borbot">
                     <p>如果您同意退货，请指定退货地址。</p>
                     <p>如果您拒绝，买家可以要求平台介入处理。如核实为商家责任，将会影响商家 &nbsp;<a href="javascript:void(0);">纠纷退款率</a>。</p>
                     <div class="shipping_address" style="margin-bottom: 10px;">
                     	<p>
                     	<a href="${basePath}/shippingAddress/todeliver.html"  target="_blank">
                     	<c:if test="${supplierAddressReturned.id!=null && supplierAddressReturned.id!=''}">修改退货地址</c:if>
                     	<c:if test="${supplierAddressReturned.id==null||supplierAddressReturned.id==''}">添加退货地址</c:if>
                     	</a>
                     	<span style="float: left;">退货地址：</span>
                     	<c:if test="${supplierAddressReturned.id!=null && supplierAddressReturned.id!=''}">
                     	${supplierAddressReturned.provinceName}${supplierAddressReturned.cityName}${supplierAddressReturned.address}，${supplierAddressReturned.aid}，${supplierAddressReturned.name}，
                     	<c:choose>
                     	<c:when test="${supplierAddressReturned.phone==null||supplierAddressReturned.phone==''}">${supplierAddressReturned.tel}</c:when>
                     	<c:otherwise>${supplierAddressReturned.phone}</c:otherwise>
                     	</c:choose>
                     	</c:if>
                     	<c:if test="${supplierAddressReturned.id!=null && supplierAddressReturned.id!=''}">
                     	<input type="hidden" id="returnedAddress" name="returnedAddress" value="${supplierAddressReturned.provinceName}${supplierAddressReturned.cityName}${supplierAddressReturned.address}，${supplierAddressReturned.aid}，${supplierAddressReturned.name}，<c:choose><c:when test="${supplierAddressReturned.phone==null||supplierAddressReturned.phone==''}">${supplierAddressReturned.tel}</c:when><c:otherwise>${supplierAddressReturned.phone}</c:otherwise></c:choose>"/>
                     	</c:if>
                     	<c:if test="${supplierAddressReturned.id==null||supplierAddressReturned.id==''}">
                     	<input type="hidden" id="returnedAddress" name="returnedAddress" value="">
                     	</c:if>
                     	</p>
                     </div>
                     <div class="order_agreebtn"><a href="javascript:agreeReturn('good');">同意退货</a></div>
                    	<p id="jjtk">您还可以：<a href="javascript:refuseAlert('good');">拒绝售后申请</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="javascript:void(0);" style="color: #434343">或申请平台介入，平台售后服务电话：010-56206418</a></p>
                    </div>
              	</c:if>
              	<c:if test="${returnorder.status eq 2}">
              		<h1 style="color: #8fc31f;margin-left: 10px">等待买家发出退货</h1>
                	<div class="refundseceess borbot">
                    <p>已同意买家退货</p>
                    <div class="shipping_address" style="margin-bottom: 10px;">
                     	<p>
                     	<span style="float: left;">退货地址：</span>
                     	<%-- ${supplierAddressReturned.provinceName}${supplierAddressReturned.cityName}${supplierAddressReturned.address}，${supplierAddressReturned.aid}，${supplierAddressReturned.name}，
                     	<c:choose>
                     	<c:when test="${supplierAddressReturned.phone==null||supplierAddressReturned.phone==''}">${supplierAddressReturned.tel}</c:when>
                     	<c:otherwise>${supplierAddressReturned.phone}</c:otherwise>
                     	</c:choose> --%>
                     	${suborder.returnedAddress}
                     	</p>
                     </div>
                	</div>
              	</c:if>
              	<c:if test="${returnorder.status eq 3}">
              		<h1 style="color: red;margin-left: 10px">您已拒绝退货</h1>
                	<div class="refundseceess borbot">
                    <p>拒绝理由：${suborder.refuseNote}</p>
                	</div>
              	</c:if>
              	<c:if test="${returnorder.status eq 4}">
               		<h3>请处理售后申请</h3>
               		<div class="order_if borbot">
               		 <p>退货物流信息：<c:if test="${returnorder.goodsStatus eq 1}">商家已签收</c:if> <c:if test="${returnorder.goodsStatus eq 0 ||returnorder.goodsStatus==null}">快递发出</c:if> &nbsp;  &nbsp; ${compInfo.name} &nbsp; &nbsp;${returnorder.expressNo}&nbsp; &nbsp; <a href="javascript:showlistlogInfoAlert();" style="color: #2b8dff">查看物流</a>&nbsp; &nbsp;
               		 
               		 <c:if test="${returnorder.goodsStatus eq 0 || returnorder.goodsStatus==null}"><a href="javascript:go2Sign();">确认签收</a></c:if>
               		 </p>
                     <p>如果您同意退款，平台会将退款金额返还到买家支付账户，下次结算时从货款中扣除</p>
                     <p>如果您拒绝，买家可以要求平台介入处理。如核实为商家责任，将会影响商家 &nbsp;<a href="javascript:void(0);">纠纷退款率</a>。</p>
                     <div class="order_agreebtn"><a href="javascript:agreeReturn('goodAndmoney');">同意退款</a></div>
                    	<p id="jjtk">您还可以：<a href="javascript:refuseAlert('goodAndmoney');">拒绝退款</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="javascript:void(0);" style="color: #434343">或申请平台介入，平台售后服务电话：010-56206418</a></p>
                    </div>
              	</c:if>
              	<c:if test="${returnorder.status eq 1}">
              		<h1 style="color: #8fc31f;margin-left: 10px">退款已完成</h1>
                	<div class="order_if borbot">
                    <p>· 如存在其他问题，可随时申请平台介入，平台售后服务电话：010-56206418</p>
                    <p>退货物流信息：<c:if test="${returnorder.goodsStatus eq 1}">商家已签收</c:if> <c:if test="${returnorder.goodsStatus eq 0}">快递发出</c:if> &nbsp;  &nbsp; ${compInfo.name} &nbsp;&nbsp;${returnorder.expressNo}&nbsp;&nbsp;<a href="javascript:showlistlogInfoAlert();" style="color: #2b8dff">查看物流</a>&nbsp; &nbsp;
               		 <c:if test="${returnorder.goodsStatus eq 0}"><a href="javascript:go2Sign();">确认签收</a></c:if>
               		 </p>
                	</div>
              	</c:if>
              	<c:if test="${returnorder.status eq 6}">
              		<h1 style="color: red;margin-left: 10px">您已拒绝退款</h1>
                	<div class="order_if borbot">
                    <p>拒绝理由：${suborder.refuseNote}</p>
                    <p>退货物流信息：<c:if test="${returnorder.goodsStatus eq 1}">商家已签收</c:if> <c:if test="${returnorder.goodsStatus eq 0 || returnorder.goodsStatus==null}">快递发出</c:if> &nbsp;  &nbsp; ${compInfo.name} &nbsp;&nbsp;${returnorder.expressNo} &nbsp;  &nbsp; <a href="javascript:showlistlogInfoAlert();" style="color: #2b8dff">查看物流</a>&nbsp; &nbsp;
               		 <c:if test="${returnorder.goodsStatus eq 0 ||returnorder.goodsStatus==null}"><a href="javascript:go2Sign();">确认签收</a></c:if>
               		 </p>
                	</div>
              	</c:if>
              	</c:when>
              	<c:otherwise>
              		<c:if test="${refundorder.status eq 1}">
               		<h3>请处理售后申请</h3>
               		<div class="order_if borbot">
                     <p>如果您同意退款，平台会将退款金额返还到买家支付账户，下次结算时从货款中扣除</p>
                     <p>如果您拒绝，买家可以要求平台介入处理。如核实为商家责任，将会影响商家 &nbsp;<a href="javascript:void(0);">纠纷退款率</a>。</p>
                     <div class="order_agreebtn"><a href="javascript:agreeReturn('money');">同意退款</a></div>
                    	<p id="jjtk">您还可以：<a href="javascript:refuseAlert('money');">拒绝退款</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    	<a href="javascript:void(0);" style="color: #434343">或申请平台介入，平台售后服务电话：010-56206418</a></p>
                    </div>
              		</c:if>
              		<c:if test="${refundorder.status eq 4}">
              			<h1 style="color: red;margin-left: 10px">您已拒绝退款</h1>
                		<div class="refundseceess borbot">
                    	<p>拒绝理由：${suborder.refuseNote}</p>
                		</div>
              		</c:if>
              		<c:if test="${refundorder.status eq 10}">
              			<h1 style="color: #8fc31f;margin-left: 10px">退款已完成</h1>
                		<div class="refundseceess borbot">
                    	<p>· 如存在其他问题，可随时申请平台介入，平台售后服务电话：010-56206418</p>
                		</div>
              		</c:if>
              	</c:otherwise>
              </c:choose>
            <h3>其他申请信息</h3>
                <div class="order_if">
                	<p style="margin-bottom: 20px; margin-top: 0px">备注：<c:if test="${isReturnOrder}">${returnorder.note}</c:if><c:if test="${!isReturnOrder}">${refundorder.note}</c:if></p>
                    <ul class="order_img_list"><span style="float: left;margin-right: 10px;font: 12px/24px 'Microsoft YaHei';color: '#434343'";>凭证：</span>
                    	<c:choose>
                    	<c:when test="${isReturnOrder}">
                        <c:forEach var="item" items="${returnorder.returnorderAttachmentList}" varStatus="status">
                            <c:if test="${item.image!=null&&item.image!=''}">
                                <li><img src="${item.image}" width="120" height="120"></li>
                            </c:if>
                        </c:forEach>
                        </c:when>
                        <c:otherwise>
                        <c:forEach var="item" items="${refundorder.attachmentList}" varStatus="status">
                            <c:if test="${item.image!=null&&item.image!=''}">
                                <li><img src="${item.image}" width="120" height="120"></li>
                            </c:if>
                        </c:forEach>
                        </c:otherwise>
                        </c:choose>
                    </ul>
                    <div class="clear"></div>
                </div>
                <!-- <div class="order_if" id="showlistlogInfo" style="display: none">
                <div class="flow_btn"></div>
                <p class="P_title" >物流信息</p>
         		<ul class="wl_xx" >
        		</ul>
                </div> -->
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->
<form id="sub_form" action="${basePath}/returnorder/returncheck.html" method="post">
    <input type="hidden" name="subOrderId" value="${suborder.subOrderId}" id="subOrderId">
    <input type="hidden" name="returnOrderId" id="returnOrderId" value="${returnorder.returnOrderId}">
    <input type="hidden" name="refundOrderId" id="refundOrderId" value="${refundorder.refundOrderId}">
    <input type="hidden" name="userId" id="userId" value="${suborder.orders.userId}">
</form>

<div class="popup_bg"></div>
<!--弹出框 begin-->
<div class="sort_popup" id="delivertime">
    <div class="popup_title">
        <span>拒绝申请</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"
                    onclick="cancelButton('delivertime');"></label>
    </div>
    <input type="hidden" id="state" value="">
    <div class="sort_popup_cont">
        <div class="change_ln hmartop">
            <span>请选择拒绝原因：</span>
            <select class="select_input c238" name="delivertimeSelect">
                <option value="">请选择</option>
                <option value="包装不完整">包装不完整</option>
                <option value="影响第二次销售">影响第二次销售</option>
                <option value="未收到退货">未收到退货</option>
            </select>
            <input type="text" name="supplierRefuseReason" value="" id="supplierRefuseReason" placeholder="其他原因" class="select_input c238" style="float: right;margin-right: 12px;margin-top: 5px;">
        </div>
        <div class="clear"></div>
        <input type="hidden" name="refundOrderId" value="">
        <div class="popup_btn chmarbtn">
            <a href="javascript:void(0);" onclick="refuse();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('delivertime');">取消</a>
        </div>
        <p class="h-notice">温馨提示：请先与用户进行友好沟通，协商出双方满意的处理办法。或申请平台介入，平台售后服务电话：010-56206418</p>
    </div>
</div>
<!--延迟收货时间弹出框 end-->

<!--弹出框 begin-->
<div class="sort_popup" id="showlistlogInfo">
	<input type="hidden" name="expressCom" id="expressCom" value="${expressCom}">
    <input type="hidden" name="searchId" id="searchId" value="${searchId}">
    <input type="hidden" name="expressNo" id="expressNo" value="${returnorder.expressNo}">
    <div class="popup_title">
        <span>物流信息</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"
                    onclick="cancelButton('showlistlogInfo');"></label>
    </div>
    <div class="sort_popup_cont">
        <div id="listLogInfo">
            
        </div>
        <div class="clear"></div>
    </div>
</div>
<!--延迟收货时间弹出框 end-->

<script type="text/javascript">
var jsBasePath="${basePath}";
var jsRefundorderStatus="${refundorder.status}";
var jsReturnorderStatus="${returnorder.status}";
var jsRefundorderId="${refundorder.refundOrderId}";
var jsReturnorderId="${returnorder.returnOrderId}";
var jsSuborderId="${suborder.subOrderId}";
var isReturnOrder="${!isReturnOrder}";

$(".flow_btn").click(function(e){
	$("#showlistlogInfo").hide();
})
</script>
<script type="text/javascript" src="<%=static_resources  %>js/product_order_newrefundapply.js"></script>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>
