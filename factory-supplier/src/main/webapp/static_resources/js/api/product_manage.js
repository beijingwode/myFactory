
var uid=GetUidCookie();
var shopId=getShopIdCookie();
var isload = true;
var sellStatus;
// JavaScript Document
$(function(){
	if(isWeiXinOpen()) {
		loginCheck('shop8');
	}
	$("#pageNum").val("0");
	sellStatus="selling";//默认在售中
	//顶部菜单切换
	$(".main_top ul li").each(function(index){
		$(this).click(function(){//先把所有的隐藏掉
			$(".main_top ul li").removeClass("thisOne");
			$(this).addClass("thisOne");
			if (index==0) {//在售中
				sellStatus="selling";
				$("#batch").html('批量下架');
				$("#batch").show();
				//提示框修改
				$(".theme-tit").html('批量下架');
				$("#content").val('确认下架这些商品吗？');
			}else if(index==1){//待上架
				sellStatus="waitsell";
				$("#batch").html('批量上架');
				$("#batch").show();
				//提示框修改
				$(".theme-tit").html('批量上架');
				$("#content").val('确认上架这些商品吗？');
			}else if(index==2){//待审核
				sellStatus="waitcheck";
				$("#batch").hide();
			}else{//有问题
				sellStatus="reject";
				$("#batch").hide();
			}
			$(".bm_btn").hide();
			init();
		});
	});	
	//批量切换
	$("#batch").toggle(
		function(){
			$(".commodity_con ul li span").hide();
			$(".commodity_con ul li em").show();
			$(".bm_btn").show();
		},
		function(){
			$(".commodity_con ul li span").show();
			$(".commodity_con ul li em").hide();
			$(".bm_btn").hide();	
		}
	);
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
				init();
			}
		}
	}
	document.addEventListener('WeixinJSBridgeReady', function onBridgeReady() {
	    // 通过下面这个API隐藏右上角按钮
	    WeixinJSBridge.call('hideOptionMenu');
	});
	
	init();
})
function init(){
	if(uid == "") return;
	var pages = parseInt($("#pageNum").val()) + 1;
	if(pages<1) return;
	$.ajax({
		url:jsBasePath+'app/product/getProductlist.user?uid='+uid+'&shopId='+shopId+'&selltype='+sellStatus+'&pages='+pages,
		type : "GET",
		async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		var data2 = data.data;
		    	var result = data2.result;
		    	var html='';
		    	if (result.errorCode==0) {//有数据
					var msgBody = result.msgBody;
					if (msgBody&&msgBody.length>0) {
						for (var i = 0; i < msgBody.length; i++) {
							html+='<li>';
							html+='<dl>';
							html+='<dt>';
							if (msgBody[i].image!=null&&msgBody[i].image!="") {
								if (sellStatus=="selling") {//在售
									html+='<a href="http://api.wd-w.com/productm?productId='+msgBody[i].id+'"><img src="'+msgBody[i].image+'" /></a>';
								}else{
									html+='<img src="'+msgBody[i].image+'" />';
								}
							}else{
								html+='<img src="'+jsBasePath+'images/shop_dt.png" />';
							}
							if(msgBody[i].saleKbn==1) {
								html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon2.png" /></div>';
							} else if(msgBody[i].saleKbn==2) {
								html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_c2.png" /></div>';
							} else if(msgBody[i].saleKbn==4) {
								html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_z2.png" /></div>';
							} else if(msgBody[i].saleKbn==5) {
								html +='<div class="picon"><img src="'+jsBasePath+'static_resources/images/picon_t2.png" /></div>';
							}
							html+='</dt>';
							if (msgBody[i].savestate!=1) {//商品信息完整
								if (msgBody[i].locked!=undefined && msgBody[i].locked==1) {//锁
									html+='<a href="javascript:showLockSeason(\''+msgBody[i].lockReason+'\');">';
								}else if(msgBody[i].saleKbn==2){//换领商品
									html+='<a href="javascript:void(0);">';
								}else{
									html+='<a href="javascript:go2UpdateProduct(\''+msgBody[i].id+'\');">';
								}
							}else{//信息不完整
								html+='<a href="javascript:void(0);">';
							}
							if (msgBody[i].locked!=undefined && msgBody[i].locked==1) {//锁
								html+='<dd class="dd1"><img src="'+jsBasePath+'static_resources/images/lock.png">'+msgBody[i].fullName+'</dd>';
							}else{
								html+='<dd class="dd1">'+msgBody[i].fullName+'</dd>';
							}
							if (msgBody[i].minprice==null||msgBody[i].minprice=="") {
								msgBody[i].minprice='_';
							}
							if (msgBody[i].maxprice==null||msgBody[i].maxprice=="") {
								msgBody[i].maxprice='_';
							}
							html+='<dd class="dd2">￥'+msgBody[i].minprice+'-'+msgBody[i].maxprice+'</dd>';
							html+='<dd class="dd3">全部库存：'+msgBody[i].allnum+'</dd>';
							if (sellStatus=='waitsell'||sellStatus=='waitcheck') {//待售或者待审核
								if (msgBody[i].savestate==1) {//商品信息不完整或是换领商品
									html+='<dd class="dd4">商品信息不足，请到网页端补充</dd>';
								}else if(msgBody[i].saleKbn==2){
									html+='<dd class="dd4">换领商品，请到网页端修改</dd>';
								}
							}
							html+='</a>';
							html+='</dl>';
							if (sellStatus=='selling'&&msgBody[i].locked!=1) {//在售
								html+='<span onclick="go2SellOff(\''+msgBody[i].id+'\')">下架</span>';
							}else if(sellStatus=='waitsell'){//待售
								if (msgBody[i].savestate!=1&& msgBody[i].saleKbn!=2) {
									html+='<span onclick="go2SellOn(\''+msgBody[i].id+'\')">上架</span>';
								}
							}else if(sellStatus=='reject'){//有问题
								html+='<span onclick="go2CKReason(\''+msgBody[i].id+'\')">查看原因</span>';
							}
							if (msgBody[i].savestate!=1 && msgBody[i].locked!=1 && msgBody[i].saleKbn!=2) {//商品信息不完整
								html+='<em onclick="toggleSel(this,\'item\')"><input type="hidden" name="productItem"><input type="hidden" name="productId" value="'+msgBody[i].id+'"></em>';
							}
							html+='</li>';
						}
						$('.commodity_con ul').html(html);
					}
				}else{
					$('.commodity_con ul').html(html);
				}
		    	
			}
	    },
	    error : function() {}
	})
}
//下架提示框
function go2SellOff(id){
	//提示框修改
	$(".theme-tit").html('商品下架');
	$("#content").val('确认下架这个商品吗？');
	$("#sure").attr("href","javascript:ofCourse("+id+");");
	$(".recharge_money").show();
	$(".add_money-mask").show();
}

