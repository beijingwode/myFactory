var wode={"hasLogin":0};//只保留一个全局变量，避免变量冲突.
wode.domain="http://www.wd-w.com";
wode.phone = /^(1[3|4|5|7|8][0-9])\d{8}$/;//手机号码验证
wode.register = /^[0-9A-Za-z]{4,20}$/;//密码验证
wode.userDomain = "https://passportd.wd-w.com";//邮箱验证
wode.comeFrom = "myFactory";//邮箱验证
wode.publiced = 0;//邮箱验证
wode.checkLogin=function(){
	GetUserInfoCookie();
}

//校验邮箱
wode.checkEmail=function(email){
	email=$.trim(email);
	if(email.length==0) return false;
	if(email.indexOf('.com.cn') !=-1 && email.indexOf('.com.cn') == email.length-7) email=email.substring(0,email.length-3);
	var pattern = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/; 
	return pattern.test(email);
}

wode.getCartNum=function(){
	$.ajax({
		   type: "POST",
		   url: "/user/getCartNum",
		   success: function(ret){
		   		if(ret.success){
		   			var obj = ret.data;
		   			$(".shopping_amount").text(obj.num);
		   			if(obj.num == 0){
		   				return;
		   			}
		   			var cartItems = obj.cartItems;
		   			var cartInfo = "";
		   			var totalPrice = 0;
		   			for(var i = 0; i < cartItems.length; i++){
		   				var cartItem = cartItems[i];
		   				cartInfo += "<div class=\"shoppingcar_list_cont\">"
		                	+"<div class=\"shoppingcar_img\" style='position:relative;'>"
		                	+ (cartItem.saleKbn==1?"<div class=\"picon\" style='width: 24px;height:24px;position: absolute;top:2px;left:25px;'><img src=\"../images/picon2.png\" style='width: 24px;height:24px;' /></div>":"")  
		                	+ (cartItem.saleKbn==2?"<div class=\"picon\" style='width: 24px;height:24px;position: absolute;top:2px;left:25px;'><img src=\"../images/picon_c2.png\" style='width: 24px;height:24px;' /></div>":"")
		                	+ (cartItem.saleKbn==4?"<div class=\"picon\" style='width: 24px;height:24px;position: absolute;top:2px;left:25px;'><img src=\"../images/picon_z2.png\" style='width: 24px;height:24px;' /></div>":"")
		                	+ (cartItem.saleKbn==5?"<div class=\"picon\" style='width: 24px;height:24px;position: absolute;top:2px;left:25px;'><img src=\"../images/picon_t2.png\" style='width: 24px;height:24px;' /></div>":"")   
		                	+"<a href=\"/"+cartItem.productId+".html\"><img src=\""+cartItem.imagePath+"\" width=\"50\" height=\"58\"></a></div>"
		                	+"<div class=\"shoppingcar_r\">"
		                	+"<p><a href=\"/"+cartItem.productId+".html\">"+cartItem.productName+"</a></p>"
		                	for(var j = 0; j<cartItem.specificationList.length; j++){
		                		cartInfo += "<p>"+cartItem.specificationList[j]+"</p>"
		                	}
		   				cartInfo += " <div class=\"shopping_r_price\">"
		                	+"<span><strong>"+currency(cartItem.price, true)+"</strong> *"+cartItem.quantity+"</span>"
		                	+"   <label><a name='delete' href=\"#\">删除</a></label>"
		                	+"<input type=\"hidden\" name=\"partNumber\" value=\""+cartItem.partNumber+"\">"
		   					+"<input type=\"hidden\" name=\"quantity\" value=\""+cartItem.quantity+"\">"
		                	+"  </div>"
		                	+"</div>"
		                	+"</div>\n"
		                	totalPrice += cartItem.price *cartItem.quantity;
		   			}
		   			cartInfo += "<div class=\"account_info\">"
		   				+"<span>"+obj.num+"</span>件商品  共计 <strong>"+currency(totalPrice, true)+"</strong>"
		   				+"</div>";
		   			if(obj.num>0){
		   				cartInfo += "<div class=\"go_account_btn\"><a href=\"/cart/list\">去购物车结算</a></div>";
		   			}
		   			$(".shoppingcar_list").html(cartInfo);
		   			
		   			
					// 删除
		   			$("a[name='delete']").click(function() {
							var partNumber = $(this).parent().parent().find("input[name='partNumber']").val();
							var $shoppingcar_list_cont = $(this).parent().parent().parent().parent();
							$.ajax({
								url : "/cart/delete",
								type : "POST",
								data : {
									partNumber : partNumber
								},
								dataType : "json",
								cache : false,
								beforeSend : function() {
//									$submit.prop("disabled", true);
								},
								success : function(data) {
									if (data.success) {
										$shoppingcar_list_cont.remove();
										var obj = data.data;
										$(".shopping_amount").text(obj.totalNumber);
										if(obj.totalNumber>0&&totalPrice>0){
											$(".account_info").html("<span>"+obj.totalNumber+"</span>件商品  共计 <strong>"+currency(obj.totalPrice, true)+"</strong>");
										}else{
											$(".go_account_btn").remove();
											$(".account_info").remove();
											$('.shoppingcar_cont').hide();
										}
									} else {
										alert(data.msg);
									}
								},
								complete : function() {
//									$submit.prop("disabled", false);
								}
							});
						return false;
				});
		   		}
		   }
	}); 
}

