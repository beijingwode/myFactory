<%@ page contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    pageContext.setAttribute("basePath", basePath);
    String static_resources = basePath + "/static_resources/";
%>
<%@ taglib uri="/WEB-INF/tlds/PageFormTag.tld" prefix="wodepageform" %>
<%@ include file="/commons/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<title>我的网商家中心</title>
<%@ include file="/commons/header.jsp" %>
<%@ include file="/commons/js.jsp" %>
<!--header end-->

<!--content begin-->
<div id="content">
    <!--left begin-->
    <div class="left">
        <div class="left_list">
            <ul>
                <c:if test="${userSession.type != 3 || userSession.hasAuth('sellList')}">
                    <li><a href="${basePath}/suborder/gotoSelllist.html">已售出的商品</a></li>
                </c:if>
                <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
                    <li class="curr"><a href="${basePath}/comments/toevaluation.html?commentDegree=all">评价管理</a></li>
                </c:if>
	             <c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
	                 <li><a href="${basePath}/questionnaire/templates.html">问卷模板</a></li>
	             </c:if>
				<c:if test="${userSession.type != 3 || userSession.hasAuth('comments')}">
					<li><a href="${basePath}/questionnaire/trialProduct.html?leftMenu=order">试用商品问卷</a></li>
				</c:if>
            </ul>
        </div>
    </div>
    <!--left end-->

    <!--right begin-->
    <div class="right">
        <div class="position">
            <span>您所在的位置：</span>
            <a href="javascript:void(0);">商家中心</a><em>></em>
            <a href="${basePath}/suborder/gotoSelllist.html">订单管理</a><em>></em>
            <a href="${basePath}/comments/toevaluation.html?commentDegree=all">评价管理</a>
        </div>
        <div class="sale_wrap">
            <div class="Evaluation">
                <p class="E_title"><span>好评率： <em>${goods}</em></span>最近评价</p>
                <div class="table">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr class="tr_title">
                            <td>&nbsp;</td>
                            <td>最近1周</td>
                            <td>最近1个月</td>
                            <td>最近6个月</td>
                            <td>6个月前</td>
                            <td>总计</td>
                        </tr>
                        <tr>
                            <td>好评</td>
                            <td>${good.split(",")[0] }</td>
                            <td>${good.split(",")[1] }</td>
                            <td>${good.split(",")[2] }</td>
                            <td>${good.split(",")[3] }</td>
                            <td>${good.split(",")[4] }</td>
                        </tr>
                        <tr>
                            <td>中评</td>
                            <td>${medium.split(",")[0] }</td>
                            <td>${medium.split(",")[1] }</td>
                            <td>${medium.split(",")[2] }</td>
                            <td>${medium.split(",")[3] }</td>
                            <td>${medium.split(",")[4] }</td>
                        </tr>
                        <tr>
                            <td>差评</td>
                            <td>${bad.split(",")[0] }</td>
                            <td>${bad.split(",")[1] }</td>
                            <td>${bad.split(",")[2] }</td>
                            <td>${bad.split(",")[3] }</td>
                            <td>${bad.split(",")[4] }</td>
                        </tr>
                        <tr>
                            <td>总计</td>
                            <td>${tote.split(",")[0] }</td>
                            <td>${tote.split(",")[1] }</td>
                            <td>${tote.split(",")[2] }</td>
                            <td>${tote.split(",")[3] }</td>
                            <td>${tote.split(",")[4] }</td>
                        </tr>
                    </table>

                </div>
            </div>

            <div class="Evaluation_con">
                <ul class="E_c_title">
                    <li class="li1"><select name="commentDegree" id="select" onchange="_jump();">
                        <option value="all" <c:if test="${commentDegree == 'all'}">selected="selected"</c:if>>全部
                        </option>
                        <option value="5"
                                <c:if test="${commentDegree eq '5'||commentDegree eq '4'}">selected="selected"</c:if>>好评
                        </option>
                        <option value="3" <c:if test="${commentDegree eq '3'}">selected="selected"</c:if>>中评</option>
                        <option value="1"
                                <c:if test="${commentDegree eq '1'||commentDegree eq '2'}">selected="selected"</c:if>>差评
                        </option>
                    </select></li>
                    <li class="li2">评价</li>
                    <li class="li3">评论人</li>
                    <li class="li4">商品信息</li>
                    <div class="clear"></div>
                </ul>
                <ul class="E_c_c">
                    <c:forEach var="item" items="${csList}">
                        <li><c:if test="${item.commentDegree eq 5}"></c:if>
                            <span class="star"><i
                                        <c:if test="${item.commentDegree eq 5}"></c:if>
                                <c:if test="${item.commentDegree eq 3}">class="star1" </c:if>
                                            <c:if test="${item.commentDegree eq 1}">class="star2"</c:if>></i></span>
                            <div class="pl">
                                <p class="p_c">${item.text}</p>
                                <p class="p_t">[<fmt:formatDate value="${item.creatTime}"
                                                                pattern="yyyy-MM-dd HH:mm:ss"/>]</p>
                            </div>
                            <div class="plr">买家 ： <em><c:if test="${'1' eq item.anonymous}">***</c:if><c:if
                                    test="${'0' eq item.anonymous || empty item.anonymous}"><c:if
                                    test="${empty item.user.nickName}">${item.user.userName}</c:if><c:if
                                    test="${not empty item.user.nickName}">${item.user.nickName}</c:if></c:if></em>
                            </div>
                            <div class="spxx">
                                <p class="p_c blue">${item.product.fullName}</p>
                                <p class="p_t red">${item.suborderitem.price}元</p>
                            </div>
                            <div class="clear"></div>
                        </li>
                    </c:forEach>
                </ul>

                <wodepageform:PageFormTag pageSize="${result.size}" totalPage="${result.totalPage}"
                                          currentPage="${result.page}" url=""/>
                <form id="sub_form" action="${basePath}/comments/toevaluation.html?commentDegree=${commentDegree}"
                      method="post">
                    <input type="hidden" id="pages" name="pages" value="${pages}"/>
                    <input type="hidden" id="sizes" name="sizes" value="${sizes}"/>
                </form>
            </div>

        </div>
    </div>
    <!--right end-->
</div>
<!--content end-->


<script type="text/javascript">
    $(document).ready(function () {
        selectedHeaderLi("ddgl_header");
    });
    function _jump() {
        var cd = $("#select").val();
        window.location.href = "${basePath}/comments/toevaluation.html?commentDegree=" + cd;
    }
    /** * 表单提交*/
    function formSubmit(page) {
        if (page != undefined) {
            $("#pages").val(page);
        } else {
            $("#pages").val(1);
        }
        $("#sub_form").submit();
    }
    /*** 快速跳转*/
    function gotoPage() {
        var pagenum = $("#pagenum").val();
        formSubmit(pagenum);
    }
</script>

<%@ include file="/commons/footer.jsp" %>
</body>
</html>
