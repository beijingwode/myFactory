var uid=GetUidCookie();
//JavaScript Document
$(document).ready(function() {
	init();
});
function init(){
	$.ajax({
		url : 'app/suborder/toSupplierExpress.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var result = data.data;
			var html='';
			var allCompInfoList = result.allCompInfoList;
			if (allCompInfoList!=undefined && allCompInfoList!='') {
				for (var i = 0; i < allCompInfoList.length; i++) {
					html += '<tr>';
					html += '<td class="d" align="center">'+allCompInfoList[i].name+'</td>';
					html += '<td class="d" align="center"><input type="checkbox"  name="id" value="'+allCompInfoList[i].id+'"/></td>';
					html += '</tr>';
				}
				$("tbody").html(html);
			}
			var boxObj = $("input:checkbox[name='id']");  //获取所有的复选框
		    var expresslist = result.supplierExpressids; //获取已选择的复选框的值为字符串类型
		    var express = expresslist.split(','); //去掉它们之间的分割符“，”   
		    for(i=0;i<boxObj.length;i++){
		       for(j=0;j<express.length;j++){            
		           if(boxObj[i].value == express[j])  //如果值与修改前的值相等
		           {
		               boxObj[i].checked= true;
		               break;
		           }
		       }
		    }  
			
		},
		error : function() {}
	});
}
//保存
function go2AddExpress(){
	var ids = "";
	var $list = $("[name=id]:checked"); //获取所有被选中的checkbox
	$list.each(function(i,val){ //each()方法规定为每个匹配元素规定运行的函数。
		ids = ids+$($list[i]).attr("value")+",";
	});
	if(ids==""){
		showInfoBox("至少选择一条!");
	}else{
		addExpress(ids);
	}
}
function addExpress(ids){
	if(uid == ""||ids=="") return;
	$.ajax({
		url:'app/suborder/ajaxUpdateSupplierExpress.user?uid='+uid,
		type : "POST",
		data :{"expressIds":ids},
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		showInfoBox(data.msg);
				setTimeout(history.back(), 1500);
			}else{
				showInfoBox(data.msg)
			}
	    },
	    error : function() {}
	})
}