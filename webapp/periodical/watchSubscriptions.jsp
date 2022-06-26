<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Your subscriptions page.</title>
        <meta charset="UTF-8">
    </head>
    <body>
    <c:if test="${periodicals.size() == 0}" >
            <p>You have not any periodicals</p>
    </c:if>

    <c:if test="${periodicals.size() > 0}" >

    <form action = "/app/periodicals/periodical/getByTopicReaderSubscriptions" method = "GET">
            <label for="topic">Find periodical by topic:</label>
            <input type = "hidden" name="readerId" value = "${sessionScope.user.id}"/>
                <select name="topic">
                    <option value="business">business</option>
                    <option value="sport" selected>sport</option>
                    <option value="technology">technology</option>
                 </select>
            <button type ="submit">Search</button>
    </form>
    <br>
    <form action = "/app/periodicals/periodical/findByNameReaderSubscriptions" method ="GET">
            <label for="name">Find periodical by name:</label>
            <input type = "hidden" name="readerId" value = "${sessionScope.user.id}"/>
            <input type="text" name="name" required>
           <button type ="submit">Search</button>
    </form>
    <br><br>

        <button onclick="sortName(false)">Sort by name</button>
        <button onclick="sortName(true)">reversed sort by name</button>
        <br><br>
        <button onclick="sortPrice(false)">sort by cost</button>
        <button onclick="sortPrice(true)">reversed sort by cost</button>
        <br><br>

        <form action ="/app/periodicals/periodical/readerSubscriptions" method = "GET">
            <label for="name">Click to reset sorting</label><br>
            <input type = "hidden" name="readerId" value = "${sessionScope.user.id}"/>
            <input type = "submit" value ='Reset'>
        </form>

        <table id="subscriberPeriodicals">
        <caption>Periodicals you have subscribed to</caption>
            <tr>
                <th>Name</th>
                <th>Topic</th>
                <th>Cost</th>
                <th>Description</th>
                <th>Start</th>
                <th>End</th>
            </tr>

                <c:forEach var="i" begin="0" end="${periodicals.size()-1}">

                <tr>
                        <td>${periodicals.get(i).name}</td>
                        <td>${periodicals.get(i).topic}</td>
                        <td>${periodicals.get(i).cost}</td>
                        <td>${periodicals.get(i).description}</td>
                        <td>${prepayments.get(i).startDate}</td>
                        <td>${prepayments.get(i).dueDate}</td>

                </tr>
            </c:forEach>

        </table>
         </c:if>
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


    <script>
    function sortName(inverse) {
      var table, rows, switching, i, x, y, shouldSwitch;
      table = document.getElementById("subscriberPeriodicals");
      switching = true;
      /*Make a loop that will continue until
      no switching has been done:*/
      while (switching) {
        //start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /*Loop through all table rows (except the
        first, which contains table headers):*/
        for (i = 1; i < (rows.length - 1); i++) {
          //start by saying there should be no switching:
          shouldSwitch = false;
          /*Get the two elements you want to compare,
          one from current row and one from the next:*/
          x = rows[i].getElementsByTagName('TD')[0];
          y = rows[i + 1].getElementsByTagName('TD')[0];
          //check if the two rows should switch place:
          if ((x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) ^ inverse) {
            //if so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
          }
        }
        if (shouldSwitch) {
          /*If a switch has been marked, make the switch
          and mark that a switch has been done:*/
          rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
          switching = true;
        }
      }
    }
    function sortPrice(inverse) {
      var table, rows, switching, i, x, y, shouldSwitch;
      table = document.getElementById("subscriberPeriodicals");
      switching = true;
      /*Make a loop that will continue until
      no switching has been done:*/
      while (switching) {
        //start by saying: no switching is done:
        switching = false;
        rows = table.rows;
        /*Loop through all table rows (except the
        first, which contains table headers):*/
        for (i = 1; i < (rows.length - 1); i++) {
          //start by saying there should be no switching:
          shouldSwitch = false;
          /*Get the two elements you want to compare,
          one from current row and one from the next:*/
          x = rows[i].getElementsByTagName('TD')[2];
          y = rows[i + 1].getElementsByTagName('TD')[2];
          //check if the two rows should switch place:
          if ((parseInt(x.innerHTML, 10) > parseInt(y.innerHTML, 10)) ^ inverse) {
            //if so, mark as a switch and break the loop:
            shouldSwitch = true;
            break;
          }
        }
        if (shouldSwitch) {
          /*If a switch has been marked, make the switch
          and mark that a switch has been done:*/
          rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
          switching = true;
        }
      }
    }
            </script>
    </html>