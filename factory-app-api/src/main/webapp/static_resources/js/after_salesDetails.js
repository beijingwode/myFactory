
var uid = GetUidCookie();
$(function(){
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	//点击显示原因
	init();
	//关闭选择物流公司
	$("#closeBox").click(function(e){
		$(".thickdiv").hide();
		$(".orderbox").hide();
		$(".main_box").removeAttr("style");
	});
	$(".thickdiv").click(function(e){
		$(".thickdiv").hide();
		$(".orderbox").hide();
		$(".main_box").removeAttr("style");
	});
})
var isReturnOrder=false;//是否为退货退款单
var returnOrderId='';
var refundOrderId='';
function init(){
	$.ajax({
		url :jsBasePath+'order/detail.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
		cache:false,
		success : function(data) {
			if (data.success) {
				var result = data.data;
				var suborder = result.order;
				var refundorder = result.refund;
				var returnorder =result.return;
				if(returnorder && returnorder!=''){
					isReturnOrder =true;
					returnOrderId = returnorder.returnOrderId;
				}
				if(isReturnOrder){
					if(returnorder.status==0){
						$("#progressPic").show();
						$(".return_hit p").html("您已申请售后，请耐心等待商家处理");
					}else if(returnorder.status==2){//商家同意发货
						$("#agreePic").show();
						$(".return_hit p").html("商家同意了您的退货申请，请您发回商品填写物流信息后提交");
						$("#returnAddress").html(suborder.returnedAddress);
						$(".logistics").show();
						$(".btns").html('<a href="javascript:go2Submit();">提交</a>');
						$(".btns").show();
					}else if(returnorder.status==3){//拒绝退货
						$("#refunsePic").show();
						$(".return_hit p").html("商家拒绝您的退货申请，您可以修改申请后提交或申请平台介入");
						$(".refusal_box p").html(suborder.refuseNote)
						$(".refusal_box").show();
						$(".btns").html('<a href="javascript:go2UpdateService(\''+suborder.realPrice+'\');">修改申请</a><a href="tel:010-56206418;">联系平台介入</a>');
						$(".btns").show();
					}else if(returnorder.status==4){
						$("#progressPic").show();
						$("#returnAddress").html(suborder.returnedAddress);
						$("#returnOrder_expressType").val(returnorder.expressType);//快递id
						$("#returnOrder_expressNo").val(returnorder.expressNo);//快递id
						$(".return_hit p").html("您已发出退货商品，请耐心等待商家处理");
						$(".logistics").show();
						$(".logistics .li2").hide();
						$(".logistics .li4").show();
					}else if(returnorder.status==6){
						$("#refunsePic").show();
						$(".return_hit p").html("商家拒绝您的退款申请，您可以修改申请后提交或申请平台介入");
						$(".refusal_box p").html(suborder.refuseNote)
						$(".refusal_box").show();
						$(".btns").html('<a href="javascript:go2UpdateService(\''+suborder.realPrice+'\');">修改申请</a><a href="tel:010-56206418;">联系平台介入</a>');
						$(".btns").show();
					}
					$("#type").html("退货退款");
					$("#price").html('￥'+returnorder.returnPrice.toFixed(2));
					$("#createdate").html(returnorder.createTimeString);
					$("#reason").html(returnorder.reason);
					$("#note").html(returnorder.note);
				}else{
					refundOrderId = refundorder.refundOrderId;
					if(refundorder.status==1){
						$("#progressPic").show();
						$(".return_hit p").html("您已申请售后，请耐心等待商家处理");
					}
					/*else if(refundorder.status==4){//商家同意发货
						$("#agreePic").show();
						$(".return_hit p").html("商家同意了您的退货申请，请您发回商品填写物流信息后提交");
						$("#returnAddress").html(suborder.returnedAddress);
						$(".logistics").show();
					}*/else if(refundorder.status==4){
						$("#refunsePic").show();
						$(".return_hit p").html("商家拒绝您的退款申请，您可以修改申请后提交或申请平台介入");
						$(".refusal_box p").html(suborder.refuseNote)
						$(".refusal_box").show();
						$(".btns").html('<a href="javascript:go2UpdateService(\''+suborder.realPrice+'\');">修改申请</a><a href="tel:010-56206418;">联系平台介入</a>');
						$(".btns").show();
					}
					$("#type").html("仅退款");
					$("#price").html('￥'+refundorder.refundPrice.toFixed(2));
					$("#createdate").html(refundorder.createTimeString);
					$("#reason").html(refundorder.reason);
					$("#note").html(refundorder.note);
				}
			}else{
				showInfoBox(data.msg);
			}
		}
	});
}
/**
 * 发出退货
 */
