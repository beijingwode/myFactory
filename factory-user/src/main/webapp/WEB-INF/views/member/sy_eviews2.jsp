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
	<form action="${basePath}/member/saveComment" id="form" method="post">
    	<input type="hidden" name="anonymous" value="1"/>
    	
    	<input type="hidden" name="questionnareId" value="${q.id}"/>
    	<input type="hidden" name="answers" value=""/>
    </form>
    <input type="hidden" id="productId" value="${q.productId}"/>
    <input type="hidden" id="skuId" value="${skuId}"/>
    <input type="hidden" id="supplierId" value="${q.supplierId}"/>
     <input type="hidden" id="buyCount" value="${quantity}"/>
    <div class="mydd_pj_wrap">
   		<div class="picon"><img src="../images/picon_t1.png" /></div>
        <div class="jp_product"><a href="/${q.productId}.html?pageKey=order" target="_blank"><img src="${q.productImg }" width="160" height="160" alt="preview_pic01"></a></div>
        <div class="jp_info">
            <p><a href="/${q.productId}.html?pageKey=order" target="_blank">${q.productName}</a></p>
           
            <ul class="jp_lb">
             <c:if test="${not empty quantity }">
                <li>
                	<span class="j_name">数&nbsp;&nbsp;&nbsp;量：</span>
                	<span class="j_cont">${quantity}</span>
                </li>
                 </c:if>
                  <c:if test="${not empty sku }">
                  <c:forEach items="${sku}" var="mymap" >
                  <li>
                     <span class="j_name">${mymap.key}：</span>
                     <span class="j_cont">${mymap.value}</span>
                  </li>
                  </c:forEach>
                  </c:if>
            </ul>
           
        </div>
    </div>

	<div class="pj_infomation">
		<div class="top_tit">
			<span>请您回答以下问卷，获得商家提供的优惠</span><p>（该商品需要回答商家提供的问卷才能下单，感谢您的积极配合）</p>
		</div>
		<div class="main_bd main_bd_con">
			<div class="vote_list js_vote_list">
 			  <c:forEach var="q" items="${q.listQuestion}" varStatus="cs">
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
				<a href="javascript:saveComment('');" class="btn btn_primary js_complete_bnt">完成</a>
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
	            	if($("#skuId").val() && $("#skuId").val() != "" && $("#supplierId").val()!='' && $("#supplierId").val()){
	            		var partNumbers =$("#supplierId").val()+"_"+$("#skuId").val()+","
						location.href="/order/info?partNumbers="+partNumbers;
	            	}else{
	            	   window.history.go(-1);
	            	}
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
