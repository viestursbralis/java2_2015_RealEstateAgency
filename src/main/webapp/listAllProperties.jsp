<%@ page import="lv.javaguru.java2.domain.Property" %>
<%@ page import="java.util.List" %>
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


<p>You are logged in as: <%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %></p>


<p>You agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>

<% if (session.getAttribute("propertyDetails")!= null) { %>

<script type="text/javascript">
  window.open('propertyDetailsPopUp.jsp',"_blank","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=600, height=280,top=200,left=200");

</script>
<%} %>





<label>There are a list of all available properties:</label>
<table border="1">
  <tr><td>Property ID</td><td>Category</td><td>Property description</td><td>Price</td><td>Adress</td><td>LivingArea</td>
    <td>Bedrooms</td><td>LandArea</td><td>See details!</td></tr>

  <%List<Property> propertiesOfThisUser= (List<Property>)request.getAttribute("model");
    for(Property prop:propertiesOfThisUser){ %>
  <tr><td><%= prop.getPropertyId()%></td><td><%= prop.getCategory().getCategoryName()%></td><td><%= prop.getPropertyDescription()%></td>
    <td><%= prop.getPrice()%></td><td><%= prop.getAdress()%></td><td><%= prop.getArea()%></td>
    <td><%= prop.getCountOfBedrooms()%></td><td><%= prop.getLandArea()%></td>
    <td><a href="<%=request.getContextPath()%>/listAllProperties?seeDetails=true&ID=<%=prop.getPropertyId()%>">See details!</a>
    </td></tr>




  <%}%>

</table>
<a href="<%=request.getContextPath()%>/returnToFirstPage">Return to main page</a>
</body>
</html>

