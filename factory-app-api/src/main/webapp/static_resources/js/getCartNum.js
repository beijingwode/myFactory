// JavaScript Document
$(document).ready(function() {
	//$("#more ul").html("");
	init();
	//$("#more").show();
});
var uid=GetUidCookie();
function init(){
	if(uid==null) return;
	$.ajax({
		url :'user/getCartNum.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
		cache: false,
		success : function(data) {
			if(data.success){
				if(data.data!=null && data.data!='' && data.data!=0){
					$(".shopping_car span").show();
				}else{
					$(".shopping_car span").hide();
				}
				$(".shopping_car span").html(data.data);
			}else{
				$(".shopping_car span").hide();
			}
		}
	})
}
function go2Cart(){
	if(uid==null) return;
	window.location = "cart/page";
}