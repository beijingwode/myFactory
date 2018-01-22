<%@ page language="java" pageEncoding="utf-8"%>
<div id="top">
	<%@ include file="/common/_tops.jsp" %>
</div>
<div class="head R_wid">
    <div class="logo"><a href="/index.html"><img src="<%=basePath %>images/logo.png" alt="logo"></a></div>
        <div class="search R_margin">
            <div class="searchbox">
            <form action="<%=basePath %>product/search" method="get" id="search_form">
                <input class="searchinput" type="text" name="key" value="${keyword}" onBlur="if($.trim(value)==''){value=defaultValue;}" onFocus="if(value==defaultValue){value='';}">
            </form>
                <button class="search_btn" type="submit" id="search_btn" form="search_form" onclick="$('#search_form').submit();">搜索</button>
            </div>
            <div class="search_list">
                 <ul>
		               <li><a href="http://wd-w.com/product/search?key=%E5%AE%B6%E7%94%A8%E7%94%B5%E5%99%A8&amp;page=1#orders" target="_blank">电器</a></li>
		               <li><a href="http://wd-w.com/product/search?key=%E6%A2%B5%E7%94%B0" target="_blank">家居</a></li>
		               <li class="curr"><a href="http://wd-w.com/product/search?key=%E6%80%9D%E5%87%AF%E4%B9%90" target="_blank">思凯乐</a></li>
		               <li><a href="http://wd-w.com/product/search?key=%E9%9F%A9%E5%A6%99" target="_blank">韩妙</a></li>
		           </ul>
            </div>
        </div>
</div>