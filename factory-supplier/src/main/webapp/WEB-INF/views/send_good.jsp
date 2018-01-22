<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no, telephone=no">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/transaction_management.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/prompt.css" />
<title>发货</title>
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/wxGetUid.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/send_good.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/api/nochange_font.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
	var subOrderId = '${subOrderId}';
</script>
</head>
<body>
<div class="main_box">   
    <div class="tk">第一步 确认收货信息及收货详情</div>
    <div class="tk_con">
    	<p id="subOrderNum"><span></span></p>
        <p id="buyName"><span></span></p>
        <p id="buyInfo"><span>买家收货信息：</span><i style="width:65%;"></i></p>
        <p id="buyTel"><span>收货手机号：</span><i></i></p>
        
    </div>
    <div id="product">
    <div class="con_details" style="background:#fff;border-top:1px solid #d5d5d5;width:96%;padding-left:4%;">
        <dl>
            <dt><a href="javascript:void(0);"><img src="<%=static_resources %>images/shop_dt.png" /></a>
                <div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
            </dt>
            <dd class="dd1"><a href="javascript:void(0);"></a></dd>
            <dd class="dd2"></dd>
        </dl>
        <div class="ds_rt"><span></span><em></em></div>
    </div>
    </div>
    <div class="tk">第二步 确认发货/退货信息</div>
    <div class="my_address" style="margin-top:0;">
    	<p ><span>我的发货信息：</span><em id="sendAddress"></em></p>
    	<p><i id="sendAddressNull" style="display: none">还未设置我的发货信息,请先到电脑端设置后再使用</i></p>
        <p><span>我的退货信息：</span><em id="returnedAddress"></em></p>
        <p><i id="returnedAddressNull" style="display: none">还未设置我的退货信息,请先到电脑端设置后再使用</i></p>
    </div>
    <div class="tk" id="chooseShip">第三步 选择物流<a href="javascript:go2ChooseExpress(2)" style="margin-left:30px;color:#ff4040;" id="commExpress">常用快递</a></div> 
    <div class="wl_con" id="shipInfo">
    	<div class="wl ydh" id="qr"><span>运单号码：</span><input type="text" id="expressNo" placeholder="请填写或点击右图扫描"/><a href="javascript:void(0);"></a></div>
        <div class="wl wlmc"><span>物流名称：</span><a href="javascript:go2ChooseExpress(1)" id="expressName">请选择</a></div>
        <input type="hidden" id="expressType">
    </div>
    <div class="fh_btn"><a href="javascript:go2ConfirmSend();">确认发货</a></div>
</div>
<div class="recharge_money"  style="display: none">
     <div class="theme-tit">请确认信息后发货</div>
     <div class="theme-input">
     <p id="expressCompany" ></p>
     <p id="expressNum" ></p>
     <!-- <input type="text" id="expressCompany" disabled="disabled">
     <input type="text" id="expressNum" disabled="disabled"> -->
     </div>
     <div class="theme-popbod">
        <a href="javascript:go2Close();" >取消</a>  
        <a href="javascript:go2Sure();" style="border:none;">确定</a>
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
<%@ include file="/commons/newAlertMessage.jsp" %>




<script type="text/javascript" src="<%=static_resources %>js/views_sendGood.js"></script>
</body>
</html>