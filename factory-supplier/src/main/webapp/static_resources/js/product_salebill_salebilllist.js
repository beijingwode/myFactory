    $(document).ready(function () {
        selectedHeaderLi("jsgl_header");
        initSelect();
    });

    function initSelect() {
        var confirmStatusTemp = $("#confirmStatusTemp").val();
        $("#confirmStatus").val(confirmStatusTemp);

        var payStatusTemp = $("#payStatusTemp").val();
        $("#payStatus").val(payStatusTemp);

        var receiptStatusTemp = $("#receiptStatusTemp").val();
        $("#receiptStatus").val(receiptStatusTemp);
    }
    /**
     * 全选记录
     */
    function checkTotal(obj) {
        var isTotalCheck = $(obj).prop("checked");
        var checkNum = 0;
        if (isTotalCheck) {
            $("input:checkbox[name='ck']").prop("checked", function (i, val) {
                checkNum = checkNum + 1;
                return true;
            });
        } else {
            $("input:checkbox[name='ck']").prop("checked", false);
        }
    }

    /**
     * 选择记录
     */
    function check() {
        var checkTotalNum = $("input:checkbox[name='ck']");
        var isAllChecked = true;
        var checkNum = 0;
        checkTotalNum.each(function (i, val) {
            if ($(checkTotalNum[i]).prop("checked")) {
                checkNum = checkNum + 1;
            } else {
                isAllChecked = false;
            }
        });

        if (!isAllChecked) {
            $("input:checkbox[id='total']").prop("checked", false);
        } else {
            if (checkTotalNum.length != 0) {
                $("input:checkbox[id='total']").prop("checked", true);
            }
        }
    }

    /**
     * 快速跳转
     */
    function gotoPage() {
        var pagenum = $("#pagenum").val();
        formSubmit(pagenum);
    }

    /**
     * 表单提交
     */
    function formSubmit(page) {
        if (page != undefined) {
            $("#pages").val(page);
        } else {
            $("#pages").val(1);
        }
        $("#sub_form").submit();
    }

    /**
     * 重置
     */
    function formReset() {
        //$("#sub_form").reset();
        //document.getElementById("sub_form").reset();
        $("#sub_form").find("input[type!='hidden']").val("");
        $("#sub_form").find("select").find("option:first").attr("selected", "selected");
    }


    /**
     * 弹出层 提交审核
     */
    function ajaxConfirmAlert(id, status) {
        if (status == '1') {
    		showConfirm("同意　对账单","确认同意此对账单？此操作不可逆","ajaxConfirm("+id+",1)");
        } else if (status == '-1') {
    		showConfirm("不同意　对账单","确认不同意此对账单？此操作不可逆","ajaxConfirm("+id+",-1)");
        }
    }

    /**
     * 同意、不同意
     */
    function ajaxConfirm(saleBillId,confirmStatus) {
        var datapate = {
            saleBillId: saleBillId,
            confirmStatus: confirmStatus
        }
        $.ajax({
            url: jsBasePath+'/saleBill/ajaxConfirm.json',
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            data: datapate,
            success: function (data) {
                if (data.result.errorCode == 0) {
                    formSubmit(1);//表单提交刷新页面
                } else {
                    showInfoBox("确认失败");
                }
            }, error: function () {
            }
        });
    }

    
    function apprReceiptConfirm() {
		showConfirm(null,"您确定要申请服务费发票吗？","apprReceipt()");
    }
    

    function apprReceipt() {
        $.ajax({
            url: jsBasePath+'/saleBill/apprReceipt.json',
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            success: function (data) {
                if (data.result.errorCode == 0) {
                    showInfoBox("申请已提交，将在5-15工作日内收到发票，请留意发票管理中的邮寄信息。");

            		setTimeout("formSubmit(1)",1500);////格式化产地
                } else {
                    showInfoBox(data.result.message);
                }
            }, error: function () {
            }
        });
    }