
var uid = GetUidCookie();
//JavaScript Document
$(document).ready(function() {	
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	$("#save").click(function(e){//点击保存
		ajaxSaveData();
		location.reload=jsBasePath+'address/page'
	})
	/*$(".top").click(function(e){//返回
		location.href=jsBasePath+'address/page'
	})*/
	$(".thickdiv").click(function(e){
		$(".thickdiv").hide();
		$(".thickbox").hide();
	});
});
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
			url:jsBasePath+'address/update.user?uid='+uid+'&cityId='+cityId,
			type : "GET",
			data: {"id":id,"userId":uid,"aid":aid,"provinceName":provinceName,"cityName":cityName,
				   "areaName":areaName,"address":address,"name":name,"phone":phone,
				   },  //返回json格式的数据  
			async: false,
			cache:false,
		    dataType:"json",
		    success : function(data) {
		    	if (data.success) {
		    		//window.location=history.go(-5);
		    		history.back();
				}else{
					showInfoBox(data.msg);
				}
		    }
		});
	}
}
function go2area(){
	var name=$("#name").val();
	var phone=$("#phone").val();
	var area=$("#area").val();
	var address=$("#address").val();
	var aid=$("#aid").val();
	$(".thickdiv").show();
	$(".thickbox").show();
	getArea(1,null);
	//window.location=jsBasePath+'selection_region.html?id='+id+'&name='+name+'&phone='+phone+'&address='+address;
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

	var myreg=/^1([3456789]\d|21)\d{8}$/i;
    if(!myreg.test(phone)) 
    { 
    	showInfoBox('请输入有效的手机号码！'); 
        return false; 
    } 
    return true;
}
