<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
	String path = request.getContextPath();
	if(request.getServerPort() != 80 && request.getServerPort() != 443) {
		path=":"+request.getServerPort()+path;
	}
	String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-个人中心</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>css/global.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>css/my_hl.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/pagination/pagination.css" media="screen">
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/member.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/pagination/jquery.pagination.js"></script>
</head>
<body>
<%@ include file="../common/header_03.jsp" %>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
<!--left nav-->
<%@ include file="menu.jsp" %>	
<!--left nav end-->
<!--right content-->
	<div class="Me_content">
    	<%@ include file="my_hlb.jsp" %>
        <div class="table_top">
        	<ul>
        		<li class="w344">商品</li>
        		<li class="w90">单价</li>
        		<li class="w90">数量</li>
        		<li class="w90">总价</li>
        		<li class="w110">使用换领币</li>
        		<li class="w80">现金支付</li>
        		<li class="w124">
					<select id='orderExchangeStatus'>
						 <option value ="">订单状态</option>
						 <option value ="2"<c:if test="${exchangeStatus == 2 }">selected="selected"</c:if>>匹配成功</option>
						 <option value ="3"<c:if test="${exchangeStatus == 3 }">selected="selected"</c:if>>匹配失败</option>
						 <option value="1"<c:if test="${exchangeStatus == 1 }">selected="selected"</c:if>>匹配中</option>
						 <option value="4"<c:if test="${exchangeStatus == 4 }">selected="selected"</c:if>>匹配失败,已调剂</option>
						 <option value="0"<c:if test="${exchangeStatus == 0 }">selected="selected"</c:if>>待支付</option>
						 <option value="-1"<c:if test="${exchangeStatus == -1 }">selected="selected"</c:if>>已关闭</option> 
					</select>
				</li>
        		<li class="w90">操作</li>
        	</ul>
        </div>
        <c:forEach items="${page.list}" var="list">
        <%-- <c:if test="${list.exchangeStatus!=-1}"> --%>
	        <div class="pro_box">
	        	<div class="pro_box_top"><p class="p1">匹配单号：${list.subOrderId}</p><p class="p2">${list.supplierName}</p><p class="p3">状态更新日期：<fmt:formatDate value="${list.updateTime}" pattern="yyyy-MM-dd"/></p></div>
	        	<c:forEach items="${list.subOrderItems}" var="subOrderItems">
	        		<div class="pro_con green_bg">
	        		<table border="0" cellspacing="0" cellpadding="0">
	        			<tr>
		        			<td class="w344">
		        				<dl>
		        					<dt><a href="<%=basePath %>${subOrderItems.productId}.html?skuId=${subOrderItems.skuId}?pageKey=huanling"><img src="${subOrderItems.image}" /></a></dt>
		        					<dd class="dd1"><a href="<%=basePath %>${subOrderItems.productId}.html?skuId=${subOrderItems.skuId}?pageKey=huanling">${subOrderItems.productName}</a></dd>
		        					<%-- <c:forEach items="${subOrderItem.proValues.keySet()}" var="key" varStatus="keyStatus"> --%>
		                                <dd>${subOrderItems.itemValues} </dd>
		                           <%--  </c:forEach> --%>
		        					<%-- <dd>${subOrderItems.itemValues}</dd> --%>
		        				</dl>
		        			</td>
			        		<td class="w90">￥${subOrderItems.internalPurchasePrice}</td>
			        		<td class="w90">${subOrderItems.number}</td>
			        		<td class="w90">￥${subOrderItems.number*subOrderItems.internalPurchasePrice}</td>
			        		<td class="w110">${list.totalAdjustment}</td>
			        		<c:choose>
							   <c:when test="${!empty list.thirdPay}">  
							    	<td class="w80">￥${list.thirdPay}</td>
							   </c:when>
							   <c:otherwise> 
							 	    <td class="w80"></td>
							   </c:otherwise>
							</c:choose>
			        		<c:if test="${list.exchangeStatus==1}">
			        			<td class="w124">匹配中</td>
				        		<td class="w90">
				        			<a href="javascript:void(0);" class="cancelOrder" soid="${list.subOrderId}">取消匹配</a><br />
				        			<a href="<%=basePath %>${subOrderItems.productId}.html?skuId=${subOrderItems.skuId}&pageKey=huanling">查看商品详情</a>
				        		</td>
			        		</c:if>
			        		<c:if test="${list.exchangeStatus==2}">
				        		<td class="w124 col_green">匹配成功</td>
				        		<td class="w90">
				        			<a href="/member/orderDetail?subOrderId=${list.subOrderId}">查看订单</a><br />
				        			<a href="<%=basePath %>${subOrderItems.productId}.html?skuId=${subOrderItems.skuId}&pageKey=huanling">查看商品详情</a>
				        		</td>
			        		</c:if>
			        		<c:if test="${list.exchangeStatus==3}">
				        		<td class="w124 col_yellow">匹配失败</td>
				        		<td class="w90">
				        			<a href="${basePath}/huanling.html">挑选其他商品</a><br />
				        		</td>
			        		</c:if>
			        		<c:if test="${list.exchangeStatus==4}">
				        		<td class="w124 col_yellow">匹配失败<br />已调剂</td>
				        		<td class="w90">
				        			<a href="javascript:test(${list.batchId})">查看调剂详情</a>
				        		</td>
			        		</c:if>
			        		<c:if test="${list.exchangeStatus==-1}">
				        		<td class="w124 col_yellow">已关闭</td>
				        		<td class="w90">
				        			<a class="deleteOrder" href="javascript:void(0);" soid="${list.subOrderId}">删除订单</a>
				        		</td>
			        		</c:if>
			        		<c:if test="${list.exchangeStatus==0}">
				        		<td class="w124 col_yellow">待支付</td>
				        		<td class="w90">
				        			<a class="payment" href="javascript:void(0);" soid="${list.subOrderId }">立即付款</a><br />
				        			<a href="javascript:void(0);" class="cancelOrder" soid="${list.subOrderId}">取消匹配</a><br />
				        		</td>
			        		</c:if>
		        		</tr>
	        		</table>
	        	</div>
	        </c:forEach>
	        </div>
	       <%--  </c:if> --%>
        </c:forEach>
        
