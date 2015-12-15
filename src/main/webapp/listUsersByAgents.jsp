<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 11-Dec-15
  Time: 8:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="lv.javaguru.java2.domain.User" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
  <title></title>
</head>
<body>



<p>You are logged in as:</p><%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %>


<p>Attribute ID:<%=session.getAttribute("idToSee")%></p>
<p>seeDetails:<%=session.getAttribute("seeUserDetails")%></p>


<p> There are a list of your users:</p>
<table>
  <tr><td>User Id</td>
    <% if (session.getAttribute("seeUserDetails")!=null && session.getAttribute("seeUserDetails").equals("true")) { %>
    <td>User first name</td><td>User last name</td><td>User statuss</td><td>User email</td>
    <td>Properties of this user</td>
    <td>See details/See contact info</td><%}
    else if  (session.getAttribute("seeUserDetails")!=null && session.getAttribute("seeUserDetails").equals("false")){%>
    <td>See details/See contact info</td>
    <% } %>
  </tr>
  <%List<User> usersOfThisAgent= (List<User>)request.getAttribute("model");
    for(User user:usersOfThisAgent){ %>

  <tr><td><%= user.getUserId()%></td>


    <% if (session.getAttribute("seeUserDetails")!=null && session.getAttribute("seeUserDetails").equals("true")) { %>

    <% if (session.getAttribute("idToSee")!=null && session.getAttribute("idToSee").equals(user.getUserId())) { %>
    <td><%= user.getFirstName()%></td>
    <td><%= user.getLastName()%></td><td><%= user.getStatuss()%></td><td><%=user.getUserEmail()%></td>

    <td><a href="<%=request.getContextPath()%>/listByUser?seeUserDetails=false&ID=<%=user.getUserId()%>">Hide details!</a>/
      <a href="<%=request.getContextPath()%>/listByUser?seeInfo=false">Hide contact info!</a></td></tr>
  <% } else if (session.getAttribute("idToSee")==null ){%>
  <td>---</td>
  <td>---</td><td>---</td><td>---</td>
  <td>---</td>
  <td><a href="<%=request.getContextPath()%>/listByUser?seeUserDetails=false&ID=<%= user.getUserId()%>">Hide details!</a>/
    <a href="<%=request.getContextPath()%>/listByUser?seeInfo=false">Hide contact info!</a></td></tr>

  <% } %>

  <% } else if (session.getAttribute("seeUserDetails")!=null && session.getAttribute("seeUserDetails").equals("false")){%>
  <td><a href="<%=request.getContextPath()%>/listByUser?seeUserDetails=true&ID=<%= user.getUserId()%>">See details!</a>/
    <a href="<%=request.getContextPath()%>/listByUser?seeInfo=true">See contact info!</a></td></tr>
  <% } %>

  <%}%>

  <a href="<%=request.getContextPath()%>/returnToFirstPage">Return to main page</a>

</table>

</body>
</html>

