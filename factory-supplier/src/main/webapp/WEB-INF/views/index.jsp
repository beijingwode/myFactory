<%@ page contentType="text/html;charset=UTF-8" %>
    <%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<title>个人中心</title>
<head>
<%@ include file="/commons/js.jsp" %>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<script type="text/javascript" src="<%=static_resources %>js/index.js"></script>
<!--header end-->

<!--content begin-->
<div id="content">
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
			<li class="curr"><a href="#">首页</a></li>
			<li><a href="${basePath}/user/info.html">个人信息</a></li>
			<li><a href="${basePath}/user/security.html">安全设置</a></li>
			<li style="background-color: #f5f5f5">&nbsp;&nbsp;</li>
			<li><a href="${basePath}/user/app_security.html">API安全</a></li>
        </ul>
    </div>
    <!--left end-->
    
    <!--right begin-->
    <div class="right">
    	<div class="position">
        	<span>您所在的位置</span><em>></em>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/user/tosuppliermain.html">首页</a>
        </div>
        <div class="merchant_info">
        	<div class="merchant_icon_box">
	        	<div class="merchant_icon">
	        		<a href="javascript:void(0);"><!-- <img src="${result.msgBody.shopList[0].logo}" > -->
	        		<img id="uploadpic1" src='${result.msgBody.firmLogo}' width="112px" height="112px"></a>
	        		<p id="uploadpic">上传企业logo<br />增强员工<br />内购福利专属感</p>
					<input type="file" id="avatar1" name="avatar1" style="display:none;" onchange="uploadImage('avatar1','uploadpic1','images1')">
					<input type="hidden" id="images1" name="images" value=""> 					
	        	</div>
	        	
	        	<span>企业logo<br />(高度小于110px)</span>
        	</div>
            <div class="merchant_infomartion">
            	<ul class="info_list">
                	<li> 
                    	<div class="Li01">用户名称：${sessionScope.userSession.userName}</div>
                        <div class="Li02">上一次登录时间：<fmt:formatDate value="${sessionScope.userSession.lastLoginTime}" pattern="yyyy/MM/dd  HH:mm:ss" /></div>
                    </li>
                    <li>
                    	<div class="Li01">商家ID： <c:if test="${result.errorCode eq 0}"><input id="supplierId" type="hidden" value="${result.msgBody.id}">${result.msgBody.id}</c:if></div>
                        <div class="Li02">店铺ID： <c:if test="${result.errorCode eq 0}"><c:if test="${not empty result.msgBody.shopList }">${result.msgBody.shopList[0].id}</c:if></c:if></div>
                    </li>
                    <li>
                    	<div class="Li01">公司名称： <c:if test="${result.errorCode eq 0}">${result.msgBody.comName}</c:if></div>
                        <div class="Li02">商铺名称： <c:if test="${result.errorCode eq 0}"><c:if test="${not empty result.msgBody.shopList }">${result.msgBody.shopList[0].shopname}</c:if></c:if></div>
                    </li>
                    <c:if test="${sessionScope.userSession.type != 3}">
                    <li>
                    	<div class="Li01">
                    	
                        	<div class="add_new add_new_a " style="float:left">
			           			<a href="<c:if test="${apprS eq '商户审核中'}">javascript:void(0);</c:if><c:if test="${apprS != '商户审核中'}">${basePath}/supplier/tocreatesupplierinfo.html?mode=edit</c:if>">${apprS}</a>
			           		</div>
                    	</div>
                    	<c:if test="${apprS != '商户审核中'}">
                        <div class="Li02">
                        	<div class="add_new add_new_a ft_rt " style="margin-left:0">
			           			<a href="<c:if test="${apprShop eq '店铺审核中'}">javascript:void(0);</c:if><c:if test="${apprShop != '店铺审核中'}">${basePath}/supplier/torecruitmentstore.html</c:if>">${apprShop}</a>
			           		</div>
                        </div>
                    	</c:if>
                    </li>
                    </c:if>
                </ul>
                <div class="info_account">
                	<div class="info_account_l">
                    	<p>待处理订单 （<strong><a href="${basePath}/suborder/gotoSelllist.html?type=3">${df}</a></strong>）</p>
                        <p>待退款审核（<strong><a href="${basePath}/suborder/gotoSelllist.html?type=5">${tk}</a></strong>）</p>
                    </div>
                    <div class="info_account_r">
                        <p>商品审批未通过（<strong><a href="${basePath}/product/gotoProductlist.html?selltype=waitcheck&status=-1">${wtg}</a></strong>）</p>
                        <p></p>
                    </div>
                </div>
            </div>
            <div class="merchant_help">
            	<h2>帮助中心</h2>
                <ul class="merchant_help_list">
                	<li><a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>help-center.html" target="_blank">新手必读</a></li>
                    <li><a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>feedback.html" target="_blank">我要提问</a></li>
                    <li><a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>help-center.html?from=cxhd" target="_blank">促销知识</a></li>
                    <li><a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>help-center.html?from=jsbd" target="_blank">结算必读</a></li>
                    <li><a href="<%=com.wode.factory.supplier.util.Constant.FACTORY_WEB_URL%>help-center.html?from=shgz" target="_blank">售后规则</a></li>
                </ul>
            </div>
            <div class="notice">
            	<div class="notice_title">最新公告</div>
                <div class="notice_cont">
                	<div class="notice_menu">
                    	<ul>
                        	<li class="surr"><a href="javascript:;">最新通知</a></li>
                            <li><a href="javascript:;">新上线功能</a></li>
                        </ul>
                    </div>
                    <!--最新通知 begin-->
                    <div class="notice_list" style="display:block;">
                    	<ul>
                    	<c:forEach items="${page.result}" var="item" varStatus="status">
                    		<li>
                            	<p style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item.title}</p>
                                <strong><fmt:formatDate value="${item.creatdate}" pattern="yyyy-MM-dd"/></strong>
                            </li>
                    	</c:forEach>
                        </ul>
                    </div>
                    <!--最新通知 end-->
                    
                    <!--新上线功能 begin-->
                    <div class="notice_list">
                    	<ul>
                    	<c:forEach items="${page2.result}" var="item2" varStatus="status2">
                    		<li>
                            	<p style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">${item2.title}</p>
                                <strong><fmt:formatDate value="${item2.creatdate}" pattern="yyyy-MM-dd"/></strong>
                            </li>
                    	</c:forEach>
                        </ul>
                    </div>
                    <!--新上线功能 end-->
                </div>
            </div>
            <div class="sales_ranking">
            	<div class="notice_title">单品销售排名</div>
                <div class="ranking_info">
                	<ul class="ranking_title">
                    	<li class="Li01">排名</li>
                        <li class="Li02">商品编号</li>
                        <li class="Li03">商品名称</li>
                        <li class="Li04">销售件数</li>
                    </ul>
                    <c:if test="${empty saleroomlist}">
                    <ul class="ranking_cont">
                    	<li class="Li01"><span>1</span></li>
                        <li class="Li02"></li>
                        <li class="Li03" style="overflow: hidden;" title=""></li>
                        <li class="Li04"></li>
		            </ul>
		            <ul class="ranking_cont">
                    	<li class="Li01"><span>2</span></li>
                        <li class="Li02"></li>
                        <li class="Li03" style="overflow: hidden;" title=""></li>
                        <li class="Li04"></li>
		            </ul>
		            <ul class="ranking_cont">
                    	<li class="Li01"><span>3</span></li>
                        <li class="Li02"></li>
                        <li class="Li03" style="overflow: hidden;" title=""></li>
                        <li class="Li04"></li>
		            </ul>
                    </c:if>
                    <c:if test="${!empty saleroomlist}">
	                    <c:forEach items="${saleroomlist}" var="item" varStatus="index">
		                    <ul class="ranking_cont">
		                    	<li class="Li01"><span>${index.count}</span></li>
		                        <li class="Li02">${item.barcode}<c:if test="${empty item.barcode}">&nbsp;</c:if></li>
		                        <li class="Li03" style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;" title="${item.name}">${item.name}</li>
		                        <li class="Li04"><a href="${basePath}/product/productView.html?productId=${item.id}">${item.sellNum}</a></li>
		                    </ul>
		                    <c:if test="${index.last}">
		                    	<c:if test="${index.count eq 1}">
		                    		<ul class="ranking_cont">
				                    	<li class="Li01"><span>2</span></li>
				                        <li class="Li02"></li>
				                        <li class="Li03" style="overflow: hidden;" title=""></li>
				                        <li class="Li04"></li>
						            </ul>
						            <ul class="ranking_cont">
				                    	<li class="Li01"><span>3</span></li>
				                        <li class="Li02"></li>
				                        <li class="Li03" style="overflow: hidden;" title=""></li>
				                        <li class="Li04"></li>
						            </ul>
		                    	</c:if>
		                    	<c:if test="${index.count eq 2}">
						            <ul class="ranking_cont">
				                    	<li class="Li01"><span>3</span></li>
				                        <li class="Li02"></li>
				                        <li class="Li03" style="overflow: hidden;" title=""></li>
				                        <li class="Li04"></li>
						            </ul>
		                    	</c:if>
		                    </c:if>
	                    </c:forEach>
                    </c:if>
                </div>
                <div class="notice_title">销售情况</div>
                <div class="ranking_info">
                	<ul class="ranking_title">
                    	<li class="L_item">项目</li>
                        <li class="L_order">订单</li>
                        <li class="L_money">订单金额</li>
                    </ul>
                    <ul class="ranking_cont">
                    	<li class="L_item">日销售</li>
                        <li class="L_order">${suborderr.subOrderCount }</li>
                        <li class="L_money">${suborderr.sumPrice }</li>
                    </ul>
                    <ul class="ranking_cont">
                    	<li class="L_item">月销售</li>
                        <li class="L_order">${subordery.subOrderCount }</li>
                        <li class="L_money">${subordery.sumPrice }</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!--right end-->
