<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
if(request.getServerPort() != 80 && request.getServerPort() != 443) {
	path=":"+request.getServerPort()+path;
}
String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
String static_resources = basePath + "static_resources/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" >
<meta content="telephone=no" name="format-detection" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/reset.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/font.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/plbb_image.css" />

<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<script type="text/javascript" src="<%=static_resources %>js/h5_exit.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/nochange_font.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources %>js/h5upload.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/comments_images.js"></script>
<title>宝贝评论</title>
</head>

<body>
<div class="main-cont" id="main-cont">
	<div class="top">
        <h1><a href="javascript:close();" class="aleft"></a>发表评论</h1>
    </div>
    <div class="main-box">
    <!-- subOrderId -->
    <input type="hidden" value="${subOrderId}" id="subOrderId">
    <input type="hidden" value="${userId}" id="userId">
    <input type="hidden" value="${qcnt}" id="qcnt">
<c:forEach items="${info}" var="pro" varStatus="status">
        <div class="main1-con">       	
            <dl>
                <dt>
                	<c:if test="${pro.suborderitem.saleKbn == 1 }">
                	<div class="picon"><img src="<%=static_resources %>images/picon2.png" /></div>
                	</c:if>
                	<c:if test="${pro.suborderitem.saleKbn == 2 }">
                	<div class="picon"><img src="<%=static_resources %>images/picon_c2.png" /></div>
                	</c:if>
                	<c:if test="${pro.suborderitem.saleKbn == 4 }">
                	<div class="picon"><img src="<%=static_resources %>images/picon_z2.png" /></div>
                	</c:if>
                	<c:if test="${pro.suborderitem.saleKbn == 5 }">
                	<div class="picon"><img src="<%=static_resources %>images/picon_t2.png" /></div>
                	</c:if>
                	<input type="hidden" class="subOrderItemId" value="${pro.subOrderItemId}"/>
                	<a href="#"><img src="${pro.pic}" /></a>
                </dt>
                <dd><span>为商品评分</span>
                	<em id="star" class="product">
                		<a href="#" class="star1"></a>
                		<a href="#"  class="star2"></a>
                		<a href="#"  class="star3"></a>
                		<a href="#"  class="star4"></a>
                		<a href="#"  class="star5" id="success"></a>
                	</em>
                </dd>
                <dd><span>为服务评分</span>
                	<em id="star" class="service">
                		<a href="#"  class="star1"></a>
                		<a href="#"  class="star2"></a>
                		<a href="#"  class="star3"></a>
                		<a href="#"  class="star4"></a>
                		<a href="#"  class="star5" id="success"></a>
                	</em></dd>
                <dd><span>为物流评分</span>
                	<em id="star" class="logistics">
	                	<a href="#" class="star1"></a>
	                	<a href="#"  class="star2"></a>
	                	<a href="#"  class="star3"></a>
	                	<a href="#"  class="star4"></a>
	                	<a href="#"  class="star5" id="success"></a>
                	</em>
                	</dd>
            </dl>
            <div class="pl-box">
                <span>发表评论</span>
                <div class="pl-con">
                     <textarea maxlength="200" placeholder="说点什么吧，您的意见对其他买家的帮助很大哦~~"  id="txt" onkeyup="noEmoji(this)"></textarea>
                </div>
            </div>
           <div class="feedback_pic" id="feedback_pic${status.index}">
              	<ul>
               		<li>
                  		<div class="pic_up">
                  			<a href="javascript:void(0);" onclick="fileSelect(0,this)" data-index="${status.index}"><span>+</span><p>上传凭证<br />最多3张</p></a>
                  		</div>
                  	</li>
                   <li></li>
                   <li></li>
          		</ul>
            </div>
		    <input type="file" id="avatar${status.index}" data-index="${status.index}" style="display: none" accept="image/*" onchange="updateImg(this);">
		    <input type="hidden" id="picLieq${status.index}">
		    <div style="display: none">
   			 <img id="resultImage" />
    		</div>
        </div>
