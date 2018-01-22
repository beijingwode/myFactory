<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ page trimDirectiveWhitespaces="true" %>

<header class="headercolor">
    <!--top begin-->
    <div id="top">
        <div class="top_cont">
            <div class="top_cont_left_fl" style="float:left;">
                <span><strong><c:if test="${empty sessionScope.userSession.nickName}">${sessionScope.userSession.userName}</c:if><c:if test="${not empty sessionScope.userSession.nickName}">${sessionScope.userSession.nickName}</c:if></strong>&nbsp;&nbsp;你好，欢迎回来！</span>
                <label><a href="${basePath}/user/loginOut.html">退出</a></label>
            </div>
            <div class="top_cont_right">
                <ul>
                	<li><a href="${basePath}/develop_doc_html/develop_doc11.html">开发文档</a></li>
                    <li style="margin-left:20px;"><a href="${basePath}/user/login.html">商家平台首页</a></li>
                </ul>
            </div>
        </div>

    </div>
    <!--top end-->

    <!--header begin-->
    <div id="header">
        <div class="header_cont">
            <div class="logo">
                <a href="${basePath}/user/tosuppliermain.html"><img src="<%=static_resources %>images/logo.jpg" width="200"
                                                                    height="50" alt="logo"></a>
            </div>
            <div class="nav">
                <ul>
                    <li id="sy_header"><a href="${basePath}/user/tosuppliermain.html">首页</a></li>
                    <c:if test="${userSession.shopCount >0}">
                    <c:if test="${userSession.type != 3 || userSession.hasAuth('myShop')}">
                        <li id="wddp_header"><a href="${basePath}/${userSession.type != 3 ? 'shopSetting.html' : userSession.getAuthUri('myShop')}">我的店铺</a></li>
                    </c:if>
                    <c:if test="${userSession.type != 3 || userSession.hasAuth('productManage')}">
                        <li id="spgl_header"><a href="${basePath}/${userSession.type != 3 ? 'product/toSelectProducttype.html' : userSession.getAuthUri('productManage')}">商品管理</a></li>
                    </c:if>
                    <c:if test="${userSession.type != 3 || userSession.hasAuth('orderMange')}">
                        <li id="ddgl_header"><a href="${basePath}/${userSession.type != 3 ? 'suborder/gotoSelllist.html' : userSession.getAuthUri('orderMange')}">订单管理</a></li>
                    </c:if>
                    <c:if test="${userSession.type != 3 || userSession.hasAuth('sentManage')}">
                        <li id="psgl_header"><a href="${basePath}/${userSession.type != 3 ? 'shippingAddress/todeliver.html' : userSession.getAuthUri('sentManage')}">配送管理</a></li>
                    </c:if>
                    <c:if test="${userSession.type != 3 || userSession.hasAuth('promotionManage')}">
                        <li id="hdgl_header"><a href="${basePath}/${userSession.type != 3 ? 'activity/qicai/page.html' : userSession.getAuthUri('promotionManage')}">活动管理</a></li>
                    </c:if>
                    <c:if test="${userSession.type != 3 || userSession.hasAuth('billManage')}">
                        <li id="jsgl_header"><a href="${basePath}/${userSession.type != 3 ? 'saleBill/gotoSaleBillList.html' : userSession.getAuthUri('billManage')}">结算管理</a></li>
                    </c:if>
                    </c:if>
                    <c:if test="${userSession.type != 3 || userSession.hasAuth('staffFuli')}">
                        <li id="qygl_header"><a href="${basePath}/${userSession.type != 3 ? 'company/emp/page.html' : userSession.getAuthUri('staffFuli')}">员工福利</a></li>
                    </c:if>
                    <c:if test="${userSession.type eq 2 }">
                        <li id="permissions_header"><a href="${basePath}/permissions_role.html">权限管理</a></li>
                    </c:if>
                </ul>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        function selectedHeaderLi(liname) {
            if (liname == '') {
                liname = "sy_header";
            }
            if (!$("#" + liname).hasClass("active")) {
                $("#" + liname).addClass("active");
            }
        }
    </script>
    <!--header end-->
</header>
