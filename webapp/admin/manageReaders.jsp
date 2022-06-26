<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <body>
        <c:if test = "${sessionScope.user.userRole == 'ADMIN'}">
        <jsp:text>
            Welcome: ${sessionScope.user.login}
        </jsp:text><br><br>
        <button onclick="location.href='/app/admin/adminHome.jsp'"> Back home </button>
        <br><br>

        <table id="listOfReaders">
            <caption>Readers</caption>
                <tr>
                    <th>Reader`s login</th>
                    <th>Account balance</th>
                    <th>Reader`s status</th>
                    <th>Lock reader</th>
                    <th>Unlock reader</th>
                </tr>
                <c:forEach items="${readers}" var="reader">
                <tr>
                    <td>${reader.login}</td>
                    <td>${reader.account.amountOfMoney}</td>
                        <c:if test="${reader.lock == 'true'}" >
                            <td>Blocked</td>
                        </c:if>
                        <c:if test="${reader.lock == 'false'}" >
                            <td>Not blocked</td>
                        </c:if>
                    <td align="center">
                        <form action = "/app/periodicals/admin/lockReader" method = "POST">
                            <input type = "hidden" name = "readerId" value = "${reader.id}">
                            <input type = "submit" value = 'Lock'>
                        </form>
                    </td>
                    <td align="center">
                        <form action = "/app/periodicals/admin/unlockReader" method = "POST">
                            <input type = "hidden" name = "readerId" value = "${reader.id}">
                            <input type = "submit" value = 'Unlock'>
                        </form>
                </c:forEach>
        </table>

        <style>
            caption {
                font-family: annabelle;
                font-weight: bold;
                font-size: 1.5em;
                padding: 10px;
                border: 1px solid #A9E2CC;
            }
            th {
                padding: 10px;
                border: 1px solid #A9E2CC;
            }
            td {
                font-size: 1.0em;
                padding: 5px 7px;
                border: 1px solid #A9E2CC;
            }
        </style>

        </c:if>
        <c:if test = "${sessionScope.user.userRole == 'READER'}">
            <jsp:text>
                Forbidden!
            </jsp:text>
        </c:if>
    </body>
</html>