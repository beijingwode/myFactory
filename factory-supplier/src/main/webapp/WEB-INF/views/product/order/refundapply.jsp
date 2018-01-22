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
        <c:if test="${suborder.status eq 3 || suborder.status eq 5}"><p class="r-process-step r-p-2"></p></c:if>
        <c:if test="${suborder.status eq 11 || suborder.status eq 12}"><p class="r-process-step r-p-3"></p></c:if>
        <c:if test="${suborder.status eq -11 || suborder.status eq -12}"><p class="r-process-step r-p-11"></p></c:if>
    </div>

    <!--left begin-->
    <div class="order_left">
        <div class="order_applay">
            <c:if test="${refundorder.returnOrderId == null}">
                <h3>退款申请</h3>
            </c:if>
            <c:if test="${refundorder.returnOrderId != null}">
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
            </div>
            <div class="shopinfo">
                <div class="order_lst">
                    <span class="or_name">买&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;家：</span>
                    <span class="or_cont">${suborder.orders.name}</span>
                </div>
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
            </div>
            <div class="shopinfo" <c:if test="${suborder.status eq -11 || suborder.status eq -12}">style='display:none;'</c:if>>
                <div class="order_lst">
                    <span class="or_name">退款编号：</span>
                    <span class="or_cont">${refundorder.refundOrderId}</span>
                </div>
                <div class="order_lst">
                    <span class="or_name">退款金额：</span>
                    <span class="or_cont red">￥${refundorder.refundPrice}元</span>
                </div>
                <div class="order_lst">
                    <span class="or_name">退款原因：</span>
                    <span class="or_cont">${refundorder.reason}</span>
                </div>
                <div class="order_lst">
                    <span class="or_name">货物状态：</span>
                    <c:if test="${refundorder.goodsStatus == null || refundorder.goodsStatus==1}">
                        <span class="or_cont">已收到货</span>
                    </c:if>
                    <c:if test="${refundorder.goodsStatus ==0}">
                        <span class="or_cont">未收到货</span>
                    </c:if>
              	</div>
            </div>
        </div>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="order_right">
        <div class="order_right_info">
            <c:if test="${suborder.status eq 3 || suborder.status eq 5}">
                <h3>请处理退款申请</h3>
                <div class="order_if borbot">
                    <p>如果您同意，将直接退款给买家。</p>
                    <p>如果您拒绝，买家可以要求我的网工作人员介入处理。如我们核实是您的责任，将会影响您店铺的&nbsp;<a href="javascript:void(0);">纠纷退款率</a>。</p>
                    <p>如果您逾期未处理，系统将自动退款给买家。</p>
                    <strong id="divdown1">( 倒计时：<i class="red">0天00时00分00秒</i>)</strong>
                    <div class="order_agreebtn" id="tytk"><a href="javascript:void(0);">同意退款申请</a></div>
                    	<p id="jjtk">您还可以：<a href="javascript:refuseAlert(${refundorder.refundOrderId});">拒绝退款申请</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
                            href="javascript:afterServiceAlert();">申请客服介入</a></p>
                </div>
            </c:if>
            <c:if test="${suborder.status eq 11 || suborder.status eq 12}">
                <h3 style="visibility:hidden;">h</h3>
                <div class="refundseceess borbot">
                    <h4>退款成功</h4>
                    <p>· 退款原因：${refundorder.reason}</p>
                    <p>· 退款金额：${refundorder.refundPrice}元。</p>
                </div>
            </c:if>
            <c:if test="${suborder.status eq -11 || suborder.status eq -12}">
                <h3 style="visibility:hidden;">h</h3>
                <div class="refundseceess borbot">
                    <h4>退款失败</h4>
                    <p>· 拒绝理由：${suborder.refuseNote}</p>
                </div>
            </c:if>
            <h3>申请说明</h3>
            <c:if test="${suborder.status eq 3 || suborder.status eq 11 || suborder.status eq -11}">
                <div class="order_if">
                    <ul class="order_img_list">
                        <c:forEach var="item" items="${returnorder.returnorderAttachmentList}" varStatus="status">
                            <c:if test="${item.image!=null&&item.image!=''}">
                                <li><img src="${item.image}" width="120" height="120"></li>
                            </c:if>
                        </c:forEach>
                    </ul>
                    <input type="hidden" id="sj"
                           value="<fmt:formatDate value="${returnorder.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                    <div class="clear"></div>
                    <p>${returnorder.note}</p>
                </div>
            </c:if>
            <c:if test="${suborder.status eq 5 || suborder.status eq 12 || suborder.status eq -12}">
                <div class="order_if">
                    <ul class="order_img_list">
                        <c:forEach var="item" items="${refundorder.attachmentList}" varStatus="status">
                            <c:if test="${item.image!=null&&item.image!=''}">
                                <li><img src="${item.image}" width="120" height="120"></li>
                            </c:if>
                        </c:forEach>
                    </ul>
                    <input type="hidden" id="sj"
                           value="<fmt:formatDate value="${refundorder.lastTime}" pattern="yyyy-MM-dd HH:mm:ss"/>">
                    <div class="clear"></div>
                    <p>${refundorder.note}</p>
                </div>
            </c:if>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->
<form id="sub_form" action="${basePath}/returnorder/returncheck.html" method="post">
    <input type="hidden" name="subOrderId" value="${suborder.subOrderId}">
    <input type="hidden" name="returnOrderId" value="${returnorder.returnOrderId}">
    <input type="hidden" name="refundOrderId" value="${refundorder.refundOrderId}">
    <input type="hidden" name="userId" value="${suborder.orders.userId}">
</form>

<div class="popup_bg"></div>
<!--弹出框 begin-->
<div class="sort_popup" id="delivertime">
    <input type="hidden" name="subOrderId" value="${suborder.subOrderId}"/>
    <div class="popup_title">
        <span>退货申请</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"
                    onclick="cancelButton('delivertime');"></label>
    </div>
    <div class="sort_popup_cont">
        <div class="change_ln hmartop">
            <span>请选择拒绝原因：</span>
            <select class="select_input c238" name="delivertimeSelect">
                <option value="">请选择</option>
                <option value="包装不完整">包装不完整</option>
                <option value="影响第二次销售">影响第二次销售</option>
                <option value="未收到退货">未收到退货</option>
            </select>
        </div>
        <div class="clear"></div>
        <input type="hidden" name="refundOrderId" value="">
        <div class="popup_btn chmarbtn">
            <a href="javascript:void(0);" onclick="refuse();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('delivertime');">取消</a>
        </div>
        <p class="h-notice">温馨提示：拒绝用户的退款申请，请首先与用户进行友好沟通，否则，可能会有管理系统管理人员进行协商。</p>
    </div>
</div>
<!--延迟收货时间弹出框 end-->

<!--弹出框 begin-->
<div class="sort_popup" id="afterService">
    <div class="popup_title">
        <span>申请客服介入</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"
                    onclick="cancelButton('afterService');"></label>
    </div>
    <div class="sort_popup_cont">
        <div class="change_ln hmartop">
            <p class="h-notice">客服电话：400-XXXX XXX</p>
        </div>
        <div class="clear"></div>
        <div class="popup_btn chmarbtn">
            <a href="javascript:void(0);" onclick="cancelButton('afterService');">确定</a>
        </div>

    </div>
</div>
<!--延迟收货时间弹出框 end-->

<script type="text/javascript">
var jsBasePath="${basePath}";
var jsRefundorderStatus="${refundorder.status}"
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_order_refundapply.js"></script>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>
