<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: xiaomi
  Date: 07.03.2020
  Time: 1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>New Customer</title>
</head>
<body>
<div align="center">
    <h2>New Customer</h2>
    <%--@elvariable id="station" type="com.narnia.railways.model.Station"--%>
    <form:form action="/tick" method="GET">
        <table border="0" cellpadding="5">
            <tr>
                <td colspan="2"><input type="submit" value="Tick"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>