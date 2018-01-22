	function takePrize(){
		var address = $("#address").val();
		var name = $("#name").val();
		var mobile = $("#mobile").val();
		var email = $("#email").val();
		if(address == null || address == ""){
			$("#error").html("收货地址不能为空")
			return false;
		}
		if(name == null || name == ""){
			$("#error").html('收货人不能为空');
			return false;
		}
		if(mobile.length==0) { 
			$("#error").html('请输入手机号码'); 
	          return false; 
	    }     
        if(mobile.length!=11) 
        { 
           $("#error").html('请输入有效的手机号码'); 
           return false; 
        } 
	        
        var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
        if(!myreg.test(mobile)) 
        { 
           $("#error").html('请输入有效的手机号码'); 
           return false; 
        } 
		if(email == null && email == ""){
			$("#error").html('邮箱不能为空');
			return false;
		}
		var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if(!myreg.test(email))
         {
        	$("#error").html('请填写正确的邮箱');
            return false;
        }
		//验证一切数据
		$.ajax({
			url : jsBasePath+'acticity/takePrize',
			type : "GET",
			data: {"prizeId":prizeId,"address":address,"name":name,"mobile":mobile,"email":email},
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    cache:false,
			success : function(data) {
				if(data.success){
					window.location=jsBasePath+'acticity/toTakeSuccessPage?prizeId='+prizeId;
				}
				if(data.data != null){
					window.location=jsBasePath+'acticity/toTakeSuccessPage?prizeId='+data.data;
				}
			},error : function() {}
		})
	}