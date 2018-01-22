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
<script type="text/javascript" src="<%=static_resources %>js/create_product.js"></script>
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
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productOncheck')}">
				<li <c:if test="${selltype =='waitcheck'}">class="curr"</c:if>><a href="${basePath}/product/gotoProductlist.html?selltype=waitcheck">待审批商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li <c:if test="${selltype =='createproduct'}">class="curr"</c:if>><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li>
			</c:if>
			<%-- <li><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li> --%>
        </ul>
    </div>
    <!--left end-->
    <!--right begin-->
     
	
    <div class="right">
    	<div class="position">
        	<span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/product/toSelectProducttype.html">商品管理</a><em>></em>
            <a href="javascript:void(0);">商品主图管理</a>
        </div>
        <div class="sale_wrap">
        	<div class="wrap_con">
	           <div class="manage_btn_box" style="margin-top:5px;height:25px;">
	           		<div class="add_new add_new_a ft_rt " style="margin-left:0">
	           			<a href="javascript:void(0);" onclick="uploadDivdisplay(this);">+添加</a>
	           		</div>
	           </div>
	           <div class="tab_box">
	           <form action="${basePath}/supplierSkuImage/fetchSkuImage.html" method="post" id="sub_form">
	        	 <input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
	     	     <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
     	     </form>
	           		<table border="0px" cellpadding="0" cellspacing="0">
	           			<thead>
	           				<tr>
	           					<th>图片组</th>
	           					<th style="width:250px;">描述</th>
	           					<th>图片一</th>
	           					<th>图片二</th>
	           					<th>图片三</th>
	           					<th>图片四</th>
	           					<th>图片五</th>
	           					<th>日期</th>
	           				</tr>
	           			</thead>
	           			<tbody>
       					<c:forEach items="${page.list}" var="img">
							<tr>
	           					<td>${img.id}</td>
	           					<td style="width:250px;">${img.introduce}</td>
	           					<td><img src="${img.image1}" width="40px" height="40px" /></td>
	           					<td><img src="${img.image2}" width="40px" height="40px"  /></td>
	           					<td><img src="${img.image3}" width="40px" height="40px"  /></td>
	           					<td><img src="${img.image4}" width="40px" height="40px"  /></td>
	           					<td><img src="${img.image5}" width="40px" height="40px"  /></td>
	           					<td><fmt:formatDate value="${img.createTime}" pattern="MM月dd日"/></td>
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
<div style="display:none;">
<input type="hidden" id="uploadingJqSelecter"/>
<input type="file" id="uploadFile" name="file" onchange="fileUpload()"/>
</div>

<div class="popup_bg" style="z-index:99;"></div>
 <div class="uploadimg_box" style="z-index: 3335; display: none;">
    <h2>上传图片<span onclick="uploadingClose();"><img src="<%=static_resources %>images/close.gif" width="14" height="14"></span></h2>
    <p style="padding-left:20px;"><b class="out">*</b>商品图片：请上传五张图片。图片尺寸为80*80px，无品牌LOGO和其他网站水印。简易图片为白底。</p>
    <div class="uploadimgstep">
        <div class="tsh">80x80<br>图片将在商品<br>详情图片页中展示</div>
        <div class="jiantou"><img src="<%=static_resources %>images/jiantou.gif" width="32" height="23" alt="jiantou"></div>
        <div class="uploadimg_list">
            <ul><li name="li_0"><div>主图<br>80*80</div></li><li name="li_1"><div>主图<br>80*80</div></li><li name="li_2"><div>主图<br>80*80</div></li><li name="li_3"><div>主图<br>80*80</div></li><li name="li_4"><div>主图<br>80*80</div></li></ul>
        </div>
         
    </div>
   
    
    <div class="uploadimg_btn" style="margin-top:42px;padding:0">
    	<a id="filePicker" href="javascript:void(0);">添加图片</a>
    	<input type="hidden" name="forname" value="1">
    	<span id="errorUploadSpan" style="color:red;margin-left:25px;display:none;"></span>
       	<a href="javascript:void(0);" onclick="submitSkuImg();">确认</a>
        <a onclick="uploadingClose();" href="javascript:void(0);">取消</a>
    </div>
</div>   
<script>
var jsBasePath='${basePath}';
</script>

<script type="text/javascript" src="<%=static_resources %>js/product_product_picasa.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>