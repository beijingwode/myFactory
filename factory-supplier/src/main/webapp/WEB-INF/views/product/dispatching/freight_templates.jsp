<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<!DOCTYPE HTML>
<html>
<title>我的网商家中心</title>

<%@ include file="/commons/header.jsp" %>
<%@ include file="/commons/js.jsp" %>

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/freight.css">
<script type="text/javascript">
window.UEDITOR_serverUrl = '${basePath}';
var jsBasePath = '${basePath}';
var jsSupplierId = '${supplier.id}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_dispatching_freightTemplates.js"></script>
<style>

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
    </style>
<!--header end-->

<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
            <c:if test="${userSession.type != 3 || userSession.hasAuth('address')}">
                <li><a href="${basePath}/shippingAddress/todeliver.html">发货地址管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('suborder')}">
                <li><a href="${basePath}/suborder/gotoSuborderlist.html">发货</a></li>
            </c:if>
            
            <li class="curr"><a href="${basePath}/shippingAddress/freight_templates.html?ty=1&isAudit=0">运费模板</a></li>
            <li><a href="${basePath}/suborder/toSupplier_express.html?blank=1">常用快递公司</a></li>
        </ul>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/shippingAddress/todeliver.html">配送管理</a><em>></em>
            <a href="${basePath}/shippingAddress/freight_templates.html">运费模板</a>
        </div>
        <div class="sale_wrap">
			<form action="${basePath}/shippingAddress/freight_templates.html" method="post" id="sub_form">
        	 <input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
     	     <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
        	<div class="wrap_con">
	           <div class="manage_btn_box" style="margin-top:5px;height:25px;">
	                <span style="margin-left:0;float: left;font-size:14px;">
		           		<c:if test="${flag==1}">全场包邮未设置</c:if> 
		           		<c:if test="${flag==2}"><a href="javascript:apprTransfer();"><span style="color: #2b8dff">全场包邮策略</span></a></c:if>
		           		<c:if test="${flag==3}">全场满&nbsp;<em style="font-style:normal;font-size:16px;color:#ff0000">${supplier.shippingFree}</em>&nbsp;元包邮</c:if>     
	                </span>
	           		 <div class="add_new add_new_a ft_rt " style="margin-left:20px;float: left">
	           			<a href="javascript:apprTransfer();">设置</a>
	           		</div>
	           		<div class="add_new add_new_a ft_rt " style="margin-left:0">
	           			<a href="javascript:edit(0);">+新增运费模板</a>
	           		</div>
	           </div>
	           
               <c:forEach items="${page.list}" var="i" varStatus="status">
	           <div class="tem_box">
	           		<div class="tem_tit"><span>${i.name}</span><p>最后编辑时间：<fmt:formatDate value="${i.updateTime}" pattern="yyyy-MM-dd HH:mm"/><a href="javascript:edit(${i.id})">修改</a><a href="javascript:remove(${i.id})">删除</a></p></div>
	           		<div class="tab_box">
		           		<table  border="0px" cellpadding="0" cellspacing="0">
		           			<thead>
		           				<tr>
		           					<th>运送方式</th>
		           					<th style="width:250px;">配送到</th>
		           					<c:if test="${i.countType=='1'}"><th>首件(个)</th><th>运费（元）</th><th>续件(个)</th></c:if>
		           					<c:if test="${i.countType=='2'}"><th>首重(kg)</th><th>运费（元）</th><th>续重(kg)</th></c:if>
		           					<c:if test="${i.countType=='3'}"><th>首体积(m³)</th><th>运费（元）</th><th>续体积(m³)</th></c:if>
		           					<th>运费（元）</th>
		           				</tr>
		           			</thead>
		           			<tbody>
               					<c:forEach items="${i.rulelist}" var="rule" varStatus="status2">		           				
		           				<tr>
		           					<td>快递</td>
		           					<td style="width:250px;">${rule.areasName}</td>
		           					<td>${rule.firstCnt}</td>
		           					<td>${rule.firstPrice}</td>
		           					<td>${rule.plusCnt}</td>
		           					<td>${rule.plusPrice}</td>
		           				</tr>
	           					</c:forEach>
		           				
		           			</tbody>
		           		</table>
		           		<c:if test="${not empty i.freelist}">
		           		<table  border="0px" cellpadding="0" cellspacing="0" style="margin-top:6px;">
		           			<thead>
		           				<tr>
		           					<th>运送方式</th>
		           					<th style="width:250px;">配送到</th>
		           					<th  style="width:523px;">包邮条件</th>
		           				</tr>
		           			</thead>
		           			<tbody>
               					<c:forEach items="${i.freelist}" var="rule">		           				
		           				<tr>
		           					<td>快递</td>
		           					<td style="width:250px;">${rule.areasName}</td>
		           					<td>
		           					<c:if test="${rule.countTypeDes=='2'}">满 &nbsp;${rule.param2}&nbsp;元包邮</c:if>
		           					<c:if test="${rule.countTypeDes=='1'}">
		           						<c:if test="${i.countType=='1'}">满 &nbsp;${rule.param1}&nbsp;件包邮</c:if>
		           						<c:if test="${i.countType=='2'}">在&nbsp;${rule.param1}&nbsp;kg内包邮</c:if>
		           						<c:if test="${i.countType=='3'}">在&nbsp;${rule.param1}&nbsp;m³内包邮</c:if>
									</c:if>
		           					<c:if test="${rule.countTypeDes=='3'}">
		           						<c:if test="${i.countType=='1'}">满 &nbsp;${rule.param1}&nbsp;件,且&nbsp;${rule.param2}&nbsp;元以上 包邮</c:if>
		           						<c:if test="${i.countType=='2'}">在 &nbsp;${rule.param1}&nbsp;kg内,且&nbsp;${rule.param2}&nbsp;元以上 包邮</c:if>
		           						<c:if test="${i.countType=='3'}">在 &nbsp;${rule.param1}&nbsp;m³内,且&nbsp;${rule.param2}&nbsp;元以上 包邮</c:if>
									</c:if>
		           					</td>
		           				</tr>
	           					</c:forEach>
		           			</tbody>
		           		</table>
		           		</c:if>
	           		</div>
	           </div>
	           </c:forEach>
           	  </div><!-- 新增运费模板wrap_con1结束 -->
           	</form>
            <!-- 分页位置 start--> 
            <wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<!--邮寄退回-->
