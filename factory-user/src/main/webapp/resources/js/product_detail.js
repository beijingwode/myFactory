// JavaScript Document
$(document).ready(function() {
	var pageKey = GetQueryString("pageKey");
	if(pageKey==null || typeof(pageKey)=="undefined" ){
		pageKey="";
	}
//相关分类
	 if($("#categoryId").length>0){     
			$.ajax({
				type: "POST",

				url: '/pcList/' + $("#categoryId").val(),
				success: function(data){
					if(data.success){
						var html='';
						for(i=0;i<data.data.length;i++) {
							if(i%2==0){
								html += '<li>';
							}
							html += '<span><a href="/product/list?cat='+data.data[i].id+'">'+data.data[i].name+'</a></span>';
							if(i%2!=0||i==data.data.length-1){
								html += '</li>';
							}
						}
						
						$('.sort_list:eq(0) ul').html(html);			
						
					}
				}
			});
		   };
		   
//同类其他品牌
   if($("#categoryId").length>0){
	  var cateid=$("#categoryId").val()
		$.ajax({
			type: "POST",
			url: '/bvList/' + cateid,
			success: function(data){
				if(data.success){
					var html='';
					
					for(i=0;i<data.data.length;i++) {
						if(i%2==0){
							html += '<li>';
						}
						html += '<span><a href="/product/list?cat='+cateid+'&brand='+data.data[i].brandName+'" id="'+data.data[i].brandId+'">'+data.data[i].brandName+'</a></span>';
						if(i%2!=0||i==data.data.length-1){
							html += '</li>';
						}
					}
					
					$('.sort_list:eq(1) ul').html(html);								
				}
			}
		});
	   };	
//浏览商品的用户最后买了
   if($("#categoryId").length>0){     
	$.ajax({
		type: "POST",
		url: '/categoryList/' + $("#categoryId").val(),
		success: function(data){
			if(data.success){
				var html='';
				for(i=0;i<data.data.length;i++) {
					html += '<li>';
					html += '<div class="p-img"><a href="'+data.data[i].id+'.html?pageKey='+pageKey+'">';
					html += '<img src="'+data.data[i].image+'" width="174" height="77" alt="historyproduct"></a>';
					html += '</div>';
					html += '<p class="p3"><a href="'+data.data[i].id+'.html?pageKey='+pageKey+'">'+data.data[i].name+'</a></p>';
					html += data.data[i].showPrice;
					var minprice = parseFloat(data.data[i].minprice);
					html += '<p  class="p2">电商价：￥'+minprice.toFixed(2)+'</p>';
					html += '</li>';
				}
				
				$('.historyproduct ul').html(html);			
				
			}
		}
	});
   };
   
   //添加我想领按钮
  // $(".item_btn .item_btn_02").after('<div class="buybtn item_btn_04"><a href="javascript:void(0);">我想领</a></div>');
   //添加换领规则
   //$(".item_btn .item_btn_04").after('<div class="hl_help"><a href="javascript:;" title="点击查看换领规则"><img src="images/hl_help_icon1.png" /></a></div>');
   
   $("body").append('<div class="hl_help_pop"><span><img src="images/loading_app_ewm_x.png" /></span><div class="hl_help_img"><img src="images/hl_help_pic3.png" /></div></div>')
   
   $('.hl_help_pop span').click(function(){
			$('.popup_bg').hide();
			$('.hl_help_pop').hide();	
	});
   
   //添加一起购按钮
   $(".item_btn").append('<div class="buybtn item_btn_03" style="display:none;"><a href="javascript:void(0);">一起购</a></div>')
   $("body").append('<div class="TogetherToBuy_btn_ewm"><span><img src="images/loading_app_ewm_x.png" /></span><p class="p1">扫码下载APP  马上开启一起购~</p><p class="p2">拉上亲友，一起省钱！</p><div class="ewm_img"><img src="images/loading_app_ewm.png" /></div></div>')
   //点击一起购按钮弹出二维码效果
   $('.p_item_info .item_btn_03 a').click(function(){
		$('.popup_bg').show();
		$('.TogetherToBuy_btn_ewm').show();	
	})
   $('.TogetherToBuy_btn_ewm span').click(function(){
		$('.popup_bg').hide();
		$('.TogetherToBuy_btn_ewm').hide();	
	})
   
});
//其他换领
function setSortLeft(){
	var pageKey = GetQueryString("pageKey");
	if(pageKey==null || typeof(pageKey)=="undefined" ){
		pageKey="";
	}
	   $.ajax({
			type: "POST",
			//url: '/product/jsonSearch?saleKbn=2&salePrice=0-'+salePrice,
			url: '/product/jsonSearch?saleKbn=2',
			success: function(data){
				if(data.success){
					var result = data.data;
					var end=1;
					var html="";
					   html += '<div class="sort">';
					   html += '<h2>其他换领</h2>';
					   html += '<div class="hl_product">';
					   html += '<ul>';
					   if(result.length<3){
						   end =result.length;
					   }else{
						   end =3;
					   }
					   var productId = $("#productId").val();
					   for (var i = 0; i < end; i++) {
						   
						   html += '<li>';
						   html += '<dl>';
						   html += '<dt><a href="'+result[i].id+'.html?pageKey='+pageKey+'"><img src="'+result[i].image+'" /></a></dt>';
						   html += '<dd><a href="javascript:;" title="'+result[i].brand+'" >'+result[i].name+'</a></dd>';
						   html += '<dd class="dd2"><span>换领价：'+result[i].salePrice+'</span><em>库存：'+result[i].stock+'</em></dd>';
						   html += '</dl>';
						   html += '  </li>';
					   }
					   html += ' </ul>';
					   html += ' </div>';
					   html += '</div>';
					   $(".detail_wrap .left").html(html);
				}
			}
		});
}