<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>我的网---换领商品管理</title>
    <%@ include file="/commons/js.jsp" %>
	<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<!-- top start -->
<%@ include file="/commons/header.jsp" %>
<!-- top end -->
<div id="content" class="clear">
    <!--left begin-->
    <div class="left">
    <div class="left">
    	<ul class="left_list">
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/product/toSelectProducttype.html">添加新商品</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productOnsell')}">
				<li><a href="${basePath}/product/gotoProductlist.html?selltype=selling">在售商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productWaitSell')}">
				<li><a href="${basePath}/product/gotoProductlist.html?selltype=waitsell">待售商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productWaitSell')}">
				<li><a href="${basePath}/product/gotoProductlist.html?selltype=reject">问题商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productOncheck')}">
				<li><a href="${basePath}/product/gotoProductlist.html?selltype=waitcheck">待审批商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li>
			</c:if>
			<%-- <li><a href="${basePath}/supplierProductExcel/fetchProductExcel.html">商品批量上传</a></li> --%>
			<li style="background-color: #f5f5f5">&nbsp;&nbsp;</li>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li class="curr"><a href="${basePath}/product/exchageProduct.html">换领商品管理</a></li>
			</c:if>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
				<li><a href="${basePath}/questionnaire/trialProduct.html">试用商品问卷</a></li>
			</c:if>
        </ul>
    </div>
    </div>
    <!--left end-->
    
    <!--right begin-->
    <div class="right">
    	<div class="position">
        	<span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/product/toSelectProducttype.html">商品管理</a><em>></em>
            <a href="javascript:void(0);">换领商品管理</a>
        </div>
        <div class="sale_wrap">
		<form action="${basePath}/product/exchageProduct.html" method="post" id="sub_form">
       	 	<input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
    	    <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
    	    <input type="hidden" id="type" name="type" value="${type}"/>
             <div class="sale_info">
                <div class="sale_one">
                	<div class="sale_option">
                    	<div class="title">商品名称：</div>
                        <input class="pubilc_input f218" type="text" id="productName" name="productName" value="${query.productName}" maxLength="20">
                    </div>
                	<div class="sale_option">
                    	<div class="title">状态</div>
                		<select class="pubilc_input" name="status"  id="status">
                           	<option value="">全部</option>
                           	<option value="1" <c:if test="${query.status == 1 }">selected="selected"</c:if>>待审核</option>
                           	<option value="2" <c:if test="${query.status == 2 }">selected="selected"</c:if>>换领中</option>
                           	<option value="3" <c:if test="${query.status == 3 }">selected="selected"</c:if>>已结束（领完）</option>
                           	<option value="4" <c:if test="${query.status == 4 }">selected="selected"</c:if>>提前终止</option>
                           	<option value="5" <c:if test="${query.status == 5 }">selected="selected"</c:if>>已结束（终止）</option>
                           	<option value="7" <c:if test="${query.status == 7 }">selected="selected"</c:if>>已结束（到期）</option>
                           	<option value="9" <c:if test="${query.status == 9 }">selected="selected"</c:if>>线下发放</option>
                        </select>
                    </div>
                    <div class="sale_option">
                    	<div class="title">使用期限</div>
                    	<input class="pubilc_input f91 r-num" type="text" id="startDate" name="limitStart" readOnly="readOnly" value="${query.limitStart}"  onClick="WdatePicker()">
                    	<span class="s_zhi">到</span>
                    	<input class="pubilc_input f91 r-num" style="margin-left:5px" type="text" id="endDate" name="limitEnd" readOnly="readOnly" value="${query.limitEnd}"  onClick="WdatePicker()">
                    </div>
                </div>
            </div>
        </form>
		<div class="clear"></div>
		<div class="btnwrap">
			<div class="checkbtn"><a href="javascript:void(0);"  onclick="formSubmit('1');">查询</a></div>
			<div class="resetbtn"><a href="javascript:void(0);" onclick="formReset();">重置</a></div>
		</div>
          
      	<div class="sale_list_wrap">
          <div class="sale_content">
          	<ul class="sale_them">
            	<li class="s1">商品信息</li>
                <li class="s2">换领币</li>
                <li class="s3">换领件数(含预定)</li>
                <li class="s4">期限</li>
                <li class="s5">状态</li>
                <li class="s6">操作</li>
            </ul>
		    <ul class="sale_infomation_list">
		      <c:forEach items="${page.list}" var="item" varStatus="status">
		      	<li>
		      		<div class="sale_sort_tl">
		      			<span>商品名称：${item.productName}
		      			<c:if test="${item.productId == -1}">
									(虚拟商品)
								</c:if></span>
		      		</div>
					<div class="infowrap">
						<div class="shop_sort_info">
							<div class="s_img">
								<div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
								<c:if test="${item.productId == -1}">
									<img src="${item.productImg}"  alt="Me-order-img">
								</c:if>
								<c:if test="${item.productId != -1}">
									<a href="${basePath}/product/productView.html?productId=${item.productId}"  target="_blank"><img src="${item.productImg}"  alt="Me-order-img"></a>
			                	</c:if>
			                </div>
			                <p>换领商品总数：${item.productCnt}</p>
			                <p>换领原因：
			                  	${item.saleNote}
			                </p>
			                <p>内购价格：${item.productPrice}元</p>
			            </div>
			            <span>
			            	<p>平均<fmt:formatNumber value="${item.empAvgAmount}" pattern="#,##0.00#"/>换领币</p>
			            	<c:if test="${item.productPrice.unscaledValue() != 0.00}">
			            		<p>约<fmt:formatNumber type="number" value="${item.empAvgAmount/item.productPrice}" pattern="0.0" maxFractionDigits="1"/> 件</p>
			            	</c:if>
			            	<c:if test="${item.productPrice.unscaledValue() == 0.00}">
			            		<p>约0.0 件</p>
			            	</c:if>
			            	<p>${item.empCnt}人领用</p>
			            </span>
			            <div class="stock">
			            	<p>${item.exchangeSuNum+item.reservedNum}</p>
			            </div>
			            <div class="sort_time">
			            	<p><fmt:formatDate value="${item.limitStart}" pattern="MM-dd" />--<fmt:formatDate value="${item.limitEnd}" pattern="MM-dd" /></p>
			            </div>
			      		<div class="sort">
			      		  <c:choose>
			      		  	<c:when test="${item.status==2}">
			            		<p>换领中</p>
			      		  	</c:when>
			      		  	<c:when test="${item.status==3}">
			            		<p>已结束（领完）</p>
			      		  	</c:when>
			      		  	<c:when test="${item.status==4}">
			            		<p>提前终止</p>
			      		  	</c:when>
			      		  	<c:when test="${item.status==5}">
			            		<p>已结束（终止）</p>
			      		  	</c:when>
			      		  	<c:when test="${item.status==7}">
			            		<p>已结束（到期）</p>
			      		  	</c:when>
			      		  	<c:when test="${item.status==9}">
			            		<p>线下已发放</p>
			      		  	</c:when>
			      		  	<c:otherwise>
			            		<p>待审核</p>
			      		  	</c:otherwise>
			      		  </c:choose> 		  
				        </div>
				        <div class="operate">
				          <c:if test="${item.status > 1 }">
                        	<p><a href="${basePath}/exchangeProduct/exchangeHis.html?ticketId=${item.id}" target="_blank">换领币记录</a></p>
                          </c:if>
				          <c:if test="${item.status == 2 }">
                            <p><a href="javascript:void(0);" onclick="stopShow(${item.id})">提前结束</a></p>
                            <p><a href="javascript:void(0);" onclick="delayShow(${item.id})">延长期限</a></p>
                          </c:if>
                          <c:if test="${item.productId != -1}">
                          <c:if test="${item.status > 1 }">
                        	<p><a href="${basePath}/exchangeProduct/exchangeProductHis.html?ticketId=${item.id}" target="_blank">换领记录</a></p>
                          </c:if>
                          </c:if>
			           	</div>
			    	</div>
            	</li>
              </c:forEach>
            </ul>
		                    
         </div>
        </div>
        <wodepageform:PageFormTag pageSize="${page.pageSize}"  totalPage="${page.pages}" currentPage="${page.pageNum}" url=""/>
        </div>
    </div>
    <!--right end-->
    <input type="hidden" id="id_sel" value=""/>
