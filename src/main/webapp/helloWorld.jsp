<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JSP page</title>
</head>
<body>

    <h1>RealEstateAgency!</h1>


<fieldset>
<p>If you are already member, sign in:</p>
    <form action="AgencyLogin" method="GET">
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
    <p>If you are not a member yet, this is a right time to <a href="newClientRegister">register</a>!</p>

</fieldset>







</body>
</html>
