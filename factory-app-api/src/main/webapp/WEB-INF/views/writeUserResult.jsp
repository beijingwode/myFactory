<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	var data=eval('(${data})');
    if('${type}'=='forget') {
    	//找回密码
        parent.forgetResult(data);

    } else if('${type}'=='security') {
    	//通过邮箱注册
        parent.securityResult(data);
    	
    } else if('${type}'=='login') {
    	//通过邮箱注册
        parent.showLoginResult(data,'${showVcode}');
    	
    } else if('${type}'=='loginMini') {
    	//通过邮箱注册
        parent.loginResult(data,'${showVcode}');
        
    } else if('${type}'=='regist2') {
    	//通过邮箱注册
        parent.showRegisterResult2(data);
    } else {
    	//手机注册
        parent.showRegisterResult(data);
    }
    
    
</script>