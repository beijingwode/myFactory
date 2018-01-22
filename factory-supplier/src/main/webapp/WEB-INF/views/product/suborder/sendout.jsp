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
    <link rel="stylesheet" type="text/css" href="<%=static_resources %>css/invoice_entry.css" />
    <script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.min.js"></script>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
	<script type="text/javascript" src="<%=static_resources %>js/areaFnc.js"></script>
</head>
<style>
.bctxt {
    border: 1px solid #ff6161;
}
</style>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
    <div id="content">
        <!--left begin-->
        <div class="left">
            <div class="left_list">
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
                <a href="javascript:void(0);">发货</a>
            </div>
		<form id="sub_form" action="${basePath}/suborder/sendOut.html" method="post">
            <input type="hidden" name="subOrderId" value="${suborder.subOrderId}"/>
            <input type="hidden" name="type" value="${type}"/>
            <input type="hidden" name="gotoType" value="${gotoType}"/>
            <div class="sale_wrap">
                <p class="s_s_fl">发货</p>
                <p class="buzhou">第一步 确认收货信息及交易详情</p>
                <div class="sale_content" style="min-height:0px;">
                    <div class="in_title qling">
                        <span>订单编号：${suborder.subOrderId}</span><span>  买家：${suborder.orders.mobile} </span>
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
                                            <p class="p1" style="width:540px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;"><a href="javascript:void(0);" title="${suborderitem.productName}">${suborderitem.productName}</a></p>
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
                    <div class="in_title qling">
                        <a href="javascript:updateAddress('userAddress_${suborder.subOrderId}');">修改收货地址</a>
                        <span style="float: left; padding:0;">买家收货信息：</span><span id="userAddress_${suborder.subOrderId}" style="width: 600px; height: 38px;overflow: hidden;float: left;" title="${suborder.orders.address}，${suborder.orders.name}， ${suborder.orders.mobile}">${suborder.orders.address}，${suborder.orders.name}， ${suborder.orders.mobile}</span>
                    </div>
                </div>
                <p class="buzhou">第二步 确认发货/退货信息</p>
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
                <p class="buzhou"<c:if test="${suborder.orders.selfDelivery=='1' }"> style="display: none" </c:if>>第三步 选择物流&nbsp;&nbsp;&nbsp;&nbsp;<span><a target="_blank" href="${basePath}/suborder/toSupplier_express.html" style="color: #2b8dff">编辑常用的快递公司</a></span></p>
                <div class="xzl"<c:if test="${suborder.orders.selfDelivery=='1' }"> style="display: none" </c:if>>
                    <ul>
                        <li><span class="s_name">物流名称：</span>
                            <select class="select_s" name="expressType" onchange="express_change()">
                                <option value="0">--请选择--</option> 
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
                        <li><span class="s_name">&nbsp;&nbsp;物流单号:</span><input type="text" name="expressNo" class="input_s" id="expressNo" style="width: 400px"  maxLength="50" /></li>
                    </ul>
                    <div class="clear"></div>
                </div>
                <p class="btn_shopping_s"><a href="JavaScript:void(0);" onclick="formSubmit();" id="sub_button">确认发货</a></p>
            </div>
	</form>
            <!-- 发票录入开始 -->
            <form id="addInvoiceForm" >
            <c:if test="${not empty invoiceApply}">
            <div class="main_right" style="float:left;width:auto;padding-left:10px;color:#6a6a6a;">
	     		<div class="lt_tit">录入发票</div>
	     		<p>请将电子发票链接地址，或者上传纸质发票扫描件(拍照)，以便消费者查看</p>
	     		<div class="rt_con">     			
		     		<ul>
		     			<li><span>抬头：</span><p>${invoiceApply.title}</p>
		     			<li>
		     				<span>发票类型：</span>
							<select name="type">
							  <option value ="0">电子增值税普通发票</option>
							  <option value ="1">电子增值税专用发票</option>
							  <option value="2">纸质发票</option>
							</select>
						</li>
		     			<li><span>发票号：</span><input name="invoice" type="text" value=""></li>
	     			<li><span>销售方：</span><input name="seller" type="text" value=""></li>
	     			<li><span>价税合计：</span><input name="price"   type="text" value=""></li>
	     			<li><span>电子发票链接：</span><input name="electronicInvoice"  type="text" value=""></li>
		     		</ul>
		     		<div class="up">
		     			<div class="up_top"><span>纸质发票扫描件（拍照）</span><a href="javascript:;"  onclick="toUpload();" >上传</a></div>
		     			<div class="up_img"><img  id="invoice_img"   src="<%=static_resources %>images/fapiao_img.png" /></div>
		     			<div class="del_btn"><a href="javascript:;" >删除</a></div>
		     		</div>
	     		</div>
	     		
	     		<input name="suborderid" type="hidden" value="${suborder.subOrderId }">
     			<input name="title" type="hidden" value="${invoiceApply.title}">
     			<input name="paperInvoice" type="hidden" id="paperInvoice" value="${invoice.paperInvoice }">
	     		<div class="btn"><a onclick="addInvoiceForm()" href="javascript:;">保存信息</a></div>
	     		</div>
	     	</c:if>
			</form>
	     	
            <!-- 发票录入结束 -->
            <div style="display: none;">
		          <input type="file" id="uploadFile" name="file" onchange="fileUpload()" />
	        </div>
        </div>
        <!--right end-->
    </div>