//显示登陆框
wode.showLoginBox=function(cb){
	if(typeof(cb)=="function"){
		wode.afterLogin=cb;
	}else{
		wode.afterLogin=null;
	}
	
	$('.popup_bg').show();
	$('.login_popup').show();	
}

 wode.errorHide=function(){
	$("#error").fadeOut("slow");
}

 wode.doLogin=function(){
	var result = true;
	var timeout = 4000;//错误信息展示时间（4秒）
	if($("#userName").val().length<1){
		$("#error").attr("class","error");
		$(".error").html("邮箱/手机号码不能为空");
		$(".error").fadeIn("slow");
		setTimeout("wode.errorHide()",timeout);
		result = false;
	}
	
	if($("#password").val().length<1){
		$("#error").attr("class","error");
		$(".error").html("密码不能为空");
		$(".error").fadeIn("slow");
		setTimeout("wode.errorHide()",timeout);
		result = false;
	}
	
	if(result){
		var user_from = document.getElementById("user_from");
		if(typeof(user_from) != "undefined" && user_from!=null) {
			
		} else {
			var html='<div style="display: none">'+
				'<iframe id="login_iframe" name="login_iframe"></iframe>'+
				'<form method="POST" id="user_from" target="login_iframe">'+
				'<div id="post_param"></div></form></div>';		
			$(".login_popup").append(html);
			
			user_from = document.getElementById("user_from");
		}
		
		$("#post_param").html('<input type="hidden" name="userName" value="'+$("#userName").val() +'">'
				+ '<input type="hidden" name="password" value="'+$("#password").val() +'">'
				+ '<input type="hidden" name="userCom" value="'+wode.comeFrom +'">'
				+ '<input type="hidden" name="loginType" value="factory_web_login">'
				+ '<input type="hidden" name="returnUrl" value="'+wode.domain+'/user/writeUserResult?type=loginMini">');
		user_from.action = wode.userDomain+'/login';
		user_from.submit();
	}
}

 function loginResult(json,showVcode){

	if(json.success){
		
		$.ajax({
		    dataType: 'json',
		    url:'/user/hasLogin?ticket='+json.data+"&remenber=1",
		    success: function(data){
    			if(data.success){
					setHeaderInfo(data.data.nickName,data.data.id,json.data);

    	    		$('.popup_bg').hide();
    	    		$('.login_popup').hide();	
		    		if(typeof(wode.afterLogin)=="function"){
		   				//wode.afterLogin();
		   			}
    	    		
    			}else{
    				$("#error").attr("class","error");
    				$(".error").html(data.msg);
    				$(".error").fadeIn("slow");
    				setTimeout("wode.errorHide()",timeout);
    			}
		    }	
		});
		
	}else{
		$("#error").attr("class","error");
		$("#error").html(json.msg);
		$(".error").fadeIn("slow");
		if(showVcode=='1') {
			window.location=wode.domain + "/login.html";
		}
		setTimeout("wode.errorHide()",timeout);
	}
 }

