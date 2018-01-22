// JavaScript Document


    $("#keyword").attr("autocomplete","off");	
    
    
   function rememberKey(){
    	var text = $('#keyword').val();
    	var searchHis = "";
    	if($.cookie('searchHis')){
    		searchHis =$.cookie('searchHis');
    	}
    	searchHis = removeOld(searchHis,text);
    	setCookie("searchHis",encodeURI(text)+","+searchHis);
    	//$(".search_history1 ul").prepend('<li><span>'+text+'</span><a href="javascript:;" >搜索历史</a></li>');
   }
   
   function removeKey(text) {
	   	var searchHis = "";
		if($.cookie('searchHis')){
			searchHis =$.cookie('searchHis');
		}
		searchHis = removeOld(searchHis,text);
		setCookie("searchHis", searchHis);
		cookieShow();
	}
	function removeAllKey() {
		setCookie("searchHis", "");
		cookieShow();
	}
	function setCookie(name, value) {
		var expdate = new Date();
		expdate.setTime(expdate.getTime() + 24 * 60 * 60 * 1000 * 180);
		document.cookie = name + "=" + value + ";expires=" + expdate.toGMTString()
				+ ";path=/";
	}

	function removeOld(str,n){
		str=","+str;
		str=encodeURI(str);
		n=encodeURI(n);
		var p=str.indexOf(","+n+",");
		if(p>-1){
			var left=str.substring(0,p);
			var right=str.substring(p+n.length+1);
			str =left+right;
		}
		return str.substring(1);
	}
    function cookieShow(){
		var text = $.cookie('searchHis'); 
		if(text){
			if($.trim(text)=="") {
				$(".search_history").hide();
				return;
			}
		} else {
			$(".search_history").hide();
			return;
		}
		console.log(text);
		var textArray = text.split(',');
		if(textArray.length > 0 ){
			console.log($(".search_history").length);
			if($(".search_history").length==0){
				$(".main_con").append('<div class="search_history" style="display:block;"></div>');
			}
			var html = ''; 
			html+='<p>历史记录</p>';
			html+='   <ul>';
			for(var i=0;i<textArray.length-1;i++){
				if(textArray[i] != ''){
					html+='<li  onclick="goSearch(this)"><span>'+textArray[i]+'</span><a href="javascript:removeKey(\''+textArray[i]+'\');" >删除</a></li>';
				}
			}
			html+='   </ul>';;
			html += '<div class="search_history_del"><a href="javascript:;">全部删除</a></div>';
			$(".search_history").html(html);
			$(".search_history").show();
			
			$(".search_history ul li").mouseover(function(){
			     $(this).find("a").html("删除");
			 });

			 $(".search_history ul li a").unbind("click");
			 $(".search_history ul li a").click(function(e){
				 e.stopPropagation();
		    	 removeKey(text);
			 });
			 $(".search_history_del").click(function(){
				 removeAllKey();
			 });		    
		}
	}
   
    
   
     
	$("#keyword").on("focus",function(e){
		 if($("#keyword").val() != ""){
			 $(".search_history").show();
		 } 
	});
	$("#keyword").on("blur",function(){
   		 if($("#keyword").val() != ""){
   			// $(".search_history").hide();
   		 }
   	})
   

	$("#keyword").bind("keyup",function(){
		 changeKey();
	});
	 
	
	function changeKey() {
		var key =$("#keyword").val();
		if(key=="") {
			cookieShow();
		} else {
			suggest(key);
		}
	}
	
	function suggest(key){
		$.ajax({
			url:"search/dd?key="+key,
			dataType : 'json',
			success: function(result){
				//window.status=JSON.stringify(result);
				//$(".roof_left span").html(key+JSON.stringify(result));
				
				var html="";
				
				html += '<ul>';
				for (var i = 0; i < result.length; i++) {
					html+='<li onclick="goSearch(this)">';
					//html+='<li>';
					html+='<span>'+result[i].title+'</span><em>约'+result[i].cnt+'个商品</em>';
					html+='</li>';
				}
				html += '</ul>';
				//html += '<div class="search_history_close"><a href="javascript:;">关闭</a></div>';
				
				if($(".search_history").length>0){
					$(".search_history").html(html);
				} else {
					$(".main_con").append('<div class="search_history" style="display:block;">'+html+'</div>');
				}
				if(result.length>0){
					$(".search_history").show();
				}else {
					$(".search_history").hide();
				}
				$(".search_history_close a").click(function(){
					$(".search_history").hide();
				});	
				
				
				/*$(".search_history ul li").each(function(){
					//$(this).hover(function(){
					//	var spanHtml=$(this).find("span").text();
					//	$(".searchinput").val(spanHtml);
					//});
					$(this).mousedown(function(){
						var spanHtml=$(this).find("span").text();
						$(".searchinput").val(spanHtml);
						$('#search_form').submit();
						$(".search_history").hide();
					});
				})*/
				
				
				
				
			 },
			 error:function(e){
				 alert(JSON.stringify(e));
			}
		});
	}
	
	
	//$(".main_con").mouseleave(function(){		
		//var timeout;
		 //clearTimeout(timeout);
		 //timeout = setTimeout(function(){
			 $(".search_history").hide();
			 $("#keyword").blur();
		 //},4000);
	//});
	
	function goSearch(obj){
		var title = $(obj).find("span").text();
		//alert(title);
		$("#keyword").val(title);
		rememberKey();
		$(".search_history").hide();
		
		go2Search(mkParam(title));
		
	}
	
	function mkParam(key) {
		var param = window.location.href;
		var p=param.indexOf("?");
		if (p>-1){
			param=param.substring(p+1);
		} else {
			param="";
		}
		
		p=param.indexOf("key=");
		if(p>-1){
			var left=param.substring(0,p);
			var right=param.substring(p+4);
			p=right.indexOf("&");
			if(p>-1) {
				right=right.substring(p+1);
			} else {
				right="";
			}
			
			param=left+right;
		}
		if(param!="") {
			param="key="+key+"&"+param;
		} else {
			param="key="+key;
		}
		
		return param;
	}
	
	
	
	
	
	
	
	
	
	
	
	