
/**
 * 快速跳转
 */
function gotoPage(){
	var pagenum = $("#pagenum").val();
	formSubmit(pagenum);
}

/**
 * 表单提交
 */
function formSubmit(page){
	if(page!=undefined){
		$("#pageNumber").val(page);
	}else{
		$("#pageNumber").val(1);
	}
	$("#sub_form").submit();
}


/**
 * 表单提交
 */
function exportExcel() {
	$("#pageNumber").val(1);
    
    var action = $("#sub_form").attr("action");
    $("#sub_form").attr("action",jsBasePath + "/company/benefit/exportExcel.html");
    $("#sub_form").attr("target","_blank");
    $("#sub_form").submit();
    $("#sub_form").attr("action",action);
    $("#sub_form").attr("target","_self");
}

//提交表单并刷新页面
function refreshHtml(){
	$("#sub_form").submit();
}
/**
 * 重置
 */
function formReset(){
	//$("#sub_form").reset();
	//document.getElementById("sub_form").reset();
	$("#sub_form").find("input[type!='hidden']").val("");
	$("#sub_form").find("select").find("option:first").attr("selected","selected");
}


$(document).ready(function(){
	//加载页面，控制左边的菜单
	$("#ent_giveBenefit").addClass("curr");
	
	//输入框获取光标
	$(".r-input,.r-select,.r-num").live("focus",function(){
		$(this).addClass("p-red");
	});
	//输入框失去光标
	$(".r-input,.r-select,.r-num").live("blur",function(){
		$(this).removeClass("p-red");
	});
	
	//条件筛选(分页查询)
	$("#filter_btn").click(function(){
		
		refreshHtml();
	});
	$("#batch").click(function(){
		
		$("#all-send").show();
	});
	//批量发放
	$('#all-grade').click(function(){
		$('.popup_bg').show();
		$('#all-send').show();
		$('#allsend-close').click(function(){
			$('.popup_bg').hide();
			$('#all-send').hide();	
		})
		$('#allsend-cansel-btn').click(function(){
			$('.popup_bg').hide();
			$('#all-send').hide();	
		})	
		$('#allsend-ture-btn').click(function(){
			ajaxFileUpload();
		})
	});
	
	$('.allsend-suceess-close').click(function(){
		$('.popup_bg').hide();
		$('#allsend-suceess').hide();
		refreshHtml();
	})
	//级别发放按钮
	$("#levelGrant").live("click",function(){
		grade();
	});
	$("#levelGrant").live("keyup",function(event){
		if(event.keyCode==13){
			grade();
		}
	});
});

