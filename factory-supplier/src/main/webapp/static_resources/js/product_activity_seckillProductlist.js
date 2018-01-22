$(document).ready(function(){
	selectedHeaderLi("hdgl_header");
	ajaxGetCategoryListByids();
});

/**
 * 表单提交
 */
function productset(id){
	window.location.href = jsBasePath + "/promotion/productSet.html?id="+id+"&bmTime="+$("#bmTime").val()+"&promotionId="+$("#promotionId").val();
}


/**
 * 快速跳转
 */
function gotoPage(){
	var pagenum = $("#pagenum").val();
	formSubmit(pagenum);
}

function toNext(){
	$("#sub_form2").submit();
};

/**
*ajax加载类目
*/
function ajaxGetCategoryListByids(){
	var categoryidtemp = $("#categoryidtemp").val();
	var basePath = jsBasePath;
	$.ajax({
		url : basePath +'/productCategory/ajaxGetCategoryListByids.json',
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html = '<option value="">--请选择--</option>';
			if(data.result.errorCode==0){
				if(data.result.msgBody.length>0){
					for(var i=0;i<data.result.msgBody.length;i++){
						if(data.result.msgBody[i].id==categoryidtemp){
							html+='<option value="'+data.result.msgBody[i].id+'" selected>'+data.result.msgBody[i].name+'</option>';
						}else{
							html+='<option value="'+data.result.msgBody[i].id+'">'+data.result.msgBody[i].name+'</option>';
						}
					}
				}
			}
			$("#categoryid").html(html);
		}, error : function() {    
	     }  
	});
}

/**
 * 表单提交
 */
function formSubmit(page){
	if(page!=undefined){
		$("#pages").val(page);
	}else{
		$("#pages").val(1);
	}
	$("#sub_form").submit();
}