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
    <title>佣金返还</title>
    <script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
    <%@ include file="/commons/js.jsp" %>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
    <style>
        .table-c table {
            border-right: 1px solid #F00;
            border-bottom: 1px solid #F00
        }

        .table-c table td {
            border-left: 1px solid #F00;
            border-top: 1px solid #F00
        }

        table tbody tr td {
            text-align: center;
        }

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
                <li><a href="${basePath}/saleBill/gotoSaleBillList.html">对账管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/account.html">现金账户</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/transferFlow.html">提现记录</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('commissionRefund')}">
                <li class="curr"><a href="${basePath}/commissionRefund/gotoCommissionRefundList.html">佣金返还</a></li>
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
            <a href="${basePath}/commissionRefund/gotoSaleBillList.html">结算管理</a><em>></em>
            <a href="javascript:void(0);">佣金返还</a>
        </div>
        <div class="sale_wrap">
            <form id="sub_form" action="${basePath}/commissionRefund/findCommissionRefundList.html" method="post">
                <input type="hidden" id="pages" name="pages" value="${pages}"/>
                <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
            </form>
            <div class="sale_list_wrap">
                <div class="sale_content">
                    <table class="table-c" border="0" cellpadding="0" cellspacing="0">
                        <thead>
                        <th style="width:100px;">返还佣金ID</th>
                        <th style="width:100px;">销售金额</th>
                        <th style="width:100px;">应付佣金</th>
                        <th style="width:100px;">购买总金额</th>
                        <th style="width:100px;">购买含佣金</th>
                        <th style="width:100px;">现金券抵扣销售</th>
                        <th style="width:100px;">累计抵扣</th>
                        <th style="width:100px;">累计发放现金券</th>
                        <th style="width:100px;">返还佣金</th>
                        <th style="width:60px;">操作</th>
                        </thead>
                        <tbody>
                        <c:forEach items="${result.msgBody}" var="item" varStatus="status">
                            <tr class="td_bor<c:if test="${status.index%2 == 1 }">_g</c:if>">
                                <td>
                                    <div class="shop_info">
                                        <p><a href="${basePath}/commissionRefund/toCommissionRefundView.html?commissionRefundId=${item.id}" target="_blank" style="word-break: break-all;">${item.refundId}</a></p>
                                    </div>
                                </td>
                                <td>
                                    <div class="shop_info"><fmt:formatNumber value="${item.saleAmount}" type="currency" pattern="0.00"/></div>
                                </td>
                                <td>
                                    <div class="shop_info"><fmt:formatNumber value="${item.currentCommission}" type="currency" pattern="0.00"/></div>
                                </td>
                                <td>
                                    <div class="shop_info"><fmt:formatNumber value="${item.amount}" type="currency" pattern="0.00"/></div>
                                </td>
                                <td>
                                    <div class="shop_info"><fmt:formatNumber value="${item.commissionTotal}" type="currency" pattern="0.00"/></div>
                                </td>
                                <td>
                                    <div class="shop_info"><font color="green"><fmt:formatNumber value="${item.dedCash}" type="currency" pattern="0.00"/></font></div>
                                </td>
                                <td>
                                    <div class="shop_info"><fmt:formatNumber value="${item.dedCashSum}" type="currency" pattern="0.00"/></div>
                                </td>
                                <td>
                                    <div class="shop_info"><fmt:formatNumber value="${item.giveCashSum}" type="currency" pattern="0.00"/></div>
                                </td>
                                <td>
                                    <div class="shop_info"><font color="red"><fmt:formatNumber value="${item.commissionAmount}" type="currency" pattern="0.00"/></font></div>
                                </td>
                                <td><a href="${basePath}/commissionRefund/toCommissionRefundView.html?commissionRefundId=${item.id}" target="_blank">查看</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <wodepageform:PageFormTag pageSize="${result.size}" totalPage="${result.totalPage}" currentPage="${result.page}" url=""/>
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<script type="text/javascript">
    $(document).ready(function () {
        selectedHeaderLi("jsgl_header");
    });

    /*** 快速跳转*/
    function gotoPage() {
        var pagenum = $("#pagenum").val();
        formSubmit(pagenum);
    }
    /*** 表单提交*/
    function formSubmit(page) {
        if (page != undefined) {
            $("#pages").val(page);
        } else {
            $("#pages").val(1);
        }
        $("#sub_form").submit();
    }

    /*** 商品详情*/
    function productVideo(id) {
        window.location.href = "${basePath}/product/productView.html?productId=" + id;
    }
</script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>