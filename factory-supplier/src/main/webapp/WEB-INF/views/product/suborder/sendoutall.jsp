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
	<script type="text/javascript" src="<%=static_resources %>js/areaFnc.js"></script>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<form id="sub_form" action="${basePath}/suborder/sendOutAll.html" method="post">
    <input type="hidden" name="type" value="${type}"/>
    <input type="hidden" name="gotoType" value="${gotoType}"/>
    <div id="content">
        <!--left begin-->
        <div class="left">
            <div class="left_list">
                <c:choose>
                    <c:when test="${gotoType=='suborderlist'}">
                        <ul>
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('address')}">
                                <li><a href="${basePath}/shippingAddress/todeliver.html">发货地址管理</a></li>
                            </c:if>
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('suborder')}">
                                <li class="curr"><a href="${basePath}/suborder/gotoSuborderlist.html">发货</a></li>
                            </c:if>
                            <li><a href="${basePath}/shippingAddress/freight_templates.html?ty=1">运费模板</a></li>
            				<li><a href="${basePath}/suborder/toSupplier_express.html?blank=1">常用快递公司</a></li>
                        </ul>
                    </c:when>
                    <c:otherwise>
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
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        <!--left end-->

        <!--right begin-->
        <div class="right">
            <div class="position">
                <span>您所在的位置：</span>
                <a href="javascript:void(0);">商家中心</a><em>></em>
                <c:choose>
                    <c:when test="${gotoType=='suborderlist'}">
                        <a href="${basePath}/shippingAddress/todeliver.html">配送管理</a><em>></em>
                    </c:when>
                    <c:otherwise>
                        <a href="${basePath}/suborder/gotoSelllist.html">订单管理</a><em>></em>
                    </c:otherwise>
                </c:choose>
                <a href="javascript:void(0);">批量发货</a>
            </div>
            <div class="sale_wrap">
                <p class="s_s_fl">批量发货</p>
                <p class="buzhou">第一步 确认发货/退货信息</p>
                <div class="shipping_address">
                    <c:choose>
                        <c:when test="${supplierAddressSend.id==null||supplierAddressSend.id==''}">
                            <p><a href="javascript:addAddress('sendAddress');">添加发货地址</a><span style="float: left;">我的发货地址：</span><span id="sendAddressTemp" style="width: 700px; height: 20px;overflow: hidden;float: left;" title=""></span></p><input type="hidden" id="sendAddress" name="sendAddress" value=""/>
                        </c:when>
                        <c:otherwise>
                            <p><a href="javascript:updateAddress('sendAddress');">修改发货地址</a><span style="float: left;">我的发货地址：</span><span id="sendAddressTemp" style="width: 700px; height: 20px;overflow: hidden;float: left;"
                                                                                                                                           title="${supplierAddressSend.provinceName}${supplierAddressSend.cityName}${supplierAddressSend.address}，${supplierAddressSend.aid}，${supplierAddressSend.name}，<c:choose><c:when test="${supplierAddressSend.phone==null||supplierAddressSend.phone==''}">${supplierAddressSend.tel}</c:when><c:otherwise>${supplierAddressSend.phone}</c:otherwise></c:choose>">${supplierAddressSend.provinceName}${supplierAddressSend.cityName}${supplierAddressSend.address}，${supplierAddressSend.aid}，${supplierAddressSend.name}，<c:choose><c:when
                                    test="${supplierAddressSend.phone==null||supplierAddressSend.phone==''}">${supplierAddressSend.tel}</c:when><c:otherwise>${supplierAddressSend.phone}</c:otherwise></c:choose></span></p><input type="hidden" id="sendAddress" name="sendAddress"
                                                                                                                                                                                                                                    value="${supplierAddressSend.provinceName}${supplierAddressSend.cityName}${supplierAddressSend.address}，${supplierAddressSend.aid}，${supplierAddressSend.name}，<c:choose><c:when test="${supplierAddressSend.phone==null||supplierAddressSend.phone==''}">${supplierAddressSend.tel}</c:when><c:otherwise>${supplierAddressSend.phone}</c:otherwise></c:choose>"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <p class="buzhou">第二步 选择物流&nbsp;&nbsp;&nbsp;&nbsp;<span><a target="_blank" href="${basePath}/suborder/toSupplier_express.html" style="color: #2b8dff">编辑常用的快递公司</a></span></p>
                <div class="xzl">
                    <ul>
                        <li><span class="s_name">物流名称：</span>
                            <select class="select_s" name="expressType" onchange="express_change()">
                                <option value="">--请选择--</option>                           
                                <c:if test="${empty CompInfoList}">
                                    <c:forEach var="item" items="${allCompInfoList }">
                                        <option value="${item.id }">${item.name }</option>
                                    </c:forEach>                             
                                </c:if>                   
                                <c:if test="${not empty CompInfoList && CompInfoList.size() > 0 }">
                                    <c:forEach var="item" items="${CompInfoList }">
                                        <option value="${item.expressId }">${item.name }</option>
                                    </c:forEach>
                                <option value="1">--全部--</option>
                                </c:if>
                            </select>
                        </li>
                    </ul>
                    <div class="clear"></div>
                </div>
                <c:forEach items="${suborderlist}" var="suborder" varStatus="index">

                    <div class="sale_content" style="min-height:0;">
                        <input type="hidden" name="subOrderId" value="${suborder.subOrderId}"/>
                        <div class="in_title qling">
                            <span>订单编号：${suborder.subOrderId} </span><span>  买家：${suborder.orders.mobile}</span>
                        </div>
                        <ul class="sale_infomation_list">
                            <c:forEach items="${suborder.suborderitemlist}" var="suborderitem" varStatus="statusitem">
                                <li>
                                    <div class="infowrap">
                                        <div class="shp_info " style="width:640px;">
                                            <div class="s_imgwrap" style="width:630px;">
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
                                                <p class="p1" style="width:540px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;" title="${suborderitem.productName}"><a href="javascript:void(0);">${suborderitem.productName}</a></p>
                                                <div style="width: 450px;">
                                                        ${suborderitem.itemValues}
                                                </div>
                                            </div>
                                        </div>
                                        <span class="w80">￥${suborderitem.internalPurchasePrice}+${suborderitem.companyTicket/suborderitem.number}券</span>
                                        <div class="belong_type w52">
                                            <p>×${suborderitem.number}</p>
                                        </div>

                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                         <div class="down_con">
                            <p class="change_a"><a href="javascript:void(0);"></a></p>
                            <div class="add_dz"><a href="javascript:updateAddress('userAddress_${suborder.subOrderId}');">修改收货地址</a>
                                <span style="float: left; padding:0;">买家收货信息：</span><span id="userAddress_${suborder.subOrderId}" style="width: 600px; height: 20px;overflow: hidden;float: left;" title="${suborder.orders.address}，${suborder.orders.name}， ${suborder.orders.mobile}"> ${suborder.orders.address}，${suborder.orders.name}， ${suborder.orders.mobile}</span></div>
                            <ul <c:if test="${suborder.orders.selfDelivery=='1' }"> style="display: none" </c:if>>
                                <li><span class="s_name">物流单号：</span><input type="hidden" name="selfDelivery" value="${suborder.orders.selfDelivery}"><input type="text" name="expressNo" class="input_s" style="width: 450px" maxLength="50" onchange="onchangeResult(this)" onkeyup="value=value.replace(/[^\w\-\/]/ig,'')" onkeydown='if(event.keyCode==13) return false;'/></li>
                            </ul>
                            <div class="clear"></div>
                        </div>
                        <input type="hidden" name="suborder_result" value="${suborder.subOrderId}|_|"/>
                    </div>

                </c:forEach>
                <p class="btn_shopping_s"><a href="JavaScript:void(0);" onclick="formSubmit();" id="sub_button">确认批量发货</a></p>
            </div>
        </div>
        <!--right end-->
    </div>
