<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>我的网---结算设置</title>
    <%@ include file="/commons/js.jsp" %>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	/*** 快速跳转*/
	function gotoPage(){
		var pagenum = $("#pagenum").val();
		formSubmit(pagenum);
	}
	/*** 表单提交*/
	function formSubmit(page){
		if(page!=undefined){
			$("#pageNumber").val(page);
		}else{
			$("#pageNumber").val(1);
		}
		$("#sub_form").submit();
	}
	/*** 标签状态切换*/
	function tagChange(obj){
		$(obj).parent().parent().find("li>a").removeClass("a1").end().end().end().addClass("a1");
		var type = $(obj).attr("typename");
		$("[name=type]").val(type);
		
		$("#startDate").val("");
		$("#endDate").val("");

		formSubmit(1);
	}
    /**重置**/
   
    function formReset() {      
        $("#sub_form").find("input[type!='hidden']").val("");
        $("#sub_form").find("select").find("option:first").attr("selected", "selected");
    }
</script>
</head>

<body>
<!-- top start -->
<%@ include file="/commons/header.jsp" %>
<!-- top end -->
<div id="content" class="clear">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/gotoSaleBillList.html">对账管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/account.html">现金账户</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li class="curr"><a href="${basePath}/saleBill/transferFlow.html">提现记录</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('commissionRefund')}">
                <li><a href="${basePath}/commissionRefund/gotoCommissionRefundList.html">佣金返还</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/receiptList.html">发票管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/baseEdit.html">结算设置</a></li>
            </c:if>
        </ul>
    </div>
    <!--left end-->
    
    <!--right begin-->
    <div class="right">
    	<div class="position">
        	<span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
         	<a href="${basePath}/saleBill/gotoSaleBillList.html">结算管理</a><em>></em>
         	<a href="javascript:void(0);">提现记录</a>
        </div>
        <div class="sale_wrap">
		<form action="${basePath}/saleBill/transferFlow.html" method="post" id="sub_form">
       	 	<input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
    	    <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
    	    <input type="hidden" id="type" name="type" value="${type}"/>
             <div class="sale_info">
                <div class="sale_one">
                	<div class="sale_option">
                    	<div class="title">状态</div>
                		<select class="pubilc_input f226" name="status"  id="status">
                           	<option value="">全部</option>
                           	<option value="1" <c:if test="${query.status == 1 }">selected="selected"</c:if>>已申请</option>
                           	<option value="2" <c:if test="${query.status == 2 }">selected="selected"</c:if>>已确认</option>
                           	<option value="3" <c:if test="${query.status == 3 }">selected="selected"</c:if>>已转账</option>
                           	<option value="-1" <c:if test="${query.status == -1 }">selected="selected"</c:if>>已拒绝</option>
                           	<option value="-3" <c:if test="${query.status == -3 }">selected="selected"</c:if>>转账失败</option>
                        </select>
                    </div>
                    <div class="sale_option">
                    	<div class="title">起止时间</div>
                    	<input class="pubilc_input f91 r-num" type="text" id="startDate" name="startDate" readOnly="readOnly" value="${query.startDate}"  onClick="WdatePicker()">
                    	<span class="s_zhi">到</span>
                    	<input class="pubilc_input f91 r-num" style="margin-left:5px" type="text" id="endDate" name="endDate" readOnly="readOnly" value="${query.endDate}"  onClick="WdatePicker()">
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
                     <th style="min-width:120px;">申请日期</th>
                     <th style="width:100px;">申请时间</th>
                     <th style="width:100px;">状态</th>
                     <th style="width:110px;">金额</th>
                     <th style="width:120px;">转账日期</th>
                     <th style="width:200px;">交易流水号</th>
                     <th style="width:200px;">备注</th>
                 </thead>
                 <tbody >
                 	<c:forEach items="${page.list}" var="info" varStatus="status">
                 		<tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>" >
                  	  <td><fmt:formatDate value="${info.createDate}" pattern="yyyy-MM-dd"/></td>
                      <td><fmt:formatDate value="${info.createDate}" pattern="HH:mm:ss"/></td>
                      <td>                      
                           <c:if test="${info.status==1}"><p class="">已申请</p></c:if>
                           <c:if test="${info.status==2}"><p class="">已确认</p></c:if>
                           <c:if test="${info.status==3}"><p class="">已转账</p></c:if>
                           <c:if test="${info.status==-1}"><p class="">已拒绝</p></c:if>
                           <c:if test="${info.status==-3}"><p class="">转账失败</p></c:if>
                      </td>
                      <td>
                        <div class="b-size">
                        <fmt:formatNumber type="number" value="${info.amount }" maxFractionDigits="2" /> 
                        </div>
                      </td>
                  	  <td><fmt:formatDate value="${info.payDate}" pattern="yyyy-MM-dd"/></td>
                  	  <td>${info.payFlowCode}</td>
                  	  <td>${info.rejectNote}</td>
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
<script type="text/javascript">
    $(document).ready(function () {
        selectedHeaderLi("jsgl_header");
    });
</script>
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	
</body>
</html>
