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
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
        	<li style="border-bottom-style: none;" ><a href="${basePath}/permissions_role.html">角色</a></li>
        	<li class="curr"><a href="${basePath}/permissions_user.html">用户</a></li>
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
        	<form id="sub_form" action="${basePath}/permissions_user.html" method="post">
        	 <input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
     	     <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
     	     
            <div class="sale_info">
            	<div class="sale_one" style="width:660px;">
            		<div class="sale_option">
                    	<div class="title">账号：</div>
                        <input class="pubilc_input f218" type="text" id="userName" name="userName" value="${query.userName}" maxlength="100">
                    </div>
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

                </div>
               
                <div class="btnwrap" style="float:left;width:200px;">
                	<div class="checkbtn"  style="float:left;"><a href="javascript:void(0);" onclick="formSubmit('1');">查询</a></div>
                    <div class="checkbtn " style="float:left;"><a href="javascript:void(0);" class="add_user_btn" >添加</a></div>
                </div>
            </div>
           </form>
            <div class="sale_list_wrap">
                <div class="sale_content">
	                    <table class="table-c" border="0" cellpadding="0" cellspacing="0">
	                     <thead>
	                        <tr><th style="width:270px;">账号</th>
	                        <th style="width:180px;">角色</th>
	                        <th style="width:220px;">邮箱</th>
	                        <th style="min-width:280px;">操作</th>
	                    </tr></thead>
                    	<tbody>
                    	<c:forEach items="${page.list}" var="p">
                    			<tr class="td_bor">
                    				<td>${p.userName}</td>
			                        <td>${p.name}</td>			                        
			                        <td>${p.email}</td>
			                        <td><div class="operate">
			                        <a href="javascript:void(0);" class="alter_btn" onclick="updateUserBox('${p.userId}','${p.id}')">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
			                        <a href="javascript:void(0);" class="del_btn" onclick="deleteUserBox('${p.userId}','${p.id}');">删除</a>&nbsp;&nbsp;&nbsp;&nbsp;
			                        <a href="javascript:void(0);" onclick="resetPassBox('${p.userId}');" class="reset_btn">重置密码</a>
			                        </div></td>
		                    	</tr>
	                    	</c:forEach>
                    	</tbody>
                    </table>
                </div>
                <!-- 分页位置 start-->
                <wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>       
        </div>
    </div>
    <!--right end-->
</div>
</div>
<!--content end-->


<script src="<%=static_resources %>js/jquery1.8.0.js"></script>
<div class="popup_bg"></div>
<!--添加-->
<div class="public_popup" id="add_pop" style="height:auto;">
    <div class="pop-title">
    	<span>添加用户信息</span>
        <div class="add-close-btn" id="add-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<ul class="change-list">
        	<li>
            	<span>账号(密码)</span>
                <div class="c-r-box"><input class="p-red" id="add_account" onblur='checkAccount(this.value,"add");' placeholder="邮箱/手机号/字母数字组合" maxlength="50" type="text" name=""><span id="add_account_error" style="display: none;"></span></div>
            </li>
            <li>
            	<span>选择角色</span>
                <div class="c-r-box">
                	<select id="add_roleId">
	                	<c:forEach items="${role}" var="r">
							<option value="${r.id}">${r.name}</option>
	                	</c:forEach>
                	</select>
                </div>
            </li>  
        	<li>
            	<span>邮箱</span>
                <div class="c-r-box"><input class="p-red" id="add_email" onblur='checkEmail1(this.value,"add")' placeholder="用于账号安全及找回密码" maxlength="50" type="text" name="" value=""><span id="add_email_error" style="display: none;"></span></div>
            </li>
        	<!-- <li>
            	<span>手机号</span>
                <div class="c-r-box"><input class="p-red" id="add_phone" onblur='checkPhone(this.value,"add")' maxlength="11" type="text" name="" value=""><span id="add_phone_error" style="display: none;"></span></div>
            </li> -->
        	<!-- <li>
            	<span>昵称</span>
                <div class="c-r-box"><input class="p-red" id="add_nickName" onblur='checkNickName(this.value,"add")' maxlength="11" type="text" name="" value=""><span id="add_nickName_error" style="display: none;"></span></div>
            </li> -->
        	<li>
            	<span>姓名</span>
                <div class="c-r-box"><input class="p-red" id="add_name" onblur='checkName(this.value,"add")' maxlength="6" type="text" name="" value=""><span id="add_name_error" style="display: none;"></span></div>
            </li>     
        </ul>
        <div class="add-btn-box"><a class="true-btn" onclick="addUserSub();" href="javascript:">确认</a><a class="cansel-btn" id="add-cansel-btn" href="javascript:">取消</a></div>
    </div>
