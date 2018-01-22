 $(document).ready(function () {
        selectedHeaderLi("jsgl_header");

		$("#send_return_popup").attr("style","width:500px;z-index:9999999;position:absolute!important;margin-left:-50px!important;");
        if(jsBtnStatus=="0") {
        	$("#btn").attr("onclick","apprTransfer()");
        }
    });
    
    function apprTransfer() {
		$(".popup_bg").show();
		$("#ajaxErrMsg_send_return").html("");
		$("#send_return_popup").show();
    }
    
    function sendReturn() {
        if ($("#amount").val() == '' || $("#amount").val() <= 0) {
    		$("#ajaxErrMsg_send_return").html("请输入提现金额。");
            return false;
        }

        var cashBalance=jsCashBalance;
        if ($("#amount").val() > cashBalance) {
    		$("#ajaxErrMsg_send_return").html("提现金额不能超过账户余额。");
            return false;
        }
        
        $.ajax({
            url: jsBasePath+'/saleBill/apprTransfer.json?amount='+$("#amount").val(),
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            success: function (data) {
                if (data.result.errorCode == 0) {
                	hiddenObjById('send_return_popup');
                	
                    showInfoBox("申请已提交，等待平台转账，请留意结算账户余额或提现记录状态。");

            		setTimeout("formSubmit(1)",1500);////格式化产地
                } else {
                    showInfoBox("确认失败");
                }
            }, error: function () {
            }
        });
        return false;
    }