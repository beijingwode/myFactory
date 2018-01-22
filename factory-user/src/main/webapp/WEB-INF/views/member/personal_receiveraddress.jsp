<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-我的收货地址</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/areaFnc.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/receiverAddress.js"></script>
<body>
<!--top begin-->
<%@ include file="../common/header_03.jsp"%>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
	<%@ include file="menu.jsp"%>
<!--right content-->
	<div class="Me_content">
    	<div class="on_title">
        	<span class="onlt">收货地址</span>
        </div>
        <div class="receiver_list">
            <ul>
            	<c:forEach var="shippingAddress" items="${saList}">
	                <li class="ads" id="${shippingAddress.id}" value="${shippingAddress.aid}">
	                	<div class="r_icon">
	                		<c:if test="${shippingAddress.send==1}">
	                			<img src="<%=basePath %>images/address.jpg" width="53" height="53" alt="address">
	                		</c:if>
	                	</div> 
	                    <p>${shippingAddress.name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;收</p>
	                    <p>${shippingAddress.provinceName}
	                    <c:if test="${shippingAddress.cityName!='县' && shippingAddress.cityName!='市辖区'}">
	                      ${shippingAddress.cityName}
	                     </c:if>
	                        ${shippingAddress.areaName}</p>
	                    <p class="had03">${shippingAddress.address}</p>
	                    <p>${shippingAddress.phone}</p>                    
	                </li>
               	</c:forEach>
               	<c:if test="${fn:length(saList)<9}">
	                <li class="bgcolor">
	                	<p>增加新地址</p>              
	                </li>
                </c:if>
            </ul>
        </div>
    </div>
<!--right contont end-->
<div class="clear:after"></div>
</div>
<div class="clear"></div>
<!--footer begin-->
   <%@ include file="../common/footer.jsp" %>
<!--footer end-->
<!--增加新地址弹框 begin-->
<div class="popup_bg"></div>
<div class="address_popup" id="address_popup">
	<div class="popup_title">
    	<span>增加新地址</span>
        <label><img src="<%=basePath %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
    <div class="popup_cont">	
		<div class="popup_address_ln">
        	<span class="name"><i class="red">*</i>收货人：</span>
            <input class="common_input p158" type="text" id="receiver" maxlength="10"/>
            <p class="r_error">请输入收货人姓名</p>
        </div>
        <div class="popup_address_ln">
        	<span class="name"><i class="red">*</i>省市区：</span>
            <div class="Info_Select">
            	<input type="text" id="provinceNo" value="${user.province}" style="display: none;"/>
                <select id="province">                                	
                    <option value="0">省</option>
                </select>
            </div>
            <div class="Info_Select">
            	<input type="text" id="cityNo" value="${user.city}" style="display: none;"/>
                <select id="city">
                    <option value="0">市</option>
                </select>
            </div>
            <div class="Info_Select">
            	<input type="text" id="districtNo" value="${user.district}" style="display: none;"/>
                <select id="district">
                    <option value="0">区/县</option>
                </select>
            </div>
            <input type="hidden" id="allSelect"/>
            <p class="s_error">请选择省、市、区</p>
        </div> 
        <div class="popup_address_ln">
        	<span class="name"><i class="red">*</i>详细地址：</span>
            <input class="common_input p332" type="text" id="address" maxlength="48"/>
            <p class="a_error">请输入详细地址</p>
        </div>
        <div class="popup_address_ln">
        	<span class="name"><i class="red">*</i>手机号码：</span>
            <input class="common_input p158" type="text" id="phone" maxlength="11"/>
            <p class="p_error">请输入手机号码</p>
        </div>
        <div class="popup_dftaddress">
            <input class="radio" type="checkbox" id="default" /><strong>设为默认地址</strong>
        </div>
        <div class="clear"></div>
        <div class="popup_btn">
            <div class="popupbtn"><a href="javascript:void(0);" id="s_submit">保存</a></div>
            <div class="popupbtn" id="cansel"><a href="javascript:void(0);">取消</a></div>
        </div>
	</div>
</div>
<!--增加新地址弹框 end-->
<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
  <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>
