<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" >
<meta content="telephone=no" name="format-detection" />
<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="/css/public1.css" />
<link rel="stylesheet" type="text/css" href="/css/404.css" />
</head>

<body>
<!--content begin-->
<div id="content">
<input type="hidden" id="pageKey" value="${pageParam["page_key"]!}">
	<div class="errorwrap">
    	<div class="errortext">
        	<p class="errortitle">非常抱歉，此链接已失效！</p>
            <p><span class="errred">可能原因：</span>特惠活动已结束或二维码已过期，请访问其他页面</p>
            <p>请尝试以下办法：</p>
            <p>1、请检查您的操作是否正确;</p>
            <p>2、直接联系在线客服 或者拨打010－57746483进行咨询；</p>
            <p>3、返回<a href="${pageParam["page_url"]!}">我的网首页</a>。</p>
        </div>
    </div>
</div>
<!--content end-->
<script type="text/javascript">
setTimeout("window.location.href='${pageParam["page_url"]!}'",1500);
</script>

</body>
</html>
