<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-订单详情</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
 
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/returnOrder.js"></script>
<style>
.wl_xx li span{width:270px;color:#6a6a6a;padding-right:30px;display:block;}
</style>
</head>
<body>
<!--top begin-->
<%@ include file="../common/header_03.jsp" %>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Deal_nav">
 <c:if test="${!empty returnOrder || !empty refundOrder}">
 	<c:if test="${subOrder.status == 3 || subOrder.status == 5}">
 		<p class="O_deal_2"></p>
 	</c:if>
 	<c:if test="${subOrder.status == 11 || subOrder.status == 12}">
 		<p class="O_deal_3"></p>
 	</c:if>
 	<c:if test="${subOrder.status == -11 || subOrder.status == -12}">
 		<p class="O_deal_11"></p>
 	</c:if>
 	
 </c:if>
  <c:if test="${empty returnOrder && empty refundOrder}">
  	<p class="O_deal"></p>
  </c:if>
	
</div>
<div class="Order_content">
	<div class="order_c_left">
    	<p class="P_title">商品信息</p>
        <c:forEach var="subOrderItem" items="${subOrder.subOrderItems }" varStatus="vs">
	        <div class="M-shangpin P_w300">
         		<c:if test="${subOrderItem.saleKbn==1 }">
          		<div class="picon"><img src="../images/picon2.png" /></div>
          		</c:if>
         		<c:if test="${subOrderItem.saleKbn==2 }">
          		<div class="picon"><img src="../images/picon_c2.png" /></div>
          		</c:if>
          		<c:if test="${subOrderItem.saleKbn==4 }">
          		<div class="picon"><img src="../images/picon_z2.png" /></div>
          		</c:if>
         		<c:if test="${subOrderItem.saleKbn==5 }">
          		<div class="picon"><img src="../images/picon_t2.png" /></div>
          		</c:if>
	            <span class="M-s-img"><a href="/${subOrderItem.productId }.html?pageKey=order"><img src="${subOrderItem.image }" height="78" width="78"></a></span>
	            <div class="M-s-text o_w200">
	                <p class="p_name">${subOrderItem.productName }</p>
	                <p class="p2">单价：<span class="O_jg">
	                <fmt:formatNumber value="${subOrderItem.internalPurchasePrice }" type="currency" pattern="￥0.00"/>
	                <p class="p3">数量：<span>${subOrderItem.number }</span></p>
<%-- 	                <c:if test="${subOrderItem.companyTicket > 0}">+<em><fmt:formatNumber value="${subOrderItem.companyTicket}" type="currency" pattern="#0.00"/>券</em></c:if> --%>
<%-- 	                <c:if test="${subOrderItem.benefitAmount > 0}">+<em><fmt:formatNumber value="${subOrderItem.benefitAmount}" type="currency" pattern="#0.00"/>惠</em></c:if> --%>
	                </span>
	                </p>
	                <p class="p3">商家：<span class="O_dp">
	                <c:if test="${user.supplierId==subOrder.supplierId}">自家</c:if>
	                <c:if test="${user.supplierId!=subOrder.supplierId}">${subOrder.supplierName }</c:if>
	                </span>
	                </p>
	            </div>
	        </div>
        </c:forEach>
        <p class="P_title">订单信息</p>
        <ul class="O_con bom">
            <li><span class="O_c_q">订单编号：</span><span class="O_c_h">${subOrder.subOrderId }</span></li>
            <c:set value="0" var="totalProduct"></c:set>
            <c:forEach var="subOrderItem" items="${subOrder.subOrderItems }"> 
            	<c:set value="${totalProduct + (subOrderItem.internalPurchasePrice * subOrderItem.number)}" var="totalProduct"></c:set>
            </c:forEach>
	        <li><span class="O_c_q">商品总额：</span><span class="O_c_h"><fmt:formatNumber value="${totalProduct}" type="currency" pattern="￥0.00"/></span></li>
            <li><span class="O_c_q">运   费：</span><span class="O_c_h"><fmt:formatNumber value="${subOrder.totalShipping }" type="currency" pattern="￥0.00"/></span></li>
            <li><span class="O_c_q">订单总价：</span><span class="O_c_h"><fmt:formatNumber value="${subOrder.totalShipping + totalProduct }" type="currency" pattern="￥0.00"/></span></li>
            <c:if test="${flag}">
            <li>
            <span class="O_c_q">换领币(使用${totalBenefitTicket})：</span>
            <span class="O_c_h red" style="width:auto;">-${subOrder.benefitAmount } </span>
            </li>
            </c:if>
            <li><span class="O_c_q">实付金额：</span><span class="O_c_h"><fmt:formatNumber value="${subOrder.realPrice }" type="currency" pattern="￥0.00"/>
        		<c:if test="${subOrder.status == 0 && !empty subOrder.cashPay}"> 
            		(已支付 <em><fmt:formatNumber value="${subOrder.cashPay}" type="currency" pattern="￥0.00"/></em>)
                </c:if></span>
            </li>            <li><span class="O_c_q">成交时间：</span><span class="O_c_h"><fmt:formatDate value="${subOrder.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            <li><span class="O_c_q">收货地址：</span><span class="O_c_h">${subOrder.name}，${subOrder.mobile}，${subOrder.address }</span></li>
        </ul>
        <p class="P_title">物流信息</p>
         <ul class="wl_xx D_height D_height01">
        </ul>
        <div class="flow_btn"></div>
    </div>


    <%--已申请退货并退款--%>
    <c:if test="${!empty returnOrder }">
    	<c:if test="${returnOrder.status == 0 }">
    		    <div class="order_c_right">
			    	<p class="P_title">退货申请</p>
			        <div class="refund_wrap">
			        	<div class="refund_info">售后信息</div>
			            <ul class="info_lt">
			            	<li>
			                	<span class="nm">售后申请：</span>
			                    <span class="cot">待处理</span>
			                </li>
			                <li>
			                	<span class="nm">售后类型：</span>
			                    <span class="cot">退货退款</span>
			                </li>
			                <li>
			                	<span class="nm">退货金额：</span>
			                    <span class="cot red"><fmt:formatNumber value="${returnOrder.returnPrice }" type="currency" pattern="￥0.00"/></span>
			                </li>
			                <li>
			                	<span class="nm">退货编号：</span>
			                    <span class="cot">${returnOrder.returnOrderId }</span>
			                </li>
			            </ul>
			            <div class="clear"></div>
			            <div class="friend_info">
			            	<h3>等待商家处理退货申请</h3>
			                <ul class="friend_list">
			                	<li>· 如果商家同意，退款申请将达成并需要您退货给商家</li>
			                    <li>· 如果商家拒绝，将需要修改退款申请</li>
			                    <li>· 如果<span class="red" id="endTime"></span>内卖家未处理，退款申请将达成并退款至您的平台账号</li>
			                    <input type="hidden" value="<fmt:formatDate value="${returnOrder.lastTime }"  pattern="yyyy-MM-dd HH:mm:ss"/>"  id="lastTiime"/>
			                </ul>
			            </div>
			      </div>
    		</div>
    	</c:if>
    	<c:if test="${returnOrder.status == 1 }">
    		    <div class="order_c_right">
			    	<p class="P_title">退货申请</p>
			        <div class="refund_wrap">
			            <div class="friend_info">
			            	<h3>退货成功</h3>
			                <ul class="friend_list">
			                	<li>退货成功时间：<fmt:formatDate value="${returnOrder.updateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/></li>
			                    <li>退款金额：<span class="red"><fmt:formatNumber value="${returnOrder.returnPrice }" type="currency" pattern="￥0.00"/></span>元</li>
			                </ul>
			            </div>
			        </div>
			    </div>
    	</c:if>
    	<c:if test="${returnOrder.status == -1 }">
    		    <div class="order_c_right">
			    	<p class="P_title">退货申请</p>
			        <div class="refund_wrap">
			            <div class="friend_info">
			            	<h3><span class="red">退款失败</span></h3>
			                <ul class="friend_list">
			                	<li>拒绝理由：${subOrder.refuseNote }</li>
			                	<li>处理时间：<fmt:formatDate value="${returnOrder.updateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/></li>
			                    <li>退款金额：<span class="red"><fmt:formatNumber value="${returnOrder.returnPrice }" type="currency" pattern="￥0.00"/></span>元</li>
			                </ul>
			            </div>
			        </div>
			    </div>
    	</c:if>
    </c:if>

<%--申请仅退款操作--%>
    <c:if test="${!empty refundOrder }">
    <c:if test="${refundOrder.status == 1 }">
        <div class="order_c_right">
            <p class="P_title">退款申请</p>
            <div class="refund_wrap">
                <div class="refund_info">售后信息</div>
                <ul class="info_lt">
                    <li>
                        <span class="nm">售后申请：</span>
                        <span class="cot">待处理</span>
                    </li>
                    <li>
                        <span class="nm">售后类型：</span>
                        <span class="cot">仅退款</span>
                    </li>
                    <li>
                        <span class="nm">退款金额：</span>
                        <span class="cot red"><fmt:formatNumber value="${refundOrder.refundPrice }" type="currency" pattern="￥0.00"/></span>
                    </li>
                    <li>
                        <span class="nm">退款编号：</span>
                        <span class="cot">${refundOrder.refundOrderId }</span>
                    </li>
                </ul>
                <div class="clear"></div>
                <div class="friend_info">
                    <h3>等待商家处理退款申请</h3>
                    <ul class="friend_list">
                        <li>· 如果商家同意，退款申请将达成</li>
                        <li>· 如果商家拒绝，将需要修改退款申请</li>
                    </ul>
                </div>
            </div>
        </div>
        </c:if>

        <c:if test="${refundOrder.status == 2 }">
            <div class="order_c_right">
                <p class="P_title">退款申请</p>
                <div class="refund_wrap">
                    <div class="friend_info">
                        <h3>退款处理中</h3>
                        <ul class="friend_list">
                            <li>退货成功时间：${refundOrder.updateTime }</li>
                            <li>退款金额：<span class="red"><fmt:formatNumber value="${refundOrder.refundPrice }" type="currency" pattern="￥0.00"/></span>元</li>
                        </ul>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${refundOrder.status == 10 }">
            <div class="order_c_right">
                <p class="P_title">退款申请</p>
                <div class="refund_wrap">
                    <div class="friend_info">
                        <h3>退款成功</h3>
                        <ul class="friend_list">
                            <li>退款金额：<span class="red"><fmt:formatNumber value="${refundOrder.refundPrice }" type="currency" pattern="￥0.00"/></span>元</li>
                        </ul>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${refundOrder.status == 3 }">
            <div class="order_c_right">
                <p class="P_title">退款申请</p>
                <div class="refund_wrap">
                    <div class="friend_info">
			            <h3><span class="red">退款失败</span></h3>
                        <ul class="friend_list">
		                	<li>拒绝理由：${subOrder.refuseNote }</li>
		                	<li>处理时间：<fmt:formatDate value="${refundOrder.updateTime }"  pattern="yyyy-MM-dd HH:mm:ss"/></li>
		                    <li>退款金额：<span class="red"><fmt:formatNumber value="${refundOrder.refundPrice }" type="currency" pattern="￥0.00"/></span>元</li>
                        </ul>
                    </div>
                </div>
            </div>
        </c:if>
    </c:if>

     <c:if test="${empty returnOrder && empty refundOrder }">
     	    <div class="order_c_right">
    	<form id="returnOrderForm" action="/member/createReturnOrder" method="post">
    	<input type="hidden" name="subOrderId" value="${subOrder.subOrderId }">
    	<p class="P_title">退货申请</p>
        <div class="refund_wrap">
            <div class="refund_one">
                <span><b class="red">*</b>执行操作：</span>
                <div class="R_cont">
                    <div class="R_select">
                        <select id="type" name="type">
                            <option value="0">退货并退款</option>
                            <option value="1">仅退款</option>
                        </select>
                    </div>
                </div>
            </div>

        	<div class="refund_one">
            	<span><b class="red">*</b>退款原因：</span>
                <div class="R_cont">
                    <div class="R_select">
                        <select id="reason" name="reason">
                            <option value="">请选择退货理由</option>
                            <option value="收到商品破损">收到商品破损</option>
                            <option value="商品错发/漏发">商品错发/漏发</option>
                            <option value="商品需要维修">商品需要维修</option>
                            <option value="发票问题">发票问题</option>
                            <option value="收到商品与描述不符">收到商品与描述不符</option>
                            <option value="商品质量问题">商品质量问题</option>
                            <option value="未按约定时间发货">未按约定时间发货</option>
                        </select>                    
                    </div>
                    <p>如果您需要退还运费，可申请此退款，并提供有效凭证或与商家协商一致。</p>
                </div>
            </div>
            <div class="refund_one jj">
            	<span><b class="red">*</b>退款金额：</span>
                <div class="R_cont">
                	<input type="hidden" id="price" value="${subOrder.realPrice- trialReturn}">
                    <input class="money_txt" type="text" id="returnPrice" name="returnPrice" value="${subOrder.realPrice - trialReturn}" maxlength="10">
                    <strong>最多<b class="red">${subOrder.realPrice- trialReturn }</b>元(含运费<b class="red">${subOrder.totalShipping }</b>元)</strong>
                </div>
            </div>
            <div class="refund_one">
            	<span>退款说明：</span>
                <textarea class="textarea_txt" id="note" name="note"></textarea>
            </div>
            <div class="refund_one">
            	<span>上传凭证：</span>
                <div class="R_cont">
                    <ul class="pho_list">
                    	<li>
                    		<img id="uploadpic1" src="<%=basePath %>images/uploadpic.jpg" width="120" height="120">
  								<input type="file" id="avatar1" name="avatar1"  style="display:none;" onchange="uploadImage('avatar1','uploadpic1','images1');"/>
  								<input type="hidden" id="images1" name="images" value=""> 
                    		<!-- <i></i> -->
                    	</li>
                        <li>
                        	<a href="#"><img id="uploadpic2" src="<%=basePath %>images/uploadpic.jpg" width="120" height="120"></a>
                        	<input type="file" id="avatar2" name="avatar2"  style="display:none;" onchange="uploadImage('avatar2','uploadpic2','images2');"/>
                        	<input type="hidden" id="images2" name="images" value=""> 
                        </li>
                        <li>
                        	<a href="#"><img id="uploadpic3" src="<%=basePath %>images/uploadpic.jpg" width="120" height="120"></a>
                        	<input type="file" id="avatar3" name="avatar3"  style="display:none;" onchange="uploadImage('avatar3','uploadpic3','images3');"/>
                        	<input type="hidden" id="images3" name="images" value=""> 
                        </li>
                    </ul>
                    <p>每张图片大小不超过<b class="red">5M</b>，最多3张，支持<b class="red">GIF</b>、<b class="red">JPG</b>、<b class="red">PNG</b>、<b class="red">BMP</b>格式</p>
                </div>
            </div>
            <div class="refund_one logistics">
            	<span><b class="red">*</b>物流公司：</span>
                <div class="R_cont">
                    <div class="R_select">
                        <select id="expressType" name="expressType">
			        	<c:forEach items="${expressCompanys}" var="com"> 
                            <option value="${com.id}">${com.name}</option>
						</c:forEach>
                        </select>                    
                    </div>
                </div>
            </div>
            <div class="refund_one logistics">
            	<span><b class="red">*</b>快递单号：</span>
                <input class="kd-input" type="text" id="expressNo" name="expressNo" value="" maxlength="20">
            </div>
            <div class="refund_submitbtn"><a id="createReturnOrder" href="javascript:void(0);">提交申请</a></div>
        </div>
        </form>
    </div>
    </c:if>
</div>

<div class="clear"></div>
<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
<%@ include file="../common/footer.jsp" %>

<script type="text/javascript">
$(document).ready(function(){

	//物流情报显示
	var html="";
	if ('${subOrder.selfDelivery}'!='null' && '${subOrder.selfDelivery}'=='1') {//自提
		 html+= "<li><span>配送方式：</span>自提</li>";
		 $(".wl_xx").html(html);
	}else {
	if('${expressCom}' != '' && '${expressCom}'!='null') {
		if ('${subOrder.expressType}'=='14660000000000000') {//电子卡券
			 html+= "<li><span>配送方式：电子卡券</span></li>";
			 html+= "<li><span>卡券密码:${expressNo}</span></li>";
			 $(".wl_xx").html(html);
		}else if('${subOrder.expressType}'=='14660000000000001'){//厂家直送
			 html+= "<li><span>配送方式：厂家直送</span></li>";
			 html+= "<li><span>货运号:${expressNo}</span></li>";
			 $(".wl_xx").html(html);
		}else{
    	var content = '"sname":"express.ExpressSearch","com":"${expressCom}","express_no":"${expressNo}","user":${searchId},"version":"v2"';
    	$.ajax({
            url: 'http://kuaidi.wo-de.com/express/busJsonp.do?content={' +content+ '}&token=',
    		dataType : 'jsonp',
    	    jsonp:'jsonpcallback',
    	    success: function(json){
    	    	var ary = eval(json.body.history)
    	    	var lis="";
    	    	for(var i=0; i<ary.length; i++)  
	    	  	{  
	    	     	lis += "<li><span>"+ary[i].dealDate+"</span>"+ary[i].des+"</li>";
	    	  	}
    	    	if(lis!="") {
    	    		$(".D_height").html(lis);
    	    	}
    	    }
    	});
		}
	}
	}
	
	//点击显示未显示的物流信息
	
	$(".flow_btn").toggle(
			  function(){
			  $(".D_height").removeClass("D_height01");},
			  function(){
			  $(".D_height").addClass("D_height01");}
			);
	
});
</script>
   <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>