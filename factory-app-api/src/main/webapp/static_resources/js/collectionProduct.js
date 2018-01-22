var isload = true;
var uid = GetUidCookie();
// JavaScript Document
$(document).ready(function() {
	$("#pageNum").val("0");
	ajaxProductData();
	$(function(){
		$(window).scroll(function(){
			Load();
		});
	});
	function Load(){
		if(isload){//ajax在后台获取数据时，设值其false，防止页面多次加载
			var loadHeight = 115;//指定滚动条距离底部还有多少距离时进行数据加载
			var documentHeight = parseInt($(document).height(),10);//可视区域当前高度
			var windowHeight = parseInt(window.innerHeight,10);//窗口当前高度
			var scrollHight = parseInt($(window).scrollTop(),10);//窗口滚动条位置
			if(documentHeight - scrollHight - windowHeight < loadHeight){
				//ajax获取数据，以下为模拟
				isload = false;
				
				ajaxProductData();
			}
		}
	}
	//点击编辑
	$(".top_box span").toggle(
	 function(){			
		$(this).html("保存");					
		$(".shop_bottom").show();
		$(".like_con ul li dl dt em").show();
		},
	 function(){			  
		$(this).html("编辑");					
		$(".shop_bottom").hide();
		$(".like_con ul li dl dt em").hide();
	    }
	);

});
function selall() {
	if ($("#sel_all").val()==1) {
		$("#sel_all").val("0");//取消
	}else{
		$("#sel_all").val("1");//选中
	}
	
	if ($("#sel_all").val()==1) {
		//选中
		$(".all_btn em").css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon1.png) no-repeat","background-size":"22px 22px"});
		$(".like_con ul li dl dt input[name='productItem']").val("1");
		$(".like_con ul li dl dt em").css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon1.png) no-repeat","background-size":"22px 22px"});
	}else{
		//取消
		$(".all_btn em").css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon2.png) no-repeat","background-size":"22px 22px"});
		$(".like_con ul li dl dt input[name='productItem']").val("0");
		$(".like_con ul li dl dt em").css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon2.png) no-repeat","background-size":"22px 22px"});
	}
}

function toggleSel(obj,type) {
	if ('item'==type) {//点击单选
		var sel=$(obj).children("input[name='productItem']");
		if ($(sel).val()==1) {
			$(sel).val("0");
			$(obj).css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon2.png) no-repeat","background-size":"22px 22px"});
		}else{
			$(sel).val("1");
			$(obj).css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon1.png) no-repeat","background-size":"22px 22px"});
		}
	}
	
}


function ajaxProductData(){
	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	if(uid=='') return;
	$.ajax({
		url : jsBasePath+'collectProduct/list.user?uid='+uid+'&page='+ page+'&pageSize='+6,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var result = data.data.list;
			var cost = data.msg;
			if(result.length>0) {
				$("#pageNum").val(page);
				var html='';
				for(var i=0;i<result.length;i++) {
					html +='<li>';
					html +='<dl>';
					html +='<dt>';
					if(result[i].saleKbn==1) {
						html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon2.png" /></div>';
					} else if(result[i].saleKbn==2) {
						html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_c2.png" /></div>';
					} else if(result[i].saleKbn==4) {
						html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_z2.png" /></div>';
					}else if(result[i].saleKbn==5) {
						html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_t2.png" /></div>';
					}
					html +='<a href="javascript:go2Product(\''+result[i].id+'\');"><img src="'+result[i].image+'"></a>';
					html +='<em onclick="toggleSel(this,\'item\')";><input type="hidden" name="productItem"><input type="hidden" name="productId" value="'+result[i].id+'"></em>';
					html +='</dt>';
					html +='<dd class="dd1"><a href="javascript:go2Product(\''+result[i].id+'\');">'+result[i].name+'</a></dd>';
					if(result[i].maxprice>cost){
						result[i].maxprice=cost;
					}
					if (result[i].maxprice!=null && result[i].maxprice>0) {//
						html +='<dd><span>￥'+(parseFloat(result[i].showPrice).toFixed(2))+'+'+parseFloat(result[i].maxprice).toFixed(2)+'券</span></dd>';
					}else{
						if(result[i].empLevel!=null && result[i].empLevel!=''){
							html +='<dd><span>￥'+(parseFloat(result[i].empPrice).toFixed(2))+'</span></dd>';	
						}else{
							html +='<dd><span>￥'+(parseFloat(result[i].showPrice).toFixed(2))+'</span></dd>';
						}
					}
					html +='<dd class="dd3"><span>电商价：￥'+(parseFloat(result[i].marketPrice).toFixed(2))+'</span></dd>'
					html +='</dl>';
					html +='</li>';
				}
				$("#like_li").append(html);
			}else {
				$("#pageNum").val(-1);
				var html='';
				html +='<div class="bottom_hint">已经浏览到最后了</div>';
				$(".main-box").append(html);
			}
			isload = true;
		},
		error : function(e) {
			alert(JSON.stringify(e))
		}
	});
}
function go2Product(productId){
	window.location = jsBasePath+'productm?productId='+productId+'&from=a&pageKey=collection';
}
function delProduct(){//取消关注
	
	var $listproduct=$('input[name="productItem"]');
	var $listpId=$('input[name="productId"]');
	var productIdList="";
	for (var i = 0; i < $listproduct.length; i++) {
		if ($($listproduct[i]).val()=="1") {
			productIdList +=$($listpId[i]).val()+",";
		}
	}
	if (productIdList=='') {
		showInfoBox("请勾选商品！")
	}else{
	$.ajax({
		url : jsBasePath+'collectProduct/delete.user?uid='+uid+'&productIdList='+productIdList,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				//window.location=jsBasePath+'collectProduct/page';
				refresh();
			}else{
				showInfoBox(data.msg)
			}
		}
	});
	}
}
function refresh(){
	location.reload();
}