<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index page.</title>
        <meta charset="UTF-8">
    </head>
    <h1>Your registration successful, now you may login!</h1>
    <body>
        <form accept-charset="UTF-8" method="POST" action="/app/periodicals/login">
            <label for="name">Login:</label><br>
            <input type="text" name="login"><br><br>
            <label for="pass">Password:</label><br>
            <input type="password" name="password"><br><br>
            <input type = "submit" style="width: 8%" value='Sign in'>
            <br><br>
        </form>
    </body>
</html>