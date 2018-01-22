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
	/** * 快速跳转*/
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
</script>
<style>
        table tbody tr td {
            text-align: center;
            height: 30px;
        }

        table thead th {
            border-right: 1px solid #e6e8ea;
            background: #e9e7e7;
            height: 30px;
        }

        .td_bor td {
            border: 1px solid #e6e8ea;
            border-left: none;
        }

        .td_bor_g td {
            background: #f4f3f3;
            border-right: 1px solid #e6e8ea;
        }
 #divExpress{width:100%;padding:0;}
#divExpress p{height:24px;line-height:24px;color:#6a6a6a;padding-left:20px;}
#divExpress p span{padding-left:20px;}
#divExpress .es_box{height:30px;margin-top:20px;}
#divExpress .es {width:200px;float:left;}
#divExpress .es span{padding-left:0px;font-size:14px;}
#divExpress .dt{font-size:14px;height:40px;line-height:40px;margin-top:10px;border-top:1px solid #f2f1f1}
#divExpress .cont_box1{width:100%;background:none;padding-top:30px;}

#divExpress .cont_box1 p{width:380px;margin:10px 0 0 50px;height:30px;padding:0;font-size:14px;}
#divExpress .cont_box1 p span{height:30px;width:70px;text-align:right;padding-right:10px;display:block;float:left;font-size:14px;line-height:30px;}
#divExpress .cont_box1 p em{height:30px;width:280px;line-height:30px;float:left;font-size:12px;font-style:normal}
#divExpress .cont_box1 p input{width:250px;margin:0}
#divExpress .cont_box1 select{width:158px;height:30px;color:#6a6a6a;}
 .shop_popup{width:500px;}      
 .popup_btn1 {
    text-align: center;
    width: 100%;
    margin: 30px auto 0;
}
.popup_btn1 a:link, .popup_btn1 a:visited {
    display: inline-block;
    text-decoration: none;
    font: 14px/30px "Microsoft YaHei";
    color: #fff;
    width: 86px;
    height: 30px;
    text-align: center;
    margin-right: 20px;
    background: #5d6781;
    border-radius: 3px;
    behavior: url(iecss3/PIE.htc);
    position: relative;
    z-index: 2;
}
.sale_option{margin-right:30px;}
.sale_option span {float:left;}
.sale_option span em{color:#ff6161;font-style:normal;padding:0 5px;font-size:12px;}
 .sale_option .title{width:auto;float:left;}
 .Sold_change{margin-top:30px;}
       
        /*
        css 注释：
        只对table td设置左与上边框；
        对table设置右与下边框；
        为了便于截图，我们将css 注释说明换行排版
        */
#cant_carry_popup tr td{border-left:none;border-bottom:none;}
    </style>
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
                <li class="curr"><a href="${basePath}/saleBill/account.html">现金账户</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/transferFlow.html">提现记录</a></li>
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
		<form action="${basePath}/saleBill/account.html" method="post" id="sub_form">
       	 	<input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
    	    <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
    	    <input type="hidden" id="type" name="type" value="${type}"/>
            <div class="sale_info">
                <div class="sale_one">
                	<div class="sale_option">
                    	<div class="title" style="font-size:16px;">现金余额</div>
                		<span><em  style="font-size:16px;font-weight:600;"><fmt:formatNumber type="number" value="${cashBalance}" maxFractionDigits="2" /></em>元</span>
                    </div>
                	<div class="sale_option">
                    	<div class="title">累计返还佣金（三个月）</div>
                		<span><em><fmt:formatNumber type="number" value="${cashReturn}" maxFractionDigits="2" /></em>元</span>
                    </div>
                	<div class="sale_option">
                    	<div class="title">内购券余额</div>
                		<span><em><fmt:formatNumber type="number" value="${ticketBalance}" maxFractionDigits="2" /></em>元</span>
                    </div>
                    <div class="add_new" style="float:right"><a href="javascript:void(0);" id="btn">${btnName}</a></div>
                </div>
            </div>
        </form>
            	
		<div class="clear"></div>
                		
        <ul class="Sold_change">
        	<li><a href="javascript:void(0);"  onclick="tagChange(this);" typename="1" <c:if test="${type==1}">class="a1"</c:if>>全部</a></li>
            <li><a href="javascript:void(0);"  onclick="tagChange(this);" typename="2" <c:if test="${type==2}">class="a1"</c:if>>近一个月</a></li>
            <li><a href="javascript:void(0);"  onclick="tagChange(this);" typename="3" <c:if test="${type==3}">class="a1"</c:if>>近三个月</a></li>
        </ul>
                
      	<div class="sale_list_wrap">
          <div class="sale_content">
              <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                  <thead>
                     <th style="min-width:110px;">日期</th>
                     <th style="width:100px;">时间</th>
                     <th style="width:120px;">事项</th>
                     <th style="width:150px;">现金额度</th>
                     <th style="width:150px;">操作后余额</th>
                     <th style="width:350px;">备注</th>
                 </thead>
                 <tbody >
                 	<c:forEach items="${page.list}" var="info" varStatus="status">
                 	<tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>" >
                  		<td><fmt:formatDate value="${info.opDate}" pattern="yyyy-MM-dd"/></td>
                      <td><fmt:formatDate value="${info.opDate}" pattern="HH:mm:ss"/></td>
                      <td><c:out value="${info.name }"/></td>
                      <td>
                        <div class="b-size"><c:if test="${info.value==-1 and info.cash >0 }">-</c:if>
                        <fmt:formatNumber type="number" value="${info.cash }" maxFractionDigits="2" /> 
                        </div>
                      </td>
                      <td>
                        <fmt:formatNumber type="number" value="${info.cashBalance }" maxFractionDigits="2" /> 
                      </td>
                      <td><c:out value="${info.note }"/></td>
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

<!--邮寄退回-->
<div class="shop_popup" id="send_return_popup" style="width:500px;">
	<div class="popup_title">
    	<span>申请提现</span>
        <label onclick="hiddenObjById('send_return_popup');"><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
   	<div class="popup_cont" id="divExpress"  style="padding:0">
		<div class="cont_box cont_box1" style="padding-top:15px;">
			<p style="margin-left:100px;"><span>账户余额：</span><em style="line-height:30px;"><fmt:formatNumber type="number" value="${cashBalance}" maxFractionDigits="2" />
			元</em></p>
			<p style="margin-left:100px;"><span>累计返佣：</span><em><fmt:formatNumber type="number" value="${cashReturn}" maxFractionDigits="2" />元</em></p>
			<p style="margin-top:15px;margin-left:100px;"><span>提现金额：</span><input class="pubilc_input " style="width:150px;" id="amount" type="text" maxlength="10" value="${cashBalance}" onblur="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');"/></p>
		</div>
	</div>
   
    <div class="clear"></div>
  	<div id="ajaxErrMsg_send_return" class="box_msg" style="color: red;padding-left:152px;"></div>        
        <div class="popup_btn1" style="padding-bottom:20px;">
            <a href="Javascript:void(0);" onclick="return sendReturn();">确认</a>
            <a href="javascript:void(0);" onclick="hiddenObjById('send_return_popup');">取消</a>
        </div>
	</div> 
</div>
<!--邮寄退回end-->

<script type="text/javascript">
var jsBasePath='${basePath}';
   var jsCashBalance=${cashBalance};
   var jsBtnStatus="${btnStatus}";
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_salebill_account.js"></script>
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<%@ include file="/commons/alertMessage.jsp" %>
<!-- 页脚 end -->	
</body>
</html>
