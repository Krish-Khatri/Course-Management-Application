package pages;

import accounts.StudentAccount;
import database.Database;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Krish Khatri
 */
@WebServlet(name = "StudentUploadHwPage", urlPatterns = {"/StudentUploadHwPage"})
public class StudentUploadHwPage extends HttpServlet {

    /**
     * Process Http requests
     * Get and Post Methods
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException
     * @throws IOException
     */
    protected void RequestProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        try {
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>Servlet StudentUploadHwPage</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h1>Servlet StudentUploadHwPage at " + request.getContextPath() + "</h1>");
            pw.println("</body>");
            pw.println("</html>");
        } finally {
            pw.close();
        }
    }

    /**
     * Http Handler, Get Method
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
        StudentAccount account = (StudentAccount) session.getAttribute("Student");
        ArrayList<String> courses = account.getCourses();

        pw.println("<form method=\"post\" action =\"" + request.getContextPath() + "/StudentUploadHwPage\">");


        pw.println("<p>Courses Currently registered in: ");
        pw.print("<select name = \"courseRegIn\" size = \"1\">");
        for (int i = 0; i < courses.size(); i++) {
            pw.print("<option value = " + courses.get(i) + ">" + courses.get(i) + "</option>");
        }
        pw.print("</select>");

        pw.println("<p><input type=\"submit\" value=\"Confirm\" >");
        pw.println("</form>");
        pw.println("</body></html>");
    }

    /**
     * Http handler, Post method
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

        String course = request.getParameter("courseRegIn");
        pw.print("<b><u>" + course + "</u> </b>");
        int number_of_homework = GetHomeworkAmount(request.getParameter("courseRegIn"));

        pw.print("<form action=\"UploadServlet\" method=\"post\" enctype=\"multipart.form-data\"> </td>");
        pw.print("File should include First and Last Name:");
        if(number_of_homework == 0){
            pw.print("<table border=\"1\">");
            pw.print("<tr>");
            pw.print("<td> Homework # </td>");
            pw.print("<td> Homework Description </td>");
            pw.print("<td> Upload Homework </td>");
            pw.print("</tr>");
            pw.print("<tr>");
            pw.print("<td> # </td>");
            pw.print("<td> No Homework assigned </td>");
            pw.print("</tr>");
            pw.print("</table>");
        } else {
            pw.print("<table border=\"1\">");
            pw.print("<tr>");
            pw.print("<td> Homework # </td>");
            pw.print("<td> Homework Description </td>");
            pw.print("<td> Upload Homework <input type=\"file\"  name=\"file\"/> </td>");
            pw.print("</tr>");

            for (int i = 0; i < number_of_homework; i++) {
                pw.print("<tr>");
                pw.print("<td>HW # " + (i + 1) + "</td>");
                pw.print("<td> " + GetHomeworkDesc(course, (i + 1)) + " </td>");
                pw.print("<td> <input type=\"submit\" value=\"Upload File\" /> </td>");
                pw.print("</tr>");
            }
            pw.print("</table>");
            pw.print("</form>");
        }
    }

    @Override
    public String getServletInfo(){
        return "Short Description";
    }

    /**
     * Get Number of Assigned Homeworks for Course
     * @param course
     * @return amount of homework for specific class
     */
    private int GetHomeworkAmount(String course){
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
     * Get description of homework
     * @param course
     * @param hwNumber
     * @return string containing homework description
     */
    private String GetHomeworkDesc(String course, int hwNumber) {
        String path = "C:\\Users\\" + System.getProperty("user.name") + "\\IdeaProjects\\CourseManagementApplication\\Course-Management-Application\\src\\main\\" + course;
        String desc = "Not Available";

        String file;
        File folder = new File(path);
        File[] file_list = folder.listFiles();

        for (int i = 0; i < file_list.length; i++){
            if(file_list[i].isFile()){
                file = file_list[i].getName();
                if(file.endsWith(".txt") || file.endsWith("TXT")){
                    if (file_list[i].getName().equals("HW"+hwNumber+".txt")){
                        try {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file_list[i])));
                            String line = "";
                            desc = "";
                            while ((line = bufferedReader.readLine()) != null){
                                desc += line;
                                desc += "\n";
                            }
                        } catch (FileNotFoundException fnfe){
                            desc += "filenotfound";
                            fnfe.printStackTrace(System.out);
                        } catch (IOException ie){
                            desc += "ioexception";
                            ie.printStackTrace(System.out);
                        }
                    }

                }
            }
        }
        return desc;
    }
}
