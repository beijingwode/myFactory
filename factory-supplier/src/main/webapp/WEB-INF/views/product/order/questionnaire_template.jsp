<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<title>我的网商家中心</title>
<%@ include file="/commons/header.jsp" %>
<%@ include file="/commons/js.jsp" %>

<script type="text/javascript">
	var jsBasePath = '${basePath}';
	var jsUserId='${userId}';
	var jsStaticResources='<%=static_resources %>';
</script>

<!--header end-->

<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <ul class="left_list">
             <c:if test="${userSession.type != 3 || userSession.hasAuth('sellList')}">
                 <li><a href="${basePath}/suborder/gotoSelllist.html">已售出的商品</a></li>
             </c:if>
             <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
                 <li><a href="${basePath}/comments/toevaluation.html?commentDegree=all">评价管理</a></li>
             </c:if>
             <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
                 <li class="curr"><a href="${basePath}/questionnaire/templates.html">问卷模板</a></li>
             </c:if>
			 <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
					<li><a href="${basePath}/questionnaire/trialProduct.html?leftMenu=order">试用商品问卷</a></li>
			 </c:if>
        </ul>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/suborder/gotoSelllist.html">订单管理</a><em>></em>
            <a href="${basePath}/questionnaire/templates.html">问卷模板</a><em>></em>
            <a href="JavaScript:void(0);">问卷编辑</a>
        </div>
        <div class="sale_wrap">
            <div class="main_bd main_bd_con">
				<div class="page_msg">
					<i class="icon_msg_mini"></i>模板设置后，可作为收集商品评价反馈的问卷使用，试用商品管理时可选择问卷模板。模板编辑时不影响已发出的问卷。
				</div>
				
				<div class="frm_control_group">
					<label for="" class="frm_label">模板名称</label>
					<div class="frm_controls">
						<input type="hidden" name="id" value="${template.id}" />
						<span class="frm_input_box"><input type="text" placeholder="" class="frm_input"  id="album_name" maxlength="35" name="templateTitle" value="${template.templateTitle}" oninput="checkNum (this)" onpropertychange="checkNum (this)" ><em>0/35</em></span>
						
						<p class="frm_tips">模板名称只用于管理，不显示在下发的问卷内容中</p>
					</div>
				</div>
				<br />
				<p class="frm_tips frm_tips_btm">上传图片的最佳尺寸：300像素*300像素，其他尺寸会影响页面效果，格式png，jpeg，jpg，gif。大小不超过1M  </p>
				
				<div class="">
					<div class="vote_meta_container js_question_container">
						
                	  <c:forEach var="q" items="${template.listQuestion}" varStatus="status">
						<div class="vote_meta option_setting js_question">							
							<div class="vote_meta_title group">
								<div class="vote_meta_title_opr">
									<a href="javascript:;" class="js_question_edit" data-tag="0">收起</a>
									<c:if test="${status.index>0}">
										<a href="javascript:;" class="js_question_delete" data-tag="0">删除</a>
									</c:if>
								</div>
								<span class="vote_warn" style="display:none">问题填写完整才能添加下一个问题</span>
								<span class="vote_num">问题一</span>
								<span class="vote_question js_vote_question"></span>
							</div>
							<div class="vote_meta js_item_container vote_meta_content" style="display:">
								<div class="vote_meta_detail">
									<div class="frm_control_group">
										<label for="" class="frm_label">标题</label>
										<div class="frm_controls">
											<span class="frm_input_box with_counter counter_in append vote_title js_question_title count">
												<input autofocus type="text" placeholder="" class="frm_input js_option_input" name="questionTitle" value="${q.questionTitle}" id="" maxlength="35" oninput="checkNum (this)" onpropertychange="checkNum (this)" ><em class="frm_input_append frm_counter">0/35</em>
											</span>
											<span class="frm_tips"></span>
										</div>
									</div>
								</div>
								<div class="vote_meta_detail js_vote_type vote_meta_radio">
									<div class="frm_control_group">
										<div class="frm_controls vote_meta_radio">
											<label class="vote_radio_label selected frm_radio_label" for="checkbox${status.index*2}">											
												<input name="selectType${status.index}" type="radio" value="1" class="vote_radio frm_radio"<c:if test="${q.selectType ==1}"> checked="checked"</c:if> id="checkbox${status.index*2}">
												<span type="label_content">单选</span>
											</label>
											<label class="vote_radio_label frm_radio_label" for="checkbox${status.index*2+1}">												
												<input name="selectType${status.index}" type="radio" value="2" class="vote_radio frm_radio"<c:if test="${q.selectType ==2}"> checked="checked"</c:if> id="checkbox${status.index*2+1}">
												<span type="label_content">多选</span>
											</label>
										</div>
									</div>	
								</div>
								
                	  		   <c:forEach var="o" items="${q.listOption}" varStatus="statusJ">
								<div class="vote_meta_detail js_vote_option">
									<div class="frm_control_group">
										<div class="frm_label">选项一</div>
										<div class="frm_controls">
											<div class="frm_ht">
												<span class="frm_input_box with_counter counter_in append count">
													<input type="text" placeholder="" class="frm_input js_option_input" name="optionTitle" value="${o.optionTitle}" id="" maxlength="35" oninput="checkNum (this)" onpropertychange="checkNum (this)"><em class="frm_input_append frm_counter">0/35</em>
												</span>
												
												<div class="upload_area webuploader-container">													
													<a class="btn btn_upload js_vote_upload_btn webuploader-pick" id="js_upload_0_0"><c:if test="${not empty o.image}"> 重新上传</c:if><c:if test="${empty o.image}"> 上传图片</c:if></a>
													<c:if test="${statusJ.index>1}">
													<a href="javascript:;" class="link_delete js_delete_item" data-tag="0" data-item="2">删除选项</a>
													</c:if>
													<span class="frm_tips"></span>
												</div>
											</div>
											<div class="js_img_container img_container" id="js_upload_0_0" style="display:<c:if test="${not empty o.image}">block</c:if><c:if test="${empty o.image}">none</c:if>">
												<span class="img_panel">
													<span class="js_img_preview preview bg_img poi" data-src="${o.image}" style="background-image:url(${o.image});"></span>
												</span>
												<a href="javascript:;" class="link_dele" id="js_delete_0_0">删除</a>
											</div>
										</div>
									</div>
								</div>
								</c:forEach>
								
								<div class="vote_meta_detail tips_wrp">
									<p id="voteAdd" class="tips_global option_tips">
										<a href="javascript:;" class="js_add_item" data-tag="0">添加选项</a>
									</p>
									<p id="voteFull" class="tips_global option_tips"  style="display:none;">选项已满，不可继续添加</p>
								</div>
							</div>
						</div>
						</c:forEach>
					</div>
					
					
					<div class="vote_container_dec">
						<a class="btn btn_default btn_add btn_vote_add" href="javascript:addQuestion();" id="js_add_question"><i class="icon14_common add_gray"></i>添加问题</a>
			        	<p id="js_error" style="display:none;" class="frm_tips">问题填写完整才能添加下一个问题</p>
			        </div>
				</div>
				</div>
			
				
				
			<div class="tool_bar"><a href="javascript:submit(0);" class="btn btn_primary js_complete_bnt">完成</a></div>		
		</div>
    </div>
    <!--right end-->
</div>
<!--content end-->

<%@ include file="/commons/alertMessage.jsp" %>
<%@ include file="/commons/footer.jsp" %>

<div style="display:none;" id="tmp-upload_container">
 <input type="file" id="uploadFile" name="file" onchange="fileUpload()" style="position:absolute;filter:alpha(opacity=0);width:56px;height:30px;"  />
</div>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/style.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/new_vote.css">

  
<script type="text/javascript" src="<%=static_resources %>js/product_order_questionnaire_template.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/ajaxfileupload.js"></script>
 
</body>
</html>
