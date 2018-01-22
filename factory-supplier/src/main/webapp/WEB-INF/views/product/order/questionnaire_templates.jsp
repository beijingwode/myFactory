<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<title>我的网商家中心</title>
<%@ include file="/commons/header.jsp" %>
<%@ include file="/commons/js.jsp" %>

<script type="text/javascript">
	var jsBasePath = '${basePath}';
	var jsStaticResources='<%=static_resources %>';
</script>

<!--header end-->

<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
             <c:if test="${userSession.type != 3 || userSession.hasAuth('sellList')}">
                 <li><a href="${basePath}/suborder/gotoSelllist.html">已售出的商品</a></li>
             </c:if>
             <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
                 <li><a href="${basePath}/comments/toevaluation.html?commentDegree=all">评价管理</a></li>
             </c:if>
             <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
                 <li class="curr"><a href="${basePath}/questionnaire/templates.html">问卷模板</a></li>
             </c:if>
			 <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
					<li><a href="${basePath}/questionnaire/trialProduct.html?leftMenu=order">试用商品问卷</a></li>
			 </c:if>
        </ul>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/suborder/gotoSelllist.html">订单管理</a><em>></em>
            <a href="${basePath}/questionnaire/templates.html">问卷模板</a><em>></em>
        </div>
        <div class="sale_wrap">
            <div class="main_bd">
            	<div class="add_new topbar group ">
            		<a class="btn btn_primary btn_add js_add_btn" href="${basePath}/questionnaire/template_edit">+新建模板</a>
            	</div>
            	<div class="table_wrp with_border">
            		<table class="table" cellspacing="0">
            			<thead class="thead">
            				<tr>
            					<th class="table_cell vote_title">
            						<div class="td_panel">模板名称</div>
            					</th>
            					<th class="table_cell vote_num">
            						<div class="td_panel">问题个数</div>
            					</th>
            					<th class="table_cell vote_state">
            						<div class="td_panel">使用次数</div>
            					</th>
            					<th class="table_cell last_child no_extra vote_opr">
            						<div class="td_panel">操作</div>
            					</th>
            				</tr>
            			</thead>
            			<tbody class="tbody js_list_body">            			
                	  	  <c:forEach var="q" items="${list}" varStatus="status">
							<tr>
								<td class="table_cell vote_title">
									<div class="td_panel">${q.templateTitle}</div>
								</td>
								<td class="table_cell vote_num">
									<div class="td_panel">${q.testFlg}</div>
								</td>
								<td class="table_cell vote_state">
									<div class="td_panel">${q.createUser}</div>
								</td>
								<td class="table_cell last_child vote_opr">
									<div class="td_panel">
										<a href="${basePath}/questionnaire/template_edit?templateId=${q.id}">编辑</a>
										<a href="javascript:toDelete(${q.id});" supervoteid="463522720" class="delete">删除</a>
									</div>
								</td>
							</tr>
						  </c:forEach>
						</tbody>
					</table>
				</div>
				
			</div>		
		</div>
    </div>
    <!--right end-->
    <input type="hidden" id="delId">
</div>
<!--content end-->

<%@ include file="/commons/alertMessage.jsp" %>
<%@ include file="/commons/footer.jsp" %>
<script type="text/javascript">
function toDelete(id) {
	showConfirm("模板删除确认","您确认删除此模板吗？删除模板不影响正在使用的问卷.","doDelete("+id+")");
}


function doDelete(id) {
	$.ajax({
        url: jsBasePath+'/questionnaire/delTemplate.json',
        type: "POST",
        dataType: "json",  //返回json格式的数据
        data: {"id":id},  //返回json格式的数据
        async: true,
        success: function (data) {
        	if (data.success) {
                showInfoBox("删除成功");
                setTimeout(gotoList, 1500);
            } else {
                showInfoBox("删除失败");
            }
        }, error: function () {
        }
    });
}

function gotoList() {
	window.location = jsBasePath+'/questionnaire/templates.html';
}


$(document).ready(function () {
	selectedHeaderLi("ddgl_header");
});
</script>
  
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/style.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/sy_vote.css">
  
</body>
</html>
