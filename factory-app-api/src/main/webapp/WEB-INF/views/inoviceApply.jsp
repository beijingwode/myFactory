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
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/sales_return.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/special_orderDetail.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/invoice_apply.js"></script>
<title>发票申请</title>
<style type="text/css">
select{
	appearance:none;
	-webkit-appearance:none;
}
.chooseType{
	border:none;
	width:74%;
	background: url("../static_resources/images/go_down.png") no-repeat scroll right center transparent;
	background-color:#fff;
	color: #444;
	background-size: 12px 7px;
}

.billType{
	border:none;
	height:auto;
	width:74%;
	background: url("../static_resources/images/go_down.png") no-repeat scroll right center transparent;
	background-color:#fff;
	color: #444;
	background-size: 12px 7px;
}
.status{
	height: auto;
    line-height: 44px;
    display: block;
    font-size: 14px;
}
.share{
    height: 42px;
    width: 100%;
    background: #fff;
    border-bottom: 1px solid #c8c7cc;
}
.share a{
    height: 42px;
    width: 96%;
    margin-left: 2%;
    font-size: 1.3em;
    display: block;
    line-height: 42px;
    background-size: 7px 14px;
    color: #333;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}
.region{
	text-align: center;
	font-size:1.4em;
	height: 24px;
	padding-top: 10px;
}
.thickdiv {position: fixed;top: 0;left: 0;z-index: 10000001;width: 100%;height: 100%;background: #000;border: 0;filter: alpha(opacity = 15);opacity: .15;display:none;}
#address{background: url(../static_resources/images/go_next.png) no-repeat right center; background-size: 7px 14px;}
.logistics .iss_link{width: 65%;text-align: right;float: right;padding-right: 14px;color: #ff4040;background: url(../images/go_next.png) no-repeat right center;background-size: 7px 14px;}
</style>
</head>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var subOrderId = '${suborderId}';
	var uid = '${uid}';
</script>
<body>
<div class="main-cont" id="main-cont" >
	<div class="top">
        <h1><a href="javascript:close();" class="aleft"></a>发票申请</h1>
    </div>
	<div class="main_box" style="position:absolute;top:45px;left:0;">
			<input type="hidden" id="invoiceApply_id" value="">
			<div class="return_hit">
				<img src="<%=static_resources %>images/tuikuan_agree.png" id="completePic" style="display: none">
				<img src="<%=static_resources %>images/tuikuan_progress.png" id="progressPic" style="display: none"/>
				<span id="status" class="status"></span>
				<p id="suborderId"></p>
				<p id="suborderPrice"></p>
			</div>	
			<div class="logistics">
				<input type="hidden" id="shippingAddressId" value="">
				<ul>
					<li class="li5" style="display: none"><span>订单编号</span><p id="suborder_id"></p></li>
					<li class="li1"><span>收票地址:</span><p id="address"></p></li>
					<li class="li2"><span><i>*</i>发票抬头:</span>
					<select id="type" class="chooseType" onchange="chooseType()">
						 <option value="0">个人</option>
						 <option value="1">单位</option>
					  </select>
					 </li>
					<li class="li3" style="display: none"><span><i>*</i>单位名称:</span><input type="text" id="title" value="" placeholder="请输入单位名称"></li>
					<li class="li7" style="display: none"><span><i>*</i>发票类型:</span>
					<select id="billType" class="chooseType" onchange="billType()">
						 <option value="0">普通发票</option>
						 <option value="1">专用发票</option>
					  </select>
					</li>
					<li class="li7" style="display: none"><span><i>*</i>纳税人识别号:</span><input type="text" id="taxpayerNumber" value="" placeholder="请输入纳税人识别号" maxlength="50"></li>
					<li class="li8" style="display: none"><span><i>*</i>注册地址:</span><input type="text" id="registerAddress" value="" placeholder="请输入注册地址" maxlength="100"></li>
					<li class="li8" style="display: none"><span><i>*</i>注册电话:</span><input type="text" id="registerPhone" value="" placeholder="请输入注册电话" maxlength="50"></li>
					<li class="li8" style="display: none"><span><i>*</i>开户行名称:</span><input type="text" id="openingBan" value="" placeholder="请输入开户行名称" maxlength="100"></li>
					<li class="li8" style="display: none"><span><i>*</i>开户行账号:</span><input type="text" id="openingBanNumber" value="" placeholder="请输入开户行账号" maxlength="50"></li>
					<li class="li6"><span>备注:</span><input type="text" value="" id="note"></li>
					<li class="li4" style="display: none"><span>创建时间:</span><p id=invoiceApply_createTime></p></li>
				</ul>
			</div>
			<div class="logistics details_refund" style="display:none">
				<ul>
					<li><span>发票类型</span><p id="iss_type"></p></li>
					<li><span>抬头</span><p id="iss_title"></p></li>
					<li><span>销售方</span><p id="iss_seller"></p></li>
					<li><span>发票号</span><p id="iss_invoice"></p></li>
					<li><span>价税合计</span><p id="iss_price"></p></li>
					<li><span>开票日期</span><p id="iss_createTime"></p></li>
					<li><span>发票链接</span><a id="iss_link" class="iss_link"></a></li>
				</ul>
			</div>
			<div class="btns" style="display: none"></div>
	</div>
</div>
<div class="thickdiv" ></div>
<div class="smallPopup">
	<div class="region">选择收票地址</div>
	<div class="share odb">
	</div>
</div>
<!-- <div class="orderbox" >
    <div class="thickcon">
    <input type="hidden" id="suborderID">
		<div class="region">选择收票地址</div>
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
</div> -->
<script>
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main_box").css("top","0");
	}
});

</script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
