<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	pageContext.setAttribute("basePath", basePath);
	String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragram" content="no-cache">
<meta http-equiv="cache-control" content="no-cache, must-revalidate">
<meta http-equiv="expires" content="0">
<title>我的商品添加</title>
<%@ include file="/commons/js.jsp"%>
<script type="text/javascript" src="<%=static_resources %>js/jquery.form.min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/map.js"></script>

<%-- <script type="text/javascript" src="<%=static_resources %>resources/layer/layer.min.js"></script> --%>
<script type="text/javascript">
	window.UEDITOR_serverUrl = '${basePath}';
	var jsBasePath = '${basePath}';
	var jsStaticResources = '<%=static_resources %>';
	var jsSupplierId = '${supplierId}';
	var jsCategoryId = '${categoryId}';

	var rowCnt = 0;
	//rowCnt = ${skuCnt};
	var shopId = '${shop.id}';
	var saleKbn = "${product.saleKbn}";
	var empPrice = "${product.empPrice}";
	var limitType = "${limitType}";
	var divLevel = "${product.divLevel}";
	var empCash = "${product.empCash}";
	var empLevel = "${product.empLevel}";
	
</script>
<script type="text/javascript" charset="utf-8" src="<%=static_resources %>resources/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=static_resources %>resources/ueditor1_4_3/ueditor.all.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=static_resources %>resources/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/areaFnc.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/create_product.js?115"></script>
<script type="text/javascript" src="<%=static_resources %>js/create_product_activity.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/cityBox.js"></script>

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/freight.css">

<style type="text/css">
.input {
	position: relative
}

.hover {
	background: #ccc
}

.addimagebutton {
	height: 11px;
	line-height: 11px;
	width: 60px;
	font-size: 12px;
}

#filePicker {
	background: none repeat scroll 0 0 #5d6781;
	border-radius: 4px;
	height: 30px;
	color: #fff;
	text-align: center;
	margin-right: 200px;
}

.popup_bg_new {
	display: none;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: #000;
	z-index: 3000;
	-moz-opacity: 0.5;
	opacity: 0.5;
	filter: alpha(opacity = 50);
	position: fixed !important;
	position: absolute;
	_top: expression_r(eval_r(document.compatMode &&
            document.compatMode == 'CSS1Compat')?
            documentElement.scrollTop+ (document.documentElement.clientHeight-this.offsetHeight)/2:
		
		
            document.body.scrollTop+ (document.body.clientHeight-
		this.clientHeight)/2);
}

.rule_input {
	margin: 0 7px;
}

.proruletab .del_role_btn {
	width: 50px;
	position: absolute;
	top: 10px;
	left: 10px;
}

.proruletab .del_role_btn2 {
	font-size: 12px;
	height: auto;
	position: absolute;
	bottom: -3px;
	right: 20px;
}

.proruletab .del_role_btn2 a {
	color: #d4d3d3;
}

.proruletab .del_role_btn2 a:hover {
	color: #ff4040;
}

.bj_text .add_new {
	color: #2b8dff;
}

#divDiv p em {
	font-style: normal;
	color: #ff4040;
	font-size: 14px;
	padding: 0 3px;
}

#divDivCnt .add_new {
	line-height: 25px;
}

#saleKbn {
	vertical-align: middle;
	margin-top: -2px;
	margin-bottom: 1px;
}

#saleKbn2 {
	vertical-align: middle;
	margin-top: -2px;
	margin-bottom: 1px;
}

#dealLadderTable a{color:#ff4040;}
#dealLadderTable input[type="text"]{margin:5px;height:22px;line-height:22px;padding-left: 6px;font: 12px/22px "Microsoft YaHei";
    color: #434343; }
