
    var SysSecond = 1000;
    var InterValObj;
    $(document).ready(function () {
        selectedHeaderLi("ddgl_header");
        var stat = jsSuborderStatus;
        if (stat == '2') {
            var date1 = new Date();  //开始时间
            var str = $("#sj").val();
            var date2 = new Date(str.replace(/-/g, "/"));
            SysSecond = date2.getTime() - date1.getTime();  //差的毫秒数
            InterValObj = window.setInterval(SetRemainTime, 1000);
        }

        //物流情报显示
        if (jsExpressCom != '' && jsExpressCom != 'null') {
            var content = '"sname":"express.ExpressSearch","com":"'+jsExpressCom+'","express_no":"'+jsSuborderExpressNo+'","user":'+jsSearchId+',"version":"v2"';
            $.ajax({
                url: 'http://kuaidi.wo-de.com/express/busJsonp.do?content={' + content + '}&token=',
                dataType: 'jsonp',
                jsonp: 'jsonpcallback',
                success: function (json) {
                    var ary = eval(json.body.history)
                    var lis = "";
                    if (ary.length > 0) {
                        lis = '<p class="cur">' + ary[0].dealDate + '<span>' + ary[0].des + '</span></p>';
                        for (var i = 1; i < ary.length; i++) {
                            lis += '<p>' + ary[i].dealDate + '<span>' + ary[i].des + '</span></p>';
                            ;
                        }
                    }
                    if (lis != "") {
                        var htm = $(".flow_right").html();
                        $(".flow_right").html("<p>物流动态</p>" + lis + htm.replace("<p>物流动态</p>", ""));
                    } else {
                        var htm = $(".flow_right").html();
                        $(".flow_right").html("<p>物流动态</p>" + htm);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var htm = $(".flow_right").html();
                    $(".flow_right").html("<p>物流动态</p>" + htm);
                }
            });
        } else {
            var htm = $(".flow_right").html();
            $(".flow_right").html("<p>物流动态</p>" + htm);
        }
    });

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

            $("#divdown1").html(days + "天" + hours + "小时" + minutes + "分" + seconds + "秒");
        } else {
            window.clearInterval(InterValObj);
        }
    }


    /**
     * 单个发货
     */
    function sendOut(subOrderId) {
        window.location.href = jsBasePath+"/suborder/toSendOut.html?subOrderId=" + subOrderId;
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
            url: jsBasePath+"/suborder/ajaxUpdateSuborder.json?status=-1&subOrderId=" + id + "&closeReason=" + closeSelect,
            type: "GET",
            dataType: "json",
            async: true,
            success: function (data) {
                location.reload();
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
            url: jsBasePath+"/suborder/ajaxUpdateSuborder.json?delivertime=" + delivertime + "&subOrderId=" + id,
            type: "GET",
            dataType: "json",
            async: true,
            success: function (data) {
                location.reload();
            },
            error: function () {
            }
        });
    }

    function _jump() {
        var cd = $("#select").val();
        window.location.href =jsBasePath+ "/comments/toevaluation.html?commentDegree=" + cd;
    }

    function ChangeBg(id, obj) {
        $(".detail_cont").hide();
        $("#page" + id).show();
        $(".current").removeClass("current");
        $(obj).addClass("current");
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
     * 快速跳转
     */
    function gotoPage() {
        var pagenum = $("#pagenum").val();
        formSubmit(pagenum);
    }