</div>
<script type="text/javascript" src="<%=static_resources %>resources/js/ajaxfileupload.js"></script>
<script type="text/javascript">
    $("#uploadpic").click(function() {
    	$("#avatar1").click();
    });
    /**
     * 图片上传
     * @param id
     */
    function uploadImage(fileId,imageId,imggeValueId) {
        var elementIds=[""+fileId]; //flag为id、name属性名
        $.ajaxFileUpload({
            url: '<%=path%>/upload/pic',
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
    				addfirmLogo();
            	}else
            		alert(data.msg);
            },
            error: function(data, status, e){ 
                alert(e);
            }
        });
    }
    function addfirmLogo(){
    	var firmLogo = $("#images1").val();
    	var supplierId = $("#supplierId").val();
    	$.ajax({
			url : "<%=path%>/supplier/addfirmLogo.json",
			type : "POST",
			dataType: "json",  //返回json格式的数据  
			data:{"firmLogo":firmLogo,"supplierId":supplierId},
		    //async: false,
			success : function(data) {
			}, error : function() { 
				alert("error");
		     }  
		});
    }
    </script>
<!--content end-->
<script type="text/javascript">
	$(document).ready(function(){
		selectedHeaderLi("sy_header");
		
		//鼠标滑过头像显示提示文字
		$(".merchant_icon").mouseover(function(){
			$(".merchant_icon p").show();
		});
		$(".merchant_icon").mouseout(function(){
			$(".merchant_icon p").hide();
		})
	});
</script>
<%@ include file="/commons/footer.jsp" %>
</body>
</html>