#dealLadderTable input[type="checkbox"]{margin:8px 5px 0 0;}
#dealLadderTable label{line-height:24px;}
</style>
</head>
<body style="position: relative">
	<%@ include file="/commons/header.jsp"%>

	<!--  ismust  是否必填项目  0：非必填，1：必填-->
	<!--  isnum   是否必须是数字 1：必须是数字-->
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
					<li><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li>
				</c:if>
				<%-- <li><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li> --%>
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
		<div class="right" id="addcontent" style="padding-bottom: 30px;">
			<div class="position">
				<span>您所在的位置：</span> <a href="javascript:void(0);">商家中心</a><em>></em> <a href="${basePath}/product/toSelectProducttype.html">商品管理</a><em>></em> <a href="javascript:void(0);"><c:if test="${product.id==null||product.id==''}">添加商品-信息</c:if> <c:if test="${product.id!=null&&product.id!=''}">编辑商品-信息</c:if></a>
			</div>

			<form autocomplete="off" id="sub_form" action="${basePath}/product/createProduct.html" method="post">
				<!-- <input type="hidden" id="status" name="status" value="0" /> -->
				<input type="hidden" id="status" name="status" value="${product.status}" /> <input type="hidden" id="categoryId" name="categoryId" value="${categoryId}" /> <input type="hidden" id="productid" name="productid" value="${product.id}" /> <input type="hidden" id="apprid" name="apprid" value="${apprid}" /> <input type="hidden" id="selltype" name="selltype" value="${selltype}" /> <input type="hidden" id="savestate" name="savestate" value="0" /> <input type="hidden" id="productcopy" name="productcopy" value="${productcopy}" />
				<!-- 增加判断 是否新版商品模板的判断 -->
				<input type="hidden" id="newCarriage" name="newCarriage" value="
				<c:if test='${ not empty product.carriage}'>${product.carriage}</c:if>
				<c:if test='${ empty product.carriage}'>1</c:if>
				" /> <input type="hidden" id="newShippingTemplateId" name="newShippingTemplateId" value="${shippingTemplateId}">
				<textarea id="introduction" name="introduction" style="display: none;">${product.introduction}</textarea>
				<div class="sort_wrap">
					<div class="add_info_wrap">
						<div class="add_title">
							基本内容编辑<span>（<b class="out">*</b>表示必填）
							</span>
							<c:if test="${displayOrhide==1}">
								<div class="add_new" style="float: right; margin-right: 20px" onclick="popupDeleteAppr(${proid})";>
									<a href="javascript:void(0);">还原在售信息</a>
								</div>
								<div class="add_new" style="float: right; margin-right: 30px">
									<span><a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>${proid}.html" target="_blank" style="color: #2b8dff">查看在售信息</a></span>
								</div>
								<div style="float: right; margin-right: 50px">
									<span>已于<fmt:formatDate value="${product.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />修改
									</span>
								</div>
							</c:if>
						</div>
						<div class="add_info">
							<div class="add_model">
								<div class="name">店铺：</div>
								<input type="hidden" name="shopId" id="shopId" value="${shop.id}"> <span>&nbsp;${shop.shopname}&nbsp;&nbsp;&nbsp;&nbsp;(<c:if test="${shop.type eq 0 }">专营店</c:if> <c:if test="${shop.type eq 1 }">专卖店</c:if>
									<c:if test="${shop.type eq 2 }">旗舰店</c:if>)
								</span>
								<c:if test="${shop.id ==null}">
									<div class="add_new" style="float: right; margin-right: 420px">
										<a href="${basePath}/product/toSelectProducttype.html?productId=${product.id}&shopId=${shop.id}&brandId=${brandId}" style="color: #2b8dff">修改</a>
									</div>
								</c:if>
							</div>
							<div class="add_model">
								<div class="name">
									<b class="out">*</b>标题：
								</div>
								<c:choose>
									<c:when test="${productcopy=='copy' }">
										<div style="width: 560px; float: left">
											<input class="pubilc_input f538" name="fullName" id="fullName" type="text" ismust="1" typename="input" value="${product.fullName}(复制)" onblur="thisWordnum(this);" maxLength="100" placeholder="" />
											<p style="padding-left: 14px; line-height: 30px; color: #acadad;">品牌名称+空格+商品名称+商品简短描述+商品型号</p>
										</div>
									</c:when>
									<c:otherwise>
										<div style="width: 560px; float: left">
											<input class="pubilc_input f538" name="fullName" id="fullName" type="text" ismust="1" typename="input" value="${product.fullName}" onblur="thisWordnum(this);" maxLength="100" placeholder="" />
											<p style="padding-left: 14px; line-height: 30px; color: #acadad;">品牌名称+空格+商品名称+商品简短描述+商品型号</p>
										</div>
									</c:otherwise>
								</c:choose>
								<span>还能输入<em name="wordnum">100</em>字
								</span>
							</div>
							<div class="add_model" style="margin-top: 10px;">
								<div class="name">
									<b class="out">*</b>副标题：
								</div>
								<div style="width: 560px; float: left">
									<input class="pubilc_input f538" name="name" id="name" type="text" ismust="1" typename="input" value="${product.name}" onblur="thisWordnum(this);" maxLength="30" placeholder="" />
									<p style="padding-left: 14px; line-height: 30px; color: #acadad;">品牌名称+商品名称</p>
								</div>
								<span>还能输入<em name="wordnum">30</em>字
								</span>
							</div>
							<div class="add_model" style="margin-top: 10px;">
								<div class="name">商品广告词：</div>
								<input class="pubilc_input f538" name="promotion" id="promotion" type="text" typename="input" value="${product.promotion}" onblur="thisWordnum(this);" maxLength="100" /> <span>还能输入<em name="wordnum">100</em>字
								</span>
							</div>
							<div class="add_model">
								<div class="name">
									<b class="out">*</b>商品品牌：
								</div>
								<select id="brandId" name="brandId" ismust="1" typename="select" class="select_input f158">
									<option value="-1">--请选择--</option>
									<c:forEach var="item" items="${brandList}">
										<option value="${item.id}" <c:if test="${item.id eq brandId}">selected="selected"</c:if>>${item.name}</option>
									</c:forEach>
								</select>
							</div>

							<div class="add_model" style="float: left">
								<div class="name">
									<b class="out">*</b>商品类别：
								</div>
								&nbsp;&nbsp;${name1}<em>></em>${name2}<em>></em>${name3}
								<div class="add_new" style="float: right; margin-right: 420px">
									<a href="${basePath}/product/toSelectProducttype.html?productId=${product.id}&shopId=${shop.id}&brandId=${brandId}" style="color: #2b8dff">修改</a>
								</div>
							</div>

							<div class="add_after" style="margin-left: 50px;">
								<div class="mark">此部分内容不在页面显示</div>
								<div class="hidden_apart">
									<div class="apart_left">
										<div class="add_model">
											<div class="a_name">
												<b class="out"></b>商品型号：
											</div>
											<input class="pubilc_input f158" type="text" name="marque" typename="input" id="marque" value="${product.marque}" maxLength="20" />
										</div>
									</div>
									<div class="apart_right">
										<div class="add_model">
											<div class="a_name">
												<b class="out"></b>条形码：
											</div>
											<input class="pubilc_input f158" type="text" name="barcode" id="barcode" typename="input" value="${product.barcode}" maxLength="20" onkeyup="value=value.replace(/[^\d|a-z|A-Z\-]/g,'')" />
										</div>
									</div>
								</div>
								<div class="add_model">
									<input id="provincetemp" name="provincetemp" type="hidden" value="${product.province}" /> <input id="towntemp" name="towntemp" type="hidden" value="${product.town}" /> <input id="countytemp" name="countytemp" type="hidden" value="${product.county}" />
									<div class="a_name">商品产地：</div>
									<select id="province" name="province" onChange="provinceOnchange(this);" class="select_input f158">
										<option value="-1">--请选择--</option>
									</select> <select id="town" name="town" onChange="townOnchange(this);" class="select_input f158">
										<option value="-1">--请选择--</option>
									</select> <select id="county" name="county" onChange="countyOnchange(this);" class="select_input f158">
										<option value="-1">--请选择--</option>
									</select> <input type="hidden" id="produceaddress" name="produceaddress" value="" /><br />
								</div>
							</div>
						</div>

						<c:if test='${(not empty attributelist) }'>
							<div class="add_title">
								产品属性<span>（<b class="out">*</b>表示必填）
								</span>
							</div>
							<div class="add_info">
								<c:forEach var="item" items="${attributelist}">
									<div class="add_model">
										<div class="name">
											<c:if test="${item.ismust==1}">
												<b class="out">*</b>
											</c:if>${item.name}:
										</div>
										<c:if test="${item.inputtype==1}">
											<select class="select_input f226" onchange="attributeChanage(this);" typename="select" <c:if test="${item.ismust==1}">ismust="1"</c:if>>
												<option value='-1'>--请选择--</option>
												<c:forEach var="obj" items="${item.optionlist}">
													<option value="${obj.name}">${obj.name}</option>
												</c:forEach>
											</select>
										</c:if>
										<c:if test="${item.inputtype==2}">
											<input class="pubilc_input f218" onchange="attributeChanage(this);" type="text" value="" <c:if test="${item.ismust==1}">ismust="1"</c:if> typename="input" maxLength="20">
										</c:if>
										<c:if test="${item.inputtype==3}">
											<div class="option" typename="checkboxdiv" <c:if test="${item.ismust==1}">ismust="1"</c:if>>
												<c:forEach var="obj" items="${item.optionlist}">
													<div class="size">
														<input class="check_input" id="${obj.name}" onclick="attributeChanage(this);" typename="checkbox" name="${item.id}" type="checkbox" /><span>${obj.name}</span>
													</div>
												</c:forEach>
											</div>
										</c:if>
										<input type="hidden" name="attribute_result" id="${item.id}" value="<c:if test='${item.selectedValue!=null&&item.selectedValue!=""}'>${item.id}_${item.inputtype}_${item.selectedValue}</c:if>" />
										<!--id_type_values-->
									</div>
								</c:forEach>
							</div>
						</c:if>

						<c:if test='${(not empty parametergrouplist) }'>
							<div class="add_title">
								产品参数<span>（<b class="out">*</b>表示必填）
								</span>
							</div>
							<div class="add_info">
								<div class="parameter_apart">
									<c:forEach var="item" items="${parametergrouplist}" varStatus="status">
										<c:if test="${status.first||status.count%2!=0}">
											<div class="parameter_one">
										</c:if>
										<div class="parameter_model">
											<div class="a_name">
												<c:if test="${item.ismust==1}">
													<b class="out">*</b>
												</c:if>${item.name}:</div>

											<c:if test="${item.inputtype==1}">
												<select class="select_input f226" onchange="parameterChanage(this);" typename="select" <c:if test="${item.ismust==1}">ismust="1"</c:if>>
													<option value="-1">--请选择--</option>
													<c:forEach var="obj" items="${item.parameterlist}">
														<option value="${obj.name}">${obj.name}</option>
													</c:forEach>
												</select>
											</c:if>

											<c:if test="${item.inputtype==2}">
												<input class="pubilc_input f218" onchange="parameterChanage(this);" type="text" value="" typename="input" maxLength="20" <c:if test="${item.ismust==1}">ismust="1"</c:if>>
											</c:if>

											<c:if test="${item.inputtype==3}">
												<div class="option" typename="checkboxdiv" <c:if test="${item.ismust==1}">ismust="1"</c:if>>
													<c:forEach var="obj" items="${item.parameterlist}">
														<div class="size">
															<input class="check_input" id="${obj.name}" typename="checkbox" onclick="parameterChanage(this);" name="${item.id}" type="checkbox" /><span>${obj.name}</span>
														</div>
													</c:forEach>
												</div>
											</c:if>

											<input type="hidden" name="parameter_result" id="${item.id}" value="<c:if test='${item.selectedValue!=null&&item.selectedValue!=""}'>${item.id}_${item.inputtype}_${item.selectedValue}</c:if>" /><br />
											<!--id_type_values-->
										</div>
										<c:if test="${status.first||status.count%2!=0}">
								</div>
						</c:if>
						</c:forEach>
					</div>
				</div>
				</c:if>

				<div class="add_title">
					装箱清单<span>（商品出厂时，一个包装盒内，所有物件名称及相应数量，包括说明书、保修卡、配件及包装盒内的物品等等）</span>
				</div>
				<div class="add_info">
					<div class="parameter_apart" id="detaillistDiv">
						<c:forEach var="item" items="${detaillist}">
							<div class="parameter_one">
								<div class="parameter_model">
									<div class="a_name">名称：</div>
									<input class="pubilc_input f218" name="name" type="text" typename="input" value="${item.name}" onchange="detaillistNameChange(this)" maxLength="20" />
								</div>
								<div class="parameter_model">
									<div class="num">数量：</div>
									<input class="pubilc_input f88" type="text" name="num" isnum="1" typename="input" value="${item.num}" onchange="detaillistNumChange(this)" maxLength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" />
									<div class="a_delete" onclick="delthisparent(this);">
										<a href="javascript:void(0);">删除</a>
									</div>
								</div>
								<input type="hidden" name="detaillist_result" value="<c:if test="${item.name!=null&&item.name!=''&&item.num!=null&&item.num!=''}">${item.name}_${item.num}</c:if>" />
							</div>
						</c:forEach>
						<c:if test="${detaillist== null || fn:length(detaillist) == 0}">
							<div class="parameter_one">
								<div class="parameter_model">
									<div class="a_name">名称：</div>
									<input class="pubilc_input f218" name="name" type="text" typename="input" value="" onchange="detaillistNameChange(this)" maxLength="20" />
								</div>
								<div class="parameter_model">
									<div class="num">数量：</div>
									<input class="pubilc_input f88" type="text" name="num" isnum="1" typename="input" value="" onchange="detaillistNumChange(this)" maxLength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" />
									<div class="a_delete" onclick="delthisparent(this);">
										<a href="javascript:void(0);">删除</a>
									</div>
								</div>
								<input type="hidden" name="detaillist_result" value="" />
							</div>
						</c:if>
					</div>
					<div class="add_item">
						<div class="add_item_btn" onclick="newDetaillist();">
							<a href="javascript:void(0);">增加项目</a>
						</div>
						<p>
							最多<span>20</span>项
						</p>
					</div>
				</div>

				<div class="add_title">
					运费<span>（<b class="out">*</b>表示必填）
					</span>
				</div>
				<div class="add_info">
					<div class="add_model">
						<input id="sendProvincetemp" name="sendProvincetemp" type="hidden" value="${product.sendProvince}" /> <input id="sendTowntemp" name="sendTowntemp" type="hidden" value="${product.sendTown}" />
						<div class="name">
							<b class="out">*</b>发货地：
						</div>
						<select id="sendProvince" name="sendProvince" ismust="1" typename="select" onChange="sendOnchange(this,'sendTown');" class="select_input f158">
							<option value="-1">--请选择--</option>
						</select> <select id="sendTown" name="sendTown" ismust="1" typename="select" onChange="sendOnchange(this,'sendCounty');" class="select_input f158">
							<option value="-1">--请选择--</option>
						</select> <input type="hidden" id="sendAddress" name="sendAddress" value="" /><br />
					</div>
					<div class="add_model" style="margin-bottom: 15px;">
						<div class="name">
							运费：
						</div>
						<div class="color_choose1" style="float: left; width: 600px; margin: 0 0 0 12px;">
							<input type="checkbox" onclick="exemptionFromPostageClick()" id="rdFreightTypeCheckBox" 
							<c:if test="${(( not empty product.carriage) && product.carriage=='0.00')}"> checked="checked"</c:if>
							> <label for="rdFreightType0">卖家承担运费（包邮）</label> <br />
							<div style="width:800px;height:30px;">
							<p style="color: #2b8dff;width:80px;float:left;">商家运费模板</p>
									
									<div class="add_new" style="float: left; margin-left: 20px">
										<a target="_blank" href="${basePath}/shippingAddress/freight_templates.html?ty=1" style="color: #2b8dff">设置</a>
									</div>
