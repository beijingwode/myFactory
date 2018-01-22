 $(document).ready(function () {
        selectedHeaderLi("hdgl_header");
    });

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

    function toNext() {
        $("#sub_form2").submit();
    }
    ;

    function toQuery(i) {
        $("#status").val(i);
        $("#sub_form").submit();
    }
    ;

    /**
     * 退出活动显示框
     */
    function closeAlert(id) {
        $("#sec-delete").find("[name='id']").val(id);
        $("#sec-delete").show();
        $(".popup_bg").show();
    }

    /**
     * 退出活动
     */
    function closeAlertImpl() {
        var id = $("#sec-delete").find("[name='id']").val();
        $.ajax({
            url: jsBasePath+"/promotion/ajaxUpdatePromotionProduct.json?status=-2&id=" + id,
            type: "GET",
            dataType: "json",
            async: true,
            success: function (data) {
                location.reload();
            },
            error: function () {
            }
        });
        $("#sec-delete").hide();
    }

    function cancelButton() {
        $("#sec-delete").hide();
        $(".popup_bg").hide();
    }

    function closeAlert2(id) {
        $("#sec-detail" + id + "").show();
        $(".popup_bg").show();
    }

    function cancelButton2(id) {
        $("#sec-detail" + id + "").hide();
        $(".popup_bg").hide();
    }