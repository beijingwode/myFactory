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
<title>凑券包详情</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/cou_style.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/recharge.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
</script>
</head>

<body>
<div class="main-cont" id="main-cont">
	<div class="top_con_box">
        <div class="top_box">
            <div class="top">
                <h1>帮忙凑券</h1>
            </div>
        </div>
        <dl>
        	<dt><img src="${vo.fromUserAvatar}" /></dt>
            <dd class="dd1">${vo.fromUserNike}</dd>
            <dd class="dd2">${vo.message}</dd>
        </dl>
    </div>
    <div class="main_middle">
    	<span><em>￥3984.00</em><a href="javascript:go2Update();"><img src="<%=static_resources %>/images/edit.png"></a></span>
        <p>同意帮他凑券后，您将为其凑内购券￥84.00</p>
    </div>
    <div class="com_det">
    	<dl>
        	<dt>
        	<c:if test="${vo.reasonType==1}"><a href="<%=basePath%>productm?productId=${vo.expKey1}&specificationsId=${vo.expKey2}&quantity=1"><img src="${vo.expImg1}" /></a></c:if>
        	<c:if test="${vo.reasonType !=1}"><img src="${vo.expImg1}" /></c:if>
        	</dt>
            <dd>${vo.expMsg1}</dd>
            <dd><span></span></dd>
        </dl>
    </div>
    <p class="p1">您剩余内购券：￥${balance}不足以支付此包！</p>
    <!-- <div class="recharge" style="display:none"><a href="javascript:go2recharge();">马上充值</a></div> -->
    <input type="hidden" id="balance" value="${balance}">
    <input type="hidden" id="envelopeId" value="${vo.id}">
    <input type="hidden" id="ticket" value="${ticket}">
    <input type="hidden" id="uid" value="${uid}">
    <input type="hidden" id="itemId">
    <input type="hidden" id="itemPrice">
    <input type="hidden" id="currencyId" value="${vo.currencyId}"><!--券的类型 -->
    <div class="btns"><input type="button" value="取消" class="btn1" onclick="back()" /><input type="button" value="同意凑券" class="btn1  btn2" id="sure" onclick="pay()" /></div>
</div>
<div class="add_money"  style="display: none">
     <div class="theme-tit">请输入凑券金额</div>
     <div class="theme-input"><input type="number" id="num" placeholder="请输入凑券金额" autocomplete="off" min="0.01" step="0.01" onkeyup="checkNum(this)"/></div>
     <div class="theme-popbod" >       
        <a href="javascript:go2close();">取消</a>  
        <a href="javascript:go2confirm();" style="border:none;">确定</a>
     </div>
</div>
<div class="recharge_money"  style="display: none">
     <div class="theme-tit">请输入充值金额</div>
     <div class="theme-input"><input type="number" id="recharge" placeholder="请输入充值金额" value="100" autocomplete="off" min="0.01" step="0.01" onkeyup="checkNum(this)"/></div>
     <div class="theme-popbod" >
     	<a href="javascript:go2close();">取消</a>  
        <a href="javascript:recharge();" style="border:none;">确定</a>
        
     </div>
</div>
<div class="add_money-mask" style="display: none"></div>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
<script type="text/javascript">

