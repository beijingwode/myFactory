<%@ page language="java" pageEncoding="utf-8"%>

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/box.css">
<div class="popup_bg" style="z-index:99999;"></div>
<div class="sort_popup" style="z-index:99999;" id="sort_popup_alert">
	<div class="popup_title">
    	<span>提示</span>
        <label><img src="<%=static_resources %>/images/close.gif" width="14" height="14" alt="close"></label>
    </div>
    <div class="sort_popup_cont">
    	<div class="box_msg">
        </div>
        <div class="clear"></div>
        <div class="popup_btn chmarbtn">
            <a href="javascript:void(0);" >确认</a>
        </div>
	</div>
 </div>
 
<!--confirm begin-->
<div class="sort_popup" style="z-index:99999;" id="shop_popup_confirm">
	<div class="popup_title">
    	<span id="confirm_title">操作确认</span>
        <label><img src="<%=static_resources %>/images/close.gif" width="14" height="14" alt="close"  onclick="hiddenObjById('shop_popup_confirm');"></label>
    </div>
    <div class="sort_popup_cont">	
		<div class="box_msg" id="confirm_text">您确定要这样做吗？</div>
        <div class="popup_btn chmarbtn">    
        	<a href="javascript:void(0);" class="btn_ok">确认</a>       
            <a href="javascript:void(0);" class="btn_qx" onclick="hiddenObjById('shop_popup_confirm');">取消</a>
            
        </div>
	</div>
</div>
<!--confirm end-->

	<script type="text/javascript">
	
		function showInfoBox(msg){
			$(".box_msg").html(msg);
			$(".popup_bg").show();
			$("#sort_popup_alert").show();
		}
		
		$(function(){
			$(".popup_btn a").click(function(){
				$('.popup_bg').hide();
				$('#sort_popup_alert').hide();	
			})
			$('.popup_title label').click(function(){
				$('.popup_bg').hide();
				$('#sort_popup_alert').hide();	
			})
		})
		
		/**
		 * 弹出层 提交审核
		 */
		function showConfirm(title,text,act,actCancel){
			//alert(name+"_"+id);
			$(".popup_bg").show();
			if(title != null && title!=""){
				$("#shop_popup_confirm").find("#confirm_title").html(title);
			}
			
			//alert(name+"_"+id);
			if(text != null && text!=""){
				$("#shop_popup_confirm").find("#confirm_text").html(text);
			}
			$("#shop_popup_confirm").find(".btn_ok").attr("onclick",act + ";hiddenObjById('shop_popup_confirm');");
			if(actCancel != null && actCancel!="") {
				$("#shop_popup_confirm").find(".btn_qx").attr("onclick",actCancel + ";hiddenObjById('shop_popup_confirm');");
			} else {
				$("#shop_popup_confirm").find(".btn_qx").attr("onclick","hiddenObjById('shop_popup_confirm');");
			}
			$("#shop_popup_confirm").show();
		}

    	function hiddenObjById(id){
    		$("#"+id).hide();
    		$(".popup_bg").hide(); 		
    	
    	}
    	
	</script>