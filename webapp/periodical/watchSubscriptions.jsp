<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Your subscriptions page.</title>
        <meta charset="UTF-8">
    </head>
    <body>
        <form action ="/app/periodicals/periodical/readerSubscriptions" method = "GET">
            <label for="name">Click to update your subscriptions</label><br>
            <input type = "hidden" name="readerId" value = "${sessionScope.user.id}"/>
            <input type = "submit" value ='Update'>
        </form>

        <table id="subscriberPeriodicals">
        <caption>Periodicals you have subscribed to</caption>
            <tr>
                <th>Name</th>
                <th>Topic</th>
                <th>Cost</th>
                <th>Description</th>
            </tr>
            <c:forEach items="${periodicals}" var="periodical">
                <tr>
                    <td>${periodical.name}</td>
                    <td>${periodical.topic}</td>
                    <td>${periodical.cost}</td>
                    <td>${periodical.description}</td>
                </tr>
            </c:forEach>
        </table>

        <button onclick="location.href='/app/reader/readerHome.jsp'"> Back </button>

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