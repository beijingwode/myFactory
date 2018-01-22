<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>	
<%@ include file="../common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利--订单评价</title>
<link rel="stylesheet" href="${basePath}/css/Personal.css" type="text/css" />
<script type="text/javascript" src="<%=basePath %>resources/js/jquery1.8.0.js"></script>
  <link rel="stylesheet"  href="${basePath}/css/sy_eviews.css" type="text/css" />
</head>
<body>
    <!--top begin-->
    <%@ include file="../common/header_03.jsp" %>
    <!--top end-->
	<form action="${basePath}/member/saveComment" id="form${comment.subOrderItemId}" method="post">
    	<input type="hidden" name="subOrderid" value="${comment.subOrderId}"/>
    	<input type="hidden" name="supplyid" value="${comment.supplierId}"/>
    	<input type="hidden" name="productId" value="${comment.productId}"/>
    	<input type="hidden" name="attributeJson" value="<c:out value='${comment.itemValues}'/>"/>
    	<input type="hidden" name="subOrderItemId" value="${comment.subOrderItemId}"/>
    	<input type="hidden" name="anonymous" value="1"/>
    	
    	<input type="hidden" name="questionnareId" value="${questionnare.id}"/>
    	<input type="hidden" name="answers" value=""/>
    </form>
    <div class="mydd_pj_wrap">
   		<div class="picon"><img src="../images/picon_t1.png" /></div>
        <div class="jp_product"><a href="/${comment.productId}.html?pageKey=order" target="_blank"><img src="${comment.image }" width="160" height="160" alt="preview_pic01"></a></div>
        <div class="jp_info">
            <p><a href="/${comment.productId}.html?pageKey=order" target="_blank">${comment.productName}</a></p>
            <ul class="jp_lb">
                <li>
                	<span class="j_name">数&nbsp;&nbsp;&nbsp;量：</span>
                	<span class="j_cont">${comment.number}</span>
                </li>
              	<c:if test="${!empty comment.companyTicket}">
	                <li>
	                    <span class="j_name">使用内购券：</span>
	                    <span class="j_cont">${comment.companyTicket}券</span>
	                </li>
                </c:if>
                <li>
                    <span class="j_name">实付金额：</span>
                    <span class="j_cont">￥<fmt:formatNumber pattern="#0.00" value="${comment.realPay}"/>
                    <c:if test="${comment.benefitAmount > 0}">+<fmt:formatNumber value="${comment.benefitAmount}" pattern="#0.00"/>惠</c:if>
                    </span>
                </li>
                <li>
                    <span class="j_name">返现金额：</span>
                    <span class="j_cont price">￥<fmt:formatNumber pattern="#0.00" value="${comment.empPrice*comment.number}"/>
                    </span>
                </li>
                
                <c:forEach items="${comment.proValues.keySet()}" var="key" varStatus="keyStatus">
                <li>
                    <span class="j_name">${key}：</span>
                    <span class="j_cont">${comment.proValues.get(key)}</span>
                </li>
                </c:forEach>
            </ul>
            <div class="jp_th">（现在查看的是您于<fmt:formatDate value="${comment.createTime}" pattern="yyyy年MM月dd日"/>所购买的试用商品的信息）</div>
        </div>
    </div>

	<div class="pj_infomation">
		<div class="top_tit">
			<span>请您回答以下问卷，对该商品进行评价</span><p>（完成问卷后，返现金额将会返还至您的现金券账户中！）</p>
		</div>
		<div class="main_bd main_bd_con">
			<div class="vote_list js_vote_list">
 			  <c:forEach var="q" items="${questionnare.listQuestion}" varStatus="cs">
				<div class="vote_item">
					<div class="vote_item_hd">
						<h4 class="vote_title">${cs.index+1}. ${q.questionTitle}</h4>
					</div>
					<div class="vote_item_bd">
						<ul class="vote_option_list">
 			  			  <c:forEach var="o" items="${q.listOption}" varStatus="os">
							<li class="vote_option">
								<div class="vote_option_msg group">
									 
									  <c:if test="${o.selectType==1}">
										<input type="radio" name="${q.id}" id="${o.id}">
									  </c:if>
									  <c:if test="${o.selectType==2}">
										<input type="checkbox" name="${q.id}" id="${o.id}">
									  </c:if>
									
									<label for="${o.id}"> 
										<c:if test="${not empty o.image}">
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
			</div>
			<div class="tool_bar">
				<a href="javascript:saveComment('${comment.subOrderItemId}');" class="btn btn_primary js_complete_bnt">完成</a>
			</div>
		</div>
	</div>
	<div class="clear"></div>

<script type="text/javascript" src="${basePath}resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="${basePath}resources/js/application.js"></script>
<script type="text/javascript">
    function saveComment(itemId){
    	if(checkInput()) {
	    	$(".js_complete_bnt").attr("href","javascript:;");
	        var form=$('#form'+itemId),content= $.trim(form.find('[name="text"]').val());
	
	        $.post("${basePath}/member/saveComment",form.serialize(),function(data){
	            if(data=="success") {
	                window.location.reload(true);
	            }
	        });
    	}
    }
    
    function checkInput() {
    	var answers="";
    	var qs = $(".vote_item");
    	
    	for(i=0;i<qs.length;i++) {
    		var ipts = $(qs[i]).find("input:checked");
    		if(ipts.length == 0) {        			
    			wode.showBox("请回答以下问题",$(qs[i]).find(".vote_title").html()+"");
    			return false;
    		}        		

        	for(j=0;j<ipts.length;j++) {
        		answers += $(ipts[j]).attr("id") + ","
        	}
    	}
    	
    	$("input[name='answers']").val(answers);
    	return true;
    }
</script>
<%@ include file="../common/footer.jsp" %>
<%@ include file="../common/box.jsp" %>
<script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>
