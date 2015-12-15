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



<p>You are logged in as:<%= session.getAttribute("agentFirstName") %> <%= session.getAttribute("agentLastName") %></p>
<% if (session.getAttribute("openUserSearchPopUp")!= null && session.getAttribute("openUserSearchPopUp")=="true") { %>

<script type="text/javascript">
  window.open('userSearchPopUp.jsp',"_blank","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=600, height=280,top=200,left=200");
  <%session.removeAttribute("openUserSearchPopUp");%>
</script>
<%} %>


<label>Choose from available options:</label><br>


<a href="<%=request.getContextPath()%>/listAllUsersByAgents?listUsers=listUsers">List all my users. </a><br>

<a href="<%=request.getContextPath()%>/searchForSpecificUser?openSearchBox=openSearchBox">Search for specific user.</a><br>

<a href="<%=request.getContextPath()%>/listByUser">List all adds posted by specific user.  <%= session.getAttribute("userFirstName") %>
  <%= session.getAttribute("userLastName") %></a><br>

<a href="<%=request.getContextPath()%>/search">See stats...</a><br>

<a href="<%=request.getContextPath()%>/deleteProperty?delete=delete">Add new user.</a><br>

<a href="<%=request.getContextPath()%>/deleteProperty?delete=delete">Delete user.</a><br>

<a href="<%=request.getContextPath()%>/logout">Log out</a><br>


</body>
</html>
