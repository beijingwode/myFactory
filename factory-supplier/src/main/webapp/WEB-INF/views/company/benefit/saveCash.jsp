<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>商家中心--员工福利</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/welfare.css">
<%@ include file="/commons/js.jsp" %>

</head>
<body>
	<!-- top start -->
	<%@ include file="/commons/header.jsp" %>
	<!-- top end -->

	<div id="content" class="clear">
		<!-- left menu start -->
		<%@ include file="/commons/leftmenu.jsp"%>
		<!-- left menu end -->
		  <!--right begin-->
    <div class="right">
    	<div class="position">
        	<span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/company/enterprise/getEnterpriseInfo.html">员工福利</a><em>></em>
            <a href="javascript:void(0);">现金储值</a>
        </div>
        <div class="r-content">
        	<div class="c-amount">
            	<span>储值额度</span>
                <div class="c-wrap">
                    <input class="c-input" type="text" name="money" value="" id="money" onafterpaste="inutNumber_double(this);" maxlength="10"/>
                    <span class="c-dem">元</span>
                </div>
                <div class="error" id="saveCash_error"></div>
                
                <div id="p_money" style="display: none">
                <p class="c-mark"  ><em>*</em>&nbsp;&nbsp;将向平台账号进行现金储值<em id="show_money"></em></p>
                </div>
            </div>
            <div class="c-cont-box">
            	<ul class="c-type-list">
                	<li class="current">平台支付</li>
                	<!-- 
                    <li>储蓄卡</li>
                     -->
                </ul>
                <div class="c-sort" style="display:block;">
                	<ul class="play-list">
                    	<li><input type="radio" value="zhifubao" name="zhifu" id="rd1" /><label for="rd1"><img src="<%=static_resources %>images/zhifubao.jpg" width="162" height="41" alt="zhifubao" title="zhifubao"></label> </li>
                    	<!-- 
                        <li><img src="${basePath}/images/hebao.jpg" width="162" height="41" alt="hebao" title="heboa"></li>
                        <li><img src="${basePath}/images/wechat.jpg" width="162" height="41" alt="wechat" title="wechat"></li>
                        <li><img src="${basePath}/images/caifutong.jpg" width="162" height="41" alt="caifutong" title="caifutong"></li>
                        <li><img src="${basePath}/images/kuaiqian.jpg" width="162" height="41" alt="kuaiqian" title="kuaiqian"></li>
                     	-->
                        <li><input type="radio" value="unionpay" name="zhifu" id="rd0" checked="checked" /><label for="rd0"><img src="<%=static_resources %>images/zaixian.jpg" width="162" height="41" alt="zaixian" title="zaixian" /></label> </li>
                    </ul>
                    <div class="next-btn"><a href="javascript:void(0)" id="next">下一步</a></div>
                </div>
                <div class="c-sort">
                	<ul class="play-list">
                    	<li><img src="<%=static_resources %>images/china.jpg" width="162" height="41" alt="china" title="china"></li>
                        <li><img src="<%=static_resources %>images/jiaotong.jpg" width="162" height="41" alt="jiaotong" title="jiaotong"></li>
                        <li><img src="<%=static_resources %>images/guangfa.jpg" width="162" height="41" alt="guangfa" title="guangfa"></li>
                        <li><img src="<%=static_resources %>images/zhaoshang.jpg" width="162" height="41" alt="zhaoshang" title="zhaoshang"></li>
                        <li><img src="<%=static_resources %>images/gongshang.jpg" width="162" height="41" alt="gongshang" title="gongshang"></li>
                        <li><img src="<%=static_resources %>images/jianshe.jpg" width="162" height="41" alt="jianshe" title="jianshe"></li>
                    </ul>
                    <div class="next-btn"><a href="${basePath}/payment/toPay.html">下一步</a></div>
                </div>
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--弹出框 begin-->
<div class="popup_bg"></div>
<div class="public_popup" id="applay">
	<div class="close-btn allsend-suceess-close" id="applay-close"><i class="close-icon"></i></div>
	<div class="pop-cont">
    	<p></p>
        <div class="ture-btn allsend-suceess-close"><a href="#">确认</a></div>
    </div>
</div>
<!--弹出框 end-->
<script>
var jsBasePath = '${basePath}';
var jsSuccess = '${success}';
var jsError = '${error}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/company_benefit_saveCash.js"></script>
</body>
</html>
