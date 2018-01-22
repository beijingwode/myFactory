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
    <%@ include file="/commons/js.jsp" %>
    <script type="text/javascript" src="<%=static_resources %>js/jquery.form.min.js"></script>

    <link rel="stylesheet" type="text/css" href="<%=static_resources %>css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=static_resources %>css/invoice_entry.css" />
</head>
<style>
.bctxt {
    border: 1px solid #ff6161;
}
</style>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<%@ include file="/commons/js.jsp" %>
<!--header end-->
<!--content begin-->
<div id="content">
    <div class="mainbody">
    <div class="position">
        <span>您的位置：</span>
        <a href="javascript:void(0);">商家中心</a><em>></em>
        <c:if test="${userSession.type != 3 || userSession.hasAuth('sellList')}">
            <a href="${basePath}/suborder/gotoSelllist.html">订单管理</a><em>></em>
        </c:if>
        <c:if test="${userSession.type != 3 || userSession.hasAuth('sellList')}">
            <a href="${basePath}/suborder/gotoSelllist.html">发票管理</a>
        </c:if>
    </div>
    </div>
    <div class="main_top">
     	<div class="tit tit${tit}"></div>
     </div>
     <div class="main_con">
     	<div class="main_left">
     		<div class="lt_tit">发票申请</div>
     		<dl>
     			<dt>
     				<a href="javascript:;"><img <c:if test="${!empty suborder.suborderitemlist}">src="${suborder.suborderitemlist[0].image}"</c:if> /></a>
     				<c:if test="${!empty suborder.suborderitemlist && suborder.suborderitemlist[0].saleKbn==1}">
                	<div class="picon"><img src="<%=static_resources %>images/picon2.png" /></div>
                	</c:if>
                	<c:if test="${!empty suborder.suborderitemlist && suborder.suborderitemlist[0].saleKbn==2}">
                	<div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
                	</c:if>
                	<c:if test="${!empty suborder.suborderitemlist && suborder.suborderitemlist[0].saleKbn==4}">
                	<div class="picon"><img src="<%=static_resources %>images/picon_z2.png" /></div>
                	</c:if>
                	<c:if test="${!empty suborder.suborderitemlist && suborder.suborderitemlist[0].saleKbn==5}">
                	<div class="picon"><img src="<%=static_resources %>images/picon_t2.png" /></div>
                	</c:if>
     			</dt>
     			<dd><a href="javascript:;"><c:if test="${!empty suborder.suborderitemlist}">${suborder.suborderitemlist[0].productName}</c:if></a></dd>
     			<dd class="dd2"><c:if test="${!empty suborder.suborderitemlist}">${suborder.suborderitemlist[0].itemValues}</c:if></dd>
     			<dd class="dd2">数量：<c:if test="${!empty suborder.suborderitemlist}">${suborder.suborderitemlist[0].number}</c:if></dd>
     		</dl>
     		<ul class="ul1">
     			<li><span>订单编号：</span><p>${suborder.subOrderId}</p></li>
     			<li><span>订单金额：</span><p class="red">￥${suborder.totalProduct-suborder.companyTicket}</p></li>
     			<li><span>快递费用：</span><p>￥${suborder.totalShipping}</p></li>
     			<li><span>收货地址：</span><p>${order.name},${order.mobile},${order.address}</p></li>
     			<li><span>物流状态：</span><p><c:if test="${(suborder.status == null ||suborder.status<3)}">未收到货</c:if><c:if test="${suborder.status >=3}">已收到货</c:if></p></li>
     		</ul>
     		<ul class="ul2">
     			<li><span>申请日期：</span><p><fmt:formatDate pattern="yyyy-MM-dd" value="${invoiceApply.createtime}"/></p></li>
     			<li><span>发票抬头：</span><p>${invoiceApply.title}</p></li>
      		</ul>
     	</div>
     	<form id="addInvoiceForm" action="${basePath}/invoice/addInvoice" method="post">
     	<div class="main_right">
     		<div class="lt_tit">录入发票</div>
     		<p>请将电子发票链接地址，或者上传纸质发票扫描件(拍照)，以便消费者查看</p>
     		<div class="rt_con">     			
	     		<ul>
	     			<li><span>抬头：</span><p>${invoiceApply.title}</p>
	     			<input type="hidden" id="billType" value="${invoiceApply.billType}">
	     			</li>
	     			<div id="divA" style="display:none">
		     			<li><span>发票类型：</span><p>${invoiceApply.billType == 1?"专用发票":"普通发票"}</p></li>
		     			<li><span>纳税人识别号：</span><p>${invoiceApply.taxpayerNumber}</p></li>
	     			</div>
	     			<div id="divA2"  style="display:none">
		     			<li><span>注册地址：</span><p>${invoiceApply.registerAddress}</p></li>
		     			<li><span>注册电话：</span><p>${invoiceApply.registerPhone}</p></li>
		     			<li><span>开户行：</span><p>${invoiceApply.openingBan}</p></li>
		     			<li><span>开户行账号：</span><p>${invoiceApply.openingBanNumber}</p></li>
	     			</div>
	     			<li>
	     				<span>发票类型：</span>
						<select name="type">
                           <option  <c:if test="${invoice.type=='0'}">selected</c:if> value="0">电子增值税普通发票</option>
          				   <option  <c:if test="${invoice.type=='1'}">selected</c:if> value="1">电子增值税专用发票</option>
          				   <option  <c:if test="${invoice.type=='2'}">selected</c:if> value="2">纸质发票</option>
                        </select>
					</li>
	     			<li><span>发票号：</span><input name="invoice" type="text" value="${invoice.invoice }"></li>
	     			<li><span>销售方：</span><input name="seller" type="text" value="${invoice.seller }"></li>
	     			<li><span>价税合计：</span><input name="price" id="priceTotal" type="text" value="${invoice.price }"></li>
	     			<li><span>电子发票链接：</span><input name="electronicInvoice"  type="text" value="${invoice.electronicInvoice }"></li>
	     			<li><span>录入时间：</span><p><fmt:formatDate pattern="yyyy-MM-dd" value="${invoice.createtime }"/></p></li>
	     		</ul>
	     		<div class="up">
	     			<div class="up_top"><span>纸质发票扫描件（拍照）</span><a href="javascript:;"  onclick="toUpload();">上传</a></div>
	     			<div class="up_img"><img id="invoice_img" src="<c:if test="${!empty invoice.paperInvoice }">${invoice.paperInvoice }</c:if><c:if test="${empty invoice.paperInvoice }"><%=static_resources %>images/fapiao_img.png</c:if>"></div>
	     			<div class="del_btn"><a href="javascript:;"  onclick="removeImg();">删除</a></div>
	     		</div>
     		</div>
     		<div class="btn"><a href="javascript:;" style="color:black" onclick="addInvoiceForm()">保存信息</a></div>
     		<div class="rt_bottom">
     			<div class="btm_tit">其他申请信息</div>
     			<div class="btm_con"><span>备注：</span><p>${invoiceApply.note}</p></div>
     		</div>
     		<input name="suborderid" type="hidden" value="${suborder.subOrderId }">
     		<input name="title" type="hidden" value="${invoiceApply.title}">
     		<input name="paperInvoice" type="hidden" id="paperInvoice" value="${invoice.paperInvoice }">
            <input name="id" type="hidden" value="${invoice.id }">
     	</div>
     	</form>
     </div>
