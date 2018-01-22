

    $(document).ready(function () {
        selectedHeaderLi("wddp_header");

        ajaxGetCategoryListByids();

        $('#sub_specificationsChange').submit(function () {
            // 提交表单
            $('#sub_specificationsChange').ajaxSubmit(function (data) {
                alert(data);
            });
            // 为了防止普通浏览器进行表单提交和产生页面导航（防止页面刷新？）返回false
            return false;
        });


    });

    /**
     * 全选记录
     */
    function checkTotal() {
        var isTotalCheck = $("input:checkbox[id='total']").prop("checked");
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
        document.getElementById("sub_form").reset();
    }

    /**
     * 删除（批量删除、单个删除）
     */
    function updateAllObject(id, isMarketable, status) {
        var ids = "";
        if (id == 0) {//批量删除
            $list = $("[name=ck]:checked");
            $list.each(function (i, val) {
                ids = ids + $($list[i]).attr("value") + ",";
            });
            if (ids != "") {
                ids = ids.substring(0, ids.length - 1);
            }
        } else {//单个删除
            ids = id;
        }
        var selltype = $("#selltype").val();
        if (selltype == 'selling') {
            isMarketable = -1;
        } else if (selltype == 'waitsell') {
            status = 1;
        }

        var datapate = {
            isMarketable: isMarketable,
            status: status,
            ids: ids
        }
        if (ids != '') {
            $.ajax({
                url: jsBasePath+'/product/ajaxUpdate.json',
                type: "GET",
                dataType: "json",  //返回json格式的数据
                async: true,
                data: datapate,
                success: function (data) {
                    if (data.result.errorCode == 0) {
                        formSubmit(1);//表单提交刷新页面
                    }
                }, error: function () {
                }
            });
        } else {
            alert("至少选择一条!");
        }
    }


    /**
     *ajax加载类目
     */
    function ajaxGetCategoryListByids() {
        var categoryidtemp = $("#categoryidtemp").val();
        var basePath = jsBasePath;
        $.ajax({
            url: basePath + '/productCategory/ajaxGetCategoryListByids.json',
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            success: function (data) {
                var html = '<option value="">--请选择--</option>';
                if (data.result.errorCode == 0) {
                    if (data.result.msgBody.length > 0) {
                        for (var i = 0; i < data.result.msgBody.length; i++) {
                            if (data.result.msgBody[i].id == categoryidtemp) {
                                html += '<option value="' + data.result.msgBody[i].id + '" selected>' + data.result.msgBody[i].name + '</option>';
                            } else {
                                html += '<option value="' + data.result.msgBody[i].id + '">' + data.result.msgBody[i].name + '</option>';
                            }
                        }
                    }
                }
                $("#categoryid").html(html);
            }, error: function () {
            }
        });
    }


    /**
     * 弹出层 提交审核
     */
    function subCheckAlert(name, id) {
        //alert(name+"_"+id);
        $("#shop_popup_true").find("#alertproductname").text(name);
        $("#shop_popup_true").find("#alertproductid").val(id);
        $("#shop_popup_true").show();
    }

    /**
     * 确定提交审核或下架
     */
    function subforCheck(obj) {
        var id = $(obj).prev("#alertproductid").val();
        var selltype = $("#selltype").val();
        if (selltype == 'selling') {
            updateAllObject(id, -1, null);
        } else {
            updateAllObject(id, null, 1);
        }
    }

    /**
     *弹出层修改sku价格
     **/
    function ajaxUpdatePrice(productid) {
        var basePath = jsBasePath;
        $.ajax({
            url: basePath + '/product/ajaxGetProductForUpdate.json?id=' + productid,
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            success: function (data) {
                var html = '';
                if (data.result.errorCode == 0) {
                    html += '<div class="popup_tl">';
                    html += '<span>商品条码：' + data.result.msgBody.barcode + '</span>';
                    html += '<span>所属类目：' + data.result.msgBody.categoryName + '</span>';
                    html += '</div>';
                    html += '<div class="popup_info">';
                    html += '<div class="p_img"><a href="#"><img src="' + data.result.msgBody.image + '" width="78" height="78" alt="Me-order-img"></a></div>';
                    html += '<p><a href="#">' + data.result.msgBody.name + '</a></p>';
                    html += '</div>';
                    html += '<div class="faster_alter">';
                    html += '<div class="alter_mark">快捷修改</div>';
                    html += '<div class="alter_cont">';
                    html += '<div class="alter_cont_title">';
                    html += '<strong>价格</strong>';
                    html += '<strong>库存</strong>';
                   // html += '<strong>预警值</strong>';
                    html += '</div>';
                    html += '<form id="sub_specificationsChange"  action='+jsBasePath+'+"/product/ajaxSpecificationsChange.html" method="post">';
                    html += '<input type="hidden" id="selltypenew" name="selltypenew" value="'+jsSelltype+'"/>';
                    html += '<input type="hidden" id="productid" name="productid" value="' + data.result.msgBody.id + '"/>';
                    if (data.result.msgBody.productSpecificationslist != null && data.result.msgBody.productSpecificationslist != "") {
                        for (var i = 0; i < data.result.msgBody.productSpecificationslist.length; i++) {
                            html += '<div class="alter_cont_list">';
                            html += '<span>' + data.result.msgBody.productSpecificationslist[i].itemnames + '</span>';
                            html += '<input class="common_input f98" type="text" onchange="specificationsChange(this)" name="productprice"  ismust="1"   typename="input"   maxLength="10" value="' + data.result.msgBody.productSpecificationslist[i].price + '">';
                            html += '<input class="common_input f98" type="text" onchange="specificationsChange(this)" name="productnum"  ismust="1"   typename="input"   maxLength="10" value="' + data.result.msgBody.productSpecificationslist[i].stock + '">';
                            html += '<input class="common_input f98" type="hidden" onchange="specificationsChange(this)" name="warnnum"  ismust="1"   typename="input"   maxLength="10" value="' + data.result.msgBody.productSpecificationslist[i].warnnum + '"><input type="hidden" id="' + data.result.msgBody.productSpecificationslist[i].id + '" name="specifications_result" value="' + data.result.msgBody.productSpecificationslist[i].id + '_' + data.result.msgBody.productSpecificationslist[i].price + '_' + data.result.msgBody.productSpecificationslist[i].stock + '_' + data.result.msgBody.productSpecificationslist[i].warnnum + '"/>';
                            html += '</div>';
                        }
                    }
                    html += '</form>';
                    html += '</div>';
                    html += '</div>';
                    html += '<div class="clear"></div>';
                    html += '<div class="popup_btn">';
                    html += '<div class="popupbtn"><a href="Javascript:void(0);" onclick="$(\'#sub_specificationsChange\').submit();">确认</a></div>';
                    html += '<div class="popupbtn" id="cansel"><a href="javascript:void(0);"  onclick="$(\'#shop_popup\').hide();">取消</a></div>';
                    html += '</div>';


                }
                $("#alertPriceDiv").html(html);
                $("#shop_popup").show();
            }, error: function () {
            }
        });
    }

    /**
     *skuchange
     */
    function specificationsChange(obj) {
        $(obj).removeClass("bctxt");
        var $par = $(obj).parent(".alter_cont_list");
        var productprice = $par.find("[name=productprice]").val();
        var productnum = $par.find("[name=productnum]").val();
        var warnnum = $par.find("[name=warnnum]").val();
        if (productprice != '' && productnum != '' && warnnum != '') {
            var id = $par.find("[name=specifications_result]").attr("id");
            $par.find("[name=specifications_result]").val(id + "_" + productprice + "_" + productnum + "_" + warnnum);
        } else {
            $par.find("[name=specifications_result]").val("");
        }
    }

    function ajaxFormSubmit() {
        $('#sub_specificationsChange').ajaxForm(function () {
            alert("Thank you for your comment!");
        });
    }

    /**
     *排序
     */
    function sortOclick(name) {
        if ($("#" + name).val() == 1) {
            $("#" + name).val(2);
        } else {
            $("#" + name).val(1);
        }

        if ($("[name='" + name + "_i'").hasClass("down")) {
            $("[name='" + name + "_i'").removeClass("down");
        } else {
            $("[name='" + name + "_i'").addClass("down");
        }

        $("#sortname").val(name);

        formSubmit();
    }

    /**
     * 商品详情
     */
    function productVideo(id) {
        window.location.href = jsBasePath+"/product/productView.html?productId=" + id;
    }
