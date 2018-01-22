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

</head> 

<body>
<div class="main-cont" id="main-cont" >
	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
	<input type="hidden" id="pagePreview" value="${pageParam["page_preview"]!}">
	<div class="top_box">
		<p class="top_shopName">【我的福利】员工福利店</p>
		<div class="search_box">		
	        <div class="search_inp">
	        	<a href="javascript:void(0);">我想要</a>
	        </div>
	        
	        <div class="my_quan">
	        	<span><img src="static_resources/images/my_quan_icon.png" /></span>
	        	<div class="quan_con">
	        		<ul>
	        			<li>现金券：0.00</li>
	        			<li>换领币：0.00</li>
	        			<li>内购券：0.00</li>
	        		</ul>
	        	</div>
	        	<input type="hidden" id="shop" value="">
	        </div>        
		</div> 
	</div>
	<div class="main-box">
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
	    
	     <div class="main_three_v2" id="Ownexclusive" style="display:none">
	     	<div class="tit">自家专享</div>
	     	<div class="main_three_con_v2 swiper-container" >
	     		<div class="swiper-wrapper">
		     	</div>
		     </div>
		 </div>
		 <#if pageMap["alliance"]??> 
		  <div class="main_three_v2 lianmeng">
	     	<div class="tit">厂商联盟</div>
	     	<div class="main_three_con_v2 swiper-container" >
	     		<div class="swiper-wrapper" >
			        <#list pageMap["alliance"] as brand> 
			        <div class="swiper-slide"><a href="javascript:go2Search('${brand.link!}')"><img src="${brand.imagePath!}" /></a></div>
			        </#list>
		     	</div>
		     </div>
		 </div>
		 </#if>
		 <!-- 现金券专区 -->
		 <#if pageMap["CashArea"]??> 
		 <div class="ngj_con main_con" id="CashArea" style="display:none">
		 	<div class="ul_box ul_box1">
		 		<ul class="ul1">
		 		</ul>
		 		<ul class="ul2">
		 		</ul>
		 	</div>
		 	<div class="ad"></div>
		 </div>
		 </#if>
		 <!-- 答题领好物 -->
		 <#if pageMap["AnswerArea"]??> 
		 <div class="ngj_con main_con" id="AnswerArea">
		 	<div class="ul_box ul_box1">
		 		<ul class="ul1">
		 		</ul>
		 		<ul class="ul2">
		 		</ul>
		 	</div>
		 	<div class="ad"></div>
		 </div>
		 </#if> 
	   	 <!-- 福利随心换 -->
		 <div class="ngj_con main_con" id="ExchangeArea" style="display:none">
		 	<div class="ul_box ul_box1">
		 		<ul class="ul1">
		 		</ul>
		 		<ul class="ul2">
		 		</ul>
		 	</div>
		 	<div class="ad"></div>
		 </div>
	   	 <!-- 不止特省 -->
		 <div class="ngj_con main_con" id="SaveMoreArea">
		 	<div class="ul_box ul_box1">
		 		<ul class="ul1">
		 		</ul>
		 		<ul class="ul2">
		 		</ul>
		 	</div>
		 	<div class="ad"></div>
		 </div>
	   	 
	   	 
	   	 
	   	 <div class="ngj_con" id="more">
	    	<div class="tit">优享内购价</div>
	    	<input type="hidden" id="pageNum" value="0" autocomplete="off">
	    	<ul>
	    	</ul>
	    </div>
	   	 
	   	 
	</div>
</div>

<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="static_resources/js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="static_resources/js/wxGetUid.js"></script>
<script type="text/javascript" src="static_resources/js/index_goto.js?1"></script>
<script type="text/javascript" src="static_resources/js/swiper-3.4.2.jquery.min.js"></script>
<#if pageParam["page_ex1Value"]??>
<script type="text/javascript" src="${pageParam["page_ex1Value"]!}"></script>
</#if>
<script type="text/javascript" src="static_resources/js/h5_exit.js"></script>
<script type="text/javascript" src="static_resources/js/common/system_config.js"></script>
<script type="text/javascript" src="static_resources/js/app2wx_page.js?111"></script>
<script>
//内购券现金券显示
function test(){
	$('.ngj_con ul li dl .div_pop1').toggle(function(){
		  $(this).find(".dd1,.dd2,.dd3").hide();
		  $(this).find(".dd4,.dd6").show();
		  $(this).find('em').html('<img src="static_resources/images/right_bottom_icon1.png" />');
	},function(){
		  $(this).find(".dd1,.dd2,.dd3").show();
		  $(this).find(".dd4,.dd6").hide();
		  $(this).find('em').html('<img src="static_resources/images/right_bottom_icon.png" />');
	});
} 
</script>
</body>

</html>
