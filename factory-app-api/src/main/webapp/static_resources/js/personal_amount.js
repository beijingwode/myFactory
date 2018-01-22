var uid= GetUidCookie();
if(typeof(system_domain) == "undefined"){
	var system_domain ="http://api.wd-w.com/";
	var weixin_open_appId ="wxb62e121cbeffdddf";
	var comm_user_domain = "https://passport.wd-w.com/";
}
$(function(){
	getExchageTicket();
	$("#wish_list").click(function(e){//点击进入心愿订单
		e.stopPropagation()||(e.cancelBubble = true);
		window.location = system_domain+"exchangeOrder/towishPage.user?uid="+uid;
	})
});
function getExchageTicket() {
	// 自家专享
	$.ajax({
		url : 'exchangeOrder/getExchageTicket.user?uid='+uid,
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				var result = data.data;
				$(".hlb_details .li1 em").html('换领币余额：'+result.balance.toFixed(2))
			}
		}
	})
}
