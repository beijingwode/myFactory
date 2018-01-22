var uid=GetUidCookie();//用户id.
function boundQRcode(){
	var targetUrl = $("#targetUrl").val();
	var supplierId = $("#supplierId").val();
	var type=$("#type option:selected").val();  //获取选中的项
	$.ajax({
		url :jsBasePath+'userShare/boundQRcode.user?uid='+userId+'&supplierId='+supplierId+'&type='+type+'&targetUrl='+targetUrl,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data){
			if (data.success) {
				var supplierName = $(".com_name").val();
				location.href=jsBasePath+"userShare/queryQrPage.user?uid="+userId+"&supplierId="+supplierId+"&comName="+supplierName;
			}
		},
		error : function(){
			alert("err");
		}
	})
}