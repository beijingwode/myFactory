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
    <script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.min.js"></script>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
            <c:if test="${userSession.type != 3 || userSession.hasAuth('address')}">
                <li><a href="${basePath}/shippingAddress/todeliver.html">发货地址管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('suborder')}">
                <li class="curr"><a href="${basePath}/suborder/gotoSuborderlist.html">发货</a></li>
            </c:if>
            
            <li><a href="${basePath}/shippingAddress/freight_templates.html">运费模板</a></li>
            <li><a href="${basePath}/suborder/toSupplier_express.html?blank=1">常用快递公司</a></li>
        </ul>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/shippingAddress/todeliver.html">配送管理</a><em>></em>
            <a href="javascript:void(0);">发货</a>
        </div>
        <div class="goods_wrap">
            <div class="goods_dh">
                <ul>
                    <li onclick="tagChange(this);" id="dfhLi" <c:if test="${status==1}">class="curr"</c:if>><a href="javascript:void(0);">待发货订单</a></li>
                    <li onclick="tagChange(this);" id="yfhLi" <c:if test="${status==2}">class="curr"</c:if>><a href="javascript:void(0);">已发货订单</a></li>
                </ul>
            </div>
            <form id="sub_form" action="${basePath}/suborder/findSuborderlistPage.html" method="post">
                <input type="hidden" id="pages" name="pages" value="${pages}"/>
                <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
                <input type="hidden" id="status" name="status" value="${status}"/>
                <input type="hidden" name="selfDelivery" value="${selfDelivery}"/>
                <!--订单查询 begin-->
                <div class="goods_cont" style="display:inline">
                    <div class="goods_ln">
                        <div class="goods_n">
                            <span class="definition">收件人姓名:</span>
                            <input class="common_input f158" type="text" id="name" name="name" value="${name}">
                        </div>
                        <div class="goods_n">
                            <span class="definition">创建时间:</span>
                            <input class="common_input f98" type="text" id="starttime" name="starttime" value="${starttime}" readOnly="readOnly" onClick="WdatePicker()"><em>至</em>
                            <input class="common_input f98" type="text" id="endtime" name="endtime" value="${endtime}" readOnly="readOnly" onClick="WdatePicker()">
                        </div>
                        <div class="goods_n">
                            <span class="definition">订单编号:</span>
                            <input class="common_input f158" type="text" id="subOrderId" name="subOrderId" value="${subOrderId}"/>
                        </div>
                    </div>
                    <div class="goods_ln" id="wuliuSearchDiv" <c:if test="${status==1}">style="display:none;"</c:if>>
                        <div class="goods_n">
                            <span class="definition">物流公司:</span>
                            <select class="common_input f98" name="expressType" onchange="express_change()">
                                <option value="">--请选择--</option>
                                <c:if test="${not empty allCompInfoList && allCompInfoList.size() > 0 }">
                                    <c:forEach var="item" items="${allCompInfoList }">
                                        <c:if test="${item.id == expressType }">
                                            <option value="${item.id}" selected>${item.name }</option>
                                        </c:if>
                                        <c:if test="${item.id != expressType }">
                                            <option value="${item.id}">${item.name }</option>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                        <div class="goods_n">
                            <span class="definition">物流单号:</span>
                            <input class="common_input f226" type="text" name="expressNo" value="${expressNo}">
                        </div>
                    </div>
                    <div class="searchbtn" onclick="formSubmit(1);"><a href="javascript:void(0);">查询</a></div>
                </div>
                <!--订单查询 end-->
            </form>

            <div class="goods_list_wrap" name="useFulicoinDiv">
                <c:choose>
                    <c:when test="${result.msgBody!=null}">
                        <div class="goods_lstbox">
                            <c:if test="${status==1}">
                                <div class="allselect">
                                    <span><input class="redio" type="checkbox" id="total" onclick="checkTotal(this);check();">全选</span>
                                    <div class="allbtn" onclick="sendOutAll()"><a href="javascript:void(0);">批量发货</a></div>
		                            <span><input class="redio" type="checkbox" name="selfDelivery" onclick="selfDeliveryChange(this);" <c:if test="${selfDelivery==0}">checked="checked"</c:if>>隐藏自提订单</span>
                                </div>
                            </c:if>
                             <c:if test="${status==2}">
                             <span><input class="redio" type="checkbox" name="selfDelivery" onclick="selfDeliveryChange(this);" <c:if test="${selfDelivery==0}">checked="checked"</c:if>>隐藏自提订单</span>
                            </c:if>
                            <c:forEach items="${result.msgBody}" var="item" varStatus="index">

                                <div class="goods_lst">
                                    <div class="informationwrap">
                                        <h3><c:if test="${status==1}"><input class="redio" type="checkbox" name="ck" onclick="check();" value="${item.subOrderId}"></c:if><span>订单编号：${item.subOrderId}</span><span>创建时间：<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></span><c:if test="${status==1}">
                                            <div class="deliverbtn" onclick="sendOut('${item.subOrderId}')"><a href="javascript:void(0);">发货</a></div>
                                        </c:if><c:if test="${item.status==1}"><label class="send_num">催促发货(<em>${item.urgeNumber}</em>)</label></c:if></h3>

                                        <div class="info_lt">
                                            <ul class="info_lt_list">
                                                <c:forEach items="${item.suborderitemlist}" var="suborderitem" varStatus="statusitem">
                                                    <li>
                                                        <div class="s_imgwrap">
                                                            <div class="img-goods">
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
                                                                <span><a href="javascript:void(0);"><img src="${suborderitem.image}" width="78" height="78" alt="Me-order-img"></a></span>
                                                                <p class="p1" style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;" title="${suborderitem.productName}">${suborderitem.productName}</p>
                                                                    ${suborderitem.itemValues}
                                                            </div>
                                                        </div>
                                                        <strong style="margin-left:20px;">￥${suborderitem.internalPurchasePrice}<!--<c:if test="${suborderitem.useFuliCoin>0}">+${suborderitem.useFuliCoin}内购券</c:if>--></strong>
                                                        <i>×${suborderitem.number}</i>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                        <div class="info_rt">收货信息：${item.orders.address}，${item.orders.name}， ${item.orders.mobile}<br/>
                                            <c:if test="${status==2}"><c:if test="${item.orders.selfDelivery !=1 }">
                                                快递公司：
                                                <c:if test="${not empty allCompInfoList && allCompInfoList.size() > 0 }">
                                                    <c:forEach var="itemExpress" items="${allCompInfoList }">
                                                        <c:if test="${itemExpress.id == item.expressType }">
                                                            ${itemExpress.name }
                                                        </c:if>
                                                    </c:forEach>
                                                </c:if>
                                                <br/>
                                                <c:if test="${item.expressType == '14660000000000000' }">卡券密码：</c:if><c:if test="${item.expressType == '14660000000000001'}">货运号：</c:if><c:if test="${item.expressType != '14660000000000000' && item.expressType != '14660000000000001' }">物流单号：</c:if>${item.expressNo} 
                                            </c:if>
                                           </c:if>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            <c:if test="${status==1}">
                                <div class="allselect">
                                    <span><input class="redio" type="checkbox" id="total" onclick="checkTotal(this);check();">全选</span>
                                    <div class="allbtn" onclick="sendOutAll()"><a href="javascript:void(0);">批量发货</a></div>
                                </div>
                            </c:if>
                            <!-- 分页位置 start-->
                            <wodepageform:PageFormTag pageSize="${result.size}" totalPage="${result.totalPage}" currentPage="${result.page}" url=""/>
                            <!-- 分页位置 end-->
                        </div>
                    </c:when>
                    <c:otherwise><p class="h-result">未查询到符合条件的订单，请修改检索条件重新查询！</p></c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->
<script type="text/javascript">

var jsBasePath='${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_suborder_suborderlist.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>