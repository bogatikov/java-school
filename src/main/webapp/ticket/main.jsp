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
    <style>
        table {
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid black;
        }
    </style>
</head>
<body>

<div align="center">


    <table>
        <tr>
            <th>Train</th>
            <th>From</th>
            <th>To</th>
        </tr>


        <c:forEach items="${trains}" var="trn">
            <%--@elvariable id="ticket" type="com.narnia.railways.service.dto.BuyTicketDTO"--%>
            <form:form action="${pageContext.request.contextPath}/ticket/buy" method="post" modelAttribute="ticket">
                <tr>
                    <td>
                        ${trn.id}
                        <form:select path="trainID">
                            <option value="${trn.id}" selected>${trn.id}</option>
                        </form:select>
                    </td>
                    <td>
                        From:
                        <form:select path="fromStationID">
                            <form:option value="${trn.track.get(0).id}">${trn.track.get(0).f_node.name}</form:option>
                            <c:forEach var="path" items="${trn.track}">
                                <form:option value="${path.s_node.id}">${path.s_node.name}</form:option>
                            </c:forEach>
                        </form:select></td>
                    <td>
                        To:
                        <form:select path="toStationID">
                            <form:option value="${trn.track.get(0).id}">${trn.track.get(0).f_node.name}</form:option>
                            <c:forEach var="path" items="${trn.track}">
                                <form:option value="${path.s_node.id}">${path.s_node.name}</form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td colspan="2"><input type="submit" value="Save"></td>
                </tr>
            </form:form>
        </c:forEach>
    </table>

</div>
</body>
</html>