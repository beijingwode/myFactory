<%@ page language="java" pageEncoding="utf-8"%>

<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/box.css">
<div class="popup_bg"></div>
<div class="sort_popup" style="z-index:999999;" id="sort_popup2">
	<div class="popup_title commbox_title">
    	<span>提示</span>
        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close" onclick="boxHidden();"></label>
        
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
	<script type="text/javascript">
	
		function showInfoBox(msg, thenDo){
			$(".box_msg").html(msg);
			$(".popup_bg").show();
			$("#sort_popup2").show();
			if(thenDo){
				$(".chmarbtn a").click(function(){
					$('.popup_bg').hide();
					$('#sort_popup2').hide();
					thenDo();
				});
				$('.commbox_title label').click(function(){
					$('.popup_bg').hide();
					$('#sort_popup2').hide();
					thenDo();
				});
			}
		}
		
		function boxHidden(){
			$('.popup_bg').hide();
			$('#sort_popup2').hide();
		}
		
 		$(function(){
			$(".chmarbtn a").click(function(){
				boxHidden();	
			});
			$('.commbox_title label').click(function(){
				boxHidden();	
			})
		}) 
	</script>