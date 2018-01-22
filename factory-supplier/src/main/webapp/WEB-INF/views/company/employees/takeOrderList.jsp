<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
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
<meta content="always" name="referrer">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>商家中心--员工福利</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/welfare.css">
<%@ include file="/commons/js.jsp" %>

<script type="text/javascript">
	var jsBasePath = '${basePath}';
	var jsStaticResources = '<%=static_resources %>';
	var jsWelfareLevel="${welfareLevel}";
	var jsEntId="${ent.id}";
</script>
<script type="text/javascript" src="<%=static_resources %>js/company_employees_takeOrder.js"></script>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
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
			<a href="javascript:void(0);">自提订单列表</a>
	      </div>
          <div class="sale_wrap">
			<form action="${basePath}/company/emp/takeOrderPage.html" method="post" id="sub_form">
        	 <input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
     	     <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
            <div class="sale_info">
                <div class="sale_one">
                	<div class="sale_option">
                    	<div class="title">员工姓名</div>
                        <input class="pubilc_input f218" type="text" name="name" value="${query.name}" maxLength="20">
                    </div>
                    <div class="sale_option">
                    	<div class="title">电话</div>
                        <input class="pubilc_input f218" type="text" name="phone" value="${query.phone}" maxLength="20">
                    </div>
                	<div class="sale_option">
                    	<div class="title">状态</div>
                        <select class="pubilc_input f226" id="orderStatus" name="orderStatus">
							<option value="-1">全部</option>
							<option value="1" ${query.orderStatus==1?"selected":""}>未收货</option>
							<option value="2" ${query.orderStatus==2?"selected":""}>已收货</option>
                        </select>
                    </div>
                </div>
                <div class="sale_one">
                    <div class="sale_option">
                    	<div class="title">下单日期</div>
                        <input class="pubilc_input f91" type="text" readonly="readonly" id="startTime" name="startTime" value="<fmt:formatDate value="${query.startTime}" pattern="yyyy-MM-dd" />" onClick="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})"/><span>到</span>
                        <input class="pubilc_input f91" type="text" readonly="readonly" id="endTime" name="endTime" value="<fmt:formatDate value="${query.endTime}" pattern="yyyy-MM-dd" />" onClick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>
                   </div>
                	<div class="sale_option">
                    	<div class="title">部门</div>
                        <input class="pubilc_input f218" type="text" name="sectionName"  value="${query.sectionName}" maxLength="50">
                    </div>
                	<div class="sale_option">
                    	<div class="title">商品名称</div>
                        <input class="pubilc_input f218" type="text" name="productName"  value="${query.productName}" maxLength="50">
                    </div>
                </div>
                <div class="clear"></div>
                <div class="btnwrap" style="width: 300px">
					<div class="checkbtn"><a href="javascript:void(0);"  onclick="formSubmit('1');">查询</a></div>
					<div class="checkbtn"><a href="javascript:void(0);" onclick="formReset();">重置</a></div>
					<div class="resetbtn"><a href="javascript:void(0);" onclick="exportExcel();">导出EXCEL</a></div>
                </div>
            	<div class="sale_list_wrap">
                <div class="sale_content">
                    <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                     <thead>
                        <th style="min-width:100px;">员工</th>
                        <th style="width:110px;">电话</th>
                        <th style="width:90px;">部门</th>
                        <th style="width:200px;">商品名称</th>
                        <th style="width:210px;">规格</th>
                        <th style="width:50px;">数量</th>
                        <th style="width:100px;">下单日期</th>
                        <th style="width:100px;">状态</th>
                    </thead>
                    	<tbody >
                    	<c:forEach items="${page.list}" var="i" varStatus="status">
                    		<tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>" >
	                    	<td>${i.name}</td>
	                        <td>${i.phone}</td>
	                        <td>${i.sectionName}</td>
	                        <td>${i.productName}</td>
	                        <td>${i.itemValues}</td>
	                        <td>${i.number}</td>
	                        <td><fmt:formatDate value="${i.createTime}" pattern="yyyy-MM-dd" /></td>
	                        <td>
	                        <c:if test="${i.orderStatus==1}">
	                       		 未收货
	                        </c:if>
	                        <c:if test="${i.orderStatus==2}">
	                       		 未收货
	                        </c:if>
	                        <c:if test="${i.orderStatus>=4}">
	                       		 已收货
	                        </c:if>
	                        </td>
	                        </tr>
                    	</c:forEach>
                    	</tbody>
                    </table>
                </div>
	           	</div>
	        </div>
           	</form>
            <!-- 分页位置 start--> 
            <wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>
            
            <!-- 分页位置 end-->
		  </div>
		</div>
		<!--right end-->
	</div>
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	
<%@ include file="/commons/box.jsp" %>
</body>
</html>
