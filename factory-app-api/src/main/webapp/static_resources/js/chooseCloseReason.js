var uid=GetUidCookie();//用户id
// JavaScript Document
var reason =null;
var subOrderId=null;
$(function(){
	subOrderId = GetQueryString('subOrderId');
})
function GetQueryString(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return decodeURI(r[2]); return null;
}
function toCancel(){
	reason = $(".close_list_con li .em1").prev().html();
	if (reason == undefined || reason=='') {
		showInfoBox("未选择取消订单原因");
		//setTimeout(refresh,1500)
	}else{
		showConfirm("退款将返还至您的原支付账户","cancel()")
	}
}
function cancel(){
	if (uid=='') return
	$.ajax({
		url : 'order/cancelOrder.user?uid='+uid+'&subOrderId='+subOrderId+'&closeReason='+reason,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
		cache:false,
		success : function(data) {
			if (data.success) {
				back();//刷新页面
			}else{
				showInfoBox(data.msg);
			}
		}
	});
}
function back(){
	history.back();
}
function refresh(){
	location.reload();
}