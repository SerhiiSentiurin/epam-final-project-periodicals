<%@page contentType="text/html" pageEncoding="UTF-8"%>
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

    </body>
</html>