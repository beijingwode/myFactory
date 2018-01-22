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
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/public.css"/>
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/attract_investment_ewm.css"/>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/generate_qr_code.js"></script>
<title>生成二维码</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var userId = ${userId}
</script>
</head>
<body>
<input type="hidden" value="${supplierId}" id="supplierId">
<div class="main-cont" id="main-cont" >
	<div class="main-box" style="margin-top:10px;background:#fff;">
		<div class="qr_code_box">
			<div class="com_name">${comName}</div>
			<div class="leixing">二维码类型</div>
			<select id="type">			  
			  <option value ="0">先关注再注册二维码</option>
			  <option value ="1">直接注册二维码</option>
			</select>
			<div class="time_end_set">
				<div class="set_top"><p class="p1"><span>跳转链接</span></p><p class="p2"><span></span><em></em></p></div>
				<div class="set_bottom">
					<input type="text" id="targetUrl" value="" />
				</div>
			</div>
			<div class="qr_code_btn"><a href="javascript:boundQRcode();">生成二维码</a></div>
			
		</div>		
	</div>
</div>
</body>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
//跳转链接开关
$(".set_top .p2 em").toggle(function(){
		$(this).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/time_end_set2.png) no-repeat","background-size":"38px 20px"});
		$(".set_bottom").show();
	},function(){
		$(this).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/time_end_set1.png) no-repeat","background-size":"38px 20px"});
		$(".set_bottom").hide();
	}
);
</script>
</html>
