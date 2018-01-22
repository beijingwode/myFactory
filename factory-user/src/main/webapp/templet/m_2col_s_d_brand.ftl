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
<body>

<div class="main-cont" id="main-cont" >
	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
  <#if pageParam["supplier_id"]??>
	<input type="hidden" id="supplierId" value="${pageParam["supplier_id"]!}">
	<input type="hidden" id="supplierName" value="${pageParam["supplier_name"]!}">
  </#if>
  
  <#if pageMap["page_title"]??>
	<div class="top">
        <h1 class="qicai_h1"><a href="javascript:close();" class="aleft"></a>${pageMap["page_title"][0].title!}</h1>
    </div>
  </#if>
  
	<#if pageMap["product"]??>
    <#assign productList =pageMap["product"] >
	<div class="main_box qicai_main_box">
		<div class="qicai_con">
			<ul>
			  <#list productList as p>
			  	<li>
					<a href="${p.link!}">
						<dl>
							<dt><img src="${p.imagePath!}" /></dt>
							<dd class="dd1">${p.proBrand!}</dd>
							<dd class="dd2">${p.title!}</dd>
							<dd class="dd3"><span>￥${p.proSalePrice!}</span><em>${p.proDiscount!}折</em></dd>
						</dl>
					</a>
				</li>
			  </#list>
			</ul>
		</div>
	</div>
    </#if>
</div>
    
</body>
<script type="text/javascript" src="static_resources/js/app2wx_page.js?111"></script>
</html>
