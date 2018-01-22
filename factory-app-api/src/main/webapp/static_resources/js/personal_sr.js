
uid=GetUidCookie();
// JavaScript Document
$(function(){
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	$("#save").click(function(e){//点击保存
		ajaxupdateData();
		location.reload='personal?uid='+uid+'&pageId='+4;
	});
	$(".top").click(function(e){//后退 
		location.href='personal?uid='+uid+'&pageId='+4;
	});
	if (birthday!='') {
		var day=getDate(birthday);
		$("#sr").val(day);
	}
});

function ajaxupdateData(){
	if(uid=="") return;
	day = $("#sr").val();//日期字符串
	var time=conversion(day);
	$.ajax({
		url : jsBasePath+'user/update.user?uid=' + uid+'&birthDay='+day,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache: false,
		success : function(data) {
			if (data.success) {
				var result = data.data;
				var avatar = result.avatar;
				//window.location = 'personal?uid='+uid+'&pageId='+4;
				history.back();
			}
		},
		error : function() {}
	});
	
}
/**
 * 时间戳转换成nian-月-日格式
 * @param tm
 * @returns {String}
 */
function getDate(tm){ 
	var tt=new Date(parseInt(tm))
	var date = (tt.getFullYear()) + "-" + 
    (tt.getMonth() + 1) + "-" +
    (tt.getDate());
	return date; 
}
/**
 * 年月日格式转成时间戳
 * @param day
 * @returns {Number}
 */
function conversion(day){
	var time=Date.parse(new Date(day));
	return time;
}

