<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ include file="/commons/taglibs.jsp" %>
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
<title>返还佣金详情</title>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>
<%-- <script type="text/javascript" src="<%=static_resources %>resources/layer/layer.min.js"></script> --%>
<style type="text/css">
	.input{position:relative}
	.hover{background:#ccc}
	.addimagebutton{height:11px;line-height:11px;width:60px;font-size:12px;}

	
	table tbody tr td{text-align:center;height:30px;}
table thead th{border-right:1px solid #e6e8ea;background:#e9e7e7;height:30px;} 
.td_bor td{border:1px solid #e6e8ea;border-left:none;}
.td_bor_g td{background:#f4f3f3;border-right:1px solid #e6e8ea;}
	
</style>

</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
    <!--right begin-->
    <div class="right">
        <div class="sort_wrap">
             <div class="sale_list_wrap">
                <div class="sale_content" style="margin-left:20px;display:inline">
	                    <table class="table-c"  border="0" cellpadding="0" cellspacing="0">
	                     <thead>
	                     	<th style="width:160px;">员工ID</th>
	                        <th style="width:150px;">订单号</th>
	                        <th style="width:170px;">实付金额</th>
	                        <th style="width:150px;">运费</th>
	                        <th style="width:150px;">内购券</th>
	                        <th style="width:170px;">佣金合计</th>
	                    </thead>
                    	<tbody>
                    	<c:forEach items="${result.msgBody}" var="item" varStatus="status">
                    		<tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>" >
	                        <td>${item.userId}</td>
	                        <td>${item.orderId}</td>
	                        <td><fmt:formatNumber value="${item.paymentAmount}" type="currency" pattern="0.00"/></td>
	                        <td><fmt:formatNumber value="${item.freight}" type="currency" pattern="0.00"/></td>
	                        <td><fmt:formatNumber value="${item.fuli}" type="currency" pattern="0.00"/></td>
	                        <td><fmt:formatNumber value="${item.commissionTotal}" type="currency" pattern="0.00"/></td>
	                        </tr>
                    	</c:forEach>
                    	</tbody>
                    </table>
                </div>
            </div> 
            <div class="savingbtn"><a href="javascript:window.close();">确定</a></div>
        </div>
    </div>
    <!--right end-->
</div>
<script id="upload_ue"  type="text/plain" style="width:0px;height:0px;"></script>
   
<!--content end-->
<script type="text/javascript">
    	$(document).ready(function(){
    		selectedHeaderLi("jsgl_header");
		});
    	
    </script>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>