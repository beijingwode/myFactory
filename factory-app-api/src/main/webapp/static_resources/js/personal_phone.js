
var uid=GetUidCookie();//用户id
var clock = '';
var nums = 60;
var btn;
var obj = document.getElementById("yzm_btn");
//JavaScript Document
$(document).ready(function() {	
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	$("#save").click(function(e){//点击保存
		ajaxUpdatePhone();
		location.reload='personal?pageId='+4;
	});
});
function sendCode(thisBtn)
{	
	var phone = $("#phone").val();
	if(phone=="" || phone.length!=11) {
		showInfoBox("请输入正确的手机号");
		return;
	}
	
	$.ajax({
	    url:jsBasePath + "user/sendCodeSms?phone="+phone,
	    type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    success:function(data){
	    	if (!data.success) {
				showInfoBox(data.msg)
			}else{
				btn = thisBtn;
				btn.disabled = true; //将按钮置为不可点击
				$(btn).html(nums+'秒倒计时');
				$("#yzm_btn").removeAttr("onclick");
				clock = setInterval(doLoop, 1000); //一秒执行一次
				obj.style.backgroundColor = "#ccc";
			}
	    },
	 	error:function(){
		}
	});
	
}
function doLoop()
{
	nums--;
	if(nums > 0){
		$(btn).html(nums+'秒倒计时');
	}else{
		clearInterval(clock); //清除js定时器
		$(btn).disabled = false;
		$(btn).html('获取验证码');
		nums = 60; //重置时间
		obj.style.backgroundColor = "#ff0005";
		$("#yzm_btn").attr("onclick","sendCode(this)");
	}
}
//修改手机
function ajaxUpdatePhone(){
	var phone = $("#phone").val();
	var code = $("#code").val();
	
	if(phone=="" || phone.length!=11) {
		showInfoBox("请输入正确的手机号");
		return;
	}
	if(code=="" || code.length!=6) {
		showInfoBox("请输入短信验证码");
		return;
	}
	var exp1=$("#exp1").val();
	var toUrl=$("#toUrl").val();
	$.ajax({
	    url:jsBasePath + "user/updatePhone.user?uid="+uid+"&phone="+phone +"&code="+code,
	    type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    success:function(data){
	    	if(data.success) {
	    		history.back();
	    	} else {
	    		showInfoBox(data.msg);
	    	}
	    },
	 	error:function(){
		}
	});
}