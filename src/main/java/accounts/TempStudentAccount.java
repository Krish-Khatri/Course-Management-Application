package accounts;

import database.Database;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Krish Khatri
 */
@Named("TempStudentAccount")
@SessionScoped
public class TempStudentAccount implements Serializable {
    private String first_name;
    private String last_name;
    private String id;
    private String user_name;
    private String password;
    private String user_nameConf;
    private String passwordConf;

    public TempStudentAccount(){}

    public String getPasswordConf() {
        return passwordConf;
    }

    public String getUser_nameConf() {
        return user_nameConf;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getId() {
        return id;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setPasswordConf(String passwordConf) {
        this.passwordConf = passwordConf;
    }

    public void setUser_nameConf(String user_nameConf) {
        this.user_nameConf = user_nameConf;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String Registration(){
        String return_string = "";

        if(!user_name.equals(user_nameConf) || !password.equals(passwordConf)){
            return_string = "StudentErrorPage";
        } else {
            if(InDatabase()){
                return_string = "StudentErrorPage";
            } else {
                InsertStudent();
                return_string = "StudentCreated";
            }
        }
        return return_string;
    }

    public boolean InDatabase(){
        boolean exists = false;
        String sql = "select id, lastname, firstname from StudentAccounts where username = ? and password = ?";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            preparedStatement.setString(1, this.user_name);
            preparedStatement.setString(2, this.password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                exists = true;
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
        return exists;
    }

    private void InsertStudent(){
        String sql = "insert into StudentAccounts(id, lastname, firstname, username, password) values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            preparedStatement.setString(1, this.id);
            preparedStatement.setString(2, this.last_name);
            preparedStatement.setString(3, this.first_name);
            preparedStatement.setString(4, this.user_name);
            preparedStatement.setString(5, this.password);
            preparedStatement.executeUpdate();
        } catch (SQLException se) {
            se.printStackTrace(System.out);
        }
    }


}