//上架下架提示框
function go2SellOn(id){
	$(".theme-tit").html('商品上架');
	$("#content").val('确认上架这个商品吗？');
	$("#sure").attr("href","javascript:ofCourse("+id+");");
	$(".recharge_money").show();
	$(".add_money-mask").show();
}
function ofCourse(ids){
	if (sellStatus=='selling') {//在售
		toSellOff(ids);
	}
	if (sellStatus=='waitsell') {//待售
		toSellOn(ids);
	}
	$(".recharge_money").hide();
	$(".add_money-mask").hide();
}
//选中
function toggleSel(obj,type) {
	if ('item'==type) {//点击单选
		var sel=$(obj).children("input[name='productItem']");
		if ($(sel).val()==1) {
			$(sel).val("0");
			$(obj).removeClass("em1");
		}else{
			$(sel).val("1");
			$(obj).addClass("em1");
		}
	}
}
function go2batch(){//提示框显示
	if (sellStatus=="selling") {
		$(".theme-tit").html('批量下架');
		$("#content").val('确认下架这些商品吗？');
	}else if(sellStatus=="waitsell"){
		$(".theme-tit").html('批量上架');
		$("#content").val('确认上架这些商品吗？');
	}
	$(".recharge_money").show();
	$(".add_money-mask").show();
	
}

function go2Close(){//取消
	$(".recharge_money").hide();
	$(".add_money-mask").hide();
}

function go2Sure(){//确定
	var $listproduct=$('input[name="productItem"]');
	var $listpId=$('input[name="productId"]');
	var productIdList="";
	for (var i = 0; i < $listproduct.length; i++) {
		if ($($listproduct[i]).val()=="1") {
			productIdList +=$($listpId[i]).val()+",";
		}
	}
	if (productIdList=='') {
		showInfoBox("请勾选商品");
	}else{
		if (sellStatus=='selling') {//在售
			toSellOff(productIdList);
		}
		if (sellStatus=='waitsell') {//待售
			toSellOn(productIdList);
		}
	}
	$(".recharge_money").hide();
	$(".add_money-mask").hide();
}
//上架
function toSellOn(id){
	if(uid == "") return;
	$.ajax({
		url:jsBasePath+'app/product/ajaxSellOn.user?uid='+uid+'&ids='+id,
		type : "GET",
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		showInfoBox("上架成功")
				setTimeout(refresh, 1500)
			}
	    },
	    error : function() {}
	})
}
//下架
function toSellOff(id){
	if(uid == "") return;
	$.ajax({
		url:jsBasePath+'app/product/ajaxSellOff.user?uid='+uid+'&ids='+id,
		type : "GET",
	    async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		showInfoBox("下架成功");
				setTimeout(refresh, 1500)
			}
	    },
	    error : function() {}
	})
}
//查看原因
function go2CKReason(id){
	if(uid == "") return;
	$.ajax({
		url:jsBasePath+'app/product/getProductCheckById.user?uid='+uid+'&productId='+id,
		type : "GET",
	   	async: false,
	    cache:false,
	    success : function(data) {
	    	if (data.success) {
	    		var result = data.data;
	    		var product = result.msgBody;
	    		if (result.errorCode==0) {
	    			var page='';
		    		page+='<div class="theme-tit">未通过审核原因</div>';
		    		page+='<div class="theme-input">';
		    		page+='<p>商品：'+product.fullName+'</p>';
		    		var opinion = product.opinion;
		    		if (opinion.length>18) {
		    			opinion=opinion.substring(0,15);
		    			opinion+='...';
					}
		    		page+='<p>原因：'+opinion+'</p>';
		    		page+='</div>';
		    		page+='<div class="theme-popbod">';
		    		page+='<span onclick="go2Close()">确定</span>';
		    		page+='</div>';
		    		$(".recharge_money").html(page);
		    		go2batch();
				}
	    		
			}
	    },
	    error : function() {}
	})
}
//修改商品页面
function go2UpdateProduct(id){
	if(uid == "") return;
	window.location=jsBasePath+'app/product/updateProduct?uid='+uid+'&id='+id;
}
//商品详情页面
function go2ProductDetail(id){
	window.location=+'app/product/updateProduct?uid='+uid+'&id='+id;
}
function refresh(){
	//window.location.reload();
	closeMsg();
	$('.commodity_con ul').html('');
	init();
}
function showLockSeason(lockSeason){//显示锁原因
	showInfoBox("属性锁定不能修改,原因:"+lockSeason);
	setTimeout("closeMsg()", 1500);
}