$(document).ready(function() {
	$.ajax({
		dataType:'json',
		type:'POST',
		url:'<%=basePath %>blackEnvelope/openBlackEnvelope.user?ticket='+$("#ticket").val()+'&uid='+$("#uid").val()+'&envelopeId='+$("#envelopeId").val()+'&again=1',
		contentType: 'application/json',
		async: true,
		success:function(data){
			if(data.success){
				var msg = data.msg;
				if(data.data.status==4) {
					showInfoBox("来晚一步，此包已经被凑全啦，");
				} else if(data.data.status==6) {
					showInfoBox("当前凑券包已过期");
				} else {
					var ary=msg.split("_");
					$("#itemId").val(ary[0]);
					$("#itemPrice").val(ary[1]);

					$(".main_middle em").html("￥"+ary[1]);
					var currtncyId = data.data.currtncyId;
					if (currtncyId==1) {//内购券
						$(".main_middle p").html("同意帮他凑券后，您将为其凑内购券￥"+ary[1]);
					}else{
						$(".main_middle p").html("同意帮他凑券后，您将为其凑现金券￥"+ary[1]);
					}
					if(parseFloat($("#balance").val())<parseFloat(ary[1])) {
						if (currtncyId==1) {
							$(".p1").html("您剩余内购券：￥"+$("#balance").val()+"不足以支付此包！");
						}else{
							$(".p1").html("您剩余现金券：￥"+$("#balance").val()+"不足以支付此包！");
							/* $(".recharge").show(); */
						}
						//$(".btn1").removeAttr("onclick");
						//$(".btn1").attr("class","btn2");
					} else {
						$(".p1").html("");
					}
				}
			}else{

				showInfoBox(data.msg);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			//评论失败  Android修改失败
			showInfoBox("系统异常");
		}
	});
});

function pay(){

	$.ajax({
		dataType:'json',
		type:'POST',
		url:'<%=basePath %>blackEnvelope/payBlackEnvelope.user?ticket='+$("#ticket").val()+'&uid='+$("#uid").val()+'&envelopeId='+$("#envelopeId").val()+'&itemId='+$("#itemId").val()+'&itemPrice='+$("#itemPrice").val(),
		contentType: 'application/json',
		async: true,
		success:function(data){
			if(data.success){
				showInfoBox("凑券成功");
				window.location = "<%=basePath %>blackEnvelope/page"+$("#envelopeId").val();
			}else{
				showInfoBox(data.msg);
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			//评论失败  Android修改失败
			showInfoBox("系统异常");
		}
	});
}

function back(){
	window.location = "<%=basePath %>blackEnvelope/page"+$("#envelopeId").val();
}
var num;
function go2Update(){
	num = $("#itemPrice").val();//编辑金额
	$(".add_money-mask").show();
	$(".add_money").show();
	$("#num").val(num);
}
function go2close(){
	$(".add_money-mask").hide();
	$(".add_money").hide();
	$(".recharge_money").hide();
}
function go2confirm(){
	num= $("#num").val();//获取更改后金额
	var currencyId=$("#currencyId").val();//凑券类型
	if (parseFloat(num)>parseFloat($("#balance").val())) {//输入金额大于自己余额
		if (currencyId==1) {//内购券
			$(".p1").html("您剩余内购券：￥"+$("#balance").val()+"不足以支付此包！");
			$(".main_middle p").html("同意帮他凑券后，您将为其凑内购券￥"+num);
		}else{
			$(".p1").html("您剩余现金券：￥"+$("#balance").val()+"不足以支付此包！");
			$(".main_middle p").html("同意帮他凑券后，您将为其凑现金券￥"+num);
		}
		$(".main_middle em").html("￥"+num)
		$(".btn1").removeAttr("onclick");
		$(".btn1").attr("class","btn2");
		$(".recharge").show();
	}else if(500<=parseFloat(num)){
		showInfoBox("赠送金额不能大于500");
	}else if(0.01>parseFloat(num)){
		showInfoBox("赠送金额不能小于0.01");
	}else{
		$(".p1").html('');
		$("#sure").attr("class","btn1");
		$("#sure").attr("onclick","pay()");
		$(".recharge").hide();
		$("#itemPrice").val(num);
		$(".main_middle em").html("￥"+num);
		if (currencyId==1) {//内购券
			$(".main_middle p").html("同意帮他凑券后，您将为其凑内购券￥"+num);
		}else{
			$(".main_middle p").html("同意帮他凑券后，您将为其凑现金券￥"+num);
		}
	}
	go2close();
}
function go2recharge(){//弹出充值窗口
	$(".recharge_money").show();
	$(".add_money-mask").show();
}
function checkNum(obj){//校验金额
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
</script>
</html>
