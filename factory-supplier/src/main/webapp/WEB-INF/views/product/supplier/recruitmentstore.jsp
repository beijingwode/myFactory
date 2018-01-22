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
<link rel="canonical" href="http://dreamdu.com/">
<title>我的网商家入驻</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/recruitment.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/bottom.css">
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
</head>
<body>
<!--top begin-->
<div id="top">
	<ul class="progress progress-2">
        <li class="t1 current">填写公司信息</li>
        <li class="t2 current">创建店铺</li>
        <li class="t3">选择类目</li>
        <li class="t4">上传资质</li>
        <li class="t5">确认合同</li>
    </ul>	
</div>
<!--top end-->

<!--content begin-->
<div id="content">
	<form  id="sub_form_supplierinfo" action="${basePath}/supplier/savesupplierinfo.html" method="post">
	<input type="hidden" name="oldId" value="${oldId }" />
	<div class="recruitment_title">命名店铺<span>（店铺名称最终以工作人员确定的为准，您的选择会做为重要参考，查看 <a href="#">我的网店铺命名规范</a>）</span></div>
    <div class="recruitment_cont">
		<div class="r_name"><span>店铺类型</span>( <a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>help-center.html?from=zzyq" id="zzyq" target="_blank" >资质要求</a> )</div>
        <div class="manufacturer">
        	<c:choose> 
        	<c:when test="${property == '2'}">
        	<input class="r_radio" type="radio" name="type" <c:if test="${shopSetting.type != 1}">checked="checked"</c:if> value="0" id="rtype0"><label for="rtype0">专营店</label>
        	&nbsp;&nbsp;&nbsp;&nbsp;<input class="r_radio" type="radio" name="type" <c:if test="${shopSetting.type == 1 }">checked="checked"</c:if> value="1" id="rtype1"><label for="rtype1">专卖店</label>
        	</c:when> 
        	<c:otherwise>
        		<input class="r_radio" type="radio" name="type" checked="checked" value="2" id="rtype2"><label for="rtype2">旗舰店</label>
        	</c:otherwise>
        	</c:choose>
        </div>     
        <div class="store mar14" style="height:auto;width:1090px;overflow:hidden;clear:both;">
        	<div style="float:left;line-height:30px;">店铺名称：</div>
        	<p style="width:300px;background:#fff;float:left;border: 1px solid #b5b5b5;box-shadow: inset 0px 1px 4px rgba(0,0,0,0.15); border-radius: 2px;">
        	<input style="width:185px;padding-right:5px;background:none;border:none;box-shadow:none;outline:none;text-align:right" class="public_input s258" ismust="1" type="text" name="shopname" id="shopname" value="${shopSetting.shopname}" maxlength="20">
        	<c:if test="${property != '2'}"><b style="color:#959595;font-weight:500">员工福利旗舰店</b></c:if>
        	<c:if test="${property == '2' && shopSetting.type != '1'}"><b style="color:#959595;font-weight:500" id="shopNameEnd">员工福利专营店</b></c:if>
        	<c:if test="${property == '2' && shopSetting.type == '1'}"><b style="color:#959595;font-weight:500" id="shopNameEnd">员工福利专卖店</b></c:if>
        	</p><span style="line-height:30px;">必填 名称不能超过20个汉字</span></div>  
    </div>
    
    <div class="recruitment_title" style="clear:both">联系信息</div>
    <div class="recruitment_cont">
    
        <div class="store col Padd">店铺负责人</div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 姓名：</span>
            <input type="text" class="nw_input wid_150" ismust="1" id="shopContact" name="shopContact" value="${shopSetting.shopContact }" onChange="autoWrite(this,'cusContact','serContact');" maxlength="8"/>
            <span class="tips"></span>
        </div>
        <div class="store_nw">
            <span class="nw_name"><i>*</i> 公司固定电话：</span>
            <input type="text" class="nw_input wid_62" ismust="1" id="shopTel1" name="shopTel1" value="${shopSetting.shopTel1 }" onChange="autoWrite(this,'cusTel1','serTel1');"  onkeyup="value=value.replace(/[^\d]/g,'');" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="4"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_130" ismust="1" id="shopTel2" name="shopTel2" value="${shopSetting.shopTel2 }" onChange="autoWrite(this,'cusTel2','serTel2');"; onkeyup="value=value.replace(/[^\d]/g,'');" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="8"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_62" id="shopTel3" name="shopTel3" value="${shopSetting.shopTel3 }" onChange="autoWrite(this,'cusTel3','serTel3');" onkeyup="value=value.replace(/[^\d]/g,'');" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="6"/>
            <p class="tips1">公司固定电话格式如：025-66996699-880665，分机号可不填写</p>
        </div>
        <div class="store_nw" id="3344">
        	<span class="nw_name"><i>*</i> 手机：</span>
            <input type="text" class="nw_input wid_150" id="shopPhone" name="shopPhone" value="${shopSetting.shopPhone }" onChange="autoWrite(this,'cusPhone','serPhone');" onkeyup="value=value.replace(/[^\d]/g,'');" onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="11"/>
            <span class="tips" name="error" style="display: none"><i></i>手机号格式不正确</span>
        </div>
        <div class="store_nw" id="3345">
        	<span class="nw_name"><i>*</i> 联系邮箱：</span>
            <input type="email" class="nw_input wid_150" id="shopEmail" value="${shopSetting.shopEmail }" onChange="autoWrite(this,'cusEmail','serEmail');" name="shopEmail" maxlength="30"/>
            <span class="tips" name="error" style="display: none"><i></i>邮箱格式不正确</span>
        </div>
        <div class="store col Padd">售后负责人</div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 姓名：</span>
            <input type="text" class="nw_input wid_150" ismust="1" id="cusContact" value="${shopSetting.cusContact }" name="cusContact" maxlength="8"/>
            <span class="tips"></span>
        </div>
        <div class="store_nw">
            <span class="nw_name"><i>*</i> 公司固定电话：</span>
            <input type="text" class="nw_input wid_62" ismust="1" id="cusTel1" name="cusTel1" value="${shopSetting.cusTel1 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="4"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_130" ismust="1" id="cusTel2" name="cusTel2" value="${shopSetting.cusTel2 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="8"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_62" id="cusTel3" name="cusTel3" value="${shopSetting.cusTel3 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="6"/>
            <p class="tips1">公司固定电话格式如：025-66996699-880665，分机号可不填写</p>
        </div>
        <div class="store_nw" id="3348">
        	<span class="nw_name"><i>*</i> 手机：</span>
            <input type="text" class="nw_input wid_150" id="cusPhone" name="cusPhone" value="${shopSetting.cusPhone }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="11"/>
            <span class="tips" name="error" style="display: none"><i></i>手机号格式不正确</span>
        </div>
        <div class="store_nw" id="3349">
        	<span class="nw_name"><i>*</i> 联系邮箱：</span>
            <input type="email" class="nw_input wid_150" id="cusEmail" name="cusEmail" value="${shopSetting.cusEmail }" maxlength="30"/>
            <span class="tips" name="error" style="display: none"><i></i>邮箱格式不正确</span>
        </div>
        <div class="store col Padd">客服负责人</div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 姓名：</span>
            <input type="text" class="nw_input wid_150" ismust="1" id="serContact" name="serContact" value="${shopSetting.serContact }" maxlength="8"/>
            <span class="tips"></span>
        </div>
        <div class="store_nw">
            <span class="nw_name"><i>*</i> 公司固定电话：</span>
            <input type="text" class="nw_input wid_62" ismust="1" id="serTel1" name="serTel1" value="${shopSetting.serTel1 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))"  maxlength="4"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_130" ismust="1" id="serTel2" name="serTel2" value="${shopSetting.serTel2 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="8"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_62" id="serTel3" name="serTel3" value="${shopSetting.serTel3 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="6"/>
            <p class="tips1">公司固定电话格式如：025-66996699-880665，分机号可不填写</p>
        </div>
        <div class="store_nw" id="3352">
        	<span class="nw_name"><i>*</i> 手机：</span>
            <input type="text" class="nw_input wid_150"  id="serPhone" name="serPhone" value="${shopSetting.serPhone }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="11"/>
            <span class="tips" name="error" style="display: none"><i></i>手机号格式不正确</span>
        </div>
        <div class="store_nw" id="3353">
        	<span class="nw_name"><i>*</i> 联系邮箱：</span>
            <input type="email" class="nw_input wid_150" id="serEmail" name="serEmail" value="${shopSetting.serEmail }" maxlength="30"/>
            <span class="tips" name="error" style="display: none"><i></i>邮箱格式不正确</span>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 客服QQ：</span>
            <input type="text" class="nw_input wid_150" ismust="1" id="qq" name="qq" value="${shopSetting.qq }" maxlength="12" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'')"/>
        </div>
    </div>
    </form>
        
    <div class="recuitment_btn">
    	<p class="h_saving" id="cg" style="display: none">√ 已保存。</p>  
    	<p class="h_failed" id="sb" style="display: none">× 保存失败。</p>
    	<div class="recuitment_btn01" id="recuitment_btn01" onclick="papa(1);"><a href="javascript:void(0);">保存</a></div>
        <div class="recuitment_btn02" id="recuitment_btn02" onclick="papa(2);"><a href="javascript:void(0);">下一步，选择类目</a></div>
        <div class="recuitment_btn03" id="recuitment_btn03" onclick="papa(3);"><a href="javascript:void(0);">上一步</a></div>
    </div>
</div>
<!--content end-->
<%@ include file="/commons/footer.jsp" %>

  <script type="text/javascript">
    var jsHasPre="${hasPre}";
    var jsBasePath='${basePath}';
    var jsProperty="${property}";
    </script>
    <script type="text/javascript" src="<%=static_resources %>js/product_supplier_recruitmentstore.js"></script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>