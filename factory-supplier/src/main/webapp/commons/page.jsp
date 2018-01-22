<%--
  User: Bing King
  Date: 2015/2/27
  Time: 17:27
  分页组件
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp" %>
<form id="pageForm" action="" method="post">
    <input type="hidden" id="pageNum" name="pageNumber" value="${page.pageNum}"/>
    <div class="page">
        <c:if test="${!page.isFirstPage}">
            <a href="javascript:;" onclick="goto('${page.prePage}');">前一页</a>
        </c:if>
        <c:forEach var="i" begin="${(page.pageNum - (5 - 1)/2) < 1 ? 1 : (page.pageNum - (5 - 1)/2) }" end="${(page.pageNum + (5-1)/2) > page.lastPage ? page.lastPage : (page.pageNum + (5-1)/2)}" varStatus="status">
            <c:if test="${status.first && i <= 3}">
                <c:forEach var="j" begin="1" end="${i - 1 }">
                    <a href="javascript:;" onclick="goto('${j}');">${j }</a>
                </c:forEach>
            </c:if>
            <c:if test="${status.first && i > 3}">
                <a href="javascript:;" onclick="goto('1');">1</a>
                <a href="javascript:;" onclick="goto('2');">2</a>
                <span>...</span>
            </c:if>
            <c:if test="${i != page.pageNum }">
                <a href="javascript:;" onclick="goto('${i}');">${i }</a>
            </c:if>
            <c:if test="${i == page.pageNum }">
                <a href="javascript:;" onclick="goto('${i}');">${i }</a>
            </c:if>
            <c:if test="${status.last && i < (page.lastPage - 1)}">
                <span>...</span>
            </c:if>
        </c:forEach>
        <c:if test="${page.pageNum < page.lastPage }">
            <a href="javascript:;" onclick="goto('${page.nextPage}');">后一页</a>
        </c:if>
        <span>共${page.lastPage }页</span>
        <input type="text" id="page" class="input_text" value="${page.pageNum}">
        <span>页</span>
        <input type="button" id="page_button" value="确定" onclick="jumpto()"  class="input_btn">
    </div>
</form>
<script type="text/javascript">
    function goto(page) {
        $("#pageNum").val(page);
        $("#pageForm").submit();
    }

    function jumpto(){
        var pageNum = $.trim($("#page").val());
        if(!isNaN(pageNum) && pageNum > 0 && pageNum <= ${page.lastPage}) {
            goto(pageNum);
        } else {
            $("#page").val($("#pageNum").val());
        }
    }
</script>