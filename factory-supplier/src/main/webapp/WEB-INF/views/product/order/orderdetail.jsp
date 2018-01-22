
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<title>我的网商家中心</title>
<%@ include file="/commons/header.jsp" %>
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
    <div class="clear"></div>
    <c:if test="${suborder.status != '-1' && !isAftermarketOrder}">
        <div class="order_process">
            <div
                    <c:if test="${suborder.status eq '0'}"> class="order_process_img process-1"</c:if>
                    <c:if test="${suborder.status eq '1'}"> class="order_process_img process-2"</c:if>
                    <c:if test="${suborder.status eq '2'}"> class="order_process_img process-3"</c:if>
                    <c:if test="${suborder.status eq '4'}"> class="order_process_img process-4"</c:if>
                    <c:if test="${suborder.status eq '-11'}"> class="order_process_img process-4"</c:if>
            ></div>
        </div>
    </c:if>
    <div class="order_state" <c:if test="${suborder.status != '0'}"> style="display: none"</c:if>>
        <div class="title">订单状态：<span>买家已下单，等待付款</span></div>
        <p class="gbbtn" onclick="closeOrderAlert('${suborder.subOrderId}')"><a href="javascript:void(0);">关闭交易</a></p>
        <div class="order_mark">
            <p class="red">我的网提醒您！</p>
            <p>买家未付款，如果您想关闭交易，请与买家协商，否则可能被买家投诉。</p>
        </div>
    </div>

    <div class="order_state" <c:if test="${suborder.status != '1'}"> style="display: none"</c:if>>
        <div class="title">订单状态：<span>买家已付款，等待卖家发货</span></div>
        <p class="gbbtn" onclick="sendOut('${suborder.subOrderId}')"><a href="javascript:void(0);">发货</a></p>
        <!--         <p class="chgbbtn"><a href="javascript:void(0);">修改发货地址</a></p> -->
        <div class="order_mark">
            <p class="red">我的网提醒您！</p>
            <p>· 买家已付款，请尽快发货，否则买家有权申请退款。</p>
            <p>· 如果无法发货，请及时与买家联系并说明情况。</p>
            <p>· 买家申请退款后，须征得买家同意后再操作发货，否则买家有权拒收货物。</p>
        </div>
    </div>

    <div class="order_state" <c:if test="${suborder.status != '2'}"> style="display: none"</c:if>>
        <div class="title">订单状态：<span>卖家已发货，等待买家确认</span></div>
        <p class="chgbbtn"><a href="javascript:void(0);" onclick="delivertimeAlert('${suborder.subOrderId}')">延长收货时间</a>
        </p>
        <div class="order_infort">
            <p>买家${suborder.orders.name} 有 <span class="red" id="divdown1">00天00小时00分00秒</span> 来完成“确认收货”。</p>
            <strong>如果期间结束时，买家没有“确认收货”，也没有“申请退款”，交易将成功。</strong>
        </div>
        <div class="order_mark">
            <p class="red">我的网提醒您！</p>
            <p>如果买家表示未收到货或者收到的货物有问题，请及时联系买家积极处理，友好协商。</p>
        </div>
    </div>

    <div class="order_state" <c:if test="${suborder.status != '4'}"> style="display: none"</c:if>>
        <div class="title">订单状态：<span>交易成功</span></div>
        <p class="chgbbtn"><a href="${basePath}/suborder/gotoSelllist.html">返回订单管理</a></p>
        <div class="order_mark">
            <p class="red">我的网提醒您！</p>
            <p>交易已成功，如果买家提出售后要求，请积极与买家协商，做好售后服务。</p>
        </div>
    </div>

    <div class="order_state" <c:if test="${suborder.status != '-1'}"> style="display: none"</c:if>>
        <div class="title">订单状态：<span>交易关闭</span></div>
        <div class="order_mark">
            <p class="red">关闭原因：</p>
            <p>${suborder.closeReason}</p>
        </div>
    </div>

    <div class="order_state" <c:if test="${!isAftermarketOrder}"> style="display: none"</c:if>>
        <div class="title">订单状态：<span><c:choose><c:when test="${suborder.status == '11'}">退货退款成功</c:when><c:when test="${suborder.status == '12'}">退款成功</c:when><c:otherwise>售后详情</c:otherwise></c:choose></span></div>
        <p class="chgbbtn"><a href="${basePath}/suborder/toAftermarketOrderDetail?subOrderId=${suborder.subOrderId}">查看售后详情</a></p>
        <div class="order_mark">
            <p class="red">我的网提醒您！</p>
            <p>请积极与买家协商，做好售后服务。</p>
        </div>
    </div>
    

    <div class="detail_list">
        <ul>
            <li onclick="ChangeBg('1',this)" class="current"><a href="javascript:void(0);">订单信息</a></li>
            <li onclick="ChangeBg('2',this)"><a href="javascript:void(0);">收货信息</a></li>
            <li onclick="ChangeBg('3',this)"><a href="javascript:void(0);">物流信息</a></li>
        </ul>
    </div>

    <div class="detail_cont" id="page1">
        <p>订单编号：${suborder.subOrderId}</p>
        <p>下单时间：<fmt:formatDate value="${suborder.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
        <c:set value="0" var="totalProduct"></c:set>
            <c:forEach var="subOrderItem" items="${suborder.suborderitemlist }"> 
            	<c:set value="${totalProduct + (subOrderItem.internalPurchasePrice * subOrderItem.number)}" var="totalProduct"></c:set>
            </c:forEach>
        <p>应收金额：<fmt:formatNumber value="${totalProduct +suborder.totalShipping}" type="currency" pattern="￥0.00"/></p>
        <c:if test="${flag}">
       	<p> 换领币：&nbsp;&nbsp;<font color="red">-<fmt:formatNumber value="${totalBenefitTicket}" type="currency" pattern="￥0.00"/></font></p>
        </c:if>
        <p>实收金额：<c:if test="${suborder.status != '0'}">￥${suborder.realPrice}</c:if><c:if test="${suborder.status == '0'}">￥${suborder.sumPrice}</c:if> </p>
        <p>支付时间：<fmt:formatDate value="${suborder.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
        <p>应付佣金：<c:if test="${commission != -1}">￥${commission}</c:if><c:if test="${commission==-1}">免佣金（本企业订单）</c:if></p>	
        <p>发票抬头：${suborder.orders.invoiceTitle}</p>
        <p>备注：${note}</p>
        <div class="detail_tab">
            <ul class="detailthem">
                <li class="od01">商品</li>
                <li class="od02">单价（元）</li>
                <li class="od03">数量</li>
                <li class="od04">商品总价（元）</li>
                <li class="od05">物流费（元）</li>
            </ul>
            <ul class="order-lst">
                <c:forEach var="item" items="${suborder.suborderitemlist}" varStatus="status">
                    <li>
                        <div class="order_product">
                            <div class="hs_imgwrap">
                                <span class="order-shop-img">                           
 								<c:if test="${item.saleKbn==1 }">
						   		<div class="picon"><img src="<%=static_resources %>images/picon2.png" /></div>
						   		</c:if>
						  		<c:if test="${item.saleKbn==2 }">
						   		<div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
						   		</c:if>
						   		<c:if test="${item.saleKbn==4 }">
						   		<div class="picon"><img src="<%=static_resources %>images/picon_z2.png" /></div>
						   		</c:if>
						  		<c:if test="${item.saleKbn==5 }">
						   		<div class="picon"><img src="<%=static_resources %>images/picon_t2.png" /></div>
						   		</c:if>
                                  <a href="javascript:void(0);"><img src="${item.image}" width="78" height="78"></a>
                                </span>
                                <div class="order_nm">
                                    <p class="p1"><a href="javascript:void(0);">${item.productName}</a></p>
                                        ${item.itemValues}
                                </div>
                            </div>
                        </div>
                        <span class="od02">￥${item.internalPurchasePrice}</span>
                        <span class="od03">${item.number}</span>
                        <span class="od04">￥${item.internalPurchasePrice * item.number}</span>
                        <span class="od05"><c:if test="${status.index == 0 }">￥${suborder.totalShipping}</c:if></span>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div class="detail_cont" id="page2" style="display: none">
        <p>
            收货地址：&nbsp;&nbsp;&nbsp;&nbsp;${suborder.orders.name }，${suborder.orders.mobile }，${suborder.orders.address }</p>
        <p>运送方式：&nbsp;&nbsp;&nbsp;&nbsp;快递</p>
    </div>
    <div class="detail_cont" id="page3" style="display: none">
        <div class="flow_left">
            <p><span>物流公司：</span>
            	<c:if test="${suborder.orders.selfDelivery !=1 }">
                <c:if test="${not empty compInfo}">
                    ${compInfo.name}
                </c:if>
                </c:if>
                <c:if test="${suborder.orders.selfDelivery ==1 }">
                	自提
                </c:if>
            </p>
            <p><span><c:if test="${suborder.expressType == '14660000000000000' }">卡券密码：</c:if><c:if test="${suborder.expressType != '14660000000000000' }">物流单号：</c:if></span>${suborder.expressNo }</p>
        </div>
        <div class="flow_right">
            <p>物流动态</p>
            <c:if test="${not empty listlogInfo && listlogInfo.size() > 0}">
                <c:forEach var="item" items="${listlogInfo}" varStatus="status">
                    <c:if test="${status.first}">
                        <p class="cur">
                    </c:if>
                    <c:if test="${!status.first}">
                        <p>
                    </c:if>
                    <fmt:formatDate value="${item.dealDate }" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate><span>${item.des }</span></p>
                </c:forEach>
            </c:if>
            <div class="flowinfo">
                <p class="si"></p>
                <div class="flow_contwrap">
                    <p>信息来源：
                    	<c:if test="${suborder.orders.selfDelivery !=1 }">
                        <c:if test="${not empty compInfo}">
                            ${compInfo.name}
                        </c:if>
                        </c:if>
                        <c:if test="${suborder.expressType == '14660000000000000' }">卡券密码：</c:if><c:if test="${suborder.expressType == '14660000000000001'}">货运号：</c:if><c:if test="${suborder.expressType != '14660000000000000' && suborder.expressType != '14660000000000001' }">物流单号：</c:if>${suborder.expressNo}
                    </p>
                    <strong>收货信息： ${suborder.orders.address }，${suborder.orders.name }，${suborder.orders.mobile }</strong>
                    <p>发货信息：${suborder.sendAddress }</p>
                    <p>退货信息：${suborder.returnedAddress }</p>
                </div>
            </div>
        </div>
    </div>
