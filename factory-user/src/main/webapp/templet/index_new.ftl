<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="css/index_style.css" />
<script type="text/javascript" src="resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="resources/js/jquery.SuperSlide2.js"></script>
<#if pageParam["page_ex1Value"]??>
<script type="text/javascript" src="${pageParam["page_ex1Value"]!}"></script>
</#if>
<#if pageParam["page_ex2Value"]??>
<script type="text/javascript" src="${pageParam["page_ex2Value"]!}"></script>
</#if>
<#if pageParam["page_ex3Value"]??>
<script type="text/javascript" src="${pageParam["page_ex3Value"]!}"></script>
</#if>

</head>

<body>
<div class="header">
	<div class="top"><p>我&nbsp;的&nbsp;福&nbsp;利&nbsp;&nbsp;&nbsp;&nbsp;也&nbsp;是&nbsp;你&nbsp;的&nbsp;福&nbsp;利</p></div>
    
	<#if pageMap["logo"]??>
	    <#assign imageList =pageMap["logo"] >
		<div class="logo">
			<#list imageList as image>
			<img src="${image.imagePath!}" />
			</#list>
		</div>		
	</#if>
	
    <div class="search_fixed">
    	<div class="search_fixed_con">
            <ul class="ul1">
                <li class="li1"><a href="javascript:;">首页</a></li>
                <li><a href="main.html" target="_blank">福利商城</a></li>
            </ul>
            <div class="searchbox">
                <div class="inp_box">
                    <form action="/product/search" method="get" id="search_form" target="_blank">
                        <input class="searchinput" placeholder="即搜即得" autocomplete="off" type="text" name="key" value="" >
                        <input  class="search_btn" id="search_btn" type="submit" value="" />
                    </form>
                </div>
                
            </div>
            <ul class="ul2">
                <li class="buycar">
                    <em><img src="images/index_shopping_car_icon.png" /></em>
                    <span class="shopping_amount"></span>
                    <a href="javascript:;" class="fff_show"></a>
                    <div class="box buycar_box dis">
                        
                        <div class="buycar_con">
                            <div class="tit">最近加入的商品</div>
                            <div class="buycar_list">
                                <div class="list_con">
                                   
                                </div>
                            </div>
                            
                            <div class="buycar_bottom">
                                <div class="buycar_bottom_con"><i class="span1"></i>件商品 共计<i class="span2"></i></div>
                                <a href="cart/list" target="_blank">去购物车结算</a>
                            </div>
                            
                        </div>
                    </div>
                </li>
                <li class="details_box">
                    <em><img src="images/index_center_icon.png" /></em>
                    <a href="javascript:;"  class="fff_show"></a>
                    <div class="box personal_details_box dis">
                        <a href="/member/myorders"  target="_blank">我的交易</a>
                        <a href="/member/personalStore" target="_blank">我的关注</a>
                        <a href="member/personalInformation" target="_blank">个人中心</a>
                        <a href="/user/loginOut">退出登录</a>
                    </div>
                </li>
                <li class="tel">
                    <em><img src="images/index_tell_icon.png" /></em>
                    <a href="javascript:;"  class="fff_show"></a>
                    <div class="box ewm_img dis">
                        <img src="images/app_download_ewm.png" />
                    </div>
                </li>
            </ul>
        </div>
    </div>
    
    
</div>

