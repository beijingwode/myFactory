<%@ page contentType="text/html;charset=UTF-8" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, minimal-ui, user-scalable=no">
<meta content="always" name="referrer">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>我的网商家入驻</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/recruitment.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/bottom.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/box.css">
<style type="text/css">
.bctxt { border:1px solid #ff6161; }
.store_nw .sc{display:inline-block;width:56px;height:30px;background:#5d6781;text-align:center;line-height:30px;color:#fff;border-radius:3px;margin-left:10px;cursor:pointer;}
.store_nw img{width:100px;height:100px;display:inline-block}
.store_nw .imgDiv{height:120px;display:inline-block;margin-left:160px}
.store_nw .imgDiv .sc_del{display:inline-block;width:56px;background:#5d6781;text-align:center;color:#fff;border-radius:3px;margin-left:10px;cursor:pointer;}

</style>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/index.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/scrollbar.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/areaFnc.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js?12321415"></script>

</head>

<body>
<!--top begin-->
<div id="top">
	<ul class="progress progress-1">
        <li class="t1 current">填写公司信息</li>
        <li class="t2-1 ">创建店铺</li>
        <li class="t3-1 ">选择类目</li>
        <li class="t4-1 ">上传资质</li>
        <li class="t5">确认合同</li>
    </ul>
</div>
<!--top end-->

<!--content begin-->
<div id="content">
	<form  id="sub_form_supplierinfo" action="${basePath}/supplier/savesupplierinfo.html" method="post">
	<div class="recruitment_title">基本信息</div>
    <div class="recruitment_cont">
        <div class="store_nw">
            <span class="nw_name"><i>*</i> 企业类型：</span>
            <select class="nw_sel wid_160" ismust="1" name="property" onchange="selectShop(this.selectedIndex);">
            	<option value="1" <c:if test="${supplier.property != '2' }">selected</c:if>>品牌商 </option>
            	<option value="2" <c:if test="${supplier.property == 2 }">selected</c:if>>代理商</option>
            </select>
        </div>
        
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 公司名称：</span>
            <input type="text" class="nw_input wid_300" ismust="1" onfocus="changeimg('license.gif');" id="comName" name="comName" value="${supplier.comName }" maxlength="40"/>
            <span class="tips" id="error1" style="display: none"><i></i>公司名称不能为纯数字或者英文</span>
            <span class="tips" id="error3" style="display: none"><i></i>公司名称已被注册</span>
            <p class="tips1">公司名称必须与企业法人营业执照的公司名称完全一致</p>
        </div>
        <div class="store_nw" id="3313">
        	<span class="nw_name"><i>*</i> 营业执照注册号：</span>
            <input type="text" class="nw_input wid_300" ismust="1" onfocus="changeimg('license_number.gif');" id="comRegisternum" name="comRegisternum" value="${supplier.comRegisternum }" onkeyup="value=value.replace(/[^\w\.\/]/ig,'')" maxlength="30"/>
            <span class="tips" name="error" style="display: none"><i></i>营业执照注册号应为2-30位数字</span>
            <span class="tips" id="error4" style="display: none"><i></i>营业执照注册号已被注册</span>
        </div>
        <div class="store_nw">
            <span class="nw_name"><i>*</i> 营业执照所在地：</span>
            <select class="nw_sel wid_160" ismust="1" id="busState" onfocus="changeimg('license_address.gif');" onChange="provinceOnchange(this);"><option value="">请选择省份 </option></select>
            <select class="nw_sel wid_160" ismust="1" id="busCity" onfocus="changeimg('license_address.gif');" onChange="townOnchange(this);"><option value="-1"></option></select>
            <select class="nw_sel wid_160" ismust="1" id="busAddress" onfocus="changeimg('license_address.gif');" onChange="countyOnchange(this);"><option value="-1"></option></select>
            <input type="hidden"  name="busState" value="${supplier.busState }"/>
            <input type="hidden"  name="busCity" value="${supplier.busCity }"/>
            <input type="hidden"  name="busAddress" value="${supplier.busAddress }"/>

        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 公司注册资本：</span>
            <input type="text" class="nw_input wid_150" ismust="1" onfocus="changeimg('license_money.gif');" id="registeredCapital" name="registeredCapital" value="${supplier.registeredCapital }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="7"/>
            <span class="tips Gray">万元</span>
            <span class="tips Gray">
            <select class="nw_sel wid_160" name="registeredCapitalCurrency" ismust="1">
                 <option <c:if test="${supplier.registeredCapitalCurrency eq 1}">selected="selected"</c:if> value="1">人民币 </option>
                 <option <c:if test="${supplier.registeredCapitalCurrency eq 2}">selected="selected"</c:if> value="2">美元 </option>
                 <option <c:if test="${supplier.registeredCapitalCurrency eq 3}">selected="selected"</c:if> value="3">港元 </option>
                 <option <c:if test="${supplier.registeredCapitalCurrency eq 4}">selected="selected"</c:if> value="4">欧元</option>
            </select>
            </span>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 企业人数：</span>
        	<input type="text" class="nw_input wid_150" min="1" max="99999"  ismust="1" id="peopleNumber"  name="peopleNumber" value="${supplier.peopleNumber }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="5"><br />
            <div class="err_hite" style="height:20px;clear:both;padding-left:165px;line-height:20px;color:#959595">请输入具体数值最大不能超过100000，最小不能小于1，不能是区间范围</div>
        </div>
        <div class="store_nw">
            <span class="nw_name"><i>&nbsp;</i> 营业执照经营范围：</span>
            <textarea maxlength="300"  class="nw_textarea" onfocus="changeimg('license_area.gif');" id="busScope" name="busScope" style="resize: none;" onkeyup="thisWordnum(this);">${supplier.busScope }</textarea>
            <p class="tips1 nub" name="wordnum">0/300</p>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 营业执照有效期：</span>
            <input type="text" readonly="readonly" class="nw_input wid_150" ismust="1" id="busBegintime" onfocus="changeimg('license_date.gif');" name="busBegintime" value="<fmt:formatDate value="${supplier.busBegintime}" pattern="yyyy-MM-dd"/>" onClick="WdatePicker()"/>
            <span class="zhi wid_70">到期日期:</span>
            <input type="text" readonly="readonly" class="nw_input wid_150" ismust="1" id="busEndtime" onfocus="changeimg('license_date.gif');" name="busEndtime" value="<fmt:formatDate value="${supplier.busEndtime}" pattern="yyyy-MM-dd"/>"  onClick="WdatePicker()"/>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 企业营业执照副本复印件：</span>（<a onclick="showimg('companylicense.jpg');" href="javascript:void(0);">查看范本</a>，需加盖公司主体红章）
            <div id="busImgUrl" class="sc" >上&nbsp;传</div>
            <input type="hidden"  name="busImgUrl" value="${supplier.busImgUrl}" />
            <c:if test="${!empty supplier.busImgUrl }">
            	<br />
            	<div class='imgDiv'>
            	<img alt="企业营业执照副本" src="${supplier.busImgUrl}"  >
            	<div class='sc_del' onclick="clearImg('busImgUrl');">删除</div>
            	</div>
            </c:if>
        </div>
        <div class="store_nw" id="3321">
        	<span class="nw_name"> 组织机构代码证编号：</span>
            <input type="text" class="nw_input wid_150" onfocus="changeimg('organization_code.gif');" id="orgNum1" name="orgNum1" value="${supplier.orgNum1 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="8"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_150" onfocus="changeimg('organization_code.gif');" id="orgNum2" name="orgNum2" value="${supplier.orgNum2 }" maxlength="1"/>
        	<span class="tips" name="error" style="width:150px; line-height:20px;display: none"><i></i>组织机构代码证编号应为9位数字组合</span>
        	<span class="tips" id="error6" style="width:150px; line-height:20px;display: none"><i></i>组织机构代码证编号已被注册</span>
        </div>
        <div class="store_nw">
        	<span class="nw_name"> 组织机构代码证有效期：</span>
            <input type="text" readonly="readonly" class="nw_input wid_150" onfocus="changeimg('organization_date.gif');" id="orgBegintime" value="<fmt:formatDate value="${supplier.orgBegintime}" pattern="yyyy-MM-dd"/>" name="orgBegintime" onClick="WdatePicker()"/>
            <span class="zhi wid_70">到期日期:</span>
            <input type="text" readonly="readonly" class="nw_input wid_150" onfocus="changeimg('organization_date.gif');" id="orgEndtime" value="<fmt:formatDate value="${supplier.orgEndtime}" pattern="yyyy-MM-dd"/>" name="orgEndtime" onClick="WdatePicker()"/>
        </div>
        <div class="store_nw">
        	<span class="nw_name"> 组织机构代码证复印件：</span>（<a onclick="showimg('organizetion.jpg');" href="javascript:void(0);">查看范本</a>，需加盖公司主体红章）
            <div id="orgImgUrl" class="sc" >上&nbsp;传</div>
            <input type="hidden"  name="orgImgUrl" value="${supplier.orgImgUrl}" />
            <c:if test="${!empty supplier.orgImgUrl }">
            	<br />
            	<div class='imgDiv'>
            		<img alt="组织机构代码证复印件" src="${supplier.orgImgUrl}"  >
            	 	<div class='sc_del' onclick="clearImg('orgImgUrl');">删除</div>
            	</div>
            </c:if>
        </div>
        <div class="store_nw" id="3325">
        	<span class="nw_name">税务登记证编号：</span>
            <input type="text" class="nw_input wid_150" id="taxNum" value="${supplier.taxNum }" name="taxNum" maxlength="18"/>
            <span class="tips" name="error" style="display: none"><i></i>税务登记证编号应为15/18位字符组合（不包含中文）</span>
        	<span class="tips" id="error5" style="display: none"><i></i>税务登记证编号已被注册</span>
        </div>
        <div class="store_nw">
        	<span class="nw_name"> 税务登记证复印件：</span>（<a onclick="showimg('tax.jpg');" href="javascript:void(0);">查看范本</a>，需加盖公司主体红章）
            <div id="taxImgUrl" class="sc" >上&nbsp;传</div>
            <input type="hidden"  name="taxImgUrl" value="${supplier.taxImgUrl}" />
            <c:if test="${!empty supplier.taxImgUrl }">
            	<br />
            	<div class='imgDiv'>
            		<img alt="税务登记证复印件" src="${supplier.taxImgUrl}"  >
            	 	<div class='sc_del' onclick="clearImg('taxImgUrl');">删除</div>
            	</div>
            </c:if>
        </div>
       	<div class="store_nw">
        	<span class="nw_name"><i>*</i> 是否为一般纳税人：</span>
            <div class="nw_radio_b">
                <input type="radio" ismust="1" name="istaxpayer" class="nw_radio" <c:if test="${supplier==null}">checked="checked"</c:if> <c:if test="${supplier.istaxpayer eq 0}">checked="checked"</c:if>  value="0"><span>是</span>
                <input type="radio" ismust="1" name="istaxpayer" class="nw_radio" <c:if test="${supplier.istaxpayer eq 1}">checked="checked"</c:if> value="1"><span>否</span>
                <p class="tips1">此选项会影响到我司给商户开具的发票类型，请如实填写</p>
            </div>
        </div>
        <div class="store_nw">
        	<span class="nw_name"> 一般纳税人资质证明：</span>（<a onclick="showimg('payduty.jpg');" href="javascript:void(0);">查看范本</a>，需加盖公司主体红章）
            <div id="istaxpayerImgUrl" class="sc" >上&nbsp;传</div>
            <input type="hidden"  name="istaxpayerImgUrl" value="${supplier.istaxpayerImgUrl}" />
            <c:if test="${!empty supplier.istaxpayerImgUrl }">
            	<br />
            	<div class='imgDiv'>
            		<img alt="税务登记证复印件" src="${supplier.istaxpayerImgUrl}"  >
            	 	<div class='sc_del' onclick="clearImg('istaxpayerImgUrl');">删除</div>
            	</div>
            </c:if>
        </div>
        <div class="store_nw">
            <span class="nw_name"><i></i> 法人代表归属地：</span>
            <select class="nw_sel wid_160" onchange="changeimg2(this);"  id="corCome" name="corCome"><!-- ismust="1" -->
            <option <c:if test="${supplier.corCome eq 0}">selected="selected"</c:if> value="0">中国大陆 </option>
            <option <c:if test="${supplier.corCome eq 1}">selected="selected"</c:if> value="1">港澳 </option>
            <option <c:if test="${supplier.corCome eq 2}">selected="selected"</c:if> value="2">台湾 </option>
            <option <c:if test="${supplier.corCome eq 3}">selected="selected"</c:if> value="3">外籍 </option>
            </select>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i></i> 法人代表人姓名：</span>
            <input type="text" class="nw_input wid_300"  id="corName" name="corName" value="${supplier.corName }" maxlength="15"/>
            <span class="tips"></span>
        </div>
        <div class="store_nw">
        	<span class="nw_name" id="nw_name"><i></i> 
        	<c:if test="${empty supplier.corCome}">身份证：</c:if>
        	<c:if test="${supplier.corCome eq 0}">身份证：</c:if>
        	<c:if test="${supplier.corCome eq 1}">往来内地通行证号码：</c:if>
        	<c:if test="${supplier.corCome eq 2}">往来内地通行证号码：</c:if>
        	<c:if test="${supplier.corCome eq 3}">护照号码：</c:if>
        	</span>
            <input type="text" class="nw_input wid_300"  id="corNum" name="corNum" value="${supplier.corNum }" maxlength="18"/>
            <span class="tips" id="error2" style="display: none"><i></i>身份证格式不正确</span>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i></i> 身份证正反面复印件：</span>（<a onclick="showimg('identity_s.jpg');" href="javascript:void(0);">查看范本</a>，需加盖公司主体红章）
            <div id="corImgUrl" class="sc" >上&nbsp;传</div>
            <input type="hidden"  name="corImgUrl" value="${supplier.corImgUrl}" />
            <c:if test="${!empty supplier.corImgUrl }">
            	<br />
            	<div class='imgDiv'>
            		<img alt="身份证正反面复印件" src="${supplier.corImgUrl}"  >
            	 	<div class='sc_del' onclick="clearImg('corImgUrl');">删除</div>
            	</div>
            </c:if>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 银行开户名：</span>
            <input type="text" readonly="readonly" class="nw_input wid_300" ismust="1" id="bankPeople" name="bankPeople" value="${supplier.bankPeople }" maxlength="20"/>
            <span class="tips"></span>
            <p class="tips1">您填写的银行账户名称需与公司名称保持一致，若不一致，会影响到我的网给您的开票操作</p>
        </div>
        <div class="store_nw">
            <span class="nw_name"><i>*</i> 开户行：</span>
            <select class="nw_sel wid_160" id="bankBankSelecter" onchange="selectBank(this)" >
            <c:forEach var="item2" items="${banks}">
         		<option value="${item2.name}">${item2.name}</option>
            </c:forEach>
         		<option value="-1">其他</option>
            </select>
            <input type="text" class="nw_input wid_150" ismust="1" id="bankId" name="bankId" value="${supplier.bankId }" maxlength="20" onkeyup="setSelecter(false);" onblur="setSelecter(true);" />
        </div>
        <div class="store_nw">
            <span class="nw_name"><i>*</i> 开户行所在地：</span>
            <select class="nw_sel wid_160" ismust="1" id="bankState" onChange="bankStateOnchange(this);"><option> </option></select>
            <select class="nw_sel wid_160" ismust="1" id="bankCity" onChange="bankCityOnchange(this);"><option value="-1"> </option></select>
            <p class="tips1">如果找不到所在城市，可以选择所在地区或者上级城市。</p>
            <input type="hidden"  name="bankState" value="${supplier.bankState }"/>
            <input type="hidden"  name="bankCity" value="${supplier.bankCity }"/>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 开户行支行名称：</span>
            <input type="text" class="nw_input wid_300" ismust="1" id="bankName" name="bankName" value="${supplier.bankName }" maxlength="25"/>
            <span class="tips"></span>
            <p class="tips1">需填写包括省市的完整支行名称，例如："中国工商银行江苏省南京市中山支行"</p>
        </div>
        <div class="store_nw" id="3335">
        	<span class="nw_name"><i>*</i> 银行账号：</span>
            <input type="text" class="nw_input wid_300" ismust="1" id="bankNum" name="bankNum" value="${supplier.bankNum }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="25"/>
            <span class="tips" name="error" style="display: none"><i></i>银行账号应为6-25位数字</span>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 银行开户许可证：</span>（<a onclick="showimg('bank.jpg');" href="javascript:void(0);">查看范本</a>，需加盖公司主体红章）
            <div id="bankImgUrl" class="sc" >上&nbsp;传</div>
            <input type="hidden"  name="bankImgUrl" value="${supplier.bankImgUrl}" />
            <c:if test="${!empty supplier.bankImgUrl }">
            	<br />
            	<div class='imgDiv'>
            		<img alt="银行开户许可证" src="${supplier.bankImgUrl}"  >
            	 	<div class='sc_del' onclick="clearImg('bankImgUrl');">删除</div>
            	</div>
            </c:if>
        </div>
        <div class="recruiment_pho">
        	<div class="img-right">
            	<span>企业营业执照副本范本：</span>
                <label><a href="javascript:void(0);" onclick="showimg('license.gif');">查看大图</a></label>
            </div>
            <div class="license"><img id="img1" src="<%=static_resources %>images/license.gif" width="382" height="344" alt="license"></div>
            <div class="img-right">
            	<span>法人代表证件样单：</span>
                <label><a href="javascript:void(0);" onclick="showimg2();">查看大图</a></label>
            </div>
            <div class="identity"><img id="img2"
            <c:if test="${empty supplier}">src="<%=static_resources %>images/identity.gif"</c:if>
        	<c:if test="${supplier.corCome eq 0}">src="<%=static_resources %>images/identity.gif"</c:if>
        	<c:if test="${supplier.corCome eq 1}">src="<%=static_resources %>images/info_01.gif"</c:if>
        	<c:if test="${supplier.corCome eq 2}">src="<%=static_resources %>images/info_02.gif"</c:if>
        	<c:if test="${supplier.corCome eq 3}">src="<%=static_resources %>images/info_03.gif"</c:if>
            width="371" height="auto" alt="identity"></div>
        </div>
    </div>
    <div class="recruitment_title">联系信息</div>
    <div class="recruitment_cont">
    	<div class="store col Padd">公司办公信息</div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 公司办公地址：</span>
            <select class="nw_sel wid_160" ismust="1" id="comState" onChange="comStateOnchange(this);"><option> </option></select>
            <select class="nw_sel wid_160" ismust="1" id="comCity" onChange="comCityOnchange(this);"><option value="-1"> </option></select>
            <select class="nw_sel wid_160" ismust="1" id="comAdd" onChange="comAddOnchange(this);"><option value="-1"> </option></select>
            <input type="hidden"  name="comState" value="${supplier.comState }"/>
            <input type="hidden"  name="comCity" value="${supplier.comCity }"/>
            <input type="hidden"  name="comAdd" value="${supplier.comAdd }"/>
            <input type="text" class="nw_input wid_300" ismust="1" id="comAddress" name="comAddress" value="${supplier.comAddress }" maxlength="30"/>
            <span class="tips"></span>
            <p class="tips1">请填写公司实际的办公地址，方便合同、发票等票据的邮寄</p>
        </div>
        <div class="store_nw">
        	<span class="nw_name"><i>*</i> 公司邮编：</span>
            <input type="text" class="nw_input wid_150" ismust="1" id="comPc" name="comPc" value="${supplier.comPc }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="6"/>
            <span class="tips"></span>
        </div>
        <div class="store_nw">
            <span class="nw_name"><i>*</i> 公司固定电话：</span>
            <input type="text" class="nw_input wid_62" ismust="1" id="comTel1" name="comTel1" value="${supplier.comTel1 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="4"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_130" ismust="1" id="comTel2" name="comTel2" value="${supplier.comTel2 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="8"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_62" id="comTel3" name="comTel3" value="${supplier.comTel3 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="6"/>
            <p class="tips1">公司固定电话格式如：025-66996699-880665，分机号可不填写</p>
        </div>
        <div class="store_nw">
            <span class="nw_name"><i>*</i> 传真：</span>
            <input type="text" class="nw_input wid_62" ismust="1" id="comPortraiture1" name="comPortraiture1" value="${supplier.comPortraiture1 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="4"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_130" ismust="1" id="comPortraiture2" name="comPortraiture2" value="${supplier.comPortraiture2 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="8"/>
            <span class="zhi wid_70">——</span>
            <input type="text" class="nw_input wid_62" id="comPortraiture3" name="comPortraiture3" value="${supplier.comPortraiture3 }" onkeyup="value=value.replace(/[^\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d.]/g,''))" maxlength="6"/>
            <p class="tips1">公司固定电话格式如：025-66996699-880665，分机号可不填写</p>
        </div>
    </div>
	
    <c:if test="${not mode eq 'edit' || empty mode}">
    <div class="recuitment_btn">
    	<p class="h_saving" id="cg" style="display: none">√ 已保存。</p>
    	<p class="h_failed" id="sb" style="display: none">× 保存失败。</p>
    	<div class="recuitment_btn01" id="recuitment_btn01" onclick="papa('1');"><a href="javascript:void(0);">保存</a></div>
        <div class="recuitment_btn02" id="recuitment_btn02" onclick="papa('2');"><a href="javascript:void(0);">下一步，创建店铺</a></div>
        <div class="ipt_chx"><input type="checkbox" id="toShop" name="toShop" onchange="changeBtn(this);" value"1" /><label for="toShop">立即开店</label></div>
    </div>
    </c:if>
	
    <c:if test="${mode  eq 'edit'}">
    <div class="recuitment_btn">
    	<p class="h_saving" id="cg" style="display: none">√ 已保存。</p>
    	<p class="h_failed" id="sb" style="display: none">× 保存失败。</p>
    	<div class="recuitment_btn01" id="recuitment_btn01"><a href="${basePath}/user/tosuppliermain.html">返回</a></div>
        <div class="recuitment_btn02" id="recuitment_btn02" onclick="papa('2');"><a href="javascript:void(0);">提交修改</a></div>
    </div>
    </c:if>
    
    </form>
    <div class="shop_popup" id="showimg">
	<div class="popup_title">
    	<span>查看大图</span>
        <label><img src="<%=static_resources %>images/close.gif" onclick="hideimg();" width="14" height="14" alt="close"></label>
    </div>
    <div class="popup_cont">
    	<div class="bigpho"><img id="img3" src="<%=static_resources %>images/license.gif" width="560" height="344" alt="license"></div>
    </div>
	</div>
</div>
<!--content end-->

<script type="text/javascript">
var jsBasePath='${basePath}';
var jsProperty='${supplier.property}';
var jsBusState="${supplier.busState}";
var jsBusCity="${supplier.busCity}";
var jsBusAddress="${supplier.busAddress}";

var jsBankState="${supplier.bankState}";
var jsBankCity="${supplier.bankCity}";

var jsComState="${supplier.comState}";
var jsComCity="${supplier.comCity}";
var jsComAdd="${supplier.comAdd}";

var jsId='${supplier.id}';
var jsMode="${mode}"
var jsStaticResources='<%=static_resources %>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_supplier_recruitmentinfo.js"></script>
<%@ include file="/commons/box.jsp" %>
<%@ include file="/commons/footer.jsp" %>
<div style="display:none;">
<input type="file" id="uploadFile" name="file" onchange="fileUpload()"/>
</div>
</body>
</html>
