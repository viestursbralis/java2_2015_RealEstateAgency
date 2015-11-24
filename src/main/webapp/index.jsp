<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>JSP page</title>
</head>
<body>

<h1>RealEstateAgency!</h1>


<fieldset>
  <p>If you are already member, sign in:</p>
  <form action="<%=request.getContextPath()%>/login" method="GET">
    <p>Enter your credentials:</p>
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"  />
    <label for="password">Password:</label>
    <input type="password" id="password" name="password" />
    <br/><br/>

    <center>
      <input type ="submit" value="Submit">
    </center>

  </form>
</fieldset>

<fieldset>
  <p>If you are not a member yet, this is a right time to <a href="<%=request.getContextPath()%>/newClientRegister">register</a>!</p>

</fieldset>

<fieldset>
  <p>If you already know a property ID, you can perform a quick serach!</p>
  <form action="<%=request.getContextPath()%>/quickSearch" method = "POST">
    Enter a property ID:<input type="text" name="ID"/><br/>
    <input type="hidden" name="quickSearch" value="true"/>
    <%--<a href="javascript:child_open()">Click me</a>
    <script type="text/javascript">
      var popupWindow=null;
      function child_open()
      {
        popupWindow =window.open('propertyDetailsPopUp.jsp',"_blank","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=600, height=280,top=200,left=200");
      }

    </script>--%>

    <input type="submit" value="Search!" />
  </form>
</fieldset>

<% if (session.getAttribute("quickSearch")!= null && session.getAttribute("quickSearch")=="true") { %>

<script type="text/javascript">
  popupWindow =window.open('propertyDetailsPopUp.jsp',"_blank","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=600, height=280,top=200,left=200");

</script>
<%} %>



</body>
</html>
