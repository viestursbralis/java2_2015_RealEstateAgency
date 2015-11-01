<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 11/1/2015
  Time: 10:43 AM
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
<fieldset>
  <p>New property utiliies register:</p>
  <form action="newPostRegisterController" method="POST">
    <p>Check which utility applies:</p>

    <input id="internet" name="internet" value="1" type="checkbox">
    <label for="internet">Broadband internet cable</label>
    <input id="city_gas" name="city_gas" value="1" type="checkbox">
    <label for="city_gas">City gas pipe</label>
    <input id="city_heat" name="city_heat" value="1" type="checkbox">
    <label for="city_heat">City heat</label>
    <input id="city_water" name="city_water" value="1" type="checkbox">
    <label for="city_water">City water</label>
    <input id="city_sewer" name="city_sewer" value="1" type="checkbox">
    <label for="city_sewer">City sewer</label>

      <input type ="submit" name="addUtility" value="Register!">

  </form>
</fieldset>

</body>
</html>
