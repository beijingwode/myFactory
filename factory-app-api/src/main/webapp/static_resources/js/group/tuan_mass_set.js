var uid=GetUidCookie();
$(function(){
	//天数
	//获得文本框对象
		var t1 = $("#number_d");
		 if(t1.val()<=2){//人数小于等于2
				$('#quantityMinus_d').attr('disabled', true);
			}
			//数量增加操作
			$("#quantityPlus_d").click(function() {
				t1.val(parseInt(t1.val()) + 1);
				if(t1.val()>=14){
					$('#quantityPlus_d').attr('disabled', true);
				}
				if (parseInt(t1.val()) >= 2) {
					$('#quantityMinus_d').attr('disabled', false);
				};
			})
	 //数量减少操作
		$("#quantityMinus_d").click(function() {
			if (parseInt(t1.val()) != 2) {
				t1.val(parseInt(t1.val()) - 1);
				if(t1.val()<14){
					$('#quantityPlus_d').attr('disabled', false);
				}
			};
		});
	//限时开关
	$(".set_top .p2 em").toggle(function(){
			$(this).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/time_end_set2.png) no-repeat","background-size":"38px 20px"});
			if($("#ifchecked").val()==1) {
				//取消勾选
				$("#ifchecked").val("0");
			} else {
				//选中
				$("#ifchecked").val("1");
			}
			$(".set_bottom").show();
		},function(){
			$(this).css({"background":"url("+jsBasePath+"static_resources/images/TogetherToBuy/time_end_set1.png) no-repeat","background-size":"38px 20px"});
			if($("#ifchecked").val()==1) {
				//取消勾选
				$("#ifchecked").val("0");
			} else {
				//选中
				$("#ifchecked").val("1");
			}
			$(".set_bottom").hide();
		}
	);
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
		 init();
		var chooseimages = sessionStorage.getItem("chooseimages");
		if(typeof(chooseimages)!=undefined && chooseimages!=null &&chooseimages!=''){
			var html = ''; 
			var slice=chooseimages.substring(0,chooseimages.length-1);
			var split=slice.split(",");
			for (var i = 0; i < split.length; i++) {
				html+='<div class="swiper-slide"><a href="javascript:;"><img src="'+split[i]+'" /></a></div>';
			}
			$("#productImage").html(html);
		}else{
			queryGroupProdyct();
		}
		//左右滑动
		var mySwiper = new Swiper('.swiper-container',{
		slidesPerView : 'auto',//'auto'
		//slidesPerView : 3.7,
		observer:true,//修改swiper自己或子元素时，自动初始化swiper
		observeParents:true,//修改swiper的父元素时，自动初始化swiper
		})
})
function init(){
	if(uid=="") return;
	var addressInfo = sessionStorage.getItem("checkAddress");
	if(typeof(addressInfo)!=undefined && addressInfo!=null &&addressInfo!=''){
		addressInfo = JSON.parse(addressInfo);
		var html ='';
		html+='<div class="p_con tuan_p_con" style="display:block;">';
		html+=' <p class="p1"><span>收货人：'+addressInfo.name+'</span><em>'+addressInfo.phone+'</em></p>'
		var address = addressInfo.provinceName+'  '+addressInfo.cityName+'  '+addressInfo.areaName+'  '+addressInfo.address;
		html+='<p class="p2">收货地址：'+address+'</p>'
		html+='</div>';
		//html+='<a style="display:none"></a>';
		$(".user").html(html);
		$(".user p").show();
		$(".user").data('setObj', JSON.stringify({'userName':addressInfo.name, 'phoneNum': addressInfo.phone,'address':address,'aid':addressInfo.aid}));
		var obj = $('.user').data('setObj');
		$(".user").on("tap",function(e){
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
					html+='<div class="p_con tuan_p_con" style="display:block;">';
					html+=' <p class="p1"><span>收货人：'+addressInfo.name+'</span><em>'+addressInfo.phone+'</em></p>'
					var address = addressInfo.provinceName+'  '+addressInfo.cityName+'  '+addressInfo.areaName+'  '+addressInfo.address;
					html+='<p class="p2">收货地址：'+address+'</p>'
					html+='</div>';
					//html+='<a style="display:none"></a>';
					$(".user").html(html);
					$(".user p").show();
					$(".user").data('setObj', JSON.stringify({'userName':addressInfo.name, 'phoneNum': addressInfo.phone,'address':address,'aid':addressInfo.aid}));
					var obj = $('.user').data('setObj');
					$(".user").on("tap",function(e){
						  go2ChooseAddress('choose');
					});
				}else{
					var html='';
					html +='<a>点击此处添加收货地址</a>'
					$(".user").html(html);
					$(".user").on("tap",function(e){
						  go2ChooseAddress('add');
					});
				}
		    }
		});
	}
}
function goTuanProduct(){
	location.href=jsBasePath+"group/goTuanCheckProduct.user?uid="+uid+"&shopId="+shopId;
}
function queryGroupProdyct(){
	$.ajax({
		url : jsBasePath +'group/getShopProducts.user?uid='+uid+'&shopId='+shopId,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				var result = data.data.hits;
				var html = ''; 
				var productIds = ''; 
				var chooseimages = '';
				for (var i = 0; i < result.length; i++) {
					html+='<div class="swiper-slide"><a href="javascript:;"><img src="'+result[i].image+'" /></a></div>';
					productIds += result[i].productId+',';
					chooseimages += result[i].image+',';
				}
				sessionStorage.setItem("chooseProductIds", productIds);//将选中商品id放入session
				sessionStorage.setItem("chooseimages",chooseimages);
				$("#productImage").html(html);
			}
			
		},
		error : function() {}
	});
}
//添加购物团
var post_flag = false;//设置一个对象来控制是否进入AJAX过程
function addGroupBuy(){
	if(uid=="") return;
	//正在提交则直接返回，停止执行
	if(post_flag) return showInfoBox("正在创建中.....");
	post_flag = true;
	var data = $('.user').data('setObj');
	data = JSON.parse(data);
	var groupName = $("#groupName").val();
	if(groupName=="") return showInfoBox("团名称不能为空");
	var productIds = sessionStorage.getItem("chooseProductIds");
	if(typeof(productIds)==undefined || productIds==null || productIds=='') return showInfoBox("可选商品不能为空");
	//if(productIds=="") return;
	var days = $("#number_d").val();
	var ifchecked = $("#ifchecked").val();
	if(ifchecked==0){
		days = 500;
	}
	$.ajax({
		url:jsBasePath+'group/createGroup.user?uid='+uid,
		type : "POST",
		data : {"groupName":groupName,"days":days,"limitedTime":ifchecked,"productIds":productIds,"shopId":shopId,"aid":data.aid,"userName":data.userName,"phoneNum":data.phoneNum,"address":data.address},
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
	    success : function(data) {
	    	post_flag =false;
	    	if (data.success) {
	    		var result = data.data;
	    		var checkAddress = sessionStorage.getItem("checkAddress");
	    		if(checkAddress && checkAddress!=null && checkAddress!=''){
	    			sessionStorage.removeItem("checkAddress");
	    		}
	    		sessionStorage.setItem("groupBuy",  JSON.stringify(result));
	    		sessionStorage.setItem("fromWay",fromWay);
	    		sessionStorage.removeItem("chooseimages");
	    		//sessionStorage.setItem("chooseimages",sessionStorage.getItem("chooseimages"));
		    	back();
			}else{
				showInfoBox(data.msg);
			}
	    },
		error: function(){
	         post_flag =false; //AJAX失败也需要将标志标记为可提交状态
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