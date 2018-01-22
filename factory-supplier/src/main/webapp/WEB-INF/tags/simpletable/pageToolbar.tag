<%@ tag pageEncoding="UTF-8"%>
<%@ attribute name="page" required="true" type="cn.org.rapid_framework.page.Page" description="Page.java" %>
<%@ attribute name="pageSizeSelectList" type="java.lang.Number[]" required="false"  %>
<%@ attribute name="isShowPageSizeList" type="java.lang.Boolean" required="false"  %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	// set default values
	Integer[] defaultPageSizes = new Integer[]{10,50,100};
	if(jspContext.getAttribute("pageSizeSelectList") == null) {
		jspContext.setAttribute("pageSizeSelectList",defaultPageSizes); 
	}
	
	if(jspContext.getAttribute("isShowPageSizeList") == null) {
		jspContext.setAttribute("isShowPageSizeList",true); 
	} 
%>

		<c:if test="${isShowPageSizeList}">
			<div class="form-group" style="float:right;width:80px;margin-left:10px;">
				<select class="form-control" onChange="simpleTable.togglePageSize(this.value)">
					<c:forEach var="item" items="${pageSizeSelectList}">
						<option value="${item}" ${page.pageSize == item ? 'selected' : '' }>${item}</option>
					</c:forEach> 
				</select>
			</div>
		</c:if>
		
		<ul class="pagination pagination-sm" style="margin:0px; padding-top:3px; margin-left:10px;float:right;">
			<%--第一页 
			<c:choose>
				<c:when test="${page.firstPage}"><img src="<c:url value='/resources/widgets/simpletable/images/firstPageDisabled.gif'/>" style="border:0" ></c:when>
				<c:otherwise><a href="javascript:simpleTable.togglePage(1);"><img src="<c:url value='/resources/widgets/simpletable/images/firstPage.gif'/>" style="border:0" ></a></c:otherwise>
			</c:choose>	 
			--%> 
		 	<!-- 上一页 
		 		<li class="prev page"><a class="follow_link" href="http://192.168.0.51/admin/user/index/10">«</a></li>
		 	-->
		 	<c:choose>
				<c:when test="${page.hasPreviousPage}"><li class="prev page"><a class="follow_link" href="javascript:simpleTable.togglePage(${page.previousPageNumber});">«</a></li></c:when>
				<%-- <c:otherwise><img src="<c:url value='/resources/widgets/simpletable/images/prevPageDisabled.gif'/>" style="border:0" ></c:otherwise> --%>
			</c:choose>
            <c:forEach var="item" items="${page.linkPageNumbers}">
				<c:choose>
					<c:when test="${item == page.thisPageNumber}">
						<li class="active">
							<a href="javascript:simpleTable.togglePage(${item});">${item}</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="page">
							<a class="follow_link" href="javascript:simpleTable.togglePage(${item});">${item}</a>
						</li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
            <!-- 下一页
            	<li class="next page"><a class="follow_link" href="http://192.168.0.51/admin/user/index/30">»</a></li>
            -->
            <c:choose>
				<c:when test="${page.hasNextPage}"><li class="next page"><a class="follow_link" href="javascript:simpleTable.togglePage(${page.nextPageNumber});">»</a></li></c:when>
				<%-- <c:otherwise><img src="<c:url value='/resources/widgets/simpletable/images/nextPageDisabled.gif'/>" style="border:0" ></c:otherwise> --%>
			</c:choose>
			<!-- 最后一页
				<c:choose>
					<c:when test="${page.lastPage}"><img src="<c:url value='/resources/widgets/simpletable/images/lastPageDisabled.gif'/>" style="border:0"></c:when>
					<c:otherwise><a href="javascript:simpleTable.togglePage(${page.lastPageNumber});"><img src="<c:url value='/resources/widgets/simpletable/images/lastPage.gif'/>" style="border:0" ></a></c:otherwise>
				</c:choose>
			-->            
		</ul><!--pagination-->
		
		<lable style="font-size:14px; float:right; padding-top:8px;">${page.thisPageFirstElementNumber} - ${page.thisPageLastElementNumber} of ${page.totalCount}</lable>