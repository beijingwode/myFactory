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
						
<!--content begin-->
<div class="main_box">
            
    <div class="item">
		<#if pageMap["pc_more_button"]??>
    	<div class="ts_tit">${pageMap["pc_more_button"][0].title!}</div>
		<input type="hidden" id="moreKey" value="${pageMap["pc_more_button"][0].ex2Value!}">
		<input type="hidden" id="moreVal" value="${pageMap["pc_more_button"][0].ex3Value!}">
    	
    	<div class="item_con">
    		<ul id="like_li">
	 		</ul>
	 	</div>
    	</#if>
    </div>
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
<script type="text/javascript" src="resources/js/more_data.js"></script>
<script type="text/javascript" src="resources/js/top_ewm.js"></script>
</html>
