<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 11-Dec-15
  Time: 10:43 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page import="lv.javaguru.java2.domain.Property" %>
<%@ page import="lv.javaguru.java2.domain.User" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>

<head>
  <title></title>
</head>
<body>
<script type="text/javascript"
        src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">

  window.onload = function() {

    $('.seeProperty').on('click', function (e) {

      e.preventDefault();
      var id=$(this).attr('id');
      var url=$(this).attr('href');
      alert (DATA);
      $.ajax({
        type: "POST",

        url: url,
        data: DATA,
        cache: false,
        success: function (response) {
          console.log(response);
          $("#responsis").empty();
          alert(JSON.stringify(response));


          $("#responsis").append("<tr><td>Nosaukums</td><td>Klasifikācija</td><td>Izbraukšanas datums</td><td>Atgriešanās datums</td><td>Valsts</td><td>Pilsēta</td><td>Cenas</td><td>Vietu skaits</td></tr>");
          for (var i=0; i < response.length; i++) {


            $("#responsis").append("<tr><td><a class=links id="+response[i].name+" href =route_name/"+ response[i].name  +">"+ response[i].name+"</a></td><td>"
                    + response[i].class + "</td><td>"
                    + response[i].izbrauc_no + "</td><td>"
                    + response[i].izbrauc_liidz + "</td><td>"
                    + response[i].valsts + "</td><td>"
                    + response[i].pilseeta + "</td><td>"
                    + response[i].cena + "</td><td>"
                    + response[i].vietu_skaits + "</td></tr>");
          };
          somethingFunction();
        },
        error: function (response){
          alert("errors");
          alert(JSON.stringify(response));

        }

      });
    });

    $(function () {
      $("#dialog").dialog({
        autoOpen: false,
        modal: true,
        title: "Maršruta apraksts",
        buttons: {
          Aizvērt: function () {
            $(this).dialog('close');
          }
        }
      });
    });
  };

  $(document).ready(function () {

    somethingFunction();

  });



  function somethingFunction(){
    $('.links').on('click', function (e) {
      e.preventDefault();
      var id=$(this).attr('id');
      var url=$(this).attr('href');


      $.ajax({
        type: "POST",
        url: url,
        data: "id="+id,
        success: function (response) {

          $("#dialog").html(response[0].desc);
          $("#dialog").dialog("open");

        },
        error: function (response){
          alert("errors");
          alert(JSON.stringify(response));
        }
      })
      return false;
    });
  };

</script>




<% if (session.getAttribute("userName")!= null) { %>
<p>Username:<%=session.getAttribute("userName")%></p>
<% } else {%>
<p></p>
<% } %>



<% if (session.getAttribute("seeUserDetails")!= null && session.getAttribute("seeUserDetails")=="true") { %>

<script type="text/javascript">
  window.open('userDetailsPopUp.jsp',"_blank","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=600, height=280,top=200,left=200");
<%session.removeAttribute("seeUserDetails");%>
</script>
<%} %>






<p>You are logged in as:</p><%= session.getAttribute("agentFirstName") %> <%= session.getAttribute("agentLastName") %>




<p> There are a list of users assigned to you: </p>
<table>
  <tr><td>User Id</td><td>User first name</td>

    <td>User last name</td><td>User statuss</td><td>User email</td>
    <td>See details!</td>


  </tr>
  <%List<User> usersOfThisAgent= (List<User>)request.getAttribute("model");
    for(User user:usersOfThisAgent){ %>

  <tr><td><%= user.getUserId()%></td>
    <td><%= user.getFirstName()%></td>
    <td><%= user.getLastName()%></td>
    <td><%= user.getStatuss()%></td><td><%= user.getUserEmail()%></td>
    <td><a class=seeProperty href="<%=request.getContextPath()%>/listAllPropertiesOfThisUserFromAgent?seeUserDetails=true&ID=<%=user.getUserId()%>">See details!</a>
    </td></tr>


  <%}%>



</table>
<table>
  <div id="responsis">
  </div>
</table>

<div id="dialog" >
</div>




<a href="<%=request.getContextPath()%>/returnToFirstPage">Return to main page</a>

</body>
</html>

