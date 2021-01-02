<%--
  Created by IntelliJ IDEA.
  User: Krish Khatri
  Date: 12/31/2020
  Time: 9:22 PM
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Instructor Log in</title>
</head>
<body>
<h1>Instructor Log In:</h1>
<form method="post" action = "InstructorValidation.jsp">
    <table border="0" width="300" cellpadding="3" cellspacing="3">
        <tr>
            <td>Username:</td>
            <td><input type = "text" name = "username" size = "20"> <br></td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type = "text" name = "password" size = "20"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Confirm" ></td>
        </tr>
    </table>

</form>
</body>
</html>
