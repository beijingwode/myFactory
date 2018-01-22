
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
		var phone = $("#phone").val();
		var companyType =  $("#companyType").val();
		
		var pattern = /^\+?[1-9][0-9]*$/;
		var age=$("#age").val();
		var seniority=$("#seniority").val();
		
		if(phone=="" || phone.length!=11) {
			showMsg("请输入正确的手机号");
			return;
		}
		var code = $("#code").val();
		if(code=="" || code.length!=6) {
			showMsg("请输入短信验证码");
			return;
		}
		var name = $("#name").val();
		if(name=="") {
			showMsg("请输入姓名");
			return;
		}
				
                var empNumber =  $("#empNumber").val();
		if(empNumber=="" || empNumber.length!=8) {
			showMsg("请输入正确的员工号");
			return;
		}
		
		$.ajax({
		    url:jsBasePath + "userShare/companyBind"+shareId+"?fromId="+$("#fromId").val()+"&companyType="+companyType+"&openId="+$("#openId").val(),
		    type : "POST",
			dataType: "json",  //返回json格式的数据  
			data: {"empNumber":empNumber,"phone":phone,"code":code,"name":name,"userName":$("#userName").val()},  //返回json格式的数据  
		    async: true,
		    success:function(data){
		    	if(data.success) {
		    		$("#rntId").val(data.data);
		    		$(".recharge_money").show();
		    		$(".theme-popover-mask").show();
		    		Setcookie("uid",data.data);
	    		} else {
					showMsg(data.msg);
		    	}
		    },
		 	error:function(){
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
		if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
		var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
		return pattern.test(email);
	}
	
	function showMsg(msg) {
		$(".theme-poptit").html(msg);
	    $(".theme-popover-mask").show();
	    $(".theme-popover").show();
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
	        setHash();
	    });
	   
	    $(".btn_box").click(function(){
	    	bindPhone();
	    });

	    window.onpopstate = function() {
			if(location.hash.indexOf("#win")>-1){
	        }else{
	        	WeixinJSBridge.call('closeWindow');
	        }
		  };
	});
	function setHash() {
		location.hash = "win";
	}
	function go2HDPage(){
		setHash();
		var openId=$("#openId").val();
		var shareId = $("#shareId").val();
		window.location = system_domain + "wx_dm_hp.html?openId="+openId;
	}