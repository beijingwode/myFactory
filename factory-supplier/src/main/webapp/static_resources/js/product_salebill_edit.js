
    function save() {
        if ($.trim($("#phone").val())=='') {
            showInfoBox("请输入电话号码!", function () {
                $("#phone").focus();
            });
            return;
        }
        if ($.trim($("#contacts").val())=='') {
            showInfoBox("请输入联系人信息!", function () {
                $("#contacts").focus();
            });
            return;
        } 
        if ($.trim($("input[name='billType']:checked").val()) == '') {
        	showInfoBox("请选择发票类型!", function () {
        		$("#contacts").focus();
        	});
        	return;
        }
        
    	if ($.trim($("#taxpayerNumber").val())=='') {
        	showInfoBox("请输入纳税人识别号!", function () {
        		$("#contacts").focus();
        	});
        	return;
        }
    	
    	if($("input[name='billType']:checked").val() == 1){
    		if ($.trim($("#addressNumber").val())=='') {
            	showInfoBox("请输入地址、电话!", function () {
            		$("#contacts").focus();
            	});
            	return;
            }
    		if ($.trim($("#openingBanNumber").val())=='') {
            	showInfoBox("请输入开户行及电话!", function () {
            		$("#contacts").focus();
            	});
            	return;
            }
    	}
        
        if ($("#pali").html()=='<span><i class="out">*</i>支付宝对公账号：</span>' && $.trim($("#alipayAccount").val())=='') {
            showInfoBox("请输入支付宝对公账号!", function () {
                $("#alipayAccount").focus();
            });
            return;
        }
        $('form').submit();
    }
    
    

    $(document).ready(function () {
        selectedHeaderLi("jsgl_header");
        changeAccountType(jsAccountType);
    });
    
    function changeAccountType(kbn) {
    	if(kbn=='1') {
    		$("#rd1").attr("checked",true);
    		$("#rd0").attr("checked",false);
    		$("#pali").html('<span><i class="out">*</i>支付宝对公账号：</span>');
    	} else {
    		$("#rd0").attr("checked",true);
    		$("#rd1").attr("checked",false);
    		$("#pali").html('<span><i class="out"> </i>支付宝对公账号：</span>');
    	}
    }
