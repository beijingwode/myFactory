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
<title>我的福利-发票申请</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
   <link rel="stylesheet" type="text/css" href="<%=basePath %>css/apply_for_invoice.css" />
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<style type="text/css">

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
<%@ include file="../common/header_06.jsp" %>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Order_nav">
<div class="tit tit${tit}"></div>
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
<%--             <c:if test="${coupon > 0}"><li><span class="O_c_q">使用内购券：</span><span class="O_c_h red">${coupon }</span></li></c:if> --%>
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
            </li>
            <li><span class="O_c_q">成交时间：</span><span class="O_c_h"><fmt:formatDate value="${subOrder.createTime}"  pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            <li><span class="O_c_q">给卖家留言：</span><span class="O_c_h">${subOrder.noto }</span></li>
			<li><span class="O_c_q">快递公司：</span><span class="O_c_h"><c:if test="${subOrder.selfDelivery == 0}">${express}</c:if></span></li>
			<li><span class="O_c_q"><c:if test="${subOrder.expressType == '14660000000000000' }">卡券密码</c:if><c:if test="${subOrder.expressType != '14660000000000000' }">运单编号</c:if>：</span><span class="O_c_h">${expressNo}</span></li>
            <li><span class="O_c_q">收货地址：</span><span class="O_c_h">${subOrder.name}，${subOrder.mobile}，${subOrder.address }</span></li>
        </ul>
    </div>
    <div class="order_c_right">
    	<div class="lt_tit">发票申请</div>
     		<form id="invoiceForm" action="">
     		<div class="rt_con">     				     		
	     		<div class="fapiao_tit">
	     			<span><i>*</i>发票抬头：</span>
	     			<div class="tit_rt">
	     				<div class="inp_box"><input type="text" id="invoiceApplyTitle" name="title" value="${invoiceApply.title}" class="tit_text" /><label><input name="type" id="typeRadio" checked="checked" <c:if test="${invoiceApply.type == 0}"> checked="checked"</c:if> value="0" type="radio" />个人</label><label><input name="type" <c:if test="${invoiceApply.type == 1}"> checked="checked"</c:if> value="1" type="radio" />单位</label></div>	     			
	     			</div>
	     		</div>
	     		<div id="oDiv" style="display:none">
	     		<div class="fapiao_tit">
	     			<span><i>*</i>发票类型：</span>
	     			<div class="tit_rt">
	     				<div class="inp_box">
	     				<label><input name="billType" id="billType" checked="checked" <c:if test="${invoiceApply.billType == 0}"> checked="checked"</c:if> value="0" type="radio" />普通发票</label>
	     				<label><input name="billType" <c:if test="${invoiceApply.billType == 1}"> checked="checked"</c:if> value="1" type="radio" />专用发票</label>
	     				</div>	     			
	     			</div>
	     		</div>
	     		<div class="fapiao_tit">
	     			<span><i>*</i>纳税人识别号：</span>
	     			<div class="tit_rt">
	     				<div class="inp_box">
	     				<input type="text" id="taxpayerNumber" name="taxpayerNumber" value="${invoiceApply.taxpayerNumber}" class="tit_text" maxlength="50"/>
	     				</div>	     			
	     			</div>
	     		</div>
	     		</div>
	     		<div id="oDiv1" style="display:none">
	     		<div class="fapiao_tit">
	     			<span><i>*</i>注册地址：</span>
	     			<div class="tit_rt">
	     				<div class="inp_box">
	     				<input type="text" id="registerAddress" name="registerAddress" value="${invoiceApply.registerAddress}" class="tit_text" maxlength="100"/>
	     				</div>	     			
	     			</div>
	     		</div>
	     		<div class="fapiao_tit">
	     			<span><i>*</i>注册电话：</span>
	     			<div class="tit_rt">
	     				<div class="inp_box">
	     				<input type="text" id="registerPhone" name="registerPhone" value="${invoiceApply.registerPhone}" class="tit_text" maxlength="50"/>
	     				</div>	     			
	     			</div>
	     		</div>
	     		<div class="fapiao_tit">
	     			<span><i>*</i>开户行：</span>
	     			<div class="tit_rt">
	     				<div class="inp_box"><input type="text" id="openingBan" name="openingBan" value="${invoiceApply.openingBan}" class="tit_text" maxlength="100"/>
	     				</div>	     			
	     			</div>
	     		</div>
	     		<div class="fapiao_tit">
	     			<span><i>*</i>开户行账号：</span>
	     			<div class="tit_rt">
	     				<div class="inp_box"><input type="text" id="openingBanNumber" name="openingBanNumber" value="${invoiceApply.openingBanNumber}" class="tit_text" maxlength="50"/>
	     				</div>	     			
	     				<p>发票项目：商家默认将以订单明细开具发票，如需要开具其他项目，请备注中说明；发票类型：根据商家情况决定</p>
	     			</div>
	     		</div>
	     		</div>
	     		<div class="address">
	     			<span id="address"><i>*</i><em>邮寄地址：${invoiceApply.address }</em></span>
	     			<ul>
	     			<c:forEach items="${shippingAddressList}" var="shippingAddress" varStatus="vsStatus">  
	                		 <li><label><input type="radio" name="addressaa" onchange="getAddress(this)" onclick="getAddress(this)" value="${shippingAddress.provinceName }  ${shippingAddress.cityName }  ${shippingAddress.areaName }  ${shippingAddress.address }  （${shippingAddress.name }收）  ${shippingAddress.phone }"/>${shippingAddress.provinceName }  ${shippingAddress.cityName }  ${shippingAddress.areaName }  ${shippingAddress.address }  （${shippingAddress.name }收）  ${shippingAddress.phone }</label><a href="javascript:;" class="change_address" onclick="turnAddressList()">修改</a><a href="javascript:;" onclick="turnAddressList()" class="del_address">删除</a></li>
	                	</c:forEach>
	     				<li onclick="turnAddressList()" class="add_address"><label>使用新收获地址</label></li>	     				
	     			</ul>
	     		</div>
	     		
	     		<div class="bz_box">
						<span>备注：</span>
						<textarea name="note" cols="20" rows="5">${invoiceApply.note} </textarea>
	     		</div>
	     		
	     		<div class="btn"><a  onclick="addInvoice()" href="javascript:;">提交申请</a></div>
	     		
     		</div>
        
        <input type="hidden" name="id" value="${invoiceApply.id}"/>
        <input type="hidden" name="suborderid" value="${subOrder.subOrderId }"/>
        <input type="hidden" id="addressValue" name="address" value="${invoiceApply.address }" > <br>
        </form>
    </div>
