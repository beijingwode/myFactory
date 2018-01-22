
    $(document).ready(function () {
        selectedHeaderLi("hdgl_header");

        // 初始化Web Uploader
        $("#filePicker").click(function () {
            $("#uploadFile").click();
        });

        // 初始化Web Uploader
        $("#filePicker_mobile").click(function () {
            $("#uploadFile_mobile").click();
        });

        initImage();
    });

    function initImage() {
        var pcImage = $("#pc_ban").attr("src");
        if (pcImage == '') {
            $("#pc_ban").attr("src", staticResources + "images/login_img.jpg");
        }

        var mobileImage = $("#mobile_ban").attr("src");
        if (mobileImage == '') {
            $("#mobile_ban").attr("src", staticResources + "images/login_img.jpg");
        }

    }
    function fileUpload() {
        $.ajaxFileUpload({
	        
			url: jsBasePath+'/upload/pic.json?folder='+ proId,
            type: 'post',
            secureuri: false, //一般设置为false
            fileElementId: "uploadFile", // 上传文件的id、name属性名
            dataType: 'json', //返回值类型，一般设置为json、application/json
            success: function (data, status) {
                if (data.success) {
	        		var imgsrc = data.data[0].original;
	        		if(imgsrc.indexOf("http://") != 0) {
	        			imgsrc = "http://"+imgsrc;
	        		}
                    $("#pc_ban").attr("src", imgsrc);
                    $("#bigImage").val(imgsrc);
                    $("#errorUploadSpan").css("display", "none");
                    //initDeltetClose();
                } else {
                    $("#errorUploadSpan").text(data.msg).css("display", "inline");
                }
            },
            error: function (data, status, e) {
                showInfoBox(e);
            }
        })
    }

    function fileUpload_mobile() {
        $.ajaxFileUpload({
	        url: jsBasePath +'/upload/pic.json?folder='+ proId,
            type: 'post',
            secureuri: false, //一般设置为false
            fileElementId: "uploadFile_mobile", // 上传文件的id、name属性名
            dataType: 'json', //返回值类型，一般设置为json、application/json
            success: function (data, status) {
                if (data.success) {
	        		var imgsrc = data.data[0].original;
	        		if(imgsrc.indexOf("http://") != 0) {
	        			imgsrc = "http://"+imgsrc;
	        		}
                    $("#mobile_ban").attr("src", imgsrc);
                    $("#smallImage").val(imgsrc);
                    $("#errorUploadSpan").css("display", "none");
                    //initDeltetClose();
                } else {
                    $("#errorUploadSpan").text(data.msg).css("display", "inline");
                }
            },
            error: function (data, status, e) {
                showInfoBox(e);
            }
        })
    }


    function pcBanSelectChange() {
        var pcBan = $("#pcBanSelect").val();
        if (pcBan == 2) {
            $("#pc_div").css("display", "inline");
        } else {
            $("#pc_div").css("display", "none");
        }
    }

    function mobileBanSelectChange() {
        var mobileBan = $("#mobileBanSelect").val();
        if (mobileBan == 2) {
            $("#mobile_div").css("display", "inline");
        } else {
            $("#mobile_div").css("display", "none");
        }
    }
    //非空验证
    function validatorForm() {
        var ret = 0;
        if ($("#preferentialNum").val() == '') {
            alert("秒杀优惠");
            ret++;
        }
        if ($("#joinQuantity").val() == '') {
            alert("活动商品数量");
            ret++;
        }
        if ($("#maxQuantity").val() == '') {
            alert("限购数量");
            ret++;
        }

        if (ret > 0) {
            return false;
        } else {
            return true;
        }
    }

    function toSet() {
        $("#sub_form").submit();
    }
    ;

    function preferentialNumChange() {
        var preferentialNum = $("#preferentialNum").val();
        var preferentialType = $("#preferentialType").val();
        var oldPrice = $("#oldPrice").val();
        if (preferentialNum == '') {
            preferentialNum = 1;
            $("#preferentialNum").val("1");
        }

        if (preferentialType == '2') {
            if (preferentialNum < 0.1 || preferentialNum >= 10) {
                $("#preferentialNum").val("1");
            }
            var price = parseFloat(preferentialNum) * parseFloat(oldPrice) / 10;
            $("#priceSpan").html(price);
        } else {
            $("#priceSpan").html(preferentialNum);
        }
    }

    function joinQuantityChange() {
        var joinQuantity = $("#joinQuantity").val();
        if (joinQuantity == '') {
            $("#joinQuantity").val("1");
        }
        var quantity = $("#quantity").val();
        if (parseInt(joinQuantity) > parseInt(quantity)) {
            $("#joinQuantity").val(quantity);
        }
    }

    function maxQuantityChange() {
        var maxQuantity = $("#maxQuantity").val();
        if (maxQuantity == '') {
            $("#maxQuantity").val("1");
        }
        var joinQuantity = $("#joinQuantity").val();
        if (joinQuantity == '') {
            joinQuantity = 1;
        }

        if (maxQuantity > joinQuantity) {
            $("#maxQuantity").val(joinQuantity);
        }
    }