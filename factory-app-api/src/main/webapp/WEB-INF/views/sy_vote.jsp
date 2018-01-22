<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>我的福利</title>
</head>
<script type="text/javascript">
	var jsBasePath = '<%=basePath%>';
</script>
<body>
	<div class="main-cont" id="main-cont">
		<div class="top_box">
			<div class="top">
				<h1>
					<a href="javascript:close();" class="aleft"></a> 试用商品投票评价
				</h1>
			</div>
		</div>
		<div class="main-box">
			<dl>
				<dt>
					<div class="picon">
						<img src="<%=static_resources%>images/picon_t2.png" />
					</div>
					<a href="#" target="_blank"><img src="${comment.image}" /></a>
				</dt>
				<dd>
					<a href="#" target="_blank">${comment.productName}</a>
				</dd>
				<dd>
					数 量：${comment.number}
				</dd>
				<dd>
					返现金额：<span class="j_cont price">￥<fmt:formatNumber
							pattern="#0.00" value="${comment.empPrice}" /></span>
				</dd>

			</dl>
			<div class="top_tit">
				<span>请您回答以下问卷，对该商品进行评价</span>
				<p>完成问卷后,返现金额将返还至您的现金券账户中！</p>
			</div>
			<div class="main_con">

				<input type="hidden" id="userId" value="${userId}" /> 
				<input type="hidden" id="subOrderId" value="${comment.subOrderId}" />
				<input type="hidden" id="subOrderItemId" value="${comment.subOrderItemId}" />

				<input type="hidden" id="questionnareId" value="${questionnare.id}" />
				<input type="hidden" id="answers" value="" />
				<c:forEach var="q" items="${questionnare.listQuestion}"	varStatus="cs">
					<div class="vote_item">
						<div class="vote_item_hd">
							<h4 class="vote_title">${cs.index+1}.${q.questionTitle}</h4>
						</div>
						<div class="vote_item_bd">
							<ul class="vote_option_list">
								<c:forEach var="o" items="${q.listOption}" varStatus="os">
									<li class="vote_option">
										<div class="vote_option_msg group">
											<label> 
											  <c:if test="${o.selectType==1}">
												<input type="radio" name="${q.id}" id="${o.id}">
											  </c:if>
											  <c:if test="${o.selectType==2}">
												<input type="checkbox" name="${q.id}" id="${o.id}">
											  </c:if>
											</label>
											<label for="${o.id}"> 
											  <c:if	test="${not empty o.image}">
												<span class="img_panel"> 
													<span class="preview bg_img" style="background-image:url('${o.image}');"></span>
												</span>
											  </c:if>
											  <strong class="vote_option_title" title="${o.optionTitle}">${o.optionTitle}</strong>
											</label>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:forEach>
				<div class="tab_bottom">
					<a href="javascript:saveComment();">完成</a>
				</div>
			</div>
		</div>
	</div>
  
</body>
<script type="text/javascript" src="<%=static_resources %>js/common/system_config.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/wxGetUid.js?1213"></script>
<script type="text/javascript" src="<%=static_resources%>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources%>js/h5_exit.js"></script>

<%@ include file="/commons/alertMessage.jsp" %>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/sy_vote.css" />
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/font.css" />
<script type="text/javascript">
$(document).ready(function(){
	if(isWeiXinH5()) {
		$(".top").hide();
		$(".main-box").css("top","0");
	}
	//左上角返回箭头点击事件  Android返回键
	$("#back").click(function(){
		Toast.show("exit");
	});
});

function saveComment(){
	if(checkInput()) {
		
		//获取用户id
		var userId = $("#userId").val();
		//获取子单id
		var subOrderId = $("#subOrderId").val();
		var qcnt = $("#qcnt").val();
		var array = new Array();

		var comments={};
		comments.anonymous = '1';
		comments.userId = userId;
		//保存子单id
		comments.subOrderid =$("#subOrderId").val();
		//保存子单项id
		comments.subOrderItemId = $("#subOrderItemId").val();
		array.push(comments);
			
		//有评价的商品才回进行ajax提交
		if(array.length>0){
			$.ajax({
				dataType:'json',
				type:'POST',
				url:'<%=basePath%>order/comments/saveComments?uid='+userId+'&questionnareId='+$("#questionnareId").val()+'&answers='+$("#answers").val(),
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
	}
}

function checkInput() {
	var answers="";
	var qs = $(".vote_item");
	
	for(i=0;i<qs.length;i++) {
		var ipts = $(qs[i]).find("input:checked");
		if(ipts.length == 0) {        			
			showInfoBox("请回答问题:"+$(qs[i]).find(".vote_title").html()+"");
			return false;
		}        		

    	for(j=0;j<ipts.length;j++) {
    		answers += $(ipts[j]).attr("id") + ","
    	}
	}
	
	$("#answers").val(answers);
	return true;
}
</script>
</html>
