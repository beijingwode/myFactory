var uid=GetUidCookie();//用户id
$(function(){
	querySupplier();
});
var managerId = '';
var managerName = '';
function querySupplier(){
	var comName = $("#search").val();
	$.ajax({
		url :jsBasePath+'managerOrderRecord/managerSupplier.user?uid='+userId+'&comName='+comName,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data){
			var list = data.data.supplierList;
			managerId = data.data.managerId;
			managerName =data.data.managerName;
			var html = '';
			for (var i = 0; i < list.length; i++) {
				html+='<li><span><img src="" /></span><a href="javascript:queryShare('+list[i].id+');">'+list[i].comName+'</a></li>';
				html+='<input type="hidden" value="'+list[i].comName+'" id="'+list[i].id+'">';
			}
			$(".company_name").html(html);
			findBySupplierTemp(managerId,comName)
		},
		error : function(){
			alert("err");
		}
	});
}
function queryShare(supplierId){
	var supplierName = $("#"+supplierId+"").val();
	location.href=jsBasePath+"userShare/queryQrPage.user?uid="+userId+"&supplierId="+supplierId+"&comName="+supplierName;
}
function saveSupplier(){
	location.href=jsBasePath+"supplierTemp/queryAddPage.user?uid="+userId+"&managerId="+managerId+"&managerName="+managerName;
}
function findBySupplierTemp(managerId,comName){
	$.ajax({
		url :jsBasePath+'supplierTemp/findBySupplierTemp?managerId='+managerId+'&comName='+comName,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data){
			var list = data.data;
			var html = '';
			for (var i = 0; i < list.length; i++) {
				html+='<li><span><img src="'+jsBasePath+'static_resources/images/linshi_icon.png" /></span><a href="javascript:queryShare('+list[i].id+');">'+list[i].comName+'</a></li>';
				html+='<input type="hidden" value="'+list[i].comName+'" id="'+list[i].id+'">';
			}
			$(".company_name").prepend(html)
		},
		error : function(){
			alert("err");
		}
	});
}