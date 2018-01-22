
	var kbn;
    $(document).ready(function () {
		$("#filePicker3").click(function(){
			if($("#img3").find("li").length>=3) {
				showInfoBox("不能超过3张图");
				return;
			}
			kbn=3;
			
			$("#uploadFile").click();
		});
		$("#filePicker1").click(function(){
			kbn=1;
			$("#uploadFile").click();
		});
		$("#filePicker2").click(function(){
			if($("#img2").find("li").length>=3) {
				showInfoBox("不能超过3张图");
				return;
			}
			kbn=2;
			$("#uploadFile").click();
		});
		
        selectedHeaderLi("wddp_header");
        var ue = UE.getEditor('container', 1000);
    });
    
    function del(obj) {
    	$($(obj).parent()).remove();
    }
	function fileUpload() {
	    $.ajaxFileUpload({
	        url: jsBasePath+'/upload/pic.json?folder='+jsSsId,
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

	        		if(kbn==3) {
		                $("#img" +kbn ).append('<li><p><img src="'+imgsrc+'" alt="图片"/></p><div onclick="del(this)"><a href="javascript:void(0);" >删除</a></div><input type="hidden" name="introImage" value="'+imgsrc+'"/></li>');
	    			} else if (kbn==2) {
	    			    $("#img" +kbn ).append('<li><p><img src="'+imgsrc+'" alt="图片"/></p><div onclick="del(this)"><a href="javascript:void(0);" >删除</a></div><input type="hidden" name="banner" value="'+imgsrc+'"/></li>');
		    		} else {
		                $("#img" +kbn ).attr("src", imgsrc);
		                $("#himg" +kbn).val(imgsrc);
	        		}
	        	}else{
	        		showInfoBox(data.msg);
	        	}
	        },
	        error: function(data, status, e){ 
	            alert(e);
	        }
	    });
	}
