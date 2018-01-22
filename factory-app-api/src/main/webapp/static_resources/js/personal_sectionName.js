
var isload = true;
var uid=GetUidCookie();//用户id
// JavaScript Document
$(function(){
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	$("#save").click(function(e){//点击保存
		ajaxupdateData();
		//location.reload='personal?pageId='+4;
	});
	$(".top").click(function(e){//后退 
		//location.href='personal?pageId='+4;
	});
});

function ajaxupdateData(){
	if(uid=="") return;
	var sectionName = $("#sectionName").val();
	
	if (sectionName==null||sectionName==""||$.trim(sectionName)=="") {
		showInfoBox("请输入部门");
	}else{
	$.ajax({
		url : jsBasePath+'user/updateEmp.user?uid=' + uid+'&sectionName='+$.trim(sectionName),
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache: false,
		success : function(data) {
			if (data.success) {
				//window.location = 'personal?uid='+uid+'&pageId='+4;//跳转到个人资料页面
				history.back();
			}else{
				showInfoBox(data.msg)
			}
		},
		error : function() {}
	});
	}
}

