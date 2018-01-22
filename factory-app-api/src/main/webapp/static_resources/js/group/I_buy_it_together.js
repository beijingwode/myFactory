var isload = true;
var uid=GetUidCookie();
$(document).ready(function() {
	$(".tuan_p_con").hide();
	$("#pageNum").val("0");
	ajaxQueryData();
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
				ajaxQueryData();
			}
		}
	}
	var isPageHide = false;           
	window.addEventListener('pageshow', function(){
	if(isPageHide){window.location.reload();}
	});           
	window.addEventListener('pagehide', function(){isPageHide = true;});
	
	//左右滑动
	var mySwiper = new Swiper('.swiper-container',{
	slidesPerView : 'auto',//'auto'
	//slidesPerView : 3.7,
	observer:true,//修改swiper自己或子元素时，自动初始化swiper
	observeParents:true,//修改swiper的父元素时，自动初始化swiper
	})
	if(localStorage.length>0){
		localStorage.clear();//全部清除数据
	}
});
function userinfo(that){
	$(that).parent().hide();
	$(that).parent().next().show();
	/*$("#tuanzhang"+i).hide();
	$("#tuan_p_con"+i).show();*/
}
function ajaxQueryData(){
	var page = parseInt($("#pageNum").val()) + 1;
	if(page<1) return;
	$.ajax({
		url :jsBasePath+'group/query.user?uid='+userId+'&page='+page+'&pageSize='+5,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data){
			if(data.success){
				var result = data.data;
				var list = result.list;
				
				if(list.length>0){
					$("#pageNum").val(page);
					var html = "";
					for(var i=0;i<list.length ;i++) {
						
						html+="<div class='main_top main_top3' style='padding-bottom:20px;'>";
						html+="<div class='tuan_name' ><p class='p1'>团名称:"+list[i].groupName+"</p>";
					 	if(list[i].status == -1) {
					 		html+="<p class='p2'>拼团失败</p></div>";
			            } else if(list[i].status == -2) {
			            	html+="<p class='p2'>拼团失败</p></div>";
			            } else {
			                if(list[i].orderStatus == -1) {
			                	html+="<p class='p2'>拼团失败</p></div>";
			                } else if(list[i].orderStatus == 1) {
			                	html+="<p class='p2'>拼团完成</p></div>";
			                } else if(list[i].orderStatus == 2) {
			                	html+="<p class='p2'>卖家已发货</p></div>";
			                } else if(list[i].orderStatus == 4) {
			                	html+="<p class='p2'>团长已签收</p></div>";
			                } else {
			                	html+="<p class='p2'>拼团中</p></div>";
			                }
			            }
						////////
						html+="<div class='user'>";
						html+="<div class='p_con'>";
						html+="<p class='p1'>团长:"+list[i].commanderName+"<em>"+list[i].phoneNum+"</em></p>";
						html+="<p class='p2'>"+list[i].address+"</p>";
						html+="</div>";			
						html+="</div>";
						
						var groupBuyProductList = list[i].groupBuyProductList;
						html+="<div class='tit'><a href='javascript:goToBuyProduct("+list[i].joinStatus+","+list[i].id+");' id='cj_"+list[i].id+"'>可购商品</a></div>";
						html+="<div class=' swiper-container' >";
						html+="<div class='swiper-wrapper' >";
						if(groupBuyProductList!=null){
							for(var j=0;j<groupBuyProductList.length ;j++) {
								html+="<div class='swiper-slide'><a href='javascript:;'><img src='"+groupBuyProductList[j].image+"'/></a></div>";
							}
						}
						
						html+="</div>";
						html+="</div>";
						if(list[i].joinStatus == "1"){
							html+="<div class='btns' style='width:92%;margin:0 auto;'><a href='javascript:go2APP();'>进入团群聊</a></div>";
						}else{
							html+="<div class='btns' style='width:92%;margin:0 auto;'><a href='javascript:go2JoinGroup("+list[i].id+");'>立即参团</a></div>";
						}
						html+="</div>";
					}
					$("#main-cont").append(html);

					for(var i=0;i<list.length ;i++) {
	    				//alert(JSON.stringify(result[i]));
						$("#cj_"+list[i].id+"").data('selData',JSON.stringify(list[i]));//绑定数据
					}
					mySwiper();
				}else {
					$("#pageNum").val(-1);
					var html='';
					$("#main-cont").append(html);
				}
				isload = true;
			}
		},error : function() {
			alert(err)
		}
	});
}

function mySwiper(){
	//左右滑动
	var mySwiper = new Swiper('.swiper-container',{
	slidesPerView : 'auto',//'auto'
	//slidesPerView : 3.7,
	observer:true,//修改swiper自己或子元素时，自动初始化swiper
	observeParents:true,//修改swiper的父元素时，自动初始化swiper
	})
}

function goToBuyProduct(status,groupId){
	if(status != "1"){
		alert("请点击立即参团，参团后才可以购买商品");
		return;
	}
	var data =$("#cj_"+groupId).data('selData');
	sessionStorage.setItem("groupBuy",data);
	window.location = jsBasePath +'group/toCanBuy.user?groupId='+groupId+"&type="+1+"&uid="+uid;
}
function go2JoinGroup(groupId){
	$.ajax({
		url :jsBasePath+'group/userMsgUpdate.user?groupId='+groupId+"&uid="+uid,
		type : "GET",
		dataType: "json",  //返回json格式的数据  
	    async: false,
	    cache:false,
		success : function(data){
			if(data.success){
				window.location = jsBasePath +'group/toCanBuy.user?groupId='+groupId+"&type="+1+"&uid="+uid;
			}
		},error : function() {
			alert(err)
		}
	});
}
function go2APP(){
	window.location = "http://www.wd-w.com/app.htm?d=1";
}

function subString(str, len) { 
	var str_length = 0;  
	   var str_len = 0;  
	      str_cut = new String();  
	      str_len = str.length;  
	      for(var i = 0;i<str_len;i++)  
	     {  
	        a = str.charAt(i);  
	        str_length++;  
	        if(escape(a).length > 4)  
	        {  
	         //中文字符的长度经编码之后大于4  
	         str_length++;  
	         }  
	         str_cut = str_cut.concat(a);  
	         if(str_length>=len)  
	         {  
	         str_cut = str_cut.concat("...");  
	         return str_cut;  
	         }  
	    }  
	    //如果给定字符串小于指定长度，则返回源字符串；  
	    if(str_length<len){  
	     return  str;  
	    }  
}  