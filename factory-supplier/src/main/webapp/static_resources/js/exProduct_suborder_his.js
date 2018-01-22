

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
     * 标签状态切换
     */
    function tagChange(obj) {
        $(obj).parent().parent().find("li>a").removeClass("a1").end().end().end().addClass("a1");
        var type = $(obj).attr("typename");
        $("[name=type]").val(type);

        formSubmit(1);
    }
