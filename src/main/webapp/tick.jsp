<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.narnia.railways.model.Train" %>
<%@ page import="com.narnia.railways.model.TrainState" %>
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
    <form:form action="/tick" method="GET">
        <table border="0" cellpadding="5">
            <tr>
                <td colspan="2"><input type="submit" value="Tick"></td>
            </tr>
        </table>
    </form:form>

    <table>
        <tr>
            <td>Train</td>
            <td>From Station</td>
            <td>To Station</td>
            <td>Status</td>
            <td>Description</td>
        </tr>
        <c:forEach items="${trains}" var="train">
            <tr>
                <td width="10%">${train.id}</td>
                <td width="10%">${train.fromStation.name}</td>
                <td width="10%">${train.toStation.name}</td>
                <td width="15%">${train.trainState.toString()}</td>
                <td width="55%">
                    <c:choose>
                        <c:when test="${train.trainState.equals(TrainState.MOVEMENT)}">
                            The train <b>${train.id}</b> will arrive at <b>${train.toStation.name}</b> throw
                            <b>${train.nextPath.length - train.moveCounter}</b> ticks
                        </c:when>
                        <c:when test="${train.trainState.equals(TrainState.ARRIVAL)}">
                            The train <b>${train.id}</b> arriving at the station <b>${train.toStation.name}</b>
                        </c:when>
                        <c:when test="${train.trainState.equals(TrainState.DEPARTURE)}">
                            The train <b>${train.id}</b> departing from <b>${train.fromStation.name}</b>
                        </c:when>
                        <c:when test="${train.trainState.equals(TrainState.STOP)}">
                            <c:choose>
                                <c:when test="${train.fromStation.val - train.moveCounter > 0}">
                                    The train <b>${train.id}</b> waiting for passengers on station <b>${train.fromStation.name}</b>
                                </c:when>
                                <c:when test="${train.fromStation.val - train.moveCounter > 0}">
                                    The train <b>${train.id}</b> waiting for departing possibility to <b>${train.toStation.name}</b>
                                </c:when>
                            </c:choose>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>


    </table>


</div>
</body>
</html>