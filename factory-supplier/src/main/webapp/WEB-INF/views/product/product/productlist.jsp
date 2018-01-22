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
<title>我的网商家中心-商品列表</title>
<%@ include file="/commons/js.jsp" %>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
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
     
	
    <div class="right">
    	<div class="position">
        	<span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/product/toSelectProducttype.html">商品管理</a><em>></em>
            <a href="javascript:void(0);"><c:if test="${selltype =='selling'}">在售商品管理</c:if><c:if test="${selltype =='waitsell'}">待售商品管理</c:if><c:if test="${selltype =='waitcheck'}">审批中商品管理</c:if><c:if test="${selltype =='reject'}">审核未通过商品管理</c:if></a>
        </div>
        <div class="sale_wrap">
        	<form id="sub_form" action="${basePath}/product/findProductlistPage.html" method="post">
        	 <input type="hidden" id="pages" name="pages" value="${pages}"/>
     	     <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
     	     <input type="hidden" id="selltype" name="selltype" value="${selltype}"/>
     	     <input type="hidden" id="pricesort" name="pricesort" value="${pricesort}"/>
     	     <input type="hidden" id="allnumsort" name="allnumsort" value="${allnumsort}"/>
     	     <input type="hidden" id="createDatesort" name="createDatesort" value="${createDatesort}"/>
     	     <input type="hidden" id="sortname" name="sortname" value="${sortname}"/>
     	     <input type="hidden" id="sort" name="sort" value="${sort}"/>
     	     
            <div class="sale_info">
            	<div class="sale_one">
                	<div class="sale_option">
                    	<div class="title">商品名称：</div>
                        <input class="pubilc_input f218" type="text" id="name" name="name" value="${name}" maxLength="20">
                    </div>
                    <div class="sale_option">
                    	<div class="title">商品条码：</div>
                        <input class="pubilc_input f218" type="text" id="barcode" name="barcode" value="${barcode}"  maxLength="20">
                    </div>
                    <!-- 
                    <div class="sale_option">
                    	<div class="title">库存：</div>
                        <input class="pubilc_input f218" type="text" name="" value="">
                    </div> -->
                    <div class="sale_option">
                    	<c:if test="${selltype =='selling'}">
                    	<div class="title">上线时间：</div>
                    	</c:if>
                    	<c:if test="${selltype !='selling'}">
                    	<div class="title">创建时间：</div>
                    	</c:if>
                        <input class="pubilc_input f91" type="text" readonly="readonly" id="starttime" name="starttime" value="${starttime}" onClick="WdatePicker()"/><span>到</span>
                        <input class="pubilc_input f91" type="text" readonly="readonly" id="endtime" name="endtime" value="${endtime}" onClick="WdatePicker()"/>
                    </div>
                </div>
                <div class="sale_one">
                	<div class="sale_option">
                    	<div class="title">商品类目：</div>
                    	<input type="hidden" name="categoryidtemp" id="categoryidtemp" value="${categoryid}"/>
                        <select class="pubilc_input f226" id="categoryid" name="categoryid">
                        	<option value="">--请选择--</option>
                        </select>
                    </div>
                    <div class="sale_option">
                    	<div class="title">价格：</div>
                        <input class="pubilc_input f91" type="text" id="minprice" name="minprice" onkeyup="this.value=this.value.replace(/\D/g,'')" value="${minprice}" maxLength="8"/><span>到</span>
                        <input class="pubilc_input f91" type="text" id="maxprice" name="maxprice"  onkeyup="this.value=this.value.replace(/\D/g,'')" value="${maxprice}" maxLength="8"/>
                    </div>
                    <div class="sale_option">
                    	<div class="title">店铺中分类：</div>
                    	<input type="hidden" id="stoIdtemp" name="stoIdtemp" value="${stoId}"/>
                        <select class="pubilc_input f226" id="stoId" name="stoId">
	                        	<option value="">--请选择--</option>
                        </select>
                    </div>
                </div>
                <div class="sale_one">
	                   <div class="sale_option">
	                    	<div class="title">店铺：</div>
	                        <select class="pubilc_input f226" id="shopId" name="shopId">
	                        	<option value="">--请选择--</option>
								<c:forEach var="item" items="${shopList}">
				            	<option value="${item.id}" <c:if test="${shopId== item.id}">selected</c:if>>${item.shopname}</option>
								</c:forEach>
	                        </select>
	                    </div>
                <c:if test="${selltype =='waitsell'}">
	                   <div class="sale_option">
	                    	<div class="title">下架状态：</div>
	                        <select class="pubilc_input f226" id="isMarketable" name="isMarketable">
	                        	<option value="">--请选择--</option>
	                        	<option value="-12"  <c:if test="${isMarketable =='-12'}">selected</c:if>>全部下架</option>
	                            <option value="-1"  <c:if test="${isMarketable =='-1'}">selected</c:if>>售完下架</option>
	                            <option value="-2"  <c:if test="${isMarketable =='-2'}">selected</c:if>>我下架的</option>
	                            <option value="0"  <c:if test="${isMarketable =='0'}">selected</c:if>>从未上架</option>
	                        </select>
	                    </div>
                    </c:if>
                </div>
                
                <div class="clear"></div>
                <div class="btnwrap">
                	<div class="checkbtn"><a href="javascript:void(0);"  onclick="formSubmit('1');">查询</a></div>
                    <div class="resetbtn"><a href="javascript:void(0);" onclick="formReset();">重置</a></div>
                </div>
            </div>
           </form>
            <div class="sale_list_wrap">
                  <c:choose>
                		<c:when test="${result.msgBody!=null}">
		            	<div class="sale_title bottom_line">
		                	<span><input class="redio" type="checkbox"  id="total" onclick="checkTotal(this);check();">全选</span>
		                	<c:if test="${selltype !='reject'}">
		                    
		                    <c:if test="${selltype =='selling'}"><div class="p_submit"> <a href="javascript:void(0);" onclick="batchSellOffAlert()" >批量下架</a></div></c:if>
		                    <c:if test="${selltype =='waitsell'}"><div class="p_submit"> <a href="javascript:void(0);" onclick="batchSellOnAlert()" >批量上架</a></div></c:if>
		                    <c:if test="${selltype =='waitcheck'}"><div class="p_submit"> <a href="javascript:void(0);" onclick="batchCheckcancelAlert();" >批量取消</a></div></c:if>
		                    </c:if>
		                    <div class="p_delete"><a href="javascript:void(0);" onclick="deleteAllObject(0,-10);" >批量删除</a></div>
		                    <div class="add_new"><a href="${basePath}/product/toSelectProducttype.html"><i class="addico"></i>添加新商品</a></div>
	                	</div>
		                <div class="sale_content">
		                       <c:if test="${selltype =='selling'||selltype =='waitsell'}">
		                       		<ul class="sale_them">
			                         	<c:if test="${selltype =='selling'}">
			                         		<input type="hidden" value="${sortTotal }" id="productViewSort_total_hidden">
			                         		<li class="li001">商品信息</li>
					                        <li class="li002"  style="cursor:pointer;" onclick="sortOclick('pricesort');">价格<i name="pricesort_i" class="<c:if test='${pricesort==2}'>down</c:if>"></i></li>
					                        <li class="li003"  style="cursor:pointer;"  onclick="sortOclick('allnumsort');">库存<i name="allnumsort_i" class="<c:if test='${allnumsort==2}'>down</c:if>"></i></li>
					                        <li class="li004"  style="cursor:pointer;"  onclick="sortOclick('createDatesort');">上线时间<i name="createDatesort_i" class="<c:if test='${createDatesort==2}'>down</c:if>"></i></li>
			                         		<li class="li005" style="cursor:pointer;"  onclick="sortOclick('sort');">排序<i name="sort_i" class="<c:if test='${sort==2}'>down</c:if>"></i></li>
			                         		<li class="li006">操作</li>
			                         	</c:if>
			                         	<c:if test="${selltype =='waitsell'}">
			                         		<li class="li01">商品信息</li>
					                        <li class="li02"  style="cursor:pointer;" onclick="sortOclick('pricesort');">价格<i name="pricesort_i" class="<c:if test='${pricesort==2}'>down</c:if>"></i></li>
					                        <li class="li03"  style="cursor:pointer;"  onclick="sortOclick('allnumsort');">库存<i name="allnumsort_i" class="<c:if test='${allnumsort==2}'>down</c:if>"></i></li>
					                        <li class="li04"  style="cursor:pointer;"  onclick="sortOclick('createDatesort');">创建日期<i name="createDatesort_i" class="<c:if test='${createDatesort==2}'>down</c:if>"></i></li>
			                         		<li class="li05">操作</li>
			                         	</c:if>
		                           	</ul>
		                       </c:if>
		                       
		                  
		                    <c:if test="${selltype =='waitcheck' || selltype =='reject'}">
			                     <ul class="sale_them">
			                        <li class="s1">商品信息</li>
			                        <li class="s2"  style="cursor:pointer;"  onclick="sortOclick('pricesort');">价格<i name="pricesort_i" class="<c:if test='${pricesort==2}'>down</c:if>"></i></li>
			                        <li class="s3"  style="cursor:pointer;"  onclick="sortOclick('allnumsort');">库存<i name="allnumsort_i" class="<c:if test='${allnumsort==2}'>down</c:if>"></i></li>
			                        <li class="s4" style="cursor:pointer;"  onclick="sortOclick('createDatesort');">创建日期<i name="createDatesort_i" class="<c:if test='${createDatesort==2}'>down</c:if>"></i></li>
			                        <li class="s5">审批状态</li>
			                        <li class="s6">操作</li>
			                    </ul>
		                    </c:if>
		                    <ul class="sale_infomation_list">
		                    	<c:forEach items="${result.msgBody}" var="item" varStatus="status">
			                    	 <li>
			                        	
			                        	<c:if test="${selltype =='selling'}"><div class="sale_sort_tl"></c:if>
			                        	<c:if test="${selltype !='selling'}"><div class="sale_tl"></c:if>
			                        	  <c:choose>
			                        	  	<c:when test="${item.locked==1 || item.saleKbn==2}">
			                        			<span><input class="redio" type="checkbox" disabled="disabled" name="ck" onclick="check();" value="${item.id}" >商品条码：${item.barcode}</span>
			                        	  	</c:when>
			                        	  	<c:otherwise>
			                                	<span><input class="redio" type="checkbox" name="ck" onclick="check();" value="${item.id}" >商品条码：${item.barcode}</span>
			                        	  	</c:otherwise>
			                        	  </c:choose>
			                                <span>所属类目：${item.categoryName}</span>
			                                <span>所属店铺：${item.shopname} 
			                                <c:if test="${selltype !='selling'}">
			                                	<c:if test="${item.savestate==1}"><font color="red">（商品信息不完整）</font></c:if>
			                                </c:if>
			                                </span>
			                            </div>
			                            <div class="infowrap">
			                           	    <c:if test="${selltype =='waitsell'}"><div class="shop_info"></c:if>
			                           	    <c:if test="${selltype =='selling'}"><div class="shop_sort_info"></c:if>
			                                <c:if test="${selltype =='waitcheck' || selltype =='reject'}"><div class="pro_info"></c:if>
			                                	<div class="s_img">
			                                		<c:if test="${item.saleKbn==1 }">
											   		<div class="picon"><img src="<%=static_resources %>images/picon2.png" /></div>
											   		</c:if>
											  		<c:if test="${item.saleKbn==2 }">
											   		<div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
											   		</c:if>
											   		<c:if test="${item.saleKbn==4 }">
											   		<div class="picon"><img src="<%=static_resources %>images/picon_z2.png" /></div>
											   		</c:if>
											  		<c:if test="${item.saleKbn==5 }">
											   		<div class="picon"><img src="<%=static_resources %>images/picon_t2.png" /></div>
											   		</c:if>
											   		<c:if test="${selltype =='selling'}">
			                                		<a  href="${basePath}/product/productView.html?productId=${item.id}"  target="_blank"><img src="${item.image}"  alt="Me-order-img"></a>
			                                		</c:if>
			                                		<c:if test="${selltype !='selling'}">
			                                		<a  href="${basePath}/product/productView.html?apprid=${item.id}"  target="_blank"><img src="${item.image}"  alt="Me-order-img"></a>
			                                		</c:if>
			                                		
			                                	</div>
			                                	<c:if test="${selltype =='selling'}">
			                                    <p><a  href="${basePath}/product/productView.html?productId=${item.id}" target="_blank"  style="word-break: break-all;">${item.fullName}</a></p>
			                                    </c:if>
			                                    <c:if test="${selltype !='selling'}">
			                                    <p><a  href="${basePath}/product/productView.html?apprid=${item.id}" target="_blank"  style="word-break: break-all;">${item.fullName}</a></p>
			                                    </c:if>
			                                </div>
			                                <span>￥<fmt:formatNumber value="${item.minprice}" pattern="#,##0.00#"/>-<fmt:formatNumber value="${item.maxprice}" pattern="#,##0.00#"/>
			                                <c:if test="${item.locked==1}">
			                                <i class="elt_icon_lock" onclick="showInfoBox('锁定原因：${item.lockReason}');"></i>
			                                </c:if>
			                                <c:if test="${item.locked==0 && item.saleKbn!=2 && item.saleKbn!=4}">
			                                	<c:if test="${selltype !='selling'}">	                                
				                                	<c:if test="${item.savestate!=1}">
				                                		<i class="elt_icon" onclick="ajaxUpdatePrice('${item.id}',0);"></i>
				                                	</c:if>
				                                </c:if>
			                                	<c:if test="${selltype =='selling'}">	
				                                	<i class="elt_icon" onclick="ajaxUpdatePrice('${item.id}',1);"></i>
				                                </c:if>
			                                </c:if>
			                                </span>
			                                <c:if test="${selltype =='selling'||selltype =='waitsell'}"> <div class="stock"></c:if>
			                                <c:if test="${selltype =='waitcheck' || selltype =='reject'}"><div class="pro_stock"></c:if>
			                                <c:if test="${item.locked==1}">
			                                	<p>${item.allnum}<i class="elt_icon_lock" onclick="showInfoBox('锁定原因：${item.lockReason}');"></i></p>
				                            </c:if>
				                             <c:if test="${item.locked==0}">
			                                	<c:if test="${selltype !='selling'}">	
			                                		<p>${item.allnum}<c:if test="${item.savestate!=1 && item.saleKbn!=4 && item.saleKbn!=2}"><i class="elt_icon" onclick="ajaxUpdatePrice('${item.id}',0);"></i></c:if></p>
				                                </c:if>
			                                	<c:if test="${selltype =='selling'}">
			                                		<p>${item.allnum}<c:if test="${item.saleKbn!=4 && item.saleKbn!=2}"><i class="elt_icon" onclick="ajaxUpdatePrice('${item.id}',2);"></i></c:if></p>
				                                </c:if>
				                                
			                                    <p class="approve">
			                                	  <c:choose>
			                                	    <c:when test="${selltype=='selling'}">
			                                	      <a href="javascript:void(0);" onclick="subCheckAlert('${item.fullName}','${item.id}','sellOff');">下架</a>
			                                	    </c:when>
			                                	    <c:when test="${selltype=='waitsell'}">
			                                	      <c:if test="${item.savestate!=1 && item.saleKbn!=2}"><a href="javascript:void(0);" onclick="subCheckAlert('${item.fullName}','${item.id}','sellOn');">上架</a></c:if>
			                                	    </c:when>
			                                	    <c:when test="${selltype=='waitcheck' || selltype =='reject'}">
			                                	      <a href="javascript:void(0);" onclick="subCheckAlert('${item.fullName}','${item.id}','cancel');">取消</a>
			                                	    </c:when>
			                                	  </c:choose>
			                                    </p>
				                             </c:if>
			                                </div>
			                                <c:if test="${selltype =='waitsell'}"><div class="time"></c:if>
			                                <c:if test="${selltype =='selling'}"><div class="sort_time"></c:if>
			                                
			                                <c:if test="${selltype =='waitcheck' || selltype =='reject'}"><div class="pro_time"></c:if>
			                                <c:choose>
			                                	<c:when test="${selltype =='selling'}">
			                                	<c:if test="${item.selfType==1}">
			                                		<p>线下销售</p>
			                                	</c:if>
			                                	<c:if test="${item.selfType!=1}">
			                                	<p><fmt:formatDate value="${item.selfTime}" pattern="yyyy-MM-dd" /></p>
			                                    <p><fmt:formatDate value="${item.selfTime}" pattern="HH:mm:ss" /></p>
			                                    </c:if>
			                                    </c:when>
			                                    <c:otherwise>
			                                	<p><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd" /></p>
			                                    <p><fmt:formatDate value="${item.createDate}" pattern="HH:mm:ss" /></p>
			                                    </c:otherwise>
			                                </c:choose>
			                                </div>
			                                <c:if test="${selltype =='selling'}">
				                                <div class="sort">
													<input class="sort_input" onkeyup="this.value=this.value.replace(/\D/g,'')" id="productViewSort_${item.id}" value="${item.sortNum }">
				                                	<input class="sort_button" type="button" value="更改" onclick="changeSort(${item.id})">
				                                </div>
			                                </c:if>
			                                <c:if test="${selltype =='waitcheck' || selltype =='reject'}">
					                       		<div class="pro_approve">
		                                				<c:if test="${item.status ==1}"><p>审批中</p></c:if>
		                                				<c:if test="${item.status ==-1}"><strong>未通过</strong><p><a href="javascript:void(0);" onclick="reasonView('${item.id}');">查看原因</a></p></c:if>
		                                		</div>
					                       </c:if>                    
		                       				<c:if test="${selltype =='selling'}">
			                                <div class="operate">
			                               
			                                 <c:if test="${item.locked == 0}">
			                                	<p><a href="${basePath}/product/toCreateProduct.html?productId=${item.id}&selltype=${selltype}">修改商品</a></p>
			                                    <p><a href="javascript:void(0);" onclick="deleteAllObject(${item.id},-10)">删除</a></p>
				                                </c:if>
			                                    <p><a href="${basePath}/product/productView.html?productId=${item.id}"  target="_blank">预览</a></p>
			                                    <p><a href="${basePath}/product/toCreateProduct.html?productId=${item.id}&selltype=createproduct&productcopy=copy">商品复制</a></p>
			                                </c:if>
		                                  	<c:if test="${selltype !='selling'}">
			                                 <div class="sort_operate">
			                                
		                                 		<c:if test="${item.locked == 0}">
			                                	<p><a href="${basePath}/product/toCreateProduct.html?apprid=${item.id}&selltype=${selltype}">修改商品</a></p>
			                                    <p><a href="javascript:void(0);" onclick="deleteAllObject(${item.id},-10)">删除</a></p>
			                                	</c:if>
			                                    <p><a href="${basePath}/product/productView.html?apprid=${item.id}"  target="_blank">预览</a></p>
			                                </c:if>
			                              </div>
			                            </div>
			                              
			                              
			                        </li>
		                    	</c:forEach>
		                    </ul>
		                </div>
                     
                <div class="sale_title">
                	<span><input class="redio" type="checkbox"  id="total" onclick="checkTotal(this);check();">全选</span>
		            <c:if test="${selltype !='reject'}">
		            <c:if test="${selltype =='selling'}"><div class="p_submit"><a href="javascript:void(0);" onclick="batchSellOff()" >批量下架</a></div></c:if>
		            <c:if test="${selltype =='waitsell'}"><div class="p_submit"> <a href="javascript:void(0);" onclick="batchSellOnAlert()" >批量上架</a></div></c:if>
		            <c:if test="${selltype =='waitcheck'}"><div class="p_submit"><a href="javascript:void(0);" onclick="batchCheckcancelAlert();" >批量取消</a></div></c:if>		             
		             </c:if>
                    <div class="p_delete"><a href="javascript:void(0);" onclick="deleteAllObject(0,-10);" >批量删除</a></div>
                </div>
                 <wodepageform:PageFormTag pageSize="${result.size}"  totalPage="${result.totalPage}" currentPage="${result.page}" url=""/>
                    </c:when>
                	<c:otherwise>
                		<div class="sale_title bottom_line">
		                    <div class="add_new"><a href="${basePath}/product/toSelectProducttype.html"><i class="addico"></i>添加新商品</a></div>
	                	</div>
	                	<p class="h-result">未查询到符合条件的商品，请修改检索条件重新查询！</p></c:otherwise>
                </c:choose>
            </div>       
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<!--商品快捷修改-弹出框 begin-->
<div class="popup_bg"></div>
<div class="shop_popup" style="width:828px;" id="shop_popup">
	<div class="popup_title">
    	<span>商品快捷修改</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="cancelButton('shop_popup');"></label>
    </div>
    <div class="popup_cont" id="alertPriceDiv" style="width:788px;" >
    	<img alt="加载中" src="<%=static_resources %>images/loading_updateproduct.gif">
    </div>
