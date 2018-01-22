/*
 * jQuery placeholder, fix for IE6,7,8,9
 * @author JENA
 * @since 20131115.1504
 * @website ishere.cn
 */
var JPlaceHolder = {
    //检测
    _check : function(){
        return 'placeholder' in document.createElement('input');
    },
    //初始化
    init : function(){
        if(!this._check()){
            this.fix();
        }
    },
    //修复
    fix : function(){
        jQuery(':input[placeholder]').each(function(index, element) {
            var self = $(this), txt = self.attr('placeholder');
            self.wrap($('<div></div>').css({position:'relative', zoom:'1', border:'none', background:'none', padding:'none', margin:'none'}));
            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
            var holder = $('<span></span>').text(txt).css({position:'absolute', left:pos.left, top:pos.top, height:h, lienHeight:h, paddingLeft:paddingleft, color:'#aaa'}).appendTo(self.parent());
            self.focusin(function(e) {
                holder.hide();
            }).focusout(function(e) {
                if(!self.val()){
                    holder.show();
                }
            });
            holder.click(function(e) {
                holder.hide();
                self.focus();
            });
        });
    }
};
//执行
jQuery(function(){
    JPlaceHolder.init();    
});

$(document).ready(function(){
	//控制菜单背景
	selectedHeaderLi("permissions_header");
});

//账号校验
function checkAccount(account,status){
	var flag = false;
	var data;
	if("add"==status){
		if(account=="" || account.length<6){
			addAccountError("账号不能为空且不能小于6位");
		}else{
			var reg=/^[0-9a-zA-Z\@\_\-\.]+$/;
			if(reg.test(account)){
				flag = true;
				data={"checkParam":account,"status":"account"};
			}else{
				addAccountError("账号格式错误");
			}
		}
		/* else if(checkEmail(account)||checkPhoneOnly(account)){
			flag = true;
			data={"checkParam":account,"status":"account"};
		}else{
			addAccountError("账号只能为邮箱或者手机号");
		} */
	}else if("update"==status){
		var userId = $("#userId").val();
		if(account==""){
			updateAccountError("账号不能为空");
		}else if(checkEmail(account)||checkPhoneOnly(account)){/* !checkEmail(account) */
			flag = true;
			data={"checkParam":account,"status":"account","userId":userId};
		}else{
			updateAccountError("账号只能为邮箱或者手机号");
		}
	}else{
		return false;
	}
	if(flag){
		$.ajax({
			url : jsBasePath+'/checkOnly.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    data:data,
			success : function(data) {
				if(data.success){
					flag=true;
				}else{
					if(status=="update"){
						updateAccountError("账号已存在");
						flag=false;
					}else if(status=="add"){
						addAccountError("账号已存在");
						flag=false;
					}
				}
			}, error : function() {
				flag=false;
		    }  
		});
		return flag;
	}
	
}
//邮箱校验
function checkEmail1(email,status){
	var flag = false;
	var data;
	if("add"==status){
		/* if(email==""){
			flag=false;
		}else  */
		if(email!=""&&!checkEmail(email)){
			addEmailError("邮箱格式错误");
		}else if(email!=""){
			flag = true;
			data={"checkParam":email,"status":"email"};
		}else{
			return true;
		}
	}else if("update"==status){
		var userId = $("#userId").val();
		/* if(email==""){
			updateEmailError("邮箱不能为空");
		}else  */
		if(email!=""&&!checkEmail(email)){
			updateEmailError("邮箱格式错误");
		}else if(email!=""){
			flag = true;
			data={"checkParam":email,"status":"email","userId":userId};
		}else{
			return true;
		}
	}else{
		return false;
	}
	if(flag){
		$.ajax({
			url : jsBasePath+'/checkOnly.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    data:data,
			success : function(data) {
				if(data.success){
					flag = true;
				}else{
					if(status=="update"){
						updateEmailError("邮箱已存在");
						flag = false;
					}else if(status=="add"){
						addEmailError("邮箱已存在");
						flag = false;
					}
				}
			}, error : function() {
				flag = false;
		    }  
		});
		return flag;
	}
}
//手机校验
function checkPhone(phone,status){
	var flag = false;
	var data;
	if("add"==status){
		/* if(phone==""){
			flag=false;
		}else */
		if(phone!=""&&!checkPhoneOnly(phone)){
			addPhoneError("手机号错误");
		}else if(phone!=""){
			flag = true;
			data={"checkParam":phone,"status":"phone"};
		}else{
			return true;
		}
	}else if("update"==status){
		var userId = $("#userId").val();
		/* if(phone==""){
			updatePhoneError("手机号不能为空");
		}else  */
		if(phone!=""&&!checkPhoneOnly(phone)){
			updatePhoneError("手机号错误");
		}else if(phone!=""){
			flag = true;
			data={"checkParam":phone,"status":"phone","userId":userId};
		}else{
			return true;
		}
	}else{
		return false;
	}
	if(flag){
		$.ajax({
			url : jsBasePath+'/checkOnly.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    data:data,
			success : function(data) {
				if(data.success){
					flag = true;
				}else{
					if(status=="update"){
						updatePhoneError("手机号已存在");
						flag = false;
					}else if(status=="add"){
						addPhoneError("手机号已存在");
						flag = false;
					}	
				}
			}, error : function() {
				flag = false;
		    }  
		});
		return flag;
	}
}

