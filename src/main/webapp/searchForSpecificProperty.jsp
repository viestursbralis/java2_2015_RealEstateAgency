<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 11/2/2015
  Time: 7:38 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>
<p>You are logged in as:</p><%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %>
<p>You agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>
<label>Specify what kind of property are you looking for:</label>
<form action="searchController" method="POST">

  <p>Select which kind of category are you searching:</p>
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

<p>Specify a price range:</p>
  From: <input type="text" name="lowerPrice" id="lowerPrice" required> To: <input type="text" name="upperPrice" id="upperPrice" required><br>

  <p>Specify an adress:</p> <input type="text" name="adress" id="adress" required><br>

  <p>Specify a living area range:</p>  From: <input type="text" name="lowerLivingArea" id="lowerLivingArea" required>  To: <input type="text" name="upperLivingArea" id="upperLivingArea" required><br>

  <p>Specify a count of bedrooms:</p> From: <input type="number" name="lowerCountOfBedrooms" id="lowerCountOfBedrooms" required> To:  <input type="number" name="upperCountOfBedrooms" id="upperCountOfBedrooms" required><br>

  <p>Specify a land area:</p>  From:  <input type="text" name="lowerLandArea" id="lowerLandArea" required>  To:  <input type="text" name="upperLandArea" id="upperLandArea" required><br>


  <input type ="submit" name="searchProperty" value="Search">
</form>

</body>
</html>