//###########################add start##############################################
function provide(empId){
	$.ajax({
		url : jsBasePath + '/company/benefit/getEnterpriseUserById.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:{"empId":empId},
		success : function(data) {
			var html = "";
			html+="<div class=\"pop-title\">";
			html+="<span>发放福利</span>";
			html+="<div class=\"add-close-btn\" onclick=\"closePop('provide')\" ><i class=\"add-close-icon\"></i></div>";
			html+="</div>";
			html+="<input type =\"hidden\" id =\"empId\" value ="+data.empId+">"; 
			html+="<input type =\"hidden\" id =\"empName\" value ="+data.name+">";
			html+="<div class=\"pop-cont\">";
			html+="<p class=\"add-tl\">向员工  <b>"+data.name+"</b>  发放福利</p>";
			html+="<ul class=\"change-list\">";
			html+="<li><span>福利额度</span><div class=\"c-r-box\"><input type=\"text\" class=\"r-input\" onkeyup=\"inutNumber(this)\" onafterpaste=\"inutNumber(this)\"  id=\"absTicket\"  placeholder=\"请输入福利额度（1人）\" maxlength=\"10\"></div></li>";
			html+="<li><span>现金额度</span><div class=\"c-r-box\"><input type=\"text\" class=\"r-input\" onkeyup=\"inutNumber(this)\" onafterpaste=\"inutNumber(this)\" id=\"absCash\"  placeholder=\"请输入现金额度（1人）\" maxlength=\"10\" ></div></li>";
			html+="<li><span>福利科目</span><div class=\"c-r-box\"><select><option value=\"1\">生日礼金</option><option value=\"2\">过节费</option><option value=\"0\">其它</option></select></div></li>";
			html+="</ul>";
			html+="<div class=\"add-btn-box\"><a class=\"true-btn\" href=\"javascript:void(0);\" id=\"btnAddBenefit\" onclick=\"addBenefit();\">确认</a>";
			html+="<a class=\"cansel-btn\" onclick=\"closePop('provide')\" href=\"javascript:void(0);\">取消</a></div></div></div>";		
			
 			$("#provide").append(html);
			$("#provide").show();
			$('.popup_bg').show();
			
			goPlaceholder();
	
		}, error : function() {
	    }  
	});
}
//输入验证
function inputVerify(){
	var absTicket=$("#absTicket").val();
	var absCash=$("#absCash").val();
	if(absTicket >0 || absCash > 0){
		$("#absTicket").removeClass("p-red");
		$("#absCash").removeClass("p-red");
	}else{
		$("#absTicket").addClass("p-red");
		$("#absCash").addClass("p-red");
		 return false;  
	}
	return true ;
}
/**
 * 发放福利(单人)
 */
function addBenefit(){
	var selectVal=$(".change-list li .c-r-box select").val();
	
	//校验
	if(!inputVerify()){
		return  ; 
	}
	
	$("#btnAddBenefit").removeAttr("onclick");
 	var absTickets =$('#absTicket').val();
	var absCashs =$('#absCash').val();
	
	//如果福利额度和现金额度没有值的话，给他们赋值为0
	if(absTickets=="请输入福利额度（1人）"){
		absTickets=0;
	}else if(absCashs=="请输入现金额度（1人）"){
		absCashs=0;
	}
	var empIds =$('#empId').val();
	var empNames =$('#empName').val();
 	var jsonData ={'empIds':empIds,
			'absTickets':absTickets,
			'absCashs':absCashs,
			'empNames':empNames,
			'exBenefitType':selectVal,
 		};
	$.ajax({
		url : jsBasePath + '/company/benefit/giveBenefitForOne.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:jsonData,
		success : function(data) {
			if(data.success){
				$("#erro_info").html("发放福利成功！");
			}else{
				$("#erro_info").html(data.msg);
			}
			$("#provide").hide();
			$('#allsend-suceess').show();
			$("#btnAddBenefit").attr("onclick","addBenefit();");
		}, error : function() {
			$("#erro_info").html("发放福利失败！");
			$('#allsend-suceess').show();
			$("#provide").hide();
			$("#btnAddBenefit").attr("onclick","addBenefit();");
	    }   
	});
}
//############################发放福利 end#############################################
//############################级别发放 statr#############################################
function grade(){
	$.ajax({
		url : jsBasePath + '/company/benefit/toGiveBenefitForLevel.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
		success : function(data) {
			$("#grade").empty();
			var html = "";
			html+="<div class=\"pop-title\"><span>级别发放福利额度</span>";
			html+="<div class=\"add-close-btn\" onclick=\"closePop('grade')\"><i class=\"add-close-icon\"></i></div></div>";
			html+="<div class=\"pop-cont\"><ul class=\"grade-list\">";
			for(var i = 0 ; i< data.list.length ; i++ ){
				//每个级别用户list
				var cnt =data.list[i];
				var h ="";
				if(cnt ==0){
					h="readonly=\"readonly\"";
				}
				html+="<li><span>福利级别"+(i+1)+"</span><em>"+cnt+"人</em><input class=\"ticket\" type=\"text\" "+h+" pnum=\""+cnt+"\" onkeyup=\"amount('ticket',this)\"  onafterpaste=\"inutNumber(this)\" maxlength=\"10\" placeholder=\"请输入福利额度（"+cnt+"人）\">";
				html+="<input class=\"cash\" type=\"text\" name=\"\" "+h+" onkeyup=\"amount('cash',this)\" onafterpaste=\"inutNumber(this)\" pnum=\""+cnt+"\" maxlength=\"10\"   placeholder=\"请输入现金额度（"+cnt+"人）\"></li>";
			}
			
			html+="</ul>";
			html+="<div class=\"grade-box\">";
			html+="<div class=\"grade-row\">";
			html+="<span>可发放福利额度：<em id =\"can_sum_ticket\" sum=\""+data.ticketSum+"\" >"+data.ticketSum+"元</em></span>";
			html+="<span>本次发放福利额度：<em id=\"sum_ticket\" sum=\"0\">0元</em></span></div>";
			html+="<div class=\"grade-row\">";
			html+="<span>可发放现金额度：<em id =\"can_sum_cash\" sum=\""+data.cashSum+"\" >"+data.cashSum+"元</em></span>";
			html+="<span>本次发放现金额度：<em id=\"sum_cash\" sum=\"0\" >0元</em></span></div></div>";
			html+="<div class=\"add-btn-box\"><div class=\"chekLabel\"><input type=\"checkbox\" id=\"checkbox1\" /><label for=\"checkbox1\">过节费</label></div><a class=\"true-btn\" id=\"levelGive_Sub\" href=\"javascript:void(0);\" onclick=\"gradeBenefit()\" >确认</a><a class=\"cansel-btn\" onclick=\"closePop('grade')\" href=\"javascript:void(0);\">取消</a></div>";
			html+="</div></div>";
		
			$("#grade").append(html);
			$("#grade").show();
			$('.popup_bg').show();
			
			goPlaceholder();
			
		}, error : function() {
	    }  
	});
}

