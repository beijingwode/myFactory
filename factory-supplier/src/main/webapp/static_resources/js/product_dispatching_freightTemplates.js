	/**
	 * 快速跳转
	 */
	function gotoPage(){
		var pagenum = $("#pagenum").val();
		formSubmit(pagenum);
	}

	/**
	 * 表单提交
	 */
	function formSubmit(page){
		if(page!=undefined){
			$("#pageNumber").val(page);
		}else{
			$("#pageNumber").val(1);
		}
		$("#sub_form").submit();
	}
	
    function remove(id) {
    	showConfirm('运费模板删除','您确定要删除此模板吗？删除后请注意修改应用过此模板商品的运费设置。',"optAjax('delete',"+id+")");
    }
    function edit(id) {
		window.location=jsBasePath+'/shippingAddress/template_edit.html?templateId='+id;
    }
    /*编辑全场包邮策略*/
    function editFreeTemplate(id) { 
		window.location=jsBasePath+'/shippingAddress/free_template_edit.html?supplierTemplateId='+id;	
    	}
    
    function apprTransfer() {
		$(".popup_bg").show();
		$("#ajaxErrMsg_send_return").html("");
		$("#send_return_popup").show();
    }
    
    function sendReturn() { 
    	var amount;
    	var amountvalue = $("#amount").val();//传递全场包邮金额
    	var shippingFree = $("[name='shippingFree']:checked").val();
    	var supplierTemplateId = jsSupplierId;
    	if (shippingFree == 1) {//如果先择了全场满多少包邮			
			amount = amountvalue;
	    	if (!valid()) {//校验input输入框金额必填
   				return ;
	    	   }	
		}else if (shippingFree == -1) {//如果选择了全场包邮未设置
			amount = shippingFree;
		}else if (shippingFree == 2) {
			amount = supplierTemplateId;
			
		}
        $.ajax({
            url: jsBasePath+'/shippingAddress/setShippingFree.json?amount='+amount,
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            success: function (data) {
                if (data.result.errorCode == 0) {
                	hiddenObjById('send_return_popup');
                	
                    showInfoBox("全场包邮设置成功");

            		setTimeout("freload()",1500);////格式化产地
                } else {
                    showInfoBox("确认失败");
                }
            }, error: function() {
            }
        });
        return false;
    }

  //校验input输入金额必填
    function valid(){
        var val = $("input[name = suppliershippfree]").val();
        if(val == '' || $.trim(val) == ''){
            //如果val为空或者空格，将错误消息显示在对应span
            $("#errorMsg").html('输入金额不能为空');
            //让span显示出来
            $("#errorMsg").show();
            return false;
        }else{
        	return true;
        }
    }
  
  	function freload(){
		window.location.reload();
  	}