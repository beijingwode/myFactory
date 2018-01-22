var uid= GetUidCookie();
var isload = true;
$(function(){
	$(".main_top .ul3 li").each(function(index){
		$(this).click(function(){//先把所有的隐藏掉
	 		//$(".tab_box").addClass("dis");
			$(".tab_box").html('');
 			//$(".tab_box:eq("+index+")").removeClass("dis");//在把对应的索引的显示
 			$(".main_top .ul3 li").removeClass("crr");
 			$(this).addClass("crr");
	 		$("#pageNum").val(0);
	 		init();
 		});	
 	});
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true; sessionStorage.removeItem("orderInfo");});
	
	$("#pageNum").val("0");
	init();
	$(function(){
		$(window).scroll(function(){
			Load();
		});
	});
	function Load(){
		if(isload){//ajax在后台获取数据时，设值其false，防止页面多次加载
			var loadHeight = 55;//指定滚动条距离底部还有多少距离时进行数据加载
			var documentHeight = parseInt($(document).height(),10);//可视区域当前高度
			var windowHeight = parseInt(window.innerHeight,10);//窗口当前高度
			var scrollHight = parseInt($(window).scrollTop(),10);//窗口滚动条位置
			if(documentHeight - scrollHight - windowHeight < loadHeight){
				//ajax获取数据，以下为模拟
				isload = false;
				init();
			}
		}
	}
});
function init(){
	var selectID = $(".crr").attr("id");
	if(selectID && selectID=='selectedPro'){
		myFavorites();
	}else if(selectID && selectID=='weiPro'){
		getSelectable();
	}
}
function myhlorder(){
	window.location.href=jsBasePath+"/exchangeOrder/myhl.user?uid="+uid;
}
function myFavorites(){
	if(uid=='') return;
	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	$.ajax({
		url : jsBasePath+"/exchangeOrder/myFavorites.user?uid="+uid+'&page='+page+'&pageSize=10',
		type : "POST",
		dataType : "json",
		cache : false,
		success : function(data){
			var result = data.data.list;
			var pager = data.data.pager;
			var html = '';
			//$("#selectedPro").html('<a href="javascript:void(0)">已选商品（'+pager.recordCount+'）</a>');
			if(result.length>0){
				$("#pageNum").val(page);
				for (var i = 0; i < result.length; i++) {
					html+='<div class="main_con main_con2">';
					html+='<div class="pp_pro sl-li sl-content">';
					html+='<dl>';
					html+='<dt><a href="javascript:;"><img src="'+result[i].imagePath+'" /></a></dt>';
					html+='<dd class="dd1"><a href="javascript:;">'+result[i].productName+'</a></dd>';
					html+='<dd class="dd2" style="height:16px;"></dd>';
					html+='<dd class="dd3"><span>'+result[i].salePrice.toFixed(2)+'</span><i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i></dd>';
					html+='</dl>';
					html+='</div>';
					html+='<div class="sl-opts">';
					html+='<span onclick="delFavorite(\''+result[i].id+'\');" style="background-color: #F95F61; color: #fff;">移出<br />已选</span>';
					html+='</div>';
					html+='</div>';
				}
				$(".tab_box").append(html);
				jQuery.getScript(jsBasePath+"static_resources/js/slideleft.js");//动态加载js
				if(pager.last){
					$("#pageNum").val(-1);
				}
			}else{
				$("#pageNum").val(-1);
				$(".tab_box").append('');
				//jQuery.getScript(jsBasePath+"static_resources/js/slideleft.js");//动态加载js
			}
		}
	})
}
function getSelectable(){
	if(uid=='') return;
	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	$.ajax({
		url : jsBasePath+"/exchangeOrder/getSelectable.user?uid="+uid+'&page='+page,
		type : "POST",
		dataType : "json",
		cache : false,
		success : function(data){
			var result = data.data.list;
			var pager = data.data.pager;
			var html = '';
			//$("#weiPro").html('<a href="javascript:void(0)">备选商品（'+pager.recordCount+'）</a>');
			if(result.length>0){
				$("#pageNum").val(page);
				for (var i = 0; i < result.length; i++) {
					html+='<div class="main_con main_con2">';
					html+='<div class="pp_pro sl-content">';
					html+='<dl>';
					html+='<dt><a href="javascript:;"><img src="'+result[i].image+'" /></a></dt>';
					html+='<dd class="dd1"><a href="javascript:;">'+result[i].name+'</a></dd>';
					html+='<dd class="dd2" style="height:16px;"></dd>';
					html+='<dd class="dd3"><span>'+result[i].salePrice+'</span><i><img src="'+jsBasePath+'static_resources/images/huanlingbi_icon.png" /></i></dd>';
					html+='</dl>';
					html+='</div>';
					html+='<div class="sl-opts">';
					html+='<span onclick="addFavorite(\''+result[i].productId+'\');" style="background-color: #F95F61; color: #fff;">移入<br />已选</span>';
					html+='</div>';
					html+='</div>';
				}
				$(".tab_box").append(html);
				jQuery.getScript(jsBasePath+"static_resources/js/slideleft.js");//动态加载js
				if(pager.last){
					$("#pageNum").val(-1);
				}
			}else{
				$("#pageNum").val(-1);
				$(".tab_box").append('');
				//jQuery.getScript(jsBasePath+"static_resources/js/slideleft.js");//动态加载js
			}
		}
	});
}
/**
 * 改为备选
 * @param id
 * @returns
 */
function delFavorite(id){
	if(uid=='' || id=='') return;
	$.ajax({
		url : jsBasePath+"/exchangeOrder/delFavorite.user?uid="+uid+'&id='+id,
		type : "POST",
		dataType : "json",
		cache : false,
		success : function(data){
			if(data.success){
				showInfoBox("修改成功");
				setTimeout("refresh()", 1000)
			}
		}
	});
}
/**
 * 改为已选
 * @param id
 * @returns
 */
function addFavorite(pid){
	if(uid=='' || pid=='') return;
	$.ajax({
		url : jsBasePath+"/exchangeOrder/addFavorite.user?uid="+uid+'&productId='+pid,
		type : "POST",
		dataType : "json",
		cache : false,
		success : function(data){
			if(data.success){
				showInfoBox(data.msg);
				setTimeout("refresh()", 1000)
			}else{
				showInfoBox(data.msg);
			}
		}
	});
}
/**
 * 刷新页面
 * @returns
 */
function refresh(){
	location.reload();
}