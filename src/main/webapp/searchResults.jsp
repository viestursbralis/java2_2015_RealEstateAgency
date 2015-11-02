<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 11/2/2015
  Time: 10:13 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>


<p>You are logged in as:<%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %></p>


<p>You agent is <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>
<label>There are a list of properties which match your search criteria:</label>
<table border="1">
  <tr><td>Category</td><td>Property description</td><td>Price</td><td>Adress</td><td>LivingArea</td><td>Bedrooms</td>
    <td>LandArea</td><td>Contact info</td></tr>

</table>

</body>
</html>

