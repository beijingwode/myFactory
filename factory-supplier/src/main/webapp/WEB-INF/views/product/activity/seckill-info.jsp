<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html>
<head>
<title>我的网商家中心-活动详情</title>
<script language="javascript" type="text/javascript" src="<%=static_resources %>resources/My97DatePicker/WdatePicker.js"></script>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform"%>
<%@ include file="/commons/js.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/jquery.form.js"></script>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
	<div class="h-position">
        <span>您所在的位置：</span>
        <a href="javascript:void(0);">活动管理</a><em>></em>
        <a href="javascript:void(0);">秒杀活动详情</a>
    </div>
        
	<!--left begin-->
    <div class="left">
    	<img src="<%=static_resources %>images/login_img.jpg" width="200" height="200">
    </div>
    <!--left end-->
    
    <!--right begin-->
    <div class="right">    	
        <div class="merchant_info">
        	<div class="seckill">
            	<h2>秒杀活动申请</h2>
                <ul class="seckill-list">
                    <li>
                    	<div class="active-time">活动上线时间：</div>
                    	<div class="active-str">
                        	<p>每日00：01上线</p>
                            <p>每日23：59截止</p>
                        </div>
                    </li>
                    <li class="Li01">活动发起人：<span>平台发起</span></li>
                    <li class="Li02">已参与人数：${promotion.joinTotal}</li>
                    <li class="Li03"><a class="sec" href="javascript:;" onfocus="WdatePicker({
                    <c:if test="${!empty promotionProductList}">
                    disabledDates:[
                    <c:forEach var="item2" varStatus="status" items="${promotionProductList}">
                    <c:if test="${status.first}">'<fmt:formatDate value="${item2.joinStart}" pattern="yyyy-MM-dd"/>'</c:if> 
                    <c:if test="${!status.first}">,'<fmt:formatDate value="${item2.joinStart}" pattern="yyyy-MM-dd"/>'</c:if> 
                    </c:forEach>
                    ],
                    </c:if>
                    isShowToday:false,autoPickDate:false,isShowClear:false,minDate:'${begin}',maxDate:'${end}',el:'sj',onpicked:function(){toNext();}})">点击报名</a></li>
                </ul>
                <form id="sub_form2" action="${basePath}/promotion/toConfirm.html" method="post">
               		<input type="hidden" name="promotionId" value="${promotion.id}">
               		<input type="hidden" id="sj" name="bmTime">
                </form>
            </div>
            <div class="activity-lb">
            	<div class="activity-list">
                    <ul>
                        <li class="active"><a href="javascript:;">活动介绍</a></li>
                        <li><a href="javascript:;">活动须知</a></li>
                    </ul>
                </div>
                <div class="activity-cont" id="activity-cont0">
                	${promotion.introduction}
                </div>
                <div class="activity-cont" id="activity-cont1">
                	${promotion.notice}
                </div>
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<script type="text/javascript">
		$(document).ready(function(){
			selectedHeaderLi("hdgl_header");
		});
		/***表单提交*/
		function formSubmit(page){
			if(page!=undefined){
				$("#pages").val(page);
			}else{
				$("#pages").val(1);
			}
			$("#sub_form").submit();
		}
		/*** 快速跳转*/
		function gotoPage(){
			var pagenum = $("#pagenum").val();
			formSubmit(pagenum);
		}
		function toNext(){
			$("#sub_form2").submit();
		};
		$ (function ()
	    {
		    $ ('.activity-list li').click (function ()
		    {
			    var that = $ (this);
			    that.addClass ('active').siblings ('li').removeClass ('active');
			    var i = that.index ('.activity-list li');
			    $ ('.activity-cont').hide();
			    $ ('#activity-cont'+i).show();
			    //alert(i);
		    });
	    });

</script>
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/box.jsp" %>
</body>
</html>