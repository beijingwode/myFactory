$(document).ready(function() {
	$(".on_checkbox").removeAttr("checked");
	$(".on_goods [type=checkbox]").removeAttr("checked");
	$(".on_checkbox").click(function(){
		if($(".on_checkbox").attr("checked")=="checked")
			$(".on_goods [type=checkbox]").attr("checked","checked");
		else
			$(".on_goods [type=checkbox]").removeAttr("checked");
	});
	
});



/**
 * 批量取消收藏商品
 * @param productId
 */
function canelMore(){
	var ids = [];
	var flag =true;
	$(".on_goods input:checkbox:checked").each(function(){
		ids.push($(this).val());
	})
	if(ids.length<1){
		wode.showBox("错误","请选择要取消关注的商品",{hideBtn:true});
		flag = false;
	}
	if(flag){
		$("#boxCheck").unbind('click');
		wode.showBox("确认","确定取消所选关注？");
		$("#boxCheck").click(function(){
			$.ajax({
				dataType: 'json',
				data:{productIds:ids},
				url:"/canelCollectionProduct",
				success: function(data){
					$(".on_checkbox").removeAttr("checked");
					location.href="/member/personalProduct";
				},
				error:function(){
					wode.showBox("错误","未知错误，请稍候再试",{hideBtn:true});
				}
			});
		});
	}
}

/**
 * 取消收藏商品
 * @param productId
 */
function canelCollectionProduct(productId){
	$("#boxCheck").unbind('click');
	wode.showBox("取消","确定取消关注该商品？");
	$("#boxCheck").click(function(){
		$.ajax({
			dataType: 'json',
			url:"/canelCollectionProduct?productId="+productId,
			success: function(data){
				location.href="/member/personalProduct";
			},
			error:function(){
				wode.showBox("错误","未知错误，请稍候再试",{hideBtn:true});
			}
		});
	});
}
