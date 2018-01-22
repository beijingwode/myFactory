<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="top">
    <%@ include file="_top.jsp" %>
    </div>
    
    <div class="head">
        <div class="logo"><a href="/index.html"><img src="${basePath }/images/logo.png" alt="logo"></a></div>
        <div class="search">
            <div class="searchbox">
            <form action="${basePath }product/search" method="get" id="search_form">
                <input class="searchinput" type="text" name="key" value="${keyword}" onBlur="if($.trim(value)==''){value=defaultValue;}" onFocus="if(value==defaultValue){value='';}">
            	<input  class="search_btn" id="search_btn" type="submit" value="搜索" />
            </form>
                
            </div>
            <div class="search_list">
                <ul>
                    <li><a href="http://wd-w.com/product/search?key=%E5%AE%B6%E7%94%A8%E7%94%B5%E5%99%A8&page=1#orders">电器</a></li>
                    <li><a href="http://wd-w.com/product/search?key=%E6%A2%B5%E7%94%B0">家居</a></li>
                    <li class="curr"><a href="http://wd-w.com/product/search?key=%E6%80%9D%E5%87%AF%E4%B9%90">思凯乐</a></li>
                     <!-- <li><a href="http://wd-w.com/product/search?key=%E9%9F%A9%E5%A6%99" target="_blank">韩妙</a></li> -->
                </ul>
            </div>
        </div>
        <div class="shoppingcar"><span class="shopping_amount">0</span><a href="/cart/list">购物车</a></div>
                <div class="shoppingcar_cont">
        	<h2>最近加入的商品</h2>
            <div class="shoppingcar_list">
            	<!--one begin-->
            	<!-- <div class="shoppingcar_list_cont">
                	<div class="shoppingcar_img"><a href="#"><img src="images/shoppingcar_img01.jpg" width="50" height="58" alt="shoppingcar_img01"></a></div>
                    <div class="shoppingcar_r">
                    	<p><a href="#">金胜（Kingshare）笔记本光驱位硬盘托架12.7mm通用型（KS-ACD2512</a></p>
                        <div class="shopping_r_price">
                        	<span><strong>￥59.9</strong> *1</span>
                            <label><a name="delete" href="#">删除</a>
                            <input type="hidden" name="partNumber" value="">
                            </label>
                        </div>
                    </div>
                </div> -->
                <!--one end-->              
                <!-- <div class="account_info">
                    <span>3</span>件商品  共计 <strong>￥5457.9</strong>
                </div>
                <div class="go_account_btn"><a href="shoppingcart.html">去购物车结算</a></div> -->
            </div>            
        </div>
    </div>
    
    <div class="nav_line">
        <div class="nav">
            <div class="allproduct_menu"><p>全部商品分类</p></div>      
            <!--下拉菜单 begin-->   
          	<div class="menu_list"></div>   
            <!--下拉菜单 end-->
            <ul>
	        	<li><a href="/index.html">首页</a></li>
	            <li class="active"><a href="http://wd-w.com/product/search?key=%E6%80%9D%E5%87%AF%E4%B9%90">服装</a></li>
	            <li><a href="http://wd-w.com/product/search?key=%E9%9F%A9%E5%A6%99&brand=%E9%9F%A9%E5%A6%99#filters">美妆</a></li>
	            <li><a href="http://wd-w.com/product/search?key=%E7%94%B5%E8%84%91&brand=%E6%B8%85%E5%8D%8E%E5%90%8C%E6%96%B9#filters">电脑</a></li>
	            <li><a href="http://wd-w.com/product/search?key=%E5%A5%BD%E4%BB%81%E5%A5%BD%E4%BA%8B">食品</a></li>
	            <li><a href="http://wd-w.com/product/search?key=%E6%89%8B%E6%9C%BA">手机</a></li>
            </ul> 
        </div>
    </div>
</div>