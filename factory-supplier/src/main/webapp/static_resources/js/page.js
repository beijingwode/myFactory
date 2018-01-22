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

/**
 * 重置
 */
function formReset(){
	//$("#sub_form").reset();
	//document.getElementById("sub_form").reset();
	$("#sub_form").find("input[type!='hidden']").val("");
	$("#sub_form").find("select").find("option:first").attr("selected","selected");
}