<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 10/30/2015
  Time: 7:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page trimDirectiveWhitespaces="true"%>
<html>
<head>
  <title></title>
</head>
<body>
<p>You are logged in as:</p><%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %>
<p>You agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>
<label>To post a new advertisement please fill out a form:</label>
<form action="<%=request.getContextPath()%>/newPost" method="GET">

  Select in which category do you want to insert Your add:<br/>
  <select name="postType" required>
    <option value="CONDO_FOR_RENT"> CONDO_FOR_RENT </option>
    <option value="CONDO_FOR_SALE"> CONDO_FOR_SALE </option>
    <option value="CONDO_TO_RENT"> CONDO_TO_RENT </option>
    <option value="CONDO_TO_BUY"> CONDO_TO_BUY </option>
    <option value="HOUSE_FOR_RENT"> HOUSE_FOR_RENT</option>
    <option value="HOUSE_FOR_SALE"> HOUSE_FOR_SALE </option>
    <option value="HOUSE_TO_RENT"> HOUSE_TO_RENT </option>
    <option value="HOUSE_TO_BUY"> HOUSE_TO_BUY </option>
      </select>
  <br/><br/>

Short description:<br>
  <textarea name="description" id="description" title="Text of your advertisement:" cols="30"  rows="5"
            maxlength="200"  placeholder="Text of your advertisement" required>  </textarea></br>

  Price:
<input type="text" name="price" id="price" required></br>
  Adress:
  <input type="text" name="adress" id="adress" required></br>
  Living area:
  <input type="text" name="livingArea" id="livingArea" required></br>
  Count of bedrooms:
  <input type="number" name="countOfBedrooms" id="countOfBedrooms" required></br>
  Land area:
  <input type="text" name="landArea" id="landArea" required></br>

  <input type ="submit" name="addProperty" value="Next page"></br>
</form>
<a href="<%=request.getContextPath()%>/returnToFirstPage">Return to main page</a>
</body>
</html>
