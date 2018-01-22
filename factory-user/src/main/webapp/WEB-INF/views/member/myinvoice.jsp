<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="../common/include.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <title>我的福利-我的发票</title>
    <link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css"/>
    <script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
    
    <script type="text/javascript" src="<%=basePath %>resources/js/member.js"></script>
    <script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
</head>
<body>

<!--top begin-->
<%@ include file="../common/header_03.jsp" %>
<!--top end--><!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
    <!--left nav-->
    <%@ include file="menu.jsp" %>
    <!--left nav end--><!--right content-->
    <div class="Me_content">
        <div class="Me-order-title">
        我的发票
        </div>
        <c:if test="${!empty invoiceList }">
            <ul class="Me-order-con">
                <c:forEach var="invoice" items="${invoiceList }">
                    <li>
                        <p class="Me-order-top">
                            <span class="T-xz"></span> <span class="T-time" style="text-align:center;"><fmt:formatDate pattern="yyyy-MM-dd" value="${invoice.createtime}"/>  </span> 
                            <span class="T-dp" style="float:right;margin-right: 10px;">
                            <c:if test="${not empty invoice.issuedInvoice}" >发票已开</c:if>
                            <c:if test="${empty invoice.issuedInvoice}" >开票已申请</c:if>
                            </span>
                        </p>
                        <div class="orderLeft" style="width:429px;height:230px;">
		                        <div class="Me-order-list"  style="width:429px;">
		                            <div class="M-product" style="color:#434343">
				                        订单编号：${invoice.suborderid}<br>
				                        发票抬头：${invoice.title}<br>
				                        邮寄地址：${invoice.address}<br>
				           <c:if test="${invoice.billType != null}">        
					                        发票类型：
					            <c:if test="${invoice.billType==0}">普通发票</c:if> 
	          				    <c:if test="${invoice.billType==1}">专用发票</c:if>
	          				    <br>
					                        纳税人识别号：${invoice.taxpayerNumber}<br>
					          <c:if test="${invoice.billType == 1}">           
					                        注册地址：${invoice.registerAddress}<br>
					                        注册电话：${invoice.registerPhone}<br>
					                        开户行：${invoice.openingBan}<br>
					                        开户行账号：${invoice.openingBanNumber}<br>
				            </c:if>   </c:if>         
                        </div>
                        </div>
	                    </div>
                    	<div class="orderRight" style="width:530px;margin-top:17px;" >
                    	 <c:if test="${not empty invoice.issuedInvoice}" >
				                <span class="s1" style="text-align:left;width:280px;color:#434343">
				                发票类型：  <c:if test="${invoice.issuedInvoice.type=='0'}">电子增值税普通发票</c:if> 
          				    <c:if test="${invoice.issuedInvoice.type=='1'}">电子增值税专用发票</c:if> 
          				   <c:if test="${invoice.issuedInvoice.type=='2'}">纸质发票</c:if> <br>
				                 抬头：${invoice.issuedInvoice.title}<br>
				                  发票号：${invoice.issuedInvoice.invoice}<br>
				                 销售方：${invoice.issuedInvoice.seller}<br>
					            价税合计：${invoice.issuedInvoice.price}<br>
					                                                                                    开票日期 ：<fmt:formatDate pattern="yyyy-MM-dd" value="${invoice.issuedInvoice.createtime}"/><br>
					            </span>
					            </c:if>
	                            <div class="s4">
	                            	 <c:if test="${not empty invoice.issuedInvoice}" >
	                            	  <p><a target="_blank" href="/invoice/getInvoice?suborderid=${invoice.suborderid}">
	                                                                                                    发票详情
	                            	  </a></p>
	                            	  </c:if>
	                                    	<p><a target="_blank" href="/member/orderDetail?subOrderId=${invoice.suborderid }">订单详情</a></p>
	                            </div>
                        	</div>
                    	<div class="clear"></div>
                    </li>
                </c:forEach>
            </ul>
        </c:if>
    </div>
    <!--right contont end-->
    <div class="clear:after"></div>
</div>
<div class="clear"></div>
<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
<%@ include file="../common/footer.jsp" %>
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>