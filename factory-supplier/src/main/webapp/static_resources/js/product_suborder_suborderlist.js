     $(document).ready(function () {
        selectedHeaderLi("psgl_header");
    });
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
                $this.text($this.text() + "+" + useFulicoin + "内购券");
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
     * 单个发货
     */
    function sendOut(subOrderId) {
        window.location.href = jsBasePath+"/suborder/toSendOut.html?subOrderId=" + subOrderId + "&gotoType=suborderlist";
    }
    /**
     * 切换标签页
     */
    function tagChange(obj) {
        initInput();
        var thisid = $(obj).attr("id");
        $(obj).parent().find("li").removeClass("curr").end().end().addClass("curr");
        if (thisid == 'dfhLi') {
            $("#status").val(1);
            $("#wuliuSearchDiv").hide();
        } else if (thisid == 'yfhLi') {
            $("#status").val(2);
            $("#wuliuSearchDiv").show();
        }
        formSubmit(0);
    }
    function initInput() {
        $("input").val("");
        $("select").val("");
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
            showInfoBox("至少选择一条订单进行发货");
            return false;
        }
        if (ids != '') {
            ids = ids.substring(0, ids.length - 1);
        }
        window.location.href = jsBasePath+"/suborder/toSendOutAll.html?ids=" + ids + "&gotoType=suborderlist";
        ;
    }
    /**
     * 商品详情
     */
    function productVideo(id) {
        window.location.href =jsBasePath+ "/product/productView.html?productId=" + id;
    }
    
    function express_change(){
        var extId = $("select[name='expressType']").val();
        if(extId == "14660000000000000") {
        	$($("select[name='expressType']").parent()).next().children("span").html("卡券密码:");
        } else {
        	$($("select[name='expressType']").parent()).next().children("span").html("物流单号:");
        }
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
    	formSubmit(0);
    }