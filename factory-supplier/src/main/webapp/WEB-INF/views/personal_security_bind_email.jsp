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
<title>个人信息</title>
<head>
<%@ include file="/commons/js.jsp" %>
</head>
<style>
.alter_cont{margin:50px 0 0 200px;}
.password_finish{overflow:hidden;}
.alter_num {
    
    font: 14px/40px "Microsoft YaHei";
    color: #434343;
    height: 60px;
}
.emailname{float:left;height:30px;line-height:30px;}
.alter_num input{height:28px;width:150px;padding:0 5px;float:left;border:1px solid #d2d2d2}
.alter_num em{font-style:normal}
.alter_num em a{float: left;
text-align:center;
    margin-left: 20px;
    width: 120px;
    height: 30px;
    background: #f1f1f1;
    border: none;
    font: 14px/30px "Microsoft YaHei";
    color: #6a6a6a;}
.alter_cont .sendEmailResult{height:30px;line-height:30px;color:#ff6161;margin-left:55px;}
.alter_cont .resendemail{height:30px;line-height:30px;float:left;margin-left:10px;}
.alter_cont .resendemail a{color:#ff6161;}
.Sreturnbtn{overflow:hidden;margin-left:240px;}
.Sreturnbtn a {
    background: #ff6161;
    float: left;
    margin: 0px 0 0 50px;
    width: 100px;
    height: 38px;
    text-align: center;
    border-radius: 4px;
    behavior: url(iecss3/PIE.htc);
    position: relative;
    z-index: 2;
    color: #fff;
    display: block;
    font: 14px/38px "Microsoft YaHei";
}
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
            <div class="password_finish">
            	<div class="alter_cont">
                    <div class="alter_num">
                    	<div style="overflow:hidden">
                    	<span class="emailname">新邮箱：</span>
                        <input class="yzminput" type="text" id="email">
                        <input type="hidden" id="userId" value="${user.id}">
                        <em>
                        	<a id="sendEmailForBind" href="javaScript:void(0);">发送验证邮件</a>
                        </em>
                        
                        <img class="loading_sendEmail" style="display: none;" id="loading" src="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>images/loading_small.gif"/>
                    	
                    	<p class="resendemail">未收到，<a id="sendEmailAgainForBind" href="javascript:void(0);">重发</a>？</p>
                    	</div>
                    	<p class="sendEmailResult" style="display: none;"></p>
                    </div>
                    
                    
                    
                </div>
            </div>
            <div class="Sreturnbtn"><a href="${basePath}/user/security.html">返回</a></div>
        </div>
    </div>
<!--right contont end-->
<div class="clear:after"></div>
</div>

<div class="clear"></div>

<script type="text/javascript">

var jsBasePath='${basePath}';

</script>
<script type="text/javascript" src="<%=static_resources %>js/views_personalSecurityBindEmail.js"></script>
<!--help begin-->
<%@ include file="/commons/footer.jsp" %>
<!--footer end-->
</body>
</html>
