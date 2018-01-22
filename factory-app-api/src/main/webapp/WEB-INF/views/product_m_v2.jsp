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
<title>商品详情</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/product_m_v2.css?1" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/WeChat_push.css" />
<script type="text/javascript" src="<%=static_resources %>js/beehive_640.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/group/handleTime.js"></script>

<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var productId = '${productId}';
	var userId = '${userId}';
	var from = '${from}';
	var pageKey = '${pageKey}';//来自的页面
	var pageStock = '${pageStock}';//页面对应的库存
	
	if(pageKey!='' && pageKey!='null' &&pageKey!='undefined') {
		sessionStorage.setItem("pageKey",pageKey);
	} else {
		sessionStorage.removeItem("pageKey");
	}
</script>
<style type="text/css">
	.base-txt{
	margin-left: 15px;
    font-size: 14px;
    color: #000;
    line-height: 24px;
    padding-right: 15px;
	}
</style>
</head> 

<body>

<div class="main-cont" id="main-cont" >
	<input type="hidden" id="specificationsId" value="${specificationsId}">
    <div class="con_box">
    	
    	<div class="flexslider">
            <ul class="slides">
              <li><a href="#"><img src=""  alt="" class="img-responsive"></a></li>
              <li><a href="#"><img src=""  alt="" class="img-responsive"></a></li>
              <li><a href="#"><img src=""  alt="" class="img-responsive"></a></li>
              <li><a href="#"><img src=""  alt="" class="img-responsive"></a></li>
              <li><a href="#"><img src=""  alt="" class="img-responsive"></a></li>
            </ul>
            
            <div class="slides_top">
            <a class="collect_btn" >
            <input id="collection" value="0" type="hidden">
            </a>
            </div>
            <div class="ttb_link" style="display: none"><a href="javascript:;" class="togetherTobuy_btn">一起购邀请 ></a></div>
        </div>
    	<div class="TogetherToBuy_link" style="display: none"></div>
    	<div class="main_two">
        	<div class="con_tit">
            	<p></p>
                <div class="tesheng_btn" id="product_flag">
                	<a href="javascript:void(0);"></a>
                </div>
            </div>
            <!-- 快递 -->
            <div class="postage"></div>
        </div>
        <div class="jtj_box">
        	<span>阶梯价：</span>
        	<div class="jtj_con">
        		<ul>        		
        		</ul>
        	</div>
        </div>
        
        <div class="prod-spec bdr-b" id="prodSpecArea">      	
            <div class="spec-desc J_ping" id="natureID" >
            	<input type="hidden" id="specificationsId" value="${specificationsId}">           	
                <span class="part-note-msg" style="float:left;width: 20%;">规格数量</span>
                 <div id="specDetailInfo"  class="base-txt">&nbsp;&nbsp;<span style="display:none;" id="amount">1件</span></div>           
            </div>
        </div>
        
        <div class="shop_link"><a href="javascript:;"></a></div>
        <div class="serve">       	
			<a href="javascript:;">
				<span>服务：</span>
				<ul>
				</ul>
			</a>
        </div>
        <div class="shop-part">
        </div>
        <div class="comment_box" style="display:none;"></div>
        <div class="product_details">
       		<p>商品详情</p>
	    	<div class="con_box_img">	        	
	        </div>
	    </div>
        <div class="bottom_box">
            <div class="bottom_con">
                <a href="<%=basePath %>cart/page" class="btm_icon buy_icon" id="go2Cart"><span id="cartNum"></span></a>
                <a href="javascript:void(0);" class="no_product" id="no_product" style="display: none">库存不足</a>
            </div>
        </div>
    </div>
</div>
<div class="thickdiv" ></div>
<div class="thickbox thickbox1" >  
    <div class="thickcon"> 	
        <dl>
            <dt><a href="javascript:void(0);"><img src="" /></a></dt>
            <dd class="dd2">￥<em></em></dd>
            <dd class="dd1">已选：</dd>
            <dd class="dd1">库存：</dd>
            <dd class="dd1"></dd>
        </dl>            
         <!--颜色-->
         <div class="pro-color">
             <span class="part-note-msg"> 颜色 </span>
             <p id="color">
             </p>
         </div>
         <div class="pro-color">
            <span class="part-note-msg" >尺寸 </span>
            <p id="size"></p>
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
    <a href="javascript:void(0)" id="closeBox1" class="thickclose" >×</a>
</div>
<div class="thickbox thickbox2" >  
    <div class="thickcon"> 	
        <p>服务</p>
    </div>
    <a href="javascript:void(0)" id="closeBox2" class="thickclose" >×</a>
</div>
<!-- 换领弹出提示 -->
<div class="huanling_hit" style="display:none">
	<p>我们将根据您的意愿匹配换领需求匹配成功后将尽快为您发货</p>
	<a href="javascript:matchRules()">知道了</a>
</div>

<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.mobile.custom.min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/order_sessionStorage.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/product_m_v2.js?0121"></script>
<script type="text/javascript" src="<%=static_resources %>js/limit_ticket/ajax_get_limit_ticket.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
$(function(){
	//弹出规格	
	$(".spec-desc").click(function(e){
		chooseSku(this);
		 var t = $("#number");
		 if (parseInt(t.val())>minLimitNum){
	          $('#quantityMinus').attr('disabled',false);
	      } 
		 if (parseInt(t.val())<=1 || parseInt(t.val())<=minLimitNum) {//初始化数量小于等于起售数量
			 $('#quantityMinus').attr('disabled',true);
		 }
		 if(parseInt(productStock)<=parseInt(t.val())){//库存小于等于当前数量
			$('#quantityPlus').attr('disabled',true); 
		 }
		 if (limitCnt>0&&limitCnt<=t.val()) {
			showInfoBox("购买数量超过限购数量");
		 }
		 selSku();
		
	}); 
	$(".thickdiv").click(function(e){
		$(".thickdiv").hide();
		$(".thickbox1").hide();
		$(".thickbox2").hide();
		$(".huanling_hit").hide();
		$(".lq_thickbox").hide();
		dealBottomBtn();
	});
	$(".thickclose").click(function(e){
		$(".thickdiv").hide();
		$(".thickbox1").hide();
		$(".thickbox2").hide();
		dealBottomBtn();
	});
	//弹出服务
	$(".serve").click(function(e){
		$(".thickdiv").show();
		$(".thickbox2").show();
	});
})

</script>
<%@ include file="/commons/alertMessage.jsp" %>
<script type="text/javascript">
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".goback").hide();
		$(".top ul").css("width","100%")
	}
});
</script>
</body>
</html>
