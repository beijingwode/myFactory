<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="static_resources/css/public.css" />
<#if pageMap["page_css"]??>
<link rel="stylesheet" type="text/css" href="${pageMap["page_css"][0].ex1Value!}" />
</#if>
<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="static_resources/js/common/system_config.js"></script>
<script type="text/javascript" src="static_resources/js/h5_exit.js"></script>
<#if pageMap["page_title"]??>
<title>${pageMap["page_title"][0].title!}</title>
</#if>
</head>

<body  >
<div class="main-cont" id="main-cont" >
<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
  <#if pageMap["page_title"]??>
	<div class="top">
        <h1><a href="javascript:close();" class="aleft"></a>${pageMap["page_title"][0].title!}</h1>
    </div>
   </#if>
	<div class="main_box" style="position:absolute;top:45px;left:0;">
	    <#if pageMap["banner"]??>
		<div class="banner">
	    	<img src="${pageMap["banner"][0].imagePath!}" />     	
	    </div>
    	</#if>
	    <div class="main_one">
	    <#if pageMap["main_one_title"]??>
	        <div class="tit"><img src="${pageMap["main_one_title"][0].imagePath!}" /></div>
	     </#if>
	      <#if pageMap["one_top_product"]??>
	      <#assign productList =pageMap["one_top_product"] >
	      <#list productList as p>
	    	<div class="one_top">
		    	<a href="${p.link!}">
		    		<dl>
		    			<dt><img src="${p.imagePath!}" /></dt>
		    			<dd class="dd1">${p.proBrand!}${p.title!}</dd>
		    			<dd class="dd2">电商价:￥${p.proPrice!}</dd>
		    			<dd class="dd3">内购价:￥<span>${p.proSalePrice!}</span></dd>
		    		</dl>
		    	</a>	
	    	</div>
	    	</#list>
	    	</#if>
	    	<#if pageMap["one_bottom_product"]??>
	    	<#assign productList =pageMap["one_bottom_product"] >
	    	<div class="one_bottom">
	    		<ul>
	    			<#list productList as p>
	    			<li>
	    				<a href="${p.link!}">
	    					<dl>
	    						<dt><img src="${p.imagePath!}" /></dt>
				    			<dd class="dd1">${p.proBrand!} <br />${p.title!}</dd>
				    			<dd class="dd2">电商价:￥${p.proPrice!}</dd>
				    			<dd class="dd3">内购价:￥<span>${p.proSalePrice!}</span></dd>
	    					</dl>
	    				</a>
	    			</li>
	    			</#list>
	    		</ul>
	    	</div>
	    	</#if>
	    </div>
	    <#if pageMap["main_two_product"]??>
	    <#assign productList =pageMap["main_two_product"] >
        <div class="main_two">
        	<ul>
        	<#list productList as p>
	    			<li>
	    				<a href="${p.link!}">
	    					<dl>
	    						<dt><img src="${p.imagePath!}" /></dt>
				    			<dd class="dd1">${p.proBrand!}<br />${p.title!}</dd>
				    			<dd class="dd2">电商价:￥${p.proPrice!}</dd>
				    			<dd class="dd3">内购价:￥<span>${p.proSalePrice!}</span></dd>
	    					</dl>
	    				</a>
	    			</li>
	    	</#list>
	    	</ul>
        </div>
        </#if>
	</div>
</div>
<script type="text/javascript" src="static_resources/js/app2wx_page.js?111"></script>
</body>
</html>
