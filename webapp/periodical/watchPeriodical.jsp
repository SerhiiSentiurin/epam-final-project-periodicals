<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
 <html>
     <head>
         <title>Watch all periodicals</title>
         <meta charset="UTF-8">
     </head>
     <body>
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

         <form action = "/app/periodicals/periodical/watchByTopic" method = "GET">
          <label for="topic">Find periodical by topic:</label>
             <select name="topic">
                <option value="business">business</option>
                <option value="sport" selected>sport</option>
                <option value="technology">technology</option>
             </select>
         <button type ="submit">Search</button>
         </form>

 <%--добавить сообщ об ошибке "Periodical with this name didn't exist", если название из нескольких слов то не ищет по одному--%>
         <form action = "/app/periodicals/periodical/findByName" method ="GET">
            <label for="name">Find periodical by name:</label>
            <input type="text" name="name" required >
            <input type="submit" value='Search'>
         </form>
<br>
         <form action = "/app/periodicals/periodical/sortByCost" method ="GET">
            <label for="name">Sort periodicals by cost:</label><br>
            <input type = "hidden" name="topic" value = "${topic}"/>
            <input type = "hidden" name="name" value = "${name}"/>
            <input type = "submit" value ='Sort'>
            <input type = "submit" formaction="/app/periodicals/periodical/reversedSortByCost" value='Reverse Sort'>
         </form>

         <form action ="/app/periodicals/periodical/sortByName" method = "GET">
            <label for="name">Sort periodicals by name:</label><br>
            <input type = "hidden" name="topic" value = "${topic}"/>
            <input type = "hidden" name="name" value = "${name}"/>
            <input type = "submit" value ='Sort'>
            <input type = "submit" formaction="/app/periodicals/periodical/reversedSortByName" value='Reverse Sort'>
         </form>


         <form accept-charset="UTF-8" method="GET" action="/app/periodicals/periodical/watch">
            <label for="name">Click to see all periodicals:</label>
            <input type = "submit"  value='All periodicals'/><br><br>
         </form>

        <button onclick="location.href='/app'">Back to registration</button>

        <table id="listOfPeriodicals">
        <caption>Periodicals</caption>
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
    </body>
</html>