<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
if(request.getServerPort() != 80 && request.getServerPort() != 443) {
	path=":"+request.getServerPort()+path;
}
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<title>我的福利-商品搜索</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/search_m.css" />
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var pageKey = '${pageKey}';
</script>
</head> 

<body>

<body>
<div class="main-cont" id="main-cont" >
	<input type="hidden" id="params" value="${params}">
	<input type="hidden" id="pageNum" value="0">
	<div class="top_box">
        <div class="top" >
            <a href="javascript:history.back();" class="aleft" style="background:none;margin-right:3%;"></a>   	
            <div class="search_box">
            	<div class="search_con"><input type="search" placeholder="我想要" class="ipt_text" name="keyword" id="keyword" value="" readonly="readonly" autocomplete="on"/></div>
                <div class="search_btn"><a id="search_btn" href="javascript:void(0);">搜索</a></div>
            </div>
        </div>
        <ul>
        	<li id="zh" class="thisone"><a href="javascript:void(0);" >综合</a></li>
            <li id="discount"><a href="javascript:void(0);" >折扣</a></li>
            <li id="price" class="search_jg search_jg1"><a id="pPrice" href="javascript:void(0);"  title="由低到高">价格</a></li>
            <li id="filter" class="search_sx search_sx1"><a href="javascript:go2filter();">筛选</a></li>
        </ul>
    </div>
    <div class="main_con"><ul></ul></div>
    <div id="bottom" style="height:30px;width:100%;line-height:30px;text-align:center;margin-top:10px;">正在加载更多数据</div>
	
</div>
<div class="thickdiv" ></div>
<div class="thickbox" >
	
    <div class="thickcon">
    	<div class="price_range">
    	<input type="hidden" id="priceRange" value=" ">
        	<span class="part-note-msg">价格区间</span> 
            <div class="price_inp">
            	<input type="text" placeholder="最低价" class="price_lt" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');"/><em></em>
            	<input type="text" placeholder="最高价" class="price_rt" onkeyup="if(isNaN(value))execCommand('undo');this.value=this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');"/>
            </div>
        </div>
        <!--颜色-->
        <div class="pro-color">
        <input type="hidden" id="searchBrand" value=" " >
            <span class="part-note-msg">全部品牌</span>           
            <p  id="color">
               	
            </p>            
        </div>
    <div class="thickbox_bottom"><a href="javascript:go2reset();" class="cz_btn">重置</a><a href="javascript:go2submit();" class="qd_btn">确定</a></div>
    </div>
    <a href="javascript:void(0)" id="closeBox" class="thickclose" >×</a>                                                             
    
</div>


<div class="back_top" id="back_top"><img src="<%=static_resources %>images/homegotopImage.png" /></div>

<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/search.js?0112"></script>
<script type="text/javascript" src="<%=static_resources %>js/jqScroll.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript">	
$(document).ready(function(){
	/*返回顶部*/
	$('#back_top').hide();
	$(window).scroll(function () {
		if ($(window).scrollTop() > 300) {
			$('#back_top').fadeIn(400);//当滑动栏向下滑动时，按钮渐现的时间
		} else {
			$('#back_top').fadeOut(0);//当页面回到顶部第一屏时，按钮渐隐的时间
		}
	});
	$('#back_top').click(function () {
		$('html,body').animate({
			scrollTop : '0px'
		}, 300);//返回顶部所用的时间 返回顶部也可调用goto()函数
	});
});
function goto(selector){
	$.scrollTo ( selector , 1000);	
}
</script>
</body>
</html>
