<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <body>
        <c:if test = "${sessionScope.user.userRole == 'ADMIN'}">
        <table id="editPeriodical">
            <caption>Edit "${periodical.name}"</caption>
                <tr>
                    <th>Name</th>
                    <th>Topic</th>
                    <th>Cost</th>
                    <th>Description</th>
                    <th>Edit</th>
                </tr>
                <tr>
                    <td><textarea name = "name" form = "edit" rows = "12" cols="25" wrap="soft" required >${periodical.name}</textarea></td>
                    <td><textarea name="topic" form = "edit" rows="12" cols="25" wrap="soft" required >${periodical.topic}</textarea></td>
                    <td><input form = "edit" type = "number" name = "cost" value = "${periodical.cost}" required></td>
                    <td><textarea name="description" form = "edit" rows="12" cols="150" wrap="soft" required >${periodical.description}</textarea></td>
                    <td>
                        <form action = "/app/periodicals/admin/editPeriodical" method = "POST" id = "edit">
                            <input type = "hidden" name = "periodicalId" value = "${periodical.id}">
                            <input type = "submit" value = 'Edit Periodical'>
                        </form>
                    </td>
                </tr>
        </table>

        <form action = "/app/periodicals/admin/managePeriodicals" method = "GET">
            <input type = "submit" value = 'Back'>
        </form>
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