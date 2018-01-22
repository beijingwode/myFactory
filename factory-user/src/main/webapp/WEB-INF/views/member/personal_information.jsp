<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-个人信息</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  
<script type="text/javascript" src="<%=basePath %>resources/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/areaFnc.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal_information.js"></script>
<script language="javascript" type="text/javascript" src="${basePath}/resources/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<!--top begin-->
<%@ include file="../common/header_03.jsp"%>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
	<%@ include file="menu.jsp"%>
<!--right content-->
	<div class="Me_content">
    	<div class="on_title">
        	<span class="onlt">个人信息</span>
        </div>
        <div class="informationwrap">
        	<div class="head_img">
        		<img width="120" <c:if test="${not empty user.avatar}">src="${user.avatar}"</c:if> 
        			<c:if test="${empty user.avatar}">src="<%=basePath %>images/head.jpg"</c:if>
        			height="120" id="avatarShow" onclick="fileSelect();">
        		<form id="form_face" enctype="multipart/form-data" style="width:auto;">
  					<input type="file" id="avatar" name="avatar" onchange="ajaxFileUpload();" style="display:none;"/>
  					<input type="hidden" id="avatarValue" value="${user.avatar}" >
 				</form>
        	</div>
            <div class="info_right">
            	<div class="info_one">
                	<span class="name">昵称：</span>
                    <input class="common_input p108" type="text" id="nickName" name="nickName" value="${user.nickName}" maxlength="24">
                </div>
                <div class="info_one">
                	<span class="name">性别：</span>
                    <input class="radio" type="radio" id="man" value="m" <c:if test="${user.gender=='m'}">checked="checked"</c:if>>男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input class="radio" type="radio" id="woman" value="f" <c:if test="${user.gender=='f'}">checked="checked"</c:if>>女
                	<input type="text" id="gender" name="gender" value="${user.gender}" style="display: none;"/>
                </div>
                <div class="info_one">
                	<span class="name">地区：</span>
                    <div class="Info_Select">
                    	<input type="text" id="provinceNo" value="${user.province}" style="display: none;"/>
                    	<select id="province" name="province">
                    		<!-- <option value=''>省</option> -->                               	
                        </select>
                    </div>
                    <div class="Info_Select">
                    	<input type="text" id="cityNo" value="${user.city}" style="display: none;"/>
                        <select id="city" name="city">
                        	<!-- <option value=''>市</option> -->
                        </select>
                    </div>
                    <div class="Info_Select">
                    	<input type="text" id="districtNo" value="${user.district}" style="display: none;"/>
                        <select id="district" name="district">
                        	<!-- <option value=''>区/县</option> -->
                        </select>
                    </div>
                </div>
            	<div class="info_one">
                	<span class="name">生日：</span>
                    <input class="common_input p108" type="text" id="birthDay" name="birthDay" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${user.birthday}"/>" readOnly="readOnly" onClick="WdatePicker()">
                </div>
                <input class="info_submitbtn" type="submit" name="" value="提交">
            </div>
        </div>
    </div>
<!--right contont end-->
<div class="clear:after"></div>
</div>
<div class="clear"></div>
<!--提示弹出框 begin-->
<%@ include file="../common/box.jsp" %>
<!--提示弹出框 end-->
<%@ include file="../common/footer.jsp" %>
<script>
$(function(){
	$('.head_img').hover(function(){
		$(this).find("img").attr("src","<%=basePath %>images/addhead.jpg");
	},function(){
		if($("#avatarValue").val()==null||$("#avatarValue").val()=="")
			$(this).find("img").attr("src","<%=basePath %>images/head.jpg");
		else
			$(this).find("img").attr("src",$("#avatarValue").val());
	})	
})
</script>
  <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>
