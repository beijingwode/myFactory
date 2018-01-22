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
<title>申请售后</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>/css/details.css" />  
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var subOrderId = '${subOrderId}';
	var realPrice = '${realPrice}';
	var returnOrderId = '${returnOrderId}';
	var refundOrderId = '${refundOrderId}';
	var returnStatus = '${returnStatus}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5upload.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/after_sales.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
</head>

<body>
<div class="main-cont" id="main-cont">
	<div class="top_box">
        <div class="top"> 
            <!-- <a href="javascript:close();" class="aleft"></a> -->  	
            <h1>申请售后</h1>
        </div>
    </div>
    <input type="hidden" id="returnStatus" value="${returnStatus}">
    <div class="main-box" style="padding-bottom:60px;position: absolute;top: 46px;">
    	<div class="top_tab">
        	<ul>
            	<li class="clickthis"><a href="javascript:void(0);" title="1">退货退款</a></li>
                <li style="background:none;"><a href="javascript:void(0);" title="2">仅退款</a></li>
        	</ul>
        </div>
        <div class="box_con">
        	<div class="return_reason">
            	<div class="return_reason_tit tit1"><em>*</em><a href="javascript:void(0);">退货原因：</a></div>
                <div class="return_reason_con con1">
                	<ul>
                    	<li>收到商品破损</li>
                        <li>商品发错/漏发</li>
                        <li>商品需要维修</li>
                        <li>发票问题</li>
                        <li>收到商品与描述不符</li>
                        <li>商品质量问题</li>
                        <li>未按约定时间发货</li>
                    </ul>
                </div>
            </div>
            <div class="reimburse">
            	<em>*</em><span>退款金额：</span><input id="returnPrice" type="text" placeholder="请输入退款金额，最多不超过￥${realPrice}" />
            </div>            
        </div>
        <div class="box_con dis_none">
        	<div class="return_reason">
            	<div class="return_reason_tit tit2"><em>*</em><a href="javascript:void(0);">货物状态：</a></div>
                <div class="return_reason_con con2">
                	<ul>
                    	<li value="-1">未收到货</li>
                        <li value="1">已收到货</li>                       
                    </ul>
                </div>
            </div>
        	<div class="return_reason">
            	<div class="return_reason_tit tit3"><em>*</em><a href="javascript:void(0);">退货原因：</a></div>
                <div class="return_reason_con con3">
                	<ul>
                    	<li>收到商品破损</li>
                        <li>商品发错/漏发</li>
                        <li>商品需要维修</li>
                        <li>发票问题</li>
                        <li>收到商品与描述不符</li>
                        <li>商品质量问题</li>
                        <li>未按约定时间发货</li>
                    </ul>
                </div>
            </div>
            <div class="reimburse">
            	<em>*</em><span>退款金额：</span><input id="returnPrice2" type="text" placeholder="请输入退款金额，最多不超过￥${realPrice}" />
            </div>            
        </div>
       	<div class="feedback">
        	<div class="feedback_tit"><span>退款说明</span>跟卖家说点什么吧（选填）</div>
           	<div class="feedback_con"><textarea id="note"></textarea></div>
            <div class="feedback_pic">
              	<ul>
               		<li>
                  		<div class="pic_up">
                  			<a href="javascript:fileSelect(0);"><span>+</span><p>上传凭证<br />最多3张</p></a>
                  		</div>
                  	</li>
                   <li></li>
                   <li></li>
          		</ul>
            </div>
		</div>
    </div>
    <input type="file" id="avatar" style="display: none" accept="image/*" onchange="updateImg(this);">
    <input type="hidden" id="picLieq">
    <div style="display: none">
    <img id="resultImage" />
    </div>
    <div class="order_btm "><a href="javascript:void(0);" onclick="go2Submit()" >提交申请</a><a href="javascript:void(0);" onclick="go2Cancle()"class="order_btm_sales" style="margin-right:10px;">取消退款</a></div>
    
</div>
<script type="text/javascript">
if(isWeiXin()) {
	$(".top_box").hide();
	$(".main-box").css("top","0");
}
</script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
