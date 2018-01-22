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
	<#if pageMap["tit_style"]??>
		.tit_text span{background:#${pageMap["tit_style"][0].ex1Value!};color:#${pageMap["tit_style"][0].ex2Value!};}
	</#if>
	<#if pageMap["imgbtn_style"]??>
		.goto_con_btn{background:#${pageMap["imgbtn_style"][0].ex1Value!};}
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
		<div class="header">
			<#list imageList as image>
			<a href="${image.link!}"><img src="${image.imagePath!}" /></a>
			</#list>
		</div>
		
	</#if>
		
	    <#if pageMap["images_button"]??>
		<div class="goto_con_btn">
			<div class="btn_con">
				<a href="#txfz"><img src="${pageMap["images_button"][0].imagePath!}" /></a>
				<a href="#gzxl"><img src="${pageMap["images_button"][1].imagePath!}" /></a>
				<a href="#bgyp"><img src="${pageMap["images_button"][2].imagePath!}" /></a>
			</div>
		</div>
		</#if>
		
		<#if pageMap["hot_brand_tit"]??>  
			<div class="tit_images"><a href="${pageMap["hot_brand_tit"][0].link!}"><img src="${pageMap["hot_brand_tit"][0].imagePath!}" /></a></div>
		</#if>
	        									
	    <div class="brand_con">
			<ul>
				<#if pageMap["hot_brand_big"]??> 
					<#list pageMap["hot_brand_big"] as brand>
							 <li class="big"><a href="${brand.link!}"><img src="${brand.imagePath!}" /></a></li>                                                                   
					 </#list>
				</#if>	 
				<#if pageMap["hot_brand_sml"]??> 
					<#list pageMap["hot_brand_sml"] as brand> 
							 <li><a href="${brand.link!}"><img src="${brand.imagePath!}" /></a></li>
					</#list>
				</#if>	
			</ul>
		</div>
		
		<#if pageMap["1f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["1f_imagesTitle"][0].link!}"><img src="${pageMap["1f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["1f_title"]??>
		<div class="tit_text" id="txfz"><span>${pageMap["1f_title"][0].title!}</span></div>
		</#if>
		
		<#if pageMap["1f_product"]??>
			<#assign products =pageMap["1f_product"] >
	    	<#if products??>	
			<div class="main_con">
				<ul>
					<#list products as product>
					<li>
						<dl>
							<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
							<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
							<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
							<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						</dl>
					</li>
					</#list>			
				</ul>
			</div>
			</#if>
	   </#if>
	   
	   <#if pageMap["2f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["2f_imagesTitle"][0].link!}"><img src="${pageMap["2f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["2f_title"]??>
			<div class="tit_text" id="gzxl"><span>${pageMap["2f_title"][0].title!}</span></div>
			</#if>
			<#if pageMap["2f_product"]??>
				<#assign products =pageMap["2f_product"] >
		    	<#if products??>
				<div class="main_con">
					<ul>
						<#list products as product>	
						<li>
							<dl>
								<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
								<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
								<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
								<dd class="dd3">电商价：￥${product.proPrice!}</dd>
							</dl>
						</li>			
						</#list>
					</ul>
				</div>
				</#if>
		   </#if>
		<#if pageMap["3f_imagesTitle"]??>
		<div class="tit_images tit_images2" id="bgyp"><a href="${pageMap["3f_imagesTitle"][0].link!}"><img src="${pageMap["3f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["3f_title"]??>
		<div class="tit_text"><span>${pageMap["3f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["3f_product"]??>
			<#assign products =pageMap["3f_product"] >
	    	<#if products??>		
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
   
   		<#if pageMap["4f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["4f_imagesTitle"][0].link!}"><img src="${pageMap["4f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["4f_title"]??>
		<div class="tit_text"><span>${pageMap["4f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["4f_product"]??>
			<#assign products =pageMap["4f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
		
		<#if pageMap["5f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["5f_imagesTitle"][0].link!}"><img src="${pageMap["5f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["5f_title"]??>
		<div class="tit_text"><span>${pageMap["5f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["5f_product"]??>
			<#assign products =pageMap["5f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["6f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["6f_imagesTitle"][0].link!}"><img src="${pageMap["6f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["6f_title"]??>
		<div class="tit_text"><span>${pageMap["6f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["6f_product"]??>
			<#assign products =pageMap["6f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["7f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["7f_imagesTitle"][0].link!}"><img src="${pageMap["7f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["7f_title"]??>
		<div class="tit_text"><span>${pageMap["7f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["7f_product"]??>
			<#assign products =pageMap["7f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["8f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["8f_imagesTitle"][0].link!}"><img src="${pageMap["8f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["8f_title"]??>
		<div class="tit_text"><span>${pageMap["8f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["8f_product"]??>
			<#assign products =pageMap["8f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["9f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["9f_imagesTitle"][0].link!}"><img src="${pageMap["9f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["9f_title"]??>
		<div class="tit_text"><span>${pageMap["9f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["9f_product"]??>
			<#assign products =pageMap["9f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["10f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["10f_imagesTitle"][0].link!}"><img src="${pageMap["10f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["10f_title"]??>
		<div class="tit_text"><span>${pageMap["10f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["10f_product"]??>
			<#assign products =pageMap["10f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["11f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["11f_imagesTitle"][0].link!}"><img src="${pageMap["11f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["11f_title"]??>
		<div class="tit_text"><span>${pageMap["11f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["11f_product"]??>
			<#assign products =pageMap["11f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["12f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["12f_imagesTitle"][0].link!}"><img src="${pageMap["12f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["12f_title"]??>
		<div class="tit_text"><span>${pageMap["12f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["12f_product"]??>
			<#assign products =pageMap["12f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["13f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["13f_imagesTitle"][0].link!}"><img src="${pageMap["13f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["13f_title"]??>
		<div class="tit_text"><span>${pageMap["13f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["13f_product"]??>
			<#assign products =pageMap["13f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["14f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["14f_imagesTitle"][0].link!}"><img src="${pageMap["14f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["14f_title"]??>
		<div class="tit_text"><span>${pageMap["14f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["14f_product"]??>
			<#assign products =pageMap["14f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["15f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["15f_imagesTitle"][0].link!}"><img src="${pageMap["15f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["15f_title"]??>
		<div class="tit_text"><span>${pageMap["15f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["15f_product"]??>
			<#assign products =pageMap["15f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["16f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["16f_imagesTitle"][0].link!}"><img src="${pageMap["16f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["16f_title"]??>
		<div class="tit_text"><span>${pageMap["16f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["16f_product"]??>
			<#assign products =pageMap["16f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["17f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["17f_imagesTitle"][0].link!}"><img src="${pageMap["17f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["17f_title"]??>
		<div class="tit_text"><span>${pageMap["17f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["17f_product"]??>
			<#assign products =pageMap["17f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["18f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["18f_imagesTitle"][0].link!}"><img src="${pageMap["18f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["18f_title"]??>
		<div class="tit_text"><span>${pageMap["18f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["18f_product"]??>
			<#assign products =pageMap["18f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["19f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["19f_imagesTitle"][0].link!}"><img src="${pageMap["19f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["19f_title"]??>
		<div class="tit_text"><span>${pageMap["19f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["19f_product"]??>
			<#assign products =pageMap["19f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
	   <#if pageMap["20f_imagesTitle"]??>
		<div class="tit_images"><a href="${pageMap["20f_imagesTitle"][0].link!}"><img src="${pageMap["20f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		<#if pageMap["20f_title"]??>
		<div class="tit_text"><span>${pageMap["20f_title"][0].title!}</span></div>
		</#if>
		<#if pageMap["20f_product"]??>
			<#assign products =pageMap["20f_product"] >
	    	<#if products??>
			<div class="main_con">
				<ul>
				<#list products as product>	
				<li>
					<dl>
						<dt><a href="${product.link!}" target="_blank"><img src="${product.imagePath!}" /></a></dt>
						<dd class="dd1"><a href="${product.link!}" target="_blank">${product.title!}</a></dd>
						<dd class="dd2">内购价：￥${product.proSalePrice!}</dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
					</dl>
				</li>			
				</#list>
				</ul>
			</div>
		</#if>
	   </#if>
	   
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
