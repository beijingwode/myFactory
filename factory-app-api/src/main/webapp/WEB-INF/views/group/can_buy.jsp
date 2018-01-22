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
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<title>可购商品</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/can_buy.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/can_buy.js?1"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/order_sessionStorage.js"></script>
</head>
<script type="text/javascript">
var jsBasePath = "<%=basePath %>";
var groupId = '${groupId}';
var type = '${type}';
var userNick = '${userNick}';
var userAvatar = '${userAvatar}';
</script>
<body>
<div class="main-cont" id="main-cont" >
	<div class="top_dl" style="display:none">
		<dl>
			<dt><img src="<%=static_resources %>images/TogetherToBuy/top_dl_dt_icon1.png" /></dt>
			<dd class="dd1">恭喜您绑定成功！</dd>
			<dd class="dd2"><span>${userNick}</span>向您推荐了以下商品</dd>
		</dl>
	</div>
    <div class="main-box">
    	<div class="order_box">
        	<div class="order_top"><p id="shopName"></p></div>
            <ul id="product">
            	
            </ul>       
        </div> 
        
    </div>
    <div class="bottom_box bottom_box1">
    	<dl>
			<dt>
				<a href="javascript:void(0);" id="totalSelectCart"></a>
				<span id="totalSelectNum">0</span>
			</dt>
			<dd class="dd1">￥<span id="prices">0.00</span>+<span id="maxFucoins">0.00</span>券</dd>
			<dd class="dd2">不含运费</dd>
		</dl>
        <div class="bottom_box_rt">       	
            <a href="javascript:submitOrder();">结算</a>
        </div>
    </div>
</div>
<div class="thickdiv" ></div>
<div class="thickbox thickbox1" >  
    <div class="thickcon order_box"> 
    	<ul>
    		<li>
		        <dl>
		           	<dt>
		           		<span class="jietijia">阶梯价</span>
		           		<a href="javascript:void(0);" class="skuImage"><img src="" /></a>
		           	</dt>
		           	<dd class="dd0"></dd>
		               <dd class="dd1">
		               	<a href="javascript:void(0);"></a>                      
		               </dd>
		               <dd class="dd3"></dd>
		               <dd class="dd4">￥<span></span><i></i></dd>
		               <dd class="dd2"><i></i></dd>
		           </dl> 
		       </li>
		   </ul>
		   <div class="gg_box">
			   <div class="jtj_box">
			   		<span>阶梯价</span>
			   		<p><i></i></p>
			   </div>
			   <div class="kucun"></div>
			   <input type="hidden" id="specificationsId" value="">     
			   <input type="hidden" id="itemValues" value="">     
         <!--颜色-->
		       <div class="pro-color">
		           <span class="part-note-msg"> 颜色 </span>
		           <p id="color">
		              <!-- <a title="1" class="a-item  J_ping" href="javascript:void(0)">黑色</a> -->
		           </p>
		       </div>
		       <div class="pro-color">
		            <span class="part-note-msg">尺码</span>
		            <p id="color">
		                <!-- <a title="1" class="a-item  J_ping" href="javascript:void(0)">XS（155/80）</a> -->
		             </p>
		        </div>                                               
		        <div class="pro-count">
		             <span class="part-note-msg">数量</span>
		             <div class="quantity-wrapper">
		                 <input id="quantityMinus" class="quantity-decrease" name="" type="button" value="-" />                         
		                 <input type="text" readonly class="quantity" size="4" value="1" id="number" >
		                 <input id="quantityPlus"  class="quantity-increase" name="" type="button" value="+" />                        
		             </div>
		         </div>
		   </div>	       		
    </div>
    <a href="javascript:void(0)" id="closeBox1" class="thickclose" >×</a>
    <div class="add_buycar"><a href="javascript:void(0)" onclick="go2AddCart(this)">加入购物车</a></div>
</div>
<div class="thickbox thickbox2" >  
    <div class="thickcon order_box"> 	
        <p>已选商品</p>
        <ul>
    		<!-- <li>
		        <dl>		           			          
		            <dd class="dd1">
		               	<a href="javascript:void(0);"></a>                      
		            </dd>
		            <dd class="dd2"></dd>	                
	                <dd class="dd4">阶梯价:￥<span>0.00</span><i>+0.00券</i></dd>
	            </dl> 
	            <div class="quantity-wrapper">
	                 <input class="quantity-decrease" name="" type="button" value="-" />                         
	                 <input type="text" readonly class="quantity" size="4" value="1" id="numbers" >
	                 <input class="quantity-increase" name="" type="button" value="+" />                        
	            </div>
	        </li> -->
	     </ul>
    </div>
    <a href="javascript:void(0)" id="closeBox2" class="thickclose" onclick="thickclose()">×</a>
</div>
<%@ include file="/commons/alertMessage.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
</body>
</html>
