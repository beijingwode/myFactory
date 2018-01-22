var wode={"hasLogin":0};//只保留一个全局变量，避免变量冲突.
wode.domain="http://www.wd-w.com";
wode.userDomain = "https://passportd.wd-w.com";//邮箱验证
wode.comeFrom = "myFactory";//邮箱验证
wode.publiced = 0;//邮箱验证
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
	
	GetUserInfoCookie();

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
	
	jQuery.getScript(wode.domain+"/resources/js/search_list.js");
	jQuery.getScript(wode.domain+"/resources/js/jquery.cookie.js");
	
	//首页-购物车下拉框
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
});

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
//		var head=$(".roof").html().replaceAll("我的.com","我的网");
//		$(".roof").html(head);
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
	}
}

//向主域写令牌
function goLogin(){
	window.location="/user/toLogin?url="+encodeURI(window.location);
}
