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
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/ace/get_the_success.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/acticity/prize_take_information.js"></script>
<title>奖品领取</title>
</head>
<script type="text/javascript">
var jsBasePath = '<%=basePath%>';
var prizeId = '${prizeId}'
</script>
<body style="background:#861215">
<div class="main-cont" id="main-cont">   
    <div class="main-box">
    	<div class="con1">
        	<img src="<%=static_resources %>images/ace/goods_receiving_information1.png" />  
        	<ul>
        		<li><span class="span1"></span><input type="text" value="" id="address" name="address" placeholder="收货地址" /></li>
        		<li><span class="span2"></span><input type="text" value="" id="name" name="name" placeholder="收件人" /></li>
        		<li><span class="span3"></span><input type="text" value="" id="mobile" name="mobile" placeholder="电话" /></li>
        		<li><span class="span4"></span><input type="text" value="" id="email" name="email" placeholder="收到邮件的邮箱" /></li>
        		<li>（该信息仅用于验证您的身份）</li>
        	</ul>
        	<p id="error"></p>
        </div>
        <div class="con3 con4">
        	<img src="<%=static_resources %>images/ace/goods_receiving_information2.png" />  
        	<a href="javascript:takePrize();">提交信息</a> 
        	<p>福袋商品由“我的福利”提供</p>
        </div>       
    </div>
     
</div>
</body>

</html>
