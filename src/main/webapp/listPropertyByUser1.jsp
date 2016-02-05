<%@ page import="lv.javaguru.java2.domain.Photo" %>
<%@ page import="lv.javaguru.java2.domain.Property" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: Viesturs
  Date: 10/30/2015
  Time: 7:07 PM
  To change this template use File | Settings | File Templates.
--%>
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

    $('a.seeProperty').on('click', function (e) {

      e.preventDefault();

      var href = $(this).attr('href');
      alert(href);
      var url = href.split('?')[0];
      alert("URL:"+url);
      var data = href.split('?')[1];
      alert("DATA:"+data);
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
          alert("JSON stringify response: "+JSON.stringify(response));//not working
          alert("Just response: "+response);//[object Objects]
          console.log(response);

          $("#responsis").empty();

          <%-- $("#responsis").append("<tr><td>Description</td><td>Price</td><td>Area</td><td>Land Area</td></tr>");



             $("#responsis").append("<tr><td>"+response.propertyDescription+"</td><td>"
                     + response.price + "</td><td>"
                     + response.area + "</td><td>"

                     + response.landArea + "</td></tr>");--%>
          $("#responsis").append("<tr><td>Property Id: </td><td>"+response.propertyId+"</td></tr><tr><td>Property description: </td><td>"+response.propertyDescription+"</td>" +
                  "<td></td></tr><tr><td>Price: </td><td>"+response.price+"</td><td></td></tr><tr><td>Adress: </td><td>"+response.adress+"</td>" +
                  "<td></td></tr><tr><td>Living area: </td><td>"+response.area+"</td><td></td></tr><tr><td>Count of bedrooms: </td><td>"+response.countOfBedrooms+ "</td>" +
                  "<td></td></tr><tr><td>Land area: </td><td>"+ response.landArea+"</td><td></td></tr>" +
                  "<tr><td>List of owners:</td></tr>");

          for (var i=0; i < response.propertyOwners.length; i++) {


            $("#responsis").append("<tr><td>Owner Id:</td><td>"+response.propertyOwners[i].id+"</td></tr><tr><td>Owners first name:</td><td>"
                    + response.propertyOwners[i].firstName + "</td></tr><tr><td>Owners last name:</td><td>"
                    + response.propertyOwners[i].lastName + "</td></tr><tr><td>Owners email:</td><td>"
                    + response.propertyOwners[i].ownerEmail + "</td></tr><tr><td>Owners phone:</td><td>"
                    + response.propertyOwners[i].ownerPhone + "</td></tr>");
          };
          $("responsis").append("<tr><td rowspan=2>List of utilities:</td></tr>");
          for (var i=0; i < response.propertyUtilities.length; i++) {


            $("#responsis").append("<tr><td>Utility description</td><td>"+response.propertyUtilities[i].utilityDescription+"</td></tr>");
          };

          $("responsis").append("<tr><td rowspan=2>List of photos:</td></tr>");

          for (var i=0; i < response.propertyPhotos.length; i++) {

            $("#responsis").append("<tr><td>Photo:</td><td><img src=PropertyPhotos/"+response.propertyPhotos[i].photoName+" width='160' height='160'> </td></tr>");



          };




        },
        error: function (response){
          alert("errors");
          alert("Just response: " +response);

          alert("JSON stingify response: "+JSON.stringify(response));

        }

      });
    });

    function get_params_from_href(href){
      var paramstr = href.split('?')[1];        // get what's after '?' in the href

      var paramsarr = paramstr.split('&');      // get all key-value items

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

<% if (session.getAttribute("deleted")!= null) { %>
<p><%=session.getAttribute("deleted")%></p>
<% } else {%>
<p></p>
<% } %>

<% if (session.getAttribute("userName")!= null) { %>
<p>Username:<%=session.getAttribute("userName")%></p>
<% } else {%>
<p></p>
<% } %>



<% if (session.getAttribute("propertyDetails")!= null) { %>

<script type="text/javascript">
window.open('propertyDetailsPopUp.jsp',"_blank","directories=no, status=no, menubar=no, scrollbars=yes, resizable=no,width=600, height=280,top=200,left=200");

</script>
 <%} %>


<p>You are logged in as:</p><%= session.getAttribute("userFirstName") %> <%= session.getAttribute("userLastName") %>
<p>Your agent is: <%=session.getAttribute("agentFirstName")%>  <%=session.getAttribute("agentLastName")%></p><br>



<p> There are a list of properties added by you:</p>
<fieldset id="field1">
<table>
  <tr><td>Property Id</td><td>Property description</td>

    <td>Price</td><td>Statuss</td>
    <td>See details!</td>


  </tr>
  <%List<Property> propertiesOfThisUser= (List<Property>)request.getAttribute("model");
    for(Property prop:propertiesOfThisUser){ %>

  <tr><td><%= prop.getPropertyId()%></td>
    <td><%= prop.getPropertyDescription()%></td>
    <td><%= prop.getPrice()%></td>
    <td><%= prop.getPostStatuss()%></td>
    <td><a class="seeProperty" href="<%=request.getContextPath()%>/seePropertyDetails?<%=prop.getPropertyId()%>">See details!</a>
      </td></tr>


  <%}%>



</table>
</fieldset>
<fieldset id="field2">
  <legent>There are details of particular property:</legent>
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
