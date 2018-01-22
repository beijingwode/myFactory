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
<link rel="stylesheet" href="<%=basePath %>css/otherPrompt.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
 
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/newreturnOrder.js"></script>
<style>
.wl_xx li span{width:270px;color:#6a6a6a;padding-right:30px;display:block;}
.newReturn{width: 990px;height: auto !important;height: 94px;min-height: 94px;background: #fff;margin: 0 auto;padding-top: 36px;}
.newReturn p{width: 916px;height: 59px;margin: 0 auto;background-image: url(../images/order_nav_img_new.png);}
.newReturnOrder{background-position: 0 -1.5px;}
.newReturn_0{background-position: 0 -64px;}
.newReturn_2{background-position: 0 -125px;}
.newReturn_3{background-position: 0 -184px;}
.newReturn_4{background-position: 0 -245px;}
.newReturn_6{background-position: 0 -307px;}
.newReturn_1{background-position: 0 -367px;}
.O_deal_13{background-position: 0 -530px;}

.annotation{width:15px;height:15px;float:left;margin:0 0 0 20px;position:relative;}
.annotation img{cursor:pointer;}
.anno_con{width:190px;height:106px;padding:0 14px 14px 14px;background:#fff;border:1px solid #dadee5;border-bottom:2px solid #ff4040;border-radius:6px;position:absolute;display:none;}
.anno_con span{width:190px;height:40px;display:block;text-align:center;line-height:40px;font-size:14px;color:#262626;border-bottom:1px solid #dadee5;}
.anno_con p{margin-top:10px;text-align:left;line-height:20px;font-size:12px;color:#262626;}
.anno_con i{width:9px;height:8px;display:block;position:absolute;left:102px;bottom:-1px;}
.anno_con p em{font-style:normal;color:#ff4040;padding:0 2px;}
</style>
</head>
<body>
<!--top begin-->
<%@ include file="../common/header_03.jsp" %>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<c:if test="${empty returnOrder and empty refundOrder}">
<div class="Deal_nav" id="header">
  	<p class="O_deal"></p>
</div>
</c:if>
<c:if test="${not empty returnOrder}">
<div class="newReturn" id="header">
  	<c:if test="${returnOrder.status == 0}">
 		<p class="newReturn_0"></p>
 	</c:if>
 	<c:if test="${returnOrder.status == 2}">
 		<p class="newReturn_2"></p>
 	</c:if>
 	<c:if test="${returnOrder.status == 3}">
 		<p class="newReturn_3"></p>
 	</c:if>
 	<c:if test="${returnOrder.status == 4}">
 		<p class="newReturn_4"></p>
 	</c:if>
 	<c:if test="${returnOrder.status == 6}">
 		<p class="newReturn_6"></p>
 	</c:if>
 	<c:if test="${returnOrder.status == 1}">
 		<p class="newReturn_1"></p>
 	</c:if>
</div>
</c:if>
<c:if test="${empty returnOrder && !empty refundOrder}">
<div class="Deal_nav" id="header">
 	<c:if test="${refundOrder.status == 1 }">
 		<p class="O_deal_2"></p>
 	</c:if>
 	<c:if test="${refundOrder.status == 10}">
 		<p class="O_deal_3"></p>
 	</c:if>
 	<c:if test="${refundOrder.status == 4}">
 		<p class="O_deal_13"></p>
 	</c:if>
 	<c:if test="${refundOrder.status == 3}">
 		<p class="O_deal_11"></p>
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
        <ul class="O_con newbom">
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
            </li>            <li><span class="O_c_q">成交时间：</span><span class="O_c_h"><fmt:formatDate value="${subOrder.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            <li><span class="O_c_q">收货地址：</span><span class="O_c_h">${subOrder.name}，${subOrder.mobile}，${subOrder.address }</span></li>
        </ul>
    </div>


    <%--已申请退货并退款--%>
    <c:if test="${!empty returnOrder }">
    	<input type="hidden" name="returnOrderId" id="returnOrderId" value="${returnOrder.returnOrderId }">
    	<c:if test="${returnOrder.status >= 0 }">
    		<div id="newrefund_wrap" class="order_c_right">
            	<p class="P_title">售后申请</p>
           		 <div  class="newrefund_wrap">
            		<div class="newrefund_one applyAfter">
                		<span><b class="red">*</b>售后类型：</span>
               		 	<div class="R_cont">
                    		<div class="r_select">
                          	 	 退货退款
                    		</div>
                		</div>
           			</div>
        			<div class="newrefund_one applyAfter">
            			<span><b class="red">*</b>退款原因：</span>
            			<div class="R_cont">
                    	<div class="r_select">
                          	 ${returnOrder.reason}
                    	</div>
               	 		</div>
            		</div>
           			 <div class="newrefund_one applyAfter">
            			<span><b class="red">*</b>退款金额：</span>
                		<div class="R_cont">
                   		 <input class="money_txt" type="text" name="returnPrice" value="${returnOrder.returnPrice}" maxlength="10" readonly="readonly">
                   		 <strong>最多<b class="red">${subOrder.realPrice- trialReturn }</b>元(含运费<b class="red">${subOrder.totalShipping }</b>元)</strong>
                		</div>
            		</div>
            		<div class="newrefund_one applyAfter">
            				<span>备注：</span>
            				<div class="r_select">${returnOrder.note}</div>
            		</div>
            		<div class="newrefund_one applyAfter">
            			<span>上传凭证：</span>
                		<div class="R_cont">
                    		<ul class="pho_list">
                    		<c:forEach items="${returnOrder.returnorderAttachmentList}" var="attachmentList">
                    			<img src="${attachmentList.image }" width="120" height="120" onerror="this.src='<%=basePath %>images/uploadpic.jpg'">
                    		</c:forEach>
                        	<%-- <li>
                        		<a href="#"><img id="uploadpic3" src="${returnOrder.attachmentList[2].image}" width="120" height="120" alt="" onerror="this.src='<%=basePath %>images/uploadpic.jpg'"></a>
                        		<input type="file" id="avatar3" name="avatar3"  style="display:none;" onchange="uploadImage('avatar3','uploadpic3','images3');"/>
                        		<input type="hidden" id="images3" name="images" value="${returnOrder.attachmentList[2].image}"> 
                        	</li> --%>
                    		</ul>
                		</div>
            		</div>
            		<c:if test="${returnOrder.status == 2 }">
            			<div class="newrefund_one applyAfter">
            				<span>退货地址：</span>
            				<div class="r_select" style="width: auto;">${subOrder.returnedAddress}</div>
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
            			<div class="refund_submitbtn"><a id="sendReturnOrder" href="javascript:sendReturnOrder();">发出退货</a></div>
			            </c:if>
			            <c:if test="${returnOrder.status == 4 || returnOrder.status == 6 ||returnOrder.status == 1 }">
			            	<div class="newrefund_one applyAfter">
            				<span>物流信息：</span>
            				<div class="r_select" style="width: auto;"><c:if test="${returnOrder.goodsStatus eq 1}">商家已签收</c:if><c:if test="${returnOrder.goodsStatus eq 0 ||returnOrder.goodsStatus==null}">快递发出</c:if>&nbsp;&nbsp;${compInfo.name} &nbsp;&nbsp;${returnOrder.expressNo}&nbsp;&nbsp;<a href="javascript:showlistlogInfoAlert();" style="color: #2b8dff">查看物流</a>
            				</div>
            				</div>
			            </c:if>
			            <div class="clear"></div>
			            <div class="newfriend_info" <c:if test="${returnOrder.status == 2 }">style="display:none"</c:if>>
			            <c:if test="${returnOrder.status == 0 }">
			            	<h3>等待商家处理退货申请</h3>
			                <ul class="friend_list">
			                	<li>· 如果商家同意，请按照指定的退货地址发货，商家收货后会进行退款</li>
			                    <li>· 如果商家拒绝，可根据商家意见修改退货申请</li>
			                    <li>· 如果商家反馈不及时或不能令您满意，可随时申请平台介入， 平台售后服务电话：010-56206418</li>
			                </ul>
			             </c:if>
			             <c:if test="${returnOrder.status == 1 }">
			             	<h3>退货退款已完成</h3>
                    		<ul class="friend_list">
                       		<li>· 如果售后服务不能令您满意，可随时申请平台介入，
  							 平台售后服务电话：010-56206418</li>
                   			</ul>
			             </c:if>
			             <c:if test="${returnOrder.status == 3 }">
			             	<h3>商家不同意退货</h3>
                    		<ul class="friend_list">
                       		<li>拒绝理由：${subOrder.refuseNote }</li>
                        	<li>您还可以：<a href="javascript:void(0);" onclick="newUpdateReturnOrderShow()" style="color: #2b8dff">修改售后申请</a> &nbsp; &nbsp; 或申请平台介入，
 								  平台售后服务电话：010-56206418</li>
                    		</ul>
			             </c:if>
			             <c:if test="${returnOrder.status == 4 }">
			             	<h3>等待商家处理退款申请</h3>
                    		<ul class="friend_list">
                       			<li>· 如果商家确认退款，退款金额会退回到您下单时的支付账户</li>
                        		<li>· 如果商家拒绝，可根据商家意见修改退货申请</li>
                        		<li>· 如果商家反馈不及时或不能令您满意，可随时申请平台介入，
 								  平台售后服务电话：010-56206418</li>
                   			 </ul>
			             </c:if>
			             <c:if test="${returnOrder.status == 6 }">
			             	<h3>商家不同意退款</h3>
                    		<ul class="friend_list">
                       		<li>拒绝理由：${subOrder.refuseNote }</li>
                        	<li>您还可以：<a href="javascript:void(0);" onclick="newUpdateReturnOrderShow()"  style="color: #2b8dff">修改售后申请</a> &nbsp; &nbsp; 或申请平台介入，
 								  平台售后服务电话：010-56206418</li>
                    		</ul>
			             </c:if>
			           </div>
			      </div>
    		</div>
    	</c:if>
    </c:if>

<%--申请仅退款操作--%>
    <c:if test="${!empty refundOrder }">
    <c:if test="${refundOrder.status >= 1 }">
        <div class="order_c_right">
            <p class="P_title">售后申请</p>
            <div class="newrefund_wrap">
            <div class="newrefund_one applyAfter">
                <span><b class="red">*</b>售后类型：</span>
                <div class="R_cont">
                    <div class="r_select">
                          	  仅退款
                    </div>
                </div>
            </div>
        	<div class="newrefund_one applyAfter">
            	<span><b class="red">*</b>退款原因：</span>
            	<div class="R_cont">
                    <div class="r_select">
                          	 ${refundOrder.reason}
                    </div>
                </div>
            </div>
            <div class="newrefund_one applyAfter">
            	<span><b class="red">*</b>退款金额：</span>
                <div class="R_cont">
                    <input class="money_txt" type="text" name="returnPrice" value="${refundOrder.refundPrice}" maxlength="10" readonly="readonly">
                    <strong>最多<b class="red">${subOrder.realPrice- trialReturn }</b>元(含运费<b class="red">${subOrder.totalShipping }</b>元)</strong>
                </div>
            </div>
            <div class="newrefund_one applyAfter">
            	<span>备注：</span>
            	<div class="R_cont">
                    <div class="r_select">
                          	 ${refundOrder.note}
                    </div>
                </div>
            	
            </div>
            <div class="newrefund_one applyAfter">
            	<span>上传凭证：</span>
                <div class="R_cont">
                    <ul class="pho_list">
                    	<c:forEach items="${refundOrder.attachmentList}" var="attachmentList">
                    			<img src="${attachmentList.image }" width="120" height="120" onerror="this.src='<%=basePath %>images/uploadpic.jpg'">
                    	</c:forEach>
                        <%-- <li>
                        	<a href="#"><img id="uploadpic3" src="${refundOrder.attachmentList[2].image}" width="120" height="120" alt="" onerror="this.src='<%=basePath %>images/uploadpic.jpg'"></a>
                        	<input type="file" id="avatar3" name="avatar3"  style="display:none;" onchange="uploadImage('avatar3','uploadpic3','images3');"/>
                        	<input type="hidden" id="images3" name="images" value="${refundOrder.attachmentList[2].image}"> 
                        </li> --%>
                    </ul>
                </div>
            </div>
                <div class="clear"></div>
                <div class="newfriend_info">
                	<c:if test="${refundOrder.status == 1 }">
                    <h3>等待商家处理退款申请</h3>
                    <ul class="friend_list">
                        <li>· 如果商家确认退款，退款金额会退回到您下单时的支付账户</li>
                        <li>· 如果商家拒绝，可根据商家意见修改退货申请</li>
                        <li>· 如果商家反馈不及时或不能令您满意，可随时申请平台介入，
 								  平台售后服务电话：010-56206418</li>
                    </ul>
                    </c:if>
                    <c:if test="${refundOrder.status ==4 }">
                    <h3>商家不同意退款</h3>
                    <ul class="friend_list">
                        <li>拒绝理由：${subOrder.refuseNote }</li>
                        <li>您还可以：<a href="javascript:void(0);" onclick="newUpdateReturnOrderShow()"  style="color: #2b8dff">修改售后申请</a> &nbsp; &nbsp; 或申请平台介入，
 								  平台售后服务电话：010-56206418</li>
                    </ul>
                    </c:if>
                    <c:if test="${refundOrder.status ==10 }">
                    <h3>退款已完成</h3>
                    <ul class="friend_list">
                        <li>· 如果售后服务不能令您满意，可随时申请平台介入，
  							 平台售后服务电话：010-56206418</li>
                    </ul>
                    </c:if>
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
        <div class="newrefund_wrap">
            <div class="newrefund_one">
                <span><b class="red">*</b>执行操作：</span>
                <div class="R_cont">
                    <div class="R_select">
                        <select id="type" name="type" onchange="chooseType()">
                            <option value="0">退货并退款</option>
                            <option value="1" selected="selected">仅退款</option>
                        </select>
                    </div>
                </div>
            </div>

        	<div class="newrefund_one">
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
            <div class="newrefund_one jj">
            	<span><b class="red">*</b>退款金额：</span>
                <div class="R_cont">
                	<input type="hidden" id="price" value="${subOrder.realPrice- trialReturn}">
                    <input class="money_txt" type="text" id="returnPrice" name="returnPrice" value="${subOrder.realPrice - trialReturn}" maxlength="10">
                    <strong>最多<b class="red">${subOrder.realPrice- trialReturn }</b>元(含运费<b class="red">${subOrder.totalShipping }</b>元)</strong>
                </div>
            </div>
            <div class="newrefund_one">
            	<span>退款说明：</span>
                <textarea class="textarea_txt" id="note" name="note"></textarea>
            </div>
            <div class="newrefund_one">
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
            <div class="refund_submitbtn"><a id="createReturnOrder" href="javascript:void(0);">提交申请</a></div>
        </div>
        </form>
    </div>
    </c:if>
    
    <c:if test="${not empty returnOrder || not empty refundOrder }">
    <div id="newUpdateReturnOrder" style="display:none;" class="order_c_right">
    	<form id="returnOrderForm" action="/member/updateReturnOrder" method="post">
    	<input type="hidden" name="subOrderId" value="${subOrder.subOrderId }">
    	<p class="P_title">退货申请</p>
        <div class="newrefund_wrap">
            <div class="newrefund_one">
                <span><b class="red">*</b>执行操作：</span>
                <div class="R_cont">
                    <div class="R_select">
                        <select id="type" name="type" onchange="chooseType()" <c:if test="${returnOrder.status ==6}" >disabled="disabled"</c:if>>
                            <option value="0" <c:if test="${not empty returnOrder }" >selected="selected"</c:if>>退货并退款</option>
                            <option value="1" <c:if test="${empty returnOrder }" >selected="selected"</c:if>>仅退款</option>
                        </select>
                    </div>
                </div>
            </div>

        	<div class="newrefund_one">
            	<span><b class="red">*</b>退款原因：</span>
                <div class="R_cont">
                    <div class="R_select">
                        <select id="reason" name="reason" <c:if test="${returnOrder.status ==6 }">disabled="disabled"</c:if>>
                            <option value="">请选择退货理由</option>
                        <c:if test="${not empty returnOrder }" >
                            <option <c:if test="${returnOrder.reason =='收到商品破损' }" >selected="selected"</c:if>  value="收到商品破损">收到商品破损</option>
                            <option <c:if test="${returnOrder.reason =='商品错发/漏发' }" >selected="selected"</c:if> value="商品错发/漏发">商品错发/漏发</option>
                            <option <c:if test="${returnOrder.reason =='商品需要维修' }" >selected="selected"</c:if> value="商品需要维修">商品需要维修</option>
                            <option <c:if test="${returnOrder.reason =='发票问题' }" >selected="selected"</c:if> value="发票问题">发票问题</option>
                            <option <c:if test="${returnOrder.reason =='收到商品与描述不符' }" >selected="selected"</c:if> value="收到商品与描述不符">收到商品与描述不符</option>
                            <option <c:if test="${returnOrder.reason =='商品质量问题' }" >selected="selected"</c:if> value="商品质量问题">商品质量问题</option>
                            <option <c:if test="${returnOrder.reason =='未按约定时间发货' }" >selected="selected"</c:if> value="未按约定时间发货">未按约定时间发货</option>
                        </c:if>
                        <c:if test="${empty returnOrder }" >
                            <option <c:if test="${refundOrder.reason =='收到商品破损' }" >selected="selected"</c:if>  value="收到商品破损">收到商品破损</option>
                            <option <c:if test="${refundOrder.reason =='商品错发/漏发' }" >selected="selected"</c:if> value="商品错发/漏发">商品错发/漏发</option>
                            <option <c:if test="${refundOrder.reason =='商品需要维修' }" >selected="selected"</c:if> value="商品需要维修">商品需要维修</option>
                            <option <c:if test="${refundOrder.reason =='发票问题' }" >selected="selected"</c:if> value="发票问题">发票问题</option>
                            <option <c:if test="${refundOrder.reason =='收到商品与描述不符' }" >selected="selected"</c:if> value="收到商品与描述不符">收到商品与描述不符</option>
                            <option <c:if test="${refundOrder.reason =='商品质量问题' }" >selected="selected"</c:if> value="商品质量问题">商品质量问题</option>
                            <option <c:if test="${refundOrder.reason =='未按约定时间发货' }" >selected="selected"</c:if> value="未按约定时间发货">未按约定时间发货</option>
                        </c:if>
                        </select>   
                    </div>
                    <p>如果您需要退还运费，可申请此退款，并提供有效凭证或与商家协商一致。</p>
                </div>
            </div>
            <div class="newrefund_one jj">
            	<span><b class="red">*</b>退款金额：</span>
                <div class="R_cont">
                	<input type="hidden" id="price" value="${subOrder.realPrice- trialReturn}">
                	<c:if test="${not empty returnOrder }" >
                    <input class="money_txt" type="text" id="returnPrice" name="returnPrice" value="${returnOrder.returnPrice}" maxlength="10">
                    </c:if>
                    <c:if test="${not empty refundOrder }" >
                    <input class="money_txt" type="text" id="returnPrice" name="returnPrice" value="${refundOrder.refundPrice}" maxlength="10">
                    </c:if>
                    <strong>最多<b class="red">${subOrder.realPrice- trialReturn }</b>元(含运费<b class="red">${subOrder.totalShipping }</b>元)</strong>
                </div>
            </div>
            <div class="newrefund_one">
            	<span>退款说明：</span>
            	<c:if test="${returnOrder.status !=6 }" >
                <textarea class="textarea_txt" id="note" name="note"><c:if test="${not empty returnOrder }" >${returnOrder.note}</c:if><c:if test="${empty returnOrder }" >${refundOrder.note}</c:if>
                </textarea>
                 </c:if> 
                <c:if test="${returnOrder.status ==6 }" >   
                           ${returnOrder.note}<input type="hidden" name="note" value=" ${returnOrder.note}">
                        </c:if>  
            </div>
            <div class="newrefund_one">
            	<span>上传凭证：</span>
                <div class="R_cont">
                <c:if test="${returnOrder.status !=6 }" >
                <c:if test="${not empty returnOrder }" >
                <ul class="pho_list">
                    	<li>
                    		<img id="uploadpic1" onerror="this.src='<%=basePath %>images/uploadpic.jpg'" src="${returnOrder.returnorderAttachmentList[0].image}" width="120" height="120">
  								<input type="file" id="avatar1" name="avatar1"  style="display:none;" onchange="uploadImage('avatar1','uploadpic1','images1');"/>
  								<input type="hidden" id="images1" name="images" value="${returnOrder.returnorderAttachmentList[0].image}"> 
                    		<!-- <i></i> -->
                    	</li>
                        <li>
                        	<a href="#"><img id="uploadpic2" onerror="this.src='<%=basePath %>images/uploadpic.jpg'" src="${returnOrder.returnorderAttachmentList[1].image}" width="120" height="120"></a>
                        	<input type="file" id="avatar2" name="avatar2"  style="display:none;" onchange="uploadImage('avatar2','uploadpic2','images2');"/>
                        	<input type="hidden" id="images2" name="images" value="${returnOrder.returnorderAttachmentList[1].image}"> 
                        </li>
                        <li>
                        	<a href="#"><img id="uploadpic3" onerror="this.src='<%=basePath %>images/uploadpic.jpg'" src="${returnOrder.returnorderAttachmentList[2].image}" width="120" height="120"></a>
                        	<input type="file" id="avatar3" name="avatar3"  style="display:none;" onchange="uploadImage('avatar3','uploadpic3','images3');"/>
                        	<input type="hidden" id="images3" name="images" value="${returnOrder.returnorderAttachmentList[2].image}"> 
                        </li>
                    </ul>
                </c:if>
                <c:if test="${empty returnOrder }" >
                    <ul class="pho_list">
                    	<li>
                    		<img id="uploadpic1" onerror="this.src='<%=basePath %>images/uploadpic.jpg'" src="${refundOrder.attachmentList[0].image}" width="120" height="120">
  								<input type="file" id="avatar1" name="avatar1"  style="display:none;" onchange="uploadImage('avatar1','uploadpic1','images1');"/>
  								<input type="hidden" id="images1" name="images" value="${refundOrder.attachmentList[0].image}"> 
                    		<!-- <i></i> -->
                    	</li>
                        <li>
                        	<a href="#"><img id="uploadpic2" onerror="this.src='<%=basePath %>images/uploadpic.jpg'" src="${refundOrder.attachmentList[1].image}" width="120" height="120"></a>
                        	<input type="file" id="avatar2" name="avatar2"  style="display:none;" onchange="uploadImage('avatar2','uploadpic2','images2');"/>
                        	<input type="hidden" id="images2" name="images" value="${refundOrder.attachmentList[1].image}"> 
                        </li>
                        <li>
                        	<a href="#"><img id="uploadpic3" onerror="this.src='<%=basePath %>images/uploadpic.jpg'" src="${refundOrder.attachmentList[2].image}" width="120" height="120"></a>
                        	<input type="file" id="avatar3" name="avatar3"  style="display:none;" onchange="uploadImage('avatar3','uploadpic3','images3');"/>
                        	<input type="hidden" id="images3" name="images" value="${refundOrder.attachmentList[2].image}"> 
                        </li>
                    </ul>
                    </c:if>
                    <p>每张图片大小不超过<b class="red">5M</b>，最多3张，支持<b class="red">GIF</b>、<b class="red">JPG</b>、<b class="red">PNG</b>、<b class="red">BMP</b>格式</p>
                 </c:if>  
                 <c:if test="${returnOrder.status ==6 }" > 
                 <c:forEach items="${returnOrder.returnorderAttachmentList}" var="attachmentList">
                    	<img src="${attachmentList.image }" width="120" height="120" onerror="this.src='<%=basePath %>images/uploadpic.jpg'">
                    	<input type="hidden"  name="images" value="${attachmentList.image}"> 
                  </c:forEach>
                 </c:if>
                </div>
            </div>
            <div class="refund_submitbtn"><a id="createReturnOrder" href="javascript:void(0);">提交申请</a></div>
        </div>
        <input type="hidden" id="returnOrderId" name="returnOrderId" value="${returnOrder.returnOrderId}">
        <input type="hidden" id="refundOrderId" name="refundOrderId" value="${refundOrder.refundOrderId}">
        </form>
    </div>
    </c:if>
</div>
<div class="popup_bg"></div>
<div class="sort_popup" id="showlistlogInfo">
	<input type="hidden" name="expressCom" id="expressCom" value="${expressCom}">
    <input type="hidden" name="searchId" id="searchId" value="${searchId}">
    <input type="hidden" name="expressNo" id="expressNo" value="${returnorder.expressNo}">
    <div class="popup_title">
        <span>物流信息</span>
        <label><img src="<%=basePath %>images/close.gif" width="14" height="14" alt="close"
                    onclick="cancelButton('showlistlogInfo');"></label>
    </div>
    <div class="sort_popup_cont">
        <div class="change_ln hmartop" id="listLogInfo">
        </div>
        <div class="clear"></div>
    </div>
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
	if('${expressCom}' != '' && '${expressCom}'!='null' && '${returnOrder.expressNo}'!='' && '${returnOrder.expressNo}'!='null') {
    	//var content = '"sname":"express.ExpressSearch","com":"${expressCom}","express_no":"${returnOrder.expressNo}","user":${searchId},"version":"v2"';
    	var content = '"sname":"express.ExpressSearch","com":"${expressCom}","express_no":"${returnOrder.expressNo}","version":"v2"';
    	$.ajax({
            url: 'http://kuaidi.wo-de.com/express/busJsonp.do?content={' +content+ '}&token=',
    		dataType : 'jsonp',
    	    jsonp:'jsonpcallback',
    	    success: function(json){
    	    	var ary = eval(json.body.history)
    	    	var lis="";
    	    	for(var i=0; i<ary.length; i++)  
	    	  	{  
	    	     	lis += "<li><span >"+ary[i].dealDate+"</span>"+ary[i].des+"</li>";
	    	  	}
    	    	if(lis!="") {
    	    		$("#listLogInfo").html(lis);
    	    	}
    	    }
    	});
		}
	
	//点击显示未显示的物流信息
	
	$(".flow_btn").toggle(
			  function(){
			  $(".D_height").removeClass("D_height01");},
			  function(){
			  $(".D_height").addClass("D_height01");}
			);
	$(".annotation img").mouseover(function(){			
		$('.anno_con').fadeIn();
	});
	$(".annotation img").mouseout(function(){			
		$('.anno_con').fadeOut();
	})
	
});
function chooseType(){
	//alert($("#type").val());
	if($("#type").val()==0){//退货退款
		$("#header").attr("class","newReturn")
		$(".newReturn p").attr("class","newReturnOrder");
	}else if($("#type").val()==1){
		$("#header").attr("class","Deal_nav")
		$(".Deal_nav p").attr("class","O_deal");
	}
}
function newUpdateReturnOrderShow(){
	$(".order_c_right").hide();
	$("#newUpdateReturnOrder").show();
	
}
</script>
   <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>