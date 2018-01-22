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
<head>
<title>我的网商家中心</title>
<%@ include file="/commons/header.jsp" %>
<%@ include file="/commons/js.jsp" %>

<script type="text/javascript" src="<%=static_resources %>js/areaFnc.js"></script>
<style type="text/css">
.table {border:1px solid #D3D3D3; border-width:1px 0px 0px 1px;}
.table .d {border:solid #D3D3D3; border-width:0px 1px 1px 0px;height:6px }
</style>
</head>
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
            <c:if test="${userSession.type != 3 || userSession.hasAuth('address')}">
                <li ><a href="${basePath}/shippingAddress/todeliver.html">发货地址管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('suborder')}">
                <li><a href="${basePath}/suborder/gotoSuborderlist.html">发货</a></li>
            </c:if>
            
            <li><a href="${basePath}/shippingAddress/freight_templates.html?ty=1">运费模板</a></li>
			<li class="curr"><a href="${basePath}/suborder/toSupplier_express.html?blank=1">常用快递公司</a></li>
            
        </ul>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/shippingAddress/todeliver.html">配送管理</a><em>></em>
            <a href="${basePath}/suborder/toSupplier_express.html?blank=1">常用快递公司</a>
        </div>
        <div class="sale_wrap">
            <div class="add_deliver_wrap">
                <form id="sub_form_express" action="${basePath}/suborder/Supplier_express.html" method="post">
                <input type="hidden" name="blank" id="blank" value="${blank}">
                <h2><strong>选择您常用的快递公司</strong><span>&nbsp;</span></h2>
					<table class="table" width="40%" cellspacing="0" cellpadding="0">
						<thead>
							<tr>
							    <th  align="center" bgcolor="#D3D3D3" >请选择</th>
								<th  align="center" bgcolor="#D3D3D3" >快递名称</th>								
							</tr>
						</thead>
						<tbody>
							<c:forEach var="item" items="${allCompInfoList }">
								<tr>
									<td class="d" align="center"><input type="checkbox"  name="id" value="${item.id}" /></td>
									<td class="d" align="center">${item.name}</td>
								</tr>
							</c:forEach>
							
						</tbody>
						
					</table>  
                   <div class="addressbtn" onclick="addExpress();"><a href="javascript:void(0);">保存</a></div>
               </form>
           </div>
            
		</div>
    
	</div>
</div>
<div class="popup_bg"></div>
<script type="text/javascript">
function addExpress(){
	var ids = "";
	var $list = $("[name=id]:checked"); //获取所有被选中的checkbox
	$list.each(function(i,val){ //each()方法规定为每个匹配元素规定运行的函数。
		ids = ids+$($list[i]).attr("value")+",";
	});
	if(ids==""){
		showInfoBox("至少选择一条!");
		return false;
	}
	$('#sub_form_express').submit();
} 

$(function() {
	selectedHeaderLi("psgl_header"); //顶部菜单配送管理选中
    var boxObj = $("input:checkbox[name='id']");  //获取所有的复选框
    var expresslist = '${supplierExpressids}'; //用el表达式获取在控制层存放的复选框的值为字符串类型
    var express = expresslist.split(','); //去掉它们之间的分割符“，”   
    for(i=0;i<boxObj.length;i++){
       for(j=0;j<express.length;j++){            
           if(boxObj[i].value == express[j])  //如果值与修改前的值相等
           {
               boxObj[i].checked= true;
               break;
           }
       }
    }          
})

</script>

<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/alertMessage.jsp"%>
</body>
</html>