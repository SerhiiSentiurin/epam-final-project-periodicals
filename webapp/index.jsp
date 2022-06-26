<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Index page.</title>
        <meta charset="UTF-8">
    </head>
    <h1>Welcome to Periodicals application </h1>
    <body>
        <form accept-charset="UTF-8" method="POST" action="/app/periodicals/login">
            <label for="name">Login:</label><br>
            <input type="text" name="login" required><br><br>
            <label for="pass">Password:</label><br>
            <input type="password" name="password" required><br><br>
            <input type = "submit" style="width: 8%" value='Sign in'>
            <br><br>
            <input type = "submit" style="width: 8%" formaction="/app/periodicals/reader/create" value='Sign up'>
        </form>
        <br><br>
    <h3>If you are not registered, you can just watch periodicals via the link below.</h3>
            <form accept-charset="UTF-8" method="GET" action="/app/periodicals/periodical/watch">
            <input type = "submit" style="width: 10%" value='Watch periodicals'/>
            </form>
    </body>
</html>