<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome page.</title>
        <meta charset="UTF-8">
    </head>
    <body>
         <jsp:text>
            Welcome ${sessionScope.user.login}, this is your home page!
         </jsp:text>
         <br><br>

кнопки сортировки периодиклов на которые подписан
<br>
состояние счёта + кнопка пополнить
<br>
кнопка посмотреть весь список чтоб подписаться
<br><br>



        <form action ="/app/periodicals/reader/readerSubscriptions" method = "GET">
            <label for="name">Click to update your subscriptions</label><br>
            <input type = "hidden" name="id" value = "${sessionScope.user.id}"/>
            <input type = "submit" value ='Update'>
        </form>

        <table id="listOfPeriodicals">
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

        <form action = "/app/periodicals/periodical/periodicalsForSubscribing" method = "GET">
        <label for="name">Click to subscribe on other periodicals</label><br>
                    <input type = "hidden" name="id" value = "${sessionScope.user.id}"/>
                    <input type = "submit" value ='Choose periodical'>
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
    </body>
</html>