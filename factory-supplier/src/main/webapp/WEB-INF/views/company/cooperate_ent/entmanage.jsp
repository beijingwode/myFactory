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
</head>
<script type="text/javascript">

		//修改按钮
		function changeBtn(){
			var html="";
			<c:forEach items="${ally}" var="ent">
				html+="<p><input class=\"add-input\" type=\"text\" name=\"\" value=\"${ent.enterpriseName}\"><input type=\"hidden\" class=\"hidden\" id=\"${ent.id}\" value=\"${ent.enterpriseName}\"></p>";
        	</c:forEach>
        	$("#update_info").append(html);
			background_open();
			ent_box_open('#alter');
		
		}
		
		//删除按钮
		function deleteBtn(){
			var html="";
			<c:forEach items="${ally}" var="ent">
				html+="<p>"
       			html+="<input class=\"add-input\" type=\"text\" id=\"${ent.id}\" style=\"\" value=\"${ent.enterpriseName}\"><i class=\"delete-icon\"></i>"
       			html+="</p>"
        	</c:forEach>
				$("#del_info").append(html);
				background_open();
				ent_box_open('#delete');
		}
		
		
		var jsBasePath = '${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/company_cooperate_entmanage.js"></script>
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
            <a href="javascript:void(0);">友盟企业管理</a>
        </div>
        <div class="r-content">
        	<div class="m-tab">
            	<div class="m-title">
                	<span>友盟企业管理</span>
                    <div class="m-btn1">
                    	<a href="#" id="add_emp_ally">添加</a>
                    	<c:if test="${!empty ally}">
	                        <a href="#" id="update_emp_ally">修改</a>
	                        <a href="#" id="del_emp_ally">删除</a>
                    	</c:if>
                    </div>
                </div>
                <ul class="m-com-list">
                <c:forEach items="${ally}" var="ent">
                	<li>${ent.enterpriseName}</li>
                </c:forEach>
                </ul>
            </div>                        
        </div>
    </div>
    <!--right end-->
		
	</div>
	<!--添加-弹出框 begin-->
<div class="public_popup" id="add" style="height: 280px;">
    <div class="pop-title">
    	<span id="add_ent_title">添加友盟企业</span>
        <div class="add-close-btn" id="add-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<p class="add-tl" id="add_ent_cont"></p>
        <p><input class="add-input" id="add_input" type="text" name=""></p>
        <div id="add_input_error" style="display: none;width:290px;margin-left:50px;color:red"></div>
        <div class="add-btn-box"><a id="add_but_sub" class="true-btn" href="#">确认</a><a class="cansel-btn" id="cansel-btn" href="#">取消</a></div><!-- id="ture-btn"  -->
    </div>
</div>
<!--添加-弹出框 end-->
<!--修改-弹出框 begin-->
<div class="public_popup" id="alter" style="height: auto;">
    <div class="pop-title">
    	<span id="update_ent_title">修改友盟企业</span>
        <div class="add-close-btn" id="alert-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<div class="alter-box" id="update_info">
			
        </div>
        <div class="add-btn-box"><a id="alter-ture-btn" class="true-btn" href="#">确认</a><a class="cansel-btn" id="alter-cansel-btn" href="#">取消</a></div>
    </div>
</div>

<!--修改-弹出框 end-->
<!--删除-弹出框 begin-->
<div class="public_popup" id="delete" >
    <div class="pop-title">
    	<span id="del_ent_title">删除友盟企业</span>
        <div class="add-close-btn" id="delete-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<div class="alter-box" id="del_info">
           

        </div>
        <div class="add-btn-box"><a id="delete-ture-btn" class="true-btn" href="#">确认</a><a class="cansel-btn" id="delete-cansel-btn" href="#">取消</a></div>
    </div>
</div>
<!--删除-弹出框 end-->
<!--通用-弹出框 begin-->
<div class="public_popup" id="operation-suceess" style="height: 240px;">
    <div class="pop-title-2">
        <div class="suceess-close-btn" id="suceess-close"><i onclick="success_close()" class="suceess-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<p id="operation_success_cont"></p>
        <div class="suceess-btn-box"><a onclick="success_close()" class="true-btn" href="#">确认</a></div>
    </div>
</div>
<!--通用-弹出框 end-->
<!-- 背景弹出框 begin-->
<div class="popup_bg"></div>
<!-- 背景弹出框 end-->
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	
</body>
</html>