</c:forEach>
        
    </div>
    <div class="bottom-box">
    	<div id="btn_img" class="img01">匿名评价</div>
        <div  id="submit" class="submit-btn">提交</div>
    </div>
</div>

<script>

$(function(){
		//左上角返回箭头点击事件  Android返回键
		$("#back").click(function(){
			Toast.show("exit");
		});
	
		$("#submit").click(function(){
			//匿名
			var anonymous = $("#btn_img").hasClass("img02");
			//获取用户id
			var userId = $("#userId").val();
			//获取子单id
			var subOrderId = $("#subOrderId").val();
			var qcnt = $("#qcnt").val();
			var img = document.getElementsByName("pic_url");
			var images = [];
			for (var i = 0; i < img.length; i++) {
				images.push(img[i].value);
			}
			var array = new Array();
			//获取每个商品div中的星级数据和评论内容
			$(".main1-con").each(function(index){
				//获取子单项id
				var subOrderItemId = $(this).find(".subOrderItemId").val();
				//评论内容
				var txt = $(this).find("#txt").val();
				var comments={}
				// 获取商品评分
				$(this).find(".product a").each(function(p){
					if("success"==$(this).attr("id")){
						comments.star1=p+1;
					}
				});
				// 获取服务评分
				$(this).find(".service a").each(function(s){
					if("success"==$(this).attr("id")){
						comments.star2=s+1;
					}
				});
				// 获取物流评分
				$(this).find(".logistics a").each(function(l){
					if("success"==$(this).attr("id")){
						comments.star3=l+1;
					}
				});
				//是否匿名评论 1是匿名  0是非匿名
				if(anonymous){
					//匿名，useriId将不会被传递
					comments.anonymous = '1';
				}
				comments.userId = userId;
				//保存评论内容
				comments.text=txt;
				//保存子单id
				comments.subOrderid =subOrderId;
				//保存子单项id
				comments.subOrderItemId = subOrderItemId;
				//保存图片
				
				comments.images=images;
				
				
				array.push(comments);
				
			})
			//有评价的商品才回进行ajax提交
			if(array.length>0){
				$.ajax({
					dataType:'json',
					type:'POST',
					url:'<%=basePath %>order/comments/saveComments?uid='+userId,
					contentType: 'application/json',
					async: false,
					cache:false,
					data:JSON.stringify(array),
					success:function(data){
						if(qcnt>0) {
							window.location.reload();
							return;
						}
						if(data.success){
							if(isWeiXinH5()) {
								history.back();
							}else{
								//评论成功  Android修改成功
								try{
									Toast.show(true);
								} catch(e) {
									window.location = "success";
								}
							}
						}else{
							//评论失败  Android修改失败
							try{
								Toast.show(false)
							} catch(e) {
								window.location = "error";
							}
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						//评论失败  Android修改失败
						try{
							Toast.show(false)
						} catch(e) {
							window.location = "error";
						}
					}
				});
			}
			
		})
	});
//获取光标val消失
	var str = "说点什么吧，您的意见对其他买家的帮助很大哦~~";
	$(function(){
		$("#txt").focus(function(){
			$(this).text("");
			
	    });
		
		$("#txt").blur(function(){
			var val = $(this).text();
			if(val=="" || val == null) $(this).text(str);
		});
		
		$("#txt1").focus(function(){
			$(this).text("");
			
	    });
		
		$("#txt1").blur(function(){
			var val = $(this).text();
			if(val=="" || val == null) $(this).text(str);
		});
	});

//评分效果 
  $(function(){
	$(".main1-con dl dd a").each(function(index){
		$(this).click(function(){
			$(this).css("background","url(<%=static_resources %>images/star-on.png) no-repeat center").nextAll().css("background","url(<%=static_resources %>images/star-off.png) no-repeat center"); 
			$(this).css("background","url(<%=static_resources %>images/star-on.png) no-repeat center").prevAll().css("background","url(<%=static_resources %>images/star-on.png) no-repeat center")
			/* $(this).addClass("active");
			$(this).siblings().removeClass("active"); */
			$(this).attr("id","success");
			$(this).siblings().removeAttr("id");
		  });
	})  
  });

//匿名购买背景切换
  $(function(){
		//左上角返回箭头点击事件  Android返回键
		$("#btn_img").click(function(){
		      $(this).toggleClass("img02");
		});
	});
	 function noEmoji(obj) {
  		var reg1=/\uD83C[\uDF00-\uDFFF]|\uD83D[\uDC00-\uDE4F\uDE80-\uDEFF]/g;
  		// var reg2=/([\u00A9\u00AE\u203C\u2049\u2122\u2139\u2194-\u2199\u21A9-\u21AA\u231A-\u231B\u2328\u23CF\u23E9-\u23F3\u23F8-\u23FA\u24C2\u25AA-\u25AB\u25B6\u25C0\u25FB-\u25FE\u2600-\u2604\u260E\u2611\u2614-\u2615\u2618\u261D\u2620\u2622-\u2623\u2626\u262A\u262E-\u262F\u2638-\u263A\u2640\u2642\u2648-\u2653\u2660\u2663\u2665-\u2666\u2668\u267B\u267F\u2692-\u2697\u2699\u269B-\u269C\u26A0-\u26A1\u26AA-\u26AB\u26B0-\u26B1\u26BD-\u26BE\u26C4-\u26C5\u26C8\u26CE-\u26CF\u26D1\u26D3-\u26D4\u26E9-\u26EA\u26F0-\u26F5\u26F7-\u26FA\u26FD\u2702\u2705\u2708-\u270D\u270F\u2712\u2714\u2716\u271D\u2721\u2728\u2733-\u2734\u2744\u2747\u274C\u274E\u2753-\u2755\u2757\u2763-\u2764\u2795-\u2797\u27A1\u27B0\u27BF\u2934-\u2935\u2B05-\u2B07\u2B1B-\u2B1C\u2B50\u2B55\u3030\u303D\u3297\u3299]|\uD83C[\uDC04\uDCCF\uDD70-\uDD71\uDD7E-\uDD7F\uDD8E\uDD91-\uDD9A\uDDE6-\uDDFF\uDE01-\uDE02\uDE1A\uDE2F\uDE32-\uDE3A\uDE50-\uDE51\uDF00-\uDF21\uDF24-\uDF93\uDF96-\uDF97\uDF99-\uDF9B\uDF9E-\uDFF0\uDFF3-\uDFF5\uDFF7-\uDFFF])|(\uD83D[\uDC00-\uDCFD\uDCFF-\uDD3D\uDD49-\uDD4E\uDD50-\uDD67\uDD6F-\uDD70\uDD73-\uDD7A\uDD87\uDD8A-\uDD8D\uDD90\uDD95-\uDD96\uDDA4-\uDDA5\uDDA8\uDDB1-\uDDB2\uDDBC\uDDC2-\uDDC4\uDDD1-\uDDD3\uDDDC-\uDDDE\uDDE1\uDDE3\uDDE8\uDDEF\uDDF3\uDDFA-\uDE4F\uDE80-\uDEC5\uDECB-\uDED2\uDEE0-\uDEE5\uDEE9\uDEEB-\uDEEC\uDEF0\uDEF3-\uDEF6])|(\uD83E[\uDD10-\uDD1E\uDD20-\uDD27\uDD30\uDD33-\uDD3A\uDD3C-\uDD3E\uDD40-\uDD45\uDD47-\uDD4B\uDD50-\uDD5E\uDD80-\uDD91\uDDC0])/g;
		 obj.value=obj.value.replace(reg2,'');
	}
</script>
<script type="text/javascript">
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main-box").css("top","5px");
	}
});
</script>
<%@ include file="/commons/alertMessage.jsp" %>
</body>
</html>