<a href="javascript:ajaxGetAllShippingTemplates();" title="点击刷新运费模板" style="height: 28px; width: 28px; margin-left: 5px; display: block; float: left;"><img src="<%=static_resources %>images/reload.png" /></a>
							
							</div>
							<div id="ajaxGetAllShippingTemplates">
							<!-- 判断新增加商品页面 -->
							<c:if test="${not empty shippingTemplate }">
								<div style="width: 600px">
									
									<div class="tem_tit" style="width: auto">
										
										最后编辑时间：
										<fmt:formatDate value="${shippingTemplate.updateTime}" pattern="yyyy-MM-dd HH:mm" />
										</p>
									</div>
									<div class="tab_box" style="width: auto">
										<table style="width: auto" border="0px" cellpadding="0" cellspacing="0">
											<thead>
												<tr>
													<th style="width: 250px;">销售区域</th>
													<th>首件(个)</th>
													<th>运费（元）</th>
													<th>续件(个)</th>
													<th>运费（元）</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${shippingTemplate.rulelist}" var="rule" varStatus="status2">
													<tr>
														<td style="width: 250px;">${rule.areasName}</td>
														<td>${rule.firstCnt}</td>
														<td>${rule.firstPrice}</td>
														<td>${rule.plusCnt}</td>
														<td>${rule.plusPrice}</td>
													</tr>
												</c:forEach>

											</tbody>
										</table>
										<c:if test="${not empty shippingTemplate.freelist}">
											<table border="0px" cellpadding="0" cellspacing="0" style="width: auto; margin-top: 6px;">
												<thead>
													<tr>
														<th style="width: 250px;">销售区域</th>
														<th style="width: 350px;">包邮条件</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${shippingTemplate.freelist}" var="rule">
														<tr>
															<td style="width: 250px;">${rule.areasName}</td>
															<td style="width: 350px;"><c:if test="${rule.countTypeDes=='2'}">满 &nbsp;${rule.param2}&nbsp;元包邮</c:if> <c:if test="${rule.countTypeDes=='1'}">
																	<c:if test="${shippingTemplate.countType=='1'}">满 &nbsp;${rule.param1}&nbsp;件包邮</c:if>
																</c:if> <c:if test="${rule.countTypeDes=='3'}">
																	<c:if test="${shippingTemplate.countType=='1'}">满 &nbsp;${rule.param1}&nbsp;件,且&nbsp;${rule.param2}&nbsp;元以上 包邮</c:if>
																</c:if></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>

										</c:if>
										</div>
										</div>
							</c:if>
						</div>
						<!-- 运费模板结束 -->
						</div>
					</div>
		<div class="tg" style="margin-left: 50px; margin-top: 15px;">
			<a href="javascript:void(0);" style="color: #2b8dff;">+运费相关信息</a>
		</div>
		<div class="add_after add_after_tg" style="margin-left: 50px; margin-top: 15px; display: none">
			<div class="mark">重量和体积</div>
			<div class="hidden_apart">
				<div class="apart_left">
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品毛重：
						</div>
						<input class="pubilc_input f158" type="text" name="roughWeight" isnum="1" typename="input" id="roughWeight" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,'$1$2.$3');" value="${product.roughWeight}" maxLength="10" /><span>kg</span>
					</div>
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品长（含外包装）：
						</div>
						<input class="pubilc_input f158" type="text" name="length" id="length" isnum="1" typename="input" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');buildBulk();" value="${product.length}" maxLength="10" /><span>mm</span>
					</div>
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品高（含外包装）：
						</div>
						<input class="pubilc_input f158" type="text" name="height" id="height" isnum="1" typename="input" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');buildBulk();" value="${product.height}" maxLength="10" /><span>mm</span>
					</div>
				</div>
				<div class="apart_right">
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品净重：
						</div>
						<input class="pubilc_input f158" type="text" name="netWeight" id="netWeight" isnum="1" typename="input" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,'$1$2.$3');" value="${product.netWeight}" maxLength="10" /><span>kg</span>
					</div>
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品宽（含外包装）：
						</div>
						<input class="pubilc_input f158" type="text" name="width" id="width" isnum="1" typename="input" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');buildBulk();" value="${product.width}" maxLength="10" /><span>mm</span>
					</div>
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品体积（含外包装）：
						</div>
						<input class="pubilc_input f158" type="text" name="bulk" id="bulk" isnum="1" typename="input" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');" value="${product.bulk}" maxLength="10" /><span>mm3</span>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="add_title">
		产品规格<span>（可自定义）</span><span style="color:#ff6161;<c:if test='${product.isMarketable!=1}'>display:none</c:if>">商品修改,平台审核通过前，仍按原商品价格销售。</span>
	</div>
	<div class="add_info">
		<%
						//---------------------标准规格 start---------------------
					%>
		<div style="margin-left: 20px;">
			<input id="rdType0" type="radio" name="specificationType" value="0" onclick="return specificationTypeChangge(this);" <c:if test="${specificationType==null || specificationType=='0'}">checked="checked"</c:if> /> <label for="rdType0">标准规格</label>
		</div>

		<c:forEach var="item" items="${specificationlist}">
			<div class="color_choose" id="specificationDiv_${item.id}" name="${item.name}" <c:if test="${specificationType=='1' || specificationType=='2'}">style="display: none;"</c:if>>
				<div class="c_title">
					<b class="out">*</b>${item.name}:</div>
				<div class="c_option">
					<c:forEach var="obj" items="${item.valuelist}">
						<div class="colorlie">
							<input id="${obj.name}" class="check_input" type="checkbox" value="${obj.name}" name="${item.id}" onclick="specificationChanage(this);"> <span name="editSpan" value="${obj.name}" contenteditable="true" onblur="specificationChanage($(this).prev());">${obj.name}</span>
						</div>
					</c:forEach>
					<input type="hidden" id="${item.id}" ismust="0" name="specification_result" value="${item.id}_${item.selectedValue}" /><br />
				</div>
			</div>
		</c:forEach>

		<div class="color_choose" id="role_btn" <c:if test="${specificationType=='1' || specificationType=='2'}">style="display: none;"</c:if>>
			<div class="role_btn" onclick="createspecificationlist(this,'');">
				<a href="javascript:void(0);">生成产品规格</a>
			</div>
		</div>
		<%
						//---------------------标准规格end--------------------- 
						
						//---------------------自定义规格 start---------------------
					%>
		<div style="margin-left: 20px;">
			<input id="rdType2" type="radio" name="specificationType" value="2" onclick="return specificationTypeChangge(this);" <c:if test="${specificationType=='2'}">checked="checked"</c:if> /> <label for="rdType2">自定义规格</label>
		</div>
		<c:forEach var="item" items="${supplierSpecificationlist}">
			<div class="color_choose" id="self_specificationDiv_${item.id}" name="${item.name}" <c:if test="${specificationType=='1' || specificationType=='0'}">style="display: none;"</c:if>>
				<div class="c_title">
					<b class="out">*</b>${item.name}:</div>
				<div class="c_option">
					<c:forEach var="obj" items="${item.valuelist}">
						<div class="colorlie">
							<input id="${obj.name}" class="check_input" type="checkbox" value="${obj.name}" name="${item.id}" onclick="specificationChanage(this);"> <span name="editSpan" value="${obj.name}" contenteditable="true" onblur="specificationChanage($(this).prev());">${obj.name}</span>
						</div>
					</c:forEach>
					<input type="hidden" id="${item.id}" ismust="0" name="self_specification_result" value="${item.id}_${item.selectedValue}" /><br />
				</div>
			</div>
		</c:forEach>

		<div class="color_choose" id="self_role_btn" <c:if test="${specificationType=='1' || specificationType=='0'}">style="display: none;"</c:if>>
			<div class="role_btn" id="selfCreatebtn" onclick="createspecificationlist(this,'self_');">
				<a href="javascript:void(0);">生成产品规格</a>
			</div>
			<div class="add_new " onclick="ajaxUpdateSpecification();" style="float: left; margin-left: 20px; display: inline">
				<a href="javascript:void(0);">自定义规格</a>
			</div>
		</div>
		<%
						//---------------------自定义规格end--------------------- 
						
						//---------------------简略SKU start---------------------
					%>
		<div style="margin-left: 20px;">
			<input id="rdType1" type="radio" name="specificationType" value="1" onclick="return specificationTypeChangge(this);" <c:if test="${specificationType=='1'}">checked="checked"</c:if> /> <label for="rdType1">简略SKU</label>
		</div>
		<%
						//---------------------简略SKU end---------------------
					%>

		<div class="pro_rule_wrap" style="display: inline;">
			<div class="hr"></div>
			<div class="pro_rule" style="width: 810px; margin-left: 70px;">
				<div style="height: 25px;">
					<h2 style="width: 130px; float: left;">产品规格列表</h2>
					<div class="add_new" style="float: right; margin: 0;" id="btn_sku_add">
						<div class="" onclick="javasrciput:addSpecificationRow();">
							<a href="javascript:void(0);" style="color: #2b8dff">+添加产品sku</a>
						</div>
					</div>
				</div>
				<img alt="加载中" src="<%=static_resources %>images/loading_updateproduct.gif" style="margin: 20px 40% 40px 40%" id="skuLoading">
				<table border="0" cellpadding="0" cellspacing="0" class="proruletab proruletab_bn" id="specificationTable">
				</table>
				<div class="uploadimg_box" style="z-index: 3335">
					<h2>
						上传图片<span onclick="uploadingClose();"><img src="<%=static_resources %>images/close.gif" width="14" height="14"></span>
					</h2>
					<p>
						<b class="out">*</b>商品图片：请上传五张图片。图片尺寸为800*800px，无品牌LOGO和其他网站水印。简易图片为白底。
					</p>
					<div class="uploadimgstep">
						<div class="tsh">
							800x800<br>图片将在商<br>品详情页中展示
						</div>
						<div class="jiantou">
							<img src="<%=static_resources %>images/jiantou.gif" width="32" height="23" alt="jiantou">
						</div>
						<div class="uploadimg_list">
							<ul class="gbin1-list">
								<li name='li_0'>
									<div>
										主图<br>800*800
									</div>
								</li>
								<li name='li_1'>
									<div>
										主图<br>800*800
									</div>
								</li>
								<li name='li_2'>
									<div>
										主图<br>800*800
									</div>
								</li>
								<li name='li_3'>
									<div>
										主图<br>800*800
									</div>
								</li>
								<li name='li_4'>
									<div>
										主图<br>800*800
									</div>
								</li>
							</ul>
						</div>
					</div>

					<div class="uploadimg_btn" style="margin-top: 42px;">
						<a href="javascript:void(0);" id="filePicker">添加图片</a> <input type="hidden" name="forname" value="" /> <span id="errorUploadSpan" style="color: red; margin-left: 25px; display: none;"></span> <a href="javascript:void(0);" onclick="submitImgs(this);">确认</a> <a onclick="uploadingClose();" href="javascript:void(0);">取消</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${displayOrhide==1}">
		<div class="add_title">

			<div class="add_new" style="float: right; margin-right: 20px" onclick="popupDeleteAppr(${proid})";>
				<a href="javascript:void(0);">还原在售信息</a>
			</div>
			<div class="add_new" style="float: right; margin-right: 30px">
				<span><a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>${proid}.html" target="_blank" style="color: #2b8dff">查看在售信息</a></span>
			</div>
			<div style="float: right; margin-right: 50px">
				<span>已于<fmt:formatDate value="${product.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />修改
				</span>
			</div>

		</div>
	</c:if>
	<div class="add_title">商品详情介绍</div>
	<div class="add_info">
		<div class="introduce" id="pc_content_div">
			<p>1.<span style="color:#ff6161">最大宽度为750px</span>，单片容量最大1M，高度不限，允许写入CSS（禁止引用外部CSS），不支持JS。</p>
			<p>2.不允许引用我的网以外的图片和外部链接</p>
			<p>3.图片不可出现售价。</p>
			<p>4.图片大小不得超过1536KB | 字数不得超过5000</p>
			<textarea id="editorTemp" name="editorTemp" style="display: none;">${product.introduction}</textarea>
			<script id="editor" type="text/plain" style="width: 860px; height: 340px;"></script>
		</div>
	</div>

	<div class="add_title">售后服务</div>
	<div class="add_info">
		<div class="introduce">
			<textarea id="afterService" name="afterService" style="width: 870px; height: 175px;" maxLength="500">${product.afterService}</textarea>
		</div>
	</div>

	<div class="add_title">其他服务</div>
	<div class="add_info">
		<div class="introduce introducemar">
			<h3 class="tl">库存计数</h3>
			<ul class="other">
				<li><input class="redio" type="radio" name="stockLockType" <c:if test="${product.stockLockType==null||product.stockLockType==1 }">checked="checked"</c:if> value="1">拍下减库存<i class="mui_tip1"></i>
					<div class="mui_tip tip1">
						买家拍下商品即减少库存，存在恶拍风险。秒杀、超低价等热销商品，如需避免超卖可选此方式<s class="l"></s>
					</div></li>
				<li><input class="redio" type="radio" name="stockLockType" <c:if test="${product.stockLockType==2}">checked="checked"</c:if> value="2">付款减库存<i class="mui_tip2"></i>
					<div class="mui_tip tip2">
						买家拍下商品并付款即减少库存，存在超卖风险。如需减少恶拍，提高回款效率可选此方式<s class="l"></s>
					</div></li>
			</ul>
		</div>
	</div>

	<div class="add_title">限购设置</div>
	<div class="add_info">
	    <div class="add_model" style="margin-top: 0px">
			<div class="name">
				<b class="out"></b>企业采购：
			</div>
			<div class="color_choose1" style="float: left; width: 600px; margin: 0 0 0 12px;">
				<div style="margin-left: 20px;">
				     <input type="checkbox" <c:if test='${(not empty product.limitKbn) && (product.limitKbn==3) }'> checked="checked"</c:if> value ="3" name="limitKbn" /> &nbsp;仅限企业统一采购
				</div>
			</div>
		</div>
		<div class="add_model" style="margin-top: 0px">
			<div class="name">
				<b class="out"></b>数量限购：
			</div>
			<div class="color_choose1" style="float: left; width: 600px; margin: 0 0 0 12px;">
				<div style="margin-left: 20px;">
					<div style="float: left; margin-right: 20px;">
						<input class="middle_st" id="rdlimitCnt1" type="radio" name="rdlimitCnt" value="1" <c:if test='${(empty product.limitCnt)||(product.limitCnt==0) }'> checked="checked"</c:if> /> <label for="rdlimitCnt1">不限购</label>
					</div>
					<div style="float: left;">
						<input class="middle_st" id="rdlimitCnt2" type="radio" name="rdlimitCnt" value="2" <c:if test='${product.limitCnt>0 }'> checked="checked"</c:if> /> <label for="rdlimitCnt2">每用户限购</label> <input id="limitCnt" class="pubilc_input1 f68" style="height: 25px; line-height: 25px;" type="text" isnum="1" name="limitCnt" value="${product.limitCnt}" maxLength="3" onfocus="clearErr(this);" onkeyup="this.value=this.value.replace(/\D/g,'')"> <span>件</span>
					</div>
				</div>
			</div>
		</div>
		<div class="add_model" style="margin-top: 2px">
			<div class="name">
				<b class="out"></b>销售区域：
			</div>
			<input type="hidden" name="areasName" id="areasName" value="${product.areasName }" /> <input type="hidden" name="areasCode" id="areasCode" value="${product.areasCode }" />
			<div class="color_choose1" style="float: left; width: 600px; margin: 0 0 0 12px;">
				<div style="margin-left: 20px;">
					<div style="float: left; margin-right: 20px;">
						<input class="middle_st" id="rdAreas0" type="radio" name="rdAreas" value="0" <c:if test='${(empty product.areasCode)||(product.areasCode=="0") }'> checked="checked"</c:if> /> <label for="rdAreas0">全国</label>
					</div>
					<div style="float: left;">
						<input class="middle_st" id="rdAreas1" type="radio" name="rdAreas" value="1" <c:if test='${(not empty product.areasCode)&&(product.areasCode!="0") }'> checked="checked"</c:if> /> <label for="rdAreas1">指定区域</label>
					</div>
					<div class="bj_text" style="float: left; margin-left: 20px; display: inline" onclick="showArea(this,'');">
						<a href="javascript:void(0);" class="add_new" id="pAreasName" style="height: auto; max-width: 400px; line-height: 23px;"><c:if test='${empty product.areasName}'>编辑</c:if>
							<c:if test='${not empty product.areasName}'>${product.areasName }</c:if></a>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="add_title">特省、换领、试用</div>
	<div class="add_info">
		<div class="add_model" style="margin-top: 0px">
			<div class="name">
				<b class="out">&nbsp;</b>商品分类项：
			</div>
			<div style="width: 560px; float: left">
				<input style="margin-left: 12px;" type="checkbox" name="saleKbn" id="saleKbn" value="1" onchange="selSaleKbn(1);" /> <label for="saleKbn">特省商品</label> <input style="margin-left: 12px;" type="checkbox" name="saleKbn" id="saleKbn2" value="2" onchange="selSaleKbn(2);" /> <label for="saleKbn2">换领商品</label> <input style="margin-left: 12px;" type="checkbox" name="saleKbn" id="saleKbn5" value="5" onchange="selSaleKbn(5);" /> <label for="saleKbn5">试用(购买后评价)</label> <input style="margin-left: 12px;" type="checkbox" name="saleKbn" id="saleKbn50" value="50" onchange="selSaleKbn(50);" /> <label for="saleKbn50">试用(评价后购买)</label>
			</div>
		</div>
		<div class="add_model" style="margin-top: 0px; display: none" id="saleNoteDiv">
			<div class="name">
				<b class="out">&nbsp;</b>特省理由：
			</div>
			<div style="width: 560px; float: left">
				<input class="pubilc_input f538" name="saleNote" id="saleNote" type="text" value="${product.saleNote}" maxLength="50" placeholder="包装破损 临期商品" />
				<p style="padding-left: 14px; line-height: 30px; color: #acadad;">例：包装破损，临期商品</p>
			</div>
		</div>
		<div class="add_model" style="margin-top: 0px; display: none" id="trialPriceDiv">
			<div class="name">
				<b class="out">&nbsp;</b>评价后返现：
			</div>
			<div style="width: 560px; float: left">
				<input class="pubilc_input f80" name="trialPrice" id="trialPrice" isnum="1" type="text" value="${product.empPrice}" maxLength="10" placeholder="购买并评价后获得返现" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,'$1$2.$3');calTrial();" onblur="checkTrialPriceAndSale();" />元 <br />
				<p style="padding-left: 14px; line-height: 30px; color: #acadad;" id="calT">
					试用商品的实际售价约为<em>100</em>元
				</p>
			</div>
		</div>
		<div class="add_model" style="margin-top: 0px; display: none" id="trialPriceBefore">
			<div class="name">
				<b class="out">&nbsp;</b>评价后购买：
			</div>
			<div style="width: 560px; float: left">
				<span>回答问卷后获得购买资格</span>
			</div>
		</div>
		<div class="add_model" style="margin-top: 0px; display: none" id="trialTemplateDiv">
			<div class="name">
				<b class="out">&nbsp;</b>使用问卷：
			</div>
			<div style="float: left; margin-left: 12px">
				<select id="questionnaireId" name="questionnaireId" class="select_input1" style="height: 28px; float: left;" onchange="questionnaireChange(this);">
					<option value="-1">--不使用问卷 普通评论--</option>
					<c:forEach var="item" items="${listQuestionnaire}">
						<option value="${item.id}" <c:if test='${item.id==questionnaireId}'>selected</c:if>>${item.templateTitle}</option>
					</c:forEach>
				</select> <a href="javascript:ajaxGetQuestionnaires(${questionnaireId});" title="点击刷新问卷模板信息" style="height: 28px; width: 28px; margin-left: 5px; display: block; float: left;"><img src="<%=static_resources %>images/reload.png" /></a>

				<div class="add_new" style="float: right; margin-left: 20px; display: inline">
					<a href="${basePath}/questionnaire/template_edit.html" target="_blank">添加新问卷</a>
				</div>
				<div class="add_new" style="float: right; margin-left: 20px; display: none" id="qresult">
					<a href="javascript:void(0);" target="_blank">查看问卷</a>
				</div>

			</div>
		</div>
		<div class="add_model" style="margin-top: 0px; display: none" id="divDiv">
			<div class="name">
				<b class="out">&nbsp;</b>平均换领币：
			</div>
			<div style="width: 560px; float: left">
				<input class="pubilc_input f100" name="empExPrice" id="empPrice1" type="text" value="${product.empPrice}" maxLength="10" onblur="exchangeLetter(this.value);" />
				<p>元，员工每人领取约<span id="spanLetter">0</span>件商品  (<span style="color: red">换领币*员工数量不应超过商品总价</span>)</p>
			</div>
			<%-- <div style="width: 560px; float: left" id="divDivCnt">
				<div style="float: left; padding-left: 14px;">
					<input type="radio" name="divLevel" id="divLevel-1" value="-1" checked style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="divLevel-1">所有员工（54）</label>
				</div>
				<div class="bj_text" style="float: left; margin-left: 20px; display: inline">
					<a class="add_new" href="${basePath}/company/emp/page.html" target="_blank">查看</a>
				</div>
				<br />
				<div style="width: 100px; padding-left: 14px; float: left;">
					<input type="radio" name="divLevel" id="divLevel1" style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="divLevel1">1级（20）</label>
				</div>
				<div style="width: 100px; padding-left: 14px; float: left;">
					<input type="radio" name="divLevel" id="divLevel2" style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="divLevel2">2级（20）</label>
				</div>
				<br />
				<p style="padding-left: 14px; line-height: 30px; color: #acadad;">
					换领商品总额<em>2000</em>元，平均每人获得约为<em>100</em>元
				</p>
			</div> --%>
		</div>
		<div class="add_model" style="margin-top: 0px; display: none" id="divLimit">
			<div class="name">
				<b class="out">&nbsp;</b>换领期限：
			</div>
			<div style="width: 560px; float: left" id="divDivCnt">
				<div style="float: left; padding-left: 14px;">
					<input type="radio" name="limitType" id="limit1" value="1" style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="limit1">1个月</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="limitType" id="limit2" value="2" style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="limit2">2个月</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="limitType" id="limit3" value="3" style="vertical-align: middle; margin: -2px 4px 1px 0;" checked="checked"><label for="limit3">3个月</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="limitType" id="limit5" value="5" style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="limit5">半个月</label>&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="limitType" id="limit9" value="9"
						style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="limit9">领完为止</label>(<span style="color: red">初始值提交审核通过将无法修改</span>)
				</div>
			</div>
		</div>
	</div>

	<div class="add_title">员工专享</div>
	<div class="add_info">
		<div class="add_model" style="margin-top: 0px">
			<div class="name">
				<b class="out">&nbsp;</b>本企业员工专享：
			</div>
			<div style="width: 560px; float: left">
				<input style="margin-left: 12px;" type="checkbox" name="saleKbn" id="saleKbn4" value="4" onchange="selSaleKbn(4);" /> <label for="saleKbn4">专享</label>
			</div>
		</div>
		<div class="add_model" style="margin-top: 0px; display: none" id="empPriceDiv">
			<div class="name">
				<b class="out">&nbsp;</b>专享价格：
			</div>
			<div style="width: 560px; float: left">
				<input class="pubilc_input f100" name="empPrice" id="empPrice" isnum="1" type="text" value="${product.empPrice}" maxLength="10" placeholder="员工将以此价格购买" onblur="exchangeCashPrice(this.value);" onkeyup="this.value=this.value.replace(/^(\d+)\.(\d\d\d).*$/,'$1$2.$3');" />元
				(合每张内购现金卷<span id="cashPrice">0</span>元,用于现金抵扣)
			</div>
		</div>
		<div class="add_model" style="margin-top: 0px; display: none" id="empCntDiv">
			<div class="name">
				<b class="out">&nbsp;</b>员工人数：
			</div>
			<div style="width: 560px; float: left">
				<input class="pubilc_input f100" name="empLevel" id="empLevel" isnum="1" type="text" value="${product.empLevel}" maxLength="10" placeholder="员工人数" onblur="exchangeWealPrice(this.value);" onkeyup="this.value=this.value.replace(/\D/g,'')" />人,
				每人限领一张，共计福利<span id="wealPrice">0</span>元
			</div>
		</div>
		<%-- <div class="add_model" style="margin-top: 0px; display: none" id="empDiv">
			<div class="name">
				<b class="out">&nbsp;</b>员工范围：
			</div>
			<div style="width: 560px; float: left" id="empDivCnt">
				<div style="float: left; padding-left: 14px;">
					<input type="radio" name="empLevel" id="empLevel-1" value="-1" checked style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="empLevel-1">所有员工（54）</label>
				</div>
				<div class="bj_text" style="float: left; margin-left: 20px; display: inline">
					<a class="add_new" href="${basePath}/company/emp/page.html" target="_blank">查看</a>
				</div>
				<br />
				<div style="width: 100px; padding-left: 14px; float: left;">
					<input type="radio" name="empLevel" id="empLevel1" style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="divLevel1">1级（20）</label>
				</div>
				<div style="width: 100px; padding-left: 14px; float: left;">
					<input type="radio" name="empLevel" id="empLevel2" style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="divLevel2">2级（20）</label>
				</div>
				<br />
				<p style="padding-left: 14px; line-height: 30px; color: #acadad;">
					<input type="checkbox" name="empCash" id="empCash" value="1" /> <label for="empCash">未购买此商品的员工可获得现金券（差价）补偿</label>
				</p>
			</div>
		</div> --%>
	</div>
    
    <div class="add_title">促销活动</div>
    <div class="add_info">
    	<div style="width:900px;height:30px;margin-left: 60px;">
			<input id="chkActivityQicai" type="checkbox" name="chkActivityQicai" value="1" onclick="javascript:clickActivityQicai(this);" style="float:left;margin:5px 5px 0 0" /> <label for="chkActivityQicai" style="color:#434343;float:left">阶梯价格（企采/集采）</label>
		</div>
    	<div class="tab_box" id="tab_box_qicai" style="width:810px;margin-left:80px;display:none;">
			<table border="0px" cellpadding="0" cellspacing="0" style="width: auto; margin-top: 6px;">
				<thead>
					<tr>
						<th style="width: 300px;">规则 <input id="chkActivityDiscount" type="checkbox" name="chkActivityDiscount" value="0" onclick="javascript:clickActivityDiscount(this);" style="vertical-align: middle;margin: -2px 4px 1px 0;"/> <label for="chkActivityDiscount" style="color:#434343;">折扣</label></th>
						<th style="width: 300px;">sku</th>
						<th style="width: 50px;">删除</th>
					</tr>
				</thead>
				<tbody id="dealLadderTable">
				</tbody>
			</table>
			<div><a href="javascript:addActivityQicaiRow();" style="color: #2b8dff;">增加一条</a><input type="hidden" id="activityQicaiRowCnt" name="activityQicaiRowCnt" value="0"></div>
		</div>
    </div>
	<div class="clear"></div>
	<div class="add_btn">
		<a href="javascript:void(0);" style="margin-left: 120px;" onclick="subForm('1');" id="sub_button1">发布</a> <a href="javascript:void(0);" onclick="subForm('0');" id="sub_button0">临时保存</a>
	</div>
	<c:if test="${not empty checkOptions }">
		<div class="add_title">审核记录</div>
		<c:if test="${not empty ptpList }">
			<div class="add_info">
				<table border="0" cellpadding="0" cellspacing="0" class="proruletab proruletab_bn sh" style="width: 870px; margin: 0 20px;">
					<thead>
						<tr>
							<th>规格</th>
							<th>电商价</th>
							<th>第三方平台</th>
							<th>商品url</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ptpList}" var="ptp">
							<tr>
								<td style="width: 150px;">${ptp.itemValues}</td>
								<td style="width: 150px;">${ptp.price}</td>
								<td style="width: 150px;"><c:if test="${ptp.thirdType =='jd'}">京东</c:if> <c:if test="${ptp.thirdType =='tmall'}">天猫</c:if> <c:if test="${ptp.thirdType =='taobao'}">淘宝</c:if></td>
								<td style="width: 500px;"><div style="width: 480px; height: 36px; overflow: hidden">
										<a href="${ptp.itemUrl}" target="_blank">${ptp.itemUrl}</a>
									</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		<div class="add_info">
			<table border="0" cellpadding="0" cellspacing="0" class="proruletab proruletab_bn sh" style="width: 870px; margin: 0 20px;">
				<thead>
					<tr>
						<th>审核人</th>
						<th>审核时间</th>
						<th>审核意见</th>
						<th>审核结果</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${checkOptions}" var="check">
						<tr>
							<td>${check.username}</td>
							<td><fmt:formatDate value="${check.time}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${check.opinion}</td>
							<td><c:if test="${check.result==2}">通过</c:if> <c:if test="${check.result==-1}">不通过</c:if> <c:if test="${check.result==-2}">强制下架</c:if></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	</div>
	</form>
	</div>
	<!--right end-->
	</div>

	<script id="upload_ue" type="text/plain" tyle="width: 0px; height: 0px;"></script>
	<!-- 定义隐藏域获取回显的商品数据 -->
	<input type="hidden" id="bak_saleKbn">
	<input type="hidden" id="bak_empPrice" value="${product.empPrice}">
	<input type="hidden" id="bak_carriage" value="${product.carriage}">
	<input type="hidden" id="bak_shippingTemplateId" value="${shippingTemplateId}">
	<input type="hidden" id="bak_empLevel" value="${product.empLevel}">
	<input type="hidden" id="bak_trialPrice" value="${product.empPrice}">
	<input type="hidden" id="bak_saleNote" value="${product.saleNote}">
	<input type="hidden" id="bak_divLevel" value="${product.divLevel}">
	<input type="hidden" id="exchanging" value="${exchanging}">

	<div id="bak_sku" style="display: none"></div>
	<div style="display: none;">
		<input type="hidden" id="uploadingJqSelecter" /> <input type="file" id="uploadFile" name="file" onchange="fileUpload()" />
	</div>

	<div class="popup_bg_new"></div>
	<div class="shop_popup" id="shop_popup">
		<div class="popup_title">
			<span>自定义规格（我的规格 我做主）</span> <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="hiddenObjById('shop_popup');"></label>
		</div>
		<div class="popup_cont" id="divLoading">
			<img alt="加载中" src="<%=static_resources %>images/loading_updateproduct.gif" style="margin: 20px 40% 40px 40%">
		</div>
		<div class="popup_cont" id="divkingaku">
			<form action="#" id="kingaku_form">
				<div class="cont_box">
					<div class="box_lt">
						<div class="lt_top">
							<div class="rule_text ft_lt">
								<span>规格1</span> <input type="text" id="kingaku1" name="kingaku1" value="颜色" class="rule_input f108" onkeyup="clearNgText2(this);" onfocus="clearErr(this);" />
							</div>
							<div class="add_new add_new_a ft_lt" style="margin: 7px 0 0 20px;">
								<a href="javascript:changeImg('chkImgA1','chkImg1',1);" id="chkImgA1">＋图片</a> <input type="hidden" id="chkImg1" />
							</div>
						</div>
						<div class="lt_con">
							<div class="add_rule">
								<div class="add_new add_new_a ft_lt">
									<a href="javascript:addKValue(1)">规格值＋</a>
								</div>
							</div>
							<div class="add_new_box" id="kingaku1Vls">
								<div class="add_row">
									<div onclick="javascript:delKValue(this);">
										<a href="javascript:void(0);" class="del_rule ft_lt" title="删除">X</a>
									</div>
									<input type="text" value="" class="rule_input f108" name="kingaku1VLname" onkeyup="clearNgText2(this);" onfocus="clearErr(this);" /> <input type="hidden" name="valuelist1id" /> <input type="hidden" name="valuelist1img" />
									<div class="img_png40" style="display: none;" id="valuelist1img_1" onclick="uploadKingkuImg(this.id)">
										<img src="<%=static_resources %>images/img_png40.png" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="box_rt">
						<div class="lt_top">
							<div class="rule_text ft_lt">
								<label><input type="checkbox" id="kingaku2chk" name="kingaku2chk" value="2" onclick="kingaku2Change(this);" /></label><span>规格2</span> <input type="text" id="kingaku2" name="kingaku2" value="尺寸" class="rule_input f108" style="display: none;" onkeyup="clearNgText2(this);" onfocus="clearErr(this);" />
							</div>
							<div class="add_new add_new_a ft_lt" style="margin: 6px 0 0 20px; display: none" id="kingaku2ImgChk">
								<a href="javascript:changeImg('chkImgA2','chkImg2',2);" id="chkImgA2">＋图片</a> <input type="hidden" id="chkImg2" />
							</div>
						</div>
						<div class="lt_con">
							<div class="add_rule" id="kingaku2VlTitile" style="display: none;">
								<div class="add_new add_new_a ft_lt" style="margin-left: 20px;">
									<a href="javascript:addKValue(2)">规格值＋</a>
								</div>
							</div>
							<div class="add_new_box" id="kingaku2Vls" style="display: none;">
								<div class="add_row">
									<div onclick="javascript:delKValue(this);">
										<a href="javascript:void(0);" class="del_rule ft_lt" title="删除">X</a>
									</div>
									<input type="text" value="" class="rule_input f108" name="kingaku2VLname" onkeyup="clearNgText2(this);" onfocus="clearErr(this);" /> <input type="hidden" name="valuelist2id" /> <input type="hidden" name="valuelist2img" />
									<div class="img_png40" style="display: none;" id="valuelist2img_1" onclick="uploadKingkuImg(this.id)">
										<img src="<%=static_resources %>images/img_png40.png" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
			<p style="width: 668px; text-align: center; color: red; margin-top: 20px;">规格图片，便于选择SKU。非商品主图。
			<p>
		</div>

		<div class="clear"></div>
		<div id="ajaxErrMsg" class="box_msg" style="color: red;"></div>
		<div class="popup_btn" style="padding-bottom: 20px;">
			<a href="Javascript:void(0);" onclick="kingakuSubmit();">确认</a> <a href="javascript:void(0);" onclick="hiddenObjById('shop_popup');">取消</a>
		</div>
	</div>
	</div>

	<%@ include file="/commons/footer.jsp"%>
	<%@ include file="/commons/alertMessage.jsp"%>

	<!--商品快捷修改-弹出框 end-->
	<input type="hidden" id="bak_kingaku1select">
	<input type="hidden" id="bak_kingaku2select">
	<script>
		$(function() {
			$(".tg a").toggle(function() {
				$(this).html("-运费相关信息");
				$(".add_after_tg").show();
			}, function() {
				$(this).html("+运费相关信息");
				$(".add_after_tg").hide()
			});
		})		
	</script>
	<script>
	/*
	全场包邮运费模板点击显示和隐藏
	*/
	function clickshippfree(){   
		var flag ='${flag}';
		if(flag == "2"){
			if ($(".tab_box").is(":hidden")) {
				$(".tab_box").show();
				$(".add_shippfree a").html("-"+"全场包邮策略");
			} else {
				$(".tab_box").hide();
				$(".add_shippfree a").html("+"+"全场包邮策略");
			}
		} else {
			$(".tab_box").hide();
		}
	}
	
	</script>
</body>

</html>