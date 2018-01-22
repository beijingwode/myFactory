
	var clock = '';
	var nums = 60;
	var btn;
	var obj = document.getElementById('yzm_btn');
    
	function sendCode(thisBtn)
	{			
		var phone = $("#phone").val();
		if(phone=="" || phone.length!=11) {
			showMsg("请输入正确的手机号");
			return;
		}
	
		btn = thisBtn;
		btn.disabled = true; //将按钮置为不可点击
		$("#yzm_btn").removeAttr("onclick");
		$(btn).html(nums+'秒倒计时');
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
		var companyType =  $("#companyType").val();
		
		var pattern = /^\+?[1-9][0-9]*$/;
		var age=$("#age").val();
		var seniority=$("#seniority").val();
		
		var phoneEmail = $.trim($("#phoneEmail").val());
		var reTel=/^1([3456789]\d|21)\d{8}$/i;
		
		var email="";
		var phone="";
		if(""==phoneEmail) {
			showMsg("请输入您的手机号或者企业邮箱");
			return;	
		} else if(reTel.test(phoneEmail)){
			if(phoneEmail.length!=11) {
				showMsg("请输入正确的手机号");
				return;
			} else {
				phone=phoneEmail;
			}
		} else if(!checkEmail(phoneEmail)) {
			showMsg("请输入正确的手机号或者企业邮箱");
			return;
		} else {
			email=phoneEmail;
		}
		
		var name = $("#name").val();
		if(name=="") {
			showMsg("请输入姓名");
			return;
		}
		
		
		
//		if ($.trim(age).length!=0) {
//			if (!pattern.test(age)) {
//				showMsg("请输入正确格式年龄");
//				return;
//			}
//		}
//		if ($.trim(seniority).length!=0) {
//			if (!pattern.test(seniority)) {
//				showMsg("请输入正确格式工龄");
//				return;
//			}
//		}
		$(".btn_box").unbind();
		$.ajax({
		    url:jsBasePath + "userShare/companyBind"+shareId+"?fromId="+$("#fromId").val()+"&companyType="+companyType+"&openId="+$("#openId").val()+"&afterDo="+$("#afterDo").val(),
		    type : "POST",
			dataType: "json",  //返回json格式的数据  
			data: {"phone":phone,"name":name,"email":email},  //返回json格式的数据  
		    async: true,
		    success:function(data){
		    	if(data.success) {
		    		$("#rntId").val(data.data);
		    		$(".recharge_money").show();
		    		$(".theme-popover-mask").show();
		    		Setcookie("uid",data.data);
		    		Setcookie("wxOpen","1");
		    		//go2HDPage();
	    		} else {
	    			showMsg(data.msg);

				    $(".btn_box").click(function(){
				    	bindPhone();
				    });
		    	}
		    },
		 	error:function(e){
			    $(".btn_box").click(function(){
			    	bindPhone();
			    });
			}
		});
	}
	
	function Setcookie (name, value){ 
	    //设置名称为name,值为value的Cookie
	    var expdate = new Date();   //初始化时间
	    expdate.setTime(expdate.getTime() + 30 * 60 * 1000);   //时间
	    document.cookie = name+"="+value+";expires="+expdate.toGMTString()+";path=/";
	   //即document.cookie= name+"="+value+";path=/";   时间可以不要，但路径(path)必须要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！~
	}
	
	function checkEmail(email){
		email=$.trim(email);
		if(email.length==0) return true;
		var pattern = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/; 
		return pattern.test(email);
	}
	
	function showMsg(msg) {
		$(".theme-poptit").html(msg);
		//msg=‘该手机/邮箱已经绑定过，请直接登录。’时，按钮显示为 ‘是’‘否’；并提示‘该手机已绑定过，是否使用验证码登录？’
		var phoneEmail = $.trim($("#phoneEmail").val());
		var reTel=/^1([3456789]\d|21)\d{8}$/i;
		if(reTel.test(phoneEmail) && $(".theme-poptit").html() == "该手机/邮箱已经绑定过，请直接登录。"){
			$(".theme-poptit").html('该手机已绑定过，是否使用验证码登录？');
			$(".theme-popbod").hide();
			$(".theme-popbod1").show();
		};
	    $(".theme-popover-mask").show();
	    $(".theme-popover").show();
	}
	
	//点击"是"时跳转到手机验证码登录页
	function toLoginYes() {
		sessionStorage.setItem("phone", $.trim($("#phoneEmail").val()));
		window.location="/user/toPhoneLogin?exp1="+sessionStorage.getItem("openId")+"&toUrl=&type=W&msg=";
	}

	function toLogin() {
		if(sessionStorage.openId){
			window.location="/user/toLogin?exp1="+sessionStorage.getItem("openId")+"&toUrl=&type=W&msg=";
		} else {
			var state="jumpCompany";
			var rtn = encodeURI(system_domain+"wx/hasBind");
			window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_open_appId+"&redirect_uri="+rtn+"&response_type=code&scope=snsapi_base&state="+state+"#wechat_redirect";
		}
	}
	$(document).ready(function() {	
	    
	    $(".theme-popbod a").click(function(){
	        $(".theme-popover-mask").hide();
	        $(".theme-popover").hide();
	       /* if($(".theme-poptit").html().indexOf("绑定成功,立刻体验")!=-1){
				//window.location.href='http://wd-w.com/app.htm';
				window.location = "http://mp.weixin.qq.com/s/AZu8nPhL703JJJJLBe8BTw";
	        }*/	        
	    });
	    //点击‘否’时 弹层隐藏
	    $(".theme-popbod1 .btn_no").click(function(){
	        $(".theme-popover-mask").hide();
	        $(".theme-popover").hide();
	    });
	   
	    $(".btn_box").click(function(){
	    	bindPhone();
	    });
	    var login =sessionStorage.getItem("login");
		if(typeof(login)!=undefined &&login!=null){
			if(login == "success"){
				sessionStorage.removeItem("login");
				if(sessionStorage.loginPreUrl){
					window.location = sessionStorage.loginPreUrl;
				} else {
					document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
					    // 通过下面这个API隐藏右上角按钮
					    WeixinJSBridge.call('closeWindow');
					});
				}
			}
		}
	});
	
	function go2HDPage(){
    	sessionStorage.setItem("login", "success");
    	var uid=$("#rntId").val();
    	var loginNextUrl =sessionStorage.getItem("loginNextUrl");
		if(typeof(loginNextUrl)!="undefined" &&loginNextUrl!=null){
			sessionStorage.removeItem("loginNextUrl");
			if(loginNextUrl.indexOf("uid=")==-1) {
				if(loginNextUrl.indexOf("?")==-1) {
					loginNextUrl=loginNextUrl+"?uid="+uid;
				} else {
					loginNextUrl=loginNextUrl+"&uid="+uid;
				}
			}
			window.location = loginNextUrl;
		} else {
			var openId=$("#openId").val();
			var shareId = $("#shareId").val();
			window.location = system_domain + "index_m.htm?openId="+openId;
		}
	}