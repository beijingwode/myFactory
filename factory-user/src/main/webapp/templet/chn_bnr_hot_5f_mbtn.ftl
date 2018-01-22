<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="我的网">
<meta name="keywords" content="我的网">
<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="css/subpage.css" />
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

<!-- hot start-->
<#if pageMap["hot_product"]??>
<#assign hotProducts =pageMap["hot_product"] >
<div class="ts_box">
	<div class="ts_tit">${hotProducts[0].sectionTitle!}</div>
	<div class="ts_con">
		<ul>
            <#list hotProducts as hotProduct>
			<#if hotProduct_index lt 4>
			<li>
				<a href="${hotProduct.link!}"  target="_blank">
					<img src="${hotProduct.imagePath!}" width="292px" height="292" />
					<div class="bp"></div>
					<dl>
						<dd class="dd1"><a href="${hotProduct.link!}" title="${hotProduct.title!}"  target="_blank">${hotProduct.title!}</a></dd>
						<dd><span>内购价：￥<i>${hotProduct.proSalePrice!}</i></span></dd>
						<dd><em>电商价：￥${hotProduct.proPrice!}</em></dd>
					</dl>
				</a>
			</li>
			</#if>
			</#list>  
	
		</ul>
	</div>
</div>
</#if>
<!-- hot  end-->
						
<!--content begin-->


 <#if pageMap["channel_rule"]??>
 <div class="main_box main_box_sy">
	<div class="sy_main">
	<#assign imageList =pageMap["channel_rule"] >
	<#list imageList as image>
		<img src="${image.imagePath!}" alt=""  />
	</#list>
	</div>
 </div>
 </#if>


<div class="main_box">
              
    <!--1F begin-->
    <#if pageMap["1f_product"]??>
    <div class="item">
		<#assign products =pageMap["1f_product"] >
    	<#if products??>
    	<div class="ts_tit">${products[0].ex1Value!}</div>
		<div class="item_con">
			<ul>
            	<#list products as product>
				<li>
					<dl>
						<dt><a href="${product.link!}"  target="_blank"><img src="${product.imagePath!}" width="292px" height="292" /></a></dt>
						<dd class="dd1" style="margin-top:10px;"><a href="${product.link!}" title="${product.title!}"  target="_blank">${product.title!}</a></dd>
						<dd><span>内购价：￥<i>${product.proSalePrice!}</i></span></dd>
						<dd><em>电商价：￥${product.proPrice!}</em></dd>
					</dl>
				</li>
				</#list>  
			</ul>
		</div>
        </#if>
    </div>
    </#if>
    <!--1F end-->   
    
    <!--2F begin-->
    <#if pageMap["2f_product"]??>
    <div class="item">
		<#assign products =pageMap["2f_product"] >
    	<#if products??>
    	<div class="ts_tit">${products[0].ex1Value!}</div>
		<div class="item_con">
			<ul>
            	<#list products as product>
				<li>
					<dl>
						<dt><a href="${product.link!}"  target="_blank"><img src="${product.imagePath!}" width="292px" height="292" /></a></dt>
						<dd class="dd1" style="margin-top:10px;"><a href="${product.link!}" title="${product.title!}"  target="_blank">${product.title!}</a></dd>
						<dd><span>内购价：￥<i>${product.proSalePrice!}</i></span></dd>
						<dd><em>电商价：￥${product.proPrice!}</em></dd>
					</dl>
				</li>
				</#list>  
			</ul>
		</div>
        </#if>
    </div>
    </#if>
    <!--2F end-->
    
    <!--3F begin-->
    <#if pageMap["3f_product"]??>
    <div class="item">
		<#assign products =pageMap["3f_product"] >
    	<#if products??>
    	<div class="ts_tit">${products[0].ex1Value!}</div>
		<div class="item_con">
			<ul>
            	<#list products as product>
				<li>
					<dl>
						<dt><a href="${product.link!}"  target="_blank"><img src="${product.imagePath!}" width="292px" height="292" /></a></dt>
						<dd class="dd1" style="margin-top:10px;"><a href="${product.link!}" title="${product.title!}"  target="_blank">${product.title!}</a></dd>
						<dd><span>内购价：￥<i>${product.proSalePrice!}</i></span></dd>
						<dd><em>电商价：￥${product.proPrice!}</em></dd>
					</dl>
				</li>
				</#list>  
			</ul>
		</div>
        </#if>
    </div>
    </#if>
    <!--3F end-->
    
    <!--4F begin-->
    <#if pageMap["4f_product"]??>
    <div class="item">
		<#assign products =pageMap["4f_product"] >
    	<#if products??>
    	<div class="ts_tit">${products[0].ex1Value!}</div>
		<div class="item_con">
			<ul>
            	<#list products as product>
				<li>
					<dl>
						<dt><a href="${product.link!}"  target="_blank"><img src="${product.imagePath!}" width="292px" height="292" /></a></dt>
						<dd class="dd1" style="margin-top:10px;"><a href="${product.link!}" title="${product.title!}"  target="_blank">${product.title!}</a></dd>
						<dd><span>内购价：￥<i>${product.proSalePrice!}</i></span></dd>
						<dd><em>电商价：￥${product.proPrice!}</em></dd>
					</dl>
				</li>
				</#list>  
			</ul>
		</div>
        </#if>
    </div>
    </#if>
    <!--4F end-->
       
    <!--5F begin-->
    <#if pageMap["5f_product"]??>
    <div class="item">
		<#assign products =pageMap["5f_product"] >
    	<#if products??>
    	<div class="ts_tit">${products[0].ex1Value!}</div>
		<div class="item_con">
			<ul>
            	<#list products as product>
				<li>
					<dl>
						<dt><a href="${product.link!}"  target="_blank"><img src="${product.imagePath!}" width="292px" height="292" /></a></dt>
						<dd class="dd1" style="margin-top:10px;"><a href="${product.link!}"  target="_blank" title="${product.title!}">${product.title!}</a></dd>
						<dd><span>内购价：￥<i>${product.proSalePrice!}</i></span></dd>
						<dd><em>电商价：￥${product.proPrice!}</em></dd>
					</dl>
				</li>
				</#list>  
			</ul>
		</div>
        </#if>
    </div>
    </#if>
    <!--5F end--> 
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
	
	$("#btn_more").attr("onclick","to_more()");
});

function to_more(){
	window.location = "/${pageParam["page_key"]!}_more.html?key=" + $(".searchinput").val();
}
function setActive() {
	var pageKey="${pageParam["page_key"]!}";
	if(pageKey=='qc' || pageKey=='qicai') {
		$(".nav ul li:eq(4)").addClass("active");
	}
}
</script>
<script type="text/javascript" src="resources/js/top_ewm.js"></script>
<script type="text/javascript" src="/user/activityLog?url=${pageParam["page_key"]!}"></script>
</html>
