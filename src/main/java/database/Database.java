package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Krish Khatri
 */
public class Database {
    private final static String className = "com.mysql.jdbc.Driver";
    private final static String url = "jdbc:mysql://localhost:3306/course_management";
    private final static String user_name = "root";
    private final static String password = "";
    private static Connection connection;

    /**
     * Returns a database connection.
     *
     * @return database connection
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection ConnectDatabase(){
        if (connection == null){
            try {
                Class.forName(className);
                connection = DriverManager.getConnection(url, user_name, password);
            } catch (ClassNotFoundException cnfe){
                cnfe.printStackTrace(System.out);
            } catch (SQLException se){
                se.printStackTrace(System.out);
            }
        }
        return connection;
    }

}
