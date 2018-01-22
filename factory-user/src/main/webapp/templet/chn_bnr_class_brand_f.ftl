<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">

<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="/css/common.css">
<link rel="stylesheet" type="text/css" href="/css/style.css">
<script type="text/javascript" src="/resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="/resources/js/shopping.js"></script>
<script type="text/javascript" src="/resources/js/application.js"></script>
<script type="text/javascript" src="/resources/js/img_player.js"></script>
<script type="text/javascript" src="/resources/js/jquery.SuperSlide2.js"></script>
</head>
<body>

<!--top begin-->
<#include "login.ftl">
<#include "header_01.ftl">
<!--top end-->
<#if pageMap??>
<!--content begin-->
<div id="content">
	<#if category??>
	<div class="position">
    	<a href="index.html">首页</a><em>></em><a href="javascript:;">${category.name}</a>
    </div>
    <div class="shopping_product_wrap">
    	<div class="shopping_product_left">
    	    <input type="hidden" id="categoryId" value="${category.id?c}" />
        	<div class="shopping_list_menu">
            	<#assign key ="${category.rootId!}${category.brotherOrderAll!}" >
				<#if category.rootId??>
					<#assign key ="${category.rootId?c}${category.brotherOrderAll!}" >
				</#if>
            	<#if categoriesMap??>
					<#if categoriesMap[key]??>	
					<ul>
						<#assign secondCategories =categoriesMap[key] >
						<#list secondCategories as secondCategory>
                		<li class="lt"><a class="hcolor" href="#">${secondCategory.name!}</a>
                    	<#if secondCategory.rootId??>
							<#assign key ="${secondCategory.rootId?c}${secondCategory.brotherOrderAll!}" >
						</#if>
						<#if categoriesMap[key]??>	
						<#assign thirdCategories =categoriesMap[key] >
                    	<div class="shopping_menu_layer">
                       	  	<div class="shopping_label_list">
                            	<ul>
                            		<#list thirdCategories as thirdCategory>
										<#if (thirdCategory_index % 4 == 0) >
											<#if (thirdCategory_index > 0) >
												</li>
	                                		</#if>
                                			<li>
                                		</#if>
                                    		<span><a target="_blank" href="/product/list?cat=${category.id?c},${secondCategory.id?c},${thirdCategory.id?c}">${thirdCategory.name!}</a></span>
                                    	<#if ((thirdCategory_index+1) == thirdCategories?size)>
											</li>
										</#if>
                                    </#list>
                                </ul>  
								<#if secondCategory.image??>
									<div class="shopping_product_pic"><a href="#"><img src="images/shopping_product.jpg" width="316" height="87"></a></div>
								</#if>
                            </div>                            
                        </div>
                        </#if>
                    	</li>
                    	</#list> 
                	</ul>
                	</#if>
                </#if>
            </div>
      </#if>
        			
		    <!--图片轮播 begin-->
            <div class="shopping_packet" id="shopping_packet">
			  <#if pageMap["focus_picture"]??>
                <#assign imageList =pageMap["focus_picture"] >
                <ol class="shopping_num">
                	<#list imageList as image>
						<li <#if image_index = 0>class="active"</#if>>${image_index+1}</a></li>
					</#list>
                </ol>
                <ul class="shopping_img_list">
                	<#list imageList as image>
						<li <#if image_index = 0>style="display:block;"</#if>><a href="${image.link!}"><img src="${image.imagePath!}" width="700" height="307" alt="${image.title!}"/></a></li>
					</#list>  
                </ul>
              </#if>
            </div>
            <!--图片轮播 end-->
            
            <!--左右按钮图片滚动 begin-->
            <div class="shopping_showpro">
            	<h2>品牌街</h2>
            	<div class="img-scroll">
			  	  <#if pageMap["hot_brand"]??>  	
            		<#assign imageList =pageMap["hot_brand"] >
					<span class="prev" ></span>
                    <span class="next" ></span>
                    <div class="img-list" id="ISL_Cont">
                        <div class="ScrCont">
                          <ul id="List1">
                        	<#list imageList as image>
							<li><a href="${image.link!}" target="_blank"><img src="${image.imagePath!}" width="139" height="121"/></a></li>
                            </#list> 
                          </ul>                        
                        </div>
                    </div>
                  </#if>
                </div>
            </div>
            <!--左右图片滚动 end-->
        </div>
        
        
	  <#if pageMap["hot_sales_text"]??>
        <#assign imageList =pageMap["hot_sales_text"] >
        <div class="shopping_product_right">
        	<h2>热销排行榜</h2>
            <div class="shopping_pro_list">
            	<ul>
            		<#list imageList as image>
                    <li <#if image_index = 0>class="surr"</#if>><a href="${image.link!}">${image.title!}</a></li>
					</#list>
                </ul>
            </div>
      
          <!--hot_sales_1 begin-->
	  	  <#if pageMap["hot_sales_1"]??>
            <#assign imageList =pageMap["hot_sales_1"] >
            <ul class="shopping_hotpro" style="display:block;">
              <#list imageList as image>
				<#if image_index lt 6>
            	<li>
                	<div class="
					<#if image_index = 0>	
						num nred	
					<#elseif image_index =1>
						num npink
					<#elseif image_index =2>
						num nlightred
					<#else>
						num ngray
					</#if>
					">${image_index+1}</div>
                    <div class="shopping_hotinfo">
                    	<a href="${image.link!}"><img src="${image.imagePath!}" width="49" height="58" alt="${image.title!}"></a>
                        <p>${image.title!}</p>
                        <strong>内购价：￥${image.proSalePrice!}</strong>
                    </div>
                </li>
				</#if>
              </#list>
            </ul>
          </#if>
          <!--hot_sales_1 end-->
            
          <!--hot_sales_2 begin-->
	  	  <#if pageMap["hot_sales_2"]??>
            <#assign imageList =pageMap["hot_sales_2"] >
            <ul class="shopping_hotpro" style="display:block;">
              <#list imageList as image>
				<#if image_index lt 6>
            	<li>
                	<div class="
					<#if image_index = 0>	
						num nred	
					<#elseif image_index =1>
						num npink
					<#elseif image_index =2>
						num nlightred
					<#else>
						num ngray
					</#if>
					">${image_index+1}</div>
                    <div class="shopping_hotinfo">
                    	<a href="${image.link!}"><img src="${image.imagePath!}" width="49" height="58" alt="${image.title!}"></a>
                        <p>${image.title!}</p>
                        <strong>内购价：￥${image.proSalePrice!}</strong>
                    </div>
                </li>
				</#if>
              </#list>
            </ul>
          </#if>
          <!--hot_sales_2 end-->
            
          <!--hot_sales_3 begin-->
	  	  <#if pageMap["hot_sales_3"]??>
            <#assign imageList =pageMap["hot_sales_3"] >
            <ul class="shopping_hotpro" style="display:block;">
              <#list imageList as image>
				<#if image_index lt 6>
            	<li>
                	<div class="
					<#if image_index = 0>	
						num nred	
					<#elseif image_index =1>
						num npink
					<#elseif image_index =2>
						num nlightred
					<#else>
						num ngray
					</#if>
					">${image_index+1}</div>
                    <div class="shopping_hotinfo">
                    	<a href="${image.link!}"><img src="${image.imagePath!}" width="49" height="58" alt="${image.title!}"></a>
                        <p>${image.title!}</p>
                        <strong>内购价：￥${image.proSalePrice!}</strong>
                    </div>
                </li>
				</#if>
              </#list>
            </ul>
          </#if>
          <!--hot_sales_3 end-->
        </div>
	  </#if>
    </div>
    
    <!--1F begin-->
    <#if pageMap["1f_title"]??>
    <div class="shopping_if">
		<#assign textList =pageMap["1f_title"] >
		<#assign imageList =pageMap["1f_image"] >
		<#assign imageTextList =pageMap["1f_image_text"] >
    	<div class="shopping_if_title">
        	<h2><span>1F</span>&nbsp;${textList[0].title!}</h2>
   			<ul class="shoppingif_r_list">
				<#list textList as text>
					<#if text_index gt 0 && ((textList?size -text_index) gt 1)>	
						<li><a href="${text.link!}">${text.title!}</a>
							<#if text_index gt 0 && (textList?size -text_index) gt 2 >
								<em>|</em>
							</#if>
						</li>
					</#if>
					<#if textList?size -text_index  =1>
						<p class="more"><a href="${text.link!}">${text.title!}</a></p>
					</#if>
				</#list>
            </ul>            
        </div>
        <div class="shopping_wrap">
        	<div class="shopping_info"> 
           		<#if imageTextList??>
                <div class="shopping_info_list">
                	<ul>
                		<#assign x=(imageTextList?size+1)/2 >
						<#list 1..x as i>
							<#if i lt 6>
                    		<li>
                    			<span><a href="${imageTextList[(i-1)*2].link!}">${imageTextList[(i-1)*2].title!}</a></span>
                        			<#if (((i-1)*2+1) lt imageTextList?size) >
										<span><a href="${imageTextList[(i-1)*2+1].link!}">${imageTextList[(i-1)*2+1].title!}</a></span>	
									</#if>
                       		 </li>
							</#if>
                        </#list>
                    </ul>
                </div>
                </#if>
				<#if imageList?? && (imageList?size>0) >
					<div class="shopping_info_img"><img src="${imageList[0].imagePath!}" width="203" height="204" alt="${imageList[0].title!}"></div>
				</#if>
            </div>
            <div class="shopping_list_wrap">
				<#if imageList??>
            	<div class="shopping_list">
                	<ul>
                		<#list imageList as image>
							<#if (image_index gt 0) && (image_index lt 12)>
								<li>
									<#if image.saleKbn ??>
									<#if image.saleKbn == 1>
									<div class="picon"><img src="images/picon1.png" /></div>
									</#if>
									<#if image.saleKbn == 2>
									<div class="picon"><img src="images/picon_c1.png" /></div>
									</#if>
									<#if image.saleKbn == 4>
									<div class="picon"><img src="images/picon_z1.png" /></div>
									</#if>
									<#if image.saleKbn == 5>
									<div class="picon"><img src="images/picon_t1.png" /></div>
									</#if>
									</#if>
                        			<a href="${image.link!}"><img src="${image.imagePath!}" width="104" height="104" alt="${image.title!}"></a>
                           			<p class="p3"><a href="${image.link!}">${image.title!}</a></p>
			                		<p class="p1"><span>内购价：￥${image.proSalePrice!}</span><em>${image.proDiscount!}折</em></p>
			                		<p  class="p2">电商价：￥${image.proPrice!}</p>
                       			 </li>
							</#if>	
						</#list>  
                    </ul>
                </div>
                </#if>
            </div>
        </div>
    </div>
	</#if>
    <!--1F end-->
    
    <!--2F begin-->
    <#if pageMap["2f_title"]??>
    <div class="shopping_if">
		<#assign textList =pageMap["2f_title"] >
		<#assign imageList =pageMap["2f_image"] >
		<#assign imageTextList =pageMap["2f_image_text"] >
    	<div class="shopping_if_title">
        	<h2><span>2F</span>&nbsp;${textList[0].title!}</h2>
   			<ul class="shoppingif_r_list">
				<#list textList as text>
					<#if text_index gt 0 && ((textList?size -text_index) gt 1)>	
						<li><a href="${text.link!}">${text.title!}</a>
							<#if text_index gt 0 && (textList?size -text_index) gt 2 >
								<em>|</em>
							</#if>
						</li>
					</#if>
					<#if textList?size -text_index  =1>
						<p class="more"><a href="${text.link!}">${text.title!}</a></p>
					</#if>
				</#list>
            </ul>            
        </div>
        <div class="shopping_wrap">
        	<div class="shopping_info"> 
           		<#if imageTextList??>
                <div class="shopping_info_list">
                	<ul>
                		<#assign x=(imageTextList?size+1)/2 >
						<#list 1..x as i>
							<#if i lt 6>
                    		<li>
                    			<span><a href="${imageTextList[(i-1)*2].link!}">${imageTextList[(i-1)*2].title!}</a></span>
                        			<#if (((i-1)*2+1) lt imageTextList?size) >
										<span><a href="${imageTextList[(i-1)*2+1].link!}">${imageTextList[(i-1)*2+1].title!}</a></span>	
									</#if>
                       		 </li>
							</#if>
                        </#list>
                    </ul>
                </div>
                </#if>
				<#if imageList?? && (imageList?size>0) >
					<div class="shopping_info_img"><img src="${imageList[0].imagePath!}" width="203" height="204" alt="${imageList[0].title!}"></div>
				</#if>
            </div>
            <div class="shopping_list_wrap">
				<#if imageList??>
            	<div class="shopping_list">
                	<ul>
                		<#list imageList as image>
							<#if (image_index gt 0) && (image_index lt 12)>
								<li>
									<#if image.saleKbn ??>
									<#if image.saleKbn == 1>
									<div class="picon"><img src="images/picon1.png" /></div>
									</#if>
									<#if image.saleKbn == 2>
									<div class="picon"><img src="images/picon_c1.png" /></div>
									</#if>
									<#if image.saleKbn == 4>
									<div class="picon"><img src="images/picon_z1.png" /></div>
									</#if>
									<#if image.saleKbn == 5>
									<div class="picon"><img src="images/picon_t1.png" /></div>
									</#if>
									</#if>
                        			<a href="${image.link!}"><img src="${image.imagePath!}" width="104" height="104" alt="${image.title!}"></a>
                           			<p class="p3"><a href="${image.link!}">${image.title!}</a></p>
			                		<p class="p1"><span>内购价：￥${image.proSalePrice!}</span><em>${image.proDiscount!}折</em></p>
			                		<p  class="p2">电商价：￥${image.proPrice!}</p>
                       			 </li>
							</#if>	
						</#list>  
                    </ul>
                </div>
                </#if>
            </div>
        </div>
    </div>
	</#if>
    <!--2F end-->
    
    <!--3F begin-->
    <#if pageMap["3f_title"]??>
    <div class="shopping_if">
		<#assign textList =pageMap["3f_title"] >
		<#assign imageList =pageMap["3f_image"] >
		<#assign imageTextList =pageMap["3f_image_text"] >
    	<div class="shopping_if_title">
        	<h2><span>3F</span>&nbsp;${textList[0].title!}</h2>
   			<ul class="shoppingif_r_list">
				<#list textList as text>
					<#if text_index gt 0 && ((textList?size -text_index) gt 1)>	
						<li><a href="${text.link!}">${text.title!}</a>
							<#if text_index gt 0 && (textList?size -text_index) gt 2 >
								<em>|</em>
							</#if>
						</li>
					</#if>
					<#if textList?size -text_index  =1>
						<p class="more"><a href="${text.link!}">${text.title!}</a></p>
					</#if>
				</#list>
            </ul>            
        </div>
        <div class="shopping_wrap">
        	<div class="shopping_info"> 
           		<#if imageTextList??>
                <div class="shopping_info_list">
                	<ul>
                		<#assign x=(imageTextList?size+1)/2 >
						<#list 1..x as i>
							<#if i lt 6>
                    		<li>
                    			<span><a href="${imageTextList[(i-1)*2].link!}">${imageTextList[(i-1)*2].title!}</a></span>
                        			<#if (((i-1)*2+1) lt imageTextList?size) >
										<span><a href="${imageTextList[(i-1)*2+1].link!}">${imageTextList[(i-1)*2+1].title!}</a></span>	
									</#if>
                       		 </li>
							</#if>
                        </#list>
                    </ul>
                </div>
                </#if>
				<#if imageList?? && (imageList?size>0) >
					<div class="shopping_info_img"><img src="${imageList[0].imagePath!}" width="203" height="204" alt="${imageList[0].title!}"></div>
				</#if>
            </div>
            <div class="shopping_list_wrap">
				<#if imageList??>
            	<div class="shopping_list">
                	<ul>
                		<#list imageList as image>
							<#if (image_index gt 0) && (image_index lt 12)>
								<li>
									<#if image.saleKbn ??>
									<#if image.saleKbn == 1>
									<div class="picon"><img src="images/picon1.png" /></div>
									</#if>
									<#if image.saleKbn == 2>
									<div class="picon"><img src="images/picon_c1.png" /></div>
									</#if>
									<#if image.saleKbn == 4>
									<div class="picon"><img src="images/picon_z1.png" /></div>
									</#if>
									<#if image.saleKbn == 5>
									<div class="picon"><img src="images/picon_t1.png" /></div>
									</#if>
									</#if>
                        			<a href="${image.link!}"><img src="${image.imagePath!}" width="104" height="104" alt="${image.title!}"></a>
                           			<p class="p3"><a href="${image.link!}">${image.title!}</a></p>
			                		<p class="p1"><span>内购价：￥${image.proSalePrice!}</span><em>${image.proDiscount!}折</em></p>
			                		<p  class="p2">电商价：￥${image.proPrice!}</p>
                       			 </li>
							</#if>	
						</#list>  
                    </ul>
                </div>
                </#if>
            </div>
        </div>
    </div>
	</#if>
    <!--3F end-->
    
  
    
	<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
  <#if pageParam["supplier_id"]??>
	<input type="hidden" id="supplierNd" value="${pageParam["supplier_id"]!}">
	<input type="hidden" id="supplierName" value="${pageParam["supplier_name"]!}">
  </#if>
</div>
<!--content end-->
</#if>
<!--footer begin-->
<#include "footer.ftl">
<!--footer end-->
<script type="text/javascript">
//带按钮的图片左右滚动效果
$(".img-scroll").slide({
	titCell:"",
	mainCell:".img-list ul",
	autoPage:true,
	effect:"leftLoop",
	autoPlay:true,
	delayTime:3000,
	interTime:8000,
	vis:6,
	scroll:6
});
$(function(){
	$("a").attr("target","_blank");
	
	$(".roof").find("a").removeAttr("target");	
});
</script>
<script type="text/javascript" src="/resources/js/top_ewm.js"></script>
<script type="text/javascript" src="/user/activityLog?url=${pageParam["page_key"]!}"></script>
</body>
</html>
