$(document).ready(function() {
	/**
	 * 删除订单确认
	 */
	$(".deleteOrder").click(function(){
		var sorderid = $(this).attr("soid");
		
		wode.showBox("删除订单确认","你确认删除订单吗？");
		
		$("#boxCheck").click(function(){
			location.href="/member/deleteOrder?subOrderId="+sorderid;
		});
	});
	
	/**
	 * 取消订单弹窗
	 */
	$(".cancelOrder").click(function(){
		var sorderid = $(this).attr("soid");
		$(".popup_box_cont select option:eq(0)").attr("selected","selected");		
		
		wode.showBox("取消订单确认","请选择取消理由",{"longContent":"<select class='o-select-input wor210'>"+$(".cancel-select").html()+"</select>"});
		
		/**
		 * 订单取消确认
		 */
		$("#boxCheck").click(function(){
			$.ajax({
	            type: "GET",
	            url: "/order/orderStatus",
	            data:{"subOrderId":sorderid},
	            success: function (data) {
	            	var status = data.data;
	                if (status==0||(status=1&&status!=2)) {//未支付，已支付未发货可以取消订单
	                	location.href="/member/cancelOrder?subOrderId="+sorderid+"&closeReason="+$(".popup_box_cont select option:selected").text();
	                } else if(status==2){
	                	wode.showBox("订单信息","您的订单已发货");
	                	$("#boxCheck").click(function(){
	                		$(".box").hide();
	                	});
	                }else{
	                	wode.showBox("订单信息","您的订单状态有误,请刷新");
	                	$("#boxCheck").click(function(){
	                		$(".box").hide();
	                	});
	                }
	            }
	        });
			
		});
	});
	/**
	 * 立即付款
	 * */
	$(".payment").click(function(){
		var sorderid = $(this).attr("soid");
		$.ajax({
            type: "GET",
            url: "/order/orderStatus",
            data:{"subOrderId":sorderid},
            success: function (data) {
            	var status = data.data;
                if (status==0) {//未支付
                	location.href="/payment/pay?subOrderId="+sorderid;
                	
                } else if(status==1){//已支付
                	wode.showBox("订单信息","您的订单已付款");
                	$("#boxCheck").click(function(){
                		$(".box").hide();
                	});
                }else{
                	wode.showBox("订单信息","您的订单状态有误,请刷新");
                	$("#boxCheck").click(function(){
                		$(".box").hide();
                	});
                }
            }
        });
	});
	/**
	 * 催促发货
	 */
	$(".canUrgedDelivery").click(function () {
        var soid = $(this).attr("sorderid");
        $.ajax({
            type: "GET",
            url: "/member/urgedDelivery/" + soid,
            success: function (data) {
                if (data.success) {
                    $("#" + soid + "_act_msg").html("催促发货成功");
                    $(".canUrgedDelivery[sorderid='" + soid + "']").remove();
                } else {
                    $("#" + soid + "_act_msg").html("催促失败,您可以重新登录再试!");
                }
            }
        });
    })
	/**
	 * 子单统计数据居中设置
	 */
	$(".Me-order-con li").each(function(){
		$(this).find(".orderRight").attr("style","margin-top:"+$(this).find(".orderLeft>div").length*40+"px;")
	});
	/**
	 * 居中设置
	 */
	$(".s4").each(function(){
		if($(this).find("p").length==2){
			$(this).attr("style","margin-top:10px;");
		}
		if($(this).find("p").length>3){
			$(this).attr("style","margin-top:-10px;");
		}
	});
})