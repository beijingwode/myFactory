<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.wode.common.util.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<c:set var="userdomain"  value="${initParam['userdomain']}" />
<c:set var="productdomain"  value="${initParam['productdomain']}" />
<c:set var="paydomain"  value="${initParam['paydomain']}" />
<%
request.setAttribute("ctx", StringUtils.TrimRight(request.getServletContext().getContextPath(),"/")+"/");
%>
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}"/> --%>
<c:set var="url" value="${initParam['resdomain']}"/>  <%--资源文件使用这个  --%>
<c:set var="lparam" value="${pageContext.request.queryString }"></c:set> <%-- 得到当前页面的url？后面的参数 --%>
