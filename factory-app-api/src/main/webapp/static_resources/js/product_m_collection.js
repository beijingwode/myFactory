function ajaxGetCollectProduct(){
    $.ajax({
        url : jsBasePath +'collectProduct/check.user?uid='+uid+'&productId='+productId,
        type : "GET",
        dataType: "json",  //返回json格式的数据
        async: true,
        success : function(data) {
            if (data.success && data.data) {
                $("#collection").val(1);
                $("#collection").after('<img src="'+jsBasePath+'static_resources/images/TogetherToBuy/collect_btn_img.png" />');
            }else{
                $("#collection").after('<img src="'+jsBasePath+'static_resources/images/TogetherToBuy/no_collection.png"/>');
            }
            //收藏按钮点击效果
        	$(".slides_top").on("tap",function(e){
        		  toggleCollectProduct();
        	});
        },
        error : function() {}
    });
}

function toggleCollectProduct(){
	if(uid=="") return;
	if($("#collection").val()==1){
		$.ajax({
			url : jsBasePath +'collectProduct/delete.user?uid='+uid+'&productIdList='+productId,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				if (data.success) {
					$("#collection").val(0);
					$(".collect_btn img").attr("src",$(".collect_btn img").attr("src").replace("collect_btn_img.png","no_collection.png"));
				}
			},
			error : function() {}
		});
	} else {
		$.ajax({
			url : jsBasePath +'collectProduct/add.user?uid='+uid+'&productId='+productId,
			type : "GET",
			dataType: "json",  //返回json格式的数据  
		    async: true,
			success : function(data) {
				if (data.success) {
					  $("#collection").val(1);
					  $(".collect_btn img").attr("src",$(".collect_btn img").attr("src").replace("no_collection.png","collect_btn_img.png"));
				}
			},
			error : function() {}
		});
	}
}