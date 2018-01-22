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
<script type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<script>
var jsBasePath = '${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/company_benefit_benefitFlow.js"></script>
<%@ include file="/commons/js.jsp" %>

</head>

<body>
<!-- top start -->
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
            <a href="javascript:void(0);">福利流水</a>
        </div>
        <div class="sale_wrap">
		<form action="${basePath}/company/benefit/entBenefitFlow" method="post" id="sub_form">
       	 	<input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
    	    <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
    	    <input type="hidden" id="type" name="type" value="${type}"/>
             <div class="sale_info">
                <div class="sale_one">
                	<div class="sale_option">
                    	<div class="title">事项</div>
                		<select class="pubilc_input f226" name="opCode"  id="code">
                           	<option value="">全部</option>
                           	<c:forEach items="${codeList}" var="i">
                           	<option value="${i.code }" <c:if test="${opCode eq i.code }">selected="selected"</c:if>>${i.name }</option>
                           	</c:forEach>
                        </select>
                    </div>
                    <div class="sale_option">
                    	<div class="title">起止时间</div>
                    	<input class="pubilc_input f91 r-num" type="text" id="startDate" name="startDate" readOnly="readOnly" value="${startDate}"  onClick="WdatePicker()">
                    	<span class="s_zhi">到</span>
                    	<input class="pubilc_input f91 r-num" style="margin-left:5px" type="text" id="endDate" name="endDate" readOnly="readOnly" value="${endDate}"  onClick="WdatePicker()">
                    </div>
                </div>
            </div>
        </form>
		<div class="clear"></div>
		<div class="btnwrap">
			<div class="checkbtn"><a href="javascript:void(0);"  onclick="formSubmit('1');">查询</a></div>
			<div class="resetbtn"><a href="javascript:void(0);" onclick="formReset();">重置</a></div>
		</div>
                		
        <ul class="Sold_change">
        	<li><a href="javascript:void(0);"  onclick="tagChange(this);" typename="1" <c:if test="${type==1}">class="a1"</c:if>>全部</a></li>
            <li><a href="javascript:void(0);"  onclick="tagChange(this);" typename="2" <c:if test="${type==2}">class="a1"</c:if>>近一个月</a></li>
            <li><a href="javascript:void(0);"  onclick="tagChange(this);" typename="3" <c:if test="${type==3}">class="a1"</c:if>>近三个月</a></li>
        </ul>
        
        
      	<div class="sale_list_wrap">
          <div class="sale_content">
              <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                  <thead>
                     <th style="min-width:180px;">日期</th>
                     <th style="width:180px;">时间</th>
                     <th style="width:185px;">事项</th>
                     <th style="width:200px;">福利额度</th>
                     <th style="width:200px;">现金额度</th>
                 </thead>
                 <tbody >
                 	<c:forEach items="${page.list}" var="info" varStatus="status">
                 		<tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>" >
                  		<td><fmt:formatDate value="${info.opDate}" pattern="yyyy-MM-dd"/></td>
                      <td><fmt:formatDate value="${info.opDate}" pattern="HH:mm:ss"/></td>
                      <td><c:out value="${info.name }"/></td>
                      <td>
                        <div class="b-size">
                        <c:if test="${info.value==-1 and info.ticket >0 }">-</c:if>
                        <fmt:formatNumber type="number" value="${info.ticket }" maxFractionDigits="2" /> 
                        </div>
                      </td>
                      <td>
                        <div class="b-size"><c:if test="${info.value==-1 and info.cash >0 }">-</c:if>
                        <fmt:formatNumber type="number" value="${info.cash }" maxFractionDigits="2" /> 
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
<script type="text/javascript" src="<%=static_resources %>js/manhuaDate.1.0.js"></script>
<script type="text/javascript">
$(function (){

	/* $("input.text-box").manhuaDate({					       
		Event : "click",//可选				       
		Left : 0,//弹出时间停靠的左边位置
		Top : -16,//弹出时间停靠的顶部边位置
		fuhao : "-",//日期连接符默认为-
		isTime : false,//是否开启时间值默认为false
		beginY : 1949,//年份的开始默认为1949
		endY :2100//年份的结束默认为2049
	}); */
	
});
</script>
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	
</body>
</html>
