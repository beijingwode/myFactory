function stopShow(id){
	showConfirm("停止使用","您确定要停止使用该问卷吗？停止后买家评价方式同一般商品。","doStop("+id+")");
}

function doStop(id){
	$.ajax({
		url : jsBasePath+'/questionnaire/stop.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:{"selId":id},
		success : function(data) {
			if(data.success){
				showInfoBox("处理成功");
				gotoPage();
			} else {
				showInfoBox(data.msg);
			}
		}, error : function() {    
	    }  
	});
}
function delShow(id){
	showConfirm("删除","您确定删除此问卷吗？删除后还可以通过状态查询。","doDel("+id+")");
}

function doDel(id){
	$.ajax({
		url : jsBasePath+'/questionnaire/delete.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:{"selId":id},
		success : function(data) {
			if(data.success){
				showInfoBox("处理成功");
				gotoPage();
			} else {
				showInfoBox(data.msg);
			}
		}, error : function() {    
	    }  
	});
}
