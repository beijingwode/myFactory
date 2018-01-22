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
</head>
<body>
    <!--top begin-->
    <%@ include file="../common/header_03.jsp" %>
    <!--top end-->
 <c:forEach var="comment" items="${list }" varStatus="cs">
    <div class="mydd_pj_wrap">
  		<c:if test="${comment.saleKbn==1 }">
   		<div class="picon"><img src="../images/picon1.png" /></div>
   		</c:if>
  		<c:if test="${comment.saleKbn==2 }">
   		<div class="picon"><img src="../images/picon_c1.png" /></div>
   		</c:if>
   		<c:if test="${comment.saleKbn==4 }">
   		<div class="picon"><img src="../images/picon_z1.png" /></div>
   		</c:if>
  		<c:if test="${comment.saleKbn==5 }">
   		<div class="picon"><img src="../images/picon_t1.png" /></div>
   		</c:if>
        <div class="jp_product"><a href="/${comment.productId}.html?pageKey=order" target="_blank"><img src="${comment.image }" width="240" height="240" alt="preview_pic01"></a></div>
        <div class="jp_info">
            <p><a href="/${comment.productId}.html?pageKey=order" target="_blank">${comment.productName}</a></p>
            <ul class="jp_lb">
                <li>
                	<span class="j_name">数&nbsp;&nbsp;&nbsp;量：</span>
                	<span class="j_cont">${comment.number}</span>
                </li>
<%--               	<c:if test="${!empty comment.companyTicket}"> --%>
<!-- 	                <li> -->
<!-- 	                    <span class="j_name">使用内购券：</span> -->
<%-- 	                    <span class="j_cont">${comment.companyTicket}券</span> --%>
<!-- 	                </li> -->
<%--                 </c:if> --%>
                <li>
                    <span class="j_name">实付金额：</span>
                    <span class="j_cont price">￥<fmt:formatNumber pattern="#0.00" value="${comment.realPay}"/>
                    <c:if test="${comment.benefitAmount > 0}">+<fmt:formatNumber value="${comment.benefitAmount}" pattern="#0.00"/>惠</c:if>
                    </span>
                </li>
                <li>
                    <span class="j_name">运&nbsp;&nbsp;&nbsp;费：</span>
                    <span class="j_cont red">￥<fmt:formatNumber pattern="#0.00" value="${comment.shipping}"/></span>
                </li>
                <li>
                    <span class="j_name">评&nbsp;&nbsp;&nbsp;价：</span>
                    <span class="j_cont"><div class="jp_percent_star"><span class="current_rating" style="width:${comment.score * 15}px;"></span></div>${comment.score}分（累计评价<em class="blue">${comment.count}</em>）</span>
                </li>
                <c:forEach items="${comment.proValues.keySet()}" var="key" varStatus="keyStatus">
                <li>
                    <span class="j_name">${key}：</span>
                    <span class="j_cont">${comment.proValues.get(key)}</span>
                </li>
                </c:forEach>
            </ul>
            <div class="jp_th">（现在查看的是您于<fmt:formatDate value="${comment.createTime}" pattern="yyyy年MM月dd日"/>所购买商品的信息）</div>
        </div>
    </div>
    <div class="pj_infomation">
        <form action="${basePath}/member/saveComment" id="form${comment.subOrderItemId}" method="post">
            <input type="hidden" name="subOrderid" value="${comment.subOrderId}"/>
            <input type="hidden" name="supplyid" value="${comment.supplierId}"/>
            <input type="hidden" name="productId" value="${comment.productId}"/>
            <input type="hidden" name="attributeJson" value="<c:out value='${comment.itemValues}'/>"/>
            <input type="hidden" name="subOrderItemId" value="${comment.subOrderItemId}"/>
            <input type="hidden" name="anonymous" value="1"/>
            <div class="cont_wrap">
                <span class="b_name">商品评分：</span>
                <ul class="star_pj fl star_ul" id="star_ul04">
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
                <ul class="star_pj fl star_ul" id="star_ul05">
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
                <ul class="star_pj fl star_ul" id="star_ul06">
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
            <img data-index="${cs.index}1" id="uploadpic${cs.index}1" name="uploadpic" src="<%=basePath %>images/uploadpic.jpg" width="120" height="120">
			<input type="file" id="avatar${cs.index}1" name="avatar1" style="display:none;" onchange="uploadImage('avatar${cs.index}1','uploadpic${cs.index}1','images${cs.index}1')">
			<input type="hidden" id="images${cs.index}1" name="images" value=""> 
			<img data-index="${cs.index}2" id="uploadpic${cs.index}2" name="uploadpic" src="<%=basePath %>images/uploadpic.jpg" width="120" height="120">
			<input type="file" id="avatar${cs.index}2" name="avatar2" style="display:none;" onchange="uploadImage('avatar${cs.index}2','uploadpic${cs.index}2','images${cs.index}2')">
			<input type="hidden" id="images${cs.index}2" name="images" value=""> 
			<img data-index="${cs.index}3" id="uploadpic${cs.index}3" name="uploadpic" src="<%=basePath %>images/uploadpic.jpg" width="120" height="120">
			<input type="file" id="avatar${cs.index}3" name="avatar3" style="display:none;" onchange="uploadImage('avatar${cs.index}3','uploadpic${cs.index}3','images${cs.index}3')">
			<input type="hidden" id="images${cs.index}3" name="images" value=""> 
            </div>
            <input class="p_submit" type="button" onclick="saveComment('${comment.subOrderItemId}');" value="提交">
        </form>
    </div>
</c:forEach>
    <div class="clear"></div>
    <script type="text/javascript" src="${basePath}resources/js/jquery1.8.0.js"></script>
    <script type="text/javascript" src="${basePath}resources/js/shopping.js"></script>
    <script type="text/javascript" src="${basePath}resources/js/application.js"></script>
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
    <script type="text/javascript">
        function saveComment(itemId){
        	$(".p_submit").attr("disabled","disabled");
            var form=$('#form'+itemId),content= $.trim(form.find('[name="text"]').val());
            /*if(content == "") {
                wode.showBox("警告","请输入评论内容!");

            } else if(content.length > 500) {
                wode.showBox("警告","评论请控制在500字以内!");

            } else {*/
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
                /*$.post("${basePath}/member/saveComment",form.serialize(),function(data){
                    if(data=="success") {
                        window.location.reload(true);
                    }
                });*/
            /*}*/
        }

       /* $(document).ready(function(){
            $('.mydd_pj_wrap').each(function(index){
                $(this).click(function(){
                    var pj_inf = $('.pj_infomation').get(index);
                    if($(pj_inf).is(":hidden")) {
                        $(pj_inf).show();
                    } else {
                        $(pj_inf).hide();
                    }
                });
            });
        });*/
    </script>
    <%@ include file="../common/footer.jsp" %>
    <%@ include file="../common/box.jsp" %>
      <script type="text/javascript" src="<%=basePath %>resources/js/top_ewm.js"></script>
</body>
</html>
