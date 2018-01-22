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
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/sign_in/sign_in.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.eraser.js"></script>
<title>签到成功</title>
</head>
<script type="text/javascript">
var jsBasePath = '<%=basePath%>';
var recordId = '${recordId}';
var ticket = '${ticket}';
</script>
<body style="background:#c22936">
<div class="main-cont" id="main-cont">   
    <div class="sign_in_success">
    	<div class="xh_box">
    		<img src="<%=static_resources %>images/sign_in/sign_in_success1.png" />
    		<span>${luckyNumber}</span>
    	</div>
    </div> 
    <div class="gj_box">
    	<img src="<%=static_resources %>images/sign_in/sign_in_success2.png" />
    	<div class="content">
    		<img src="<%=static_resources %>images/sign_in/mask_img.png" />
	        <div class="mask_img_bg" id="mask_img_bg">
	        	<ul>
	        		<li class="li1">${companyName}</li>
	        		<li class="li2">员工专享福利</li>
	        	</ul>
	        	<span>${ticket}<i>元</i></span>
	        </div>
	        <img class="redux" id="redux" src="<%=static_resources %>images/sign_in/layer.png"/>
	    </div>
    </div>   
</div>
<div class="mask"></div>
<div class="hint_show2">
	<ul>
		<li class="li1">恭喜您中奖!</li>
		<li class="li2">内购券<span>${ticket}元</span>已存入到您的账户</li>
		<li class="li3"><img src="<%=static_resources %>images/sign_in/sign_in_ewm.png"/></li>
		<li class="li4">抽奖还在继续，不要错过哦~</li>
		<li class="li5">长按识别二维码，及时获取抽奖信息</li>
		<li>凭获奖推送可领取幸运大奖</li>
	</ul>
	<em>X</em>
</div>
<script type="text/javascript">
    $(window).load(function () {
         $('#redux').eraser({
             size: 50,   //设置橡皮擦大小
             completeRatio: .5, //设置擦除面积比例
             completeFunction: showResetButton,
         });
         $(".hint_show2 em").click(function () {
             $(".hint_show2,.mask").fadeOut(300);
         });
    })
    
    function showResetButton(){
    	$(".hint_show2,.mask").fadeIn(300);
    	$.ajax({
    		url : jsBasePath+'acticity/updateTicketMsg',
    		type : "GET",
    		data: {"recordId":recordId,"ticket":ticket},
    		dataType: "json",  //返回json格式的数据  
    	    async: false,
    	    cache:false,
    		success : function(data) {
    		},error : function() {}
    	})
    }
</script>
</body>

</html>
