<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragram" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0"> 
<title>我的网商家中心-权限管理</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/style.css" />
<style>
.sale_option select,.c-r-box select{width:184px;height:30px;padding-left: 10px;border: 1px solid #bebebe;box-shadow: inset 0px 1px 4px rgba(0,0,0,0.15);border-radius: 2px;line-height: 28px;outline: none;}
.sale_option select{width:226px;}
</style>
<%@ include file="/commons/js.jsp" %>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
        	<li class="curr"><a href="${basePath}/permissions_role.html">角色</a></li>
        	<li style="border-bottom-style: none;"><a href="${basePath}/permissions_user.html">用户</a></li>
	    </ul>
    </div>
    <!--left end-->
    <!--right begin-->
     
	
    <div class="right">
    	<div class="position">
        	<span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>&gt;</em>
            <a href="javascript:void(0);">首页</a><em>&gt;</em>
            <a href="javascript:void(0);">权限管理</a>      
        </div>
        <div class="sale_wrap">
        	<form id="sub_form" action="${basePath}/permissions_role.html" method="post">
        	 <input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
     	     <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
     	     
            <div class="sale_info">
            	<div class="sale_one" style="width:660px;">
                	<div class="sale_option">
                    	<div class="title">角色：</div>
                        <select name="id">
	                        <option value=""></option>
                        	<c:forEach items="${role}" var="r">
                        		<c:if test="${r.id eq query.id}">
		                            <option selected="selected" value="${r.id}">${r.name}</option>
                        		</c:if>
                        		<c:if test="${r.id != query.id}">
		                            <option value="${r.id}">${r.name}</option>
                        		</c:if>
                        	</c:forEach>
                        </select>
                    </div>
                	<div class="sale_option">
                    	<div class="title">用户：</div>
                        <input class="pubilc_input f218" type="text" id="userName" name="userName" value="${query.userName}" maxlength="100">
                    </div>

                </div>
               
                <div class="btnwrap" style="float:left;">
                	<div class="checkbtn"><a href="javascript:void(0);" onclick="formSubmit('1');">查询</a></div>
                    <div class="resetbtn"><a href="${basePath}/permissions_role_add.html" onclick="formReset();">添加</a></div>
                </div>
            </div>
           </form>
            <div class="sale_list_wrap">
                <div class="sale_content">
	                    <table class="table-c" border="0" cellpadding="0" cellspacing="0">
	                     <thead>
	                        <tr><th style="min-width:130px;">角色</th>
	                        <th style="width:200px;">用户</th>
	                        <th style="width:300px;">描述</th>
	                        <th style="min-width:320px;">操作</th>
	                    </tr></thead>
                    	<tbody>
	                    	<c:forEach items="${page.list}" var="p">
                    			<tr class="td_bor">
		                        <td>${p.name}</td>
		                        <td>
		                        	<c:forEach items="${p.user}" var="user">
			                        ${user.userName}</br>
		                        	</c:forEach>
		                        </td>
		                        <td>${p.description}</td>
		                        <td><div class="operate">
		                        <a href="${basePath}/role/edit/${p.id}" class="alter_btn" onclick="">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;<!-- update_role('${p.id}') -->
		                        <c:if test="${not empty p.user }">
			                        <a href="javascript:void(0);" class="del_btn" onclick="del_role('${p.id}','userName')">删除</a>
		                        </c:if>
		                        <c:if test="${empty p.user }">
			                        <a href="javascript:void(0);" class="del_btn" onclick="del_role('${p.id}','')">删除</a>
		                        </c:if>
		                        
		                        </div></td>
		                    	</tr>
	                    	</c:forEach>
                    	</tbody>
                    </table>
                </div>
               <!-- 分页位置 start--> 
               <!-- <div class="page"><a class="disabled" href="javascript:void(0);">前一页</a><a class="page_curr" href="javascript:void(0);" onclick="formSubmit(&#39;1&#39;);">1</a><a href="javascript:void(0);" onclick="formSubmit(&#39;2&#39;);">2</a><a href="javascript:void(0);" onclick="formSubmit(&#39;2&#39;);">后一页</a><span>共2页</span><input maxlength="8" onkeyup="this.value=this.value.replace(/\D/g,&#39;&#39;)" onafterpaste="this.value=this.value.replace(/\D/g,&#39;&#39;)" type="text" id="pagenum" name="pagenum" class="input_text"><span>页</span><input type="button" value="确定" class="input_btn" onclick="gotoPage();"></div></div> -->       
               <wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>
        </div>
    </div>
    <!--right end-->
</div>
</div>
<!--content end-->


<div class="popup_bg"></div>
<!--修改-->
<div class="public_popup" id="alter_pop" style="height:auto;">
    <div class="pop-title">
    	<span>修改角色信息</span>
        <div class="add-close-btn" id="provide-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<ul class="change-list">
        	<li>
            	<span>角色</span>
                <div class="c-r-box"><input class="p-red" id="update_roleName" type="text" name="" value=""></div>
            </li>
            <li>
            	<span>用户</span>
                <div class="c-r-box"><input type="text" id="update_userName" name="" value=""></div>
            </li>  
            <li>
            	<span>描述</span>
                <div class="c-r-box"><input type="text" id="update_roleDescription" name="" value=""></div>
            </li>            
        </ul>
        <div class="add-btn-box"><a class="true-btn" href="javascript:void(0);">确认</a><a class="cansel-btn" id="provide-cansel-btn" href="javascript:void(0);">取消</a></div>
    </div>
</div>
<!--删除-->
<div class="public_popup" id="delete_pop" style="height:auto;">
    <div class="pop-title">
    	<span>删除角色信息</span>
        <div class="add-close-btn" id="provide-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<p id="deleteBoxInfo">确定删除此角色信息？</p>
        <div class="add-btn-box"><a class="true-btn" id="deleteRoleSub" href="javascript:void(0);">确认</a><a class="cansel-btn" id="provide-cansel-btn" href="javascript:void(0);">取消</a></div>
    </div>
</div>

<%@ include file="/commons/footer.jsp" %>


<script>
var jsBasePath="${basePath}";
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_permissions_role.js"></script>
</body>
</html>