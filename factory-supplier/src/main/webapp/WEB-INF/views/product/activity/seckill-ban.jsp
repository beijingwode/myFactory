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
<title>我的网商家中心-活动列表</title>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js?123123"></script>
<style type="text/css">
	.input{position:relative}
	.hover{background:#ccc}
	.addimagebutton{height:11px;line-height:11px;width:60px;font-size:12px;}
	#filePicker{background: none repeat scroll 0 0 #5d6781;
    border-radius: 4px;height: 30px;color: #fff;text-align: center;margin-right:200px;
    }
    .popup_bg_new{ display:none; position:absolute; top:0%; left:0%; width:100%; height:100%; background-color:#000; z-index:3000; -moz-opacity:0.5; opacity:0.5; filter:alpha(opacity=50);
    	position:fixed!important;
		position:absolute;
		_top:       expression_r(eval_r(document.compatMode &&
            document.compatMode=='CSS1Compat') ?
            documentElement.scrollTop + (document.documentElement.clientHeight-this.offsetHeight)/2 :
            document.body.scrollTop + (document.body.clientHeight - this.clientHeight)/2); 
} 
</style>
</head>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
            <c:if test="${userSession.type != 3 || userSession.hasAuth('promotionList')}">
                <li class="curr"><a href="${basePath}/promotion/list.html">活动申请中心</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('myPromotionList')}">
                <li><a href="${basePath}/promotion/mylist.html">申请中的活动</a></li>
            </c:if>
        </ul>
    </div>
    <!--left end-->
    <form id="sub_form" action="${basePath}/promotion/promotionSkuBanSet.html" method="post">
        <input type="hidden" id="promotionProductId" name="promotionProductId" value="${promotionProduct.id}"/>
        <input type="hidden" id="bigImage" name="bigImage" value="${promotionProduct.bigImage}"/>
        <input type="hidden" id="smallImage" name="smallImage" value="${promotionProduct.smallImage}"/>
    </form>
    <!--right begin-->
    <div class="right">
        <div class="merchant_info">
            <div class="process step_3"></div>
            <!--<div class="s-wrap">
            	<div class="s-cot">
                	<span class="btn1"><a href="#"><img src="${basePath}/images/s-btn1.jpg"></a></span>
                    <select class="s-select" id="pcBanSelect" onchange="pcBanSelectChange();">
                    	<option value="1">使用图片商品作为商品展示图</option>
                    	<option value="2">重新上传图片</option>
                    </select>
      		        <div class="uploadimg_btn" id="pc_div" style="float:left;display:none;">
                    	<a href="javascript:void(0);" id="filePicker" >上传图片</a>
                    </div>
                </div>
                <div class="s-inp"><img id="pc_ban" src="${promotionProduct.bigImage}"></div>
            </div>  --><!--电脑端活动页面设置-->

            <div class="s-wrap">
                <div class="s-cot">
                    <span class="btn1"><a href="#"><img src="<%=static_resources %>images/s-btn2.jpg"></a></span>
                    <select class="s-select" id="mobileBanSelect" onchange="mobileBanSelectChange();">
                        <option value="1">使用图片商品作为商品展示图</option>
                        <option value="2">重新上传图片</option>
                    </select>
                    <div class="uploadimg_btn" id="mobile_div" style="float:left;display:none;">
                        <a href="javascript:void(0);" id="filePicker_mobile">上传图片</a>
                    </div>
                </div>
                <div class="s-inp"><img id="mobile_ban" src="${promotionProduct.smallImage}"></div>
            </div>
            <div class="go-btn3"><a href="javascript:toSet();">下一步</a></div>
        </div>
    </div>
    <!--right end-->
</div>
<div style="display:none;">
    <input type="file" id="uploadFile" name="file" onchange="fileUpload()"/>
    <input type="file" id="uploadFile_mobile" name="file" onchange="fileUpload_mobile()"/>
</div>
<!--content end-->

<script type="text/javascript">
var jsBasePath = '${basePath}';
var proId ='${promotionProduct.id}';
var staticResources = '<%=static_resources %>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_activity_seckillBan.js"></script>

<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>