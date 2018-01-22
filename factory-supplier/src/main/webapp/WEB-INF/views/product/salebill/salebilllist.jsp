<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>对账单总单</title>
    <script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
    <%@ include file="/commons/js.jsp" %>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
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

        /*
        css 注释：
        只对table td设置左与上边框；
        对table设置右与下边框；
        为了便于截图，我们将css 注释说明换行排版
        */
    </style>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li class="curr"><a href="${basePath}/saleBill/gotoSaleBillList.html">对账管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/account.html">现金账户</a></li>
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
            <!-- <li><a href="${basePath}/supplierDuration/toDurationSet.html">设置账单日</a></li> -->
        </ul>
    </div>
    <!--left end-->
    <!--right begin-->


    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/saleBill/gotoSaleBillList.html">结算管理</a><em>></em>
            <a href="javascript:void(0);">对账单管理</a>
        </div>
        <div class="sale_wrap">
            <form id="sub_form" action="${basePath}/saleBill/findSaleBillList.html" method="post">
                <input type="hidden" id="pages" name="pages" value="${pages}"/>
                <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>

                <div class="sale_info">
                    <div class="sale_one" style="display:none;">
                        <div class="sale_option">
                            <div class="title">商家ID：</div>
                            <input class="pubilc_input f218" type="text" id="supplierId" name="supplierId" value="${supplierId}" maxLength="20">
                        </div>
                        <div class="sale_option">
                            <div class="title">商家名称：</div>
                            <input class="pubilc_input f218" type="text" id="supplierName" name="supplierName" value="${supplierName}" maxLength="20">
                        </div>

                    </div>
                    <div class="sale_one">
                        <div class="sale_option">
                            <div class="title">对账单ID：</div>
                            <input class="pubilc_input f218" type="text" id="billId" name="billId" value="${billId}" maxLength="20">
                        </div>
                        <div class="sale_option">
                            <div class="title">结算周期：</div>
                            <input class="pubilc_input f91" type="text" readonly="readonly" id="startTime" name="startTime" value="${startTime}" onClick="WdatePicker()"/><span>到</span>
                            <input class="pubilc_input f91" type="text" readonly="readonly" id="endTime" name="endTime" value="${endTime}" onClick="WdatePicker()"/>
                        </div>
                        <div class="sale_option">
                            <div class="title">结算时间：</div>
                            <input class="pubilc_input f91" type="text" readonly="readonly" id="paystarttime" name="paystarttime" value="${paystarttime}" onClick="WdatePicker()"/><span>到</span>
                            <input class="pubilc_input f91" type="text" readonly="readonly" id="payendtime" name="payendtime" value="${payendtime}" onClick="WdatePicker()"/>
                        </div>
                    </div>
                    <div class="sale_one">
                        <div class="sale_option">
                            <div class="title">结算状态：</div>
                            <input type="hidden" id="payStatusTemp" value="${payStatus}">
                            <select class="pubilc_input f226" id="payStatus" name="payStatus">
                                <option value="">全部</option>
                                <option value="0">待审核</option>
                                <option value="3">审核通过</option>
                                <option value="-1">审核未通过</option>
                                <option value="4">已结算</option>

                            </select>
                        </div>

                        <div class="sale_option">
                            <div class="title">发票状态：</div>
                            <input type="hidden" id="receiptStatusTemp" value="${receiptStatus}">
                            <select class="pubilc_input f226" id="receiptStatus" name="receiptStatus">
                                <option value="">全部</option>
                                <option value="2">已开</option>
                                <option value="1">已申请</option>
                                <option value="0">未开</option>
                            </select>
                        </div>
                        <div class="sale_option">
                            <div class="title">商家确认：</div>
                            <input type="hidden" id="confirmStatusTemp" value="${confirmStatus}">
                            <select class="pubilc_input f226" id="confirmStatus" name="confirmStatus">
                                <option value="">全部</option>
                                <option value="0">待确认</option>
                                <option value="1">商家已同意</option>
                                <option value="-1">商家不同意</option>
                            </select>
                        </div>
                    </div>

                    <div class="clear"></div>
                    <div class="btnwrap">
                        <div class="checkbtn"><a href="javascript:void(0);" onclick="formSubmit('1');">查询</a></div>
                        <div class="resetbtn"><a href="javascript:void(0);" onclick="formReset();">重置</a></div>
                        
                    </div>
                </div>
            </form>
            <div class="sale_list_wrap" style="margin-top:10px;">
            	<div class="add_new" style="float:right"><a href="javascript:void(0);" onclick="apprReceiptConfirm();">申请发票</a></div>
                <div class="sale_content" style="margin-top:10px;">
                
                    <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                        <thead>
                        <th style="min-width:95px;">对账单ID</th>
                        <th style="width:100px;">结算周期</th>
                        <th style="width:75px;">货款</th>
                        <th style="width:85px;">内购券</th>
                        <th style="width:75px;">运费</th>
                        <th style="width:75px;">佣金</th>
                        <th style="width:85px;">应收款</th>
                        <th style="width:75px;">状态</th>
                        <th style="width:65px;">发票</th>
                        <th style="width:100px;">结算日期</th>
                        <th style="min-width:120px;">操作</th>
                        </thead>
                        <tbody>
                        <c:forEach items="${result.msgBody}" var="item" varStatus="status">
                            <tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>">
                                <td><a href="${basePath}/saleBill/toSaleBillView.html?saleBillId=${item.id}" target="_blank" style="word-break: break-all;">${item.billId}</a></td>
                                <td><fmt:formatDate value="${item.startTime}" pattern="MM-dd"/>至<fmt:formatDate value="${item.endTime}" pattern="MM-dd"/></td>
                                <td><fmt:formatNumber value="${item.receivePrice}" type="currency" pattern="0.00"/></td>
                                <td><fmt:formatNumber value="${item.fuCoin}" type="currency" pattern="0.00"/></td>
                                <td><fmt:formatNumber value="${item.carriagePrice}" type="currency" pattern="0.00"/></td>
                                <td><fmt:formatNumber value="${item.commissionPrice-item.refundAmount}" type="currency" pattern="0.00"/></td>
                                <td><fmt:formatNumber value="${item.payPrice}" type="currency" pattern="0.00"/></td>
                                <td>
                                  <c:if test="${item.confirmStatus==0}">待商家确认</c:if>
                                  <c:if test="${item.confirmStatus==1}">
                                  	<c:choose>
                                	  <c:when test="${item.payStatus==0}">
							                     待审核
							          </c:when>
                                	  <c:when test="${item.payStatus==3}">
							                     审核通过
							          </c:when>
                                	  <c:when test="${item.payStatus==4}">
							                     已结算
							          </c:when>
                                	  <c:when test="${item.payStatus<0}">
							                     审核未通过
							          </c:when>
							          <c:otherwise>
							          	正在审核
							          </c:otherwise>
                                	</c:choose>
                                  </c:if>
                                  <c:if test="${item.confirmStatus==-1}">商家不同意</c:if>
                                </td>
                                <td><c:if test="${item.receiptStatus==0}">未开</c:if><c:if test="${item.receiptStatus==1}">已申请</c:if><c:if test="${item.receiptStatus==2}">已开</c:if></td>
                                <td><fmt:formatDate value="${item.payTime}" pattern="yyyy-MM-dd"/></td>
                                <td>
                                    <div class="operate">
                                        <c:if test="${item.confirmStatus==0}"><a href="${basePath}/saleBill/toSaleBillView.html?saleBillId=${item.id}" target="_blank">查看</a>&nbsp;&nbsp;<a href="javascript:ajaxConfirmAlert(${item.id},1);">同意</a>&nbsp;&nbsp;<a href="javascript:ajaxConfirmAlert(${item.id},-1);">不同意</a></c:if>
                                        <c:if test="${item.confirmStatus==1||item.confirmStatus==-1}"><a href="${basePath}/saleBill/toSaleBillView.html?saleBillId=${item.id}" target="_blank">查看</a></c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <wodepageform:PageFormTag pageSize="${result.size}" totalPage="${result.totalPage}" currentPage="${result.page}" url=""/>
            </div>
            
	        <div class="explain_box">
	        	<span><i>*</i>&nbsp;发票申请须知：</span>
	        	<ul>
	        		<li>（1） 开票类型：增值税普通发票</li>
	        		<li>（2）申请开票金额：当佣金结算金额满500元后（包括500元）可申请开票，佣金结算未满500元时，需累计至500元可申请开票，当年佣金不满500元时，年底可统一申请开票。如有特殊申请开票问题，可致平台处理。 </li>
	        		<li>（3）发票开具及寄出时间：平台自收到开票申请起10个工作日内开具并寄出发票。</li>
	        		<li>（4）变更发票抬头：发票抬头与入驻商户的公司名称一致,如需更改名称，首页中点击商户信息进行修改，公司名称经平台审核通过后，发票抬头自动更改。</li>
	        		<li>（5）开具的发票有误或其他问题需要重新开具：当月开出的发票（看发票上的开具时间）只要在当月内（以我的网平台实际收到发票时间为准）可将发票退回重新开具，隔月发票退回需开具证明加盖公司公章，与发票同时退回。</li>
	        		<li>（6）开具的发票未收到:可在发票开具后台查看发票当前处理状态及快递查询（平台寄出发票后由于物流信息查询原因，请在平台寄出快递3个月内查询），如确认未收到，请致平台核实并处理。</li>	        		
	        	</ul>
	        </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<script type="text/javascript">
var jsBasePath='${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_salebill_salebilllist.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>