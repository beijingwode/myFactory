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
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" >
<meta content="telephone=no" name="format-detection" />
<title>${title}</title>
<!--<link rel="stylesheet" type="text/css" href="css/style.css" />-->
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css?123" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/style2.css?123" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/recharge.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/recharge.js?5"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
</head>
<body>
<div class="main-cont" id="main-cont">
	<input type="hidden" id="charge" value="${charge}"/>
	<input type="hidden" id="cId" value="${cId}"/>
	<div class="top">
        <h1><a href="javascript:close();" class="aleft"></a><c:if test="${cId==0}">我的现金券</c:if><c:if test="${cId==1}">我的内购券</c:if><c:if test="${cId==3}">我的换领币</c:if></h1>
    </div>
    <div class="main-box">
    	<div class="summary summary${cId}">
			<div class="con_summary quan_${qId }">
				<c:if test="${cId!=3 }">
				<dl>
					<dt><img src="<%=static_resources %>images/f${cId}.png" /></dt>
					<dd class="dd1"><c:if test="${cId==0}">现金余额<%-- <a href="javascript:go2recharge();"><img src="<%=static_resources %>images/recharge_1.png" width="60" height="30"/></a> --%></c:if><c:if test="${cId==1}">内购券余额</c:if></dd>
					<dd class="dd2"><c:if test="${cId==0}">${ub.balance}<em>元</em></c:if><c:if test="${cId==1}"><em>￥</em>${ub.balance}</c:if><c:if test="${cId==2}"><em>￥</em>${ub.balance}</c:if>
					</dd>
				</dl>
				</c:if>
				<c:if test="${cId==3 }">
				  <ul>
					<li class="li1">
						<i>余额：</i><span><fmt:formatNumber value="${ut.empAvgAmount-ut.usedAmount}" type="number" groupingUsed="false" minFractionDigits="2"/></span><em><img src="<%=static_resources %>images/huanlingbi_icon.png" /></em>
						  <%-- <c:if test="${ut.activeAmount>0 }">
							<em>(已激活￥<fmt:formatNumber value="${ut.activeAmount}" type="number" groupingUsed="false" minFractionDigits="2"/>)</em>
						  </c:if> --%>
					</li>
					<li class="li2">总额：<fmt:formatNumber value="${ut.empAvgAmount}" type="number" groupingUsed="false" minFractionDigits="2"/><em><img src="<%=static_resources %>images/huanlingbi_icon.png" /></em></li>
					<%-- <li>将于<fmt:formatDate value="${ut.limitEnd}" pattern="yyyy-MM-dd"/>过期</li>--%>
					<li><fmt:formatDate value="${ut.limitStart}" pattern="yyyy.MM.dd"/>-<fmt:formatDate value="${ut.limitEnd}" pattern="yyyy.MM.dd"/></li>
				  </ul>
				</c:if>
			</div>
		</div> 
		
		<%-- <c:if test="${cId!=3 }"> --%>
	  	  <c:forEach items="${info}" var="pro" varStatus="status">
	        <div class="con_box">
	        	<div class="con_icon"><img src="<%=static_resources %>images/${pro.iconUrl}" /></div>
	            <div class="con1">
	            	<p class="p1"><nobr>${pro.note}</nobr></p>
	                <p class="p2"><fmt:formatDate value="${pro.opDate}" pattern="yyyy-M-d"/></p>
	            </div>
	            <div class="con2" <c:if test="${pro.value=='+' }">style="color:#c22936;"</c:if> >${pro.value} <fmt:formatNumber value="${pro.amount }" type="number" groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"/></div>
	        </div>
	      </c:forEach>
		<%-- </c:if> --%>
    </div>
    
</div>
<div class="recharge_money"  style="display: none">
     <div class="theme-tit">请输入充值金额</div>
     <div class="theme-input"><input type="number" id="recharge" placeholder="请输入充值金额" value="100" autocomplete="off" min="0.01" step="0.01" onkeyup="checkNum(this)"/></div>
     <div class="theme-popbod" >
     	<a href="javascript:go2close();">取消</a>
        <a href="javascript:recharge();"  style="border:none;">确定</a>
          
     </div>
</div>
<div class="add_money-mask" style="display: none"></div>
<%@ include file="/commons/alertMessage.jsp" %>
</body>

<script type="text/javascript">
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main-box").css("top","0");
	}
	
	if('1'==$("#charge").val()) {
		go2recharge();
	} else {
 	   if(window.location.href.indexOf("payOk=1")>-1) {
 			window.onpopstate = function() {
 				if(location.hash.indexOf("#win")>-1){
 		        }else{
 		            history.go(-2);
 		        }
 			  };
 			  

			showInfoBox("充值成功！","setHash()");
 	    	   
 	   }	
	}
	
	if ($("#cId").val()==3 && $("#backNum").val()!='') {//换领流水页面
		$(".top a").attr("href","javascript:goback();")
	}
});

function go2recharge(){//弹出充值窗
	try{
	if (isWeiXinH5()) {
		$(".recharge_money").show();
		$(".add_money-mask").show();
	}else{
		Toast.show("go2recharge");
	}
	}catch (e) {
		window.location = "go2recharge";
	}
}
function checkNum(obj){//检验金额
	 obj.value = obj.value.replace(/[^\d.]/g,""); 
     obj.value = obj.value.replace(/\.{2,}/g,".");    
     obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");  
     obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');   
     if(obj.value.indexOf(".")< 0 && obj.value !=""){
     obj.value= parseFloat(obj.value);
     }
}
function recharge(){//确认充值
	var rechargeMoney=$("#recharge").val();//充值金额
	if (parseFloat(rechargeMoney)<1) {//充值金额不能小于1元
		showInfoBox("充值金额不能低于1元");
	}else if(parseFloat(rechargeMoney)>500){//充值金额不能大于500
		showInfoBox("充值金额不能高于500元");
	}else{
		toRecharge(rechargeMoney);
	}
	$(".recharge_money").hide();
	$(".add_money-mask").hide();
}
function go2close(){
	$(".add_money-mask").hide();
	$(".recharge_money").hide();
}

function setHash() {
	location.hash = "win";
}
function goback(){
	/*  try{
		if (isWeiXinH5()) {
			window.history.back();
		}else{
			Toast.show("goback");
		}
		}catch (e) {
			window.location = "goback";
		} */
	window.history.back();
}
</script>
</html>
