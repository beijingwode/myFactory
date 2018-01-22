<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/commons/taglibs.jsp" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<meta name="description" content="">
<meta name="keywords" content="">
<link rel="canonical" href="http://dreamdu.com/">
<title>我的网商家入驻</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/recruitment.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/bottom.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/box.css">
<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/index.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/scrollbar.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/sleep.js"></script>
</head>
<body>
<!--top begin-->
<div id="top">
	<ul class="progress progress-3">
        <li class="t1 current">填写公司信息</li>
        <li class="t2 current">创建店铺</li>
        <li class="t3 current">选择类目</li>
        <li class="t4">上传资质</li>
        <li class="t5">确认合同</li>
    </ul>	
</div>
<!--top end-->
<input type="hidden" id="categoryid" value="">
<!--content begin-->
<div id="content">
	<div class="recruitment_title" style="padding-bottom:10px;"><div style="float:left;">选择类目</div>
		<div class="recuitment_btn" style="width:580px;margin:0;height:27px;float:right">
	    	<p class="h_saving" id="cg" style="display: none;height:27px;font-size:12px;line-height:27px;">√ 已保存。</p>
	    	<p class="h_failed" id="sb" style="display: none;height:27px;font-size:12px;line-height:27px;">× 保存失败。</p> 
	    	<p class="h_failed" id="ts" style="display: none;height:27px;font-size:12px;line-height:27px;">× 请先选择分类。</p>
	    	<div style="float:right">
	    	<div class="recuitment_btn01" id="recuitment_btn01" onclick="papa('1');" style="height:27px;margin-bottom:0;width:60px;"><a href="javascript:void(0);" style="height:27px;font-size:12px;line-height:27px;">保存</a></div>
	        <div class="recuitment_btn02" id="recuitment_btn02" onclick="papa('2');" style="height:27px;margin-bottom:0;width:150px;"><a href="javascript:void(0);" style="height:27px;font-size:12px;line-height:27px;">下一步，上传资质</a></div>
	        <div class="recuitment_btn03" id="recuitment_btn03" onclick="papa('3');" style="height:27px;margin:0;width:60px;"><a href="javascript:void(0);" style="height:27px;font-size:12px;line-height:27px;">上一步</a></div>
	    	</div>
	    </div>
	</div>
    <div class="recruitment_cont">
    	<div class="r_name"><span>店铺名称</span></div>
        <div class="manufacturer">
        	${ss.shopname}&nbsp;&nbsp;&nbsp;&nbsp;(<c:if test="${empty ss.type || ss.type eq 0 }">专营店</c:if><c:if test="${ss.type eq 1 }">专卖店</c:if><c:if test="${ss.type eq 2 }">旗舰店</c:if>)
        </div>
        <div class="r_name martop"><span>选择类目</span>(您所选择的所有类目必须对应自有品牌，且选择经营的类目直接对应于需要缴纳的保证金金额，如果您不确定选择哪些类目，请联系招商人员。)</div>
        <div class="r_tl">请选择你要添加的类目</div>
        <div class="r_addwrap">
        	<ul class="r_add_title">
            	<li>一级类目</li>
                <li>二级类目</li>
                <li>三级类目(佣金比例%)</li>
            </ul>
            <div class="choose_add">        	
                <!--add begin-->
                <div class="scr_con">
                    <div style="overflow-y:scroll;overflow-x:hidden;height:100%;">
                        <div class="Scroller-Container">
                            <ul class="add_list" id="ul1">
                                 <c:forEach var="item" items="${productCategoryList}">
									<li  id="${item.id}" onclick="checkli1(this);"><a href ="javascript:void(0);">${item.name}</a></li>
								 </c:forEach>
                            </ul>
                        </div>
                    </div>
                    
                    <div>
                        <div class="Scrollbar-Track">
                            <div class="Scrollbar-Handle"></div>
                        </div>
                    </div>
                </div>
                <!--add end-->
                <div class="scr_con">
                    <div style="overflow-y:scroll;overflow-x:hidden;height:100%;">
                        <div class="Scroller-Container">
                            <ul class="add_list" id="ul2">
                            </ul>
                        </div>
                    </div>
                    
                    <div>
                        <div class="Scrollbar-Track">
                            <div class="Scrollbar-Handle"></div>
                        </div>
                    </div>
                </div>
                
                <div class="scr_con">
                    <div style="overflow-y:scroll;overflow-x:hidden;height:100%;">
                        <div class="Scroller-Container">
                            <ul class="add_last_list" id="ul3">
                            </ul>
                        </div>
                    </div>
                    
                    <div>
                        <div class="Scrollbar-Track">
                            <div class="Scrollbar-Handle"></div>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
        <div class="r_tl">已选择的类目</div>
        <div class="r_addwrap">
        	<table width="100%" border="1" cellpadding="0" cellspacing="0" class="choosetab" id="table">
        	<tr>
               <th>一级类目</th>
               <th>二级类目</th>
               <th>三级类目(佣金比例%)</th>
            </tr>
        	<c:forEach var="item" items="${supplierCategoryLists}">
        		<tr id="re${item.id}">
                <td><p onclick="delcategory(${item.id},'1')">${item.name}</p></td>
                <td>
	                <c:forEach var="item1" items="${item.childrens}">
		               	<p id="re${item1.id}" onclick="delcategory(${item1.id},'2')">${item1.name}</p>
	                </c:forEach>
                </td>
                <td>
	                <c:forEach var="item2" items="${item.childrens}">
		               	<c:forEach var="item3" items="${item2.childrens}">
		               		<p onclick="delcategory(${item3.id},'3')" id="re${item3.id}" idp="re${item2.id}" name="childrensid">${item3.name}           (${item3.commissionScale}%)</p>
	                	</c:forEach>
	                </c:forEach>
                </td>
              	</tr>
        	</c:forEach>
            </table>

        </div>
        
    </div>
    <div class="recuitment_btn">
    	<p class="h_saving" id="cg" style="display: none">√ 已保存。</p>
    	<p class="h_failed" id="sb" style="display: none">× 保存失败。</p> 
    	<p class="h_failed" id="ts" style="display: none">× 请先选择分类。</p>
    	<div class="recuitment_btn01" id="recuitment_btn01" onclick="papa('1');"><a href="javascript:void(0);">保存</a></div>
        <div class="recuitment_btn02" id="recuitment_btn02" onclick="papa('2');"><a href="javascript:void(0);">下一步，上传资质</a></div>
        <div class="recuitment_btn03" id="recuitment_btn03" onclick="papa('3');"><a href="javascript:void(0);">上一步</a></div>
    </div>
	<!--删除确认 begin-->
	<div class="popup_bg"></div>   
	<div class="shop_popup" id="shop_popup_delete">
	<input type="hidden" id="deleteid" value="" >
	<input type="hidden" id="typeid" value="" >
		<div class="popup_title" onclick="delcategory2();">
	    	<span>删除确认</span>
	        <label><img src="<%=static_resources %>images/close.gif" width="14" height="14" alt="close"></label>
	    </div>
	    <div class="popup_cont">	
			<div class="popup_txt">请确认是否删除选择类目？</div>
	        <div class="popup_btn">
	            <a href="javascript:void(0);" onclick="delcategory1();">确认</a>
	            <a id="dcansel" href="javascript:void(0);" onclick="delcategory2();">取消</a>
	        </div>
		</div>
	</div>
	<!--删除确认 end-->
</div>


<!--content end-->
<%@ include file="/commons/footer.jsp" %>

<script type="text/javascript">
  var jsApprType='${apprType}';		
  var jsPsid="${pid}";
  var jsCid="${cid}";
  var jsBasePath='${basePath}';
  var jsShopId='${shopId}';
  var jsOid='${oid}';
  var jsScsize="${scsize}";
  var jsApprId="${apprId}";
  
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_supplier_recruitmenttype.js"></script>
</body>