</div>
<input type="hidden" id="sj" value="<fmt:formatDate value="${suborder.lasttakeTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
<!--content end-->


<!--关闭订单弹出框 begin-->
<div class="popup_bg"></div>
<div class="sort_popup" id="close_order">
    <input type="hidden" name="subOrderId" value=""/>
    <div class="popup_title">
        <span>关闭订单</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"
                    onclick="cancelButton('close_order');"></label>
    </div>
    <div class="sort_popup_cont">
        <div class="change_ln hmartop">
            <span>请选择关闭理由：</span>
            <select class="select_input c238" name="closeSelect">
                <option value="未及时付款">未及时付款</option>
                <option value="买家不想买">买家不想买</option>
                <option value="买家重新拍">买家重新拍</option>
                <option value="恶意买家/同行捣乱">恶意买家/同行捣乱</option>
                <option value="缺货">缺货</option>
                <option value="其他原因">其他原因</option>
            </select>
        </div>
        <div class="clear"></div>
        <div class="popup_btn chmarbtn">
            <a href="javascript:void(0);" onclick="closeOrder();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('close_order');">取消</a>
        </div>
        <p class="h-notice">温馨提示：请您在与买家协商达成一致的前提下，再进行关闭交易操作，否则可能招致买家投诉。</p>
    </div>
</div>
<!--关闭订单弹出框 end-->

<!--延迟收货时间弹出框 begin-->
<div class="sort_popup" id="delivertime">
    <input type="hidden" name="subOrderId" value=""/>
    <div class="popup_title">
        <span>延长收货时间</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"
                    onclick="cancelButton('delivertime');"></label>
    </div>
    <div class="sort_popup_cont">
        <div class="change_ln hmartop">
            <span>请选择延长天数：</span>
            <select class="select_input c238" name="delivertimeSelect">
                <option value="3">3天</option>
                <option value="5">5天</option>
                <option value="7">7天</option>
                <option value="10">10天</option>
            </select>
        </div>
        <div class="clear"></div>
        <div class="popup_btn chmarbtn">
            <a href="javascript:void(0);" onclick="delivertime();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('delivertime');">取消</a>
        </div>
        <p class="h-notice">温馨提示：延长收货时间可以让买家有更多时间来“确定收货”，而不会急于去申请退款。</p>
    </div>
</div>
<!--延迟收货时间弹出框 end-->


<script type="text/javascript">
var jsBasePath='${basePath}';
var jsExpressCom='${expressCom}';
var jsSuborderStatus="${suborder.status}";
   
var jsSuborderExpressNo="${suborder.expressNo}";
var jsSearchId='${searchId}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_order_orderdetail.js"></script>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>
