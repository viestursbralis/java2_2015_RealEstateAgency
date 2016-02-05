<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 11/2/2015
  Time: 7:38 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    $(document).on('submit', '#searchForm', function(e) {
      e.preventDefault();

var data = $("#searchForm").serialize();
alert("Serialized data are:" +data);
      alert(JSON.stringify(data));
      $.ajax({
        //contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        contentType:  "application/json; charset=utf-8",
        //dataType:"json",
        type: "GET",
        url: "bigSearch",
        //data: JSON.stringify($(this).serializeArray()),
        data:data,

        cache: false,
        success: function (response) {
          $("#responsis").empty();
          alert("Data are submited successfully!");

          $("#responsis").append("<tr><td>Description</td><td>Price</td><td>Area</td><td>Land Area</td><td>See details</td></tr>");
          for (var i=0; i < response.length; i++) {


            $("#responsis").append("<tr><td>"+response[i].propertyDescription+"</td><td>"
                    + response[i].price + "</td><td>"
                    + response[i].area + "</td><td>"

                    + response[i].landArea + "</td><td><a class=links id="
                    + response[i].propertyId+" href =seePropertyDetails/"
                    + response[i].propertyId  +">"
                    + response[i].propertyId+"</a></td></tr>");
          };

        somethingFunction();
        },
        error: function (response){
          alert("errors");
          alert(JSON.stringify(response));
        }
      })

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
      var data = href.split('/')[1];
      alert(url+"/"+data);

      $.ajax({
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        dataType:"json",
        type: "GET",
        url: url+"/"+data,
        success: function (response) {
$("#dialog").empty();
          <%-- $("#responsis").append("<tr><td>Description</td><td>Price</td><td>Area</td><td>Land Area</td></tr>");



              $("#responsis").append("<tr><td>"+response.propertyDescription+"</td><td>"
                      + response.price + "</td><td>"
                      + response.area + "</td><td>"

                      + response.landArea + "</td></tr>");--%>
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


            $("#dialog").append("<tr><td>Utility description</td><td>"+response.propertyUtilities[i].utilityDescription+"</td></tr>");
          };

          $("dialog").append("<tr><td rowspan=2>List of photos:</td></tr>");

          for (var i=0; i < response.propertyPhotos.length; i++) {

            $("#dialog").append("<tr><td>Photo:</td><td><img src=PropertyPhotos/"+response.propertyPhotos[i].photoName+" width='160' height='160'> </td></tr>");



          };




        },
        error: function (response){
          alert("errors");
          alert(JSON.stringify(response));
        }
      })
      return false;
    });
  };


  $(document).on('click', '#delete', function(e) {
    e.preventDefault();
    alert("deleteFunction");

    //alert(JSON.stringify($(this).serializeArray()));
    var data = {"propertyId":178};
    var dataString = JSON.stringify(data);
    alert(dataString);
    alert(JSON.stringify(data));
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
      data:JSON.stringify(data),

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


<p>You are logged in as: <%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %></p>


<p>You agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>

<fieldset id="field1">
<label>Specify what kind of property are you looking for:</label>
<form id ="searchForm" action="<%=request.getContextPath()%>/bigSearch" method="GET">

  <p>Select which kind of category are you searching:</p>
  <select name="postType" >
    <option value="CONDO_FOR_RENT"> CONDO_FOR_RENT </option>
    <option value="CONDO_FOR_SALE"> CONDO_FOR_SALE </option>
    <option value="CONDO_TO_RENT"> CONDO_TO_RENT </option>
    <option value="CONDO_TO_BUY"> CONDO_TO_BUY </option>
    <option value="HOUSE_FOR_RENT"> HOUSE_FOR_RENT</option>
    <option value="HOUSE_FOR_SALE"> HOUSE_FOR_SALE </option>
    <option value="HOUSE_TO_RENT"> HOUSE_TO_RENT </option>
    <option value="HOUSE_TO_BUY"> HOUSE_TO_BUY </option>
  </select>
  <br/><br/>

<p>Specify a price range:</p>
  From: <input type="text" name="lowerPrice" id="lowerPrice" >
  To: <input type="text" name="upperPrice" id="upperPrice" ><br>

  <p>Specify an adress:</p> <input type="text" name="adress" id="adress" ><br>

  <p>Specify a living area range:</p>
  From: <input type="text" name="lowerLivingArea" id="lowerLivingArea">
  To: <input type="text" name="upperLivingArea" id="upperLivingArea" ><br>

  <p>Specify a count of bedrooms:</p>
  From: <input type="number" name="lowerCountOfBedrooms" id="lowerCountOfBedrooms" >
  To:  <input type="number" name="upperCountOfBedrooms" id="upperCountOfBedrooms" ><br>

  <p>Specify a land area:</p>
  From:  <input type="text" name="lowerLandArea" id="lowerLandArea" >
  To:  <input type="text" name="upperLandArea" id="upperLandArea" ><br>

  <p>Check which utility applies:</p>

  <input id="internet" name="internet" value="1" type="checkbox">
  <label for="internet">Broadband internet cable</label><br>
  <input id="city_gas" name="city_gas" value="1" type="checkbox">
  <label for="city_gas">City gas pipe</label><br>
  <input id="city_heat" name="city_heat" value="1" type="checkbox">
  <label for="city_heat">City heat</label><br>
  <input id="city_water" name="city_water" value="1" type="checkbox">
  <label for="city_water">City water</label><br>
  <input id="city_sewer" name="city_sewer" value="1" type="checkbox">
  <label for="city_sewer">City sewer</label><br>
  <input type ="submit" name="searchProperty" value="Search">
</form>
</fieldset>


<fieldset id="field2">
  <legend>Result of your search are the following:</legend>
  <table>
    <div id="responsis">
    </div>
    </table>
  <table>
      <div id="dialog" >
      </div>
    </table>
  </fieldset>
<a href="<%=request.getContextPath()%>/returnToFirstPage">Return to main page</a>

</body>
</html>

