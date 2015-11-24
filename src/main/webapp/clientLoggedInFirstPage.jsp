<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 10/30/2015
  Time: 7:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>




<% if (session.getAttribute("postSuccessfull")!= null) { %>
<p><%=session.getAttribute("postSuccessfull")%></p>
<% } else {%>
<p></p>
<% } %>

<p>You are logged in as:<%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %></p>


<p>You agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>
<label>Choose from available options and press "Submit":</label>
<form action="<%=request.getContextPath()%>/clientsChoice" method="POST">
<div>
  <label><input  type="radio" name="clientsChoiceA"  value="1">Add a new RealEstate to the database</label><br>
  <label><input  type="radio" name="clientsChoiceA"  value="2">List all RealEstate's in the database</label><br>
  <label><input  type="radio" name="clientsChoiceA"  value="3">Search for specific RealEstate in the database</label><br>
  <label><input  type="radio" name="clientsChoiceA"  value="4">Delete a specific RealEstate from the database</label><br>
  <label><input  type="radio" name="clientsChoiceA"  value="5">Log out</label>
</div>
  <input type ="submit" value="Submit">
</form>

</body>
</html>
