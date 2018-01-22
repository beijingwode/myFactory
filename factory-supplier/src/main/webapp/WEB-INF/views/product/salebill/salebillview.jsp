<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ include file="/commons/taglibs.jsp" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragram" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0"> 
<title>对账单详情</title>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>
<%-- <script type="text/javascript" src="<%=static_resources %>resources/layer/layer.min.js"></script> --%>
<style type="text/css">
	.input{position:relative}
	.hover{background:#ccc}
	.add_model{margin-top:0px}
	.addimagebutton{height:11px;line-height:11px;width:60px;font-size:12px;}
	table tbody tr td{text-align:center;} 
</style>
</head>
<body>
<!--content begin-->
<div id="content" style="width:990px;">
    <!--right begin-->
    <div class="right" style="float:left;">
        <div class="sort_title">${saleBill.title}</div>
        <div class="sort_wrap">
        	<div class="add_info_wrap">
                <div class="add_info">
                	<div class="add_model">
                    	<div class="name">本期账期周期：</div>
                        <fmt:formatDate value="${saleBill.startTime}" pattern="yyyy-MM-dd"/>--<fmt:formatDate value="${saleBill.endTime}" pattern="yyyy-MM-dd"/>
                    </div>
                    <div class="add_model">
                    	<div class="name">货款总额：</div>
                        <fmt:formatNumber value="${saleBill.receivePrice}" type="currency" pattern="0.00"/>
                    </div>
                    <div class="add_model"  style="word-break: break-all;">
                    	<div class="name">运费总额：</div>
                        <fmt:formatNumber value="${saleBill.carriagePrice}" type="currency" pattern="0.00"/>
                    </div>
                    <div class="add_model">
                    	<div class="name">佣金总金额：</div>
                    	<fmt:formatNumber value="${saleBill.commissionPrice}" type="currency" pattern="0.00"/>
                    </div>
                    <div class="add_model">
                    	<div class="name">佣金返还总金额：</div>
                    	<fmt:formatNumber value="${saleBill.refundAmount}" type="currency" pattern="0.00"/>
                    	 <c:if test="${not empty saleBill.refundId}"><a href="${basePath}/commissionRefund/toCommissionRefundView.html?commissionRefundId=${saleBill.refundId}">查看详细</a></c:if>
                    </div>
                    <div class="add_model">
                    	<div class="name">扣款总额：</div>
                    	<fmt:formatNumber value="${saleBill.deductPrice}" type="currency" pattern="0.00"/>
                    </div>
                    <div class="add_model">
                    	<div class="name">本期应收款总额：</div>
                    	<fmt:formatNumber value="${saleBill.payPrice}" type="currency" pattern="0.00"/>
                    </div>
                    <div class="add_model">
                    	<div class="name">结算内容：</div>
                    	<c:choose>
		                    <c:when test="${saleBill.closeType==7}">
		                       	 货款、运费、佣金
		                    </c:when>
		                    <c:when test="${saleBill.closeType==6}">
		                       	 货款、运费
		                    </c:when>
		                    <c:when test="${saleBill.closeType==5}">
		                       	 货款、佣金
		                    </c:when>
		                    <c:when test="${saleBill.closeType==4}">
		                       	 货款
		                    </c:when>
		                    <c:when test="${saleBill.closeType==3}">
		                       	 运费、佣金
		                    </c:when>
		                    <c:when test="${saleBill.closeType==2}">
		                       	 运费
		                    </c:when>
		                    <c:when test="${saleBill.closeType==1}">
		                       	 佣金
		                    </c:when>
		                    <c:otherwise>
		                    </c:otherwise>
		                </c:choose>
                    </div>
                    <div class="add_model">
                    	<div class="name">备注：</div>
                    	${saleBill.closeNote}
                    </div>
                    <c:if test="${saleBill.closeType==1||saleBill.closeType==2}">
                    <div class="add_model">
                    	<div class="name">结算方式：</div>
                   		<c:if test="${saleBill.payType==1}">
                   			结算到商家现金账户
                   		</c:if>
                   		<c:if test="${saleBill.payType==2}">
                    		${saleBill.payNote}
                   		</c:if>
                    </div>
                    </c:if>
                    <c:if test="${saleBill.closeType!=7&&saleBill.closeType!=1&&saleBill.closeType!=2}">
                    <div class="add_model">
                    	<div class="name">二次结算：</div>
                    	<c:if test="${saleBill.relationType==0}">
                    		<c:if test="${saleBill.closeType==6}">
                    			佣金未结算
                    		</c:if>
                    		<c:if test="${saleBill.closeType==5}">
                    			运费未结算
                    		</c:if>
                    	</c:if>
                    	<c:if test="${saleBill.relationType==1}">
                    		<a href="${basePath}/saleBill/toSaleBillView.html?saleBillId=${saleBill.relationKey}">查看佣金结算</a>
                    	</c:if>
                    	<c:if test="${saleBill.relationType==2}">
                    		<a href="${basePath}/saleBill/toSaleBillView.html?saleBillId=${saleBill.relationKey}">查看运费结算</a>
                    	</c:if>
                    </div>
                    </c:if>
                </div>
             </div>
             <div class="sale_list_wrap">
                <div class="sale_content" style="margin-left:10px;width:967px;">
                    <c:if test="${saleBill.closeType!=1&&saleBill.closeType!=2}">
	                <table class="table-c">
	                     <thead>
	                     	<th style="width:30px;">序号</th>
	                        <th style="width:60px;">订单号</th>
	                        <th style="width:75px;">付款日期</th>
	                        <th style="width:75px;">确认日期</th>
	                        <th style="width:75px;">退货日期</th>
	                        <th style="width:47px;">本企业订单</th>
	                        <th style="width:120px;">商品</th>
	                        <th style="width:60px;">商品分类</th>
	                        <th style="width:50px;">单价</th>
	                        <th style="width:30px;">数量</th>
	                        <th style="width:50px;">金额</th>
	                        <th style="width:50px;">优惠</th>
	                        <th style="width:50px;">应收货款</th>
	                        <th style="width:50px;">佣金比例</th>
	                        <th style="width:50px;">应付佣金</th>
	                        <th style="width:50px;">运费</th>
	                        <th style="width:60px;">应收账款</th>
	                    </thead>
                    	<tbody>
                    	<c:forEach items="${result.msgBody}" var="item" varStatus="status">
                    		<tr>
	                    	<td>${status.index+1}</td>
	                        <td>${item.subOrderId}</td>
	                        <td><fmt:formatDate value="${item.payTime}" pattern="yyyy-MM-dd"/></td>
	                        <td><fmt:formatDate value="${item.takeTime}" pattern="yyyy-MM-dd"/></td>
	                        <td><c:if test="${item.returnTime==null||item.returnTime==''}">-</c:if><fmt:formatDate value="${item.returnTime}" pattern="yyyy-MM-dd"/></td>
	                        <td><c:if test="${item.own==0}">是</c:if><c:if test="${item.own==1}">-</c:if></td>
	                        <td>${item.productName}</td>
	                        <td>${item.categoryName}</td>
	                        <td><fmt:formatNumber value="${item.salePrice}" type="currency" pattern="0.00"/></td>
	                        <td>${item.number}</td>
	                        <td><fmt:formatNumber value="${item.salePrice*item.number}" type="currency" pattern="0.00"/></td>
	                        <td><c:if test="${item.haveCheap==null||item.haveCheap==0}">无</c:if><c:if test="${item.haveCheap==3}">换领</c:if><c:if test="${item.haveCheap==5}">试用</c:if><c:if test="${item.haveCheap==1}">有</c:if></td>
	                        <td><c:if test="${item.status==-1}">-</c:if><fmt:formatNumber value="${item.realPrice}" type="currency" pattern="0.00"/></td>
	                        <td><fmt:formatNumber value="${item.commissionRatio}" type="currency" pattern="0.00"/>%</td>
	                        <td><c:if test="${item.status==-1}">-</c:if><fmt:formatNumber value="${item.commission}" type="currency" pattern="0.00"/></td>
	                        <td>${item.carriagePrice}</td>
	                        <td><fmt:formatNumber value="${item.payPrice * item.status}" type="currency" pattern="0.00"/></td>
	                        </tr>
                    	</c:forEach>
                    	</tbody>
                    </table>
                    </c:if>
                    <c:if test="${saleBill.closeType==1||saleBill.closeType==2}">
	                <table class="table-c">
                        <thead>
                        <th style="min-width:105px;">对账单ID</th>
                        <th style="width:110px;">结算周期</th>
                        <th style="width:90px;">货款</th>
                        <th style="width:85px;">运费</th>
                        <th style="width:85px;">佣金</th>
                        <th style="width:105px;">应收款</th>
                        <th style="width:75px;">状态</th>
                        <th style="width:75px;">发票</th>
                        <th style="width:100px;">结算日期</th>
                        <th style="min-width:120px;">操作</th>
                        </thead>
                        <tbody>
                        <c:forEach items="${relationList}" var="item" varStatus="status">
                            <tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>">
                                <td><a href="${basePath}/saleBill/toSaleBillView.html?saleBillId=${item.id}" target="_blank" style="word-break: break-all;">${item.billId}</a></td>
                                <td><fmt:formatDate value="${item.startTime}" pattern="MM-dd"/>至<fmt:formatDate value="${item.endTime}" pattern="MM-dd"/></td>
                                <td><fmt:formatNumber value="${item.receivePrice}" type="currency" pattern="0.00"/></td>
                                <td><fmt:formatNumber value="${item.carriagePrice}" type="currency" pattern="0.00"/></td>
                                <td><fmt:formatNumber value="${item.commissionPrice-item.refundAmount}" type="currency" pattern="0.00"/></td>
                                <td><fmt:formatNumber value="${item.payPrice}" type="currency" pattern="0.00"/></td>
                                <td><c:if test="${item.confirmStatus==0}">待确认</c:if><c:if test="${item.confirmStatus==1}"><c:if test="${item.payStatus==0}">待审核</c:if><c:if test="${item.payStatus>1 && item.payStatus<4 }">审核通过</c:if><c:if test="${item.payStatus<0}">审核未通过</c:if><c:if test="${item.payStatus==4}">已结算</c:if></c:if><c:if test="${item.confirmStatus==-1}">商家不同意</c:if></td>
                                <td><c:if test="${item.receiptStatus==0}">未开</c:if><c:if test="${item.receiptStatus==1}">已申请</c:if><c:if test="${item.receiptStatus==2}">已开</c:if></td>
                                <td><fmt:formatDate value="${item.payTime}" pattern="yyyy-MM-dd"/></td>
                                <td>
                                    <div class="operate">
										<a href="${basePath}/saleBill/toSaleBillView.html?saleBillId=${item.id}" target="_blank">查看结算单</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    
                    </c:if>
                </div>
                <wodepageform:PageFormTag pageSize="${result.size}"  totalPage="${result.totalPage}" currentPage="${result.page}" url=""/>
            </div> 
            <div class="savingbtn"><a href="javascript:window.close();">确定</a></div>
            <div class="savingbtn"><a href="javascript:void(0)" onclick="javascript:ajaxExport('${saleBill.id}');">导出</a></div>
            <div class="savingbtn"><a href="javascript:printHtml();">打印</a></div>
            
        </div>
    </div>
    <!--right end-->
</div>
<script id="upload_ue"  type="text/plain" style="width:0px;height:0px;"></script>
   
<!--content end-->
<script type="text/javascript">
    	
    	
    	 function printHtml() {
    		 
   		    //var tit = document.title;

   		   // document.title = "";

   		    $(".savingbtn").css("display","none");

   		    window.print();

   		    //document.title = tit;

   		 	$(".savingbtn").css("display","");

    	} 
    	/**
    	 * 同意、不同意
    	 */
    	function ajaxExport(saleBillId){
    		var datapate = {
    				saleBillId:saleBillId
    		}
    		
    		window.open("${basePath}/exportExcel/ajaxExportSaleBillView.html?saleBillId="+saleBillId);
			
    		return true;
     	}
    	
    </script>
</body>
</html>