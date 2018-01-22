<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE HTML>
<html>
<head>
    <title>设置账单日</title>
    <script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
    <%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform" %>
    <%@ include file="/commons/js.jsp" %>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
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
                <li><a href="${basePath}/commissionRefund/gotoCommissionRefundList.html">佣金返还</a></li>
            </c:if>
            <li class="curr"><a href="${basePath}/supplierDuration/toDurationSet.html">设置账单日</a></li>
        </ul>
    </div>
    <!--left end-->

    <!--right begin-->
    <form id="sub_form" action="${basePath}/supplierDuration/durationSet.html" method="post">
        <input type="hidden" id="id" name="id" value="${supplierDuration.id}"/>

        <div class="right">
            <div class="merchant_info">
                <div class="s-bg">
                    <div class="s-lt">
                        <div class="s-li-01"><span>*</span>账单类型：</div>
                        <div class="s-li-02">
                            <select class="s-input" id="saleDurationKey" name="saleDurationKey" disabled="disabled">
                                <option value="">--请选择--</option>
                                <c:forEach var="item" items="${saleDurationParamlist}">
                                    <option value="${item.key}" <c:if test='${supplierDuration.saleDurationKey==item.key}'>selected</c:if>>${item.caption }</option>
                                </c:forEach>

                            </select>
                        </div>
                    </div>
                    <div class="s-lt">
                        <div class="s-li-01"><span>*</span>首次账单生成日：</div>
                        <div class="s-li-02">
                            <input class="pubilc_input f91" type="text" readonly="readonly" id="startTime" name="startTime" value="${startTime}" onClick="WdatePicker()" disabled="disabled"/>
                        </div>
                    </div>
                </div>
                <div class="s-btn" style="display:none;">
                    <a href="javascript:void(0);">确定</a>
                </div>
            </div>
        </div>
    </form>
    <!--right end-->
</div>

<!--content end-->

<script type="text/javascript">

    $(document).ready(function () {
        selectedHeaderLi("jsgl_header");
    });
    //非空验证
    function validatorForm() {
        if ($("#saleDurationKey").val() == '') {
            alert("请选择账单类型");
            return false;
        } else {
            if ($("#startTime").val() == '') {
                alert("首次账单生成日");
                return false;
            }
        }

        return true;
    }

    function toSet() {
        if (validatorForm()) {
            $("#sub_form").submit();
        }
    }
    ;
</script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>