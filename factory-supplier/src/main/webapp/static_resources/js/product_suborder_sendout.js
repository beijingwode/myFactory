
    $(document).ready(function () {
        selectedHeaderLi("psgl_header");

        ajaxLoadNext('root', 'root');//格式化区域:默认加载省级

        ajaxLoadNext1('root', 'root');
        $("input").bind("change", function () {
            $(this).removeClass("bctxt");
        });
        $("select").bind("change", function () {
            $(this).removeClass("bctxt");
        });

    });

    // /*********************收货地址start***********************/
    /**
     *第一级省的单击事件
     */
    function provinceOnchange(obj) {
        var id = $(obj).val();
        var thisid = $(obj).attr("id");
        ajaxLoadNext(id, thisid);
        $("#county").html("<option   value='-1''>-请选择-</option>");//清空第三级县的选项
    }


    /**
     *第二级市的单击事件
     */
    function townOnchange(obj) {
        var id = $(obj).val();
        var thisid = $(obj).attr("id");
        ajaxLoadNext(id, thisid);

    }

    /**
     *第三级的单击事件
     */
    function countyOnchange(obj) {
        var thisid = $(obj).attr("id");
    }
    /**
     *动态格式化下一级的类目
     */
    function ajaxLoadNext(parentid, selectid) {
        var data;
        if (selectid == 'root') {
            selectid = 'province';
            data=getArea90(0,null);
        } else if (selectid == 'province') {
            selectid = 'town';
            data=getArea90(2,parentid);
        } else if (selectid == 'town') {
            selectid = 'county';
            data=getArea90(3,parentid);
        }

         var html = "<option  value='-1'>-请选择-</option>";

          for (var i = 0; i < data.length; i++) {
              html += '<option value="' + data[i].code + '">' + data[i].name + '</option>';
          }

         $("#" + selectid).html(html);

    }
    /*********************收货地址end***********************/

    function cancelButton(id) {
        $("#" + id).hide();
        $(".popup_bg").hide();
    }

    /**
     *修改地址1：收货人地址    2：发货地址   3：退货地址
     */
    function updateAddress(type) {
        $("#updateTypename").val(type);
        var html = "";
        if (type == 'sendAddress' || type == 'returnedAddress') {
            if (type == 'sendAddress') {
                $("#update_span").html("修改发货地址");
            }
            if (type == 'returnedAddress') {
                $("#update_span").html("修改退货地址");
            }
            $.ajax({
                url: jsBasePath+"/shippingAddress/ajaxGetAddresslist.json",
                type: "GET",
                dataType: "json",
                async: true,
                success: function (data) {
                    if (data.result.errorCode == 0) {
                        if (data.result.msgBody.length > 0) {
                            for (var i = 0; i < data.result.msgBody.length; i++) {
                                html += '<div class="deliver_pop" >';
                                html += '<input type="hidden" id="typenamealert" value="' + type + '"/>';
                                html += '<input type="hidden" id="sendAddressHidd" value="' + data.result.msgBody[i].provinceName + data.result.msgBody[i].cityName + data.result.msgBody[i].address + '，' + data.result.msgBody[i].aid + '，' + data.result.msgBody[i].name + '，';
                                if (data.result.msgBody[i].phone != '') {
                                    html += data.result.msgBody[i].phone;
                                } else {
                                    html += data.result.msgBody[i].tel;
                                }
                                html += '"/>';
                                html += '<span style="width:69px;white-space:nowrap;overflow:hidden;height: 17px;text-overflow:ellipsis;display: inline-block;cursor:pointer;" title="' + data.result.msgBody[i].name + '" onclick="radilClick(this);"><input class="radio" type="radio" name="sendAddress" ';
                                if (type == 'sendAddress') {
                                    if (data.result.msgBody[i].send == 1) {
                                        html += ' checked="checked"';
                                    }
                                } else if (type == 'returnedAddress') {
                                    if (data.result.msgBody[i].returned == 1) {
                                        html += ' checked="checked"';
                                    }
                                }
                                html += '>' + data.result.msgBody[i].name + '</span>';
                                html += '<span style="width: 362px;white-space:nowrap;overflow:hidden;height: 15px;text-overflow:ellipsis;display: inline-block;cursor:default;" title="' + data.result.msgBody[i].provinceName + data.result.msgBody[i].cityName + data.result.msgBody[i].address + '">' + data.result.msgBody[i].provinceName + data.result.msgBody[i].cityName + data.result.msgBody[i].address + '</span>';
                                html += '<span style="width:43px;">' + data.result.msgBody[i].aid + '</span>';
                                if (data.result.msgBody[i].phone != '') {
                                    html += '<span style="width:126px;">' + data.result.msgBody[i].phone + '</span>';
                                } else {
                                    html += '<span style="width:126px;">' + data.result.msgBody[i].tel + '</span>';
                                }

                                html += '</div>';
                            }
                            html += '<div class="clear"></div>';
                            html += '<div class="popup_btn">';
                            html += '<a href="'+jsBasePath+'/shippingAddress/todeliver.html"  target="_blank">管理地址库</a>';
                            html += '</div>';
                        }

                        $("#change_address").find(".popup_cont").html(html).end().show();
                        $(".popup_bg").show();
                    }
                },
                error: function () {
                }
            });
        } else {
            $('#change_deliver').find(".bctxt").removeClass("bctxt");
            $('#change_deliver').show();
            $(".popup_bg").show();
        }
    }


    /**
     *修改地址userAddress_id：收货人地址    sendAddress：发货地址   returnedAddress：退货地址
     */
    function updateAddressSubmit() {
        var type = $("#updateTypename").val();
        var html = "";
        if (type == 'sendAddress' || type == 'returnedAddress') {
        } else {
            var provincetext = $("#province").find("option:selected").text();
            var towntext = $("#town").find("option:selected").text();
            var countytext = $("#county").find("option:selected").text();
            var detailaddress = $("#detailaddress").val();
            var username_alert = $("#username_alert").val();
            var mobile_alert = $("#mobile_alert").val();
            var id = type.split("_")[1];
            var isok = true;
            if ($("#province").val() == -1) {
                $("#province").addClass("bctxt");
                isok = false;
            }
            if ($("#town").val() == -1) {
                $("#town").addClass("bctxt");
                isok = false;
            }
            if ($("#county").val() == -1) {
                $("#county").addClass("bctxt");
                isok = false;
            }
            if ($("#detailaddress").val() == '') {
                $("#detailaddress").addClass("bctxt");
                isok = false;
            }
            if ($.trim(username_alert) == '') {
                $("#username_alert").addClass("bctxt");
                isok = false;
            }
            if ($.trim(mobile_alert) == '') {
                $("#mobile_alert").addClass("bctxt");
                isok = false;
            }

            if (isok == true) {
                var address = provincetext + towntext + countytext + detailaddress;
                var dataPata = {
                    address: address,
                    name: username_alert,
                    mobile: mobile_alert,
                    subOrderId: id
                }
               
                $.ajax({
                    url: jsBasePath + '/suborder/ajaxUpdateOrder.json',
                    type: "POST",
                    dataType: "json",  //返回json格式的数据
                    async: true,
                    data: dataPata,
                    success: function (data) {
                        $("#" + type).text(address + "，" + $.trim(username_alert) + "，" + $.trim(mobile_alert)).attr("title", address + "，" + $.trim(username_alert) + "，" + $.trim(mobile_alert));
                    }, error: function () {
                    }
                });
                cancelButton('change_deliver');
            }
        }
    }

    function radilClick(obj) {
        var sendAddressHidd = $(obj).parents(".deliver_pop").find("#sendAddressHidd").val();
        var typenamealert = $(obj).parents(".deliver_pop").find("#typenamealert").val();
        $("#" + typenamealert).val(sendAddressHidd);
        $("#" + typenamealert + "Temp").text(sendAddressHidd).attr("title", sendAddressHidd);
        cancelButton('change_address');
    }
    /**
     * submit
     */
    function formSubmit() {
        if (validatorForm()) {
            $("#sub_form").submit();
            $("#sub_button").addClass("heycolor").removeAttr("onclick");
        } else {
            $(".popup_bg").show();
            $("#popup_alert").find(".box_msg").text("请补充完整红色区域，再次提交！").end().show();
        }
    }

    //非空验证
    function validatorForm() {
        var selfDelivery = jsSelfDelivery;
        var ret = 0;
        if("1"!=selfDelivery) {
	        var expressType = $("[name=expressType]").val();
	        var $expressNolist = $("[name='expressNo']");
	        if (expressType !="14660000000000001" ) {//厂家直送
	        	 if ($.trim(expressType) == ''||expressType==0) {
	 	            $("[name=expressType]").addClass("bctxt");
	 	            ret++;
	 	        }
	 	        $expressNolist.each(function (i, val) {
	 	            if ($.trim($($expressNolist[i]).val()) == '') {
	 	                $($expressNolist[i]).addClass("bctxt");
	 	                ret++;
	 	            }
	 	        });
			}
        }

        var sendAddressTemp = $("#sendAddressTemp").text();
        //var returnedAddressTemp = $("#returnedAddressTemp").text();
        if (sendAddressTemp == '') {
            $("#sendAddressTemp").parent().addClass("bordercolor");
            ret++;
        }
        /*if (returnedAddressTemp == '') {
            $("#returnedAddressTemp").parent().addClass("bordercolor");
            ret++;
        }*/
        if (ret > 0) {
            return false;
        } else {
            return true;
        }
    }


    function _submit1() {
        if (validatorForm1()) {
            var dataPa = {
                name: $("#name").val(),
                provinceName: $("[name='provinceName']").val(),
                cityName: $("[name='cityName']").val(),
                address: $("#address").val(),
                aid: $("#aid").val(),
                phone: $("#phone").val(),
                tel: $("#tel").val(),
                companyname: $("#companyname").val(),
                comments: $("#comments").val()
            }
            $.ajax({
                url: jsBasePath+'/shippingAddress/ajaxsave.json',
                type: "POST",
                dataType: "json",  //返回json格式的数据
                data: dataPa,
                async: true,
                success: function (data) {
                    if (data.result.errorCode == 0) {
                        cancelButton('add_receive');
                        if (data.result.msgBody != null) {
                            var obj = data.result.msgBody;
                            var htmlNew = "";
                            if (obj != null) {
                                htmlNew += '<p><a href="javascript:updateAddress(\'sendAddress\');">修改发货地址</a><span style="float: left;">我的发货地址：</span><span id="sendAddressTemp" style="width: 700px; height: 20px;overflow: hidden;float: left;" title="' + obj.provinceName + obj.cityName + obj.address + '，' + obj.aid + '，' + obj.name + '，' + obj.phone + '">' + obj.provinceName + obj.cityName + obj.address + '，' + obj.aid + '，' + obj.name + '，' + obj.phone + '</span></p><input type="hidden" id="sendAddress" name="sendAddress" value="' + obj.provinceName + obj.cityName + obj.address + '，' + obj.aid + '，' + obj.name + '，' + obj.phone + '"/>';
                                //htmlNew += '<p><a href="javascript:updateAddress(\'returnedAddress\');">修改退货地址</a><span style="float: left;">我的退货地址：</span><span id="returnedAddressTemp" style="width: 700px; height: 20px;overflow: hidden;float: left;" title="' + obj.provinceName + obj.cityName + obj.address + '，' + obj.aid + '，' + obj.name + '，' + obj.phone + '">' + obj.provinceName + obj.cityName + obj.address + '，' + obj.aid + '，' + obj.name + '，' + obj.phone + '</span></p><input type="hidden" id="returnedAddress" name="returnedAddress" value="' + obj.provinceName + obj.cityName + obj.address + '，' + obj.aid + '，' + obj.name + '，' + obj.phone + '"/>';
                                $(".shipping_address").html(htmlNew);
                            }
                        }
                    }
                }, error: function () {
                }
            });
        }
    }

    //非空验证
    function validatorForm1() {
        var name = $("#name").val();
        if (!/^[\u4e00-\u9fa5]|[a-zA-Z]+$/.test(name)) {
            $("#name").focus();
            $("#name1").fadeIn();
            setTimeout("display()", 3000);
            return false;
        }
        var provinceName = $("#provinceName").val();
        var cityName = $("#cityName").val();
        if (provinceName == '-1' || cityName == '-1') {
            $("#provinceName").focus();
            $("#province1").fadeIn();
            setTimeout("display()", 3000);
            return false;
        }
        var address = $("#address").val();
        if (address.replace(/[ ]/g, "").length == 0) {
            $("#address").focus();
            $("#address1").fadeIn();
            setTimeout("display()", 3000);
            return false;
        }
        var aid = $("#aid").val();
        if (aid.length != 6) {
            $("#aid").focus();
            $("#aid1").fadeIn();
            setTimeout("display()", 3000);
            return false;
        }
        var phone = $("#phone").val();
        var tel = $("#tel").val();
        if (phone.replace(/[ ]/g, "").length == 0 && tel.replace(/[ ]/g, "").length == 0) {
            $("#tel").focus();
            $("#tel1").fadeIn();
            setTimeout("display()", 3000);
            return false;
        } else if (phone.replace(/[ ]/g, "").length != 0 && !/^1[3|4|5|8][0-9]\d{4,8}$/.test(phone)) {
            $("#phone").focus();
            $("#phone2").fadeIn();
            setTimeout("display()", 3000);
            return false;
        }
        return true;
    }
    
    function display(){

   	 
   	 $("#name1").fadeOut();
   	 $("#province1").fadeOut();
   	 $("#address1").fadeOut();
   	
   	 $("#aid1").fadeOut();
   	
   	 $("#tel1").fadeOut();
   	 $("#phone2").fadeOut();
   	
   }

    /**
     *格式化商品产地
     */
    function initProductAddress() {
        var provincetemp = $("#provinceName").val();
        if (provincetemp != '') {
            ajaxLoadNext1(provincetemp, 'provinceName');//格式化区域:默认加载市
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

    /**
     *动态格式化下一级的类目
     */
    function ajaxLoadNext1(parentid, selectid) {
        var bus = "";
        var data;
        if (selectid == 'root') {
            selectid = 'provinceName';
            bus = jsProvinceName;
            data=getArea90(0,null);
        } else if (selectid == 'provinceName') {
            selectid = 'cityName';
            bus = jsCityName;
            data=getArea90(2,parentid);
        }

        var html = "<option  value='-1'>--请选择--</option>";
        var selected = 'selected="selected"';


        for (var i = 0; i < data.length; i++) {
            html += '<option value="' + data[i].code + '" ' + (bus != null && bus == data[i].name ? selected : "") + ' texttemp="' + data[i].name + '">' + data[i].name + '</option>';
        }

        $("#" + selectid).html(html);
    }

    function addAddress(sendOrreturned) {
        $("#add_receive").find("#sendOrreturned").val(sendOrreturned).end().find("#reciveSpan").val();
        if (sendOrreturned == 'sendAddress') {
            $("#add_receive").find("#reciveSpan").text("添加发货地址");
        } else {
            $("#add_receive").find("#reciveSpan").text("添加退货地址");
        }
        $("#add_receive").show();
        $(".popup_bg").show();
    }

    /**
     *第一级省的单击事件
     */
    function provinceOnchange1(obj) {
        var id = $(obj).val();
        var thisid = $(obj).attr("id");
        ajaxLoadNext1(id, thisid);
        $("#busAddress").html("<option   value='-1'>--请选择--</option>");//清空第三级县的选项
        if (id != '-1') {
            $("[name='" + thisid + "']").val($(obj).find("option:selected").text());
        }
    }

    /**
     *第二级市的单击事件
     */
    function townOnchange1(obj) {
        var thisid = $(obj).attr("id");
        if (thisid != '-1') {
            $("[name='" + thisid + "']").val($(obj).find("option:selected").text());
        }
    }

    function express_change(){
        var extId = $("select[name='expressType']").val();
        if(extId == "14660000000000000") {
        	$($("select[name='expressType']").parent()).next().children("span").html("&nbsp;&nbsp;卡券密码:");
        } else {
        	if (extId == "14660000000000001") {//厂家直送
        		$($("select[name='expressType']").parent()).next().children("span").html("&nbsp;&nbsp;货运号:");
        	}else{
        		$($("select[name='expressType']").parent()).next().children("span").html("&nbsp;&nbsp;物流单号:");
        	}
        	if(extId == "1") {
        		jsOption();
        	}
        }
        if (extId == "14660000000000001") {//厂家直送
    		$("#expressNo").attr("placeholder","此项可不填");
		}else{
			$("#expressNo").removeAttr("placeholder");
		}
	}

