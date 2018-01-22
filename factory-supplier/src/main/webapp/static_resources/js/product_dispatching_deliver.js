  $(document).ready(function () {
        ajaxLoadNext('root', 'root');

        selectedHeaderLi("psgl_header");

        $("input").bind('change', function () {
            $(this).removeClass("bctxt");
        });
        $("select").bind('change', function () {
            $(this).removeClass("bctxt");
        });

    });


    /**
     *第一级省的单击事件
     */
    function provinceOnchange(obj) {
        var id = $(obj).val();
        var thisid = $(obj).attr("id");
        ajaxLoadNext(id, thisid);
        $("#busAddress").html("<option   value='-1'>--请选择--</option>");//清空第三级县的选项
        if (id != '-1') {
            $("[name='" + thisid + "']").val($(obj).find("option:selected").text());
        }
    }

    /**
     *第二级市的单击事件
     */
    function townOnchange(obj) {
        var thisid = $(obj).attr("id");
        if (thisid != '-1') {
            $("[name='" + thisid + "']").val($(obj).find("option:selected").text());
        }
    }


    /**
     *动态格式化下一级的类目
     */
    function ajaxLoadNext(parentid, selectid) {
        var bus = "";
        var data;
        if (selectid == 'root') {
            selectid = 'provinceName';
            data=getArea90(0,null);
            bus = jsProvinceName;
        } else if (selectid == 'provinceName') {
            selectid = 'cityName';
            data=getArea90(2,parentid);
            bus = jsCityName;
        }

         var html = "<option  value='-1'>--请选择--</option>";
         var selected = 'selected="selected"';

         if (data.length > 0) {
             for (var i = 0; i < data.length; i++) {
                 html += '<option value="' + data[i].code + '" ' + (bus != null && bus == data[i].name ? selected : "") + ' texttemp="' + data[i].name + '">' + data[i].name + '</option>';
             }
         }
         
         $("#" + selectid).html(html);
    }

    function _submit() {
        if (validatorForm()) {
            $('#sub_form_resetpwd').submit();
        }
    }

    //非空验证
    function validatorForm() {
        var name = $("#name").val();
        if (!/^[\u4e00-\u9fa5]|[a-zA-Z]+$/.test(name)) {
            $("#name").focus();
            $("#name1").fadeIn();
            setTimeout("display()", 6000);
            return false;
        }
        var provinceName = $("#provinceName").val();
        var cityName = $("#cityName").val();
        if (provinceName == '-1' || cityName == '-1') {
            $("#provinceName").focus();
            $("#province1").fadeIn();
            setTimeout("display()", 6000);
            return false;
        }
        var address = $("#address").val();
        if (address.replace(/[ ]/g, "").length == 0) {
            $("#address").focus();
            $("#address1").fadeIn();
            setTimeout("display()", 6000);
            return false;
        }
        var aid = $("#aid").val();
        if (aid.length != 6) {
            $("#aid").focus();
            $("#aid1").fadeIn();
            setTimeout("display()", 6000);
            return false;
        }
        var phone = $("#phone").val();
        var tel = $("#tel").val();
        if (phone.replace(/[ ]/g, "").length == 0 && tel.replace(/[ ]/g, "").length == 0) {
            $("#tel").focus();
            $("#tel1").fadeIn();
            setTimeout("display()", 6000);
            return false;
        } else if (phone.replace(/[ ]/g, "").length != 0 && !/^1[3|4|5|8][0-9]\d{4,8}$/.test(phone)) {
            $("#phone").focus();
            $("#phone2").fadeIn();
            setTimeout("display()", 6000);
            return false;
        }


        return true;
    }

    function display() {
        var $list = $("[name=error]");
        $list.each(function (i, val) {
            $($list[i]).fadeOut();
        });
        $("#cg").fadeOut();
        $("#sb").fadeOut();
    }


    function editajax(id) {
        var basePath = jsBasePath;
        $.ajax({
            url: basePath + '/shippingAddress/edit.json?id=' + id,
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            success: function (data) {

                if (data.result.errorCode == 0) {
                    var supplierAddress = data.result.msgBody;
                    $("#name").val(supplierAddress.name);
                    $("#province").val(supplierAddress.provinceName);
                    $("#city").val(supplierAddress.cityName);
                    $("#address").val(supplierAddress.address);
                    $("#aid").val(supplierAddress.aid);
                    $("#tel").val(supplierAddress.tel);
                    $("#phone").val(supplierAddress.phone);
                    $("#companyname").val(supplierAddress.companyname);
                    $("#comments").val(supplierAddress.comments);
                    $("#id").val(supplierAddress.id);
                    //$("#cityName").find("option[text='"+supplierAddress.cityName+"']").attr("selected",true);
                    $("#provinceName").find("option[texttemp='" + supplierAddress.provinceName + "']").attr("selected", true);
                }

                initProductAddress();

            }, error: function () {
            }
        });
    }

    /**
     *格式化商品产地
     */
    function initProductAddress() {
        var provincetemp = $("#provinceName").val();
        if (provincetemp != '') {
            ajaxLoadNext(provincetemp, 'provinceName');//格式化区域:默认加载市
            setTimeout("setProductAddress()", 200);
        }
    }

    //赋值
    function setProductAddress() {
        var countytemp = $("#city").val();
        if (countytemp != '') {
            $("#cityName").find("option[texttemp='" + countytemp + "']").attr("selected", true);
        }
    }

    function setdefault(id, type) {
        var basePath = jsBasePath;
        $.ajax({
            url: basePath + '/shippingAddress/setdefault.json?id=' + id + '&type=' + type,
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            success: function (data) {

                if (data.result.errorCode == 0) {
                    var shippingAddress = data.result.msgBody;
                }

            }, error: function () {
            }
        });
    }

    var s = jsSize;

    function delcategory2() {
        $(".popup_bg").hide();
        $("#shop_popup_delete").fadeOut();
    }

    function delcategory(id) {
        $("#deleteid").val(id);
        $(".popup_bg").show();
        $("#shop_popup_delete").fadeIn();
    }

    function deleteajax() {
        var id = $("#deleteid").val();
        var basePath = jsBasePath;
        $.ajax({
            url: basePath + '/shippingAddress/delete.json?id=' + id,
            type: "GET",
            dataType: "json",  //返回json格式的数据
            async: true,
            success: function (data) {

                if (data.result.errorCode == 0) {
                    var a = $("#id").val();
                    if (a == id) {
                        $("#id").val("");
                    }
                    $("#" + id + "").hide();
                    s++;
                    $("#dzsize").html("您还可以添加" + s + "条地址");
                    if (data.result.key != undefined) {
                        if (data.result.size == 2) {
                            $("#" + data.result.key).find("input[name=send]").attr("checked", true);
                            $("#" + data.result.key).find("input[name=returned]").attr("checked", true);
                        } else {
                            $("#" + data.result.key).find("input[name=" + data.result.message + "]").attr("checked", true);
                        }
                    }
                }

            }, error: function () {
            }
        });
        delcategory2();
    }

    function ValidateValue(textbox) {
        var IllegalString = "\`~@#;,.!！‘；“（）#$%^&*()+{}|\\:\"<>?-=/,\'";
        var textboxvalue = textbox.value;
        var index = textboxvalue.length - 1;

        var s = textbox.value.charAt(index);

        if (IllegalString.indexOf(s) >= 0) {
            s = textboxvalue.substring(0, index);
            textbox.value = s;
        }
    }
