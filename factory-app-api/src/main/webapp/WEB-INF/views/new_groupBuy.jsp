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
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/TogetherToBuy.css"/>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.mobile.custom.min.js"></script>
<title>一起购</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var shopId = '${shopId}';
	var fromWay = '${fromWay}';
</script>
</head>

<body>
<div class="main-cont" id="main-cont" >
	<div class="pro-count">
         <span class="part-note-msg">人数</span>
         <div class="quantity-wrapper">
             <input id="quantityMinus_p" class="quantity-decrease" name="" type="button" value="-" />  
             
             <input type="text" readonly class="quantity" size="4" value="2" id="number_p" >
             <input id="quantityPlus_p"  class="quantity-increase" name="" type="button" value="+" /> 
         </div>
         <em>最多20人</em>
     </div>
     <div class="pro-count">
          <span class="part-note-msg">天数</span>
          <div class="quantity-wrapper">
              <input id="quantityMinus_d" class="quantity-decrease" name="" type="button" value="-" />                
              <input type="text" readonly class="quantity" size="4" value="2" id="number_d" >
              <input id="quantityPlus_d"  class="quantity-increase" name="" type="button" value="+" /> 
          </div>
          <em>最多14天</em>
      </div>
      <div class="address">
      	  <p class="p1"><span></span><em></em></p>
      	  <p class="p2"></p>
      </div>
      <div class="leave_note"><input type="text" value="" id="note" placeholder="给团员留言" /></div>
      <div class="TogetherToBuy_btn"><a href="javascript:addGroupBuy();">建立购物团</a></div>
	  <div class="TogetherToBuy_help"><a href="<%=basePath%>TogetherToBuy/TogetherToBuy_help.html">如何一起购？</a></div>
</div>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/new_groupBuy.js"></script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
