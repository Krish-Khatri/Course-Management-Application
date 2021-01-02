<%-- 
    Document   : InstructorChooseHw
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
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Pick hw to grade</h1>
        <%  String student = request.getParameter("studentInstructing");
            session.setAttribute("student", student);
            String course = (String) session.getAttribute("course");

            out.print("Student = " + student);

            out.println("<form method=\"post\" action = \"InstructorSubmitGrade.jsp\">");


            out.print("<select name = \"hwChoosen\" size = \"1\">");
            for (int i = 0; i < instructor.GetHomeworkNum(course); i++) {
                out.print("<option value = HW" + (i + 1) + "> HW" + (i + 1) + "</option>");
            }
            out.print("</select>");

            out.print("<input type=\"text\" name=\"hwgrade\">");

            out.print("<input type=\"submit\" value=\"Assign Grade\" >");
        %>
    </body>
</html>
