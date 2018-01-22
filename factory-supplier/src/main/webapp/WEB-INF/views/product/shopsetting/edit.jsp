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
    <title>我的网---${ss.shopname}店铺设置</title>
    <%@ include file="/commons/js.jsp" %>
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
		                <li <c:if test="${ss.id==shop.id}">class="active"</c:if>><a href="${basePath}/shopSetting.html?id=${shop.id}">基本信息</a></li>
		                <c:if test="${userSession.type != 3 || userSession.hasAuth('pageSet')}">
		                    <li><a href="${basePath}/shopSetting/pageset.html?id=${shop.id}">店铺页面设置</a></li>
		                </c:if>
		                <c:if test="${userSession.type != 3 }">
		                    <li><a href="${basePath}/shopSetting/categoryBrand.html?id=${shop.id}">品牌及分类</a></li>
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
    <form action="${basePath}/shopSetting/update.html" method="post">
        <input type="hidden" name="shopsettingId" value="${ss.id}"/>
        <div class="right">
            <div class="position">
                <span>您所在的位置：</span>
                <a href="javascript:void(0);">商家中心</a><em>></em>
                <a href="${basePath}/shopSetting.html">我的店铺</a><em>></em>
                <a href="javascript:;">添加店铺信息</a>
            </div>
            <div class="sort_wrap">
                <div class="sort_title">店铺信息</div>
                <div class="store_cont">
                    <p><span class="out">*</span>店铺类型：<c:if test="${ss.type == 0}">专营店</c:if><c:if test="${ss.type == 1}">专卖店</c:if><c:if test="${ss.type == 2}">旗舰店</c:if></p>
                    <strong>（此为入驻时填写不可以修改）</strong>
                </div>
                <div class="store_cont">
                    <p><span class="out">*</span>店铺名称：${ss.shopname}</p>
                    <strong style="display:none;"><a href="#">发起修改</a><a href="#">查看申请进度</a>（此为入驻时填写不可以修改）</strong>
                </div>
                <div class="store_cont">
                    <p><span class="out">*</span>店铺Logo</p>
                    <div id="filePicker" style="margin-right:480px;color: #fff;width:50px; text-align: center;float:right;background: none repeat scroll 0 0 #5d6781;">上&nbsp;传</div>
                    <strong>（提示：上传图片不可删除，再次上传覆盖前一张）</strong>

                    <div class="uploadlogo">
                        <div class="puplogo">
                            <c:choose>
                                <c:when test="${empty ss.logo}">
                                    <img id="logo" src="<%=static_resources %>images/nophoto.gif"/>
                                </c:when>
                                <c:otherwise>
                                    <img id="logo" src="${ss.logo}"/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <input type="hidden" name="logo" id="logo_url" value="${ss.logo}"/>
                        <div class="pup_txt">该logo需与注册商标图文信息一致，也可是品牌logo</div>
                        <div class="pup_txt">建议 <span class="out">JPG</span>，<span class="out">JEPG</span>，<span
                                class="out">PNG</span>，文件大小<span class="out">80K</span> 以内，建议尺寸<span
                                class="out">130PX*60PX</span></div>
                    </div>
                </div>
                <div class="store_cont">
                    <p><span class="out">*</span>操作品牌：<c:forEach items="${brands}" var="brand">${empty brand.name ? brand.nameEn : brand.name}&nbsp; </c:forEach></p>
                </div>
                <div class="store_cont">
                    <p>&nbsp;&nbsp;&nbsp;宣传标语：</p>
                    <textarea class="textarea_txt wh188" name="introduce" id="introduce" maxlength="300">${ss.introduce}</textarea>
                    <strong>（用于消费者搜索店铺时展示）</strong>

                    <div class="limitfont"><%--<b id="introNum">0</b>/300--%>支持300个字符</div>
                </div>
                <div class="store_cont">
                    <p>&nbsp;&nbsp;&nbsp;企业QQ：</p>
                    <input class="pubilc_input f118" type="text" id="qq" name="qq" value="${ss.qq}" maxLength="12" onkeyup="this.value=this.value.replace(/[^\d]/g,'')" onafterpaste="this.value=this.value.replace(/[^\d]/g,'')"/>
                </div>
                <div class="store_cont">
                    <p>&nbsp;&nbsp;&nbsp;联系电话：</p>
                    <input class="pubilc_input f118" type="text" id="shopPhone" name="shopPhone" value="${ss.shopPhone}"/>
                    <input type="hidden" name="supplierId" value="${sp.id}"/>
                    <div class="uploadlogo martop">
                        <div class="pup_txt">联系电话为售前联系电话，将展示在店铺页面店铺信息模块内可为手机号码</div>
                        <div class="pup_txt">也可以为座机或400电话，输入内容只包含<span class="out">“数字”</span>和<span class="out">“-”</span>，最大<span
                                class="out">20位</span></div>
                    </div>
                </div>

                <div class="savingbtn"><a href="javascript:;" onclick="save();">保存</a></div>
            </div>
        </div>
    </form>
    <!--right end-->
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="/commons/footer.jsp" %>
<!--footer end-->
<%@ include file="/commons/box.jsp" %>
<div style="display:none;" id="tmp-upload_container">
 <input type="file" id="uploadFile" name="file" onchange="fileUpload()" style="position:absolute;filter:alpha(opacity=0);width:56px;height:30px;"  />
</div>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js?1233"></script>
<script type="text/javascript" src="<%=static_resources %>js/menu_min.js"></script>
<script type="text/javascript">
var jsBasePath='${basePath}';
var jsSsId="${ss.id}";
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_shopsetting_edit.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        selectedHeaderLi("wddp_header");
        
		$("#filePicker").click(function(){
			$("#uploadFile").click();
		})
    });
</script>
</body>
</html>
