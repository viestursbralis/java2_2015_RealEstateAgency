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



<%--
<script type="text/javascript">

  window.onunload=refreshParent;
  function refreshParent() {
    window.opener.location.reload(true);

  }
</script>--%>


<script type="text/javascript">
  function closeSelf(){
    self.close();
    <%session.removeAttribute("openUserSearchPopUp");%>
    return true;
  }
</script>




<h1>RealEstateAgency!</h1>




<fieldset>
  <form action="<%=request.getContextPath()%>/searchForSpecificUser" method="GET">
    <input type="text" name="userParameter" />


    <input type="submit" value="Search!"/>
  </form>

</fieldset>


<%--<script type="text/javascript">
  propertyDeletePopUp.onunload = function () {

    window.opener.location.reload(true);
  }


</script>--%>




</body>
</html>

