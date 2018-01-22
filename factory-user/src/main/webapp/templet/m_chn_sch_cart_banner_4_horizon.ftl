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
</head> 

<body>

<div class="main-cont" id="main-cont">
	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
  <#if pageParam["supplier_id"]??>
	<input type="hidden" id="supplierId" value="${pageParam["supplier_id"]!}">
	<input type="hidden" id="supplierName" value="${pageParam["supplier_name"]!}">
  </#if>
	<#if pageMap["search_cart"]??>
	<div class="search_box">		
        <div class="search_inp">
        	<a href="javascript:void(0);">${pageMap["search_cart"][0].title!}</a>
        </div>
        
        <#if pageMap["search_cart"][0].ex1Value??>
        <div class="shopping_car">
        	<img src="${pageMap["search_cart"][0].ex1Value!}" />
        	<span>0</span>
        </div>
	</#if>
        
	</div>
	</#if>
	
	<div class="main_con"> 
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
	  
		<div class="main_one_qc">
	  	  <#if pageMap["category_scope1"]??>
	  	  	<div class="tit"><img src="${pageMap["category_scope1"][0].imagePath!}" /></div>
			<div class="qc_con">				
			  <#assign l =pageMap["category_scope1"]?size-1 >
			  <#assign x =l-l%2 >
			  <#if x gt 1>
			  <#list 1..x as i>
				<a href="javascript:go2Search('${pageMap["category_scope1"][i].link!}')">
					<img src="${pageMap["category_scope1"][i].imagePath!}">
				</a>
			  </#list>
			  </#if>
			</div>
	      </#if>
		
	  	  <#if pageMap["horizontal_show_scope1"]??>
	  	  	<div class="qc_hot_brand">
		  	  	<div class="hot_brand_con swiper-container">
					<div class="swiper-wrapper">		
				  	  <#list pageMap["horizontal_show_scope1"] as brand>
				  	  	<div class='swiper-slide'>
				  	  		<a href="javascript:go2Search('${brand.link!}')"><img src="${brand.imagePath!}" /></a>
				  	  	</div>
				  	  </#list>	
					</div>
				</div>
			</div>
	      </#if>
		</div>
		
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
    	
	</div>
	
</div>


</body>
<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="static_resources/js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="static_resources/js/wxGetUid.js"></script>
<script type="text/javascript" src="static_resources/js/index_goto.js?1"></script>
<script type="text/javascript" src="static_resources/js/swiper-3.4.2.jquery.min.js"></script>
<script type="text/javascript" src="static_resources/js/getCartNum.js"></script>
<script type="text/javascript" src="static_resources/js/more_2col.js"></script>
<script type="text/javascript" src="static_resources/js/h5_exit.js"></script>
<script>
//左右滑动
var mySwiper = new Swiper('.swiper-container',{
slidesPerView : 'auto',//'auto'
//slidesPerView : 3.7,
})
$(".shopping_car").click(function(e){//点击进入购物车
	e.stopPropagation()||(e.cancelBubble = true);
	go2Cart();
})
$(".search_box").click(function(e){
	var moreKey= $("#moreKey").val();
	var moreVal= $("#moreVal").val();
	if(moreKey!="" && moreVal!="" ) {
		go2Search(moreKey +"="+moreVal);
	} else {
		go2Search('');
	}
});
</script>
<script type="text/javascript" src="static_resources/js/app2wx_page.js?111"></script>
</html>