<div class="mainbody">	
	<!--banner开始-->
    <div class="banner">
    	<#assign imageList =pageMap["focus_picture"] >
		<#if imageList??>
        <div class="banner_box" id="banner">
        	<#list imageList as image>
            <a href="${image.link!}" class="d1"  target="_blank" style="background:url(${image.imagePath!}) center no-repeat;"></a>
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
    <!--banner结束--> 
    <div class="wrap">
        <!--自家专享开始-->
        <div class="exclusive" style="display:none;">
        	<div class="tit1"><p><img src="images/new_index_tit1.png" /></p><a href="product/search?supplierId="  target="_blank">查看更多>></a></div>
        	<div class="exc_box">
        		<div class="prev"><a href="javascript:;"><img src="images/new_index_prev_icon.png"></a></div>
        		<div class="exc_con"><ul></ul></div>
        		<div class="next"><a href="javascript:;"><img src="images/new_index_next_icon.png" /></a></div>
        	</div>
        </div>
        <!--自家专享结束-->  
        <!--厂商联盟开始-->
        <div class="alliance">
            <div class="tit1">
                <p><img src="images/new_index_tit2.png" /></p>
                <div class="btns1"><em class="btn_left prev"></em><em class="btn_middle">|</em><em class="btn_right next"></em></div>
            </div>
            <div class="alliance_con">
                <ul>
                    <#if pageMap["alliance"]??> 
						<#list pageMap["alliance"] as brand> 
							<li><a href="${brand.link!}"  target="_blank"><img src="${brand.imagePath!}" /></a></li>
						</#list>
					</#if>	
                </ul>
            </div>
        </div>
        <!--厂商联盟结束-->
        <!--现金券专区开始--> 
        <div class="main_box" id="pro_if1" style="display:none;">
            <div class="tit2">
                <p class="p1">现金券专区</p>
                <p class="p2">减到0元，现金券就 当现金花</p>
                <a href="main.html"  target="_blank">更多减免>></a>
                <span><img src="images/new_index_tit2_spanbg.png" /></span>
            </div>
            
            <div class="main_con">
                <ul></ul>
				<#if pageMap["1f_product"]??>
                  <#assign producList =pageMap["1f_product"] >
				  <#list producList as product>
                	<div class="hid_product_div" style="display:none;">	
                        <input type="hidden" name="link" value="${product.link!}">
                        <input type="hidden" name="image" value="${product.imagePath!}">
                        <#if product.title1 ??>
                          <input type="hidden" name="titel1" value="${product.title1!}">
                        <#else>
                          <input type="hidden" name="titel1" value="${product.proBrand!}">
                        </#if>
                        <input type="hidden" name="titel2" value="${product.title!}">
                        <input type="hidden" name="maxFucoin" value="${product.maxFucoin!}">
                        <input type="hidden" name="proPrice" value="${product.proPrice!}">  
                        <input type="hidden" name="proSalePrice" value="${product.proSalePrice!}">  
                        <input type="hidden" name="quantity" value="${product.quantity!}">
                        <input type="hidden" name="replaceIndex" value="${product.ex6Value!}">
                	</div>
				  </#list>
				</#if>
            </div>
        </div> 
        <!--现金券专区结束-->
        <!--答题领好物开始--> 
        <div class="main_box" id="pro_if2" style="display:none;">            
            <div class="tit2">
                <p class="p1">答题领好物</p>
                <p class="p2">一份问卷“等于”一件商品，我的意见有价值</p>
                <a href="shiyong.html"  target="_blank">更多试用>></a>
                <span><img src="images/new_index_tit2_spanbg.png" /></span>
            </div>
            
            <div class="main_con">
                <ul></ul>
				<#if pageMap["2f_product"]??>
                  <#assign producList =pageMap["2f_product"] >
				  <#list producList as product>
                	<div class="hid_product_div" style="display:none;">
                        <input type="hidden" name="link" value="${product.link!}">
                        <input type="hidden" name="image" value="${product.imagePath!}">
                        <#if product.title1 ??>
                          <input type="hidden" name="titel1" value="${product.title1!}">
                        <#else>
                          <input type="hidden" name="titel1" value="${product.proBrand!}">
                        </#if>
                        <input type="hidden" name="titel2" value="${product.title!}">
                        <input type="hidden" name="maxFucoin" value="${product.maxFucoin!}">
                        <input type="hidden" name="proPrice" value="${product.proPrice!}">  
                        <input type="hidden" name="proSalePrice" value="${product.proSalePrice!}">  
                        <input type="hidden" name="quantity" value="${product.quantity!}">
                        <input type="hidden" name="replaceIndex" value="${product.ex6Value!}">
                	</div>
				  </#list>
				</#if>
            </div>
        </div> 
        <!--答题领好物结束-->
         <!--福利随心换开始--> 
        <div class="main_box" id="pro_if3" style="display:none;">           
            <div class="tit2">
                <p class="p1">福利随心换</p>
                <p class="p2">各取所需，各家福利换起来</p>
                <a href="huanling.html"  target="_blank">更多换领>></a>
                <span><img src="images/new_index_tit2_spanbg.png" /></span>
            </div>
            
            <div class="main_con">
                <ul></ul>
				<#if pageMap["3f_product"]??>
                  <#assign producList =pageMap["3f_product"] >
				  <#list producList as product>
                	<div class="hid_product_div" style="display:none;">
                        <input type="hidden" name="link" value="${product.link!}">
                        <input type="hidden" name="image" value="${product.imagePath!}">
                        <#if product.title1 ??>
                          <input type="hidden" name="titel1" value="${product.title1!}">
                        <#else>
                          <input type="hidden" name="titel1" value="${product.proBrand!}">
                        </#if>
                        <input type="hidden" name="titel2" value="${product.title!}">
                        <input type="hidden" name="maxFucoin" value="${product.maxFucoin!}">
                        <input type="hidden" name="proPrice" value="${product.proPrice!}">  
                        <input type="hidden" name="proSalePrice" value="${product.proSalePrice!}">  
                        <input type="hidden" name="quantity" value="${product.quantity!}">
                        <input type="hidden" name="replaceIndex" value="${product.ex6Value!}">
                	</div>
				  </#list>
				</#if>
            </div>
        </div> 
        <!--福利随心换结束-->  
        <!--不止特省开始--> 
        <div class="main_box" id="pro_if4" style="display:none;">
            <div class="tit2">
                <p class="p1">不止特省</p>
                <p class="p2">价格超低，质量如一，能有多省有多省</p>
                <a href="tesheng.html"  target="_blank">更多特省>></a>
                <span><img src="images/new_index_tit2_spanbg.png" /></span>
            </div>
            
            <div class="main_con">
                <ul></ul>
				<#if pageMap["4f_product"]??>
                  <#assign producList =pageMap["4f_product"] >
				  <#list producList as product>
                	<div class="hid_product_div" style="display:none;">
                        <input type="hidden" name="link" value="${product.link!}">
                        <input type="hidden" name="image" value="${product.imagePath!}">
                        <#if product.title1 ??>
                          <input type="hidden" name="titel1" value="${product.title1!}">
                        <#else>
                          <input type="hidden" name="titel1" value="${product.proBrand!}">
                        </#if>
                        <input type="hidden" name="titel2" value="${product.title!}">
                        <input type="hidden" name="maxFucoin" value="${product.maxFucoin!}">
                        <input type="hidden" name="proPrice" value="${product.proPrice!}">  
                        <input type="hidden" name="proSalePrice" value="${product.proSalePrice!}">  
                        <input type="hidden" name="quantity" value="${product.quantity!}">
                        <input type="hidden" name="replaceIndex" value="${product.ex6Value!}">
                	</div>
				  </#list>
				</#if>
            </div>
        </div> 
        <!--不止特省结束-->
        <!--优享内购价开始--> 
        <div class="main_box" id="pro_if5" style="display:none;">
            <div class="tit2">
                <p class="p1">优享内购价</p>
                <p class="p2">一样的品质，不一样的价格，我的福利不一样</p>
                <a href="main.html"  target="_blank">更多内购>></a>
                <span><img src="images/new_index_tit2_spanbg.png" /></span>
            </div>
            
            <div class="main_con">
                <ul></ul>
				<#if pageMap["5f_product"]??>
                  <#assign producList =pageMap["5f_product"] >
				  <#list producList as product>
                	<div class="hid_product_div" style="display:none;">
                        <input type="hidden" name="link" value="${product.link!}">
                        <input type="hidden" name="image" value="${product.imagePath!}">
                        <#if product.title1 ??>
                          <input type="hidden" name="titel1" value="${product.title1!}">
                        <#else>
                          <input type="hidden" name="titel1" value="${product.proBrand!}">
                        </#if>
                        <input type="hidden" name="titel2" value="${product.title!}">
                        <input type="hidden" name="maxFucoin" value="${product.maxFucoin!}">
                        <input type="hidden" name="proPrice" value="${product.proPrice!}">  
                        <input type="hidden" name="proSalePrice" value="${product.proSalePrice!}">  
                        <input type="hidden" name="quantity" value="${product.quantity!}">
                        <input type="hidden" name="replaceIndex" value="${product.ex6Value!}">
                	</div>
				  </#list>
				</#if>
            </div>
        </div> 
        <!--优享内购价结束-->   
        <div class="my_stamps">
            <ul>
            </ul>
        </div>
    </div> 
</div>

<!--content end-->
<#include "footer.ftl">
<#if pageParam["page_ex4Value"]??>
<script type="text/javascript" src="${pageParam["page_ex4Value"]!}"></script>
</#if>
<script type="text/javascript" src="/user/activityLog?url=${pageParam["page_key"]!}"></script>
</body>
</html>
