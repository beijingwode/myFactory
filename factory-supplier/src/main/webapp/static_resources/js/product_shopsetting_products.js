
    function submitQuery() {
        if ($('[name="name"]').val() == '请输入商品名称')
            $('[name="name"]').val('');
        $('#queryForm').submit();
    }
    function addCategory(dom, id) {
        var ids = $(dom).attr('id-data').split(" ");
        $('[for = "categories"]').attr("checked", false);
        $.each(ids, function (index, item) {
            $("#" + item).attr("checked", true);
        });
        $('#catSaveButton').attr('for', id);
        $('.popup_bg').show();
        $('#sort_popup1').show();
        CM5.intro.start();
    }

    function addCategories() {
        var ids = $(".redio.pros:checked"), proIds = [];
        if (ids.length == 0) {
            showInfoBox("请选中要分类的商品!");
            return false;
        } else {
            ids.each(function (index) {
                proIds[index] = $(this).val();
            });
            $('#catSaveButton').attr('for', proIds.join(","));
            $('.popup_bg').show();
            $('#sort_popup1').show();
            CM5.intro.start();
        }
    }

    function selectAll(dom) {
        if ($(dom).is(":checked")) {
            $(".redio.pros").attr("checked", true);
            $('[name="selectAll"]').attr("checked", true);
        } else {
            $(".redio.pros").attr("checked", false);
            $('[name="selectAll"]').attr("checked", false);
        }
    }
    function saveCate(dom) {
        var proIds = $(dom).attr('for'), cats = $('[for = "categories"]:checked'), catIds = [];
        cats.each(function (index) {
            catIds[index] = $(this).val();
        });
        if (catIds.length == 0) {
            alert("请先选择分类!");
            return;
        } else {
            $.post(jsBasePath+"/category/saveCats.html", {proIds: proIds, catIds: catIds.join(",")}, function (data) {
                if (data == "success")$("#pageForm").submit();
            });
        }
    }
