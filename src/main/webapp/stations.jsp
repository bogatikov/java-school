<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.narnia.railways.model.Station" %>

<%--
  Created by IntelliJ IDEA.
  User: xiaomi
  Date: 06.03.2020
  Time: 22:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stations</title>
</head>
<body>
<table>
    <tr>
        <td>Name</td>
        <td>Longitude</td>
        <td>Latitude</td>
    </tr>

    <c:forEach items="${stations}" var="station">
        <tr>
            <td>${station.id}</td>
            <td>${station.name}</td>
            <td>${station.latitude}</td>
            <td>${station.longitude}</td>
            <td>
                <a href="/edit?id=${station.id}">Edit</a>
                &nbsp;&nbsp;&nbsp;
                <a href="/delete?id=${station.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>


</body>
</html>
