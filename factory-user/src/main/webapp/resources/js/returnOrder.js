$().ready(function() {
	//提交表单
	$("#createReturnOrder").click(function() {
        var type = $("#type").val();
		var reason = $("#reason").val();
		var returnPrice = $("#returnPrice").val();
		var note = $("#note").val();
		var expressType = $("#expressType").val();
		var expressNo = $("#expressNo").val();
		if(reason == ""){
			prompt("请选择退款原因");
			return;
		}
		if(returnPrice == "" || isNaN(returnPrice) || returnPrice <= 0){
			prompt("请输入退款金额");
			return;
		}
		var price = $("#price").val();
		if(parseFloat(returnPrice) > parseFloat(price)){
			prompt("退款金额不能超过订单金额");
			return;
		}
		if(type == 0 && expressType == ""){
			prompt("请选择物流公司");
			return;
		}
		if(type == 0 && expressNo == ""){
			prompt("请输入物流单号");
			return;
		}
		$("#returnOrderForm").submit();
	});
	
	$("#type").change(function(){
        if($(this).val()==1) {
            $(".logistics").hide();
        } else {
            $(".logistics").show();
        }
    });

	$("#uploadpic1").click(function() {
		$("#avatar1").click();
	});
	
	$("#uploadpic2").click(function() {
		$("#avatar2").click();
	});	
	
	$("#uploadpic3").click(function() {
		$("#avatar3").click();
	});	
	
});

/**
 * 提示信息
 */
function prompt(msage){
	$(".popup_box_title span").html("提示信息");
	$(".box_message li").html(msage);
	$("#boxCancel").hide();
	$("#boxCheck").css("margin-right","-180px");
	$(".box").show();
	$("#boxCheck").click(function(){
		$(".box").hide();
	});
}
/**
 * 图片上传
 * @param id
 */
function uploadImage(fileId,imageId,imggeValueId) {
    var elementIds=[""+fileId]; //flag为id、name属性名
    $.ajaxFileUpload({
        url: '/upload/pic',
        type: 'post',
        secureuri: true, //一般设置为false
        fileElementId: fileId, // 上传文件的id、name属性名
        dataType: 'json', //返回值类型，一般设置为json、application/json
        elementIds: elementIds, //传递参数到服务器
        success: function(data, status){
        	if(data.success){
        		var imgPath = "http://"+data.data[0];
        		//$("#avatarValue").val(imgPath);
        		$("#"+imggeValueId).val(imgPath);
				$("#"+imageId).attr('src',imgPath);
        	}else
        		alert(data.msg);
        },
        error: function(data, status, e){ 
            alert(e);
        }
    });
}

var SysSecond = 1000;
var InterValObj;
$(document).ready(function() {
//	SysSecond = parseInt($("#lastTiime").val());
//	SysSecond = parseInt($("#lastTiime").val());
	var date1=new Date();  //开始时间
	var str=$("#lastTiime").val();
	if(str) {
		var date2=new Date(str.replace(/-/g,"/"));
	//	alert(date2);
	//	alert($("#lastTiime").val());
		SysSecond=date2.getTime()-date1.getTime();  //时间差的毫秒数
		InterValObj = window.setInterval(SetRemainTime, 1000);
	}
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
		var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
		var minutes=Math.floor(leave2/(60*1000));
		//计算相差秒数
		var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
		var seconds=Math.round(leave3/1000);
		
		$("#endTime").html(days + "天" + hours + "小时" + minutes + "分" + seconds + "秒");
	} else {
		window.clearInterval(InterValObj);
//		$(".O_btn1 .a1").css("cursor","default");
	}
}

