<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="static_resources/css/swiper-3.4.2.min.css" />
<link rel="stylesheet" type="text/css" href="static_resources/css/public_m.css" />
<#if pageMap["page_css"]??>
<link rel="stylesheet" type="text/css" href="${pageMap["page_css"][0].ex1Value!}" />
</#if>
<!--<link rel="stylesheet" type="text/css" href="static_resources/css/nav_change.css" />-->
<script type="text/javascript" src="static_resources/js/common/system_config.js"></script>
<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="static_resources/js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="static_resources/js/swiper-3.4.2.jquery.min.js"></script>
<script type="text/javascript" src="static_resources/js/h5_exit.js"></script>
<script type="text/javascript" src="static_resources/js/app2wx_page.js?111"></script>
<script type="text/javascript" src="static_resources/js/index_goto.js?12"></script>

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
	  	<div class="top_box">
			<div class="top" style="z-index:99999">
		        <h1 ><a href="javascript:close();" class="aleft"></a>${pageMap["page_title"][0].title!}</h1>
		    </div>
	    </div>
	  </#if>
	<div class="main_box" style="position:absolute;top:45px;left:0;">
		
		<#if pageMap["banner"]??>
		   <div class="flexslider" id="banner">
		   		<ul class="slides">
				  <#assign l =pageMap["banner"]?size-1 >
				  <#if l gte 1>
					<#list 1..l as i>
					<li>
						<a href="javascript:go2Search('${pageMap["banner"][i].link!}')">
							<img src="${pageMap["banner"][i].imagePath!}" alt="" class="img-responsive">
						</a>
					</li>
					</#list>
				  </#if>
					<li>
						<a href="javascript:go2Search('${pageMap["banner"][0].link!}')">
							<img src="${pageMap["banner"][0].imagePath!}" alt="" class="img-responsive">
						</a>
					</li>
		   		 </ul>
			</div>
		</#if>
		
		<#if pageMap["nav_box"]??>
	    <div class="nav_box">
	    	<div class="nav_con">
	    		<div class="con_lt">全部</div>
	    		<#assign nav_boxs =pageMap["nav_box"] >
				<#if nav_boxs??>
	    		<div class="nav_div ">
				    <ul class="nav_ul">
				    <#list nav_boxs as nav_box>
				        <li><a href="javascript:js_method('${nav_box.ex1Value!}')">${nav_box.title!}</a></li>
				    </#list>
				    </ul>
				</div>
	    		<div class="con_rt"><a href="javascript:;"></a></div>
	    	</#if>
	    	</div>
	    </div>	
	    </#if>
	    
	    <!--不加链接时手动添加内购价电商价-->
	    <#if pageMap["0f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["0f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["0f_imagesTitle"][0].link!}"><img src="${pageMap["0f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["0f_product"]??>
			<#assign products =pageMap["0f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${pageMap["0f_product"][0].ex1Value!}</dd>
				        			<dd class="dd3">电商价：￥${pageMap["0f_product"][0].ex2Value!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
		
	    <!-- 1f -->
	    <#if pageMap["1f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["1f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["1f_imagesTitle"][0].link!}"><img src="${pageMap["1f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["1f_product"]??>
			<#assign products =pageMap["1f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     
	     <!-- 2f -->
	     <#if pageMap["2f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["2f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["2f_imagesTitle"][0].link!}"><img src="${pageMap["2f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["2f_product"]??>
			<#assign products =pageMap["2f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 3f -->
	     <#if pageMap["3f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["3f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["3f_imagesTitle"][0].link!}"><img src="${pageMap["3f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["3f_product"]??>
			<#assign products =pageMap["3f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 4f -->
	     <#if pageMap["4f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["4f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["4f_imagesTitle"][0].link!}"><img src="${pageMap["4f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["4f_product"]??>
			<#assign products =pageMap["4f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 5f -->
	     <#if pageMap["5f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["5f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["5f_imagesTitle"][0].link!}"><img src="${pageMap["5f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["5f_product"]??>
			<#assign products =pageMap["5f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 6f -->
	     <#if pageMap["6f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["6f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["6f_imagesTitle"][0].link!}"><img src="${pageMap["6f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["6f_product"]??>
			<#assign products =pageMap["6f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 7f -->
	     <#if pageMap["7f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["7f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["7f_imagesTitle"][0].link!}"><img src="${pageMap["7f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["7f_product"]??>
			<#assign products =pageMap["7f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 8f -->
	     <#if pageMap["8f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["8f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["8f_imagesTitle"][0].link!}"><img src="${pageMap["8f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["8f_product"]??>
			<#assign products =pageMap["8f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 9f -->
	     <#if pageMap["9f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["9f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["9f_imagesTitle"][0].link!}"><img src="${pageMap["9f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["9f_product"]??>
			<#assign products =pageMap["9f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 10f -->
	     <#if pageMap["10f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["10f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["10f_imagesTitle"][0].link!}"><img src="${pageMap["10f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["10f_product"]??>
			<#assign products =pageMap["10f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 11f -->
	     <#if pageMap["11f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["11f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["11f_imagesTitle"][0].link!}"><img src="${pageMap["11f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["11f_product"]??>
			<#assign products =pageMap["11f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 12f -->
	     <#if pageMap["12f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["12f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["12f_imagesTitle"][0].link!}"><img src="${pageMap["12f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["12f_product"]??>
			<#assign products =pageMap["12f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 13f -->
	     <#if pageMap["13f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["13f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["13f_imagesTitle"][0].link!}"><img src="${pageMap["13f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["13f_product"]??>
			<#assign products =pageMap["13f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 14f -->
	     <#if pageMap["14f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["14f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["14f_imagesTitle"][0].link!}"><img src="${pageMap["14f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["14f_product"]??>
			<#assign products =pageMap["14f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 15f -->
	     <#if pageMap["15f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["15f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["15f_imagesTitle"][0].link!}"><img src="${pageMap["15f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["15f_product"]??>
			<#assign products =pageMap["15f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 16f -->
	     <#if pageMap["16f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["16f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["16f_imagesTitle"][0].link!}"><img src="${pageMap["16f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["16f_product"]??>
			<#assign products =pageMap["16f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 17f -->
	     <#if pageMap["17f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["17f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["17f_imagesTitle"][0].link!}"><img src="${pageMap["17f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["17f_product"]??>
			<#assign products =pageMap["17f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 18f -->
	     <#if pageMap["18f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["18f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["18f_imagesTitle"][0].link!}"><img src="${pageMap["18f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["18f_product"]??>
			<#assign products =pageMap["18f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 19f -->
	     <#if pageMap["19f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["19f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["19f_imagesTitle"][0].link!}"><img src="${pageMap["19f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["19f_product"]??>
			<#assign products =pageMap["19f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 20f -->
	     <#if pageMap["20f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["20f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["20f_imagesTitle"][0].link!}"><img src="${pageMap["20f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["20f_product"]??>
			<#assign products =pageMap["20f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 21f -->
	     <#if pageMap["21f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["21f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["21f_imagesTitle"][0].link!}"><img src="${pageMap["21f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["21f_product"]??>
			<#assign products =pageMap["21f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 22f -->
	     <#if pageMap["22f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["22f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["22f_imagesTitle"][0].link!}"><img src="${pageMap["22f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["22f_product"]??>
			<#assign products =pageMap["22f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 23f -->
	     <#if pageMap["23f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["23f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["23_imagesTitle"][0].link!}"><img src="${pageMap["23f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["23f_product"]??>
			<#assign products =pageMap["23f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 24f -->
	     <#if pageMap["24f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["24f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["24f_imagesTitle"][0].link!}"><img src="${pageMap["24f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["24f_product"]??>
			<#assign products =pageMap["24f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     <!-- 25f -->
	     <#if pageMap["25f_imagesTitle"]??>
		<div class="img_tit" id="${pageMap["25f_imagesTitle"][0].ex1Value!}"><a href="${pageMap["25f_imagesTitle"][0].link!}"><img src="${pageMap["25f_imagesTitle"][0].imagePath!}" /></a></div>
		</#if>
		
		<#if pageMap["25f_product"]??>
			<#assign products =pageMap["25f_product"] >
		    <div class="nav_change_con swiper-container" >
	     		<ul class="swiper-wrapper" >			       
			    	<#if products??>	
			    		<#list products as product>
				        <li class="swiper-slide">
				        	<a href="${product.link!}">
				        		<dl>
				        			<dt><img src="${product.imagePath!}" /></dt>
				        			<dd class="dd1">${product.title!}</dd>
				        			<dd class="dd2">￥${product.proSalePrice!}</dd>
				        			<dd class="dd3">电商价：￥${product.proPrice!}</dd>
				        		</dl>
				        	</a>
				        </li>
				       </#list>
					</#if>			    	
			     </ul>
		     </div>
		</#if>
	     
	     <#if pageMap["imgLink_imagesTitle"]??>
			<div class="img_tit"><a href="${pageMap["imgLink_imagesTitle"][0].link!}"><img src="${pageMap["imgLink_imagesTitle"][0].imagePath!}" /></a></div>
		 </#if>
	     <#if pageMap["img_con"]??>
	     <div class="img_con">
	     	<a href="${pageMap["img_con"][0].link!}" class="big_img"><img src="${pageMap["img_con"][0].imagePath!}" /></a>
	     	<a href="${pageMap["img_con"][1].link!}" class="small_img"><img src="${pageMap["img_con"][1].imagePath!}" /></a>
	     	<a href="${pageMap["img_con"][2].link!}" class="small_img"><img src="${pageMap["img_con"][2].imagePath!}" /></a>
	     </div>
	     </#if>
	     <#if pageMap["img_con_more"]??>
	     <div class="small_img_box">	     	
			<#list pageMap["img_con_more"] as brand>
     			  <#if (brand_index % 2 == 0) >
					<a href="javascript:;" class="big_img">
				  </#if>
	    		  <#if (brand_index % 2 == 1) >
					<a href="${brand.link!}" class="small_img">
				  </#if>
		     			<img src="${brand.imagePath!}" />
		     	   </a>
		  	</#list>			
	     </div>
	     </#if>
	     
	     <#if pageMap["footer"]??>
			<div class="footer">
		    	<a href="${pageMap["footer"][0].link!}"><img src="${pageMap["footer"][0].imagePath!}" /></a>
			</div>
		</#if>
	</div>
</div>

<div class="back_top" id="back_top"><img src="static_resources/images/back_top_btn1.png" /></div>
<script>
$(function(){
	
	//导航切换
    $(".con_rt a").toggle(function(){
		$(this).css({"background":"url(static_resources/images/ypp_icon1.png) no-repeat right center","background-size":"16px 9px"});
		$(".nav_div").addClass("nav_div_position");
		$(".con_lt").html('切换楼层');
	},function(){
		$(this).css({"background":"url(static_resources/images/ypp_icon.png) no-repeat right center","background-size":"16px 9px"});
		$(".nav_div").removeClass("nav_div_position");
		$(".con_lt").html('全部');
	});
	//$(".nav_ul li a").click(function(){
	//	$(".con_rt a").css({"background":"url(static_resources/images/ypp_icon.png) no-repeat right center","background-size":"16px 9px"});
	//	$(".nav_div").removeClass("nav_div_position");
	//	$(".con_lt").html('全部');
	//});
	
	//导航滚动
	var a = $('.nav_con'), b = a.offset();

	$(document).on('scroll', function() {
		var c = $(document).scrollTop();

		if (c-$("#banner").height()>0) {
			a.addClass("fixed");
		} else {
			a.removeClass("fixed");
		}
		
        var topmun = 150;
        if(isWeiXinH5()) {
            topmun=100
        }
        //导航切换
        $(".nav_div ul li").each(function(index){
            var href = $(this).find("a").attr("href");
            href=href.substring(href.indexOf("'")+1);
            href=href.substring(0,href.indexOf("'"));
            var y = $("#"+href).offset().top;

            if(y-c < topmun) {
                $(".nav_div ul li").removeClass("crr")
                $(this).addClass("crr")
            }
        });
	});
	
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main_box").css("top","0");
		$(".footer").show();
		$(".nav_con").css("top","0");
	};
	
	//导航切换
	$(".nav_div ul li").each(function(index){
		$(this).click(function(){//先把所有的隐藏掉
 			
 			$(".nav_div ul li").removeClass("crr")
 			$(this).addClass("crr")
 		});	
 		
 	});
 	
 	//返回顶部
	$("#back_top").hide();
	$(window).scroll(function () {
		if ($(window).scrollTop() > 100) {
			$("#back_top").fadeIn(400);//当滑动栏向下滑动时，按钮渐现的时间
		} else {
			$("#back_top").fadeOut(0);//当页面回到顶部第一屏时，按钮渐隐的时间
		}
	});
	$("#back_top").click(function () {
		$("html,body").animate({
			scrollTop : "0px"
		}, 300);//返回顶部所用的时间 返回顶部也可调用goto()函数
	});	
	
    $('.swiper-wrapper').width($("body").width());
})
	function js_method(href){
		$(".con_rt a").css({"background":"url(static_resources/images/ypp_icon.png) no-repeat right center","background-size":"16px 9px"});
		$(".nav_div").removeClass("nav_div_position");
		$(".con_lt").html('全部');
		
        if(isWeiXinH5()) {
            $("body").scrollTop($("#"+href).offset().top-34);
        } else {
            $("body").scrollTop($("#"+href).offset().top-45-34);
        }
	}
</script>
<script>
//左右滑动
var mySwiper = new Swiper('.swiper-container',{
slidesPerView : 'auto',//'auto'
//slidesPerView : 3.7,
})
</script>
</body>
</html>
