//uid = GetUidCookie();
$(function(){
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	//点击显示原因
	init();
	$(".thickdiv").click(function(e){
		$(".thickdiv").hide();
		$(".smallPopup").hide();
	});
	/*$(".logistics .li1").click(function(e){
		$(".thickdiv").show();
		$(".smallPopup").show();
	});*/
})
var isApplay=false;//是否为申请
var flag = 0;
function init(){
	$.ajax({
		url :jsBasePath+'invoice/getInvoice.user?uid='+uid+'&suborderid='+subOrderId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
		async: false,
		cache:false,
		success : function(data) {
			if (data.success) {
				var result = data.data;
				var suborder = result.subOrder;
				if(suborder && suborder!='' && suborder.invoiceStatus==0){
					isApplay =true;
				}
				var shippingAddressList = result.shippingAddressList;
				var html='';
				if(shippingAddressList && shippingAddressList!=''){
					html+='<div class="region">选择收票地址</div>';
					for (var i = 0; i < shippingAddressList.length; i++) {
						html +='<div class="share odb">';
						html +='<a onclick="selAddress(this)">'+shippingAddressList[i].provinceName+','+shippingAddressList[i].cityName+' '+shippingAddressList[i].areaName+','+shippingAddressList[i].address+'('+shippingAddressList[i].name+'收)'+shippingAddressList[i].phone+'</a>';
						html +='</div>';
					}
				}else{
					html+='<div class="region">选择收票地址</div>';
					html +='<div class="share odb">';
					html +='<a>没有收货地址，请去添加收货地址</a>';
					html +='</div>';
				}
				$(".smallPopup").html(html);
				if(suborder.invoiceStatus==0 || suborder.invoiceStatus==1){
					$("#address").attr("onclick","showAddressList()");
				}
				if(isApplay){
					$("#progressPic").show();
					//$("return_hit").html("<p>订单编号："+subOrderId+"</p>"+"<p>实付金额：￥"+parseFloat(suborder.realPrice).toFixed(2)+"</p>");
					$("#address").html(suborder.address+' ('+suborder.name+'收) '+suborder.mobile);
					$("#suborderPrice").html("实付金额：￥"+parseFloat(suborder.realPrice).toFixed(2));
					$("#suborderId").html("订单编号："+subOrderId);
					$(".btns").html('<a href="javascript:go2Submit();">提交</a>');
					$(".btns").show();
				}else{
					var invoiceApply = result.invoiceApply;
					if(invoiceApply && invoiceApply!=''){
						$("#invoiceApply_id").val(invoiceApply.id);
						$("#progressPic").show();
						$("#status").html("开票状态：已申请");
						$("#address").html(invoiceApply.address);
						if(invoiceApply.type==0){
							$("#type").html('<option value="0" selected="selected">个人</option><option value="1">单位</option>');
						}else{
							$("#type").html('<option value="0">个人</option><option value="1" selected="selected">单位</option>');
							//$("#title").html(invoiceApply.title);
							$("#title").val(invoiceApply.title);
							$("#title").show();
							$(".logistics .li3").show();
							$("#taxpayerNumber").val(invoiceApply.taxpayerNumber);
							$("#taxpayerNumber").show();
							$(".logistics .li7").show();
							if(invoiceApply.billType==0){
								$("#billType").html('<option value="0" selected="selected">普通发票</option><option value="1">专用发票</option>');
							}else{
								$("#billType").html('<option value="0" >普通发票</option><option value="1" selected="selected">专用发票</option>');
								$("#taxpayerNumber").val(invoiceApply.taxpayerNumber);
								$("#taxpayerNumber").show();
								$("#registerAddress").val(invoiceApply.registerAddress);
								$("#registerAddress").show();
								$("#registerPhone").val(invoiceApply.registerPhone);
								$("#registerPhone").show();
								$("#openingBan").val(invoiceApply.openingBan);
								$("#openingBan").show();
								$("#openingBanNumber").val(invoiceApply.openingBanNumber);
								$("#openingBanNumber").show();
								$(".logistics .li8").show();
							}
							flag = 1;
						}
						$("#suborder_id").html(invoiceApply.suborderid)
						$("#note").val(invoiceApply.note);
						$("#invoiceApply_createTime").html(getDate(invoiceApply.createtime));
						$(".logistics .li5").show();
						$(".logistics .li4").show();
						$(".btns").html('<a href="javascript:go2Submit();">修改申请</a>');
						$(".btns").show();
					}
					var issuedInvoice = result.issuedInvoice;
					if(issuedInvoice && issuedInvoice!=''){
						$(".top").html('<h1><a href="javascript:close();" class="aleft"></a>发票详情</h1>');
						$("title").html("发票详情");
						$("#completePic").show();
						$("#progressPic").hide();
						$(".logistics").hide();
						$("#status").html("开票状态：发票已开");
						if(issuedInvoice.type==0){
							$("#iss_type").html("电子增值税普通发票");
						}else if(issuedInvoice.type==1){
							$("#iss_type").html("电子增值税专用发票");
						}else if(issuedInvoice.type==2){
							$("#iss_type").html("纸质发票");
						}
						$("#iss_seller").html(issuedInvoice.seller);
						$("#iss_title").html(issuedInvoice.title);
						$("#iss_invoice").html(issuedInvoice.invoice);
						$("#iss_price").html("￥"+parseFloat(issuedInvoice.price).toFixed(2));
						$("#iss_createTime").html(getDate(issuedInvoice.createtime));
						var electronicInvoice = issuedInvoice.electronicInvoice;
						var paperInvoice = issuedInvoice.paperInvoice;
						if(electronicInvoice && electronicInvoice!=null && electronicInvoice!=''){
							$("#iss_link").attr("href",electronicInvoice);
							$("#iss_link").html("查看");
						}else{
							if(paperInvoice && paperInvoice!=null && paperInvoice!=""){
								$("#iss_link").attr("href",paperInvoice);
								$("#iss_link").html("查看");
							}
						}
						$(".btns").hide();
						$(".details_refund").show();
					}else{
						$(".details_refund").hide();
					}
				}
			}else{
				showInfoBox(data.msg);
			}
		}
	});
}

