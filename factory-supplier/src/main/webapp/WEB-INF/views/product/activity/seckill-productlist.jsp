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
<title>我的网商家中心-活动列表</title>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
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
    
    <!--right begin-->
    <div class="right">    	
        <div class="merchant_info">
        	<div class="process step_1"></div>
            <div class="s1-wrap">
                <div class="s1-step s2-mar">第二步 商品选定</div>
                <div class="s2-cont">
                	<div class="s2-title"> 
                      <form id="sub_form" action="${basePath}/promotion/productlist.html" method="post">
                    	<span class="title">商品类目：</span>
                    	<input type="hidden" name="categoryidtemp" id="categoryidtemp" value="${categoryid}"/>
                        <select class="pubilc_input f226" style="margin-top: 14px;margin-right: 14px;" id="categoryid" name="categoryid">
                        	<option value="">--请选择--</option>
                        </select>
                     	<span class="title">商品名称：</span><input class="s-input s2w218" type="text" id="name" name="name" value="${name}">
                        <input type="hidden" id="pages" name="pages" value="${pages}"/>
		     	 		<input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
		     	 		<input type="hidden" id="bmTime" name="bmTime" value="${bmTime}"/>
		     	 		<input type="hidden" id="promotionId" name="promotionId" value="${promotionId}"/>
                        <div class="s2-search"><a href="javascript:void(0);" onclick="$('#sub_form').submit();">搜索</a></div>
                      </form>
                    </div>
                    <ul class="s2-tl-list">
                    	<li class="li01">宝贝描述</li>
                        <li class="li02">商品价格</li>
                        <li class="li03">操作</li>
                    </ul>
                    <ul class="s2-cont-list">
                       <c:forEach var="item" items="${result.msgBody}">
			               <li>
	                        	<div class="s2-wrap">
	                            	<div class="s2-img">
	                            		<c:if test="${item.pSaleKbn==1 }">
								   		<div class="picon"><img src="<%=static_resources %>images/picon2.png" /></div>
								   		</c:if>
								  		<c:if test="${item.pSaleKbn==2 }">
								   		<div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
								   		</c:if>
								   		<c:if test="${item.pSaleKbn==4 }">
								   		<div class="picon"><img src="<%=static_resources %>images/picon_z2.png" /></div>
								   		</c:if>
								  		<c:if test="${item.pSaleKbn==5 }">
								   		<div class="picon"><img src="<%=static_resources %>images/picon_t2.png" /></div>
								   		</c:if>
	                            		<a href="#"><img src="${item.productImage}" width="78" height="78" alt="Me-order-img"></a>
	                            	</div>
	                                <p class="s2-ft">${item.productName}(${item.itemValues})</p>
	                            </div>
	                            <span class="s2-price">${item.price}</span>
	                            <div class="s2-btn-box"><div class="s2-btn"><a href="javascript:productset(${item.id});">选定</a></div></div>
	                        </li>
		                </c:forEach>
                    </ul>
                    <wodepageform:PageFormTag pageSize="${result.size}"  totalPage="${result.totalPage}" currentPage="${result.page}" url=""/>
                </div>
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->
<script type="text/javascript">
   var jsBasePath = '${basePath}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_activity_seckillProductlist.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>