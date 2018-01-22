<%@ page language="java" pageEncoding="utf-8"%>

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/alert.css">
<div class="theme-popover" id="msgShowDiv" style="display: none">
     <div class="theme-poptit"></div>
     <div class="theme-popbod" style="display: none">
        <a href="javascript:closeMsg();">取消</a>
        <a href="javascript:void(0);" style="border:none;">确定</a>          
     </div>
     <div class="theme-popbod1" >
        <a href="javascript:void(0);">确定</a>
     </div>
</div>
<div class="theme-popover-mask" style="display: none"></div>
<script type="text/javascript">

function msgShow(){
	$(".theme-popover-mask").show();
	$("#msgShowDiv").show();
}

function closeMsg(){ 
	$(".theme-popover-mask").hide();
	$("#msgShowDiv").hide();
}

function showInfoBox(msg,act){
	$(".theme-popover .theme-poptit").html(msg);
	$(".theme-popover .theme-popbod").hide();
	$(".theme-popover .theme-popbod1").show();
	if(act != null && act!="") {
		$(".theme-popbod1 a").attr("onclick",act + ";closeMsg();");
	} else {
		$(".theme-popbod1 a").attr("onclick","closeMsg();");		
	}
	msgShow();
}

function showConfirm(msg,act,act2){
	//alert(name+"_"+id);
	$(".popup_bg").show();
	
	//alert(name+"_"+id);
	if(msg != null && msg!=""){
		$(".theme-popover .theme-poptit").html(msg);
	}
	$(".theme-popover .theme-popbod").show();
	$(".theme-popover .theme-popbod1").hide();
	$(".theme-popover .theme-popbod a:eq(1)").attr("onclick",act + ";closeMsg();");
	if(act2 != null && act2!="") {
		$(".theme-popover .theme-popbod a:eq(0)").attr("onclick",act2 + ";closeMsg();");
	} else {
		$(".theme-popover .theme-popbod a:eq(0)").attr("onclick","closeMsg();");
	}
	msgShow();
}
</script>
