
var uid = GetUidCookie();
//JavaScript Document
$(function() {
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
	
	ajaxAddressData();
	$(".top").click(function(e){//点击返回
		history.go(-1);
	});
});
function ajaxAddressData(){
	if(uid=="") return;
	$.ajax({
		url:jsBasePath+'address/all.user?uid='+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	var result = data.data;
	    	if (result.length>0) {
				var html='';
				for (var i = 0; i < result.length; i++) {
					if (result[i].areaName==undefined ||result[i].areaName==null ) {
						result[i].areaName='';
					}
					html +='<div class="address_con">';
					html +='<div onclick="go2OrderConfirm(this)" data-shipAddress='+JSON.stringify(result[i])+'>';
					html +='<div class="name_tel">';
					html +='<span style="float:left;">'+result[i].name+'</span>';
					html +='<span style="float:right;">'+result[i].phone+'</span></div>';
					html +='<p>'+result[i].provinceName+' '+result[i].cityName+' '+result[i].areaName+' '+result[i].address+'</p>';
					html +='</div>';
					html +='<div class="ads_bottom">';
					html +='<label onclick="selAddress(this,\''+result[i].aid+'\',\''+result[i].areaName+'\');">';
					if (result[i].send==1) {
						html+='<input type="checkbox" value="'+result[i].id+'" checked="checked"/>设置默认地址</label>';
					}else{
						html+='<input type="checkbox" value="'+result[i].id+'"/>设置默认地址</label>';
					}
					html +=' <div class="bj_sc">';
					html +='<a href="javascript:go2Update(\''+result[i].id+'\',\''+result[i].name+'\',\''+result[i].phone+'\',\''+result[i].provinceName+'\',\''+result[i].cityName+'\',\''+result[i].areaName+'\',\''+result[i].address+'\',\''+result[i].aid+'\');">编辑</a>|<a href="javascript:go2Del(\''+result[i].id+'\');">删除</a>';
					html +='</div>';
					html +='</div>';
					html +='</div>';
				}
				$(".main-box").append(html);
			}
	    }
	});
}
function go2OrderConfirm(that){
	var shippingAddress = JSON.parse($(that).attr("data-shipAddress"));
	var shippingAddressId = shippingAddress.id;
	sessionStorage.setItem("checkAddress", $(that).attr("data-shipAddress"));
	history.back();	
}
function selAddress(result,aid,areaName){
	if (areaName==null || areaName=='') {
		$(result).children("input[type='checkbox']").removeAttr("checked");
		showInfoBox("县/区域地址不全，请先编辑补充");
		return;
	}
	$("input[type='checkbox']").removeAttr("checked");
	$(result).children("input[type='checkbox']").attr("checked","checked");
	var newId=$(result).children("input[type='checkbox']").val();//修改地址的id
	var cityId='';
	cityId =aid.substring(0,aid.length-2)+'00';
	$.ajax({
		url:jsBasePath+'address/update.user?uid='+uid+'&cityId='+cityId,
		type : "GET",
		data:{"id":newId,send:1,aid:aid},
		async: true,
		dataType:"json",
		success : function(data) {
		    	if (data.success) {
					refresh()//刷新页面
				}else{
					showInfoBox(data.msg);
				}
		}
	});
}

function go2Add(){//添加收货地址
	window.location=jsBasePath+'address/newAddress.user?uid='+uid+'&pageId=0';
}
function go2Update(id,name,phone,provinceName,cityName,areaName,address,aid){//编辑地址页面
	window.location=jsBasePath+'address/newAddress.user?uid='+uid+'&pageId=1'+'&id='+id+'&name='+name+'&phone='+phone+'&provinceName='+provinceName+'&cityName='+cityName+'&areaName='+areaName+'&address='+address+'&aid='+aid;
}
function go2Del(id){
	$.ajax({
		url:jsBasePath+'address/delete.user?uid='+uid+'&id='+id,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    success : function(data) {
	    	if (data.success) {
				refresh();
			}else{
				showInfoBox(data.msg);
			}
	    }
	});
}
function refresh(){//刷新页面
	location.reload();
}