</div>
<!--商品快捷修改-弹出框 end-->


<!--确认上架 begin-->
<div class="shop_popup" id="shop_popup_true">
	<div class="popup_title">
    	<span><c:if test="${selltype=='selling'}">确认下架</c:if><c:if test="${selltype=='waitsell'}">确认上架</c:if><c:if test="${selltype=='waitcheck' || selltype =='reject'}">确认取消审核</c:if></span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"  onclick="cancelButton('shop_popup_true');"></label>
    </div>
    <div class="popup_cont">	
		<div class="popup_txt">确认把商品  "<span id="alertproductname"></span>" <c:if test="${selltype=='selling'}">下架吗？</c:if><c:if test="${selltype=='waitsell'}">上架吗？</c:if>
		<c:if test="${selltype=='waitcheck' || selltype =='reject'}">取消审核吗？</c:if></div>
        <div class="popup_btn">
            <input type="hidden" class="alertproductid" value=""/>
            <a href="javascript:void(0);" class="btn_ok" onclick="subforCheck(this);">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('shop_popup_true');">取消</a>
        </div>
	</div>
</div>
<!--确认上架 end-->

<!--确认上架 begin-->
<div class="shop_popup" id="shop_popup_true_all">
	<div class="popup_title">
    	<span><c:if test="${selltype=='selling'}">确认下架</c:if><c:if test="${selltype=='waitsell'}">确认上架</c:if><c:if test="${selltype=='waitcheck' || selltype =='reject'}"></c:if><c:if test="${selltype=='waitcheck' || selltype =='reject'}">确认取消审核</c:if></span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"  onclick="cancelButton('shop_popup_true_all');"></label>
    </div>
    <div class="popup_cont">	
		<div class="popup_txt">确认把选中的商品 <c:if test="${selltype=='selling'}">下架吗？</c:if><c:if test="${selltype=='waitsell'}">上架吗？</c:if>
		<c:if test="${selltype=='waitcheck' || selltype =='reject'}">取消审核吗？</c:if></div>
        <div class="popup_btn">
            <a href="javascript:void(0);" class="btn_ok">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('shop_popup_true_all');">取消</a>
        </div>
	</div>
