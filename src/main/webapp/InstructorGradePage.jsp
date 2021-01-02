<%-- 
    Document   : InstructorGradePage
    Created on : Dec 26, 2020
    Author     : Krish Khatri
--%>


<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "accounts.InstructorAccount" %>
<jsp:useBean id="instructor" class="accounts.InstructorAccount" scope="session" >
</jsp:useBean>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pick Course</title>
    </head>
    <body>
        <h3>Pick course to assign grades</h3>
        <%
            ArrayList<String> courses = instructor.getCourses();
            out.println("<form method=\"post\" action = \"InstructorAssignGradePage.jsp\">");

            out.println("<p>Courses Currently Teaching: ");
            out.print("<select name = \"courseInstructIn\" size = \"1\">");
            for (int i = 0; i < courses.size(); i++) {
                out.print("<option value = " + courses.get(i) + ">" + courses.get(i) + "</option>");
            }
            out.print("</select>");
            
            out.print("<input type=\"submit\" value=\"Course Picked\" >");
        %>
    </body>
</html>