</form>
<!--content end-->

<!--修改收货地址弹出框 begin-->
<div class="popup_bg"></div>
<div class="sort_popup" id="change_deliver" style="display:none;">
    <div class="popup_title">
        <span>修改收货地址</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="cancelButton('change_deliver');"></label>
    </div>
    <div class="sort_popup_cont">
        <input type="hidden" id="updateTypename" value=""/>
        <div class="change_ln hmartop">
            <span><b class="out">*</b>收货地址：</span>
            <select class="select_input c77" id="province" name="province" onChange="provinceOnchange(this);">
                <option value="-1">-请选择-</option>
            </select>
            <select class="select_input c77" id="town" name="town" onChange="townOnchange(this);">
                <option value="-1">-请选择-</option>
            </select>
            <select class="select_input c77" id="county" name="county" onChange="countyOnchange(this);">
                <option value="-1">-请选择-</option>
            </select><br><br>
            <input class="common_input c248 marlt" style="margin-left:94px;" type="text" id="detailaddress" name="detailaddress" value="" maxLength="50">
        </div>
        <div class="change_ln">
            <span><b class="out">*</b>联&nbsp;系&nbsp;人&nbsp;:&nbsp;&nbsp;&nbsp;</span>
            <input class="common_input c248" type="text" id="username_alert" value="" maxLength="20">
        </div>
        <div class="change_ln">
            <span><b class="out">*</b>电话号码：</span>
            <input class="common_input c248" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" id="mobile_alert" value="" maxLength="11">
        </div>
        <div class="clear"></div>
        <div class="popup_btn chmarbtn">
            <a href="javascript:void(0);" onclick="updateAddressSubmit();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('change_deliver');">取消</a>
        </div>
    </div>
