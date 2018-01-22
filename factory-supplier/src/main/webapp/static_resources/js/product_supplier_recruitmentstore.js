	$(document).ready(function(){
			if("1"!=jsHasPre) {
				$("#recuitment_btn03").html('<a href="javascript:void(0);">取消</a>');
			}

			//if("2"!="${property}") {
			replaceShopName();
			//}
		});
    	
		function autoWrite(obj,cus,ser){
			var o = $(obj).val();
			if($("#"+cus).val()==''){
				$("#"+cus).val(o);
			}

			if($("#"+ser).val()==''){
				$("#"+ser).val(o);
			}

		}
		function replaceShopName(){
			if($("#shopname").val().indexOf("员工福利旗舰店") != -1){
				$("#shopname").val($("#shopname").val().replace("员工福利旗舰店",""));
			}else if($("#shopname").val().indexOf("员工福利专卖店") != -1){
				$("#shopname").val($("#shopname").val().replace("员工福利专卖店",""));
			}else if($("#shopname").val().indexOf("员工福利专营店") != -1){
				$("#shopname").val($("#shopname").val().replace("员工福利专营店",""));
			}
			//$("#shopname").val($("#shopname").val()+$("#shopNameEnd").text());
		}
		$(".r_radio").change(function(){
			var type = $("input[name='type']:checked").val(); 
			if (type == 1) { 
				$("#shopNameEnd").html("员工福利专卖店");
			} else { 
				$("#shopNameEnd").html("员工福利专营店");
			} 
		})
		//非空验证
		function validatorForm(){
			var $list = $("[ismust=1]");
			var ret = 0;
			$list.each(function(i,val){
				if($($list[i]).get(0).tagName  =='SELECT'){
					if($($list[i]).val()=='-1'){
						$($list[i]).addClass("bctxt");
						ret++;
					}
				}else if($($list[i]).get(0).tagName  =='INPUT'){
					if($($list[i]).val()==''){
						$($list[i]).addClass("bctxt");
						ret++;
					}
				}else if($($list[i]).get(0).tagName  =='TEXTAREA'){
					if($($list[i]).val()==''){
						$($list[i]).addClass("bctxt");
						ret++;
					}
				}

			});

			if(ret>0){
				return false;
			}else{
				return true;
			}
		}
		
		function chekout(){
			if(!validatorForm()) {
				showInfoBox("请补充必填项目后，再次提交！");
				return false;
			}
			
			var shopEmail = $("#shopEmail").val();
			var cusEmail = $("#cusEmail").val();
			var serEmail = $("#serEmail").val();
			if(!checkEmail(shopEmail)){
				//$("#shopEmail").addClass("bctxt");
				$("#3345").find("[name=error]").fadeIn();
	    		setTimeout("display()",6000);
				return false;
			}
			else if(!checkEmail(cusEmail)){
				//$("#cusEmail").addClass("bctxt");
				$("#3349").find("[name=error]").fadeIn();
	    		setTimeout("display()",6000);
				return false;
			}
			else if(!checkEmail(serEmail)){
				//$("#serEmail").addClass("bctxt");
				$("#3353").find("[name=error]").fadeIn();
	    		setTimeout("display()",6000);
				return false;
			}
			else
			{
				return true;
			}
		}
		
    	function papa(type){
    		$("#recuitment_btn01").addClass("btngray").removeAttr("onclick");
    		$("#recuitment_btn02").addClass("btngray").removeAttr("onclick");
    		$("#recuitment_btn03").addClass("btngray").removeAttr("onclick");
    		setTimeout("submit("+type+")",500);
    	}
    	
    	function display(){
    		var $list = $("[name=error]");
    		$list.each(function(i,val){
    			$($list[i]).fadeOut();
    		});
    		$("#cg").fadeOut();
    		$("#sb").fadeOut();
    	}
    	/**
    	*跳转添加品牌
    	*/
    	function submit(type){
    		if(type==3){
    			
    			if("1"==jsHasPre) {
        			window.location.href = jsBasePath+"/supplier/tocreatesupplierinfo.html";
    			} else {
    				window.location.href = jsBasePath+"/supplier/cancelEdit.html";
    			}
    		}else{
    			if(chekout()) {
    				if("2"!=jsProperty) {//品牌商
    					if($("#shopname").val().indexOf("员工福利旗舰店") == -1) {
        					$("#shopname").val($("#shopname").val()+"员工福利旗舰店");
    					}
    				}else{//代理商
    					replaceShopName();
    					$("#shopname").val($("#shopname").val()+$("#shopNameEnd").text())
    				}
	    			$.ajax({
	                    cache: true,
	                    type: "POST",
	                    dataType: "json",
	                    url:jsBasePath+"/shopSetting/setshopname.json",
	                    data:$('#sub_form_supplierinfo').serialize(),// 你的formid
	                    async: false,
	                    error: function(request) {
	                        //alert("Connection error");
	                        return false;
	                    },
	                    success: function(data) {
	                    	if(data.result.errorCode==1){
	                    		if(type==2){
	                    			window.location.href = jsBasePath+"/supplier/torecruitmenttype.html";
	                    		}
	                    		replaceShopName();
	                    		$("#cg").fadeIn();
	                    		setTimeout("display()",6000);
	                    	}else{
	                    		replaceShopName();
	            				//$("#shopname").val($("#shopname").val().replace("员工福利旗舰店",""));
								$("#sb").text("× " + data.result.message+".");
	                    		$("#sb").fadeIn();
	                    		setTimeout("display()",6000);
	                    		//alert("错误："+data.result.errorCode+"  原因："+data.result.message);
	                    	}
	                    }
	                });
    			}
    		}
    		$("#recuitment_btn01").removeClass("btngray").attr("onclick","papa('1')");
    		$("#recuitment_btn02").removeClass("btngray").attr("onclick","papa('2')");
    		$("#recuitment_btn03").removeClass("btngray").attr("onclick","papa('3')");
    	}  	

    	//校验邮箱
    	function checkEmail(email){
    		email=$.trim(email);
    		if(email.length==0) return false;
    		if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
    		var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
    		return pattern.test(email);
    	    //if(!pattern.test(email)){//只处验证不能为空并且只能为英文或者数字或者下划线组成的２－１５个字符
    	    //	return false;
    	    //}else{
    	    //	return true;
    	    //}
    	}