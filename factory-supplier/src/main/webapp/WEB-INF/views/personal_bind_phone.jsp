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
<script type="text/javascript" >
var jsBasePath='<%=basePath %>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/personal_bind_phone.js"></script>
</head>
<style>
.alter_cont{margin:50px 0 0 200px;max-height:400px}
.password_finish{overflow:hidden;}
.alter_num {
    
    font: 14px/40px "Microsoft YaHei";
    color: #434343;
    height: 60px;
}
.alter_num .name {
    width: 126px;
    text-align: right;
    float: left;
}
.alter_num .getyzm {
    float:left;
    margin-left: 20px;
    width: 120px;
    height: 30px;
    background: #f1f1f1;
    border: none;
    font: 14px/30px "Microsoft YaHei";
    color: #6a6a6a;
    cursor: pointer;
}
.btnnext {overflow:hidden;margin-left:50px;}
.btnnext a{
    background: #ff6161;
    float: left;
    margin: 50px 0 0 50px;
    width: 100px;
    height: 38px;
    text-align: center;
    border-radius: 4px;
    behavior: url(iecss3/PIE.htc);
    position: relative;
    z-index: 2;
    color:#fff;
    display: block;
    font: 14px/38px "Microsoft YaHei";
}
.alter_num .login_yzm_chg {
  float: left;
  line-height: 20px;
}
.alter_num .login_yzm {
  float: left;
  margin-right: 10px;
}
.sendSmsResult{color:#ff6161;margin-left:125px;}
.alter_num .name{height:30px;line-height:30px;float:left;}
.yzminput{height:28px;float:left;border:1px solid #d2d2d2;padding:0 5px;}
.getyzminput{height:28px;float:left;border:1px solid #d2d2d2;padding:0 5px;}
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
			<li><a href="${basePath}/user/updateInfo.html">个人信息</a></li>	
			<li class="curr"><a href="${basePath}/user/security.html">安全设置</a></li>
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
        	<p class="progress-2"></p>
            <div class="password_finish">
            	<div class="alter_cont">
                    <div class="alter_num">
                    	<input type="hidden" id="uid" value="${user.id}">
                    	<span class="name">新手机：</span>
                        <input class="yzminput" type="text" id="phone" maxlength="11">
                    </div>
                	 <div class="alter_num">
                        <span class="name">输入校验码：</span>
                        <input class="user_input" type="text" id="vcode" maxlength="4" style="width:98px;float:left;margin-right:5px;">
		                <div class="login_yzm" id="" ><img src="" width="85px" height="40px" /></div>
		                <div class="login_yzm_chg" id="" ><a href="javascript:changeVCode();" style="color:#2b8dff;">看不清？<br />换一张！</a></div>
                    </div>
                    <div class="alter_num">
                    	<div style="overflow:hidden">
	                    	<span class="name">短信验证码：</span>
	                        <input class="getyzminput" type="text" id="code" maxlength="6">
	                        <button class="getyzm">获取验证码</button>
	                        <img id="loading" src="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>images/loading_small.gif"/>
                        </div>
	                    <span class="sendSmsResult" id="error" style="display: none;"></span>
                    </div>
                    <div class="btnnext"><a id="submitResult" href="javascript:void(0);">提交</a><a href="${basePath}/user/security.html">返回</a></div>
                </div>
            </div>
           
        </div>
    </div>
<!--right contont end-->
<div class="clear:after"></div>
</div>

<!--help begin-->
<%@ include file="/commons/footer.jsp" %>
<!--footer end-->
</body>
</html>
