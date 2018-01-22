
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
	$("[name=type]").val("");
	
	$("#sub_form").find("input[type!='hidden']").val("");
	$("#sub_form").find("select").find("option:first").attr("selected","selected");
}

/**
 * 标签状态切换
 */
function tagChange(obj){
	$(obj).parent().parent().find("li>a").removeClass("a1").end().end().end().addClass("a1");
	var type = $(obj).attr("typename");
	$("[name=type]").val(type);
	
	$("#startDate").val("");
	$("#endDate").val("");

	formSubmit(1);
}