//昵称校验
/* function checkNickName(nickName,status){
	if("add"==status){
		if(nickName==""){
			addNickNameError("昵称不能为空");
		}else if(nickName.length>11){
			addNickNameError("昵称长度错误");
		}else{
			return true;
		}
	}else if("update"==status){
		if(nickName==""){
			updateNickNameError("昵称不能为空");
		}else if(nickName.length>11){
			updateNickNameError("昵称长度错误");
		}else{
			return true;
		}
	}else{
		return false;
	}
} */
//姓名校验
function checkName(name,status){
	if("add"==status){
		if(name==""){
			addNameError("姓名不能为空");
		}else
		if(name!=""&&name.length>11){
			addNameError("姓名长度错误");
		}else{
			return true;
		}
	}else if("update"==status){
		if(name==""){
			updateNameError("姓名不能为空");
		}else
		if(name!=""&&name.length>11){
			updateNameError("姓名长度错误");
		}else{
			return true;
		}
	}else{
		return false;
	}
}

//添加用户/////////////////////////////////////////////////////////////////////////
function addAccountError(text){
	$("#add_account_error").text(text);
	$("#add_account_error").fadeIn();
	setTimeout("gotomain()",2000);
	return false;
}
function addEmailError(text){
	$("#add_email_error").text(text);
	$("#add_email_error").fadeIn();
	setTimeout("gotomain()",2000);
	return false;
}
function addPhoneError(text){
	$("#add_phone_error").text(text);
	$("#add_phone_error").fadeIn();
	setTimeout("gotomain()",2000);
	return false;
}
/* function addNickNameError(text){
	$("#add_nickName_error").text(text);
	$("#add_nickName_error").fadeIn();
	setTimeout("gotomain()",2000);
	return false;
} */
function addNameError(text){
	$("#add_name_error").text(text);
	$("#add_name_error").fadeIn();
	setTimeout("gotomain()",2000);
	return false;
}

function addUserSub(){
	//验证邮箱、账号、手机的唯一性
	var account = $("#add_account").val();
	var roleId = $("#add_roleId").val();
	var email = $("#add_email").val();
	/* var phone = $("#add_phone").val(); */
	var name = $("#add_name").val();
	if(roleId==null){
		alert("请您先添加角色！");
		return ;
	}else if(checkAccount(account,"add")&&checkEmail1(email,"add")&&checkName(name,"add")){/* checkPhone(phone,"add")&& */
		$.ajax({
			url :jsBasePath+ '/editor/add.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    data:{"userName":account,"id":roleId,"email":email,"realName":name,},//"phone":phone,
			success : function(data) {
				if(data.success){
					alert("注册成功");
					$('.popup_bg').hide();
					$('#add_pop').hide();
					location.reload()
				}else{
					alert("用户已注册");
					$('.popup_bg').hide();
					$('#add_pop').hide();
				}
			}, error : function() {
		    }  
		});
	}
}


////////////////////////////////////////////////////////////////////////////////

//修改用户/////////////////////////////////////////////////////////////////////////
function updateAccountError(text){
	$("#update_account_error").text(text);
	$("#update_account_error").fadeIn();
	setTimeout("gotomain()",2000);
	return false;
}
function updateEmailError(text){
	$("#update_email_error").text(text);
	$("#update_email_error").fadeIn();
	setTimeout("gotomain()",2000);
	return false;
}
function updatePhoneError(text){
	$("#update_phone_error").text(text);
	$("#update_phone_error").fadeIn();
	setTimeout("gotomain()",2000);
	return false;
}
/* function updateNickNameError(text){
	$("#update_nickName_error").text(text);
	$("#update_nickName_error").fadeIn();
	setTimeout("gotomain()",2000);
	return false;
} */
function updateNameError(text){
	$("#update_name_error").text(text);
	$("#update_name_error").fadeIn();
	setTimeout("gotomain()",2000);
	return false;
}


