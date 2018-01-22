<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<title>我的网商家中心</title>
<%@ include file="/commons/header.jsp" %>
<%@ include file="/commons/js.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/freight.css">

<script type="text/javascript">
	var jsBasePath = '${basePath}';
	 $(document).ready(function () {
	        selectedHeaderLi("psgl_header");
	    });
</script>

<!--header end-->

<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
            <c:if test="${userSession.type != 3 || userSession.hasAuth('address')}">
                <li><a href="${basePath}/shippingAddress/todeliver.html">发货地址管理</a></li>
            </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('suborder')}">
                <li><a href="${basePath}/suborder/gotoSuborderlist.html">发货</a></li>
            </c:if>
            
            <li class="curr"><a href="${basePath}/shippingAddress/freight_templates.html?ty=1&isAudit=0">运费模板</a></li>
            <li><a href="${basePath}/suborder/toSupplier_express.html?blank=1">常用快递公司</a></li>
        </ul>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/shippingAddress/todeliver.html">配送管理</a><em>></em>
            <a href="${basePath}/shippingAddress/freight_templates.html?ty=1">运费模板</a>
        </div>
        <div class="sale_wrap">
             <div class="add_form"><!-- 新增运费模板内容 -->
             	 <div class="form_tit"><c:if test="${empty template}">新增</c:if><c:if test="${not empty template}">编辑</c:if>运费模板</div>
       			 <form id="sub_form" method="post">
       			 	<input type="hidden" name="id" value="${template.id}" />
       			 	<!-- 判断是否新版添加 -->
       			 	<input type="hidden" name="version" value="2" />
       			 	<input type="hidden" id="isAudit" name="isAudit" value="${template.isAudit}" />
             	  <ul>
					   <li class="form-elem express"> 
					   		<div class="span_box">
								<span class="label-like">模板状态：</span>
								<p>
									<span class="field-note"><c:if test="${template.isAudit=='0'}">使用中</c:if><c:if test="${template.isAudit=='1'}">待审核（审核未通过前，使用<a href="javascript:oldShow();" style="color: #2b8dff">上一版本</a>）</c:if><c:if test="${template.isAudit=='2'}">审核不通过（审核未通过前，使用<a href="javascript:oldShow();" style="color: #2b8dff">上一版本</a>）</c:if></span>
								</p>
							</div>
					   		<div class="span_box">
								<span class="label-like">运送方式：</span>
								<p>
									<span class="field-note">除指定地区外，其余地区的运费采用“默认运费”</span>
								</p>
							</div>
							<div class="postage-detail" >
								<div class="postage-detail-con">
									<div class="detail-con-top">默认运费：
										<input type="hidden" name="areasName" value="全国 "/>
										<input type="hidden" name="areasCode" value="0,"/>
										<c:if test="${empty template}">
										<input type="text" name="first_cnt" value="1" maxlength="6"  />件内，
										<input type="text" name="first_price" value="10" maxlength="6"  />元，每增加
										<input type="text" name="plus_cnt" value="1" maxlength="6"  />件，增加运费
										<input type="text" name="plus_price" value="5" maxlength="6"  />元
										</c:if>
										<c:if test="${not empty template}">
										<input type="text" name="first_cnt" value="${template.rulelist[0].firstCnt }" maxlength="6"  /><c:if test="${template.countType=='1'}">件</c:if>内，
										<input type="text" name="first_price" value="${template.rulelist[0].firstPrice }" maxlength="6"  />元，每增加
										<input type="text" name="plus_cnt" value="${template.rulelist[0].plusCnt }" maxlength="6"  /><c:if test="${template.countType=='1'}">件</c:if>，增加运费
										<input type="text" name="plus_price" value="${template.rulelist[0].plusPrice }" maxlength="6"  />元
										</c:if>
									</div>
									<div class="detail_tab_box">
										<table  border="0" cellpadding="0" cellspacing="0">
											<thead>
												<tr>
													<th class="con_th1">运送到</th>
						           					<c:if test="${empty template}"><th>首件(件)</th><th>运费（元）</th><th>续件(件)</th></c:if>
						           					<c:if test="${template.countType=='1'}"><th>首件(件)</th><th>运费（元）</th><th>续件(件)</th></c:if>
													<th class="con_th2">续费（元）</th>
													<th class="con_th3">操作</th>
												</tr>
											<thead>
											<tbody id="rulesTable">
												<c:if test="${not empty template}">
               									<c:forEach items="${template.rulelist}" var="i" varStatus="status" begin="1">
												<tr>
													<td class="con_td1">
														<span>${i.areasName }</span><div onclick="showArea(this,'');"><a href="javascript:void(0);">编辑</a></div>
														<input type="hidden" name="areasName" value="${i.areasName }"/>
														<input type="hidden" name="areasCode" value="${i.areasCode }"/>
													</td>
													<td><input type="text" name="first_cnt" value="${i.firstCnt}" maxlength="6"  /></td>
													<td><input type="text" name="first_price" value="${i.firstPrice}" maxlength="6"  /></td>
													<td><input type="text" name="plus_cnt" value="${i.plusCnt}" maxlength="6"  /></td>
													<td><input type="text" name="plus_price" value="${i.plusPrice}" maxlength="6"  /></td>
													<td><div onclick="delRow(this);"><a href="javascript:void(0);">删除</a></div></td>
												</tr>
												</c:forEach>
												</c:if>
											</tbody>
										</table>
			
										</div>	
										<div class="add_adr_btn"><a href="javascript:addRow();">为指定地区城市设置运费</a></div>									
								</div>
							</div>									
	    		 		</li>             	  	   
             	  </ul>
             	  <!--指定条件包邮可选-->
             	  <div class="ex_postage">
             	  		<div class="ex_postage_tit"><input type="checkbox" <c:if test="${not empty template.freelist}">checked="checked"</c:if> id="freeRule" value="1" onclick="changeFree(this);"/><label for="freeRule">指定条件包邮(可选)</label></div>
             	  	  	<div class="table_box_fre">
							<c:if test="${not empty template.freelist}">
	             	  	  	<table style="border:0px;cellpadding:0;cellspacing:0;" >
	             	  	  		<thead>
	             	  	  			<tr>
	             	  	  				<th>选择地区</th>
	             	  	  				<th>设置包邮条件</th>
	             	  	  				<th>操作</th>
	             	  	  			</tr>
	             	  	  		</thead>
	             	  	  		<tbody>
               						<c:forEach items="${template.freelist}" var="i" varStatus="status">
	             	  	  			<tr>
	             	  	  				<td class="con_td1">
	             	  	  					<span>${i.areasName }</span><div onclick="showArea(this,'free_');"><a href="javascript:void(0);">编辑</a></div>
											<input type="hidden" name="free_areasName" value="${i.areasName }"/>
											<input type="hidden" name="free_areasCode" value="${i.areasCode }"/>
	             	  	  				</td>
	             	  	  				<td class="con_td1 con_td2">
	             	  	  					<div class="slt_wh">
												<select name="free_countTypeDes" onchange="typeDesChange(this);">
			             	  	  				        <option value="1" <c:if test="${i.countTypeDes==1}">selected</c:if>>件数</option>
			             	  	  						<option value="2" <c:if test="${i.countTypeDes==2}">selected</c:if>>金额</option>
			             	  	  						<option value="3" <c:if test="${i.countTypeDes==3}">selected</c:if>>件数＋金额</option>
	             	  	  						</select>
	             	  	  					
	             	  	  						<div class="inp_text_wh">
						           					<c:if test="${template.countType=='1'}">
			             	  	  						<c:if test="${i.countTypeDes=='1'}">
			             	  	  							满&nbsp;<input type="text" name="free_param1" value="${i.param1}" maxlength="6" />&nbsp;件包邮 <input type="hidden" name="free_param2" value="0.00"/>
														</c:if>
			             	  	  						<c:if test="${i.countTypeDes=='2'}">
			             	  	  							<input type="hidden" name="free_param1" value="0.00"/>满&nbsp;<input type="text" name="free_param2" value="${i.param2}" maxlength="6" />&nbsp;元包邮 
														</c:if>
			             	  	  						<c:if test="${i.countTypeDes=='3'}">
			             	  	  							满&nbsp;<input type="text" name="free_param1" value="${i.param1}" maxlength="6" />&nbsp;件,且&nbsp;<input type="text" name="free_param2" value="${i.param2}" maxlength="6" />&nbsp;元以上&nbsp;包邮
														</c:if>
													</c:if>
	             	  	  						</div>
	             	  	  					</div>
	             	  	  				</td>
	             	  	  				<td><div onclick="delRow(this);"><a href="javascript:void(0);">删除</a></div></td>
	             	  	  			</tr>
									</c:forEach>
	             	  	  		</tbody>
	             	  	  	</table>
	             	  	  	<div class="add_adr_btn" style="margin-left:80px;"><a href="javascript:addFreeRow();">为指定地区城市设置包邮条件</a></div>	
							</c:if>
             	  		</div>
             	  </div>
             </form>
             </div><!-- 新增运费模板内容 结束--> 
             <div class="popup_btn" style="padding-bottom:20px;">
                 <a href="javascript:formSubmit();" >保存</a>
                <c:if test="${template.isAudit!='0'}"> <a href="javascript:deleteTemplate(${template.id});" >取消</a></c:if>
             </div>
        </div>
         <c:if test="${template.isAudit!='0'}">
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
							<td><c:if test="${check.result==2}">不通过</c:if> <c:if test="${check.result==0}">通过</c:if> </td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
    </c:if>
    </div>
   
    <!--right end-->
