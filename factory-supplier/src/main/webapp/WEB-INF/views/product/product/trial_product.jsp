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
    <title>我的网---试用商品问卷</title>
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
    	  <c:if test="${leftMenu != 'order'}">
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
				<li><a href="${basePath}/product/exchageProduct.html">换领商品管理</a></li>
			</c:if>
		  </c:if>
    	  <c:if test="${leftMenu == 'order'}">
             <c:if test="${userSession.type != 3 || userSession.hasAuth('sellList')}">
                 <li><a href="${basePath}/suborder/gotoSelllist.html">已售出的商品</a></li>
             </c:if>
             <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
                 <li><a href="${basePath}/comments/toevaluation.html?commentDegree=all">评价管理</a></li>
             </c:if>
            <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
                <li><a href="${basePath}/questionnaire/templates.html">问卷模板</a></li>
            </c:if>
		  </c:if>
			
		  <c:if test="${userSession.type != 3 || userSession.hasAuth('addProduct')}">
			<li class="curr"><a href="${basePath}/questionnaire/trialProduct.html?leftMenu=${leftMenu}">试用商品问卷</a></li>
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
            <a href="javascript:void(0);">商品管理</a><em>></em>
            <a href="javascript:void(0);">试用商品问卷</a>
        </div>
        <div class="sale_wrap">
		<form action="${basePath}/questionnaire/trialProduct.html" method="post" id="sub_form">
       	 	<input type="hidden" id="pageNumber" name="pageNumber" value="${query.pageNumber}"/>
    	    <input type="hidden" id="pageSize" name="pageSize" value="${query.pageSize}"/>
    	    <input type="hidden" name="leftMenu" value="${leftMenu}"/>
    	    
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
                           	<option value="2" <c:if test="${query.status == 2 }">selected="selected"</c:if>>进行中</option>
                           	<option value="3" <c:if test="${query.status == 3 }">selected="selected"</c:if>>已结束</option>
                           	<option value="-1" <c:if test="${query.status == -1 }">selected="selected"</c:if>>已删除</option>
                        </select>
                    </div>
                    <div class="sale_option">
                    	<div class="title">创建时间</div>
                    	<input class="pubilc_input f91 r-num" type="text" id="startDate" name="startDate" readOnly="readOnly" value="${query.startDate}"  onClick="WdatePicker()">
                    	<span class="s_zhi">到</span>
                    	<input class="pubilc_input f91 r-num" style="margin-left:5px" type="text" id="endDate" name="endDate" readOnly="readOnly" value="${query.endDate}"  onClick="WdatePicker()">
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
                <li class="s2">返现金额</li>
                <li class="s3">回答次数</li>
                <li class="s4">结束日期</li>
                <li class="s5">状态</li>
                <li class="s6">操作</li>
            </ul>
		    <ul class="sale_infomation_list">
		      <c:forEach items="${page.list}" var="item" varStatus="status">
		      	<li>
		      		<div class="sale_sort_tl">
		      			<span>商品名称：${item.productName}</span>
		      		</div>
					<div class="infowrap">
						<div class="shop_sort_info">
							<div class="s_img">
								<div class="picon"><img src="<%=static_resources %>images/picon_t2.png" /></div>
								<a href="${basePath}/product/productView.html?productId=${item.productId}"  target="_blank"><img src="${item.productImg}"  alt="Me-order-img"></a>
			                </div>
			                <p>试用商品总数：${item.productCnt}</p>
			                <p>价格(含内购券)：￥<fmt:formatNumber value="${item.minprice}" pattern="#,##0.00#"/>--<fmt:formatNumber value="${item.maxprice}" pattern="#,##0.00#"/>
			                </p>
			            </div>
			            <span>￥<fmt:formatNumber value="${item.empPrice}" pattern="#,##0.00#"/></span>
			            <div class="stock">
			            	<p>${item.answerCnt}</p>
			            </div>
			            <div class="sort_time">
			            	<p><fmt:formatDate value="${item.endDate}" pattern="yyyy-MM-dd" /></p>
			            </div>
			      		<div class="sort">
			      		  <c:choose>
			      		  	<c:when test="${item.status==2}">
			            		<p>进行中</p>
			      		  	</c:when>
			      		  	<c:when test="${item.status==3}">
			            		<p>已结束</p>
			      		  	</c:when>
			      		  	<c:when test="${item.status==-1}">
			            		<p>已删除</p>
			      		  	</c:when>
			      		  	<c:otherwise>
			            		<p>待审核</p>
			      		  	</c:otherwise>
			      		  </c:choose> 		  
				        </div>
				        <div class="operate">
				          <c:if test="${item.status != 1 }">
                        	<p><a href="${basePath}/questionnaire/result.html?leftMenu=${leftMenu}&qId=${item.id}" target="_blank">查看</a></p>
                          </c:if>
				          <c:if test="${item.status == 2 && item.empPrice>0}">
                            <p><a href="javascript:void(0);" onclick="stopShow(${item.id})">停止使用</a></p>
                          </c:if>
				          <c:if test="${item.status == 3 }">
                            <p><a href="javascript:void(0);" onclick="delShow(${item.id})">删除</a></p>
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
</div>


<script type="text/javascript">
	$(document).ready(function () {
		if('${leftMenu}'=='order') {			
			selectedHeaderLi("ddgl_header");
		} else {
			selectedHeaderLi("spgl_header");
		}
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
<script type="text/javascript" src="<%=static_resources %>js/product_product_trial.js"></script>
<!-- 页脚 begin -->	
<%@ include file="/commons/alertMessage.jsp" %>
<%@ include file="/commons/footer.jsp"%>
<!-- 页脚 end -->	
</body>
</html>
