<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<#if pageMap["page_title"]??>
<title>${pageMap["page_title"][0].title!}</title>
</#if>
<link rel="stylesheet" type="text/css" href="static_resources/css/swiper-3.4.2.min.css" />
<link rel="stylesheet" type="text/css" href="static_resources/css/public_m.css" />
<#if pageMap["page_css"]??>
<link rel="stylesheet" type="text/css" href="${pageMap["page_css"][0].ex1Value!}" />
</#if>
<script type="text/javascript" src="static_resources/js/common/system_config.js"></script>
<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="static_resources/js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="static_resources/js/swiper-3.4.2.jquery.min.js"></script>
</head> 

<body>

<div class="main-cont" id="main-cont">
	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
	<div class="top">
        <h1><a href="javascript:close();" class="aleft"></a>${pageMap["page_title"][0].title!}</h1>
    </div>
	
	<div class="main_con huanling_con"> 
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
	  <div class="zw_box">
	    	<div class="zw_con">
			    <div class="hlb_details">
			    	<ul>
			    		<#if pageMap["personal_amount"]??>
			    		<li class="li1"><i><img src="static_resources/images/hlb_ye_icon.png" /></i><em>我的换领币：0.00</em></li>
			    		</#if>
			    		<#if pageMap["search_tag"]??>
			    			<li class="li2" id="wish_list"><i><img src="${pageMap["search_tag"][0].imagePath!}" /></i><em>${pageMap["search_tag"][0].title!}</em></li>
						</#if>
			    	</ul>
			    </div>
			    <#if pageMap["category_scope"]??>
			    <div class="nav_box swiper-container">
					    <div class="swiper-wrapper">
					    	<#assign l =pageMap["category_scope"]?size-1 >
					    	<div class="swiper-slide thisone" data-index="${pageMap["category_scope"][0].link!}">
									${pageMap["category_scope"][0].title!}
							</div>
			  				<#if l gte 1>
			  				<#list 1..l as i>
								<div class="swiper-slide" data-index="${pageMap["category_scope"][i].link!}">
									${pageMap["category_scope"][i].title!}
								</div>
			 		 		</#list>
			  				</#if>
					    </div>
			    </div>
			    <script>
			    	var mySwiper = new Swiper('.swiper-container',{
						slidesPerView : 'auto',
					});
				</script>
			    </#if>
		    </div>
	    </div>
		<div class="hl_con" id="more">
			<#if pageMap["more"]??>
			<input type="hidden" id="moreKey" value="${pageMap["more"][0].ex2Value!}">
			<input type="hidden" id="moreVal" value="${pageMap["more"][0].ex3Value!}">
			<ul>
			</ul>
	      	</#if>
    	</div> 
	</div>
	
</div>


</body>
<script type="text/javascript" src="static_resources/js/wxGetUid.js"></script>
<script type="text/javascript" src="static_resources/js/index_goto.js?1"></script>
<script type="text/javascript" src="${pageMap["page_js"][0].ex1Value!}"></script>
<#if pageMap["personal_amount"]??>
<script type="text/javascript" src="${pageMap["personal_amount"][0].ex1Value!}"></script>
</#if>
<script type="text/javascript" src="static_resources/js/h5_exit.js"></script>
<script>
//导航滚动
var a = $('.zw_con'), b = a.offset();
$(document).on('scroll', function() {
	var c = $(document).scrollTop();
	if (c-$("#banner").height()>0) {
		a.addClass("fixed");
	} else {
		a.removeClass("fixed");
	}
});
$(".nav_box .swiper-slide").each(function(){
	$(this).click(function(){//先把所有的隐藏掉 			
 		$(".nav_box .swiper-slide").removeClass("thisone")
 		$(this).addClass("thisone");
 		$("#more ul").html("");
 		getTsMore(1);
 	});			
 });
</script>
<script type="text/javascript" src="static_resources/js/app2wx_page.js?111"></script>
</html>
