package accounts;

import database.Database;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.jsp.JspWriter;

/**
 * @author Krish Khatri
 */
public class InstructorAccount extends Account{
    private ArrayList<String> courses;
    private ArrayList<String> students;
    private int homework_num;

    public InstructorAccount() {}
    public void log_in_info(String user_name, String password){
        this.user_name = user_name;
        this.password = password;
    }
    public String getFirst_name(){
        return first_name;
    }
    public String getLast_name(){
        return last_name;
    }

    /**
     * Get List of Student's in class
     * @param course Course Search
     * @return Students in course
     */
    public ArrayList<String> GetStudentList(String course){
        String sql = "select studentid from " + course + "course";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            ResultSet result_set = preparedStatement.executeQuery();

            students = new ArrayList<String>();
            while (result_set.next()){
                sql = "select  id, firstname, lastname from studentaccounts where id = " + result_set.getString(1);
                preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
                ResultSet students_in_class = preparedStatement.executeQuery();
                if(students_in_class.next()){
                    students.add(students_in_class.getString(1) + " - " + students_in_class.getString(2)+ " " + students_in_class.getString(3));
                }
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
        return students;
    }

    /**
     * Get courses instructor is teaching
     * @return Courses instructor is teaching
     */
    public ArrayList<String> getCourses(){
        String sql = "select coursename from courses";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            ResultSet result_set = preparedStatement.executeQuery();

            courses = new ArrayList<String>();
            while (result_set.next()){
                sql = "select * from " + result_set.getString(1) + "course where instructorid = " + id;
                preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
                ResultSet instructor_in_class = preparedStatement.executeQuery();
                if(instructor_in_class.next()){
                    courses.add(result_set.getString(1));
                }
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
        return courses;
    }

    /**
     * Check if instructor is in database
     * @return True if instructor in database exists
     */
    @Override
    public boolean InDatabase() {
        boolean exists = false;
        String sql = "select id, lastname, firstname from InstructorAccounts where username = ? and password = ?";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            preparedStatement.setString(1, this.user_name);
            preparedStatement.setString(2, this.password);

            ResultSet result_set = preparedStatement.executeQuery();
            if(result_set.next()){
                initAccount(result_set);
                exists = true;
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
        return exists;
    }

    /**
     * Initialize fields for instructor
     * @param result_set Result set from InDatabase();
     */
    private void initAccount(ResultSet result_set){
        try{
            this.id = result_set.getString(1);
            this.last_name = result_set.getString(2);
            this.first_name = result_set.getString(3);
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }

    /**
     * Get number of homeworks for course
     * @param course
     * @return homeworkNum
     */
    public int GetHomeworkNum(String course){
        int homeworkNum = 0;
        String sql = "select numHw from courses where coursename = \'" + course + "\'";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            ResultSet result_set = preparedStatement.executeQuery();
            if (result_set.next()){
                homeworkNum = result_set.getInt("nuwHw");
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
        return homeworkNum;
    }

    /**
     * Assign total amount of homework for a course
     * @param course
     * @param homeworkNum
     */
    private void SetHomeworkNum(String course, int homeworkNum){
        String sql = "update courses set numHw = " + Integer.toString(homeworkNum) + " where coursename = \'" + course + "\'";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            preparedStatement.executeQuery();
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }

    /**
     * Add course to course table
     * @param course
     */
    private void AppendToCourseTable(String course){
        String sql = "alter table " + course + "course add Hw" + homework_num + " int";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }

    /**
     * Assign homework to course
     * @param course
     * @param assignment
     */
    public void setHw(String course, String assignment){
        homework_num = GetHomeworkNum(course);
        SetHomeworkNum(course, ++homework_num);
        AppendToCourseTable(course);

        Writer output = null;
        File file = new File("C:\\Users\\"+ System.getProperty("user.name") + "\\IdeaJProjects\\CourseManagementApplication\\Course-ManagementApplication\\src\\main\\" + course + "\\HW" + homework_num + ".txt");
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(assignment);
        } catch (IOException ie){
            ie.printStackTrace(System.out);
        } finally {
            try {
                output.close();
            } catch (IOException ie){
                ie.printStackTrace(System.out);
            }
        }
    }

    /**
     * Get Grade for specified homework assignment for specified student
     * @param student
     * @param course
     * @param assignment
     * @param grade
     */
    public void AssignGrade(String student, String course, String assignment, String grade){
        String sql = "update " + course + "course set " + assignment + " = \'" + grade + "\' where studentid = \'" + student + "\'";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }
}
