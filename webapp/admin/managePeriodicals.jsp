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

        <table id="createPeriodical">
            <tr>
                <th>Write name</th>
                <th>Write topic</th>
                <th>Write cost</th>
                <th>Write description</th>
                <th>Create new Periodical</th>
            </tr>
            <tr>
                <td><textarea form = "create" name = "name" rows = "3" cols="25" wrap="soft" required></textarea></td>
                <td><textarea form = "create" name = "topic" rows = "3" cols="25" wrap="soft" required></textarea></td>
                <td><input form = "create" type= "number" step = 0.01 name="cost" required></td>
                <td><textarea form = "create" name = "description" rows = "3" cols="125" wrap="soft" required> </textarea></td>
                <td  align="center">
                    <form action = "/app/periodicals/admin/createNewPeriodical" method = "POST" id = "create">
                        <input type = "submit" value = 'Create'>
                    </form>
                </td>
            </tr>
        </table>

        <table id="listOfPeriodicals">
            <caption>Periodicals</caption>
                <tr>
                    <th>Name</th>
                    <th>Topic</th>
                    <th>Cost</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Delete Periodical for users</th>
                    <th>Restore Periodical for users</th>
                    <th>Completely Delete periodical</th>
                    <th>Edit periodical</th>
                </tr>
            <c:forEach items="${periodicals}" var="periodical">
                <tr>
                    <td>${periodical.name}</td>
                    <td>${periodical.topic}</td>
                    <td>${periodical.cost}</td>
                    <td>${periodical.description}</td>
                        <c:if test="${periodical.isDeleted == 'true'}" >
                            <td>Blocked</td>
                        </c:if>
                        <c:if test="${periodical.isDeleted == 'false'}" >
                            <td>Available</td>
                        </c:if>
                    <td align="center">
                        <form action = "/app/periodicals/admin/deletePeriodicalForReaders" method = "POST">
                            <input type = "hidden" name = "periodicalId" value = "${periodical.id}">
                            <input type = "submit" value = 'Delete'>
                        </form>
                    </td>
                    <td align="center">
                        <form action = "/app/periodicals/admin/restorePeriodicalForReaders" method = "POST">
                            <input type = "hidden" name = "periodicalId" value = "${periodical.id}">
                            <input type = "submit" value = 'Restore'>
                        </form>
                    </td>
                    <td align="center">
                        <form action = "/app/periodicals/admin/deletePeriodical" method = "POST" onsubmit = "return confirm('Are you sure you want to delete periodical ${periodical.name}?');">
                            <input type = "hidden" name = "periodicalId" value = "${periodical.id}">
                            <input type = "submit" value = 'Warning! Absolut Delete'>
                        </form>
                    </td>
                    <td align="center">
                        <form action = "/app/periodicals/admin/getPeriodicalForEdit" method = "GET">
                            <input type = "hidden" name = "periodicalId" value = "${periodical.id}">
                            <input type = "submit" value = 'Edit'>
                        </form>
                    </td>
                </tr>
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