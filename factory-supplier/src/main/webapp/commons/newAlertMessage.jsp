<%@ page language="java" pageEncoding="utf-8"%>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/newAlertMessage.css">
<div class="new-theme-popover" id="msgShowDiv" style="display: none">
     <div class="new-theme-poptit"></div>
     <div class="new-theme-popbod" style="display: none">
        <a href="javascript:void(0);">确定</a>
        <a href="javascript:closeMsg();" style="border:none;">取消</a>  
     </div>
     <div class="new-theme-popbod1" >
        <a href="javascript:void(0);">确定</a>
     </div>
</div>
<div class="new-theme-popover-mask" style="display: none"></div>
<script type="text/javascript">

function msgShow(){
	$(".new-theme-popover-mask").show();
	$("#msgShowDiv").show();
}

function closeMsg(){ 
	$(".new-theme-popover-mask").hide();
	$("#msgShowDiv").hide();
}

function showInfoBox(msg,act){
	$(".new-theme-popover .new-theme-poptit").html(msg);
	$(".new-theme-popover .new-theme-popbod").hide();
	$(".new-theme-popover .new-theme-popbod1").show();
	if(act != null && act!="") {
		$(".new-theme-popbod1 a").attr("onclick",act + ";closeMsg();");
	} else {
		$(".new-theme-popbod1 a").attr("onclick","closeMsg();");		
	}
	msgShow();
}

function showConfirm(msg,act,act2){
	//alert(name+"_"+id);
	$(".new-popup_bg").show();
	
	//alert(name+"_"+id);
	if(msg != null && msg!=""){
		$(".new-theme-popover .new-theme-poptit").html(msg);
	}
	$(".new-theme-popover .new-theme-popbod").show();
	$(".new-theme-popover .new-theme-popbod1").hide();
	$(".new-theme-popover .new-theme-popbod a:eq(1)").attr("onclick",act + ";closeMsg();");
	if(act2 != null && act2!="") {
		$(".new-theme-popover .new-theme-popbod a:eq(0)").attr("onclick",act2 + ";closeMsg();");
	} else {
		$(".new-theme-popover .new-theme-popbod a:eq(0)").attr("onclick","closeMsg();");
	}
	msgShow();
}
</script>
