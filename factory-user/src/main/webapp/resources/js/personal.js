
var pageSize = 2;//每页显示条数
var result = true;//提交验证前标识
var timeout = 4000;//错误信息展示时间（4秒）
$(document).ready(function() {
	/**
	 * 修改绑定邮箱
	 */
	$("#changeEmail").click(function(){
		$("#loading").removeAttr("class");
		$("#loading").attr("class","loading_img");
		$("#loading").show();
		$.ajax({
		    dataType: 'json',
		    url:"/personal/changeBindEmail",
		    success: function(data){
		    	$("#loading").hide();
		    	$("#sendResult").html(data.msg);
				$("#sendResult").fadeIn("slow");
				setTimeout("display()",timeout);
		    },
		});
	});
	
	/**
	 * 取消收藏店铺
	 * @param shopId
	 */
	$(".onstore_info").each(function(i){
		$(".onstore_info:eq("+i+") span:eq(1) a").click(function(){
			$("#boxCheck").unbind('click');
			var shopId = $(this).attr("id");
			wode.showBox("确认","取消收藏该店铺？");
			$("#boxCheck").click(function(){
				$.ajax({
					dataType: 'json',
					url:"/canelCollectionShop?shopId="+shopId,
					success: function(data){
						if(data.success){
							location.href="/member/personalStore";
						}else{
							wode.showBox("错误",data.msg,{"hideBtn":true});
						}
					 },
				});
			});
		});
	});
});

function display(){
	$("#sendResult").fadeOut("slow");
}