</div>

<div class="popup_bg"></div>
<!--确认删除begin-->
<div class="shop_popup" id="shop_popup_delete">
	<div class="popup_title">
    	<span>提示</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"  onclick="cancelButton('shop_popup_delete');"></label>
    </div>
    <div class="popup_cont">	
		<div class="popup_txt">确认提前终止该商品的换领吗？终止后商品下架，员工账户中的换领币停止使用</div>
        <div class="popup_btn">
            <a href="javascript:void(0);" onclick="doStop();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('shop_popup_delete');">取消</a>
        </div>
	</div>
</div>
<!--确认删除 end-->

<!--确认删除begin-->
<div class="shop_popup" id="shop_popup_offline">
	<div class="popup_title">
    	<span>提示</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"  onclick="cancelButton('shop_popup_offline');"></label>
    </div>
    <div class="popup_cont">
		<div class="popup_txt">确认要线下发放剩余换领商品吗？</div>
        <div class="popup_btn">
            <a href="javascript:void(0);" onclick="doOffline();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('shop_popup_offline');">取消</a>
        </div>
	</div>
</div>
<!--确认删除 end-->


<!--确认删除begin-->
<div class="shop_popup" id="shop_popup_delay">
	<div class="popup_title">
    	<span>提示</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"  onclick="cancelButton('shop_popup_delay');"></label>
    </div>
    <div class="popup_cont">
		<div class="popup_txt">请选择延长时间：
			<input type="radio" name="rdDelay" value="1" id="rdDelay1" checked="checked"><label for="rdDelay1">1个月</label>
			<input type="radio" name="rdDelay" value="3" id="rdDelay3"><label for="rdDelay3">3个月</label>
			<input type="radio" name="rdDelay" value="5" id="rdDelay5"><label for="rdDelay5">半个月</label>
		</div>
        <div class="popup_btn">
            <a href="javascript:void(0);" onclick="doDelay();">确认</a>
            <a href="javascript:void(0);" onclick="cancelButton('shop_popup_delay');">取消</a>
        </div>
	</div>
</div>
<!--确认删除 end-->


<script type="text/javascript">
	$(document).ready(function () {
		selectedHeaderLi("spgl_header");
	});

	/*** 快速跳转*/
	function gotoPage(){
		var pagenum = $("#pagenum").val();
		formSubmit(pagenum);
	}
	/*** 表单提交*/
	function formSubmit(page){
		if(page!=undefined){
			$("#pageNumber").val(page);
		}else{
			$("#pageNumber").val(1);
		}
		$("#sub_form").submit();
	}
    /**重置**/
    function formReset() {      
        $("#sub_form").find("input[type!='hidden']").val("");
        $("#sub_form").find("select").find("option:first").attr("selected", "selected");
    }
    var jsBasePath='${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_product_exchange.js"></script>
<!-- 页脚 begin -->	
<%@ include file="/commons/footer.jsp"%>
<%@ include file="/commons/box.jsp" %>
<!-- 页脚 end -->	
</body>
</html>
