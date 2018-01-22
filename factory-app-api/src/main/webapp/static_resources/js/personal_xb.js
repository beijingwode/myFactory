
var uid=GetUidCookie();//用户id
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
	if (gender=='f') {//女
			$("#n").removeClass("thisone");
			$("#m").removeClass("thisone");
			$("#f").addClass("thisone");
		}else if(gender=='n'){//保密
			$("#m").removeClass("thisone");
			$("#f").removeClass("thisone");
			$("#n").addClass("thisone");
		}else{//男
			$("#f").removeClass("thisone");
			$("#n").removeClass("thisone");
			$("#m").addClass("thisone");
		}
	//顶部切换
	$(".personal_xb_box ul li").each(function(index){
			$(this).click(function(){//先把所有的隐藏掉
			$(".personal_xb_box ul li").removeClass("thisone");
			$(this).addClass("thisone");
			var text=$(this).text();
			if (text=='男') {
				gender='m';
			}else if(text=='女'){
				gender='f';
			}else{
				gender='n';
			}
		});
	});
});

function ajaxupdateData(){
	if(uid=="") return;
	$.ajax({
		url : jsBasePath+'user/update.user?uid=' + uid+'&gender='+gender,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache: false,
		success : function(data) {
			if (data.success) {
				//window.location = 'personal?uid='+uid+'&pageId='+4;//跳转到个人资料页面
				history.back();
			}
		},
		error : function() {}
	});
	
}


