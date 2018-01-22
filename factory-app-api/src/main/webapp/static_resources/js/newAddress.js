
var uid = GetUidCookie();
//JavaScript Document
$(document).ready(function() {	
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	init();
	$("#save").click(function(e){//点击保存
		ajaxSaveData();
	});
	//关闭选则地区
	$(".thickclose").click(function(e){
		$(".thickdiv").hide();
		$(".thickbox").hide();
	});
	$(".thickdiv").click(function(e){
		$(".thickdiv").hide();
		$(".thickbox").hide();
	});
});
function init(){
	$.ajax({
		url:jsBasePath+'address/addAddress.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
				var result = data.data;
				if (result!=undefined && result!='') {
					if (result.name!=undefined && result.name!='') {
						$("#name").val(result.name)
					}
					$("#phone").val(result.phone);
					$("#address").val(result.address);
					$("#area").val(result.provinceName+' '+result.cityName+' '+result.areaName);
					$("#aid").val(result.aid);
				}
			}
	    }
	});
}
function go2area(){//打开选择地区div
	var name=$("#name").val();
	var phone=$("#phone").val();
	var area=$("#area").val();
	var address=$("#address").val();
	var aid=$("#aid").val();
	//window.location=jsBasePath+'selection_region.html?id='+id+'&name='+name+'&phone='+phone+'&address='+address+'&orderType='+orderType+'&partNumbers='+partNumbers+'&productId='+productId+'&specificationId='+specificationsId+'&quantity='+quantity;
	$(".thickdiv").show();
	$(".thickbox").show();
	getArea(1,null);
}
function ajaxSaveData(){
	var name=$("#name").val();
	var phone=$("#phone").val();
	var area=$("#area").val();
	var address=$("#address").val();
	var aid=$("#aid").val();
	if(uid=="") return;
	if (name==''||$.trim(name)==''){
		showInfoBox("收件人姓名不能为空!");
		return;
	}else if(address==''||$.trim(address)==''){
		showInfoBox("详细地址不能为空!");
		return;
	}else if(!checkPhone(phone)){
		return;
	}else if(area==''||$.trim(area)==null){
		showInfoBox("请选择所在地区!");
		return;
	}else{
		cityId =aid.substring(0,aid.length-2)+'00';
		var split=area.split(" ");
		var provinceName=split[0];
		var cityName=split[1];
		var areaName=split[2];
		$.ajax({
			url:jsBasePath+'address/add.user?uid='+uid+'&cityId='+cityId,
			type : "GET",
			data: {"userId":uid,"aid":aid,"provinceName":provinceName,"cityName":cityName,
				   "areaName":areaName,"address":address,"name":name,"phone":phone,
				   }, 
			dataType: "json",  //返回json格式的数据  
			async: false,
		    cache:false,
		    success : function(data) {
		    	if (data.success) {
		    		var shippingAddressId = data.data.id;
		    		if (orderType=='cart') {
		    			sessionStorage.setItem("checkAddress", JSON.stringify(data.data));
		    			history.back();
		    		}else{
						history.back();
					}
				}else{
					showInfoBox(data.msg);
				}
		    }
		});
	}
}
function checkPhone(phone) 
{ 
    if(phone.length==0||$.trim(phone)=='') 
    { 
       showInfoBox('请输入手机号码！'); 
       return false; 
    }     
    if(phone.length!=11) 
    { 
    	showInfoBox('请输入有效的手机号码！'); 
        return false; 
    } 

	var myreg=/^1([358]\d|7[23]|77|76|47)\d{8}$/i;
    if(!myreg.test(phone)) 
    { 
    	showInfoBox('请输入有效的手机号码！'); 
        return false; 
    } 
    return true;
}