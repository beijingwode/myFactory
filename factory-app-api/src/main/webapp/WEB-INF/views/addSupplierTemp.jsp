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
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/public.css"/>
<link rel="stylesheet" type="text/css"  href="<%=static_resources %>css/attract_investment_ewm.css"/>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery.mobile.custom.min.js"></script>
<title>添加临时商家</title>
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
	var managerId = ${managerId};
	var userId = ${userId};
	var managerName = "${managerName}";
</script>
</head>
<body>
<div class="main-cont" id="main-cont" >
	<div class="main-box" style="margin-top:10px;background:#fff;padding-bottom:24px;">
		<ul class="add_ul">
			<li>
				<span>企业名称<em>*</em></span>
				<input type="text" value="" id="suppilerName"/>
			</li>
			<li>
				<span>品牌</span>
				<input type="text" value="" id="brandName"/>
			</li>
			<li>
				<span>地址</span>
				<input type="text" value="" id="addres"/>
			</li>
			<li>
				<span>联系人</span>
				<input type="text" value="" id="contact"/>
			</li>
			<li>
				<span>联系方式</span>
				<input type="text" value="" id="phone"/>
			</li>
		</ul>
		<div class="add_btn"><a href="javascript:saveSupplier();">生成临时商家</a></div>
	</div>
	<div class="ts_p">生成的临时商家排列在商家列表最顶端。</div>
	
<%@ include file="/commons/alertMessage.jsp" %>
</div>
</body>
<script type="text/javascript">
var flag = false;
function saveSupplier(){
	if(flag) return;//防止重复点击
	flag = true;
	var suppilerName = $("#suppilerName").val()
	if(suppilerName==""){
		showInfoBox("商家名称不能为空");
		return;
	}
	$.ajax({
		url :jsBasePath+'supplierTemp/saveSupplierTemp',
		type : "post",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    data:{"managerId":managerId,"managerName":managerName,"suppilerName":suppilerName,"brandName":$("#brandName").val(),"addres":$("#addres").val(),"contact":$("#contact").val(),"phone":$("#phone").val()},
		success : function(data){
			if (data.success) {
				location.href=jsBasePath+"managerOrderRecord/merchanPage.user?uid="+userId;
			}
		},
		error : function(){
			alert("err");
		}
	});
}
</script>
</html>