</div>
<!--修改-->
<div class="public_popup" id="alter_pop" style="height:auto;">
    <div class="pop-title">
    	<span>修改用户信息</span>
        <div class="add-close-btn" id="provide-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<input type="hidden" id="userId">
    	<!-- <input type="hidden" id="update_roleId"> -->
    	<ul class="change-list">
        	<li>
            	<span>角色</span>
                <div class="c-r-box">
                	<!-- <input class="p-red" id="update_roleName" onfocus=this.blur() type="text" name="" value=""> -->
                	<select id="update_roleId">
						
                	</select>
                </div>
            </li>
            <!-- <li>
            	<span>账号</span>
                <div class="c-r-box"><input type="hidden" id="hidden_update_account" /><input type="text" id="update_account" onblur='checkAccount(this.value,"update");' maxlength="50" name="" value=""><span id="update_account_error" style="display: none;"></span></div>
            </li>  --> 
            <li>
            	<span>邮箱</span>
                <div class="c-r-box"><input type="hidden" id="hidden_update_email" /><input type="text" id="update_email" placeholder="用于账号安全及找回密码" maxlength="50" name="" onblur='checkEmail1(this.value,"update")' value=""><span id="update_email_error" style="display: none;"></span></div>
            </li>  
            <!-- <li>
            	<span>手机号</span>
                <div class="c-r-box"><input type="hidden" id="hidden_update_phone" /><input type="text" id="update_phone" maxlength="11" onblur='checkPhone(this.value,"update")' name="" value=""><span id="update_phone_error" style="display: none;"></span></div>
            </li> -->
            <!-- <li>
            	<span>昵称</span>
                <div class="c-r-box"><input type="text" id="update_nickName" maxlength="50" onblur='checkNickName(this.value,"update")' name="" value=""><span id="update_nickName_error" style="display: none;"></span></div>
            </li> -->  
            <li>
            	<span>姓名</span>
                <div class="c-r-box"><input type="text" id="update_name" maxlength="6" onblur='checkName(this.value,"update")' name="" value=""><span id="update_name_error" style="display: none;"></span></div>
            </li>        
        </ul>
        <div class="add-btn-box"><a class="true-btn" onclick="updateUserSub();" href="javascript:">确认</a><a class="cansel-btn" id="provide-cansel-btn" href="javascript:">取消</a></div>
    </div>
</div>
<!--删除-->
<div class="public_popup" id="delete_pop" style="height:auto;">
    <div class="pop-title">
    	<span>删除用户信息</span>
        <div class="add-close-btn" id="provide-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
    	<p>确定删除此用户信息？</p>
        <div class="add-btn-box"><a class="true-btn" id="deleteUserSub" href="javascript:">确认</a><a class="cansel-btn" id="provide-cansel-btn" href="javascript:">取消</a></div>
    </div>
</div>
<!--重置密码-->
<div class="public_popup" id="reset_pop" style="height:auto;">
    <div class="pop-title">
    	<span>重置密码</span>
        <div class="add-close-btn" id="reset-close"><i class="add-close-icon"></i></div>
    </div>
    <div class="pop-cont">
        <p>您确定要重置该用户密码吗？密码将会与账号相同。</p>
        <input type="hidden" id="resetPassUserId" />
        <div class="add-btn-box"><a class="true-btn" href="javascript:" onclick="resetPassTrue();">确认</a><a class="cansel-btn" id="reset-cansel-btn" href="javascript:">取消</a></div>
    </div>
</div>

<%@ include file="/commons/footer.jsp" %>
<script>
var jsBasePath="${basePath}";
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_permissions_user.js"></script>
</body>
</html>