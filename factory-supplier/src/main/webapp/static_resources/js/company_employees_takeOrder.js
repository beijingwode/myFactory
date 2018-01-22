	$(function(){
		//加载页面，控制左边的菜单
		$("#emp_manage").parent().parent().prev().addClass("active");
		$("#emp_manage").parent().parent().attr("style","display: block;");
		$("#emp_manage").parent().addClass("active");
		$("#emp_manage").addClass("active");
	});

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
		if(page!=undefined && page != ''){
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
	
	/**
	 * 导出excel
	 * @returns
	 */
	function exportExcel(){
		$("#pageNumber").val(1);
	    
	    var action = $("#sub_form").attr("action");
	    $("#sub_form").attr("action",jsBasePath + "/company/emp/exportExcel.html");
	    $("#sub_form").attr("target","_blank");
	    $("#sub_form").submit();
	    $("#sub_form").attr("action",action);
	    $("#sub_form").attr("target","_self");
	}
