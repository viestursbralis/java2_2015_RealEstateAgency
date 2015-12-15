<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 22-Nov-15
  Time: 6:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="lv.javaguru.java2.domain.Photo" %>
<%@ page import="lv.javaguru.java2.domain.Property" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>JSP page</title>
</head>
<body>




<script type="text/javascript">

  <%--propertyDeletePopUp.onunload = refreshParent;--%>
  window.onunload=refreshParent;
  function refreshParent() {
    window.opener.location.reload(true);


  }
</script>


<script type="text/javascript">
  function closeSelf(){
    self.close();
    <%session.removeAttribute("propertyDetails");%>
    return true;
  }
</script>




<h1>RealEstateAgency!</h1>


<fieldset>
  <p>There are property details:</p>
  <table>
    <%Property property = (Property)session.getAttribute("prop");%>
    <%List<String>photoNames=(List<String>)session.getAttribute("photoNames");%>

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
    <tr><td rowspan="2">Contact information: </td><td>email:<%= property.getClient().getAgent().getAgentEmail()%>;
    </td><td></td></tr>
    <tr><td>Agent:<%= property.getClient().getAgent().getAgentFirstName()%>
      <%= property.getClient().getAgent().getAgentLastName()%> </td></tr>
    <%--<tr><td><a href="<%=request.getContextPath()%>/deleteProperty?deleteFromPopup=true&ID=<%=property.getPropertyId()%>" >Delete</a></td></tr>

    <%=request.getContextPath()%>/deleteProperty?deleteFromPopup=true&ID=<%=property.getPropertyId()%>--%>
  </table>

</fieldset>

<fieldset>
  <form action="<%=request.getContextPath()%>/deleteProperty" method="GET" onsubmit="return closeSelf()">
    <input type="hidden" name="deleteFromPopup" value = "true"/>
    <input type="hidden" name="ID" value="<%=property.getPropertyId()%>"/>
    <input type="hidden" name="seeDetails" value="false"/>

    <input type="submit" value="Delete!"/>
  </form>

</fieldset>


<%--<script type="text/javascript">
  propertyDeletePopUp.onunload = function () {

    window.opener.location.reload(true);
  }


</script>--%>




</body>
</html>

