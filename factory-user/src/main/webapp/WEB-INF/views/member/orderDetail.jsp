<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
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
<script type="text/javascript" src="<%=basePath %>resources/js/orderDetial.js"></script>
<style type="text/css">

.annotation{width:15px;height:15px;float:left;margin:0 0 0 20px;position:relative;}
.annotation img{cursor:pointer;}
.anno_con{width:190px;height:106px;padding:0 14px 14px 14px;background:#fff;border:1px solid #dadee5;border-bottom:2px solid #ff4040;border-radius:6px;position:absolute;display:none;}
.anno_con span{width:190px;height:40px;display:block;text-align:center;line-height:40px;font-size:14px;color:#262626;border-bottom:1px solid #dadee5;}
.anno_con p{margin-top:10px;text-align:left;line-height:20px;font-size:12px;color:#262626;}
.anno_con i{width:9px;height:8px;display:block;position:absolute;left:102px;bottom:-1px;}
.anno_con p em{font-style:normal;color:#ff4040;padding:0 2px;}

.thickdiv {position: fixed;top: 0;left: 0;z-index:98;width: 100%;height: 100%;background: #000;border: 0;filter: alpha(opacity = 15);opacity: .15;display:block;} 
.main_box{width:400px;height:580px;border:1px solid #ccc;z-index:99;background:#fff;padding:0px 0 10px 0;position:fixed;top:50%;left:50%;margin:-270px 0 0 -200px}
.main_box a{position:absolute;top:10px;right:15px;font-size:20px;color:#ccc;}
.main_box .p_tit{height:40px;width:96%;line-height:40px;text-align:left;padding-left:4%;}
iframe{width:400px;height:540px;border:none;}
</style>
</head>
<body>
<!--top begin-->
<%@ include file="../common/header_06.jsp" %>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->

<input type="hidden" id="subOrderId" value="${subOrder.subOrderId }">
<c:set var="commentable" value="false" scope="page"/>
<c:forEach var="subOrderItem" items="${subOrder.subOrderItems }" varStatus="vs">
	<c:if test="${empty subOrderItem.commentFlag || subOrderItem.commentFlag == 0}">
		<c:set var="commentable" value="true" scope="page"/>
	</c:if>
</c:forEach>
<c:if test="${subOrder.status != -1 && !isAftermarketOrder}">
<div class="Order_nav">
	<c:if test="${subOrder.status == 0 }">
		<p class="O_xd"></p>
	</c:if>
	<c:if test="${subOrder.status == 1 }">
		<p class="O_zf"></p>
	</c:if>
	<c:if test="${subOrder.status == 2 }">
		<p class="O_fh"></p>
	</c:if>
	<c:if test="${subOrder.status == 4 && commentable}">
		<p class="O_sh"></p>
	</c:if>
	<c:if test="${subOrder.status == 4 && !commentable}">
		<p class="O_pj"></p>
	</c:if>
</div>
</c:if>
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
	            <span class="M-s-img"><a href="/${subOrderItem.productId }.html?skuId=${subOrderItem.partNumber }&pageKey=order"><img src="${subOrderItem.image }" height="78" width="78"></a></span>
	            <div class="M-s-text o_w200">
	                <p class="p_name">${subOrderItem.productName }</p>
	                 <p class="p2">单价：<span class="O_jg">
	                <fmt:formatNumber value="${subOrderItem.internalPurchasePrice}" type="currency" pattern="￥0.00"/>
	            	<p class="p3">数量：<span>${subOrderItem.number }</span></p>
<%-- 	            	<c:if test="${subOrderItem.companyTicket > 0}">+<em><fmt:formatNumber value="${subOrderItem.companyTicket}" type="currency" pattern="#0.00"/>券</em></c:if> --%>
<%-- 	                <c:if test="${subOrderItem.benefitAmount > 0}">+<em><fmt:formatNumber value="${subOrderItem.benefitAmount}" type="currency" pattern="#0.00"/>惠</em></c:if></span></p> --%>
	                <p class="p3">商家：<span><a href="/shop/${subOrder.shopId }" class="O_dp">
	                <c:if test="${user.supplierId != subOrder.supplierId}">${subOrder.supplierName }</c:if>
	                <c:if test="${user.supplierId == subOrder.supplierId}">自家</c:if>
	                </a></span></p>
	            </div>
	        </div>
        </c:forEach>
        <p class="P_title">订单信息</p>
        <ul class="O_con">
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
            <c:if test="${suborderLimitTicket!=null && suborderLimitTicket.totalBenefitCash>0}">
            <li>
            <span class="O_c_q">优惠券：</span>
            <span class="O_c_h red" style="width:auto;">-<fmt:formatNumber value="${suborderLimitTicket.totalBenefitCash }" type="currency" pattern="￥0.00"/></span>
            </li>
            </c:if>
            <li><span class="O_c_q">实付金额：</span><span class="O_c_h"><fmt:formatNumber value="${subOrder.realPrice }" type="currency" pattern="￥0.00"/>
        		<c:if test="${subOrder.status == 0 && !empty subOrder.cashPay}"> 
            		(已支付 <em><fmt:formatNumber value="${subOrder.cashPay}" type="currency" pattern="￥0.00"/></em>)
                </c:if></span>
            </li>
            <li><span class="O_c_q">成交时间：</span><span class="O_c_h"><fmt:formatDate value="${subOrder.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            <li><span class="O_c_q">给卖家留言：</span><span class="O_c_h">${subOrder.noto }</span></li>
			<li><span class="O_c_q">快递公司：</span><span class="O_c_h"><c:if test="${subOrder.selfDelivery == 0}">${express}</c:if></span></li>
			<li><span class="O_c_q"><c:if test="${subOrder.expressType == '14660000000000000' }">卡券密码</c:if><c:if test="${subOrder.expressType != '14660000000000000' }">运单编号</c:if>：</span><span class="O_c_h">${expressNo}</span></li>
            <li><span class="O_c_q">收货地址：</span><span class="O_c_h">${subOrder.name}，${subOrder.mobile}，${subOrder.address }</span></li>
        </ul>
    </div>
    <div class="order_c_right">
    	<p class="P_title">订单状态</p>
        <div class="O_box_st" style="padding-left:40px;">
        	<input type="hidden" id="orderStatus" value="${subOrder.status }">
        	<c:if test="${subOrder.status == 0 }">
        		<input type="hidden" id="createTime" value="${subOrder.createTime.time }">
	        	<p class="O_ddzt">订单状态：<span>已下单，待支付</span></p>
	            <p class="O_time_c">您有  <span class="red padd" id="endTime"></span>  来付款，超时订单将自动关闭</p>
	            <p class="O_btn1">
	            	<span>您可以</span>
	            		<a href="/payment/pay?subOrderId=${subOrder.subOrderId }" class="a1">立即支付</a>
	            		<a href="javascript:void(0);" id="cancel" sorderid="${subOrder.subOrderId }"  class="a2">取消订单</a>
	            </p>
	            <select class="cancel-select" style="display:none;">
		            <option title="我不想买了" value="1">我不想买了</option>
		            <option title="信息错了，我重新拍" value="2">信息错了，我重新拍</option>
		            <option title="卖家缺货" value="3">卖家缺货</option>
		            <option title="付款遇到问题（余额不足，不会付款）" value="4">付款遇到问题（余额不足，不会付款）</option>
		            <option title="拍错了" value="5">拍错了</option>
		            <option title="其他原因" value="6">其他原因</option>
		     </select>
        	</c:if>
        	<c:if test="${subOrder.status == 1 && (subOrder.stockUp==0||empty subOrder.stockUp)}">
        	    <p class="O_ddzt">订单状态：<span>已支付，待发货</span></p>
	            <p class="O_time_c"></p>
	            <p class="${!subOrder.canUrgedDelivery? 'O_btn2':'O_btn1' }">
	            	<span>您可以</span>
	            	<c:if test="${! subOrder.canUrgedDelivery }">
	            		<a href="javascript:void(0);" class="a1">催促发货</a>
	            	</c:if>
	            	<c:if test="${subOrder.canUrgedDelivery}">
	            		<a href="javascript:void(0);" class="a1 canUrgedDelivery" sorderid="${subOrder.subOrderId }">催促发货</a>
	            	</c:if>
	            	<c:if test="${subOrder.selfDelivery == 1}">
	            		<a href="/member/toConfirmOrder?subOrderId=${subOrder.subOrderId }" class="a1" style="background: #ff6161;">确认收货</a><!-- <a href="#" class="a2">查看物流</a> -->
	            	</c:if>
	            	<span class="O_btn1">
	            		<a href="javascript:void(0);" id="cancel" sorderid="${subOrder.subOrderId }"  class="a2">取消订单</a>
	            	</span>
	            </p>
	            <select class="cancel-select" style="display:none;">
		            <option title="我不想买了" value="1">我不想买了</option>
		            <option title="信息错了，我重新拍" value="2">信息错了，我重新拍</option>
		            <option title="卖家缺货" value="3">卖家缺货</option>
		            <option title="付款遇到问题（余额不足，不会付款）" value="4">付款遇到问题（余额不足，不会付款）</option>
		            <option title="拍错了" value="5">拍错了</option>
		            <option title="其他原因" value="6">其他原因</option>
		     </select>
        	</c:if>
        	<c:if test="${subOrder.status ==1 && subOrder.stockUp==1}">
        		<p class="O_ddzt">订单状态：<span>已支付，备货中</span></p>
	            <p class="O_time_c"></p>
	            <p class="${!subOrder.canUrgedDelivery? 'O_btn2':'O_btn1' }">
	            	<span>您可以</span>
	            	<c:if test="${! subOrder.canUrgedDelivery }">
	            		<a href="javascript:void(0);" class="a1">催促发货</a>
	            	</c:if>
	            	<c:if test="${subOrder.canUrgedDelivery}">
	            		<a href="javascript:void(0);" class="a1 canUrgedDelivery" sorderid="${subOrder.subOrderId }">催促发货</a>
	            	</c:if>
	            	<c:if test="${subOrder.selfDelivery == 1}">
	            		<a href="/member/toConfirmOrder?subOrderId=${subOrder.subOrderId }" class="a1" style="background: #ff6161;">确认收货</a><!-- <a href="#" class="a2">查看物流</a> -->
	            	</c:if>
	            	<c:if test="${subOrder.orderProductType != 1}">
	            	<a href="/member/toReturnOrder?subOrderId=${subOrder.subOrderId }" class="a2">申请售后 </a>
	            	</c:if>
	            </p>
        	</c:if>
        	<c:if test="${subOrder.status == 2 }">
        		<input type="hidden" id="lasttakeTime" value="${subOrder.lasttakeTime.time }">
	        	<p class="O_ddzt">订单状态：<span>卖家已发货，待买家确认收货</span></p>
	            <p class="O_time_c">
	            	您有 <span class="red" id="endTime"></span> 来确认收货，超时订单将自动收货
	            </p>
	            <p class="O_btn1">
	            	<span>您可以</span><a href="/member/toConfirmOrder?subOrderId=${subOrder.subOrderId }" class="a1">确认收货</a><!-- <a href="#" class="a2">查看物流</a> -->
	            	<c:if test="${subOrder.orderProductType != 1}">
	            	<a href="/member/toReturnOrder?subOrderId=${subOrder.subOrderId }" class="a2">申请售后 </a>
	            	</c:if>
	            </p>
        	</c:if>
            <c:if test="${subOrder.status == 4 && commentable}">
		        <p class="O_ddzt">订单状态：<span>交易成功</span></p>
	            <p class="O_btn1">
	            	<span>您可以</span><a href="/member/ordereviews?order=${subOrder.subOrderId}" class="a1">评价</a><!--<a href="#" class="a2">查看物流</a>-->
	            	<c:if test="${subOrder.orderProductType != 1}">
	            	<a href="/member/toReturnOrder?subOrderId=${subOrder.subOrderId }" class="a2">申请售后 </a>
	            	</c:if>
	            </p>
        	</c:if>
            <c:if test="${subOrder.status == 4 && !commentable}">
	        	<p class="O_ddzt">订单状态：<span>已评价</span></p>
				<p class="O_btn1">
					<span>您可以</span><a href="/member/mycomments?state=1" class="a1">查看评价</a><!--<a href="#" class="a2">查看物流</a>-->
					<a href="/member/toReturnOrder?subOrderId=${subOrder.subOrderId }" class="a2">申请售后 </a>
				</p>
        	</c:if>
        	<c:if test="${subOrder.status == -1 }">
	        	<p class="O_ddzt_st">订单状态：<span>订单已关闭</span></p>
	        	<p class="O_ddzt_rs">关闭原因：<span>${subOrder.closeReason }</span></p>
        	</c:if>
        	<c:if test="${isAftermarketOrder}">
	        	<p class="O_ddzt">订单状态：<span><c:choose><c:when test="${subOrder.status == 11}">退货退款成功</c:when><c:when test="${subOrder.status == 12}">退款成功</c:when><c:otherwise>售后处理中</c:otherwise></c:choose></span></p>
				<p class="O_btn1">
					<span>您可以</span>
					<a href="/member/toReturnOrder?subOrderId=${subOrder.subOrderId }" class="a1">售后详情 </a>
				</p>
        	</c:if>
        </div>
        <p class="P_title" style="margin-top:30px;border-top:1px solid #ccc"><c:if test="${subOrder.expressType == '14660000000000000' }">电子卡券</c:if><c:if test="${subOrder.expressType != '14660000000000000' }">物流动态</c:if></p>
       	<ul class="wl_xx" style="display: none">
       	</ul>
       	<c:if test="${subOrder.expressType == '14660000000000000' }"><p class="P_title" style="padding-left:40px;">卡券密码：${expressNo}</p></c:if>
       	<c:if test="${eCardUrl != '' }"><p class="P_title" style="padding-left:40px;"><a href="javascript:;" style="color: #2b8dff;" id="detail_btn">查看详情</a></p></c:if>
       	
    </div>
</div>

<c:if test="${eCardUrl != '' }">
<div class="thickdiv" style="display: none;"></div>
<!-- 卡券详情弹层 -->	
	<div class="main_box" style="display: none;">
		<p class="p_tit">建议在“我的网”微信公众号中，查看此订单</p>
		<a href="javascript:;">X</a>
		<iframe scrolling="no" src="${eCardUrl}?iframe=1&pw=${expressNo}"></iframe>
	</div>
</c:if>

<script type="text/javascript">
$(document).ready(function(){
	$(".canUrgedDelivery").click(function(){
		var soid=$(this).attr("sorderid");
		$.ajax({
			type: "GET",
			url: "/member/urgedDelivery/"+soid,
			success: function(data){
				if(data.success){
					wode.showBox("操作结果","催促成功",{"icon":"success","hideBtn":true});
					//$(".canUrgedDelivery").remove();
					var str = "<span>您可以</span><a class=\"a1\" href=\"javascript:void(0);\">催促发货</a>";
					$(".O_btn1").removeClass("O_btn1").addClass("O_btn2").html(str);
				}else{
					wode.showBox("操作结果","催促失败:"+data.msg,{"hideBtn":true});
				}
			}
		});
		
	});

	//物流情报显示
	if('${expressCom}' != '' && 'null'!='${expressCom}') {
    	var content = '"sname":"express.ExpressSearch","com":"${expressCom}","express_no":"${expressNo}","user":${searchId},"version":"v2"';
    	$.ajax({
            url: 'http://kuaidi.wo-de.com/express/busJsonp.do?content={' +content+ '}&token=',
    		dataType : 'jsonp',
    	    jsonp:'jsonpcallback',
    	    success: function(json){
    	    	if(json.body){
	    	    	var ary = eval(json.body.history)
	    	    	var lis="";
	    	    	for(var i=0; i<ary.length; i++){  
		    	     	lis += "<li><span>"+ary[i].dealDate+"</span>"+ary[i].des+"</li>";
		    	  	}
	    	    	if(lis!="") {
	    	    		$(".wl_xx").html(lis);
	    	    		$(".wl_xx").show();
	    	    	}
    	    	}
    	    }
    	});
	}
	$(".annotation img").mouseover(function(){			
		$('.anno_con').fadeIn();
	});
	$(".annotation img").mouseout(function(){			
		$('.anno_con').fadeOut();
	});
	
	
	
	$("#detail_btn").click(function(e){
		$(".thickdiv").show();
		$(".main_box").show();
	});

	$(".main_box a").click(function(e){
		$(".thickdiv").hide();
		$(".main_box").hide();
	});
	
	//物流情报显示
	if('${eCardUrl}' != '') {
		$(".thickdiv").show();
		$(".main_box").show();
	}
	
});
</script>
<div class="clear"></div>
<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
<%@ include file="../common/footer.jsp" %>
 <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>