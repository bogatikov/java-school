<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: xiaomi
  Date: 07.03.2020
  Time: 1:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Edit Train</title>
</head>
<body>
<div align="center">
    <h2>Edit Train</h2>
    <form:form action="save" method="post" modelAttribute="station">
        <table>
            <tr>
                <td>ID: </td>
                <td>${station.id}
                    <form:hidden path="id"/>
                </td>
            </tr>
            <tr>
                <td>Name: </td>
                <td><form:input path="name" /></td>
            </tr>
            <tr>
                <td>Longitude: </td>
                <td><form:input path="longitude" /></td>
            </tr>
            <tr>
                <td>Latitude: </td>
                <td><form:input path="latitude" /></td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Save"></td>
            </tr>
        </table>
    </form:form>
</div>
</body>
</html>