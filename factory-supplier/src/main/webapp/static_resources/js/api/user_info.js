
var uid=GetUidCookie();
// JavaScript Document
$(function(){
	init()
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
});
function init(){
	if(uid == "") return;
	$.ajax({
		url:jsBasePath+'/app/user/statistic.user?uid='+uid+'&shopId='+shopId,
		type : "GET",
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				var data2 = data.data;
				var shopList = data2.shopList;
				$(".tx_photo img").attr("src",data2.avatar);//头像
				$("#userName").html(data2.userName);
				$("#introduce").html(data2.shopIntroduce);
				$("#shopName").html(data2.shopName);
			}
	    },
	    error : function() {}
	})
}
function selectImg(){
	$("#fileInput").click();
}
function updateAvatar(self) {
	var file = self.files[0];
	var formData = new FormData();
	formData.append('file', file);
	$.ajax({
		url : jsBasePath + '/app/user/uploadAvatar.user?uid=' + uid,
		type : "POST",
		async : true,
		contentType : false, //这个一定要写
		processData : false,
		cache : false,
		data : formData,
		success : function(data) {
			if (data.success) {
				var imgPath = data.data;
				$.ajax({
					url : jsBasePath + '/app/user/update.user?uid=' + uid
							+ '&avatar=' + imgPath,
					type : "GET",
					dataType : "json", //返回json格式的数据  
					async : true,
					success : function(data) {
						if (data.success) {
							$(".tx_photo img").attr("src", imgPath);
						} else {
							showInfoBox(data.msg);
						}
					},
					error : function() {
					}
				});
			} else {
				showInfoBox(data.msg);
			}
		},
		error : function(e) {
			showInfoBox("头像图片大小不能超过200k ");
		}
	});
}