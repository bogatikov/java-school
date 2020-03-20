<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.narnia.railways.model.Train" %>
<%@ page import="com.narnia.railways.model.Station" %>
<%@ page import="com.narnia.railways.service.dto.TrainScheduleDTO" %>
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
    <c:forEach items="${schedules.keySet()}" var="station">
        Table for station <b>${station.name}</b>
        <table>
            <tr>
                <td>Train</td>
                <td>Will arrive through</td>
            </tr>
            <c:forEach items="${schedules.get(station)}" var="schedule">
                <tr>
                    <td>${schedule.getTrain().getId()}</td>
                    <td>${schedule.getArriveThrough()}</td>
                </tr>
            </c:forEach>
        </table>
    </c:forEach>
</div>
</body>
</html>