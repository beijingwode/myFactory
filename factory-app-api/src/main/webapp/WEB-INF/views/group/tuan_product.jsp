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
<title>可购商品详情</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/product_m_v2.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/WeChat_push.css" />
<script type="text/javascript" src="<%=static_resources %>js/beehive_640.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var productId = '${productId}';
	var userId = '${userId}';
	//var from = '${from}';
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
            <img src="<%=static_resources %>images/TogetherToBuy/collect_btn_img.png" id="isCollection" style="display: none"/>
            <img src="<%=static_resources %>images/TogetherToBuy/no_collection.png" id="noCollection"/>
            <input id="collection" value="0" type="hidden">
            </a>
            </div>
            <div class="ttb_link" style="display: none"><a href="javascript:chooseSku();">一起购邀请 ></a></div>
        </div>
    	<div class="TogetherToBuy_link" style="display: none"></div>
    	<div class="main_two">
        	<div class="con_tit">
            	<p></p>
                <div class="tesheng_btn" id="product_flag">
                	<a href="javascript:void(0);"><img src="<%=static_resources %>images/TogetherToBuy/tesheng_icon.png" /></a>
                </div>
            </div>
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
            	<input type="hidden" class="quantity"  value="1" id="number" >      	
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
       <div class="product_details">
       		<p>商品详情</p>
	    	<div class="con_box_img">
	        	
	        </div>
	    </div>
        <div class="bottom_box">
            <div class="bottom_con">
               <a href="javascript:checkProduct();" class="buy_btn" style="float:right" id="buy_btn">勾选</a>
            </div>
        </div>
    </div>
    
    
    
</div>
<div class="thickdiv" ></div>
<div class="thickbox thickbox1" >  
    <div class="thickcon"> 	
         <div class="pro-color">
             <span class="part-note-msg"> 颜色 </span>
             <p id="color">
             </p>
         </div>
         <div class="pro-color">
            <span class="part-note-msg" >尺寸 </span>
            <p id="size"></p>
        </div>                                           
    </div>
    <a href="javascript:void(0)" id="closeBox1" class="thickclose" >×</a>
</div>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.mobile.custom.min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>

<script type="text/javascript" src="<%=static_resources %>js/group/tuan_product.js"></script>
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".goback").hide();
		$(".top ul").css("width","100%")
	}
});
</script>
</body>
</html>
