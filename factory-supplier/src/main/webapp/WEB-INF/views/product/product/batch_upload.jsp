<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>我的网商家中心-图片管理</title>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/picasa.css">
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li <c:if test="${selltype =='createproduct'}">class="curr"</c:if>><a href="${basePath}/product/toSelectProducttype.html">添加新商品</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productOnsell')}">
				<li <c:if test="${selltype =='selling'}">class="curr"</c:if>><a href="${basePath}/product/gotoProductlist.html?selltype=selling">在售商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productWaitSell')}">
				<li <c:if test="${selltype =='waitsell'}">class="curr"</c:if>><a href="${basePath}/product/gotoProductlist.html?selltype=waitsell">待售商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productWaitSell')}">
				<li <c:if test="${selltype =='reject'}">class="curr"</c:if>><a href="${basePath}/product/gotoProductlist.html?selltype=reject">问题商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productOncheck')}">
				<li <c:if test="${selltype =='waitcheck'}">class="curr"</c:if>><a href="${basePath}/product/gotoProductlist.html?selltype=waitcheck">待审批商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li class="curr"><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li>
			</c:if>
			<%-- <li class="curr"><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li> --%>
			<li style="background-color: #f5f5f5">&nbsp;&nbsp;</li>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/product/exchageProduct.html">换领商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/questionnaire/trialProduct.html">试用商品问卷</a></li>
			</c:if>
        </ul>
    </div>
    <!--left end-->
    <!--right begin-->
     
	
    <div class="right">
    	<div class="position">
        	<span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/product/toSelectProducttype.html">商品管理</a><em>></em>
            <a href="javascript:void(0);">商品批量上传</a>
        </div>
        <div class="sale_wrap">
        	<div class="wrap_con">
	           <div class="manage_btn_box" style="margin-top:5px;height:25px;">
	           		
	           		<div class="add_new add_new_a ft_rt " style="margin-left:10px">
	           			<a href="javascript:void(0);" onclick="uploadDivdisplay(this);">+上传zip</a>
	           		</div>
	           		<div class="add_new add_new_a ft_rt " >
	           			<a href="${basePath}/批量上传模板V2.zip" >下载最新模板V2</a>
	           		</div>
	           </div>
	           <div class="tab_box">
	           <form action="${basePath}/supplierProductExcel/fetchProductExcel.html" method="post" id="sub_form">
	        	 <input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
	     	     <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
     	       </form>
	           		<table border="0px" cellpadding="0" cellspacing="0">
	           			<thead>
	           				<tr>
	           					<th>上传日期</th>
	           					<th style="width:200px;">上传文件名</th>
	           					<th>状态</th>
	           					<th>总件数</th>
	           					<th>成功件数</th>
	           					<th style="width:160px;">处理结果（excel文件下载）</th>
	           					
	           				</tr>
	           			</thead>
	           			<tbody>
	           			<c:forEach items="${page.list}" var="upload">
           					<tr>
	           					<td><fmt:formatDate value="${upload.createTime}" pattern="yyyy年MM月dd日"/></td>
	           					<td style="width:200px;">${upload.introduce}</td>
	           					<td><c:if test="${upload.status==-1}">已取消</c:if><c:if test="${upload.status==0||upload.status==null}"><a href="javascript:void(0);" onclick="cancelStatusBox('${upload.id}')">待处理</a></c:if><c:if test="${upload.status==1}">处理中</c:if><c:if test="${upload.status==2}">处理完成</c:if></td>
	           					<td>${upload.totalNumber}</td>
	           					<td>${upload.successNumber}</td>
	           					<td style="width:160px;"><a href="javascript:void(0);" onclick="down('${upload.id}');">${upload.processingResult}</a></td>
	           				</tr>
	           			</c:forEach>
           				</tbody>
	           		</table>
	           		 <!-- 分页位置 start-->
	           		 <wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>
	           </div>
	           
	           
	           
     	    </div>  
     	    
     	    
     	    
     	    
     	    
        </div>
       
    </div>
    <!--right end-->
</div>
<!--content end-->
<div class="popup_bg" style="z-index:99;"></div>
<div class="uploadimg_box" style="z-index: 3335; display: none;width:430px;">
    <h2>批量上传<span onclick="uploadingClose();"><img src="<%=static_resources %>images/close.gif" width="14" height="14"></span></h2>
    
    <div class="alladd-mark-cont" style="margin-left:40px;">
        	<h3>请导入包含商品信息的zip文件</h3>
            <div class="file-box"><span class="choice-file">选择文件&nbsp;&nbsp;</span><input class="file" type="file" id="file" name="file"></div>
            <div class="alladd-mark-box">
            	<span class="span-lt">说明：</span>
                <div class="alladd-mark">
                	<p>请下载批量上传模版并按要求填写zip文件。</p>
                    <p>zip文件大小不能超过30Mb。</p>               
                </div>
                <div class="up_results_suggest" style="display: none;"></div>
            </div>
        </div>
    
    <div class="uploadimg_btn" style="margin-top:42px;">
    	
       	<a href="javascript:void(0);" onclick="submitExcel();">确认</a>
        <a onclick="uploadingClose();" href="javascript:void(0);">取消</a>
    </div>
</div>



<div class="uploadimg_box" style="z-index: 3335; display: none;width:430px;">
    <h2>修改状态<span onclick="uploadingClose();"><img src="<%=static_resources %>images/close.gif" width="14" height="14"></span></h2>
    
    <div class="alladd-mark-cont" style="margin-left:40px;">
        	<h3>您确定要将状态更新为取消？</h3>
        </div>
    <div class="uploadimg_btn" style="margin-top:42px;">
       	<a href="javascript:void(0);">确认</a>
        <a onclick="uploadingClose();" href="javascript:void(0);">取消</a>
    </div>
</div>   
<script>
var jsBasePath="${basePath}";
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_product_batchUpload.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>