var SysSecond = 1000;
var InterValObj;
$(document).ready(function() {
	var status = $("#orderStatus").val();
	if(status == 0){//下单待付款时间倒计时
		var date1=new Date();  //开始时间
		var str=$("#createTime").val();
		
		SysSecond=(parseInt(str)+1000*3600*24)-date1.getTime();  //时间差的毫秒数
		InterValObj = window.setInterval(SetRemainTime, 1000);
	}else if (status == 2){//待确认收货倒计时
		var date1=new Date();  //开始时间
		var str=$("#lasttakeTime").val();
		
		SysSecond=parseInt(str)-date1.getTime(); //时间差的毫秒数
		InterValObj = window.setInterval(SetRemainTime, 1000);
	}
	
	/**
	 * 取消订单
	 */
	$("#cancel").click(function(){
		var sorderid = $(this).attr("sorderid");

		wode.showBox("取消订单确认","请选择取消理由",{"longContent":"<select class='o-select-input wor210'>"+$(".cancel-select").html()+"</select>"});
		
		$("#boxCheck").click(function(){
			location.href="/member/cancelOrder?subOrderId="+sorderid+"&closeReason="+$(".popup_box_cont select option:selected").text();
		});
	})
});

function SetRemainTime() {
	if (SysSecond > 1000) {
		SysSecond = SysSecond - 1000;
		//计算出相差天数
		var days=Math.floor(SysSecond/(24*3600*1000));

		//计算出小时数
		var leave1=SysSecond%(24*3600*1000);    //计算天数后剩余的毫秒数
		var hours=Math.floor(leave1/(3600*1000));
		//计算相差分钟数
		var leave2=leave1%(3600*1000) ;       //计算小时数后剩余的毫秒数
		var minutes=Math.floor(leave2/(60*1000));
		//计算相差秒数
		var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
		var seconds=Math.round(leave3/1000);
		if(days == 0){
			$("#endTime").html(hours + "小时" + minutes + "分" + seconds + "秒");
		}else{
			$("#endTime").html(days + "天" + hours + "小时" + minutes + "分" + seconds + "秒");
		}
	} else {
		window.clearInterval(InterValObj);
		$("#endTime").html("0小时0分0秒");
		var status = $("#orderStatus").val();
		if(status == 0){//下单待付款时间倒计时
			$(".O_btn1 .a1").remove();
			location.href="/member/cancelOrder?subOrderId="+$("#subOrderId").val()+"&closeReason=超时系统自动取消";
		}
	}
}

