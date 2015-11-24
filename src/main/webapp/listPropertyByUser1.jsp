<%@ page import="lv.javaguru.java2.domain.Photo" %>
<%@ page import="lv.javaguru.java2.domain.Property" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 10/30/2015
  Time: 7:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
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

<% if (session.getAttribute("userName")!= null) { %>
<p>Username:<%=session.getAttribute("userName")%></p>
<% } else {%>
<p></p>
<% } %>



<% if (session.getAttribute("propertyDetails")!= null) { %>

<script type="text/javascript">
window.open('propertyDetailsPopUp.jsp',"_blank","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=600, height=280,top=200,left=200");

</script>
 <%} %>


<p>You are logged in as:</p><%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %>
<p>Your agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>



<p> There are a list of properties added by you:</p>
<table>
  <tr><td>Property Id</td><td>Property description</td>

    <td>Price</td><td>Adress</td><td>Living area</td><td>CountOfBedrooms</td>
    <td>Land area</td>
    <td>See details!</td>


  </tr>
  <%List<Property> propertiesOfThisUser= (List<Property>)request.getAttribute("model");
    for(Property prop:propertiesOfThisUser){ %>

  <tr><td><%= prop.getPropertyId()%></td>
    <td><%= prop.getPropertyDescription()%></td>
    <td><%= prop.getPrice()%></td>
    <td><%= prop.getAdress()%></td><td><%= prop.getArea()%></td><td><%=prop.getCountOfBedrooms()%></td>
    <td><%= prop.getLandArea()%></td>
    <td><a href="<%=request.getContextPath()%>/listByUser?seeDetails=true&ID=<%=prop.getPropertyId()%>">See details!</a>
      </td></tr>


  <%}%>



</table>
<a href="<%=request.getContextPath()%>/returnToFirstPage">Return to main page</a>

</body>
</html>
