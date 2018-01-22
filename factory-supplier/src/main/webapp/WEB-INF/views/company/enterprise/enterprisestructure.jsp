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
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, minimal-ui, user-scalable=no">
<meta content="always" name="referrer">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>商家中心--员工福利</title>
<%@ include file="/commons/js.jsp" %>


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
            <a href="javascript:void(0);">公司结构管理</a>
        </div>
        <div class="r-content">
        	<div class="m-tab">
            	<div class="m-title">
                	<span>品牌方</span>
                    <div class="m-btn1">
                    </div>
                </div>
                <ul class="m-com-list">
                <c:forEach items="${motherEnt}" var="m_ent">
                	<li>${m_ent.enterpriseName}</li>
                </c:forEach>
                </ul>
                <div class="m-title">
                	<span>代运营方</span>
                    <div class="m-btn1">
                    </div>
                </div>
                <ul class="m-com-list">
                <c:forEach items="${childEnt}" var="c_ent">
                	<li>${c_ent.enterpriseName}</li>
                </c:forEach>
                </ul>
            </div>                        
        </div>
    </div>
    <!--right end-->
	</div>
<!-- 背景弹出框 begin-->
<div class="popup_bg"></div>
<!-- 背景弹出框 end-->
<!--添加-弹出框 begin-->
<div class="public_popup" id="add" style="height:280px;">
    <div class="pop-title">
    	<span id="add_ent_title"></span>
        <div class="add-close-btn" id="add-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<p class="add-tl" id="add_ent_cont"></p>
        <p><input class="add-input" id="add_input" type="text" name=""></p>
        <div id="add_input_error" style="display: none;width:290px;margin-left:50px;color:red"></div>
        <div class="add-btn-box"><a id="ture-btn" class="true-btn" href="#">确认</a><a class="cansel-btn" id="cansel-btn" href="#">取消</a></div><!-- id="ture-btn"  -->
    </div>
</div>

<div class="public_popup" id="operation-suceess" style="height:240px;">
    <div class="pop-title-2">
        <div class="suceess-close-btn" id="suceess-close"><i onclick="success_close()" class="suceess-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<p id="operation_success_cont"></p>
        <div class="suceess-btn-box"><a onclick="success_close()" class="true-btn" href="#">确认</a></div>
    </div>
</div>
<!--添加-弹出框 end-->
<!--修改-弹出框 begin-->
<div class="public_popup" id="alter" style="height:auto">
    <div class="pop-title">
    	<span id="update_ent_title"></span>
        <div class="add-close-btn" id="alert-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<div class="alter-box" id="update_info">

        </div>
        <div class="add-btn-box"><a id="alter-ture-btn" class="true-btn" href="#" >确认</a><a class="cansel-btn" id="alter-cansel-btn" href="#">取消</a></div>
    </div>
</div>

<!--修改-弹出框 end-->
<!--删除-弹出框 begin-->
<div class="public_popup" id="delete" style="height:auto">
    <div class="pop-title">
    	<span id="del_ent_title"></span>
        <div class="add-close-btn" id="delete-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<div class="alter-box" id="del_info">
           

        </div>
        <div class="add-btn-box"><a id="delete-ture-btn" class="true-btn" href="#">确认</a><a class="cansel-btn" id="delete-cansel-btn" href="#">取消</a></div>
    </div>
</div>

<!-- <div class="public_popup" id="delete-suceess">
    <div class="pop-title-2">
        <div class="suceess-close-btn" id="delete-suceess-close"><i class="suceess-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<p>删除子公司列表成功！</p>
        <div class="suceess-btn-box"><a class="true-btn" href="#">确认</a></div>
    </div>
</div> -->
<!--删除-弹出框 end-->
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	

<script type="text/javascript">
//新输入公司名称
var new_ent_name = $(this).find(".add-input").val();
var jsBasePath = '${basePath}';
var entId = "${ent_id}";   
    function changeBtn(){
    	var html=""
		<c:forEach items="${motherEnt}" var="m_ent">
      			html+="<p>"
      			html+="<input class=\"add-input\" type=\"text\" id=\"\" value=\"${m_ent.enterpriseName}\">"
      			html+="<input class=\"hidden\" type=\"hidden\" id=\"${m_ent.id}\" value=\"${m_ent.enterpriseName}\">"
      			html+="</p>"
		</c:forEach>
		$("#update_info").append(html);
    }
    
    function changeIdVal(){
    	var html=""
			<c:forEach items="${childEnt}" var="c_ent">
       			html+="<p>"
       	       		html+="<input class=\"add-input\" type=\"text\" id=\"\" value=\"${c_ent.enterpriseName}\">"
       	       		html+="<input class=\"hidden\" type=\"hidden\" id=\"${c_ent.id}\" value=\"${c_ent.enterpriseName}\">"
       	       	html+="</p>"
			</c:forEach>
		$("#update_info").append(html);
    }
    
    function deleteMotherEnt(){
    	var html=""
			<c:forEach items="${motherEnt}" var="m_ent">
       			html+="<p>"
       			html+="<input class=\"add-input\" type=\"text\" id=\"${m_ent.id}\" style=\"\" value=\"${m_ent.enterpriseName}\"><i class=\"delete-icon del_mother_icon\"></i>"

       			html+="</p>"
			</c:forEach>
			$("#del_info").append(html);
    }
    
    function deleteChildEnt(){
    	var html=""
			<c:forEach items="${childEnt}" var="c_ent">
       			html+="<p>"
       			html+="<input class=\"add-input\" type=\"text\" id=\"${c_ent.id}\" style=\"\" value=\"${c_ent.enterpriseName}\"><i class=\"delete-icon del_child_icon\"></i>"
       			html+="</p>"
			</c:forEach>
			$("#del_info").append(html);
    }
    
    function addChildEnt(){
    	var name = $("#add_input").val();
    	<c:forEach items="${childEnt}" var="c_ent">
    		if("${c_ent.enterpriseName}"==name){
    			add_ent_error_open(name+"在子公司已经存在",3000);
    			return ;
    		}
    	</c:forEach>
    	add_ajax(1,"母公司");//1表示母公司
    }
    
    function addMotherEnt(){
    	var name = $("#add_input").val();
    	<c:forEach items="${motherEnt}" var="m_ent">
    		if("${m_ent.enterpriseName}"==name){
    			add_ent_error_open(name+"在母公司已经存在",3000);
    			return ;
    		}
    	</c:forEach>
    	add_ajax(2,"子公司");//2表示子公司
    }
    
    function mEnt(){
    	<c:forEach items="${motherEnt}" var="m_ent">
		if(new_ent_name == "${m_ent.enterpriseName}"){
			operation_success_box("母公司有重复");
			ent_box_close('#alter');
			return ;
		}
		</c:forEach>
    }
    
    function cEnt(){
    	<c:forEach items="${childEnt}" var="c_ent">
 		if(new_ent_name == "${c_ent.enterpriseName}"){
 			operation_success_box("子公司有重复");
			ent_box_close('#alter');
			return ;
 		}
 	 </c:forEach>
    }
    
</script>
<script type="text/javascript" src="<%=static_resources %>js/company_enterprise_enterprisestructure.js"></script>
</body>
</html>
