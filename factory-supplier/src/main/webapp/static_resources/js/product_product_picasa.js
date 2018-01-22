$(document).ready(function(){
	selectedHeaderLi("spgl_header");
	
	
	$("#filePicker").click(function(){
		$("#uploadingJqSelecter").val("");
		var hasLoadCount=$(".uploadimg_list>ul>li>img[name='loadimage']").length;
		if(hasLoadCount>=5){
			$("#errorUploadSpan").text("已上传完5张图片").css("display","inline");
			return false;
		}
		$("#uploadFile").click();
	})
});

function submitSkuImg(){
	var $list = $(".uploadimg_list").find("ul>li>img[name='loadimage']");
	if($list.length<5){
		$("#errorUploadSpan").text("请上传五张图片").css("display","inline");
	}else{
		$.ajax({
			url : jsBasePath+'/supplierSkuImage/addSkuImage.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    data:{"image1":$list.eq(0).attr("src"),"image2":$list.eq(1).attr("src"),"image3":$list.eq(2).attr("src"),"image4":$list.eq(3).attr("src"),"image5":$list.eq(4).attr("src")},
			success : function(data) {
				if(data.success){
					location.reload();
				}else{
					$("#errorUploadSpan").text("上传失败").css("display","inline");
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

function uploadDivdisplay(){
	$(".popup_bg").show();
	$(".uploadimg_box").show();	
}
function uploadingClose(){
	$(".popup_bg").hide();
	$(".uploadimg_box").hide();	
}