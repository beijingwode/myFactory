function delqr(){
	showConfirm("您确定要重新生成二维码吗，重新生成后原二维码将作废","delQrCode()","setSubmitA()");
}
function setSubmitA(){
	return;
}
function delQrCode(){
	var shareId = $("#shareId").val();
	var supplierId = $("#supplierId").val();
	$.ajax({
		url :jsBasePath+'userShare/delCode.user?uid='+userId+'&shareId='+shareId+'&supplierId='+supplierId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data){
			if (data.success) {
				var supplierName = $("#userNick").val();
				location.href=jsBasePath+"userShare/queryQrPage.user?uid="+userId+"&supplierId="+supplierId+"&comName="+supplierName;
			}
		},
		error : function(){
			alert("err");
		}
	})
}
function downLoadQrEmp500Ticket(){
	var text = $("#linkUrl").val(); 
	var supplierName = $("#userNick").val();
	var limitEnd = $("#limitEnd").val();
	var exchange = $("#picr").val();
	location.href=jsBasePath+'userShare/downLoadQrEmp500Ticket?text='+text+'&companyName='+supplierName+'&limitEnd='+limitEnd+'&exchange='+exchange;
}