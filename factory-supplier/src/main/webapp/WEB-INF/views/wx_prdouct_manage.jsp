<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("basePath", basePath);
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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/prompt.css" />
<title>商品管理</title>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/wxGetUid.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/product_manage.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/nochange_font.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
</head>
<body>
<body>
<div class="main_box" style="padding-bottom:42px;">
	<input type="hidden" id="pageNum" value="0">
	<div class="main_top">
    	<ul>
        	<li class="thisOne"><a href="javascript:void(0);">在售中</a></li>
            <li><a href="javascript:void(0);">待上架</a></li>
            <li><a href="javascript:void(0);">待审核</a></li>
            <li style="border:none;"><a href="javascript:void(0);">有问题</a></li>
        </ul>
        <span id="batch">批量下架</span>
    </div>
    <div class="commodity_con">
    	<ul>
        </ul>
    </div>
    <div class="bottom_con">没有更多内容了</div>
    <div class="bm_btn"><a href="javascript:go2batch();">确认操作</a></div>
</div>
<div class="recharge_money"  style="display: none">
     <div class="theme-tit"></div>
     <div class="theme-input"><input type="text" id="content" value=""/></div>
     <div class="theme-popbod">
        <a href="javascript:go2Close();" >取消</a>  
        <a href="javascript:go2Sure();" style="border:none;" id="sure">确定</a>
     </div>
</div>
<div class="add_money-mask" style="display: none"></div>
<%@ include file="/commons/newAlertMessage.jsp" %>
</body>
</html>