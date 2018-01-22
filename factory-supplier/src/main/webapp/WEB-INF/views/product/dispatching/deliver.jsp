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
<script type="text/javascript" src="<%=static_resources %>js/areaFnc.js"></script>
<!--header end-->

<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
            <c:if test="${userSession.type != 3 || userSession.hasAuth('address')}">
                <li class="curr"><a href="${basePath}/shippingAddress/todeliver.html">发货地址管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('suborder')}">
                <li><a href="${basePath}/suborder/gotoSuborderlist.html">发货</a></li>
            </c:if>
            
            <li><a href="${basePath}/shippingAddress/freight_templates.html?ty=1&isAudit=0">运费模板</a></li>
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
            <a href="${basePath}/shippingAddress/todeliver.html">发货地址管理</a>
        </div>
        <div class="sale_wrap">
            <div class="add_deliver_wrap">
                <form id="sub_form_resetpwd" action="${basePath}/shippingAddress/save.html" method="post">
                    <h2><strong>添加一个发货地址</strong><span>电话号码、手机号码选填一项，备注和公司名称为可填项，其余均为必填项</span></h2>
                    <div class="deliver_box">
                        <span class="title"><b class="xred">*</b>联系人：</span>
                        <input class="common_input f158" onkeyup="ValidateValue(this)" type="text" id="name" name="name"
                               value="" ismust="1" maxlength="10">
                        <div class="h-error" id="name1" name="error" style="display: none">联系人请填写中文或英文！</div>
                    </div>
                    <div class="deliver_box">
                        <span class="title"><b class="xred">*</b>所在地区：</span>
                        <select class="common_input f98" id="provinceName" ismust="1"
                                onChange="provinceOnchange(this);"></select><span class="city">省</span>
                        <select class="common_input f98" id="cityName" ismust="1" onChange="townOnchange(this);">
                            <option value='-1'>--请选择--</option>
                        </select><span class="city">市</span>
                        <input type="hidden" id="province" name="provinceName" value=""/>
                        <input type="hidden" id="city" name="cityName" value=""/>
                        <div class="h-error" id="province1" name="error" style="display: none">所在地区不能为空！</div>
                    </div>
                    <div class="deliver_box">
                        <span class="title"><b class="xred">*</b>详细地址：</span>
                        <textarea class="address" name="address" style="resize: none;" id="address"
                                  placeholder="不需要重复填写省市" ismust="1" maxlength="50"></textarea>
                        <div class="h-error" id="address1" name="error" style="display: none">详细地址不能为空！</div>
                    </div>
                    <div class="deliver_box">
                        <span class="title"><b class="xred">*</b>邮政编码：</span>
                        <input class="common_input f158" type="text" name="aid" id="aid" value="" ismust="1"
                               maxlength="6" onkeyup="value=value.replace(/[^\d]/g,'') "
                               onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))">
                        <div class="h-error" id="aid1" name="error" style="display: none">邮政编码应为6位数字！</div>
                    </div>
                    <div class="deliver_box">
                        <span class="title">电话号码：</span>
                        <input class="common_input f158" type="text" name="tel" id="tel" value="" maxlength="15"
                               onkeyup="this.value=this.value=this.value.replace(/[^0-9\-]/gi,'')"
                               onafterpaste="this.value=this.value=this.value.replace(/[^0-9\-]/gi,'')">
                        <div class="h-error" id="tel1" name="error" style="display: none">电话或手机请选填一个！</div>
                    </div>
                    <div class="deliver_box">
                        <span class="title">手机号码：</span>
                        <input class="common_input f158" type="text" name="phone" id="phone" value="" maxlength="11">
                        <div class="h-error" id="phone1" name="error" style="display: none">电话或手机请选填一个！</div>
                        <div class="h-error" id="phone2" name="error" style="display: none">手机号格式不正确！</div>
                    </div>
                    <div class="deliver_box">
                        <span class="title">公司名称：</span>
                        <input class="common_input f158" type="text" name="companyname" id="companyname" value=""
                               maxlength="20">
                    </div>
                    <div class="deliver_box">
                        <span class="title">备注：</span>
                        <textarea class="comment" name="comments" id="comments" style="resize: none;"
                                  maxlength="100"></textarea>
                    </div>
                    <div class="addressbtn" onclick="_submit();"><a href="javascript:void(0);">添加</a></div>
                    <div class="h-seccess" id="cg" style="display: none">添加成功！</div>
                    <div class="h-failed" id="sb" style="display: none">添加失败！</div>
                    <input class="common_input f158" type="hidden" name="id" id="id" value="">
                </form>
            </div>
            <div class="address_list">
                <ul>
                    <li class="title">
                        <span class="Li01">发货地址</span>
                        <span class="Li02">退货地址</span>
                        <span class="Li03">联系人</span>
                        <span class="Li04">所在地区</span>
                        <span class="Li05">详细地址</span>
                        <span class="Li06">邮政编码</span>
                        <span class="Li07">电话号码</span>
                        <span class="Li08">手机号码</span>
                        <span class="Li09">公司名称</span>
                        <span class="Li10">备注</span>
                        <span class="Li11">操作</span>
                    </li>
                    <c:forEach var="item" items="${supplierAddress}">
                        <li id="${item.id}">
                            <span class="Li01"><input class="radio" type="radio" name="send"
                                                      onclick="setdefault(${item.id},'1');"
                            <c:if test="${item.send eq 1}"> checked="checked"</c:if> value="${item.send}">默认</span>
                            <span class="Li02"><input class="radio" type="radio" name="returned"
                                                      onclick="setdefault(${item.id},'2');"
                            <c:if test="${item.returned eq 1}"> checked="checked"</c:if>
                                                      value="${item.returned}">默认</span>
                            <span class="Li03">${item.name}</span>
                            <span class="Li04" title="${item.provinceName}${item.cityName}${item.areaName}"
                                  style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item.provinceName}${item.cityName}${item.areaName}</span>
                            <span class="Li05" title="${item.address}"
                                  style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item.address}</span>
                            <span class="Li06">${item.aid}</span>
                            <span class="Li07">${item.tel}</span>
                            <span class="Li08">${item.phone}</span>
                            <span class="Li09" title="${item.companyname}"
                                  style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item.companyname}</span>
                            <span class="Li10" title="${item.comments}"
                                  style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item.comments}</span>
                            <span class="Li11"><p class="edit" onclick="editajax(${item.id});"><a
                                    href="javascript:void(0);">编辑</a></p><p class="delete"
                                                                            onclick="delcategory(${item.id});"><a
                                    href="javascript:void(0);">删除</a></p></span>
                        </li>
                    </c:forEach>
                </ul>
            </div>
            <p class="remark" id="dzsize">您还可以添加${20-size}条地址</p>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->
<!--删除确认 begin-->
<div class="popup_bg"></div>
<div class="shop_popup" id="shop_popup_delete">
    <input type="hidden" id="deleteid" value="">
    <div class="popup_title" onclick="delcategory2();">
        <span>删除确认</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
    <div class="popup_cont">
        <div class="popup_txt">请确认是否删除选择地址？</div>
        <div class="popup_btn">
            <a href="javascript:void(0);" onclick="deleteajax();">确认</a>
            <a id="dcansel" href="javascript:void(0);" onclick="delcategory2();">取消</a>
        </div>
    </div>
</div>
<!--删除确认 end-->

<script type="text/javascript">
var jsBasePath = '${basePath}';
var jsProvinceName = "${supplier.provinceName}";
var jsCityName = "${supplier.cityName}";
var jsSize = "${20-size}";
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_dispatching_deliver.js"></script>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>
