
$(document).ready(function(){
	//加载页面，控制左边的菜单
	$("#ent_apply").addClass("curr");
	$("#apply").click(apply());
	$('#applay-close').click(function(){
		$('.popup_bg').hide();
		$('#applay_pop').hide();	
		refreshHtml()
	});
 	$('.ture-btn').click(function(){
		$('.popup_bg').hide();
		$('#applay_pop').hide();	
		refreshHtml()
	});
});


function apply(){
	$("#apply").click(function(){
		$.ajax({
			url : jsBasePath + '/company/benefit/applyBenefit.json',
			type : "POST",
			dataType: "json",  //返回json格式的数据  
		    async: true,
		    data:"",
			success : function(data) {
				if (data == 1) {
					//申请成功
					$('.popup_bg').show();
					$('#applay_pop').show();
				} else {
					//申请失败
				}
		
			}, error : function() {
		    }  
		});
	});

}
function refreshHtml(){
	window.location.reload();
}