//二级菜单
$(function(){
	$('.allproduct_menu').mouseover(function(){
		$('.menu_list').show();	
		$('.allproduct_menu p').css('background','url(/images/factory_img.png) no-repeat 180px -74px');
	})
	$('.allproduct_menu').mouseout(function(){
		$('.menu_list').hide();
		$('.allproduct_menu p').css('background','url(/images/factory_img.png) no-repeat 180px -38px');	
	})
	$('.menu_list .mt').hover(function(){ 
		$(this).find('h2').addClass("active");
		$(this).find('.menu_player').show();
	},function(){ 
		$(this).find('h2').removeClass("active");
		$(this).find('.menu_player').hide();
	}); 
}); 

//首页-购物车下拉框
$(function(){
	$('.shoppingcar').hover(function(){
		 /*var num = $(".shopping_amount").text();
		 if(num > 0){*/
			 $('.shoppingcar_cont').show();	
				$('.shoppingcar_cont').hover(function(){
					$('.shoppingcar_cont').show();		
				},function(){
					$('.shoppingcar_cont').hide();		
				})
		 //}
	},function(){
		$('.shoppingcar_cont').hide();		
	})	
})

$(document).ready(function() {

	var url=window.location.href;
	if(url.indexOf(wode.domain) != 0) {
		var indexP = url.indexOf(":");
		var start= url.substring(0,indexP+3);
		url = url.substring(indexP+3);

		indexP = url.indexOf(":");
		if(indexP != 0) {
			var domainS = url.substring(0,indexP);
			url = url.substring(indexP);
			indexP = url.indexOf("/");
			domainS=domainS+url.substring(0,indexP);

			wode.domain=start+domainS;
		}
	}
	
	wode.checkLogin();
	if($(".shoppingcar").length>0){
		wode.getCartNum();
	}
	if($.trim($('.menu_list').text()).length<1){
		$('.menu_list').mouseover(function(){
			$(this).show();	
			$('.allproduct_menu p').css('background','url(/images/factory_img.png) no-repeat 180px -74px');
		})
		$('.menu_list').mouseout(function(){
			$(this).hide();	
			$('.allproduct_menu p').css('background','url(/images/factory_img.png) no-repeat 180px -38px');
		})
		
		$('.menu_list').load('/category.html',function(){
			$('.menu_list .mt').hover(function(){ 
				$(this).find('h2').addClass("active");
				$(this).find('.menu_player').show();
			},function(){ 
				$(this).find('h2').removeClass("active");
				$(this).find('.menu_player').hide();
			}); 
		});
	}
	
	$("body").delegate(".login_popup .close_btn","click",function(){
		$('.popup_bg').hide();
		$('.login_popup').hide();
		$("#error").removeClass();
		$("#error").html("");
	});
	
	jQuery.getScript(wode.domain+"/resources/js/nav.js");
	jQuery.getScript(wode.domain+"/resources/js/search_list.js");
	jQuery.getScript(wode.domain+"/resources/js/jquery.cookie.js");
	
});

var setting = {
		priceScale: "2",
		priceRoundType: "roundHalfUp",
		currencySign: "￥",
		currencyUnit: "元",
		uploadImageExtension: "",
		uploadFlashExtension: "",
		uploadMediaExtension: "",
		uploadFileExtension: ""
	};

