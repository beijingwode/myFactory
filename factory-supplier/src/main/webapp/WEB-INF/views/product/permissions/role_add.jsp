<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>   
<%@ include file="/commons/taglibs.jsp" %>
<%
String path = request.getContextPath();  
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path; 
pageContext.setAttribute("basePath",basePath);
String static_resources = basePath + "/static_resources/";
%>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragram" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate"> 
<meta http-equiv="expires" content="0"> 
<title>我的网商家中心</title>
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/add_style.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/bottom.css">
<link rel="stylesheet" type="text/css" href="<%=static_resources %>css/box.css">

<script type="text/javascript" src="<%=static_resources %>js/jquery1.8.0.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/index.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/scrollbar.js"></script>
<script type="text/javascript" src="<%=static_resources %>js/sleep.js"></script>
</head>
<body>
<%@ include file="/commons/header.jsp" %>
<!--content begin-->
<div id="content">
	<!--left begin-->
    <div class="left">
    	<ul class="left_list">
        	<li class="curr"><a href="${basePath}/permissions_role.html">角色</a></li>
        	<li style="border-bottom-style: none;"><a href="${basePath}/permissions_user.html">用户</a></li>
	    </ul>
    </div>
    <!--left end-->

    <div class="right">
        <div class="recruitment_title">添加角色</div>
        
        <div class="recruitment_cont">
            <div class="manufacturer">
            	<input type="hidden" name="id" id="id" value="${role.id}">
                <div class="la" style="margin-right:30px;"><b class="out">*</b>名称<input class="r_text" type="text" id="name" name="name" ismust="1" onblur="ajaxCheckRoleNameOnly(this.value);" typename="input" value="${role.name}"><span id="role_name_error" style="display:none;"></span></div>
                <div class="la">描述<input class="r_text" type="text" id="description" name="description" value="${role.description}"></div>
            </div>
            <div class="r_name martop"><span>选择目录</span>(选择操作菜单)</div>
            <div class="r_addwrap">
                <ul class="r_add_title">
                    <li>一级菜单</li>
                    <li>二级菜单</li>
                    <li>三级菜单</li>
                </ul>
                <div class="choose_add">
                    <!-- 一级类目 start -->
                    <div class="scr_con">
                        <div style="overflow-y:scroll;overflow-x:hidden;height:100%;">
                            <div class="Scroller-Container">
                                <ul class="add_list" id="ul1">
                                    <li id="10000" onclick="checkli1(this);" class="current"><a href="javascript:void(0);">我的店铺</a></li>
                                    <li id="20000" onclick="checkli1(this);"><a href="javascript:void(0);">商品管理</a></li>
                                    <li id="30000" onclick="checkli1(this);"><a href="javascript:void(0);">订单管理</a></li>
                                    <li id="40000" onclick="checkli1(this);"><a href="javascript:void(0);">配送管理</a></li>
                                    <li id="50000" onclick="checkli1(this);"><a href="javascript:void(0);">活动管理</a></li>
                                    <li id="60000" onclick="checkli1(this);"><a href="javascript:void(0);">结算管理</a></li>
                                    <li id="70000" onclick="checkli1(this);"><a href="javascript:void(0);">员工福利</a></li>
                                </ul>
                            </div>
                        </div>

                        <div>
                            <div class="Scrollbar-Track">
                                <div class="Scrollbar-Handle"></div>
                            </div>
                        </div>
                    </div>
                    <!-- 一级类目 end -->


                    <!-- 二级类目 start -->
                    <div class="scr_con">
                        <div style="overflow-y:scroll;overflow-x:hidden;height:100%;">
                            <div class="Scroller-Container">
									<ul class="add_list" id="ul2">
										<!-- 我的店铺 -->
										<li id="10100" onclick="checkli2(this);" idp="10000" level="2" isnextlevel="false" style="display: block;" des="我的店铺 > 基本信息" <c:if test="${fn:contains(resourceIds, ',1,')}">class="current"</c:if>>
											<a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="basicInfo" value="1"
												<c:if test="${fn:contains(resourceIds, ',1,')}">checked="checked"</c:if>>基本信息
											</a>
										</li>
										<li id="10200" onclick="checkli2(this);" idp="10000" level="2" isnextlevel="false" style="display: block;" des="我的店铺 > 店铺页面设置" <c:if test="${fn:contains(resourceIds, ',2,')}">class="current"</c:if>>
											<a href="javascript:void(0);"><input
												class="r_radio" type="checkbox" name="pageSet" value="2"
												<c:if test="${fn:contains(resourceIds, ',2,')}">checked="checked"</c:if>>店铺页面设置</a></li>
										<li id="10300" onclick="checkli2(this);" idp="10000" level="2" <c:if test="${fn:contains(resourceIds, ',3,')}">class="current"</c:if>
											isnextlevel="false" style="display: block;" des="我的店铺 > 分类管理"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="category" value="3"
												<c:if test="${fn:contains(resourceIds, ',3,')}">checked="checked"</c:if>>分类管理</a></li>
										<li id="10400" onclick="checkli2(this);" idp="10000" level="2" <c:if test="${fn:contains(resourceIds, ',4,') ||fn:contains(resourceIds, ',5,') || fn:contains(resourceIds, ',6,')}">class="current"</c:if>
											isnextlevel="true" style="display: block;" des=""><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value=""
												<c:if test="${fn:contains(resourceIds, ',4,') ||fn:contains(resourceIds, ',5,') || fn:contains(resourceIds, ',6,')}">checked="checked"</c:if>>商品归类</a></li>
										<!-- 商品管理 -->
										<li id="20100" onclick="checkli2(this);" idp="20000" level="2" <c:if test="${fn:contains(resourceIds, ',7,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;" des="商品管理 > 添加新商品"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value="7"
												<c:if test="${fn:contains(resourceIds, ',7,')}">checked="checked"</c:if>>添加新商品</a></li>
										<li id="20200" onclick="checkli2(this);" idp="20000" level="2" <c:if test="${fn:contains(resourceIds, ',8,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;"
											des="商品管理 > 在售商品管理"><a href="javascript:void(0);"><input
												class="r_radio" type="checkbox" name="" value="8" <c:if test="${fn:contains(resourceIds, ',8,')}">checked="checked"</c:if>>在售商品管理</a></li>
										<li id="20300" onclick="checkli2(this);" idp="20000" level="2" <c:if test="${fn:contains(resourceIds, ',9,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;"
											des="商品管理 > 待售商品管理"><a href="javascript:void(0);"><input
												class="r_radio" type="checkbox" name="" value="9" <c:if test="${fn:contains(resourceIds, ',9,')}">checked="checked"</c:if>>待售商品管理</a></li>
										<li id="20400" onclick="checkli2(this);" idp="20000" level="2" <c:if test="${fn:contains(resourceIds, ',10,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;"
											des="商品管理 > 审批中商品管理"><a href="javascript:void(0);"><input
												class="r_radio" type="checkbox" name="" value="10" <c:if test="${fn:contains(resourceIds, ',10,')}">checked="checked"</c:if>>审批中商品管理</a></li>
										<!-- 订单管理 -->
										<li id="30100" onclick="checkli2(this);" idp="30000" level="2" <c:if test="${fn:contains(resourceIds, ',11,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;"
											des="订单管理 > 已售出商品管理"><a href="javascript:void(0);"><input
												class="r_radio" type="checkbox" name="" value="11" <c:if test="${fn:contains(resourceIds, ',11,')}">checked="checked"</c:if>>已售出的商品</a></li>
										<li id="30200" onclick="checkli2(this);" idp="30000" level="2"  <c:if test="${fn:contains(resourceIds, ',12,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;" des="订单管理 > 评价管理"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value="12" <c:if test="${fn:contains(resourceIds, ',12,')}">checked="checked"</c:if>>评论管理</a></li>
										<!-- 配送管理 -->
										<li id="40100" onclick="checkli2(this);" idp="40000" level="2" <c:if test="${fn:contains(resourceIds, ',13,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;"
											des="配送管理 > 发货地址管理"><a href="javascript:void(0);"><input
												class="r_radio" type="checkbox" name="" value="13" <c:if test="${fn:contains(resourceIds, ',13,')}">checked="checked"</c:if>>发货地址管理</a></li>
										<li id="40200" onclick="checkli2(this);" idp="40000" level="2" <c:if test="${fn:contains(resourceIds, ',14,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;" des="配送管理 > 发货"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value="14" <c:if test="${fn:contains(resourceIds, ',14,')}">checked="checked"</c:if>>发货</a></li>
										<!-- 活动管理 -->
										<li id="50100" onclick="checkli2(this);" idp="50000" level="2" <c:if test="${fn:contains(resourceIds, ',15,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;"
											des="活动管理 > 活动申请中心"><a href="javascript:void(0);"><input
												class="r_radio" type="checkbox" name="" value="15" <c:if test="${fn:contains(resourceIds, ',15,')}">checked="checked"</c:if>>活动申请中心</a></li>
										<li id="50200" onclick="checkli2(this);" idp="50000" level="2" <c:if test="${fn:contains(resourceIds, ',16,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;"
											des="活动管理 > 申请中的活动"><a href="javascript:void(0);"><input
												class="r_radio" type="checkbox" name="" value="16" <c:if test="${fn:contains(resourceIds, ',16,')}">checked="checked"</c:if>>申请中的活动</a></li>
										<!-- 结算管理 -->
										<li id="60100" onclick="checkli2(this);" idp="60000" level="2" <c:if test="${fn:contains(resourceIds, ',17,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;" des="结算管理 > 对账管理"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value="17" <c:if test="${fn:contains(resourceIds, ',17,')}">checked="checked"</c:if>>对账管理</a></li>
										<li id="60200" onclick="checkli2(this);" idp="60000" level="2" <c:if test="${fn:contains(resourceIds, ',18,')}">class="current"</c:if> 
											isnextlevel="false" style="display: none;" des="结算管理 > 佣金返还"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value="18" <c:if test="${fn:contains(resourceIds, ',18,')}">checked="checked"</c:if>>佣金返还</a></li>
										<!-- 员工福利 -->
										<li id="70100" onclick="checkli2(this);" idp="70000" level="2" <c:if test="${fn:contains(resourceIds, ',19,') ||fn:contains(resourceIds, ',20,')}">class="current"</c:if> 
											isnextlevel="true" style="display: none;"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value="" <c:if test="${fn:contains(resourceIds, ',19,') ||fn:contains(resourceIds, ',20,')}">checked="checked"</c:if>>基本信息管理</a></li>
										<li id="70200" onclick="checkli2(this);" idp="70000" level="2" <c:if test="${fn:contains(resourceIds, ',21,')}">class="current"</c:if> 
											isnextlevel="true" style="display: none;"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value="" <c:if test="${fn:contains(resourceIds, ',21,')}">checked="checked"</c:if>>员工管理</a></li>
										<li id="70300" onclick="checkli2(this);" idp="70000" level="2" <c:if test="${fn:contains(resourceIds, ',22,') ||fn:contains(resourceIds, ',23,') || fn:contains(resourceIds, ',24,') || fn:contains(resourceIds, ',25,') || fn:contains(resourceIds, ',26,')}">class="current"</c:if> 
											isnextlevel="true" style="display: none;"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value="" <c:if test="${fn:contains(resourceIds, ',22,') ||fn:contains(resourceIds, ',23,') || fn:contains(resourceIds, ',24,') || fn:contains(resourceIds, ',25,') || fn:contains(resourceIds, ',26,')}">checked="checked"</c:if>>福利管理</a></li>
										<li id="70400" onclick="checkli2(this);" idp="70000" level="2" <c:if test="${fn:contains(resourceIds, ',27,')}">class="current"</c:if> 
											isnextlevel="true" style="display: none;"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value="" <c:if test="${fn:contains(resourceIds, ',27,')}">checked="checked"</c:if>>交易流水管理</a></li>
										<li id="70500" onclick="checkli2(this);" idp="70000" level="2" <c:if test="${fn:contains(resourceIds, ',28,') ||fn:contains(resourceIds, ',29,')}">class="current"</c:if> 
											isnextlevel="true" style="display: none;"><a
											href="javascript:void(0);"><input class="r_radio"
												type="checkbox" name="" value="" <c:if test="${fn:contains(resourceIds, ',28,') ||fn:contains(resourceIds, ',29,')}">checked="checked"</c:if>>友盟企业管理</a></li>
									</ul>
								</div>
                        </div>

                        <div>
                            <div class="Scrollbar-Track">
                                <div class="Scrollbar-Handle"></div>
                            </div>
                        </div>
                    </div>
                    <!-- 二级类目 end -->


                    <!-- 三级类目 start -->
                    <div class="scr_con" style="border-right-style: none;">
                        <div style="overflow-y:scroll;overflow-x:hidden;height:100%;">
                            <div class="Scroller-Container">
									<ul id="ul3" class="add_last_list">
										<!-- 我的店铺->商品归类  -->
										<li id="10401" onclick="checkli3(this);" idp="10400" level="3" <c:if test="${fn:contains(resourceIds, ',4,')}">class="current"</c:if> 
											des="我的店铺 > 商品归类 > 全部商品" <c:if test="${!(fn:contains(resourceIds, ',4,') || fn:contains(resourceIds, ',5,') || fn:contains(resourceIds, ',6,'))}">style="display: none;"</c:if>><a
											href="javascript:void(0);"><input type="checkbox"
												value="4" <c:if test="${fn:contains(resourceIds, ',4,')}">checked="checked"</c:if> name="" class="r_radio">全部商品</a></li>
										<li id="10402" onclick="checkli3(this);" idp="10400" level="3" <c:if test="${fn:contains(resourceIds, ',5,')}">class="current"</c:if> 
											des="我的店铺 > 商品归类 > 未分类商品" <c:if test="${!(fn:contains(resourceIds, ',4,') || fn:contains(resourceIds, ',5,') || fn:contains(resourceIds, ',6,'))}">style="display: none;" </c:if>><a
											href="javascript:void(0);"><input type="checkbox"
												value="5" <c:if test="${fn:contains(resourceIds, ',5,')}">checked="checked"</c:if> name="" class="r_radio">未分类商品</a></li>
										<li id="10403" onclick="checkli3(this);" idp="10400" level="3" <c:if test="${fn:contains(resourceIds, ',6,')}">class="current"</c:if> 
											des="我的店铺 > 商品归类 > 已分类商品" <c:if test="${!(fn:contains(resourceIds, ',4,') || fn:contains(resourceIds, ',5,') || fn:contains(resourceIds, ',6,'))}">style="display: none;" </c:if>><a
											href="javascript:void(0);"><input type="checkbox"
												value="6" <c:if test="${fn:contains(resourceIds, ',6,')}">checked="checked"</c:if> name="" class="r_radio">已分类商品</a></li>
												
										<!-- 员工福利->基本信息管理  -->
										<li id="70101" onclick="checkli3(this);" idp="70100" level="3" <c:if test="${fn:contains(resourceIds, ',19,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 基本信息管理 > 企业基本信息"><a
											href="javascript:void(0);"><input type="checkbox"
												value="19" <c:if test="${fn:contains(resourceIds, ',19,')}">checked="checked"</c:if> name="" class="r_radio">企业基本信息</a></li>
										<li id="70102" onclick="checkli3(this);" idp="70100" level="3" <c:if test="${fn:contains(resourceIds, ',20,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 基本信息管理 > 公司结构管理"><a
											href="javascript:void(0);"><input type="checkbox"
												value="20" <c:if test="${fn:contains(resourceIds, ',20,')}">checked="checked"</c:if> name="" class="r_radio">公司结构管理</a></li>
										<!-- 员工福利->员工管理  -->
										<li id="70201" onclick="checkli3(this);" idp="70200" level="3" <c:if test="${fn:contains(resourceIds, ',21,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 员工管理 > 员工管理"><a
											href="javascript:void(0);"><input type="checkbox"
												value="21" <c:if test="${fn:contains(resourceIds, ',21,')}">checked="checked"</c:if> name="" class="r_radio">员工管理</a></li>
												
										<!-- 员工福利->福利管理  -->
										<li id="70301" onclick="checkli3(this);" idp="70300" level="3" <c:if test="${fn:contains(resourceIds, ',22,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 福利管理 > 福利额度总览"><a
											href="javascript:void(0);"><input type="checkbox"
												value="22" <c:if test="${fn:contains(resourceIds, ',22,')}">checked="checked"</c:if> name="" class="r_radio">福利额度总览</a></li>
										<li id="70302" onclick="checkli3(this);" idp="70300" level="3" <c:if test="${fn:contains(resourceIds, ',23,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 福利管理 > 额度申请"><a
											href="javascript:void(0);"><input type="checkbox"
												value="23" <c:if test="${fn:contains(resourceIds, ',23,')}">checked="checked"</c:if> name="" class="r_radio">额度申请</a></li>
										<li id="70303" onclick="checkli3(this);" idp="70300" level="3" <c:if test="${fn:contains(resourceIds, ',24,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 福利管理 > 现金储值"><a
											href="javascript:void(0);"><input type="checkbox"
												value="24" <c:if test="${fn:contains(resourceIds, ',24,')}">checked="checked"</c:if> name="" class="r_radio">现金储值</a></li>
										<li id="70304" onclick="checkli3(this);" idp="70300" level="3" <c:if test="${fn:contains(resourceIds, ',25,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 福利管理 > 福利发放"><a
											href="javascript:void(0);"><input type="checkbox"
												value="25" <c:if test="${fn:contains(resourceIds, ',25,')}">checked="checked"</c:if> name="" class="r_radio">福利发放</a></li>
										<li id="70305" onclick="checkli3(this);" idp="70300" level="3" <c:if test="${fn:contains(resourceIds, ',26,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 福利管理 > 福利流水"><a
											href="javascript:void(0);"><input type="checkbox"
												value="26" <c:if test="${fn:contains(resourceIds, ',26,')}">checked="checked"</c:if> name="" class="r_radio">福利流水</a></li>
												
										<!-- 员工福利->交易流水管理  -->
										<li id="70401" onclick="checkli3(this);" idp="70400" level="3" <c:if test="${fn:contains(resourceIds, ',27,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 交易流水管理 > 员工交易流水"><a
											href="javascript:void(0);"><input type="checkbox"
												value="27" <c:if test="${fn:contains(resourceIds, ',27,')}">checked="checked"</c:if> name="" class="r_radio">员工交易流水</a></li>
										<!-- 员工福利->友盟企业管理  -->
										<li id="70501" onclick="checkli3(this);" idp="70500" level="3" <c:if test="${fn:contains(resourceIds, ',28,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 友盟企业管理 > 友盟企业管理"><a
											href="javascript:void(0);"><input type="checkbox"
												value="28" <c:if test="${fn:contains(resourceIds, ',28,')}">checked="checked"</c:if> name="" class="r_radio">友盟企业管理</a></li>
										<li id="70502" onclick="checkli3(this);" idp="70500" level="3" <c:if test="${fn:contains(resourceIds, ',29,')}">class="current"</c:if> 
											style="display: none;" des="员工福利 > 友盟企业管理 > 划拨内购券额度"><a
											href="javascript:void(0);"><input type="checkbox"
												value="29" <c:if test="${fn:contains(resourceIds, ',29,')}">checked="checked"</c:if>  name="" class="r_radio">划拨内购券额度</a></li>
									</ul>
								</div>
                        </div>

                        <div>
                            <div class="Scrollbar-Track">
                                <div class="Scrollbar-Handle"></div>
                            </div>
                        </div>
                    </div>
                    <!-- 三级类目 end -->

                </div>
            </div>
            <div class="r_tl">已选择的目录</div>
            <div class="r_addwrap">
                <table width="100%" border="1" cellpadding="0" cellspacing="0" class="choosetab" id="table">
	                <tr>
		               <th>一级菜单</th>
		               <th>二级菜单</th>
		               <th>三级菜单</th>
		            </tr>
                    <tbody id="selected">
                    </tbody>
                </table>
            </div>
        </div>
        <div class="recuitment_btn">
            <p class="h_saving" id="cg" style="display: none">√ 已保存。</p>
            <p class="h_failed" id="sb" style="display: none"></p>
            <p class="h_failed" id="ts" style="display: none">× 请先选择分类。</p>
            <div class="recuitment_btn01" id="recuitment_btn01"><a href="javascript:void(0);"  onclick="save('${status}')">保存</a></div>
            <!-- <div class="recuitment_btn02" id="recuitment_btn02" onclick="papa(&#39;2&#39;);"><a href="javascript:void(0);">下一步，上传资质</a></div>
            <div class="recuitment_btn03" id="recuitment_btn03" onclick="papa(&#39;3&#39;);"><a href="role.html">上一步</a></div> -->
        </div>
    </div>
</div>
<!--content end-->
<%@ include file="/commons/footer.jsp" %>
<%@ include file="/commons/alertMessage.jsp" %>

<script type="text/javascript">
var jsBasePath='${basePath}';
var seleCategory = new Array(), adds = []; olds = [${resourceIds}];    
</script>
<script type="text/javascript" src="<%=static_resources %>js/product_permissions_roleAdd.js"></script>

</body>
</html>