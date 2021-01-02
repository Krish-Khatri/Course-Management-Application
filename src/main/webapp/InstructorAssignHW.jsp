<%-- 
    Document   : InstructorAssignHW
    Created on : Dec 20, 2020
    Author     : Krish Khatri
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "accounts.InstructorAccount" %>
<jsp:useBean id="instructor" class="accounts.InstructorAccount" scope="session" >
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Assign Homework</title>
    </head>
    <body>


        <%
            String course = request.getParameter("courseInstructIn");
            String homeworkAssignment = request.getParameter("homeworkAssignment");
            out.print("Creating hw file");
            instructor.setHw(course, homeworkAssignment);
            out.print("<br/>");
            out.println("<a href=\"InstructorCoursePage.jsp\">Go to Instructor Main Menu</a>");
        %>
        
        
        
    </body>
</html>