</div>
 
<script type="text/javascript">
$(function(){
	  var flag = $("input[name='type']:checked").val();
	  var flag1 = $("input[name='billType']:checked").val();
	  $('input[name="type"]').click(function(){
		   if($(this).val() == 1){
			   document.getElementById("oDiv").style.display="";
			   $('input[name="billType"]').click(function () { 
				   if($(this).val() == 1){
					   document.getElementById("oDiv1").style.display="";
				   }else{
					   if(flag == 1){
						   document.getElementById("oDiv1").style.display="none";
					   }else{
						   document.getElementById("oDiv1").style.display="none";
						   $('#oDiv1 input[type="text"]').val("");
					   }
				   }
				});
			   if($("input[name='billType']:checked").val() == 1){
				   document.getElementById("oDiv1").style.display="";
			   }
		   }else{
			   if(flag == 1){
				   document.getElementById("oDiv").style.display="none";
				   document.getElementById("oDiv1").style.display="none";
			   }else{
				   $('#oDiv1 input[type="text"]').val("");
				   $('#oDiv input[type="text"]').val("");
				   $('input[type="radio"][name="billType"][value="0"]').attr("checked",true);
				   document.getElementById("oDiv").style.display="none";
				   document.getElementById("oDiv1").style.display="none";
			   }
		   }
	  });
	  if($("input[name='type']:checked").val() == 1){
		  if($("input[name='billType']:checked").val() == 1){
			  document.getElementById("oDiv").style.display="";
			  document.getElementById("oDiv1").style.display="";
		  }
		  if($("input[name='billType']:checked").val() == 0){
			  document.getElementById("oDiv").style.display="";
		  }
		  $('input[name="billType"]').click(function () { 
			   if($(this).val() == 1){
				   document.getElementById("oDiv1").style.display="";
			   }else{
				   document.getElementById("oDiv1").style.display="none";
			   }
			}); 
	  }
	  
	 });
