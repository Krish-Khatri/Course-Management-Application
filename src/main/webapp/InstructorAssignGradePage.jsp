<%-- 
    Document   : InstructorAssignGradePage
    Created on : Dec 22, 2020
    Author     : Krish Khatri
--%>

<%@page import="java.lang.String"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "accounts.InstructorAccount" %>
<jsp:useBean id="instructor" class="accounts.InstructorAccount" scope="session" >
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pick Student</title>
    </head>
    <body>
        <h1>What Student do you want to grade?</h1>
        <%
            String course = request.getParameter("courseInstructIn");
            session.setAttribute("course", course);
            ArrayList<String> students = instructor.GetStudentList(course);
            out.println("<form method=\"post\" action = \"InstructorChooseHw.jsp\">");

            out.println("<p>Students Currently Instructing ");
            out.print("<select name = \"studentInstructing\" size = \"1\">");
            for (int i = 0; i < students.size(); i++) {
                out.print("<option value = " + students.get(i).substring(0, 4) + ">" + students.get(i) + "</option>");
            }
            out.print("</select>");

            out.print("<input type=\"submit\" value=\"Assign Grade\" >");
        %>
    </body>
</html>
