$(document).ready(function(){
	selectedHeaderLi("spgl_header");

	//图片url全选
	$(".inp_btn").click(function(){
		$(this).prev().select();
	});
	
});


function uploadImageResource(){
	var uploadImageUrl = $("#file").val();
	var imageUrlSuffix = uploadImageUrl.substring(uploadImageUrl.indexOf(".")+1,uploadImageUrl.length).toLowerCase();
	if(imageUrlSuffix=="jpg"||imageUrlSuffix=="jpeg"||imageUrlSuffix=="png"){
		 $.ajaxFileUpload({
		        url: jsBasePath+'/upload/pic.json',
		        type: 'post',
		        secureuri: false, //一般设置为false
		        fileElementId: "file", // 上传文件的id、name属性名
		        dataType: 'json', //返回值类型，一般设置为json、application/json    	       
		        success: function(data, status){
		        	if(data.success){
		        		$.ajax({
		        			url :jsBasePath+'/supplierImageResource/addImageResource.json',
		        			type : "POST",
		        			dataType: "json",  //返回json格式的数据  
		        		    async: true,
		        		    data:{"image":data.data[0].url},//introduce  介绍(描述)
		        			success : function(data) {
		        				if(data.success){
		        					location.reload();
		        				}else{
		        					
		        				}
		        			}
		        		})
		        	}else{
		        		/* data.msg */
		        	}
		        }
		 })
	}
}

/**
 * 快速跳转
 */
function gotoPage(){
	var pagenum = $("#pagenum").val();
	formSubmit(pagenum);
}

/**
 * 表单提交
 */
function formSubmit(page){
	if(page!=undefined){
		$("#pageNumber").val(page);
	}else{
		$("#pageNumber").val(1);
	}
	$("#sub_form").submit();
}

function addImageResourceBox(){
	$(".popup_bg").show();
	$(".uploadimg_box").show();	
}

function uploadingClose(){
	$(".popup_bg").hide();
	$(".uploadimg_box").each(function(){
		$(this).hide();
	});	
}