//修改用户
function updateUserSub(){
	var flag = false;
	/**
	*保证账号、邮箱、手机号的唯一性。
	*/
	var userId = $("#userId").val();
	/* var account = $("#update_account").val(); */
	var email = $("#update_email").val();
	/* var phone = $("#update_phone").val(); */
	var name = $("#update_name").val();
	var roleId = $("#update_roleId").val();
	//
	/* var oldPhone = $("#hidden_update_phone").val();
	var oldAccount = $("#hidden_update_account").val();
	var oldEmail = $("#hidden_update_email").val(); */
	
	if(userId==null){
		return ;
	}else if(checkEmail1(email,"update")&&checkName(name,"update")){/* checkAccount(account,"update")&&checkPhone(phone,"update")&& */
		$.ajax({
			url : jsBasePath+'/editor/update.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    data:{"id":roleId,"email":email,"realName":name,"userId":userId},//"userName":account,"phone":phone,
			success : function(data) {
				if(data.success){
						alert("修改成功");
						$('.popup_bg').hide();
						$('#alter_pop').hide();
						location.reload()
				}else{
					alert("修改失败");
					$('.popup_bg').hide();
					$('#alter_pop').hide();
					location.reload()
				}
			}, error : function() {
		    }  
		});
	}
}


//修改用户     弹窗
function updateUserBox(userId,roleId){
	if(userId==null||roleId==null)
		return ;
	
	$.ajax({
		url : jsBasePath+'/getByRoleId.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    data:{"id":roleId,"userId":userId},
		success : function(data) {
			if(data.success){
				$("#update_roleId").empty();
				if(data.data.user.name==null){
					/* $("#update_roleId").val(null);
					$("#update_roleName").val(null); */
					var html="";
					for(var i =0;i< data.data.roles.length;i++){
						html+="<option value="+data.data.user.roles[i].id+">"+data.data.user.roles[i].name+"</option>";
					}
					$("#update_roleId").append(html);
				}else{
					var html="";
					for(var j =0;j< data.data.roles.length;j++){
						if(data.data.user.id==data.data.roles[j].id){
							html+="<option selected=\"selected\"  value="+data.data.roles[j].id+">"+data.data.roles[j].name+"</option>";
						}else{
							html+="<option value="+data.data.roles[j].id+">"+data.data.roles[j].name+"</option>";
						}
					}
					$("#update_roleId").append(html);
					/* <option value="${r.id}">${r.name}</option> */
					//$("#update_roleId").val(data.data.user.id);//角色id
					//$("#update_roleName").val(data.data.user.name);//角色
				}
				/* if(data.data.userName==null){
					$("#update_account").val(null);
					$("#userId").val(null);
				}else{
					$("#userId").val(data.data.userId);
					$("#update_account").val(data.data.userName);
					$("#hidden_update_account").val(data.data.userName);
				} */
				if(data.data.user.userId!=null){
					$("#userId").val(data.data.user.userId);
				}
				
				if(data.data.user.email==null){
					$("#update_email").val(null);
				}else{
					$("#update_email").val(data.data.user.email);
					$("#hidden_update_email").val(data.data.user.email);
				}
				/* if(data.data.phone==null){
					$("#update_phone").val(null);
				}else{
					$("#update_phone").val(data.data.phone);
					$("#hidden_update_phone").val(data.data.phone);
				} */
				/* if(data.data.nickName==null){
					$("#update_nickName").val(null);
				}else{
					$("#update_nickName").val(data.data.nickName);
				} */
				//用户真实姓名
				if(data.data.user.realName==null){
					$("#update_name").val(null);
				}else{
					$("#update_name").val(data.data.user.realName);
				}
				$('.popup_bg').show();
				$('#alter_pop').show();
			}
			
		}, error : function() {
	    }  
	});
}
////////////////////////////////////////////////////////////////////////////////
//重置密码/////////////////////////////////////////////////////////////////////////
/**
 * 重置密码弹窗
 */
function resetPassBox(id){
	if(id!=null){
		$("#resetPassUserId").val(id);
		$('.popup_bg').show();
		$('#reset_pop').show();
	}
}
/**
 * 重置密码弹窗取消
 */
 $('#reset-cansel-btn').click(function(){
	$("#resetPassUserId").val(null);
	$('.popup_bg').hide();
	$('#reset_pop').hide();
 });