function addInvoice(){
	if(verification()){
		if($("input[name='billType']:checked").val() == 0){
			$('#oDiv1 input[type="text"]').val("");
		}
		if($("input[name='type']:checked").val() == 0){
			$('#oDiv input[type="text"]').val("");
			$("input:radio[name='billType']").attr("checked",false);
		}
	    $.ajax({
	      type: "POST",
	      url: "/invoice/addInvoice",
	      data:$('#invoiceForm').serialize(),
	      success: function(ret){
	        if(ret.success){
	        	 window.location.reload();
	        }else{
	        	alert(ret.msg);
	        }
	      }
	    });
		
	}
	
}

//$(document).ready(function() {
//	             $('#invoiceForm input').keyup(trimkeyup);
//	 });
//	 function trimkeyup(e) {
//	     lucene_objInput = $(this);
//	     /* if (e.keyCode != 38 && e.keyCode != 40 && e.keyCode != 13) { */
//	         var im = lucene_objInput.val().replace(/\s/g, "");
//	         lucene_objInput.val(im); 
//	     /* } */
//	}        

function getAddress(addressRadio){
	$("#address").html("<i>*</i><em>邮寄地址："+$(addressRadio).val()+"</em>");
	$("#addressValue").val($(addressRadio).val());
}

function turnAddressList(){
	window.open("/member/userAddress");
}

function verification(){
	var address = $("#addressValue").val().replace(/\s/g, "");
	if(address == null || address == ""){
		alert("请选择收货地址");
		return false;
	}
	 var val=$('input:radio[name="type"]:checked').val();
     if(val=="1" || val==1){
    	 
    	 var invoiceApplyTitle = $("#invoiceApplyTitle").val().replace(/\s/g, "");
    	 if(invoiceApplyTitle == null || invoiceApplyTitle == ""){
    		 alert("请填写抬头");
         return false;
    	 }
     }
     if(val == 1){
	     if($('input:radio[name="billType"]:checked').val() == 0){
	    	 if($("#taxpayerNumber").val().replace(/\s/g, "") == null || $("#taxpayerNumber").val().replace(/\s/g, "") == ""){
	    		 alert("请填写纳税人识别号");
	             return false;
	    	 }
	     }
	     if($('input:radio[name="billType"]:checked').val() == 1){
	    	 if($("#taxpayerNumber").val().replace(/\s/g, "") == null || $("#taxpayerNumber").val().replace(/\s/g, "") == ""){
	    		 alert("请填写纳税人识别号");
	             return false;
	    	 }
	    	 if($("#registerAddress").val().replace(/\s/g, "") == null || $("#registerAddress").val().replace(/\s/g, "") == ""){
	    		 alert("请填写注册地址");
	             return false;
	    	 }
	    	 if($("#registerPhone").val().replace(/\s/g, "") == null || $("#registerPhone").val().replace(/\s/g, "") == ""){
	    		 alert("请填写注册电话");
	             return false;
	    	 }
	    	 if($("#openingBan").val().replace(/\s/g, "") == null || $("#openingBan").val().replace(/\s/g, "") == ""){
	    		 alert("请填写开户行");
	             return false;
	    	 }
	    	 if($("#openingBanNumber").val().replace(/\s/g, "") == null || $("#openingBanNumber").val().replace(/\s/g, "") == ""){
	    		 alert("请填写开户行账号");
	             return false;
	    	 }
	     }
     }
     
	return true;
}
</script>
<div class="clear"></div>
<%@ include file="../common/footer.jsp" %>
</body>
</html>