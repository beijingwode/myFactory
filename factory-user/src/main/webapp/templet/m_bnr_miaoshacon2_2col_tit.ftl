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

<style>
	<#if pageMap["bg_style"]??>
		body{background:#${pageMap["bg_style"][0].ex1Value!}}
	</#if>
	<#if pageMap["color1_style"]??>
		.color1{background:#${pageMap["color1_style"][0].ex1Value!}}
		.color1 dl{background:#${pageMap["color1_style"][0].ex2Value!}}
		.color1 dl .dd3{color:#${pageMap["color1_style"][0].ex3Value!}}
		.color1 dl .dd5 a{background:#${pageMap["color1_style"][0].ex4Value!}}
	</#if>
	<#if pageMap["color2_style"]??>	
		.color2{background:#${pageMap["color2_style"][0].ex1Value!}}		
		.color2 dl{background:#${pageMap["color2_style"][0].ex2Value!}}
		.color2 dl .dd3{color:#${pageMap["color2_style"][0].ex3Value!}}
		.color2 dl .dd5 a{background:#${pageMap["color2_style"][0].ex4Value!}}
	</#if>
	<#if pageMap["color3_style"]??>	
		.color3{background:#${pageMap["color3_style"][0].ex1Value!}}		
		.color3 dl{background:#${pageMap["color3_style"][0].ex2Value!}}
		.color3 dl .dd3{color:#${pageMap["color3_style"][0].ex3Value!}}
		.color3 dl .dd5 a{background:#${pageMap["color3_style"][0].ex4Value!}}
	</#if>
	<#if pageMap["color4_style"]??>	
		.color4{background:#${pageMap["color4_style"][0].ex1Value!}}		
		.color4 dl{background:#${pageMap["color4_style"][0].ex2Value!}}
		.color4 dl .dd3{color:#${pageMap["color4_style"][0].ex3Value!}}
		.color4 dl .dd5 a{background:#${pageMap["color4_style"][0].ex4Value!}}
	</#if>
	<#if pageMap["color5_style"]??>	
		.color5{background:#${pageMap["color5_style"][0].ex1Value!}}		
		.color5 dl{background:#${pageMap["color5_style"][0].ex2Value!}}
		.color5 dl .dd3{color:#${pageMap["color5_style"][0].ex3Value!}}
		.color5 dl .dd5 a{background:#${pageMap["color5_style"][0].ex4Value!}}
	</#if>
	<#if pageMap["tit_style"]??>
		.tit_text{color:#${pageMap["tit_style"][0].ex1Value!};}
	</#if>
	
</style>
<script type="text/javascript" src="static_resources/js/common/system_config.js"></script>
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
	
		<div class="main_one color1">
			<div class="main_one_con">
				<#if pageMap["mainone_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainone_imagesTitle"][0].link!}"><img src="${pageMap["mainone_imagesTitle"][0].imagePath!}" /></a></div>
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
						<dd class="dd4" style="color:#${product.ex5Value!}">${product.ex2Value!}:￥<em>${product.ex3Value!}</em></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">${product.ex4Value!}</a></dd>
					</dl>
					</#list>
				</#if>
	  			</#if>
				
			</div>
		</div>
		<div class="main_one color2">
			<div class="main_one_con">
				<#if pageMap["mainone2_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainone2_imagesTitle"][0].link!}"><img src="${pageMap["mainone2_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainone2_title"]??>
				<div class="tit_text">${pageMap["mainone2_title"][0].title!}</div>
				</#if>
				
				<#if pageMap["mainone2_product"]??>
				<#assign products =pageMap["mainone2_product"] >
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
						<dd class="dd4" style="color:#${product.ex5Value!}">${product.ex2Value!}:￥<em>${product.ex3Value!}</em></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">${product.ex4Value!}</a></dd>
					</dl>
					</#list>
				</#if>
	  			</#if>			
			</div>
		</div>
		
		<div class="main_one color3">
			<div class="main_one_con">
				<#if pageMap["mainone3_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainone3_imagesTitle"][0].link!}"><img src="${pageMap["mainone3_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainone3_title"]??>
				<div class="tit_text">${pageMap["mainone3_title"][0].title!}</div>
				</#if>
				
				<#if pageMap["mainone3_product"]??>
				<#assign products =pageMap["mainone3_product"] >
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
						<dd class="dd4" style="color:#${product.ex5Value!}">${product.ex2Value!}:￥<em>${product.ex3Value!}</em></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">${product.ex4Value!}</a></dd>
					</dl>
					</#list>
				</#if>
	  			</#if>			
			</div>
		</div>
		
		<div class="main_one color4">
			<div class="main_one_con">
				<#if pageMap["mainone4_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainone4_imagesTitle"][0].link!}"><img src="${pageMap["mainone4_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainone4_title"]??>
				<div class="tit_text">${pageMap["mainone4_title"][0].title!}</div>
				</#if>
				
				<#if pageMap["mainone4_product"]??>
				<#assign products =pageMap["mainone4_product"] >
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
						<dd class="dd4" style="color:#${product.ex5Value!}">${product.ex2Value!}:￥<em>${product.ex3Value!}</em></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">${product.ex4Value!}</a></dd>
					</dl>
					</#list>
				</#if>
	  			</#if>			
			</div>
		</div>
		
		<div class="main_one color5">
			<div class="main_one_con">
				<#if pageMap["mainone5_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainone5_imagesTitle"][0].link!}"><img src="${pageMap["mainone5_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainone5_title"]??>
				<div class="tit_text">${pageMap["mainone5_title"][0].title!}</div>
				</#if>
				
				<#if pageMap["mainone5_product"]??>
				<#assign products =pageMap["mainone5_product"] >
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
						<dd class="dd4" style="color:#${product.ex5Value!}">${product.ex2Value!}:￥<em>${product.ex3Value!}</em></dd>
						<dd class="dd3">电商价：￥${product.proPrice!}</dd>
						<dd class="dd5"><a href="${product.link!}">${product.ex4Value!}</a></dd>
					</dl>
					</#list>
				</#if>
	  			</#if>			
			</div>
		</div>
		
		<div class="main_two">
			<div class="main_two_con">
				<#if pageMap["mainthree1_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainthree1_imagesTitle"][0].link!}"><img src="${pageMap["mainthree1_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainthree1_title"]??>
				<div class="tit_text">${pageMap["mainthree1_title"][0].title!}</div>
				</#if>
				
				<ul>
				<#if pageMap["mainthree1_product"]??>
				<#assign products =pageMap["mainthree1_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    		
		    		</li>
					</#list>
				</#if>
		    	</#if>
				</ul>	
						
				<#if pageMap["mainthree2_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainthree2_imagesTitle"][0].link!}"><img src="${pageMap["mainthree2_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainthree2_title"]??>
				<div class="tit_text">${pageMap["mainthree2_title"][0].title!}</div>
				</#if>
				
				<ul>
				<#if pageMap["mainthree2_product"]??>
				<#assign products =pageMap["mainthree2_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    		
		    		</li>
					</#list>
				</#if>
		    	</#if>
				</ul>	
					
				<#if pageMap["mainthree3_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainthree3_imagesTitle"][0].link!}"><img src="${pageMap["mainthree3_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainthree3_title"]??>
				<div class="tit_text">${pageMap["mainthree3_title"][0].title!}</div>
				</#if>
				
				<ul>
				<#if pageMap["mainthree3_product"]??>
				<#assign products =pageMap["mainthree3_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    		
		    		</li>
					</#list>
				</#if>
			    </#if>
			   </ul>
			   
			   <#if pageMap["mainthree4_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainthree4_imagesTitle"][0].link!}"><img src="${pageMap["mainthree4_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainthree4_title"]??>
				<div class="tit_text">${pageMap["mainthree4_title"][0].title!}</div>
				</#if>
				
				<ul>
				<#if pageMap["mainthree4_product"]??>
				<#assign products =pageMap["mainthree4_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    		
		    		</li>
					</#list>
				</#if>
			    </#if>
			   </ul>
			   
			   <#if pageMap["mainthree5_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainthree5_imagesTitle"][0].link!}"><img src="${pageMap["mainthree5_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainthree5_title"]??>
				<div class="tit_text">${pageMap["mainthree5_title"][0].title!}</div>
				</#if>
				
				<ul>
				<#if pageMap["mainthree5_product"]??>
				<#assign products =pageMap["mainthree5_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    		
		    		</li>
					</#list>
				</#if>
			    </#if>
			   </ul>
			   
			   <#if pageMap["mainthree6_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainthree6_imagesTitle"][0].link!}"><img src="${pageMap["mainthree6_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainthree6_title"]??>
				<div class="tit_text">${pageMap["mainthree6_title"][0].title!}</div>
				</#if>
				
				<ul>
				<#if pageMap["mainthree6_product"]??>
				<#assign products =pageMap["mainthree6_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    		
		    		</li>
					</#list>
				</#if>
			    </#if>
			   </ul>
			   
			   <#if pageMap["mainthree7_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainthree7_imagesTitle"][0].link!}"><img src="${pageMap["mainthree7_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainthree7_title"]??>
				<div class="tit_text">${pageMap["mainthree7_title"][0].title!}</div>
				</#if>
				
				<ul>
				<#if pageMap["mainthree7_product"]??>
				<#assign products =pageMap["mainthree7_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    		
		    		</li>
					</#list>
				</#if>
			    </#if>
			   </ul>
			   
			   <#if pageMap["mainthree8_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainthree8_imagesTitle"][0].link!}"><img src="${pageMap["mainthree8_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainthree8_title"]??>
				<div class="tit_text">${pageMap["mainthree8_title"][0].title!}</div>
				</#if>
				
				<ul>
				<#if pageMap["mainthree8_product"]??>
				<#assign products =pageMap["mainthree8_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    		
		    		</li>
					</#list>
				</#if>
			    </#if>
			   </ul>
			   
			   <#if pageMap["mainthree9_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainthree9_imagesTitle"][0].link!}"><img src="${pageMap["mainthree9_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainthree9_title"]??>
				<div class="tit_text">${pageMap["mainthree9_title"][0].title!}</div>
				</#if>
				
				<ul>
				<#if pageMap["mainthree9_product"]??>
				<#assign products =pageMap["mainthree9_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    		
		    		</li>
					</#list>
				</#if>
			    </#if>
			   </ul>
			   
			   <#if pageMap["mainthree10_imagesTitle"]??>
				<div class="tit_img"><a href="${pageMap["mainthree10_imagesTitle"][0].link!}"><img src="${pageMap["mainthree10_imagesTitle"][0].imagePath!}" /></a></div>
				</#if>
				<#if pageMap["mainthree10_title"]??>
				<div class="tit_text">${pageMap["mainthree10_title"][0].title!}</div>
				</#if>
				
				<ul>
				<#if pageMap["mainthree10_product"]??>
				<#assign products =pageMap["mainthree10_product"] >
		    	<#if products??>	
		    		<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    		
		    		</li>
					</#list>
				</#if>
			    </#if>
			   </ul>
			   
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
