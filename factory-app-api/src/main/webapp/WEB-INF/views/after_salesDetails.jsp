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
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<title>售后详情</title>
<style type="text/css">
/*选择区域*/
.thickdiv {position: fixed;top: 0;left: 0;z-index: 10000001;width: 100%;height: 100%;background: #000;border: 0;filter: alpha(opacity = 15);opacity: .15;display:none;} 
.thickbox {width: 85%;height: 100%;position: fixed;right: 0;top: 0px;padding-top:10px;z-index: 10000002;overflow: hidden;background:#fff;display:none;}
.thickclose{position:absolute;top:10px;right:10px;}
.thickbox .region{height:30px;width:92%;padding:0 4%;line-height:30px;font-size:1.4em;}
.tab_box{max-height:450px; overflow-y:auto;overflow-x:hidden;}
table{border-bottom:1px solid #c8c7cc;background:#fff;}
table tr td{width:33.33%;height:45px;border-top:1px solid #c8c7cc;text-align:center;line-height:20px;color:#000;font-size:1.3em;}
.orderbox {width: 90%;height: 100%;position: fixed;right: 0;top: 0px;padding-top:10px;z-index: 10000002;overflow: hidden;background:#fff;display:none;}
.thickclose{position:absolute;top:10px;right:10px;}
.orderbox .region{height:30px;width:92%;padding:0 4%;line-height:30px;font-size:1.4em;text-align: center;}
.info a {
    height: 40px;
    width: 100%;
    display: block;
    background: url(../images/go_next.png) no-repeat right center;
    background-size: 10px 16px;
}
</style>
</head>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var subOrderId = '${subOrderId}';
</script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/after_salesDetails.js"></script>
<body>
<div class="main-cont" id="main-cont" >
	
	<div class="top_box">
		<div class="top">
        	<h1><a href="javascript:void(0);" class="aleft"></a>售后详情</h1>
    	</div>
    </div>
	<div class="main_box" style="position:absolute;top:45px;left:0;">
			<div class="return_hit">
				<img src="<%=static_resources %>images/tuikuan_agree.png" id="agreePic" style="display: none">
				<img src="<%=static_resources %>images/tuikuan_progress.png" id="progressPic" style="display: none"/>
				<img src="<%=static_resources %>images/tuikuan_refunse.png" id="refunsePic" style="display: none"/>
				<p></p>
			</div>	
			<div class="refusal_box" style="display: none"><span>拒绝理由</span><p></p></div>
			<div class="logistics" style="display:none">
				<ul>
					<li class="li1"><span>退回地址</span><p id="returnAddress"></p></li>
					<li class="li2"><span><i>*</i>物流公司</span><a href="javascript:go2ExpressCompany();" id="returnOrder_expressName">请选择</a><input type="hidden" id="returnOrder_expressType" value=""></li>
					<li class="li3"><span><i>*</i>快递单号</span><input type="text" value="" id="returnOrder_expressNo" placeholder="请输入快递单号"></li>
					<li class="li4" style="display: none" onclick="go2CKShipping()"><span>查看退货物流</span><a href="javascript:void(0);">点击查看</a></li>
				</ul>
			</div>
			<div class="logistics details_refund">
				<ul>
					<li><span>退款类型</span><p id="type"></p></li>
					<li><span>退款金额</span><p id="price"></p></li>
					<li><span>申请退款时间</span><p id="createdate"></p></li>
					<li><span>退款原因</span><p id="reason"></p></li>
					<li class="li3"><span>退款说明</span><p id="note"></p></li>
				</ul>
			</div>
			<div class="btns" style="display:none"></div>
	</div>
</div>
<div class="add_money-mask" style="display: none"></div>
<div class="thickdiv" ></div>
<div class="orderbox" >
    <div class="thickcon">
 		<div class="region">选择快递</div>
 		<div class="tab_box">
        <table width="100%" cellspacing="0" cellpadding="0"> 
        	 <tr> 
            	<td>联邦</td> 
                <td>全日通</td>
                <td>申通快递</td>
            </tr>
            <tr> 
            	<td>EMS</td> 
                <td>中通快递</td>
                <td>能达快递</td>
            </tr>
            <tr> 
            	<td>EMS</td> 
                <td>中通快递</td>
                <td>能达快递</td>
            </tr>
        </table>
        </div>	
    </div>
    <a href="javascript:void(0)" id="closeBox" class="thickclose" >×</a>
</div>
<script type="text/javascript">
if(isWeiXin()) {
	$(".top_box").hide();
	$(".main_box").css("top","0px");
}
</script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
