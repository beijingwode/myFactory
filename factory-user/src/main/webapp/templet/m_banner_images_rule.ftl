<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<#if pageMap["page_title"]??>
<title>${pageMap["page_title"][0].title!}</title>
</#if>

<#if pageMap["page_css"]??>
<link rel="stylesheet" type="text/css" href="${pageMap["page_css"][0].ex1Value!}" />
<link rel="stylesheet" type="text/css" href="${pageMap["page_css"][0].ex2Value!}" />
</#if>
<script type="text/javascript" src="static_resources/js/common/system_config.js"></script>
</head> 

<body>

<div class="main-cont" id="main-cont">

<!--banner begin-->
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
<!--banner end-->

<!-- 换领  start-->
<#if pageMap["hot_product"]??>
<#assign hotProducts =pageMap["hot_product"] >
<#assign l =hotProducts?size-hotProducts?size%3 >
<div class="ts_box">
	<div class="ts_tit">${hotProducts[0].pageTypeTitle!}</div>
	<div class="ts_con">
		<ul>
			<#list 0..l-1 as i>
			<li>
				<a href="javascript:go2Search('${hotProducts[i].link!}')">
					<img src="${hotProducts[i].imagePath!}" />
					<div class="bp"></div>
					<dl>
						<dd><a href="javascript:go2Search('${hotProducts[i].link!}')">${hotProducts[i].title!}</a></dd>
						<dd><span>${hotProducts[i].proPrice!}</span></dd>
						<dd><em>${hotProducts[i].proDescription!}</em></dd>
					</dl>
				</a>
			</li>
			</#list>  
	
		</ul>
	</div>
</div>
</#if>
<!-- 换领  end-->

