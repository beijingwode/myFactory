<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/transaction_management.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/wxGetUid.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/supplier_info.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/nochange_font.js"></script>
<title>我的</title>
<script>
	var jsBasePath = '<%=basePath%>';
</script>
</head>
<body>
<div class="main_box">
	<input type="hidden" id="shopID">
	<div class="wd_top">
    	<dl>
        	<dt id="avatar"><img src="<%=static_resources %>images/shop_dt.png" /></dt>
            <dd id="userName"></dd>
            <dd id="shopName"></dd>
        </dl>
        <span id="chooseShop" onclick="chooseShop()">选择店铺</span>
    </div>
    <div class="my_box">
    <div class="my">
    	<a href="javascript:go2UserInfo();">
        	<dl>
            	<dt><img src="<%=static_resources %>images/personal_information_nor.png" /></dt>
                <dd>个人信息</dd>
            </dl>
        </a>
    </div>
    <div class="my" style="border:none;">
        <a href="javascript:go2Setsafe();">
            <dl>
                <dt><img src="<%=static_resources %>images/security_settings_down.png" /></dt>
                <dd>安全设置</dd>
            </dl>
        </a>
     </div>
    </div>
    <div class="sz_tit">设置</div>
    <div class="sz_con">
    	<ul>
    		<li class="li3"><a href="<%=basePath%>supplierExpress.html">设置常用快递</a></li>
        	<li class="li1"><a href="http://api.wd-w.com/help_center.htm">帮助中心</a></li>
            <li class="li2"><a href="<%=basePath%>about_us.html">关于我们</a></li>
        </ul>
    </div>
    <input type="file" id="fileInput" style="display: none" accept="image/*" onchange="updateAvatar(this);">
</div>

<div class="wd-theme-popover-mask" style="display: none"></div>
<div class="wd-theme-popover"  style="display: none">
    <div class="wd-theme-tit">请您选择</div>
    <ul>
    	<li></li>
        <li></li>
    </ul>
</div>
</body>
</html>