/**
 * 重置密码弹窗确定
 */
function resetPassTrue(){
	//需要修改用户的id
	var userId = $("#resetPassUserId").val();

	$.ajax({
		url : jsBasePath+'/resetPass.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    data:{"id":userId},
		success : function(data) {
			alert(data.msg);
			$('.popup_bg').hide();
			$('#reset_pop').hide();
		}, error : function() {
	    }  
	});	
}
//关闭弹窗
$('#reset-close').click(function(){
	$('.popup_bg').hide();
	$('#reset_pop').hide();	
});
///////////////////////////////////////////////////////////////////////////


//删除用户/////////////////////////////////////////////////////////////////////////
/**
 * 删除用户弹窗
 */
function deleteUserBox(userId,roleId){
	if(userId==null||roleId==null)
		return ;
	
	$('.popup_bg').show();
	$('#delete_pop').show();
	//x按钮
	$('.add-close-icon').click(function(){
		$('.popup_bg').hide();
		$('#delete_pop').hide();	
	});
	//取消按钮
	$('.cansel-btn').click(function(){
		$('.popup_bg').hide();
		$('#delete_pop').hide();	
	})
	$('#deleteUserSub').click(function(){
		
		$.ajax({
			url : jsBasePath+'/deleteUser.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    data:{"userId":userId,"roleId":roleId},
			success : function(data) {
				if(data.success){
					alert("删除成功");
					location.reload()
				}else{
					alert(data.msg);
					location.reload()
				}
				$('.popup_bg').hide();
				$('#reset_pop').hide();
			}, error : function() {
		    }  
		});	
		$('.popup_bg').hide();
		$('#delete_pop').hide();	
	})
	
}
///////////////////////////////////////////////////////////////////////////
/**
 * 快速跳转
 */
function gotoPage(){
	var pagenum = $("#pagenum").val();
	formSubmit(pagenum);
}

/**
 * 表单提交
 */
function formSubmit(page){
	if(page!=undefined){
		$("#pageNumber").val(page);
	}else{
		$("#pageNumber").val(1);
	}
	$("#sub_form").submit();
}

//校验邮箱
function checkEmail(email){
	email=$.trim(email);
	if(email.length==0) return false;
	if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
	return pattern.test(email);
    //if(!pattern.test(email)){//只处验证不能为空并且只能为英文或者数字或者下划线组成的２－１５个字符
    //	return false;
    //}else{
    //	return true;
    //}
}

// 校验手机号
function checkPhoneOnly(phone){
	var reg = /^0?1[3|4|5|8][0-9]\d{8}$/;
	 if ($.trim(reg).length==11||reg.test(phone)) {
	    return true;
	 }else{
		return false;
	 }
}

function gotomain(){

	 ///////////////////////////////////////////
	 //修改用户/////////////////////////////////////////
	 $("#update_account_error").fadeOut();
	 $("#update_email_error").fadeOut();
	 $("#update_phone_error").fadeOut();
	 /* $("#update_nickName_error").fadeOut(); */
	 $("#update_name_error").fadeOut();
	 ///////////////////////////////////////////
	 //添加用户/////////////////////////////////////////
	 $("#add_account_error").fadeOut();
	 $("#add_email_error").fadeOut();
	 /* $("#add_phone_error").fadeOut(); */
	 /* $("#add_nickName_error").fadeOut(); */
	 $("#add_name_error").fadeOut();
	 ///////////////////////////////////////////
}


$(function(){
	//添加弹层
	$('.add_user_btn').click(function(){
		$("#add_account").val(null);
		$("#add_email").val(null);
		/* $("#add_phone").val(null); */
		/* $("#add_nickName").val(null); */
		$("#add_name").val(null);
		
		$('.popup_bg').show();
		$('#add_pop').show();
		$('#add-close').click(function(){
			$('.popup_bg').hide();
			$('#add_pop').hide();	
		});
		$('#add-cansel-btn').click(function(){
			$('.popup_bg').hide();
			$('#add_pop').hide();	
		});	
	})
	
	//修改弹层
	$('.alter_btn').click(function(){
		$('.popup_bg').show();
		$('#alter_pop').show();
		$('#provide-close').click(function(){
			$('.popup_bg').hide();
			$('#alter_pop').hide();	
		});
		$('#provide-cansel-btn').click(function(){
			$('.popup_bg').hide();
			$('#alter_pop').hide();	
		});	
	})
})
