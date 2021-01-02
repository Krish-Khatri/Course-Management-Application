<%-- 
    Document   : InstructorCoursePage
    Created on : Dec 21, 2020
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
        <title>Instructor Courses</title>
    </head>
    <body>
        <H3><u>Instructor Homework menu</u></H3>
        <%
            ArrayList<String> courses = instructor.getCourses();
            out.println("<form method=\"post\" action = \"InstructorAssignHW.jsp\">");

            out.println("<p>Courses Currently Teaching: ");
            out.print("<select name = \"courseInstructIn\" size = \"1\">");
            for (int i = 0; i < courses.size(); i++) {
                out.print("<option value = " + courses.get(i) + ">" + courses.get(i) + "</option>");
            }
            out.print("</select>");
        %>
        
        </br>
        Please enter your assignment:
        </br>
        <textarea name="homeworkAssignment" rows="5"></textarea>
        </br>

        <%
            out.print("<input type=\"submit\" value=\"Pick Course\" >");
        %>


    </body>
</html>
