<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
  <title>JSP page</title>


</head>
<body>
<script type="text/javascript"
        src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">

  window.onload = function() {

    $('a.register').on('click', function (e) {

      e.preventDefault();


      $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        //dataType:"json",
        type: "GET",
        url: "newClientRegister1",

        cache: false,
        success: function (response) {
          alert("alert from success");
        $("#responsis").append(response)
        },
        error: function (response){
          alert("errors");
          alert("Just response: " +response);

          alert("JSON stingify response: "+JSON.stringify(response));

        }

      });
    });


  };
  $(document).on('submit', '#quickSearch', function(e) {
    e.preventDefault();
    alert("someOtherFunction");
      //var data1 = {propertyId: $('#propertyId').val()};
    var data = {propertyId: $('#quickSearchId').val()};
        alert(data);

    alert(JSON.stringify(data));
    $.ajax({
      //contentType: "application/x-www-form-urlencoded; charset=UTF-8",
      contentType:  "application/json; charset=utf-8",
      dataType:"json",
      type: "POST",
        async:false,
        cache:false,
        processData:false,
      url: "quickSearch",
      //data: JSON.stringify($(this).serializeArray()),
      data:JSON.stringify(data),
      //data:data,
      cache: false,
      success: function (response) {
        alert("Alert from approval form success!");
          $("#dialog").empty();



          $("#dialog").append("<tr><td>Property Id: </td><td>"+response.propertyId+"</td></tr><tr><td>Property description: </td><td>"+response.propertyDescription+"</td>" +
                  "<td></td></tr><tr><td>Price: </td><td>"+response.price+"</td><td></td></tr><tr><td>Adress: </td><td>"+response.adress+"</td>" +
                  "<td></td></tr><tr><td>Living area: </td><td>"+response.area+"</td><td></td></tr><tr><td>Count of bedrooms: </td><td>"+response.countOfBedrooms+ "</td>" +
                  "<td></td></tr><tr><td>Land area: </td><td>"+ response.landArea+"</td><td></td></tr>" +
                  "<tr><td>List of owners:</td></tr>");

          for (var i=0; i < response.propertyOwners.length; i++) {


              $("#dialog").append("<tr><td>Owner Id:</td><td>"+response.propertyOwners[i].id+"</td></tr><tr><td>Owners first name:</td><td>"
                      + response.propertyOwners[i].firstName + "</td></tr><tr><td>Owners last name:</td><td>"
                      + response.propertyOwners[i].lastName + "</td></tr><tr><td>Owners email:</td><td>"
                      + response.propertyOwners[i].ownerEmail + "</td></tr><tr><td>Owners phone:</td><td>"
                      + response.propertyOwners[i].ownerPhone + "</td></tr>");
          };
          $("dialog").append("<tr><td rowspan=2>List of utilities:</td></tr>");
          for (var i=0; i < response.propertyUtilities.length; i++) {


              $("#dialog").append("<tr><td>Utility description:</td><td>"+response.propertyUtilities[i].utilityDescription+"</td></tr>");
          };

          $("dialog").append("<tr><td rowspan=2>List of photos:</td></tr>");

          for (var i=0; i < response.propertyPhotos.length; i++) {

              $("#dialog").append("<tr><td>Photo:</td><td><img src=PropertyPhotos/"+response.propertyPhotos[i].photoName+" width='160' height='160'></td></tr>");



          };

      },
      error: function (response){
        alert("errors");
        alert(JSON.stringify(response));
      }
    })

  });




</script>
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
  <p>If you are not a member yet, this is a right time to
    <a class="register" href="<%=request.getContextPath()%>/newClientRegister">register</a>!</p>

</fieldset>

<fieldset id="field2">
  <legend>Registration form:</legend>
  <table>
    <div id="responsis">
    </div>
  </table>

</fieldset>


<fieldset>
  <p>If you already know a property ID, you can perform a quick search!</p>
  <form id="quickSearch" action="<%=request.getContextPath()%>/quickSearch" method = "GET">
    Enter a property ID:<input type="text" id="quickSearchId" name="ID"/><br/>
    <input type="hidden" name="quickSearch" value="true"/>


    <input type="submit" id="qSearchSubmit" value="Search!" />
  </form>
</fieldset>

<fieldset id="field3">
  <legend>See property from quicksearch:</legend>

    <table>
        <div id="dialog" >
        </div>

    </table>



</fieldset>

<% if (session.getAttribute("quickSearch")!= null && session.getAttribute("quickSearch")=="true") { %>

<script type="text/javascript">
  popupWindow =window.open('propertyDetailsPopUp.jsp',"_blank","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=600, height=280,top=200,left=200");
<%session.removeAttribute("quickSearch");%>
</script>
<%} %>



</body>
</html>
