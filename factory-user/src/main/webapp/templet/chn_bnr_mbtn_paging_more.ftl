<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="我的网">
<meta name="keywords" content="我的网">
<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="css/subpage.css" />
<link rel="stylesheet" type="text/css" href="css/pagination/pagination.css" media="screen">
</head>
<script type="text/javascript" src="resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="resources/js/jquery.kinMaxShow-1.0.src.js"></script>
<script type="text/javascript" src="resources/js/application.js"></script>
<script type="text/javascript" src="resources/js/pagination/jquery.pagination.js"></script>
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
<!-- 换领帮助begin -->
<#if pageMap["hl_help"]??>
<div class="help_box">
	<img src="${pageMap["hl_help"][0].imagePath!}" />
</div>
</#if>
<!-- 换领帮助end -->
<!--content begin-->
<div class="huanling_box"> 
	<#if pageMap["page_tit"]??>
    <div class="hl_tit"><img src="${pageMap["page_tit"][0].imagePath!}" /></div>
    </#if>
    <!--搜索内容start-->
    <div class="hl_con_box"> 
    <ul>
    </ul>
    </div>
    <!--搜索内容end-->
    <!-- 分页码开始 -->
      <div class="page">
      <div class="M-box"></div> 
      </div>
    <!-- 分页码结束 -->
</div>
<!--券额start-->
<#if pageParam["my_amount"]??>
<div class="my_hlb">
	<div class="hlb_con">
	</div>
	<div class="go_wish_list hl_help">
		<a href="javascript:;">换领频道帮助</a>
		<div class="hl_help_pic2"><img src="${pageMap["my_amount"][0].imagePath!}" /></div>
	</div>
	<div class="go_wish_list">
		<a href="javascript:go2WishOrder();" target="_blank">调剂清单>></a>
	</div>
</div>
</#if>
<!--券额end-->
	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
  <#if pageParam["supplier_id"]??>
	<input type="hidden" id="supplierId" value="${pageParam["supplier_id"]!}">
	<input type="hidden" id="supplierName" value="${pageParam["supplier_name"]!}">
  </#if>
<#if pageMap["pc_more_button"]??>
	<input type="hidden" id="moreKey" value="${pageMap["pc_more_button"][0].ex2Value!}">
	<input type="hidden" id="moreVal" value="${pageMap["pc_more_button"][0].ex3Value!}">
	<input type="hidden" id="pageSize" value="${pageMap["pc_more_button"][0].ex4Value!}">
</#if>

<!--content end-->
<#include "footer.ftl">
</body>
<script>
$(function(){
	banner();
	
	$("#btn_more").attr("onclick","to_more()");
	
	//鼠标滑过换领频道帮助显示
	$(".hl_help").mouseover(function(){
		$(".hl_help_pic2").fadeIn(300);
	});
	$(".hl_help").mouseout(function(){
		$(".hl_help_pic2").fadeOut(300);
	})
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
<#if pageMap["page_js"]??>
<script type="text/javascript" src="${pageMap["page_js"][0].ex1Value!}"></script>
</#if>
<#if pageMap["my_amount"]??>
<script type="text/javascript" src="${pageMap["my_amount"][0].ex1Value!}"></script>
</#if>
</html>
