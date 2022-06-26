<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Manage Account</title>
        <meta charset="UTF-8">
    </head>
    <body>
         <jsp:text>
            Welcome ${sessionScope.user.login}, here you can manage your account!
         </jsp:text>
         <br><br>

         <table id="manageAccount" vertical-align = "center">
                     <caption>Account balance: ${amountOfMoney} $</caption>
                         <tr>
                             <th>Top up account</th>
                             <th>Choose periodical to subscribe</th>
                         </tr>
                         <tr>
                             <td align="center">
                                <form action ="/app/periodicals/account/topUpAccountAmount" method = "POST">
                                             <input type = "hidden" name="readerId" value = "${sessionScope.user.id}"/>
                                             <input type="number" step = 0.01 name="amountOfMoney" required><br>
                                             <input type = "submit" value ='Top up'>
                                </form>
                             </td>
                             <td align="center">
                                <form action = "/app/periodicals/periodical/periodicalsForSubscribing" method = "GET">
                                             <input type = "hidden" name="readerId" value = "${sessionScope.user.id}"/>
                                             <input type = "submit" value ='Get subscription'>
                                </form>
                             </td>
                         </tr>
                 </table><br>
                <button onclick="location.href='/app/reader/readerHome.jsp'"> Back home </button>



                 <style>
                   caption {
                       font-family: annabelle;
                       font-weight: bold;
                       font-size: 2em;
                       padding: 10px;
                       border: 1px solid #A9E2CC;
                         }
                        th {
                          padding: 10px;
                          border: 1px solid #A9E2CC;
                        }
                        td {
                          font-size: 1.5em;
                          padding: 5px 10px;
                          border: 1px solid #A9E2CC;
                        }
                 </style>
    </body>
</html>