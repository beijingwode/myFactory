function drawBrands(size) {
	var html='';
	for(var i=0;i<size;i++) {
		
		html += '<li>';
		html += '<div class="flip"><img src="'+brandJson[i].img+'" class="front" /></div>';
		html += '<div class="mask_layer">';
		html += '	<span></span>';
		html += '	<a href="'+brandJson[i].link+'" target="_blank">全场<em>'+brandJson[i].ttl+'</em>折起</a>';
		html += '</div>';
		html += '</li>';
	}
	html += '<input type="hidden" id="brandSize" value="'+size+'">';
	html += '<input type="hidden" id="brandIndex" value="'+size+'">';
	
	//热门品牌
	$(".brand_con_middle ul").html(html);	
	$(".brand_con_middle ul li .mask_layer").hide();	
	$(".brand_con_middle ul li").hover(function(){
		$(this).find(".mask_layer").stop().fadeTo(500,0.9);		
	},
	function(){
		$(this).find(".mask_layer").stop().fadeTo(500,0);		
	});
}

function xunhuan(){
	var size = parseInt($("#brandSize").val());
	var index = parseInt($("#brandIndex").val());
	var clms = 9;
	if(size==29) clms=6;
	
	//每行5个 开始换内如
	var i=0;
	//第一行立即执行
    lineCycle(0,clms,index,size);
    //每隔0.1秒执行下一行
	setInterval(function() {
		i++;
		if (i < 5) {
			lineCycle(i * clms, (i+1)*clms,index,size);
		} else {
			clearInterval();
		}
	}, 100);

	if(index+size >= brandJson.length) {
		$("#brandIndex").val(index+size-brandJson.length);
	} else {
		$("#brandIndex").val(index+size);
	}
}

function changeBrand(i,index,size){
	if(i<size) {
		var brand;
		if(index+i >= brandJson.length) {
			brand=brandJson[index+i - brandJson.length];
		} else {
			brand=brandJson[index+i];
		}

	    var li = $(".brand_con_middle ul li:eq("+i+")");
		//链接
	    li.find("a").attr("href",brand.link);
		//折扣
	    li.find("a em").html(brand.ttl);

		//图片
	    if(li.find('.flip').hasClass('flip1'))
		{
	    	if(isSafari()) {
			    li.find(".back").fadeOut();
			    li.find(".front").fadeIn();
	    	}
		    li.addClass("flip2");
		    li.find(".front").attr('src',brand.img); 
	        li.find('.flip').removeClass("flip1");
	    	
		} else {

	        if(li.find('.back').length>0)
	        {
	        	li.find(".back").attr('src',brand.img); 
	        }else{
	        	li.find("img").after('<img src="'+brand.img+'" class="back" alt="">');   
	        }

	    	if(isSafari()) {
			    li.find(".back").fadeIn();
			    li.find(".front").fadeOut();
	    	}
		    li.addClass("flip2");
	        li.find('.flip').addClass("flip1");
		}
	}
}

function isSafari() {
	try{
		var userAgent = navigator.userAgent.toLowerCase();
		if (userAgent.indexOf("safari") > -1) {
			return true;
		}
		
		return false;
	} catch (e) {
		return false;
	}
}
//每隔0.1秒更换一个品牌
function lineCycle(b,e,index,size){
	setInterval(function() {
		if (b < e) {
			changeBrand(b,index,size);
			b++;
		} else {
			clearInterval();
		}
	}, 100);
}