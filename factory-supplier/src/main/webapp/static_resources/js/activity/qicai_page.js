

$(document).ready(function(){
	ajaxGetCategoryListByids();
	
});

/**
*ajax加载类目
*/
function ajaxGetCategoryListByids(){
	var categoryidtemp = $("#categoryidtemp").val();
	
	$.ajax({
		url : jsBasePath +'/productCategory/ajaxGetCategoryListByids.json',
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