<div class="shop_popup" id="send_return_popup" style="width:500px;">
	<div class="popup_title">
    	<span>包邮设置</span>
        <label onclick="hiddenObjById('send_return_popup');"><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
    </div>
    <div class="popup_cont" id="divExpress"  style="padding:0 30px">
		<div class="cont_box cont_box1" style="padding-top:15px;">
		<input type="radio" class="middle_st" id="rdType3" name="shippingFree" <c:if test="${flag==1}">checked="checked"</c:if> value="-1"  />
		<label>全场包邮未设置</label>	
		</div>
	</div>
   	<div class="popup_cont" id="divExpress"  style="padding:0 30px">
		<div class="cont_box cont_box1" style="padding-top:15px;">
		<input type="radio" class="middle_st" id="rdType1"  name="shippingFree" <c:if test="${flag==3}">checked="checked"</c:if> value="1"/>
		<label>全场商品满&nbsp;&nbsp;</label><input style="width:150px;" name="suppliershippfree" id="amount" type="text" maxlength="6" value="<c:if test="${supplier.shippingFree >-1 && supplier.shippingFree < 8000000}">${supplier.shippingFree}</c:if>" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');"/><label>&nbsp;&nbsp;元，包邮</label>
		<span style="display:none" id="errorMsg"/>
		</div>
	</div>
	<div style="float: left; margin-right: 20px; width: 230px;padding:22px 0 5px 30px">
		<input class="middle_st" id="rdType2" type="radio" name="shippingFree" value="2" <c:if test="${not empty freelist && flag==2}">checked="checked"</c:if>  onclick="toAddfreeTemplate()" />
		<label for="rdFreightType2">全场包邮策略</label><a href="javascript:editFreeTemplate(${supplierTemplateId})" style="color: #2b8dff">&nbsp;&nbsp;<c:if test="${empty freelist}"><span>添加</span></c:if><c:if test="${not empty freelist}"><span>修改</span></c:if></a>
		<div class="tab_box" style="width:450px">
			<table border="0px" cellpadding="0" cellspacing="0" style="width:450px">
			    <c:if test="${not empty freelist}">
				<thead>
					<tr>
						<th style="width:250px">选择地区</th>
						<th style="width:200px">设置包邮条件</th>
					</tr>
				</thead>
				</c:if>
				<tbody>
				<c:forEach items="${freelist}" var="rule">		           				
           				<tr>
           					<td style="width:250px;">${rule.areasName}</td>
           					<td>
           					<c:if test="${rule.countTypeDes=='2'}">满 &nbsp;${rule.param2}&nbsp;元包邮</c:if>
           					<c:if test="${rule.countTypeDes=='1'}">满 &nbsp;${rule.param1}&nbsp;件包邮</c:if>							
           					<c:if test="${rule.countTypeDes=='3'}">满 &nbsp;${rule.param1}&nbsp;件,且&nbsp;${rule.param2}&nbsp;元以上 包邮</c:if>
           					</td>
           				</tr>
       			</c:forEach>
				</tbody>
			</table>	
		</div> 
	</div>
    <div class="clear"></div>
  	<div id="ajaxErrMsg_send_return" class="box_msg" style="color: red;padding-left:152px;"></div>        
        <div class="popup_btn1" style="padding-bottom:20px;">
        <a href="Javascript:void(0);" onclick="return sendReturn();">确认</a>
        <a href="javascript:void(0);" onclick="hiddenObjById('send_return_popup');">取消</a>
        </div>
</div> 

<!--邮寄退回end-->
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/alertMessage.jsp" %>
<script type="text/javascript">
    $(document).ready(function () {
        selectedHeaderLi("psgl_header");
        
		$("#send_return_popup").attr("style","width:500px;z-index:9999999;position:absolute!important;margin-left:-50px!important;");
    });
</script>
<script type="text/javascript">
/*
 * 当全场包邮策略数据为空时，跳转到添加全场包邮策略页面
 */
function toAddfreeTemplate(){
	var freelist = '${len}';
	if(freelist== '0'){
	window.location='${basePath}/shippingAddress/free_template_edit.html';				
	}
}
</script>
</body>
</html>