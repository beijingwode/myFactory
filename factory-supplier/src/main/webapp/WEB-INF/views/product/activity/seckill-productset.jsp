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
    <form id="sub_form" action="${basePath}/promotion/promotionSkuSet.html" method="post">
    <input type="hidden" id="promotionId" name="promotionId" value="${promotionId}"/>
    <input type="hidden" id="bmTime" name="bmTime" value="${bmTime}"/>
    <input type="hidden" id="oldPrice" name="oldPrice" value="${productSpecifications.price}"/>
    <input type="hidden" id="productId" name="productId" value="${productSpecifications.productId}"/>
    <input type="hidden" id="skuId" name="skuId" value="${productSpecifications.id}"/>
    <input type="hidden" id="quantity" name="quantity" value="${productSpecifications.quantity}"/>
    <input type="hidden" id="promotionProductId" name="promotionProductId" value="${promotionProduct.id}"/>
    
    <input type="hidden" id="price" name="price" value="0"/>
    <div class="right">    	
        <div class="merchant_info">
        	<div class="process step_2"></div>
            <div class="s-bg">
            	<div class="s-lt">
                	<div class="s-li-01">商品价格：</div>
                    <div class="s-li-02">${productSpecifications.price}</div>
                </div>
                <div class="s-lt">
                	<div class="s-li-01"><span>*</span>秒杀优惠：</div>
                    <div class="s-li-02"><input class="s-input-s w152" type="text" name="preferentialNum" id="preferentialNum" onblur="preferentialNumChange();" value="${promotionProduct.preferentialNum}" maxLength="10" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');" />
                    <select class="s-input" style="border-left:none; height:30px; margin-left:-1px; z-index:1;" id="preferentialType" name="preferentialType" onchange="preferentialNumChange();">
                    	<option value="1" <c:if test='${promotionProduct.preferentialType==1}'>selected</c:if>>元</option>
                        <option value="2" <c:if test='${promotionProduct.preferentialType==2}'>selected</c:if>>折</option>
                    </select>
                    </div>
                    <div class="s-mark">折后价：<span id="priceSpan">0</span>元</div>
                    <div class="s-box s-box-1">
                    	<p>秒杀优惠：可对活动商品进行打折设置，或者指定价格设置，该选项为必填项</p>
                        <p>打折设置：在输入框内输入0.1-9.9数值范围之间，后缀选择（折）</p>
                        <p>指定价格设置：在不小于0，不大于商品的价格的数值填写,后缀选择（元）</p>
                    </div>
                </div>
                <div class="s-lt">
                	<div class="s-li-01"><span>*</span>活动商品数量：</div>
                    <div class="s-li-02"><input class="s-input-s w152"  type="text" onblur="joinQuantityChange();" id="joinQuantity" name="joinQuantity" maxLength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" value="1" value="${promotionProduct.joinQuantity}"/>
                    <span class="s-ft">件</span>
                    </div>
                    <div class="s-mark">总库存：<c:choose><c:when test="${productSpecifications.quantity==null||productSpecifications.quantity==''}">0</c:when><c:otherwise>${productSpecifications.quantity}</c:otherwise></c:choose>件</div>
                    <div class="s-box s-box-2">
                    	<p>活动商品数量：在输入框内输入小于总库存的商品数量，该选项为必填项</p>
                    </div>
                </div>
                <div class="s-lt">
                	<div class="s-li-01">ID限购数量：</div>
                    <div class="s-li-02"><input class="s-input-s w152" type="text" name="maxQuantity" id="maxQuantity" onblur="maxQuantityChange();" maxLength="4" onkeyup="this.value=this.value.replace(/\D/g,'')" value="1" value="${promotionProduct.maxQuantity}">
                    <span class="s-ft">件</span>
                    </div>
                    <div class="s-mark"></div>
                    <div class="s-box s-box-3">
                    	<p>ID限购数量：活动开始时，买家可购买的数量最大不可大于活动商品数量，不填默认为1</p>
                    </div>
                </div>
                <div class="s-lt">
                	<div class="s-li-01">保修售后：</div>
                    <div class="s-li-02">
                    	<select class="s-input-s w152" id="afterService" name="afterService">
                        	<option value="1" <c:if test='${promotionProduct.canRepair == 1 && promotionProduct.canReplace == 1}'>selected</c:if>>正常保修包换</option>
                        	<option value="2" <c:if test='${promotionProduct.canRepair == 1 && promotionProduct.canReplace != 1}'>selected</c:if>>保修</option>
                        	<option value="3" <c:if test='${promotionProduct.canReplace == 1 && promotionProduct.canRepair != 1}'>selected</c:if>>包换</option>
                        </select>
                    </div>
                    <div class="s-mark"></div>
                    <div class="s-box s-box-4">
                    	<p>保修售后：参与秒杀活动的商品默认为不可退货，商家可自行选择其他保修项目</p>
                    </div>
                </div>
                <div class="s-lt">
                	<div class="s-li-01">快递设置：</div>
                    <div class="s-li-02">
                    	<input type="radio" checked name="" class="s-ra"><span>包邮</span>
                    </div>
                    <div class="s-mark"></div>
                    <div class="s-box s-box-5">
                    	<p>快递设置：暂定全部包邮</p>
                    </div>
                </div>
                
            </div>
            <div class="s-btn">
            	<a href="javascript:toSet();">完成活动设置 去推广</a>
            </div>
        </div>
    </div>
    </form>
    <!--right end-->
</div>

<!--content end-->



<script type="text/javascript" src="<%=static_resources %>js/product_activity_seckillProductset.js"></script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>