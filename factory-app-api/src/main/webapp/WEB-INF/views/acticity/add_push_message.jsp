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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/push_message.css" />
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<title>推送短信</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
</head>

<body style="background:#fff;">
<div class="main-cont" id="main-cont">   
    <div class="main-box">
    	<div class="banner"><img src="<%=static_resources %>images/push_message/push_message_banner.jpg" /></div>
    	<div class="con1">
        	<ul>
        		<li><span class="span1"></span><input type="text" id="name" value="" placeholder="请输入您的姓名" /></li>
        		<li><span class="span2"></span><input type="text" id="phone" value="" placeholder="请输入您的电话" /></li>
        	</ul>
        	<p id="error"></p>
        	<a href="javascript:add();">确定</a>
        </div>
        <div class="con2" style="display:none"><img src="<%=static_resources %>images/push_message/push_message_pic1.jpg" /></div>
    </div>    
</div>
</body>
<script type="text/javascript">
	function add(){
		var name = $("#name").val();
		var phone = $("#phone").val();
		if(name == null || name == ""){
			$("#error").html("请输入您的姓名");
			return;
		}
		if(phone.length == 0){
			$("#error").html("请输入您的电话");
			return;
		}
		
		if(phone.length!=11) 
        { 
		   $("#error").html('请输入有效的手机号码！'); 
           return; 
        }

		var reTel=/^1([3456789]\d|21)\d{8}$/i;
        if(!reTel.test(phone)) 
        { 
           $("#error").html('请输入有效的手机号码！'); 
           return; 
        } 
        
      //验证一切数据
		$.ajax({
			url : jsBasePath+'acticity/addUserMessage',
			type : "GET",
			data: {"name":name,"phone":phone},
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    cache:false,
			success : function(data) {
				if(data.success){
					$(".con1").hide();
					$(".con2").show();
				}
			},error : function() {}
		})
	}

</script>
</html>
