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
		.tit{background:${pageMap["tit_style"][0].ex1Value!};color:#${pageMap["tit_style"][0].ex2Value!};}
	</#if>
	<#if pageMap["onebg_style"]??>
		.main_one_con{background:${pageMap["onebg_style"][0].ex1Value!};}
	</#if>
	<#if pageMap["linkBtn_style"]??>
		@media screen  and (max-width:414px){
			.main_one_con ul li dl .dd3 a{background:${pageMap["linkBtn_style"][0].ex1Value!};width:120px;height:27px;display:block;background-size:120px 27px;}
		 }
		 
		 @media screen  and (max-width:375px){
			.main_one_con ul li dl .dd3 a{background:${pageMap["linkBtn_style"][0].ex1Value!};width:110px;height:25px;display:block;background-size:110px 25px;}
		 }
		 @media screen  and (max-width:320px){
			.main_one_con ul li dl .dd3 a{background:${pageMap["linkBtn_style"][0].ex1Value!};width:100px;height:23px;display:block;background-size:100px 23px;}
		 }
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
	
		 <div class="main_one_con">
		 	<#if pageMap["hint"]??> 				
		    	<p><i><img src="${pageMap["hint"][0].imagePath!}"></i>${pageMap["hint"][0].title!}</p>		    	
			</#if>	
	    	<#if pageMap["main_one_product"]??>
				<#assign products =pageMap["main_one_product"] >
		    	<#if products??>
		    	<ul>
		    		<#list products as product>		    	
		    		<li>
		    			
		    			<div class="img_bg"><img src="${product.ex2Value!}" /></div>
		    			
		    			<dl>
		    				<dt><img src="${product.imagePath!}" /></dt>
		    				<dd class="dd1">${product.title!}</dd>
		    				<dd class="dd2">${product.ex1Value!}</dd>		    				
		    				<dd class="dd3"><a href="${product.link!}" style="background:${product.ex4Value!};background-size:100%;"></a></dd>		    				
		    			</dl>
		    			
		    			<span><img src="${product.ex3Value!}" /></span>
		    			
		    		</li>
		    		</#list>	    		
		    	</ul>
		    	</#if>
		   </#if>
	    </div>
	    
	    
		<#if pageMap["main_two_imagesTitle"]??>
		<div class="tit_images"><img src="${pageMap["main_two_imagesTitle"][0].imagePath!}" /></div>
		</#if>
		<#if pageMap["main_two_title"]??>
		<div class="tit">${pageMap["main_two_title"][0].title!}</div>
		</#if>
		
		<#if pageMap["main_two_product"]??>
			<#assign products =pageMap["main_two_product"] >
	    	<#if products??>	
			<div class="main_two_con">
				<ul>
					<#list products as product>
					<li>
		    			<dl>
		    				<dt><a href="${product.link!}"><img src="${product.imagePath!}"></a></dt>
		    				<dd class="dd1"><a href="${product.link!}">${product.title!}</a></dd>
		    				<dd class="dd2">内购价：￥<em>${product.proSalePrice!}</em></dd>
		    				<dd class="dd3">电商价：￥${product.proPrice!}</dd>
		    			</dl>
		    			
		    			<span><img src="${product.ex1Value!}" /></span>
		    			
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
<script>
	var supId=$("#supplierId");
	var supIdVal=$("#supplierId").val();
	if(supId.length>0 && supIdVal!=""){
		sessionStorage.setItem("supplierId", supIdVal);
	};
	
	$(document).ready(function() {
		var pageKey=$("#pageKey").val();
		if("huiyuan"==pageKey) {
			$(".main_box a").attr("href","https://open.weixin.qq.com/connect/oauth2/authorize?appid="+weixin_open_appId+"&redirect_uri="+system_domain+"wx/bridge&response_type=code&scope=snsapi_base&state=offTrailEvent#wechat_redirect");
		}
	});
</script>
</html>
