package accounts;

import database.Database;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Krish Khatri
 */
@Named("AdministratorAccount")
@SessionScoped

public class AdministratorAccount implements Serializable {

    private String first_name;
    private String last_name;
    private String user_name;
    private String password;
    private String id;

    public AdministratorAccount(){}

    public String getUser_name(){
        return user_name;
    }

    public String getFirst_name(){
        return first_name;
    }

    public String getLast_name(){
        return last_name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getPassword(){
        return password;
    }

    public void setUser_name(String user_name){
        this.user_name = user_name;
    }

    /**
     *
     * Correct information => login admin
     *
     * @return Page to go
     */
    public String validateAdmin(){
        String result = "";
        if(user_name.equals("") || password.equals("")){
            result = "AdministratorNotValidPage";
        } else {
            if(InDatabase()){
                result = "AdministratorValidPage";
            } else {
                result = "AdministratorNotFoundPage";
            }
        }
        return result;
    }

    /**
     * Set up admin account
     * @param result_set Result set from InDatabase()
     */
    private void initializeAccount(ResultSet result_set){
        try{
            this.id = result_set.getString(1);
            this.last_name = result_set.getString(2);
            this.first_name = result_set.getString(3);
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
    }

    /**
     * Check if admin exists in database
     * @return True if admin exists in database
     */
    public boolean InDatabase(){
        boolean exists = false;
        String sql = "select id, lastname, firstname from AdministratorAccounts where username = ? and password = ?";
        try{
            PreparedStatement preparedStatement = Database.ConnectDatabase().prepareStatement(sql);
            preparedStatement.setString(1, this.user_name);
            preparedStatement.setString(2, this.password);

            ResultSet result_set = preparedStatement.executeQuery();
            if(result_set.next()){
                initializeAccount(result_set);
                exists = true;
            }
        } catch (SQLException se){
            se.printStackTrace(System.out);
        }
        return exists;
    }
}