//货币格式化
function currency(value, showSign, showUnit) {
	if (value != null) {
		var price;
		if (setting.priceRoundType == "roundHalfUp") {
			price = (Math.round(value * Math.pow(10, setting.priceScale)) / Math.pow(10, setting.priceScale)).toFixed(setting.priceScale);
		} else if (setting.priceRoundType == "roundUp") {
			price = (Math.ceil(value * Math.pow(10, setting.priceScale)) / Math.pow(10, setting.priceScale)).toFixed(setting.priceScale);
		} else {
			price = (Math.floor(value * Math.pow(10, setting.priceScale)) / Math.pow(10, setting.priceScale)).toFixed(setting.priceScale);
		}
		if (showSign) {
			price = setting.currencySign + price;
		}
		if (showUnit) {
			price += setting.currencyUnit;
		}
		return price;
	}
}

String.prototype.replaceAll = function(s1,s2){
	return this.replace(new RegExp(s1,"gm"),s2);
}

/**
 * 公用回到顶部
 */
$(document).ready(function($){
	//临时处理
	var domain=document.domain; 
	if(domain.indexOf("wd-w.com")>-1){
		var tt=document.title;
		tt=tt.replace(".com","网");
		tt=tt.replace("我的网","我的福利");
		$(document).attr('title',tt);
		var head=$(".roof").html().replaceAll("我的.com","我的网");
		$(".roof").html(head);
		//$(".foter").first().find("span").eq(0).remove();
			
	}else{
		//$(".logo img").attr("src","/images/logo1.png")
	}
	
	// browser window scroll (in pixels) after which the "back to top" link is shown
	var offset = 300,
	offset_opacity = 1200,
	scroll_top_duration = 700,
	$back_to_top = $('.floatwrap');

	//hide or show the "back to top" link
	$(window).scroll(function(){
		( $(this).scrollTop() > offset ) ? $back_to_top.addClass('cd-is-visible') : $back_to_top.removeClass('cd-is-visible cd-fade-out');
	});
	
	//smooth scroll to top
	$('.floatwrap').find(".cd-top").on('click', function(event){
		event.preventDefault();
		$('body,html').animate({
			scrollTop: 0 ,
		 	}, scroll_top_duration
		);
	});

});

//加减乘除运算
//给Number类型增加一个add方法，，使用时直接用 .add 即可完成计算。   
Number.prototype.add = function (arg,fix) {  
    return accAdd(arg, this,fix);  
};  
//给Number类型增加一个add方法，，使用时直接用 .sub 即可完成计算。   
Number.prototype.sub = function (arg,fix) {  
    return Subtr(this, arg,fix);  
};  
//给Number类型增加一个mul方法，使用时直接用 .mul 即可完成计算。   
Number.prototype.mul = function (arg,fix) {  
    return accMul(arg, this,fix);  
};   
//给Number类型增加一个div方法，，使用时直接用 .div 即可完成计算。   
Number.prototype.div = function (arg,fix) {  
    return accDiv(this, arg,fix);  
};
//加法函数  
function accAdd(arg1, arg2,fix) {  
    var r1, r2, m;  
    try {  
        r1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r1 = 0;  
    }  
    try {  
        r2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r2 = 0;  
    }
    m = Math.pow(10, Math.max(r1, r2));  
    return ((arg1 * m + arg2 * m) / m).toFixed(fix);  
}   

//减法函数  
function Subtr(arg1, arg2,fix) {  
    var r1, r2, m, n;  
    try {  
        r1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r1 = 0;  
    }  
    try {  
        r2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
        r2 = 0;  
    }  
    m = Math.pow(10, Math.max(r1, r2));  
     //last modify by deeka  
    return ((arg1 * m - arg2 * m) / m).toFixed(fix);  
}  
  
//乘法函数  
function accMul(arg1, arg2,fix) {  
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();  
    try {  
        m += s1.split(".")[1].length;  
    }  
    catch (e) {  
    }  
    try {  
        m += s2.split(".")[1].length;  
    }  
    catch (e) {  
    }  
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m).toFixed(fix);  
}   
 
