<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="static_resources/css/swiper-3.4.2.min.css" />
<link rel="stylesheet" type="text/css" href="static_resources/css/public_m.css" />
<link rel="stylesheet" type="text/css" href="static_resources/css/index_m.css" />

</head> 

<body>

<div class="main-cont" id="main-cont" >
<#include "1_m_c.ftl">
   
   	 <!-- 猜你喜欢 -->
	 <div class="youlike_box">
	 	<div class="like_tit"><span>猜你喜欢</span></div>
	 	<div class="like_con">
	 		<ul id="like_li">
	 		</ul>
	 		<input type="hidden" id="pageNum" value="0" autocomplete="off">
	 	</div>
	 	<p>正在加载更多数据</p>
	 </div>
</div>
<div class="back_top" id="back_top"><img src="static_resources/images/homegotopImage.png" /></div>
<script type="text/javascript" src="static_resources/js/jquery1.8.0.js"></script>

<script type="text/javascript" src="static_resources/js/jquery.flexslider-min.js"></script>
<script type="text/javascript" src="static_resources/js/index.js?2"></script>
<script type="text/javascript" src="static_resources/js/wxGetUid.js"></script>
<script type="text/javascript" src="static_resources/js/index_goto.js?1"></script>

<script type="text/javascript" src="static_resources/js/swiper-3.4.2.jquery.min.js"></script>
<script type="text/javascript" src="static_resources/js/miaoShaEndDate.js"></script>

</body>
<script>
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
function setActive() {
	$(".nav ul li:eq(3)").addClass("active");
}
</script>
</html>
