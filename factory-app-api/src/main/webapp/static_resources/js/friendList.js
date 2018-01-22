
var uid=GetUidCookie();//用户id
//JavaScript Document
$(document).ready(function() {
	init();
	
});
function init(){
	$("#nickName").val(userName);
}

//邀请
function go2Apply(){
	var phoneNumber=$("#phoneNumber").val();
	var nickName=$("#nickName").val();
	var memo=$("#memo").val();
	if (!checkPhone(phoneNumber)) {
		return;
	}else if(nickName==''||$.trim(nickName)==''){
		showInfoBox("名称不能为空")
		return;
	}else if(memo==''||$.trim(memo)==''){
		showInfoBox("备注不能为空")
		return;
	}else{
		apply(memo,phoneNumber);
	}
	
}
function apply(memo,phoneNumber){
	$.ajax({
		url:jsBasePath+'friend/apply.user?uid='+uid+'&nickname='+memo+'&phoneNumber='+phoneNumber,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {//成功
				refresh();
			}else{
				showMsg(data.msg);
				setTimeout(refresh,1500);
			}
	    },
	    error : function() {}
	})
}
//跳转到购物车
$(".main-box p").click(function(e){
	window.location=jsBasePath+'cart/page';
});
/*//获取通讯录
function go2AddressBook(){
	
}*/
function refresh(){//刷新页面
	location.reload();
}

//验证手机
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
     
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
    if(!myreg.test(phone)) 
    { 
    	showInfoBox('请输入有效的手机号码！'); 
        return false; 
    } 
    return true;
}