<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 10/30/2015
  Time: 7:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>





<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">


  <title></title>
</head>
<body>
<script type="text/javascript"
        src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">


  window.onload = function() {

    $('a.seeWaitingList').on('click', function (e) {

      e.preventDefault();

      var href = $(this).attr('href');
      alert(href);
      var url = href.split('?')[0];
      var data = href.split('?')[1];
      var allData = href.split('?')[1];
      var arr = {};
      arr["seeDetails"] = allData.split('&')[0];
      arr["ID"] = allData.split('&')[1];



      alert(url+"/"+data);
      $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType:"json",
        type: "GET",
        url: url+"/"+data,
        //data:"ID"+data,
        cache: false,
        success: function (response) {
          alert("alert from success");
          alert(JSON.stringify(response));//not working
          alert(response);//[object Objects]
          console.log(response);
          alert(response.length);



          $("#responsis").empty();
          $("#responsis").append("<tr><td>Description</td><td>Price</td><td>Area</td><td>Land Area</td><td>See details</td></tr>");
          for (var i=0; i < response.length; i++) {


            $("#responsis").append("<tr><td>"+response[i].propertyDescription+"</td><td>"
                    + response[i].price + "</td><td>"
                    + response[i].area + "</td><td>"

                    + response[i].landArea + "</td><td><a class=links id="
                    + response[i].propertyId+" href =propertyApproveForm/"
                    + response[i].propertyId  +">"
                    + response[i].propertyId+"</a></td></tr>");
          };

          somethingFunction();
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

  };

  $(document).ready(function () {

    somethingFunction();

  });

  function somethingFunction(){
    $('a.links').on('click', function (e) {
      e.preventDefault();
      var href = $(this).attr('href');
      alert(href);
      var url = href.split('/')[0];
      var ID = href.split('/')[1];
      alert("URL + DATA from .link:" +url+"/"+ID);

      $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        //dataType:"json",
        type: "GET",
        url: url,
          data:"ID="+ID,
        success: function (response) {
            alert("Alert from approval form success!");

            $("#dialog").append(response);



        },
        error: function (response){
          alert("Evil errors from AJAX!");

          alert(JSON.stringify(response));


        }
      })
      return false;
    });
  };

  $(document).on('submit', '#form1', function(e) {
      e.preventDefault();
      alert("someOtherFunction");

        alert(JSON.stringify($(this).serializeArray()));
        alert($(this).serialize());

      //alert(JSON.stringify(data));
      $.ajax({
          //contentType: "application/x-www-form-urlencoded; charset=UTF-8",
          contentType:  "application/json; charset=utf-8",
          dataType:"json",
          type: "POST",
          url: "submitEdited",
          data: JSON.stringify($(this).serializeArray()),
            //data:JSON.stringify(data),

          cache: false,
          success: function () {
            $("#dialog").empty();
              alert("Data are updated successfully!");

          },
          error: function (response){
              alert("errors");
              alert(JSON.stringify(response));
          }
      })

  });
  $(document).on('click', '#delete', function(e) {
      e.preventDefault();
      alert("deleteFunction");

      //alert(JSON.stringify($(this).serializeArray()));
      var data0 = {propertyId:178};
      var data = $('#propertyId').val();
      var data1 = {propertyId: $('#propertyId').val()};
      //var dataString = JSON.stringify(data);
      alert(data1);
      alert(JSON.stringify(data1));
      $.ajax({
          //contentType: "application/x-www-form-urlencoded; charset=UTF-8",
          contentType:  "application/json; charset=utf-8",
          dataType:"json",
          type: "POST",
          async:false,
          cache:false,
          processData:false,
          url: "submitEdited/delete",
          //data: JSON.stringify($(this).serializeArray()),
          data:JSON.stringify(data1),
          //data:data0,
          //data:data,
          cache: false,
          success: function (response) {
              $("#dialog").empty();
              alert("Record was deleted successfully!");
              $("#dialog").append(JSON.stringify(response));

          },
          error: function (response){
              alert("errors");
              alert(JSON.stringify(response));
          }
      })

  });



</script>


<p>You are logged in as:<%= session.getAttribute("agentFirstName") %> <%= session.getAttribute("agentLastName") %></p>
<% if (session.getAttribute("openUserSearchPopUp")!= null && session.getAttribute("openUserSearchPopUp")=="true") { %>

<script type="text/javascript">
  window.open('userSearchPopUp.jsp',"_blank","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=600, height=280,top=200,left=200");
  <%session.removeAttribute("openUserSearchPopUp");%>
</script>
<%} %>

<fieldset id="field1">

  <legend>Properties in waiting list are the following:</legend>


  <a href="<%=request.getContextPath()%>/listAllUsersByAgents?listUsers=listUsers">List all my users. </a><br>

  <a href="<%=request.getContextPath()%>/searchForSpecificUser?openSearchBox=openSearchBox">Search for specific user.</a><br>

  <a href="<%=request.getContextPath()%>/listByUser">List all adds posted by specific user.  <%= session.getAttribute("userFirstName") %>
    <%= session.getAttribute("userLastName") %></a><br>

  <a class="seeWaitingList" href="<%=request.getContextPath()%>/seeWaitingList?<%= session.getAttribute("agentId")%>">See waiting list.</a><br>

  <a href="<%=request.getContextPath()%>/sendEmail">Send email</a><br>

  <a href="<%=request.getContextPath()%>/deleteProperty?delete=delete">Delete user.</a><br>

  <a href="<%=request.getContextPath()%>/logout">Log out</a><br>

</fieldset>




<fieldset id="field2">
  <legend>Properties in waiting list are the following:</legend>
  <table>
    <div id="responsis">
    </div>
  </table>
</fieldset>


<fieldset>
    <div id="dialog" >
    </div>

</fieldset>




<table>
  <div id="owners" >
  </div>
</table>

<table>
  <div id="utils" >
  </div>
</table>
<table>
  <div id="photos" >
  </div>
</table>








</body>
</html>
