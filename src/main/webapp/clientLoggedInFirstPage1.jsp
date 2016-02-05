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



<% if (request.getAttribute("model")!= null) { %>
<p>Model is:<%=request.getAttribute("model")%></p>
<% } else {%>
<p></p>
<% } %>



<% if (session.getAttribute("postSuccessfull")!= null) { %>
<p><%=session.getAttribute("postSuccessfull")%></p>
<% } else {%>
<p></p>
<% } %>
<% if (session.getAttribute("path")!= null) { %>
<p>Path is:<%=session.getAttribute("path")%></p>
<% } else {%>
<p></p>
<% } %>
<% if (session.getAttribute("fullPath")!= null) { %>
<p>Full path is:<%=session.getAttribute("fullPath")%></p>
<% } else {%>
<p></p>
<% } %>
<% if (session.getAttribute("fn")!= null) { %>
<p>FN:<%=session.getAttribute("fn")%></p>
<% } else {%>
<p></p>
<% } %>




<p>You are logged in as:<%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %></p>


<p>You agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>




<label>Choose from available options:</label><br>


<a href="<%=request.getContextPath()%>/newPost?start=start">Post a new RealEstate add to the database</a><br>

<a href="<%=request.getContextPath()%>/listAllProperties">List all RealEstate's in the database</a><br>

<a href="<%=request.getContextPath()%>/listByUser">List all add's posted by  <%= session.getAttribute("userFirstName") %>
  <%= session.getAttribute("userLastName") %></a><br>

<a href="<%=request.getContextPath()%>/search">Search for specific RealEstate in the database</a><br>

<a href="<%=request.getContextPath()%>/deleteProperty">Delete a specific RealEstate from the database</a><br>


<a href="<%=request.getContextPath()%>/logout">Log out</a><br>


<fieldset id="field2">
  <legent>There are deteils of particular property:</legent>
  <table>
    <div id="responsis">
    </div>
  </table>
  <table>
    <div id="dialog" >
    </div>

  </table>
</fieldset>


</body>



</html>
