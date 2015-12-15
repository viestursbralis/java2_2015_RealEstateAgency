<%@ page import="lv.javaguru.java2.domain.Photo" %>
<%@ page import="lv.javaguru.java2.domain.Property" %>
<%@ page import="java.util.List" %>
<%@ page import="lv.javaguru.java2.domain.Utility" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>JSP page</title>
</head>
<body>

<h1>RealEstateAgency!</h1>
<%session.removeAttribute("propertyDetails");%>

<fieldset>
  <p>There are property details:</p>
  <table>
    <%Property property = (Property)session.getAttribute("prop");
List<String>photoNames=(List<String>)session.getAttribute("photoNames");
List<Utility>utilities = (List<Utility>)session.getAttribute("utils");

%>


    <tr><td>Property Id: </td><td><%= property.getPropertyId()%></td><td rowspan="9">
        <%for (String name:photoNames){%>
        <img src="PropertyPhotos/<%=name%>" width="160" height="160">
        <%}%>


    </td></tr>
     <tr><td>Property description: </td><td><%= property.getPropertyDescription()%></td><td></td></tr>

      <tr><td>Price: </td><td><%= property.getPrice()%></td><td></td></tr>
      <tr><td>Adress: </td><td><%= property.getAdress()%></td><td></td></tr>
    <tr><td>Living area: </td><td><%= property.getArea()%></td><td></td></tr>
    <tr><td>Count of bedrooms: </td><td><%=property.getCountOfBedrooms()%></td><td></td></tr>
      <tr><td>Land area: </td><td><%= property.getLandArea()%></td><td></td></tr>
     <tr><td>List of available utilities:</td><td><% for(Utility util:utilities){%><li><%=util.getUtilityDescription()%> </li><%}%></td></tr>
      <tr><td rowspan="2">Agent Contact information: </td><td>email:<%= property.getClient().getAgent().getAgentEmail()%> </td><td></td></tr>
      <tr><td>name, surname:<%= property.getClient().getAgent().getAgentFirstName()%> <%= property.getClient().getAgent().getAgentLastName()%> </td></tr>

  </table>
</fieldset>

<fieldset>


</fieldset>







</body>
</html>
