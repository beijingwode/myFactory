function stopShow(id){
	$("#id_sel").val(id);
	$("#shop_popup_delete").show();
	$(".popup_bg").show();
}

function doStop(){
	var id=$("#id_sel").val();

	$.ajax({
		url : jsBasePath+'/exchangeProduct/exchangStop.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:{"delId":id},
		success : function(data) {
			if(data.success){
				formSubmit(1);//表单提交刷新页面
			} else {
				cancelButton('shop_popup_delete');
				showInfoBox(data.msg);
			}
		}, error : function() {    
	    }  
	});
}
function offlineShow(id){
	$("#id_sel").val(id);
	$("#shop_popup_offline").show();
	$(".popup_bg").show();
}

function doOffline(){
	var id=$("#id_sel").val();

	$.ajax({
		url : jsBasePath+'/exchangeProduct/exchangOffline.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:{"selId":id},
		success : function(data) {
			if(data.success){
				cancelButton('shop_popup_offline');
				window.location = jsBasePath + "/exchangeProduct/exportExchangeHis?type=1&ticketId=" + id;
			} else {
				cancelButton('shop_popup_offline');
				showInfoBox(data.msg);
			}
		}, error : function() {    
	    }  
	});
}

function delayShow(id){
	$("#id_sel").val(id);
	$("#shop_popup_delay").show();
	$(".popup_bg").show();
}

function doDelay(){
	var id=$("#id_sel").val();
	var delay=$("input[name='rdDelay']:checked").val();

	$.ajax({
		url : jsBasePath+'/exchangeProduct/exchangDelay.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:{"selId":id,"delay":delay},
		success : function(data) {
			if(data.success){
				showInfoBox("处理成功");
//				gotoPage();
				formSubmit(1);//表单提交刷新页面
			} else {
				cancelButton('shop_popup_delay');
				showInfoBox(data.msg);
			}
		}, error : function() {    
	    }  
	});
}

function cancelButton(id){
	$("#"+id).hide();
	$(".popup_bg").hide();
}