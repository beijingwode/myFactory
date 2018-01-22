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
</head>
<style>
.store_cont span{width:110px;float:left;text-align:right;margin-right:10px;}
.store_cont input{margin:0}
.store_cont label{height:30px;line-height:30px;padding:0 5px;color:#434343}
.store_cont #rd0{margin-top:8px;float:left;}
.store_cont #rd1{margin-top:8px;float:left;}
.savingbtn{margin-left:170px;}
</style>
<body>
<!--top begin-->
<%@ include file="/commons/header.jsp" %>
<!--header end-->

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
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li><a href="${basePath}/saleBill/receiptList.html">发票管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('saleBill')}">
                <li class="curr"><a href="${basePath}/saleBill/baseEdit.html">结算设置</a></li>
            </c:if>
        </ul>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
    <form action="${basePath}/saleBill/baseSave.html" method="post">
        <input type="hidden" name="id" value="${sd.id}"/>
        <input type="hidden" name="supplierId" value="${s.id}"/>
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
         	<a href="${basePath}/saleBill/gotoSaleBillList.html">结算管理</a><em>></em>
         	<a href="javascript:void(0);">结算设置</a>
        </div>
        <div class="sort_wrap">
            <div class="sort_title">结算设置</div>
            <div class="store_cont">
                <p><span><i class="out">*</i>收款方：</span>${s.comName}</p>
                <strong>（发票抬头，此为入驻时填写不可以修改）</strong>
            </div>
            <div class="store_cont" style="display:none">
                <p><span><i class="out">*</i>收款方式：</span></p>
                <div style="float:left;margin-right:10px;"><input type="radio" name="accountType" value="0" id="rd0" onclick="changeAccountType('0');" /><label for="rd0">银行转账</label></div>
                <div style="float:left;"><input type="radio" name="accountType" value="1" id="rd1" onclick="changeAccountType('1');" /><label for="rd1">支付宝对公转账</label></div>
            </div>
            <div class="store_cont">
                <p><span><i class="out">*</i>银行账号：</span>${s.bankPeople} @ ${s.bankNum} @ ${s.bankId}&nbsp;&nbsp;${s.bankName}</p>
                <strong>（此为入驻时填写不可以修改）</strong>
            </div>
            <div class="store_cont">
                <p id="pali"><span><i class="out"></i>支付宝对公账号：</span></p>
                <input class="pubilc_input f118" type="text" id="alipayAccount" name="alipayAccount" value="${sd.alipayAccount}" maxlength="50"/>
            </div>
            
             <div class="store_cont">
                <p><span><i class="out">*</i>发票类型：</span></p>
                <input type="radio" id="billType" name="billType" value="0" ${sd.billType == 0?"checked":""}/>普通发票
                <input type="radio" id="billType1" name="billType" value="1" ${sd.billType == 1?"checked":""}/>专用发票
            </div>
            <div class="store_cont">
                <p id="pali"><span><i class="out">*</i>纳税人识别号：</span></p>
                <input class="pubilc_input f119" type="text" id="taxpayerNumber" name="taxpayerNumber" value="${sd.taxpayerNumber}" maxlength="50"/>
            	<strong>（此为普通、专用发票必填项）</strong>
            </div>
            <div class="store_cont">
                <p id="pali"><span><i class="out">*</i>地址、电话：</span></p>
                <input class="pubilc_input f119" type="text" id="addressNumber" name="addressNumber" value="${sd.addressNumber}" maxlength="50"/>
            	<strong>（此为专用发票必填项）</strong>
            </div>
            <div class="store_cont">
                <p id="pali"><span><i class="out">*</i>开户行及电话：</span></p>
                <input class="pubilc_input f119" type="text" id="openingBanNumber" name="openingBanNumber" value="${sd.openingBanNumber}" maxlength="50"/>
            	<strong>（此为专用发票必填项）</strong>
            </div>
            <div class="store_cont">
                <p><span><i class="out">*</i>账期：</span>${sdName}</p>
                <strong>（此为入驻时与平台商定不可以修改，）</strong>
            </div>
            <div class="store_cont">
                <p><span><i class="out">*</i>发票邮寄地址：</span>${s.comState}${s.comCity}${s.comAddress}</p>
                <strong>（同入驻时办公地址，可在首页>商家信息中修改）</strong>
            </div>
            <div class="store_cont">
                <p><span><i class="out">*</i>邮编：</span>${s.comPc}</p>
                <strong>（同入驻时办公地址，可在首页>商家信息中修改）</strong>
            </div>
            <div class="store_cont">
                <p><span><i class="out">*</i>联系人：</span></p>
                <input class="pubilc_input f118" type="text" id="contacts" name="contacts" value="${sd.contacts}" maxlength="10"/>
            </div>
            <div class="store_cont">
                <p><span><i class="out">*</i>电话：</span></p>
                <input class="pubilc_input f118" type="text" id="phone" name="phone" value="${sd.phone}" maxlength="11"/>
            </div>
            

            <div class="savingbtn"><a href="javascript:;" onclick="save();">保存</a></div>
        </div>
    </form>
    </div>
    <!--right end-->
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="/commons/footer.jsp" %>
<!--footer end-->
<%@ include file="/commons/box.jsp" %>
<div style="display:none;" id="tmp-upload_container">
 <input type="file" id="uploadFile" name="file" onchange="fileUpload()" style="position:absolute;filter:alpha(opacity=0);width:56px;height:30px;"  />
</div>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js?1233"></script>
<script type="text/javascript" src="<%=static_resources %>js/menu_min.js"></script>

<script type="text/javascript">
var jsAccountType='${sd.accountType}';
 
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_salebill_edit.js"></script>
</body>
</html>