//计算本次发放合计金额
function amount(id,e){
	inutNumber(e);
	var sum =0;
	var inputs =  $("."+id+"");
	for (var i = 0; i < inputs.length; i++) {
		//额度
		var value = $(inputs[i]).attr("value");
		if(value.length>0){
			if(value.substring(0,1)!="请"){
				//人数
				var pnum=$(inputs[i]).attr("pnum");
				/* var value=$(inputs[i]).attr("value"); */
				sum+=pnum*value;
			}
		}
	}
	//可发放  福利总额
	var can_tic = $("#can_sum_ticket").attr("sum");
	//本次发放  福利金额
	var tic = $("#sum_ticket").attr("sum");
	//可发放  现金
	var can_cash = $("#can_sum_cash").attr("sum");
	//本次发放  现金
	var cash = $("#sum_cash").attr("sum");
	//内购券  
	if(id=="ticket"){
		//sum是输入的最大额度    输入额度大于可发放的福利额度
		if(parseInt(sum)>parseInt(can_tic)){
			$("#levelGive_Sub").attr("class","cansel-btn1");
		}else{
			//本次发放的现金大于可发放的现金 确定按钮变灰色
			if(parseInt(cash)>parseInt(can_cash)){
				$("#levelGive_Sub").attr("class","cansel-btn1");
			}else{
				$("#levelGive_Sub").attr("class","true-btn");
			}
		}
	//现金
	}else if(id=="cash"){
		//现金总额  输入现金额度大于可方法的现金额度
		if(parseInt(sum)>parseInt(can_cash)){
			$("#levelGive_Sub").attr("class","cansel-btn1");
		}else{
			//已发放的福利额度大于可发放的福利额度
			if(parseInt(tic)>parseInt(can_tic)){
				$("#levelGive_Sub").attr("class","cansel-btn1");
			}else{
				$("#levelGive_Sub").attr("class","true-btn");
			}
		}
	}
	$("#sum_"+id+"").html(sum+"元");
	$("#sum_"+id+"").attr("sum",sum);
}
/**
 * 级别发放福利
 */
