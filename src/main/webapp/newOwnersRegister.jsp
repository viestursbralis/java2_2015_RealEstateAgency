<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 11/1/2015
  Time: 10:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page trimDirectiveWhitespaces="true"%>

<html>
<head>
    <title></title>
  <script type="text/javascript"
          src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
</head>
<body>
<script type="text/javascript">
$(document).ready(function() {
var max_fields      = 10; //maximum input boxes allowed
var wrapper         = $(".input_fields_wrap"); //Fields wrapper
var add_button      = $(".add_field_button"); //Add button ID

var x = 1; //initlal text box count
$(add_button).click(function(e){ //on add input button click
e.preventDefault();
if(x < max_fields){ //max input box allowed
x++; //text box increment
$(wrapper).append('<div><p>New property owner (contactperson) register:</p><p>Enter your data:</p>' +
        '<label for="firstName[]">First name:</label><input type="text" id="firstName[]" name="firstName[]"  required/>' +
'<br><label for="lastName[]">Last name:</label><input type="text" id="lastName[]" name="lastName[]" required/>' +
'<br><label for="email[]">Contact email:</label><input type="email" id="email[]" name="email[]" required/>' +
'<br><label for="phone[]">Phone number:</label><input type="text" id="phone[]" name="phone[]" required/>' +
        '<br><br/><br/><a href="#" class="remove_field">Remove</a></div>'); //add new contactperson input form fields
}
});

$(wrapper).on("click",".remove_field", function(e){ //user click on remove text
e.preventDefault(); $(this).parent('div').remove(); x--;
})
});
</script>

<p>You are logged in as:<%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %></p><br>
<p>You agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>

<p>Data from previous page:<%=session.getAttribute("category")%></p>
<fieldset>
  <button class="add_field_button">Add More Contactpersons</button>
  <form action="<%=request.getContextPath()%>/newPost" method="GET">
    <div class="input_fields_wrap">
      <div>
        <p>New property owner (contactperson) register:</p>
        <p>Enter your data:</p>
        <label for="firstName[]">First name:</label>
        <input type="text" id="firstName[]" name="firstName[]"  required/><br>
        <label for="lastName[]">Last name:</label>
        <input type="text" id="lastName[]" name="lastName[]" required/><br>
        <label for="email[]">Contact email:</label>
        <input type="email" id="email[]" name="email[]" required/><br>
        <label for="phone[]">Phone number:</label>
        <input type="text" id="phone[]" name="phone[]" required/><br>

        <br/><br/>

      </div>
</div>
    <center>
      <input type ="submit" name="addOwner" value="Previous page">
      <input type ="submit" name="addOwner" value="Next page">
    </center>

  </form>
</fieldset>
<a href="<%=request.getContextPath()%>/returnToFirstPage">Return to main page</a>
</body>
</html>
