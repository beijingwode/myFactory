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
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/ace/get_quan.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/acticity/prize_take_information.js"></script>
<title>奖品领取</title>
</head>
<script type="text/javascript">
var jsBasePath = '<%=basePath%>';
var prizeId = '${prizeId}'
</script>
<body>
<div class="main-cont" id="main-cont">   
    <div class="main-box">
    	<img src="<%=static_resources %>images/ace/get_quan5.png" />
    	<div class="con1 con2 con6">
        	<img src="<%=static_resources %>images/ace/get_quan6.png" />
        	<div class="quan_con">
				
			</div>  
        	<ul>
        		<li><span class="span3"></span><input type="text" value="" id="mobile" placeholder="电话" /></li>
        		<li><span class="span4"></span><input type="text" value="" id="email" placeholder="收到邮件的邮箱" /></li>
        		<li>（该信息仅用于验证您的身份）</li>
        	</ul>
        	<p id="error"></p>
        </div>
        <div class="con3">
        	<img src="<%=static_resources %>images/ace/pick_up_the_bag3.png" />  
        	<a href="javascript:takePrize();">立即领取</a> 
        	<p>奖品由“我的福利”提供</p>
        </div>   
    </div>
     
</div>
</body>
<script type="text/javascript">
function takePrize(){
	var mobile = $("#mobile").val();
	var email = $("#email").val();
	if(mobile.length==0) { 
		$("#error").html('请输入手机号码'); 
          return false; 
    }     
    if(mobile.length!=11) 
    { 
       $("#error").html('请输入有效的手机号码'); 
       return false; 
    } 
        
    var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
    if(!myreg.test(mobile)) 
    { 
       $("#error").html('请输入有效的手机号码'); 
       return false; 
    } 
	if(email == null && email == ""){
		$("#error").html('邮箱不能为空');
		return false;
	}
	var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if(!myreg.test(email))
     {
    	$("#error").html('请填写正确的邮箱');
        return false;
    }
	//验证一切数据
	$.ajax({
		url : jsBasePath+'acticity/takePrize',
		type : "GET",
		data: {"prizeId":prizeId,"mobile":mobile,"email":email},
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if(data.success){
				window.location=jsBasePath+'acticity/toTakeSuccessPage?prizeId='+prizeId;
			}
			if(data.data != null){
				window.location=jsBasePath+'acticity/toTakeSuccessPage?prizeId='+data.data;
			}
		},error : function() {}
	})
}
</script>
</html>