//除法函数  
function accDiv(arg1, arg2,fix) {  
    var t1 = 0, t2 = 0, r1, r2;  
    try {  
        t1 = arg1.toString().split(".")[1].length;  
    }  
    catch (e) {  
    }  
    try {  
        t2 = arg2.toString().split(".")[1].length;  
    }  
    catch (e) {  
    }  
    with (Math) {  
        r1 = Number(arg1.toString().replace(".", ""));  
        r2 = Number(arg2.toString().replace(".", ""));  
        return ((r1 / r2) * pow(10, t2 - t1)).toFixed(fix);  
    }  
}   


//取Cookie的值
function GetUserInfoCookie() {
	
	//获取cookie字符串 
	var strCookie = document.cookie;
	//将多cookie切割为多个名/值对 
	var arrCookie = strCookie.split("; ");
	//遍历cookie数组，处理每个cookie对 
	
	var ticket = "";
	var uid = "";
	var nic = "";
	for (var i = 0; i < arrCookie.length; i++) {
		var arr = arrCookie[i].split("=");
		if (arr[0] == "user_ticket") {
			//ticket
			ticket = arr[1];
		} else if (arr[0] == "uid") {

			//uid
			uid = arr[1];
		} else if (arr[0] == "nickname") {
			//nic
			nic = arr[1];
			nic = decodeURI(nic);
			nic = nic.replace(/%40/, "@");
		}
	}

	//判断ticket,为空则从sso获取ticket，否则显示其他信息
	if (ticket == "") {
		//如果主域 cookie中存在ticket则写入js变量user_ticket 
		jQuery.getScript(
				wode.userDomain+"/cookie.js",
				function(data, status, jqxhr) {
					if(typeof(user_ticket) != "undefined" && user_ticket!=null ){
						//调用共通方法，查询ticket
			    		$.ajax({
			    			url : wode.domain + '/user/hasLogin',
			    			data : {
			    				ticket : user_ticket
			    			},
			    			dataType : 'json',
			    			type : 'post',
			    			success : function(json) {
			    				if (json.success) {
			    					setHeaderInfo(json.data.nickName,json.data.id,json.data.ticket);
			    				} else {
			    					goLogin();
			    				}
			    			},
			    			error : function(XMLHttpRequest, textStatus, errorThrown) {
			    				// $('#resultContent').html('no....');
		    					goLogin();
			    			}
			    		});
					} else {
    					goLogin();
					}
				});
		if(uid != "" && nic !="") {
			//显示用户名
			$(".login").hide();
			$(".roof_left_list").html("<li><a href=\"/member/center\">"+nic+"</a><em>|</em></li><li><a href=\"/user/loginOut\">注销</a></li>");
		}		
	} else {
		wode.hasLogin = 1;
		//显示用户名
		$(".login").hide();
		$(".roof_left_list").html("<li><a href=\"/member/center\">"+nic+"</a><em>|</em></li><li><a href=\"/user/loginOut\">注销</a></li>");
	}
}

//向主域写令牌
function setHeaderInfo(nickname,id,ticket){
	wode.hasLogin = 1;
	nickname = nickname.replace(/%40/, "@");
	$(".login").hide();
	$(".roof_left_list").html("<li><a href=\"/member/center\">"+nickname+"</a><em>|</em></li><li><a href=\"/user/loginOut\">注销</a></li>");

	var iframe = document.getElementById("login_iframe");
	if(iframe) {
		iframe.src = wode.userDomain + "/writeTicket/"+ticket;
	}
}


//向主域写令牌
function goLogin(){
	if(wode.publiced==0) {
		window.location="/user/toLogin?url="+encodeURI(window.location);
	}
}

//当ace进来时，a改变

$(function(){
	var host = window.location.href
	if(host.indexOf("ace.wd-w.com") >= 0 ){
		 $("a").each(function(i){
		    	var href = $(this).attr("href");
		    	if(href &&href.indexOf('www.wd-w.com')!=-1) {
			    	href = href.replace("www.wd-w.com","ace.wd-w.com");
			    	$(this).attr("href",href);
		        } 			
		 })
	}

})
