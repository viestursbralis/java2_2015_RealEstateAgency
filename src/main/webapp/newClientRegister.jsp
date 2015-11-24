<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 10/31/2015
  Time: 12:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page trimDirectiveWhitespaces="true"%>

<html>
<head>
    <title></title>
</head>
<body>
<fieldset>
  <p>New client registration form.</p>
  <form action="<%=request.getContextPath()%>/newClientRegister" method="POST">
    <p>Enter your data</p>
    <label for="firstName">First name:</label>
    <input type="text" id="firstName" name="firstName"  required/><br>
    <label for="lastName">Last name:</label>
    <input type="text" id="lastName" name="lastName" required/><br>
    <label for="email">Contact email:</label>
    <input type="email" id="email" name="email" required/><br>
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required/><br>

    <br/><br/>

    <center>
      <input type ="submit" value="Register!">
    </center>

  </form>
</fieldset>

<a href="<%=request.getContextPath()%>/index">Return to main page</a>






</body>
</html>
