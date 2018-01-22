<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
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
    <title>我的网商家中心</title>
    <%@ include file="/commons/js.jsp" %>

    <style type="text/css">
    
.btngray a{background:#959595; border-radius:4px; behavior:url(iecss3/PIE.htc); position:relative; z-index:2;}
	.store { font:12px/24px "Microsoft YaHei"; color:#434343; }
	.col{color:#717ea5;font-size:14px;font-weight:bold;}
.contact_lt { width:100%; float:left; margin-top:4px; font:12px/30px "Microsoft YaHei"; color:#434343; }
.contact_lt span { width:100px; text-align:right; float:left; }
.contact_lt strong { float:left; font-weight:normal; }
.c_table table { border-collapse:collapse; float:left; width:850px; margin-top:10px; }
.c_table table tr td { width:210px; height:38px; background:#fff; border:1px solid #e5e5e5; text-align:center; font-family:"Microsoft YaHei"; color:#434343; }
.c_table table .tr_title td { height:28px; background:#f9f9f9; }
.page { float:right; height:30px; margin:30px 144px 0 0; }
.page .page_curr { background:#eee; }
.page span { font:14px "Microsoft YaHei"; color:#6a6a6a; display:inline-block; margin-left:4px; }
.page a { background:#fff; display:inline-block; padding:0 10px; margin-left:4px; border:1px solid #e5e5e5; font:14px/28px "Microsoft YaHei"; color:#434343; text-align:center; border-radius:2px; behavior:url(iecss3/PIE.htc); position:relative; z-index:2; }
.page a:hover { background:#e5e5e5; }
.page .input_text { width:38px; height:28px; border:1px solid #e5e5e5; text-align:center; border-radius:2px; }
.input_btn { width:58px; height:28px; font:14px "Microsoft YaHei"; color:#434343; border:1px solid #e5e5e5; text-align:center; background:#fff; cursor:pointer; border-radius:2px; }
.protocol { float:left; width:100%; margin-top:20px; font:12px/30px "Microsoft YaHei"; color:#434343; }
.protocol a:link { font:12px/30px "Microsoft YaHei"; color:#2b8dff; }
.protocol a:visited { font:12px/30px "Microsoft YaHei"; color:#2b8dff; }
.protocol a:hover {}
.hupimg { margin-top:36px; }
	</style>
</head>
<body>
<!--top begin-->
<%@ include file="/commons/header.jsp" %>
<!--header end-->

<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <div class="left_store_list">
            <ul>

                <c:forEach items="${shopList}" var="shop">
                 <li><a href="javascript:"><i class="icon01"></i>${shop.shopname}</a>
                 	<ul class="twomenu" ${ss.id==shop.id ? 'style="display:block;"' : ''}>
		                <li><a href="${basePath}/shopSetting.html?id=${shop.id}">基本信息</a></li>
		                <c:if test="${userSession.type != 3 || userSession.hasAuth('pageSet')}">
		                    <li><a href="${basePath}/shopSetting/pageset.html?id=${shop.id}">店铺页面设置</a></li>
		                </c:if>
		                <c:if test="${userSession.type != 3 }">
		                    <li <c:if test="${ss.id==shop.id}">class="active"</c:if>><a href="${basePath}/shopSetting/categoryBrand.html?id=${shop.id}">品牌及分类</a></li>
		                </c:if>
                    </ul>
               	</c:forEach>
                <c:if test="${userSession.type != 3 || userSession.hasAuth('category')}">
                    <li ><a href="${basePath}/category/list.html?">分类管理</a></li>
                </c:if>
               	
                <c:if test="${userSession.type != 3 || userSession.hasAuth('productCategory')}">
                    <li><a href="javascript:"><i class="icon01"></i>商品归类</a>
                        <ul class="twomenu">
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('allProducts')}">
                                <li><a href="${basePath}/shopSetting/products.html?mark=all">全部商品</a></li>
                            </c:if>
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('unCategoryProducts')}">
                                <li><a href="${basePath}/shopSetting/products.html?mark=TMP">未分类商品</a></li>
                            </c:if>
                            <c:if test="${userSession.type != 3 || userSession.hasAuth('categoryProducts')}">
                                <li><a href="javascript:"><i class="icon02"></i>已分类商品</a></li>
                                <ul class="threemenu">
                                    <c:forEach items="${allCats}" var="cat">
                                        <c:choose>
                                            <c:when test="${not empty cat.children}">
                                                <li><a href="javascript:"><i class="icon03"></i>${cat.name}</a>
                                                    <ul class="fourmenu">
                                                        <c:forEach items="${cat.children}" var="child">
                                                            <li><a href="${basePath}/shopSetting/products.html?mark=${child.id}">${child.name}</a></li>
                                                        </c:forEach>
                                                    </ul>
                                                </li>
                                            </c:when>
                                            <c:otherwise>
                                                <li><a href="javascript:">${cat.name}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </ul>
                    </li>
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
            <a href="${basePath}/shopSetting.html">我的店铺</a><em>></em>
            <a href="javascript:;">分类及品牌</a>
        </div>
        
        <input type="hidden" name="id" value="${ss.id}"/>
       	<div class="sale_wrap">
            <div class="sort_title">合同信息</div>
            <div class="store col">我的网服务信息</div>
	        <div class="contact_lt">
	        	<span>合作模式：</span>
	            <strong>${mode }</strong>
	        </div>
	        <div class="contact_lt">
	        	<span>甲方：</span>
	            <strong>${comName }</strong>
	            <span>乙方：</span>
	            <strong>北京我的网科技有限公司</strong>
	        </div>
	        <div class="contact_lt">
	        	<span>店铺名称：</span>
	            <strong>${ss.shopname}&nbsp;&nbsp;&nbsp;&nbsp;(<c:if test="${empty ss.type || ss.type eq 0 }">专营店</c:if><c:if test="${ss.type eq 1 }">专卖店</c:if><c:if test="${ss.type eq 2 }">旗舰店</c:if>)</strong>
	        	<c:if test="${apprS eq 0 }">
	        	<div class="add_new add_new_a ft_lt" style="margin-left:50px" onclick="edit(0);" id="editShop">
			         <a href="javascript:void(0);">修改(品类及品牌)</a>
			    </div>
			    </c:if>
	        </div>
	        <div class="contact_lt">
	        	<span>协议期限：</span>
	            <strong><fmt:formatDate value="${begin}" pattern="yyyy-MM-dd"/>至<fmt:formatDate value="${end}" pattern="yyyy-MM-dd"/></strong>
	        </div>
	        <div class="contact_lt">
	        	<span>保证金金额：</span>
	            <strong class="redcolor">${cashDeposit}元</strong>
	        </div>
	        <!-- 
	        <div class="contact_lt">
	        	<span>消费者保障基金：</span>
	            <strong class="redcolor">0.1%</strong>
	        </div>
	         -->
	        <div class="contact_lt">
	        	<span>经营范围：</span>
	            <div class="c_table" style="overflow:hidden;width:900px;margin-left:40px;">
	            <table width="100%" border="0" cellpadding="0" cellspacing="0">
	              <tr class="tr_title">
	                <td>经营品类
	                	<div class="add_new add_new_a ft_rt " style="margin-right:10px;height:20px;" onclick="edit(1);" id="editCategory">
					         <a href="javascript:void(0);" style="height:20px;line-height:20px;">修改/添加</a>
					    </div>
	                </td>
	                <td>佣金扣点(%)</td>
	                <td>优惠</td>
	                <td>经营品牌名称
	                	<div class="add_new add_new_a ft_rt " style="margin-right:10px;height:20px;" onclick="edit(2);" id="editBrand">
					         <a href="javascript:void(0);" style="height:20px;line-height:20px;">修改/添加</a>
					    </div>
	                </td>
	              </tr>
	              <c:forEach var="item" items="${csList}">
		              <tr>
		                <td>${item.categoryName}</td>
		                <td>${item.commissionRatio}</td>
		                <td>返佣</td>
		                <td><c:forEach var="item1" items="${item.productBrandList}">${item1.name} </c:forEach></td>
		              </tr>
	              </c:forEach>
	            </table>
				</div>
	        </div>
	        <div class="explain_box">
	        	<span><i>*</i>&nbsp;注</span>
	        	<p>佣金返还细则：</p>
	        	<ul>
	        		<li>1、我的网在以账期为单位结算技术服务费（也称佣金）的同时，向商家在“我的网平台”的现金储值账户中将佣金按比例进行折让并计入商家的现金储值总额，可用于再次向员工进行发放。</li>
	        		<li>（1） 在当期结算账期内，品牌商商家以商家自身员工购买其他商家商品并完成妥投的销售额（不含内购券）加和为基数，按所购买商品的佣金点位的百分之五十计算，以现金储值形式返还。（员工所购买商品佣金点位为2%及以下的，商家不享受佣金返还。）</li>
	        		<li>（2） 若商家为品牌代理商，商家介绍品牌员工成功注册并上线我的网平台的，首年度可获得员工所购买的其他商家的商品的佣金点位的百分之五十的佣金返还，以现金储值形式存入商家在我的网平台账户中。（员工所购买商品佣金点位为2%及以下的，商家不享受佣金返还。）</li>
	        		<li>（3） 佣金返还总额合计不超过商家实缴佣金的一半。</li>
	        	</ul>
	        </div>
	        <div class="clear"></div>
			<br />
			<br />
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="/commons/footer.jsp" %>
<!--footer end-->
<%@ include file="/commons/box.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/menu_min.js"></script>


<script type="text/javascript">
var jsBasePath='${basePath}';
var jsEditId="${editId}";
var jsSsId="${ss.id}";
var jsApprStatus='${apprStatus}';
var jsApprType='${apprType}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_shopsetting_categoryBrand.js"></script>
</body>
</html>
