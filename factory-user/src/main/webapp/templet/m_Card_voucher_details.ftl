<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="static_resources/css/public.css" />
<script type="text/javascript" src="static_resources/js/common/system_config.js"></script>
<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="static_resources/js/h5_exit.js"></script>

<#if pageMap["page_title"]??>
<title>${pageMap["page_title"][0].title!}</title>
</#if>

<style>
.details{width:100%;height:auto;overflow:hidden;background:#f0f0f5;padding:10px 0;}
.details p{width:90%;height:auto;text-align:center;padding:10px 5%;}
.details .p1{font-size:1.4em;color:#333}
.details .p2{font-size:1.6em;color:#333;font-weight:600}
.details .p3{font-size:1.2em;color:#797979}
.ewm{width:64%;margin:0 auto;margin-top:32px;}
.ewm img{width:100%;}
.details_con{width:90%;height:auto;margin:0 auto;margin-top:32px;text-align:center;font-size:1.4em;color:#333;}
</style>

</head>
<body  style="background:#fff;">
<div class="main-cont" id="main-cont" >
	  <#if pageMap["page_title"]??>
		<div class="top">
	        <h1 ><a href="javascript:close();" class="aleft"></a>${pageMap["page_title"][0].title!}</h1>
	    </div>
	  </#if>
	  
	<div class="main_box" style="width:100%;position:absolute;top:45px;left:0;">
	   <#if pageMap["details"]??>
	    <div class="details">
			<p class="p1">${pageMap["details"][0].ex1Value!}</p>
			<p class="p2">${pageMap["details"][0].ex2Value!}</p>
			<p class="p3">${pageMap["details"][0].ex3Value!}</p>
		</div>	
		<div class="ewm">			
			<img src="${pageMap["details"][0].imagePath!}" />			
		</div>			
		<div class="details_con">${pageMap["details"][0].ex4Value!}</div>
		</#if>
	</div>
</div>
    
</body>
<script type="text/javascript" src="static_resources/js/app2wx_page.js?111"></script>

</html>