<!-- <div id="content">

</div> -->
<!--content end-->

<!--修改收货地址弹出框 begin-->
<div class="popup_bg"></div>
<div class="sort_popup" id="change_deliver" style="display:none;">
    <div class="popup_title">
        <span id="">修改收货地址</span>
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

<script type="text/javascript">
var jsBasePath='${basePath}';
var jsProvinceName="${supplier.provinceName}";
var jsCityName="${supplier.cityName}";
var jsSelfDelivery="${suborder.orders.selfDelivery}";
function jsOption(){
	var html='<option value="0">--请选择--</option>';
	<c:forEach var="item" items="${allCompInfoList }">
	html+='<option value="${item.id }">${item.name }</option>';     
	</c:forEach> 
	$("select[name='expressType']").html(html);
}
</script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>
<script  type="text/javascript">
function toUpload() {
	$("#uploadFile").click();
}
var jsSupplierId = '${supplierId}';
var jsBasePath = '${basePath}';
function addInvoiceForm() {
	$.ajax({
	      type: "POST",
	      url:  jsBasePath+"/invoice/ajaxAddInvoice",
	      data:$('#addInvoiceForm').serialize(),
	      success: function(ret){
	        if(ret.success){
	        	alert("发票录入成功");
	        }else{
	        	alert(ret.msg);
	        }
	      }
	    });
}
function removeImg() {
	$(".up_img img").attr("src","<%=static_resources %>images/fapiao_img.png");
	$("#paperInvoice").val("");
}
function fileUpload() {
		 var elementIds=["flag"]; //flag为id、name属性名
	    $.ajaxFileUpload({
	        url: jsBasePath+'/upload/pic.json?folder='+jsSupplierId,
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: "uploadFile", // 上传文件的id、name属性名
	        elementIds:elementIds,
	        dataType: 'json', //返回值类型，一般设置为json、application/json	       
	        success: function(data, status){
	        	if(data.success){
	        		var imgsrc = data.data[0].original;
	        		if(imgsrc.indexOf("http://") != 0) {
	        			imgsrc = "http://"+imgsrc;
	        		}
						$("#invoice_img").attr("src",imgsrc);
						$("#paperInvoice").val(imgsrc);
	        	}
	        },
	        error: function(data, status, e){ 
	            showInfoBox(e);
	        }
	    });
}
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_suborder_sendout.js"></script>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>