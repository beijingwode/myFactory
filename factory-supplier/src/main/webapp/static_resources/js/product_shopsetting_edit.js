
    function save() {
        var phone = $.trim($("#shopPhone").val()), gh = /^[0-9][0-9\-]+\d$/;
        if (phone != "" && (!gh.test(phone) || phone.length > 20)) {
            showInfoBox("请输入正确的联系方式!", function () {
                $("#shopPhone").focus();
            });
            return;
        }
        if ($("#introduce").val().length > 300) {
            showInfoBox("介绍最长仅支持300个字符!");
            return;
        }
        $('form').submit();
    }
    function calculate() {
        var introNum = $("#introduce").val().length;
        if (introNum <= 300)
            $("#introNum").text(introNum);
    }
    

	function fileUpload() {
	    $.ajaxFileUpload({
	        url: jsBasePath+'/upload/pic.json?folder='+jsSsId+'',
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: "uploadFile", // 上传文件的id、name属性名
	        elementIds:1,
	        dataType: 'json', //返回值类型，一般设置为json、application/json
	        success: function(data, status){
	        	if(data.success){
	        		var imgsrc = data.data[0].original;
	        		if(imgsrc.indexOf("http://") != 0) {
	        			imgsrc = "http://"+imgsrc;
	        		}
	                $("#logo").attr("src", imgsrc);
	                $("#logo_url").val(imgsrc);
    				
	        	}else{
	        		showInfoBox(data.msg);
	        	}
	        },
	        error: function(data, status, e){ 
	            alert(e);
	        }
	    });
	}