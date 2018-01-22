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
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" >
<meta content="telephone=no" name="format-detection" />
<title>${title}</title>
<!--<link rel="stylesheet" type="text/css" href="css/style.css" />-->
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/404.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>

</head>
<!--content begin-->
<div class="main-cont" id="main-cont">
    <div class="top">
    	<h1><a href="javascript:close();" class="aleft"></a>我的换领币</h1>
    </div>
    <div id="content">
        <div class="errorwrap errorwrap1">
            <div class="errortext" style="padding-top:150px;">
                <p class="errortitle">您尚未获得换领币！</p>
                <p><span class="errred">换领币获得及使用说明：</span></p>
                <p>1、换领币由企业上传换领商品获得，并作为福利发放给员工;</p>
                <p>2、换领币仅限于购买换领商品使用;</p>
                <p>3、通过换领币可使企业员工间交换所需商品，加大福利商品消化能力；</p>
                <p>4、公司商品拿来交换，员工有机会0元获得福利商品。</p>
                <p>5、返回<a href="javascript:close();">我的福利个人中心</a>。</p>
            </div>
        </div>
    </div>
    <!--content end-->
</div>

</body>
<script>
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main-box").css("top","0");
	}
});
</script>
</html>