<div style="text-align: right;margin:30px 0 10px 0;">
 		<jsp:include page="../common/page.jsp" flush="true">
            <jsp:param name="page" value="${page}"/>
        </jsp:include>
</div><!-- page end -->
<select class="cancel-select" style="display:none;">
        <option title="我不想买了" value="1">我不想买了</option>
        <option title="信息错了，我重新拍" value="2">信息错了，我重新拍</option>
        <option title="卖家缺货" value="3">卖家缺货</option>
        <option title="付款遇到问题（余额不足，不会付款）" value="4">付款遇到问题（余额不足，不会付款）</option>
        <option title="拍错了" value="5">拍错了</option>
        <option title="其他原因" value="6">其他原因</option>
 </select>
</div>
<!--right contont end-->
<div class="clear:after"></div>
</div>
<div class="clear">
	<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
</div>
<%@ include file="../common/footer.jsp" %>
<script type="text/javascript" src="/resources/js/top_ewm.js"></script>
</body>
<script type="text/javascript">
//var pageSize=5;//每页显示多少页  
	$(function(){
		$("#v1").addClass("crr");
		$("#v2").removeClass("crr");
		$(".deleteOrder").click(function(){
			var sorderid = $(this).attr("soid");
			
			wode.showBox("删除订单确认","你确认删除订单吗？");
			
			$("#boxCheck").click(function(){
				location.href="/member/deleteExOrder?subOrderId="+sorderid;
			});
		});
		/**
		 * 立即付款
		 * */
		$(".payment").click(function(){
			var sorderid = $(this).attr("soid");
			$.ajax({
	            type: "GET",
	            url: "/order/orderStatus",
	            data:{"subOrderId":sorderid,"payType":5},
	            success: function (data) {
	            	var status = data.data;
	                if (status==0) {//未支付
	                	location.href="/payment/pay?payType=5&subOrderId="+sorderid;
	                	
	                } else if(status==1){//已支付
	                	wode.showBox("订单信息","您的订单已付款");
	                	$("#boxCheck").click(function(){
	                		$(".box").hide();
	                	});
	                }else{
	                	wode.showBox("订单信息","您的订单状态有误,请刷新");
	                	$("#boxCheck").click(function(){
	                		$(".box").hide();
	                	});
	                }
	            }
	        });
		});
		/**
		 * 取消订单弹窗
		 */
		$(".cancelOrder").on('click',function(){
			var sorderid = $(this).attr("soid");
			$(".Me_content select option:eq(0)").attr("selected","selected");		
			
			wode.showBox("取消订单确认","请选择取消理由",{"longContent":"<select class='o-select-input wor210'>"+$(".cancel-select").html()+"</select>"});
			
			/**
			 * 订单取消确认
			 */
			$("#boxCheck").click(function(){
				$.ajax({
		            type: "GET",
		            url: "/member/cancelExOrder?subOrderId="+sorderid+"&closeReason="+$(".cancel-select option:selected").text(),
		            success: function (data) {
		            	if(data.success==true){
		            		location.reload();
		            	}else{
		            	}
		            }
		        });
				
			});
		});
	});
	function test(batchId){
	location.href="/member/findAdJust?batchId="+batchId;	
}
</script>
</html>