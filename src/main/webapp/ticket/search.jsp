<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: xiaomi
  Date: 26.03.2020
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form:form action="/ticket/search" method="post" modelAttribute="search">
    <form:select path="from">
        <c:forEach items="${stations}" var="station">
            <form:option value="${station.id}">${station.name}</form:option>
        </c:forEach>
    </form:select>
    <form:select path="to">
        <c:forEach items="${stations}" var="station">
            <form:option value="${station.id}">${station.name}</form:option>
        </c:forEach>
    </form:select>
    <input type="submit"/>
</form:form>
</body>
</html>
