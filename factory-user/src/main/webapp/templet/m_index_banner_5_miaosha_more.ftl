<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<#if pageMap["page_title"]??>
<title>${pageMap["page_title"][0].title!}</title>
</#if>
<link rel="stylesheet" type="text/css" href="static_resources/css/swiper-3.4.2.min.css" />
<#if pageMap["page_css"]??>
<link rel="stylesheet" type="text/css" href="${pageMap["page_css"][0].ex1Value!}" />
</#if>
<link rel="stylesheet" type="text/css" href="static_resources/css/public_m.css" />

</head> 

<body>

<div class="main-cont" id="main-cont" >
	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
  <#if pageParam["supplier_id"]??>
	<input type="hidden" id="supplierId" value="${pageParam["supplier_id"]!}">
	<input type="hidden" id="supplierName" value="${pageParam["supplier_name"]!}">
  </#if>
	<input type="hidden" id="pagePreview" value="${pageParam["page_preview"]!}">
	<div class="search_box">
		<div class="search_lt">
        	<marquee direction=up   behavior=scroll scrollamount=10 Scrolldelay=1000 loop=-1>员工福利<br />联合内购<br />一起省钱<br />我的也是你的</marquee>
        </div>
        <div class="search_rt">
        	<a href="javascript:void(0);">我想要</a>
        </div>
	</div>
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
	
	<#if pageMap["top_new_category"]??>
	<div class="main_two_v2" id="top_category">
		<#assign l =pageMap["top_new_category"] >
		<#list l as topCategory>
			<a href="javascript:go2Search('${topCategory.link!}')">
				<img src="${topCategory.imagePath!}"  />
			</a>
		</#list>
    </div>
    </#if>
    
    <#if pageMap["top_image_new"]??>
    <div class="ad">
    	<a href="javascript:go2Search('${pageMap["top_image_new"][0].link!}')"><img src="${pageMap["top_image_new"][0].imagePath!}" /></a>
    </div>
    </#if>
    
    <#if pageMap["miao_sha_must_new"]??>
    <div class="main_three_v2" >
    	<div class="main_three_con_v2">
    		<div class="main_three_lt_v2">
    			<a href="javascript:go2Search('${pageMap["miao_sha_must_new"][0].link!}')">
    				<img src="${pageMap["miao_sha_must_new"][0].imagePath!}" />
    			</a>
    			<div class="count_down">
    				<input type='hidden' id='miaoShaEndDate' value='${pageMap["miao_sha_must_new"][0].title!}' />
    				<div class='bit' id='hour'>00</div>
    				<div class='separator'>:</div>
    				<div class='bit' id='min'>00</div>
    				<div class='separator'>:</div>
    				<div class='bit' id='sec'>00</div>
    			</div>
    		</div>
    		<div class="main_three_rt_v2">
    			<a href="javascript:go2Search('${pageMap["miao_sha_must_new"][1].link!}')"><img src="${pageMap["miao_sha_must_new"][1].imagePath!}" /></a>
    			<a href="javascript:go2Search('${pageMap["miao_sha_must_new"][2].link!}')" class="three_rt_p2"><img src="${pageMap["miao_sha_must_new"][2].imagePath!}" /></a>
    			<a href="javascript:go2Search('${pageMap["miao_sha_must_new"][3].link!}')" class="three_rt_p3"><img src="${pageMap["miao_sha_must_new"][3].imagePath!}" /></a>
    		</div>
    	</div>
    </div>
    </#if>
    
    <#if pageMap["channel_scope_new"]??>
    <div class="main_four_v2">
    	<#assign images =pageMap["channel_scope_new"]>
    	<#list images as image>
    	<div class='four_con'>
    		<a href="javascript:go2Search('${image.link!}')"><img src="${image.imagePath!}" /></a>
    	</div>
    	</#list>
    </div>
    </#if>
    
    <#if pageMap["bottom_brand_new"]??>
     <div class="main_five_v2">
    	<div class='tit'><img src="${pageMap["bottom_brand_new"][0].imagePath!}" /></div>
    	
    	<div class='main_five_con_v2 swiper-container' >
    		<#assign brandList =pageMap["bottom_brand_new"]?size-1 >
    			<div class='swiper-wrapper' >
    			<#list 1..brandList as i>
    				<div class='swiper-slide'>
    				<a href="javascript:go2Search('${pageMap["bottom_brand_new"][i].link!}')">
    					<img src="${pageMap["bottom_brand_new"][i].imagePath!}" />
    				</a>
    				</div>
    			</#list>
    		</div>
    	</div>
    </div>
    </#if>
    
   	 <!-- 猜你喜欢 -->
	 <div class="youlike_box">
	 	<#if pageMap["more"]??>
	 	<div class="like_tit"><span>${pageMap["more"][0].title!}</span></div>
	 	<div class="like_con">
	 		<ul id="like_li">
	 		</ul>
	 		<input type="hidden" id="pageNum" value="0" autocomplete="off">
	 	</div>
	 	</#if>
	 	<p>正在加载更多数据</p>
	 </div>
</div>
<div class="back_top" id="back_top"><img src="static_resources/images/homegotopImage.png" /></div>
<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="static_resources/js/jquery.flexslider-min.js"></script>

</body>
<script>
var isPreview = ("1"==$("#pagePreview").val());
$(document).ready(function(){
	/*返回顶部*/
	$("#back_top").hide();
	$(window).scroll(function () {
		if ($(window).scrollTop() > 300) {
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
		
	var pageKey = $("#pageKey").val();
    if(pageKey) {
	    $.getScript("cart/activityLog?app=wx&url="+pageKey);
    }
});
function goto(selector){
	$.scrollTo ( selector , 1000);	
}
function setActive() {
	$(".nav ul li:eq(3)").addClass("active");
}
</script>
<script type="text/javascript" src="static_resources/js/index.js?3"></script>
<script type="text/javascript" src="static_resources/js/wxGetUid.js"></script>
<script type="text/javascript" src="static_resources/js/index_goto.js"></script>

<script type="text/javascript" src="static_resources/js/swiper-3.4.2.jquery.min.js"></script>
<script type="text/javascript" src="static_resources/js/miaoShaEndDate.js"></script>
</html>