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
        	<li style="border-bottom-style: none;"><a href="${basePath}/permissions_role.html">角色</a></li>
        	<li class="curr"><a href="${basePath}/permissions_user.html">用户</a></li>
	    </ul>
    </div>
    <!--left end-->
    <div class="right">
        <div class="recruitment_title">添加用户</div>
        <div class="recruitment_cont">
            <div class="manufacturer">
                <div class="la" style="margin-right:30px;">名称<input class="r_text" type="text" id="type" name="type"  value=""></div>
                <div class="la">选择角色<input class="r_text" type="text" id="type" name="type"  value=""></div>
                <div class="la">密码<input class="r_text" type="text" id="type" name="type"  value=""></div>
            </div>
            <div class="r_name martop"><span>选择类目</span>(您所选择的所有类目必须对应自有品牌，且选择经营的类目直接对应于需要缴纳的保证金金额，如果您不确定选择哪些类目，请联系招商人员。)</div>
            <div class="r_tl">请选择你要添加的类目</div>
            <div class="r_addwrap">
                <ul class="r_add_title">
                    <li>一级类目</li>
                    <li>二级类目</li>
                    <li>三级类目</li>
                </ul>
                <div class="choose_add">        	
                    <!--add begin-->
                    <div class="scr_con">
                        <div style="overflow-y:scroll;overflow-x:hidden;height:100%;">
                            <div class="Scroller-Container">
                                <ul class="add_list" id="ul1">
                                     
                                        <li id="10000" onclick="checkli1(this);" class=""><a href="javascript:void(0);">服饰/鞋类/箱包</a></li>
                                     
                                        <li id="20000" onclick="checkli1(this);" class=""><a href="javascript:void(0);">运动户外</a></li>
                                     
                                        <li id="30000" onclick="checkli1(this);"><a href="javascript:void(0);">珠宝配饰</a></li>
                                     
                                        <li id="40000" onclick="checkli1(this);" class="current"><a href="javascript:void(0);">个护化妆</a></li>
                                     
                                        <li id="50000" onclick="checkli1(this);"><a href="javascript:void(0);">居家日用</a></li>
                                     
                                        <li id="60000" onclick="checkli1(this);"><a href="javascript:void(0);">家装/家具/家纺</a></li>
                                     
                                        <li id="70000" onclick="checkli1(this);"><a href="javascript:void(0);">食品/酒类/粮油</a></li>
                                     
                                        <li id="80000" onclick="checkli1(this);"><a href="javascript:void(0);">电器/数码/办公</a></li>
                                     
                                        <li id="90000" onclick="checkli1(this);"><a href="javascript:void(0);">母婴玩具</a></li>
                                     
                                        <li id="100000" onclick="checkli1(this);" style="border-bottom-style: none;"><a href="javascript:void(0);">图书/音像/乐器</a></li>
                                     
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
                                <ul class="add_list" id="ul2"><li class="allselect" onclick="selectall2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">全选</a></li><li id="40100" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">个人洗护</a></li><li id="40200" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">口腔护理</a></li><li id="40300" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">男士剃须</a></li><li id="40400" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">其他</a></li><li id="40500" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">个人清洁用具</a></li><li id="40600" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">面部护肤</a></li><li id="40700" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">精致彩妆</a></li><li id="40800" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">男士护理</a></li><li id="40900" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">其他护理</a></li><li id="41000" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">香水</a></li><li id="41100" onclick="checkli2(this);"><a href="javascript:void(0);"><input class="r_radio" type="checkbox" name="" value="">美容工具</a></li></ul>
                            </div>
                        </div>
                        
                        <div>
                            <div class="Scrollbar-Track">
                                <div class="Scrollbar-Handle"></div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="scr_con" style="border-right-style: none;">
                        <div style="overflow-y:scroll;overflow-x:hidden;height:100%;">
                            <div class="Scroller-Container">
                                <ul class="add_last_list" id="ul3"></ul>
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
                <table width="100%" border="1" cellpadding="0" cellspacing="0" class="choosetab" id="table"><tbody><tr><th>一级类目</th><th>二级类目</th><th>三级类目</th></tr><tr id="re80000"><td><p onclick="delcategory(80000,1)">电器/数码/办公</p></td><td><p id="re80200" onclick="delcategory(80200,2)">大家电</p></td><td><p onclick="delcategory(80202,3)" id="re80202" idp="re80200" name="childrensid">冰箱</p></td></tr></tbody></table>
    
            </div>
        </div>
        <div class="recuitment_btn">
            <p class="h_saving" id="cg" style="display: none">√ 已保存。</p>
            <p class="h_failed" id="sb" style="display: none">× 保存失败。</p> 
            <p class="h_failed" id="ts" style="display: none">× 请先选择分类。</p>
            <div class="recuitment_btn01" id="recuitment_btn01" onclick="papa(&#39;1&#39;);"><a href="javascript:void(0);">保存</a></div>
            <div class="recuitment_btn02" id="recuitment_btn02" onclick="papa(&#39;2&#39;);"><a href="javascript:void(0);">下一步，上传资质</a></div>
            <div class="recuitment_btn03" id="recuitment_btn03" onclick="papa(&#39;3&#39;);"><a href="user.html">上一步</a></div>
        </div>
        <!--删除确认 begin-->
        <div class="popup_bg"></div>   
        <div class="shop_popup" id="shop_popup_delete">
        <input type="hidden" id="deleteid" value="">
        <input type="hidden" id="typeid" value="">
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
</div>
<!--content end-->

<%@ include file="/commons/footer.jsp" %>



    <script type="text/javascript" src="<%=static_resources %>js/product_permissions_userAdd.js"></script>
</body>
</html>