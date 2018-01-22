var uid=GetUidCookie();//用户id
//JavaScript Document
$(document).ready(function() {
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	$("#memo").val(memo);
	$("#save").click(function(e){//保存
		var memo=$("#memo").val();
		toRecevice(id,memo);
	})
});
function toRecevice(id,memo){
	if(uid=="") return;
	if (employeeType==1) {//员工
		check(id,memo);
	}else{//普通账户
		normalUserCheck(id,memo)
	}
}
function check(id,memo){
	var id=parseInt(id);
	$.ajax({
		url:'friend/check.user?result=true&id='+id+'&uid='+uid+"&memo="+memo,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		showInfoBox("成功通过");
	    		setTimeout(back,1500);
			}else{
				showInfoBox(data.msg);
				setTimeout(refresh,1500);
			}
	    },
	    error : function() {}
	})
}
function normalUserCheck(id,memo){
	var id=parseInt(id);
	$.ajax({
		url:'friend/normalUserCheck.user?status=true&id='+id+'&uid='+uid+"&memo="+memo,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		showInfoBox("成功通过");
	    		setTimeout(back,1500);
			}else{
				showInfoBox(data.msg);
				setTimeout(refresh,1500);
			}
	    },
	    error : function() {}
	})
}
function back(){
	history.back();
}
function refresh(){
	window.location.reload();
}