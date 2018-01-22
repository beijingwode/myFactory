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
.savingbtn{margin:7px 0 45px 105px}
.store_cont p{width:55px;}
.Info_Select_box{float:left;width:750px;}
.Info_Select{width: 118px;
    float: left;
    margin-right: 10px;
    outline: none;
    float: left;
    height: 30px;
    background: url(../static_resources/images/factory_img1.png) no-repeat -90px -299px;}
    .Info_Select select {
    width: 118px;
    height: 30px;
    padding-left: 4px;
    border: 1px solid #b5b5b5;
    overflow: hidden;
    font: 12px/28px "Microsoft YaHei";
    color: #434343;
    background: transparent;
    -webkit-appearance: none;
    box-shadow: inset 0px 1px 4px rgba(0,0,0,0.15);
    border-radius: 2px;
    behavior: url(iecss3/PIE.htc);
    position: relative;
    z-index: 2;
}
.f118{margin-left:0}
.store_cont {
    float: left;
    width: 100%;
    margin-bottom: 11px;
    font: 12px/28px "Microsoft YaHei";
    color: #434343;
}
.radio {
    width: 14px;
    height: 14px;
    vertical-align: middle;
    margin-right: 6px;
}
</style>
<body>
<%@ include file="/commons/header.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/index.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/areaFnc.js"></script>
<!--header end-->

<!--content begin-->
<div id="content">
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
			<li><a href="${basePath}/user/tosuppliermain.html">首页</a></li>
			<li class="curr"><a href="#">个人信息</a></li>	
			<li><a href="${basePath}/user/security.html">安全设置</a></li>
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
            <a href="${basePath}/user/info.html">个人信息</a>
        </div>
        <div class="sort_wrap">
    		<form action="${basePath}/user/updateInfo.html" method="post">
            <div class="sort_title">个人信息</div>
            <div class="store_cont">
                <p>头像</p>
                <div id="filePicker" style="margin-right:580px;color: #fff;width:50px; text-align: center;float:right;background: none repeat scroll 0 0 #5d6781;border-radius: 4px;cursor:pointer;">上&nbsp;传</div>
                <strong>（提示：上传图片不可删除，再次上传覆盖前一张）</strong>

                <div class="uploadlogo">
                    <div class="puplogo">
                        <c:choose>
                            <c:when test="${empty user.avatar}">
                                <img id="logo" src="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>images/head.jpg"/>
                            </c:when>
                            <c:otherwise>
                                <img id="logo" src="${user.avatar}"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <input type="hidden" name="avatar" id="logo_url" value="${user.avatar}"/>
                </div>
            </div>
            <div class="store_cont">
                <p>&nbsp;&nbsp;&nbsp;昵称：</p>
                <input class="pubilc_input f118" type="text" id="nickName" name="nickName" value="${user.nickName}" maxlength="24"/>
            </div>
            <div class="store_cont">
                <p>&nbsp;&nbsp;&nbsp;性别：</p>
                    <input class="radio" type="radio" name="gender" id="man" value="m" <c:if test="${user.gender=='m'}">checked="checked"</c:if>>男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input class="radio" type="radio" name="gender"  id="woman" value="f" <c:if test="${user.gender=='f'}">checked="checked"</c:if>>女
            </div>
            <div class="store_cont">
                <p>&nbsp;&nbsp;&nbsp;地区：</p>
                <div class="Info_Select_box">
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
            </div>
            <div class="savingbtn"><a href="javascript:;" onclick="save();">保存</a></div>
			</form>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<script type="text/javascript">

var jsBasePath='${basePath}';
var jsSsId='${ss.id}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/views_personInfo.js"></script>
<%@ include file="/commons/footer.jsp" %>
<div style="display:none;" id="tmp-upload_container">
 <input type="file" id="uploadFile" name="file" onchange="fileUpload()" style="position:absolute;filter:alpha(opacity=0);width:56px;height:30px;"  />
</div>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js?1233"></script>
</body>
</html>
