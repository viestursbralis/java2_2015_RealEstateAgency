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


<% String resPath = request.getContextPath()+"/resources/static/css";%>


<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
   <%-- <link rel="stylesheet" type="text/css" href="<%=resPath%>/genStyles.css">
    <link rel="stylesheet" type="text/css" href="<%=resPath%>/fieldsetStyles.css">--%>

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
<%--
          $("#dialog").empty();
            $("#form1").empty();
            $("#dialog").append("<form id='form1' action='' method='GET'></form>");

            $("#form1").append("<p>ID:<input type='text' name='ID' value=" + response.propertyId + "></p>");
            $("#form1").append("<p>Category:<input type='text' name='categoryName' value=" + response.category.categoryName + "></p>");
            $("#form1").append("<p>Description:<input type='text' name='description' value=" + response.propertyDescription + "></p>");
            $("#form1").append("<p>Price:<input type='text' name='price' value=" + response.price + "></p>");
            $("#form1").append("<p>Adress:<input type='text' name='adress' value=" + response.adress + "></p>");
            $("#form1").append("<p>Area:<input type='text' name='area' value=" + response.area + "></p>");
            $("#form1").append("<p>Count of bedrooms:<input type='text' name='bedrooms' value=" + response.countOfBedrooms + "></p>");
            $("#form1").append("<p>Land area:<input type='text' name='landArea' value=" + response.landArea + "></p>");






          for (var i=0; i < response.propertyOwners.length; i++) {
$("#form1").append("<p>Owner Nr."+i+ " details:</p>");

              var ownerId = "ownerId"+i;
              var ownerFirstName = "ownerFirstName"+i;
              var ownerLastName= "ownerLastName"+i;
              var ownerEmail = "ownerEmail"+i;
              var ownerPhone = "ownerPhone"+i;
              alert(ownerId);
              $("#form1").append("<p>Owner Id:<input type='text' name = '"+ ownerId +"' value="+response.propertyOwners[i].id  +"></p>");
              $("#form1").append("<p>Owner first name:<input type='text' name = '"+ ownerFirstName +"' value="+response.propertyOwners[i].firstName + "></p>");
              $("#form1").append("<p>Owner last name:<input type='text' name = '"+ ownerLastName +"' value="+response.propertyOwners[i].lastName +"></p>");
              $("#form1").append("<p>Owner email:<input type='text' name = '"+ ownerEmail +"' value="+response.propertyOwners[i].ownerEmail+"></p>");
              $("#form1").append("<p>Owner phone:<input type='text' name = '"+ ownerPhone +"' value="+response.propertyOwners[i].ownerPhone+"></p>");

          };

          $("#form1").append("<p>List of utilities:</p>");

          for (var i=0; i < response.propertyUtilities.length; i++) {

            $("#form1").append("<label for='"+response.propertyUtilities[i].utilityDescription+"'>"+response.propertyUtilities[i].utilityDescription+" </label>");
          $("#form1").append("<input type='checkbox' value='"+response.propertyUtilities[i].utilityDescription+"' name='"+response.propertyUtilities[i].utilityDescription+"' id='"+response.propertyUtilities[i].utilityDescription+"' checked='checked' />");


          };
$("#form1").append("<p>List of photos:</p>");
            for (var i=0; i < response.propertyPhotos.length; i++) {

                $("#form1").append("<p>Photo:</p><p><img src=PropertyPhotos/"+response.propertyPhotos[i].photoName+" width='160' height='160'> </p>");
                $("#form1").append("<input type='checkbox' value='"+response.propertyPhotos[i].photoName+"' name='"+response.propertyPhotos[i].photoName+"' id='"+response.propertyPhotos[i].photoName+"' checked='checked' />");


            };

        $("#form1").append("<input type='submit' name ='submitChanges' value='Submit changes!' />");
            $("#form1").append("<input type='submit' id='delete' name ='delete' value='Delete!' />");
--%>

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
