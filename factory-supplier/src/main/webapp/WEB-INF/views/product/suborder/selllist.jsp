<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/commons/js.jsp" %>
    <script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.min.js"></script>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <div class="left_list">
            <ul>
                <c:if test="${userSession.type != 3 || userSession.hasAuth('sellList')}">
                    <li class="curr"><a href="${basePath}/suborder/gotoSelllist.html">已售出的商品</a></li>
                </c:if>
                <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
                    <li><a href="${basePath}/comments/toevaluation.html?commentDegree=all">评价管理</a></li>
                </c:if>          
	             <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
	                 <li><a href="${basePath}/questionnaire/templates.html">问卷模板</a></li>
	             </c:if>  	
				<c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
					<li><a href="${basePath}/questionnaire/trialProduct.html?leftMenu=order">试用商品问卷</a></li>
				</c:if>
            </ul>
        </div>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/suborder/gotoSelllist.html">订单管理</a><em>></em>
            <a href="JavaScript:void(0);">已售出的商品</a>
        </div>
        <div class="sale_wrap">
            <form id="sub_form" action="${basePath}/suborder/findSelllistPage.html" method="post">
                <div class="Sold_search">
                    <input type="hidden" name="type" value="${type}"/>
                    <input type="hidden" name="selfDelivery" value="${selfDelivery}"/>
                    <input type="hidden" id="pages" name="pages" value="${pages}"/>
                    <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
                    <ul>
                        <li><span class="s_name">商品名称：</span><input type="text" name="productName" class="input_s" maxLength="20" value="${productName}"/></li>
                        <li><span class="s_name">成交时间：</span><input class="common_input f98" style="width:92px;margin-left:0px;" type="text" name="starttime" readOnly="readOnly" value="${starttime}" onClick="WdatePicker()"><span class="s_zhi">到</span><input class="common_input f98" style="width:92px;margin-left:0px;" type="text" name="endtime" readOnly="readOnly" value="${endtime}" onClick="WdatePicker()"></li>
                        <li><span class="s_name">订单编号：</span><input type="text" name="subOrderId" class="input_s" maxLength="20" value="${subOrderId}"/></li>
                        <li name="oldLi" <c:if test="${type==8}">style="display:none;"</c:if>><span class="s_name">订单状态：</span><select class="select_s" name="status">
                            <option value="">--请选择--</option>
                            <option value="0" <c:if test="${status=='0'}">selected</c:if>>待付款</option>
                            <option value="1" <c:if test="${status=='1'}">selected</c:if>>已付款</option>
                            <option value="2" <c:if test="${status=='2'}">selected</c:if>>已发货</option>
                            <option value="4" <c:if test="${status=='4'}">selected</c:if>>交易成功</option>
                            <option value="-1" <c:if test="${status=='-1'}">selected</c:if>>交易关闭</option>
                        </select></li>
                        <li name="oldLi" <c:if test="${type==8}">style="display:none;"</c:if>><span class="s_name">评价状态：</span><select class="select_s" name="commentStatus">
                            <option value="">--请选择--</option>
                            <option value="0" <c:if test="${commentStatus=='0'}">selected</c:if>>待评价</option>
                            <option value="1" <c:if test="${commentStatus=='1'}">selected</c:if>>已评价</option>
                        </select></li>
                        <li name="oldLi" <c:if test="${type==8}">style="display:none;"</c:if>><span class="s_name">售后服务：</span><select class="select_s" name="afterserviceStatus">
                            <option value="">--请选择--</option>
                            <option value="3" <c:if test="${afterserviceStatus=='3'}">selected</c:if>>售后申请</option>
                            <option value="11" <c:if test="${afterserviceStatus=='11'}">selected</c:if>>售后已完成</option>
                            <option value="13" <c:if test="${afterserviceStatus=='13'}">selected</c:if>>同意退货</option>
                            <option value="14" <c:if test="${afterserviceStatus=='14'}">selected</c:if>>拒绝退货</option>
                            <option value="15" <c:if test="${afterserviceStatus=='15'}">selected</c:if>>同意退款</option>
                            <option value="16" <c:if test="${afterserviceStatus=='16'}">selected</c:if>>拒绝退款</option>
                        </select></li>
                        <li name="oldLi" <c:if test="${type==8}">style="display:none;"</c:if>><span class="s_name">备货状态：</span><select class="select_s" name="stockUp">
                            <option value="">--请选择--</option>
                            <option value="1" <c:if test="${stockUp=='1'}">selected</c:if>>备货中</option>
                        </select></li>
                        <li name="oldLi" <c:if test="${type==8}">style="display:none;"</c:if>><span class="s_name">发票状态：</span><select class="select_s" name="invoiceStatus">
                            <option value="">--请选择--</option>
                            <option value="1" <c:if test="${invoiceStatus=='1'}">selected</c:if>>发票未开</option>
                            <option value="2" <c:if test="${invoiceStatus=='2'}">selected</c:if>>发票已开</option>
                            <option value="0" <c:if test="${invoiceStatus=='0'}">selected</c:if>>未申请发票</option>
                        </select></li>
                        <li class="li_btn">
                            <input type="button" onclick="buttonSubmit(1);" value="查询"/>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" onclick="exportExcel();" value="导出EXCLE"/>
                        </li>
                    </ul>
                    <div class="clear"></div>
                </div>
            </form>
            <ul class="Sold_change">
                <li><a href="javascript:void(0);" onclick="tagChange(this);" typename="1" <c:if test="${type==1}">class="a1"</c:if>>近三个月的订单</a></li>
                <li><a href="javascript:void(0);" onclick="tagChange(this);" typename="2" <c:if test="${type==2}">class="a1"</c:if>>等待买家付款</a></li>
                <li><a href="javascript:void(0);" onclick="tagChange(this);" typename="3" <c:if test="${type==3}">class="a1"</c:if>>等待发货</a></li>
                <li><a href="javascript:void(0);" onclick="tagChange(this);" typename="4" <c:if test="${type==4}">class="a1"</c:if>>已发货</a></li>
                <li><a href="javascript:void(0);" onclick="tagChange(this);" typename="6" <c:if test="${type==6}">class="a1"</c:if>>成功的订单</a></li>
                <li><a href="javascript:void(0);" onclick="tagChange(this);" typename="7" <c:if test="${type==7}">class="a1"</c:if>>关闭的订单</a></li>
                <li><a href="javascript:void(0);" onclick="tagChange(this);" typename="5" <c:if test="${type==5}">class="a1"</c:if>>维权订单</a></li>
                <li><a href="javascript:void(0);" onclick="tagChange(this);" typename="8" <c:if test="${type==8}">class="a1"</c:if>>三个月前的订单</a></li>
            </ul>
            <c:if test="${type==1||type==3}">
                <div class="S_qx">
                    <span><input class="redio" type="checkbox" id="total" onclick="checkTotal(this);check();">全选</span>
                    <a href="javascript:void(0);" onclick="sendOutAll()">批量发货</a>
                    <span><input class="redio" type="checkbox" id="selfDeliveryChoose"  onclick="selfDeliveryChange(this);" <c:if test="${selfDelivery==0}">checked="checked"</c:if>>隐藏自提订单</span>
                </div>
            </c:if>
            <div class="sale_content">
                <ul class="sale_them">
                    <li class="s_1">商品</li>
                    <li class="s_7">单价(元)</li>
                    <li class="s_7">数量</li>
                    <li class="s_7">状态</li>
                    <li class="s_7">订单金额(元)</li>
                    <li class="s_7">操作</li>
                </ul>
                <c:choose>
                    <c:when test="${result.msgBody!=null}">
                        <ul class="sale_infomation_list">
                            <c:forEach items="${result.msgBody}" var="item" varStatus="index">
                            <li>
                                <div class="in_title">
                                    <c:if test="${item.status==1}"><input class="redio" type="checkbox" name="ck" onclick="check();" value="${item.subOrderId}"></c:if><span>订单编号：${item.subOrderId}</span><span>成交时间：<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                                    <c:if test="${item.status==1}"><label style="margin-right: 183px;" class="send_num">催促发货(<em>${item.urgeNumber}</em>)</label></c:if>
                                </div>
                                <div class="infowrap" name="useFulicoinDiv">
                                    <div class="shp_info wid547">
                                        <c:forEach items="${item.suborderitemlist}" var="suborderitem" varStatus="statusitem">
                                            <input type="hidden" name="useFuliCoin" value="${suborderitem.useFuliCoin}"/>
                                            <div class="s_list">
                                                <div class="s_imgwrap" style="width:305px;">
                                                    <div class="s_img">
                                                    <c:if test="${suborderitem.saleKbn==1 }">
											   		<div class="picon"><img src="<%=static_resources %>images/picon2.png" /></div>
											   		</c:if>
											  		<c:if test="${suborderitem.saleKbn==2 }">
											   		<div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
											   		</c:if>
											   		<c:if test="${suborderitem.saleKbn==4 }">
											   		<div class="picon"><img src="<%=static_resources %>images/picon_z2.png" /></div>
											   		</c:if>
											  		<c:if test="${suborderitem.saleKbn==5 }">
											   		<div class="picon"><img src="<%=static_resources %>images/picon_t2.png" /></div>
											   		</c:if>
                                                      <a href="javascript:void(0);"><img src="${suborderitem.image}" width="78" height="78" alt="Me-order-img"></a>
                                                    </div>
                                                    <p class="p1" title="${suborderitem.productName}" style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;"><a href="${basePath}/suborder/toOrderDetail.html?subOrderId=${item.subOrderId}">${suborderitem.productName}</a></p>
                                                        ${suborderitem.itemValues}
                                                </div>
                                                <span class="w120">￥${suborderitem.internalPurchasePrice}</span>
                                                <div class="belong_type w120"><strong style="width:auto">×${suborderitem.number}</strong></div>
                                                <div class="clear"></div>
                                            </div>
                                        </c:forEach>
                                    </div>

                                    <div class="pro_time w120">
                                        <c:if test="${item.status==0}"><p class="">待付款</p></c:if>
                                        <c:if test="${item.status==1}">
                                        <c:if test="${item.stockUp==0||empty item.stockUp }">
                                        	<p class="">待发货</p>
                                        </c:if>
                                        <c:if test="${item.stockUp==1 }"><p class="">备货中</p></c:if>
                                        </c:if>
                                        <c:if test="${item.status==2}"><p class="">待收货</p></c:if>
                                        <%-- <c:if test="${item.status==3||item.status==5}"><p class="">退款申请中</p></c:if> --%>
                                        <c:if test="${item.status==4}"><c:if test="${item.commentStatus==null||item.commentStatus==0}"><p class="">待评价</p></c:if><c:if test="${item.commentStatus==1}"><p class="">已评价</p></c:if></c:if>
                                        <c:if test="${item.status==-1}"><p class="">已关闭</p></c:if>
                                        <c:if test="${item.status==11||item.status==12}"><p class="">已退款成功</p></c:if>
                                        <c:if test="${item.status==-11||item.status==-12}"><p class="">退款失败</p></c:if>
                                         <c:if test="${item.invoiceStatus == 1}"><p ><font color="#FF6161">发票申请</font></p></c:if>
                                         <c:if test="${item.invoiceStatus == 2}"><p ><font color="#959595">发票已开</font></p></c:if>
                                         <c:if test="${item.status==3 || item.status==5 || item.status > 12}"><p ><font color="#FF6161">售后申请</font></p></c:if>
                                        <p class="lase"><a href="${basePath}/suborder/toOrderDetail.html?subOrderId=${item.subOrderId}">查看详情</a></p>
                                        <c:if test="${item.closeFlg=='2'}"><p class="">已出账</p></c:if>
                                        <c:if test="${item.orders.selfDelivery=='1'}"><p><font color="#FF6161">自提订单</font></p></c:if>
                                    </div>
                                    <div class="pro_time w120">
                                        <p class="red" name="useFuliCoin_p">￥${item.realPrice}</p>
                                        <p class="red"><i>含快递：</i>￥${item.totalShipping} <c:if test="${item.status==0 && item.sumPrice.doubleValue()==0.00}"><i class="elt_icon" onclick="updateFreight('${item.subOrderId}','${item.totalShipping}');"></i></c:if></p>
                                    </div>
                                    <div class="add_type w120">
                                        <c:if test="${item.status==0 && item.sumPrice.doubleValue()==0.00}"><a href="javascript:void(0);" class="h_btn" onclick="closeOrderAlert('${item.subOrderId}');">关闭订单</a></c:if>
                                        <c:if test="${item.status==1}">
                                        <c:if test="${item.stockUp==0 ||empty item.stockUp}">
                                        <a href="javascript:void(0);" class="l_btn" title="开始备货，买家无法取消订单" onclick="sendStockUp('${item.subOrderId}')">备货</a><br/>
                                        </c:if>
                                        <c:if test="${item.stockUp==1}">
                                        <a href="javascript:void(0);" class="l_btn" title="未备货，买家可以取消订单" onclick="sendCancelStockUp('${item.subOrderId}')">取消备货</a><br/>
                                        </c:if>
                                        <a href="javascript:void(0);" class="l_btn" onclick="sendOut('${item.subOrderId}')">发货</a><br/>
                                        </c:if>
                                        <c:if test="${item.status==2}"><a href="javascript:void(0);" class="l_btn" onclick="delivertimeAlert('${item.subOrderId}');">延长收货时间</a><br/></c:if>
                                        
                                        <c:if test="${item.invoiceStatus != 0}"><a href="javascript:void(0);" class="l_btn" onclick="queryInvoice('${item.subOrderId}');"><c:if test="${item.invoiceStatus == 1||item.invoiceStatus==null}">录入发票</c:if><c:if test="${item.invoiceStatus == 2}">查看发票</c:if></a><br/></c:if>
                                        <c:if test="${item.status==3 || item.status==5 || item.status > 12}"><a href="${basePath}/suborder/toAftermarketOrderDetail?subOrderId=${item.subOrderId}" class="l_btn" target="_blank">售后处理</a></c:if>
                                        <c:if test="${item.closeFlg=='2'}"><a href="${basePath}/saleBill/toSaleBillView.html?saleBillId=${item.saleBillId}" class="l_btn" target="_blank">已出账 查看</a></c:if>
                                    </div>
                                    </c:forEach>
                        </ul>
                    </c:when>
                    <c:otherwise><p class="h-result">未查询到符合条件的订单，请修改检索条件重新查询！</p></c:otherwise>
                </c:choose>
            </div>
            <c:if test="${type==1||type==3}">
                <div class="S_qx">
                    <span><input class="redio" type="checkbox" id="total" onclick="checkTotal(this);check();">全选</span>
                    <a href="javascript:void(0);" onclick="sendOutAll()" >批量发货</a>
                </div>
            </c:if>
            <!-- 分页位置 start-->
            <wodepageform:PageFormTag pageSize="${result.size}" totalPage="${result.totalPage}" currentPage="${result.page}" url=""/>
            <!-- 分页位置 end-->
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<!--关闭订单弹出框 begin-->
<div class="popup_bg"></div>
<div class="sort_popup" id="change_freight">
    <div class="popup_title">
        <span>修改运费</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="cancelButton('change_freight');"></label>
    </div>
    <div class="sort_popup_cont">
        <div class="change_ln hmartop">
            <span>运费：</span>
            <input type="hidden" id="freight_order_id"/>
            <input type="text" id="freight" style="height:28px;line-height:28px;border:1px solid #d4d4d4;padding-left:5px;"/>
        </div>
        <div class="clear"></div>
        <div class="popup_btn chmarbtn">
            <a href="javascript:void(0);" onclick="changeFreight();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('change_freight');">取消</a>
        </div>
    </div>
</div>

<div class="sort_popup" id="close_order">
    <input type="hidden" name="subOrderId" value=""/>
    <div class="popup_title">
        <span>关闭订单</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="cancelButton('close_order');"></label>
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
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="cancelButton('delivertime');"></label>
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

<!-- 弹出提示框 start -->
<div class="sort_popup" id="popup_alert">
    <div class="popup_title commbox_title">
        <span>提示</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="cancelButton('popup_alert');"></label>
    </div>
    <div class="sort_popup_cont">
        <div class="box_msg">

        </div>
        <div class="clear"></div>
        <div class="popup_btn chmarbtn">
            <a href="javascript:void(0);" onclick="cancelButton('popup_alert');">确认</a>
        </div>
    </div>
</div>
<!-- 弹出提示框 end-->

<script type="text/javascript">
var jsBasePath='${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_suborder_selllist.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>