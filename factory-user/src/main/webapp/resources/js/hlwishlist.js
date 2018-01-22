
var pageSize=12;//每页显示多少页  
$(function(){
	getWishOrder(1);
	getselectableOrder(1);
	
	//点击编辑
	$(".bianji").click(function(){
		$(this).hide();
		$(this).attr('data-index',1);
		$(".wancheng,.quxiao").show();
		$(".list_con ul li").unbind();
		$(".list_con ul li .add_box").show();
	});
	
	//点击取消
	$(".quxiao").click(function(){
		$(this).hide();
		$(".wancheng").hide();
		$(".bianji").show();
		$(".bianji").attr('data-index',0);
		liHoverShowDetail();
	});
	
	//点击完成 
	$(".wancheng").click(function(){
		$(this).hide();
		$(".bianji").attr('data-index',0);
		$(".quxiao").hide();
		$(".bianji").show();
		liHoverShowDetail();
	});

	// 删除已选
    $(".list_con .add_yx_box").live('click',function(){
		var proId = $(this).attr("data-id");
		delselected(proId);
		$(this).parent().remove();
		if($(".list_con .add_yx_box").length==0) {
			getWishOrder(1);
		} else {
			var cnt =parseInt($("#hidCheckedCnt").val())-1;
			$("#hidCheckedCnt").val(cnt);
			$("#checkedList").html('已选商品（'+cnt+'）');
		}
    });
    
	// 移入已选
    $(".list_con .add_bx_box").live('click',function(){
		var proId = $(this).attr("data-id");
		savehiw(proId);
		$(this).parent().remove();
		if($(".list_con .add_bx_box").length==0) {
			getselectableOrder(1);
		} else {
			var cnt =parseInt($("#hidSelectableCnt").val())-1;
    		$("#hidSelectableCnt").val(cnt);
    		$("#selectableOrder").html('可选商品（'+cnt+'）');
		}
    });
})

function liHoverShowDetail() {
	$(".list_con ul li").unbind();
	$(".list_con ul li").hover(function(){
		$(this).find(".mengceng").show();
		$(this).find(".list_pro_detail").show();
	},function(){
		$(this).find(".mengceng").hide();
		$(this).find(".list_pro_detail").hide();
	});
	
	$(".list_con ul li .add_box").hide();
}

//移除已选商品
function delselected(id){
	if(id && id!=null){
		$.ajax({
			url:"/member/delectSelected?id="+id,
			cache:false,
			async:false,
			success: function(data){
				//getWishOrder(1);
				getselectableOrder(1);
				//window.location.reload();
			}
		});
	}
}
//添加可选商品
function savehiw(proId){
	$.ajax({
	    dataType: 'json',
	    url:"/member/selectedProduct",
	    cache:false,
	    async:false,
	    data:{"productId":proId},
	    success: function(data){
	    	getWishOrder(1);
	    	//getselectableOrder(1);
	    	//window.location.reload();
	    }
	});
}
//查询已选商品
function getWishOrder(p){
	$.ajax({
	    dataType: 'json',
	    url:"/member/getWishOrder?page="+p+"&pageSize="+pageSize,
	    cache:false,
	    success: function(data){
	    	var result = data.data.list;
	    	var pager = data.data.pager;
	    	var totalPage = pager.pageCount;
	    	var html = '';
	    	if(result.length<=0){
	    		$("#nowish").show();
	    		$("#checkedList").hide();
	    		$("#checkedList ul").hide();
	    	}else{
	    		$("#nowish").hide();
	    		$("#hidCheckedCnt").val(pager.recordCount);
	    		$("#checkedList").html('已选商品（'+pager.recordCount+'）');
		    	for (var i = 0; i < result.length; i++) {
					html +='<li>';
					html +='<em><img src="'+result[i].imagePath+'" /></em>';
					html +='<div class="mengceng"></div>';
					html +='<div class="list_pro_detail" >';			
					html +='<div class="detail_a"><a href="'+basePath+result[i].productId+'.html?skuId='+result[i].skuId+'&pageKey=huanling" title=""  target="_blank">'+result[i].productName+'</a></div>';
					html +='<span class="span1">单价：￥'+result[i].salePrice+'</span>';
					html +='<span class="span2">库存：'+result[i].stock+'</span>   ';    					
					html +='</div>';
					html +='<input type="hidden" id="id'+i+'" value="'+result[i].id+'">';
					html +='<div data-id="'+result[i].id+'" class="add_box add_yx_box"></div>';
					html +='</li>';
				}
	    	}
	    	$(".lt ul").html(html);
	    	$('.M-box1').pagination({  
                pageCount: totalPage,  
                current:p,//当前第几页  
                jump: false,  
                coping: false,  
                homePage: '',  
                endPage: '', 
                isHide: true,
                count:3,
                prevContent: '上一页',  
                nextContent: '下一页',  
                callback:PageClick  
            }); 
	    	if($(".bianji").attr('data-index')==1){
				$(".list_con ul li .add_box").show();
	    	} else {
	    		liHoverShowDetail();
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {  
            alert('网络连接异常，请重试！')  
		}
	})
}
//查询可选商品
function getselectableOrder(p){
	$.ajax({
	    dataType: 'json',
	    url:"/member/getselectableOrder?page="+p+"&pageSize="+pageSize,
	    cache:false,
	    success: function(data){
	    	var result = data.data.list;
	    	var pager = data.data.pager;
	    	var totalPage = pager.pageCount;
	    	var html = '';
	    	if(result.length<=0){
				$("#noSelect").show();
				$("#selectableOrder").hide();
				$("#selectableOrder ul").hide();
	    	}else{
				$("#noSelect").hide();
	    		$("#hidSelectableCnt").val(pager.recordCount);
	    		$("#selectableOrder").html('可选商品（'+pager.recordCount+'）');
	    		
		    	for (var i = 0; i < result.length; i++) {
					html +='<li>';
					html +='<em><img src="'+result[i].image+'" /></em>';
					html +='<div class="mengceng"></div>';
					html +='<div class="list_pro_detail">';			
					html +='<div class="detail_a"><a href="'+basePath+result[i].productId+'.html?skuId='+result[i].skuId+'&pageKey=huanling" title=""  target="_blank">'+result[i].name+'</a></div>';
					html +='<span class="span1">单价：￥'+result[i].salePrice+'</span>';
					html +='<span class="span2">库存：'+result[i].stock+'</span>   ';    					
					html +='</div>';
					html +='<div class="add_box add_bx_box" data-id="'+result[i].productId+'"></div>';
					html +='</li>';
				}
	    	}
	    	$(".rt ul").html(html);
	    	$('.M-box').pagination({  
                pageCount: totalPage,  
                current:p,//当前第几页  
                jump: false,  
                coping: false,  
                homePage: '',  
                endPage: '', 
                isHide: true,
                count:3,
                prevContent: '上一页',  
                nextContent: '下一页',  
                callback:Page2Click  
            }); 
	    	//判断是否为编辑状态是编辑状态
	    	if($(".bianji").attr('data-index')==1){
				$(".list_con ul li .add_box").show();
	    	} else {
	    		liHoverShowDetail();
	    	}
	    },
	    error: function (XMLHttpRequest, textStatus, errorThrown) {  
            alert('网络连接异常，请重试！')  
		}
	})
}
//回调函数    
PageClick = function(index){
	getWishOrder(index.getCurrent());//点击分页加载列表数据  */
	} 
Page2Click = function(index){  
	getselectableOrder(index.getCurrent());//点击分页加载列表数据  */  
	} 