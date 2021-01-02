package courses;

import database.Database;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Krish Khatri
 */
@Named
@RequestScoped
public class Course {
    private String name;
    private String id;
    private ArrayList<String> instructors;
    private ArrayList<String> students;
    private ArrayList<String> selected_instructors;
    private ArrayList<String> selected_students;

    public Course(){
        InstructorsInCourse();
        StudentsInDB();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getInstructors() {
        return instructors;
    }

    public void setInstructors(ArrayList<String> instructors) {
        this.instructors = instructors;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<String> students) {
        this.students = students;
    }

    public ArrayList<String> getSelected_instructors() {
        return selected_instructors;
    }

    public void setSelected_instructors(ArrayList<String> selected_instructors) {
        this.selected_instructors = selected_instructors;
    }

    public ArrayList<String> getSelected_students() {
        return selected_students;
    }

    public void setSelected_students(ArrayList<String> selected_students) {
        this.selected_students = selected_students;
    }

    /**
     * Create course table containing students and instructors in course
     * when new course is created
     */
    private void CreateCourseTable(){
        String sql = "create table " + name + "Course ( id varchar(4), instructorid varchar(4), studentId varchar(4) )";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            preparedStatement.executeUpdate();

            CreateHomeworkFolder();
            InsertCourseTable();
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }

    /**
     * Add  to list of courses, when course is created
     */
    private void InsertCourseTable(){
        String sql = "insert into courses (id, coursename, numHw) values (?,?,?)";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }
    /**
     * Get All Instructors
     */
    private void InstructorsInCourse(){
        String sql = "select id, firstname, lastname from instructoraccounts";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            instructors = new ArrayList<String>();
            while (rs.next()){
                instructors.add(rs.getString(1) + " - " + rs.getString(2) + " " + rs.getString(3));
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }

    /**
     * Get All students
     */
    private void StudentsInDB(){
        String sql = "select id, firstname, lastname from studentaccounts";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            students = new ArrayList<String>();
            while (rs.next()){
                students.add(rs.getString(1) + " - " + rs.getString(2) + " " + rs.getString(3));
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }

    /**
     * Validate if course has met requirements
     * @return directed page
     */
    public String CreateCourse(){
        String page_directed = "";

        if(name.equals("") || id.equals("") || selected_instructors.size() == 0 || selected_instructors.size() > 2 || selected_students.size() < 5 || selected_students.size() > 30 ){
            page_directed = "CourseErrorPage";
        } else if (CourseTableExists()){
            page_directed = "CourseErrorPage";
        } else {
            CreateCourseTable();
            InsertCourseIntoDB();
            page_directed  = "CourseCreatedPage";
        }
        return page_directed;
    }

    /**
     * Returns True if course table already exists
     * @return True if course in database
     */
    private boolean CourseTableExists(){
        boolean exists = false;
        String sql = "select * from courses where id = " + id;
        try{
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            students = new ArrayList<String>();

            if(resultSet.next()){
                exists = true;
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
        return exists;
    }

    /**
     * Insert the instructors and students taking course into database
     */
    private void InsertCourseIntoDB(){
        String sql = "insert into " + name + "course (id, instructorid) values (?,?)";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            for (int i = 0; i < selected_instructors.size(); i++){
                preparedStatement.setString(1, id);
                String InstructorId = selected_instructors.get(i).substring(0,4);
                preparedStatement.setString(2, InstructorId);
                preparedStatement.executeUpdate();
            }

            sql = "insert into " + name + "course (id, studentid) values (?, ?)";
            preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            for (int i = 0; i < selected_students.size(); i++){
                preparedStatement.setString(1, id);
                String StudentId = selected_students.get(i).substring(0,4);
                preparedStatement.setString(2, StudentId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }

    /**
     * Create Homework Folder for New Course
     */
    private void CreateHomeworkFolder(){
        File dir = new File("C:\\Users\\" + System.getProperty("user.name") + "\\IdeaProjects\\CourseManagementApplication\\Course-Management-Application\\src\\main" + name);
        dir.mkdir();
    }
}
