
var uid=GetUidCookie();//用户id
//JavaScript Document
$(document).ready(function() {
	init();
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	if (employeeType!=1) {
		$(".add_btn").hide();
	}
});
$(".li1").click(function(e){
	$(this).next().removeClass("thisone");
	$(this).addClass("thisone");
	getCheckList();
})
$(".li2").click(function(e){
	$(this).prev().removeClass("thisone")
	$(this).addClass("thisone");
	go2friendList();
})
//跳转亲友申请
function go2friendApply(){
	//window.location = jsBasePath+'userShare/page.user?uid='+uid;
	window.location = jsBasePath+'friends_apply2.html';
}
function init(){
	//待审核亲友列表
	//getCheckList();
	//亲友管理
	go2friendList();
}
function go2friendList(){
	if (employeeType==1) {//员工用户
		//获取员工用户亲友列表
		getfriendList();
	}else{
		//获取普通用户亲友列表
		getNormalUserFriendsList();
	}
}
//亲友申请
function getCheckList(){
	if(uid=="") return;
	$.ajax({
		url:jsBasePath+'friend/checkList.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				var result = data.data.list
				if (result==undefined) {
					result='';
				}
				if (result.length>0) {
					var html='';
					for (var i = 0; i < result.length; i++) {
						html +='<div class="shop_con sl-li ui-border-b">';
						html +='<div class="sl-content">';
						html +='<dl>';
						if (employeeType==1) {//员工
							html +='<dt>'+result[i].memo+'</dt>';
							html +='<dt>'+result[i].phoneNumber+'</dt>';
							html +='<dt>我是'+result[i].memo+'</dt>';
						}else{
							html +='<dt>'+result[i].nickname+'</dt>';
							html +='<dt>'+result[i].phoneNumber+'</dt>';
							html +='<dt>我是'+result[i].nickname+'</dt>';
						}
						html +='</dl>';
						if (employeeType==1) {
							html +='<div class="option_btn"><span class="btn_span1" onclick="go2refuse(\''+result[i].id+'\');">拒绝</span><span class="btn_span2" onclick="go2receive(\''+result[i].id+'\',\''+result[i].memo+'\')">接受</span></div>';
						}else{
							html +='<div class="option_btn"><span class="btn_span1" onclick="go2refuse(\''+result[i].id+'\');">拒绝</span><span class="btn_span2" onclick="go2receive(\''+result[i].id+'\',\''+result[i].nickname+'\')">接受</span></div>';
						}
						html +='</div>'
						html +='<div class="sl-opts">';
						html +='<span onclick=go2Del(\''+result[i].employeeid+'\')>删除</span>';
						html +='</div>';
						html +='</div>';
					}
					$(".main-box").html(html);
					//jQuery.getScript(jsBasePath+"static_resources/js/slideleft.js");//动态加载js
				}else{//数据为空页面为空
					$(".main-box").html('');
				}
			}else{
				showInfoBox(data.msg);
				setTimeout(refresh,1500);
			}
	    },
	    error : function() {}
	});
}
//获取员工用户亲友列表
function getfriendList(){
	if(uid=="") return;
	$.ajax({
		url:jsBasePath+'friend/list.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				var result = data.data;
				if (result==undefined) {
					result='';
				}
				if (result.length>0) {
					var html='';
					for (var i = 0; i < result.length; i++) {
						html +='<div class="shop_con shop_con1 sl-li ui-border-b">';
						html +='<div class="sl-content sl-content1">';
						html +='<dl>';
						if (result[i].state==1) {
							html +='<dt onclick="go2changeNick(\''+result[i].memo+'\',\''+result[i].userid+'\')"><img src="'+jsBasePath+'static_resources/images/friend_note.png" width="24" height="24" /></dt>';
						}
						var memo = result[i].memo;
						if (result[i].state==1) {
							if (memo&&memo.length>4) {
								var name = memo.substring(0,3);
								name+='..'
								html +='<dd><span>'+name+'</span><em>是您的亲友</em></dd>';
							}else{
								html +='<dd><span>'+result[i].memo+'</span><em>是您的亲友</em></dd>';
							}
						}else{
							if (memo&&memo.length>4) {
								var name = memo.substring(0,3);
								name+='..'
								html +='<dd><span>'+name+'</span><em>邀请已发送</em></dd>';
							}else{
								html +='<dd><span>'+result[i].memo+'</span><em>邀请已发送</em></dd>';
							}
						}
						html +='</dl>';
						if (result[i].state==1) {
							html +=' <p>券：'+result[i].balance+'<span onclick="go2giveFucoin(\''+result[i].userid+'\',\''+result[i].memo+'\')">赠券</span></p>';
						}else if(result[i].state==0){
							html +='<p>等待同意</p>';
						}else if(result[i].state=='-1'){
							html +='<p>对方已拒绝</p>'
						}
						html +='</div>'
						html +='<div class="sl-opts">';
						html +='<span onclick=go2Del(\''+result[i].id+'\')>删除</span>';
						html +='</div>';
						html +='</div>';
					}
					$(".main-box").html(html);
					jQuery.getScript(jsBasePath+"static_resources/js/slideleft.js");//动态加载js
					$(".bottom").hide();
				}else{
					$(".main-box").html('');
				}
			}else{
				showInfoBox(data.msg);
				setTimeout(refresh,1500);
			}
	    },
	    error : function() {}
	});
}
//获取普通用户亲友列表
function getNormalUserFriendsList(){
	if(uid=="") return;
	$.ajax({
		url:jsBasePath+'friend/normalUserFriendsList.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				var result = data.data;
				if (result==undefined) {
					result='';
				}
				if (result.length>0) {
					var html='';
					for (var i = 0; i < result.length; i++) {
						html +='<div class="shop_con shop_con1 sl-li ui-border-b">';
						html +='<div class="sl-content sl-content1">';
						html +='<dl>';
						//html +='<dt onclick="go2changeNick(\''+result[i].memo+'\',\''+result[i].userid+'\')"><img src="'+jsBasePath+'static_resources/images/friend_note.png" width="24" height="24" /></dt>';
						if (result[i].state==1) { 
							html +='<dd><span>'+result[i].nickname+'</span><em>是您的亲友</em></dd>';
						}else{
							html +='<dd><span>'+result[i].nickname+'</span><em>申请已发送</em></dd>';
						}
						
						html +='</dl>';
						if (result[i].state==0) {
							html +='<p>等待同意</p>';
						}else if (result[i].state=='-1') {
							html +='<p>对方已拒绝</p>';
						}
						//html +=' <p>券：'+result[i].balance+'<span onclick="go2giveFucoin(\''+result[i].userid+'\',\''+result[i].memo+'\')">赠券</span></p>';
						html +='</div>'
						html +='<div class="sl-opts">';
						html +='<span onclick=go2Del(\''+result[i].id+'\')>删除</span>';
						html +='</div>';
						html +='</div>';
					}
					$(".main-box").html(html);
					jQuery.getScript(jsBasePath+"static_resources/js/slideleft.js");//动态加载js
					$(".bottom").hide();
				}else{
					$(".main-box").html('');
				}
			}else{
				showInfoBox(data.msg);
				setTimeout(refresh,1500);
			}
	    },
	    error : function() {}
	});
}
//拒绝
function go2refuse(id){
	if(uid=="") return;
	var id=parseInt(id);
	if (employeeType==1) {//员工
		check(id);
	}else if(employeeType==0){//普通账户
		normalUserCheck(id)
	}
}
function check(id){//员工账户拒绝
	$.ajax({
		url:jsBasePath+'friend/check.user?result=false&id='+id+'&uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				showInfoBox("已拒绝","refresh()")
			}else{
				showInfoBox(data.msg);
				setTimeout(refresh,1500);
			}
	    },
	    error : function() {}
	})
}
function normalUserCheck(id){//普通账户拒绝
	$.ajax({
		url:jsBasePath+'friend/normalUserCheck.user?status=false&id='+id+'&uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				showInfoBox("已拒绝","refresh()")
			}else{
				showInfoBox(data.msg);
				setTimeout(refresh,1500);
			}
	    },
	    error : function() {}
	})
}
//接受
function go2receive(id,memo){
	/*$(".theme-popover-mask").show();
	$(".add_remarks").show();
	$("#memo").val(memo);
	
	$(".add_remarks .theme-popbod a").click(function(){
		$(".theme-popover-mask").hide();
		$(".add_remarks").hide();
		var memo=$("#memo").val();
		var text=$(this).html();
		if (text=='取消') {
			closeMsg();
		}else{
			toReceive(id);
		}
	})*/
	window.location = jsBasePath+'friends_receive.html?id='+id+'&memo='+memo+'&employeeType='+employeeType;
}
//删除亲友
function go2Del(id){
	if(uid=="") return;
	var id=parseInt(id);
	$.ajax({
		url:jsBasePath+'friend/deleteUserFriend.user?id='+id+'&uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		showInfoBox("解除他与您的亲友关系!确认删除吗?","refresh()")
			}else{
				showInfoBox(data.msg);
				//setTimeout(refresh,1500);
			}
	    },
	    error : function() {}
	})
}
//修改备注
function go2changeNick(memo,userid){
	if(uid=="") return;
	$(".theme-popover-mask").show();
	$(".add_remarks").show();
	$("#memo").val(memo);
	$(".add_remarks .theme-popbod a").click(function(){
		$(".theme-popover-mask").hide();
		$(".add_remarks").hide();
		var memo=$("#memo").val();
		var text=$(this).html();
		if (text=='取消') {
			closeMsg();
		}else{
			go2Sure(userid,memo);
		}
	})
}
function go2Sure(userid,memo){
	$.ajax({
		url:jsBasePath+'friend/changeNick.user?userId='+userid+'&memo='+memo+'&uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		showInfoBox("修改成功");
	    		setTimeout(refresh,1500)
			}else{
				showInfoBox(data.msg);
				setTimeout(refresh,1500);
			}
	    },
	    error : function() {}
	})
}
function closeMsg(){
	$(".theme-popover-mask").hide();
	$(".add_remarks").hide();
}
//赠券
function go2giveFucoin(userid,memo){
	if(uid=="") return;
	$(".theme-popover-mask").show();
	$(".add_money").show();
	$(".theme-tit").html("赠内购券给"+memo);
	$(".add_money .theme-popbod a").click(function(){
		var num=$("#num").val();
		$(".theme-popover-mask").hide();
		$(".add_money").hide();
		var text=$(this).html();
		if (text=='取消'){
			$("#num").val('');
			closeMsg();
		}else{
		if (parseInt(num)<0) {
				showInfoBox("赠送金额不能小于0");
				setTimeout(refresh,1500);
		}else{
		$.ajax({
			url:jsBasePath+'friend/giveFucoin.user?userId='+userid+'&num='+num+'&uid='+uid,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    cache:false,
		    success : function(data) {
		    	if (data.success) {
		    		showInfoBox("赠送成功");
		    		setTimeout(refresh,1500)
				}else{
					showInfoBox(data.msg);
					setTimeout(refresh,1500);
				}
		    },
		    error : function() {}
		})
		}
		}
	})
}
function go2Close(){//关闭
	$(".bottom").hide();
}
function refresh(){
	location.reload();
}