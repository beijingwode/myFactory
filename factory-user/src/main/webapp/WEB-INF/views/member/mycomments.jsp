<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="../common/include.jsp" %>
<!doctype html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<title>我的福利-个人中心</title>
<link rel="stylesheet" href="${basePath}/css/Personal.css" type="text/css" />
<script type="text/javascript" src="${basePath}/resources/js/jquery1.8.0.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/comment.js"></script>
<script type="text/javascript" src="${basePath}/resources/js/application.js"></script>
<script type="text/javascript">
    function saveComment(itemId){
    	$(".p_submit").attr("disabled","disabled");
        var form=$('#form'+itemId),content= $.trim(form.find('[name="text"]').val());
        if(content.length > 500) {
            wode.showBox("警告","评论请控制在500字以内!");
        }else {
        	$.ajax({
                type: "POST",
                url: "${basePath}/member/saveComment",
                data: form.serialize(),
                dataType: "json",
                cache:false,
                success: function(data){
                	if(data=="success") {
                        window.location.reload(true);
                    }else
                    	wode.showBox("错误",data.msg);
                         }
            });
        }
    }
</script>
</head>
<body>

<!--top begin-->
<%@ include file="../common/header_03.jsp" %>
<!--top end-->
<!---------------------------------------内容------------------------------------------------->
<div class="Me_wrap">
<!--left nav-->
<%@ include file="menu.jsp" %>
<!--left nav end-->
<!--right content-->
	<div class="Me_content">
    	<p class="P_title">我的评价</p>
        <div class="appraise_nav">
            <ul>
                <li ${empty state ? 'class="current"' : ''}><a href="/member/mycomments">全部</a></li>
                <li ${state == false? 'class="current"' : ''}><a href="/member/mycomments?state=0" >待评价</a></li>
                <li ${state == true ? 'class="current"' : ''}><a href="/member/mycomments?state=1" >已评价</a></li>
            </ul>
        </div>  
        <c:if test="${!empty page.list }">
        <!--全部 begin-->      
        <div class="appraise_list_wrap" style="display:block;">
        	<ul class="appraise_theme">
            	<li class="L1">商品信息</li>
                <li class="L2">交易时间</li>
                <li class="L3">评价状态</li>
            </ul>
            <ul class="A_cont">
            
            	<c:forEach  varStatus="commentstatus" var="comment" items="${page.list }">
            	<li>
                	<div class="A-product">
                  		
                    	<span class="A-s-img">
                    		<c:if test="${comment.saleKbn==1 }">
	                   		<div class="picon"><img src="../images/picon2.png" /></div>
	                   		</c:if>
	                  		<c:if test="${comment.saleKbn==2 }">
	                   		<div class="picon"><img src="../images/picon_c2.png" /></div>
	                   		</c:if>
	                   		<c:if test="${comment.saleKbn==4 }">
	                   		<div class="picon"><img src="../images/picon_z2.png" /></div>
	                   		</c:if>
	                  		<c:if test="${comment.saleKbn==5 }">
	                   		<div class="picon"><img src="../images/picon_t2.png" /></div>
	                   		</c:if>
                    	<a href="${basePath}/${comment.productId}.html?pageKey=order" target="_blank"><img src="${comment.image }" width="78" height="78"></a></span>
                        <div class="A-s-text">
                        	<p class="p1">
                        		<c:choose>
									<c:when test="${fn:length(comment.productName) > 35}">
								      <c:out value="${fn:substring(comment.productName, 0, 35)}..." />
								     </c:when>
								     <c:otherwise>
								      <c:out value="${comment.productName}" />
									 </c:otherwise>
							    </c:choose>
                        	</p>
                            <c:forEach items="${comment.proValues.keySet()}" var="key" varStatus="keyStatus">
                                <p class="p${keyStatus.index + 2}">${key}：${comment.proValues.get(key)} </p>
                            </c:forEach>
                        </div>
                    </div>
                    <span class="A_time"><fmt:formatDate value="${comment.createTime }" pattern="yyyy-MM-dd"/></span>
                    <div class="A_btn">
	                    <c:if test="${comment.commentFlag == 0 }">
                    	  <c:if test="${comment.count == 0 }"> 
	                    	<p><a href="javascript:">发表评价</a></p>
                    	  </c:if>
                    	  <c:if test="${comment.count > 0 }"> 
	                    	<p><a href="/member/ordereviews?order=${comment.subOrderId}&itemId=${comment.subOrderItemId}" data-type="1">发表评价</a></p>
                    	  </c:if>
	                    </c:if>
	                    <c:if test="${comment.commentFlag == 1 }">
	                    	已评价
	                    </c:if>
                    </div>
                    <c:if test="${comment.commentFlag==0}">
                        <!--发表评价 begin-->
                        <div class="appraise_box">
                            <p class="point"></p>
                            <div class="click_btn"></div>
                            <div class="appraise_box_cont">
                                <div class="show_pro">
                                    <span class="b-s-img">
                                    	<c:if test="${comment.saleKbn==1 }">
				                   		<div class="picon"><img src="../images/picon2.png" /></div>
				                   		</c:if>
				                  		<c:if test="${comment.saleKbn==2 }">
				                   		<div class="picon"><img src="../images/picon_c2.png" /></div>
				                   		</c:if>
				                  		<c:if test="${comment.saleKbn==5 }">
				                   		<div class="picon"><img src="../images/picon_t2.png" /></div>
				                   		</c:if>
                                    <a href="${basePath}${comment.productId}.html?pageKey=order" target="_blank"><img src="${comment.image }" width="78" height="78" alt="Me-order-img"></a></span>
                                    <div class="b-s-text">
                                        <p class="p1"><c:choose>
                                            <c:when test="${fn:length(comment.productName) > 35}">
                                                <c:out value="${fn:substring(comment.productName, 0, 35)}..." />
                                            </c:when>
                                            <c:otherwise>
                                                <c:out value="${comment.productName}" />
                                            </c:otherwise>
                                        </c:choose></p>
                                        <c:forEach items="${comment.proValues.keySet()}" var="key" varStatus="keyStatus">
                                            <p class="p${keyStatus.index + 2}">${key}：${comment.proValues.get(key)} </p>
                                        </c:forEach>
                                    </div>
                                    <span class="b_date"><fmt:formatDate value="${comment.createTime }" pattern="yyyy-MM-dd"/></span>
                                </div>
                                <form action="${basePath}/member/saveComment" id="form${comment.subOrderItemId}" method="post">
                                    <input type="hidden" name="subOrderid" value="${comment.subOrderId}"/>
                                    <input type="hidden" name="supplyid" value="${comment.supplierId}"/>
                                    <input type="hidden" name="productId" value="${comment.productId}"/>
                                    <input type="hidden" name="attributeJson" value="<c:out value='${comment.itemValues}'/>"/>
                                    <input type="hidden" name="subOrderItemId" value="${comment.subOrderItemId}"/>
                                <div class="cont_wrap">
                                    <span class="b_name">商品评分：</span>
                                    <ul class="star_ul fl star_ul" id="star_ul04">
                                        <li><a class="one-star" title="很不满意" score="1" href="javascript:"></a></li>
                                        <li><a class="two-star" title="不满意" score="2" href="javascript:"></a></li>
                                        <li><a class="three-star" title="一般" score="3" href="javascript:"></a></li>
                                        <li><a class="four-star" title="满意" score="4" href="javascript:"></a></li>
                                        <li><a class="five-star active-star" score="5" title="非常满意" href="javascript:"></a></li>
                                    </ul>
                                    <span class="s_result fl s_result" id="s_result04">非常满意</span>
                                    <input type="hidden" name="star1" id="star1" value="5"/>
                                </div>
                                <div class="cont_wrap">
                                    <span class="b_name">服务评分：</span>
                                    <ul class="star_ul fl star_ul" id="star_ul05">
                                        <li><a class="one-star" title="很不满意" score="1" href="javascript:"></a></li>
                                        <li><a class="two-star" title="不满意" score="2" href="javascript:"></a></li>
                                        <li><a class="three-star" title="一般" score="3" href="javascript:"></a></li>
                                        <li><a class="four-star" title="满意" score="4" href="javascript:"></a></li>
                                        <li><a class="five-star active-star" score="5" title="非常满意" href="javascript:"></a></li>
                                    </ul>
                                    <span class="s_result fl s_result" id="s_result05">非常满意</span>
                                    <input type="hidden" name="star2" id="star2" value="5"/>
                                </div>
                                <div class="cont_wrap">
                                    <span class="b_name">物流评分：</span>
                                    <ul class="star_ul fl star_ul" id="star_ul06">
                                        <li><a class="one-star" title="很不满意" score="1" href="javascript:"></a></li>
                                        <li><a class="two-star" title="不满意" score="2" href="javascript:"></a></li>
                                        <li><a class="three-star" title="一般" score="3" href="javascript:"></a></li>
                                        <li><a class="four-star" title="满意" score="4" href="javascript:"></a></li>
                                        <li><a class="five-star active-star" score="5" title="非常满意" href="javascript:"></a></li>
                                    </ul>
                                    <span class="s_result fl s_result" id="s_result06">非常满意</span>
                                    <input type="hidden" name="star3" id="star3" value="5"/>
                                </div>
                                <div class="cont_wrap">
                                    <span class="b_name">商品评价：</span>
                                    <textarea class="pop_textarea" name="text"></textarea>
                                </div>
                                <div class="cont_wrap">
                                    <span class="b_name">图片评价：</span>
                                <img data-index="${commentstatus.index}1" id="uploadpic${commentstatus.index}1" name="uploadpic" src="<%=basePath %>images/uploadpic.jpg" width="120" height="120">
					            <input type="file" id="avatar${commentstatus.index}1" name="avatar1" style="display:none;" onchange="uploadImage('avatar${commentstatus.index}1','uploadpic${commentstatus.index}1','images${commentstatus.index}1')">
					            <input type="hidden" id="images${commentstatus.index}1" name="images" value=""> 
					             <img data-index="${commentstatus.index}2" id="uploadpic${commentstatus.index}2" name="uploadpic" src="<%=basePath %>images/uploadpic.jpg" width="120" height="120">
					            <input type="file" id="avatar${commentstatus.index}2" name="avatar2" style="display:none;" onchange="uploadImage('avatar${commentstatus.index}2','uploadpic${commentstatus.index}2','images${commentstatus.index}2')">
					            <input type="hidden" id="images${commentstatus.index}2" name="images" value=""> 
					             <img data-index="${commentstatus.index}3" id="uploadpic${commentstatus.index}3" name="uploadpic" src="<%=basePath %>images/uploadpic.jpg" width="120" height="120">
					            <input type="file" id="avatar${commentstatus.index}3" name="avatar3" style="display:none;" onchange="uploadImage('avatar${commentstatus.index}3','uploadpic${commentstatus.index}3','images${commentstatus.index}3')">
					            <input type="hidden" id="images${commentstatus.index}3" name="images" value=""> 
                                </div>
                                <input class="p_submit" type="button" onclick="saveComment('${comment.subOrderItemId}');" value="提交">
                                </form>
                            </div>
                        </div>
                        <!--发表评价 end-->
                    </c:if>
                    <c:if test="${comment.commentFlag == 1 }">
                    	<div class="user_appraise">
                    	  <c:if test="${comment.count == 0 }"> 
                    		<div class="A_percent_star"><span class="current_rating a_1" style="width:${comment.comment.star1 * 15}px;"></span></div>
                       		<p>${comment.comment.text }</p>
                    	  </c:if>
                    	  <c:if test="${comment.count > 0 }"> 
                       		<p>问卷评价</p>
                    	  </c:if>
                    	</div>
                    </c:if>
                     <!-- 评论图片展示 -->
                    <div class="cont_wrap">
	                    <ul >
	                    	<li style="width:908px;padding:20px 0 0 110px;">
	                    	 	<c:forEach items="${comment.comment.images}" var="image">
	                    			<c:if test="${!empty image&&image!=''}"><img src="${image}" width="80" height="80"></c:if>
	                    		</c:forEach>
	                    	</li>
	                    </ul>
                    </div>
                </li>
                </c:forEach>
                
            </ul>
        </div>
        <!--全部 end-->
        <div style="text-align: right;">
        	<jsp:include page="../common/page.jsp" flush="true">
            		<jsp:param name="page" value="${page}"/>
      		</jsp:include>
        </div>
        </c:if>
    </div>
