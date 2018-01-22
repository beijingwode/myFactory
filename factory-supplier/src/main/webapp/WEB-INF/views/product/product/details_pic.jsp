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
            <a href="javascript:void(0);">商品详情图片</a>
        </div>
        <div class="sale_wrap">
        	<div class="wrap_con">
	           <form action="${basePath}/supplierImageResource/fetchImageResource.html" method="post" id="sub_form">
	        	 <input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
	     	     <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
     	       </form>
     	       <c:if test="${empty image}">
     	       		 <div class="tab_box">
     	       			<div class="date_box">${newDate}</div>
						<div class="date_pic">
								<dl>
			           				<dt><img src="<%=static_resources %>images/add_img_png200.png" onclick="addImageResourceBox();"/></dt>
			           			</dl>
						</div>
	     	       </div>	
     	       	</c:if>
     	       <c:forEach items="${image}" var="imgs" varStatus="v">
     	       		<c:if test="${v.index eq 0 && page.pageNum eq 1}">
							<c:if test="${imgs.key eq newDate}">
								<div class="tab_box">
									<div class="date_box">${imgs.key}</div>
									<div class="date_pic">
										<c:forEach items="${imgs.value}" var="img">
											<dl>
												<dt>
													<img src="${img.image}" />
												</dt>
												<dd>
													url&nbsp;&nbsp;<input type="text" class="inp_text"
														value="${img.image}" /><input type="button" value="全选"
														class="inp_btn" />
												</dd>
											</dl>
										</c:forEach>
										<dl>
											<dt>
												<img src="<%=static_resources %>images/add_img_png200.png"
													onclick="addImageResourceBox();" />
											</dt>
										</dl>
									</div>
								</div>
							</c:if>

							<c:if test="${imgs.key ne newDate && page.pageNum eq 1}">
								<div class="tab_box">
									<div class="date_box">${newDate}</div>
									<div class="date_pic">
										<dl>
											<dt>
												<img src="<%=static_resources %>images/add_img_png200.png"
													onclick="addImageResourceBox();" />
											</dt>
										</dl>
									</div>
								</div>
							</c:if>
						</c:if>
						<c:if test="${v.index eq 0 && page.pageNum ne 1}">
						<div class="tab_box">
									<div class="date_box">${imgs.key}</div>
									<div class="date_pic">
										<c:forEach items="${imgs.value}" var="img">
											<dl>
												<dt>
													<img src="${img.image}" />
												</dt>
												<dd>
													url&nbsp;&nbsp;<input type="text" class="inp_text"
														value="${img.image}" /><input type="button" value="全选"
														class="inp_btn" />
												</dd>
											</dl>
										</c:forEach>
									</div>
								</div>
						</c:if>

     	       		<c:if test="${v.index ne 0}">
     	       	    <div class="tab_box">
     	       			<div class="date_box">${imgs.key}</div>
						<div class="date_pic">
							<c:forEach items="${imgs.value}" var="img">
								<dl>
			           				<dt><img src="${img.image}" /></dt>
			           				<dd>url&nbsp;&nbsp;<input type="text" class="inp_text" value="${img.image}" /><input type="button" value="全选" class="inp_btn" /></dd>
			           			</dl>
							</c:forEach>
						</div>
	     	       </div>			
     	       		</c:if>
     	       		
     	       </c:forEach>
	           
	           <!-- 分页位置 start-->
	           <wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>
     	    </div>  
     	    
     	    
     	    
     	    
     	    
        </div>
       
    </div>
    <!--right end-->
    
    
    <div class="popup_bg" style="z-index:99;"></div>
<div class="uploadimg_box" style="z-index: 3335; display: none;width:430px;">
    <h2>上传图片<span onclick="uploadingClose();"><img src="<%=static_resources %>images/close.gif" width="14" height="14"></span></h2>
    
    <div class="alladd-mark-cont" style="margin-left:40px;">
        	<h3>请上传图片</h3>
            <div class="file-box"><span class="choice-file">选择图片&nbsp;&nbsp;</span><input class="file" type="file" id="file" name="file"></div>
            <div class="alladd-mark-box">
            	<span class="span-lt">说明：</span>
                <div class="alladd-mark">
                    <p>上传的图片应小于5Mb。</p>               
                </div>
                <div class="up_results_suggest">上传成功</div>
            </div>
        </div>
   
    
    <div class="uploadimg_btn" style="margin-top:42px;">
    	
       	<a href="javascript:void(0);" onclick="uploadImageResource();">确认</a>
        <a onclick="uploadingClose();" href="javascript:void(0);">取消</a>
    </div>
</div>

</div>
<!--content end-->

<script>
var jsBasePath="${basePath}";
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_product_detailsPic.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>