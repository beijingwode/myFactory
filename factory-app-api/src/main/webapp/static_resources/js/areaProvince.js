
/*var uid = GetUidCookie();
//JavaScript Document
$(document).ready(function() {
	$(".top").click(function(e){//点击返回
		history.go(-1);
	});
	init();
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
});
function init(){
	getArea(1,null);
}*/
var provinceName,cityName,areaName
function getArea(str,code,name){
	var data;
	if (code!="") {
		if (str==1) {
			data=getArea90(1,null);
		}else if(str==2){
			data=getArea90(2,code);
		}else if(str==3){
			data=getArea90(3,code);
		}
		var html="";
		var arr = new Array();
		for (var i = 0; i < data.length; i=i+3) {
			arr[0] = data[i];
			if (i+1>=data.length) {
				arr[1]='';
			}else{
				arr[1] = data[i+1];
			}
			if (i+2>=data.length) {
				arr[2]='';
			}else{
				arr[2] = data[i+2];
			}
			if (str==1) {
				html +='<tr>';
				html +='<td onclick="go2City(\''+arr[0]["code"]+'\',\''+arr[0]["name"]+'\')">'+arr[0]["name"]+'</td>';
				if (arr[1]=='') {
					html +='<td>  </td>';
				}else{
				html +='<td onclick="go2City(\''+arr[1]["code"]+'\',\''+arr[1]["name"]+'\')">'+arr[1]["name"]+'</td>';
				}
				if (arr[2]=='') {
					html +='<td>  </td>';
				}else{
					html +='<td onclick="go2City(\''+arr[2]["code"]+'\',\''+arr[2]["name"]+'\')">'+arr[2]["name"]+'</td>';
				}
				html+='</tr>';
			}
			if (str==2) {
				$(".region").html(name)
				$("table").empty();//清空数据
				html +='<tr>';
				html +='<td onclick="go2Area(\''+arr[0]["code"]+'\',\''+arr[0]["name"]+'\')">'+arr[0]["name"]+'</td>';
				if (arr[1]=='') {
					html +='<td>  </td>';
				}else{
				html +='<td onclick="go2Area(\''+arr[1]["code"]+'\',\''+arr[1]["name"]+'\')">'+arr[1]["name"]+'</td>';
				}
				if (arr[2]=='') {
					html +='<td>  </td>';
				}else{
					html +='<td onclick="go2Area(\''+arr[2]["code"]+'\',\''+arr[2]["name"]+'\')">'+arr[2]["name"]+'</td>';
				}
				html+='</tr>';
			}
			if (str==3) {
				$(".region").html(name)
				$("table").empty();//清空数据
				html +='<tr>';
				html +='<td onclick="go2Address(\''+arr[0]["code"]+'\',\''+arr[0]["name"]+'\')">'+arr[0]["name"]+'</td>';
				if (arr[1]=='') {
					html +='<td>  </td>';
				}else{
				html +='<td onclick="go2Address(\''+arr[1]["code"]+'\',\''+arr[1]["name"]+'\')">'+arr[1]["name"]+'</td>';
				}
				if (arr[2]=='') {
					html +='<td>  </td>';
				}else{
					html +='<td onclick="go2Address(\''+arr[2]["code"]+'\',\''+arr[2]["name"]+'\')">'+arr[2]["name"]+'</td>';
				}
				html+='</tr>';
			}
		}
		$("table").html(html);
	}
}
function go2City(code,pName){
	provinceName=pName;
	getArea(2, code,pName);
}
function go2Area(code,cName){
	cityName=cName;
	getArea(3, code,cName);
}
function go2Address(code,aName){
	areaName= aName;
	$(".region").html('选择省份');
	$("#area").val(provinceName+' '+cityName+' '+areaName);
	$("#aid").val(code);
	$(".thickdiv").hide();
	$(".thickbox").hide();
}