var isload = true;
var uid = GetUidCookie();
// JavaScript Document
$(document).ready(function() {
	$("#pageNum").val("0");
	ajaxShopData();
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
				
				ajaxShopData();
			}
		}
	}
});
/*function selall() {
	if ($("#sel_all").val()==1) {
		$("#sel_all").val("0");//取消
	}else{
		$("#sel_all").val("1");//选中
	}
	
	if ($("#sel_all").val()==1) {
		//选中
		$(".all_btn em").css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon1.png) no-repeat","background-size":"14px 14px"});
		$(".shop_con input[name='shopItem']").val("1");
		$(".shop_con em").css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon1.png) no-repeat","background-size":"14px 14px"});
	}else{
		//取消
		$(".all_btn em").css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon2.png) no-repeat","background-size":"14px 14px"});
		$(".shop_con input[name='shopItem']").val("0");
		$(".shop_con em").css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon2.png) no-repeat","background-size":"14px 14px"});
	}
}

function toggleSel(obj,type) {
	if ('item'==type) {//点击单选
		var sel=$(obj).children("input[name='shopItem']");
		if ($(sel).val()==1) {
			$(sel).val("0");
			$(obj).css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon2.png) no-repeat","background-size":"14px 14px"});
		}else{
			$(sel).val("1");
			$(obj).css({"background":"url("+jsBasePath+"static_resources/images/checkbox_icon1.png) no-repeat","background-size":"14px 14px"});
		}
	}
	
}*/
var userSupplierId = getSupplierIdCookie();
function ajaxShopData(){
	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	if(uid=='') return;
	$.ajax({
		url : jsBasePath+'collectShop/list.user?uid='+uid+'&page='+ page+'&pageSize='+10,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: true,
		success : function(data) {
			var result = data.data.list;
			if(result.length>0) {
				$("#pageNum").val(page);
				var html='';
				for(var i=0;i<result.length;i++) {
					html +='<div class="sl-li ui-border-b">';
					html +='<dl class="sl-content">';
					html +='<dt><a href="javascript:go2Shop(\''+result[i].id+'\');">';
					if (result[i].logo){
						html +='<img src="'+result[i].logo+'"/>'
					}else{
						html +='<img src="'+jsBasePath+"static_resources/images/good_no_load.png"+'"/>'
					}
					html +='</a></dt>';
					if (userSupplierId !='' && userSupplierId==result[i].supplierId) {
						html +='<dd><a href="javascript:go2Shop(\''+result[i].id+'\');">自家</a></dd>';
					}else{
						html +='<dd><a href="javascript:go2Shop(\''+result[i].id+'\');">'+result[i].shopname+'</a></dd>';
					}
					html +='</dl>';
					html +='<div class="sl-opts">';
					html +='<span style="background-color: #F95F61; color: #fff;" onclick="delShop(\''+result[i].id+'\')">删除</span>';
					html +='</div>';
					//html +='<em onclick="toggleSel(this,\'item\')";><input type="hidden" name="shopItem"><input type="hidden" name="shopId" value="'+result[i].id+'"></em>';
					html +='</div>';
				}
				$(".main-box").append(html);
				jQuery.getScript(jsBasePath+"static_resources/js/slideleft.js");//动态加载js
			}else {
				$("#pageNum").val(-1);
				var html='';
				html +='<div class="bottom_hint">已经浏览到最后了</div>';
				$(".main-box").append(html);
			}
			isload = true;
		},
		error : function() {}
	});
}
function go2Shop(shopId){
	window.location = jsBasePath +'shop/page?shopId='+shopId;
}
function delShop(shopIdList){//取消关注
	
	/*var $listShop=$('input[name="shopItem"]');
	var $listShopId=$('input[name="shopId"]');
	var shopIdList="";
	for (var i = 0; i < $listShop.length; i++) {
		if ($($listShop[i]).val()=="1") {
			shopIdList +=$($listShopId[i]).val()+",";
		}
	}*/
	$.ajax({
		url : jsBasePath+'collectShop/delete.user?uid='+uid+'&shopIdList='+shopIdList,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data) {
			if (data.success) {
				refresh();
			}else{
				showInfoBox(data.msg)
			}
		}
	});
}
function refresh(){
	location.reload();
}