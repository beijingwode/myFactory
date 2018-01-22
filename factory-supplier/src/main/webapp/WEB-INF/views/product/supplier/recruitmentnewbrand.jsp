<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/commons/taglibs.jsp" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的网商家入驻</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/recruitment.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/bottom.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/box.css">
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/index.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/scrollbar.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>


<style type="text/css">
	.addimagebutton{height:4px;line-height:4px;width:40px;font-size:12px;}
	.recruitment_cont .nw_text{margin-top:20px;}
	.nw_text img{width:100px;height:100px;display:inline-block;}
	.filepick{margin-left:600px;margin-top:-26px;}
</style>
</head>
<body>
<!--top begin-->
<div id="top">
	<ul class="progress progress-4">
        <li class="t1 current">填写公司信息</li>
        <li class="t2 current">创建店铺</li>
        <li class="t3 current">选择类目</li>
        <li class="t4-1 current">上传资质</li>
        <li class="t5">确认合同</li>
    </ul>	
</div>
<!--top end-->

<!--content begin-->
<div id="content">
	 <form id="sub_form" action="${basePath}/supplier/createAptitude.html" method="post">
	<div class="recruitment_title" style="padding-bottom:10px;"><div style="float:left;">上传资质<span class="red">所有资质必须清晰可辨并加盖贵司红章/彩章<em>（即在资质复印件上加盖贵司自己的红章，再扫描或拍照上传）</em></span></div>
		<div class="recuitment_btn" style="width:500px;margin:0;height:27px;float:right">
	    	<p class="h_saving" id="cg" style="display: none;height:27px;font-size:12px;line-height:27px;">√ 已保存。</p>
	    	<p class="h_failed" id="sb" style="display: none;height:27px;font-size:12px;line-height:27px;">× 保存失败。</p> 
	    	<p class="h_failed" id="ts" style="display: none;height:27px;font-size:12px;line-height:27px;">× 请先选择分类。</p>
	    	<div style="float:right">
	    	<div class="recuitment_btn01" id="recuitment_btn01" onclick="papa('1');" style="height:27px;margin-bottom:0;margin-right:5px;width:50px;"><a href="javascript:void(0);" style="height:27px;font-size:12px;line-height:27px;">保存</a></div>
	        <div class="recuitment_btn02" id="recuitment_btn02" onclick="papa('2');" style="height:27px;margin-bottom:0;margin-right:5px;width:130px;"><a href="javascript:void(0);" style="height:27px;font-size:12px;line-height:27px;">下一步，确认合同</a></div>
	        <div class="recuitment_btn03" id="recuitment_btn03" onclick="papa('3');" style="height:27px;margin:0;width:60px;"><a href="javascript:void(0);" style="height:27px;font-size:12px;line-height:27px;">上一步</a></div>
	    	</div>
	    </div>
	</div>
    <div class="recruitment_cont">
    	<div class="r_name"><span>店铺名称</span>( <a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>help-center.html?from=zzyq" id="zzyq" target="_blank" >资质要求</a> )</div>
        <div class="manufacturer">
        	${ss.shopname}&nbsp;&nbsp;&nbsp;&nbsp;(<c:if test="${empty ss.type || ss.type eq 0 }">专营店</c:if><c:if test="${ss.type eq 1 }">专卖店</c:if><c:if test="${ss.type eq 2 }">旗舰店</c:if>)
        	<br />
        	<br />
        </div>
    	<div class="store col Padd">品牌相关资料</div>
        <div class="s_table">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr class="tr_title">
                <td>序号</td>
                <td>品牌类型</td>
                <td>品牌中文名</td>
                <td>品牌英文名</td>
                <td>品牌资质</td>
                <td>操作</td>
              </tr>
              <c:forEach var="item" items="${productBrandlist}" varStatus="status">
	              <tr>
	                <td>${ status.index + 1}</td>
	                <td><c:if test="${item.natural eq 0}">自有品牌 </c:if><c:if test="${item.natural eq 1}">代理品牌 </c:if></td>
	                <td>${item.name }</td>
	                <td>${item.nameEn }</td>
	                
         	
	                <td><span class="bb"><i></i>
			        	<c:forEach var="itemu" items="${item.brandurlList}">
	                	<a onclick="showimg2('${itemu }');" href="javascript:void(0);">查看</a>
			       		</c:forEach>
	                </span></td>
	                <td>
	                	<span class="bb"><i class="bj"></i><a href="${basePath}/supplier/tocreatebrand.html?id=${item.id}">编辑</a></span>
	                    <span class="bb"><a href="javascript:void(0);" onclick="deletetan(${item.id});" class="scb">删除</a></span>
	                	<input id="deleteid" type="hidden" value="" />
	                </td>
	                <!-- 授权日期 -->
	               <input type="hidden" id="begintimeAuth" name="begintimeAuth" value="${item.begintimeAuth }" />
	               <input type="hidden"  id="endtimeAuth" name="endtimeAuth" value="${item.endtimeAuth }" />
	               <input type="hidden"  id="imageList" name="imageList" value="${item.imageList }" />
	              </tr>
              </c:forEach>
            </table>
			<p class="add_btn" ><a onclick="createbrand();" href="javascript:void(0);">添加品牌</a></p>
        </div>
        <div class="store col pad-top">添加新资质<span>（特殊格式支持JPG、GIF、JPEG、PNG，图片大小不超过3M）</span></div>
        <div class="nw_text " name="aptitudeDiv" style="overflow:hidden;">
        	<div  style="overflow:hidden;">
	        	<div style="width:700px;height:30px;float:left;">
		            <span name="aptitudetypename" style="float:left;">（选填）品牌归属同一实际控制人的证明</span>（<a onclick="showimg('control.jpg');" href="javascript:void(0);" style="float:left;">查看范本</a>，需加盖公司主体红章）
		         </div>
	            <div id="filePicker6" class="sc" style="">上&nbsp;传</div>
	            <c:choose>
	            <c:when test="${empty attachment6 || attachment6==null}">
	            <input type="hidden" zizi="6" name="aptitude_result" value="" />
	            </c:when>
	            <c:otherwise>
	            <c:forEach var="item" items="${attachment6}" varStatus="status">
	            <input type="hidden" zizi="6" name="aptitude_result" value="<c:if test='${item.name!=null&&item.name!=""}'>${item.name}_${item.relatedId}_${item.url}</c:if>" />
	            </c:forEach>
	            </c:otherwise>
	            </c:choose>
	        </div>
	            <br/>
	            <c:forEach var="item" items="${attachment6}" varStatus="status">
	            <c:if test="${!empty item.url }">
	            	<div class="hupimg" style="float:left;height:170px;margin-right:20px;">
	            	  <img   src="${item.url}"  ><br />
	            	  <a class="sc" href="javascript:void(0);" onclick="del(this);" style="float:none;margin:10px 0 0 22px;">删除</a>
	            	</div>
	            </c:if>
             </c:forEach>
        </div>
        <div class="nw_text r_name" name="aptitudeDiv">
        	<div style="overflow:hidden;">
            <div style="float:left;"><span name="aptitudetypename">食品酒水等特殊行业资质</span>（<a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>help-center.html?from=zzyq#tslm" id="zzyq" target="_blank">查看特殊行业资质</a>）  15类</div>
            <div id="filePicker7" class="sc">上&nbsp;传</div>
            <c:choose>
            <c:when test="${empty attachment7 || attachment7==null}">
            <input type="hidden" zizi="7" name="aptitude_result" value="" />
            </c:when>
            <c:otherwise>
            <c:forEach var="item" items="${attachment7}" varStatus="status">
            <input type="hidden" zizi="7" name="aptitude_result" value="<c:if test='${item.name!=null&&item.name!=""}'>${item.name}_${item.relatedId}_${item.url}</c:if>" />
            </c:forEach>
            </c:otherwise>
            </c:choose>
            </div>
            <br/>
            <c:forEach var="item" items="${attachment7}" varStatus="status">
            <c:if test="${!empty item.url }">
            	<div class="hupimg" style="float:left;height:170px;margin-right:20px;">
            	  <img   src="${item.url}"  ><br />
            	  <a class="sc" href="javascript:void(0);" onclick="del(this);" style="float:none;margin:10px 0 0 22px;color:#fff;">删除</a>
            	</div>
            </c:if>
            </c:forEach>
        </div>
    </div>
    
    <div class="recuitment_btn">
    	<input type="hidden" id="flag" name="flag" value="" />
    	<div class="recuitment_btn01" id="recuitment_btn01" onclick="papa(1);"><a href="javascript:void(0);">保存</a></div>
        <div class="recuitment_btn02" id="recuitment_btn02" onclick="papa(2);"><a href="javascript:void(0);">下一步，确认合同</a></div>
        <div class="recuitment_btn03" id="recuitment_btn03" onclick="papa(3);"><a href="javascript:void(0);">上一步</a></div>
    </div>
    </form>
    <div class="shop_popup" id="showimg">
	<div class="popup_title">
    	<span>查看大图</span>
        <label><img src="<%=static_resources %>images/close.gif" onclick="hideimg();" width="14" height="14" alt="close"></label>
    </div>
    <div class="popup_cont">
    	<div class="bigpho"><img id="img3" src="" width="560" height="344" alt="license"></div>
    </div>
	</div>
</div>
<!--content end-->
	<!--删除确认 begin-->
	<div class="popup_bg"></div>   
	<div class="shop_popup" id="shop_popup_delete">
		<div class="popup_title" onclick="delcategory2();">
	    	<span>删除确认</span>
	        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
	    </div>
	    <div class="popup_cont">	
			<div class="popup_txt">请确认是否删除选择类目？</div>
	        <div class="popup_btn">
	            <a href="javascript:void(0);" onclick="delcategory1();">确认</a>
	            <a id="dcansel" href="javascript:void(0);" onclick="delcategory2();">取消</a>
	        </div>
		</div>
	</div>
	<!--删除确认 end-->
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>

<div style="display:none;">
<input type="file" id="uploadFile" name="file" onchange="fileUpload()"/>
</div>
  <script type="text/javascript">
  var jsBasePath='${basePath}';
  var jsStaticResources='<%=static_resources %>';
  var jsApprType='${apprType}';
  var jsSsId='${ss.id}';
  var jsBs="${bs}";
  var supplierType ='${supplierType}'
    </script>
    <script type="text/javascript" src="<%=static_resources %>js/product_supplier_recruitmentnewbrand.js"></script>
</body>