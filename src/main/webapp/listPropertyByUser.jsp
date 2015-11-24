<%@ page import="lv.javaguru.java2.domain.Property" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
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
<% if (session.getAttribute("deleted")!= null) { %>
<p><%=session.getAttribute("deleted")%></p>
<% } else {%>
<p></p>
<% } %>


<p>You are logged in as:</p><%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %>
<p>Your agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>

<p>Attribute ID:<%=session.getAttribute("idToSee")%></p>
<p>seeDetails:<%=session.getAttribute("seeDetails")%></p>


<p> There are a list of properties added by you:</p>
<table>
  <tr><td>Property Id</td><td>Property description</td>
    <% if (session.getAttribute("seeDetails")!=null && session.getAttribute("seeDetails").equals("true")) { %>
    <td>Price</td><td>Adress</td><td>Living area</td><td>CountOfBedrooms</td>
    <td>Land area</td>
    <td>See details/See contact info</td><%}
     else if  (session.getAttribute("seeDetails")!=null && session.getAttribute("seeDetails").equals("false")){%>
    <td>See details/See contact info</td>
    <% } %>
  </tr>
  <%List<Property> propertiesOfThisUser= (List<Property>)request.getAttribute("model");
    for(Property prop:propertiesOfThisUser){ %>

  <tr><td><%= prop.getPropertyId()%></td>
    <td><%= prop.getPropertyDescription()%></td>

    <% if (session.getAttribute("seeDetails")!=null && session.getAttribute("seeDetails").equals("true")) { %>

    <% if (session.getAttribute("idToSee")!=null && session.getAttribute("idToSee").equals(prop.getPropertyId())) { %>
    <td><%= prop.getPrice()%></td>
    <td><%= prop.getAdress()%></td><td><%= prop.getArea()%></td><td><%=prop.getCountOfBedrooms()%></td>
    <td><%= prop.getLandArea()%></td>
    <td><a href="<%=request.getContextPath()%>/listByUser?seeDetails=false&ID=<%=prop.getPropertyId()%>">Hide details!</a>/
      <a href="<%=request.getContextPath()%>/listByUser?seeInfo=false">Hide contact info!</a></td></tr>
    <% } else if (session.getAttribute("idToSee")==null ){%>
    <td>---</td>
    <td>---</td><td>---</td><td>---</td>
    <td>---</td>
    <td><a href="<%=request.getContextPath()%>/listByUser?seeDetails=false&ID=<%= prop.getPropertyId()%>">Hide details!</a>/
      <a href="<%=request.getContextPath()%>/listByUser?seeInfo=false">Hide contact info!</a></td></tr>

    <% } %>

    <% } else if (session.getAttribute("seeDetails")!=null && session.getAttribute("seeDetails").equals("false")){%>
    <td><a href="<%=request.getContextPath()%>/listByUser?seeDetails=true&ID=<%= prop.getPropertyId()%>">See details!</a>/
      <a href="<%=request.getContextPath()%>/listByUser?seeInfo=true">See contact info!</a></td></tr>
    <% } %>

  <%}%>

  <a href="<%=request.getContextPath()%>/returnToFirstPage">Return to main page</a>

</table>

</body>
</html>
