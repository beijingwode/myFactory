var uid=GetUidCookie();
$(document).ready(function() {
	//人数
	//获得文本框对象
	var t = $("#number_p");
	//初始化数量为2,并失效减
	if(t.val()<=2){//人数小于等于2
		$('#quantityMinus_p').attr('disabled', true);
	}
	//数量增加操作
	$("#quantityPlus_p").click(function() {
		t.val(parseInt(t.val()) + 1);
		if(t.val()>=20){
			$('#quantityPlus_p').attr('disabled', true);
		}
		if (parseInt(t.val()) >= 2) {
			$('#quantityMinus_p').attr('disabled', false);
		};
	})
	//数量减少操作
	$("#quantityMinus_p").click(function() {
		if (parseInt(t.val()) != 2) {
			t.val(parseInt(t.val()) - 1);
			if(t.val()<20){
				$('#quantityPlus_p').attr('disabled', false);
			}
		};
	});

	//天数
	//获得文本框对象
	var t1 = $("#number_d");
	//数量增加操作
	$("#quantityPlus_d").click(function() {
		t1.val(parseInt(t1.val()) + 1)
		if (parseInt(t1.val()) > 1) {
			$('#quantityMinus_d').attr('disabled', false);
		};
		if (parseInt(t1.val()) >= 14) {
			$('#quantityPlus_d').attr('disabled', true);
		};
	});
	//数量减少操作
	$("#quantityMinus_d").click(function() {
		t1.val(parseInt(t1.val()) - 1);
		if (parseInt(t1.val()) == 1) {
			$('#quantityMinus_d').attr('disabled', true);
		};
		if (parseInt(t1.val()) < 14) {
			$('#quantityPlus_d').attr('disabled', false);
		};
	})
	
	init();
});


function init(){
	if(uid=="") return;
	var addressInfo = sessionStorage.getItem("checkAddress");
	if(typeof(addressInfo)!=undefined && addressInfo!=null &&addressInfo!=''){
		addressInfo = JSON.parse(addressInfo);
		var html ='';
		html+=' <p class="p1"><span>收货人：'+addressInfo.name+'</span><em>'+addressInfo.phone+'</em></p>'
		var address = addressInfo.provinceName+'  '+addressInfo.cityName+'  '+addressInfo.areaName+'  '+addressInfo.address;
		html+='<p class="p2">收货地址：'+address+'</p>'
		html+='<a style="display:none"></a>';
		$(".address").html(html);
		$(".address p").show();
		$(".address").data('setObj', JSON.stringify({'userName':addressInfo.name, 'phoneNum': addressInfo.phone,'address':address,'aid':addressInfo.aid}));
		var obj = $('.address').data('setObj');
		$(".address").on("tap",function(e){
			  go2ChooseAddress('choose');
		});
	}else{
		$.ajax({
			url:jsBasePath+'address/all.user?uid='+uid,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: false,
		    cache:false,
		    success : function(data) {
		    	var result = data.data;
		    	var addressInfo = null;
		    	if (result.length>0) {
					var html='';
					for (var i = 0; i < result.length; i++) {
						if(result[i].send==1){ 
							addressInfo = result[i];
						}
					}
					if(addressInfo==null){
						addressInfo = result[0];
					}
					html+=' <p class="p1"><span>收货人：'+addressInfo.name+'</span><em>'+addressInfo.phone+'</em></p>'
					var address = addressInfo.provinceName+'  '+addressInfo.cityName+'  '+addressInfo.areaName+'  '+addressInfo.address;
					html+='<p class="p2">收货地址：'+address+'</p>'
					html+='<a style="display:none"></a>';
					$(".address").html(html);
					$(".address p").show();
					$(".address").data('setObj', JSON.stringify({'userName':addressInfo.name, 'phoneNum': addressInfo.phone,'address':address,'aid':addressInfo.aid}));
					var obj = $('.address').data('setObj');
					$(".address").on("tap",function(e){
						  go2ChooseAddress('choose');
					});
				}else{
					var html='';
					html +='<a>点击此处添加收货地址</a>'
					$(".address").html(html);
					$(".address").on("tap",function(e){
						  go2ChooseAddress('add');
					});
				}
		    }
		});
	}
}
//添加购物团
function addGroupBuy(){
	if(uid=="") return;
	var data = $('.address').data('setObj');
	data = JSON.parse(data);
	$.ajax({
		url:jsBasePath+'group/createGroup.user?uid='+uid,
		type : "POST",
		data : {"num":$("#number_p").val(),"days":$("#number_d").val(),"shopId":shopId,"aid":data.aid,"userName":data.userName,"phoneNum":data.phoneNum,"address":data.address,"comment":$("#note").val()},
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		var result = data.data;
	    		var checkAddress = sessionStorage.getItem("checkAddress");
	    		if(checkAddress && checkAddress!=null && checkAddress!=''){
	    			sessionStorage.removeItem("checkAddress");
	    		}
	    		sessionStorage.setItem("groupBuy",  JSON.stringify(result));
	    		sessionStorage.setItem("fromWay",fromWay);
		    	back();
			}else{
				showInfoBox(data.msg);
			}
	    }
	});
}
function back(){
	history.back();
}
function go2ChooseAddress(type){
	if(type == "add") {
		//添加地址
		window.location=jsBasePath+"address/newAddress.user?uid="+uid+'&pageId=0';
	} else {
		//选择地址
		window.location=jsBasePath+"address/page";
	}
}