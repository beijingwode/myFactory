<%@page import="com.wode.factory.supplier.util.Constant"%>
<%@ page contentType="text/html;charset=UTF-8" %>
    <%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<title>安全设置</title>
<head>
<%@ include file="/commons/js.jsp" %>
</head>
<style>
.store_cont{width: 702px;
    margin: 66px 100px;
    position: relative;}
.store_cont h2 {
    font: 12px "Microsoft YaHei";
    color: #959595;
}
.orange-light {
    font: 12px "Microsoft YaHei";
    color: #caab80;
}
.AccountTab {
    width: 702px;
    margin-top: 20px;
    text-align: center;
    font: 12px "Microsoft YaHei";
    color: #6a6a6a;
    border-left: 1px solid #e5e5e5;
    border-bottom: 1px solid #e5e5e5;
}
tbody {
    display: table-row-group;
    vertical-align: middle;
    border-color: inherit;
}
.AccountTab .Tabname {
    width: 180px;
    font: 18px "Microsoft YaHei";
}
.AccountTab td {
    border: 1px solid #e5e5e5;
    border-left: none;
    border-bottom: none;
}
.AccountTab tr {
    height: 77px;
}
.AccountTab .Tabcont {
    text-align: left;
    padding-left: 40px;
}
.AccountTab .Tabcoper {
    width: 124px;
}
.Tabcoper a:visited {color:#ff6161}
</style>
<body>
<%@ include file="/commons/header.jsp" %>
<!--header end-->

<!--content begin-->
<div id="content">
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
			<li><a href="${basePath}/user/tosuppliermain.html">首页</a></li>
			<li><a href="${basePath}/user/info.html">个人信息</a></li>	
			<li class="curr"><a href="#">安全设置</a></li>
			<li style="background-color: #f5f5f5">&nbsp;&nbsp;</li>
			<li><a href="${basePath}/user/app_security.html">API安全</a></li>
        </ul>
    </div>
    <!--left end-->
    
    <!--right begin-->
    <div class="right">
    	<div class="position">
        	<span>您所在的位置</span><em>></em>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/user/security.html">安全设置</a>
        </div>
        <div class="sort_wrap">
    		<form action="${basePath}/user/updateInfo.html" method="post">
            <div class="sort_title">安全设置</div>
            <div class="store_cont">

        	<h2><em class="orange-light">${user.nickName}</em>，欢迎来到安全中心</h2>
        	<div>
				<img class="loading_img" id="loading" src="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>images/loading_small.gif" style="display: none;"/>
			</div>
            <table width="100%" class="AccountTab" border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td class="Tabname"><span class="icon01"><img src="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>images/icon01.jpg"></span>登录密码</td>
                <td class="Tabcont">为减少账号被盗风险，建议您定期更改密码以保护账户安全</td>
                
                <td class="Tabcoper"><c:if test="${not empty user.email}"><a href="<%=Constant.COMM_USER_URL %>personalSecurity1?uid=${user.id}&todo=resetPw&from=myFactory&domain=<%=Constant.SYSTEM_DOMAIN %>" >更换</a></c:if></td>
              </tr>
              <c:if test="${empty user.email}">
	              <tr>
	                <td class="Tabname"><span class="icon02"><img src="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>images/icon02.jpg"></span>绑定邮箱</td>
	                <td class="Tabcont">绑定邮箱，可以用来登录或找回密码</td>
	                <td class="Tabcoper bluecolor"><a href="<%=Constant.COMM_USER_URL %>personalSecurity1?uid=${user.id}&todo=bindEmail&from=myFactory&domain=<%=Constant.SYSTEM_DOMAIN %>">立即绑定</a></td>
	              </tr>
              </c:if>
              <c:if test="${not empty user.email}">
	              <tr>
	                <td class="Tabname"><span class="icon02"><img src="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>images/icon02.jpg"></span>更换邮箱</td>
	                <td class="Tabcont">当前绑定邮箱：<font class="red">${user.email}</font></td>
	                <td class="Tabcoper"><a id="changeEmail" href="<%=Constant.COMM_USER_URL %>personalSecurity1?uid=${user.id}&todo=bindEmail&from=myFactory&domain=<%=Constant.SYSTEM_DOMAIN %>">更换</a></td>
	              </tr>
              </c:if>
              <c:if test="${empty user.phone}">
	              <tr>
	                <td class="Tabname"><span class="icon03"><img src="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>images/icon03.jpg"></span>绑定手机</td>
	                <td class="Tabcont">绑定手机，可以用来登录或找回密码</td>
	                <td class="Tabcoper bluecolor"><a href="<%=Constant.COMM_USER_URL %>personalSecurity1?uid=${user.id}&todo=bindPhone&from=myFactory&domain=<%=Constant.SYSTEM_DOMAIN %>">立即绑定</a></td>
	              </tr>
              </c:if>
              <c:if test="${not empty user.phone}">
	              <tr>
	                <td class="Tabname"><span class="icon03"><img src="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>images/icon03.jpg"></span>更换手机</td>
	                <td class="Tabcont">当前绑定手机：<font class="red">${user.phone}</font></td>
	                <td class="Tabcoper"><a id="changePhone" href="<%=Constant.COMM_USER_URL %>personalSecurity1?uid=${user.id}&todo=bindPhone&from=myFactory&domain=<%=Constant.SYSTEM_DOMAIN %>">更换</a></td>
	              </tr>
              </c:if>
            </table>
            </div>
			</form>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->
<script type="text/javascript">
	$(document).ready(function(){
		selectedHeaderLi("sy_header");
	});
	
</script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
