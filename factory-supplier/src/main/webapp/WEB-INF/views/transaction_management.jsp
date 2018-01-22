<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Cache-Control" contnt="no-cache, no-store, must-revalidate">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>交易管理</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/transaction_management.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/wxGetUid.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/transaction_management.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/nochange_font.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
</head> 

<body>
<div class="main_box">
	<input type="hidden" id="pageNum" value="0">
	<div class="main_one">
    	<ul>
        	<li><a href="javascript:go2OrderList(0);" id="nonPaymentCount"><em></em><span>待付款</span></a></li>
            <li><a href="javascript:go2OrderList(1);" id="unfilledCount"><em></em><span>待发货</span></a></li>
            <li style="border:none;"><a href="javascript:go2OrderList(5);" id="refundCount"><em></em><span>维权单</span></a></li>
        </ul>
        <ul style="border-bottom:none;">
        	<li><a href="javascript:go2OrderList(2);" id="notReceivingCount"><em></em><span>已发货</span></a></li>
            <li><a href="javascript:go2OrderList(4);" id="completeCount"><em></em><span>已完成</span></a></li>
            <li style="border:none;"><a href="javascript:go2OrderList(-1);" id="closeCount"><em></em><span>已关闭</span></a></li>
        </ul>
    </div>
    <div class="main_two">
   		 <p>急需处理的订单</p>
    </div>
    <div class="bottom_con">没有更多内容了</div>
</div>
<%@ include file="/commons/newAlertMessage.jsp" %>
</body>
</html>
