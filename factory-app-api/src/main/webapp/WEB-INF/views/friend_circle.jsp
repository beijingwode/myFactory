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
<meta name = "format-detection" content = "telephone=no">
<title>我的亲友</title>

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/friend_circle.css" />
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var employeeType='${employeeType}';
	var userName='${nickName}';
</script>
</head> 

<body>
<div class="main-cont" id="main-cont">
	<div class="top_box">
        <div class="top"> 
            <ul>
            	<li class="li1"><a href="javascript:void(0);">亲友申请</a></li>
                <li class="li2 thisone"><a href="javascript:void(0);">亲友管理</a></li>
            </ul>
            <div class="add_btn"><a href="javascript:go2friendApply()"></a></div>
        </div>
    </div>
    
    <div class="main-box ">
    
    </div>
    
    <div class="main-box dis_none" >
        
    </div>
</div>

<div class="theme-popover add_remarks"  style="display: none">
     <div class="theme-tit">添加备注</div>
     <div class="theme-input"><input type="text" id="memo" placeholder="备注姓名" maxlength="8"/></div>
     <div class="theme-popbod" >
        
        <a href="javascript:closeMsg();">取消</a>  
        <a href="javascript:void(0);" style="border:none;">确定</a>
     </div>
     <div class="theme-popbod1" style="display: none" >
        <a href="javascript:void(0);">确定</a>
     </div>
</div>
<div class="theme-popover add_money"  style="display: none">
     <div class="theme-tit">请输入赠券金额</div>
     <div class="theme-input"><input type="text" id="num" placeholder="请输入赠券金额" onkeyup="value=value.replace(/[^\1-9\.]/g,'')" onpaste="value=value.replace(/[^\1-9\.]/g,'')" oncontextmenu = "value=value.replace(/[^\1-9\.]/g,'')"/></div>
     <div class="theme-popbod" >
        
        <a href="javascript:closeMsg();">取消</a>  
        <a href="javascript:void(0);" style="border:none;">确定</a>
     </div>
     <div class="theme-popbod1" style="display: none" >
        <a href="javascript:void(0);">确定</a>
     </div>
</div>
<div class="theme-popover-mask" style="display: none"></div>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
<script type="text/javascript" src="<%=static_resources %>/js/zepto.min.js"></script>

<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/friend_circle.js"></script>

</html>
