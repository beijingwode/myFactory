<%--
  User: Bing King
  Date: 2015/2/27
  Time: 17:27
  分页组件
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="taglibs.jsp" %>
<form id="pageForm" action="" method="post">
    <input type="hidden" id="pageNum" name="pageNumber" value="${page.offset}"/>
    <div class="page">
        <c:if test="${page.offset > 1}">
            <a href="javascript:" onclick="goto('${page.offset -1}');">前一页</a>
        </c:if>
        <c:forEach var="i" begin="${(page.offset - (5 - 1)/2) < 1 ? 1 : (page.offset - (5 - 1)/2) }" end="${(page.offset + (5-1)/2) > page.pagerSize ? page.pagerSize : (page.offset + (5-1)/2)}" varStatus="status">
            <c:if test="${status.first && i <= 3}">
                <c:forEach var="j" begin="1" end="${i - 1 }">
                    <a href="javascript:" onclick="goto('${j}');">${j }</a>
                </c:forEach>
            </c:if>
            <c:if test="${status.first && i > 3}">
                <a href="javascript:" onclick="goto('1');">1</a>
                <a href="javascript:" onclick="goto('2');">2</a>
                <span>...</span>
            </c:if>
            <c:if test="${i != page.offset }">
                <a href="javascript:" onclick="goto('${i}');">${i }</a>
            </c:if>
            <c:if test="${i == page.offset }">
                <a href="javascript:" onclick="goto('${i}');">${i }</a>
            </c:if>
            <c:if test="${status.last && i < (page.pagerSize - 1)}">
                <span>...</span>
            </c:if>
        </c:forEach>
        <c:if test="${page.offset < page.pagerSize }">
            <a href="javascript:" onclick="goto('${page.offset + 1}');">后一页</a>
        </c:if>
        <span>共${page.pagerSize }页</span>
        <input type="text" id="page" class="input_text" value="${page.offset}">
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
        if(!isNaN(pageNum) && pageNum > 0 && pageNum <= ${page.pagerSize}) {
            goto(pageNum);
        } else {
            $("#page").val($("#pageNum").val());
        }
    }
</script>