</div>
<!--content end-->
<div style="display: none;">
		<input type="file" id="uploadFile" name="file" onchange="fileUpload()" />
	</div>
<%@ include file="/commons/footer.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>
<script  type="text/javascript">
$(function(){
	if($("#billType").val() == 1){
		document.getElementById("divA").style.display="";
		document.getElementById("divA2").style.display="";
	}
	if($("#billType").val() == 0 && $("#billType").val() != ''){
		document.getElementById("divA").style.display="";
	}
	
});
function toUpload() {
	$("#uploadFile").click();
}
function addInvoiceForm() {
	 var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
     var money = $("#priceTotal").val();
     if (!reg.test(money)) {
    	alert("请输入正确的税价合计");
        return false;
     };
	
	$("#addInvoiceForm").submit();
}
function removeImg() {
	$(".up_img img").attr("src","<%=static_resources %>images/fapiao_img.png");
	$("#paperInvoice").val("");
}
var jsSupplierId = '${supplierId}';
var jsBasePath = '${basePath}';
function fileUpload() {
		 var elementIds=["flag"]; //flag为id、name属性名
	    $.ajaxFileUpload({
	        url: jsBasePath+'/upload/pic.json?folder='+jsSupplierId,
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: "uploadFile", // 上传文件的id、name属性名
	        elementIds:elementIds,
	        dataType: 'json', //返回值类型，一般设置为json、application/json	       
	        success: function(data, status){
	        	if(data.success){
	        		var imgsrc = data.data[0].original;
	        		if(imgsrc.indexOf("http://") != 0) {
	        			imgsrc = "http://"+imgsrc;
	        		}
						$("#invoice_img").attr("src",imgsrc);
						$("#paperInvoice").val(imgsrc);
	        	}
	        },
	        error: function(data, status, e){ 
	            showInfoBox(e);
	        }
	    });
}

</script>
</body>
</html>