</div>
<!--确认上架 end-->

<!--确认删除begin-->
<div class="shop_popup" id="shop_popup_delete">
	<div class="popup_title">
    	<span>提示</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"  onclick="cancelButton('shop_popup_delete');"></label>
    </div>
    <div class="popup_cont">	
		<div class="popup_txt">确认删除该商品吗？</div>
        <div class="popup_btn">
            <input type="hidden" id="id_del" value=""/>
            <input type="hidden" id="isMarketable_del" vlaue="">
            <a href="javascript:void(0);" onclick="subforDelete();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('shop_popup_delete');">取消</a>
        </div>
	</div>
</div>
<!--确认删除 end-->

<!--确认取消begin-->
<div class="shop_popup" id="shop_popup_cancel">
	<div class="popup_title">
    	<span>提示</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"  onclick="cancelButton('shop_popup_delete');"></label>
    </div>
    <div class="popup_cont">	
		<div class="popup_txt">确认取消该商品的审核吗？</div>
        <div class="popup_btn">
            <input type="hidden" id="id_cancel" value=""/>
            <input type="hidden" id="status_cancel" vlaue="">
            <a href="javascript:void(0);" onclick="subforCancel();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('shop_popup_cancel');">取消</a>
        </div>
	</div>
</div>
<!--确认取消 end-->

<!--商品上架未通过 begin-->
<div class="shop_popup" id="shop_popup_fail">
	<div class="popup_title">
    	<span>商品上架未通过</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"  onclick="cancelButton('shop_popup_fail');"></label>
    </div>
    <div class="popup_cont">
    </div>
</div>
<!--商品上架未通过 end-->


<script>
var jsBasePath='${basePath}';

var jsSelltype='${selltype}';
</script>

<script type="text/javascript" src="<%=static_resources %>js/product_product_productlist.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>