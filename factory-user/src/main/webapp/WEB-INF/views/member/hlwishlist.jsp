<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
	String path = request.getContextPath();
	if(request.getServerPort() != 80 && request.getServerPort() != 443) {
		path=":"+request.getServerPort()+path;
	}
	String basePath = request.getScheme()+"://"+request.getServerName()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-个人中心</title>
<link rel="stylesheet" href="<%=basePath %>css/Personal.css" type="text/css" />
<link rel="stylesheet" href="<%=basePath %>css/my_hl.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=basePath %>css/pagination/pagination.css" media="screen">
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/application.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/pagination/jquery.pagination.js"></script>
<script type="text/javascript">
	var basePath = '<%=basePath %>';
</script>
</head>
<body>
<%@ include file="../common/header_03.jsp" %>
<div class="Me_wrap">
<%@ include file="menu.jsp" %>
<div class="Me_content">
<%@ include file="my_hlb.jsp" %>
        <div class="change_list"><a href="javascript:;" class="bianji" data-index="0">编辑</a><a href="javascript:;" class="wancheng">完成</a><a href="javascript:;" class="quxiao">取消</a></div>
        
        <div class="list_box">
        
        	<div class="list_con lt" >
        		<p style="display:none;" id="nowish">心愿单还没有商品哦~</p>
        		<div class="list_tit" id="checkedList">已选商品（）</div>
        		<input type="hidden" id="hidCheckedCnt" value="0">
        		<ul>
        		</ul>
        		<!-- 分页码开始 -->
			      <div class="page">
     				 <div class="M-box1"></div> 
      			</div>
			      <!-- 分页码结束 -->
        	</div>
        	<div class="list_con rt" >
        		<p style="display:none;" id="noSelect">没有可选商品哦~</p>
        		<div class="list_tit" id="selectableOrder">可选商品（）</div>
        		<input type="hidden" id="hidSelectableCnt" value="0">
        		<ul>
        		</ul>
        		<!-- 分页码开始 -->
			      <div class="page">
      				<div class="M-box"></div> 
     			 </div>
			      <!-- 分页码结束 -->
        		
        	</div>
        	
        	
        </div>
        
 	</div>
 </div>	
 <div class="clear"></div>
<%@ include file="../common/footer.jsp" %>
</body>
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/hlwishlist.js"></script>
<script type="text/javascript">
	$(function(){
		$("#v2").addClass("crr");
		$("#v1").removeClass("crr");
	})
</script>
</html>
