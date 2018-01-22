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
<title>我的福利-修改生日</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/details.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>laydate/need/laydate.css"/>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>laydate/skins/default/laydate.css"/>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>mobiscroll/css/mobiscroll.custom-2.6.2.min.css"/>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/personal_sr.js"></script>
<script type="text/javascript" src="<%=static_resources %>laydate/laydate.js"></script>
<script type="text/javascript" src="<%=static_resources %>mobiscroll/js/mobiscroll.custom-2.6.2.min.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var avatar = '${avatar}';
	var nickName = '${nickName}';
	var gender = '${gender}';
	var birthday = '${birthday}';
</script>
</head>
<body>
<div class="main-cont" id="main-cont">
	
    <div class="main-box" style="position: absolute;top: 20px;">
        	&nbsp;&nbsp;&nbsp;&nbsp;生日：<input type="text" class="laydate-icon" id="sr" value="">
        	<!-- <script>
        	laydate({
        		elem: '#sr', 
        	    event: 'focus' //响应事件。如果没有传入event，则按照默认的click
        	});
        	</script> -->
        	<script>
	$(function() {
		$("#sr").mobiscroll().date();
		// $("#scroll2").mobiscroll().image_text();  
		var currYear = (new Date()).getFullYear();
		//初始化日期控件  
		var opt = {
			preset : 'date', //日期，可选：date\datetime\time\tree_list\image_text\select  
			theme : 'default', //皮肤样式，可选：default\android\android-ics light\android-ics\ios\jqm\sense-ui\wp light\wp  
			display : 'modal', //显示方式 ，可选：modal\inline\bubble\top\bottom  
			mode : 'scroller', //日期选择模式，可选：scroller\clickpick\mixed  
			lang : 'zh',
			dateFormat : 'yyyy-mm-dd', // 日期格式  
			setText : '确定', //确认按钮名称  
			cancelText : '取消',//取消按钮名籍我  
			dateOrder : 'yyyymmdd', //面板中日期排列格式  
			dateFormat:"yy-mm-dd",
			dayText : '日',
			monthText : '月',
			yearText : '年', //面板中年月日文字  
			showNow : true,
			nowText : "今天",
			startYear : currYear - 100, //开始年份    
			endYear : currYear + 100
		//结束年份    
		//endYear:2099 //结束年份  
		};
		$("#sr").mobiscroll(opt);

	});
</script>
        	 <div id="save" ><span>保存</span></div> 
    
    </div>
    
</div>
</body>
</html>
