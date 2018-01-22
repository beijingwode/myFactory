<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
if(request.getServerPort() != 80 && request.getServerPort() != 443) {
	path=":"+request.getServerPort()+path;
}
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name = "format-detection" content = "telephone=no">
<title>我的福利-添加收货地址</title>

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/newAddress.js?3"></script>
<script type="text/javascript" src="<%=static_resources %>js/areaFnc.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/areaProvince.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var id = '${id}';
	var orderType='${orderType}';
	var specificationsId='${specificationsId}';
	var productId='${productId}';
	var quantity='${quantity}';
	var partNumbers='${partNumbers}';
	var backNum='${backNum}';
</script>
</head> 

<body>
<div class="main-cont" id="main-cont">
	
    <div class="main-box" style="position: absolute;top: 0px;">
        <div class="ads_con">
        	<ul>
            	<li><span>收货人姓名：</span><input type="text" value="${sa.name }" id="name" /></li>
                <li><span>联系电话：</span><input type="text" value="${sa.phone }" id="phone" /></li>
                <li><span>所在地区：</span>
                	<c:if test="${sa.provinceName!=null}"><input type="text" value="${sa.provinceName} ${sa.cityName} ${sa.areaName}" id="area" onclick="go2area();" readonly="readonly"/></c:if>
                	<c:if test="${sa.provinceName ==null}"><input type="text" value="" id="area" onclick="go2area();" readonly="readonly"/></c:if>
                	<input type="hidden" value="${sa.aid}" id="aid"/>
                </li>
                <li><span>详细地址：</span><input type="text" value="${sa.address}" id="address" /></li>
            </ul>
        </div>
        <div id="save"><span>保存</span></div> 
    
    </div>
</div>
<div class="thickdiv" ></div>
<div class="thickbox" >
    <div class="thickcon">
 		<div class="region">选择省份</div>
 		<div class="tab_box">
        <table width="100%" cellspacing="0" cellpadding="0"> 
        	 <tr> 
            	<td>北京市</td> 
                <td>河北省</td>
                <td>天津市</td>
            </tr>
            <tr> 
            	<td>内蒙古自治区内蒙古自治区</td> 
                <td>河北省</td>
                <td>天津市</td>
            </tr>
            <tr> 
            	<td>北京市</td> 
                <td>河北省</td>
                <td>天津市</td>
            </tr> 
        </table>
        </div>	
    </div>
    <a href="javascript:void(0)" id="closeBox" class="thickclose" >×</a>
</div>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>