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
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>商家中心--员工福利</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/welfare.css">
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript">
/**
 * 快速跳转
 */
function gotoPage(){
	var pagenum = $("#pagenum").val();
	formSubmit(pagenum);
}

/**
 * 表单提交
 */
function formSubmit(page){
	if(page!=undefined){
		$("#pageNumber").val(page);
	}else{
		$("#pageNumber").val(1);
	}
	$("#sub_form").submit();
}

/**
 * 重置
 */
function formReset(){
	//$("#sub_form").reset();
	//document.getElementById("sub_form").reset();
	
	$("#sub_form").find("input[type!='hidden']").val("");
	$("#sub_form").find("select").find("option:first").attr("selected","selected");
}
</script>
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
            <a href="javascript:void(0);">员工交易流水</a>
        </div>
        <div class="sale_wrap">
        <form action="${basePath}/company/tradeFlow/page.html" method="post" id="sub_form">
       	 	<input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
    	    <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
            <div class="sale_info">
                <div class="sale_one">
                    <div class="sale_option">
                    	<div class="title">起止时间</div>
                    	<input class="pubilc_input f91 r-num" type="text" id="startDate" name="startDate" readOnly="readOnly" value="${query.startDate}"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})">
                    	<span class="s_zhi">到</span>
                    	<input class="pubilc_input f91 r-num" style="margin-left:5px" type="text" id="endDate" name="endDate" readOnly="readOnly" value="${query.endDate}"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})">
                    </div>
                </div>
            </div>
        </form>
		<div class="clear"></div>
		<div class="btnwrap">
			<div class="checkbtn"><a href="javascript:void(0);"  onclick="formSubmit('1');">查询</a></div>
			<div class="resetbtn"><a href="javascript:void(0);" onclick="formReset();">重置</a></div>
		</div>
		
		<div class="sale_list_wrap">
          <div class="sale_content">
              <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                  <thead>
                     <th style="min-width:113px;">日期</th>
                     <th style="width:116px;">姓名</th>
                     <th style="width:113px;">事项</th>
                     <th style="width:150px;">订单总额</th>
                     <th style="width:150px;">实付金额</th>
                     <th style="width:150px;">现金储值</th>
                     <th style="width:150px;">福利金额</th>
                 </thead>
                 <tbody >
                 <c:forEach items="${page.list}" var="info" varStatus="status">
                 	<tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>" >
                  		<td><fmt:formatDate value="${info.opDate}" pattern="yyyy-MM-dd"/></td>
                      	<td><c:out value="${info.empName }"/></td>
                      	<td><c:out value="${info.opName }"/></td>
                      	<td>
	                      	<div class="t-size">
	                        <fmt:formatNumber type="number" value="${info.totalProduct}" maxFractionDigits="2" /> 
	                        </div>
                        </td>
                        <td>
	                        <div class="t-size">
	                        <c:if test="${ not empty info.realPrice }">
	                        <fmt:formatNumber type="number" value="${info.realPrice }" maxFractionDigits="2" /> 
	                        </c:if>
                        	</div>
                        </td>
                        <td>
                        	<div class="t-size">
                        	<c:if test="${info.value==-1 and  info.cash > 0}">-</c:if>
	                        <fmt:formatNumber type="number" value="${info.cash }" maxFractionDigits="2" /> 
	                        </div>
                        </td>
                        <td>
							<div class="t-size">
                        	<c:if test="${info.value==-1 and  info.ticket > 0}">-</c:if>
                        	<fmt:formatNumber type="number" value="${info.ticket }" maxFractionDigits="2" /> 
                        	</div>
                      	</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
         </div>
       </div>
       <!-- 分页位置 start--> 
       <wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>

       </div>
    </div>
    <!--right end-->
  </div>

<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	
</body>
</html>
