<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 10-Jan-16
  Time: 1:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>File Upload</title>
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
        $(wrapper).append('<div><p>Select file to upload: </p><input type="file" name="file[]" /><br />' +
                '<br><br/><br/><a href="#" class="remove_field">Remove</a></div>'); //add new photo input form fields
      }
    });

    $(wrapper).on("click",".remove_field", function(e){ //user click on remove text
      e.preventDefault(); $(this).parent('div').remove(); x--;
    })
  });
</script>
<h1>File Upload</h1>
<fieldset>
  <button class="add_field_button">Add More Photos</button>
  <form method="post" action="<%=request.getContextPath()%>/newPost"
        enctype="multipart/form-data">
  <div class="input_fields_wrap">
    <div>


    <p>You can add some photos to your post:</p>
    Select file to upload: <input type="file" name="file[]" /><br />


    </div>
    </div>
    <br /> <input type="submit" name="savePost" value="Save your post!" />
  </form>


</fieldset>


</body>
</html>
