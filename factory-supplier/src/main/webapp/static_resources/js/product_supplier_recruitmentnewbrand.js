		$(document).ready(function(){
			
			if(jsApprType=='2') {
		  		$(".recuitment_btn01 a").html("提交");
		  		$(".recuitment_btn02").hide();
		  		$(".recuitment_btn03 a").html("取消");
			}
		});
		
  		function deletetan(id){
  			$("#deleteid").val(id);
  			$(".popup_bg").show();
    		$("#shop_popup_delete").fadeIn();
  		}
  		
  		function delcategory1(){
  			var id = $("#deleteid").val();
  			window.location.href = jsBasePath+"/productBrand/delete.html?id="+id;
  			delcategory2();
  		}
  		
  		function delcategory2(){
    		$(".popup_bg").hide();
    		$("#shop_popup_delete").fadeOut();
    	}
  
        var select_zizi;
    	
    	$(document).ready(function(){    		
 			   		
    		$(".sc").click(function(){
    			select_zizi=$(this).siblings("input[name=aptitude_result]").attr("zizi")
    			$("#uploadFile").click();
    		})
    		
		});
    	
    	
    	function preView(select_zizi,imgsrc){
			 var  img=$("#filePicker"+select_zizi).siblings("div").find("img");
			 var name = $("#filePicker"+select_zizi).siblings("div").find("span[name=aptitudetypename]").text();
			 var hidden=$("#filePicker"+select_zizi).siblings("input[name=aptitude_result]");
			 if(img.length<1){
				 $("#filePicker"+select_zizi).parent("div").after('<div class="hupimg" style="float:left;height:170px;margin-right:20px;"><img src="'+imgsrc+'" ><br /><a class="sc" href="javascript:void(0);" onclick="del(this);" style="float:none;margin:10px 0 0 22px;color:#fff;">删除</a></div>');
				 //$("#filePicker"+select_zizi).after('<input type="hidden" name="aptitude_result" value="'+name+"_"+select_zizi+"_"+imgsrc+'>');
				 $("#filePicker"+select_zizi).after('<input type="hidden" zizi="'+select_zizi+'" name="aptitude_result" value="'+name+"_"+select_zizi+"_"+imgsrc+'">');
			 }else{
				 img.attr("src",imgsrc);
			 }
			
		     //hidden.attr("zizi");
    	}

    	function fileUpload() {
    	    var elementIds=["flag"]; //flag为id、name属性名
    	    
    	    $.ajaxFileUpload({
    	        url: jsBasePath+'/upload/pic.json?folder='+jsSsId,
    	        type: 'post',
    	        secureuri: false, //一般设置为false
    	        fileElementId: "uploadFile", // 上传文件的id、name属性名
    	        dataType: 'json', //返回值类型，一般设置为json、application/json
    	        elementIds: elementIds, //传递参数到服务器
    	        success: function(data, status){
    	        	if(data.success){
    	        		var imgPath = data.data[0].original;
    	        		if(imgPath.indexOf("http://") != 0) {
    	        			imgPath = "http://"+imgPath;
    	        		}
    	        		preView(select_zizi,imgPath);
    	        		//alert("图片上传成功");
    	        	}else{
    	        		showInfoBox(data.msg);
    	        	}
    	        },
    	        error: function(data, status, e){
					if(status == "413"){
						showInfoBox("图片过大,请重新选择!");
					}
    	            alert(e);
    	        }
    	    });
    	}
    	
    	
    	function papa(type){
    		$(".recuitment_btn01").addClass("btngray").removeAttr("onclick");
    		$(".recuitment_btn02").addClass("btngray").removeAttr("onclick");
    		$(".recuitment_btn03").addClass("btngray").removeAttr("onclick");
    		setTimeout("formSubmit("+type+")",500);
    	}
    	
    	
    	function formSubmit(flag){
    		if(flag==3){
    			if(jsApprType=='2') {
    				window.location.href = jsBasePath+"/supplier/cancelEdit.html";			
    			} else {
    				window.location.href = jsBasePath+"/supplier/torecruitmenttype.html";    	
    			}
    		}else{
    			if(flag==2){
    				var bs = jsBs;
    				if(bs == 0){
    					$(".recuitment_btn01").removeClass("btngray").attr("onclick","papa('1')");
    		    		$(".recuitment_btn02").removeClass("btngray").attr("onclick","papa('2')");
    		    		$(".recuitment_btn03").removeClass("btngray").attr("onclick","papa('3')");
    					showInfoBox("请先添加品牌！");
    					return false;
    				}
    			}
    			if(supplierType ==2){
    				if(checkBrand()){
    					$("#flag").val(flag);
        	    		$("#sub_form").submit();
        			}else{
        				$(".recuitment_btn01").removeClass("btngray").attr("onclick","papa('1')");
    		    		$(".recuitment_btn02").removeClass("btngray").attr("onclick","papa('2')");
    		    		$(".recuitment_btn03").removeClass("btngray").attr("onclick","papa('3')");
    					showInfoBox("请先编辑品牌！");
        			}
    			}else{
    				$("#flag").val(flag);
    	    		$("#sub_form").submit();
    			}
    			
    		}
    	}
    	
    	function del(obj) {
    		$($($(obj).parent()).parent()).find("input[type=hidden]").val('');
    		$($(obj).parent()).remove();
    	}
    	/**
    	*跳转添加品牌
    	*/
    	function createbrand(){
    		window.location.href = jsBasePath+"/supplier/tocreatebrand.html";
    	}
    	
    	function showimg(imgname){
    		$("#img3").attr("src",jsStaticResources+"images/"+imgname);
    		$("#showimg").show();
    	}
    	
    	function showimg2(imgname){
    		$("#img3").attr("src",imgname);
    		$("#showimg").show();
    	}
    	
    	function hideimg(){
    		$("#showimg").hide();
    	}
    	function checkBrand(){
    		var falg =true;
    		$("input[name='begintimeAuth']").each(function(){
    			  if($(this).val()==''){
    				  falg =false;
    			  }
    		})
    		$("input[name='endtimeAuth']").each(function(){
    			  if($(this).val()==''){
    				  falg =false;
    			  }
    		})
    		$("input[name='imageList']").each(function(){
    			  if($(this).val()==''){
    				  falg =false;
    			  }
    		})
    		return falg;
    	}