function gradeBenefit(){
	var checked1=$("#checkbox1").attr("checked");
 	
	$("#levelGive_Sub").removeAttr("onclick");
	if($("#sum_ticket").attr("sum") ==0 && $("#sum_cash").attr("sum") == 0){
		$.each($(".ticket"),function(){
			if(!$(this).attr("readonly")){
				$(this).addClass("p-red");;
			};
		});
		$.each($(".cash"),function(){
			if(!$(this).attr("readonly")){
				$(this).addClass("p-red");;
			};
		});
		$("#levelGive_Sub").attr("onclick","gradeBenefit()");
		return false ;
	}else{
		$.each($(".ticket"),function(){
			if(!$(this).attr("readonly")){
				$(this).removeClass("p-red");;
			};
		});
		$.each($(".cash"),function(){
			if(!$(this).attr("readonly")){
				$(this).removeClass("p-red");;
			};
		});
	}
	
	if(($("#can_sum_ticket").attr("sum") - $("#sum_ticket").attr("sum"))<0){
		//alert("剩余福利额度不足!");
		$("#levelGive_Sub").attr("onclick","gradeBenefit()");
		return false ;
	}
	if(($("#can_sum_cash").attr("sum") - $("#sum_cash").attr("sum"))<0){
		//alert("剩余现金额度不足!");
		$("#levelGive_Sub").attr("onclick","gradeBenefit()");
		return false ;
	}
	var absTickets ="";//每个员工发放内购券数组
	var absCashs ="";//每个员工发放现金数组

 	$.each($(".ticket"),function(i){
		var t = $(".ticket").eq(i);
		var v = t.val().trim();
		if(v=="") {
			v="0";
		}else if(v.length>1){
			if(v.substring(0,1)=="请"){
				v="0";
			}
		}
		
		absTickets+=v+"#";
	}); 
 	$.each($(".cash"),function(i){
		var t = $(".cash").eq(i);
		var v = t.val().trim();
		if(v=="") {
			v="0";
		}else if(v.length>1){
			if(v.substring(0,1)=="请"){
				v="0";
			}
		}
		absCashs+=v+"#";
	}); 
 	
	var ticketSum =$("#sum_ticket").attr("sum") ;
	var cashSum =$("#sum_cash").attr("sum") ;
 	var jsonData ={
 			'absTickets':absTickets.substring(0,absTickets.length-1),//截取最后的“#”
			'absCashs':absCashs.substring(0,absCashs.length-1),//截取最后的“#”
			'ticketSum':ticketSum,
			'cashSum':cashSum,
			'exBenefitType':checked1?2:0,
 			};
	$.ajax({
		url : jsBasePath + '/company/benefit/giveBenefit.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:jsonData,
		success : function(data) {
			if(data.success){
				$("#erro_info").html("发放福利成功！");
			}else{
        		$("#erro_info").html(data.msg);
			}
			$("#grade").hide();
			$('#allsend-suceess').show();
			$("#levelGive_Sub").attr("onclick","gradeBenefit()");
		}, error : function() {
    		$("#erro_info").html("发放福利失败！");
			$('#allsend-suceess').show();
			$("#levelGive_Sub").attr("onclick","gradeBenefit()");
	    }  
	});
}

