<!doctype html>
<html>
<head>
<meta charset="utf-8">
<#if pageMap["page_title"]??>
<title>${pageMap["page_title"][0].title!}</title>
</#if>
<#if pageMap["page_css"]??>
<link rel="stylesheet" type="text/css" href="${pageMap["page_css"][0].ex1Value!}" />
</#if>

<style>
	<#if pageMap["bg_style"]??>
		body{background:#${pageMap["bg_style"][0].ex1Value!}}
	</#if>
	<#if pageMap["mainone_style"]??>
		.main_one{background:#${pageMap["mainone_style"][0].ex1Value!}}
	</#if>
	<#if pageMap["maintwo_style"]??>
		.main_two{background:#${pageMap["maintwo_style"][0].ex1Value!}}
	</#if>
	<#if pageMap["tit_style"]??>
		.tit_text{color:#${pageMap["tit_style"][0].ex1Value!};}
	</#if>
	
	
</style>

<script type="text/javascript" src="resources/js/jquery1.8.0.js"></script>
</head>

<body>
<#if pageMap["banner"]??>
    <#assign imageList =pageMap["banner"] >
	<div class="banner">
		<#list imageList as image>
		<img src="${image.imagePath!}" />
		</#list>
	</div>
	
</#if>

<div class="main_one">
	<div class="main_one_con">
		<#if pageMap["mainone_imagesTitle"]??>
		<div class="tit_img"><img src="${pageMap["mainone_imagesTitle"][0].imagePath!}" /></div>
		</#if>
		<#if pageMap["mainone_title"]??>
		<div class="tit_text">${pageMap["mainone_title"][0].title!}</div>
		</#if>
		
		<#if pageMap["mainone_product"]??>
			<#assign products =pageMap["mainone_product"] >
	    	<#if products??>	
	    		<#list products as product>
	    		  <#if (product_index % 2 == 0) >
					<dl class="dl1">
				  </#if>
	    		  <#if (product_index % 2 == 1) >
					<dl class="dl2">
				  </#if>
					<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a><span>${product.ex2Value!}</span></dt>
					<dd class="dd1">${product.proBrand!}</dd>
					<dd class="dd2"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
					<dd class="dd3" style="color:#${product.ex5Value!}">电商价：￥${product.proPrice!}</dd>
					<dd class="dd4" style="color:#${product.ex6Value!}">${product.ex3Value!}:￥<em>${product.ex4Value!}</em></dd>
				</dl>
				</#list>
			</#if>
	   </#if>
		
	</div>
</div>
<div class="main_two">
	<div class="main_two_con">
		<#if pageMap["maintwo1_imagesTitle"]??>
		<div class="tit_img"><img src="${pageMap["maintwo1_imagesTitle"][0].imagePath!}" /></div>
		</#if>
		<#if pageMap["maintwo1_title"]??>
		<div class="tit_text">${pageMap["maintwo1_title"][0].title!}</div>
		</#if>
		<ul>
		<#if pageMap["maintwo1_product"]??>
			<#assign products =pageMap["maintwo1_product"] >
	    	<#if products??>	
	    		<#list products as product>
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd4">内购价:￥${product.proSalePrice!}</dd>
					</dl>
				</li>
				</#list>
			</#if>
	    </#if>
		
		</ul>
		
		<#if pageMap["maintwo2_imagesTitle"]??>
		<div class="tit_img"><img src="${pageMap["maintwo2_imagesTitle"][0].imagePath!}" /></div>
		</#if>
		<#if pageMap["maintwo2_title"]??>
		<div class="tit_text">${pageMap["maintwo2_title"][0].title!}</div>
		</#if>
		<ul>
			<#if pageMap["maintwo2_product"]??>
			<#assign products =pageMap["maintwo2_product"] >
	    	<#if products??>	
	    		<#list products as product>
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd4">内购价:￥${product.proSalePrice!}</dd>
					</dl>
				</li>
				</#list>
			</#if>
	    </#if>
		</ul>
		
		
		<#if pageMap["maintwo3_imagesTitle"]??>
		<div class="tit_img"><img src="${pageMap["maintwo3_imagesTitle"][0].imagePath!}" /></div>
		</#if>
		<#if pageMap["maintwo3_title"]??>
		<div class="tit_text">${pageMap["maintwo3_title"][0].title!}</div>
		</#if>
		<ul>
			<#if pageMap["maintwo3_product"]??>
			<#assign products =pageMap["maintwo3_product"] >
	    	<#if products??>	
	    		<#list products as product>
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd4">内购价:￥${product.proSalePrice!}</dd>
					</dl>
				</li>
				</#list>
			</#if>
	    </#if>
		</ul>
		
		
		<#if pageMap["maintwo4_imagesTitle"]??>
		<div class="tit_img"><img src="${pageMap["maintwo4_imagesTitle"][0].imagePath!}" /></div>
		</#if>
		<#if pageMap["maintwo4_title"]??>
		<div class="tit_text">${pageMap["maintwo4_title"][0].title!}</div>
		</#if>
		<ul>
			<#if pageMap["maintwo4_product"]??>
			<#assign products =pageMap["maintwo4_product"] >
	    	<#if products??>	
	    		<#list products as product>
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd4">内购价:￥${product.proSalePrice!}</dd>
					</dl>
				</li>
				</#list>
			</#if>
	    </#if>
		</ul>
		
		
		<#if pageMap["maintwo5_imagesTitle"]??>
		<div class="tit_img"><img src="${pageMap["maintwo5_imagesTitle"][0].imagePath!}" /></div>
		</#if>
		<#if pageMap["maintwo5_title"]??>
		<div class="tit_text">${pageMap["maintwo5_title"][0].title!}</div>
		</#if>
		<ul>
			<#if pageMap["maintwo5_product"]??>
			<#assign products =pageMap["maintwo5_product"] >
	    	<#if products??>	
	    		<#list products as product>
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd4">内购价:￥${product.proSalePrice!}</dd>
					</dl>
				</li>
				</#list>
			</#if>
	    </#if>
		</ul>
	</div>

	<div class="footer">
		<#if pageMap["footer"]??>			
		    <a href="${pageMap["footer"][0].link!}"><img src="${pageMap["footer"][0].imagePath!}" /></a>			
		</#if>			
	</div>
</div>

	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
  <#if pageParam["supplier_id"]??>
	<input type="hidden" id="supplierId" value="${pageParam["supplier_id"]!}">
	<input type="hidden" id="supplierName" value="${pageParam["supplier_name"]!}">
  </#if>

</body>
<script type="text/javascript" src="/user/activityLog?url=${pageParam["page_key"]!}"></script>
</html>
