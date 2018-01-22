<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="我的网">
<meta name="keywords" content="我的网">
<title>我的福利</title>
<script type="text/javascript" src="resources/js/auto_css.js"></script>
<link rel="stylesheet" type="text/css" href="css/box.css">
</head>
<script type="text/javascript" src="resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="resources/js/jquery.kinMaxShow-1.0.src.js"></script>
<script type="text/javascript" src="resources/js/application.js"></script>
<script type="text/javascript" src="resources/js/index.js?3"></script>
<script type="text/javascript" src="resources/js/index_hot_brand.js"></script>
<script type="text/javascript" src="resources/js/index_clr_tab.js"></script>
<script type="text/javascript" src="resources/js/zzsc.js"></script>

<body>
<#include "header.ftl">
<!--banner begin-->
<div class="bannerwrapper">
    <#assign imageList =pageMap["focus_picture"] >
	<#if imageList??>
      <div class="banner" id="banner" >
      	<#list imageList as image>
				<a href="${image.link!}" class="d1" style="background:url(${image.imagePath!}) center no-repeat"></a>
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
    <div class="menu_wrap">
        <div class="menu">
            <div class="menu_title"><p>全部商品分类</p></div>
             <div class="menu_list">
				<ul>
            	<#if categoriesList??>
					<#list categoriesList as category>
                	<li class="mt">
                	<h2>
	                	<#if category.url?? &&  category.url?length gt 0>
	                	 <a href="/${category.url}">${category.name!}</a>
	                	<#else>
	                	 <a href="/channel${category.id?c}.html">${category.name!}</a>
	                	</#if>
					</h2>
                    </li>
                    </#list>
				</#if>
              </ul>     
            </div>      
        </div>
    </div>
</div>
<!--banner end-->

