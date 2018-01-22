// JavaScript Document
var totalPage=1;//总共多少页
var totalRecords=1;//总共多少条 
var pageSize=$("#pageSize").val();//每页显示多少页  
$(document).ready(function() {
	$(".hl_con_box ul").html("");
    //loadList(1);
	//getMoreData(1);
	findMoreData(1);
});
function GetQueryString(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return decodeURI(r[2]); return null;
}
function getMoreData(p) {
	var key =GetQueryString("key");
	if(key && key!=null & key!=''){
	}else{
		key='';
	}
	var searchKey="";
	var moreKey= $("#moreKey").val();
	var moreVal= $("#moreVal").val();
	if(moreKey!="" && moreVal!="" ) {
		searchKey = moreKey +"="+moreVal + "&";
	}
	if(key!=''){
		$(".searchinput").val(decodeURI(key));
	}
	$.ajax({
		type: "POST",
		url: "/product/jsonPagingSearch?"+searchKey+"key="+key+"&page="+p+"&pageSize="+pageSize,
		dataType: "json",  
		success: function(data){
			if(data.success){
				var result = data.data;
				var hits = result.hits;
				var html = '';
				totalPage = result.totalPage;//总共多少页  
			    totalRecords = result.totalNum;//总共多少条  
				if(hits && hits.length>0){
					for(var i=0;i<hits.length;i++) {
						var name = hits[i].name;
						html +='<li>';
						html +='<dl>';
						html +='<dt><a href="/'+hits[i].productId+'.html?skuId='+hits[i].minSkuId+'&pageKey='+$("#pageKey").val()+'" target="_blank"><img src="'+hits[i].image+'"/></a></dt>'
						html +='<dd class="dd1">'+hits[i].brand+'</dd>';
						html +='<dd class="dd2"><a href="/'+hits[i].productId+'.html?skuId='+hits[i].minSkuId+'&pageKey='+$("#pageKey").val()+'" target="_blank">'+name+'</a></dd>';
						html +='<dd class="dd3"><span>单价：¥'+hits[i].salePrice+'</span><i><img src="images/huanlingbi_icon.png"></i></dd>';
						html +='<dd class="dd4">库存：'+hits[i].stock+'</dd>';
						html +='</dl>';
						html +='</li>';
					}
				}
				$(".hl_con_box ul").append(html);
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
	                callback:PageClick  
	            }); 
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {  
            alert('网络连接异常，请重试！')  
		}  
	});
}
//回调函数    
PageClick = function(index){  
    getMoreData(index.getCurrent());//点击分页加载列表数据 
	} 
function findMoreData(page){
	var key =GetQueryString("key");
	if(key && key!=null & key!=''){
	}else{
		key='';
	}
	var searchKey="";
	var moreKey= $("#moreKey").val();
	var moreVal= $("#moreVal").val();
	if(moreKey!="" && moreVal!="" ) {
		searchKey = moreKey +"="+moreVal + "&";
	}
	if(key!=''){
		$(".searchinput").val(decodeURI(key));
	}
	$.ajax({
		type: "POST",
		url: "/product/jsonSearch?"+searchKey+"key="+key+"&page="+page,
		dataType: "json",
		async: false,
	    cache:false,
		success: function(data){
			if(data.success){
				var hits = data.data;
				//var hits = result.hits;
				var html = '';
				if(hits && hits.length>0){
					for(var i=0;i<hits.length;i++) {
						var name = hits[i].name;
						html +='<li>';
						html +='<dl>';
						html +='<dt><a href="/'+hits[i].productId+'.html?skuId='+hits[i].minSkuId+'&pageKey='+$("#pageKey").val()+'" target="_blank"><img src="'+hits[i].image+'"/></a></dt>'
						html +='<dd class="dd1">'+hits[i].brand+'</dd>';
						html +='<dd class="dd2"><a href="/'+hits[i].productId+'.html?skuId='+hits[i].minSkuId+'" target="_blank">'+name+'</a></dd>';
						html +='<dd class="dd3"><span>单价：¥'+hits[i].salePrice+'</span><i><img src="images/huanlingbi_icon.png"></i></dd>';
						html +='<dd class="dd4">库存：'+hits[i].stock+'</dd>';
						html +='</dl>';
						html +='</li>';
					}
				}
				$(".hl_con_box ul").append(html);
				if(hits.length == 20) {
					setTimeout("findMoreData("+(++page)+");", 3000);
					
				}
			}
		},
		error : function() {}
	});
}