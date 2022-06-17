<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome page.</title>
        <meta charset="UTF-8">
    </head>
    <body>
         <jsp:text>
            Welcome ${sessionScope.user.login}, this is periodicals that you may subscribe!
         </jsp:text>
         <br><br>

        <table id="listOfPeriodicals">
            <caption>Periodicals you are not subscribed yet</caption>
                <tr>
                    <th>Name</th>
                    <th>Topic</th>
                    <th>Cost</th>
                    <th>Description</th>
                    <th>Choose subscribe period!</th>
                </tr>
            <c:forEach items="${periodicals}" var="periodical">
                <tr>
                    <td>${periodical.name}</td>
                    <td>${periodical.topic}</td>
                    <td>${periodical.cost}</td>
                    <td>${periodical.description}</td>
                    <td align="center">
                        <form action ="/app/periodicals/prepayment/addSubscription" method = "POST">
                            <input type = "hidden" name="readerId" value = "${sessionScope.user.id}"/>
                            <input type = "hidden" name = "periodicalId" value = "${periodical.id}">
                                <select name="durationOfSubscription">
                                    <option value="30">Subscription for 30 days!</option>
                                    <option value="365" selected>Subscription for 365 days!(with 10% discount)</option>
                                </select>
                            <input type = "submit" value ='Subscribe'>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <button onclick="location.href='/app/reader/readerHome.jsp'"> Back home </button>

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
    </body>
</html>