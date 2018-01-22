<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ include file="/commons/taglibs.jsp" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragram" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0"> 
<title>我的商品详情</title>
<%@ include file="/commons/js.jsp" %>

<script type="text/javascript">
window.UEDITOR_serverUrl = '${basePath}';
var jsBasePath = '${basePath}';
var jsStaticResources = '<%=static_resources %>';
var saleKbn = "${product.saleKbn}";
var supplierId = "${product.supplierId}";
var empPrice = "${product.empPrice}";
var limitType = "${limitType}";
var empAvgAmount = "${empAvgAmount}";
var empAvgCnt = "${empAvgCnt}";
var productCnt = "${productCnt}";
var divLevel = "${product.divLevel}";
var empCash = "${product.empCash}";
var empLevel = "${product.empLevel}";

</script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>
<%-- <script type="text/javascript" src="<%=static_resources %>resources/layer/layer.min.js"></script> --%>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/freight.css">
<style type="text/css">
	.input{position:relative}
	.hover{background:#ccc}
	.addimagebutton{height:11px;line-height:11px;width:60px;font-size:12px;}
#dealLadderTable a{color:#ff4040;}
#dealLadderTable input[type="text"]{margin:5px;height:22px;line-height:22px;padding-left: 6px;font: 12px/22px "Microsoft YaHei";
    color: #434343; }
#dealLadderTable input[type="checkbox"]{margin:8px 5px 0 0;}
#dealLadderTable label{line-height:24px;}
</style>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--  ismust  是否必填项目  0：非必填，1：必填-->
<!--content begin-->
<div id="content">
    <!--right begin-->
    <div class="right">
    	<div class="position">
        	<span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/product/toSelectProducttype.html">商品管理</a><em>></em>
            <a href="javascript:void(0);">预览商品-信息</a>
        </div>

        <form id="sub_form" action="${basePath}/product/createProduct.html" method="post">
        <input type="hidden" id="status" name="status" value="2"/>
        <input type="hidden" id="categoryId" name="categoryId" value="${categoryId}"/>
        <input type="hidden" id="productid" name="productid" value="${product.id}"/>
        <textarea id="introduction" name="introduction" style="display:none;">${product.introduction}</textarea>
        <div class="sort_wrap">
        	<div class="add_info_wrap">
        		<div class="add_title">基本内容</div>
                <div class="add_info">
                	<div class="add_model">
                    	<div class="name">标题：</div>
                        ${product.fullName}
                    </div>
                    <div class="add_model">
                    	<div class="name">副标题：</div>
                        ${product.name}
                    </div>
                    <div class="add_model"  style="word-break: break-all;">
                    	<div class="name">商品广告词：</div>
                        ${product.promotion}
                    </div>
                    <div class="add_model">
                    	<div class="name">商品品牌：</div>
                    	${product.brandName}
                    </div>
                    <div class="add_after">
                    	<div class="mark">此部分内容不在页面显示</div>
                        <div class="hidden_apart">
                        	<div class="apart_left">
                            	<div class="add_model">
                                    <div class="a_name">商品型号：</div>
                                   ${product.marque}
                                </div>
                                <div class="add_model">
                                    <div class="a_name">单品毛重：</div>
                                    ${product.roughWeight}<span>kg</span>
                                </div>
                                <div class="add_model">
                                    <div class="a_name">单品长（含外包装）：</div>
                                    ${product.length}<span>mm</span>
                                </div>
                                <div class="add_model">
                                    <div class="a_name">单品高（含外包装）：</div>
                                    ${product.height}<span>mm</span>
                                </div>
                            </div>
                            <div class="apart_right">
                            	<div class="add_model">
                                    <div class="a_name">条形码：</div>
                                    ${product.barcode}
                                </div>
                                <div class="add_model">
                                    <div class="a_name">单品净重：</div>
                                    ${product.netWeight}<span>kg</span>
                                </div>
                                <div class="add_model">
                                    <div class="a_name">单品宽（含外包装）：</div>
                                    ${product.width}<span>mm</span>
                                </div>
                                <div class="add_model">
                                    <div class="a_name">单品体积（含外包装）：</div>
                                    ${product.bulk}<span>mm3</span>
                                </div>
                            </div>
                        </div>
                        <div class="add_model">
                        	<div class="a_name">商品产地：</div>
                        		${product.produceaddress}
                        </div>
                    </div>
                </div>
                
                <div class="add_title">产品属性<span></span></div>
                <div class="add_info">
                		<c:forEach var="item" items="${product.productAttributelist}">
	                		<div class="add_model">
								<div class="name">${item.attributeName}:</div>
								${item.value}
							</div>
						</c:forEach>
                </div>
                <div class="add_title">产品参数<span></span></div>
                <div class="add_info">
                	<div class="parameter_apart">
                		   <c:forEach var="item" items="${product.productParameterValuelist}" varStatus="status">
                		   	<c:if test="${status.first||status.count%2!=0}">
                		   	<div class="parameter_one">
                		   	</c:if>
		                            	<div class="parameter_model">
			                                <div class="a_name">${item.parameterGroupName}:</div>
			                                ${item.parameterValue}
											<br/>
			                           	</div>
				             <c:if test="${status.last||status.count%2==0}">
	                		   </div>
	                		   	</c:if>
							</c:forEach>	
                    </div>
                </div>
                <div class="add_title">装箱清单<span>（商品出厂时，一个包装盒内，所有物件名称及相应数量，包括说明书、保修卡、配件及包装盒内的物品等等）</span></div>
                <div class="add_info">
                	<div class="parameter_apart" id="detaillistDiv">
	                	<c:forEach var="item" items="${product.productDetaillist}">
								<div class="parameter_one">
		                            <div class="parameter_model">
		                                <div class="a_name">名称：</div>
		                                ${item.name}
		                            </div>
		                            <div class="parameter_model">
		                                <div class="num">数量：</div>
		                                ${item.num}
		                            </div>
		                        </div>
						</c:forEach>
                    </div>
                </div>
                <div class="add_title">运费<span></span></div>
                <div class="add_info">
                        <c:if test="${empty shippingTemplate}">
                	<div class="add_model">
                        <div class="name">运费：</div>
                        ${product.carriage}<span>元</span>
                   <%-- <c:if test="${not empty shippingTemplate}">
                        	<span>　使用运费模板：<a href="${basePath}/shippingAddress/template_edit.html?templateId=${shippingTemplate.id}">${shippingTemplate.name}</a></span>
                        </c:if> --%>
                    </div>
                      </c:if>
                	<div class="add_model">
                        <div class="name">发货地：</div>${product.sendAddress}
                    </div>
                    <c:if test="${not empty shippingTemplate}">
					<div class="add_model" style="margin-bottom: 15px;">
						<div class="name">
							运费：
						</div>
						<div class="color_choose1" style="float: left; width: 600px; margin: 0 0 0 12px;">
							<input type="checkbox" id="rdFreightTypeCheckBox" 
							<c:if test="${(( not empty product.carriage) && product.carriage=='0.00')}"> checked="checked" disabled = "disabled"</c:if>> <label for="rdFreightType0">卖家承担运费（包邮）</label> <br />
							<div style="width:800px;height:30px;">
							<p style="color: #2b8dff;width:80px;float:left;">商家运费模板</p>
									<div class="add_new" style="float: left; margin-left: 20px">
										<a target="_blank" style="color: #2b8dff">设置</a>
									</div>
							</div>
							<div id="ajaxGetAllShippingTemplates">
							<!-- 判断新增加商品页面 -->
							<c:if test="${empty shippingTemplate }">
								<div class="add_new" style="float: left; margin-left: 20px">
									<a style="color: #2b8dff">设置</a>
								</div>
							</c:if>
							<c:if test="${not empty shippingTemplate }">
								<div style="width: 600px">
									<div class="tem_tit" style="width: auto">
										最后编辑时间：
										<fmt:formatDate value="${shippingTemplate.updateTime}" pattern="yyyy-MM-dd HH:mm" />
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
					 </c:if>
		<div class="tg" style="margin-left: 50px; margin-top: 15px;">
			<a href="javascript:void(0);" style="color: #2b8dff;">+运费相关信息</a>
		</div>
		<c:if test="${(product.bulk !=null) && (not empty product.bulk)}">
		<div class="add_after add_after_tg" style="margin-left: 50px; margin-top: 15px;">
			<div class="mark">重量和体积</div>
			<div class="hidden_apart">
				<div class="apart_left">
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品毛重：
						</div>
						<input class="pubilc_input f158" type="text" name="roughWeight" isnum="1" typename="input" id="roughWeight"  value="${product.roughWeight}" maxLength="10" /><span>kg</span>
					</div>
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品长（含外包装）：
						</div>
						<input class="pubilc_input f158" type="text" name="length" id="length" isnum="1" typename="input"  value="${product.length}" maxLength="10" /><span>mm</span>
					</div>
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品高（含外包装）：
						</div>
						<input class="pubilc_input f158" type="text" name="height" id="height" isnum="1" typename="input"  value="${product.height}" maxLength="10" /><span>mm</span>
					</div>
				</div>
				<div class="apart_right">
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品净重：
						</div>
						<input class="pubilc_input f158" type="text" name="netWeight" id="netWeight" isnum="1" typename="input" value="${product.netWeight}" maxLength="10" /><span>kg</span>
					</div>
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品宽（含外包装）：
						</div>
						<input class="pubilc_input f158" type="text" name="width" id="width" isnum="1" typename="input" value="${product.width}" maxLength="10" /><span>mm</span>
					</div>
					<div class="add_model">
						<div class="a_name">
							<b class="out"></b>单品体积（含外包装）：
						</div>
						<input class="pubilc_input f158" type="text" name="bulk" id="bulk" isnum="1" typename="input"  value="${product.bulk}" maxLength="10" /><span>mm3</span>
					</div>
				</div>
			</div>
		</div>
		</c:if>
                </div>
                <div class="add_title">产品规格</div>
                <div class="add_info">
                <div class="faster_alter">
					            <div class="alter_cont" style="width:800px;">
					            	<div class="alter_cont_title" style="width:800px;">
					                    <strong style="margin-left:110px;">条形码</strong>
					                    <strong>价格</strong>
					                    <strong>库存</strong>
					                    <strong>预警值</strong>
                                        <strong>内购券</strong>
					                </div>
                					<c:forEach var="item" items="${product.productSpecificationslist}">
					            	<div class="alter_cont_list">
					                	<span>${item.itemnames}</span>
					                	<input class="common_input f98" type="text"  readonly="readonly" value="${item.productCode}">
					                    <input class="common_input f98" type="text"  name ="skuPrice" readonly="readonly" value="${item.price}">
					                    <input class="common_input f98" type="text"  name ="skuNum"  readonly="readonly" value="${item.stock}">
					                    <input class="common_input f98" type="text"  readonly="readonly" value="${item.warnnum}">
                                        <input class="common_input f98" type="text"  readonly="readonly" value="${item.maxFucoin}">
                                        <input class="common_input f98" type="hidden" name ="internalPurchasePrice" readonly="readonly" value="${item.internalPurchasePrice}">
					                </div>
							</c:forEach>		
						</div>
					</div>
                </div>
                <div class="add_title">商品详情介绍</div>
                <div class="add_info">
                	<div class="introduce"  style="word-break: break-all;">
                        ${product.introduction}
                    </div>                    
                </div>
                <div class="add_title">售后服务</div>
                <div class="add_info">
                	<div class="introduce" style="word-break: break-all;">
                    	${product.afterService}
                    </div>
                </div>
                <div class="add_title">其他服务</div>
                <div class="add_info">
                	<div class="introduce introducemar">
                    	<h3 class="tl">库存计数</h3>
                        <ul class="other">
                            <li><input class="redio" type="radio" name="stockLockType" <c:if test="${product.stockLockType==1 }">checked="checked"</c:if>  value="1" disabled="disabled">拍下减库存<i class="mui_tip1"></i>
                            	<div class="mui_tip tip1">买家拍下商品即减少库存，存在恶拍风险。秒杀、超低价等热销商品，如需避免超卖可选此方式<s class="l"></s></div>
                            </li>
                            <li><input class="redio" type="radio" name="stockLockType" <c:if test="${product.stockLockType==2}">checked="checked"</c:if> value="2" disabled="disabled">付款减库存<i class="mui_tip2"></i>
                            	<div class="mui_tip tip2">买家拍下商品并付款即减少库存，存在超卖风险。如需减少恶拍，提高回款效率可选此方式<s class="l"></s></div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="add_title">限购设置</div>
				<div class="add_info">
					 <div class="add_model" style="margin-top: 0px">
						<div class="name"><b class="out">*</b>用户类型：</div>
						<div class="color_choose1" style="float: left; width: 600px; margin: 0 0 0 12px;">
							<div style="margin-left: 20px;">
				    		 <input type="checkbox" <c:if test='${(not empty product.limitKbn) && (product.limitKbn==3) }'> disabled="disabled" checked="checked"</c:if> name="limitKbn" /> &nbsp;企业用户(勾选后，只能是企业用户购买)
							</div>
						</div>
					</div>
					<div class="add_model" style="margin-top: 0px">
						<div class="name">
							<b class="out">*</b>数量限购：
						</div>
						<div class="color_choose1"style="float: left; width: 600px; margin: 0 0 0 12px;">
							<div style="margin-left: 20px;">
								<div style="float: left; margin-right: 20px;">
									<input class="middle_st" id="rdlimitCnt1" type="radio"name="rdlimitCnt" value="1" disabled="disabled" <c:if test='${(empty product.limitCnt)||(product.limitCnt==0) }'> checked="checked"</c:if> />
									<label for="rdlimitCnt1">不限购</label>
								</div>
								<div style="float: left;">
									<input class="middle_st" id="rdlimitCnt2" type="radio"name="rdlimitCnt" value="2"<c:if test='${product.limitCnt>0 }'> checked="checked"</c:if> disabled="disabled"/>
									<label for="rdlimitCnt2">每用户限购</label>
									<input id="limitCnt"class="pubilc_input1 f68"style="height: 25px; line-height: 25px;" type="text"isnum="1" name="limitCnt" value="${product.limitCnt}"maxLength="3" readonly="readonly"> <span>件</span>
								</div>
							</div>
						</div>
					</div>
					<div class="add_model" style="margin-top: 2px">
						<div class="name">
							<b class="out">*</b>销售区域：
						</div>
						<div class="color_choose1"style="float: left; width: 600px; margin: 0 0 0 12px;">
							<div style="margin-left: 20px;">
								<div style="float: left; margin-right: 20px;">
									<input class="middle_st" id="rdAreas0" type="radio"name="rdAreas" value="0" disabled="disabled" <c:if test='${(empty product.areasCode)||(product.areasCode=="0") }'> checked="checked"</c:if> />
									<label for="rdAreas0">全国</label>
								</div>
								<div style="float: left;">
									<input class="middle_st" id="rdAreas1" type="radio"name="rdAreas" value="1" disabled="disabled" <c:if test='${(not empty product.areasCode)&&(product.areasCode!="0") }'> checked="checked"</c:if> />
									<label for="rdAreas1">指定区域</label>
								</div>
								<div class="bj_text"style="float: left; margin-left: 20px; display: inline">
									<a href="javascript:void(0);" class="add_new" id="pAreasName"style="height: auto; max-width: 400px; line-height: 23px;">
									<c:if test='${empty product.areasName}'>编辑</c:if> <c:if test='${not empty product.areasName}'>${product.areasName }</c:if>
									</a>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="add_title">特省、换领、试用</div>
				<div class="add_info">
					<div class="add_model" style="margin-top: 0px">
						<div class="name"><b class="out">&nbsp;</b>商品分类项：</div>
						<div style="width: 560px; float: left">
							<input style="margin-left: 12px;" type="checkbox" name="saleKbn" id="saleKbn" <c:if test="${product.saleKbn==1 }">checked="checked"</c:if>  disabled="disabled"/> <label for="saleKbn">特省商品</label> 
							<input style="margin-left: 12px;" type="checkbox" name="saleKbn" id="saleKbn2" <c:if test="${product.saleKbn==2 }">checked="checked"</c:if> disabled="disabled" /> <label for="saleKbn2">换领商品</label> 
							<input style="margin-left: 12px;" type="checkbox" name="saleKbn" id="saleKbn5" <c:if test="${product.saleKbn==5 }">checked="checked"</c:if> disabled="disabled" /> <label for="saleKbn5">试用(购买后评价)</label> 
							<input style="margin-left: 12px;" type="checkbox" name="saleKbn" id="saleKbn50"<c:if test="${product.saleKbn==50 }">checked="checked"</c:if> disabled="disabled"/> <label for="saleKbn50">试用(评价后购买)</label>
						</div>
					</div>
					<div class="add_model" style="margin-top: 0px;" id="saleNoteDiv">
						<div class="name"><b class="out">&nbsp;</b>
							<c:if test="${product.saleKbn==1}">特省理由：</c:if>
							<c:if test="${product.saleKbn==2}">换领理由：</c:if>
							<c:if test="${product.saleKbn==5}">评价后返现：</c:if>
							<c:if test="${product.saleKbn==50}">评价后购买：</c:if>
						</div>
						<div style="width: 560px; float: left">
							<c:if test="${product.saleKbn==1 || product.saleKbn==2}">
							<input class="pubilc_input f538" name="saleNote" id="saleNote" type="text" value="${product.saleNote}" re"/>
							</c:if>
							<c:if test="${product.saleKbn==5}">
							<input class="pubilc_input f80" name="trialPrice" id="trialPrice" isnum="1" type="text" value="${product.empPrice}" readonly="readonly"/>元 <br />
							<p style="padding-left: 14px; line-height: 30px; color: #acadad;" id="calT">试用商品的实际售价约为<em>${product.showPrice-empPrice}</em>元</p>
							</c:if>
							<c:if test="${product.saleKbn==50}"><span>回答问卷后获得购买资格</span></c:if>
						</div>
					</div>
					<c:if test="${product.saleKbn==2 }">
					<div class="add_model" style="margin-top: 0px;" id="divDiv">
						<div class="name">
							<b class="out">&nbsp;</b>员工范围：
						</div>
						<div style="width: 560px; float: left" id="divDivCnt">
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
								换领商品总额<em>${productCnt }</em>元，平均每人获得约为<em>100</em>元
							</p>
						</div>
					</div>
					<div class="add_model" style="margin-top: 0px;" id="divLimit">
						<div class="name"><b class="out">&nbsp;</b>换领期限：</div>
						<div style="width: 560px; float: left" id="divDivCnt">
							<div style="float: left; padding-left: 14px;">
								<input type="radio" name="limitType" id="limit1" value="1" style="vertical-align: middle; margin: -2px 4px 1px 0;" disabled="disabled"><label for="limit1">1个月</label>&nbsp;&nbsp;&nbsp;&nbsp; 
								<input type="radio" name="limitType" id="limit2" value="2" style="vertical-align: middle; margin: -2px 4px 1px 0;" disabled="disabled"><label for="limit2">2个月</label>&nbsp;&nbsp;&nbsp;&nbsp; 
								<input type="radio" name="limitType" id="limit3" value="3" style="vertical-align: middle; margin: -2px 4px 1px 0;" disabled="disabled"><label for="limit3">3个月</label>&nbsp;&nbsp;&nbsp;&nbsp; 
								<input type="radio" name="limitType" id="limit5" value="5" style="vertical-align: middle; margin: -2px 4px 1px 0;" disabled="disabled"><label for="limit5">半个月</label>&nbsp;&nbsp;&nbsp;&nbsp; 
								<input type="radio" name="limitType" id="limit9" value="9"style="vertical-align: middle; margin: -2px 4px 1px 0;" disabled="disabled"><label for="limit9">领完为止</label>&nbsp;&nbsp;&nbsp;&nbsp;
							</div>
						</div>
					</div>
					</c:if>
					<c:if test="${product.saleKbn==5 || product.saleKbn==50 }">
					<div class="add_model" style="margin-top: 0px;" id="trialTemplateDiv">
						<div class="name"><b class="out">&nbsp;</b>使用问卷：</div>
						<div style="float: left; margin-left: 12px">
						<select id="questionnaireId" name="questionnaireId" class="select_input1" style="height: 28px; float: left;" disabled="disabled" >
							<option value="-1" <c:if test='${1==questionnaireId}'>selected</c:if>>--不使用问卷 普通评论--</option>
						<c:forEach var="item" items="${listQuestionnaire}">
							<option value="${item.id}" <c:if test='${item.id==questionnaireId}'>selected</c:if>>${item.templateTitle}</option>
						</c:forEach>
						</select>
							<div class="add_new" style="float: right; margin-left: 20px; display: none" id="qresult">
							<a href="javascript:void(0);" target="_blank">查看问卷</a>
							</div>
						</div>
					</div>
					</c:if>
				</div>
				<div class="add_title">员工专享</div>
				<div class="add_info">
					<div class="add_model" style="margin-top: 0px">
					<div class="name"><b class="out">&nbsp;</b>本企业员工专享：</div>
					<div style="width: 560px; float: left">
						<input style="margin-left: 12px;" type="checkbox" name="saleKbn" id="saleKbn4" value="4" <c:if test="${product.saleKbn==4 }"> checked="checked" disabled="disabled"</c:if> /> <label for="saleKbn4">专享</label>
					</div>
				</div>
				<c:if test="${product.saleKbn==4 }">
				<div class="add_model" style="margin-top: 0px;" id="empPriceDiv">
					<div class="name"><b class="out">&nbsp;</b>专享价格：</div>
					<div style="width: 560px; float: left">
						<input class="pubilc_input f100" name="empPrice" id="empPrice" isnum="1" type="text" value="${product.empPrice}" readonly="readonly"/>元
					</div>
				</div>
				<div class="add_model" style="margin-top: 0px;" id="empDiv">
					<div class="name"><b class="out">&nbsp;</b>员工范围：</div>
					<div style="width: 560px; float: left" id="empDivCnt">
						<div style="float: left; padding-left: 14px;">
							<input type="radio" name="empLevel" id="empLevel-1" value="-1" <c:if test="${product.empLevel== -1 }"> checked="checked" disabled="disabled"</c:if> style="vertical-align: middle; margin: -2px 4px 1px 0;"><label for="empLevel-1">所有员工（54）</label>
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
					<input type="checkbox" name="empCash" id="empCash" value="1" <c:if test="${product.empCash==1 }"> checked="checked" disabled="disabled"</c:if>  /> <label for="empCash">未购买此商品的员工可获得现金券（差价）补偿</label>
					</p>
					</div>
				</div>
				</c:if>
			</div>
			<div class="add_title">促销活动</div>
    		<div class="add_info">
    			<div style="width:900px;height:30px;margin-left: 60px;">
					<input id="chkActivityQicai" type="checkbox" name="chkActivityQicai" value="1" <c:if test="${productLadderList!=null && not empty productLadderList}"> checked="checked" disabled="disabled"</c:if> style="float:left;margin:5px 5px 0 0" /> <label for="chkActivityQicai" style="color:#434343;float:left">阶梯价格（企采/集采）</label>
				</div>
    			<div class="tab_box" id="tab_box_qicai" style="width:810px;margin-left:80px;">
					<table border="0px" cellpadding="0" cellspacing="0" style="width: auto; margin-top: 6px;">
						<thead>
							<tr>
								<th style="width: 300px;">规则<input id="chkActivityDiscount" type="checkbox" name="chkActivityDiscount" value="0" <c:if test="${productLadderType!=null && not empty productLadderType  && productLadderType==1}"> checked="checked" disabled="disabled"</c:if> style="vertical-align: middle;margin: -2px -4px 1px 12px"/> <label for="chkActivityDiscount" style="color:#434343;">折扣</label></th>
								<th style="width: 300px;">sku</th>
							<th style="width: 50px;">删除</th>
						</tr>
						</thead>
						<tbody id="dealLadderTable">
							${productLadderList}
						</tbody>
					</table>
				</div>
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
                <div class="clear"></div>
                <div class="next_btn" style="width:120px;"><a href="javascript:window.close();">确定</a></div>
            </div>
        </div>
        </form>
    </div>
    <!--right end-->
</div>
<script id="upload_ue"  type="text/plain" style="width:0px;height:0px;"></script>
<script type="text/javascript" src="<%=static_resources %>js/product_preview.js"></script>
<!--content end-->
<script type="text/javascript">
    	$(document).ready(function(){
    		selectedHeaderLi("spgl_header");
		});
    	
    </script>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>