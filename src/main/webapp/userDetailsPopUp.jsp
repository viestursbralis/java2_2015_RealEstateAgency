<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 11-Dec-15
  Time: 11:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="lv.javaguru.java2.domain.Property" %>
<%@ page import="lv.javaguru.java2.domain.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>JSP page</title>
</head>
<body>
<% if (session.getAttribute("propertyDetails")!= null) { %>

<script type="text/javascript">
  window.open('propertyDetailsPopUp.jsp',"_blank","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=600, height=280,top=200,left=200");

</script>
<%} %>




<h1>RealEstateAgency!</h1>
<%session.removeAttribute("propertyDetails");%>
<p>You are logged in as <%=session.getAttribute("agentFirstName")%> <%=session.getAttribute("agentLastName")%>, <%=session.getAttribute("status")%></p>
<fieldset>
  <p>There are list of adds posted by client <%=session.getAttribute("userFirstName")%> <%=session.getAttribute("userLastName")%>:</p>
  <table>
    <%List<Property>propertiesOfThisUser = (List<Property>)session.getAttribute("propertiesOfThisUser");%>


    <tr><td>Property category </td><td>Property Id    </td>
    <td>Property description: </td><td>Price: </td><td>See more details!</td></tr>
    <%for(Property prop:propertiesOfThisUser){ %>
      <tr><td><%= prop.getCategory().getCategoryName()%></td>

    <td><%= prop.getPropertyId()%></td>
    <td><%= prop.getPropertyDescription()%></td><td><%= prop.getPrice()%></td>


        <td><a href="<%=request.getContextPath()%>/listByUserFromAgent?seeDetails=true&ID=<%=prop.getPropertyId()%>">See details!</a></td></tr>

<%}%>
  </table>
</fieldset>

<fieldset>


</fieldset>







</body>
</html>