//############################级别发放 end #############################################
//###########################回收福利 start##############################################
function recycle(empId){
	$.ajax({
		url : jsBasePath + '/company/benefit/getEnterpriseUserById.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:{"empId":empId},
		success : function(data) {
			var html = "";
			html+="<div class=\"pop-title\">";
			html+="<span>回收福利</span>";
			html+="<div class=\"add-close-btn\" onclick=\"closePop('recycle')\" ><i class=\"add-close-icon\"></i></div>";
			html+="</div>";
			html+="<input type =\"hidden\" id =\"empId\" value ="+data.empId+">"; 
			html+="<input type =\"hidden\" id =\"empName\" value ="+data.name+">";
			html+="<div class=\"pop-cont\">";
			html+="<p class=\"add-tl\">向员工  <b>"+data.name+"</b>  回收福利</p>";
			html+="<ul class=\"change-list\">";
			html+="<li><span>福利额度</span><div class=\"c-r-box\"><input value ="+data.giveTicketSason+"  type=\"text\"  id=\"absTicket\"  disabled=\"disabled\"></div></li>";
			html+="<li><span>现金额度</span><div class=\"c-r-box\"><input type=\"text\" value ="+data.giveCashSason+" id=\"absCash\" disabled=\"disabled\" ></div></li>";
			html+="</ul>";
			html+="<div class=\"add-btn-box\"><a class=\"true-btn\" href=\"javascript:void(0);\" onclick=\"delBenefit();\">确认</a>";
			html+="<a class=\"cansel-btn\" onclick=\"closePop('recycle')\" href=\"javascript:void(0);\">取消</a></div></div></div>";		
		
			$("#recycle").append(html);
			$("#recycle").show();
			$('.popup_bg').show();
			
		}, error : function() {
	    }  
	});
}

//关闭弹窗
function closePop(id){
	$('.popup_bg').hide();
	$('#'+id+'').hide();
	$('#'+id+'').empty();

}
//回收福利
function delBenefit(){
	var absTicket =$('#absTicket').val();
	var absCash =$('#absCash').val();
	var empId =$('#empId').val();
	var empName =$('#empName').val();
	var jsonData ={'empIds':empId,
			'absTickets':absTicket,
			'absCashs':absCash,
			'empNames':empName};
	$.ajax({
		url : jsBasePath + '/company/benefit/recycleBenefit.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
	    async: true,
	    data:jsonData,
		success : function(data) {
			if(data.success){
				$("#erro_info").html("回收福利成功！");
			}else{
				$("#erro_info").html(data.msg);
			}
			$("#recycle").hide();
			$('#allsend-suceess').show();
		}, error : function() {
			$("#erro_info").html("回收福利失败！");
			$('#allsend-suceess').show();
	    }  
	});
}

 
//############################回收福利 end#############################################

function ajaxFileUpload() {
        $.ajaxFileUpload({
            url: jsBasePath + '/company/benefit/allSend.json', 
            type: 'post',
            secureuri: false, //是否启用安全提交,默认为false 
            fileElementId: 'file', //文件选择框的id属性
            dataType: 'json', //返回值类型，一般设置为json、application/json
            success: function(data, status){
            	//if(data.success) {
                	$('#all-send').hide();
                	$('.popup_bg').show();
               		$("#erro_info").empty();
               		$("#erro_info").html(data.msg);
 
            	//}
    
            	/* if(!data.success){
            		$("#erro_info").html(data.msg);
            		$("#erro_info1").show();
            	} */
            	$('#allsend-suceess').show();
            	$('.allsend-suceess-close').click(function(){
            		$('.popup_bg').hide();
            		$('#allsend-suceess').hide();
            		if(data.success){
            			//刷新本页
            			location.reload();
            		}
            	})
            },
            error: function(data, status, e){ 
            	$('#all-send').hide();
            	$('.popup_bg').show();
            	$('#allsend-suceess').show();
            	$('#allsend-suceess-close').click(function(){
            		$('.popup_bg').hide();
            		$('#allsend-suceess').hide();	
            	})
            }
        });
        //return false;
    }
    
//下载模板
function down(){
	$.ajax({
		url : jsBasePath + '/company/benefit/downloadTemlplate.json',
		type : "POST",
		dataType: "json",  //返回json格式的数据  
		success : function(data) {
			if(data==1){
				location.href=jsBasePath + "/template/batchGiveBenefit.xlsx?r="+Math.random();
			}else{
				alert("下载模板失败！");
			}
		}, error : function() {
	    }  
	});
}
