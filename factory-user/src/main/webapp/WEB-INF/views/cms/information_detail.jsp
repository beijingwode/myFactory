<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../common/include.jsp" %> 
<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-${cmsContent.title}</title>
<link rel="stylesheet" type="text/css" href="${basePath }css/common.css">
<link rel="stylesheet" type="text/css" href="${basePath }css/information.css">
</head>
<script type="text/javascript" src="${basePath }resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="${basePath }resources/js/jquery.Query.js"></script>
<script type="text/javascript" src="${basePath }resources/js/application.js"></script>
<script type="text/javascript" src="${basePath }resources/js/shopping.js"></script>
<body>
<!--top begin-->
<%@ include file="/common/header.jsp" %> 
<!--top end-->

<!--content begin-->
<div id="content">    
    <div class="detail_wrap">
    	<div class="detail-left">
        	<div class="detail-cont">
                <h1>${cmsContent.title}</h1>
                <div class="detail-title">
                    <fmt:formatDate value="${content.creatdate}" pattern="yyyy-MM-dd"/>&nbsp;&nbsp;&nbsp;&nbsp;
                    <div class="jiathis_style_24x24">
                    	<!-- <a class="jiathis_button_tsina">新浪微博</a> -->
                        <a class="jiathis_button_qzone" title="分享到QQ空间"></a>
                        <a class="jiathis_button_tsina" title="分享到新浪微博"></a>
                        <a class="jiathis_button_tqq" title="分享到腾讯微博"></span></a>
                        <a class="jiathis_button_weixin" title="分享到微信"></span></a> 
                        <a class="jiathis_button_renren" title="分享到人人网"></span></a>
                        <a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank"></a>
                        <a class="jiathis_counter_style"><span class="jiathis_button_expanded jiathis_counter jiathis_bubble_style" id="jiathis_counter_17" title="累计分享0次">0</span></a>
                    </div>
                    <script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script><script type="text/javascript" src="http://v3.jiathis.com/code/plugin.client.js" charset="utf-8"></script>
                </div>
                <div class="detail-nr">
                	<p>${cmsContent.txt}</p>
                </div>
            </div>
        </div>
        <div class="detail-right">
        	<img src="images/information.jpg" alt="">
            <div class="detail-rt-list">
            	<h2>推荐阅读</h2>
            	<ul class="am-list">
            		<c:forEach items="${list}" var="content" begin="0" end="3">
            			<li>
	            			<a href="/cmsContent?id=${content.id}&channelId=${content.channelid}">${content.title}</a>
	            			<span class="am-list-date"><fmt:formatDate value="${content.creatdate}" pattern="yyyy-MM-dd"/></span>
            			</li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
</div>
<!--content end-->


<script type="text/javascript">
    $("#page_button").click(function(){
        var page = $.trim($("#page")
        		.val()), total = ${result.totalPage};
        if(!isNaN(page) && page > 0 && page <= total) {
            window.location.href=$.query.set("page", page);
        }
    })
</script>
<!--footer begin-->
<%@ include file="/common/footer.jsp" %>
<!--footer end-->
</body>
</html>
