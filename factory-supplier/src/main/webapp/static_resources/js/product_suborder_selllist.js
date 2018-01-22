    $(document).ready(function () {
        selectedHeaderLi("ddgl_header");
        //initspecificationsItemvalues();
        initUseFuliCoin();
    });

    /* function initspecificationsItemvalues(){
     var $list = $("[name='specificationsItemvalues']");
     $list.each(function (i,val){
     var obj = $($list[i]).text().toString();
     if(obj!=''){
     obj =obj.replace('\"','');
     obj = obj.substring(1,obj.length-1);
     }
     $($list[i]).text(obj);
     });
     } */

    function initUseFuliCoin() {
        var $list = $("div[name='useFulicoinDiv']");
        $list.each(function (i, val) {
            var $objlist = $($list[i]).find("input[name='useFuliCoin']");
            var useFulicoin = 0;
            $objlist.each(function (j, va) {
                var obj = $($objlist[j]).val();
                if (obj != null && obj != '') {
                    useFulicoin = useFulicoin + parseInt(obj);
                }
            })
            if (useFulicoin > 0) {
                $this = $($list[i]).find("p[name='useFuliCoin_p']");
                $this.text($this.text() + "(含" + useFulicoin + "内购券)");
            }
        });
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
     * 表单提交
     */
    function buttonSubmit(page) {
        //$("[name='type']").val(1);
        var type = $("[name=type]").val();

        if (type > 1 && type < 8) {
            //formReset();
            $("[name='type']").val(1);
        } else if (type == 8) {
            $("#Sold_search").find("li[name='oldLi']").find("select").find("option:first").attr("selected", "selected");
        } else if (type == 1) {
            $(".Sold_search").find("li[name='oldLi']").css("display", "online");
        }

        if (page != undefined) {
            $("#pages").val(page);
        } else {
            $("#pages").val(1);
        }
        $("#sub_form").submit();
    }

    /**
     * 表单提交
     */
    function exportExcel() {        
        var action = $("#sub_form").attr("action");
        $("#sub_form").attr("action",jsBasePath+"/suborder/exportExcel.html");
        $("#sub_form").attr("target","_blank");
        $("#sub_form").submit();
        $("#sub_form").attr("action",action);
        $("#sub_form").attr("target","_self");
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

    function statusChange() {
        var optionValue = $("[name='status']").find("option:selected").val();
        if (optionValue == '') {
            $("[name='status']").removeClass().addClass("select_s");
        } else {
            $("[name='status']").removeClass().addClass("select_ss");
        }
    }
    /**
    *备货
    */
    function sendStockUp(subOrderId){
    	$.ajax({
            url: jsBasePath+"/suborder/ajaxUpdateSuborderById.json?stockUp=1&subOrderId=" + subOrderId ,
            type: "GET",
            dataType: "json",
            async: true,
            success: function (data) {
                formSubmit(1);
            },
            error: function () {
            }
        });
    }
    /**
    *取消备货
    */
    function sendCancelStockUp(subOrderId){
    	$.ajax({
            url: jsBasePath+"/suborder/ajaxUpdateSuborderById.json?stockUp=0&subOrderId=" + subOrderId ,
            type: "GET",
            dataType: "json",
            async: true,
            success: function (data) {
                formSubmit(1);
            },
            error: function () {
            }
        });
    }
    /**
     * 单个发货
     */
    function sendOut(subOrderId) {
        var type = $("[name='type']").val();
        window.location.href = jsBasePath+"/suborder/toSendOut.html?subOrderId=" + subOrderId + "&gotoType=suborderlist&type=" + type;
    }

    /**
     * 批量发货
     */
    function sendOutAll() {
        var checkTotalNum = $("input:checkbox[name='ck']:checked");
        var checkNum = 0;
        var ids = "";
        checkTotalNum.each(function (i, val) {
            ids = ids + $(checkTotalNum[i]).attr("value") + ",";
            checkNum++;
        });
        if (checkNum == 0) {
            $("#popup_alert").find(".box_msg").html("至少选择一条订单进行发货").end().show();
            $(".popup_bg").show();
            return false;
        }

        if (ids != '') {
            ids = ids.substring(0, ids.length - 1);
        }
        var type = $("[name='type']").val();
        window.location.href = jsBasePath+"/suborder/toSendOutAll.html?ids=" + ids + "&gotoType=suborderlist&type=" + type;
    }

    /**
     * 商品详情
     */
    function productVideo(id) {
        window.location.href =jsBasePath+ "/product/productView.html?productId=" + id;
    }

    /**
     * 标签状态切换
     */
    function tagChange(obj) {
        $(obj).parent().parent().find("li>a").removeClass("a1").end().end().end().addClass("a1");
        var type = $(obj).attr("typename");
        $("[name=type]").val(type);

//	if(type>1&&type<8){
        formReset();
        /*}else*/
        if (type == 8) {
            $("#Sold_search").find("li[name='oldLi']").find("select").find("option:first").attr("selected", "selected");
        } else if (type == 1) {
            $(".Sold_search").find("li[name='oldLi']").css("display", "online");
        }
        formSubmit(1);
    }
    function cancelButton(id) {
        $("#" + id).hide();
        $(".popup_bg").hide();
    }

    /**
     * 关闭订单
     */
    function closeOrderAlert(id) {
        $("#close_order").find("[name='subOrderId']").val(id);
        $("#close_order").show();
        $(".popup_bg").show();
    }

    /**
     * 关闭订单
     */
    function closeOrder() {
        var id = $("#close_order").find("[name='subOrderId']").val();
        var closeSelect = $("#close_order").find("[name='closeSelect']").find("option:selected").val();
        $.ajax({
            url:jsBasePath+ "/suborder/ajaxUpdateSuborder.json?status=-1&subOrderId=" + id + "&closeReason=" + closeSelect,
            type: "GET",
            dataType: "json",
            async: true,
            success: function (data) {
                formSubmit(1);
            },
            error: function () {
            }
        });
    }


    /**
     * 延长收货时间
     */
    function delivertimeAlert(id) {
        $("#delivertime").find("[name='subOrderId']").val(id);
        $("#delivertime").show();
        $(".popup_bg").show();
    }

    /**
     * 延长收货时间
     */
    function delivertime() {
        var id = $("#delivertime").find("[name='subOrderId']").val();
        var delivertime = $("#delivertime").find("[name='delivertimeSelect']").find("option:selected").val();
        $.ajax({
            url:jsBasePath+ "/suborder/ajaxUpdateSuborder.json?delivertime=" + delivertime + "&subOrderId=" + id,
            type: "GET",
            dataType: "json",
            async: true,
            success: function (data) {
                formSubmit(1);
            },
            error: function () {
            }
        });
    }

    function updateFreight(id, freight){
        $("#freight_order_id").val(id);
        $("#freight").val(freight);
        $("#change_freight").show();
        $(".popup_bg").show();
        
    }

    function changeFreight() {
        var freight = $.trim($("#freight").val()), orderId = $.trim($("#freight_order_id").val());
        $.post(jsBasePath+"/suborder/updateFreight",{suborder:orderId, freight: freight},function(rel){
            if(rel.success){
            	gotoPage();
            }else{
            	showInfoBox(rel.msg);
            }
        },"json")
        $("#change_freight").hide();
        $(".popup_bg").hide();
    }

    
    function queryInvoice(id){
    	window.open(jsBasePath + "/invoice/getInvoice?id=" + id)
    }
    
    function selfDeliveryChange(obj){
    	if ($(obj).prop("checked")) {
    		$("[name=selfDelivery]").val(0);
    		$("input:checkbox[name='selfDelivery']").prop("checked", true);
        } else {
        	$("input:checkbox[name='selfDelivery']").prop("checked", false);
        	$("[name=selfDelivery]").val(1);
        }
    	//formReset();
    	formSubmit(1);
    }