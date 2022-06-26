<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
 <html>
    <body>
        <c:if test = "${sessionScope.user.userRole == 'ADMIN'}">
            <jsp:text>
                Welcome: ${sessionScope.user.login}
            </jsp:text>
            <br><br>


        <form action = "/app/periodicals/admin/managePeriodicals" method = "GET">
            <input type = "submit" value = 'Manage periodicals'>
        </form><br><br>

        <form action = "/app/periodicals/admin/manageReaders" method = "GET">
            <input type = "submit" value = 'Manage Readers'>
        </form><br><br>

        <form action = "/app/periodicals/logout" method = "POST">
            <input type = "submit" value = 'Logout'>
        </form><br><br>
        </c:if>

        <c:if test = "${sessionScope.user.userRole == 'READER'}">
        <jsp:text>
            Forbidden!
        </jsp:text>
        </c:if>
    </body>

 </html>