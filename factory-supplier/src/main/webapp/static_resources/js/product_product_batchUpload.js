
$(document).ready(function(){
	selectedHeaderLi("spgl_header");
});

function cancelStatus(id){
	$.ajax({
		url :jsBasePath+'/supplierProductExcel/editorProductExcelStatus.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:{"id":id},
		success : function(data) {
			if(data.success){
				location.reload();
			}else{
				
			}
		}
	})
}

function cancelStatusBox(id){
	$(".popup_bg").show();
	$(".uploadimg_box").eq(1).find("a").eq(0).attr("onclick","cancelStatus("+id+");");
	$(".uploadimg_box").eq(1).show();	
}


function submitExcel(){
	var fileUploadUrl = $("#file").val();
	var excelUrlSuffix = fileUploadUrl.substring(fileUploadUrl.indexOf(".")+1,fileUploadUrl.length).toLowerCase();
	if(excelUrlSuffix=="zip"){
		var files = document.querySelector("#file").files;
		if(files[0].size<1024*1024*30){
		 $.ajaxFileUpload({
		        url: jsBasePath+'/supplierProductExcel/uploadExcel.json',
		        type: 'post',
		        secureuri: false, //一般设置为false
		        fileElementId: "file", // 上传文件的id、name属性名
		        dataType: 'json', //返回值类型，一般设置为json、application/json    	       
		        success: function(data, status){
		        	if(data.success){
		        		
		        		$.ajax({
		        			url :jsBasePath+'/supplierProductExcel/addProductExcel.json',
		        			type : "POST",
		        			dataType: "json",  //返回json格式的数据  
		        		    async: true,
		        		    data:{"excelUrl":data.data,"introduce":fileUploadUrl.substring(fileUploadUrl.lastIndexOf('\\')+1)},//introduce  介绍(描述)
		        			success : function(data) {
		        				if(data.success){
		        					location.reload();
		        				}else{
		        					zipErrorBox(data.msg);
		        				}
		        			}
		        		})
		        	}else{
		        		zipErrorBox(data.msg);
		        	}
		        }
		 })
		}else{
			zipErrorBox("上传zip文件过大,不能超过30Mb");
		}
	}else{
		/* 请上传zip文件夹 */
		zipErrorBox("请上传zip文件");
	}
	
}
//zip上传错误弹窗
function zipErrorBox(text,closeTime){
	$(".up_results_suggest").html(text);
	$(".up_results_suggest").fadeIn();
	setTimeout("closeInfoBox()",closeTime);
}
//关闭信息弹窗
function closeInfoBox(str){
	$(".up_results_suggest").fadeOut();
}

function down(param){
	$.ajax({
		url :jsBasePath+'/supplierProductExcel/checkFileExist.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:{"param":param},
		success : function(data) {
			if(data.success){
				location.href=jsBasePath+"/supplierProductExcel/downLoad.html?param="+param;
			}else{
				alert("文件不存在");
			}
		}
	})
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
	$(".uploadimg_box").eq(0).show();	
}
function uploadingClose(){
	$(".popup_bg").hide();
	$(".uploadimg_box").each(function(){
		$(this).hide();
	});	
}