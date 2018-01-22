<%@ page import="com.wode.common.util.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>

<%
    request.setAttribute("ctx", StringUtils.TrimRight(request.getServletContext().getContextPath(), "/")+"/");
%>
<c:set var="url" value="${initParam['resdomain']}"/>
<c:set var="lparam" value="${pageContext.request.queryString }"></c:set>