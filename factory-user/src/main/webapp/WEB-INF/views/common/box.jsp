<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>	
<link rel="stylesheet" type="text/css" href="/css/box.css">
<script type="text/javascript" >
$(document).ready(function() {
/**
 * 提示信息弹窗
 */
$(".popup_box_title img").click(function(){
	$(".box").hide();
});

$("#boxCancel").click(function(){
	$(".box").hide();
});

$("#boxCheck").click(function(){
	$(".box").hide();
});

$(".update_express_close").click(function(){
	$("#daishoudian").hide();
	$(".box").hide();
});

});
if(!wode){
	wode={};
}
wode.showBox=function(title,msg,opt){
	$(".box .popup_box_title>span").html(title);
	$("#box_message_icon li").html(msg);
	if(opt&&opt.hideBtn){
		$(".box .popup_box_btn").hide();
	}else{
		$(".box .popup_box_btn").show("");
	}
	if(opt&&opt.longContent){
		$(".box .longContent").html(opt.longContent);
	}else{
		$(".box .longContent").html("");
	}
	if(opt&&opt.icon){
		$("#box_message_icon").removeClass();
		$("#box_message_icon").addClass(opt.icon);		
	}else{
		$("#box_message_icon").attr("class","box_message");
	}
	
	$(".box").show();
}

</script>
<!-- begin-->
<div class="box">
<div class="popup_box"></div>
<div class="box_popup">
	<div class="popup_box_title">
    	<span>确认</span>
        <label><img src="/images/close.gif" width="14" height="14" alt="close" id="close"></label>
    </div>
    <div class="popup_box_cont">	
		<div class="popup_box_order">
            <div class="box_message" id="box_message_icon">
                <ul>
                    <li></li>
                </ul>
            </div>
            
        </div>
       <div class="longContent"></div>
        <div class="popup_box_btn">
            <a id="boxCheck" href="javascript:void(0);">确认</a>
            <a id="boxCancel" class="btn-1" href="javascript:void(0);">取消</a>
        </div>
	</div>
</div>
</div>
<!-- end-->



<!-- 代收点 begin -->
<div class="shop_popup stationTab-popbox" id="daishoudian" style="display:none;">
    <div class="popup_title">
        <span>选择代收点</span>
        <label><img src="/images/close.gif" width="14" height="14" alt="close" class="update_express_close"></label>
    </div>
    <div class="stationTab-popcont">    
        <ul class="stationTab-step">
            <li><span>1</span>选择代收点并下单</li><em>></em>
            <li><span>2</span>收到提货短信(或物流详情)</li><em>></em>
            <li><span>3</span>到代收点自提</li>
        </ul>
        <div class="station-search">
            <select name="" id="express_province" class="station-province">
            </select>
            <select name="" id="express_city" class="station-province">
            </select>
            <select name="" id="express_area" class="station-province">
            </select>
            <input type="text" id="add_address" class="s-adrress-input" name="" placeholder="请输入地址或店名">
            <button class="s-btn" id="search_address">搜地址</button>
            <button class="s-btn" id="search_shopName">搜店名</button>
        </div>
        <div class="pop-error" style="display: none;"><i class="error-icon">ⓧ</i>不能为空！</div>
        <div class="map-box">
            <div class="map-ad">
                <ul class="dai-addr-ul" id="map_left_ul">
                </ul>
            </div>
            <div class="map-rt" id="baidu_map"></div>
        </div>
        <div class="consignee-container">
            <ul class="consignee-info">
                <li>
                    <span class="asterisk">*</span>
                    <label>收货人姓名：</label>
                    <input class="txt-box txt-long" id="update_shipping_name" maxlength="20" type="text" name="" value="${name}">
                    <div class="mui-msg-error mr" id="update_shipping_error" style="display: none;"><i class="error-icon">ⓧ</i>不能为空！</div>
                </li>
                <li>
                    <span class="asterisk">*</span>
                    <label>手机：</label>
                    <input class="txt-box txt-long" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" id="update_shipping_phone" maxlength="11" type="text" name="" value="${phone}">
                	<div class="mui-msg-error mrs" id="update_phone_error" style="display: none;"><i class="error-icon">ⓧ</i>不能为空！</div>
                </li>
            </ul>
        </div>
        <div class="dai-agree">
            <div class="dai-msg">
                <input name="check" type="checkbox" checked="checked">我已阅读并同意
                <a href="#">《代收货相关条例》</a>
            </div>            
            <div class="desc">（代收点代收免费保管期为包裹签收日起5天内，请及时取件；
                <span class="t-blue">体积过大</span>
                <span class="t-red">*</span>包裹建议不要选择代收点代收）
            </div>
        </div>
        <div class="dai-agree-error" style="display: none;"><i class="error-icon" >ⓧ</i>复选框没有被选中!</div>
        <div class="dai-button">
            <button type="button" class="tb-btn t-blue-bg" onclick="submit();">&nbsp;&nbsp;确定&nbsp;&nbsp;</button>
            <button type="button" class="cancle-btn update_express_close">取消</button>
        </div>
    </div>
</div>
<!-- 代收点 end -->
