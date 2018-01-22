<html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.wode.common.util.StringUtils" %>
<head>
    <title>Test api.wode.com word</title>
    <style>
        body {font-size:8pt;}
        ol {line-height:18px;}
    </style>
</head>
<body>
    <%
        request.setAttribute("ctx", StringUtils.TrimRight(request.getServletContext().getContextPath(), "/")+"/");
    %>
    <form action="${ctx}word/test.html" method="post">
        <table>
            <tr>
                <td>方法名称：</td>
                <td><input type="text" name="methodName"></td>
            </tr>
            <tr>
                <td>参数名称：</td>
                <td><input type="text" name="paramName"></td>
            </tr>
            <tr>
                <td>参数值：</td>
                <td><textarea name="param"></textarea></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" name="提交"></td>
            </tr>
        </table>
    </form>
</body>
</html>