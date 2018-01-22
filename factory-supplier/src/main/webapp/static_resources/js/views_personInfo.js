
	$(document).ready(function(){
		selectedHeaderLi("sy_header");

		getArea(1,"");
		$("#province").change(function(){
			getArea(2,$('#province option:selected').val());
		});
		$("#city").change(function(){
			getArea(3,$('#city option:selected').val());
		});

		$("#filePicker").click(function(){
			$("#uploadFile").click();
		});
	});
    function save() {
        $('form').submit();
    }
	/**
	 * 根据code获取地址信息
	 */
	function getArea(grade,areaCode) {
		var data = window.China;
		if(grade==2)
			$("#city>option").remove();
		if(grade==3)
			$("#district>option").remove();
		$.each(data, function (key, value) {
			var name = value.name;
	        var level = value.grade;
	        var code = value.code;
	        var parentId = value.father;
	        if(grade!=level){
	            return true;
	        }
	        if(areaCode!=null){
		        if(grade==2 && parentId!=areaCode){
		        	return true;
		        }
		        if(grade==3 && parentId!=areaCode){
		        	return true;
		        }
			}
	        switch (level) {
	            case 1 :
	            	if(key==$("#provinceNo").val())
			    		var option = "<option value=\""+key+"\" selected='selected'>"+name+"</option>\n";
			    	else
			    		var option = "<option value=\""+key+"\">"+name+"</option>\n";
	                $("#province").append(option);
					getArea(2,$('#province>option:selected').val());
	                break;
	            case 2 :
	            	if(key==$("#cityNo").val())
	            		var option = "<option value=\""+key+"\" selected='selected'>"+name+"</option>\n";
			    	else
			    		var option = "<option value=\""+key+"\">"+name+"</option>\n";
	            	$("#city").append(option);
					getArea(3,$('#city option:selected').val());
	                break;
	            case 3 :
	            	if(key==$("#districtNo").val())
	            		var option = "<option value=\""+key+"\" selected='selected'>"+name+"</option>\n";
			    	else
			    		var option = "<option value=\""+key+"\">"+name+"</option>\n";
	            	$("#district").append(option);
	                break;
	        }
	    });
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
