<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!doctype html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利</title>
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/common.css">
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/information.css">
<style>
.loading_img {position: absolute;top:105px;display:none; left:470px;}
</style>
</head>
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/shopping.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/util.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	//点击栏目触发事件
	$(".channel_url").click(function(){
		//清空栏目点击变红的效果
		$(this).parents("ul").find("li").removeClass("active");
		//添加栏目点击变红的效果
		$(this).parent().addClass("active");
		//channelId 栏目id
		var channelId = $(this).attr("id");
		//删除ajax请求之前栏目和栏目对应的信息
		$(".am-news-list-content-item").find("*").remove();
		$(".info-page").attr("style","display: none");
		//异步请求显示加载的图片
		$(".loading_img").show();
		$.ajax({
			dataType:'json',
			type:'POST',
			url:'/cms/pageInfo',
			async:false,
			data:{"channelId":channelId,"pages":1},
			success:function(data){
				//异步请求成功，并返回值关闭加载图片
				$(".loading_img").hide();
				if(!$.isEmptyObject(data.content)){
					$(".info-page").attr("style","display: block");
					$.each(data.content,function(index,item){
						var v = "/cmsContent?id="+item.id+"&channelId="+item.channelid;
						$(".am-news-list-content-item").append("<li><h2><a href="+v+" title=\"\">"+item.title+"</a></h2><div class=\"am-text-sm-gray\">"+(new Date(item.creatdate).format("yyyy-MM-dd hh:mm:ss"))+"</div><div class=\"am-text-sm\">"+item.txt+"</div></li>");
					});
					//修改分页信息
					updatePaging(data);
					
				}else{
					$(".am-news-list-content-item").append("<li style=\"text-align: center;\">没有数据...</li>");
					$(".info-page").attr("style","display: none");
				}
			}
		});
		
	});
	
});
//修改分页信息
function updatePaging(data){
	$("#totalNumber").html("共"+data.totalNumber+"条");
	$("#pages").html(data.pages+"/"+data.totalPages);
	if(data.pages==1){
		$("#prev").addClass("disabled");
		$("#prev").attr("onclick","");
	}else{
		$("#prev").attr("class","");
		$("#prev").attr("onclick","cmsContentPage("+(data.pages-1)+","+data.channelId+")");
	}
	
	if(data.pages==data.totalPages){
		$("#next1").addClass("disabled");
		$("#next1").attr("onclick","");
		$("#next").remove();
	}else{
		$("#next1").attr("class","");
		$("#next").remove();
		$("#next1").attr("onclick","cmsContentPage("+(data.pages+1)+","+data.channelId+")");
	}
}

	//分页
	function cmsContentPage(page,channelId){
		
		//异步请求显示加载的图片
		$(".loading_img").show();
		$.ajax({
			dataType:'json',
			type:'POST',
			url:'/cms/pageInfo',
			async:false,
			data:{"channelId":channelId,"pages":page},
			beforeSend : function() {
			},
			success:function(data){
				//删除ajax请求之前栏目和栏目对应的信息
				$(".am-news-list-content-item").find("*").remove();
				
				//异步请求成功，并返回值关闭加载图片
				$(".loading_img").hide();
				$.each(data.content,function(index,item){
					var v = "/cmsContent?id="+item.id+"&channelId="+item.channelid;
					$(".am-news-list-content-item").append("<li><h2><a href="+v+" title=\"\">"+item.title+"</a></h2><div class=\"am-text-sm-gray\">"+(new Date(item.creatdate).format("yyyy-MM-dd hh:mm:ss"))+"</div><div class=\"am-text-sm\">"+item.txt+"</div></li>");
				});
				//修改分页信息
				updatePaging(data);
				
			}
		});
	}
		
</script>
<body>
<!--top begin-->
<%@ include file="/common/header.jsp" %> 
<!--top end-->
<!--content begin-->
<div id="content">
	<div class="position">
    	<a href="/index.html">首页</a><em>></em><a href="javascript:">资讯</a>
    </div>
    
    <div class="detail_wrap" style="min-height:480px">
    	<div class="left">
        	<div class="info_list">
            	<ul>
            	<c:forEach items="${channelInfo}" var="ch" varStatus="status">
            		<c:if test="${status.count eq 1}">
                	<li class="active"><a class="channel_url" id="${ch.id}" href="#">${ch.name}</a></li>
            		</c:if>
            		<c:if test="${status.count ne 1}">
                	<li><a class="channel_url" id="${ch.id}" href="#">${ch.name}</a></li>
            		</c:if>
               	</c:forEach>
                </ul>
            </div>
        </div>
        <div class="right" style="width:980px; position:relative;"><!-- style="width:980px; position:relative;" -->
			
        	<ul class="am-news-list-content-item" style="min-height:190px;"><!--  style="min-height:190px;" -->
        	
        	<c:if test="${empty content.content}">
	        	<li style="text-align: center;">没有数据...</li>
			</c:if>
        	<c:forEach items="${content.content}" var="con">
                <li>
                    <h2><a href="/cmsContent?id=${con.id}&channelId=${con.channelid}" title="">${con.title}</a></h2>
                    <div class="am-text-sm-gray"><fmt:formatDate value="${con.creatdate}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate></div>
                    <div class="am-text-sm">${con.txt}</div>
                </li>
            </c:forEach>
            </ul>
            
            <div>
				<img class="loading_img" id="loading" src="../images/loading_small.gif"/>
			</div>
			
            <div class="info-page" >
            <c:if test="${content.totalNumber > 0}">
                <span id="totalNumber">共${content.totalNumber}条</span>
                <span id="pages">${content.pages}/${content.totalPages}</span>
                
                <span><a id="prev" href="#" class="disabled">前一页</a></span>
                
                <c:if test="${content.pages eq content.totalPages}">
                <span><a id="next" href="#" class="disabled">后一页</a></span>
                </c:if>
                <c:if test="${content.pages ne content.totalPages}">
                <span><a id="next1" href="#" onclick="cmsContentPage(${content.pages+1},${content.channelId})" >后一页</a></span>
                </c:if>
             </c:if>
            </div>
            
            
        </div>
    </div>
</div>
<!--content end-->

<!--footer begin-->
<%@ include file="/common/footer.jsp"%>
<!--footer end-->

</body>
</html>
