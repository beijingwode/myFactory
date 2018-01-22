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
<script type="text/javascript" src="static_resources/js/common/system_config.js"></script>
<style>
	<#if pageMap["bg_style"]??>
		body{background:#${pageMap["bg_style"][0].ex1Value!}}
	</#if>
	<#if pageMap["mainonedl_style"]??>
		.main_one_con dl{background:#${pageMap["mainone_style"][0].ex1Value!}}
	</#if>
	<#if pageMap["maintwodl_style"]??>
		.main_two_con dl{background:#${pageMap["maintwodl_style"][0].ex1Value!}}
	</#if>
	<#if pageMap["tit_style"]??>
		.tit_text{color:#${pageMap["tit_style"][0].ex1Value!};}
	</#if>
	
</style>

<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="static_resources/js/h5_exit.js"></script>
<#if pageMap["page_title"]??>
<title>${pageMap["page_title"][0].title!}</title>
</#if>
</head>
<body>
<div class="main-cont" id="main-cont" >
	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
  <#if pageParam["supplier_id"]??>
	<input type="hidden" id="supplierId" value="${pageParam["supplier_id"]!}">
	<input type="hidden" id="supplierName" value="${pageParam["supplier_name"]!}">
  </#if>
	  <#if pageMap["page_title"]??>
		<div class="top">
	        <h1 ><a href="javascript:close();" class="aleft"></a>${pageMap["page_title"][0].title!}</h1>
	    </div>
	  </#if>
	  
	<div class="main_box" style="position:absolute;top:45px;left:0;">
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
						<dt><a href="${product.link!}"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}">${product.title!}</a></dd>		
						<dd class="dd4">秒杀价:￥<em>${product.ex2Value!}</em></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">立即秒杀</a></dd>
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
				
				<#if pageMap["maintwo1_product"]??>
				<#assign products =pageMap["maintwo1_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<dl>
						<dt><a href="${product.link!}"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}">${product.title!}</a></dd>
						<dd class="dd4">内购价:￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">立即抢购</a></dd>				
					</dl>
					</#list>
				</#if>
		    	</#if>
					
						
				<#if pageMap["maintwo2_imagesTitle"]??>
				<div class="tit_img"><img src="${pageMap["maintwo2_imagesTitle"][0].imagePath!}" /></div>
				</#if>
				<#if pageMap["maintwo2_title"]??>
				<div class="tit_text">${pageMap["maintwo2_title"][0].title!}</div>
				</#if>
				
				<#if pageMap["maintwo2_product"]??>
				<#assign products =pageMap["maintwo2_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<dl>
						<dt><a href="${product.link!}"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}">${product.title!}</a></dd>
						<dd class="dd4">内购价:￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">立即抢购</a></dd>			
					</dl>
					</#list>
				</#if>
		    	</#if>
					
				<#if pageMap["maintwo3_imagesTitle"]??>
				<div class="tit_img"><img src="${pageMap["maintwo3_imagesTitle"][0].imagePath!}" /></div>
				</#if>
				<#if pageMap["maintwo3_title"]??>
				<div class="tit_text">${pageMap["maintwo3_title"][0].title!}</div>
				</#if>
				
				<#if pageMap["maintwo3_product"]??>
				<#assign products =pageMap["maintwo3_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<dl>
						<dt><a href="${product.link!}"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}">${product.title!}</a></dd>
						<dd class="dd4">内购价:￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">立即抢购</a></dd>		
					</dl>
					</#list>
				</#if>
			    </#if>
			    
			    <#if pageMap["maintwo4_imagesTitle"]??>
				<div class="tit_img"><img src="${pageMap["maintwo4_imagesTitle"][0].imagePath!}" /></div>
				</#if>
				<#if pageMap["maintwo4_title"]??>
				<div class="tit_text">${pageMap["maintwo4_title"][0].title!}</div>
				</#if>
				
				<#if pageMap["maintwo4_product"]??>
				<#assign products =pageMap["maintwo4_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<dl>
						<dt><a href="${product.link!}"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}">${product.title!}</a></dd>
						<dd class="dd4">内购价:￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">立即抢购</a></dd>		
					</dl>
					</#list>
				</#if>
			    </#if>
			    
			    <#if pageMap["maintwo5_imagesTitle"]??>
				<div class="tit_img"><img src="${pageMap["maintwo5_imagesTitle"][0].imagePath!}" /></div>
				</#if>
				<#if pageMap["maintwo5_title"]??>
				<div class="tit_text">${pageMap["maintwo5_title"][0].title!}</div>
				</#if>
				
				<#if pageMap["maintwo5_product"]??>
				<#assign products =pageMap["maintwo5_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<dl>
						<dt><a href="${product.link!}"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1">${product.proBrand!}</dd>
						<dd class="dd2"><a href="${product.link!}">${product.title!}</a></dd>
						<dd class="dd4">内购价:￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">立即抢购</a></dd>		
					</dl>
					</#list>
				</#if>
			    </#if>
					
			</div>
			
		</div>
		
	   <#if pageMap["footer"]??>
			<div class="footer">
		    	<a href="${pageMap["footer"][0].link!}"><img src="${pageMap["footer"][0].imagePath!}" /></a>
			</div>
		</#if>
	    
	</div>
</div>
    
</body>
<script type="text/javascript" src="static_resources/js/app2wx_page.js?111"></script>

</html>
