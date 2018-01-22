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
    <script type="text/javascript">
        window.UEDITOR_serverUrl = '${basePath}';
    </script>
    <script type="text/javascript" charset="utf-8" src="<%=static_resources %>resources/ueditor1_4_3/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="<%=static_resources %>resources/ueditor1_4_3/ueditor.all.min.js"></script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="<%=static_resources %>resources/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
</head>
<style>
.uploadedimg2 li {
    width: 120px;
    height: 150px;
    margin-right: 10px;
    text-align: center;
   
    color: #434343;
    float: left;
    
    border-radius: 4px;
    behavior: url(iecss3/PIE.htc);
    position: relative;
    z-index: 2;
    display: table-cell;
    text-align: center;
    vertical-align: middle;
    
}
.uploadedimg2 li p{
    width: 120px;
    height: 120px;
    margin-right: 10px;
    text-align: center;
    font: 14px/120px "Microsoft YaHei"; 
    color: #434343;
    float: left;
    background: #edf0f3;
    border-radius: 4px;
    behavior: url(iecss3/PIE.htc);
    position: relative;
    z-index: 2;
    display: table-cell;
    text-align: center;
    vertical-align: middle;
    line-height: 120px;
     overflow: hidden;
}
.uploadedimg2 li a{display:block;margin:120px 0 0 30px;
   color:#fff;
    width: 56px;
    height: 30px;
    text-align: center;
    background: #5d6781;
    border-radius: 4px;
    behavior: url(iecss3/PIE.htc);
    line-height:30px;
    z-index: 2;
}
</style>
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
		                    <li <c:if test="${ss.id==shop.id}">class="active"</c:if>><a href="${basePath}/shopSetting/pageset.html?id=${shop.id}">店铺页面设置</a></li>
		                </c:if>
		                <c:if test="${userSession.type != 3 }">
		                    <li ><a href="${basePath}/shopSetting/categoryBrand.html?id=${shop.id}">品牌及分类</a></li>
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
            <a href="javascript:;">店铺页面设置</a>
        </div>
        <form action="${basePath}/shopSetting/setpage.html" method="post">
            <input type="hidden" name="id" value="${ss.id}"/>
            <div class="sort_wrap">
                <div class="sort_title">首页信息</div>
                <div class="uploadpic">
                    <p><span class="out">*</span>请上传首页店招图片</p>
                    <div id="filePicker1" class="overbtn filePicker"><a href="#">上传</a></div>
                    <strong>(<span class="out">1200PX*120PX</span>，图片大小<span class="out">300K</span>以下)</strong>
                </div>

                <div class="uploadpic">
                    <ul class="uploadedimg">
                        <li><img id="img1" src="${ss.topImage}" alt="图片"/></li>
                    </ul>
                    <input type="hidden" id="himg1" name="topImage" value="${ss.topImage}"/>
                </div>
                <div class="uploadpic">
                    <p><span class="out">*</span>请上传首页广告图片</p>
                    <div id="filePicker2" class="overbtn filePicker"><a href="#">上传</a></div>
                    <strong>(<span class="out">1200PX*300PX</span>，图片大小<span class="out">300K</span>以下)</strong>
                </div>
                <div class="uploadpic">
                    <ul class="uploadedimg uploadedimg2" id="img2">
				     	<c:forEach items="${banners}" var="item">
                        	<li><p><img src="${item}" alt="图片"/></p><div onclick="del(this)"><a href="javascript:void(0);" >删除</a></div>
                    		<input type="hidden" name="banner" value="${item}"/></li>
				     	</c:forEach>
                    </ul>
                </div>
                <div class="uploadpic">
                    <p><span class="out">*</span>请上传品牌介绍页图片</p>
                    <div id="filePicker3" class="overbtn filePicker"><a href="#">上传</a></div>
                    <strong>(<span class="out">1200PX*300PX</span>，图片大小<span class="out">300K</span>以下)</strong>
                </div>
                <div class="uploadpic">
                    <ul class="uploadedimg uploadedimg2" id="img3">
				     	<c:forEach items="${introImages}" var="item">
                        	<li><p><img src="${item}" alt="图片"/></p><div onclick="del(this)"><a href="javascript:void(0);" >删除</a></div>
                    		<input type="hidden" name="introImage" value="${item}"/></li>
				     	</c:forEach>
                    </ul>
                </div>
                <div class="uploadpic">
                    <p style="width:300px;"><span class="out">*</span>品牌介绍页描述<span class="out" style="padding-left:10px;">(图片的最大宽度不能超过750px)</span></p>
                    <div class=" marleft">
                        <script id="container" name="brandIntroduce" type="text/plain" style="width:860px;height:340px; margin-top:50px;">
                        ${ss.brandIntroduce}

                        </script>
                    </div>
                </div>
                <div class="uploadpic">
                    <div class="submitbtn"><a href="javascript:;" onclick="$('form').submit();">提交</a></div>
                    <div class="submitbtn"><a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>shop/${ss.id}" target="_blank">预览店铺</a></div>
                </div>
            </div>
        </form>
    </div>
    <!--right end-->
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="/commons/footer.jsp" %>
<!--footer end-->
<%@ include file="/commons/box.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/menu_min.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js?12321415"></script>
<script type="text/javascript" src="<%=static_resources %>resources/webuploader-0.1.5/webuploader.flashonly.js"></script>

<div style="display:none;" id="tmp-upload_container">
 <input type="file" id="uploadFile" name="file" onchange="fileUpload()" style="position:absolute;filter:alpha(opacity=0);width:56px;height:30px;"  />
</div>

<script type="text/javascript">
var jsBasePath='${basePath}';
var jsSsId="${ss.id}";
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_shopsetting_pageset.js"></script>
</body>
</html>
