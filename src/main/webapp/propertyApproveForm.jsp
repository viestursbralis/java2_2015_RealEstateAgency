

<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 23-Jan-16
  Time: 4:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String photos = request.getContextPath()+"/PropertyPhotos";%>
<!--<html>
<head>
    <title></title>
</head>
<body>-->




<form:form method="POST" commandName="propertyApprovalModel" action="submitEdited">
  <table>
    <p>New post approval form: </p>
    <tbody><tr>
      <td><form:label path="propertyId">Property ID:</form:label></td>
      <td><form:input path="propertyId"></form:input></td>
    </tr>
    <tr>
      <td><form:label path="postStatuss">Post statuss:</form:label></td>
      <td>
        <select name="postStatuss">
            <c:if test="${(propertyApprovalModel.postStatuss.getStatuss())=='WAITING'}">
                <option value="WAITING" selected = "selected"
                        <c:out value="WAITING" >WAITING</c:out>
                >WAITING</option>
                <option value="APPROVED"
                        <c:out value="APPROVED">APPROVED</c:out>
                >APPROVED</option>

            </c:if>

            <c:if test="${(propertyApprovalModel.postStatuss.getStatuss())=='APPROVED'}">

                <option value="WAITING"
                        <c:out value="WAITING" >WAITING</c:out>
                >WAITING</option>
                <option value="APPROVED" selected = "selected"
                        <c:out value="APPROVED">APPROVED</c:out>

                >APPROWED</option>
            </c:if>

        </select>
    </td>
    </tr>
    <tr>
      <td><form:label path="propertyDescription">Property description:</form:label></td>
      <td><form:input path="propertyDescription"></form:input></td>
    </tr>
    <tr>
      <td><form:label path="price">Price:</form:label></td>
      <td><form:input path="price"></form:input></td>
    </tr>
    <tr>
        <td><form:label path="area">Area:</form:label></td>
        <td><form:input path="area"></form:input></td>
    </tr>
    <tr>
        <td><form:label path="landArea">Land area:</form:label></td>
        <td><form:input path="landArea"></form:input></td>
    </tr>
    <tr>
        <td><form:label path="countOfBedrooms">Count of bedrooms:</form:label></td>
        <td><form:input path="countOfBedrooms"></form:input></td>
    </tr>


    <c:forEach  var = "owners" items="${propertyApprovalModel.propertyOwners}" varStatus="po">
      <tr><td rowspan=2>Owner nr. ${po.index}</td></tr>
      <tr>
        <td><form:label path="propertyOwners[${po.index}].id">Owner's ID in database:</form:label></td>
        <td><form:input path="propertyOwners[${po.index}].id"/></td>

      </tr>
      <tr>
        <td><form:label path="propertyOwners[${po.index}].firstName">Owner's first name:</form:label></td>
        <td><form:input path="propertyOwners[${po.index}].firstName"/></td>
      </tr>
      <tr>
        <td><form:label path="propertyOwners[${po.index}].lastName">Owner's last name:</form:label></td>
        <td><form:input path="propertyOwners[${po.index}].lastName"/></td>
      </tr>
      <tr>
        <td><form:label path="propertyOwners[${po.index}].ownerEmail">Owner's email:</form:label></td>
        <td><form:input path="propertyOwners[${po.index}].ownerEmail"/></td>
      </tr>
      <tr>
        <td><form:label path="propertyOwners[${po.index}].ownerPhone">Owner's phone:</form:label></td>
        <td><form:input path="propertyOwners[${po.index}].ownerPhone"/></td>
      </tr>


    </c:forEach>

   <c:forEach items="${propertyApprovalModel.propertyUtilities}" var="utils"  varStatus = "ut">
      <tr>
        <td><form:checkbox path="propertyUtilities[${ut.index}].checked"
                          name="propertyUtilities[${ut.index}].checked"
                           value="propertyUtilities[${ut.index}].checked"

        /></td>

        <td><form:label path="propertyUtilities[${ut.index}].utilityDescription"
                        >

          <c:out value="${utils.getUtilityDescription()}" />
        </form:label></td>
        <td> <form:hidden path="propertyUtilities[${ut.index}].utilityDescription"
        /></td>
        <td> <form:hidden path="propertyUtilities[${ut.index}].utilityId"
                          /></td>
      </tr>
    </c:forEach>


    <c:forEach items="${propertyApprovalModel.propertyPhotos}" var="photos"  varStatus = "ph">

      <tr>
        <td><form:checkbox path="propertyPhotos[${ph.index}].checked"
                           name="propertyPhotos[${ph.index}].checked"
                           value="propertyPhotos[${ph.index}].checked"

        /></td>

        <td><img src=PropertyPhotos/${photos.getPhotoName()} width='160' height='160'/> </td>
        <td>

          <c:out value="${photos.getPhotoName()}" />
        </td>
        <td> <form:hidden path="propertyPhotos[${ph.index}].photoName"
        /></td>
        <td> <form:hidden path="propertyPhotos[${ph.index}].photoId"
        /></td>



      </tr>


    </c:forEach>


    <tr>



    </tr>






    <%--<form:checkboxes items="${propertyApprovalModel.propertyUtilities}"
                     path="propertyUtilities"
                      itemLabel="utilityDescription"
                      itemValue="checked"
    />--%>

    <tr>


      <td colspan="2">
        <input value="Submit" type="submit">
      </td>

      <td><input value="Delete" name="delete"  id="delete" type="submit"></td>
    </tr>
    </tbody></table>
</form:form>

<!--</body>
</html>-->