<!--content begin-->
<div class="main_box">
	<#if pageMap["channel_rule"]??>
		<div class="hl_con">
   		<#assign imageList =pageMap["channel_rule"] >
  		<#list imageList as image>
  			<img src="${image.imagePath!}" alt=""  />
		</#list>
		</div>
	</#if>
    <!--1F begin-->
    <#if pageMap["1f_product"]??>
    <#assign products =pageMap["1f_product"] >
	<#assign x =products?size-products?size%3 >
	<#if x gt 2>
    <div class="item">
    	<div class="ts_tit">${products[0].pageTypeTitle!}</div>
		<div class="item_con">
			<ul>
				<#list 0..x-1 as i>
				<li>
					<dl>
						<dt><a href="javascript:go2Search('${products[i].link!}')"><img src="${products[i].imagePath!}" width="292px" height="292" /></a></dt>
						<dd style="margin-top:10px;"><a href="javascript:go2Search('${products[i].link!}')" style="overflow: hidden;white-space:nowrap;" >&nbsp;&nbsp;${products[i].title!}&nbsp;&nbsp;</a></dd>
						<dd><span>${products[i].proPrice!}</span></dd>
						<dd><em>${products[i].proDescription!}</em></dd>
					</dl>
				</li>
				</#list>  
			</ul>
		</div>
    </div>
    </#if>
    </#if>
    <!--1F end-->   
    
    <!--2F begin-->
    <#if pageMap["2f_product"]??>
    <#assign products =pageMap["2f_product"] >
	<#assign x =products?size-products?size%3 >
	<#if x gt 2>
    <div class="item">
    	<div class="ts_tit">${products[0].pageTypeTitle!}</div>
		<div class="item_con">
			<ul>
				<#list 0..x-1 as i>
				<li>
					<dl>
						<dt><a href="javascript:go2Search('${products[i].link!}')"><img src="${products[i].imagePath!}" width="292px" height="292" /></a></dt>
						<dd style="margin-top:10px;"><a href="javascript:go2Search('${products[i].link!}')" style="overflow: hidden;white-space:nowrap;" >&nbsp;&nbsp;${products[i].title!}&nbsp;&nbsp;</a></dd>
						<dd><span>${products[i].proPrice!}</span></dd>
						<dd><em>${products[i].proDescription!}</em></dd>
					</dl>
				</li>
				</#list>  
			</ul>
		</div>
    </div>
    </#if>
    </#if>
    <!--2F end-->
    
    <!--3F begin-->
    <#if pageMap["3f_product"]??>
    <#assign products =pageMap["3f_product"] >
	<#assign x =products?size-products?size%3 >
	<#if x gt 2>
    <div class="item">
    	<div class="ts_tit">${products[0].pageTypeTitle!}</div>
		<div class="item_con">
			<ul>
				<#list 0..x-1 as i>
				<li>
					<dl>
						<dt><a href="javascript:go2Search('${products[i].link!}')"><img src="${products[i].imagePath!}" width="292px" height="292" /></a></dt>
						<dd style="margin-top:10px;"><a href="javascript:go2Search('${products[i].link!}')" style="overflow: hidden;white-space:nowrap;" >&nbsp;&nbsp;${products[i].title!}&nbsp;&nbsp;</a></dd>
						<dd><span>${products[i].proPrice!}</span></dd>
						<dd><em>${products[i].proDescription!}</em></dd>
					</dl>
				</li>
				</#list>  
			</ul>
		</div>
    </div>
    </#if>
    </#if>
    <!--3F end-->
    
    <!--4F begin-->
    <#if pageMap["4f_product"]??>
    <#assign products =pageMap["4f_product"] >
	<#assign x =products?size-products?size%3 >
	<#if x gt 2>
    <div class="item">
		
    	<div class="ts_tit">${products[0].pageTypeTitle!}</div>
		<div class="item_con">
			<ul>
				<#list 0..x-1 as i>
				<li>
					<dl>
						<dt><a href="javascript:go2Search('${products[i].link!}')"><img src="${products[i].imagePath!}" width="292px" height="292" /></a></dt>
						<dd style="margin-top:10px;"><a href="javascript:go2Search('${products[i].link!}')" style="overflow: hidden;white-space:nowrap;" >&nbsp;&nbsp;${products[i].title!}&nbsp;&nbsp;</a></dd>
						<dd><span>${products[i].proPrice!}</span></dd>
						<dd><em>${products[i].proDescription!}</em></dd>
					</dl>
				</li>
				</#list>  
			</ul>
		</div>
    </div>
    </#if>
    </#if>
    <!--4F end-->
       
    <!--5F begin-->
    <#if pageMap["5f_product"]??>
    <#assign products =pageMap["5f_product"] >
	<#assign x =products?size-products?size%3 >
	<#if x gt 2>
    <div class="item">
		
    	<div class="ts_tit">${products[0].pageTypeTitle!}</div>
		<div class="item_con">
			<ul>
				<#list 0..x-1 as i>
				<li>
					<dl>
						<dt><a href="javascript:go2Search('${products[i].link!}')"><img src="${products[i].imagePath!}" width="292px" height="292" /></a></dt>
						<dd style="margin-top:10px;"><a href="javascript:go2Search('${products[i].link!}')" style="overflow: hidden;white-space:nowrap;" >&nbsp;&nbsp;${products[i].title!}&nbsp;&nbsp;</a></dd>
						<dd><span>${products[i].proPrice!}</span></dd>
						<dd><em>${products[i].proDescription!}</em></dd>
					</dl>
				</li>
				</#list>  
			</ul>
		</div>
    </div>
    </#if>
    </#if>
    <!--5F end-->
   
    <div class="youlike_box" id="more" style="display:none;">
			<#if pageMap["more"]??>
			<input type="hidden" id="moreKey" value="${pageMap["more"][0].ex2Value!}">
			<input type="hidden" id="moreVal" value="${pageMap["more"][0].ex3Value!}">
    		<div class="like_tit"><span>${pageMap["more"][0].title!}</span></div>
			<div class="like_con">
				<ul id="like_li">
				</ul>
			</div>
	      	</#if>
    	</div> 
<!--content end-->

	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
  <#if pageParam["supplier_id"]??>
	<input type="hidden" id="supplierId" value="${pageParam["supplier_id"]!}">
	<input type="hidden" id="supplierName" value="${pageParam["supplier_name"]!}">
  </#if>
</div>
</body>

<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="static_resources/js/more_2col.js"></script>
<script type="text/javascript" src="static_resources/js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="static_resources/js/wxGetUid.js"></script>
<script type="text/javascript" src="static_resources/js/index_goto.js?12"></script>
<script type="text/javascript" src="static_resources/js/h5_exit.js"></script>
<script type="text/javascript" src="static_resources/js/app2wx_page.js?111"></script>
</html>