</div>
<div  id="old" style="display: none;top:185px;left: 563px;width:610px;height:auto;border:1px solid #dbebfe;background:#fff;position:absolute;z-index:3">
<div class="city_box_tit"><a href="javascript:hiddenObjById('old')">关闭</a></div>
<c:if test="${not empty oldShippingTemplate }">
								<div style="width: 600px">
									
									<div class="tem_tit" style="width: auto">
										
										最后编辑时间：
										<fmt:formatDate value="${oldShippingTemplate.updateTime}" pattern="yyyy-MM-dd HH:mm" />
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
												<c:forEach items="${oldShippingTemplate.rulelist}" var="rule" varStatus="status2">
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
										<c:if test="${not empty oldShippingTemplate.freelist}">
											<table border="0px" cellpadding="0" cellspacing="0" style="width: auto; margin-top: 6px;">
												<thead>
													<tr>
														<th style="width: 250px;">销售区域</th>
														<th style="width: 350px;">包邮条件</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${oldShippingTemplate.freelist}" var="rule">
														<tr>
															<td style="width: 250px;">${rule.areasName}</td>
															<td style="width: 350px;"><c:if test="${rule.countTypeDes=='2'}">满 &nbsp;${rule.param2}&nbsp;元包邮</c:if> <c:if test="${rule.countTypeDes=='1'}">
																	<c:if test="${oldShippingTemplate.countType=='1'}">满 &nbsp;${rule.param1}&nbsp;件包邮</c:if>
																</c:if> <c:if test="${rule.countTypeDes=='3'}">
																	<c:if test="${oldShippingTemplate.countType=='1'}">满 &nbsp;${rule.param1}&nbsp;件,且&nbsp;${rule.param2}&nbsp;元以上 包邮</c:if>
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
<!--content end-->
<div class="city_box" id="city_box" style="display: none">
	<div class="city_box_tit"><span>选择区域</span><a href="javascript:hiddenObjById('city_box')">关闭</a></div>
	<div class="city_box_con">
		<dl>
			<dt><span><input type="checkbox" /><label>华东</label></span></dt>
			<dd>
				<ul>
					<li><input type="checkbox" value="310000"/><label>上海</label><em>(1)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="310000"/><label>上海</label></li>												
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="320000"/><label>江苏</label><em>(13)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="320100"/><label>南京</label></li>
								<li><input type="checkbox" value="320200"/><label>无锡</label></li>
								<li><input type="checkbox" value="320300"/><label>徐州</label></li>
								<li><input type="checkbox" value="320400"/><label>常州</label></li>
								<li><input type="checkbox" value="320500"/><label>苏州</label></li>
								<li><input type="checkbox" value="320600"/><label>南通</label></li>
								<li><input type="checkbox" value="320700"/><label>连云港</label></li>
								<li><input type="checkbox" value="320800"/><label>淮安</label></li>
								<li><input type="checkbox" value="320900"/><label>盐城</label></li>
								<li><input type="checkbox" value="321000"/><label>扬州</label></li>
								<li><input type="checkbox" value="321100"/><label>镇江</label></li>
								<li><input type="checkbox" value="321200"/><label>泰州</label></li>
								<li><input type="checkbox" value="321300"/><label>宿迁</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="330000"/><label>浙江</label><em>(11)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="330100"/><label>杭州</label></li>
								<li><input type="checkbox" value="330200"/><label>宁波</label></li>
								<li><input type="checkbox" value="330300"/><label>温州</label></li>
								<li><input type="checkbox" value="330400"/><label>嘉兴</label></li>
								<li><input type="checkbox" value="330500"/><label>湖州</label></li>
								<li><input type="checkbox" value="330600"/><label>绍兴</label></li>
								<li><input type="checkbox" value="330700"/><label>金华</label></li>
								<li><input type="checkbox" value="330800"/><label>衢州</label></li>
								<li><input type="checkbox" value="330900"/><label>舟山</label></li>
								<li><input type="checkbox" value="331000"/><label>台州</label></li>
								<li><input type="checkbox" value="331100"/><label>丽水</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="340000"/><label>安徽</label><em>(17)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="340100"/><label>合肥</label></li>
								<li><input type="checkbox" value="340200"/><label>芜湖</label></li>
								<li><input type="checkbox" value="340300"/><label>蚌埠</label></li>
								<li><input type="checkbox" value="340400"/><label>淮南</label></li>
								<li><input type="checkbox" value="340500"/><label>马鞍山</label></li>
								<li><input type="checkbox" value="340600"/><label>淮北</label></li>
								<li><input type="checkbox" value="340700"/><label>铜陵</label></li>
								<li><input type="checkbox" value="340800"/><label>安庆</label></li>
								<li><input type="checkbox" value="341000"/><label>黄山</label></li>
								<li><input type="checkbox" value="341100"/><label>滁州</label></li>
								<li><input type="checkbox" value="341200"/><label>阜阳</label></li>
								<li><input type="checkbox" value="341300"/><label>宿州</label></li>
								<li><input type="checkbox" value="341400"/><label>巢湖</label></li>
								<li><input type="checkbox" value="341500"/><label>六安</label></li>
								<li><input type="checkbox" value="341600"/><label>亳州</label></li>
								<li><input type="checkbox" value="341700"/><label>池州</label></li>
								<li><input type="checkbox" value="341800"/><label>宣城</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="360000"/><label>江西</label><em>(11)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="360100"/><label>南昌</label></li>
								<li><input type="checkbox" value="360200"/><label>景德镇</label></li>
								<li><input type="checkbox" value="360300"/><label>萍乡</label></li>
								<li><input type="checkbox" value="360400"/><label>九江</label></li>
								<li><input type="checkbox" value="360500"/><label>新余</label></li>
								<li><input type="checkbox" value="360600"/><label>鹰潭</label></li>
								<li><input type="checkbox" value="360700"/><label>赣州</label></li>
								<li><input type="checkbox" value="360800"/><label>吉安</label></li>
								<li><input type="checkbox" value="360900"/><label>宜春</label></li>
								<li><input type="checkbox" value="361000"/><label>抚州</label></li>
								<li><input type="checkbox" value="361100"/><label>上饶</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>		
					
				</ul>
			</dd>
		</dl>
		<dl class="bg_change" style="height:70px;">
			<dt><span><input type="checkbox" /><label>华北</label></span></dt>
			<dd>
				<ul>
					<li><input type="checkbox" value="110000"/><label>北京</label><em>(1)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="110000"/><label>北京</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>	
					
					<li><input type="checkbox" value="120000"/><label>天津</label><em>(1)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="120000"/><label>天津</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>	
					
					<li><input type="checkbox" value="140000"/><label>山西</label><em>(11)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="140100"/><label>太原</label></li>
								<li><input type="checkbox" value="140200"/><label>大同</label></li>
								<li><input type="checkbox" value="140300"/><label>阳泉</label></li>
								<li><input type="checkbox" value="140400"/><label>长治</label></li>
								<li><input type="checkbox" value="140500"/><label>晋城</label></li>
								<li><input type="checkbox" value="140600"/><label>朔州</label></li>
								<li><input type="checkbox" value="140700"/><label>晋中</label></li>
								<li><input type="checkbox" value="140800"/><label>运城</label></li>
								<li><input type="checkbox" value="140900"/><label>忻州</label></li>
								<li><input type="checkbox" value="141000"/><label>临汾</label></li>
								<li><input type="checkbox" value="141100"/><label>吕梁</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>	
					
					<li><input type="checkbox" value="370000"/><label>山东</label><em>(17)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="370100"/><label>济南</label></li>
								<li><input type="checkbox" value="370200"/><label>青岛</label></li>
								<li><input type="checkbox" value="370300"/><label>淄博</label></li>
								<li><input type="checkbox" value="370400"/><label>枣庄</label></li>
								<li><input type="checkbox" value="370500"/><label>东营</label></li>
								<li><input type="checkbox" value="370600"/><label>烟台</label></li>
								<li><input type="checkbox" value="370700"/><label>潍坊</label></li>
								<li><input type="checkbox" value="370800"/><label>济宁</label></li>
								<li><input type="checkbox" value="370900"/><label>泰安</label></li>
								<li><input type="checkbox" value="371000"/><label>威海</label></li>
								<li><input type="checkbox" value="371100"/><label>日照</label></li>
								<li><input type="checkbox" value="371200"/><label>莱芜</label></li>
								<li><input type="checkbox" value="371300"/><label>临沂</label></li>
								<li><input type="checkbox" value="371400"/><label>德州</label></li>
								<li><input type="checkbox" value="371500"/><label>聊城</label></li>
								<li><input type="checkbox" value="371600"/><label>滨州</label></li>
								<li><input type="checkbox" value="371700"/><label>荷泽</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="130000"/><label>河北</label><em>(11)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="130100"/><label>石家庄</label></li>
								<li><input type="checkbox" value="130200"/><label>唐山</label></li>
								<li><input type="checkbox" value="130300"/><label>秦皇岛</label></li>
								<li><input type="checkbox" value="130400"/><label>邯郸</label></li>
								<li><input type="checkbox" value="130500"/><label>邢台</label></li>
								<li><input type="checkbox" value="130600"/><label>保定</label></li>
								<li><input type="checkbox" value="130700"/><label>张家口</label></li>
								<li><input type="checkbox" value="130800"/><label>承德</label></li>
								<li><input type="checkbox" value="130900"/><label>沧州</label></li>
								<li><input type="checkbox" value="131000"/><label>廊坊</label></li>
								<li><input type="checkbox" value="131100"/><label>衡水</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="150000"/><label>内蒙古</label><em>(12)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="150100"/><label>呼和浩特</label></li>
								<li><input type="checkbox" value="150200"/><label>包头</label></li>
								<li><input type="checkbox" value="150300"/><label>乌海</label></li>
								<li><input type="checkbox" value="150400"/><label>赤峰</label></li>
								<li><input type="checkbox" value="150500"/><label>通辽</label></li>
								<li><input type="checkbox" value="150600"/><label>鄂尔多斯</label></li>
								<li><input type="checkbox" value="150700"/><label>呼伦贝尔</label></li>
								<li><input type="checkbox" value="150800"/><label>巴彦淖尔</label></li>
								<li><input type="checkbox" value="150900"/><label>乌兰察布</label></li>
								<li><input type="checkbox" value="152200"/><label>兴安盟</label></li>
								<li><input type="checkbox" value="152500"/><label>锡林郭勒盟</label></li>
								<li><input type="checkbox" value="152900"/><label>阿拉善盟</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>	
					
				</ul>
			</dd>
		</dl>
		
		<dl>
			<dt><span><input type="checkbox" /><label>华中</label></span></dt>
			<dd>
				<ul>
					<li><input type="checkbox" value="410000"/><label>河南</label><em>(17)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="410100"/><label>郑州</label></li>
								<li><input type="checkbox" value="410200"/><label>开封</label></li>
								<li><input type="checkbox" value="410300"/><label>洛阳</label></li>
								<li><input type="checkbox" value="410400"/><label>平顶山</label></li>
								<li><input type="checkbox" value="410500"/><label>安阳</label></li>
								<li><input type="checkbox" value="410600"/><label>鹤壁</label></li>
								<li><input type="checkbox" value="410700"/><label>新乡</label></li>
								<li><input type="checkbox" value="410800"/><label>焦作</label></li>
								<li><input type="checkbox" value="410900"/><label>濮阳</label></li>
								<li><input type="checkbox" value="411000"/><label>许昌</label></li>
								<li><input type="checkbox" value="411100"/><label>漯河</label></li>
								<li><input type="checkbox" value="411200"/><label>三门峡</label></li>
								<li><input type="checkbox" value="411300"/><label>南阳</label></li>
								<li><input type="checkbox" value="411400"/><label>商丘</label></li>
								<li><input type="checkbox" value="411500"/><label>信阳</label></li>
								<li><input type="checkbox" value="411600"/><label>周口</label></li>
								<li><input type="checkbox" value="411700"/><label>驻马店</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>	
					
					<li><input type="checkbox" value="420000"/><label>湖北</label><em>(14)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="420100"/><label>武汉</label></li>
								<li><input type="checkbox" value="420200"/><label>黄石</label></li>
								<li><input type="checkbox" value="420300"/><label>十堰</label></li>
								<li><input type="checkbox" value="420500"/><label>宜昌</label></li>
								<li><input type="checkbox" value="420600"/><label>襄樊</label></li>
								<li><input type="checkbox" value="420700"/><label>鄂州</label></li>
								<li><input type="checkbox" value="420800"/><label>荆门</label></li>
								<li><input type="checkbox" value="420900"/><label>孝感</label></li>
								<li><input type="checkbox" value="421000"/><label>荆州</label></li>
								<li><input type="checkbox" value="421100"/><label>黄冈</label></li>
								<li><input type="checkbox" value="421200"/><label>咸宁</label></li>
								<li><input type="checkbox" value="421300"/><label>随州</label></li>
								<li><input type="checkbox" value="422800"/><label>恩施</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>	
					
					<li><input type="checkbox" value="430000"/><label>湖南</label><em>(14)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="430100"/><label>长沙</label></li>
								<li><input type="checkbox" value="430200"/><label>株洲</label></li>
								<li><input type="checkbox" value="430300"/><label>湘潭</label></li>
								<li><input type="checkbox" value="430400"/><label>衡阳</label></li>
								<li><input type="checkbox" value="430500"/><label>邵阳</label></li>
								<li><input type="checkbox" value="430600"/><label>岳阳</label></li>
								<li><input type="checkbox" value="430700"/><label>常德</label></li>
								<li><input type="checkbox" value="430800"/><label>张家界</label></li>
								<li><input type="checkbox" value="430900"/><label>益阳</label></li>
								<li><input type="checkbox" value="431000"/><label>郴州</label></li>
								<li><input type="checkbox" value="431100"/><label>永州</label></li>
								<li><input type="checkbox" value="431200"/><label>怀化</label></li>
								<li><input type="checkbox" value="431300"/><label>娄底</label></li>
								<li><input type="checkbox" value="433100"/><label>湘西土家族苗族自治</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>	
					
				</ul>
			</dd>
		</dl>
		
		<dl class="bg_change">
			<dt><span><input type="checkbox" /><label>华南</label></span></dt>
			<dd>
				<ul>
					<li><input type="checkbox" value="440000"/><label>广东</label><em>(21)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="440100"/><label>广州</label></li>
								<li><input type="checkbox" value="440200"/><label>韶关</label></li>
								<li><input type="checkbox" value="440300"/><label>深圳</label></li>
								<li><input type="checkbox" value="440400"/><label>珠海</label></li>
								<li><input type="checkbox" value="440500"/><label>汕头</label></li>
								<li><input type="checkbox" value="440600"/><label>佛山</label></li>
								<li><input type="checkbox" value="440700"/><label>江门</label></li>
								<li><input type="checkbox" value="440800"/><label>湛江</label></li>
								<li><input type="checkbox" value="440900"/><label>茂名</label></li>
								<li><input type="checkbox" value="441200"/><label>肇庆</label></li>
								<li><input type="checkbox" value="441300"/><label>惠州</label></li>
								<li><input type="checkbox" value="441400"/><label>梅州</label></li>
								<li><input type="checkbox" value="441500"/><label>汕尾</label></li>
								<li><input type="checkbox" value="441600"/><label>河源</label></li>
								<li><input type="checkbox" value="441700"/><label>阳江</label></li>
								<li><input type="checkbox" value="441800"/><label>清远</label></li>
								<li><input type="checkbox" value="441900"/><label>东莞</label></li>
								<li><input type="checkbox" value="442000"/><label>中山</label></li>
								<li><input type="checkbox" value="445100"/><label>潮州</label></li>
								<li><input type="checkbox" value="445200"/><label>揭阳</label></li>
								<li><input type="checkbox" value="445300"/><label>云浮</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="450000"/><label>广西</label><em>(14)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="450100"/><label>南宁</label></li>
								<li><input type="checkbox" value="450200"/><label>柳州</label></li>
								<li><input type="checkbox" value="450300"/><label>桂林</label></li>
								<li><input type="checkbox" value="450400"/><label>梧州</label></li>
								<li><input type="checkbox" value="450500"/><label>北海</label></li>
								<li><input type="checkbox" value="450600"/><label>防城港</label></li>
								<li><input type="checkbox" value="450700"/><label>钦州</label></li>
								<li><input type="checkbox" value="450800"/><label>贵港</label></li>
								<li><input type="checkbox" value="450900"/><label>玉林</label></li>
								<li><input type="checkbox" value="451000"/><label>百色</label></li>
								<li><input type="checkbox" value="451100"/><label>贺州</label></li>
								<li><input type="checkbox" value="451200"/><label>河池</label></li>
								<li><input type="checkbox" value="451300"/><label>来宾</label></li>
								<li><input type="checkbox" value="451400"/><label>崇左</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="350000"/><label>福建</label><em>(9)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="350100"/><label>福州</label></li>
								<li><input type="checkbox" value="350200"/><label>厦门</label></li>
								<li><input type="checkbox" value="350300"/><label>莆田</label></li>
								<li><input type="checkbox" value="350400"/><label>三明</label></li>
								<li><input type="checkbox" value="350500"/><label>泉州</label></li>
								<li><input type="checkbox" value="350600"/><label>漳州</label></li>
								<li><input type="checkbox" value="350700"/><label>南平</label></li>
								<li><input type="checkbox" value="350800"/><label>龙岩</label></li>
								<li><input type="checkbox" value="350900"/><label>宁德</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="460000"/><label>海南</label><em>(2)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="460100"/><label>海口</label></li>
								<li><input type="checkbox" value="460200"/><label>三亚</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
				</ul>
			</dd>
		</dl>
		
		<dl>
			<dt><span><input type="checkbox" /><label>东北</label></span></dt>
			<dd>
				<ul>
					<li><input type="checkbox" value="230000"/><label>黑龙江</label><em>(13)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="230100"/><label>哈尔滨</label></li>
								<li><input type="checkbox" value="230200"/><label>齐齐哈尔</label></li>
								<li><input type="checkbox" value="230300"/><label>鸡西</label></li>
								<li><input type="checkbox" value="230400"/><label>鹤岗</label></li>
								<li><input type="checkbox" value="230500"/><label>双鸭山</label></li>
								<li><input type="checkbox" value="230600"/><label>大庆</label></li>
								<li><input type="checkbox" value="230700"/><label>伊春</label></li>
								<li><input type="checkbox" value="230800"/><label>佳木斯</label></li>
								<li><input type="checkbox" value="230900"/><label>七台河</label></li>
								<li><input type="checkbox" value="231000"/><label>牡丹江</label></li>
								<li><input type="checkbox" value="231100"/><label>黑河</label></li>
								<li><input type="checkbox" value="231200"/><label>绥化</label></li>
								<li><input type="checkbox" value="232700"/><label>大兴安岭</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>			
					</li>
							
					<li><input type="checkbox" value="220000"/><label>吉林</label><em>(9)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="220100"/><label>长春</label></li>
								<li><input type="checkbox" value="220200"/><label>吉林</label></li>
								<li><input type="checkbox" value="220300"/><label>四平</label></li>
								<li><input type="checkbox" value="220400"/><label>辽源</label></li>
								<li><input type="checkbox" value="220500"/><label>通化</label></li>
								<li><input type="checkbox" value="220600"/><label>白山</label></li>
								<li><input type="checkbox" value="220700"/><label>松原</label></li>
								<li><input type="checkbox" value="220800"/><label>白城</label></li>
								<li><input type="checkbox" value="222400"/><label>延边</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>		
					</li>
								
					<li><input type="checkbox" value="210000" /><label>辽宁</label><em>(10)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="210100"/><label>沈阳</label></li>
								<li><input type="checkbox" value="210200"/><label>大连</label></li>
								<li><input type="checkbox" value="210300"/><label>鞍山</label></li>
								<li><input type="checkbox" value="210400"/><label>抚顺</label></li>
								<li><input type="checkbox" value="210500"/><label>本溪</label></li>
								<li><input type="checkbox" value="210600"/><label>丹东</label></li>
								<li><input type="checkbox" value="210700"/><label>锦州</label></li>
								<li><input type="checkbox" value="210800"/><label>营口</label></li>
								<li><input type="checkbox" value="210900"/><label>阜新</label></li>
								<li><input type="checkbox" value="211000"/><label>辽阳</label></li>
								<li><input type="checkbox" value="211100"/><label>盘锦</label></li>
								<li><input type="checkbox" value="211200"/><label>铁岭</label></li>
								<li><input type="checkbox" value="211300"/><label>朝阳</label></li>
								<li><input type="checkbox" value="211400"/><label>葫芦岛</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
				</ul>
			</dd>
		</dl>
		
		<dl class="bg_change">
			<dt><span><input type="checkbox" /><label>西北</label></span></dt>
			<dd>
				<ul>
					<li><input type="checkbox" value="610000" /><label>陕西</label><em>(10)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="610100"/><label>西安</label></li>
								<li><input type="checkbox" value="610200"/><label>铜川</label></li>
								<li><input type="checkbox" value="610300"/><label>宝鸡</label></li>
								<li><input type="checkbox" value="610400"/><label>咸阳</label></li>
								<li><input type="checkbox" value="610500"/><label>渭南</label></li>
								<li><input type="checkbox" value="610600"/><label>延安</label></li>
								<li><input type="checkbox" value="610700"/><label>汉中</label></li>
								<li><input type="checkbox" value="610800"/><label>榆林</label></li>
								<li><input type="checkbox" value="610900"/><label>安康</label></li>
								<li><input type="checkbox" value="611000"/><label>商洛</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="650000" /><label>新疆</label><em>(14)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="650100"/><label>乌鲁木齐</label></li>
								<li><input type="checkbox" value="650200"/><label>克拉玛依</label></li>
								<li><input type="checkbox" value="652100"/><label>吐鲁番</label></li>
								<li><input type="checkbox" value="652200"/><label>哈密</label></li>
								<li><input type="checkbox" value="652300"/><label>昌吉</label></li>
								<li><input type="checkbox" value="652700"/><label>博尔塔拉</label></li>
								<li><input type="checkbox" value="652800"/><label>巴音郭楞</label></li>
								<li><input type="checkbox" value="652900"/><label>阿克苏</label></li>
								<li><input type="checkbox" value="653000"/><label>克孜勒苏柯尔</label></li>
								<li><input type="checkbox" value="653100"/><label>喀什</label></li>
								<li><input type="checkbox" value="653200"/><label>和田</label></li>
								<li><input type="checkbox" value="654000"/><label>伊犁</label></li>
								<li><input type="checkbox" value="654200"/><label>塔城</label></li>
								<li><input type="checkbox" value="654300"/><label>阿勒泰</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="620000" /><label>甘肃</label><em>(14)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="620100"/><label>兰州</label></li>
								<li><input type="checkbox" value="620200"/><label>嘉峪关</label></li>
								<li><input type="checkbox" value="620300"/><label>金昌</label></li>
								<li><input type="checkbox" value="620400"/><label>白银</label></li>
								<li><input type="checkbox" value="620500"/><label>天水</label></li>
								<li><input type="checkbox" value="620600"/><label>武威</label></li>
								<li><input type="checkbox" value="620700"/><label>张掖</label></li>
								<li><input type="checkbox" value="620800"/><label>平凉</label></li>
								<li><input type="checkbox" value="620900"/><label>酒泉</label></li>
								<li><input type="checkbox" value="621000"/><label>庆阳</label></li>
								<li><input type="checkbox" value="621100"/><label>定西</label></li>
								<li><input type="checkbox" value="621200"/><label>陇南</label></li>
								<li><input type="checkbox" value="622900"/><label>临夏</label></li>
								<li><input type="checkbox" value="623000"/><label>甘南</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="640000" /><label>宁夏</label><em>(5)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="640100"/><label>银川</label></li>
								<li><input type="checkbox" value="640200"/><label>石嘴山</label></li>
								<li><input type="checkbox" value="640300"/><label>吴忠</label></li>
								<li><input type="checkbox" value="640400"/><label>固原</label></li>
								<li><input type="checkbox" value="640500"/><label>中卫</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="630000" /><label>青海</label><em>(8)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="630100"/><label>西宁</label></li>
								<li><input type="checkbox" value="632100"/><label>海东</label></li>
								<li><input type="checkbox" value="632200"/><label>海北</label></li>
								<li><input type="checkbox" value="632300"/><label>黄南</label></li>
								<li><input type="checkbox" value="632500"/><label>海南</label></li>
								<li><input type="checkbox" value="632600"/><label>果洛</label></li>
								<li><input type="checkbox" value="632700"/><label>玉树</label></li>
								<li><input type="checkbox" value="632800"/><label>海西</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>	
					</li>	
					
				</ul>
			</dd>
		</dl>
		
		<dl>
			<dt><span><input type="checkbox" /><label>西南</label></span></dt>
			<dd>
				<ul>
					<li><input type="checkbox" value="500000" /><label>重庆</label><em>(1)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="500000"/><label>重庆</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="530000" /><label>云南</label><em>(16)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="530100"/><label>昆明</label></li>
								<li><input type="checkbox" value="530300"/><label>曲靖</label></li>
								<li><input type="checkbox" value="530400"/><label>玉溪</label></li>
								<li><input type="checkbox" value="530500"/><label>保山</label></li>
								<li><input type="checkbox" value="530600"/><label>昭通</label></li>
								<li><input type="checkbox" value="530700"/><label>丽江</label></li>
								<li><input type="checkbox" value="530800"/><label>思茅</label></li>
								<li><input type="checkbox" value="530900"/><label>临沧</label></li>
								<li><input type="checkbox" value="532300"/><label>楚雄</label></li>
								<li><input type="checkbox" value="532500"/><label>红河</label></li>
								<li><input type="checkbox" value="532600"/><label>文山</label></li>
								<li><input type="checkbox" value="532800"/><label>西双版纳</label></li>
								<li><input type="checkbox" value="532900"/><label>大理</label></li>
								<li><input type="checkbox" value="533100"/><label>德宏</label></li>
								<li><input type="checkbox" value="533300"/><label>怒江</label></li>
								<li><input type="checkbox" value="533400"/><label>迪庆</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="520000" /><label>贵州</label><em>(9)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="520100"/><label>贵阳</label></li>
								<li><input type="checkbox" value="520200"/><label>六盘水</label></li>
								<li><input type="checkbox" value="520300"/><label>遵义</label></li>
								<li><input type="checkbox" value="520400"/><label>安顺</label></li>
								<li><input type="checkbox" value="522200"/><label>铜仁</label></li>
								<li><input type="checkbox" value="522300"/><label>黔西南</label></li>
								<li><input type="checkbox" value="522400"/><label>毕节</label></li>
								<li><input type="checkbox" value="522600"/><label>黔东南</label></li>
								<li><input type="checkbox" value="522700"/><label>黔南</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					<li><input type="checkbox" value="540000" /><label>西藏</label><em>(7)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="540100"/><label>拉萨</label></li>
								<li><input type="checkbox" value="542100"/><label>昌都</label></li>
								<li><input type="checkbox" value="542200"/><label>山南</label></li>
								<li><input type="checkbox" value="542300"/><label>日喀则</label></li>
								<li><input type="checkbox" value="542400"/><label>那曲</label></li>
								<li><input type="checkbox" value="542500"/><label>阿里</label></li>
								<li><input type="checkbox" value="542600"/><label>林芝</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>
					</li>
					
					
					<li><input type="checkbox" value="510000" /><label>四川</label><em>(21)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="510100"/><label>成都</label></li>
								<li><input type="checkbox" value="510300"/><label>自贡</label></li>
								<li><input type="checkbox" value="510400"/><label>攀枝花</label></li>
								<li><input type="checkbox" value="510500"/><label>泸州</label></li>
								<li><input type="checkbox" value="510600"/><label>德阳</label></li>
								<li><input type="checkbox" value="510700"/><label>绵阳</label></li>
								<li><input type="checkbox" value="510800"/><label>广元</label></li>
								<li><input type="checkbox" value="510900"/><label>遂宁</label></li>
								<li><input type="checkbox" value="511000"/><label>内江</label></li>
								<li><input type="checkbox" value="511100"/><label>乐山</label></li>
								<li><input type="checkbox" value="511300"/><label>南充</label></li>
								<li><input type="checkbox" value="511400"/><label>眉山</label></li>
								<li><input type="checkbox" value="511500"/><label>宜宾</label></li>
								<li><input type="checkbox" value="511600"/><label>广安</label></li>
								<li><input type="checkbox" value="511700"/><label>达州</label></li>
								<li><input type="checkbox" value="511800"/><label>雅安</label></li>
								<li><input type="checkbox" value="511900"/><label>巴中</label></li>
								<li><input type="checkbox" value="512000"/><label>资阳</label></li>
								<li><input type="checkbox" value="513200"/><label>阿坝</label></li>
								<li><input type="checkbox" value="513300"/><label>甘孜</label></li>
								<li><input type="checkbox" value="513400"/><label>凉山</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>	
					</li>	
					
				</ul>
			</dd>
		</dl>
		
		<dl class="bg_change">
			<dt><span><input type="checkbox" /><label>港澳台</label></span></dt>
			<dd>
				<ul>
					<li><input type="checkbox" value="810000" /><label>香港</label><em>(3)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="810100"/><label>香港岛</label></li>
								<li><input type="checkbox" value="810200"/><label>九龙</label></li>
								<li><input type="checkbox" value="810300"/><label>新界</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>	
					</li>
					
					<li><input type="checkbox" value="820000" /><label>澳门</label><em>(3)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="820100"/><label>澳门半岛</label></li>
								<li><input type="checkbox" value="820200"/><label>离岛</label></li>
								<li><input type="checkbox" value="820300"/><label>路氹城</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>	
					</li>
					
					<li><input type="checkbox" value="710000" /><label>台湾</label><em>(1)</em><img src="<%=static_resources %>images/city_sj.png" />
						<div class="city_ch">
							<ul>
								<li><input type="checkbox" value="710100"/><label>台北</label></li>
							</ul>
							<div class="cle_btn"><a href="javascript:void(0);">关闭</a></div>
						</div>	
					</li>
					
				</ul>
			</dd>
		</dl>
	</div>
	<div class="popup_btn" style="margin:0;padding-top:15px;padding-bottom:10px;border-top:1px solid #dbebfe">
        <a href="Javascript:saveArea();hiddenObjById('city_box');" >确认</a>
        <a href="javascript:hiddenObjById('city_box');" >取消</a>
    </div>				
</div>


<%@ include file="/commons/alertMessage.jsp" %>
<%@ include file="/commons/footer.jsp" %>

<script type="text/javascript" src="<%=static_resources %>js/template_edit.js"></script>
</body>
</html>
