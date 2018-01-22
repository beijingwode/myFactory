		<div class="search">
		  <#if pageParam["hasMore"]??>
			<div class="searchbox" style="width:640px;">
	            <form action="/product/search" method="get" id="search_form" target="_blank">
	                <input class="searchinput" type="text" name="key" value="" onBlur="if($.trim(value)==''){value=defaultValue;}" onFocus="if(value==defaultValue){value='';}">
                	<div class="search_btn" onclick="$('#search_form').submit();">搜我的网</div>
                	<div id="btn_more" class="search_btn btn_ll" style="border-radius:5px 5px 5px 5px;">${pageParam["hasMore"]!}</div>
	            </form>
	     	</div>
		  <#else>
			<div class="searchbox" style="width:640px;">
	            <form action="/product/search" method="get" id="search_form" target="_blank">
	                <input class="searchinput" type="text" name="key" value="" onBlur="if($.trim(value)==''){value=defaultValue;}" onFocus="if(value==defaultValue){value='';}">
	                <input  class="search_btn" id="search_btn" type="submit" value="搜索"/>
	            </form>
	     	</div>
		  </#if>
		     <div class="search_list">
	                <ul>
	                    <li><a href="http://www.wd-w.com/1965418437870859.html">惠普</a></li>
						<li class="curr"><a href="http://www.wd-w.com/product/search?key=徐福记">徐福记</a></li>
						<li><a href="http://www.wd-w.com/product/search?key=tcl">tcl</a></li>
						<li class="curr"><a href="http://www.wd-w.com/product/search?key=净美仕">净美仕</a></li>
						<li><a href="http://www.wd-w.com/product/search?key=九阳">九阳</a></li>
						<li class="curr"><a href="http://www.wd-w.com/product/search?key=爱国者">爱国者</a></li>
						<li><a href="http://www.wd-w.com/product/search?key=富士">富士</a></li>
						<li><a href="http://www.wd-w.com/product/search?key=北欧欧慕">北欧欧慕</a></li>
	                </ul>
		      </div>
	    </div>    
        
   