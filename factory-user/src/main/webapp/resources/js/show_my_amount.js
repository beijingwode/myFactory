//我的券显示效果（页面滑动到现金券专区时，我的券显示）
$(window).scroll(function(){
    var h=$(this).scrollTop();//获得滚动条距top的高度
    //alert(h); 
    if(h>450){
        $(".my_hlb").fadeIn();
    }else{
        $(".my_hlb").fadeOut();
    }
});
$(function(){
	getExchageTicket();
});
function getExchageTicket() {
	// 自家专享
	$.ajax({
		url : 'member/getExchageTicket',
		dataType : 'json',
		success : function(data) {
			if (data.success) {
				var result = data.data;
				var html = '';
				html += '<p class="p1">余额：' + result.balance.toFixed(2) + '</p>';
				html += '<p class="p2">总额：' + result.total.toFixed(2)+ '</p>';
				$(".hlb_con").html(html);
			}
		}
	})
}
function go2WishOrder(){
	window.location ="/member/myrenewal";
}