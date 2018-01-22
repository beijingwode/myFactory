//鼠标滑过问好显示
	$(function(){
		$(".AccountTab .Tabcont i").mouseover(function(){
			$(".AccountTab .Tabcont i span").show();
		});
		$(".AccountTab .Tabcont i").mouseout(function(){
			$(".AccountTab .Tabcont i span").hide();
		})
	})
	
	//输入字符串个数联动
	function checkNum(obj){
		var i = obj.value.length;
		var em=$(obj).next();
		if (i<33) {
			em.html(i+"/32");
		}else{
			return;
		}
	}
	
	//自动生成字符串
	function _getRandomString(len) {  
	    len = len || 32;  
	    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1  
	    var maxPos = $chars.length;  
	    var pwd = '';  
	    for (i = 0; i < len; i++) {  
	        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));  
	    }  
	    $("#val1").val(pwd);
	    $("#val1").next().html(pwd.length+"/32")
	    //$(".inp em").html(pwd.length+"/32");
	    
	}  
	
	//点击叉号和取消关闭弹层
	function closeBtn(){
		$(".popup_bg").hide();
		$("#divExpress").hide();
		window.location.reload();
	}
	
	//打开弹层
	$('.AccountTab .Tabcont a').click(function(){
		//打开背景
		background_open();
		//打开弹窗
		ent_box_open("#divExpress");
					
	});
	
	//确定按钮
	function sureBtn(){
		$(".inp input").focus(function(){
			$("#box_msg").html("");
			$(".inp").css({"border":"1px solid #e7e7eb",})
		});
		
		var val = $(".inp input").val();
		var val1=$("#val1").val();
		var val2=$("#val2").val();
		var pattern = /^[a-zA-Z0-9]{32}$/; 			
		var appId=$("#appId").val();
		
		//判断value是否相同
		if($.trim(val).length==32 && val1 != val2){
			$("#box_msg").html("请输入相同的密钥");
			$(".inp").css({"border":"1px solid #ff4040",});
			return;
		};
		
		//判断是否是32个字符
		if($.trim(val).length<32){
			$("#box_msg").html("请输入数字和英文大小写字母组成的32个字符");
			$(".inp").css({"border":"1px solid #ff4040",});
			return;
		};
		
		//输入字符必须是由数字和大小写字母组成
		if($.trim(val).length==32 &&!pattern.test(val)){
			$("#box_msg").html("只允许输入数字和英文大小写字母的组合");
			$(".inp").css({"border":"1px solid #ff4040",});
			return;
		};
		
		
		$.ajax({
			url : jsBasePath +'/user/updAppSecurity',
			type : "POST",
			data:{"id":appId,"secret":val1},
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {	
				alert("保存成功");
				window.location.reload();
				
			},
			error : function() {}
		});
		
	
}	