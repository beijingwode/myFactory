<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>商家中心--员工福利</title>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript">
var jsBasePath = '${basePath}';

</script>
<script type="text/javascript" src="<%=static_resources %>js/company_enterprise_enterpriseinfo.js"></script>
</head>
<body>
	<!-- top start -->
	<%@ include file="/commons/header.jsp" %>
	<!-- top end -->

	<div id="content" class="clear">
		<!-- left menu start -->
		<%@ include file="/commons/leftmenu.jsp"%>
		<!-- left menu end -->
		  <!--right begin-->
    	<div class="right">
	    	<div class="position">
	        	<span>您所在的位置：</span>
	            <a href="javascript:void(0);">商家中心</a><em>></em>
	            <a href="${basePath}/company/enterprise/getEnterpriseInfo.html">员工福利</a><em>></em>
	            <a href="javascript:void(0);">企业基本信息</a>
	        </div>
	        <form id="sub_form" action="${basePath}/product/createProduct.html" method="post">
	    	<div class="sale_wrap">
	    	  <div class="add_info_wrap">
	            <div class="add_title">企业信息<span>（<b class="out">*</b>表示必填）</span></div>
		        <div class="add_info">
		        	<input type="hidden" name="id" value="${enterprise.id}">
		            <div class="add_model">
		            	<div class="name"><b class="out">*</b>企业名称：</div>
		                <input class="pubilc_input f538" type="text" id="update_name" name="name" value="${enterprise.name}" ismust="1" typename="input" />
		                <span>（上下游及友盟企业关联用）</span>
		            </div>
		            <div class="add_model">
		            	<div class="name"><b class="out">*</b>企业类型：</div>
		                <select class="select_input f538" name="type" ismust="1" typename="select">
		                	<option value="-1">未设置</option>
						<c:forEach items="${ent_type}" var="i">
							<option value="${i.id }" <c:if test="${enterprise.typeId eq i.id }">selected="selected"</c:if>>${i.name }</option>
						</c:forEach>
		                </select>
		                <span>（仅为福利额度审核时参考用）</span>
		            </div>
		            <div class="add_model">
		            	<div class="name"><b class="out">*</b>所属行业：</div>
		                <select class="select_input f538" name="industry" ismust="1" typename="select">
		                	<option value="-1">未设置</option>
						<c:forEach items="${ent_industry}" var="i">
							<option value="${i.id }" <c:if test="${enterprise.industryId eq i.id }">selected="selected"</c:if>>${i.name }</option>
						</c:forEach>
		                </select>
		                <span>（仅为福利额度审核时参考用）</span>
		            </div>
		            <div class="add_model">
		            	<div class="name"><b class="out"></b>上市企业：</div>
		                <select class="select_input f158" name="listed">
		                	<option value="是" <c:if test="${enterprise.listed eq '是' }">selected="selected"</c:if>>是</option>
		                    <option value="否" <c:if test="${enterprise.listed eq '否' }">selected="selected"</c:if>>否</option>
		                </select>
		                <span>（仅为福利额度审核时参考用）</span>
		            </div>
		            <div class="add_model">
		            	<div class="name"><b class="out"></b>年营业额（万）：</div>
						<input class="pubilc_input f158" type="text" name="turnover" id="update_turnover" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)" value="${enterprise.turnover}" maxlength="20">
		                <span>（仅为福利额度审核时参考用）</span>
		            </div>
		            <div class="add_model">
		            	<div class="name"><b class="out">*</b>企业人数（人）：</div>
						<input class="pubilc_input f158" type="text" name="peopleNumber" ismust="1" typename="input" id="update_peopleNumber" onkeyup="inutNumber(this)" onafterpaste="inutNumber(this)" value="${enterprise.peopleNumber}" maxlength="20">
						<span>（仅为福利额度审核时参考用）</span>
		            </div>
		            <div class="add_model">
		            	<div class="name"><b class="out">*</b>福利级别：</div>
						<input class="pubilc_input f158" style="text-align: center" type="text" readonly="readonly" name="welfareLevel" ismust="1" typename="input" id="update_welfareLevel" value="${enterprise.welfareLevel}">
		                <span>（员工最大可用福利级别，可以在员工管理画面修改）</span>
		            </div>
		        </div>
    		  </div>
            <div class="clear"></div>
         	<div class="add_btn">
             	<a href="javascript:void(0);" onclick="subForm();" id="sub_button0">保存</a>
         	</div>
    	</div>
    	</form>
    </div>
    <!--right end-->
	</div>
	
<!--新增企业-弹出框 end-->
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<%@ include file="/commons/alertMessage.jsp" %>
<!-- 页脚 end -->	
</body>
</html>