<!--content begin-->
<div id="content">
	<!--hot brand begin-->
	<#assign hotbrands =pageMap["hot_brand"] >
	<#if hotbrands??>
    <div class="hot_brand">
        <div class="hot_brand_pic">
        	<div class="brand_pho">
        		<img src="${hotbrands[0].imagePath!}" width="190" height="150" alt="${hotbrands[0].title!}">
        	</div>
            <div class="brand_list">
				<div class="picbox">
            	  <ul class="piclist mainlist">
				  <#list hotbrands as hotbrand>
					<#if hotbrand_index gt 0 && hotbrand_index lt 13>
                    <li><a href="${hotbrand.link!}" ><img src="${hotbrand.imagePath!}" width="250" height="164" alt="${hotbrand.title!}"></a></li>
                    </#if>
				  </#list>  
                  </ul>
				<ul class="piclist swaplist"></ul>
				</div>
				<div class="og_prev"></div>
				<div class="og_next"></div>			
            </div>
        </div>
    </div>
    </#if>
    <!--hot brand end-->
    
    <!-- 热门品牌开始 -->
	<#if pageMap["index_brand_product"]??>
	<#assign indexBrandProducts =pageMap["index_brand_product"] >
    <div class="hot_brand_box">
    	<div class="hot_brand_title"></div>
    	<div class="hot_brand_con">
    		<div class="brand_con_lt">
    			<a href="${indexBrandProducts[0].link!}" target="_blank"><img src="${indexBrandProducts[0].imagePath!}" /></a>
    		</div>
    		<div class="brand_con_middle">
    			<ul>
                	${indexBrands!}
    			</ul>
    			<div class="fpBrandFresh" data-spm="fpBrandFresh" data-spm-max-idx="1">
					<a class="refresh-btn">
					  <i class="rotate-icon fp-iconfont"></i>
					  <span class="btn-text">换一批</span>
					</a>
				</div>
    		</div>
    		<div class="brand_con_rt">
    			<ul>
    				<li><a href="${indexBrandProducts[1].link!}" target="_blank"><img src="${indexBrandProducts[1].imagePath!}" /></a></li>
    				<li><a href="${indexBrandProducts[2].link!}" target="_blank"><img src="${indexBrandProducts[2].imagePath!}" /></a></li>
    				<li><a href="${indexBrandProducts[3].link!}" target="_blank"><img src="${indexBrandProducts[3].imagePath!}" /></a></li>
    				<li style="border-bottom:none;"><a href="${indexBrandProducts[4].link!}" target="_blank"><img src="${indexBrandProducts[4].imagePath!}" /></a></a></li>
    			</ul>
    		</div>
    	</div>
    </div>
    <!-- 热门品牌结束 -->
    <!-- 产品广告 begin-->
	<#if indexBrandProducts?size gt 8 >
    <div class="main_banner_ad">
    	<a href="${indexBrandProducts[5].link!}" target="_blank"><img src="${indexBrandProducts[5].imagePath!}" /></a>
    	<a href="${indexBrandProducts[6].link!}" target="_blank"><img src="${indexBrandProducts[6].imagePath!}" /></a>
    	<a href="${indexBrandProducts[7].link!}" target="_blank"><img src="${indexBrandProducts[7].imagePath!}" /></a>
    	<a href="${indexBrandProducts[8].link!}" target="_blank"><img src="${indexBrandProducts[8].imagePath!}" /></a>
    </div>
    </#if>
    </#if>
    <!-- 广告位end -->
        <!--1F begin-->
	 <div id="pro_if1" class="pro_if blue_btm">
    	
    </div>
    <!--1F end-->
    
    <!--2F begin-->
     <div id="pro_if2" class="pro_if green_btm">
    	
    </div>
    <!--2F end-->
    
    <!--3F begin-->
    <div id="pro_if3" class="pro_if yellow_btm">
    </div>
    <!--3F end-->
    
    <!--4F begin-->
    <div id="pro_if4" class="pro_if pink_btm">
    </div>
    <!--4F end-->
    
    <!--5F begin-->
    <div id="pro_if5" class="pro_if gray_btm">
    	
    </div>
    <!--5F end-->
    
    <!--6F begin-->
    <div id="pro_if6" class="pro_if purple_btm">
    	
    </div>
    <!--6F end-->    
    
    <!--7F begin-->
    <div id="pro_if7" class="pro_if orange_btm">
    	
    </div>
    <!--7F end--> 
    
    <!--8F begin-->
    <div id="pro_if8" class="pro_if olive_btm">
    	
    </div>
    <!--8F end--> 
    
    <!--9F begin-->
    <div id="pro_if9" class="pro_if LightBlue_btm">
    	
    </div>
    <!--9F end-->

    <!--10F begin-->
    <div id="pro_if10" class="pro_if LightBlue_btm">
    	
    </div>
    <!--10F end--> 
    <input type="hidden" id="pageNum" value="1" autocomplete="off">
</div>


<!--content end-->
<#include "footer.ftl">
</body>
<script>
	
	var version='${version!}';
$(function(){
   var domain=document.domain; 
	if(domain.indexOf("我的")>-1||domain.indexOf("xn--wnu286b")>-1){
		$("#temp_pop").show();
		$("#temp_pop .close").click(function(){$("#temp_pop").hide()});
		
	}
	$("a").attr("target","_blank");
	
	$(".roof").find("a").removeAttr("target");
	banner();
	$("#topbanner-close").click(function(){
		$("#top-banner").slideUp(2000);
	});
	
	
	//特省
	$(".save_con ul li dl dt span").hide();	
	$(".save_con ul li").hover(function(){
		$(this).find("span").stop().fadeTo(500,0.9);		
	},
	function(){
		$(this).find("span").stop().fadeTo(500,0);		
	});
	
	if(window.innerWidth) {
		var width = window.innerWidth;
		if(width>1700) {
			drawBrands(44);
		} else {
			drawBrands(29);
		}
	} else {
		drawBrands(29);
	}
	
	$(".fpBrandFresh").click(function(){
        xunhuan();
    })
});
</script>
<script type="text/javascript" src="resources/js/top_ewm.js"></script>
<script type="text/javascript" src="/user/clientLog"></script>
</html>
