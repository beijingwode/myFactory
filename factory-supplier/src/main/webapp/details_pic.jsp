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
			    <li class="curr"><a href="./details_pic.jsp">商品详情图片</a></li>
			    <li><a href="./batch_upload.jsp">商品批量上传</a></li>
        </ul>
    </div>
    <!--left end-->
    <!--right begin-->
     
	
    <div class="right">
    	<div class="position">
        	<span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/product/toSelectProducttype.html">商品管理</a><em>></em>
            <a href="javascript:void(0);">商品详情图片</a>
        </div>
        <div class="sale_wrap">
        	<div class="wrap_con">
	           
	           <div class="tab_box">
	           	    <div class="date_box">3月1日</div>
	           		<div class="date_pic">
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			
	           			<dl>
	           				<dt><a href="javascript:void(0);"><img src="images/add_img_png200.png" /></a></dt>           				
	           			</dl>
	           		</div>
	           </div>
	           <div class="tab_box">
	           	    <div class="date_box">2月29日</div>
	           		<div class="date_pic">
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           		</div>
	           </div>
	           <div class="tab_box">
	           	    <div class="date_box">2月28日</div>
	           		<div class="date_pic">
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           			<dl>
	           				<dt><img src="<%=static_resources %>images/img_png200.png" /></dt>
	           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" /><input type="button" value="复制" class="inp_btn" /></dd>
	           			</dl>
	           		</div>
	           </div>
	           
	           
	           
     	    </div>  
     	    
     	    
     	    
     	    
     	    
        </div>
       
    </div>
    <!--right end-->
</div>
<!--content end-->

<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>