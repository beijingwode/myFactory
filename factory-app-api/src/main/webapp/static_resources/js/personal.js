
var uid=GetUidCookie();//用户id
// JavaScript Document
$(function(){
	ajaxpageDataIndex();
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
});

function ajaxpageDataIndex(){
	if(uid=="") return;
	$.ajax({
		url : jsBasePath+'user/info.user?uid=' + uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache: false,
		success : function(data) {
			if (data.success) {
				var result = data.data;//个人资料对象
				avatar = result.avatar;
				if(avatar!=undefined && avatar!=null && avatar!='') {
					$(".tx_photo img").attr("src",avatar+'');//头像
				}
				 nickName = result.nickName;
				 gender = result.gender;
				 birthday = result.birthday
				 phone = result.phone
				 sectionName = result.sectionName;
				if(result.employeeType && result.employeeType==1){
					$(".sectionName").show();
				}else{
					$(".sectionName").hide();
				}
				$(".nicheng em").html(nickName);//昵称
				if (gender=='n') {//性别 n表示保密 m：男 f:女 
					$(".sex em").html("保密");
				}else if(result.gender=='m'){
					$(".sex em").html("男");
				}else{
					$(".sex em").html("女");
				}
				var date=getDate(birthday);
				if (date=='NaN-NaN-NaN') {
					$(".birthday em").html("");
				}else{
					$(".birthday em").html(date);//生日
				}
				if(typeof(phone)!="undefined") {
					$(".mobile em").html(phone);//有手机信息
				}else{
					phone='';
					$(".mobile em").html("请绑定");//无手机信息
				}
				if(sectionName && sectionName!=null && sectionName!=''){
					$(".sectionName em").html(sectionName);//部门
				}
				$(".acount em").html(result.userName);//昵称
				var pageId;
				$(".nicheng").click(function(e) {//昵称
					pageId=0;
					window.location = 'personal?uid='+uid+'&nickName='+nickName+'&pageId='+0;
				})
				$(".sex").click(function(e) {//性别
					pageId=1;
					window.location = 'personal?uid='+uid+'&gender='+gender+'&pageId='+1;
				})
				$(".birthday").click(function(e) {//生日
					pageId=2;
					if (birthday==''||birthday==null) {//日期为空
						birthday= new Date().getTime();
					}
					window.location = 'personal?uid='+uid+'&birthday='+birthday+'&pageId='+2;
				})
				$(".mobile").click(function(e) {//手机
					pageId=3;
					window.location = 'personal?uid='+uid+'&phone='+phone+'&pageId='+3;
				})
				$(".sectionName").click(function(e) {//手机
					pageId=5;
					if (typeof(sectionName) == "undefined") { 
						sectionName = "";
					}
					window.location = 'personal?uid='+uid+'&sectionName='+sectionName+'&pageId='+5;
				})
			}
		},
		error : function() {}
	});
}
function selectImg() {
	$("#fileInput").click();
}
function updateAvatar(self) {
	var file = self.files[0];
	var formData = new FormData();
	formData.append('file', file);
	$.ajax({
		url : jsBasePath + 'user/uploadAvatar.user?uid=' + uid,
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
					url : jsBasePath + 'user/update.user?uid=' + uid
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

function getDate(tm){ 
	var tt=new Date(parseInt(tm))
	var date = (tt.getFullYear()) + "-" + 
    (tt.getMonth() + 1) + "-" +
    (tt.getDate());
	return date; 
}
