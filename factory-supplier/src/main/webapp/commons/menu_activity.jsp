<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript">
    $(document).ready(function () {
        selectedHeaderLi("hdgl_header");
    });
    
    function activeMenu(menuId) {
		//加载页面，控制左边的菜单
		$("#"+menuId).parent().parent().prev().addClass("active");
		$("#"+menuId).parent().parent().attr("style","display: block;");
		$("#"+menuId).parent().addClass("active");
		$("#"+menuId).addClass("active");
    }
</script>
<script type="text/javascript" src="<%=static_resources %>js/menu_min.js"></script>
<!--left begin-->
<div class="left">
    <div class="left_store_list">
        <ul>
			<c:if test="${userSession.type != 3 || userSession.hasAuth('productOnsell')}">
                <li class="cl_show">
                    <a href="javascript:;">
                        <i class="icon01"></i>阶梯价格（企采/集采）
                    </a>
                    <ul class="twomenu">
                        <li><a id="menu_activity_qicai" href="${basePath}/activity/qicai/page.html">阶梯价格（企采/集采）</a></li>
                    </ul>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<!--left end-->
