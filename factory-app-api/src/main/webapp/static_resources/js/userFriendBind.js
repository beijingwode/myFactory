	var clock = '';
	var nums = 60;
	var btn;
	var obj = document.getElementById('yzm_btn');
	
	$(document).ready(function() {
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
		
		var uid=GetUidCookie();
		if(uid!="") {
			var shareId = $("#shareId").val();
			var fuid = $("#fuid").val();
			var phone = $("#phone").val();
			var code = $("#code").val();
			bind(shareId,phone,code,uid,fuid);
		}
	});
	
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
		var fuid = $("#fuid").val();
		var phone = $("#phone").val();
		var code = $("#code").val();
		if(type!="9") {
			if(phone=="" || phone.length!=11) {
				showMsg("请输入正确的手机号");
				return;
			}
			if(code=="" || code.length!=6) {
				showMsg("请输入短信验证码");
				return;
			}
		}
		
		bind(shareId,phone,code,GetUidCookie(),fuid);
	}
	
	function bind(shareId,phone,code,uid,fuid) {
		var isCompany=0;
		if($("#chooseWorkmate").is(':checked')){
			isCompany=1;
			var memo=$("#memo").val();
			if(typeof(empSupplierId)==undefined || empSupplierId==null || empSupplierId==''){
				return;
			}
			$.ajax({
			    url:jsBasePath + "userShare/companyBind"+empSupplierId+"?fromId="+fuid+'&openId='+openId+'&phone='+phone+'&code='+code+'&name='+memo+'&motoShareId='+shareId,
			    type : "POST",
				dataType: "json",  //返回json格式的数据  
			    async: true,
			    success:function(data){
			    	if(data.success) {
			    		Setcookie("uid",data.data);
			    		Setcookie("wxOpen","1");
		    		}
			    	var msg= data.msg;
			    	if(msg==null || msg == '') {
			    		msg="绑定成功，即刻体验";
			    	}
					showMsg(msg);
			    },
			 	error:function(){
				}
			});
		}else{
			$.ajax({
			    url:jsBasePath + "userShare/bind"+shareId+"?phone="+phone +"&type="+type+"&code="+code+"&memo="+$("#memo").val()+"&userName="+$("#userName").val()+"&openId="+openId+"&uid="+uid+'&fuid='+fuid+'&isCompany='+isCompany+'&fromId='+fuid,
			    type : "POST",
				dataType: "json",  //返回json格式的数据  
			    async: true,
			    success:function(data){
			    	if(data.success) {
			    		Setcookie("uid",data.data);
			    		Setcookie("wxOpen","1");
		    		}
			    	var msg= data.msg;
			    	if(msg==null || msg == '') {
			    		msg="绑定成功，即刻体验";
			    	}
					showMsg(msg);
			    },
			 	error:function(){
				}
			});
		}
	}
	function showMsg(msg) {
		$(".theme-poptit").html(msg);
	    $(".theme-popover-mask").show();
	    $(".theme-popover").show();
	}
	
	$(document).ready(function() {	
	    
	    $(".theme-popbod a").click(function(){
	    	sessionStorage.setItem("login", "success");
	    	
	        $(".theme-popover-mask").hide();
	        $(".theme-popover").hide();
	        var truePath = system_domain;
			if(typeof(jsBasePath)!="undefined") {
				truePath=jsBasePath;
			}
			
			var msg = $(".theme-poptit").html();
			
			var uid=GetUidCookie();
	        if(uid!=""){	        	
	        	if(sessionStorage.loginNextUrl) {
	        		var loginNextUrl= sessionStorage.loginNextUrl;
					if(loginNextUrl.indexOf("uid=")==-1) {
						if(loginNextUrl.indexOf("?")==-1) {
							loginNextUrl=loginNextUrl+"?uid="+GetUidCookie();
						} else {
							loginNextUrl=loginNextUrl+"&uid="+GetUidCookie();
						}
					}
	        		
	    			sessionStorage.removeItem("loginNextUrl");
					window.location.href=loginNextUrl;
	        	} else {
					window.location.href=truePath + 'index_m.htm';
	        	}
	        }
	    });
	   
	    $(".btn_box").click(function(){
	    	bindPhone();
	    });
	    
	    
	});
	function Setcookie (name, value){ 
	    //设置名称为name,值为value的Cookie
	    var expdate = new Date();   //初始化时间
	    expdate.setTime(expdate.getTime() + 30 * 60 * 1000);   //时间
	    document.cookie = name+"="+value+";expires="+expdate.toGMTString()+";path=/";
	   //即document.cookie= name+"="+value+";path=/";   时间可以不要，但路径(path)必须要填写，因为JS的默认路径是当前页，如果不填，此cookie只在当前页面生效！~
	}