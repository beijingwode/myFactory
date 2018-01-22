    	$(document).ready(function(){
    		selectedHeaderLi("spgl_header");
		});
    	
    	/**
    	*第一级的单击事件
    	*/
    	function checkli1(obj,id){
    		//添加选中状态
			$(obj).siblings("li").removeClass("current").end().addClass("current");
			ajaxLoadNext(id,2);
			$("#ul3").html("");
			$("#span1").html($(obj).text());
			$("#span2").html("");
			$("#span3").html("");
			$("#categoryid").val("");
    	}
    	
    	/**
    	*第二级的单击事件
    	*/
    	function checkli2(obj,id){
    		//添加选中状态
			$(obj).siblings("li").removeClass("current").end().addClass("current");
			ajaxLoadNext(id,3);
			$("#span2").html("<em>> </em>"+$(obj).text());
			$("#span3").html("");
			$("#categoryid").val("");
    	}
    	
    	/**
    	*第二级的单击事件
    	*/
    	function checkli3(obj,id){
    		//添加选中状态
			$(obj).siblings("li").removeClass("current").end().addClass("current");
			$("#span3").html("<em>> </em>"+$(obj).text());
			$("#categoryid").val(id);
    	}
    	
    	
    	function refreshCategory() {
    		var shopId = $("#shopId").val();
    		var brandId = $("#brandId").val();
    		
    		if(shopId != jsShopId || brandId != jsBrandId) {
	    		window.location.href = jsBasePath+"/product/toSelectProducttype.html?productId="+jsProductId+"&shopId="+shopId+"&brandId="+brandId;
    		}
    		
    	} 
    	
    	/**
    	*动态格式化下一级的类目
    	*/
    	function ajaxLoadNext(parentid,order){
    		
    		var shopId = $("#shopId").val();
			$.ajax({
				url : jsBasePath +'/productCategory/getProductCategoryList.json?shopId='+shopId+'&pids='+parentid+"&order="+order,
				type : "GET",
				dataType: "json",  //返回json格式的数据  
			    async: true,
				success : function(data) {
					var html = "";
					if(data.result.errorCode==0){
						if(data.result.msgBody.length>0){
							for(var i=0;i<data.result.msgBody.length;i++){
								html+='<li id="'+data.result.msgBody[i].id+'" onclick="checkli'+order+'(this,'+data.result.msgBody[i].id+');"><a href=\"javascript:void(0);\">'+data.result.msgBody[i].name+'</a></li>';
							}
						}else{
							
						}
					}
					$("#ul"+order).html(html);
				}, error : function() {    
			     }  
			});
    	}
    	
    	/**
    	*跳转到新增商品页面
    	*/
    	function toCreateproduct(){
    		var shopId = $("#shopId").val();
    		var categoryid = $("#categoryid").val();
    		var brandId = $("#brandId").val();
    		if(categoryid==""){
    			showInfoBox("请选择第三级类目!");
    		}else{
	    		window.location.href = jsBasePath+"/product/toCreateProduct.html?shopId="+shopId+"&categoryid="+categoryid+"&brandId="+brandId;
    		}
    	}
    	/**
    	*跳转到修改页面
    	*/
    	function toUpdateproduct(){
    		var categoryid = $("#categoryid").val();
    		var brandId = $("#brandId").val();
    		var shopId = $("#shopId").val();
    		if(categoryid==""){
    			showInfoBox("请选择第三级类目!");
    		}else{
	    		window.location.href = jsBasePath+"/product/toCreateProduct.html?productId="+jsProductId+"&categoryid="+categoryid+"&brandId="+brandId+"&shopId="+shopId;
    		}
    	}
    	
    	/**
    	*快速查找定位
    	*/
    	function searchtype(){
    		var searchname = $("#searchname").val();
    		var $ul1=$("#ul1").find("li");
    		var $ul2=$("#ul2").find("li");
    		var $ul3=$("#ul3").find("li");
    		if(searchname!=""){
    			if($ul3.length>0){
        			$ul3.each(function(i,val){
        				if($($ul3[i]).find("a").text().indexOf(searchname)>=0){
        					var id = $($ul3[i]).attr("id");
        					checkli3($($ul3[i]),id);
        					return  false;
        				}
        			});
        		}else{
        			if($ul2.length>0){
        				$ul2.each(function(i,val){
        					if($($ul2[i]).find("a").text().indexOf(searchname)>=0){
            					var id = $($ul2[i]).attr("id");
            					checkli2($($ul2[i]),id);
            					return false;
            				}
            			});
        			}else{
        				if($ul1.length>0){
            				$ul1.each(function(i,val){
            					if($($ul1[i]).find("a").text().indexOf(searchname)>=0){
                					var id = $($ul1[i]).attr("id");
                					checkli1($($ul1[i]),id);
                					return false;
                				}
                			});
            			}
        			}
        		}
    		}
    	}
