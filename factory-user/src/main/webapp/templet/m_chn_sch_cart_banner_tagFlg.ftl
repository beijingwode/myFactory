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
	<div class="top">
        <h1><a href="javascript:close();" class="aleft"></a>${pageMap["page_title"][0].title!}</h1>
    </div>
	<#if pageMap["search_cart"]??>
	<div class="search_box">		
        <div class="search_inp shiyong_inp">
        	<a href="javascript:void(0);">${pageMap["search_cart"][0].title!}</a>
        </div>
        
        <#if pageMap["search_cart"][0].ex1Value??>
        <div class="shopping_car">
        	<img src="${pageMap["search_cart"][0].ex1Value!}" />
        	<span>0</span>
        </div>
		</#if>
		
		<#if pageMap["search_tag"]??>
        <div class="tab_icon">
        	<img src="${pageMap["search_tag"][0].imagePath!}" id="${pageMap["search_tag"][0].title!}"/>
        	<img src="${pageMap["search_tag"][1].imagePath!}" style="display: none" id="${pageMap["search_tag"][1].title!}"/>
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
		<div class="main_one_sy" id="more">
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
<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="static_resources/js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="static_resources/js/wxGetUid.js"></script>
<script type="text/javascript" src="static_resources/js/index_goto.js?122"></script>
<script type="text/javascript" src="static_resources/js/getCartNum.js"></script>
<script type="text/javascript" src="${pageMap["page_js"][0].ex1Value!}"></script>
<script type="text/javascript" src="static_resources/js/h5_exit.js"></script>
<script>
$(document).ready(function(){
if(isWeiXinH5()) {
	$(".top").hide();
	$(".search_box").removeAttr("style");
}else{
	$(".search_box").attr("style","top:45px;position:absolute;");
}
});
$(".shopping_car").click(function(e){//点击进入购物车
	e.stopPropagation()||(e.cancelBubble = true);
	go2Cart();
})
//点击编辑
$(".tab_icon").toggle(
	   function(){
		 $("#personage_icon").hide();
		 $("#company_icon").show();
		 $("#moreVal").val("1X");
		 $("#more ul").html("");
		  getTsMore(1);
	  },
	   function(){
		  $("#company_icon").hide();
		  $("#personage_icon").show();
		  $("#moreVal").val("0X");
		  $("#more ul").html("");
		  getTsMore(1);
	  }
	  
	);
$(".search_box").click(function(e){
	var moreKey= $("#moreKey").val();
	var moreVal= $("#moreVal").val();
	var pageKey= $("#pageKey").val();
	if(moreKey!="" && moreVal!="") {
		if(pageKey!="" && pageKey=="shiyong"){
			go2Search("saleKbn=5&"+moreKey +"="+moreVal);
		}
	} else {
		go2Search('');
	}
}); 
</script>
<script type="text/javascript" src="static_resources/js/app2wx_page.js?111"></script>
</html>