</div>
<!--修改收货地址弹出框 end-->
<!--修改收货地址2弹出框 begin-->
<div class="shop_popup" id="change_address">
    <div class="popup_title">
        <span id="update_span">修改收货地址</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="cancelButton('change_address');"></label>
    </div>
    <div class="popup_cont">
    </div>
</div>
<!--修改收货地址2弹出框 end-->
<!--新增退货地址弹出框 begin-->
<div class="shop_popup" id="add_receive">
    <input id="sendOrreturned" type="hidden" value=""/>
    <div class="popup_title">
        <span id="reciveSpan"></span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="cancelButton('add_receive');"></label>
    </div>
    <div class="popup_cont">
        <p class="grayzt">电话号码、手机号码选填一项，备注和公司名称为可填项，其余均为必填项</p>
        <div class="add_itembox">
            <div class="item_name"><b>*</b>联系人：</div>
            <input class="common_input c160" type="text" id="name" name="name" value="" ismust="1" maxlength="10">
            <div class="h-error" id="name1" name="error" style="display: none">联系人请填写中文或英文！</div>
        </div>
        <div class="add_itembox">
            <div class="item_name"><b>*</b>所在地区：</div>
            <select class="common_input c168" id="provinceName" ismust="1" onChange="provinceOnchange1(this);"></select><span class="city">省</span>
            <select class="common_input c168" id="cityName" ismust="1" onChange="townOnchange1(this);">
                <option value='-1'>--请选择--</option>
            </select><span class="city">市</span>
            <input type="hidden" id="province" name="provinceName" value=""/>
            <input type="hidden" id="city" name="cityName" value=""/>
            <div class="h-error" id="province1" name="error" style="display: none">所在地区不能为空！</div>
        </div>
        <div class="add_itembox">
            <div class="item_name"><b>*</b>详细地址：</div>
            <textarea class="textarea_input" name="address" style="resize: none;" id="address" placeholder="不需要重复填写省市" ismust="1" maxlength="50"></textarea>
            <div class="h-error" id="address1" name="error" style="display: none">详细地址不能为空！</div>
        </div>
        <div class="add_itembox">
            <div class="item_name"><b>*</b>邮政编码：</div>
            <input class="common_input c160" type="text" name="aid" id="aid" value="" ismust="1" maxlength="6" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))">
            <div class="h-error" id="aid1" name="error" style="display: none">邮政编码应为6位数字！</div>
        </div>
        <div class="add_itembox">
            <div class="item_name">电话号码：</div>
            <input class="common_input c128" type="text" name="tel" id="tel" value="" maxlength="15" onkeyup="this.value=this.value=this.value.replace(/[^0-9\-]/gi,'')" onafterpaste="this.value=this.value=this.value.replace(/[^0-9\-]/gi,'')">
            <div class="h-error" id="tel1" name="error" style="display: none">电话或手机请选填一个！</div>
        </div>
        <div class="add_itembox">
            <div class="item_name">手机号码：</div>
            <input class="common_input c160" type="text" name="phone" id="phone" value="" maxlength="11">
            <div class="h-error" id="phone1" name="error" style="display: none">电话或手机请选填一个！</div>
            <div class="h-error" id="phone2" name="error" style="display: none">手机号格式不正确！</div>
        </div>
        <div class="add_itembox">
            <div class="item_name">公司名称：</div>
            <input class="common_input c160" type="text" name="companyname" id="companyname" value="" maxlength="20">
        </div>
        <div class="add_itembox">
            <div class="item_name">备注：</div>
            <textarea class="textarea_input" name="comments" id="comments" style="resize: none;" maxlength="100"></textarea>
        </div>
        <div class="clear"></div>
        <div class="popup_btn" onclick="_submit1();"><a href="javascript:void(0);">添加</a></div>
        <div class="h-seccess" id="cg" style="display: none">添加成功！</div>
        <div class="h-failed" id="sb" style="display: none">添加失败！</div>
        <input type="hidden" name="id" id="id" value="">
    </div>
</div>
<!--新增退货地址弹出框 end-->
<!-- 提示 -->
<div class="sort_popup" id="popup_alert">
    <div class="popup_title">
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
<!-- 提示 -->

<script type="text/javascript">
    var jsBasePath='${basePath}';
    var jsProvinceName="${supplier.provinceName}";
    var jsCityName="${supplier.cityName}";
    function jsOption(){
    	
    	var html='<option value="0">--请选择--</option>';
		<c:forEach var="item" items="${allCompInfoList }">
		html+='<option value="${item.id }">${item.name }</option>';
		</c:forEach> 
        $("select[name='expressType']").html(html);
    }
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_suborder_sendoutall.js"></script>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>