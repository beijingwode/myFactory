/**
*获取评论
**/
function ajaxGetComments(){
	$.ajax({
		url : jsBasePath +'product/comments.json?productId='+productId+'&page=1&pageSize=1',
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var html='';
			if (data.success) {
				var result = data.data;
				var list = result.comments.list;
				var ccv = result.commentRatings;
				var html="";
				if(list.length>0){
					//var good = ccv.praiseCount;
					var all = ccv.praiseCount+ccv.nomalCount+ccv.badCount;
					var p=parseFloat(100.00);
			    	$(".comment_top p").html('用户评价（'+all+'）');
			    	var username='';
					for(var i=0;i<list.length ;i++) {
						var shopLink = "javascript:void(0)";
						var head = "static_resources/images/ease_default_avatar.png";
						if(list[i].shopLink && list[i].shopLink!="" && list[i].shopLink!="null") {
							shopLink= jsBasePath+"/shop/page?shopId=" + list[i].shopLink;
						}
						if(list[i].avatar && list[i].avatar!="") {
							head= list[i].avatar;
						}
						html += '<li>';
						html += '<div class="li_top">';
						html += '<dl>';
						html += '<dt>';
						html += '<img src='+head+'>';
                		html += '</dt>';
                		username = list[i].userNickName;
                		if(username && username!=''){
                			var userhead = username.substring(0,1);
                			var userend = username.substring(username.length-1,username.length);
                			username = userhead+"*****"+userend;
                		}
                		html += '<dd>'+username+'</dd>';
                		html += '</dl>';
                		html += '<div class="li_top_rt">'+list[i].creatTimeString+'</div>';
                		html += '</div>';
						if(list[i].star1<2){
							html += '<div class="li_top_lt star1"></div>';							
						} else if(list[i].star1<3) {
							html += '<div class="li_top_lt star2"></div>';					
						} else if(list[i].star1<4) {
							html += '<div class="li_top_lt star3"></div>';					
						} else if(list[i].star1<5) {
							html += '<div class="li_top_lt star4"></div>';					
						} else {
							html += '<div class="li_top_lt star5"></div>';
						}
						html += '<p>'+list[i].text +'</p>';
						var imageList = list[i].images;
						if(imageList && imageList!=null){
						html +='<div class="comment_img_box">';
						for (var j = 0; j < imageList.length; j++) {
							if(imageList[j]!=''){
								html+='<image src="'+imageList[j]+'">';
							}
						}
                        html +='</div>';
						}
						html += '</li>';
					}
					html = '<div class="comment_top"><p>用户评价</p><a href="javaScript:toComments()">全部评价</a></div>'
						+ '<ul>'+html+'</ul>'
			    	$(".comment_box").html(html);
					$(".comment_box").show();
			    	
				}else{
					$(".comment_box").hide();
				}			
			}
		},
		error : function() {}
	});
}

function toComments(){
	location.href="comments_m_v2.html?productId="+productId+"";
}