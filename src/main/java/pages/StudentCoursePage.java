package pages;

import accounts.StudentAccount;
import database.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Krish Khatri
 */
@WebServlet(name = "StudentCoursePage", urlPatterns = {"/StudentCoursePage"})
public class StudentCoursePage extends HttpServlet {

    /**
     * HTTP request processors
     *
     * @param request servlet request
     * @param upload response of servlet
     * @throws ServletException
     * @throws IOException
     */
    protected void RequestProcessor(HttpServletRequest request, HttpServletResponse upload) throws ServletException, IOException {
        upload.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = upload.getWriter();
        try {
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>StudentCoursePage Servlet</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h1>StudentCoursePage Servlet at " + request.getContextPath() + "</h1>");
            pw.println("</body>");
            pw.println("</html>");
        } finally {
            pw.close();
        }
    }

    /**
     * HTTP Handler (Get)
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        HttpSession session = request.getSession();
        StudentAccount student_account = (StudentAccount) session.getAttribute("Student");
        ArrayList<String> courses = student_account.getCourses();

        pw.println("<form method=\"post\" action =\"" + request.getContextPath() + "/StudentCoursePage\">");
        pw.println("<p>Courses Registered: ");
        pw.println("<select name = \"courseRegIn\" size = \"1\">");
        for(int i = 0; i < courses.size(); i++){
            pw.print("<option value = " + courses.get(i) + ">" + courses.get(i) + "</option>");
        }
        pw.print("</select>");
        pw.println("<p><input type=\"submit\" value=\"Confirm\" >");
        pw.println("</form>");
        pw.println("</body></html>");
    }

    /**
     * HTTP Handler (Post)
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        HttpSession session = request.getSession();
        StudentAccount studentAccount = (StudentAccount) session.getAttribute("Student");
        String course_name = request.getParameter("courseRegIn");
        pw.print(course_name);
        int amount_of_homework = GetNumofHomework(course_name);

        if(amount_of_homework == 0){
            pw.print("<table border=\"1\">");
            pw.print("<tr>");
            pw.print("<td> Homework # </td>");
            pw.print("<td> Homework Grade </td>");
            pw.print("</tr>");
            pw.print("<tr>");
            pw.print("<td> No Homework is Assigned </td>");
            pw.print("<td> <center> # </center> </td>");
            pw.print("</tr>");
            pw.print("</table>");
        } else {
            pw.print("<table border=\"1\">");
            pw.print("<tr>");
            pw.print("<td> Homework # </td>");
            pw.print("<td> Homework Grade </td>");
            pw.print("</tr>");

            for (int i = 0; i < amount_of_homework; i++){
                pw.print("<tr>");
                pw.print("<td>HW # " + (i+1) + "</td>");
                if(GetHomeworkGrade(studentAccount,course_name, (i+1)) == null){
                    pw.print("<td>WAITING</td>");
                } else {
                    pw.print("<td>" + GetHomeworkGrade(studentAccount, course_name, (i+1)) + "</td>");
                }
                pw.print("</tr");
            }
            pw.print("</tr>");
            pw.print("</table");
        }
    }

    /**
     * Gives a description of the servlet
     * @return String with servlet description
     */
    @Override
    public String getServletInfo(){
        return "Quick Description";
    }

    /**
     * Get Number of Assigned Homeworks for Course
     * @param course
     * @return amount of homework for specific class
     */
    private int GetNumofHomework(String course){
        int number_of_homework = 0;
        String sql = "select * from " + course + "course";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            number_of_homework = resultSetMetaData.getColumnCount()-3;
        } catch (SQLException se){
            se.printStackTrace(System.out);
            number_of_homework = 1000;
        }

        return number_of_homework;
    }

    /**
     * Return Grade of Homework
     * @param student_account
     * @param course
     * @param number_of_homework
     * @return grade of homework
     */
    private String GetHomeworkGrade(StudentAccount student_account, String course, int number_of_homework){
        String grade = "";
        String sql = "select Hw" + number_of_homework + " from " + course + "course where studentid = \'" + student_account.GetID() + "\'";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                grade = resultSet.getString(1);
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
        return grade;
    }
}
