<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.wode.factory.user.util.Constant"%>
<%
	String path = request.getContextPath();
	if(request.getServerPort() != 80 && request.getServerPort() != 443) {
		path=":"+request.getServerPort()+path;
	}
	String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-安全设置</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/personal.js"></script>
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
        	<span class="onlt">安全设置</span>
        </div>
        <div class="clear"></div>
        <div class="Account_management">
        	<h2><em class="orange-light">${user.nickName}</em>，欢迎来到安全中心</h2>
        	<div>
				<img class="loading_img" id="loading" src="<%=basePath %>images/loading_small.gif" style="display: none;"/>
			</div>
            <table width="100%" class="AccountTab" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="Tabname"><span class="icon01"><img src="<%=basePath %>images/icon01.jpg"></span>登录密码</td>
                <td class="Tabcont">为减少账号被盗风险，建议您定期更改密码以保护账户安全</td>
                <td class="Tabcoper"><a href="<%=Constant.COMM_USER_URL %>personalSecurity1?uid=${user.id}&todo=resetPw&from=myFactory&domain=<%=Constant.SYSTEM_DOMAIN %>">更换</a></td>
              </tr>
              <c:if test="${empty user.email}">
	              <tr>
	                <td class="Tabname"><span class="icon02"><img src="<%=basePath %>images/icon02.jpg"></span>绑定邮箱</td>
	                <td class="Tabcont">绑定邮箱，可以用来登录或找回密码</td>
	                <td class="Tabcoper bluecolor"><a href="<%=Constant.COMM_USER_URL %>personalSecurity1?uid=${user.id}&todo=bindEmail&from=myFactory&domain=<%=Constant.SYSTEM_DOMAIN %>">立即绑定</a></td>
	              </tr>
              </c:if>
              <c:if test="${not empty user.email}">
	              <tr>
	                <td class="Tabname"><span class="icon02"><img src="<%=basePath %>images/icon02.jpg"></span>更换邮箱</td>
	                <td class="Tabcont">当前绑定邮箱：<font class="red">${user.email}</font></td>
	                <td class="Tabcoper"><a href="<%=Constant.COMM_USER_URL %>personalSecurity1?uid=${user.id}&todo=bindEmail&from=myFactory&domain=<%=Constant.SYSTEM_DOMAIN %>">更换</a></td>
	              </tr>
              </c:if>
              <c:if test="${empty user.phone}">
	              <tr>
	                <td class="Tabname"><span class="icon03"><img src="<%=basePath %>images/icon02.jpg"></span>绑定手机</td>
	                <td class="Tabcont">绑定手机，可以用来登录或找回密码</td>
	                <td class="Tabcoper bluecolor"><a href="<%=Constant.COMM_USER_URL %>personalSecurity1?uid=${user.id}&todo=bindPhone&from=myFactory&domain=<%=Constant.SYSTEM_DOMAIN %>">立即绑定</a></td>
	              </tr>
              </c:if>
              <c:if test="${not empty user.phone}">
	              <tr>
	                <td class="Tabname"><span class="icon03"><img src="<%=basePath %>images/icon03.jpg"></span>更换手机</td>
	                <td class="Tabcont">当前绑定手机：<font class="red">${phone}</font></td>
	                <td class="Tabcoper"><a id="changePhone" href="<%=Constant.COMM_USER_URL %>personalSecurity1?uid=${user.id}&todo=bindPhone&from=myFactory&domain=<%=Constant.SYSTEM_DOMAIN %>">更换</a></td>
	              </tr>
              </c:if>
            </table>
              <span id="sendResult" class="sendEmailResult" style="display: none;"></span>
        </div>
    </div>
<!--right contont end-->
	<div class="clear:after"></div>
</div>
<div class="clear"></div>
<!--help begin-->
<%@ include file="../common/footer.jsp" %>
<!--footer end-->
  <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>
