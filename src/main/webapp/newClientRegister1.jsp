<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 22-Jan-16
  Time: 6:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>



<html>
<head>
    <title></title>
</head>
<body>
<form:form method="POST" commandName="userRegisterModel" action="newClientRegister">
  <table>
    <p>New client registration form. </p>
    <tbody><tr>
      <td><form:label path="firstName">First name:</form:label></td>
      <td><form:input path="firstName"></form:input></td>
    </tr>
    <tr>
      <td><form:label path="lastName">Last name:</form:label></td>
      <td><form:input path="lastName"></form:input></td>
    </tr>
    <tr>
        <td><form:label path="userEmail">Email:</form:label></td>
      <td><form:input path="userEmail"></form:input></td>
    </tr>
    <tr>
        <td><form:label path="password">Password:</form:label></td>
      <td><form:input path="password"></form:input></td>
    </tr>
    <tr>
        <%--<td><form:label path="age">Age:</form:label></td>--%>
      <td><form:hidden path="statuss" value="CLIENT" ></form:hidden></td>
    </tr>

    <tr>
      <td colspan="2">
        <input value="Submit" type="submit">
      </td>
      <td></td>
      <td></td>
    </tr>
    </tbody></table>
</form:form>
</body>
</html>
