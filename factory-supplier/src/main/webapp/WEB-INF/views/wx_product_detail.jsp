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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/transaction_management.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/prompt.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/wxGetUid.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/update_product.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/nochange_font.js"></script>
<title>修改商品</title>
<script>
	var jsBasePath = '<%=basePath%>';
	var productId = '${id}';
</script>
</head>
<body>
<div class="main_box">
	<input type="hidden" id="productId">
	<input type="hidden" id="empPrice" value="0">
	<input type="hidden" id="trialPrice" value="0">
	<input type="hidden" id="isMarketable">
	<input type="hidden" id="status">
	<div class="com_top">
    	<dl>
        	<dt id="mainPic"><img src="<%=static_resources %>images/shop_dt.png" /></dt>
            <dd class="dd1"></dd>
            <dd class="dd2"></dd>
        </dl>
    </div>
   <div id="pslList">
   	 <div class="standard_con">
    	<ul>
        	<li class="li1"></li>
            <li class="li2"><span></span></li>
            <li class="li3"><span></span></li>
            <li class="li4"><span class="span1"></span><span class="span2"></span></li>
        </ul>
        <em onclick="changPSL()"></em>
    </div>
   </div>
</div>
<div class="recharge_money"  style="display: none">
     <div class="theme-tit">确定修改</div>
     <div class="theme-input"><p>若您修改了价格,该商品将按原价格销售，待审核通过后自动修改！</p></div>
     <div class="theme-popbod">
        <a href="javascript:close();" >取消</a>  
        <a href="javascript:go2Sure();" id="sure" style="border:none;">确定</a>
     </div>
</div>
<div class="add_money-mask" style="display: none"></div>
<div class="thickdiv" ></div>
<div class="thickbox" >
	<div class="tit tit2" id="ps"></div>
	<input type="hidden" id="psId">
    <div class="tit_con">
    	<ul>
        	<li><span class="tit_span">修改电商价</span><input type="text" id="price" onkeyup="checkNum(this)"/></li>
            <li><span>修改内购价</span><input type="text" id="flj" onkeyup="checkNum(this)"/></li>
            <li><span>修改商品库存</span><input type="text" id="allnum" onkeyup="this.value=this.value.replace(/\D/g,'')"/></li>
            <li style="border:none;"><span>修改预警值</span><input type="text" id="warnnum" onkeyup="this.value=this.value.replace(/\D/g,'')"/></li>
        </ul>
    </div>
   <div class="thickbox_bottom"><a href="javascript:go2Save();">确认保存</a><a href="javascript:go2Close();" style="border:none;" class="close">取消</a></div>
</div>
<div style="display: none">
<iframe id='updateProduct_iframe' name="updateProduct_iframe"></iframe>
<form method="POST" id="updateSku_from" target="updateProduct_iframe">
	<div id="post_param"></div>
</form>
</div>
<%@ include file="/commons/newAlertMessage.jsp" %>
</body>
</html>