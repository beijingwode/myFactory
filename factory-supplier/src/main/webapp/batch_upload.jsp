<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
pageContext.setAttribute("basePath", basePath);
String static_resources = basePath + "static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>我的网商家中心-图片管理</title>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/create_product.js"></script>
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
				<li><a href="./picasa.jsp">商品主图管理</a></li>
			    <li><a href="./details_pic.jsp">商品详情图片</a></li>
			    <li class="curr"><a href="./batch_upload.jsp">商品批量上传</a></li>
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
	           		<div class="add_new add_new_a ft_rt " style="margin-left:0">
	           			<a href="javascript:edit(0);" onclick="uploadDivdisplay(this);">+添加</a>
	           		</div>
	           </div>
	           <div class="tab_box">
	           		<table border="0px" cellpadding="0" cellspacing="0">
	           			<thead>
	           				<tr>
	           					<th>上传日期</th>
	           					<th style="width:200px;">描述</th>
	           					<th>状态</th>
	           					<th>总件数</th>
	           					<th>成功件数</th>
	           					<th style="width:160px;">处理结果（excel文件下载）</th>
	           					
	           				</tr>
	           			</thead>
	           			<tbody>
           					<tr>
	           					<td>2月29日</td>
	           					<td style="width:200px;"><input type="text" value="" style="margin:5px 0" /></td>
	           					<td ><span onclick="uploadDivdisplay1(this);" style="cursor:pointer";>等待处理</span></td>
	           					<td></td>
	           					<td></td>
	           					<td style="width:160px;"></td>
	           					
	           				</tr>
           					
           				</tbody>
	           		</table>
	           </div>
	           
	           
	           
     	    </div>  
     	    
     	    
     	    
     	    
     	    
        </div>
       
    </div>
    <!--right end-->
</div>
<!--content end-->
<div class="popup_bg" style="z-index:99;"></div>
 <div class="uploadimg_box uploadimg_box1" style="z-index: 3335; display: none;width:430px;">
    <h2>批量上传<span onclick="uploadingClose();"><img src="http://localhost:8080/supplier/images/close.gif" width="14" height="14"></span></h2>
    
    <div class="alladd-mark-cont" style="margin-left:40px;">
        	<h3>请导入包含商品信息的Excel表</h3>
            <div class="file-box"><span class="choice-file">选择文件&nbsp;&nbsp;</span><input class="file" type="file" id="file" name="file"></div>
            <div class="alladd-mark-box">
            	<span class="span-lt">说明：</span>
                <div class="alladd-mark">
                	<p>请下载批量上传模版并按要求填写Excel表格。</p>
                    <p>Excel表格文件大小应小于5Mb。</p>               
                </div>
            </div>
        </div>
   
    
    <div class="uploadimg_btn" style="margin-top:42px;">
    	
       	<a href="javascript:void(0);" onclick="submitImgs(this);">确认</a>
        <a onclick="uploadingClose();" href="javascript:void(0);">取消</a>
    </div>
</div>

<div class="uploadimg_box uploadimg_box2" style="z-index: 3335; display: none;width:430px;">
    <h2>上传状态<span onclick="uploadingClose();"><img src="<%=static_resources %>images/close.gif" width="14" height="14"></span></h2>
    
    <div class="alladd-mark-cont" style="margin-left:40px;">
    	<h3>是否取消上传状态</h3>            
    </div>
     
    <div class="uploadimg_btn" style="margin-top:42px;">   	
       	<a href="javascript:void(0);" onclick="submitImgs(this);">确认</a>
        <a onclick="uploadingClose();" href="javascript:void(0);">取消</a>
    </div>
</div>      
<script>
function uploadDivdisplay(){
	$(".popup_bg").show();
	$(".uploadimg_box1").show();	
}
function uploadingClose(){
	$(".popup_bg").hide();
	$(".uploadimg_box1").hide();	
}


function uploadDivdisplay1(){
	$(".popup_bg").show();
	$(".uploadimg_box2").show();	
}
function uploadingClose(){
	$(".popup_bg").hide();
	$(".uploadimg_box2").hide();	
}
</script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>