function chooseType(){
	if($("#type").val()==0){//个人
		$(".li3").hide();
		$(".li7").hide();
		$(".li8").hide();
		if(flag == 0){
			$(".li3").find("input").val("");
			$(".li7").find("input").val("");
			$(".li8").find("input").val("");
			$(".chooseType").val(0);
		}
		
		
	}else{
		$(".li3").show();
		$(".li7").show();
		if($("#billType").val()==1){
			$(".li8").show();
		}
	}
}
function billType(){
	if($("#billType").val()==0){//普通发票
		$(".li8").hide();
		if(flag == 0){
			$(".li8").find("input").val("");
		}
	}else{
		$(".li8").show();
	}
}

function go2Submit(){
	if(uid=="") return;
	var type=$("#type").val();
	var mustFlag = true;
	var title = '';
	var address=$("#address").html();
	var note=$("#note").val();
	var billType = '';
	var taxpayerNumber = '';
	var registerAddress = '';
	var registerPhone = '';
	var openingBan = '';
	var openingBanNumber = '';
	//if(isApplay){//退货退款
		if(typeof(type)==undefined || $.trim(type)==''){
			showInfoBox("请选择抬头");
			mustFlag=false;
		}else{
			if(type==1){//单位
				billType=$("#billType").val();
				title=$("#title").val();
				taxpayerNumber=$("#taxpayerNumber").val();
				if(billType == 1){
					registerAddress=$("#registerAddress").val();
					registerPhone=$("#registerPhone").val();
					openingBan=$("#openingBan").val();
					openingBanNumber=$("#openingBanNumber").val();
					if(typeof(openingBanNumber)==undefined ||$.trim(openingBanNumber)==''){
						showInfoBox("请填写开户行账号");
						mustFlag=false;
					}
					if(typeof(openingBan)==undefined ||$.trim(openingBan)==''){
						showInfoBox("请填写开户行名称");
						mustFlag=false;
					}
					if(typeof(registerPhone)==undefined ||$.trim(registerPhone)==''){
						showInfoBox("请填写注册电话");
						mustFlag=false;
					}
					if(typeof(registerAddress)==undefined ||$.trim(registerAddress)==''){
						showInfoBox("请填写注册地址");
						mustFlag=false;
					}
				}
				if(typeof(taxpayerNumber)==undefined ||$.trim(taxpayerNumber)==''){
					showInfoBox("请填写纳税人识别号");
					mustFlag=false;
				}
				if(typeof(title)==undefined ||$.trim(title)==''){
					showInfoBox("请填写单位名称");
					mustFlag=false;
				}
			}else{
				title="个人";
			}
		}
	//}
	if(subOrderId== ""){
		showInfoBox("缺少订单id");
		mustFlag=false;
	}
	if(typeof(address)==undefined ||$.trim(address)==''){
		showInfoBox("请选择地址");
		mustFlag=false;
	}
	if(mustFlag){
		$.ajax({
			type: "POST",
			url: jsBasePath+"invoice/addInvoice.user?uid="+uid+"&suborderid="+subOrderId+"&type="+type
			+"&title="+title+"&address="+address+"&note="+note+"&id="+$("#invoiceApply_id").val()+"&billType="+billType
			+"&taxpayerNumber="+taxpayerNumber+"&registerAddress="+registerAddress+"&registerPhone="+registerPhone
			+"&openingBan="+openingBan+"&openingBanNumber="+openingBanNumber,
			dataType: "json",  //返回json格式的数据  
			async: false,
			cache:false,
			success: function(data){
				if(data.success){
					showInfoBox("提交成功")
					setTimeout("refresh()", 1500);
				}
			},
		})
	}
}

/**
 * 刷新页面
 * @returns
 */
function refresh(){
	location.reload();
}

function getDate(createTime){
	var tt=new Date(parseInt(createTime))
	Y = tt.getFullYear() + '-';
	M = (tt.getMonth()+1 < 10 ? '0'+(tt.getMonth()+1) : tt.getMonth()+1) + '-';
	D = tt.getDate() + ' ';
	h = tt.getHours() + ':';
	m = (tt.getMinutes()<10 ? '0'+(tt.getMinutes()) :tt.getMinutes())+ ':';
	s = (tt.getSeconds()<10 ? '0'+(tt.getSeconds()) :tt.getSeconds());
    return   Y+M+D+h+m+s;     
}
function selAddress(that){
	$("#address").html(that.innerHTML);
	$(".thickdiv").hide();
	$(".smallPopup").hide();
}
function showAddressList(){
	$(".thickdiv").show();
	$(".smallPopup").show();
}