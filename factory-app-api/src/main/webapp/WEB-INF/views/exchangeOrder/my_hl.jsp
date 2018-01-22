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
<title>我的换领</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public_m.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/my_hl.css" />
</head> 
<script type="text/javascript">
	var jsBasePath = '<%=basePath %>';
</script>
<body>

<div class="main-cont" id="main-cont">
<!--content begin-->
	<input type="hidden" id="pageNum" value="0">
	<div class="main_box" id="matchOrder">
		
		<div class="main_top">
			<ul class="ul1">
				<li><a href="javascript:void(0);" class="this_one">欲领清单</a></li>
				<li><a href="javascript:go2WishOrder();">调剂清单</a></li>
			</ul>
			<ul class="ul2">
				<li id="balance"><i><img src="<%=static_resources %>images/hlb_ye_icon.png" /></i><span>换领币余额：0.00</span></li>
				<li id="total"><i><img src="<%=static_resources %>images/hlb_ze_icon.png" /></i><span>总额：0.00</span></li>
			</ul>
		</div>	
		
		<div id="exchangOrderList">
		
		</div>
		
	</div>
</div>
<div class="thickdiv" ></div>
<div class="orderbox" >
    <div class="thickcon">
    <input type="hidden" id="suborderID">
    <input type="hidden" id="exchangeStatus">
		<div class="region">选择关闭订单原因</div>
			<div class="close_list_con">
				<ul>
					<li><span>我不想买了</span><em></em></li>
					<li><span>信息错了，我重拍</span><em></em></li>
					<li><span>卖家缺货</span><em></em></li>
					<li><span>付款遇到问题（余额不足，不会付款）</span><em></em></li>
					<li><span>拍错了</span><em></em></li>
					<li><span>其它原因</span><em></em></li>
				</ul>
			</div>
		<div class="fh_btn" style="position:absolute;left:4%;bottom:0;">
		<a href="javascript:toCancel();">确认取消订单</a>
		</div>
   </div>
   <a href="javascript:void(0)" id="closeBox" class="thickclose" >×</a>
</div>
<!-- 弹出二维码 -->
<div class="t_ewm"><img src="<%=static_resources %>images/ewm_img.png" /></div>
<div class="thickdiv" onclick="hide()"></div>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/exchangeOrder/my_hl.js?1"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/order_sessionStorage.js"></script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
<script>
$(function(){
	$(".close_list_con ul li em").each(function(index){
		$(this).click(function(){//先把所有的隐藏掉
			
			$(".close_list_con ul li em").removeClass("em1");
			$(this).addClass("em1");
		});
	});	
})

function hide(){
	$(".t_ewm").hide();
	$(".thickdiv").hide();
}
</script>
</html>
