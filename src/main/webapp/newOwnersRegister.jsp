<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 11/1/2015
  Time: 10:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page trimDirectiveWhitespaces="true"%>

<html>
<head>
    <title></title>
</head>
<body>
<p>You are logged in as:<%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %></p><br>
<p>You agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>

<p>Data from previous page:<%=session.getAttribute("category")%></p>
<fieldset>
  <p>New property owner (contactperson) register:</p>
  <form action="<%=request.getContextPath()%>/newPost" method="POST">
    <p>Enter your data</p>
    <label for="firstName">First name:</label>
    <input type="text" id="firstName" name="firstName"  required/><br>
    <label for="lastName">Last name:</label>
    <input type="text" id="lastName" name="lastName" required/><br>
    <label for="email">Contact email:</label>
    <input type="email" id="email" name="email" required/><br>
    <label for="phone">Phone number:</label>
    <input type="text" id="phone" name="phone" required/><br>

    <br/><br/>

    <center>
      <input type ="submit" name="addOwner" value="Previous page">
      <input type ="submit" name="addOwner" value="Next page">
    </center>

  </form>
</fieldset>
<a href="<%=request.getContextPath()%>/returnToFirstPage">Return to main page</a>
</body>
</html>
