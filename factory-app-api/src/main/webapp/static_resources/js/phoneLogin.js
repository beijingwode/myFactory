	var clock = '';
	var nums = 60;
	var btn;
	var obj = document.getElementById('yzm_btn');

	function sendCode(thisBtn)
	{	
		var phone = $("#phone").val();
		if(phone=="" || phone.length!=11) {
			showInfoBox("请输入正确的手机号");
			return;
		}
		btn = thisBtn;
		btn.disabled = true; //将按钮置为不可点击
		$(btn).html(nums+'秒倒计时');
		$("#yzm_btn").removeAttr("onclick");
		clock = setInterval(doLoop, 1000); //一秒执行一次
		obj.style.backgroundColor = "#ccc";
		
		$.ajax({
		    url:jsBasePath + "userShare/sendVerCodeSms?phone="+phone,
		    type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    success:function(data){
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

	function bindPhone()
	{	
		var shareId = $("#shareId").val();
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
		    url:jsBasePath + "user/getUserIdByPhone?phone="+phone +"&code="+code,
		    type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    success:function(data){
		    	if(data.success) {
		    		if(type == 'W') {
		    	    	sessionStorage.setItem("login", "success");
		    	    	var loginNextUrl =sessionStorage.getItem("loginNextUrl");
		    			if(typeof(loginNextUrl)!="undefined" &&loginNextUrl!=null){
		    				sessionStorage.removeItem("loginNextUrl");
							toUrl=encodeURI(loginNextUrl.replace(/&/g,"____").replace(/=/g,"****"));
		    			}
		    			window.location = jsBasePath+'wx/bindAccount.user?uid='+data.data+'&exp1='+exp1+'&toUrl='+toUrl+'&type=W';			
		    		} else {
		    			window.location = jsBasePath+"blackEnvelope/openPage"+$("#forId").val() +'.user?uid='+data.data;
		    		}
		    	} else {
		    		showInfoBox(data.msg);
		    	}
		    },
		 	error:function(){
			}
		});
	}
	
	$(document).ready(function() {	
		//从绑定页跳转来的手机号放入验证码登录页的手机号输入框里
		var phoneNum=sessionStorage.getItem("phone");
		if(phoneNum){
			$("#phone").val(phoneNum);
		};
		
	    $(".btn_box").click(function(){
	    	bindPhone();
	    });
	});
