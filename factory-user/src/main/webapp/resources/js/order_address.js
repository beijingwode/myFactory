$().ready(function() {
	var $province = $("#province");
	var $city = $("#city");
	var $area = $("#area");
	var $saveAddress = $("#saveAddress");
	var $shippingAddressId= $("#shippingAddressId");
	var $editAddressDiv = $(".alter_address");  
	var $showAddress= $("#showAddress");
	var $newAddressDiv = $(".address_info_wrap");  
	var $useNewAddress= $("#useNewAddress");

	var $newInvoiceDiv = $(".edit_bill_wrap");  
	var $editInvoiceDiv= $(".edit_bill");
	
	var $t_name = $("input[name='name']");
	var $address = $("input[name='address']");
	var $mobile = $("input[name='mobile']");
	var $aid = $("input[name='aid']");
    var $provinceName = $("input[name='provinceName']");
    var $cityName = $("input[name='cityName']");
    var $areaName = $("input[name='areaName']");
	
	var $delAddress = $("a[name='delAddress']");
	
	var editAddressFlag = false;//常用地址编辑标识
	var $editAddressRadio=$('input[name="addressRadio"]');
	var $editAddress = $("a[name='editAddress']");
	
	var $editAddress = $("a[name='editAddress']");
	
	//---------------------------------------------------------------
	//代收点复选框选中
	$("input[name=collectingPointCheck]").live("click",function(){
		var check = $(this).attr("checked");
		//未选中状态
		if(check==undefined){
			//常用地址单选框选中
			$("input[name=commonAddress]").attr("checked","checked");
		}else{
			$("input[name=commonAddress]").removeAttr("checked");
		}
	});
	
	//常用收货地址单选框
	$("input[name=commonAddress]").live("click",function(){
		$("input[name=collectingPointCheck]").removeAttr("checked");
	});
	//---------------------------------------------------------------
	$('#t_name').focus(
			function (){
				$(this).parent().find(".hinterror").text("");
			}
	); 
	$('#t_mobile').focus(
			function (){
				$(this).parent().find(".hinterror").text("");
			}
	); 	
	$('#t_address').focus(
			function (){
				$(this).parent().find(".hinterror").text("");
			}
	);
	
	$province.change(function(){
		getArea(2,$province.val());//加载市
		//$area.html("<option value=0>请选择...</option>");
		$(this).parent().parent().find(".hinterror").text("");
	});

	$city.change(function(){
		getArea(3,$city.val());//加载区
		$(this).parent().parent().find(".hinterror").text("");
	});
	
	$area.change(function(){
		$(this).parent().parent().find(".hinterror").text("");
	});
	
	
	getArea(1,null);//加载省
	//显示修改地址DIV
	$showAddress.click(function() {
		var display = $editAddressDiv.css("display");
		if(display == "none"){
			$editAddressDiv.show(500);
			$("#addressInfomation").hide();
		}else if(dispaly = "block"){
			$editAddressDiv.hide(500);
		}
	});
	
	//选择发票地址 radio
	$editAddressRadio.change(editAddressRadio);
	//显示使用新地址DIV
	$useNewAddress.unbind("change").change(useNewAddress);
	
	$editAddress.click(editAddress);
	
	$saveAddress.click(function() {
		var $addressRadio  = $('input[name="addressRadio"]:checked');
		if(typeof($addressRadio.val()) =='undefined' && $shippingAddressId.val()== -1){
			wode.showBox("错误","请选择收货地址",{hideBtn:true});
			return;
		}
		var number = 10;
		if($shippingAddressId.val() == ""){
			number = 9;
		}
		if($(".newaddress").prevAll().length > number){
			wode.showBox("错误","最多保存9个收货地址",{hideBtn:true});
			return;
		}
		var value = $addressRadio.val();
		
		// 自提
		if(value == "1") {
			$("#selfDelivery").val("1");
			if($("#match").val() != "match"){
				$("#addressP").html("<input class=\"selradio\" type=\"radio\" name=\"commonAddress\" value=\"\" checked=\"checked\"><span>自提（自己联系商家取货）</span>");
			}else{
				$("#addressP").html("<input class=\"selradio\" type=\"radio\" name=\"commonAddress\" value=\"\" checked=\"checked\"><span>公司统一收货（免运费）</span>");
			}
			$t_name.val("");
			$address.val("")
			$mobile.val("");
			$editAddressDiv.hide(500);
			$("#addressP").show();
			$("#addressInfomation").show();
			calculateTotalFreight();
			return;
		} else {
			$("#selfDelivery").val("0");
		}
		var falg = editAddressFlag;
		if(value == 0 || falg == true){//使用新地址
			var shippingAddressId = $shippingAddressId.val();
			var aid=$area.val();
			var address=$.trim($("#t_address").val());
			var name=$.trim($("#t_name").val());
			var phone=$.trim($("#t_mobile").val());
			var provinceCode = $province.val();
			var cityCode = $city.val();
			var areaCode = $area.val();
			if(name == ""){
				$("#t_name").parent().find(".hinterror").text("收货人不能为空");
				return;
			}
			if(!wode.phone.test(phone)){
				$("#t_mobile").parent().find(".hinterror").text("手机号填写不正确");
				return;
			}
			if(provinceCode == 0){
				$province.parent().parent().find(".hinterror").text("请选择省");
				return;
			}
			if(cityCode == 0){
				$city.parent().parent().find(".hinterror").text("请选择市");
				return;
			}
			if(areaCode == 0){
				$area.parent().parent().find(".hinterror").text("请选择区");
				return;
			}
			if(address == ""){
				$("#t_address").parent().find(".hinterror").text("请输入地址");
				return;
			}
			var provinceName=$province.find("option:selected").text();
			var cityName=$city.find("option:selected").text();
			var areaName=$area.find("option:selected").text();
			
			$.ajax({
				url : "/order/saveShippingAddress",
				type : "POST",
				data : {
					"shippingAddressId":shippingAddressId,"aid" : aid,"address":address,"name":name,"phone":phone,"provinceName":provinceName,"cityName":cityName,"areaName":areaName,send:1
				},
				dataType : "json",
				cache : false,
				beforeSend : function() {
					// $submit.prop("disabled", true);
				},
				success : function(data) {
					if(data.success){
						//ajax获取代收点信息
						//dispDaishoudian(provinceName+cityName+areaName+address+"",name,phone);
						
						editAddressFlag = false;
						$(".one_infomation p").css("display","block");
						$shippingAddressId.val(data.data.id);
						var spanText = provinceName+" "+cityName+" "+areaName+" "+address+"（"+name+"收）"+phone;
						$("#addressP").html("<input class=\"selradio\" type=\"radio\" name=\"commonAddress\" value=\"\" checked=\"checked\"><span>"+spanText+"</span>");
						$t_name.val(name);
						$address.val(provinceName+" "+cityName+" "+areaName+" "+address)
						$mobile.val(phone);
						var addressStr = "<input type=\"hidden\" value=\""+data.data.id+"\" name=\"s_shippingAddressId\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.aid+"\" name=\"s_aid\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.name+"\" name=\"s_name\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.phone+"\" name=\"s_phone\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.address+"\" name=\"s_address\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.provinceName+"\" name=\"s_provinceName\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.cityName+"\" name=\"s_cityName\">"+"\n"
						+"<input type=\"hidden\" value=\""+data.data.areaName+"\" name=\"s_areaName\">"+"\n"
						+"<input class=\"selradio\" type=\"radio\" value=\""+data.data.id+"\" name=\"addressRadio\" checked=\"checked\">"+"\n"
						+""+provinceName+" "+cityName+" "+areaName+" "+address+"（"+name+"收）"+phone+"\n"
						+"<strong class=\"s_ch\"><a name=\"editAddress\" href=\"javascript:void(0);\">修改</a></strong>"+"\n"
						+"<strong class=\"s_de\"><a href=\"javascript:void(0);\" name=\"delAddress\">删除</a></strong>"+"\n";
						
						if(falg== true){//替换
							$("input[name='addressRadio'][value='"+shippingAddressId+"']").parent().html(addressStr);
						}else{//追加
							$(".newaddress").before("<p>"+"\n"+addressStr+"</p>"+"\n");
						}
						
						var display = $newAddressDiv.css("display");
						  if(display == "block"){
							$newAddressDiv.hide(500);
						  }
						$editAddressDiv.hide(500);
						$("#addressInfomation").show();
						//重新绑定事件
						
						$("a[name='delAddress']").unbind('click').click(delAddress);//地址编辑 删除事件
						$("a[name='editAddress']").unbind('click').click(editAddress);//地址编辑 编辑事件
						$('input[name="addressRadio"]').unbind("change").change(editAddressRadio);//发票地址 单选按钮事件
						$("#useNewAddress").unbind("change").change(useNewAddress);//使用新地址 单选按钮事件

						calculateTotalFreight();
					}else{
						wode.showBox("错误",data.msg,{hideBtn:true});
					}
				},
				complete : function() {
					// $submit.prop("disabled", false);
				}
			});
		}else{//切换地址
			var shippingAddressId = $addressRadio.parent().find("input[name='s_shippingAddressId']").val();
			var aid = $addressRadio.parent().find("input[name='s_aid']").val();
			var name = $addressRadio.parent().find("input[name='s_name']").val();
			var phone = $addressRadio.parent().find("input[name='s_phone']").val();
			var address = $addressRadio.parent().find("input[name='s_address']").val();
			var provinceName = $addressRadio.parent().find("input[name='s_provinceName']").val();
			var cityName = $addressRadio.parent().find("input[name='s_cityName']").val();
			var areaName = $addressRadio.parent().find("input[name='s_areaName']").val();
			
			$.ajax({
				url : "/order/saveShippingAddress",
				type : "POST",
				data : {
					"shippingAddressId":shippingAddressId,"aid" : aid,"address":address,"name":name,"phone":phone,"provinceName":provinceName,"cityName":cityName,"areaName":areaName
				},
				dataType : "json",
				cache : false,
				beforeSend : function() {
					// $submit.prop("disabled", true);
				},
				success : function(data) {
					if(data.success){

						//ajax获取代收点信息
						//dispDaishoudian(provinceName+cityName+areaName+address+"",name,phone);
						
//						$(".one_infomation p").css("display","block");
						var spanText = provinceName+" "+cityName+" "+areaName+" "+address+"（"+name+"收）"+phone;
						$("#addressP").html("<input class=\"selradio\" type=\"radio\" name=\"commonAddress\" value=\"\" checked=\"checked\"><span>"+spanText+"</span>");
						$t_name.val(name);
						$address.val(provinceName+" "+cityName+" "+areaName+" "+address)
						$mobile.val(phone);
						$editAddressDiv.hide(500);
						$("#addressInfomation").show();
						
						calculateTotalFreight();
					}else{
						wode.showBox("错误",data.msg,{hideBtn:true});
					}
				},
				complete : function() {
					// $submit.prop("disabled", false);
				}
			});	
		}
	});
	
	$delAddress.click(delAddress);
	
/**
 * 根据code获取地址信息
 */
function getArea(grade,areaCode) {
	var data = window.China;
	if(grade==1)
		$("#province>option").remove();
	if(grade==2)
		$("#city>option").remove();
	if(grade==3)
		$("#area>option").remove();
	$.each(data, function (key, value,i) {
		var name = value.name;
        var level = value.grade;
        var code = value.code;
        var parentId = value.father;
        if(grade!=level){
            return true;
        }
        if(areaCode!=null){
	        if(grade==2 && parentId!=areaCode){
	        	return true;
	        }
	        if(grade==3 && parentId!=areaCode){
	        	return true;
	        }
		}
        switch (level) {
            case 1 :
            	if(key==$("#provinceNo").val())
		    		var option = "<option value=\""+key+"\" selected='selected'>"+name+"</option>\n";
		    	else
		    		var option = "<option value=\""+key+"\">"+name+"</option>\n";
                $("#province").append(option);
				getArea(2,$('#province>option:selected').val());
                break;
            case 2 :
            	if(key==$("#cityNo").val())
            		var option = "<option value=\""+key+"\" selected='selected'>"+name+"</option>\n";
		    	else
		    		var option = "<option value=\""+key+"\">"+name+"</option>\n";
            	$("#city").append(option);
				getArea(3,$('#city option:selected').val());
                break;
            case 3 :
            	if(key==$("#areaNo").val())
            		var option = "<option value=\""+key+"\" selected='selected'>"+name+"</option>\n";
		    	else
		    		var option = "<option value=\""+key+"\">"+name+"</option>\n";
            	$("#area").append(option);
                break;
        }
    });
}


	//initDaishoudian();
	//初始化代收点信息
	function initDaishoudian(){
		var name = $.trim($('input[name="name"]').val());
		var phone=$.trim($('input[name="mobile"]').val());
		var address=$.trim($('input[name="address"]').val());
		if(address != "" && address!=null) {
			dispDaishoudian(address.replaceAll(" ",""),name,phone);
		}		
	};
	
	// 代收点显示控制
	function dispDaishoudian(address,name,phone) {

		//ajax获取代收点信息
    	var content = '"sname":"distriBution","action":"near","address":"'+address+'"';
    	$.ajax({
            url: 'http://kuaidi.wo-de.com/express/busJsonp.do?content={' +content+ '}&token=',
    		dataType : 'jsonp',
    	    jsonp:'jsonpcallback',
    	    success: function(json){
    	    	var ary = eval(json.body);
    	    	if(ary.length>0) {
					$("#collecting_address_div").attr("style","display: block;");
					$("#collecting_address_div").empty();
					//[代收点]
					var v = "<div class=\"delivery-tab\"><span>代收点免费代收服务</span><a href=\"#\">[?]</a></div><div class=\"delivery-tab-content\" id=\"collectingPoints\">" +
							"<input type=\"hidden\" id=\"shipping_phone\" value="+ary[0].phone+">" +
							"<input type=\"hidden\" id=\"shipping_name\" value="+ary[0].name+">" +
							"<input type=\"hidden\" id=\"shipping_address\" value="+ary[0].address+">" +
							"<input class=\"checkbox-input\" type=\"checkbox\" name=\"collectingPointCheck\">" +// checked=\"checked\"  ${collectingPoint_address}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;+"(电话:"+${collectingPoint_phone}+")&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;("+${name}+" 收) "+${phone}
							"<span class=\"dsDesc-station-info\" id=\"collectingPointsVal\">"+ary[0].address+"&nbsp;&nbsp;(电话:"+ary[0].phone+")&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;("+name+" 收)&nbsp;"+phone+"</span>" +
							"<label for=\"\"><a class=\"dsDesc-edit\" href=\"#\" id=\"updata_expressCollectingPoints_box\">修改代收点</a></label>" +
							"</div>";
					$("#collecting_address_div").append(v);
					
					$("#update_recipient_phone").val(name);
					$("#update_recipient_name").val(phone);
    	    	} else {
					$("#collecting_address_div").attr("style","display: none;");
    	    	}
    	    },
    	    error:function(json){
				$("#collecting_address_div").attr("style","display: none;");    	    
    	    }
    	});
	};
	
	
	function delAddress() {
		var $address=$(this).parent().parent();
		var shippingAddressId = $address.find("input[name='s_shippingAddressId']").val();
		$.ajax({
			url : "/order/deleteShippingAddress",
			type : "POST",
			data : {
				"shippingAddressId":shippingAddressId
			},
			dataType : "json",
			cache : false,
			success : function(data) {
				if(data.success){
					if($("#shippingAddressId").val() == shippingAddressId){//若删除的地址，是当前选中，清空地址信息
						$("#shippingAddressId").val("-1");
						$("input[name='aid']").val("");
						$("input[name='name']").val("");
						$("input[name='address']").val("");
						$("input[name='mobile']").val("");
						$("#addressP").html("");
						
					}
					$address.remove();
				}else{
					wode.showBox("错误",data.msg,{hideBtn:true});
				}
			}
		});
	}
	
	function editAddress() {
		var $address=$(this).parent().parent();
		var name = $address.find("input[name='s_name']").val();
		var phone = $address.find("input[name='s_phone']").val();
		var address = $address.find("input[name='s_address']").val();
		var provinceName = $address.find("input[name='s_provinceName']").val();
		var cityName = $address.find("input[name='s_cityName']").val();
		var areaName = $address.find("input[name='s_areaName']").val();
		var shippingAddressId = $address.find("input[name='s_shippingAddressId']").val();
		var aid = $address.find("input[name='s_aid']").val();
		$("#t_address").val(address);
		$("#t_name").val(name);
		$("#t_mobile").val(phone);
		$shippingAddressId.val(shippingAddressId);
		$aid.val(aid);
		$('#province option:contains(' + provinceName + ')').attr('selected', true);
		getArea(2,$('#province>option:selected').val());
		$('#city option:contains(' + cityName + ')').attr('selected', true);
		$('#area option:contains(' + areaName + ')').attr('selected', true);
		var display = $newAddressDiv.css("display");
		if(display == "none"){
			$newAddressDiv.show(500);
		}
		editAddressFlag = true;
		//editArea(provinceName,cityName,areaName);
	};
	
	
	function useNewAddress() {
		$shippingAddressId.val("");
		$("#t_address").val("");
		$("#t_name").val("");
		$("#t_mobile").val("");
		getArea(1,null);//加载省
		//$city.html("<option value=0>请选择...</option>");
		//$area.html("<option value=0>请选择...</option>");
		//TODO 清空新地址内容
		var display = $newAddressDiv.css("display");
		if(display == "none"){
			$newAddressDiv.show(500);
		}
	};
	function editAddressRadio(){
		  editAddressFlag = false;
		  var display = $newAddressDiv.css("display");
		  if(display == "block"){
			$newAddressDiv.hide(500);
		  }
	};
	
	function editArea(provinceName,cityName,areaName){
		//让默认的 省选中
		//var data = window.China;
		//getArea(1,null);
		$('#province option:contains(' + provinceName + ')').attr('selected', true);
		$('#city option:contains(' + cityName + ')').attr('selected', true);
		$('#area option:contains(' + areaName + ')').attr('selected', true);
		var display = $newAddressDiv.css("display");
		if(display == "none"){
			$newAddressDiv.show(500);
		}
		editAddressFlag = true;
	}
});