<!--right contont end-->
<div class="clear:after"></div>
</div>

<div class="clear"></div>
<%@ include file="../common/footer.jsp" %>
<%@ include file="../common/box.jsp" %>
<script type="text/javascript" src="${basePath}resources/js/top_ewm.js"></script>
<script type="text/javascript" src="<%=basePath %>resources/js/ajaxfileupload.js"></script>
 <script type="text/javascript">
 
    
    $("[name='uploadpic']").each(function(){
        $(this).click(function() {
        	$("#avatar" + $(this).attr("data-index")).click();
        });
    });
    /**
     * 图片上传
     * @param id
     */
    function uploadImage(fileId,imageId,imggeValueId) {
        var elementIds=[""+fileId]; //flag为id、name属性名
        $.ajaxFileUpload({
            url: '/upload/pic',
            type: 'post',
            secureuri: true, //一般设置为false
            fileElementId: fileId, // 上传文件的id、name属性名
            dataType: 'json', //返回值类型，一般设置为json、application/json
            elementIds: elementIds, //传递参数到服务器
            success: function(data, status){
            	if(data.success){
            		var imgPath = "http://"+data.data[0];
            		//$("#avatarValue").val(imgPath);
            		$("#"+imggeValueId).val(imgPath);
    				$("#"+imageId).attr('src',imgPath);
            	}else
            		alert(data.msg);
            },
            error: function(data, status, e){ 
                alert(e);
            }
        });
    }
    </script>
</body>
</html>