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
<% String resPath = request.getContextPath()+"/resources/static/css";%>


<!DOCTYPE html>
<html>

<head>
  <meta http-equiv="Content-type" content="text/html; charset=utf-8">
  <link rel="stylesheet" type="text/css" href="<%=resPath%>/genStyles.css">
  <link rel="stylesheet" type="text/css" href="<%=resPath%>/fieldsetStyles.css">



  <title></title>
</head>
<body>
<div class="container">
<script type="text/javascript"
        src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">

  window.onload = function() {

    $('a.seeProperty').on('click', function (e) {

      e.preventDefault();

      var href = $(this).attr('href');
      alert(href);
      var url = href.split('?')[0];
      var data = href.split('?')[1];
      var allData = href.split('?')[1];
      var arr = {};
      arr["seeDetails"] = allData.split('&')[0];
      arr["ID"] = allData.split('&')[1];



              //alert("url:"+url);
     /* var params = get_params_from_href(href);
var arr = {};


    for (var key in params) {
      var value = params[key];
      alert("key:"+key +" value:"+value);
    }*/

      alert(url+"/"+data);
      $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType:"json",
        type: "GET",
       url: url+"/"+data,

        /*beforeSend: function(xhr) {
          xhr.setRequestHeader("Accept", "application/json");
          xhr.setRequestHeader("Content-Type", "application/json");
        },*/
      //data:JSON.stringify(data),
        cache: false,
        success: function (response) {
          alert("alert from success");
          alert(JSON.stringify(response));//not working
          alert(response);//[object Objects]
console.log(response);
alert(response.length);



          $("#responsis").empty();
          $("#responsis").append("<tr><td>Description</td><td>Price</td><td>Area</td><td>Land Area</td></tr>");
          for (var i=0; i < response.length; i++) {


            $("#responsis").append("<tr><td>"+response[i].propertyDescription+"</td><td>"
                    + response[i].price + "</td><td>"
                    + response[i].area + "</td><td>"

                    + response[i].landArea + "</td></tr>");
          };






        },
        error: function (response){
          alert("errors");
          alert(response);
          for (var i=0; i < response.length; i++) {
            $("#responsis").append("<tr><td>"+response[i].getPropertyId()+"</td></tr>");

          }
          alert(JSON.stringify(response));

        }

      });
    });

    function get_params_from_href(href){
      var paramstr = href.split('?')[1];        // get what's after '?' in the href
      alert("Alert paramstr from function:"+paramstr);
      var paramsarr = paramstr.split('&');      // get all key-value items
      alert("Alert paramstr 2 from function:"+paramsarr);
      var params = Array();
      for (var i = 0; i < paramsarr.length; i++) {
        var tmparr = paramsarr[i].split('='); // split key from value
        params[tmparr[0]] = tmparr[1];
      }
      return params;
    };

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
<fieldset id="field1">
  <legend>There are a list of users assigned to you:</legend>
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
    <!--<td><a class="seeProperty" href="<%=request.getContextPath()%>/listAllPropertiesOfThisUserFromAgent?seeUserDetails=true&ID=<%=user.getUserId()%>">See details!</a>-->
    <td><a class="seeProperty" href="<%=request.getContextPath()%>/lAPOTUFA?<%=user.getUserId()%>">See details!</a>
    </td></tr>


  <%}%>



</table>
  </fieldset>
<fieldset id="field2">
  <legent>List of properties of this particular user:</legent>
  <table>
  <div id="responsis">
  </div>
</table></fieldset>


<div id="dialog" >
</div>




<a href="<%=request.getContextPath()%>/returnToFirstPage">Return to main page</a>
</div>
</body>
</html>

