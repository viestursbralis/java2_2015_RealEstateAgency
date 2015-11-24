<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>JSP page</title>
</head>
<body>

<h1>RealEstateAgency!</h1>


<fieldset>

  <form method="POST" action="<%=request.getContextPath()%>/imageUpload" enctype="multipart/form-data" >
    <input type="text" name="description"/>
    <input type="file" name="file"/>

    <input type="submit" name="addImage" value="Register!">

  </form>
</fieldset>

<fieldset>


</fieldset>







</body>
</html>
