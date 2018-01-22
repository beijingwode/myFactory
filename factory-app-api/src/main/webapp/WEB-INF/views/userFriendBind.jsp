<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
if(request.getServerPort() != 80 && request.getServerPort() != 443) {
	path=":"+request.getServerPort()+path;
}
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" >
<meta content="telephone=no" name="format-detection" />
<title>绑定手机</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/binding_phone.css" />
<script type="text/javascript">
var jsBasePath = "<%=basePath %>";
var type = "${type}";
var userNick = "${userNick}";
var openId = sessionStorage.openId;
var empSupplierId = "${empSupplierId}";
</script>
</head>

<body>
<div class="main-cont" id="main-cont">
    <input type="hidden" id="shareId" value="${shareId}" />
    <input type="hidden" id="fuid" value="${fuid}" />
	<div class="top">   	
        <h1 style="width:100%;text-align:center;">绑定手机 成为亲友</h1>
    </div>
    <div class="main-box">
    	<div class="main_con">
            <div class="int_box" <c:if test="${type!=9 }">style="display:none"</c:if>>
                <input type="text" id="userName" value="${userName}" readonly="readonly"/>
            </div>
            <div class="int_box">
                <input type="text" id="phone" placeholder="请输入手机号" maxlength="11" />
            </div>
            <div class="int_box">
                <input type="text" id="code" placeholder="请输入短信验证码" maxlength="6" /><a class="yzm_btn" id="yzm_btn" onclick="sendCode(this)" />获取验证码</a>
            </div>
            <div class="int_box">
                <input type="text" id="memo" placeholder="我是（姓名）" maxlength="6" value="<c:if test="${type==9}">临时账号  ${userName}</c:if>" />
            </div>
        </div>
        
        <p><input type="checkbox" checked="checked" disabled="disabled" /> <em>我已认真阅读并同意我的网</em>&nbsp;<a href="<%=basePath%>/protocol.html">《服务协议》</a></p> 
        <div class="btn_box">立即绑定</div>
        <p>绑定手机和好友<span>${userNick}</span>一起享受更多员工福利吧！</p>    
        <div class="fd_box">
            <dl>
                <dt><img src="${userAvatar}" /></dt>
                <dd>${userNick}</dd>
                <dd>${shareMsg1}</dd>
              <c:if test="${empSupplierId != '' }">
                <dd><input type="checkbox" id="chooseWorkmate" style="-webkit-appearance:checkbox"/><label for="chooseWorkmate">是同事,一起领公司福利</label></dd>
              </c:if>
            </dl>
        </div>
    
    </div>
     
</div>
<div class="theme-popover">
     <div class="theme-poptit">          
     </div>
     <div class="theme-popbod">
        <a href="javascript:void(0);">确定</a>  
     </div>
</div>
<div class="theme-popover-mask"></div>

<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/userFriendBind.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript">
$(document).ready(function() {	
	$("#chooseWorkmate").on("click",this,function(){
		if($(this).prop("checked")==true){
	        //当前为选中状态
			$(".top h1").html("绑定手机 成为同事");
	    }else{
	        //当前为不选中状态
	    	$(".top h1").html("绑定手机 成为亲友");
	    }
	});
});
</script>
</body>
</html>
