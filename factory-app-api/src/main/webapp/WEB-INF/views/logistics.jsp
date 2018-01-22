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
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" >
<meta content="telephone=no" name="format-detection" />
<title>物流信息</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/logistics.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>

${localHtml}
</head>

<body>
<div class="main-cont" id="main-cont">
	<div class="top">
    	<h1><a href="javascript:close();" class="aleft"></a>物流信息</h1>
    </div>
    <div class="main-box">
        <div class="main-con">
        	<div class="main_one">
            	<div class="logistics_logo"><img src="<%=static_resources %>${comIcon}" /></div>
                <ul>
                	<li class="li1">在路上</li>
                	<li class="li2"><span>物流公司：</span><em>${comName}</em></li>
                    <li class="li2"><span>运单编号：</span><em>${expressNo}</em></li>
                </ul>
            </div>
            <div class="main_two">
        
                <div class="t-2">
	                <div class="ls-tit1">物流追踪</div>
	                <div class="tit-con" style="display: none">
	                	<ul>
	                    </ul>
	                </div>
                </div>
            </div>
            
        </div>
    </div>    
</div>

<script type="text/javascript">
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main-box").css("top","0");
	}
	
	//物流情报显示
	if('${expressCom}' != '' && '${expressCom}'!='null') {
    	$.ajax({
            url: '<%=basePath %>express/search',
    		type : "GET",
    		dataType: "json",  //返回json格式的数据  
    		data: {"expressCom":"${expressCom}","express_no":"${expressNo}","user":"${searchId}"},
    		success : function(data) {
    			var json = data.data; 
	    		$(".li1").text(json.body.status);
    	    	var ary = eval(json.body.history)
    	    	if(ary.length > 0) {
	    	    	var lis="<li class=\"tit-con-li1\"><p>"+ary[0].des+"<br />"+ary[0].dealDate+"</p></li>";
	    	    	for(var i=1; i<ary.length; i++)  
    	    	  	{  
    	    	     	lis += "<li><p>"+ary[i].des+"<br />"+ary[i].dealDate+"</p></li>";
    	    	  	}
	    	    	$(".tit-con ul").html(lis);
	    	    	$(".tit-con").show();
    	    	}
    		}
    	});
	}
});
</script>
</body>
</html>
