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
<meta name="viewport" content="width=divice-width,initial-scale=1">
<title>分类</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/public.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/classify_style.css" />
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
${localHtml}
</head>
<body>
<div class="content">
	<div class="menu_list">
	  <ul>
        <c:forEach items="${root.data}" var="root" varStatus="status">
       	  <c:if test="${status.count eq 1}">
           <li class="active"><a id="${root.id }" href="javascript:rootClick(${root.id })">${root.name}</a></li>
       	  </c:if>
       	  <c:if test="${status.count ne 1}">
        	<li><a id="${root.id }" href="javascript:rootClick(${root.id })">${root.name}</a></li>
   		  </c:if>
        </c:forEach>
      </ul>
    </div>
    <div class="product_list">
      <div class="banr"><img src="<%=static_resources %>images/fenlei_03.png" /></div>
      	<div id="info">
        <c:forEach items="${categoryInfo}" var="secondNode">
      	<div class="pro_cont">
        	<h2>${secondNode.name}</h2>
            <div class="products">
	        	<ul>
            	<c:forEach items="${secondNode.childrens}" var="thirdNode" varStatus="status">
            		<li>
            			<a href="javascript:getObjName('${thirdNode.name}');"><dl><dt>
            				
                       	<c:if test="${empty thirdNode.image}">
                       		<img src="<%=static_resources %>images/fenlei_p1.png" />
                        </c:if>
                       	<c:if test="${not empty thirdNode.image}">
                       		<img src="${thirdNode.image}" />
                       	</c:if>
                     	</dt><dd>${thirdNode.name}</dd></dl></a>
                     </li>
            	</c:forEach>
                </ul>
            </div>
        </div>
       	</c:forEach>
        </div>
   	</div>
</div>
</body>

<script type="text/javascript">

function rootClick(rootId){
	//清空栏目点击变红的效果
	$("#"+rootId).parents("ul").find("li").removeClass("active");
	//添加栏目点击变红的效果
	$("#"+rootId).parent().addClass("active");
	
	//异步请求显示加载的图片
	$(".loading_img").show();
	$.ajax({
		dataType:'json',
		type:'POST',
		url:'<%=basePath %>category/categoryInfo',
		async:false,
		data:{"pid":rootId},
		success:function(data){
			//异步请求成功，并返回值关闭加载图片
			$(".loading_img").hide();
			 //删除class为pro_cont的所有div,然后遍历新的节点信息
			 $(".pro_cont").remove();
			 $.each(data,function(index,item){
				 var nodeStr = "<div class=\"pro_cont\"><h2>"+item.name+"</h2><div class=\"products\"><ul>";
					 $.each(item.childrens,function(index,child){
						 var img;
						 if(null==child.image){
							 img= "<%=static_resources %>images/fenlei_p1.png";
						 }else{
							 img=child.image;
						 }
						 nodeStr+="<li><a href=\"javascript:getObjName('"+child.name+"')\"><dl><dt>";
						 nodeStr+="<img src=\""+img+"\" />";
						 nodeStr+="</dt><dd>"+child.name+"</dd></dl></a></li>";
					 });
					 nodeStr+="</ul></div></div>";
				 $("#info").append(nodeStr);
			});
		}
	});
}

function getObjName(name){
	try{
		Toast.show(name);
	} catch(e) {}

	window.location = "http://iOS/2/" + name;
}
</script>
</html>