function go2Submit(){
	if(uid=="") return;
	var expressType=$("#returnOrder_expressType").val();
	var expressNo=$("#returnOrder_expressNo").val();
	var mustFlag = true;
	if(isReturnOrder){//退货退款
		if(typeof(expressType)==undefined || $.trim(expressType)==''){
			showInfoBox("请选择物流公司");
			mustFlag=false;
		}else if(typeof(expressNo)==undefined  || $.trim(expressNo)==''){
			showInfoBox("请填写快递单号");
			mustFlag=false;
		}
		if(returnOrderId== ""){
			showInfoBox("缺少退货id");
			mustFlag=false;
		}
	}
	if(mustFlag){
		$.ajax({
			type: "POST",
			url: jsBasePath+"order/sendReturnOrder.user?uid="+uid+"&returnOrderId="+returnOrderId+"&expressType="+expressType+"&expressNo="+expressNo,
			dataType: "json",  //返回json格式的数据  
			async: false,
			cache:false,
			success: function(data){
				if(data.success){
					window.location.reload();
				}
			},
		})
	}
}
//修改申请
function go2UpdateService(realPrice){
	if(uid=="") return;
	$.ajax({//获取返现金额
		url : jsBasePath+'order/getTrialReturn.user?uid='+uid+'&subOrderId='+subOrderId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			if (data.success) {
				var returnPrice = data.data;
				if (returnPrice==''||returnPrice==null||typeof(returnPrice)==undefined) {
					returnPrice=0;
				}
				//if (commentStatus=="1") {//已评价
					realPrice =(realPrice-returnPrice).toFixed(2);
				//}
			}
			window.location=jsBasePath+'orderM/applyReturn?subOrderId='+subOrderId+'&realPrice='+realPrice+'&returnOrderId='+returnOrderId+'&refundOrderId='+refundOrderId;
		}
	});
	//window.location=jsBasePath+'orderM/applyReturn?subOrderId='+subOrderId;
}
//取消
function go2Cancle(){
	history.back();
}
function go2CKShipping(){
	if(uid=="") return;
	var expressType=$("#returnOrder_expressType").val();
	var expressNo=$("#returnOrder_expressNo").val();
	window.location=jsBasePath+'logistics?userId='+uid+'&subOrderId='+subOrderId+'&expressType='+expressType+'&expressNo='+expressNo
}
function go2ExpressCompany(){
	$.ajax({
		url : jsBasePath+'orderM/getExpressCompany.user?uid='+ uid,
		type : "GET",
		async : true,
		cache : false,
		dataType: "json",  //返回json格式的数据  
		success : function(data) {
			var allCompInfoList = data.data;
			//快递公司信息
			if (allCompInfoList&&allCompInfoList!=null&&allCompInfoList.length>0) {
				var html="";
				
				var arr = new Array();
				for (var i = 0; i < allCompInfoList.length; i=i+3) {
					arr[0] = allCompInfoList[i];
					if (i+1>=allCompInfoList.length) {
						arr[1]='';
					}else{
						arr[1] = allCompInfoList[i+1];
					}
					if (i+2>=allCompInfoList.length) {
						arr[2]='';
					}else{
						arr[2] = allCompInfoList[i+2];
					}
					html +='<tr>';
					html +='<td onclick="chooseShipping(\''+arr[0]["id"]+'\',\''+arr[0]["name"]+'\')">'+arr[0]["name"]+'</td>';
					if (arr[1]=='') {
						html +='<td>  </td>';
					}else{
					html +='<td onclick="chooseShipping(\''+arr[1]["id"]+'\',\''+arr[1]["name"]+'\')">'+arr[1]["name"]+'</td>';
					}
					if (arr[2]=='') {
						html +='<td>  </td>';
					}else{
						html +='<td onclick="chooseShipping(\''+arr[2]["id"]+'\',\''+arr[2]["name"]+'\')">'+arr[2]["name"]+'</td>';
					}
					html+='</tr>';
				}
				$("table").html(html);
				$(".thickdiv").show();
				$(".orderbox").show();
				$(".main_box").attr("style","position:fixed");
			}
		},
	});
}
//选择完物流公司
function chooseShipping(expressType,expressName){
	$("#returnOrder_expressName").html(expressName);//页面快递名称
	$("#returnOrder_expressType").val(expressType);//快递id
	$(".thickdiv").hide();
	$(".orderbox").hide();
	$(".main_box").removeAttr("style");
}