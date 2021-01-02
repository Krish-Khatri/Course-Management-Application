package accounts;

import database.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Krish Khatri
 */
public class StudentAccount extends Account {
    private ArrayList<String> courses;

    public StudentAccount(){}
    public StudentAccount(String username , String password){
        this.user_name = username;
        this.password = password;
    }

    public String GetFirstName(){
        return first_name;
    }

    public String GetLastName(){
        return last_name;
    }

    public String GetID(){
        return id;
    }

    /**
     * Get courses student is taking
     * @return Courses Student is Taking
     */
    public ArrayList<String> getCourses(){
        String sql = "select coursename from courses";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            courses = new ArrayList<String>();
            while (resultSet.next()){
                sql = "select * from " + resultSet.getString(1) + "course where studentid = " + id;
                preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
                ResultSet studentsinclass = preparedStatement.executeQuery();
                if (studentsinclass.next()){
                    courses.add(resultSet.getString(1));
                }
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
        return courses;
    }

    /**
     * Checks if student exists
     * @return True if student is in database
     */
    @Override
    public boolean InDatabase(){
        boolean exists = false;
        String sql = "select id, lastname, firstname, from StudentAccounts where username = ? and password = ?";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            preparedStatement.setString(1,this.user_name);
            preparedStatement.setString(2,this.password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                initAccount(resultSet);
                exists = true;
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
        return exists;
    }

    private void initAccount(ResultSet resultSet){
        try {
            this.id = resultSet.getString(1);
            this.last_name = resultSet.getString(2);
            this.first_name = resultSet.getString(3);
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }
}
