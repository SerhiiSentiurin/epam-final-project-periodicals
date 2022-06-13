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


<br>
состояние счёта + кнопка пополнить
<br>
кнопка посмотреть весь список чтоб подписаться
<br><br>

        <form action ="/app/periodicals/periodical/readerSubscriptions" method = "GET">
                    <label for="name">Click to watch your subscriptions</label><br>
                    <input type = "hidden" name="readerId" value = "${sessionScope.user.id}"/>
                    <input type = "submit" value ='Watch'>
        </form>
        <br><br>
        <form action = "/app/periodicals/periodical/periodicalsForSubscribing" method = "GET">
        <label for="name">Click to subscribe on other periodicals</label><br>
                    <input type = "hidden" name="readerId" value = "${sessionScope.user.id}"/>
                    <input type = "submit" value ='Choose periodical'>
        </form>
        <form action ="/app/periodicals/updateAccount" method = "POST">
                    <label for="name">Update Account</label><br>
                    <input type = "hidden" name="readerId" value = "${sessionScope.user.id}"/>
                     <input type="number" name="amountOfMoney" required><br><br>
                    <input type = "submit" value ='update'>
        </form>


    </body>
</html>