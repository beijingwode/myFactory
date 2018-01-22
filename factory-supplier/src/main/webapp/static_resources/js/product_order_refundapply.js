    function sub_() {
        $("#sub_form").submit();
    }

    var SysSecond = 1000;
    var InterValObj;
    $(document).ready(function () {
        selectedHeaderLi("ddgl_header");
        var stat = jsRefundorderStatus;
        if (stat == '1' || stat == '2') {
            var date1 = new Date();  //开始时间
            var str = $("#sj").val();

            var date2 = new Date(str.replace(/-/g, "/"));
            SysSecond = date2.getTime() - date1.getTime();  //差的毫秒数
            InterValObj = window.setInterval(SetRemainTime, 1000);
            bianhui();
        }

    });

    function bianhui() {
    	var stat = jsRefundorderStatus;
        var date1 = new Date();
        var str = $("#sj").val();
        var date2 = new Date(str.replace(/-/g, "/"));
        if (date2 == undefined || date2 <= date1) {
            $("#tytk").css("background", "#959595");
            $("#jjtk").hide();
            $("#tytk").unbind("click");
        } else {
            $("#tytk").click(function () {
                sub_();
            });
        }
    }


    function SetRemainTime() {
        if (SysSecond > 1000) {
            SysSecond = SysSecond - 1000;
            //计算出相差天数
            var days = Math.floor(SysSecond / (24 * 3600 * 1000))

            //计算出小时数
            var leave1 = SysSecond % (24 * 3600 * 1000)    //计算天数后剩余的毫秒数
            var hours = Math.floor(leave1 / (3600 * 1000))
            //计算相差分钟数
            var leave2 = leave1 % (3600 * 1000)        //计算小时数后剩余的毫秒数
            var minutes = Math.floor(leave2 / (60 * 1000))
            //计算相差秒数
            var leave3 = leave2 % (60 * 1000)      //计算分钟数后剩余的毫秒数
            var seconds = Math.round(leave3 / 1000)

            $("#divdown1").html("( 倒计时：<i class=\"red\">" + days + "天" + hours + "时" + minutes + "分" + seconds + "秒</i>)");
            bianhui();
        } else {
            bianhui();
            window.clearInterval(InterValObj);
//			$(".O_btn1 .a1").css("cursor","default");
        }
    }
    function cancelButton(id) {
        $("#" + id).hide();
        $(".popup_bg").hide();
    }
    /**
     * 拒绝退款
     */
    function refuseAlert(id) {
        $("#delivertime").find("[name='refundOrderId']").val(id);
        $("#delivertime").show();
        $(".popup_bg").show();
    }
    /**
     * 联系客服
     */
    function afterServiceAlert() {
        $("#afterService").show();
        $(".popup_bg").show();
    }
    /**
     * 拒绝延迟收货
     */
    function refuse() {
        var id = $("#delivertime").find("[name='refundOrderId']").val();
        var subOrderId = $("#delivertime").find("[name='subOrderId']").val();
        var refuseNote = $("#delivertime").find("[name='delivertimeSelect']").find("option:selected").val();
        if(refuseNote == null || refuseNote == ""){
        	alert("请选择退货理由");
        	return;
        }
        $.ajax({
            url: jsBasePath+"/returnorder/ajaxRefuse.json?subOrderId=" + subOrderId + "&refundOrderId=" + id + "&refuseNote=" + refuseNote,
            type: "GET",
            dataType: "json",
            async: true,
            success: function (data) {
                window.location.reload();
            },
            error: function () {
            }
        });
    }