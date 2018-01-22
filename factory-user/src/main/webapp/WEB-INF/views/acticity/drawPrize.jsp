<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<title>抽奖</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>choujiang/css/choujiang.css" />
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/acticity/drawPrize.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var acticityId = '${acticityId}';
</script>
<body style="background:#fff url(${bgBanner}) no-repeat top center;">
<div class="cj_qdmd"><a href="javascript:signMsg();">签到名单</a></div>
<div class="mainbody">
	<div class="banner"><img src="${bannerImage}" /></div>
	<div class="jp_name"></div>
	<input type="hidden" id="gradeId">
	<div class="jc_box">
		<ul id="phoneMsg">
			<li class="xing">*</li>
			<li class="xing">*</li>
			<li class="xing">*</li>
			<li class="hz">我</li>
			<li class="hz">的</li>
			<li class="hz">福</li>
			<li class="hz">利</li>
			<li class="xing">*</li>
			<li class="xing">*</li>
			<li class="xing">*</li>
			<li class="xing">*</li>
		</ul>
	</div>
	<div class="zjr"></div>
	<div class="cj_btn"><a href="javascript:begin();"></a></div>
	<div class="ting_btn"><a href="javascript:stopFunEx();"></a></div>
	<div class="cj_show"><a href="javascript:prizeUserMsg();" class="hjmd">获奖名单</a><a href="javascript:changeStyle();"  class="jiangji">选择奖品</a></div>
	<div class="jiangji_box">
		<div class="auto_box">
			<ul id="prizeMsg">
				<li class="thisOne">一等奖</li>
				<li>二等奖</li>
				<li>三等奖</li>
			</ul>
		</div>
	</div>
	<div class="hjmd_box" id="prizeDiv">
		<div class="hjmd_con">
			<div class="hjmd_scroll">
			</div>
		</div>
		<div class="close_btn"><a href="javascript:;"><img src="<%=basePath %>choujiang/images/close_btn.png" /></a></div>
	</div>
	<div class="hjmd_box" id="signDiv">
		<div class="hjmd_con">
			<div class="hjmd_scroll">
				<div class="tit">签到名单</div>
				<div class="tit_con">
					<dl class="dl3">
						<dt><span class="span1">姓名</span><span class="span2">手机号码</span><span class="span3">签到时间</span></dt>
						<dd class="dd2"><span class="span1">No.999</span><span class="span2">刘女士</span><span class="span3">138****9991</span></dd>
						<dd class="dd2"><span class="span1">No.999</span><span class="span2">刘女士</span><span class="span3">138****9991</span></dd>
						<dd class="dd2"><span class="span1">No.999</span><span class="span2">刘女士</span><span class="span3">138****9991</span></dd>
						<dd class="dd2"><span class="span1">No.999</span><span class="span2">刘女士</span><span class="span3">138****9991</span></dd>
					</dl>					
				</div>
				
			</div>
			
		</div>
		<input class="export" type="button" value="下载EXCEL" onclick="exportMsg()">
		<div class="close_btn"><a href="javascript:;"><img src="<%=basePath %>choujiang/images/close_btn.png" /></a></div>
	</div>
</div>
<div class="mask"></div>
</body>
<script>
$(function(){
	$(".cj_qdmd").click(function(){
		$(".mask").show();
		$("#signDiv").show();
	}); 
	$(".close_btn").click(function(){
		$(".mask").hide();
		$("#prizeDiv").hide();
		$("#signDiv").hide();
	});
	
	$(".jiangji").click(function(){
		$(".jiangji_box").show();
	});
	
})
</script>
</html>
