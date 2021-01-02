package pages;

import accounts.StudentAccount;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Krish Khatri
 */
@WebServlet(name = "StudentPage", urlPatterns = {"/StudentPage"})
public class StudentLoginPage extends HttpServlet {

    /**
     * Process Http requests
     * Both Get and Post methods
     *
     * @param request servlet request;
     * @param response servlet response;
     * @throws ServletException
     * @throws IOException
     */
    protected void RequestProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        try {
            pw.println("<html>");
            pw.println("<head>");
            pw.println("<title>StudentPage Servlet</title>");
            pw.println("</head>");
            pw.println("<body>");
            pw.println("<h1>StudentPage Servlet at " + request.getContextPath() + "</h1>");
            pw.println("</body>");
            pw.println("</html>");
        } finally {
            pw.close();
        }
    }

    /**
     * Handle Http
     * Get method
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

        pw.println("<form method=\"post\" action =\"" + request.getContextPath() + "/StudentPage\">");
        pw.println("Log In Here");
        pw.println("<p> User Name:");
        pw.println("<input type = \"text\" name = \"username\" size = \"20\">");
        pw.println("<p>Password:");
        pw.println("<input type = \"text\" name = \"password\" size =  \"20\">");

        pw.println("<p><input type=\"submit\" value=\"Confirm\" >");
        pw.println("</form>");
        pw.println("</body></html>");
    }

    /**
     * Handle Http
     * Post method
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

        String user_name = request.getParameter("username");
        String password = request.getParameter("password");

        if(user_name.equals("") || password.equals("")) {
            pw.println("Please enter a username and password");
        } else {
            StudentAccount studentAccount = new StudentAccount(user_name,password);
            try {
                if(studentAccount.InDatabase()){
                    session.setAttribute("Student", studentAccount);
                    pw.println("<table border=\"0\" style=\"background-color:\" width=\"100%\" cellpadding=\"3\" cellspacing=\"3\">");
                    pw.println("<tr>");
                    pw.println("<td>Welcome " + studentAccount.GetFirstName() + " " + studentAccount.GetLastName() + "</td>");
                    pw.println("</tr>");
                    pw.println("<tr>");
                    pw.println("<td>ID: " + studentAccount.GetID() + "</td>");
                    pw.println("</tr>");
                    pw.println("<td><a href=\"StudentCoursePage\">Check Courses</a> </td>");
                    pw.println("</tr>");
                    pw.println("<tr>");
                    pw.println("<td> <a href=\"StudentUploadHwPage\">Upload HW</a> </td>");
                    pw.println("</tr>");
                    pw.println("</table>");
                    pw.println("</form>");
                } else {
                    pw.println("Sorry, we were unable to find you");
                    pw.println("</br>");
                    pw.println("Please contact the administrator");
                    pw.println("<p><form method=\"get\" action=" + "StudentPage>");
                    pw.println("<p><input type=\"submit\" value=\"Go Back\" >");
                    pw.println("</form");
                }
            }catch (Exception e){
                pw.print(e.getMessage());
            }
        }
    }
}
