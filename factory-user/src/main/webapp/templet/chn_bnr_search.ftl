<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="我的网">
<meta name="keywords" content="我的网">
<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="css/subpage.css" />
<#if pageMap["page_css"]??>
<link rel="stylesheet" type="text/css" href="${pageMap["page_css"][0].ex1Value!}" />
</#if>
</head>
<script type="text/javascript" src="resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="resources/js/jquery.kinMaxShow-1.0.src.js"></script>
<script type="text/javascript" src="resources/js/application.js"></script>
<body>
<#include "header.ftl">

<!--banner begin-->
<div class="bannerwrapper">
	<#if pageMap["banner"]??>
    <#assign imageList =pageMap["banner"] >
    <div class="banner" id="banner" >
      	<#list imageList as image>
				<a href="${image.link!}"  target="_blank" class="d1" style="background:url(${image.imagePath!}) center no-repeat"></a>
		</#list>
        <div class="d2" id="banner_id">
            <ul>
            	<#list imageList as image>
						<li></li>
				 </#list> 
            </ul>
        </div>
    </div>
    </#if>
</div>
<!--banner end-->
						
<!--content begin-->
<div  id="content">
     <div class="all_product">
     <div class="sort_title sort_title1">
		<ul class="sort_lt u1">
			<li class="surr"><a href="javascript:void(0);" id="gr">个人购</a></li>
			<li><a href="javascript:void(0);" id="qy">企业购</a></li>
		</ul>
	</div>
	<div class="sort_title">
		<ul class="sort_lt u2">
			<li class="surr"><a href="javascript:void(0);" id="zh">综合排序</a></li>
			<li><a href="javascript:void(0);" id="discount">折扣</a></li>
			<li><a href="javascript:void(0);" id="price">价格</a><i></i></li>
		</ul>
	</div>   
    <div class="all_product_list">
    	<ul>
   
		</ul>
	</div>
   </div>
</div>


	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
  <#if pageParam["supplier_id"]??>
	<input type="hidden" id="supplierId" value="${pageParam["supplier_id"]!}">
	<input type="hidden" id="supplierName" value="${pageParam["supplier_name"]!}">
  </#if>
<!--content end-->
<#include "footer.ftl">
</body>
<script>
$(function(){
	banner();
});
</script>
<script type="text/javascript" src="resources/js/top_ewm.js"></script>
<#if pageMap["page_js"]??>
<script type="text/javascript" src="${pageMap["page_js"][0].ex1Value!}"></script>
</#if>
</html>
