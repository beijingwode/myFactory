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
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js?12321415"></script>
<style type="text/css">
	.addimagebutton{height:11px;line-height:11px;width:60px;font-size:12px;}
	.bctxt { border:1px solid #ff6161; }
	.hupimg{margin-top: 2px; }
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
	<form id="sub_form" action="${basePath}/productBrand/save.html" method="post">
	<div class="zz_img"><img src="<%=static_resources %>images/test1.jpg"></div>
	<div class="recruitment_title">上传资质<span class="red">所有资质必须清晰可辨并加盖贵司红章/彩章<em>（即在资质复印件上加盖贵司自己的红章，再扫描或拍照上传）</em></span></div>
    <div class="recruitment_cont">
    	<div class="store col Padd">添加新品牌</div>
        <div class="store_nw">
        	<input type="hidden" name="natural" id="natural" value="<c:if test="${ss.type eq 2 }">0</c:if><c:if test="${ss.type != 2 }">1</c:if>" />
            <span class="nw_name"><i>*</i> 品牌名称：</span>
            <input type="text" class="nw_input" id="name" name="name" value="${productBrand.name }" maxlength="30"/>
            <p class="tips1">请您严格按照商标注册证上的中文或英文填写，两项至少填写一项(都有都要填,标明英文大小写)</p>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> logo：</span>
            
                <div class="nw_text" style="float:left;position:reluate">
                	<img id="logoImg" style='height:40px;width:100px' src="${productBrand.logo}">(100PX*40PX)
                	<input type="hidden" id="logo" name="logo" value="${productBrand.logo}"/>
                	
	                <a id="filePicker0" class="sc" href="javascript:void(0);" style="float:none;">
	                	上传图片
	                </a>
                </div>
            
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 商标类型：</span>
            <div class="nw_radio_b" style="float:left;">
                <input type="radio" id="radio1" onclick="selectshow1(0);" <c:if test="${productBrand.brandType eq 0}">checked="checked"</c:if> name="brandType" class="nw_radio" value="0"><span>TM标</span>
                <input type="radio" id="radio2" onclick="selectshow1(1);" <c:if test="${productBrand.brandType eq 1}">checked="checked"</c:if> name="brandType" class="nw_radio" value="1"><span>R标</span>
            </div>
        </div>
        <div>
        	<div class="store_nw">
                <span class="nw_name"><i>*</i> 商标注册号：</span>
                <input type="text" class="nw_input" id="brandNo" name="brandNo" value="${productBrand.brandNo }"  maxlength="8"/>
            </div>
            <div class="store_nw" id="tmselect" style="display: none">
                <span class="nw_name"><i>*</i> 商标申请日期：</span>
                <input type="text" readonly="readonly" class="nw_input" id="brandcreatedTm" name="brandcreatedTm" value="<fmt:formatDate value="${productBrand.brandcreatedTm}" pattern="yyyy-MM-dd"/>" onClick="WdatePicker()"/>
            </div>
            <div class="store_nw" id="rselects"  style="display: none">
                <span class="nw_name"><i>*</i> 商标注册证有效期：</span>
                <input type="text" readonly="readonly" class="nw_input wid_130" id="begintimeR" name="begintimeR" value="<fmt:formatDate value="${productBrand.begintimeR}" pattern="yyyy-MM-dd"/>" onClick="WdatePicker()"/>
                <span class="zhi">至</span>
                <input type="text" readonly="readonly" class="nw_input wid_130" id="endtimeR" name="endtimeR" value="<fmt:formatDate value="${productBrand.endtimeR}" pattern="yyyy-MM-dd"/>" onClick="WdatePicker()"/>
            </div>
        </div>
        <div class="store_nw">
            <span class="nw_name"><i>*</i> 品牌英文名：</span>
            <input type="text" class="nw_input" id="nameEn" name="nameEn" value="${productBrand.nameEn }" maxlength="30"/>
            <p class="tips1">请您严格按照商标注册证上的中文或英文填写，两项至少填写一项(都有都要填,标明英文大小写)</p>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 进口标致：</span>
            <div class="nw_radio_b">
                <input type="radio" id="radio1i" <c:if test="${not (productBrand.importFlg  eq '1')}">checked="checked"</c:if> name="importFlg" class="nw_radio" value="0"><span>非进口</span>
                <input type="radio" id="radio2i" <c:if test="${productBrand.importFlg eq '1'}">checked="checked"</c:if> name="importFlg" class="nw_radio" value="1"><span>进口</span>
            </div>
        </div>      
        <div class="store_nw">
            <div class="store_nw">
            	<c:if test="${supplierType == 2}"><!-- 0:专营店/1:专卖店/2:旗舰店  -->
                <span class="nw_name"><i>*</i> 授权有效期：</span>
                </c:if>
                <c:if test="${supplierType != 2}">
                <span class="nw_name"><i>&nbsp;</i> 授权有效期：</span>
                </c:if>
                <input type="text" readonly="readonly" class="nw_input wid_130" id="begintimeAuth" name="begintimeAuth" value="<fmt:formatDate value="${productBrand.begintimeAuth}" pattern="yyyy-MM-dd"/>" onClick="WdatePicker()"/>
                <span class="zhi">至</span>
                <input type="text" readonly="readonly" class="nw_input wid_130" id="endtimeAuth" name="endtimeAuth" value="<fmt:formatDate value="${productBrand.endtimeAuth}" pattern="yyyy-MM-dd"/>" onClick="WdatePicker()"/>
            </div>
        </div>      
        <div class="store col pad-top">品牌资质 <span>（特殊格式支持JPG、GIF、JPEG、PNG，图片大小不超过3M）</span></div>
        <div class="nw_text">
        	<div class="nw_text_part1" style="width:1000px;overflow:hidden">
        	<i>*</i> 商标注册证或者商标注册申请受理通知书（<a onclick="showimg('trademark.jpg');" href="javascript:void(0);">查看范本</a>，经营自有品牌和代理品牌均需要上传商标受理通知书，若分别有中文商标和英文商标请一并上传）
        	<a id="filePicker1" class="sc" href="javascript:void(0);" style="float:none;">
            	上传图片
            </a>
            </div>
            
	        	<c:forEach var="item" items="${productBrand.brandurlList}">
	        	
	       		<div class="hupimg" style="float:left;height:170px;margin-right:20px;" ><img style='height:120px;' src="${item}" /><br /><a class="sc" href="javascript:void(0);" onclick="del(this);" style="float:none;margin:5px 0 0 5px;">删除</a><input type="hidden" name="brandurl" value="${item}"/></div>
	         	
	         	</c:forEach>
	        
        </div>
        <br />
        <div class="nw_text">
        	<div  class="nw_text_part1" style="width:1000px;overflow:hidden">
        	<i id="mustI" style="display: none;">*</i> 商标授权书（<a onclick="showimg('authorize.jpg');" href="javascript:void(0);">查看范本</a>，经营代理品牌均需要上传商标授权书，可以上传多图）
        	<a id="filePicker2" class="sc" href="javascript:void(0);" style="float:none;">
            	上传图片
            </a>
            </div>
            <c:if test="${!empty brandImages }">
         	 	<c:forEach var="item" items="${brandImages}">
        		<div class="hupimg" style="float:left;height:200px;margin-right:40px;" ><img style='height:120px;' src="${item.source}" /><br /><a class="sc" href="javascript:void(0);" onclick="del(this);" style="float:none;margin:5px 0 0 5px;">删除</a><input type="hidden" name="brandImg" value="${item.source}"/></div>
          		</c:forEach>
        	</c:if>
        </div>
        <div class="store col pad-top">品牌授权类目</div>
        <div class="store_nw">
            <div class="nw_ch_box" id="lm">
            	<ul>
            	<c:forEach var="item" items="${supplierCategoryLists}">
            		<c:forEach var="item1" items="${item.childrens}">
	            		<li>
	                    	<p class="nw_c_t"><span><input type="checkbox" onclick="checkall(this);" value="${item1.id}">${item1.name}</span></p>
	                    	<div class="nw_c_t">
	                    	<c:forEach var="item3" items="${item1.childrens}">
	                        	<span><input type="checkbox" name="category" value="${item3.id}" onclick="hideclass();" <c:forEach var="item2" items="${brandProducttypeList}"> <c:if test="${item3.id eq item2.categoryId}">checked="checked"</c:if></c:forEach>>${item3.name}</span>
	                		</c:forEach>
	                		</div>
	                		<div class="clear"></div>
	                    </li>
            		</c:forEach>
            	</c:forEach>
                </ul>
            </div>
        </div>  
        <div class="clear"></div>
    </div>
    <div class="recuitment_btn">
    	<input type="hidden" id="categoryIds" name="categoryIds" value=""/>
    	<input type="hidden" id="id" name="id" value="${productBrand.id }"/>
    	<input type="hidden" name="oldId" value="${productBrand.oldId }"/>
    	<input type="hidden" id="supplierId" name="supplierId" value="${productBrand.supplierId }"/>
    	<div class="recuitment_btn01" id="recuitment_btn01" onclick="papa();"><a href="javascript:void(0);">保存</a></div>
        <c:if test="${!empty returnUrl }">
    		<input type="hidden" name="returnUrl" value="${returnUrl}"/>
        	<div class="recuitment_btn01" id="recuitment_btn02" onclick="tosubmit();"><a href="javascript:void(0);">返回</a></div>
        </c:if>
        <c:if test="${empty returnUrl }">
        	<div class="recuitment_btn02" id="recuitment_btn02" onclick="tosubmit();"><a href="javascript:void(0);">返回上传资质</a></div>
        </c:if>
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
<%@ include file="/commons/footer.jsp" %>
<div style="display:none;" id="tmp-upload_container">
 <input type="file" id="uploadFile" name="file" onchange="fileUpload()" style="position:absolute;filter:alpha(opacity=0);width:56px;height:30px;"  />
</div>
<%@ include file="/commons/box.jsp" %>


 
<script type="text/javascript">
var jsBasePath='${basePath}';
var jsSsType='${ss.type}';
var jsSsId='${ss.id}';
var jsReturnUrlv="${returnUrl}";
var supplierType="${supplierType}";
var jsStaticResources='<%=static_resources %>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_supplier_createbrand.js"></script>
</body>